package khh.gui.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GraphicsUtil {

    /*
    GraphicsEnvironment > GraphicsDevice > GraphicsConfiguration
    */
    public static GraphicsEnvironment getGraphicsEnvironment(){
        return GraphicsEnvironment.getLocalGraphicsEnvironment();
    }
    public static GraphicsDevice getDefaultScreenDevice(){
        return getGraphicsEnvironment().getDefaultScreenDevice(); 
//        device.setFullScreenWindow(frame); 
        //device.setFullScreenWindow(null); 
    }
    public static GraphicsDevice[] getScreenDevice(){
        return getGraphicsEnvironment().getScreenDevices(); 
    }
    
    public static GraphicsConfiguration getGraphicsConfiguration(GraphicsDevice device){
        return device.getDefaultConfiguration(); 
    }
    
    
    public static GraphicsConfiguration getDefaultGraphicsConfiguration(){
        return getGraphicsConfiguration(getDefaultScreenDevice());
    }
    

    
    
}
