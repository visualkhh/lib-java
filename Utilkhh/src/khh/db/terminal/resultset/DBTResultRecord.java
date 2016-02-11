package khh.db.terminal.resultset;

import khh.std.adapter.AdapterMapBase;

//String columnName ,  String Data
public class DBTResultRecord extends AdapterMapBase<String, String> {


    @Override
    public Integer getInt(String value) throws ClassCastException {
        if(value==null)
            return null;
        return Integer.parseInt(getString(value));
    }
    @Override
    public Long getLong(String value) throws ClassCastException {
    	if(value==null)
    		return null;
    	return Long.parseLong(getString(value));
    }
    
    @Override
    public Float getFloat(String value) throws ClassCastException {
        if(value==null)
            return null;
        return Float.parseFloat(getString(value));
    }
    @Override
    public Double getDouble(String value) throws ClassCastException {
        if(value==null)
            return null;
        return Double.parseDouble(getString(value));
    }
    
    @Override
    public Boolean getBoolean(String value) throws ClassCastException {
        if(value==null)
            return null;
        return Boolean.parseBoolean(getString(value));
    }
    

}
