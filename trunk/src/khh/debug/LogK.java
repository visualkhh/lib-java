package khh.debug;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import khh.callstack.util.StackTraceUtil;
import khh.date.util.DateUtil;
import khh.file.util.FileUtil;
import khh.property.util.PropertyUtil;
import khh.reflection.ReflectionUtil;
import khh.schedule.Scheduler;
import khh.string.util.StringUtil;
import khh.xml.XMLparser;


//<!-- Level can be  A, D, I, W, E, F, O -->
//<!--
//
//파일명은 logk 로시작해서 .xml으로 끝나야한다     클래스페스에 logk 폴더안에 생성되어야한다.
//	logk는 다양한 로깅레벨을 지원합니다.
//	
//	 O = OFF
//	 A = ALL
//	① F = FATAL : 가장 크리티컬한 에러가 일어 났을 때 사용합니다.   
//	② E = ERROR : 일반 에러가 일어 났을 때 사용합니다.
//	③ W = WARN : 에러는 아니지만 주의할 필요가 있을 때 사용합니다.
//	④ I = INFO : 일반 정보를 나타낼 때 사용합니다.
//	⑤ D = DEBUG : 일반 정보를 상세히 나타낼 때 사용합니다.
//
//	Pattern to output
//		%d : date
//		%l : priority (level)
//		%c : class,name   category (where the log is from) fullclassname 
//		%k : filename
//		%m : message
//		%n : line_number
//		%e : exception message
//		%f : MethodName
//		%r : EnterChar
//		category는 자바형  정규식으로 가능하다. =		^com\\.[A-Za-z0-9\\.\\@_\\-~#]+\\.hhk
//		
//			target > loger > outputstream 의 기본값은   System.out
//		java.io.OutputStream 를 상속받은 애들을... 가따놔야한다.
// -->
// <!--logkattribute 속성은 마지막파일에만 적용된속성값을 가짐 한곳에서만 적어주길원합니다. -->

public class LogK  implements Serializable {
    public static  LogK instance =null;
    String configfolder ="logk";
    ArrayList<File> configfile = new ArrayList<File>();
    ArrayList<LogKTarget> targets = new ArrayList<LogKTarget>();
    Scheduler scheduler = new Scheduler();
    long recycle_ms=10000;
    boolean recycle=true;
    
    
    ArrayList<String> configfile_add = new ArrayList<String>();
    ArrayList<String> path_add = new ArrayList<String>();
    
    private LogK(){
        setting();
    }
    
 
    
