package samples;

import java.util.Random;

public class RandomNumTest {

	public static void main(String[] args) {
		
    	Random random = new Random();
    	int randomNumber = 0;
    	Long id = 0L;
    	Long currentMilSec = 0L; 
    	
    	// Sample 1
    	randomNumber = random.nextInt(10 - 1) + 1;
    	currentMilSec = System.currentTimeMillis();
    	System.out.println(currentMilSec + " " + randomNumber);
    	id = currentMilSec + randomNumber;
    	System.out.println(id);
    	id = Long.valueOf("" + currentMilSec + randomNumber);
    	System.out.println(id);
    	
    	// Sample 2
    	randomNumber = Math.abs(random.nextInt()) % 10;
	    System.out.println(randomNumber);
	    id = System.currentTimeMillis()+randomNumber;
	    
    	System.out.println(id);
    	System.out.println("_______________");
    	System.out.println(System.currentTimeMillis());
    	System.out.println(System.nanoTime());
	}

}
