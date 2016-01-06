// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Mailer.java

package khh.communication.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// Referenced classes of package kv.common.util:
//            Log

public class Mailer
{

    public Mailer()
    {
    }

    
    static public final String TYPE_UTF_8 = "UTF-8";    
    static public final String TYPE_UTF_16 = "UTF-16";   
    static public final String TYPE_EUC_KR = "EUC-KR";   
    static public final String TYPE_8859_1 = "8859_1";   
    static public final String TYPE_MS949 = "MS949"; 
    static public final String TYPE_KSC5601 = "KSC5601";    
    
    
    private String ip="localhost";
    private String port="25";
    private String from_mail;
    private String to_mail;
    private String title;
    private String message; 
    private String charset="UTF-8";
    private String mailer="visualkhhMailer";
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFrom_mail() {
        return from_mail;
    }

    public void setFrom_mail(String from_mail) {
        this.from_mail = from_mail;
    }

    public String getTo_mail() {
        return to_mail;
    }

    public void setTo_mail(String to_mail) {
        this.to_mail = to_mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMailer() {
        return mailer;
    }

    public void setMailer(String mailer) {
        this.mailer = mailer;
    }

    public boolean send(String form_mail, String to_mail, String title, String message) throws AddressException, MessagingException{
        setFrom_mail(from_mail);
        setTo_mail(to_mail);
        setTitle(title);
        setMessage(message);
        return send();
    }
    public boolean send() throws AddressException, MessagingException
    {
        boolean flag = false;
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", getIp());
            properties.put("mail.smtp.port", getPort());
            Session session = Session.getDefaultInstance(properties, null);
//            String s4 = new String(form.getBytes("KSC5601"), "8859_1");
//            String s5 = new String(to.getBytes("KSC5601"), "8859_1");
            MimeMessage mimemessage = new MimeMessage(session);
            mimemessage.setFrom(new InternetAddress(getFrom_mail()));
            mimemessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(getTo_mail(), false));
            mimemessage.setSubject(getTitle(),getCharset());
            mimemessage.setText(getMessage());
            mimemessage.setHeader("X-Mailer", getMailer());
            mimemessage.setHeader("Content-Type", "text/html; charset="+getCharset());
            mimemessage.setSentDate(new Date());
            Transport.send(mimemessage);
            flag = true;
        return flag;
    }
    public static void main(String[] args) throws AddressException, MessagingException {
    	Mailer m =new Mailer();
    	m.setFrom_mail("visualkhh@gmail.com");
    	m.setIp("smtp://naver.com");
    	m.setMailer("aa");
    	m.setMessage("vvvvvvvvvvv");
    	m.send("visualkhh@gmail.com", "visualkhh@gmail.com", "ss", "vv");
	}
}
