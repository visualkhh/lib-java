

public class JniT {
    static{
        System.loadLibrary("libjnit_shartlib_2");
        
    }
    public JniT() {
     System.out.println("jnitest hello");
    }
    
//    public void start(){
//       
//        System.out.println("helloworld_lib_start");
//        sayHelloJNI();
//    }
//    
    public static  native void sayHelloJNI();
    
public static void main(String[] args) {
    System.out.println("hello ja________va gogosing");
    new JniT().sayHelloJNI();//.start();
}
}
