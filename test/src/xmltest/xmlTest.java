package xmltest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import khh.url.util.URLUtil;
import khh.xml.XMLparser;

public class xmlTest {
	public static void main(String[] args) throws NoClassDefFoundError, MalformedURLException, IOException, SAXException, XPathExpressionException {
//		XMLparser x = new XMLparser(new URL("http://www.okjsp.pe.kr/bbs?act=DOWN&maskname=1350798096664&fileName=sqlmapa.xml"));
//		System.out.println( x.getString("/sqlmap/select") );
		
		
		System.out.println(URLUtil.getURLString("http://jsputil-visualkhh.googlecode.com/svn/trunk/WebContent/JavascriptUtil/util.js"));
	}
}
