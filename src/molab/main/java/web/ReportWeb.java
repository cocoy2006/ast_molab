package molab.main.java.web;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import molab.main.java.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/report")
public class ReportWeb {
	
	private static final Logger log = Logger.getLogger(ReportWeb.class.getName());

	@Autowired
	private ReportService service;
	
	@RequestMapping(value = "/{taskId}")
	public ModelAndView report(HttpSession session, @PathVariable int taskId) {
		ModelAndView mav = new ModelAndView("user/report");
		mav.addObject("tc", service.findByTaskId(taskId));
		return mav;
	}
	
}
