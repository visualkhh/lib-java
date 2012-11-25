package cal;

import com.kdn.util.date.LunarCalendar;

public class cal {
    public static void main(String[] args) {
        LunarCalendar aLunarCalendar = new LunarCalendar();
        String aDate = "20120101";
        String lunarDate = null;
        String solarDate = null;
        // 양력 --> 음력 (20090623 --> 20090501)
        lunarDate = aLunarCalendar.toLunar(aDate);
        // 음력 (평달) --> 양력 (20090501 --> 20090524)
        solarDate = aLunarCalendar.fromLunar("20120101");

        
        System.out.println(lunarDate+"         "+solarDate);
    }
}
