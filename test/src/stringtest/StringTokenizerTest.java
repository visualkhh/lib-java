package stringtest;

import java.util.StringTokenizer;

public class StringTokenizerTest {
    public static void main(String[] args) {
        String data="GET / HTTP/1.1";
        StringTokenizer tok = new StringTokenizer(data);
        System.out.println(tok.nextToken(" "));
        System.out.println(tok.nextToken(" "));
        System.out.println(tok.nextToken(" "));
        System.out.println(tok.nextToken(" "));
        System.out.println(tok.nextToken(" "));
//        System.out.println(tok.nextToken());
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
