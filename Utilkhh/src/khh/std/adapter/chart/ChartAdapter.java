package khh.std.adapter.chart;

import javax.swing.text.Utilities;

import khh.std.adapter.Adapter_Base;
import khh.std.chart.Chart_DTO;
import khh.util.Util;


public class ChartAdapter extends Adapter_Base<Integer,Chart_DTO> {
	public enum TYPE {
		NOMAL(0),
		SCOPE(1)
		;
		private final int value;

		TYPE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	public enum AXIS_TYPE {
		LEFT(0),
		RIGHT(1),
		BOTTOM(2),
		TOP(3)
		;
		private final int value;

		AXIS_TYPE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	String title;
	int		axis_type=AXIS_TYPE.LEFT.getValue();
	int		type=TYPE.NOMAL.getValue();
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAxis_type() {
		return axis_type;
	}
	public void setAxis_type(int axisType) {
		axis_type = axisType;
	}
	
	
	
	
	
	
	public Chart_DTO getMaxLocation() throws Exception{
		return get(Util.getMaxIndex(getLocationArray()));
	}
	public Chart_DTO getMinLocation() throws Exception{
		return get(Util.getMinIndex(getLocationArray()));
	}
	
	public Chart_DTO getMaxValue() throws Exception{
		return get(Util.getMaxIndex(getValueArray()));
	}
	public Chart_DTO getMinValue() throws Exception{
		return get(Util.getMinIndex(getValueArray()));
	}
	
//	public Chart_DTO getMaxDate() throws Exception{
//		return get(Utilities.getMaxIndex(getDateArray()));
//	}
//	public Chart_DTO getMinDate() throws Exception{
//		return get(Utilities.getMinIndex(getDateArray()));
//	}
	
	
	
	
	
	
	
	
	
	
	
	private double[] getLocationArray() throws Exception{
		double [] data = new double[size()];
		for(int  i = 0 ; i < data.length ; i++){
				data[i]=get(i).getLocation();
		}
		return data;
	}
	private double[] getValueArray() throws Exception{
		double [] data = new double[size()];
		for(int  i = 0 ; i < data.length ; i++){
				data[i]=get(i).getValue();
		}
		return data;
	}
//	private double[] getDateArray() throws Exception{
//		double [] data = new double[size()];
//		for(int  i = 0 ; i < data.length ; i++){
//				data[i]=get(i).getDate().getTime();
//		}
//		return data;
//	}
 	
}


