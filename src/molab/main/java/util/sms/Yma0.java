package molab.main.java.util.sms;

import java.util.logging.Logger;

import molab.main.java.util.HttpUtil;

public class Yma0 {

	private static final Logger log = Logger.getLogger(Yma0.class.getName());
	private static final String SERVER_HOST = "http://api.yma0.com/http.aspx?action=";
	private static final String uid = "appst,";
	private static final String pwd = "appst,123456";
	private static String token = "21c99df0f7b72e65";
	
	public static void token() {
		String url = SERVER_HOST + "loginIn&uid=" + uid + "&pwd=" + pwd;
		String token = HttpUtil.get(url);
		if(token != null) {
			Yma0.token = token.substring(token.indexOf("|") + 1);
		}
	}
	
	public static String getMobilenum(Integer pid) {
		String url = SERVER_HOST + "getMobilenum&pid=" + pid + "&uid=" + uid + "&token=" + token;
		String mobile = HttpUtil.get(url);
		if(mobile.indexOf("|") > -1 && !mobile.startsWith("message")) { // OK
			return mobile.substring(0, mobile.indexOf("|"));
		} else if(mobile.indexOf("not_login") > -1) {
			token();
			getMobilenum(pid);
		} 
		return null;
	}
	
	private static long period = 5000; // 5s
	public static String getVcodeAndReleaseMobile(Integer pid, String mobile) {
		long total = 100000; // 100s
		while(total > 0) {
			String url = SERVER_HOST + "getVcodeAndReleaseMobile&pid=" + pid
					+ "&uid=" + uid + "&token=" + token + "&mobile=" + mobile + "&author_uid=" + uid;
			String vcode = HttpUtil.get(url);
			if(vcode.indexOf("|") > -1 && !vcode.startsWith("message")) { // OK
				return vcode.substring(vcode.indexOf("|") + 1);
			} else if(vcode.indexOf("not_login") > -1) { // 没有登录,在没有登录下去访问需要登录的资源，忘记传入uid,token
				token();
			} else if(vcode.indexOf("not_receive") > -1) { // 还没有接收到验证码,请让程序等待几秒后再次尝试
				total -= period;
				try {
					Thread.sleep(period);
				} catch (InterruptedException e) {
					log.severe(e.getMessage());
				}
			}
		}
		return null;
	}
	
	/**
	 * getVcodeAndReleaseMobile() contains this:
	 *  
	public static String releaseMobile(String mobile) {
		String url = SERVER_HOST + "ReleaseMobile&uid=" + uid + "&token="
				+ token + "&mobile=" + mobile;
		return HttpUtil.get(url);
	}
	*/
	
}