   synchronized private void setting() {
        String[] classpath = getConfigPath();
        setConfigFile(classpath);
        setConfig();
        
        scheduler.cancel();
        scheduler = new Scheduler();
        try {
            if(recycle){
                scheduler.schedule("recycle",new Runnable() {
                    public void run() {
                        System.out.println("LogK: recycle config file read \t recycle_ms : "+recycle_ms +" \t recycle : "+recycle);
                        setting();
                    }
                }, recycle_ms,1 );
            }else{
                scheduler.cancel();
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   synchronized private void setConfig() {
        targets.clear();
        String targetxpath="//target";
        for (int i = 0; i < configfile.size(); i++) {
        	
            XMLparser parser = null;
            try {
                parser = new XMLparser((File) configfile.get(i));
                recycle_ms = parser.getInt("//logkattribute/recycle_ms")==null?recycle_ms:parser.getInt("//logkattribute/recycle_ms");
                recycle = parser.getBoolean("//logkattribute/recycle")==null?false:parser.getBoolean("//logkattribute/recycle");
                int targetcnt = parser.getInt("count("+targetxpath+")");
                
                
                System.out.println("LogK: ConfigFilePath : "+configfile.get(i)+" \t recycle_ms :"+recycle_ms+" \t recycle : "+recycle+" \t targetcnt : "+targetcnt);
                
                
                for (int j = 1; j <= targetcnt; j++) {
                    LogKTarget atTarget = new LogKTarget();
                    atTarget.setId(parser.getString(targetxpath+"["+j+"]/@id"));
                    atTarget.setCategory(parser.getString(targetxpath+"["+j+"]/@category"));
                    atTarget.setExtendcategory(parser.getString(targetxpath+"["+j+"]/@extendcategory"));
                    
                    
                    Object value=null;
                    if(atTarget.getExtendcategory()!=null){
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/loger/level");
                        if(value!=null){
                            String s = ((String)value).replaceAll("\\p{Space}", "");
                            s = s.replaceAll(" ", "");
                            atTarget.setLoger_level(s.split(","));
                        }
                        
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/loger/dateformat");
                        if(value!=null)
                        atTarget.setLoger_dateformat((String)value);
                        
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/loger/logformat");
                        if(value!=null)
                        atTarget.setLoger_logformat((String)value);
                        
                        value=parser.getBoolean(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/loger/exception_stacktrace");
                        if(value!=null)
                            atTarget.setException_stacktrace((Boolean)value);
                        
                        
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/saver/dateformat");
                        if(value!=null)
                        atTarget.setSaver_dateformat((String)value);
                        
                        value=parser.getBoolean(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/saver/save");
                        if(value!=null)
                        atTarget.setSaver_save((Boolean)value);
                        
                        value=parser.getBoolean(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/saver/append");
                        if(value!=null)
                        atTarget.setSaver_append((Boolean)value);
                        
                        
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/saver/savepath");
                        if(value!=null)
                        atTarget.setSaver_savepath((String)value);
                        
                        
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/saver/filename");
                        if(value!=null)
                        atTarget.setSaver_filename((String)value);
                        
                        //추가20120925
                        value=parser.getString(targetxpath+"[@id='"+atTarget.getExtendcategory()+"']/loger/outputstream");
                        if(value!=null&& !value.equals(""))
                        atTarget.setOutputstream((OutputStream)ReflectionUtil.newClass((String)value));
                        
                        
                    }
                    
                    
                    value = parser.getString(targetxpath+"["+j+"]/loger/level");
                    if(value!=null){
                        String s = ((String)value).replaceAll("\\p{Space}", "");
                        s = s.replaceAll(" ", "");
                        atTarget.setLoger_level(s.split(","));
                    }
                    
                    value = parser.getString(targetxpath+"["+j+"]/loger/dateformat");
                    if(value!=null)
                    atTarget.setLoger_dateformat((String)value);
                    
                    value = parser.getString(targetxpath+"["+j+"]/loger/logformat");
                    if(value!=null)
                    atTarget.setLoger_logformat((String)value);
                    
                    value=parser.getBoolean(targetxpath+"["+j+"]/loger/exception_stacktrace");
                    if(value!=null)
                    atTarget.setException_stacktrace((Boolean)value);
                    
                    value = parser.getString(targetxpath+"["+j+"]/saver/dateformat");
                    if(value!=null)
                    atTarget.setSaver_dateformat((String)value);
                    
                    value = parser.getBoolean(targetxpath+"["+j+"]/saver/save");
                    if(value!=null)
                    atTarget.setSaver_save((Boolean)value);
                    
                    value = parser.getBoolean(targetxpath+"["+j+"]/saver/append");
                    if(value!=null)
                    atTarget.setSaver_append((Boolean)value);
                    
                    value = parser.getString(targetxpath+"["+j+"]/saver/savepath");
                    if(value!=null)
                    atTarget.setSaver_savepath((String)value);
                    
                    value = parser.getString(targetxpath+"["+j+"]/saver/filename");
                    if(value!=null)
                    atTarget.setSaver_filename((String)value);
                    
                    //추가20120925
                    value=parser.getString(targetxpath+"["+j+"]/loger/outputstream");
                    if(value!=null && !value.equals(""))
                    atTarget.setOutputstream((OutputStream)ReflectionUtil.newClass((String)value));
                    
                    
                    //System.out.println(atTarget.getId()+"       "+atTarget.getException_stacktrace());
                    
                    targets.add(atTarget);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                if(parser!=null)
                parser.finalize();
            }finally{
                if(parser!=null)
                    parser.finalize();
            }
        }
    }
    
   synchronized  private void setConfigFile(String[] classpath) {
        configfile.clear();
        FilenameFilter filenamefilter = new FilenameFilter() {
            public boolean accept(File arg0, String filename) {
//                System.out.println(Util.isFind("^logk.*.\\.xml", filename)+"    "+filename);
//            	 System.out.println("LOGK --- > "+filename);
                return StringUtil.isFind("^logk[A-Za-z0-9\\.\\@_\\-~#]+\\.xml", filename);
//                return filename.startsWith("logk") && filename.endsWith(".xml"); 
            }
        };
        
        
        
        for (int i = 0; i < classpath.length; i++) {
            String atpath =  classpath[i] +PropertyUtil.getFileSeparator()+configfolder;
            System.out.println("LogK: ConfigPath : "+atpath);
            File[] files=null;
            try {
                files = FileUtil.getFileList(new File(atpath), filenamefilter);
                //System.out.println("LogK ConfigFile  size : "+ atpath+"      "+files.length);
            } catch (IOException e) {
            }
            for (int j = 0; files!=null && j < files.length; j++) {
                System.out.println("LogK ConfigFile loading  : "+atpath+"     "+files[j].getName());
                configfile.add(files[j]);
            }
        };
        
        
        //user driect Add 
        for (int i = 0; i < configfile_add.size(); i++) {
        	configfile.add(new File(configfile_add.get(i)));
		};
        
        
    }
   synchronized  private String[] getConfigPath() {
	   //System.out.println("----------- "+ FileUtil.getFilePath(LogK.class,""));
        String classpaths = PropertyUtil.getClassPath()+PropertyUtil.getPathSeparator()+PropertyUtil.getUserDir();
        for (int i = 0; i < path_add.size(); i++) {
        	classpaths+=(PropertyUtil.getPathSeparator()+path_add.get(i));
		}
        //System.out.println(classpaths);
        String [] classpath = classpaths.split(PropertyUtil.getPathSeparator());
        return classpath;
    }
   synchronized static public LogK getInstance(){
        if(instance==null)
            instance = new LogK();
        return instance;
    }
    
    

    

    
   synchronized public ArrayList<LogKTarget> getLogKTarget(String classpath){
        ArrayList<LogKTarget> g = new ArrayList<LogKTarget>();
        for (int i = 0; i < targets.size(); i++) {
            LogKTarget target = (LogKTarget)targets.get(i);
            if(target.getCategory()!=null){
//                if(Util.isFind("^"+(target.getCategory().replaceAll("\\.", "\\\\\\.")), classpath)){
                if(StringUtil.isFind(target.getCategory(), classpath)){
                    g.add(target);
                }
                //System.out.println(Util.isFind("^"+target.getCategory(), classpath)+"   "+classpath+"   "+"^"+target.getCategory());
            }
        }
        return g;
    }
    
    
    
   synchronized private void log(String level,Object message){
        log(level,message,null);
    }
   synchronized  private void log(String level,Object message,Throwable e){
       StackTraceElement st = 	StackTraceUtil.getBeforeStackTraceElement(LogK.class);
       String classpath 	=	st.getClassName();
       String filename 		=	st.getFileName();
       String methodname 	=	st.getMethodName();
       int classlinenumber 	=	st.getLineNumber();
       
       ArrayList<LogKTarget> gettarget =  getLogKTarget(classpath);
       for (int i = 0; i < gettarget.size(); i++) {
           LogKTarget attarget = (LogKTarget) gettarget.get(i);
         
           if(getGrant(level, attarget)==false)
               continue;
           
           
           
           String logerDateformat = attarget.getLoger_dateformat();
           String logerformat = attarget.getLoger_logformat();
           String date = DateUtil.getDate(logerDateformat);
           
           logerformat = logerformat.replaceAll("%d", date);
           logerformat = logerformat.replaceAll("%l", level);
           logerformat = logerformat.replaceAll("%c", StringUtil.regexMetaCharToEscapeChar(classpath));
           logerformat = logerformat.replaceAll("%k", filename);
           logerformat = logerformat.replaceAll("%f", methodname);
           logerformat = logerformat.replaceAll("%n", classlinenumber+"");
           logerformat = logerformat.replaceAll("%r", PropertyUtil.getSeparator());
           logerformat = logerformat.replaceAll("%m", StringUtil.regexMetaCharToEscapeChar(message.toString()));
           if(e==null){
               logerformat = logerformat.replaceAll("%e","");
           }else{
               StringBuffer buffer = new StringBuffer();
               StackTraceElement[] av = e.getStackTrace();
               buffer.append("Exception "+e.toString()+" "+(e.getMessage()==null?"":e.getMessage()));
               for (int j = 0; attarget.getException_stacktrace()!=null&&attarget.getException_stacktrace()==true&&j < av.length; j++) {
                   buffer.append(PropertyUtil.getSeparator());
                   buffer.append(av[j].getClassName()+av[j].getMethodName()+"("+av[j].getFileName()+":"+av[j].getLineNumber()+")");
               }
               logerformat = logerformat.replaceAll("%e",StringUtil.regexMetaCharToEscapeChar(buffer.toString()));
           }
           
           try{
        	   attarget.getOutputstream().write(logerformat.getBytes());
           }catch (Exception e2) {
        	   e2.printStackTrace();
           }
           //    System.out.println(logerformat);
           
               
               if(attarget.isSaver_save()){
                   boolean append= attarget.isSaver_append()==null?true:attarget.isSaver_append();
                   String saver_dateformat = attarget.getSaver_dateformat();
                   String saver_date = DateUtil.getDate(saver_dateformat);
                   
                   String saver_filename = attarget.getSaver_filename();
                   saver_filename= saver_filename.replaceAll("%c", classpath);
                   saver_filename= saver_filename.replaceAll("%d", saver_date);
                   
                   String savepath = attarget.getSaver_savepath()+PropertyUtil.getFileSeparator()+saver_filename;
                   try {
                	   if(!FileUtil.isExists(attarget.getSaver_savepath())){
                		   FileUtil.mkdirs(attarget.getSaver_savepath());
                	   };
                	   
                        FileUtil.writeFile(savepath, logerformat+PropertyUtil.getSeparator(),append);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
               }
               
//           if(level.equals(LogKTarget.FATAL) || level.equals(LogKTarget.ERROR) ){
//               System.out.println(logerformat);
//           }else{
//               System.out.println(logerformat);
//           }
           
       }
   }
   
   
   
   
   
   synchronized public void fatal(Object message){
       fatal(message,null);
   }
   synchronized public void fatal(Object message,Throwable e){
       log(LogKTarget.FATAL,message,e);
   };
   
   synchronized public void error(Object message){
       error(message,null);
   }
   synchronized public void error(Object message,Throwable e){
       log(LogKTarget.ERROR,message,e);
   };
   
   synchronized public void warn(Object message){
       warn(message,null);
   }
   synchronized public void warn(Object message,Throwable e){
       log(LogKTarget.WARN,message,e);
   };
   
   synchronized public void info(Object message){
       info(message,null);
   }
   synchronized public void info(Object message,Throwable e){
       log(LogKTarget.INFO,message,e);
   };
   
   synchronized public void debug(Object message){
       debug(message,null);
   }
   synchronized public void debug(Object message,Throwable e){
       log(LogKTarget.DEBUG,message,e);
   };
   synchronized private boolean getGrant(String grant, LogKTarget target){
        String[] grants = target.getLoger_level();
        boolean sw = false;
        for (int i = 0; grants!=null && i < grants.length; i++) {
            if(grant.equals(grants[i])){
                sw =  true;
            }
            if(grants[i].equals(LogKTarget.OFF)){
                sw=false;
                break;
            }
            if(grants[i].equals(LogKTarget.ALL)){
                sw=true;
            }
        }
       // System.out.println(sw);
        return sw;
    }

    
    synchronized public void addPath(String path){
    	path_add.add(path);
    	setting() ;
    }
    synchronized public void addConfigfile(String configfilepath){
    	configfile_add.add(configfilepath);
    	setting() ;
    }
    
    
    synchronized public void close(){
        if(scheduler!=null)
         scheduler.cancel();
     }

    
    @Override
    protected void finalize() throws Throwable {
        if(scheduler!=null)
        scheduler.cancel();
        super.finalize();
    }
    
    
}
