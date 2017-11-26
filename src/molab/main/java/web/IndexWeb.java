package molab.main.java.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexWeb {
	
	@RequestMapping(value = "/")
	public String _() {
		return "redirect:user/signin";
	}
	
	@RequestMapping(value = "/admin")
	public String admin() {
		return "redirect:admin/main";
	}
	
}
