package com.kdn.p.techexport.engin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.kdn.util.db.ConnectionUtil;
import com.kdt.util.xml.XMLparser;

public class ext {
    public static void main(String[] args) throws SAXException, IOException, XPathExpressionException, NoClassDefFoundError, ClassNotFoundException, SQLException {
        
//        XMLparser parser = new XMLparser("L:\\회사KDN-김현하\\배전일반\\공통\\계정비번\\지사계정.xml");
        XMLparser parser = new XMLparser("C:\\추출\\지사계정.xml");
        String xmlpath="//STD/SYSTEM";
        int size =  parser.getInt("count("+xmlpath+")");
        
        
        
        
//        <SYSTEM>
//        <NAME>충북</NAME>
//        <DBIP>100.21.100.43</DBIP>
//        <DBPORT>1526</DBPORT>
//        <DBSID>CBU2</DBSID>
//        <DBID>CBU2UPD</DBID>
//        <DBPASSWORD></DBPASSWORD>
//        <STARTYMD>20060101</STARTYMD>
//        <ENDYMD>20101231</ENDYMD>
//        <STEP>30</STEP>
//    </SYSTEM>
        
        
        for (int i = 1; i <= size; i++) {
            Systeminfo s = new Systeminfo();
            String subxmlpath = (xmlpath+"["+i+"]");
            String NAME=parser.getString(subxmlpath+"/NAME");
            String DBIP=parser.getString(subxmlpath+"/DBIP");
            String DBPORT=parser.getString(subxmlpath+"/DBPORT");
            String DBSID=parser.getString(subxmlpath+"/DBSID");
            String DBID=parser.getString(subxmlpath+"/DBID");
            String DBPASSWORD=parser.getString(subxmlpath+"/DBPASSWORD");
            String STARTYMD=parser.getString(subxmlpath+"/STARTYMD");
            String ENDYMD=parser.getString(subxmlpath+"/ENDYMD");
            Integer STEP=parser.getInt(subxmlpath+"/STEP");
            
            s.setNAME(NAME);
            s.setDBIP(DBIP);
            s.setDBPORT(DBPORT);
            s.setDBSID(DBSID);
            s.setDBID(DBID);
            s.setDBPASSWORD(DBPASSWORD);
            s.setSTARTYMD(STARTYMD);
            s.setENDYMD(ENDYMD);
            s.setSTEP(STEP);
            
            //System.out.println(NAME+" "+DBIP+" "+DBPORT+" "+DBSID+" "+DBID+" "+DBPASSWORD+" "+STARTYMD+" "+ENDYMD+" "+STEP );
            
//            STD std =  new STD(s);
//            std.start();
            
            //break;
        }
        
//        
//        
//        Connection con = ConnectionUtil.getConnection(ConnectionUtil.ORACLE, ip, port, sid, id, passwd);
//        PreparedStatement a = con.prepareStatement("select * from tab");
//        ResultSet r = a.executeQuery();
//        
//        while(r.next()){
//            System.out.println(r.getString("TNAME"));
//        }
        
    }
}
