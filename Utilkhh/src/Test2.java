import khh.string.util.StringUtil;

public class Test2 {
	public static void main(String[] args) {
		String a = "/view/log/form/map.form";
		String aa = StringUtil.transRegex(a, "${/WEB-INF}.*\\.${jsp}");
//		String aa = StringUtil.transRegex(a, ".*");
		System.out.println(aa);
//		System.out.println(StringUtil.escapeCharToregexMetaChar(p));
		//System.out.println(StringUtil.isMatches(a,p));
	}
}
