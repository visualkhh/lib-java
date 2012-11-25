package jijong;

import java.util.ArrayList;

class Licens{
    boolean m;
    boolean j;
    public Licens(boolean m,boolean j) {
        this.m=m;this.j=j;
    }
    public boolean isM() {
        return m;
    }
    public void setM(boolean m) {
        this.m = m;
    }
    public boolean isJ() {
        return j;
    }
    public void setJ(boolean j) {
        this.j = j;
    }
    
}

public class test {
    public static void main(String[] args) {
            
        ArrayList<Licens> list = new ArrayList<Licens>();
        
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        list.add(new Licens(true,true) );
        
        
        
        
    }
}
