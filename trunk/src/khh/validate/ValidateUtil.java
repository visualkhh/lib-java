package khh.validate;

public class ValidateUtil {

    /**
     * <p>문자열이 Null인지 확인</p>
     *
     * @param    대상 문자열.
	 * @return   Null이면 TRUE, 아니면 FALSE.
     */	
    public static boolean isNull (String data) {
        return ( data == null || data.equals(null) );
    }


    /**
     * <p>문자열이 제로(0)인지 확인</p>
     *
     * @param    대상 문자열.
	 * @return   제로(0)이면 TRUE, 아니면 FALSE.
     */	
    public static boolean isZero (String data) {
        return (data.equals("0"));
    }
    

    /**
     * <p>문자열이 비어있는지, 길이가 제로(0)인지 확인</p>
     *
     * @param    대상 문자열.
	 * @return   값이 비어있으면, 제로(0)이면 TRUE, 아니면 FALSE.
     */	
    public static boolean isBlank (String data) {  
	   
		if (data == null)
			return true;
		else
			return (data.equals(""));   
    }
    /**
     * <p>스트링이 숫자로만 이루어져 있는지 확인</p>
     *
     * @param    대상 문자열.
	 * @return   숫자인 문자로만 되어있으면 TRUE, 아니면 FALSE.
     */
    public final static boolean isAlphaNumeric (String text) {
		
		if (text == null)
			return false;

		int size = text.length();
        for(int i = 0 ; i < size ; i++) {
            if(!Character.isDigit(text.charAt(i)))
                return false;
		}
        return true;
    }
    /**
     * <p>E-mail주소가 올바른지 확인</p>
     *
     * @param    대상 문자열.
	 * @return   맞으면 TRUE, 아니면 FALSE.
     */
    public static boolean isEmailAddr (String data) {
       
		if (data == null)
			return false;  
		
        if (data.indexOf("@") == -1) 
            return false;            
      
        return true;
    }
    
    /**
     * <p>주민등록번호가 올바른지 확인</p>
     *
     * @param    대상 문자열 1.
     * @param    대상 문자열 2.
	 * @return   맞으면 TRUE, 아니면 FALSE.
     */
    public static boolean isSSN (String serial1, String serial2) {
     
		if (serial1.length() != 6) {
			return false;
		}
		else if (serial2.length() != 7) {
			return false;
		}
		else {
			int digit = 0;
			for (int i=0 ; i < serial1.length() ; i++) {
				String str_dig = serial1.substring(i,i+1);
				if (str_dig.length() < 0 || str_dig.length() > 9) { 
					digit = digit + 1; 
				}
			}

			if ((serial1 == "") || ( digit != 0 )) {
				return false;   
			}

			int digit1=0;
			for(int i=0 ; i < serial2.length() ; i++) {
				String str_dig1 = serial2.substring(i,i+1);
				if(str_dig1.length() < 0 || str_dig1.length() > 9) { 
					digit1 = digit1 + 1; 
				}
			}

			if((serial2 == "") || ( digit1 != 0 )) {
				return false;   
			}

			if (Integer.parseInt(serial1.substring(2,3)) > 1) {
				return false;
			}

			if (Integer.parseInt(serial1.substring(4,5))> 3) {
				return false;   
			}

			if (Integer.parseInt(serial2.substring(0,1))> 4 || 
				Integer.parseInt(serial2.substring(0,1)) == 0) {
				return false;   
			}

			int a1= Integer.parseInt(serial1.substring(0,1));
			int a2= Integer.parseInt(serial1.substring(1,2));
			int a3= Integer.parseInt(serial1.substring(2,3));
			int a4= Integer.parseInt(serial1.substring(3,4));
			int a5= Integer.parseInt(serial1.substring(4,5));
			int a6= Integer.parseInt(serial1.substring(5,6));

			int check_digit1 = a1*2 + a2*3 + a3*4 + a4*5 + a5*6 + a6*7;

			int  b1 =  Integer.parseInt(serial2.substring(0,1));
			int  b2 =  Integer.parseInt(serial2.substring(1,2));
			int  b3 =  Integer.parseInt(serial2.substring(2,3));
			int  b4 =  Integer.parseInt(serial2.substring(3,4));
			int  b5 =  Integer.parseInt(serial2.substring(4,5));
			int  b6 =  Integer.parseInt(serial2.substring(5,6));
			int  b7 =  Integer.parseInt(serial2.substring(6,7));

			int check_digit = check_digit1 + b1*8 + b2*9 + b3*2 + b4*3 + b5*4 + b6*5;

			check_digit = check_digit % 11;
			check_digit = 11 - check_digit;
			check_digit = check_digit % 10;

			if (check_digit != b7) {
				return false;   
			}
			else {
				return true;      
			}
		}
	}
    
}
