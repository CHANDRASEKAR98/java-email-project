package com.email.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * JavaEmail class is used to send email to the multiple recipients.
 * 
 */
public class JavaEmail {

	public static void main(String[] args) {
		// create a list of mail recipients
		List<String> recipientList = new ArrayList<>();
		recipientList.add("<recipient_address_1>");
		recipientList.add("<recipient_address_2>");
		try {
			// set new properties for email
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "465");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.socketFactory.port", "465");
	        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        
	        // authenticating with the mail server
	        Session session = Session.getInstance(prop, new Authenticator() {
			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {
			    	String username = "<paste_your_mail_address>";
			    	String password = "<Paste_your_mail_password>";
			        return new PasswordAuthentication(username, password);
			    }
			});
	        
	        // creating a message object using the authenticated session
	        Message message = new MimeMessage(session);
	        
	        // setting from address for the mail
			message.setFrom(new InternetAddress("<sender_mail_address>"));			
			
			// setting list of recipient address
			message.addRecipients(Message.RecipientType.TO, getRecepients(recipientList));
			
			// set a subject for the mail
			message.setSubject("Email from CHANDRASEKAR98 GitHub user");
			
			// creating an MimeBodyPart object for setting the body content of the mail
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			
			// body content for the mail is set
			mimeBodyPart.setContent(setBodyContent(), "text/html; charset=utf-8");
			
			// Creating an MimeBodyPart object again for setting the attachment file with the mail
			MimeBodyPart mimeattachmentPart = new MimeBodyPart();
			
			// fetching the file data from the system
			DataSource dataSource = new FileDataSource(new File("F:/hearticon.jpg"));
			
			// setting the file data on the attachment object as a mail attachment
			mimeattachmentPart.setDataHandler(new DataHandler(dataSource));
			
			// naming the attachment file with the original file name
			mimeattachmentPart.setFileName(dataSource.getName());
			
			// creating MultiPart to set all the body parts into it
			Multipart multiPart = new MimeMultipart();
			
			// setting the body content to the MultiPart object
			multiPart.addBodyPart(mimeBodyPart);
			
			// setting the mail attachment to the MultiPart object
			multiPart.addBodyPart(mimeattachmentPart);
			
			// finally setting the entire body contents into the message object
			message.setContent(multiPart);
			
			// sending the mail
			Transport.send(message);
			
			System.out.println("The Email is sent successfully!!!");
		} catch (MessagingException e) {
			System.out.println("Unable to send the mail : "+ e);
		}

	}
	
	/*
	 * getRecepients method is used to convert List to Array
	 * @param List<String>
	 * return Address[]
	 * 
	 */
	private static Address[] getRecepients(List<String> recipientList) {
		List<Address> address = new ArrayList<>();
		recipientList.stream().forEach(recipient -> {
			try {
				address.add(new InternetAddress(recipient));
			} catch (AddressException e) {
				e.printStackTrace();
			}
		});
		return address.stream().toArray(Address[] :: new);
	}

	/*
	 * setBodyContent method is used to set the body content of the mail
	 * return String
	 * 
	 */
	private static String setBodyContent() {
		String content = "Hello User,<br><br>This email is sent inorder to check Java Mail API.<br>"
				+ "<br>Hope to see you soon!!!"
				+ "<br><br>Thanks and regards<br>"
				+ "Chandrasekar Balakumar,<br>"
				+ "Software Developer.";
		return content;
	}

}
