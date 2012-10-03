package khh.std.chart;



public class Chart_DTO {
	
	public enum AREA_TYPE {
		BLANK(0),
		AREA(1)
		;
		private final int value;

		AREA_TYPE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	public enum LINE_TYPE {
		BLANK(0),
		SOLID(1),
		DOTTED(2)
		;
		private final int value;

		LINE_TYPE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	public enum POINT_TYPE {
		BLANK(0),CIRCLE(1), TRIANGLE(2), SQUARE(3), DIAMOND(3);
		private final int value;
		
		POINT_TYPE(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
		double 	location;
		double 	value;
//		Date date;
		boolean isDisplay = false;
		int color = 0xffffffff;
		float line_size = 2;
		int lineType=LINE_TYPE.SOLID.getValue();
		int areaType=AREA_TYPE.AREA.getValue();
		int pointType=POINT_TYPE.BLANK.getValue();
		String title="";
		
		

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getPointType() {
			return pointType;
		}
		public void setPointType(int pointType) {
			this.pointType = pointType;
		}
		public float getLine_size() {
			return line_size;
		}
		public void setLine_size(float lineSize) {
			line_size = lineSize;
		}
		public int getLineType() {
			return lineType;
		}
		public void setLineType(int lineType) {
			this.lineType = lineType;
		}
		public int getAreaType() {
			return areaType;
		}
		public void setAreaType(int areaType) {
			this.areaType = areaType;
		}
		public boolean isDisplay() {
			return isDisplay;
		}
		public void setDisplay(boolean isDisplay) {
			this.isDisplay = isDisplay;
		}
 
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public double getLocation() {
			return location;
		}
		public void setLocation(double location) {
			this.location = location;
		}
//		public Date getDate() {
//			return date;
//		}
//		public void setDate(Date date) {
//			this.date = date;
//		}
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
	
}
