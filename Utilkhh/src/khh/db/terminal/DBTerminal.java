package khh.db.terminal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import khh.collection.DuplicationArrayList;
import khh.collection.StandardArrayList;
import khh.db.connection.ConnectionCreator_I;
import khh.db.statement.LogPreparedStatement;
import khh.db.terminal.resultset.DBTResultRecord;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.db.util.DBUtil;
import khh.debug.LogK;
import khh.file.util.FileUtil;
import khh.property.util.PropertyUtil;
import khh.sort.SortUtil;
import khh.sort.comparator.CompareBase;
import khh.sort.comparator.CompareIntegerStandard;
import khh.std.Standard;
import khh.std.adapter.AdapterMap;
import khh.std.adapter.AdapterMapBase;
import khh.string.util.StringUtil;
import khh.xml.XMLparser;

public class DBTerminal {

    
    //config쪽을 왜 static으로 잡았냐면   DBTerminal을 생성할때마다 매번 xml을 읽을 필요가 없기때문이다.
    
    //static fild
    private static String configfolder ="dbt";
    private static ArrayList<String> path_add = new ArrayList<String>();
    private static DuplicationArrayList<String> configfile_add = new DuplicationArrayList<String>();
    private static ArrayList<File> configfile = new ArrayList<File>();
    private static HashMap<String,String> sql = new HashMap<String, String>();
    private static LogK log = LogK.getInstance();
    
    
    private ConnectionCreator_I connectioncreator=null;
    private Connection connection=null;
    private LogK logk  = LogK.getInstance();
    private Boolean autoCommit=null;
    
    
    
    
    synchronized public static void addConfigfile(String configfilepath){
        configfile_add.add(configfilepath);
        reload() ;
    }
    synchronized public static void reload(){
       String[] classpath = getConfigPath();
       setConfigFile(classpath);
       setConfig();
    }
    synchronized  private static String[] getConfigPath() {
        //System.out.println("----------- "+ FileUtil.getFilePath(LogK.class,""));
         String classpaths = PropertyUtil.getClassPath()+PropertyUtil.getPathSeparator()+PropertyUtil.getUserDir();
         for (int i = 0; i < path_add.size(); i++) {
             classpaths+=(PropertyUtil.getPathSeparator()+path_add.get(i));
         }
         //System.out.println(classpaths);
         String [] classpath = classpaths.split(PropertyUtil.getPathSeparator());
         return classpath;
     }
    
    synchronized  private static void setConfigFile(String[] classpath) {
        configfile.clear();
        FilenameFilter filenamefilter = new FilenameFilter() {
            public boolean accept(File arg0, String filename) {
                return StringUtil.isFind(filename,"^dbt[A-Za-z0-9\\.\\@_\\-~#]+\\.xml");
            }
        };
        
        
        
        for (int i = 0; i < classpath.length; i++) {
            String atpath =  classpath[i] +PropertyUtil.getFileSeparator()+configfolder;
            log.debug("ConfigPath : "+atpath);
            File[] files=null;
            try {
                files = FileUtil.getFileList(new File(atpath), filenamefilter);
                //System.out.println("LogK ConfigFile  size : "+ atpath+"      "+files.length);
            } catch (IOException e) {
            }
            for (int j = 0; files!=null && j < files.length; j++) {
                log.debug("ConfigFile loading  : "+atpath+"     "+files[j].getName());
                configfile.add(files[j]);
            }
        };
        
        
        //user driect Add 
        ArrayList<String> distinct = configfile_add.getDistinct();
        for (int i = 0; i < distinct.size(); i++) {
            log.debug("ConfigAddFile loading  : "+distinct.get(i));
            configfile.add(new File(distinct.get(i)));
        };
    }
    
    
    
    
    
