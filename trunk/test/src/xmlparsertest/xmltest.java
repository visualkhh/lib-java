package xmlparsertest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.kdt.util.xml.XMLparser;

public class xmltest {
    public static void main(String[] args) throws NoClassDefFoundError, MalformedURLException, IOException, SAXException, XPathExpressionException {
        XMLparser xml  =  new XMLparser("./xml/logk.xml");
        
        String a = xml.getString("//logkattribute/recycle_ms");
        System.out.println(a);
    }
}
