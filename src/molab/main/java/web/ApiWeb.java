package molab.main.java.web;

import java.text.ParseException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.component.api.RegisterJson;
import molab.main.java.component.api.TaskJson;
import molab.main.java.component.api.VcodeJson;
import molab.main.java.service.ApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/api")
public class ApiWeb {
	
	private static final Logger log = Logger.getLogger(ApiWeb.class.getName());

	@Autowired
	private ApiService service;
	
	@RequestMapping(value = "/")
	public ModelAndView main(HttpSession session) {
		ModelAndView mav = new ModelAndView("api");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/register")
	public String register() {
		int id = service.register();
		RegisterJson json = new RegisterJson(id);
		return new Gson().toJson(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/{agentid}/{action}")
	public String task(HttpServletRequest request, @PathVariable int agentid,
			@PathVariable int action) throws ParseException {
		if(action == 1) { // task to agent
			String[] packagenames = request.getParameterValues("procname");
			TaskJson json = service.task(agentid, packagenames);
			if(json != null) {
				log.info("Task assigned to agent " + agentid);
				String result = new Gson().toJson(json);
				log.info("Response: " + result);
				return result;
			}
		} else if(action == 2) { // response from agent
			int taskid = Integer.parseInt(request.getParameter("taskid"));
			int error = Integer.parseInt(request.getParameter("error"));
			int message = 0;
			if(error > 0) {
				message = Integer.parseInt(request.getParameter("message"));
			}
			int json = service.response(taskid, agentid, error, message);
			return new Gson().toJson(json);
		} else if(action == 3) { // vcode to agent
			int taskid = Integer.parseInt(request.getParameter("taskid"));
			String mobile = request.getParameter("mobile");
			VcodeJson json = service.vcode(taskid, mobile);
			if(json != null) {
				log.info("Vcode " + json.getVcode() + " response to agent " + agentid);
				return new Gson().toJson(json);
			} else {
				log.info("No vcode response to agent " + agentid);
			}
		}
		return "{}";
	}
	
}
