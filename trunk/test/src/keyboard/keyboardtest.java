package keyboard;

import java.awt.AWTException;
import java.awt.event.InputEvent;

import com.khm.util.io.keyboard.KeyBoardUtil;
import com.khm.util.io.mouse.MouseUtil;

public class keyboardtest {
    public static void main(String[] args) throws AWTException, InterruptedException {
        /*for (int i = 0; i < 20 ; i++) {
            KeyBoardUtil.pess('A'+i);
            System.out.println('A'+i);
            Thread.sleep(1000);
        }*/
        char input_char = '\n';
        System.out.format("%02X",(byte)input_char);
        //KeyBoardUtil.pess();
        
    }
}
