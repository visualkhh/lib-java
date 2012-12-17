package messageformatTest;

import java.text.MessageFormat;
import java.util.Date;

public class MessageFormatTest{
//	     private static final String patternSimplifiedData = "내이름은 {0} 이고 나이는 {1}이다. 사는곳은 {2}이며 {3}를 꿈꾸고 있다.";
//      public static void main(String[] args)
//      {
//          String area = "오산";
//          System.out.println(MessageFormat.format(patternSimplifiedData,
//                  new Object[] { "강한구", "19", area, "간지나는 개발자"}));
//      }
    public static void main(String[] args)
    {
        Object[] arguments = {
                new Integer(7),
                new Date(System.currentTimeMillis()),
                "a disturbance in the Force"
        };
 
 
        String result = MessageFormat.format(
                "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
                arguments);
        System.out.println("result :" + result);
 
 
    }
}
