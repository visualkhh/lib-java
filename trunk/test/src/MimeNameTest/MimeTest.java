package MimeNameTest;

import java.io.File;

import khh.file.util.FileUtil;

public class MimeTest {
	public static void main(String[] args) {
		//String a = FileUtil.getMIMEType(new File("D:\\finger\\신한\\비즈파트너\\업무\\erp연동관련_확인작업.docx"));
		String a = FileUtil.getMIMEType(new File("c:\\TEMP"));
		System.out.println(a);
	}
}
