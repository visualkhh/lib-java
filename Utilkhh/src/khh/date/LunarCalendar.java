package khh.date;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;

/**
*	양음력간 변환
*
*	사용법>>
*		LunarCalendar lc = new LunarCalendar() ;
*		String lunarDate = lc.toLunar("20021023") ;
*		String date = lc.fromLunar("20020918") ;
*/
public class LunarCalendar {
	
	private Calendar cal ;
	private ChineseCalendar cc ;


	public LunarCalendar() {
		// default TimeZone, Locale 을 사용..
		cal = Calendar.getInstance() ;
		cc = new ChineseCalendar();
	}

	/**
	*	양력(yyyyMMdd) -> 음력(yyyyMMdd)
	*
	*/
	public synchronized String toLunar( String yyyymmdd ) {
		if( 	yyyymmdd == null )
			return "" ;

		String date = yyyymmdd.trim() ;
		if( date.length() != 8 ) {
			if( date.length() == 4 ) 
				date = date + "0101" ;
			else if( date.length() == 6 )
				date = date + "01" ;
			else if( date.length() > 8 )
				date = date.substring(0,8) ;
			else
				return "" ;
		}

		cal.set( Calendar.YEAR, Integer.parseInt(date.substring(0,4)) ) ;
		cal.set( Calendar.MONTH, Integer.parseInt(date.substring(4,6))-1 ) ;
		cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)) ) ;

		cc.setTimeInMillis( cal.getTimeInMillis() ) ;

		// ChinessCalendar.YEAR 는 1~60 까지의 값만 가지고 , ChinessCalendar.EXTENDED_YEAR 는 Calendar.YEAR 값과 2637 만큼의 차이를 가집니다.
		int y = cc.get(ChineseCalendar.EXTENDED_YEAR)-2637 ;
		int m = cc.get(ChineseCalendar.MONTH)+1 ;
		int d = cc.get(ChineseCalendar.DAY_OF_MONTH) ;

		StringBuffer ret = new StringBuffer() ;
		if( y < 1000 )
			ret.append( "0" ) ;
		else if( y < 100 )
			ret.append( "00" ) ;
		else if( y < 10 )
			ret.append( "000" ) ;
		ret.append( y ) ;

		if( m < 10 )
			ret.append( "0" ) ;
		ret.append( m ) ;

		if( d < 10 ) 
			ret.append( "0" ) ;
		ret.append( d ) ;

		return ret.toString() ;
	}


	/**
	*	음력(yyyyMMdd) -> 양력(yyyyMMdd)
	*
	*/
	public synchronized String fromLunar( String yyyymmdd ) {
		if( 	yyyymmdd == null )
			return "" ;

		String date = yyyymmdd.trim() ;
		if( date.length() != 8 ) {
			if( date.length() == 4 ) 
				date = date + "0101" ;
			else if( date.length() == 6 )
				date = date + "01" ;
			else if( date.length() > 8 )
				date = date.substring(0,8) ;
			else
				return "" ;
		}

		cc.set( ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0,4))+2637 ) ;
		cc.set( ChineseCalendar.MONTH, Integer.parseInt(date.substring(4,6))-1 ) ;
		cc.set( ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)) ) ;

		//윤달여부
//		cc.set(ChineseCalendar.IS_LEAP_MONTH, (isLeapMonth == true) ? 1 : 0);
		
		cal.setTimeInMillis( cc.getTimeInMillis() ) ;

		int y = cal.get(Calendar.YEAR) ;
		int m = cal.get(Calendar.MONTH)+1 ;
		int d = cal.get(Calendar.DAY_OF_MONTH) ;

		StringBuffer ret = new StringBuffer() ;
		if( y < 1000 )
			ret.append( "0" ) ;
		else if( y < 100 )
			ret.append( "00" ) ;
		else if( y < 10 )
			ret.append( "000" ) ;
		ret.append( y ) ;

		if( m < 10 )
			ret.append( "0" ) ;
		ret.append( m ) ;

		if( d < 10 ) 
			ret.append( "0" ) ;
		ret.append( d ) ;

		return ret.toString() ;
	}


}