package mousetest;

import java.awt.AWTException;
import java.awt.Point;

import com.khm.util.io.mouse.MouseUtil;

public class mousetest {
    public static void main(String[] args) throws AWTException, InterruptedException {

        int x=0;
        int y = 0;
        for (int i = 0; i < 10; i++) {
            MouseUtil.moveMouse(new Point(x, y));
            x+=100;
            y+=100;
            Thread.sleep(3000);
        }
    }
}
