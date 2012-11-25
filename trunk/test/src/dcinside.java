public class dcinside {
    
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
        //685123947
        int [] i={8,7,8,6,5,6,5,9,2,10,2,3,10,9,7};
        ex2(i,i.length/2);
    }
    
    public static void ex2(int[] arr,int n){
        System.out.println("arr.length: "+arr.length+"     n : "+n);
        
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print("\t"+ arr[i]);
        }
        
        System.out.println();
        
        
        
        
        int jump_position=0;
        int good=-1;
        int sum=0;
        
    /*    while (position<arr.length) {
            System.out.print("\t"+ arr[position]);
            position++;
            
        }
     */
        
        
        
        for (int i = 0; i < arr.length ; i++) {

            //System.out.println("i: "+i+ " position: "+position);
            
        
            
            
            if(jump_position !=i){
                sum+= arr[i];
            }else{
              //  System.out.println(position+ "low"+i);
            }
            
      
            
            
            if((i+1)>=arr.length){
                
                System.out.println("i:"+i+ "\tjumpposition:"+jump_position+"\tsum:"+sum+ "\tsum/2:"+sum/2 +"\tsum%2:"+sum%2+"\tarr[jumpposition]"+arr[jump_position]);
               
                
                /*if((sum/2)==1 && (sum%2) ==0){
                    good=position;
                }else{
                }*/
                
                
                sum=0;
                jump_position++;
                i=0;
                
            }
            
            
      
            
            if(jump_position>=arr.length){
                break;
            }
            
            
        }
        
        
        
        
        
        
        //System.out.println("index "+good+"   result : "+arr[good]);
        
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print("\t"+ arr[i]);
        }
        
        
       // System.out.println(n+"  "+sum);
    }
    
}
