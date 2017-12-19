package samples;

public class GeneratePasswordTest {

	private static java.util.Random random = null;
	
	public static void main(String[] args) {
		String pass = generatePassword(8);
		System.out.println(pass);
		System.out.println(isPasswordValid(pass));
		System.out.println("-----");
		String newPassword = "";
		do {
			newPassword = generatePassword(8);
			System.out.println(newPassword);
			System.out.println(isPasswordValid(newPassword));
		} while(!isPasswordValid(newPassword));
	}

	public static boolean isPasswordValid(String passwordOne) {
		// TODO Auto-generated method stub		
		if(passwordOne!=null&&passwordOne.length() >= 8 ){
			//System.out.println("is at least 8 char.");
			int x=0;
			//check for digit
			if(passwordOne.matches("((?=.*\\d).{1,})"))
				x++;
			//check for lowercase
			if(passwordOne.matches("((?=.*[a-z]).{1,})"))
				x++;
			//check for upper case
			if(passwordOne.matches("((?=.*[A-Z]).{1,})"))
				x++;
			//check for characters !@#$%^*()_+-={}[]|/
			if(passwordOne.matches("((?=.*[@#%/_=\\-\\(\\)\\!\\+\\^\\*\\[\\]\\{\\}\\|\\$]).{1,})"))
				x++;

			if(x<3){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	
	public static String generatePassword(int charLen){
		  if(charLen <= 0)
			  charLen = 8; //default to 8
		  if(random==null){
				try{
					//Provided By sun, it will cause a lot garbage collection when startup
				    //random = java.security.SecureRandom.getInstance("SHA1PRNG");
					//random = new FastRandom();
					random = new java.util.Random();//use classic one which is fastter
				}catch(Exception ex){
				    System.out.println(ex.toString());
				}
		  }
		  byte charBytes[] = new byte[charLen];
	      
	      for(int i = 0; i < charLen; i++){
	          charBytes[i] = LETTER_AND_NUMBERS[(random.nextInt(128)) % 64];
	      }

	      return new String(charBytes);
	  }
	
	private static final byte LETTER_AND_NUMBERS[] = {
	      65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
	      75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
	      85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
	      101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
	      111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
	      121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
	      56, 57, 50, 49
	  };
}
