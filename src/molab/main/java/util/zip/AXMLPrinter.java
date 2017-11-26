package molab.main.java.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.AXmlResourceParser;

public class AXMLPrinter {
	
	private static final Logger log = Logger.getLogger(AXMLPrinter.class.getName());
	private static final float[] RADIX_MULTS = { 0.0039063F, 3.051758E-005F,
			1.192093E-007F, 4.656613E-010F };

	private static final String[] DIMENSION_UNITS = { "px", "dip", "sp", "pt",
			"in", "mm", "", "" };

	private static final String[] FRACTION_UNITS = { "%", "%p", "", "", "", "",
			"", "" };

	private static FileWriter fw = null;
	public static File parseToText(String androidManifestXml) {
		File file = null;
		try {
			file = new File(androidManifestXml + ".xml");
			fw = new FileWriter(file);
			
			AXmlResourceParser parser = new AXmlResourceParser();
			parser.open(new FileInputStream(androidManifestXml));
			StringBuilder indent = new StringBuilder(10);
			StringBuffer sb = new StringBuffer();
			while (true) {
				int type = parser.next();
				if (type == XmlPullParser.END_DOCUMENT) {
					break;
				}
				switch (type) {
				case XmlPullParser.START_DOCUMENT:
					sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
					break;
				case XmlPullParser.START_TAG:
					sb.append(indent + "<" + getNamespacePrefix(parser.getPrefix()) + parser.getName());
					indent.append("\t");

					int namespaceCountBefore = parser.getNamespaceCount(parser
							.getDepth() - 1);
					int namespaceCount = parser.getNamespaceCount(parser
							.getDepth());
					for (int i = namespaceCountBefore; i != namespaceCount; i++) {
						sb.append(indent + "xmlns:" + parser.getNamespacePrefix(i) + "=\"" + parser.getNamespaceUri(i) + "\"");
					}

					for (int i = 0; i != parser.getAttributeCount(); i++) {
						String attributeValue = getAttributeValue(parser, i);
						if(hasChinese(attributeValue)) {
							log.info("Replace chinese characters.");
							attributeValue = "chineseCharacters";
						}
						sb.append(indent + getNamespacePrefix(parser
								.getAttributePrefix(i)) + parser.getAttributeName(i) +
								"=\"" + attributeValue + "\"");
					}
					sb.append(indent + ">");
					break;
				case XmlPullParser.END_TAG:
					indent.setLength(indent.length() - "\t".length());
					sb.append(indent + "</" + getNamespacePrefix(parser.getPrefix()) + parser.getName() + ">");
					break;
				case XmlPullParser.TEXT:
					sb.append(indent + parser.getText());
				case XmlPullParser.END_DOCUMENT:
				}
			}
			fw.write(sb.toString());
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			if(fw != null) {
				try {
					fw.flush();
					fw.close();
				} catch (IOException e) {
					log.severe(e.getMessage());
				}
			}
		}
		return file;
	}
	
	private static String getNamespacePrefix(String prefix) {
		if ((prefix == null) || (prefix.length() == 0)) {
			return "";
		}
		return prefix + ":";
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == 3) {
			return parser.getAttributeValue(index);
		}
		if (type == 2) {
			return String.format("?%s%08X", new Object[] { getPackage(data),
					Integer.valueOf(data) });
		}
		if (type == 1) {
			return String.format("@%s%08X", new Object[] { getPackage(data),
					Integer.valueOf(data) });
		}
		if (type == 4) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == 17) {
			return String.format("0x%08X",
					new Object[] { Integer.valueOf(data) });
		}
		if (type == 18) {
			return data != 0 ? "true" : "false";
		}
		if (type == 5) {
			return Float.toString(complexToFloat(data))
					+ DIMENSION_UNITS[(data & 0xF)];
		}
		if (type == 6) {
			return Float.toString(complexToFloat(data))
					+ FRACTION_UNITS[(data & 0xF)];
		}
		if ((type >= 28) && (type <= 31)) {
			return String.format("#%08X",
					new Object[] { Integer.valueOf(data) });
		}
		if ((type >= 16) && (type <= 31)) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", new Object[] {
				Integer.valueOf(data), Integer.valueOf(type) });
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}
	
	private static float complexToFloat(int complex) {
		return (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4 & 0x3)];
	}
	
	private static boolean hasChinese(String str) {
		if(str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}
	
}
