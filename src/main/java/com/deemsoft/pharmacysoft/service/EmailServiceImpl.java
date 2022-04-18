package com.deemsoft.pharmacysoft.service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Base64Utils;

import com.deemsoft.pharmacysoft.model.EmailInfo;

@Service("emailService")
public class EmailServiceImpl implements EmailService
{  
	@Autowired 
    JavaMailSender javaMailSender;

	public void emailSendSimple(String to, String from, String sub, String body){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        //mailMessage.setReplyTo("someone@localhost");
        mailMessage.setFrom(from);
        mailMessage.setSubject(sub);
        mailMessage.setText(body);
		javaMailSender.send(mailMessage);
	}
	public void emailSend(String to, String from, String sub, String body){
		try {

            MimeMessage message = javaMailSender.createMimeMessage();

            message.setSubject(sub);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (MessagingException ex) {
           //
		   }
	}
	
	public void SendEmailWithAttachment(EmailInfo emailInfo){
		try {

            MimeMessage message = javaMailSender.createMimeMessage();

            message.setSubject(emailInfo.subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailInfo.from);
            helper.setTo(emailInfo.to);
			//helper.setCc(emailInfo.cc);
			//helper.setBcc("shreedhara21@gmail.com");
			
			byte[] byteArr = Base64Utils.decodeFromString(emailInfo.attachment);
			ByteArrayDataSource dSource = new ByteArrayDataSource(byteArr, "application/pdf");
			
			helper.addAttachment(emailInfo.filename,dSource);
            helper.setText(emailInfo.body, true);
            javaMailSender.send(message);
        } catch (MessagingException ex) {
           //
		   }
	}
}

