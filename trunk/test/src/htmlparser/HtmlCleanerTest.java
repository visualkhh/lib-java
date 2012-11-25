package htmlparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.xpath.XPathExpressionException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.xml.sax.SAXException;

import com.kdt.util.xml.XMLparser;
import com.khm.io.MemoryInputStream.MemoryInputStream;
import com.khm.io.MemoryOutputStream.MemoryOutputStream;

public class HtmlCleanerTest {
    public static void main(String[] args) throws MalformedURLException, IOException, SAXException, XPathExpressionException, NoClassDefFoundError {
        long start_time = System.currentTimeMillis() ;
        System.out.println(start_time);
       /* CleanerProperties props = new CleanerProperties();
        SimpleHtmlSerializer htmlSerializer = new SimpleHtmlSerializer(props);
        String url="http://naver.com";
        String fileName ="naver.xml";
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode tagNode = htmlCleaner.clean(new URL(url));
        htmlSerializer.writeToFile(tagNode, fileName, "utf-8");
        */
        
        CleanerProperties props = new CleanerProperties();
     // set some properties to non-default values
    // props.setTranslateSpecialEntities(true);
  //   props.setTransResCharsToNCR(true);
//     props.setOmitComments(true);
      
     // do parsing
     TagNode tagNode = new HtmlCleaner(props).clean(
         new URL("http://www.chinadaily.com.cn/")
//         new URL("http://beenpage.com/?type=sentence&word=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD+%EC%A0%9C19%EB%8C%80+%EA%B5%AD%ED%9A%8C")
     );
      
     // serialize to xml file
     new PrettyXmlSerializer(props).writeToFile(
         tagNode, "chinadaily.xml", "utf-8"
     );
     
     
    // PrettyXmlSerializer s = new PrettyXmlSerializer(props);
    // s.writeToFile(tagNode, "good.xml");
     
     
     
     PrettyXmlSerializer s = new PrettyXmlSerializer(props);
     MemoryOutputStream outputstream = new MemoryOutputStream();
      s.writeToStream(tagNode, outputstream);
//    s.writeToFile(tagNode, "good.xml");
      
      
      
      
   /*   for (int i = 0; i <  outputstream.getBuffer().size(); i++) {
        System.out.print((char)(int)outputstream.getBuffer().get(i));
      }
      */
//      
//      
//      System.out.println("-----------");
//      
//      for (int i = 0; i <  outputstream.getBuffer().size(); i++) {
//          System.out.print((char)(int)outputstream.getBuffer().get(i));
//        }
      
      System.out.println(outputstream.getString());
      
      long end_time = System.currentTimeMillis() ;
      
     //XMLparser parser = new XMLparser("good.xml");
     //parser.setInputStremStream(new MemoryInputStream(outputstream.getBuffer()));
//      MemoryInputStream i = new MemoryInputStream(outputstream.getBuffer());
//       XMLparser parser = new XMLparser(i);
      //XMLparser parser = new XMLparser(new FileInputStream(new File("good.xml")) );
     
     
     //System.out.println( parser.getString("//head") );
     
     
       end_time= System.currentTimeMillis() ;
     
     
      System.out.println(start_time+"   "+end_time +"    =  "+(end_time-start_time));
     
     
     
     
    }
}