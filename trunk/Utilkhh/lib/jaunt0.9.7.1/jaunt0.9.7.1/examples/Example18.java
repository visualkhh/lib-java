import com.jaunt.*;
import com.jaunt.component.*;

public class Example18{
  public static void main(String[] args) throws ResponseException{
    UserAgent userAgent = new UserAgent(); 
    userAgent.setProxyHost("3.14.159.68");             //specify the proxy host (ip address)
    userAgent.setProxyPort(8080);                      //specify the proxy port
                                                       //visit a URL through the proxy
    userAgent.visit("http://amazon.com");               
    System.out.println(userAgent.doc.innerXML());      //print the retrieved document
  }
}