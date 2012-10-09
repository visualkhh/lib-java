package khh.web.request;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * HTTP 요청을 전송한 후, 응답을 받아오는 유틸리티 클래스
 * <p>
 * GET 방식과 POST 방식으로 데이터를 전송해주며
 * POSTE 방식의 경우 multipart/form-date 인코딩 방식도
 * 지원해준다.
 *
 * @author 최범균 [era13@korea.com]
 */
public final class HttpRequestor {
	public static final String CRLF = "\r\n";
    
    /**
     * 연결할 URL
     */
    private URL targetURL;
    
    /**
     * 파라미터 목록을 저장하고 있다.
     * 파라미터 이름과 값이 차례대로 저장된다.
     */
    private ArrayList list;
    
    /**
     * HttpRequest를 생성한다.
     * 
     * @param target HTTP 메시지를 전송할 대상 URL
     */
    public HttpRequestor(URL target) {
        this(target, 20);
    }
    
    /**
     * HttpRequest를 생성한다.
     * 
     * @param target HTTP 메시지를 전송할 대상 URL
     */
    public HttpRequestor(URL target, int initialCapicity) {
        this.targetURL = target;
        this.list = new ArrayList(initialCapicity);
    }
    
    /**
     * 파라미터를 추가한다.
     * @param parameterName 파라미터 이름
     * @param parameterValue 파라미터 값
     * @exception IllegalArgumentException parameterValue가 null일 경우
     */
    public void addParameter(String parameterName, String parameterValue) {
        if (parameterValue == null) 
        throw new IllegalArgumentException("parameterValue can't be null!");
        
        list.add(parameterName);
        list.add(parameterValue);
    }
    
    /**
     * 파일 파라미터를 추가한다.
     * 만약 parameterValue가 null이면(즉, 전송할 파일을 지정하지 않는다면
     * 서버에 전송되는 filename 은 "" 이 된다.
     * 
     * @param parameterName 파라미터 이름
     * @param parameterValue 전송할 파일
     * @exception IllegalArgumentException parameterValue가 null일 경우
     */
    public void addFile(String parameterName, File parameterValue) {
        // paramterValue가 null일 경우 NullFile을 삽입한다.
        if (parameterValue == null) {
            list.add(parameterName);
            list.add(new NullFile());
        } else {
            list.add(parameterName);
            list.add(parameterValue);
        }
    }
    /**
     * 지금까지 지정한 파라미터를 모두 삭제한다.
     */
    public void clearParameters() {
        list.clear();
    }
    
    /**
     * GET 방식으로 대상 URL에 파라미터를 전송한 후
     * 응답을 InputStream으로 리턴한다.
     * @return InputStream
     */
    public InputStream sendGet() throws IOException {
        String paramString = null;
        if (list.size() > 0)
            paramString = "?" + encodeString(list);
        else
            paramString = "";
        
        URL url = new URL(targetURL.toExternalForm() + paramString);
        
        URLConnection conn = url.openConnection();
        
        return conn.getInputStream();
    }
    
