package khh.string.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
/*
String a="01063989-094";
a = a.replaceAll("-","");

StringFormat sf ;
if(a.length()<11){
    sf = new StringFormat("###-###-####");
}else{
    sf = new StringFormat("###-####-####");
}

*/
public class StringFormat  extends Format{
//    public static final int TYPE_LEFT_ALIGN=1;
//    public static final int TYPE_RIGHT_ALIGN=2;
//    int typeAlign=TYPE_LEFT_ALIGN; 
    String matchPattern="#";
    String oddPattern="";
    String format=null;
    
    
    public StringFormat() {
        // TODO Auto-generated constructor stub
    }
    public StringFormat(String format ){
        this.format = format;
    }
    
    
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public String getMatchPattern() {
        return matchPattern;
    }
    public void setMatchPattern(String matchPattern) {
        this.matchPattern = matchPattern;
    }
    
    
    public String getOddPattern() {
        return oddPattern;
    }
    public void setOddPattern(String oddPattern) {
        this.oddPattern = oddPattern;
    }
    public String format(String data){
        
        String format  = getFormat();
        String matchpattern = getMatchPattern();
        String oddpattern = getOddPattern();
        for (int i = 0;  i < data.length() && i< format.length();  i++) {
            format =  format.replaceFirst(matchpattern, data.charAt(i)+"");
        }
        
        format = format.replaceAll(matchpattern, oddpattern);
        
//        data,getTypeAlign()
        
        
        return format;
    }
    
    
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        // TODO Auto-generated method stub
        return null;
    }

}
