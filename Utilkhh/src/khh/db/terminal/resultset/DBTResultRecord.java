package khh.db.terminal.resultset;

import khh.std.adapter.AdapterMapBase;

//String columnName ,  String Data
public class DBTResultRecord extends AdapterMapBase<String, String> {


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
