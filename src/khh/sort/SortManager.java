package khh.sort;


public class SortManager <T>{


    public  T[][] toGroupArray(T[] data,int groupsize,T defaultdata,int align_type) {
        
        int rowsize = data.length/groupsize;
        int nam = data.length%groupsize;
        
       // System.out.println(rowsize+"     "+nam);
        
        if(nam>=1){
            rowsize+=1;
        }
//            System.out.println("-----"+data.length+"   nam: "+nam+"    "+groupsize+"       "+rowsize);
        T[][] return_val =  (T[][])new Object[rowsize][];
        for (int i = 0; i < rowsize; i++) {
            T[] input_data =  (T[])new Object[groupsize];
            int row_startindex = groupsize*i;
           // row_startindex=row_startindex<=0?0:row_startindex-1;
            int row_endindex = (groupsize);
            
         
            
            
//            System.out.println(input_data.length+"      "+row_startindex+"      "+row_endindex);
            try{
                input_data = sliceArray(data,row_startindex,row_endindex);
            }catch (IndexOutOfBoundsException e) {
                input_data = sliceArray(data,row_startindex,data.length-row_startindex);
            }
            
            return_val[i] =toArray(input_data,groupsize,defaultdata,align_type);
            
        }
        
       // System.out.println(rowsize+"    "+data.length+"      "+groupsize+"    "+nam );
        
        return return_val;
    }
    
    public static final int ALIGN_LEFT=1;
    public static final int ALIGN_RIGHT=2;
    public  T[] sliceArray(T[] data,int start_index,int length){
        T[] return_datal= (T[]) new Object[length];
        System.arraycopy(data, start_index, return_datal, 0,length );
        return return_datal;
    }
    
    public  T[] toArray(T[] data,int length,T defaultdata, int align_type){
        T[] retrun_data = (T[])new Object[length];
        for (int i = 0; i < retrun_data.length; i++) {
            retrun_data[i]=defaultdata;
        }
        if(align_type == ALIGN_LEFT){
            System.arraycopy(data, 0, retrun_data, 0, data.length);
        }else if(align_type == ALIGN_RIGHT){
            System.arraycopy(data, 0, retrun_data, retrun_data.length-data.length, data.length);
        }
        return retrun_data;
    }
    
    
 // hex to byte[]
    public static byte[] toByteArray(String hex_str) {
        if (hex_str == null || hex_str.length() == 0) {
            return null;
        }
        hex_str = hex_str.replaceFirst("^0x", "");
     
        byte[] ba = new byte[hex_str.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex_str.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
     
    
    
}
