package algorithams;

import java.util.Scanner;

public class StringReverse {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String s = sc.next();
		for(int i=s.length()-1;i>=0;i--){
			char c = (Character.valueOf(s.charAt(i)));
			System.out.print(c);
		}
		sc.close();
	}
}
