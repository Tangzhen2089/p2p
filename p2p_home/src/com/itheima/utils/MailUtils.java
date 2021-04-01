package com.itheima.utils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * ���ʼ�������
 */
public final class MailUtils {
    private static final String USER = "821305324@qq.com"; // �����˳ƺţ�ͬ�����ַ
    private static final String PASSWORD = "jpojkglemjaxbcab"; // �����qq�������ʹ������Ȩ�룬���ߵ�¼����

    /**
     *
     * @param to �ռ�������
     * @param text �ʼ�����
     * @param title ����
     */
    /* ������֤��Ϣ���ʼ� */
    public static boolean sendMail(String to, String text, String title) throws Exception{
       
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            // �����˵��˺�
            props.put("mail.user", USER);
            //�����˵�����
            props.put("mail.password", PASSWORD);

            // ������Ȩ��Ϣ�����ڽ���SMTP����������֤
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // �û���������
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // ʹ�û������Ժ���Ȩ��Ϣ�������ʼ��Ự
            Session mailSession = Session.getInstance(props, authenticator);
            // �����ʼ���Ϣ
            MimeMessage message = new MimeMessage(mailSession);
            // ���÷�����
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // �����ռ���
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // �����ʼ�����
            message.setSubject(title);

            // �����ʼ���������
            message.setContent(text, "text/plain;charset=gb18030");
     
            // �����ʼ�
            Transport.send(message);
            return true;
        
       
    }

    public static void main(String[] args) throws Exception { // ��������
        MailUtils.sendMail("821305324@qq.com","����һ������ʼ�������ظ�","�����ʼ�");
        System.out.println("���ͳɹ�");
    }
    
}