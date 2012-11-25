package khh.communication.network.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkUtil {
    //network
    public static String getHostAddress() throws SocketException {
        String ip = null;
        Enumeration<NetworkInterface> ne;
//      try {
            ne = NetworkInterface.getNetworkInterfaces();
//      } catch (SocketException e) {
//          e.printStackTrace();
//          return null;
//      }
        while ( ne.hasMoreElements()) {
             NetworkInterface ni = ne.nextElement();
//           System.out.println("Name : " + ni.getName());
             Enumeration<InetAddress> inets = ni.getInetAddresses();
             while(inets.hasMoreElements()){
                  InetAddress iaddr = inets.nextElement();
                  byte[] ar = iaddr.getAddress();
//                System.out.println(ar[0]+"    "+ar[1]);
                  if(ar[0]==127 || ar[2]<=0)
                      continue;
                  
                  ip = iaddr.getHostAddress();
                  
             }
        }
        return ip;
        
    }
    //network
    public static String getHostName() throws SocketException {
        String ip = null;
        Enumeration<NetworkInterface> ne;
//      try {
            ne = NetworkInterface.getNetworkInterfaces();
//      } catch (SocketException e) {
//          e.printStackTrace();
//          return null;
//      }
        while ( ne.hasMoreElements()) {
             NetworkInterface ni = ne.nextElement();
//           System.out.println("Name : " + ni.getName());
             Enumeration<InetAddress> inets = ni.getInetAddresses();
             while(inets.hasMoreElements()){
                  InetAddress iaddr = inets.nextElement();
                  byte[] ar = iaddr.getAddress();
//                System.out.println(ar[0]+"    "+ar[1]);
                  if(ar[0]==127 || ar[2]<=0)
                      continue;
                  
                  ip = iaddr.getHostName();
                  
             }
        }
        return ip;
        
    }
    public static String getIp(String domainAddress) throws UnknownHostException {
        InetAddress address, myAddress;
            address = InetAddress.getByName(domainAddress);
            myAddress = InetAddress.getLocalHost();
            
//          System.out.println(domainAddress+" 도메인 이름과 주소는 " + address);
//          System.out.println("현재 호스트의 주소는 " + myAddress);
            return address.getHostAddress();
    }
    
    
    public static boolean ping(String ip,int respon_mmTime) {
        boolean sw = false;
            try {
                 
                InetAddress pingcheck = InetAddress.getByName(ip);
                sw = pingcheck.isReachable(respon_mmTime);
                System.out.println(sw);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sw;
    }
    
    
}
