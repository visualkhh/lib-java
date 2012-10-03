package khh.db.resultset;

import khh.std.adapter.Adapter_Base;

//String columnName ,  String Data
public class ResultRow extends Adapter_Base<String, String> {


    @Override
    public Integer conversionInt(String value) throws Exception {
        if(value==null)
            return null;
        return Integer.parseInt(value);
    }
    
    @Override
    public Float conversionFloat(String value) throws Exception {
        if(value==null)
            return null;
        return Float.parseFloat(value);
    }
    @Override
    public Double conversionDouble(String value) throws Exception {
        if(value==null)
            return null;
        return Double.parseDouble(value);
    }
    
    @Override
    public Boolean conversionBoolean(String value) throws Exception {
        if(value==null)
            return null;
        return Boolean.parseBoolean(value);
    }
    
    @Override
    public String conversionString(String value) throws Exception {
        if(value==null)
            return null;
        return value;
    }
}
