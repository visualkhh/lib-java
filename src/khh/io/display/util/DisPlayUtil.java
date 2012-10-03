package khh.io.display.util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class DisPlayUtil {

    public static int getWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        return screenSize.width;
    }
    public static int getHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        return screenSize.height;
    }
    public static Dimension getDimension(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        return screenSize;
    }
    
    public static BufferedImage getScreenCapture() throws AWTException{
        return getScreenCapture(new Robot());
    }
    
    public static BufferedImage getScreenCapture(Robot robot) throws AWTException{
        // 전체 스크린 샷 가져오기
        // Capture the whole screen
        Rectangle area  = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage bufferedImage = robot.createScreenCapture(area);
        
        return bufferedImage;
    }
    public static BufferedImage getScreenCapture(Rectangle rt) throws AWTException{
        return getScreenCapture(new Robot(),rt);
    }
    public static BufferedImage getScreenCapture(Robot robot,Rectangle rt) throws AWTException{
        BufferedImage bufferedImage = robot.createScreenCapture(rt);
        return bufferedImage;
    }
    public static BufferedImage getScreenCapture(int startX,int startY, int endX,int endY) throws AWTException{
        return getScreenCapture(new Robot(),startX,startY,endX,endY);
    }
    public static BufferedImage getScreenCapture(Robot robot,int startX,int startY, int endX,int endY) throws AWTException{
        // 부분 스크린샷 가져오기
        // Capture a particular area on the screen
        Rectangle area = new Rectangle(startX, startY, endX, endY);
        BufferedImage bufferedImage = robot.createScreenCapture(area);
        return bufferedImage;
    }
    
    
}
