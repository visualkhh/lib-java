package khh.string.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import khh.std.adapter.AdapterMapBase;


public class StringTokenizer extends java.util.StringTokenizer{

	private String token=" \t\n\r\f";
	private String concat="=";
	public StringTokenizer(String str, String delim, boolean returnDelims){
		super(str, delim, returnDelims);
		this.token = delim;
	}
	public StringTokenizer(String str, String delim){
		super(str, delim);
		this.token = delim;
	}
	public StringTokenizer(String delim){
		super("",delim);
		this.token=delim;
	}
	public StringTokenizer(){
		this(" \t\n\r\f");
	}
	
	@Override
	public String nextToken(String delim){
		this.token = delim;
		return super.nextToken();
	}
	

	public String makeString(LinkedHashMap  param){
		return makeString((HashMap) param);
	}
	
	public String makeString(HashMap param){
		Iterator iter = param.keySet().iterator();
		StringBuffer buf = new StringBuffer();
		boolean isFirst=true;
		
		while(iter.hasNext()){
			if(isFirst==false){
				buf.append(getToken());
			}
			
			Object key = iter.next();
			buf.append(key+getConcat()+param.get(key));
			isFirst=false;
		}
		return buf.toString();
	}
	public String makeKeyString(HashMap param){
		Iterator iter = param.keySet().iterator();
		StringBuffer buf = new StringBuffer();
		boolean isFirst=true;
		
		while(iter.hasNext()){
			if(isFirst==false){
				buf.append(getToken());
			}
			
			Object key = iter.next();
			buf.append(key);
			isFirst=false;
		}
		return buf.toString();
	}
	public String makeValueString(HashMap param){
		Iterator iter = param.keySet().iterator();
		StringBuffer buf = new StringBuffer();
		boolean isFirst=true;
		
		while(iter.hasNext()){
			if(isFirst==false){
				buf.append(getToken());
			}
			
			Object key = iter.next();
			buf.append(param.get(key));
			isFirst=false;
		}
		return buf.toString();
	}
	
	
	
	
	
	public String makeString(AdapterMapBase param) throws Exception{
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < param.size(); i++){
			if(i!=0){
				buf.append(getToken());
			}
			buf.append(param.getKey(i)+getConcat()+param.get(i));
		}
		return buf.toString();
	}
	
	
	public String makeKeyString(AdapterMapBase param){
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < param.size(); i++){
			if(i!=0){
				buf.append(getToken());
			}
			buf.append(param.getKey(i));
		}
		return buf.toString();
	}
	public String makeValueString(AdapterMapBase param) throws Exception{
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < param.size(); i++){
			if(i!=0){
				buf.append(getToken());
			}
			buf.append(param.get(i));
		}
		return buf.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String makeString(ArrayList param){
		return makeString((List)param);
	}
	public String makeString(List param){
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < param.size(); i++){
			if(i!=0){
				buf.append(getToken());
			}
			buf.append(param.get(i));
		}
		
		return buf.toString();
	}
	
	
	
	private String getConcat(){
		return concat;
	}
	private void setConcat(String concat){
		this.concat = concat;
	}
	private String getToken(){
		return token;
	}
	

}
