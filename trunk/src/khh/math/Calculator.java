package khh.math;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * # 중위표기법 수식을 후위표기법 수식으로의 변환 과정
 *    1. '('를 만나면 '('과 이후의 연산자를 Stack에 Push 하고 피연산자는 출력 한다.
 *    2. 연산자를 만나면 자신보다 우선 순위가 낮은 연산자를 만날때 까지 Stack에 있는 연산자를 계속해서 Pop 하고 자신의 가장 낮은 연산자 일경우 Stack에 Push 한다.
 *    3. ')'를 만나면 '('가 Pop 될때 까지 Stack에 있는 연산자를 모두 Pop 하여 출력 한다.
 *    4. 피연산자는 출력 한다.
 *    5. 수식이 없으면 Stack에 남아있는 모든 연산자를 Pop하여 출력 한다.
 *    
 * # 후위표기법 수식의 연산 과정
 *    1. 피연산자는 Stack에 Push 한다.
 *    2. 연산자는 Stack에서 Pop을 두 번하여 두개의 피연산자와 연산자를 연산 후 Stack에 Push 한다.
 *        Stack에서 Pop을 두번 하여 연산자와 연산 할 때에는 첫번째 Pop을 통해 얻은 수치가 연산자의 뒤쪽에 위치하며
 *        두번째 Pop을 통해 얻은 수치를 연산자의 앞쪽에 위치하도록 하여 연산 한다.
 * 
 * @author evil
 *
 */
public class Calculator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String param = "(-0.5)*2";
        System.out.println("Infix : " + param);
        Calculator cal = new Calculator();
        Queue<String> que = cal.transformPostfix(param);
        Double resultVal = cal.calculatePostfix(que);
        System.out.println("결과 : " + resultVal);
//      System.out.println(1d+2d*3d/2d+(2d*3d)-(10d/(10d-(5d-3d)))+(20d-1d));
        
        
//      System.out.println("\n\n");
//      
        System.out.println(cal.eval(param));
    }
    
    
    
    public double eval(String exp){
//      System.out.println("Infix : " + exp);
        Queue<String> que = transformPostfix(exp);
        Double resultVal = calculatePostfix(que);
//      System.out.println("결과 : " + resultVal);
        return resultVal;
        
    }
    
    /**
     * 연산자 우선순위 지정
     */
    private Map<String,Integer> hashMap = new HashMap<String, Integer>();

    public Calculator() {
        hashMap.put("+", 0);
        hashMap.put("-", 0);
        hashMap.put("*", 1);
        hashMap.put("/", 1);
        hashMap.put("(", -1);
    }
    
    
    /**
     * 후위 표기식 변환
     * @param String
     * @return Queue
     * # 중위표기법 수식을 후위표기법 수식으로의 변환 과정
     *    1. '('를 만나면 '('과 이후의 연산자를 Stack에 Push 하고 피연산자는 출력 한다.
     *    2. 연산자를 만나면 자신보다 우선 순위가 낮은 연산자를 만날때 까지 Stack에 있는 연산자를 계속해서 Pop 하고 자신의 가장 낮은 연산자 일경우 Stack에 Push 한다.
     *    3. ')'를 만나면 '('가 Pop 될때 까지 Stack에 있는 연산자를 모두 Pop 하여 출력 한다.
     *    4. 피연산자는 출력 한다.
     *    5. 수식이 없으면 Stack에 남아있는 모든 연산자를 Pop하여 출력 한다.
     */
    public Queue<String> transformPostfix(String param) {
        if(param == null || param.trim().equals("")) 
            return null;
        
        /**
         * 스택 선언
         */
        Stack<String> stack  = new Stack<String>();
        
        /**
         * 후위 변환 수식 변수 선언
         */
        StringBuilder postfixStr = new StringBuilder();
        
        /**
         * 후위 변환 수식 Queue 선언
         */
        Queue<String> postfixQue = new LinkedList<String>();
        
        /**
         * 수식 패턴 지정
         */
//      Pattern p = Pattern.compile("[0-9,.]+|\\-[0-9,.]|\\(|\\)|\\+|\\-|\\*|\\/");
        Pattern p = Pattern.compile("[0-9,.]+|\\(|\\)|\\+|\\-|\\*|\\/");
        Matcher m = p.matcher(param);

        /**
         * 수식 분리 및 후위 표기식 변환 수행
         */
        while(m.find()){
            String word = m.group();
            if(word.equals("(")) {
                stack.push(word);
            }
            else if(hashMap.containsKey(word)) {
                while(true) {
                    if(stack.isEmpty() || hashMap.get(stack.peek()) < hashMap.get(word)) {
                        stack.push(word);
                        break;
                    } else {
                        String popStr = stack.pop();
                        postfixStr.append(popStr);
                        postfixQue.add(popStr);
                    }
                }
            }
            else if(word.equals(")")) {
                while(true) {
                    String popStr = stack.pop();
                    if(popStr.equals("(")) {
                        break;
                    } else {
                        postfixStr.append(popStr);
                        postfixQue.add(popStr);
                    }
                }
            } 
            else {
                postfixStr.append(word);
                postfixQue.add(word);
            }
        }
        
        while(stack.isEmpty() == false) {
            String popStr = stack.pop();
            postfixStr.append(popStr);
            postfixQue.add(popStr);
        }
        
        System.out.println("Postfix : " + postfixStr.toString());
        return postfixQue;
    }
    
    
    /**
     * 후위 표기식 연산
     * @param Queue
     * @return String 
     * # 후위표기법 수식의 연산 과정
     *    1. 피연산자는 Stack에 Push 한다.
     *    2. 연산자는 Stack에서 Pop을 두 번하여 두개의 피연산자와 연산자를 연산 후 Stack에 Push 한다.
     *        Stack에서 Pop을 두번 하여 연산자와 연산 할 때에는 첫번째 Pop을 통해 얻은 수치가 연산자의 뒤쪽에 위치하며
     *        두번째 Pop을 통해 얻은 수치를 연산자의 앞쪽에 위치하도록 하여 연산 한다.
     */
    public Double calculatePostfix(Queue<String> param) {
        Stack<Double> stack = new Stack<Double>();
        
        Double firstVal;
        Double secondVal;
        
        while(param.isEmpty() == false) {
            String word = param.remove();
            
            if(hashMap.containsKey(word)) {
                secondVal= stack.pop();
                firstVal = stack.pop();
                
                switch(word.charAt(0)) {
                    case '+' : stack.push(firstVal + secondVal);
                        break;
                    case '-' : stack.push(firstVal - secondVal);
                        break;
                    case '*' : stack.push(firstVal * secondVal);
                        break;
                    case '/' : stack.push(firstVal / secondVal);
                        break;
                    default : 
                        break;
                }
                
            } else {
                stack.push(Double.valueOf(word));
            }
        }
        return stack.pop();
        
    }

}
