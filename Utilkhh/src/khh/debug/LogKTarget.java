package khh.debug;

import java.io.OutputStream;

//<!-- Level can be  A, D, I, W, E, F, O -->
//<!--
//
//파일명은 logk 로시작해서 .xml으로 끝나야한다     클래스페스에 logk 폴더안에 생성되어야한다.
//logk는 다양한 로깅레벨을 지원합니다.
//
// O = OFF
// A = ALL
//① F = FATAL : 가장 크리티컬한 에러가 일어 났을 때 사용합니다.   
//② E = ERROR : 일반 에러가 일어 났을 때 사용합니다.
//③ W = WARN : 에러는 아니지만 주의할 필요가 있을 때 사용합니다.
//④ I = INFO : 일반 정보를 나타낼 때 사용합니다.
//⑤ D = DEBUG : 일반 정보를 상세히 나타낼 때 사용합니다.
//
//Pattern to output
//	%d : date
//	%l : priority (level)
//	%c : class,name   category (where the log is from) fullclassname 
//	%k : filename
//	%m : message
//	%n : line_number
//	%e : exception message
//	%f : MethodName
//	%r : EnterChar
//	category는 자바형  정규식으로 가능하다. =		^com\\.[A-Za-z0-9\\.\\@_\\-~#]+\\.hhk
//	
//		target > loger > outputstream 의 기본값은   System.out
//	java.io.OutputStream 를 상속받은 애들을... 가따놔야한다.
//-->
//<!--logkattribute 속성은 마지막파일에만 적용된속성값을 가짐 한곳에서만 적어주길원합니다. -->
public class LogKTarget{
	public static final String ALL 		= "A";
	public static final String OFF 		= "O";
	public static final String FATAL 	= "F";
	public static final String ERROR 	= "E";
	public static final String WARN 	= "W";
	public static final String INFO 	= "I";
	public static final String DEBUG 	= "D";

	String loger_dateformat 	= null;
	String loger_logformat 		= null;
	String[] loger_level 		= null;
	String saver_dateformat 	= null;
	Boolean saver_save 			= null;
	String saver_savepath 		= null;
	Boolean saver_append 		= null;
	String saver_filename 		= null;
	Boolean exception_stacktrace= null;

	String id					= null;
	String category				= null;
	String extends_				= null;
	OutputStream outputstream 	= System.out;

	public String getLoger_dateformat(){
		return loger_dateformat;
	}

	public void setLoger_dateformat(String loger_dateformat){
		this.loger_dateformat = loger_dateformat;
	}

	public String getLoger_logformat(){
		return loger_logformat;
	}

	public void setLoger_logformat(String loger_logformat){
		this.loger_logformat = loger_logformat;
	}

	public String getSaver_dateformat(){
		return saver_dateformat;
	}

	public void setSaver_dateformat(String saver_dateformat){
		this.saver_dateformat = saver_dateformat;
	}

	public String getSaver_savepath(){
		return saver_savepath;
	}

	public void setSaver_savepath(String saver_savepath){
		this.saver_savepath = saver_savepath;
	}

	public String getSaver_filename(){
		return saver_filename;
	}

	public void setSaver_filename(String saver_filename){
		this.saver_filename = saver_filename;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getCategory(){
		return category;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getExtends(){
		return extends_;
	}

	public void setExtends(String extends_){
		this.extends_ = extends_;
	}

	public Boolean isSaver_save(){
		return saver_save;
	}

	public void setSaver_save(Boolean saver_save){
		this.saver_save = saver_save;
	}

	public Boolean isSaver_append(){
		return saver_append;
	}

	public void setSaver_append(Boolean saver_append){
		this.saver_append = saver_append;
	}

	public String[] getLoger_level(){
		return loger_level;
	}

	public void setLoger_level(String[] loger_level){
		this.loger_level = loger_level;
	}

	public Boolean getException_stacktrace(){
		return exception_stacktrace;
	}

	public void setException_stacktrace(Boolean exception_stacktrace){
		this.exception_stacktrace = exception_stacktrace;
	}

	public OutputStream getOutputstream(){
		return outputstream;
	}

	public void setOutputstream(OutputStream outputstream){
		this.outputstream = outputstream;
	}

}
