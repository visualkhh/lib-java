package josontest;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonTest {
    public static void main(String[] args) {
//        JSONValue jv = new JSONValue();
//        
//        js.add("aa");
//        System.out.println(js.toString());
//        
//        
//        JSONParser p = new JSONParser();
        
    	
    	
    	
        JSONArray js                 = new JSONArray();
        js.add("ab");
        js.add("ac");
        js.add("ad");
        js.add("ae");
        
        System.out.println(js.toString());
        
        
        
        
        
        
        
        JSONObject jo = new JSONObject();
        jo.put("aa", "aaaaa");
        jo.put("cell",js);
        jo.put("aav", "aaaaa");
        System.out.println(jo.toString());
        
        
        
        System.out.println(jo.get("aa").toString());
        
        JSONObject jo2 = new JSONObject();
        JSONParser parser = new JSONParser();
        
        Reader reader = new Reader() {
			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public void close() throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
        parser.parse("a");
        
//String a="aaab,bbb,sdfdf,fff";

//        if(null=="Stri"){
//        if("Stri"==null){
       
        
//        String gradestr     =null;
//        String[] grade      = gradestr==null?new String[]{}:gradestr.split("\\,");
//        System.out.println(grade.length);
//        for (int i = 0; i < grade.length; i++) {
//            System.out.println("hh"+grade[i]);
//        }
    }
}
