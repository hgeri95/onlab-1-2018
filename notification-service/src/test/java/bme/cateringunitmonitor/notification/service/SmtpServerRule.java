package bme.cateringunitmonitor.notification.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.rules.ExternalResource;

import javax.mail.internet.MimeMessage;

public class SmtpServerRule extends ExternalResource {

    private GreenMail greenMail;
    private int port;

    public SmtpServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        greenMail = new GreenMail(new ServerSetup(port, null, "smtp"));
        greenMail.start();
    }

    public MimeMessage[] getMessages() {
        return greenMail.getReceivedMessages();
    }

    public GreenMail getGreenMail() {
        return greenMail;
    }

    @Override
    protected void after() {
        super.after();
        greenMail.stop();
    }
}
