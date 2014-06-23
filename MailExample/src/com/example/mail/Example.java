package com.example.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class Example implements Job {
	   public void execute(JobExecutionContext context)
       throws JobExecutionException {
		   
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("sunnyrealsunny@gmail.com","123Waterfall");
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sunnyrealsunny@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("sunnyrealsunny@gmail.com"));
			message.setSubject("unique_real_unique");
			message.setText("Testing 123 :::: !");
		//	message.setFileName("f:\\software\\zip\\myfigs.txt");
			message.setFileName("NewText.zip");
			
	        // Create and fill first part
	        MimeBodyPart p1 = new MimeBodyPart();
	        p1.setText("This is part one of a test multipart e-mail." +
	                    "The second part is file as an attachment");

	        // Create second part
	        MimeBodyPart p2 = new MimeBodyPart();

	        // Put a file in the second part
	        File filename = new File("f:\\software\\zip\\myfigs.bmp");
			FileDataSource fds = new FileDataSource(filename);
	        p2.setDataHandler(new DataHandler(fds));
	        p2.setFileName(fds.getName());

	        // Create the Multipart.  Add BodyParts to it.
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(p1);
	        mp.addBodyPart(p2);

	        // Set Multipart as the message's content
	        message.setContent(mp);

			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}