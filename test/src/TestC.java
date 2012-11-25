import java.sql.Date;


interface LifeStyle{
    public void sleep();
    public void wake();
    public void move();
}


class HHKlife implements LifeStyle{
    @Override
    public void sleep() {
        System.out.println("난누워서자");
    }

    @Override
    public void wake() {
        System.out.println("벌덕");
    }

    @Override
    public void move() {
        System.out.println("난뛴다");
    }
}


class KMlife implements LifeStyle{
    @Override
    public void sleep() {
        System.out.println("난뒤집어서");
    }

    @Override
    public void wake() {
        System.out.println("일어나기싫어요");
    }

    @Override
    public void move() {
        System.out.println("날라다녀요");
    }
    
    public void joy(){
        System.out.println("술먹는다");
    }
    @Override
    public String toString() {
        return "난김명스타일이야";
    }
}



class DateH extends Date{
    public DateH(long arg0) {
        super(arg0);
    }
    public int getRealMonth(){
        return getMonth()+1;
    }
}

public class TestC {
    public static void main(String[] args) {
        System.out.println("a");
        String ab="5";
        java.lang.String a="5";
        
       Date date= new Date(System.currentTimeMillis());
       System.out.println( date.getMonth());
       
       
       DateH dateh= new DateH(System.currentTimeMillis());
       System.out.println( dateh.getRealMonth());
       
       
       
       
       //interface
       KMlife k = new KMlife();
       HHKlife h = new HHKlife();
//       liefe(k);
//       liefe(h);
       Object af = k;
       Object ob = new Object();
       callobject(k);
       callobject(af);
    }
    
    
 public static void liefe(LifeStyle lifestyle){
     lifestyle.sleep();
     lifestyle.wake();
     lifestyle.move();
 }
 public static void callobject(Object o){
 System.out.println( o.toString());
 }
    
}
