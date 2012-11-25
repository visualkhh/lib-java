package mailtest;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.kdt.util.communication.mail.Mailer;

public class mailtest {
    public static void main(String[] args) throws AddressException, MessagingException {
        Mailer mail = new Mailer();
        mail.setIp("100.1.5.91");
        mail.setFrom_mail("aa@aa.com");
        mail.setTo_mail("aa@aa.com");
        mail.send("aaa@g.com", "visualhhk@kdn.com", "hi", "hello");
    }
}
