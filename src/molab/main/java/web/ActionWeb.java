package molab.main.java.web;

import molab.main.java.service.ActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/action")
public class ActionWeb {
	
	@Autowired
	private ActionService service;
	
	@ResponseBody
	@RequestMapping(value = "/offline/{id}")
	public String offline(@PathVariable int id) {
		int data = service.offline(id);
		return String.valueOf(data);
	}
	
}
