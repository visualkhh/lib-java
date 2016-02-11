package khh.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import khh.debug.LogK;
import khh.std.adapter.AdapterMap;

public class XMLK {

	
	HashMap<String, File> configFile = null;
	ArrayList<Element> targetElements = null;
	private String targetXPath = "/xmlk/*";
	LogK log = null;

	Predicate<Node> filterAddElement = t -> {
			if (Node.ELEMENT_NODE == t.getNodeType()) {
				return true;
			} else {
				return false;
			}
	};
	BiPredicate<Element, Element> filterExtends = (Element target, Element thiss) -> {
			String targetId 		= target.getAttr("id");
			String targetExtends	= target.getAttr("extends");
			String thisId			= thiss.getAttr("id");
			String thisExtends		= thiss.getAttr("extends");
			//맞는거 넣는다..아이디가 있어야하고.., 아이디와 상속이 같지 않아야하며, 타겟의 상속값이 상대방의 id와같은거
			return (target.isAttr("id")&&!targetId.equals(targetExtends) && thiss.isAttr("extends")&&thisExtends.equals(targetId));
	};
	
	BiConsumer<Element, Element> logicExtendsAddChild = new BiConsumer<Element, Element>() {
		public void accept(Element parent, Element child) {
			child.addAllChildElement(parent.getChildElement());
		}
	};
	BiConsumer<Element, Element> logicExtendsPutAttr = new BiConsumer<Element, Element>() {
		public void accept(Element parent, Element child) {
			child.putAllAttr(parent.getAttr());
		}
	};
	
	
	Consumer<Element> init = null;

	public XMLK() {
		init();
	}
	public XMLK(String realpath) throws Exception {
		init();
		addConfigFile(realpath);
	}
	public XMLK(File file) throws Exception {
		init();
		addConfigFile(file);
	}
	
