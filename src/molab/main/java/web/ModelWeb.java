package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.ModelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/model")
public class ModelWeb {
	
	@Autowired
	private ModelService service;
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate/{id}/{manufacturerId}/{name}/{occupancy}/")
	public String saveOrUpdate(@PathVariable int id, @PathVariable int manufacturerId, 
			@PathVariable String name, @PathVariable float occupancy) {
		int data = service.saveOrUpdate(id, manufacturerId, name, occupancy);
		return String.valueOf(data);
	}
	
	@ResponseBody
	@RequestMapping(value = "/remove/{manufacturerId}")
	public String remove(HttpServletRequest request, @PathVariable int manufacturerId) {
		String[] ids = request.getParameterValues("ids");
		int data = service.remove(manufacturerId, ids);
		return String.valueOf(data);
	}
	
}
