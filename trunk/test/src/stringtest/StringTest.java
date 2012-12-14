package stringtest;

import java.util.ArrayList;


import khh.collection.StandardArrayList;
import khh.sort.SortUtil;
import khh.sort.comparator.CompareBase;
import khh.sort.comparator.CompareInteger;
import khh.sort.comparator.CompareIntegerStandard;
import khh.std.Standard;
import khh.std.adapter.AdapterMapBase;
import khh.string.util.StringUtil;




public class StringTest {
    public static void main(String[] args) throws Exception {
        
        
        AdapterMapBase<String, String> param = new AdapterMapBase<String, String>() {
        };
        param.add("a", "1");
        param.add("b", "2");
        param.add("c", "3");
        
       String sql="select * from a where  a=#a# and b=#b#  and c=#c# and a=#a#   and  and c=#c#";

       
       StandardArrayList<Integer,Object> datafull = new StandardArrayList<Integer,Object>();
       
       
       for (int i = 0; i < param.size(); i++) {
           String findstr="#"+param.getKey(i)+"#";
           ArrayList<Integer> size = StringUtil.getFindIndex(findstr, sql);
          System.out.println( findstr +"  "+ sql.indexOf(findstr)+"    "+size.size() );
          
          for (int j = 0; j < size.size(); j++) {
            System.out.println(size.get(j));
            datafull.add(new Standard<Integer, Object>(size.get(j),param.get(i)));
          }
          
          sql = StringUtil.replaceAll(sql, findstr, "?");
       }
       
       
       
       
       
       System.out.println();
       SortUtil.sort(datafull, new CompareIntegerStandard(CompareBase.TYPE_DESC));
       for (int j = 0; j < datafull.size(); j++) {
           System.out.println(j+" "+datafull.get(j).getKey()+"  "+datafull.get(j).getValue());
       }
       
       System.out.println();
       System.out.println(sql);
        
       /* 
        */
        
        
        
        
        /*
        
        String sql="select * from a where  a=#a# and b=#b#  and c=#c# and a=#a#";
        sql="#aaa#aaaa#";
        
        
        String findstr="#";
        int findindex =-1;
        int nextindex =0;
        while((findindex = sql.indexOf(findstr,nextindex))!=-1){
            nextindex=findindex+1;
            System.out.println(findindex);
        }
        
        
//        Pattern p = Pattern.compile("^#");
        Pattern p = Pattern.compile("a+");
        Matcher m = p.matcher(sql);
        System.out.println( m.find());
        System.out.println( m.groupCount());
        System.out.println( m.group());
        System.out.println( m.regionStart());
        System.out.println( m.regionEnd());
        System.out.println( m.start(1));
        */
        
        
        
    }

}
