package khh.std.report;

import javax.swing.text.Utilities;

import khh.decimal.util.DecimalUtil;
import khh.util.Util;

public class List_DTO {
		double[] list;
		String name;
		String sign;
		
		
	 
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public double[] getList() {
			return list;
		}
		public void setList(double[] list) {
			this.list = list;
		}
		
		public int getMaxIndex() {
			return (Util.getMaxIndex(this.list));
		}
 
		public int getMinIndex() {
			return (Util.getMinIndex(this.list));
		}
		//
		public double getMaxValue(){
			return this.list[getMaxIndex()];
		}
		public double getMinValue(){
			return this.list[getMinIndex()];
		}
		public double getAvgValue(){
			return  Util.getAvg(this.list);
		}
		public double getSumValue(){
			return Util.getSum(this.list);
		}
		
		
		
		public String getMaxValue_toString(){
			return getMaxValue_toString(1);
		}
		public String getMinValue_toString(){
			return getMinValue_toString(1);
		}
		
		public double getValue(int index){
			return this.list[index];
		}
		public String getValue_toString(int index){
			return getValue_toString(index, 1);
		}

		
		public String getMaxValue_toString(int decimalPosition){
			return DecimalUtil.decimal(this.list[getMaxIndex()], 0, decimalPosition);
		}
		public String getMinValue_toString(int decimalPosition){
			return DecimalUtil.decimal(this.list[getMinIndex()], 0, decimalPosition);
		}
		public String getValue_toString(int index,int decimalPosition){
			return DecimalUtil.decimal(this.list[index], 0, decimalPosition);
		}
		
		
		
		
		
}
