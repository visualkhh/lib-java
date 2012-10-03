package khh.collection;

import java.util.ArrayList;

public class DuplicationArrayList<T>  extends ArrayList<T>{

    public int getDuplicateCount(Object search){
        int cnt=0;
        for (int i = 0; i < size(); i++) {
            if(search.equals(get(i)) || search == get(i) )
                cnt++;
        }
        return cnt;
    }
    
    public ArrayList<T> getDuplication(){
        ArrayList<T> returnval = new ArrayList<T>();
        for (int i = 0; i < this.size(); i++) {  
            for (int j = 0; j < this.size(); j++) { 
                if(i==j)  {      //나랑같은건 비교하지마세요
                    continue;
                }
                if(this.get(i).equals(this.get(j)) || this.get(i)==this.get(j)){
                    boolean before_insert = false;
                    for (int j2 = 0; j2 < returnval.size(); j2++) {
                        if(returnval.get(j2).equals(get(i)) || returnval.get(j2)==get(i)){
                            before_insert = true;
                            break;
                        }
                    }
                    if(!before_insert){
                        returnval.add(get(i));
                    }
                }
            }
        }
        return returnval;
    }
    
    public ArrayList<T> getDeDuplication(){
        ArrayList<T> duplication_data = getDuplication();
        ArrayList<T> returnval = new ArrayList<T>();
        for (int i = 0; i < this.size(); i++) {
            boolean ismatching = false;
            for (int j = 0; j < duplication_data.size(); j++) {
                if ( this.get(i).equals(duplication_data.get(j)) ||  this.get(i) == duplication_data.get(j)){
                    ismatching = true;
                    break;
                }
            }
            if(!ismatching){
                returnval.add(this.get(i));
            }
        }
        return returnval;
    }
    
    
    public ArrayList<T> getDistinct(){
        ArrayList<T> returnval = new ArrayList<T>();
        for (int i = 0; i < this.size(); i++) {  
            
            boolean before_insert = false;
            for (int j2 = 0; j2 < returnval.size(); j2++) {
                if(returnval.get(j2).equals(get(i)) || returnval.get(j2)==get(i)){
                    before_insert = true;
                    break;
                }
            }
            
            if(!before_insert){
                returnval.add(get(i));
            }
            
        }
        return returnval;
    }
    
    
    
}
