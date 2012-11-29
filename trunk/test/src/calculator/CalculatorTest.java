package calculator;

import khh.math.Calculator;
import khh.math.Calculator2;
import khh.math.SyntaxError;

public class CalculatorTest{
public static void main(String[] args) throws SyntaxError{
	Calculator c = new Calculator();
	 System.out.println( c.eval("1+5"));
	 Calculator2 cc = new Calculator2();
	 System.out.println( cc.eval("1+5"));
	 
	 
}
}
