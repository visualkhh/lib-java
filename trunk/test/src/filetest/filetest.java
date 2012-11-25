package filetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class filetest {
    public static ArrayList a = new ArrayList();
    public static void main(String[] args) {
        String a="C:\\apache-tomcat-5.5.28";
        File file = new File(a);        
        System.out.println(file.exists());
//        try{
//        for (int i = 0; i < 30000000; i++) {
//            in();
//        }
//        }catch (Exception e) {
//            System.out.println("exception  "+a.size());
//        }
//        System.out.println("------------->   "+a.size());

    }
    
    public static void in () throws IOException{
              a.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}
