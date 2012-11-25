package utiltest;

import java.util.ArrayList;

public class JsonTest {
    public static void main(String[] args) {
//        JSONValue jv = new JSONValue();
//        
//        js.add("aa");
//        System.out.println(js.toString());
//        
//        
//        JSONParser p = new JSONParser();
        
//        JSONArray js                 = new JSONArray();
//        js.add("ab");
//        js.add("ac");
//        js.add("ad");
//        js.add("ae");
//        
//        System.out.println(js.toString());
//        
//        JSONObject jo = new JSONObject();
//        jo.put("aa", "aaaaa");
//        jo.put("cell",js);
//        System.out.println(jo.toString());
        
//String a="aaab,bbb,sdfdf,fff";

//        if(null=="Stri"){
//        if("Stri"==null){
        String gradestr     =null;
        String[] grade      = gradestr==null?new String[]{}:gradestr.split("\\,");
        System.out.println(grade.length);
        for (int i = 0; i < grade.length; i++) {
            System.out.println("hh"+grade[i]);
        }
    }
}
