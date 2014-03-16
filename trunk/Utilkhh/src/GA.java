import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import khh.xml.Element;
import khh.xml.XMLparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class GA {
	public static XMLparser xml=null;
	public static void main(String[] args) throws Exception {
		 xml = new XMLparser(new File("xml/xxx.xml"));
		Document doc = xml.getDocument();
		System.out.println( doc.getNodeName());
		
		
//		NodeList nodelist = doc.getChildNodes();
//		Element e = loop(nodelist);
		Node firstchild = doc.getFirstChild();
		Element e = loop(firstchild);
//		HashMap map = xml.getAttribute(firstchild);
//		printMap(map);
		
		
		
		
	}
	
	//extends, src
	public static Element loop(Node node) throws NoClassDefFoundError, Exception{
		Element f = new Element();
		try{
			f.add(xml.getAttribute(node));
		}catch (Exception e) {}
		 
		
		NodeList nodeList = node.getChildNodes();
//		System.out.println(nodeList.getLength());
		for (int i = 0; nodeList!=null && i < nodeList.getLength(); i++) {
			Node atNode = nodeList.item(i);
			if(atNode.getNodeType()!=Node.ELEMENT_NODE){
				continue;
			}
			System.out.println(atNode.getNodeName()+"---"+atNode.getNodeType());
			Element childElement = new Element();
			try{
				childElement.add(xml.getAttribute(atNode));
			}catch (Exception e) {}
			f.addChildElement(childElement);
			
			loop(atNode);
			
//			f.addChildElement());
		}
		return f;
	}
//	public static Element loop(NodeList nodeList) throws NoClassDefFoundError, Exception{
//		Element f = new Element();
//		for (int i = 0; i < nodeList.getLength(); i++) {
//			Node atNode = nodeList.item(i);
//			Element e = new Element();
//			f.addChildElement(e);
//			try{
//			e.add(xml.getAttribute(atNode));
//			}catch (Exception ex) {
//			}
//			
//			e.addChildElement( loop(atNode.getChildNodes()) );
//		}
//		return f;
//	}
	
	public static void printMap(HashMap map){
		Set keySet = map.keySet();
		Iterator it = keySet.iterator();
		while(it.hasNext()){
			Object key = it.next();
			Object value = map.get(key);
			System.out.println(key+"  :  "+value);
		}
	}
}
