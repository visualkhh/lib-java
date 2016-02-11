import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Date;

import khh.date.util.DateUtil;

public class DateTest {
public static void main(String[] args) {
//	String a = DateUtil.dateFormat(DateUtil.modifyDate(Calendar.MINUTE, -5),"yyyyMMddHHmmss");
//	System.out.println(a);
//	System.out.println(DateUtil.getMillis());
	
	long a =50150425111244l;
	System.out.println(a);
	
	
	Date d = new Date(1454321654000l);
	System.out.println(DateUtil.dateFormat(d,"yyyy:MM:dd HH:mm:ss"));
	OffsetTime ot1 = OffsetTime.now();
	System.out.println("Current  offset  time: " + ot1);

	// a zone offset +01:30
    ZoneOffset offset = ZoneOffset.ofHoursMinutes(1, 30);
    OffsetTime offsetTime = OffsetTime.of(16, 40, 28, 0, offset);
    System.out.println(offsetTime);

}
}
