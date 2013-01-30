package khh.conversion.util;

import java.text.DecimalFormat;

public class UnitUtil{

	private static DecimalFormat df = new DecimalFormat("###,##0");

	private UnitUtil() {}


    /**
     * <p>한국 통화단위로 값을 변경한다.</p>
     *
     * @param    대상 값.
     * @return   변경된 한국 통화단위 값, "###,##0".
     */	
	public static String toWon (double w) {
		return df.format(w);
	}


    /**
     * <p>한국 통화단위로 값을 변경한다.</p>
     *
     * @param    대상 값.
     * @return   변경된 한국 통화단위 값, "###,##0".
     */	
	public static String toWon (int w) {
		
		return df.format(w);
	}


    /**
     * <p>한국 통화단위로 값을 변경한다.</p>
     *
     * @param    대상 값.
     * @return   변경된 한국 통화단위 값, "###,##0".
     */	
	public static String toWon (long w) {
		
		return df.format(w);
	}


    /**
     * <p>한국 통화단위로 값을 변경한다.</p>
     *
     * @param    대상 값.
     * @return   변경된 한국 통화단위 값, "###,##0".
     */	
	public static String toWon (String w) {

        if (w == null || w.equals("") )
            return "";
        
		double d = 0;

		try {
			d = Double.valueOf(w).doubleValue();
		
		} catch (Exception e) {
			d = 0;
		}
		
		return toWon(d);
	}
	
	/**
	 * 외화에서 소숫점이하 2째 자리까지 리턴한다.
	 */
	public static String toWoi(String s){
		String temp = null;

		if (s == null)
			temp = "";
		else {
			try {
				double change = Double.valueOf(s).doubleValue();
				DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
				temp = decimal.format(change);
			} catch (Exception e) {
				return "";
			}
		}
 
		return temp;
	}
}
