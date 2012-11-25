package instansof;

import java.util.ArrayList;

public class instan {
    public static void main(String[] args) {
        
        ArrayList a  =new ArrayList();
        int ii=1;
        double dd=1.4;
        float ff=1.4f;
        a.add(ii);
        a.add(1);
        a.add(true);
        a.add(dd);
        a.add(ff);
        a.add("st");
        a.add(11.1);
        
        instan(a);
    }
    
    
    
    public static void instan(ArrayList array){
        
        for (int i = 0; i < array.size(); i++) {
            Object a= array.get(i);
            if(a instanceof String){
                System.out.println("string");
            }else if(a instanceof Integer){
                System.out.println("int");
            }else if(a instanceof Float){
                System.out.println("float");
            }
            else if(a instanceof Double){
                System.out.println("double");
            }else if(a instanceof Boolean){
                System.out.println("boolean");
            }
        }
    }
}
