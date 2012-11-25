package interfacetest;

interface StrEvent_Interface {
    public void event(String id, Object o);
}

abstract class Target implements StrEvent_Interface {
    private int interval = 1000;
    public void action() {
        this.event(null, null);
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void addURL(String url) {
    }

    public void addFunction(Object function) {
    }
}

class t extends Target{
    @Override
    public void event(String id, Object o) {
        // TODO Auto-generated method stub
        System.out.println("a");
    }
}
public class interfacetest {

    public static void main(String[] args) {
        Target e = new t();
        e.action();
    }
}
