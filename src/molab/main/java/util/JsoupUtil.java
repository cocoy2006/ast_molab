package molab.main.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import molab.main.java.entity.Proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {

	public static List<Proxy> getProxy(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements trList = doc.select("#list table tbody tr");
		if(trList != null && trList.size() > 0) {
			List<Proxy> proxyList = new ArrayList<Proxy>();
			for(Element e : trList) {
				String ip = e.childNode(1).childNode(0).toString();
				Integer port = Integer.parseInt(e.childNode(3).childNode(0).toString());
				Proxy proxy = new Proxy(ip, port, Status.Common.FALSE.getInt(), System.currentTimeMillis());
				proxyList.add(proxy);
			}
			return proxyList;
		}
		return null;
	}
}
