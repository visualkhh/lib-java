import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import khh.xml.XMLparser;

public class XMLTest {

	public static void main(String[] args) throws Exception {
		XMLparser xml = new XMLparser(new File("C:\\activity_784081461.gpx.xml"));
		String targetxpath = "//gpx/trk/trkseg/trkpt";
		Integer targetcnt = xml.getInt("count("+targetxpath+")");
		System.out.println(targetcnt);
		StringBuffer b = new StringBuffer();
//		for (int j = 1; j <= targetcnt; j++) {
//			String atxpath = targetxpath+"["+j+"]";
//			String atTimexpath = atxpath+"/time";
//			String time = xml.getString(atTimexpath);
//			//2015-05-24T22:34:47.000Z
//			time = time.substring(0,4)+"."+time.substring(5,5+2)+"."+time.substring(8,8+2)+" "+time.substring(11,11+2)+":"+time.substring(14,14+2)+":"+time.substring(17,17+2);
//			
//			String lon = xml.getString(atxpath+"/@lon");
//			String lat = xml.getString(atxpath+"/@lat");
//			 b.append("[\""+time+"\",\""+lat+"\",\""+lon+"\"],");
//			 System.out.println(j);
//		}
		for (int j = 1; j <= targetcnt; j+=500) {
			String atxpath = targetxpath+"["+j+"]";
			String atTimexpath = atxpath+"/time";
			String time = xml.getString(atTimexpath);
			//2015-05-24T22:34:47.000Z
			time = time.substring(0,4)+"."+time.substring(5,5+2)+"."+time.substring(8,8+2)+" "+time.substring(11,11+2)+":"+time.substring(14,14+2)+":"+time.substring(17,17+2);
			
			String ele = xml.getString(atxpath+"/ele");
			 b.append("[\""+time+"\",\""+ele+"\"],");
			 System.out.println(j);
		}
		
		System.out.println(b.toString());
		
	}

}
