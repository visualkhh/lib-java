package guitest;

import java.awt.*; 
import javax.swing.*; 

import khh.gui.util.GUIUtil;
import khh.gui.util.WindowUtil;
import com.sun.awt.AWTUtilities;

public class Graphics extends JFrame{ 
    GraphicsDevice device; 
    JFrame frame; 

    public Graphics() { 
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
        device = ge.getDefaultScreenDevice(); 
        
        GraphicsConfiguration gc = device.getDefaultConfiguration(); 
        try { 
            frame = new JFrame(gc); 
            frame.setUndecorated(true); 
       //     device.setFullScreenWindow(frame); 
            frame.setVisible(true); 
       //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            frame.setTitle("Euclase"); 
            
            GUIUtil.setSize(frame, 100, 100);
            frame.setVisible(true);
            GUIUtil.setSize(this, 100, 100);
           AWTUtilities.setWindowOpacity(this, 80.0f / 100.0f);
           // WindowUtil.setWindowOpacity(this, 75.0f / 100.0f);
            this.setAlwaysOnTop(true);
            this.setVisible(true);
            
        } catch (Exception ex) { 
            device.setFullScreenWindow(null); 
            System.exit(0); 
        } 
         
    } 
     
    public static void main(String args[]){ 
        Graphics fg = new Graphics(); 
        //fg.setAlwaysOnTop(true);   



    } 
} 

