package colortest;

import java.awt.Color;

import com.kdt.util.converting.Converting;

public class ColorTest {
    public static void main(String[] args) {
        Color c = new Color(16, 16, 16, 0xfa);
        byte[] a = Converting.toByteArray(c.getRGB());
        for (int i = 0; i < a.length; i++) {
            System.out.format("%02X ", a[i]);
        }
        
        System.out.println();
        
        int ba = Converting.toInt(new byte[]{16, 16, 16, (byte) 0xf1});
        Color cb = new Color(ba,true);
        byte[] ab = Converting.toByteArray(cb.getRGB());
        for (int i = 0; i < ab.length; i++) {
            System.out.format("%02X ", ab[i]);
        }
    }
}
