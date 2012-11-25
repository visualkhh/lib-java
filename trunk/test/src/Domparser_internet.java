import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kdt.util.xml.XMLparser;
public class Domparser_internet {
        private static Document xmlDoc = null;
        private static String XML_URL = "http://www.gemsplus.co.kr/m/androidmarket.jsp?query=m2m";
//        private static String XML_URL = "http://weather.unisys.com/forexml.cgi?seoul";
        private static String XML_NODE = "observation";
        private static String XML_CITY = "city";
        private static String XML_TIME = "time";
        private static String XML_SKIES = "skies";
        private static String XML_TEMP = "temp.C";
        public static void main(String[] args) throws XPathExpressionException, SAXException {
                printXmlFromUrl(XML_URL);
        }
        private static void printXmlFromUrl(String urlString) throws SAXException, XPathExpressionException {
                try {
                        URL url = new URL(urlString);
                        try {
                                URLConnection urlConnection = url.openConnection();
                                HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
                                int responseCode = httpConnection.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                        InputStream inputStream = httpConnection.getInputStream();
                                        // Parsing XML Document
                                        XMLparser xml = new XMLparser(inputStream);
                                        String name=xml.getString("/apps/app/title");
                                        System.out.println(name);
                                        inputStream.close();
                                } else {
                                        System.out.println("HTTP Response is not \"HTTP Status-Code 200: OK.\"");
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                } catch (MalformedURLException e) {  
                        e.printStackTrace();
                }
        }
//        private static void createDomParser(InputStream inputStream) {
//                // Use factory to create a DOM document
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                factory.setIgnoringElementContentWhitespace(true);
//                DocumentBuilder builder = null;
//                try { // Get a DOM parser from the Factory
//                        builder = factory.newDocumentBuilder();
//                } catch (ParserConfigurationException e) {
//                        e.printStackTrace();
//                        return;
//                }
//                try { // Request the DOM parser to parse the file
//                        xmlDoc = builder.parse(inputStream);
//                } catch (SAXException e) {
//                        e.printStackTrace();
//                        return;
//                } catch (IOException e) {
//                        e.printStackTrace();
//                        return;
//                }
//        }
//        private static String[] getByTagName(String tagName) {
//                if (!(xmlDoc == null)) {
//                        NodeList nodes = xmlDoc.getElementsByTagName(tagName);
//                        NamedNodeMap nodeMap = nodes.item(0).getAttributes();
//                        String[] values = new String[4];
//                        values[0] = nodeMap.getNamedItem(XML_CITY).getNodeValue();
//                        values[1] = nodeMap.getNamedItem(XML_TIME).getNodeValue();
//                        values[2] = nodeMap.getNamedItem(XML_SKIES).getNodeValue();
//                        values[3] = nodeMap.getNamedItem(XML_TEMP).getNodeValue();
//                        return values;
//                }
//                return null;
//        }
}