    /**
     * POST 방식으로 대상 URL에 파라미터를 전송한 후
     * 응답을 InputStream으로 리턴한다.
     * @return InputStream
     */
    public HttpURLConnection sendPost() throws IOException {
        String paramString = null;
        if (list.size() > 0)
            paramString = encodeString(list);
        else
            paramString = "";
        
        HttpURLConnection conn = (HttpURLConnection)targetURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(paramString);
            out.flush();
        } finally {
            if (out != null) out.close();
        }
        return conn;
    }
    
    /**
     * multipart/form-data 인코딩을 사용하여
     * 대상 URL에 데이터를 전송한 후에
     * 응답을 InputStream으로 리턴한다.
     * @return InputStream
     */
    public HttpURLConnection sendMultipartPost() throws IOException {
        HttpURLConnection conn = (HttpURLConnection)targetURL.openConnection();
        
        // Delimeter 생성
        String delimeter = makeDelimeter();
        
        byte[] newLineBytes = CRLF.getBytes();
        byte[] delimeterBytes = delimeter.getBytes();
        byte[] dispositionBytes = "Content-Disposition: form-data; name=".getBytes();
        byte[] quotationBytes = "\"".getBytes();
        byte[] contentTypeBytes = "Content-Type: application/octet-stream".getBytes();
        byte[] fileNameBytes = "; filename=".getBytes();
        byte[] twoDashBytes = "--".getBytes();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+delimeter);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(conn.getOutputStream());
            
            Object[] obj = new Object[list.size()];
            list.toArray(obj);
            
            for (int i = 0 ; i < obj.length ; i += 2) {
                // Delimeter 전송
                out.write(twoDashBytes);
                out.write(delimeterBytes);
                out.write(newLineBytes);
                // 파라미터 이름 출력
                out.write(dispositionBytes);
                out.write(quotationBytes);
                out.write( ((String)obj[i]).getBytes() );
                out.write(quotationBytes);
                if ( obj[i+1] instanceof String) {
                    // String 이라면
                    out.write(newLineBytes);
                    out.write(newLineBytes);
                    // 값 출력
                    out.write( ((String)obj[i+1]).getBytes() );
                    out.write(newLineBytes);
                } else {
                    // 파라미터의 값이 File 이나 NullFile인 경우
                    if ( obj[i+1] instanceof File) {
                        File file = (File)obj[i+1];
                        // File이 존재하는 지 검사한다.
                        out.write(fileNameBytes);
                        out.write(quotationBytes);
                        out.write(file.getAbsolutePath().getBytes() );
                        out.write(quotationBytes);
                    } else {
                        // NullFile 인 경우
                        out.write(fileNameBytes);
                        out.write(quotationBytes);
                        out.write(quotationBytes);
                    }
                    out.write(newLineBytes);
                    out.write(contentTypeBytes);
                    out.write(newLineBytes);
                    out.write(newLineBytes);
                    // File 데이터를 전송한다.
                    if (obj[i+1] instanceof File) {
                        File file = (File)obj[i+1];
                        // file에 있는 내용을 전송한다.
                        BufferedInputStream is = null;
                        try {
                            is = new BufferedInputStream(
                                     new FileInputStream(file));
                            byte[] fileBuffer = new byte[1024 * 8]; // 8k
                            int len = -1;
                            while ( (len = is.read(fileBuffer)) != -1) {
                                out.write(fileBuffer, 0, len);
                            }
                        } finally {
                            if (is != null) try { is.close(); } catch(IOException ex) {}
                        }
                    }
                    out.write(newLineBytes);
                } // 파일 데이터의 전송 블럭 끝
                if ( i + 2 == obj.length ) {
                    // 마지막 Delimeter 전송
                    out.write(twoDashBytes);
                    out.write(delimeterBytes);
                    out.write(twoDashBytes);
                    out.write(newLineBytes);
                }
            } // for 루프의 끝
            
            out.flush();
        } finally {
            if (out != null) out.close();
        }
        return conn;
    }

    /**
     * 지정한 ArrayList에 저장되어 있는 파라미터&값 목록을
     * application/x-www-form-urlencoded MIME에 맞춰서 인코딩한다.
     * 파라미터의 값의 타입이 File일 경우에는 그 파라미터를 무시하고
     * 다음 파라미터를 처리한다.
     *
     * @param parameters 파라미터 이름과 파라미터 값을 저장하고 있는 객체
     * @return 인코딩된 String
     */
    private static String encodeString(ArrayList parameters) {
        StringBuffer sb = new StringBuffer(256);
        
        Object[] obj = new Object[parameters.size()];
        parameters.toArray(obj);
        try
        {
        	for (int i = 0 ; i < obj.length ; i += 2) {
				if ( obj[i+1] instanceof File || obj[i+1] instanceof NullFile ) continue;
				
				//sb.append(URLEncoder.encode((String)obj[i], "ISO-8859-1") );
				//sb.append('=');
				//sb.append(URLEncoder.encode((String)obj[i+1], "ISO-8859-1") );

				sb.append((String)obj[i] );
				sb.append('=');
				sb.append((String)obj[i+1] );
				
				if (i + 2 < obj.length) sb.append('&');
			}
        }
        catch (Exception e)
        {
        }
        
        
        return sb.toString();
    }
    
    /**
     * multipart/form-data 로 데이터를 전송할 때 사용되는
     * 딜리미터를 생성한다.
     * <p>
     * 임의로 생성하지 않고 매번 같은 딜리미터를 생성한다.
     */
    private static String makeDelimeter() {
        return "---------------------------7d115d2a20060c";
    }
    
    /**
     * 전송할 파일을 지정하지 않은 경우에 사용되는 클래스
     */
    private class NullFile {
        NullFile() {
        }
        public String toString() {
            return "";
        }
    }
}
