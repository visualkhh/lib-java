package khh.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.Collectors.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import khh.debug.LogK;
import khh.std.adapter.AdapterMap;

public class XMLK2 {
	AdapterMap<String, File> configFile = null;
	ArrayList<Element> elements = null;
	private String targetXPath = "/xmlk/*";
	LogK log = null;

	Predicate<Node> addElementFilter = new Predicate<Node>() {
		public boolean test(Node t) {
			if (Node.ELEMENT_NODE == t.getNodeType()) {
				return true;
			} else {
				return false;
			}
		}
	};

	public XMLK2() {
		log = LogK.getInstance();
		configFile = new AdapterMap<String, File>();
		elements = new ArrayList<Element>();
	}

	public String getTargetXPath() {
		return targetXPath;
	}

	public void setTargetXPath(String targetXPath) {
		this.targetXPath = targetXPath;
	}

	public void addConfigFile(String realpath) throws Exception {
		if (realpath != null) {
			addConfigFile(new File(realpath));
		}
	}

	public void addConfigFile(File file) throws Exception {
		if (file != null && file.exists() && file.isFile()) {
			configFile.add(file.getAbsolutePath(), file);
		}
	}

	public void start() {
		log.debug("configFileSize:" + configFile.size());
		for (int i = 0; i < configFile.size(); i++) {
			XMLparser xml = null;
			try {
				File config = configFile.get(i);
				log.debug("configFile Setting(" + i + "):" + config);
				xml = new XMLparser(config);
				Integer rootCnt = xml.getInt("count(" + targetXPath + ")");
				log.debug("targetNode  xpath(" + targetXPath + "), Cnt(" + rootCnt + ")");
				NodeList targetNodeList = xml.getNodes(targetXPath);
				for (int j = 0; j < targetNodeList.getLength(); j++) {
					Node targetNode = targetNodeList.item(j);
					Element<Node> el = newElement(targetNode);
					elements.add(el);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//mergeTargetExtend(elements);

		for (int i = 0; i < elements.size(); i++) {
			log.debug(elements.get(i));
			settingChild(elements.get(i));
		}
		
		
		log.debug("--------..--");
		elements.stream()
		.filter(element->null!=element.getAttr("extends")&&getElementById(element.getAttr("extends")).size()>0)
		.forEach(element->{
			String extend = element.getAttr("extends");
			ArrayList<Element<Node>> idElements = getElementById(extend);
			HashMap<String,String> m = mergeAttr(idElements);
			for (int i = 0; i < idElements.size(); i++) {
				Element<Node> a = idElements.get(i);
				element.addAllChildElement( a.getChildElement() );
				element.setAttr(m);
				element.putAllAttr(XMLUtil.getAttr((Node)element.getObject()));
				element.setObject(a.getObject());
			}
//			ArrayList<Element<Node>> idElements = getElementById(extend);
			//System.out.println("sssss"+element);
		});
		
		log.debug("elements " + elements.size() + "----------------");
		for (int i = 0; i < elements.size(); i++) {
			printElement(elements.get(i));
			log.debug("---------");
		}

	}

	String tab="";
	private void printElement(Element<Node> element) {
		ArrayList<Element<Node>> childs = element.getChildElement();
		log.debug(tab+element);
		for (int j = 0; j < childs.size(); j++) {
			tab+="\t";
			log.debug(tab+childs.get(j));
			for (int i = 0; i < childs.get(j).getChildElement().size(); i++) {
				tab+="\t";
				printElement(childs.get(j).getChildElement().get(i));
			}
			try{
			tab=tab.substring(0,tab.length()-2);
			}catch(Exception e){
				tab="";
			}
			
		}

	}

	private Element<Node> settingChild(Element<Node> el) {
		// ArrayList<Element<Node>> child = new ArrayList<Element<Node>>();
		Node atNode = el.getObject();
		NodeList atNodeChildList = atNode.getChildNodes();
		// Node node = el.getObject();
		for (int i = 0; i < atNodeChildList.getLength(); i++) {
			Node atNodeChild = atNodeChildList.item(i);
			if (!addElementFilter.test(atNodeChild)) {
				continue;
			}
			// Element<Node> atChild = child.get(i);
			// Node atChildObject = child.get(i).getObject();
			Element<Node> newElement = newElement(atNodeChild);
			String extendsId = XMLUtil.getAttrValue(atNodeChild, "extends");
			if (null != extendsId) {
				ArrayList<Element<Node>> exetendsElement = getElementById(extendsId);
				log.debug("extends size" + exetendsElement.size() + " "+newElement+"   "+extendsId+" "+ (null != exetendsElement && exetendsElement.size() > 0));
				if (null != exetendsElement && exetendsElement.size() > 0) {
					HashMap<String, String> mergeArrt = mergeAttr(exetendsElement);
					ArrayList<Element<Node>> newChildElement = (ArrayList<Element<Node>>) exetendsElement.stream()
							.map(element->{
						
						//newElement.setChildElement(element.getChildElement());
						newElement.addAllChildElement(element.getChildElement());
						newElement.setAttr(mergeArrt);
						newElement.putAllAttr(XMLUtil.getAttr(atNodeChild));
						newElement.setObject(element.getObject());
						
						//ArrayList<Element<Node>> c = new ArrayList<Element<Node>>();
						NodeList node = atNodeChild.getChildNodes();
						for (int j = 0; j < node.getLength(); j++) {
							Node atatNode = node.item(j);
							if (addElementFilter.test(atatNode)) {
								Element<Node> ee = newElement(atatNode);
								settingChild(ee);
								el.addChildElement(ee);
								
							}
						}
						
						
						return newElement;
					}).collect(Collectors.toList());
					
					//Stream.of(1,4,5,6).map(i->"#"+i).collect(toList)
//					ArrayList<Element<Node>> newChildElement = new ArrayList<Element<Node>>();
//					for (int j = 0; j < exetendsElement.size(); j++) {
//						Element<Node> newElement = newElement(atNodeChild);
//						newElement.setObject(exetendsElement.get(j).getObject());
//						newElement.setChildElement(exetendsElement.get(j).getChildElement());
//						newElement.setAttr(mergeArrt);
//						newElement.putAllAttr(XMLUtil.getAttr(atNodeChild));
//						newChildElement.add(newElement);
//					}
					el.addAllChildElement(newChildElement);
					continue;
				}
			}

			// mergeExtend(nes);
			el.addChildElement(newElement);
			settingChild(newElement);

			// Node node = atChild.getObject();
		}
		return null;
	}

	// 부모값이 상위 그아래 자식값이 들어감 따라서 자식값이 부모값 덮어쒸운다.
	private HashMap<String, String> mergeAttr(ArrayList<Element<Node>> parentE) {
		HashMap<String, String> newAttr = new HashMap<String, String>();
		for (int i = 0; i < parentE.size(); i++) {
			Element<Node> atE = parentE.get(i);
			newAttr.putAll(atE.getAttr());
		}
		return newAttr;
	}

	private void mergeAttr(ArrayList<Element<Node>> parentE, Element<Node> childE) {
		HashMap<String, String> newAttr = mergeAttr(parentE);
		newAttr.putAll(childE.getAttr()); // 마지막 해당클래스의 속성으로 ..
		childE.setAttr(newAttr);
	}

	public ArrayList<Element<Node>> getElementById(String id) {
		return getElementByAttrVal("id", id);
	}

	public ArrayList<Element<Node>> getElementByAttrVal(String attrName, String value) {
		ArrayList<Element<Node>> list = new ArrayList<Element<Node>>();
		for (int i = 0; value != null && i < elements.size(); i++) {
			Element atE = elements.get(i);
			if (value.equals(atE.getAttr(attrName))) {
				list.add(atE);
			}
		}
		return list;
	}

	private Element<Node> newElement(Node node) {
		Element<Node> el = new Element<Node>();
		el.setName(node.getNodeName());
		el.setType(node.getNodeType());
		el.setAttr(XMLUtil.getAttr(node));
		el.setObject(node);
		return el;
	}

	private void mergeTargetExtend(ArrayList<Element<Node>> el) {
		for (int i = 0; i < el.size(); i++) {
			mergeTargetElementExtendChildElement(el.get(i));
			mergeTargetElementExtendAttr(el.get(i));
		}
	}

	private void mergeTargetExtendChildElement(ArrayList<Element<Node>> el) {
		for (int i = 0; i < el.size(); i++) {
			mergeTargetElementExtendChildElement(el.get(i));
		}
	}

	private void mergeTargetElementExtendAttr(ArrayList<Element<Node>> el) {
		for (int i = 0; i < el.size(); i++) {
			mergeTargetElementExtendAttr(el.get(i));
		}
	}

	private void mergeTargetElementExtend(Element<Node> el) {
		mergeTargetElementExtendChildElement(el);
		mergeTargetElementExtendAttr(el);
	}

	private void mergeTargetElementExtendChildElement(Element<Node> el) {
		String extend = el.getAttr("extends");
		log.debug(el.getName() + "   attr extend:(" + extend);
		if (null != extend) {
			ArrayList<Element<Node>> extendElements = getElementById(extend);
			log.debug("mergeExtendChildElement size" + extendElements.size() + " "+ (null != extendElements && extendElements.size() > 0));
			for (int i = 0; null!=extendElements&&extendElements.size()>0&&i<extendElements.size(); i++) {
				Element atElement = extendElements.get(i);
				el.putAllAttr(atElement.getAttr());
				el.addAllChildElement(atElement.getChildElement());
			}
			el.putAllAttr(XMLUtil.getAttr(el.getObject()));
//			el.addAllChildElement(extendElements);
		}
	}

	private void mergeTargetElementExtendAttr(Element el) {
		String extend = el.getAttr("extends");
		if (null != extend) {
			ArrayList<Element<Node>> elist = getElementById(extend);
			HashMap<String, String> newAttr = new HashMap<String, String>();
			for (int i = 0; i < elist.size(); i++) {
				Element atE = elist.get(i);
				newAttr.putAll(atE.getAttr());// e.setAttr(ConversionUtil.mergeToNewMap(elist.get(i).getAttr(),
												// e.getAttr()));
			}
			newAttr.putAll(el.getAttr()); // 마지막 해당클래스의 속성으로 ..
			el.setAttr(newAttr);
		}

	}

	public static void main(String[] args) throws Exception {
		XMLK2 k = new XMLK2();
		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\xml\\xmlfile.xml");
//		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
//		k.setTargetXPath("/dynamick/class");
		k.start();
	}

}
