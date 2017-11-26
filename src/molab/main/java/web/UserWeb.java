package molab.main.java.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.component.AssetsComponent;
import molab.main.java.entity.User;
import molab.main.java.service.DistrictService;
import molab.main.java.service.ReportService;
import molab.main.java.service.TaskService;
import molab.main.java.service.UserService;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/user")
public class UserWeb {

	private static final String HOST = "user/";
	
	@Autowired
	private DistrictService ds;
	
	@Autowired
	private ReportService rs;
	
	@Autowired
	private TaskService ts;

	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/main")
	public ModelAndView main(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "main");
		if(session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			mav.addObject("tcList", ts.findAll(user));
		}
		return mav;
	}
	
	@RequestMapping(value = "/build")
	public ModelAndView build(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "build");
		mav.addObject("districtList", ds.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/success")
	public ModelAndView success(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "success");
		return mav;
	}
	
	@RequestMapping(value = "/info")
	public ModelAndView info(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "info");
		if(session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			mav.addObject("user", user);
		}
		return mav;
	}
	
	@RequestMapping(value = "/signin")
	public ModelAndView signinPage(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "signin");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/signinAction")
	public String signinAction(HttpServletRequest request, HttpSession session) {
		String username = request.getParameter("l_u");
		String password = request.getParameter("l_p");
		Object obj = service.signin(username, password);
		Integer json = Status.UserStatus.NORMAL.getInt();
		if(obj instanceof User) {
			User user = (User) obj;
			if(json.compareTo(user.getState()) == 0) {
				session.setAttribute("user", user);
			} else {
				json = user.getState();
			}
		} else {
			json = (Integer) obj;
		}
		return new Gson().toJson(json);
	}
	
	@RequestMapping(value = "/signup")
	public ModelAndView signupPage(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "signup");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/check/{username}")
	public String check(HttpServletRequest request, @PathVariable String username) {
		int json = service.check(username);
		return new Gson().toJson(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/signupAction")
	public String signupAction(HttpServletRequest request, HttpSession session) {
		String username = request.getParameter("r_u");
		String password = request.getParameter("r_p");
		String email = request.getParameter("r_email");
		int json = service.signup(username, password, email);
		return new Gson().toJson(json);
	}
	
	@RequestMapping(value = "/signout")
	public ModelAndView signout(HttpSession session) {
		ModelAndView mav = new ModelAndView("index");
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkAssets/{id}")
	public String checkAssets(HttpServletRequest request, HttpSession session,
			@PathVariable int id) {
		// users
		String usersString = request.getParameter("users");
		if(usersString == null || usersString == "") {
			usersString = Molab.DEFAULT_USERS;
		}
		int users = Integer.parseInt(usersString);
		// retention
		int dayRetention = Integer.parseInt(request.getParameter("dayRetention"));
		int weekRetention = Integer.parseInt(request.getParameter("weekRetention"));
		int monthRetention = Integer.parseInt(request.getParameter("monthRetention"));
		// startDay
		String startDay = request.getParameter("startDate");
		if(startDay == null || "".equals(startDay)) {
			startDay = Molab.today();
		}
		// endDay
		String endDay = request.getParameter("endDate");
		if(endDay == null || "".equals(endDay)) {
			endDay = Molab.today();
		}
		AssetsComponent ac = new AssetsComponent();
		ac.setState(Status.Common.ERROR.getInt());
		try {
			ac = service.checkAssets(id, users, dayRetention, weekRetention, monthRetention, startDay, endDay);
		} catch (ParseException e) {
			ac.setMessage(e.getMessage());
		}
		return new Gson().toJson(ac);
	}
	
}
