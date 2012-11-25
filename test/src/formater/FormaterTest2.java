package formater;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormaterTest2 {
    public static void main(String[] args) {

        String format ="###0.#";
        double decimal=5.5;
        DecimalFormat df = new DecimalFormat(format); 
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(); 
//        dfs.setGroupingSeparator(',');// 구분자를 ,로 
//        df.setGroupingSize(3);//3자리 단위마다 구분자처리 한다. 
//        df.setDecimalFormatSymbols(dfs); 
        System.out.println( (df.format(decimal)).toString() ); 
  
    }
}
