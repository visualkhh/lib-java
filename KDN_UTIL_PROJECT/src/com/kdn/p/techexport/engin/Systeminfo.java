package com.kdn.p.techexport.engin;

public class Systeminfo {
    
//  <SYSTEM>
//  <NAME>충북</NAME>
//  <DBIP>100.21.100.43</DBIP>
//  <DBPORT>1526</DBPORT>
//  <DBSID>CBU2</DBSID>
//  <DBID>CBU2UPD</DBID>
//  <DBPASSWORD></DBPASSWORD>
//  <STARTYMD>20060101</STARTYMD>
//  <ENDYMD>20101231</ENDYMD>
//  <STEP>30</STEP>
//</SYSTEM>
    
    private String  NAME;
    private String  DBIP;
    private String  DBPORT;
    private String  DBSID;
    private String  DBID;
    private String  DBPASSWORD;
    private String STARTYMD;
    private String ENDYMD;
    private int STEP;
    
    private String SIGONG_PATH;
    private String JUNGONF_PATH;
    
    
    public String getNAME() {
        return NAME;
    }
    public void setNAME(String nAME) {
        NAME = nAME;
    }
    public String getDBIP() {
        return DBIP;
    }
    public void setDBIP(String dBIP) {
        DBIP = dBIP;
    }
    public String getDBPORT() {
        return DBPORT;
    }
    public void setDBPORT(String dBPORT) {
        DBPORT = dBPORT;
    }
    public String getDBSID() {
        return DBSID;
    }
    public void setDBSID(String dBSID) {
        DBSID = dBSID;
    }
    public String getDBID() {
        return DBID;
    }
    public void setDBID(String dBID) {
        DBID = dBID;
    }
    public String getDBPASSWORD() {
        return DBPASSWORD;
    }
    public void setDBPASSWORD(String dBPASSWORD) {
        DBPASSWORD = dBPASSWORD;
    }
    public String getSTARTYMD() {
        return STARTYMD;
    }
    public void setSTARTYMD(String sTARTYMD) {
        STARTYMD = sTARTYMD;
    }
    public String getENDYMD() {
        return ENDYMD;
    }
    public void setENDYMD(String eNDYMD) {
        ENDYMD = eNDYMD;
    }
    public int getSTEP() {
        return STEP;
    }
    public void setSTEP(int sTEP) {
        STEP = sTEP;
    }
    public String getSIGONG_PATH() {
        return SIGONG_PATH;
    }
    public void setSIGONG_PATH(String sIGONG_PATH) {
        SIGONG_PATH = sIGONG_PATH;
    }
    public String getJUNGONF_PATH() {
        return JUNGONF_PATH;
    }
    public void setJUNGONF_PATH(String jUNGONF_PATH) {
        JUNGONF_PATH = jUNGONF_PATH;
    }
    
}
