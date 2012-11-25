package extendsTest;

class A {

    public A(){
        
    }
    public String get() {
        return "a";
    }
}

class B extends A {

    public String get() {
        return "b";
    }
}

public class extendsTest {

    public static void main(String[] args) {
        B a = (B) new A();
        System.out.println(a.get());
    }
}
