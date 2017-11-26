package molab.main.java.util;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import molab.main.java.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Repository
public class Interceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = Logger.getLogger(Interceptor.class.getName());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String[] noFilters = new String[] { "admin/signin", "user/signin", "user/signup", "user/signout", "api" };
		String uri = request.getRequestURI();
		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {
			Object obj = request.getSession().getAttribute("user");
			if (null == obj) {
				if(uri.indexOf("admin") > -1) {
					response.sendRedirect("/" + uri.split("/")[1] + "/admin/signin");
				} else {
					response.sendRedirect("/" + uri.split("/")[1] + "/user/signin");
				}
				return false;
			} else {
				if(uri.indexOf("admin") > -1) {
					User user = (User) obj;
					if(user.getRole().equals(Status.UserRole.USER.getInt())) {
						response.sendRedirect("/" + uri.split("/")[1] + "/admin/signin");
						return false;
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
