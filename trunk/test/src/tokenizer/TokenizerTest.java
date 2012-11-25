package tokenizer;

public class TokenizerTest {
public static void main(String[] args) {
    String ph="010-50950425";
    java.util.StringTokenizer token2 = new java.util.StringTokenizer(ph, "-");
    
    System.out.println( token2.countTokens() );
    
   
    /*while(token2.hasMoreElements()){
        System.out.println(token2.nextToken());
    }*/
}
}
