package algorithams;

import java.util.Scanner;

public class TelephoneCount {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String num =sc.next();
		System.out.println(countsize(num));
		sc.close();
	}	
	static String countsize(String num){
		if(num.length()==1){
			return num;
		}
		 int sum=0;
		 for(int i=0;i<num.length();i++){
			 sum=sum+Character.getNumericValue(num.charAt(i));
		 }
		return countsize(Integer.toString(sum));
	}
}
