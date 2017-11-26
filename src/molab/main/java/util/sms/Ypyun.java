package molab.main.java.util.sms;

import java.util.logging.Logger;

import molab.main.java.util.HttpUtil;

public class Ypyun {
	
	private static final Logger log = Logger.getLogger(Ypyun.class.getName());
	private static final String SERVER_HOST = "http://api.ypyun.com/http.do?&action=";
	private static final String uid = "appst";
	private static final String pwd = "appst,123456";
	private static String token = "D0E8469D803AAC30FACDAA5AC2400BA2";
	
	public static void token() {
		String url = SERVER_HOST + "loginIn&uid=" + uid + "&pwd=" + pwd;
		String token = HttpUtil.get(url);
		if(token != null) {
			Ypyun.token = token.substring(token.indexOf("|") + 1);
			log.info("New token: " + token);
		}
	}
	
	public static String getMobilenum(Integer pid) {
		String url = SERVER_HOST + "getMobilenum&pid=" + pid 
				+ "&uid=" + uid + "&token=" + token;
		String mobile = HttpUtil.get(url);
		log.info("Mobile is: " + mobile);
		if(mobile.indexOf("|") > -1) { // OK
			mobile = mobile.substring(0, mobile.indexOf("|") - 1);
			executeBs(pid, mobile);
			return mobile;
		} else if(mobile.indexOf("not_login") > -1) {
			token();
			getMobilenum(pid);
		} 
		return null;
	}
	
	private static void executeBs(Integer pid, String mobile) {
		String url = SERVER_HOST + "executeBs&pid=" + pid + "&uid=" + uid
				+ "&token=" + token + "&mobile=" + mobile + "&step=1"
				+ "&Author=" + uid;
		String bs = HttpUtil.get(url);
		if(bs.indexOf("succ") > -1) { // OK
			log.info("Execute success.");
		} else if(bs.indexOf("not_login") > -1){
			token();
			executeBs(pid, mobile);
		} else {
			log.info("Execute failure.");
		}
	}
	
	public static String getVcode(Integer pid, String mobile) {
		return getExeResult(pid, mobile);
	}
	
	private static long period = 5000; // 5s
	private static String getExeResult(Integer pid, String mobile) {
		long total = 100000; // 100s
		while(total > 0) {
			String url = SERVER_HOST + "getExeResult&pid=" + pid
					+ "&uid=" + uid + "&token=" + token + "&mobile=" + mobile + "&step=1";
			String sms = HttpUtil.get(url);
			if(sms.indexOf("succ") > -1) { // OK
				sms = sms.substring(sms.indexOf("|") + 1);
				log.info("Sms: " + sms);
				return SmsUtil.parseVcode(pid, sms);
			} else if(sms.indexOf("not_login") > -1) { // 没有登录,在没有登录下去访问需要登录的资源，忘记传入uid,token
				log.warning(" 未登录，或未传入uid/token，或令牌错误 ");
				token();
			} else if(sms.indexOf("waitting") > -1 || sms.indexOf("timeout") > -1
					|| sms.indexOf("unknow_error") > -1) { // 还没有接收到验证码,请让程序等待几秒后再次尝试
				total -= period;
				log.info(" 还没有接收到验证码，继续等待 " + total + "ms");
				try {
					Thread.sleep(period);
				} catch (InterruptedException e) {
					log.severe(e.getMessage());
				}
			} else if(sms.indexOf("no_action") > -1) {
				log.warning(" 未执行任务 ");
				return null;
			} else if(sms.indexOf("parameter_error") > -1) {
				log.warning(" 参数错 ");
				return null;
			} else if(sms.indexOf("pid_err") > -1) {
				log.warning(" 未找到业务 ");
				return null;
			} else if(sms.indexOf("not_found_moblie") > -1) {
				log.warning(" 未发现手机号 ");
				return null;
			} else if(sms.indexOf("fail") > -1) {
				log.warning(" 执行失败 ");
				addIgnoreCard(pid, mobile);
				return null;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * @param pid 项目ID
	 * @param mobiles 以逗号分隔的手机号码 */
	public static void addIgnoreCard(Integer pid, String mobiles) {
		String url = SERVER_HOST + "addIgnoreCard&pid=" + pid + "&uid=" + uid 
				+ "&token=" + token + "&mobile=" + mobiles;
		String result = HttpUtil.get(url);
		if(result.indexOf("not_login") > -1) {
			token();
			addIgnoreCard(pid, mobiles);
		} else {
			log.info(" 加黑无用号码: " + mobiles);
		}
	}
	
	public static void getRecvingInfo(Integer pid) {
		String url = SERVER_HOST + "getRecvingInfo&pid=" + pid
				+ "&uid=" + uid + "&token=" + token;
		String mobiles = HttpUtil.get(url);
		if(mobiles.indexOf("not_login") > -1) {
			token();
			getRecvingInfo(pid);
		} else {
			log.info(" 获取的号码: " + mobiles);
		}
	}
	
}
