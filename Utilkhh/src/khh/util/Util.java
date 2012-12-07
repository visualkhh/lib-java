package khh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import khh.conversion.util.ConversionUtil;

public class Util {
    
    /*
    public String getParameter(String parameterName, String[] args) {
        String returnval = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if ( arg.indexOf(parameterName) >= 0 ) {
                try {
                    returnval = args[i + 1];
                } catch (Exception e) {
                    returnval = "";
                }
            }
        }
        return returnval;
    }

    public String getDefaultValue(String value) {
        return getDefaultValue(value, "");
    }

    public String getDefaultValue(String value, String defaultvalue) {
        if ( value == null )
            return defaultvalue;
        return value;
    }
*/
    
    
    
//    // String rex="^one cat.*.yard"; . 찍어서 붙쳐줘야함. 오리지널 문자로바꾸는건 \\. 이런식
//    public static boolean isFind(String rex, String str) {
//        Pattern p = Pattern.compile(rex);
//        Matcher m = p.matcher(str);
//        return m.find();
//    }



    //avg
    public static double getAvg(ArrayList<Double> data){
        return getAvg(ConversionUtil.toDoubleArray(data));
    }
    //sum
    public static double getSum(ArrayList<Double> data){
     return getSum(ConversionUtil.toDoubleArray(data));
    }
    
    
    //비교 및 큰값 작은값  min max sum
    public static double getMax(ArrayList<Double> data){
        return getMax(ConversionUtil.toDoubleArray(data));
    }
    public static double getMin(ArrayList<Double> data){
        return getMin(ConversionUtil.toDoubleArray(data));
    }
    
    
    //avg
    public static double getAvg(double [] data){
        return getSum(data)/data.length;
    }
    //sum
    public static double getSum(double [] data){
        double maxValue = 0;
        for(int i = 0 ; i <data.length ;  i++){
            maxValue +=data[i];
        }
        return maxValue;
    }
    
    
    //비교 및 큰값 작은값  min max sum
    public static double getMax(double [] data){
        return data[getMaxIndex(data)];
    }
    public static double getMin(double [] data){
        return data[getMinIndex(data)];
    }
    
    
    //큰값 인덱스 작은값인덱스
    public static int getMaxIndex(double [] data){
        int index=data.length-1;
        double maxValue = Integer.MIN_VALUE;
        for(int i = 0 ; i <data.length ;  i++){
            if(maxValue != Math.max(data[i], maxValue)){
                maxValue = Math.max(data[i], maxValue);
                index = i;
            }
        }
        return index;
    }
    public static int getMinIndex(double [] data){
        int index= 0;
        double minValue = Integer.MAX_VALUE;
        
        for(int i = 0 ; i <data.length ;  i++){
            if(minValue != Math.min(data[i], minValue)){
                minValue = Math.min(data[i], minValue);
                index=i;
            }
        }
        return index;
    }
    
    
    public static ArrayList getDeDuplication(ArrayList item){
        Object[] object=  new Object[item.size()];
        for(int  i = 0;  i < item.size(); i ++)
            object[i] = (item.get(i));
        
        
        Object[] returnobject = getDeDuplication(object);
        System.out.println(object.length+"    "+returnobject.length);
        
        ArrayList returnvalue = new ArrayList();
        for(int i = 0 ; i < returnobject.length;i++){
            returnvalue.add(returnobject[i]);
        }
        return returnvalue;
    }
    public static Object[] getDeDuplication(Object[] item){
      HashMap<Object, Integer> hm = new HashMap<Object,Integer>();
      for(int  i = 0 ; i < item.length;i++){
          hm.put(item[i], i);
      }
      Object[] returnvalue = new Object[hm.size()];
       Iterator<Object> iter = hm.keySet().iterator();
       int index=0;
       while(iter.hasNext())
       {
           returnvalue[index]   = iter.next();
           index++;
       }
       Arrays.sort(returnvalue);
        return returnvalue;
    }
    
