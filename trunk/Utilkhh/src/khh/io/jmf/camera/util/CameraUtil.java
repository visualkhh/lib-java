//JMF 깔아야지 되는거임  그럼 JMF 관련된 jar 들어감. lib쪽에 그럼됨 

package khh.io.jmf.camera.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.util.BufferToImage;

import com.sun.media.protocol.vfw.VFWCapture;

public class CameraUtil {

    
    public static ArrayList<CaptureDeviceInfo> getCameraList(){
        Vector<CaptureDeviceInfo> list = CaptureDeviceManager.getDeviceList ( null );
        CaptureDeviceInfo devInfo = null;
        String name=null;
        ArrayList<CaptureDeviceInfo> return_list = new ArrayList<CaptureDeviceInfo>();
 
        if ( list != null )
        {
 
            for ( int i=0; i < list.size(); i++ ){
                devInfo = (CaptureDeviceInfo)list.elementAt ( i );
                name = devInfo.getName();
                if ( name.startsWith ("vfw:") ){
//                    System.out.println ("DeviceManager List : " + name );
                    return_list.add(  devInfo );
                }
            }
            
        }
        else{
            for ( int i = 0; i < 10; i++ ){
                try{
                    name = VFWCapture.capGetDriverDescriptionName ( i );
                    if (name != null && name.length() > 1)
                    {
                        devInfo = com.sun.media.protocol.vfw.VFWSourceStream.autoDetect ( i );
                        if ( devInfo != null )
                        {
//                            System.out.println ("VFW Autodetect List : " + name );
                            return_list.add(devInfo);
                        }
                    }
                }
                catch ( Exception ioEx ){
                    //System.err.println ("Error connecting to [" + webCamDeviceInfo.getName() + "] : " + ioEx.getMessage() );
                    // ignore errors detecting device
                    //statusBar.setText ( "AutoDetect failed : " + ioEx.getMessage() );
                }
            }
        }
        return return_list;
    }
    
    
    
    public static FormatControl getFormatControl(Player player){
        FormatControl formatControl = (FormatControl)player.getControl ( "javax.media.control.FormatControl" );
        return formatControl;
    }
    public static VideoFormat getPlayerFormat(Player player){
        FormatControl formatControl = getFormatControl(player);
        VideoFormat format = (VideoFormat) formatControl.getFormat();
        return format;
    }
    
    
    public static FrameGrabbingControl getFrameGrabbingControl(Player player){
        FrameGrabbingControl fgc = (FrameGrabbingControl)player.getControl ( "javax.media.control.FrameGrabbingControl" );
        return fgc;
    }
    public static Buffer getFrameBuffer(Player player){
        FrameGrabbingControl fgc = getFrameGrabbingControl(player);
        return fgc.grabFrame();
    }
    
    
    public static Image getImage(Buffer buffer){
        return    new BufferToImage ( (VideoFormat)buffer.getFormat() ).createImage(buffer);
    }
    public static Image getImage(Player player){
        Buffer buffer   =getFrameBuffer(player);
        return getImage(buffer);
    }
    
    
    
    
    
    public static Player getPlayer(CaptureDeviceInfo capturedeviceinfo) throws NoPlayerException, CannotRealizeException, IOException{
        return Manager.createRealizedPlayer ( capturedeviceinfo.getLocator());
    }
    public static Player getPlayer(MediaLocator medialocator) throws NoPlayerException, CannotRealizeException, IOException{
        return Manager.createRealizedPlayer ( medialocator);
    }
    public static Player getPlayer(URL url) throws NoPlayerException, CannotRealizeException, IOException{
        return Manager.createRealizedPlayer ( url);
    }
    public static Player getPlayer(DataSource datasource) throws NoPlayerException, CannotRealizeException, IOException{
        return Manager.createRealizedPlayer (datasource);
    }
 
    
    
}
