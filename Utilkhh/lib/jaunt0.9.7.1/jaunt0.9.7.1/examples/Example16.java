import com.jaunt.*;
import com.jaunt.component.*;
import java.util.*;

public class Example16{
  public static void main(String[] args){
    try{
      UserAgent userAgent = new UserAgent();
      userAgent.visit("http://jaunt-api.com/examples/stocks.htm");
   
      //technique #1 (no indexes)
      Element table = userAgent.doc.findFirst("<table class=stocks>");  //find table element
      Elements tds = table.findEach("<td|th>");                         //find non-nested td/th elements
      for(Element td: tds){                                             //iterate through td/th's
        System.out.println(td.outerHTML());                             //print each td/th element
      }
   
      //technique #2 (with indexes)                                                                
      System.out.println("-----------");                                //find table's immediate child trs     
      List<Element> trs = userAgent.doc.findFirst("<table class=stocks>").getEach("<tr>").toList();
      for(int i=0; i < trs.size(); i++){                                //iterate through trs
        Element tr = trs.get(i);                                 
        List<Element> tdList = tr.getEach("<td|th>").toList();          //get immediate child td/th's for each tr
        for(int j=0; j < tdList.size(); j++){                           //iterate through td/th's
          Element td = tdList.get(j);
          System.out.println("row " + i + " col " + j + ":" + td.outerHTML());  //print indexes and td/th
        }
      }
    }
    catch(JauntException e){
      System.err.println(e);
    }
  }
}