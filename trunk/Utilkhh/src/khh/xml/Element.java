package khh.xml;

import java.util.ArrayList;
import java.util.HashMap;

import khh.std.adapter.AdapterMapBase;

public class Element extends AdapterMapBase<String, String> {
	ArrayList<Element> childElement = new ArrayList<Element>();

	public Element() {
	}
	public Element(HashMap<String,String> map) throws Exception{
		add(map);
	}
	
	public ArrayList<Element> getChildElement() {
		return childElement;
	}

	public void setChildElement(ArrayList<Element> childElement) {
		this.childElement = childElement;
	}
	
	
	public void addChildElement(Element e){
		childElement.add(e);
	}
	
}
