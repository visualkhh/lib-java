import com.jaunt.*;
import com.jaunt.component.*;

public class Example3{
  public static void main(String[] args){
    try{
      UserAgent userAgent = new UserAgent(); 
      userAgent.setFilter(new Filter(){                       //subclass Filter to override default settings
   
        public boolean tagNameAllowed(String tagName){        //overriding callback method
          return tagName.matches("input|form");               //only allow tags named 'input' or 'form'
        }
 
        public boolean attNameAllowed(String tagName, String attName){ //overriding callback method
          return !attName.equalsIgnoreCase("checked");        //filter out attributes named 'checked'
        }
 
        public boolean attValueAllowed(String tagName, String attname, String attValue){//overriding callback method
          return !attValue.toLowerCase().startsWith("http");  //filter out attribute values starting with 'http'
        }
      });
      userAgent.visit("http://jaunt-api.com/examples_advanced/signup2.htm");            //open content 
      System.out.println("Filtered document:\n" + userAgent.doc.innerHTML());  //print filtered document.    
    }
    catch(JauntException e){
      System.err.println(e);
    }
  }
}