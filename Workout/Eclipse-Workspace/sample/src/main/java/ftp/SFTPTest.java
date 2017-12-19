package ftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPTest {

	public static void main(String[] args) {
		SFTPTest sftpTest = new SFTPTest();
		sftpTest.sftpTest();
	}
	
	public void sftpTest() {
		//JSch.setLogger(new JSCHLogger()); // More on this below
		JSch jsch = new JSch();
		Session session = null;
		ChannelSftp channel = null;
		FileInputStream localFileStream = null;
		
		String YOUR_KEY_FILE_NAME = "D:/AWS/Documents/Credential/New/new_dsa_ssh";
		String YOUR_SSH_SERVER_USER_NAME = "oracle";
		String YOUR_SSH_SERVER_NAME = "10.142.10.21";
		int YOUR_SSH_SERVER_PORT = 22;
		String YOUR_SSH_PASSWORD = "";
		String YOUR_SSH_SERVER_PASSWORD = "";
		String YOUR_SSH_SERVER_FILE_DIRECTORY = "/opt/home/oracle/test";
		String YOUR_LOCAL_FILE_TO_COPY = "D:/test.txt";
		String YOUR_REMOTE_FILE_NAME = "SFTP_TEST.txt";
		String YOUR_KEY_FILE_PASS_PHRASE="puttydsa";
		
		try
		{
			//Use key authentication if it is set, else use password auth
			if (YOUR_KEY_FILE_NAME != null && YOUR_KEY_FILE_NAME != "") {
				/*//URL keyFileURL = this.getClass().getClassLoader().getResource(YOUR_KEY_FILE_NAME);
				URL keyFileURL = this.getClass().getClassLoader().getResource(YOUR_KEY_FILE_NAME);
				if (keyFileURL == null)
				{
				throw new RuntimeException("Key file " + YOUR_KEY_FILE_NAME  + "not found in classpath");
				}
				URI keyFileURI = keyFileURL.toURI();
			 	*/
				
				/*
				File file = new File("resources/key.txt");
				String absolutePath = file.getAbsolutePath();
				jsch.addIdentity(file.getAbsolutePath(), "puttydsa");
				*/
				
			    Path path = Paths.get("src/main/resources/key.txt");
			    byte[] prvKey = Files.readAllBytes(path);
			    
			    //jsch.addIdentity(new File(YOUR_KEY_FILE_NAME).getAbsolutePath(), "puttydsa");
			    jsch.addIdentity(
			    		YOUR_SSH_SERVER_USER_NAME,    // String userName
			            prvKey,          // byte[] privateKey 
			            null,            // byte[] publicKey
			            YOUR_KEY_FILE_PASS_PHRASE.getBytes()  // byte[] passPhrase
			        );
				
				
				session = jsch.getSession(YOUR_SSH_SERVER_USER_NAME, YOUR_SSH_SERVER_NAME, YOUR_SSH_SERVER_PORT);
			} else if (YOUR_SSH_PASSWORD != null && YOUR_SSH_PASSWORD != "") {
				session = jsch.getSession(YOUR_SSH_SERVER_USER_NAME, YOUR_SSH_SERVER_NAME, YOUR_SSH_SERVER_PORT);
				session.setPassword(YOUR_SSH_SERVER_PASSWORD);
			}
			 
			//Make it so we do not do host key checking. Enabling this would require some extra code and maintenance, but would increase security.
			session.setConfig("StrictHostKeyChecking", "no");
			session.setTimeout(15000);
			System.out.println("Connecting...");
			session.connect();
			System.out.println("Connected!"); 
			channel = (ChannelSftp)session.openChannel("sftp");
			channel.connect();
			System.out.println("Channel Connected!");
			if (YOUR_SSH_SERVER_FILE_DIRECTORY != null && YOUR_SSH_SERVER_FILE_DIRECTORY != "") {
				channel.cd(YOUR_SSH_SERVER_FILE_DIRECTORY);
				System.out.println("Directory Changed!");  
			}
			
			File localFile = new File (YOUR_LOCAL_FILE_TO_COPY);
			if (localFile.exists()) {
				localFileStream = new FileInputStream(localFile);
				ByteArrayInputStream stream = new ByteArrayInputStream("test data".getBytes(StandardCharsets.UTF_8));
				channel.put(localFileStream, YOUR_REMOTE_FILE_NAME);
				System.out.println("File Moved!");
			} else {
				System.out.println("Local file not found " + localFile.getAbsolutePath());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
