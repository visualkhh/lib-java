package convertertest;

import com.kdt.util.converting.Converting;

public class convertingtest {
public static void main(String[] args) {
    byte[] input_data =new byte[]{(byte) 0x01,(byte) 0x02,(byte) 0x03,(byte) 0x04,0x07,0x08,0x09,0x01,0x02};
    // Converting.test(4, input_data, (byte) 0xff);
     
    /* 
   byte[] a  = Converting.sliceByteArray(input_data, 0, 4);
     for (int i = 0; i < a.length; i++) {
         System.out.format("[%02X] ",a[i]);
         
     }
    */
     
     byte[][] grouparray =Converting.toByteGroupArray(input_data, 4, (byte)0x00, Converting.ALIGN_LEFT);
     for (int i = 0; i < grouparray.length; i++) {
       //  System.out.println(grouparray.length+"       "+grouparray[i].length);
         for (int j = 0; j < grouparray[i].length; j++) {
             byte bs = grouparray[i][j];
             System.out.format("[%02X] ",bs);
         }
         
}
}
