package khh.std.realworld;

public class TPointToPoint {
private TPoint start;
private TPoint end;

public TPointToPoint() {
}
public TPointToPoint(TPoint start, TPoint end) {
    this.start=start;
    this.end=end;
}


public TPoint getStart() {
    return start;
}
public void setStart(TPoint start) {
    this.start = start;
}
public TPoint getEnd() {
    return end;
}
public void setEnd(TPoint end) {
    this.end = end;
}

}
