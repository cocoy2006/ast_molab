package molab.main.java.web;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.entity.User;
import molab.main.java.service.TaskService;
import molab.main.java.service.UserService;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;
import molab.main.java.util.fileupload.FileUploadListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/task")
public class TaskWeb {
	
	private static final Logger log = Logger.getLogger(TaskWeb.class.getName());
	
	@Autowired
	private TaskService service;
	
	@Autowired
	private UserService us;

	@ResponseBody
	@RequestMapping(value = "/uploadApp", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, HttpSession session) {
		int result = Status.Common.ERROR.getInt();
		try {
			User user = (User) session.getAttribute("user");
			// parameters
			// -- users
			String usersString = request.getParameter("users");
			if(usersString == null || "".equals(usersString)) {
				usersString = Molab.DEFAULT_USERS;
			}
			int users = Integer.parseInt(usersString);
			// -- conversion
			int conversion = Integer.parseInt(request.getParameter("conversion"));
			// -- retention
			int dayRetention = Integer.parseInt(request.getParameter("dayRetention"));
			int weekRetention = Integer.parseInt(request.getParameter("weekRetention"));
			int monthRetention = Integer.parseInt(request.getParameter("monthRetention"));
			// districts
			String district = request.getParameter("district");
			// -- start day
			String startDay = request.getParameter("startDate");
			if(startDay == null || "".equals(startDay)) {
				startDay = Molab.tomorrow();
			}
			// -- end day
			String endDay = request.getParameter("endDate");
			if(endDay == null || "".equals(endDay)) {
				endDay = Molab.tomorrow();
			}
			// -- period
			int periodLength = Integer.parseInt(request.getParameter("periodLength"));
			Integer[][] periodArray = new Integer[periodLength][3];
			for(int i = 0; i < periodLength; i++) {
				periodArray[i][0] = Integer.parseInt(request.getParameter("startPeriod" + String.valueOf(i)));
				periodArray[i][1] = Integer.parseInt(request.getParameter("endPeriod" + String.valueOf(i)));
				periodArray[i][2] = Integer.parseInt(request.getParameter("percent" + String.valueOf(i)));
			}
			// -- pid
			String pidString = request.getParameter("pid");
			if(pidString == null || "".equals(pidString)) {
				pidString = "0";
			}
			Integer pid = Integer.parseInt(pidString);
			// parameters end
			String path = Molab.getUploadDir();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			
			result = service.build(file, path, user.getId(), users, conversion,
					dayRetention, weekRetention, monthRetention, district,
					startDay, endDay, periodArray, pid);
			if(result == Status.Common.SUCCESS.getInt()) {
				user = us.findById(user.getId());
				session.setAttribute("user", user);
			}
			// force set percentDone to 100%
			FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener");
			if (listener != null) {
				listener.setPercentDone(100);
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return new Gson().toJson(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadAction", method = RequestMethod.POST)
	public String action(HttpServletRequest request, HttpSession session) {
		int result = Status.Common.ERROR.getInt();
		try {
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			int actionType = Integer.parseInt(request.getParameter("actionType"));
			String path = Molab.getUploadDir();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> files = multipartRequest.getFiles("actions");
			result = service.parseAction(files, path, taskId, actionType);
			// force set percentDone to 100%
			FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener");
			if (listener != null) {
				listener.setPercentDone(100);
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return new Gson().toJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "/percentDone", method = RequestMethod.GET)
	public String loadPercent(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "{\"data\":-2}";
		}
		FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener");
		if (listener == null) {
			return "{\"data\":-1}";
		}
		double percent = listener.getPercentDone();
		return "{\"data\":" + String.valueOf(percent) + "}";
	}
	
}
