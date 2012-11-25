package stringtest;

import com.kdn.util.format.StringFormat;

public class StringFormatTest2 {

    public static void main(String[] args) {
//        // Create a pattern for our MessageFormat object to use.
//        String pattern = "I bought {0,number,#} " +
//            "apples for {1,number,currency} " +
//            "on {2,date,dd-MMM-yyyy}.";
//
//        // Specify which formatters are to be used for each
//        // of the placeholders in the pattern above.
//        Format[] formats = { new DecimalFormat("#"),
//            NumberFormat.getCurrencyInstance(),
//            new SimpleDateFormat("MMM/dd/yyyy") };
//
//        // Create values to populate the position in the pattern.
//        Object[] values = { new Integer(5), new Double(7.53),
//            new Date("04-Jul-2004") };
//
//        // Create a MessageFormat object and apply the pattern
//        // to it.
//        MessageFormat mFmt = new MessageFormat(pattern);
//        mFmt.setFormats(formats);
//
//        // Print out the pattern being used for formatting
//        // and the formatted output.
//        System.out.println(mFmt.toPattern());
//        System.out.println(mFmt.format(pattern, values));
//        
        String a="01063989-094";
        a = a.replaceAll("-","");
       
        StringFormat sf ;
        if(a.length()<11){
            sf = new StringFormat("###-###-####");
        }else{
            sf = new StringFormat("###-####-####");
        }
        
//        sf.setMatchPattern("a");
        //sf.setOddPattern("_");
        
        System.out.println( sf.format(a) );
        
        
    }

}