    synchronized private static void setConfig() {
        sql.clear();
        String root_path="//sqlmap";
        String select_path="//sqlmap/select";
        String insert_path="//sqlmap/insert";
        String update_path="//sqlmap/update";
        String delete_path="//sqlmap/delete";
        for (int i = 0; i < configfile.size(); i++) {
            
            XMLparser parser = null;
            try {
                parser = new XMLparser((File) configfile.get(i));
                int cnt = parser.getInt("count("+select_path+")");
                for (int j = 1; j <= cnt; j++) {
                    String key = parser.getString(select_path+"["+j+"]/@id");
                    String value = parser.getString(select_path+"["+j+"]");
                    sql.put(key, value);
                }
                
                
                
                cnt = parser.getInt("count("+insert_path+")");
                for (int j = 1; j <= cnt; j++) {
                    String key = parser.getString(insert_path+"["+j+"]/@id");
                    String value = parser.getString(insert_path+"["+j+"]");
                    sql.put(key, value);
                }
                
                
                cnt = parser.getInt("count("+update_path+")");
                for (int j = 1; j <= cnt; j++) {
                    String key = parser.getString(update_path+"["+j+"]/@id");
                    String value = parser.getString(update_path+"["+j+"]");
                    sql.put(key, value);
                }
                
                
                cnt = parser.getInt("count("+delete_path+")");
                for (int j = 1; j <= cnt; j++) {
                    String key = parser.getString(delete_path+"["+j+"]/@id");
                    String value = parser.getString(delete_path+"["+j+"]");
                    sql.put(key, value);
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
        
        log.debug("sql Size : "+sql.size());
        
    }
    
    
    
    
    
    
    
    
    

    
    public DBTerminal(ConnectionCreator_I connectioncreator) {
        this.connectioncreator=connectioncreator;
    }
    
  
    

    
    
    
    
    public void setAutoCommit(Boolean watnAutoCommit) throws Exception {
        autoCommit=watnAutoCommit;
        if(null!=connection){
        	if(connection.isClosed()){
        		connection = getConnection();
        	}
        	connection.setAutoCommit(autoCommit);
        }
    }
    
 
//    private void closeConnection() throws SQLException, Exception {
//       getConnection().close();
//       logk.debug("CloseConnection");
//        
//    }



    public DBTResultSetContainer executeMapQuery(String sqlid) throws Exception{
        if(sql.get(sqlid)==null){
            log.error("not Found find SqlMap id "+sqlid);
            throw new Exception("not Found find SqlMap id "+sqlid);
        }
        return executeQuery(sql.get(sqlid));
    }
//    public DBTResultSetContainer executeMapQuery(String sqlid,AdapterMapBase<String,Object> param) throws Exception{
//        if(sql.get(sqlid)==null){
//            log.error("not Found find SqlMap id "+sqlid);
//            throw new Exception("not Found find SqlMap id "+sqlid);
//        }
//        String sql_get  = sql.get(sqlid);
//        StandardArrayList<Integer,Object> datafull = new StandardArrayList<Integer,Object>();
//        for (int i = 0; i < param.size(); i++) {
//            String findstr="#"+param.getKey(i)+"#";
//            ArrayList<Integer> size = StringUtil.getFindIndex(findstr, sql_get);
//           log.debug( findstr +"  "+ sql_get.indexOf(findstr)+"    "+size.size() );
//           for (int j = 0; j < size.size(); j++) {
//             datafull.add(new Standard<Integer, Object>(size.get(j),param.get(i)));
//           }
//           sql_get = StringUtil.replaceAll(sql_get, findstr, StringUtil.lpad(" ",findstr.length(),"?"));
//        }
//        
//        SortUtil.sort(datafull, new CompareIntegerStandard(CompareBase.TYPE_ASC));
//        return executeQuery(sql_get,datafull.getValueArrayList());
//    }
    public DBTResultSetContainer executeMapQuery(String sqlid,Map<String,Object> param) throws Exception{
    	if(sql.get(sqlid)==null){
    		log.error("not Found find SqlMap id "+sqlid);
    		throw new Exception("not Found find SqlMap id "+sqlid);
    	}
    	String sql_get  = sql.get(sqlid);
    	StandardArrayList<Integer,Object> datafull = new StandardArrayList<Integer,Object>();
    	
    	Iterator<Entry<String, Object>> iterator = param.entrySet().iterator();
    	while(iterator.hasNext()){
    		Map.Entry entry = (Map.Entry) iterator.next();
    		String findstr="#"+entry.getKey()+"#";
    		ArrayList<Integer> size = StringUtil.getFindIndex(findstr, sql_get);
    		log.debug( findstr +"  "+ sql_get.indexOf(findstr)+"    "+size.size() );
    		for (int j = 0; j < size.size(); j++) {
    			datafull.add(new Standard<Integer, Object>(size.get(j),entry.getValue()));
    		}
    		sql_get = StringUtil.replaceAll(sql_get, findstr, StringUtil.lpad(" ",findstr.length(),"?"));
    	}
    	
    	SortUtil.sort(datafull, new CompareIntegerStandard(CompareBase.TYPE_ASC));
    	return executeQuery(sql_get,datafull.getValueArrayList());
    }
    
    
    public DBTResultSetContainer executeQuery(String sql) throws Exception{
      return executeQuery(sql,null);
    }
    public DBTResultSetContainer executeQuery(String sql,ArrayList param) throws Exception{
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet rset=null;
        DBTResultSetContainer rsc=null;
        try {
            connection = getConnection();
            pstmt = new LogPreparedStatement(connection,sql);
            if(param!=null)
            pstmt = DBUtil.setPreparedStatementValue(pstmt,param);
            logk.debug( ((LogPreparedStatement)pstmt).getQueryString());
            rset =  pstmt.executeQuery();
            rsc = DBUtil.makeResultSetContainer(rset);
            //commit();
        } catch (Exception e) {
            logk.error("error  ",e);
            if(connection!=null){
                rollback();
            }
            throw e;
        }finally{
            if(rset!=null){
                rset.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                if(connection.getAutoCommit()==true){
                    connection.close();
                    //closeConnection();
                }
            }
        }
        return rsc;
    }
    
    
    
    
    
    
    
    public int executeMapUpdate(String sqlid) throws Exception{
        if(sql.get(sqlid)==null){
            log.error("not Found find SqlMap id "+sqlid);
            throw new Exception("not Found find SqlMap id "+sqlid);
        }
        return executeUpdate(sql.get(sqlid));
    }
//    public int executeMapUpdate(String sqlid,AdapterMapBase<String,Object> param) throws Exception{
//        if(sql.get(sqlid)==null){
//            log.error("not Found find SqlMap id "+sqlid);
//            throw new Exception("not Found find SqlMap id "+sqlid);
//        }
//        String sql_get  = sql.get(sqlid);
//        StandardArrayList<Integer,Object> datafull = new StandardArrayList<Integer,Object>();
//        for (int i = 0; null!=param && i < param.size(); i++) {
//            String findstr="#"+param.getKey(i)+"#";
//            ArrayList<Integer> size = StringUtil.getFindIndex(findstr, sql_get);
//           log.debug( findstr +"  "+ sql_get.indexOf(findstr)+"    "+size.size() );
//           for (int j = 0; j < size.size(); j++) {
//             datafull.add(new Standard<Integer, Object>(size.get(j),param.get(i)));
//           }
//           sql_get = StringUtil.replaceAll(sql_get, findstr, StringUtil.lpad(" ",findstr.length(),"?"));
//        }
//        
//        SortUtil.sort(datafull, new CompareIntegerStandard(CompareBase.TYPE_ASC));
//        return executeUpdate(sql_get,datafull.getValueArrayList());
//    }
    
    
    
    public int executeMapUpdate(String sqlid,Map<String,Object> param) throws Exception{
    	if(sql.get(sqlid)==null){
    		log.error("not Found find SqlMap id "+sqlid);
    		throw new Exception("not Found find SqlMap id "+sqlid);
    	}
    	String sql_get  = sql.get(sqlid);
    	StandardArrayList<Integer,Object> datafull = new StandardArrayList<Integer,Object>();
    	
    	Iterator<Entry<String, Object>> iterator = param.entrySet().iterator();
    	while(iterator.hasNext()){
    		Entry entry = (Entry) iterator.next();
    		String findstr="#"+entry.getKey()+"#";
    		ArrayList<Integer> size = StringUtil.getFindIndex(findstr, sql_get);
    		log.debug( findstr +"  "+ sql_get.indexOf(findstr)+"    "+size.size() );
    		for (int j = 0; j < size.size(); j++) {
    			datafull.add(new Standard<Integer, Object>(size.get(j),entry.getValue()));
    		}
    		sql_get = StringUtil.replaceAll(sql_get, findstr, StringUtil.lpad(" ",findstr.length(),"?"));
    	}
    	SortUtil.sort(datafull, new CompareIntegerStandard(CompareBase.TYPE_ASC));
    	return executeUpdate(sql_get,datafull.getValueArrayList());
    }
    
    
    
    
    
    
    
    public int executeUpdate(String sql) throws Exception{
       return executeUpdate(sql,null);
    }
    
    
    public int executeUpdate(String sql,ArrayList param) throws Exception{
        Connection connection =null;
        PreparedStatement pstmt=null;
        int updatecnt=0;
        try {
            connection = getConnection();
           // System.out.println("getConnection2   "+connection.getAutoCommit());
            pstmt = new LogPreparedStatement(connection,sql);
            if(param!=null)
            pstmt = DBUtil.setPreparedStatementValue(pstmt,param);
            logk.debug( ((LogPreparedStatement)pstmt).getQueryString());
            updatecnt =  pstmt.executeUpdate();
            //commit();
        } catch (Exception e) {
            logk.error("error  ",e);
            if(connection!=null){
                rollback();
            }
            throw e;
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                if(connection.getAutoCommit()==true){
                    connection.close();
                    //closeConnection();
                }
            }
        }
        return updatecnt;
    }
    
    
    public void commit() throws Exception{
    	if(null!=connection){
    		if(!connection.isClosed()){
				if(!connection.getAutoCommit())
					connection.commit();
    		}
    	}
//        if(getConnection()!=null && getConnection().isClosed()==false){
//        	if(getConnection().getAutoCommit()==false){
//        		getConnection().commit();
//        	}
//        	getConnection().close();
//            closeConnection();
//        }
    }
    
    public void rollback() throws Exception{
       	if(null!=connection){
       		if(!connection.isClosed()){
	       		if(!connection.getAutoCommit())
	       			connection.rollback();
       		}
    	}
//        if(getConnection()!=null && getConnection().isClosed()==false ){
//        	if(getConnection().getAutoCommit()==false){
//        		getConnection().rollback();
//        	}
//        	getConnection().close();
//            closeConnection();
//        }
    }
    
    
    //어떻게구현해야될까.. 루트에있는 data로 처리한다
    public Document execute(Document doc) throws Exception{
        Document newDoc=null;
        try{
            newDoc = executeQuery(doc);
        }catch (Exception e) {
            newDoc = executeUpdate(doc);
        }
        return newDoc;
    }
    public Document executeUpdate(Document doc) throws Exception{
        XMLparser xml = new XMLparser(doc);
        String id = xml.getString("//@id");
        int rc = executeMapUpdate(id, toAdapterMap(doc));
        Document newDoc = toDocument(rc);
        return newDoc;
    }
    
    public Document executeQuery(Document doc) throws Exception{
        XMLparser xml = new XMLparser(doc);
        String id = xml.getString("//@id");
        DBTResultSetContainer rc = executeMapQuery(id, toAdapterMap(doc));
        Document newDoc = toDocument(rc);
        return newDoc;
    }
    
    public Document toDocument(DBTResultSetContainer rc) throws Exception{
        XMLparser xml = new XMLparser();
        Document newDoc = xml.getDocument();
        Element rootElement = newDoc.createElement("table");
        newDoc.appendChild(rootElement);
        
        for (int i = 0; i < rc.size(); i++) {
            DBTResultRecord atRecord = rc.get(i);
            for (int j = 0; j < atRecord.size(); j++) {
                Element data = newDoc.createElement("data");
                rootElement.appendChild(data);
                
                Attr attr = newDoc.createAttribute(atRecord.getKey(j));
                attr.setValue(atRecord.get(j));
                data.setAttributeNode(attr);
         
            }
        }
        return newDoc;
    }
    public Document toDocument(int rc_cnt) throws Exception{
        XMLparser xml = new XMLparser();
        Document newDoc = xml.getDocument();
        Element rootElement = newDoc.createElement("table");
        newDoc.appendChild(rootElement);
        Element data = newDoc.createElement("data");
        rootElement.appendChild(data);
        Attr attr = newDoc.createAttribute("update");
        attr.setValue(String.valueOf(rc_cnt));
        data.setAttributeNode(attr);
        return newDoc;
    }
    

    public Map<String,Object>  toAdapterMap(Document doc) throws Exception{
        XMLparser xml = new XMLparser(doc);
        NodeList list = xml.getNodes("//data");
        
        HashMap<String,Object> param = new HashMap<String, Object>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
           HashMap<String,String> map =  xml.getAttribute(node);
           Set keys = map.keySet();
           Iterator<String> it =  keys.iterator();
           while(it.hasNext()){
               String key      = it.next();
               String value    = map.get(key);
               param.put(key, value);
//             System.out.println("key:"+key+"    value:"+value);  
           }
           //map.g
        }
        
        return param;
    }
//    public  AdapterMapBase<String,Object>  toAdapterMap(Document doc) throws Exception{
//        XMLparser xml = new XMLparser(doc);
//        NodeList list = xml.getNodes("//data");
//        
//        AdapterMapBase<String,Object> param = new AdapterMap<String, Object>();
//        for (int i = 0; i < list.getLength(); i++) {
//            Node node = list.item(i);
//           HashMap<String,String> map =  xml.getAttribute(node);
//           Set keys = map.keySet();
//           Iterator<String> it =  keys.iterator();
//           while(it.hasNext()){
//               String key      = it.next();
//               String value    = map.get(key);
//               param.add(key, value);
////             System.out.println("key:"+key+"    value:"+value);  
//           }
//           //map.g
//        }
//        
//        return param;
//    }
    
    public Connection getConnection() throws Exception{
        
        //오토커밋시 다른 커넥션으로 사용하면안되니깐.. 열려있고 오토커밋이 펄스인놈
        if(connection!=null && connection.isClosed()==false && connection.getAutoCommit()==false){
            return connection;
        }
        
        //없어지기전에 새로만들기전 무조건 옛날건 클로우즈
        if(connection!=null && connection.isClosed()==false){
            connection.close();
            connection=null;
        }
        
        //그외는 뭐든지 새롭게 커넥션
        connection = connectioncreator.getMakeConnection();
        logk.debug("GetConnection ConnectionCreator.getMakeConnection      Call  makeConnection AutoCommit("+connection.getAutoCommit()+")   settingAutoCommit("+autoCommit+")");
        
        //사용자가 셋팅한게있음 그걸로 셋팅 
        if(autoCommit!=null){
            connection.setAutoCommit(autoCommit);
        }
        
        return  connection;
    }
    
    
    @Override
    protected void finalize() throws Throwable {
        //없어지기전에 무조건 클로우즈
        if(connection!=null){
            try{
            connection.close();
            }catch (Exception e) {
            }
        }
        
        super.finalize();
    }
}