	public void init(){
		log = LogK.getInstance();
		configFile = new HashMap<String, File>();
		targetElements = new ArrayList<Element>();
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
			configFile.put(file.getAbsolutePath(), file);
		}
	}

	public void start() {
		log.debug("configFileSize:" + configFile.size());
		//ArrayList<Element> list = new ArrayList<>();
		//config에 있는 뭐든 엘리먼트들을 우선 타겟팅된것들을 새성만 시킵니다.. 
		
		configFile.entrySet().stream().forEach(atConfig->{
			XMLparser xml = null;
			try {
				File config = atConfig.getValue();
				log.debug("configFile Setting(" + config.getAbsolutePath() + ")");
				xml = new XMLparser(config);
				Integer rootCnt = xml.getInt("count(" + targetXPath + ")");
				log.debug("targetNode  xpath(" + targetXPath + "), Cnt(" + rootCnt + ")");
				NodeList targetNodeList = xml.getNodes(targetXPath);
				for (int j = 0; j < targetNodeList.getLength(); j++) {
					Node targetNode = targetNodeList.item(j);
					//list.addAll(loopChildNode(targetNode));
					targetElements.add(newElement(targetNode));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});


		
		
		//이제 자식 들을 추가시킵니다.
		for (int i = 0; i < targetElements.size(); i++) {
			Element e = targetElements.get(i);
			e.addAllChildElement( appendLoopChildNode((Node) e.getObject()));
		}
		
		//extends를 적용합니다.
		for (int i = 0; i < targetElements.size(); i++) {
			Element e = targetElements.get(i);
			loopExtends(e);
		}
		
		
		loopNode(targetElements, (Element e)->{
			e.setObject(null);
			if(null!=init){
				init.accept(e);
			}
		});
		
		//한번 뿌려봅니다.
		/*
		loopNode(targetElements, (Element e,Integer depth)->{
			log.debug(StringUtil.loopString("\t",depth)+e);
		});
		*/
		

	}


	private void loopExtends(Element el) {
		String extend = el.getAttr("extends");
		ArrayList<Element<Node>> elements = getTargetElementById(extend);
		
		//상속이면..
		if(null!=extend&&elements.size()>0){
			//HashMap<String,String> mergeAttr = mergeAttr(elements);
			//맞는거 넣는다..아이디가 있어야하고.., 아이디와 상속이 같지 않아야하며, 타겟의 상속값이 상대방의 id와같은거
			elements.stream().filter(aE->filterExtends.test(aE,el)).forEach(aE->{
				logicExtendsPutAttr.accept(aE,el);//el.putAllAttr(aE.getAttr());
				logicExtendsAddChild.accept(aE,el);//el.addAllChildElement(aE.getChildElement());
			});
			el.putAllAttr(XMLUtil.getAttr((Node)el.getObject()));
			
		}

		//위에서 넣을거 다넣고..
		ArrayList<Element<Node>> childElement = el.getChildElement();
		for (int i = 0; i < childElement.size(); i++) {
			loopExtends(childElement.get(i));
		}
		
	}

	private ArrayList<Element> appendLoopChildNode(Node targetNode) {
		ArrayList<Element> list = new ArrayList<>();
		NodeList nodelist = targetNode.getChildNodes();
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node atNode = nodelist.item(i);
			if(filterAddElement.test(atNode)){
				//log.debug(parentE.getName());
				Element e = newElement(atNode);
				e.addAllChildElement(appendLoopChildNode(atNode));
				list.add(e);
			}
		}
		return list;
	}
	
	
	public void loopNode(ArrayList<Element> el, Consumer<Element> ce) {
		for (int i = 0; i < el.size(); i++) {
			Element e = el.get(i);
			ce.accept(e);
			if(e.getChildElement().size()>0){
				loopNode(e.getChildElement(),ce);
			}
		}
		//return el;
	}
	public void loopNode(ArrayList<Element> el, BiConsumer<Element, Integer> bce) {
		loopNode(el,0,bce);
	}
	public void loopNode(ArrayList<Element> el, int depth, BiConsumer<Element, Integer> bce) {

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
	
//	public ArrayList<Element> getElement(ArrayList<Element> el, Predicate<Element> pe) {
//		ArrayList<Element> list =new ArrayList<>();
//		for (int i = 0; i < el.size(); i++) {
//			Element e = el.get(i);
//			if(pe.test(e)){
//				list.add(e);
//			}else{
//				continue;
//			}
//			//System.out.print(StringUtil.loopString("\t",depth));
//			//log.debug(e);
//			if(e.getChildElement().size()>0){
//				getElement(e.getChildElement(),pe);
//			}
//		}
//		return list;
//	}

		
	private Element<Node> newElement(Node node) {
		Element<Node> el = new Element<Node>();
		el.setName(node.getNodeName());
		el.setType(node.getNodeType());
		try{
		el.setAttr(XMLUtil.getAttr(node));
		}catch(Exception e){}
		el.setObject(node);
		return el;
	}
	private HashMap<String, String> mergeAttr(ArrayList<Element<Node>> parentE) {
		HashMap<String, String> newAttr = new HashMap<String, String>();
		for (int i = 0; i < parentE.size(); i++) {
			Element<Node> atE = parentE.get(i);
			newAttr.putAll(atE.getAttr());
		}
		return newAttr;
	}
	public ArrayList<Element<Node>> getTargetElementById(String id) {
		return getTargetElementById("id", id);
	}

	public ArrayList<Element<Node>> getTargetElementById(String attrName, String value) {
		ArrayList<Element<Node>> list = new ArrayList<Element<Node>>();
		for (int i = 0; value != null && i < targetElements.size(); i++) {
			Element atE = targetElements.get(i);
			if (value.equals(atE.getAttr(attrName))) {
				list.add(atE);
			}
		}
		return list;
	}
	
	
	
	public ArrayList<Element> getTargetElements() {
		return targetElements;
	}
	public void setTargetElements(ArrayList<Element> targetElements) {
		this.targetElements = targetElements;
	}
	public Predicate<Node> getFilterAddElement() {
		return filterAddElement;
	}
	public void setFilterAddElement(Predicate<Node> filterAddElement) {
		this.filterAddElement = filterAddElement;
	}
	public BiPredicate<Element, Element> getFilterExtends() {
		return filterExtends;
	}
	public void setFilterExtends(BiPredicate<Element, Element> filterExtends) {
		this.filterExtends = filterExtends;
	}
	public BiConsumer<Element, Element> getLogicExtendsAddChild() {
		return logicExtendsAddChild;
	}
	public void setLogicExtendsAddChild(BiConsumer<Element, Element> logicExtendsAddChild) {
		this.logicExtendsAddChild = logicExtendsAddChild;
	}
	public BiConsumer<Element, Element> getLogicExtendsPutAttr() {
		return logicExtendsPutAttr;
	}
	public void setLogicExtendsPutAttr(BiConsumer<Element, Element> logicExtendsPutAttr) {
		this.logicExtendsPutAttr = logicExtendsPutAttr;
	}
	public Consumer<Element> getInit() {
		return init;
	}
	public void setInit(Consumer<Element> init) {
		this.init = init;
	}
	public static void main(String[] args) throws Exception {
		XMLK k = new XMLK();
		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\xml\\xmlfile.xml");
//		k.addConfigFile("Z:\\git\\javautil-visualkhh\\Utilkhh\\src\\khh\\dynamick\\dynamic_config.xml");
//		k.setTargetXPath("/dynamick/class");
		k.start();
	}


}
