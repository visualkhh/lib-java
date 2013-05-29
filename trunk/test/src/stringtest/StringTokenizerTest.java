package stringtest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class StringTokenizerTest {
    public static void main(String[] args) {
    	String[] g = "Host: 122233.33.33.55:555".split(": ");
    	System.out.println(g[0]);
    	System.out.println(g[1]);
    	
    	
    	
//        String data="GET /* HTTP /* 1.1";
//        StringTokenizer tok = new StringTokenizer(data,"/*");
////        tok.
//        while(tok.hasMoreTokens()){
//        	System.out.println(tok.nextToken()+"  "+tok.countTokens());
//        }
//        System.out.println(tok.nextToken(" "));
//        System.out.println(tok.nextToken(" "));
//        System.out.println(tok.nextToken(" "));
//        System.out.println(tok.nextToken(" "));
//        System.out.println(tok.nextToken());
        
//        Object a= new String("a");
        
    	khh.string.token.StringTokenizer  hhkTok = new khh.string.token.StringTokenizer("\r\n");
        ArrayList ggg = new ArrayList();
        ggg.add("aa");
        ggg.add("aba");
        ggg.add("aca");
        ggg.add("ada");
        System.out.println(hhkTok.makeString(ggg));
        
        
//        
//        khh.string.token.StringTokenizer hhkTok2 = new khh.string.token.StringTokenizer("vv,aa",",");
//        LinkedHashMap  h = new LinkedHashMap ();
//        h.put("a","aa");
//        h.put("b","bb");
//        h.put("c","cc");
//        h.put("d",1);
//        System.out.println(hhkTok.makeString(h));
//        hhkTok2.nextToken();
//        hhkTok2.nextToken();
//        hhkTok2.nextToken();
//        hhkTok2.nextToken();
    }
    
    private String getValue(String origin, String key){
        for(StringTokenizer keyValues = new StringTokenizer(origin, "&");
            keyValues.hasMoreTokens();)
        {
            StringTokenizer keyValue = new StringTokenizer(keyValues.nextToken(), "=");
            String _key = keyValue.nextToken();
            if(_key.equals(key)){
                return keyValue.nextToken();                // value (key에 해당하는)
            }// end fi
        }// end for
        return null;
    }
}
