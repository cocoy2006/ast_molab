package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.entity.User;
import molab.main.java.service.AdminService;
import molab.main.java.service.DistrictService;
import molab.main.java.service.MetaService;
import molab.main.java.service.ServerService;
import molab.main.java.service.TaskService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/admin")
public class AdminWeb {

	private static final String HOST = "admin/";
	
	@Autowired
	private AdminService service;
	
	@Autowired
	private DistrictService ds;
	
	@Autowired
	private MetaService ms;
	
	@Autowired
	private ServerService ss;
	
	@Autowired
	private TaskService ts;
	
	@RequestMapping(value = "/")
	public ModelAndView _() {
		return user();
	}
	
	@RequestMapping(value = "/user")
	public ModelAndView user() {
		ModelAndView mav = new ModelAndView(HOST + "user");
		mav.addObject("userList", service.findUsers());
		return mav;
	}
	
	@RequestMapping(value = "/meta")
	public ModelAndView meta() {
		ModelAndView mav = new ModelAndView(HOST + "meta");
		mav.addObject("metas", ms.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/district")
	public ModelAndView district() {
		ModelAndView mav = new ModelAndView(HOST + "district");
		mav.addObject("districtList", ds.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/server")
	public ModelAndView server() {
		ModelAndView mav = new ModelAndView(HOST + "server");
		mav.addObject("serverList", ss.findAll());
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
	
	@RequestMapping(value = "/signout")
	public ModelAndView signout(HttpSession session) {
		ModelAndView mav = new ModelAndView(HOST + "signin");
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/recharge/{userId}")
	public String recharge(HttpServletRequest request, @PathVariable int userId) {
		float amount = Float.parseFloat(request.getParameter("amount"));
		int json = Status.Common.SUCCESS.getInt();
		try {
			service.recharge(userId, amount);
		} catch(Exception e) {
			json = Status.Common.ERROR.getInt();
		}
		return new Gson().toJson(json);
	}
	
}
