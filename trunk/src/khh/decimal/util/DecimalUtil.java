package khh.decimal.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class DecimalUtil {

public static String decimal(Double data,int pow,int dot){
    return decimal(new BigDecimal(data.toString()),pow,dot);
}
public static String decimal(Integer data,int pow){
    return decimal(new BigDecimal(data.toString()),pow,0);
}


public static String decimal(BigDecimal data,int pow, int dot){
       data=decimal(data,pow);
       return decimalFormat(data, dot);
}
public static BigDecimal decimal(BigDecimal data,int dotmove){
    if(dotmove<0){
           int num=0;
           for (int i = 0; i > dotmove; i--) {
               num++;
           }
           int dotnum = (int)Math.pow(10,num);
           data=data.divide(new BigDecimal(dotnum));
       }else{
           int pownum = (int)Math.pow(10,dotmove);
           data=data.multiply(new BigDecimal(pownum));
       }
    return data;
}






public static String decimalFormat(Double decimal,int dotSize){
    return decimalFormat(decimal.toString(),dotSize,false);
}
public static String decimalFormat(Integer decimal){
    return decimalFormat(decimal.toString(),0,false);
}


public static String decimalFormat(BigDecimal decimal,int dotSize){
    return decimalFormat(decimal.toString(),dotSize,false);
}
public static String decimalFormat(BigDecimal decimal,int dotSize,boolean wantfixsize){
    return decimalFormat(decimal.toString(),dotSize,wantfixsize);
}


public static String decimalFormat(String decimal,int dotSize){
    return decimalFormat(decimal, dotSize,false);
}
public static String decimalFormat(String decimal,int dotSize,boolean wantfixsize) 
{  
    String format="#,##0";
    for(int  i = 0 ; i < dotSize ; i++){
        if(i==0){
            format+=".";
        }
        if(wantfixsize)
            format+="0";
        else
            format+="#";
    }
//    String format = "#,###.00"; 
//    String moneyString = new Double(decimal).toString(); 
//
//    DecimalFormat df = new DecimalFormat(format); 
//    DecimalFormatSymbols dfs = new DecimalFormatSymbols(); 
//
//    dfs.setGroupingSeparator(',');// 구분자를 ,로 
//    df.setGroupingSize(3);//3자리 단위마다 구분자처리 한다. 
//    df.setDecimalFormatSymbols(dfs); 
//
//    return (df.format(Double.parseDouble(moneyString))).toString(); 
    return decimalFormat(format,decimal);
} 


public static String decimalFormat(String format,String decimal) 
{  
    return decimalFormat(format,new BigDecimal(decimal));
} 
public static String decimalFormat(String format,BigDecimal decimal) 
{  

    DecimalFormat df = new DecimalFormat(format); 
    DecimalFormatSymbols dfs = new DecimalFormatSymbols(); 

//    dfs.setGroupingSeparator(',');// 구분자를 ,로 
//    df.setGroupingSize(3);//3자리 단위마다 구분자처리 한다. 
//    df.setDecimalFormatSymbols(dfs); 
    return (df.format(decimal)).toString(); 
} 
}
