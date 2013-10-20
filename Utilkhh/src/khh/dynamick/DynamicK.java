package khh.dynamick;

import java.io.File;
import java.util.ArrayList;

import khh.debug.LogK;
import khh.std.adapter.AdapterMap;
import khh.xml.XMLparser;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DynamicK {
   private AdapterMap<String,File> configlist=null;
   private LogK log = LogK.getInstance();
   private AdapterMap<String,DynamicClass> classlist=null;
    public DynamicK() {
        configlist      = new AdapterMap<String,File>();
        classlist       = new AdapterMap<String,DynamicClass>();
    }
    
    
    
    
    public void addConfigFile(String realpath) throws Exception{
        if(realpath!=null){
            addConfigFile(new File(realpath));
        }
    }
    public void addConfigFile(File file) throws Exception{
        if(file!=null && file.exists() && file.isFile()){
            configlist.add(file.getAbsolutePath(),file);
        }
    }
    
    
    
    
    public void setting() {
        if(configlist!=null && configlist.size()>0){
            setConfig();
        }
    }
    
    
    
    
    
    
    
    
    
    

  private String classxpath="/dynamick/class";
  private void setConfig() {
      
      //Global settingConfig
      // 우선 전체적인 config파일에서  뭐든 class파일을 불러서 넣는다.
      for (int i = 0; i < configlist.size(); i++) {
          try{
              log.debug("ConfigFile["+i+"]   size("+configlist.size()+")");
              XMLparser parser = new XMLparser(configlist.get(i));
              
              //global class
              String contextpath      =   classxpath+"[@id]";
              NodeList nodes = parser.getNodes(contextpath);
              log.debug("Class Global Element  xpath:"+contextpath+ "   size:"+nodes.getLength());
              
              
              for (int j = 0; j < nodes.getLength(); j++) {
                  DynamicClass dynamicClass = new DynamicClass();
                  dynamicClass.setNode(nodes.item(j));
//                  dynamicClass.setId(attr.get("id"));
//                  dynamicClass.setClassPath(attr.get("classpath"));
//                  dynamicClass.setRef(attr.get("ref"));
                  classlist.add(dynamicClass.getAttribute("id"), dynamicClass);
              }
              parser.close();
          }catch (Exception e) {
                  log.error("Class Global Element Error ",e);
          }
      }
      
      
      //재귀적 DynamicClass 생성
      for (int i = 0; i < classlist.size(); i++){
          try{
              getClass(classlist.get(i));
          }catch (Exception e) {
            log.error("재귀적 DynamicClass 생성 Error ",e);
          }
      }
      
      

      //Class 생성
      for (int i = 0; i < classlist.size(); i++){
          try{
              String id = classlist.get(i).getAttribute("id");
              String classpath = classlist.get(i).getAttribute("classpath");
             
              classlist.get(i).newClassObject();
//              getClass(classlist.get(i));
              
          }catch (Exception e) {
            log.error("Class 생성 Error ",e);
          }finally{
          }
      }
      
      
      
      //methoad 실행
      for (int i = 0; i < classlist.size(); i++){
          try{
              String id = classlist.get(i).getAttribute("id");
              String classpath = classlist.get(i).getAttribute("classpath");
             
              classlist.get(i).executeMethoad();
//              getClass(classlist.get(i));
              
          }catch (Exception e) {
            log.error("methoad 실행 Error ",e);
          }finally{
          }
      }
      
        log.debug(classlist.size());
   
  }
    
  
  
  
  

    public void getClass(DynamicClass dclass) throws Exception {
        Node node = dclass.getNode();
        NodeList list = node.getChildNodes();    
        
        ArrayList<DynamicClass> constructorParameter = new ArrayList<DynamicClass>();
        AdapterMap<String, ArrayList<DynamicClass>> methoadParameter = new AdapterMap<String, ArrayList<DynamicClass>>();
        
          for(int i = 0 ; i<list.getLength() ; i++) {
              
            Node classSubNode = list.item(i);
            //Element만. constructor, methoad
            if( classSubNode.getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
            }else{
                continue;
            }
                    
            
            
             if("constructor".equals(classSubNode.getNodeName()) ||  "constructor"==classSubNode.getNodeName()){
                NodeList conSubList = classSubNode.getChildNodes();
                for(int y = 0; y < conSubList.getLength(); y++){
                    Node conSubNode = conSubList.item(y);
                    if( conSubNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ){
                    }else{
                        continue;
                    }
                    
                    
                    DynamicClass sdclass = new DynamicClass(conSubNode);
                    String ref = sdclass.getAttribute("ref");
                    
                    
                    if(ref!=null && ref.length()>0){
                        sdclass = classlist.get(ref);
                    }
                    constructorParameter.add(sdclass);
                    getClass(sdclass);
                }
                 
                 
             }else if("methoad".equals(classSubNode.getNodeName()) ||  "methoad"==classSubNode.getNodeName()){
                 ArrayList<DynamicClass> methoadSubClassList= new ArrayList<DynamicClass>();
                 NodeList methoadSubList = classSubNode.getChildNodes();
                 for(int y = 0; y < methoadSubList.getLength(); y++){
                     Node methoadSubNode = methoadSubList.item(y);
//                     log.debug(methoadSubNode.getNodeName()+"     ("+methoadSubNode.getNodeType()+")");
                     if( methoadSubNode.getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
                     }else{
                         continue;
                     }
                     DynamicClass sdclass = new DynamicClass(methoadSubNode);
                     String ref = sdclass.getAttribute("ref");
                     if(ref!=null && ref.length()>0){
                         sdclass = classlist.get(ref);
                     }
                     methoadSubClassList.add(sdclass);
                     getClass(sdclass);
                 }
                 methoadParameter.add(
                         ((Attr)classSubNode.getAttributes().getNamedItem("name")).getValue()
                         , methoadSubClassList
                         );
                 
                 
             }
          }
          
          dclass.setConstructorParameter(constructorParameter);
          dclass.setMethoadParameter(methoadParameter);
        
//        return null;
    }
  
  
  
  
  
  
  
  
//    
//    private String classxpath="/dynamick/class";
//    private void setConfig() {
//        
//        //Global settingConfig
//        // 우선 전체적인 config파일에서  뭐든 class파일을 불러서 넣는다.
//        for (int i = 0; i < configlist.size(); i++) {
//            try{
//                log.debug("ConfigFile["+i+"]   size("+configlist.size()+")");
//                XMLparser parser = new XMLparser(configlist.get(i));
//                
//                //global class
//                int classsize = parser.getInt("count("+classxpath+")");
//                log.debug("Class Global Element size : "+classsize+" path:"+classxpath);
//                for (int j = 1; j <= classsize; j++) {
//                    String contextpath      =   classxpath+"["+j+"]";
//                    //String constructorpath  =   classxpath+"["+j+"]/constructor";
//                    String id               =   parser.getString(contextpath+"/@id");
//                    String classpath        =   parser.getString(contextpath+"/@classpath");
//                    String ref              =   parser.getString(contextpath+"/@ref");
//                    
//                    DynamicClass dynamicClass = new DynamicClass();
//                    dynamicClass.setId(id);
//                    dynamicClass.setClassPath(classpath);
//                    dynamicClass.setRef(ref);
//                    classlist.add(id, dynamicClass);
//                }
//            }catch (Exception e) {
//                try {
//                    log.error("Class Global Element Error ",e);
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        
//        
//        // 
//        for (int i = 0; i < configlist.size(); i++) {
//            try{
//                log.debug("ConfigFile["+i+"]   size("+configlist.size()+")");
//                XMLparser parser = new XMLparser(configlist.get(i));
//                
//                //global class
//                int classsize = parser.getInt("count("+classxpath+")");
//                log.debug("Class Element size : "+classsize+" path:"+classxpath);
//                for (int j = 1; j <= classsize; j++) {
//                    String contextpath      =   classxpath+"["+j+"]";
//                    String constructorpath  =   classxpath+"["+j+"]/constructor";
//                    String methoadpath      =   classxpath+"["+j+"]/methoad";
//                    int    constructorsize  =   parser.getInt("count("+constructorpath+")");
//                    int    methoadsize      =   parser.getInt("count("+methoadpath+")");
//                    String id               =   parser.getString(contextpath+"/@id");
//                    String classpath        =   parser.getString(contextpath+"/@classpath");
//                    String ref              =   parser.getString(contextpath+"/@ref");
//                    
//                    DynamicClass classAt = classlist.get(id);
//                    
//                    //Constructor
//                    for (int c = 1; c <= constructorsize; c++) {
//                        String c_contextpath  =   constructorpath+"["+c+"]/class";
//                        int    c_class_size   =   parser.getInt("count("+c_contextpath+")");
//                        log.debug("constructor Class Element size : "+constructorsize+" path:"+c_contextpath+"    sub_class_size("+c_class_size+")");
//                        
//                        //Constructor sub Class
//                        ArrayList<DynamicClass> constructorParameter = new ArrayList<DynamicClass>();
//                        for (int cc = 1; cc <= c_class_size; cc++) {
//                            String cc_id               =   parser.getString(c_contextpath+"/@id");
//                            String cc_classpath        =   parser.getString(c_contextpath+"/@classpath");
//                            String cc_ref              =   parser.getString(c_contextpath+"/@ref");
//                            
//                            DynamicClass dynamicClass = null;
//                            if(cc_ref==null){
//                                dynamicClass = new DynamicClass();
//                                dynamicClass.setId(cc_id);
//                                dynamicClass.setClassPath(cc_classpath);
//                                dynamicClass.setRef(cc_ref);
//                            }else{
//                                dynamicClass = classlist.get(cc_ref);
//                            }
//                            constructorParameter.add(dynamicClass);
//                        }
//                        classAt.setConstructorParameter(constructorParameter);
//                    }
//                    
//                    
//                    
//                    //methoad
//                    AdapterMap<String, ArrayList<DynamicClass>> methoads = new AdapterMap<String, ArrayList<DynamicClass>>(); 
//                    for (int m = 1; m <= methoadsize; m++) {
//                        String m_name         =   parser.getString(methoadpath+"["+m+"]/@name");
//                        String m_contextpath  =   methoadpath+"["+m+"]/class";
//                        int    m_class_size   =   parser.getInt("count("+m_contextpath+")");
//                        log.debug("methoads Class Element size : "+m_class_size+" path:"+m_contextpath+"     m_class_size("+m_class_size+")");
//                        
//                        //Constructor sub Class
//                        ArrayList<DynamicClass> methoadParameter = new ArrayList<DynamicClass>();
//                        for (int cc = 1; cc <= m_class_size; cc++) {
//                            String cc_id               =   parser.getString(m_contextpath+"/@id");
//                            String cc_classpath        =   parser.getString(m_contextpath+"/@classpath");
//                            String cc_ref              =   parser.getString(m_contextpath+"/@ref");
//                            
//                            DynamicClass dynamicClass = null;
//                            if(cc_ref==null){
//                                dynamicClass = new DynamicClass();
//                                dynamicClass.setId(cc_id);
//                                dynamicClass.setClassPath(cc_classpath);
//                                dynamicClass.setRef(cc_ref);
//                            }else{
//                                dynamicClass = classlist.get(cc_ref);
//                            }
//                            methoadParameter.add(dynamicClass);
//                        }
//                        methoads.add(m_name, methoadParameter);
//                    }
//                    classAt.setMethoadParameter(methoads);
//                    
//                    
//                    
//                    
//
//                }
//            }catch (Exception e) {
//                try {
//                    log.error("sub  Class Error ",e);
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }




    public AdapterMap<String, DynamicClass> getClasslist() {
        return classlist;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
