package cn.gzy.util;

import cn.gzy.controller.MailController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class SendMail {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);
    public static String sendEmailAccount = "wangjiajin0920@163.com";
    public static String sendEmailPassword = "你的邮箱授权码";
    public static String sendEmailSMTPHost = "smtp.163.com";
    public boolean sendMail(String receiveMailAccount, String mailContent) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", sendEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        try {
            MimeMessage message = createMimeMessage(session, sendEmailAccount,
                    receiveMailAccount, mailContent);
            Transport transport = session.getTransport();
            transport.connect(sendEmailAccount, sendEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            logger.error("邮件发送失败：无法找到邮件服务提供者。错误信息：" + e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("邮件发送失败：消息传输错误。错误信息：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("邮件发送失败：未知错误。错误信息：" + e.getMessage());
        }
        return false;
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,
                                                String mailContent) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "OMT科技博客", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(receiveMail, "尊敬的用户", "UTF-8"));
        message.setSubject("注册验证码", "UTF-8");
        message.setContent(mailContent, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}

