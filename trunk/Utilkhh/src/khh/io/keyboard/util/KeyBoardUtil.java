package khh.io.keyboard.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyBoardUtil {
    public static void pess(String input_str) throws AWTException {
        pess(input_str.getBytes());
    }
    public static void pess(byte[] input_byteArray) throws AWTException {
        for (int i = 0; i < input_byteArray.length; i++) {
            pess(input_byteArray[i]);
        }
    }
    
    public static void pess(byte ascii_code) throws AWTException{
        pess((int)ascii_code);
    }
    public static void pess(int ascii_code) throws AWTException {
//        int keyInput[] = {
//                KeyEvent.VK_T,
//                KeyEvent.VK_E,
//                KeyEvent.VK_S,
//                KeyEvent.VK_T,
//                KeyEvent.VK_I,
//                KeyEvent.VK_N,
//                KeyEvent.VK_G
//            };
        //rob.delay(1000);
        Robot robot = new Robot();
//        robot.keyPress(key);
        press(robot,ascii_code);
    }
    public static void press(Robot robot,int ascii_code) throws AWTException {
        robot.keyPress(ascii_code);
    }
}
