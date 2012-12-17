package khh.runtime.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import khh.property.util.PropertyUtil;

public class RunTimeUtil{
	// 프로그램이 강제 종료 될때 실행되는거!! 컨트롤+C
	public static void addShutdownHook(Thread tread) throws Exception{
		Runtime.getRuntime().addShutdownHook(tread);
	};

	// 시스템명령어 내리는거
	public static String execute(String commend) throws IOException{
		Process result = Runtime.getRuntime().exec(commend);
		BufferedReader output = new BufferedReader(new InputStreamReader(result.getInputStream()));
		StringBuffer returnvalue = new StringBuffer();
		String inputLine;

		int count = 0;
		while((inputLine = output.readLine()) != null){
			// System.out.println(inputLine);
			if(count != 0)
				returnvalue.append(PropertyUtil.getSeparator());

			returnvalue.append(inputLine);
			count++;
		}
		return returnvalue.toString();
	};

}
