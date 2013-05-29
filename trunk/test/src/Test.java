import java.awt.AWTException;

import khh.debug.LogK;
import khh.string.util.StringUtil;

public class Test {
    public static void main(String[] args) throws AWTException, InterruptedException {
        String p = "poll*.xml";
        String g = "poll_aa.xml";
//        System.out.println( StringUtil.isFind(p, g));
        System.out.println( StringUtil.isMatches(g, p));
        
        LogK.getInstance().debug("\r\n","\r\n".getBytes());
    }
}
