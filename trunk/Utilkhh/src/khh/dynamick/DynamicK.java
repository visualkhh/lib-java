package khh.dynamick;

import java.io.File;
import java.util.ArrayList;

import khh.debug.LogK;
import khh.std.adapter.AdapterMap;
import khh.xml.XMLparser;

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
                int classsize = parser.getInt("count("+classxpath+")");
                log.debug("Class Global Element size : "+classsize+" path:"+classxpath);
                for (int j = 1; j <= classsize; j++) {
                    String contextpath      =   classxpath+"["+j+"]";
                    //String constructorpath  =   classxpath+"["+j+"]/constructor";
                    String id               =   parser.getString(contextpath+"/@id");
                    String classpath        =   parser.getString(contextpath+"/@classpath");
                    String ref              =   parser.getString(contextpath+"/@ref");
                    
                    DynamicClass dynamicClass = new DynamicClass();
                    dynamicClass.setId(id);
                    dynamicClass.setClassPath(classpath);
                    dynamicClass.setRef(ref);
                    classlist.add(id, dynamicClass);
                }
            }catch (Exception e) {
                try {
                    log.error("Class Global Element Error ",e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        
        // 
        for (int i = 0; i < configlist.size(); i++) {
            try{
                log.debug("ConfigFile["+i+"]   size("+configlist.size()+")");
                XMLparser parser = new XMLparser(configlist.get(i));
                
                //global class
                int classsize = parser.getInt("count("+classxpath+")");
                log.debug("Class Element size : "+classsize+" path:"+classxpath);
                for (int j = 1; j <= classsize; j++) {
                    String contextpath      =   classxpath+"["+j+"]";
                    String constructorpath  =   classxpath+"["+j+"]/constructor";
                    String methoadpath      =   classxpath+"["+j+"]/methoad";
                    int    constructorsize  =   parser.getInt("count("+constructorpath+")");
                    int    methoadsize      =   parser.getInt("count("+methoadpath+")");
                    String id               =   parser.getString(contextpath+"/@id");
                    String classpath        =   parser.getString(contextpath+"/@classpath");
                    String ref              =   parser.getString(contextpath+"/@ref");
                    
                    DynamicClass classAt = classlist.get(id);
                    
                    //Constructor
                    for (int c = 1; c <= constructorsize; c++) {
                        String c_contextpath  =   constructorpath+"["+c+"]/class";
                        int    c_class_size   =   parser.getInt("count("+c_contextpath+")");
                        log.debug("constructor Class Element size : "+constructorsize+" path:"+c_contextpath+"    sub_class_size("+c_class_size+")");
                        
                        //Constructor sub Class
                        ArrayList<DynamicClass> constructorParameter = new ArrayList<DynamicClass>();
                        for (int cc = 1; cc <= c_class_size; cc++) {
                            String cc_id               =   parser.getString(c_contextpath+"/@id");
                            String cc_classpath        =   parser.getString(c_contextpath+"/@classpath");
                            String cc_ref              =   parser.getString(c_contextpath+"/@ref");
                            
                            DynamicClass dynamicClass = null;
                            if(cc_ref==null){
                                dynamicClass = new DynamicClass();
                                dynamicClass.setId(cc_id);
                                dynamicClass.setClassPath(cc_classpath);
                                dynamicClass.setRef(cc_ref);
                            }else{
                                dynamicClass = classlist.get(cc_ref);
                            }
                            constructorParameter.add(dynamicClass);
                        }
                        classAt.setConstructorParameter(constructorParameter);
                    }
                    
                    
                    
                    //methoad
                    AdapterMap<String, ArrayList<DynamicClass>> methoads = new AdapterMap<String, ArrayList<DynamicClass>>(); 
                    for (int m = 1; m <= methoadsize; m++) {
                        String m_name         =   parser.getString(methoadpath+"["+m+"]/@name");
                        String m_contextpath  =   methoadpath+"["+m+"]/class";
                        int    m_class_size   =   parser.getInt("count("+m_contextpath+")");
                        log.debug("methoads Class Element size : "+m_class_size+" path:"+m_contextpath+"     m_class_size("+m_class_size+")");
                        
                        //Constructor sub Class
                        ArrayList<DynamicClass> methoadParameter = new ArrayList<DynamicClass>();
                        for (int cc = 1; cc <= m_class_size; cc++) {
                            String cc_id               =   parser.getString(m_contextpath+"/@id");
                            String cc_classpath        =   parser.getString(m_contextpath+"/@classpath");
                            String cc_ref              =   parser.getString(m_contextpath+"/@ref");
                            
                            DynamicClass dynamicClass = null;
                            if(cc_ref==null){
                                dynamicClass = new DynamicClass();
                                dynamicClass.setId(cc_id);
                                dynamicClass.setClassPath(cc_classpath);
                                dynamicClass.setRef(cc_ref);
                            }else{
                                dynamicClass = classlist.get(cc_ref);
                            }
                            methoadParameter.add(dynamicClass);
                        }
                        methoads.add(m_name, methoadParameter);
                    }
                    classAt.setMethoadParameter(methoads);
                    
                    
                    
                    

                }
            }catch (Exception e) {
                try {
                    log.error("sub  Class Error ",e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }




    public AdapterMap<String, DynamicClass> getClasslist() {
        return classlist;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
