import point.PointService;
import point.WOWPoint;

public class Main {
    public static void main(String[] args) {
        new PointService().printPoint((builder) -> builder.x(10).y(20).build());
        new PointService().printPoint(new WOWPoint(1,2));
    }
}