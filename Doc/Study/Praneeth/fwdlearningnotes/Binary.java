package algorithams;

import java.util.Scanner;

/* 0?
 * 00
 * 01
 * 
 * 0??
 * 
 * 000
 * 010
 * 001
 * 
 * 0???
 * 0111
 * 
 * 0421
 * 
 * 0001
 * 0010raam rbam
 * 0100
 * 0000
 * 
 * 0000
 * 0100
 * 0010
 * 0001
 * 
 */
public class Binary extends BinarySearch {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		char c =0;
		char c1=0;
		for(int i=0;i<s.length();i++){
				c1 = s.charAt(i+1);
				System.out.println(c1);
				if(s.replace(c1,s.charAt(i)) != null){
					System.out.println(s);
				}
		}
		System.out.println(c);
		sc.close();
		
	}
	
	 
	
	
	

}
