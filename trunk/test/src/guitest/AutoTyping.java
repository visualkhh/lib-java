package guitest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.khm.util.io.keyboard.KeyBoardUtil;

public class AutoTyping extends Thread {
    JFrame jFrame;
    JPanel jPanel;
    JLabel jLabel;
    JTextField jTextField;
    JButton jButton;
    Robot rob;
        
    AutoTyping() {

        jFrame = new JFrame("혼자 돌아댕깁니다.");
        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3,1));

        jTextField = new JTextField(10);
        jLabel = new JLabel("테스트입니다.");

        jButton = new JButton("종료");

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.gc();
                System.exit(0);
            }
        });


        jPanel.add(jTextField);
        jPanel.add(jLabel);
        jPanel.add(jButton);
        jFrame.getContentPane().add(jPanel);
        jFrame.setSize(150,100);
        jFrame.setLocation(350,400);
        jFrame.setVisible(true);
        
        try {
            process();
        } catch (Exception e) { }
    }

    public void process() throws AWTException {   

        this.start();
        rob = new Robot();
        
        int keyInput[] = {
            KeyEvent.VK_T,
            KeyEvent.VK_E,
            KeyEvent.VK_S,
            KeyEvent.VK_T,
            KeyEvent.VK_I,
            KeyEvent.VK_N,
            KeyEvent.VK_G
        };
        rob.delay(1000);
        jTextField.requestFocus();
        for (int i = 0; i < keyInput.length; i++) {
            KeyBoardUtil.keyPress(keyInput[i]);
//            rob.keyPress(keyInput[i]);
            System.out.println((char)keyInput[i]);
//            rob.delay(500);
        }
        rob.keyPress(KeyEvent.VK_ENTER);
        
        this.stop();
    }
    
    public void run() {
        while (true) {
            try {
                Robot rob2 = new Robot();
                for (int i = 200; i < 600; i=i+15) {
                    rob2.mouseMove(i,600);
                    sleep(50);
                }
                for (int i = 600; i > 200; i=i-15) {
                    rob2.mouseMove(i,600);
                    sleep(50);
                }             
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[])  {
        new AutoTyping();
    }
}