package algorithams;

import java.util.Scanner;

public class ReadBraces {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String data= sc.next();
		
		int a=0;
		for(int i =0;i<data.length();i++){
			if(data.charAt(i) == '(' && data.charAt(i+1)==')'){
				i++;
			}
			a++;
		}
		sc.close();
		System.out.println("The result is" +a);
	}
}
