package controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.beans.MailingList;
import model.beans.User;

public class AppSession {
	
	private static Session session = null;
	private static User appUser = null;
	
	private AppSession() {
		
	}
	
	
	
	public static Session getSession() {
		return session;
	}



	public static void setSession(Session session) {
		AppSession.session = session;
	}



	public static User getAppUser() {
		return appUser;
	}



	public static void setAppUser(User appUser) {
		AppSession.appUser = appUser;
	}



	public static boolean createAppSession(User user) {
		
		if(session == null) {			
			    try {
					appUser = user;

					Properties properties = new Properties();
					//smtp properties
					properties.setProperty("mail.smtp.host", "smtp.gmail.com");
					properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
					properties.setProperty("mail.smtp.starttls.enable", "true");
					properties.setProperty("mail.smtp.auth", "true");
					properties.setProperty("mail.smtp.port", "587");
					
					//imap properties
					properties.setProperty("mail.store.protocol", "imaps");
					properties.setProperty("mail.imaps.host", "pop.gmail.com");
					properties.setProperty("mail.imaps.ssl.protocols", "TLSv1.2");
					properties.setProperty("mail.imaps.starttls.enable", "true");
					properties.setProperty("mail.imaps.auth", "true");
					properties.setProperty("mail.imaps.port", "993");
					
					//session
					session = Session.getDefaultInstance(properties, new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(appUser.getEmail(), appUser.getPassword());
						}
					});
					
					//verify email
					Transport transport = session.getTransport("smtp");
						transport.connect("smtp.gmail.com", appUser.getEmail(), appUser.getPassword());
						transport.close();
						return true; //correct email & password

				} catch (MessagingException e) {
					session = null;
					return false; //incorrect email or password
				}

		}
		return false; //session already created
	}
	
	
	
	public static boolean send(String sujet, String text, String... destinataires) {
		
		if(session == null) 
			return false;
		if(destinataires.length == 0)
			return false;
		
		// preparer et envoyer le message
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(appUser.getEmail()));
			for(String destinataire : destinataires)
				  message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));
			message.setSubject(sujet);
			message.setText(text);
				
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	 

	public static boolean send(String sujet, String texte, MailingList list) {
		return send(sujet, texte, list.getEmails().toArray(new String[0]));
	}

	public static Message[] getMessages() {
		
		if(session == null) 
			return null;
		
		//store	
		try {
			Store store = session.getStore("imaps");
			store.connect("imap.googlemail.com", appUser.getEmail(), appUser.getPassword());
			Folder folder = null;
			folder = store.getFolder("inbox");
			if(!folder.isOpen())
				folder.open(Folder.READ_ONLY);			
			Message[] messages = folder.getMessages();  
			//folder.close(true);  
			return messages;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}

	}

	
}
