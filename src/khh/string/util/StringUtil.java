package khh.string.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import khh.conversion.util.ConversionUtil;
import khh.std.adapter.Adapter_Std;

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
//    /**
//     * <p>특정문자로 둘러?맛? 알맹이 문자 얻기</p>
//	 * <p>ex) getLapOutText("<!Text!>", "<!", "!>") --> Text</p>
//     *
//     * @param    대상 문자열.
//	 * @param    제거될 시작문자열.
//	 * @param    제거될 끝문자열.
//	 * @param    몇번제 시작문자열 부터 작업할 것인지 Index (1부터 시작).
//     * @return   알맹이 문자열.
//     */
//     public static String getLapOutText(String str, String startTag, String endTag, int index) {
//        String  l_String        =   str;
//        int     l_posStartTag   =   0;
//        int     l_lookUpPoint   =   0;
//        int     l_findCnt       =   0;
//        //---------------------------------------------------------------------
//        // 시작 Tag 위치 알아 내기
//        //---------------------------------------------------------------------
//        do {
//			//-- 시작 Tag의 위치 알아 내기
//            l_posStartTag = l_String.indexOf(startTag, l_lookUpPoint);
//			//-- 찾은 수 증가
//            l_findCnt++;
//			//-- 다음 검색 위치조정
//            l_lookUpPoint = l_posStartTag + 1;
//			//-- 찾은 수가 찾을 번째 수와 같을때 까지 Loop
//        }   while (l_findCnt < index && l_posStartTag >= 0 );
//
//        if  (l_posStartTag < 0)  return "";
//        //---------------------------------------------------------------------
//        // 종료 Tag의 위치 알아 내기
//        //---------------------------------------------------------------------
//        int l_posEndTag = l_String.indexOf(endTag, l_posStartTag);
//
//        if  ( l_posEndTag < 0 ) return "";
//
//        //---------------------------------------------------------------------
//        // Column Name 가져오기
//        //---------------------------------------------------------------------
//        l_String = l_String.substring(l_posStartTag + startTag.length(), l_posEndTag).trim();
//
//        return l_String;
//    }
//     
    
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
        map.put("&", "&#38;"); //buf.append("&amp;");
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
    
    //finger
    public static String SQLInjectionFilter(String sInvalid)
    {

        String sValid = sInvalid;

        if (sValid == null || sValid.equals(""))
            return "";

        sValid = sValid.replaceAll("OR", "");
        sValid = sValid.replaceAll("AND", "");
        sValid = sValid.replaceAll("or", "");
        sValid = sValid.replaceAll("and", "");
        sValid = sValid.replaceAll(";", "");
        sValid = sValid.replaceAll(":", "");
        sValid = sValid.replaceAll("--", "");
        sValid = sValid.replaceAll("-", "");
        sValid = sValid.replaceAll("`","");
        sValid = sValid.replaceAll("\'","");
        sValid = sValid.replaceAll("\"","");

        return sValid;
    }
    
    
    public static int getMatchingCount(String matching_keyword,String full_str){
    		int count = 0;
    		int index = 0;

    		while( (index = full_str.indexOf(matching_keyword, index)) > 0 ) {
    		count++;
    		index += matching_keyword.length();
    		}
    		return count;
    }
    
    
    
    public static String concatenate(Map<String,String> map,String divisionString){
    	return concatenate(map,"=",divisionString);
    }
    public static String concatenate(Map<String,String> map,String assignmentstring,String divisionString){
    	Iterator it = map.keySet().iterator(); 
		StringBuffer buffer = new StringBuffer();
		
		while(it.hasNext()) {
			String key   = it.next().toString();
			String value = map.get(key).toString();
			buffer.append(key).append(assignmentstring).append(value);
			//buffer.append(key).append("=").append(value);
			if (it.hasNext())
				buffer.append(divisionString);
				//buffer.append("&");
		}
		return buffer.toString();
    }

  public static String concatenate(Adapter_Std<String,String> map,String divisionString) throws Exception{
		
    	return concatenate(map,"=",divisionString);
    }
    public static String concatenate(Adapter_Std<String,String> map,String assignmentstring,String divisionString) throws Exception{
    	StringBuffer buffer = new StringBuffer();
		for(int i = 0 ; i < map.size() ; i ++ ) {
				if (i!=0)
				buffer.append(divisionString);
			
			String key   =	map.getKey(i);
			String value = 	map.get(key);
			buffer.append(key).append(assignmentstring).append(value);
			//buffer.append(key).append("=").append(value);
				//buffer.append("&");
		}
		return buffer.toString();
    }
    
    
    
    
    /**
     * <p>문자열을 지정 구분자로 나눈 뒤 배열값으로 반환. (join과 반대의 기능을 함)</p>
     *
     * @param    대상 문자열.
     * @param    구분자.
     * @return   문자열 토큰배열.
     */
    public final static String[] split(String s, String delimiter) {

        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(s, delimiter);
        while(st.hasMoreTokens())
            v.addElement(st.nextToken());

        String array[] = new String[v.size()];
        v.copyInto(array);

        return(array);
    }



    /**
     * <p>문자열 토큰배열에 구분자를 넣어서 합친 문자열 반환. (spilit와 반대의 기능을 함)</p>
     *
     * @param    문자열 토큰배열.
     * @param    구분자.
     * @return   합쳐진 문자열.
     */
    public final static String join(String array[], String delimiter) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < array.length; i++){
            if(i > 0) sb.append(delimiter);
                sb.append(array[i]);
        }
        return(sb.toString());
    }
    
    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 "..."를 덧붙여 보여줌.</p>
     *
     * @param    원본 문자열.
     * @param    잘라야 될 길이.
     * @return   변경된 문자열.
     */
    public static String fixLength(String s, int limit) {
        return fixLength(s, limit, "...");
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여줌.</p>
     *
     * @param    원본 문자열.
     * @param    잘라야 될 길이.
     * @param    덧붙일 문자열.
     * @return   변경된 문자열.
     */
    public static String fixLength(String s, int limit, String postfix) {
        char[] charArray = s.toCharArray();

        if (limit >= charArray.length)
            return s;
        return new String( charArray, 0, limit ).concat( postfix );
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 "..."를 덧붙여 보여줌.</p>
     * <p>단 fixLength와의 차이는 제한길이의 기준이 char가 아니라 byte로
     * 처리함으로해서 한글문제를 해결할수 있다.</p>
     *
     * @param    원본 문자열.
     * @param    잘라야 될 길이 (byte).
     * @return   변경된 문자열.
     */
    public static String fixUnicodeLength(String s, int limitByte) {
        return fixUnicodeLength(s, limitByte, "...");
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여줌.</p>
     *
     * @param    원본 문자열.
     * @param    잘라야 될 길이 (byte).
     * @param    덧붙일 문자열.
     * @return   변경된 문자열.
     */
    public static String fixUnicodeLength(String s, int limitByte, String postfix) {

        // Cut empty string
        s = s.trim();

        byte[] outputBytes = s.getBytes();
        String output = null;

        if (outputBytes.length <= limitByte) {
            output = s;
        }else {
            output = new String( outputBytes, 0, limitByte );

            if(output.length() == 0)
                output = new String( outputBytes, 0, limitByte-1 );
            //output.concat( postfix );
            //Minkoo : upper code do not work. I don't know exatly why...
            output += postfix;
        }
        return output;
    }
    
    
    
    
    public static String lpad (String padStr,int totLength,String src){
    	return pad("L",padStr,totLength,src);
    }
    public static String rpad (String padStr,int totLength,String src){
    	return pad("R",padStr,totLength,src);
    }
    /**
     * <p>원본 문자열에 지정한 문자열/자리수/방향 으로 문자열을 첨가.</p>
     * ex) paddingStr( "L", "0", 7,"1234")   ==>   return "0001234"
     *
     * @param    원본 문자열.
     * @param    첨가 방향.
     * @param    첨가 문자.
     * @param    전체 문자열 length.
     * @return   변경된 문자열.
     */
    public static String pad( String direction, String padStr, int totLength,String src) {

        String convStr = null;

        if("".equals(ConversionUtil.nullToString(src,""))) {  // 원본 분자열이 null 이거나  ""이면  ""을 넘김.
            return convStr = "";
        }else if(!(src.length() < totLength)) {  // 원본 문자열이 전체 문자열 크기보다 같거나 작다면 원본 반환.
            return convStr = src;
        }else if("L:;:;R".indexOf(direction) < 0) { // pad 방향값이 L / R 이 아닐때 원본 반환.
            return convStr = src;
        }else if(ConversionUtil.nullToString(src,"").length() != 1) { // 첨가 문자열 길이가 0이거나 2이상일  때 원본 반환.
            return convStr = src;
        }

        try {
            int gapSize = totLength - src.length();
            String tempStr = "";
            for(int i=0; i < gapSize; i ++) {
                tempStr += padStr;
            }

            if("L".equals(direction)) {
                convStr = tempStr + src;
            }else{
                convStr = src + tempStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convStr;
    }
    
    
}
