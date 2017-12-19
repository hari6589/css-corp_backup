package ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
 
/**
 * This java program shows you how to make SFTP/SSH connection with public key using JSch java API
 * 
 * @author http://kodehelp.com
 */
public class SFTPinJava {
	 
    /**
     * @param args
     */
    public static void main(String[] args) {
         
        /*Below we have declared and defined the SFTP HOST, PORT, USER 
                   and Local private key from where you will make connection */
        String SFTPHOST = "10.142.10.21";
        int    SFTPPORT = 22;
        String SFTPUSER = "oracle";
        // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
        String privateKey = "D:/AWS/Credential/New/new_dsa_ssh"; 
        String SFTPWORKINGDIR = "/opt/home/oracle/test";
         
        JSch jSch = new JSch();
        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;
        
        try {
        	System.out.println("Initialization..");
            jSch.addIdentity(privateKey, "puttydsa");
            System.out.println("Private Key Added.");
            session = jSch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            System.out.println("session created.");
             
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            System.out.println("connecting....");
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("shell channel connected....");
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
            System.out.println("Changed the directory...");
        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(channelSftp!=null){
                channelSftp.disconnect();
                channelSftp.exit();
            }
            if(channel!=null) channel.disconnect();
            if(session!=null) session.disconnect();
        }
    }
}