package khh.math;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class Calculator2{
	private StreamTokenizer lexer;

	public double eval(String exp) throws SyntaxError{
		return parse(exp);
	}

	private double parse(String ex) throws SyntaxError{
		try{
			lexer = new StreamTokenizer(new StringReader(ex));
			lexer.eolIsSignificant(true);
			lexer.ordinaryChar('/');
			lexer.ordinaryChar('-');
			lexer.ordinaryChar('*');
			lexer.nextToken();
			double value = expression();
			return value;
		}catch(Exception e){
			throw new SyntaxError(e.toString());
		}
	}

	private double expression() throws IOException, SyntaxError{
		double arg1 = 0, arg2 = 0;
		arg1 = term();
		switch(lexer.ttype){
		case '+':
			lexer.nextToken();
			arg2 = expression();
			arg1 = arg1 + arg2;
			break;
		case '-':
			lexer.nextToken();
			arg2 = expression();
			arg1 = arg1 - arg2;
			break;
		case ')':
		case StreamTokenizer.TT_EOF:
			break;
		default:
			error();
		}
		return arg1;
	}

	private double term() throws IOException, SyntaxError{
		double arg1 = 0, arg2 = 0;
		arg1 = factor();
		switch(lexer.ttype){
		case '*':
			lexer.nextToken();
			arg2 = term();
			arg1 = arg1 * arg2;
			break;
		case '/':
			lexer.nextToken();
			arg2 = term();
			arg1 = arg1 / arg2;
			break;
		case '+':
		case '-':
		case ')':
		case StreamTokenizer.TT_EOF:
			break;
		default:
			error();
		}
		return arg1;
	}

	private double factor() throws IOException, SyntaxError{
		double arg1 = 0;
		switch(lexer.ttype){
		case StreamTokenizer.TT_NUMBER:
			arg1 = lexer.nval;
			lexer.nextToken();
			break;
		case '(':
			lexer.nextToken();
			arg1 = expression();
			if(lexer.ttype != ')')
				throw new SyntaxError("괄호가 맞지 않음");
			lexer.nextToken();
			break;
		case StreamTokenizer.TT_WORD:
			String funName = lexer.sval;
			lexer.nextToken();
			if(lexer.ttype != '(')
				throw new SyntaxError("함수 형태가 맞지 않");
			lexer.nextToken();
			double temp = expression();
			if(lexer.ttype != ')')
				throw new SyntaxError("괄호가 맞지 않음");
			if(funName.equalsIgnoreCase("sqrt")){
				arg1 = Math.sqrt(temp);
			}else if(funName.equalsIgnoreCase("log")){
				arg1 = Math.log(temp);
			}else if(funName.equalsIgnoreCase("log10")){
				arg1 = Math.log10(temp);
			}else if(funName.equalsIgnoreCase("sin")){
				arg1 = Math.sin(temp);
			}else if(funName.equalsIgnoreCase("cos")){
				arg1 = Math.cos(temp);
			}else if(funName.equalsIgnoreCase("tan")){
				arg1 = Math.tan(temp);
			}else{
				throw new SyntaxError("구현되지 않은 함수");
			}
			lexer.nextToken();
			break;
		default:
			error();
		}
		return arg1;
	}

	private void error() throws SyntaxError{
		switch(lexer.ttype){
		case StreamTokenizer.TT_NUMBER:
			throw new SyntaxError("맞지않은 숫자: " + lexer.nval);
		case StreamTokenizer.TT_WORD:
			throw new SyntaxError("맞지않은 문자: " + lexer.sval);
		default:
			throw new SyntaxError("맞지않은 문자: " + (char)lexer.ttype);
		}
	}

}
