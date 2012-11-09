package khh.io.mouse.util;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import khh.std.realworld.TPoint;

public class MouseUtil {

    public static Point getPoint(){
    	
        return MouseInfo.getPointerInfo().getLocation();
    }
    public static int getX(){
        return MouseInfo.getPointerInfo().getLocation().x;
    }
    public static int getY(){
        return MouseInfo.getPointerInfo().getLocation().y;
    }
    public static void move(TPoint p) throws AWTException{
        move(new Point(p.getX(),p.getY()));
    }
    public static void move(Point p) throws AWTException{
        move(new Robot(),p);
    }
    public static void move(Robot robot, Point p) throws AWTException{
        robot.mouseMove(p.x, p.y);
    }
    public static void press(int button_mask) throws AWTException{
        // InputEvent.BUTTON1_MASK
        // InputEvent.BUTTON2_MASK ...
        press(new Robot(),button_mask);
    }
    public static void press(Robot robot,int button_mask){
        robot.mousePress(button_mask);
    }
    public static void release(int button_mask) throws AWTException{
        // InputEvent.BUTTON1_MASK
        // InputEvent.BUTTON2_MASK ...
        release(new Robot(),button_mask);
    }
    public static void release(Robot robot,int button_mask){
        robot.mouseRelease(button_mask);
    }

}
