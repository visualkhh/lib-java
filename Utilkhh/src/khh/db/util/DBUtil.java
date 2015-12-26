package khh.db.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import khh.collection.StandardArrayList;
import khh.db.terminal.resultset.DBTResultRecord;
import khh.db.terminal.resultset.DBTResultSetContainer;
import khh.property.util.PropertiesUtil;
import khh.property.util.PropertyUtil;
import khh.sort.SortUtil;
import khh.sort.comparator.CompareBase;
import khh.sort.comparator.CompareIntegerStandard;
import khh.std.Standard;
import khh.std.adapter.AdapterMapBase;
import khh.string.util.StringUtil;

public class DBUtil {

    public static ArrayList setPreparedStatementValueName(String sql_naming,AdapterMapBase<String,Object> param) throws Exception{
        StandardArrayList<Integer,Object> datafull = new StandardArrayList<Integer,Object>();
        for (int i = 0; i < param.size(); i++) {
            String findstr="#"+param.getKey(i)+"#";
            ArrayList<Integer> size = StringUtil.getFindIndex(findstr, sql_naming);
           for (int j = 0; j < size.size(); j++) {
             datafull.add(new Standard<Integer, Object>(size.get(j),param.get(i)));
           }
           sql_naming = StringUtil.replaceAll(sql_naming, findstr, "?");
        }
        //select * from w where a=#a#...
        SortUtil.sort(datafull, new CompareIntegerStandard(CompareBase.TYPE_ASC));
        return datafull;
    }
    public static PreparedStatement setPreparedStatementValue(PreparedStatement psmt,ArrayList param) throws Exception{
        
        int addindex=0;
        for (int index = 0; index < param.size(); index++) {
            addindex=(index+1);
            Object value  = param.get(index);
            
            if(value instanceof Array){
                psmt.setArray( addindex,  (Array)value)  ;
            }else if(value instanceof InputStream){
            psmt.setAsciiStream( addindex, (InputStream) value)  ;
           // }else if(arg1 instanceof eger){
           // psmt.setAsciiStream( arg0, InputStream arg1,  arg2)  ;
           // }else if(arg1 instanceof eger){
            //psmt.setAsciiStream( arg0, InputStream arg1, long arg2)  ;
            }else if(value instanceof BigDecimal){
            psmt.setBigDecimal( addindex, (BigDecimal) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setBinaryStream( arg0, InputStream arg1)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setBinaryStream( arg0, InputStream arg1,  arg2)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setBinaryStream( arg0, InputStream arg1, long arg2)  ;
            }else if(value instanceof Blob){
            psmt.setBlob( addindex, (Blob) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setBlob( arg0, InputStream arg1)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setBlob( arg0, InputStream arg1, long arg2)  ;
            }else if(value instanceof Boolean){
            psmt.setBoolean( addindex, (Boolean) value)  ;
            }else if(value instanceof Byte){
            psmt.setByte( addindex, (Byte) value)  ;
            }else if(value instanceof byte[]){
            psmt.setBytes( addindex, (byte[]) value)  ;
            }else if(value instanceof Reader){
            psmt.setCharacterStream( addindex, (Reader) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setCharacterStream( arg0, Reader arg1,  arg2)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setCharacterStream( arg0, Reader arg1, long arg2)  ;
            }else if(value instanceof Clob){
            psmt.setClob( addindex, (Clob) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setClob( arg0, Reader arg1)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setClob( arg0, Reader arg1, long arg2)  ;
            }else if(value instanceof java.sql.Date){
            psmt.setDate( addindex, (java.sql.Date) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setDate( arg0, Date arg1, Calendar arg2)  ;
            }else if(value instanceof Double){
            psmt.setDouble( addindex, (Double) value)  ;
            }else if(value instanceof Float){
            psmt.setFloat( addindex, (Float) value)  ;
            }else if(value instanceof Integer){
            psmt.setInt( addindex,  (Integer)value)  ;
            }else if(value instanceof Long){
            psmt.setLong( addindex, (Long) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setNCharacterStream( arg0, Reader arg1)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setNCharacterStream( arg0, Reader arg1, long arg2)  ;
            }else if(value instanceof NClob){
            psmt.setNClob( addindex, (NClob) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setNClob( arg0, Reader arg1)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setNClob( arg0, Reader arg1, long arg2)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setNString( arg0, String arg1)  ;
            
//            }else if(arg1 instanceof Null){
//            psmt.setNull( arg0,  arg1)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setNull( arg0,  arg1, String arg2)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setObject( arg0, Object arg1,  arg2)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setObject( arg0, Object arg1,  arg2,  arg3)  ;
            }else if(value instanceof Ref){
            psmt.setRef( addindex, (Ref) value)  ;
            }else if(value instanceof RowId){
            psmt.setRowId( addindex, (RowId) value)  ;
            }else if(value instanceof SQLXML){
            psmt.setSQLXML( addindex, (SQLXML) value)  ;
            }else if(value instanceof Short){
            psmt.setShort( addindex, (Short) value)  ;
            }else if(value instanceof String){
            psmt.setString( addindex, (String) value)  ;
            }else if(value instanceof Time){
            psmt.setTime( addindex, (Time) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setTime( arg0, Time arg1, Calendar arg2)  ;
            }else if(value instanceof Timestamp){
            psmt.setTimestamp( addindex, (Timestamp) value)  ;
//            }else if(arg1 instanceof eger){
//            psmt.setTimestamp( arg0, Timestamp arg1, Calendar arg2)  ;
            }else if(value instanceof URL){
            psmt.setURL( addindex, (URL) value)  ;
//            }else if(arg1 instanceof InputStream){
//            psmt.setUnicodeStream( arg0, InputStream arg1,  arg2) ;
            }else if(value instanceof Object){
                psmt.setObject( addindex, (Object) value)  ;
            }

        }
        return psmt;
    }
    
    public static DBTResultSetContainer makeResultSetContainer(ResultSet rset) throws SQLException, Exception {
        ResultSetMetaData   rsmd = rset.getMetaData();
        DBTResultSetContainer rstc = new DBTResultSetContainer();
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                rstc.addColumnName(rsmd.getColumnName(i));
            }
            Long index = (long) 0 ;
            while(rset.next()){
                DBTResultRecord row = new DBTResultRecord();
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
//                    row.add(rstc.getColumnName(i-1), rset.getString(i));
                    row.add(rsmd.getColumnName(i), rset.getString(i));
                }
                rstc.add(index++, row);
            }
        return rstc;
    }
    
    public static String getXMLTagString(DBTResultSetContainer dbtResultSetContiner) throws SQLException, Exception {
    	StringBuffer rstr = new StringBuffer();
    	String enter = PropertyUtil.getSeparator();
    	rstr.append("<TABLE>"+enter);
    	
    	for (int i = 0; i < dbtResultSetContiner.size(); i++) {
    		 DBTResultRecord row = dbtResultSetContiner.get(i);
    		 rstr.append("<RECODE>");
    		 for (int j = 0; j < row.size(); j++) {
    			 rstr.append("<"+row.getKey(j)+"><![CDATA["+row.getString(j)+"]]></"+row.getKey(j)+">");
    		 }
    		 rstr.append("</RECODE>"+enter);
		}
    	rstr.append("</TABLE>");
        return rstr.toString();
    }
    
    // table 테그
    public static String getTableHTMLTag(ResultSet rs) throws SQLException {
        ResultSetMetaData rsm = rs.getMetaData();
        String returnString = "";
        returnString += "<table border='1'>";
        returnString += "           <tr> ";
        int columncnt = rsm.getColumnCount();
        for (int i = 1; i <= columncnt; i++) {
            returnString += "<td>" + rsm.getColumnName(i) + "</td> ";
        }
        while (rs.next()) {
            returnString += "<tr>";
            for (int i = 1; i <= columncnt; i++) {
                returnString += "<td>" + rs.getString(i) + "</td>";
            }
            returnString += "</tr>";
        }
        returnString += "</tr>";
        return returnString += "</table>";
    }
    
    
    // table 테그
    public static String getTableTag(ResultSet rs) throws SQLException {
        ResultSetMetaData rsm = rs.getMetaData();
        String returnString = "";
        int columncnt = rsm.getColumnCount();
        
        returnString += "<table>";
        while (rs.next()) {
            returnString+="<recode>";
            for (int i = 1; i <= columncnt; i++) {
                    returnString += "   <"+rsm.getColumnName(i)+">" + rs.getString(i)+ "</"+rsm.getColumnName(i)+"> ";
            }
            returnString += "</recode>";
        }
        return returnString += "</table>";
    }
    
    
    
}
