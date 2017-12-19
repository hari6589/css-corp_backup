package aws.ses;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	public static void main(String[] args) throws AddressException, MessagingException {
		System.out.println(0);
		String emailTo = "aravindhan.jayakumar@csscorp.com";
		String emailFrom = "aravindhan.jayakumar@csscorp.com";
		String emailSubject = "Test Mail from QA";
		String emailBody = "Test Mail!";
		
		String smtpHost = "email-smtp.us-east-1.amazonaws.com";
		
		//DEV
    	//String smtpUsername = "AKIAIL3SYIS6JUHYPNDA";
    	//String smtpPassword = "AjO4KTmdkRarufvK+soD3BHnYc9hHDPLo0us2lRN/b1b";
    	
		//QA
    	String smtpUsername = "AKIAJNC7RBGEV3HFSAUQ";
    	String smtpPassword = "+9ryX+JrqadbmZ3n6CgUpqFz3zwEX9VTad/3Nbd3";
    	
    	//PROD
    	//String smtpUsername = "AKIAIG3UU2ZSZKUPFUIQ";
    	//String smtpPassword = "Ahe+9FOlqLJjPDHRfhQZg3giExSf1vYOQ3ryBr8mU3Np";
    	
    	int smtpPort = 25;
    	System.out.println(1);
		// Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtps");
    	props.put("mail.smtp.port", smtpPort);
    	System.out.println(2);
    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
    	// The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);
    	System.out.println(3);
        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailFrom));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        System.out.println(4);
        msg.setSubject(emailSubject);
        msg.setContent(emailBody,"text/html");
        
        // Create a transport.
        Transport transport = session.getTransport();
        System.out.println(5);
        // Send the message.
        try {
            // Connect to Amazon SES using the SMTP username and password you specified above.
        	System.out.println("host : " +smtpHost);
        	System.out.println("smtpUsername : " +smtpUsername);
        	System.out.println("smtpPassword : " +smtpPassword);
            transport.connect(smtpHost, smtpUsername, smtpPassword);
            System.out.println(6);
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email Sent!");
        } catch (Exception ex) {
            System.out.println("Mailer : sendEmail() : Status - Email not sent : Exception : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Close and terminate the connection.
            transport.close();        	
        }
	}
	
}
