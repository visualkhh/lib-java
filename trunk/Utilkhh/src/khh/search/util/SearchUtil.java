package khh.search.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

import khh.conversion.util.ConversionUtil;
import khh.string.util.StringUtil;

public class SearchUtil {
    static public String[] isMatches(String inputStr,String[] matchingStrArr){
        ArrayList mats= new ArrayList();
        for (int i = 0; i < matchingStrArr.length; i++)
        {
            mats.add(matchingStrArr[i]);
        }
        ArrayList  r = isMatches(inputStr, mats);
        String[] ra = new String[r.size()];
        for (int i = 0; i < r.size(); i++)
        {
            ra[i]=(String)r.get(i);
        }
        return ra;
    }
    
    
    
    static public ArrayList isMatches(String inputStr,ArrayList<String> matchingStrArr){
        ArrayList mats= new ArrayList();
        for (int i = 0; i < matchingStrArr.size(); i++)
        {
            String in =StringUtil.regexMetaCharToEscapeChar( (String)matchingStrArr.get(i));
            if(isMatches(inputStr,".*"+in+".*"))
                mats.add(matchingStrArr.get(i));
            
        }
        return mats;
    }
    
    
    static private boolean  isMatches(String str,String regex){
        if(str==null || regex == null)
            return false;
        
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
                 
//             str = eval(str);
                   HashMap<String,String> m = new HashMap();
                   m.put("\r","");
                   m.put("\n","");
                   m.put("\t","");
                   str  = StringUtil.replaceAll(str,m);
                     
                  if (str.matches(regex)){
                      sw = true;
                  }else{
                      sw = false;
                  }

                } catch (PatternSyntaxException e) { 
                    System.err.println(e);
                }
                return sw;
        }
}
