public class dcinside_2 {
    
    /*
      2. int find_sole_one(int *arr, size_t n);
        복잡하게 하기 귀찮으니까. n은 무조건 홀수야. 에러처리 안해도 됨.
        arr에는 n/2 개의 수가 2개씩, 딱 한 수만 한개 있어.
        그니까 {1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6} 뭐 이런식임. 물론 순서는 섞여있는거고.
        내가 귀찮아서 오름차순으로 쓴거임.
        저기서 4 처럼. 두개 있지 않고 딱 하나만 있는걸 찾아봐.
        
        //ㄴ 1, 1, 5, 5, 31337, 3, 2, 3, 2 뭐 이런것도 되야함
        //쌍으로안나옴
     */
    
    public static void main(String[] args) {
        Integer [] i={8,7,8,6,5,6,5,3,9,2,14,2,3,9,7};
        ex2(i,i.length/2);
    }
    
    public static void ex2(Integer[] arr,int n){

        int position=0;
        int good=-1;

        
        
        
        for (int i = 0; i < arr.length ; i++) {
            
            
            if(arr[i]!=null){
                good=i;
            }
            
          if(position!=i &&  arr[i]!=null ){

              if(arr[position]==arr[i]){
                  arr[position]=null;
                  arr[i]=null;
                  position++;
                  i=-1;
              }
              
          }
          
          if(arr[position]==null){
              position++;
          }
          
            
        }
        
        System.out.println("index "+good+"   result : "+arr[good]);
        
        

        
        
   
    }
    
}
