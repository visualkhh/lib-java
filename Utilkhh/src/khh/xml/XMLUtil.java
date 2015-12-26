package khh.xml;

import java.util.HashMap;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XMLUtil {

	
    public static HashMap<String, String> getAttr(Node node){
        NamedNodeMap attrs = node.getAttributes();  
        HashMap<String,String> attrMap = new HashMap<String, String>();
        for(int i = 0 ; i<attrs.getLength() ; i++) {
          Attr attribute = (Attr)attrs.item(i);     
          attrMap.put(attribute.getName(), attribute.getValue());
        }
        return attrMap;
    }
    public static Attr getAttr(Node node, String attrName){
    	if(null==node || null==attrName){
    		return null;
    	}
    	Attr at = (Attr) node.getAttributes().getNamedItem(attrName);
    	return at;
    }
    
    public static String getAttrValue(Node node, String attrName){
    	return getAttrValue(getAttr(node, attrName) ,attrName);
    }
    
    public static String getAttrValue(Attr node,String attrName){
    	if(null==node || null==attrName){
    		return null;
    	}
    	
        try{
            return node.getValue();
        }catch (Exception e) {
            return null;
        }
    }
    
}
