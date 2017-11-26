package molab.main.java.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import molab.main.java.entity.Proxy;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class ProxyUtil {

	private static final Logger log = Logger.getLogger(ProxyUtil.class.getName());
	private static ProxyUtil pu = null;
	private static final Map<Integer, Proxy> map = new HashMap<Integer, Proxy>();
	
	private ProxyUtil() {}
	
	public static ProxyUtil getInstance() {
		if(pu == null) {
			synchronized(ProxyUtil.class) {
				pu = new ProxyUtil();
			}
		}
		return pu;
	}
		
	public static void update(Integer agentId, Proxy proxy) {
		map.put(agentId, proxy);
	}
	
	public static Proxy get(Integer agentId) {
		if(map.containsKey(agentId)) {
			Object obj = map.get(agentId);
			if(obj != null) {
				return (Proxy) obj;
			}
		} else {
			map.put(agentId, null);
		}
		return null;
	}
	
	private static final String SERVER_HOST = "http://alog.umeng.com/app_logs";
	private static final int TIMEOUT = 5000;
	public static boolean check(Proxy proxy) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setProxy(proxy.getIp(), proxy.getPort());
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		GetMethod getMethod = new GetMethod(SERVER_HOST);
//		log.info("Proxy " + proxy.getIp() + ":" + proxy.getPort());
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode == HttpStatus.SC_OK) {
//				log.info(" avaliable.");
				return true;
			}
		} catch (Exception e) {

		} finally {
			getMethod.releaseConnection();
		}
//		log.warning(" not avaliable.");
		return false;
	}
}
