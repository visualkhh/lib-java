import java.awt.AWTException;

import khh.string.util.StringUtil;

public class Test {
    public static void main(String[] args) throws AWTException, InterruptedException {
        String p = "poll*.xml";
        String g = "poll_aa.xml";
//        System.out.println( StringUtil.isFind(p, g));
        System.out.println( StringUtil.isMatches(g, p));
    }
}
