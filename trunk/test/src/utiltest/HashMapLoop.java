package utiltest;

import java.util.HashMap;
import java.util.Iterator;

public class HashMapLoop {
    public static void main(String[] args) {

HashMap<String,String> data = new HashMap<String,String>();
data.put("1202","대청도");
data.put("1204","소청도");
data.put("1206","연평도");
data.put("1207","자월도");
data.put("4210","홍도 ");
data.put("7201","비양도");
data.put("4501","덕우도");
data.put("4503","여서도");
data.put("7101","가파도");
data.put("3104","비안도");
data.put("3108","연도 ");
data.put("3102","개야도");
data.put("3109","장자도");
data.put("3107","어청도");
data.put("2103","삽시도");
data.put("2104","외연도");
data.put("1205","승봉도");
data.put("1101","풍도 ");
data.put("2201","가의도");
data.put("4201","가거도");
data.put("4304","여자도");
data.put("5101","매물도");
data.put("5102","수우도");
data.put("5103","어의도");
data.put("5105","추도 ");
data.put("1208","문갑도");
data.put("2105","장고도");
data.put("2101","고대도");
data.put("4605","성남도");
data.put("4606","내병도");
data.put("4607","독거도");
data.put("2102","녹도 ");
data.put("2106","호도 ");
data.put("4401","낙월도");
data.put("4402","송이도");
data.put("4608","눌옥도");
data.put("4609","슬도 ");
data.put("4610","구자도");
data.put("4101","득량도");
data.put("4504","어룡도");
data.put("3202","왕등도");
data.put("4505","황제도");
data.put("4506","당사도");
data.put("4211","평사도");
data.put("4212","고사도");
data.put("4213","율도 ");
data.put("1102","육도 ");
data.put("4302","상화도");
data.put("1201","덕적도");
data.put("3201","위도 ");
data.put("4604","조도 ");
data.put("4301","거문도");
data.put("7202","추자도");
data.put("6101","울릉도");
data.put("6101","울릉도");
data.put("6101","울릉도");
data.put("1203","백령도");
data.put("4209","흑산도");
data.put("4303","손죽도");
data.put("4305","초도 ");
data.put("4306","하화도");
data.put("4307","평도 ");
data.put("1209","울도 ");
data.put("4102","시산도");
data.put("4611","화도 ");
      Iterator iter = data.keySet().iterator();
        while(iter.hasNext()){
            String key =(String) iter.next();
           System.out.println("key:"+key+","+ (String) data.get(key));
        }
        
    
    }
}
