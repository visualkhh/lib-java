package khh.string.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {

    static public final String SET_UTF_8 = "UTF-8"; 
    static public final String SET_UTF_16 = "UTF-16";   
    static public final String SET_EUC_KR = "EUC-KR";   
    static public final String SET_8859_1 = "8859_1";   
    static public final String SET_MS949 = "MS949"; 
    static public final String SET_KSC5601 = "KSC5601"; 
    
    
    public static String urlDecode(String data, String decodetype) throws UnsupportedEncodingException{
        return  URLDecoder.decode(data,decodetype);
    }
    public static String urlEncode(String data, String encodetype) throws UnsupportedEncodingException{
        return  URLEncoder.encode(data,encodetype);
    }
    public static String stringCharSetConversion(String data,String ecodetype,String decodetype) throws UnsupportedEncodingException{
//      Charset          charset =  Charset.forName("UTF-8");
//      CharsetDecoder   decoder = charset.newDecoder();
//      CharsetEncoder   encoder = charset.newEncoder();
//      CharBuffer r = decoder.decode(ByteBuffer.wrap(msg.getBytes()));
//      r.toString();
      return new String(data.getBytes(ecodetype),decodetype );
  } 
    
    //스트링  스페이스바 제거
    public static String deleteAllSpace(String string){
        String s = string.replaceAll("\\p{Space}", "");
        s = s.replaceAll(" ", "");
        return s;
    }
    //스트링 문자사이  문자열 뺴오기 <USERID>test</USERID>   userid = substringBetween(word, "<USERID>", "</USERID>");   = test
    public static String substringBetween(String str, String open, String close) {
       if (str == null || open == null || close == null) {
          return null;
       }
       int start = str.indexOf(open);
       if (start != -1) {
          int end = str.indexOf(close, start + open.length());
          if (end != -1) {
             return str.substring(start + open.length(), end);
          }
       }
       return null;
   }
    
    //아래꺼 index나오게 바꿔야될듯.
    // String rex="^one cat.*.yard"; . 찍어서 붙쳐줘야함. 오리지널 문자로바꾸는건 \\. 이런식
    public static boolean isFind(String rex, String str) {
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(str);
        return m.find();
    }
    static public boolean  isMatches(String str,String regex){
    //  System.out.println(Utilities.isMatches(str, ".*R001.*"));
            boolean sw = false;
             try {
//               Pattern p = Pattern.compile(regex);
//               Matcher m= p.matcher(str);
//              if(m.matches() )
//              {
//                  return true;
//              }else{
//                  return false;
//              }
                  if (str.matches(regex)){
                      sw = true;
                  }else{
                      sw = false;
                  }

                } catch (PatternSyntaxException e) { // 정규식에 에러가 있다면
                    System.err.println(e);
                }
                return sw;

        }
    
    //아래  함수2개는-_- 왜만들어논지.... 우선가져옴..
    public static String patternFirstDelete(String str,char pettern , int nextPettern) {
        int count=0;
        int last=0;
        for(int i = 0 ;i< str.length();i++){
            if(str.getBytes()[i]==pettern){
                count++;
                if(count>=nextPettern)
                    break;
            }
            last++;
        }
        return str.substring(0,last);
        
    }
    public static String patternTailDelete(String str,char pettern , int nextPettern) {
        int count=0;
        int last=0;
        for(int i = str.length()-1 ;i >= 0;i--){
            last++;
            if(str.getBytes()[i]==pettern){
                count++;
                if(count>=nextPettern)
                    break;
            }
        }
        return str.substring(0,str.length()-last);
        
    }
    
    
    
    
    
    

    static public String replaceUL(String inputStr,String matchingStr, String replaceStr){
        return inputStr.replaceFirst("(?i)"+matchingStr, replaceStr);
    }
    
    static public String replaceAll(String inputStr,String[] matchingStrArr, String replaceStr){
        for(int i = 0 ; i < matchingStrArr.length;i++){
            inputStr =  replaceAll(inputStr,matchingStrArr[i], replaceStr);
        }
        return inputStr;
    }
    
    static public String replaceAll(String inputStr,ArrayList<String> matchingStrArr, String replaceStr){
        for(int i = 0 ; i < matchingStrArr.size();i++){
            inputStr =  replaceAll(inputStr,(String)matchingStrArr.get(i), replaceStr);
        }
        return inputStr;
    }
    
    static public String replaceAll(String inputStr,HashMap<String,String> matchingMap){

        Iterator iter = matchingMap.keySet().iterator();
        
        while(iter.hasNext()){
            String key =(String) iter.next();
//          String key2 =h_rex(key);
//          System.out.println(key+"  "+key2);
//          System.out.println(inputStr);
            inputStr =replaceAll(inputStr,key, (String) matchingMap.get(key));
        }
        
        return inputStr;
    }
    
    static public String replaceAll(String inputStr,String matchingStr,String replaceStr){
        if(inputStr==null)
            return null;
        
        String key  =regexMetaCharToEscapeChar(matchingStr);
        if(matchingStr==null || replaceStr==null || key==null){
            return inputStr;
        }
        
        return inputStr.replaceAll(key,replaceStr);
        
    }
    
    
    
    public static String regexMetaCharToEscapeChar(String h){
        String in = (String)h;
        in = in.replaceAll("\\\\", "\\\\\\\\");
        in = in.replaceAll("\\.", "\\\\\\.");
        in = in.replaceAll("\\?", "\\\\\\?");
        in = in.replaceAll("\\(", "\\\\\\(");
        in = in.replaceAll("\\)", "\\\\\\)");
        in = in.replaceAll("\\{", "\\\\\\{");
        in = in.replaceAll("\\[", "\\\\\\[");
        in = in.replaceAll("\\$", "\\\\\\$");
        in = in.replaceAll("\\^", "\\\\\\^");
        in = in.replaceAll("\\*", "\\\\\\*");
        in = in.replaceAll("\\+", "\\\\\\+");
        in = in.replaceAll("\\|", "\\\\\\|");
        return in;
    }
    public static String tagMetaCharToEscapeChar(String h){
        HashMap<String,String> map = new HashMap();
        map.put("&", "&#38;");
        h = replaceAll(h,map);
        map = new HashMap();
        map.put("<", "&lt;");
        map.put(">", "&gt;");
        map.put("(", "&#40;");
        map.put(")", "&#41;");
        map.put("#", "&#35;");
        map.put("'", "&#39;");
        map.put("\"", "&#quot;");
        map.put(" ", "&nbsp;");
        map.put("=", "&#61");
         h = replaceAll(h,map);
         return h;
     }
}
