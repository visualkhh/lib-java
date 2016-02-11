
import java.io.StringReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class JsonTest2 {
	public static void main(String[] args) {
		
		
		try {
			String jsonInfo = "{ \"data\":[ {\"date\":\"2015:05:29 19:16:23\",\"latlng\":\"37.66212036833167,127.04516300931573\", \"ele\":\"76.19999694824219\", \"atemp\":\"23.0\"},{\"date\":\"2015:05:29 19:16:24\",\"latlng\":\"37.662154817953706,127.0452713035047\", \"ele\":\"76.4000015258789\", \"atemp\":\"23.0\"} ] }";
//			String jsonInfo = "[{\"date\":\"2015:05:29 19:16:23\",\"latlng\":\"37.66212036833167,127.04516300931573\", \"ele\":\"76.19999694824219\", \"atemp\":\"23.0\"},{\"date\":\"2015:05:29 19:16:24\",\"latlng\":\"37.662154817953706,127.0452713035047\", \"ele\":\"76.4000015258789\", \"atemp\":\"23.0\"}]";
            JSONParser jsonParser = new JSONParser();
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
//            JSONArray jsonObject = (JSONArray) jsonParser.parse(new StringReader( jsonInfo));
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new StringReader( jsonInfo));
            //books의 배열을 추출 
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
 
            System.out.println("* DATA *");
 
            for(int i=0; i<dataArray.size(); i++){
 
                System.out.println("=DATA"+i+" ===========================================");
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                JSONObject dataObject = (JSONObject) dataArray.get(i);
                //JSON name으로 추출
                //System.out.println("Data: date==>"+dataObject.get("date"));
                
                dataObject.remove("date");
                dataObject.put("ggg", 14);
                System.out.println(dataObject);
                
//                dataObject.keySet().stream().forEach(e->{
//                	System.out.println(e);
//                });
 
            }
 
 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
