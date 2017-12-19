package algorithams;

public class SubString {
	public static void main(String[] args) {
		String K1="memakai";
		String K2="pakai";
		boolean retVal;
		retVal = K1.endsWith(K2.substring(1));
		System.out.println("Returned Value = " + retVal);
		String name="PraneethHari";
		String name1= "Hari";
		boolean check ;
		
		System.out.println(name.endsWith(name1.substring(0)));
		
	}
}
