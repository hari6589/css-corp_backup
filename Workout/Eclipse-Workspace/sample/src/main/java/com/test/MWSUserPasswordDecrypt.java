package com.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MWSUserPasswordDecrypt {

	public static void main(String[] args) {
		// String password = "mxaka62A"; //RS Q1o+2n56Ey7NnAW1bZkbdA== //DB Q1o+2n56Ey7NnAW1bZkbdA==
		
		String password = "Test@123";
		encryptUser(password); 
		
	}
	
	public static void encryptUser(String password) {
		String hash = "";
		try {
			System.out.println(password);
			System.out.println(password.getBytes("UTF-8"));
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(password.getBytes("UTF-8"));
			System.out.println(md.digest());
			byte raw[] = md.digest(); 
			hash = (new BASE64Encoder()).encode(raw);
			System.out.println(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(hash);
	}
	// 2@Hari6589
	// 5xZ9y+eP6aBON5xVrovzeA==
	// N19HZ8Yzl8KN+8ce3QSEyQ==
	
	public static void decryptUser(String password) {
		String hash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(password.getBytes("UTF-8"));
			byte raw[] = md.digest(); 
			hash = (new BASE64Encoder()).encode(raw);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(hash);
	}
}