    //random
    public static double[] getRandom(double standardValue, int toleranceValue,int wantsize){
        Random random = new Random();
        double [] randomvalue=new double[wantsize];
        for (int i = 1; i <= wantsize; i++) {
            double d =standardValue+ random.nextInt(toleranceValue*2)+Math.random();
            randomvalue[i-1]=d-toleranceValue;
          }
        return randomvalue;
    }
    
    
    // int형 모든 범위에서 랜덤하게
    public static int getRandomInt(){
        return new Random().nextInt();  
    }
    public static int getRandomInt(int size){
        return new Random().nextInt(size);  
    }
    public static float getRandomFloat(){
    return    new Random().nextFloat();  
    }
    
 // true(참), false(거짓) 중의 하나를 랜덤하게 출력
    public static boolean getRandomBoolean(){
        return    new Random().nextBoolean();
    }
    
    
    
    public static String executeSystem(String commend) throws IOException{
        Process result = Runtime.getRuntime().exec(commend); 
        BufferedReader output = new BufferedReader(new InputStreamReader(result.getInputStream())); 
        String returnvalue ="";
        String inputLine;
        
        int count=0;
        while ((inputLine = output.readLine()) != null) {
//          System.out.println(inputLine);
            if(count!=0)
                returnvalue += '\n';
            
            returnvalue += inputLine;
            count++;
        }
        return returnvalue;
    };
    
    //finger
    //프로그램이 강제 종료 될때  실행되는거!!  컨트롤+C
    public static void addShutdownHook(Thread tread) throws Exception {
        Runtime.getRuntime().addShutdownHook(tread);
    };
    
    
    
    public static Object[][] splitGroup(ArrayList data,
            int splitSize) {
        
        Object[] temp = new Object[data.size()];
        for(int i = 0 ; i < temp.length;i++)
        temp[i] = data.get(i);
        return splitGroup(temp, splitSize);
    }
    public static Object[][] splitGroup(Object[] data,
            int splitSize) {
        
        int rowIndex=0;
        int lastColumIndex = splitSize;
        
        for(int i =  0 ; i  <data.length;i++){
            //개씩끊기위해 4에 나머지없을때 생성해서
            int columIndex = i%splitSize;
            lastColumIndex = columIndex;
            
            if(columIndex+1 == splitSize && i+1 <data.length){
                rowIndex++;
            }
        }
        
        Object[][] temp=new Object[rowIndex+1][];
        
        for(int i = 0 ; i<temp.length;i++){
            if(i+1 == temp.length){
                temp[i]=new Object[lastColumIndex+1];
            }else{
                temp[i]=new Object[splitSize];
            }
        }
        
        try{
        rowIndex=0;
        for(int i =  0 ; i  <data.length;i++){
            //개씩끊기위해 4에 나머지없을때 생성해서
            int columIndex = i%splitSize;
            temp[rowIndex][columIndex]=(Object)data[i];
            
            if(columIndex+1 == splitSize){
                rowIndex++;
            }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }
    
    
  //아이디 안겹치게 분배.
    static int value=0;
    public synchronized static int getNextNumber(){
        return ++value;
    }
    
    synchronized public static String getTimeUnikey() {

		/*
		 * Unikey 생성 로직
		 * DATETIME(yyyyMMddHHmmssSSS;17) + IncNum(000~999;3)
		 */

		Object[] objs = { new Integer((getNextNumber()) % 1000)};
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(
			new java.util.Date())
			+ new MessageFormat("{0,number,000}").format(objs);

	}
    
    
    public static boolean isTimeOver(long start_mm,long daly_mm){
        long time = System.currentTimeMillis()  -  start_mm;
        if(time>daly_mm){
            return true;
        }else{
            return false;
        }
    }
    
    public void arraycopy(Object oriArray,int startOriArrayIndex,Object outputArray,int startOutIndex,int endOutIndex){
    	System.arraycopy(oriArray, startOriArrayIndex, outputArray, startOutIndex, endOutIndex);
    }
    
}
