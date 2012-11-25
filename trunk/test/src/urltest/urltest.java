package urltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class urltest {
    public static void main(String[] args) throws MalformedURLException, IOException {
      String  strUrl = "http://kms.kepco.co.kr/km/openAPI/connectXML.asp";
      String  strKind="aa";
        HttpURLConnection con = (HttpURLConnection)new URL(strUrl +"?datacnt=4&datakind="+ strKind +"&CATA_CD=7000" ).openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);

        con.setRequestMethod("GET");
        //response.setStatus(con.getResponseCode());
        System.out.println( con.getResponseCode() );
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
String input=null;
String strb=null;
        while((input = reader.readLine()) != null)  strb = strb + input;
        
        System.out.println(strb);
        
    }
}
