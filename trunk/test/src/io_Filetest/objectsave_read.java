package io_Filetest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import com.kdn.util.file.FileUtilKDN;
import com.kdt.std.adapter.Adapter_Std;
import com.khh.util.file.FileUtil;

public class objectsave_read {
    
    public objectsave_read() {
    }
    
    
    public void gogo() throws Exception{
//        Adapter_Std<String, String> tt = new Adapter_Std<String, String>();
//        tt.add(new String("g"), new String("ha"));
//        tt.add(new String("ag"), new String("ha"));
//        
//      for (int i = 0; i < tt.size(); i++) {
//          System.out.println(tt.getKey(i)+"  "+tt.get(i));
//      }
//      FileUtilKDN.writeFile("C:\\write\\tt.obj", tt);
//  
//  
//  
//          System.out.println(tt.size());
//          System.out.println();
//  
//  
//          Adapter_Std<String, String> t = (Adapter_Std<String, String>) FileUtilKDN.readFileToObject("C:\\write\\tt.obj");
//          System.out.println(t.size());
//          for (int i = 0; i < t.size(); i++) {
//              System.out.println(tt.getKey(i)+"  "+tt.get(i));
//          }
//        
        
        
        
//        ArrayList<String> tt = new ArrayList<String>();
//        tt.add("aa");
//        tt.add("1aa");
//        tt.add("2aa");
//        tt.add("4aa");
//        tt.add("a56a");
//        tt.add("a346a");
//        tt.add("a234a");
//        tt.add("av234a");
//        tt.add("aasda");
//        tt.add("aga");
//        tt.add("aa5");
//        tt.add("a7a");
//        tt.add("a4a");
//        tt.add("a22a");
//        tt.add("a676a");
//        tt.add("a345a");
//        tt.add("aa");
//        tt.add("aa");
//        tt.add("aa");
//        tt.add("aa");
//        tt.add("aa");
//        tt.add("aa");
//        tt.add("aa");
//        
//        for (int i = 0; i < tt.size(); i++) {
//            System.out.println("  "+tt.get(i));
//        }
//        FileUtilKDN.writeFile("C:\\write\\tt.obj", tt);
//        
//        System.out.println(tt.toString());
//        System.out.println();
//        
//        ArrayList<String> t = (ArrayList<String>) FileUtilKDN.readFileToObject("C:\\write\\tt.obj");
//        
//        System.out.println(t.size()+"      "+t.toString());
//        for (int i = 0; i < t.size(); i++) {
//            System.out.println("  "+t.get(i));
//        }
//        
        
        
        
//        String input="visualhhk";
//        System.out.println(input);
//        FileUtilKDN.writeFile("C:\\write\\tt.obj", input);
//        
//        System.out.println();
//        System.out.println();
//        
//        String output = (String) FileUtilKDN.readFileToObject("C:\\write\\tt.obj");
//        System.out.println(output);
        
        
        
        FileUtilKDN.writeFile("C:\\write\\by", new byte[]{1,2,3,4,5,6,8,9,0,1,1,1,1,1,1,5,6,76,23,5,6});
        byte[] get = FileUtilKDN.readFileToByte("C:\\write\\by");
        
        for (int i = 0; i < get.length; i++) {
            byte b = get[i];
            System.out.println(b);
        }
        
        
    }
    
    
    public static void main(String[] args) throws Exception {
        objectsave_read b = new objectsave_read();
        b.gogo();
        
    }
}
