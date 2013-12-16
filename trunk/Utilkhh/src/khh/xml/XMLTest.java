package khh.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLTest {

    /**
     * @param args
     * @throws IOException 
     * @throws SAXException 
     * @throws NoClassDefFoundError 
     * @throws XPathExpressionException 
     * @throws ParserConfigurationException 
     * @throws TransformerException 
     */
    public static void main(String[] args) throws SAXException, IOException, XPathExpressionException, NoClassDefFoundError, ParserConfigurationException, TransformerException {
        XMLparser xml = new XMLparser(new File("src/khh/xml/xmlfile.xml"));
        //XMLparser xml = new XMLparser(new URL("http://stackoverflow.com/feeds/question/216894"));
//        System.out.println(xml.getString());
        Document doc = xml.getDocument();
        Node node = xml.getNode("//gogo");
          Attr attr = doc.createAttribute("id");
          attr.setValue("1");
          ((Element) node).setAttributeNode(attr);
//        xml.saveFile(new File("src/khh/xml/xmlfile.xml"));
          System.out.println(xml.getString("//gogo/@id"));
          System.out.println(xml.getString());
        //System.out.println( xml.getString("//feed") );
          
        
        
          
          
          
          
//          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//          Document retval = dbf.newDocumentBuilder().newDocument();
//          Element parent = retval.createElement("parent");
//          retval.appendChild(parent);
//
//          Element child1 = retval.createElement("child");
//          child1.setTextContent("child.text");
//          parent.appendChild(child1);
//          Element child2 = retval.createElement("child");
//          child2.setTextContent("child.text.2");
//          parent.appendChild(child2);
//          
//          XPathFactory factory = XPathFactory.newInstance();
//          XPath xPath = factory.newXPath();
//          Node node_at = (Node) xPath.evaluate("//xmlmap", retval, XPathConstants.NODE);
//          System.out.println(xPath.evaluate("//xmlmap", retval, XPathConstants.NODE).getClass());

          
          
          
//        XMLparser xml = new XMLparser(new File("src/khh/xml/xmlfile.xml"));
//        XMLparser xml = new XMLparser();
//        System.out.println( xml.getString("//gogo") );
//        
//        
//        Document doc = xml.getDocument();
//        Element rootElement = doc.createElement("company");
//        doc.appendChild(rootElement);
// 
//     // staff elements
//        Element staff = doc.createElement("Staff");
//        rootElement.appendChild(staff);
// 
//        // set attribute to staff element
//        Attr attr = doc.createAttribute("id");
//        attr.setValue("1");
//        staff.setAttributeNode(attr);
//        
//     // firstname elements
//        Element firstname = doc.createElement("firstname");
//        firstname.appendChild(doc.createTextNode("yong"));
//        staff.appendChild(firstname);
// 
//        // lastname elements
//        Element lastname = doc.createElement("lastname");
//        lastname.appendChild(doc.createTextNode("mook kim"));
//        staff.appendChild(lastname);
// 
//        // nickname elements
//        Element nickname = doc.createElement("nickname");
//        nickname.appendChild(doc.createTextNode("mkyong"));
//        staff.appendChild(nickname);
// 
//        // salary elements
//        Element salary = doc.createElement("salary");
//        salary.appendChild(doc.createTextNode("100000"));
//        staff.appendChild(salary);
//        
//     // write the content into xml file
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File("C:\\file.xml"));
//        transformer.transform(source, result);
    }

}
