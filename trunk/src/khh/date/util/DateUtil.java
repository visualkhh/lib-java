package khh.date.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
     // Date!!!!!!!!!!!!!
     /**
      * <pre>
      * 문자열 형태의 날짜를 원하는 형태로 변환합니다.
      * 
      * 예시)
      * "yyyy.MM.dd G 'at' HH:mm:ss z"       2001.07.04 AD at 12:08:56 PDT
      * "EEE, MMM d, ''yy"   Wed, Jul 4, '01
      * "h:mm a"     12:08 PM
      * "hh 'o''clock' a, zzzz"      12 o'clock PM, Pacific Daylight Time
      * "K:mm a, z"  0:08 PM, PDT
      * "yyyyy.MMMMM.dd GGG hh:mm aaa"       02001.July.04 AD 12:08 PM
      * "EEE, d MMM yyyy HH:mm:ss Z" Wed, 4 Jul 2001 12:08:56 -0700
      * "yyMMddHHmmssZ"      010704120856-0700
      * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 2001-07-04T12:08:56.235-0700
      * </pre>
      * 
      * @param date 변환할 날짜
      * @param fromFormatString 변환될 포맷
      * @param toFormatString 변환할 포맷
      * @return 변환된 날짜 문자열
      */
     
    public static long getMillis()
    {
        return System.currentTimeMillis();
    }

    //해당년월에 마지막 날을 구한다.
    public static int getDateMaxDay(Calendar cal){
        Calendar calendar = cal;
//      calendar.set ( y, m-1, 1 );
        int maxDays = calendar.getActualMaximum ( Calendar.DAY_OF_MONTH );
        return maxDays;
    }

    
    
    
    //setDate
    
    //날짜 차이구한다.
    public static long getBetweenDay(Calendar before,Calendar after){

        Calendar before_cal = before;
//      before_cal.set ( before_y, before_m-1, before_d ); // 기준일로 설정. month의 경우 해당월수-1을 해줍니다.
        
        Calendar after_cal = after;
//      after_cal.set ( after_y,after_m-1, after_d);// 오늘로 설정. 
        int count = 0;

        
        if(before.equals(after)|| before==after){
            return 0;
        }else if(before_cal.before(after_cal)){ //내가 너보다  뒤에있으면
            
            while (before_cal.before( after_cal ) ) 
            {
                    
                before_cal.add ( Calendar.DATE, 1 ); // 다음날로 바뀜
                if(before_cal.after ( after_cal ) ){
                    break;
                }
                count++;
            }
            
        }else if(before_cal.after(after_cal)){  //내가 너보다 앞에있으면 
            while (before_cal.after( after_cal ) ) 
            {
                    
                before_cal.add ( Calendar.DATE, -1 ); // 다음날로 바뀜
                if(before_cal.before ( after_cal ) ){
                    break;
                }
                count--;
            }
        }
        
//      System.out.println ( "기준일로부터 " + count + "일이 지났습니다." );
//      if(count<0)
//          count = 0 ;
        return count;
    }
    
    
    
    //현재 날짜를 구한다.
//  public static String getDate_toString(){
//      return getDate_toString("yyyy/MM/dd HH:mm:ss/SSS");
//  }
    public static String dateFormat(Calendar calendar,String format){
        SimpleDateFormat formatter = new SimpleDateFormat ( format, Locale.KOREA );
        String dTime = formatter.format (calendar.getTime() );
        return dTime;
    }
    public static String dateFormat(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat ( format, Locale.KOREA );
        String dTime = formatter.format (date );
        return dTime;
    }
    
    
    

    public static Date getDate(String format,String date) throws ParseException{
        SimpleDateFormat sd = new SimpleDateFormat(format, Locale.KOREA);
        Date dateobj =sd.parse(date);
        return dateobj;
    }
    public static String getDate(String format) {
        return dateFormat(new Date(),format);
    }
    
    
    
    
    
//Calendar.DATE
    public static Date modifyDate(Date date,int calendarAddType,int value){
//      Date d = new Date ( );
        Calendar c = Calendar.getInstance ( );
        c.setTime ( date );
        c.add(calendarAddType, value);
        return c.getTime();
    }
    public static Calendar modifyDate(Calendar calendar,int calendarAddType,int value){
//      Date d = new Date ( );
        Calendar c = Calendar.getInstance ( );
        c.setTime ( calendar.getTime() );
        c.add(calendarAddType, value);
        return c;
    }
    
    
    
    
    
    //달력으로 맵핑한다. Claender  월은 -1 해줘야한다 사용할때는 +1
    public static Calendar getCalender(String format,String date) throws ParseException{
        Date dateobj =getDate(format,date);
        Calendar cal = Calendar.getInstance ( );//
        cal.setTime(dateobj);
        return cal;
    }
    public static Calendar getCalendar(Date date){
//      Date d = new Date ( );
        Calendar c = Calendar.getInstance ( );
        c.setTime ( date );
        return c;
    }
    public static Calendar getCalender(){
        Calendar cal = Calendar.getInstance ( );//
         cal.setTime(new Date());
        return cal;
    }

    
    public static int getDay_Of_Week(Calendar cal){
        int day_of_week = cal.get ( Calendar.DAY_OF_WEEK );
        return day_of_week;
//      if ( day_of_week == 1 )
//      m_week="일요일";
//      else if ( day_of_week == 2 )
//      m_week="월요일";
//      else if ( day_of_week == 3 )
//      m_week="화요일";
//      else if ( day_of_week == 4 )
//      m_week="수요일";
//      else if ( day_of_week == 5 )
//      m_week="목요일";
//      else if ( day_of_week == 6 )
//      m_week="금요일";
//      else if ( day_of_week == 7 )
//      m_week="토요일";

    }
}
