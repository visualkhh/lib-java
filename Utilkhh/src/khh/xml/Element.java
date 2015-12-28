package khh.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import khh.std.adapter.AdapterMapBase;

public class Element<T> {

	String name						= null;
	int type						= -1;
	Map<String, String> attr	= new HashMap<String, String>();
	ArrayList<Element> childElement	= new ArrayList<Element>();
	T object = null;
	
	
	public Element() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}
	public Map<String, String> getAttr() {
		return attr;
	}
	public boolean isAttr(String attrName) {
		return null!=getAttr().get(attrName);
	}
	public String getAttr(String attrName){
		return attr.get(attrName);
	}
	public void putAllAttr(Map<String, String> attr) {
		getAttr().putAll(attr);
	}
	public void putAttr(String attrName, String value) {
		getAttr().put(attrName, value);
	}
	public void removeAttr(String attrName) {
		getAttr().remove(attrName);
	}

	public ArrayList<Element> getChildElementById(String id) {
		return childElement.stream().filter(at->id.equals(at.getAttr("id"))).collect(Collectors.toCollection(ArrayList::new));
	}
	public ArrayList<Element> getChildElementByTagName(String tagName) {
//		return childElement.stream().filter(at->null!=at.getName()).collect(Collectors.toCollection(ArrayList::new));
		return childElement.stream().filter(at->tagName.equals(at.getName())).collect(Collectors.toCollection(ArrayList::new));
	}
	public Element getChildElementByTagNameAtLast(String tagName) {
		ArrayList<Element> list = childElement.stream().filter(at->tagName.equals(at.getName())).collect(Collectors.toCollection(ArrayList::new));
		if(list.size()>0)
			return list.get(list.size()-1);
		else
			return null;
		//return childElement.stream().filter(at->name.equals(at.getAttr("id"))).collect(Collectors.toList());
	}
	public ArrayList<Element> getChildElementByAttr(String attrName) {
		return childElement.stream().filter(at->null!=at.getAttr(attrName)).collect(Collectors.toCollection(ArrayList::new));
	}
	public ArrayList<Element> getChildElementByAttr(String attrName, String value) {
		return childElement.stream().filter(at->value.equals(at.getAttr(attrName))).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<Element> getChildElement() {
		return childElement;
	}
	public int getChildElementSize() {
		return childElement.size();
	}
	public void setChildElement(ArrayList<Element> childElement) {
		this.childElement = childElement;
	}
	public void removeChildElementByTagName(String name) {
		for (int i = 0; i < childElement.size(); i++) {
			String atName = childElement.get(i).getName();
			if(atName.equals(name)){
				childElement.remove(i);
				i--;
			}
		}
		//return childElement.size();
	}
	
	
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
//	public void addAttr(String name, String value){
//		getAttr().put(name, value);
//	}
	public void addChildElement(Element<T> e){
		childElement.add(e);
	}
	public void addAllChildElement(ArrayList<Element<T>> arrayList){
		this.childElement.addAll(0,arrayList);
	}
	
	public void loopNode(Consumer<Element> ce){
		loopNode(getChildElement(),ce);
	}
	private void loopNode(ArrayList<Element> el, Consumer<Element> ce) {
		for (int i = 0; i < el.size(); i++) {
			Element e = el.get(i);
			if(null!=ce)
				ce.accept(e);
			if(e.getChildElement().size()>0){
				loopNode(e.getChildElement(),ce);
			}
		}
		//return el;
	}
	
	public void loopNode(BiConsumer<Element, Integer> bce) {
		loopNode(getChildElement(),bce);
	}
	public void loopNode(ArrayList<Element> el, BiConsumer<Element, Integer> bce) {
		loopNode(el,0,bce);
	}
	public void loopNode(ArrayList<Element> el, int depth, BiConsumer<Element, Integer> bce) {
/*
 (Element e,Integer depth)->{
			log.debug(StringUtil.loopString("\t",depth)+e);
		}
 */
		for (int i = 0; i < el.size(); i++) {
			Element e = el.get(i);
			bce.accept(e, depth);
			//System.out.print(StringUtil.loopString("\t",depth));
			//log.debug(e);
			if(e.getChildElement().size()>0){
				loopNode(e.getChildElement(),depth+1,bce);
			}
		}
		//return el;
	}
	@Override
	public String toString() {
		return "Element [name=" + name + ", type=" + type + ", object=" + object + ", attr=" + attr + ", childElement=" + childElement+"]";
	}
	
}
