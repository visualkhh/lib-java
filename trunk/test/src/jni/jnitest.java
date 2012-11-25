package jni;

import com.kdn.util.property.PropertyUtil;

public class jnitest {
    static{
//        System.loadLibrary("libjni_shard_lib");
        System.loadLibrary("libjni_shard");
    }
    
    public  static native void nativeHelloWorld();
//    public void start(){
//        System.out.println("helloworld_lib_start");
//        sayHelloJNI();
//    }
//    
    
public static void main(String[] args) {
    System.out.println("hello java gogosing"+PropertyUtil.getVM_Version());
    nativeHelloWorld();
   // new jnitest().sayHelloJNI();//.start();   
}
}
