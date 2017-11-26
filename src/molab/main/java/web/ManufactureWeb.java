package molab.main.java.web;

import molab.main.java.service.ManufactureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manufacturer")
public class ManufactureWeb {
	
	@Autowired
	private ManufactureService service;
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate/{id}/{name}")
	public String saveOrUpdate(@PathVariable int id, @PathVariable String name) {
		int data = service.saveOrUpdate(id, name);
		return String.valueOf(data);
	}
	
	@ResponseBody
	@RequestMapping(value = "/remove/{id}")
	public String remove(@PathVariable int id) {
		int data = service.remove(id);
		return String.valueOf(data);
	}
	
}
