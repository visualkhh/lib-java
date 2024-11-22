package point;

import java.util.function.Function;

public class PointService {

    public void printPoint(Function<WOWPoint.Builder, WOWPoint> pointSupplier) {
        var point = pointSupplier.apply(new WOWPoint.Builder());
        System.out.println("x: " + point.x() + ", y: " + point.y());
    }
    public void printPoint(WOWPoint point) {
        System.out.println("x: " + point.x() + ", y: " + point.y());
    }
}
