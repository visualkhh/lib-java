package khh.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import khh.conversion.util.ConversionUtil;
import khh.string.util.StringUtil;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLparser
{
	private Document document;
	private String filePath;
	private File file;
	private URL url;
	private InputStream inputStream;
	private int connectionTimeout=10000;
	private int readTimeout=3000;
	public XMLparser() {
	}
	public XMLparser (Document document){
		setDocument(document);
	}
	public XMLparser (String filePath) throws SAXException, IOException{
		setFilePath(filePath);
	}
	public XMLparser (InputStream fileStream) throws SAXException, IOException{
		setInputStrem(fileStream);
	}
	public XMLparser (File file) throws SAXException, IOException{
		setFile(file);
	}
	public XMLparser (URL url) throws IOException, SAXException,NoClassDefFoundError{
		setURL(url);
	}

	public void finalize(){
		
		if(inputStream!=null){
			try
			{
				inputStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	private DocumentBuilder getBuilder(){
		DocumentBuilderFactory documentFactory;
		DocumentBuilder docBuilder=null;
		documentFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = documentFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return docBuilder;
	}
	
	
	private void makeDocument(String filePath) throws SAXException, IOException{
		DocumentBuilder docBuilder=getBuilder();
//		try {
			File file = new File(filePath);
			this.document = docBuilder.parse(file);
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	private void makeDocument(File file) throws SAXException, IOException{
		DocumentBuilder docBuilder=getBuilder();
//		try {
			this.document = docBuilder.parse(file);
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	private void makeDocument(InputStream filestream) throws SAXException, IOException{
		DocumentBuilder docBuilder=getBuilder();
//		try {
			this.document = docBuilder.parse(filestream,null);
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
//	다음은 DOM을 사용할 때 가장 많이 사용하게 될 메소드들이다.
//	-          Document.getDocumentElement()
//	: XML 문서의 루트(root)엘리먼트를 리턴한다.
//	-          Node.getFirstChild() 와 Node.getLastChild()
//	: 주어지는 노드의 처음과 마지막 노드를 리턴한다.
//	-          Node.getNextSibling()과 Node.getPreviousSibling()
//	: 주어진 노드의 전과 다음 노드를 리턴한다.
//	-          Node.getAttribute(attrName)
//	: 주어진 노드에 파라미터로 들어온 attrName을 가진 속성(attribute)을 리턴한다.(예를 들어 id라는 이름의 속성을 얻으려면 getAttribute(“id”) 를 사용하면된다.)
	
	
///////////////
	public Document getDocument() {
		return document;
	}
	public String getFilePath() {
		return filePath;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public File getFile() {
		return file;
	}
	//---
	public void setDocument(Document document) {
		this.document = document;
	}
	public void setString(String stringXML) throws SAXException, IOException {
		makeDocument(ConversionUtil.toInputStream(stringXML));
	}
	public void setFilePath(String filePath) throws SAXException, IOException {
		this.filePath = filePath;
		makeDocument(filePath);
	}
	public void setInputStrem(InputStream inputStream) throws SAXException, IOException {
		this.inputStream = inputStream;
		makeDocument(inputStream);
	}
	public void setFile(File file) throws SAXException, IOException {
		this.file = file;
		makeDocument(file);
	}
	
	public void setURL(URL url) throws IOException, SAXException  {
		this.url = url;
		try{
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(getConnectionTimeout());
            urlConnection.setReadTimeout(getReadTimeout());
            HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpConnection.getInputStream();
                    setInputStrem(inputStream);
            } else {
                    throw new IOException("HTTP Response is not \"HTTP Status-Code 200: OK.\"");
            }
//            System.out.println("a");
		}catch (SAXParseException e) {
//			System.out.println("b");
			CleanerProperties props = new CleanerProperties();
			props.setTranslateSpecialEntities(true);
			props.setTransResCharsToNCR(true);
			props.setOmitComments(true);
		    TagNode tagNode = new HtmlCleaner(props).clean(url);
		    PrettyXmlSerializer s = new PrettyXmlSerializer(props);
//		    OutputMemoryStream outputstream = new OutputMemoryStream();
//		    System.out.println( s.getAsString(tagNode));
//		    s.writeToStream(tagNode, outputstream);
//		    InputMemoryStream inputStream = new InputMemoryStream(outputstream.getBuffer());
		   InputStream inputStream =  ConversionUtil.toInputStream( s.getAsString(tagNode));
		    setInputStrem(inputStream);
		   /*  new PrettyXmlSerializer(props).writeToFile(
		             tagNode, "chinadaily.xml", "utf-8"
		         );*/
			
		}
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	public void getElementList(){
//		try{
			Document document = getDocument();
			Node node = document.getDocumentElement().getFirstChild();
//			System.out.println(node.getNodeName()+"1");
			while(node!=null){
				for (int i = 0; i < node.getChildNodes().getLength(); i++)
				{
					Node cnode = node.getChildNodes().item(i);
					System.out.println(cnode.getNodeName());
				}
				node = node.getNextSibling();
			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	
	private XPath getXPath() throws NoClassDefFoundError{
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		return xpath;
	}
	private XPathExpression xPathCompile(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		XPathExpression expr = null;
//		try
//		{
			expr = getXPath().compile(xPathUrl);
//		}
//		catch (XPathExpressionException e)
//		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return expr;
		
	}
	
	
//  System.out.println(parser.getString("//*[@id=\"Attribution1\"]/div[1]/a"));
//	String xPathUrl="/beans/bean[name='factory_a']/@class";
//	String xPathUrl="/beans/bean";
//	PathExpression expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
//	String xPathUrl="/beans/bean[constructor='a']"  빈안에 컨스트럭터 엘리먼트가 a인거.;
//	String xPathUrl="/beans/bean[@name='Float']/text()";
	public NodeList getNodes(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		NodeList nodesresult=null;
//		try
//		{
			XPathExpression expr=xPathCompile(xPathUrl);
			nodesresult = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
//		}
//		catch (XPathExpressionException e)
//		{
//			e.printStackTrace();
//		}
		return nodesresult;
		
	}
	public Node getNode(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		Node nodesresult=null;
//		try
//		{
			XPathExpression expr=xPathCompile(xPathUrl);
			nodesresult = (Node) expr.evaluate(document, XPathConstants.NODE);
//		}
//		catch (XPathExpressionException e)
//		{
//			e.printStackTrace();
//		}
		return nodesresult;
	}
	public Boolean getBoolean(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		Boolean nodesresult=null;
//		try
//		{
		String str  =  getString(xPathUrl);
			if(str==null){
			    return null;
			}
			
			str = StringUtil.deleteAllSpace(getString(xPathUrl));  //여기 KDN 수정됨
			if(str.equals("true") || str=="true"||str.equals("TRUE") || str=="TRUE"){                  
				return true;
			}else if(str.equals("false") || str=="false"||str.equals("FALSE") || str=="FALSE"){
				return false;
			}
			
			
			
			XPathExpression expr=xPathCompile(xPathUrl);
			nodesresult = (Boolean) expr.evaluate(document, XPathConstants.BOOLEAN);
//		}
//		catch (XPathExpressionException e)
//		{
//			e.printStackTrace();
//		}
		return nodesresult;
	}
	public Double getDouble(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		Double nodesresult=null;
//		try
//		{
			XPathExpression expr=xPathCompile(xPathUrl);
			nodesresult = (Double) expr.evaluate(document, XPathConstants.NUMBER);
//		}
//		catch (XPathExpressionException e)
//		{
//			e.printStackTrace();
//		}
         if(nodesresult==null || nodesresult.toString().equals("NaN") || nodesresult.toString()=="NaN"){
                return null;
        }
	         
		return nodesresult;
	}
	public Integer getInt(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		Double nodesresult=null;
//		try
//		{
			XPathExpression expr=xPathCompile(xPathUrl);
			nodesresult = (Double) expr.evaluate(document, XPathConstants.NUMBER);
			
	         if(nodesresult==null || nodesresult.toString().equals("NaN") || nodesresult.toString()=="NaN"){
	                return null;
	        }
//		}
//		catch (XPathExpressionException e)
//		{
//			e.printStackTrace();
//		}
		nodesresult+=0.000001;
		String t = String.format("%f", nodesresult).split("\\.")[0];
		return new Integer(t);//Integer.pnodesresult;
	}
	public String getString(String xPathUrl) throws XPathExpressionException,NoClassDefFoundError{
		String nodesresult=null;
//		try
//		{
		//System.out.println(xPathUrl);
			XPathExpression expr=xPathCompile(xPathUrl);
			nodesresult = (String) expr.evaluate(document, XPathConstants.STRING);
			
	         if(nodesresult==null ||nodesresult.length()==0 || nodesresult.toString().equals("NaN") || nodesresult.toString()=="NaN"){
	                return null;
	        }
//		}
//		catch (XPathExpressionException e)
//		{
//			e.printStackTrace();
//		}
		return nodesresult;
	}
	
	
	public String getString() throws IOException{
		if(inputStream!=null){
			inputStream.reset();
			return ConversionUtil.toString(inputStream);
		}
		return null;
	}
	
	
}
