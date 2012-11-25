package khh.std.realworld;

import java.util.Date;

public class TPoint {
	public int x;
	public int y;
	public int z;
	Date date;
	
	
	public TPoint() {
    }
   public TPoint(int x, int y) {
       this.x=x;
       this.y=y;
    }
	 
   
   
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
