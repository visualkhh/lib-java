package com.math;

import khh.string.util.StringUtil;

public class MathTest {
    public static void main(String[] args) {

       /* double s =0;
        double e =100;
        
        double p=50;
        
        double size=40;
        
        
        double lg = p-size/2;
        double rg = p+size/2;
        
        System.out.println(lg);
        System.out.println(rg);
        
        int aa =04;
        String a = String.format("b_w:%d  b_h:%d  w_w: %d  w_h: %d ",  aa, aa,aa,aa);
        System.out.println(a);
        */
        String format ="2012-09-29_21:31:43,437 [D] %c(%f)(line %n)  >>  %m   %e %r";
        
        format = format.replaceAll("%c",StringUtil.regexMetaCharToEscapeChar("AnyPangHack$1") );
        System.out.println(format);
        //classpath:AnyPangHack$1
    }
}
