package samples;

public class Calculus {

	public static void main(String[] args) {
		Calculus calculus = new Calculus();
		calculus.findHundredsLoop();
	}

	public void findHundredsLoop() {
		System.out.println("Starts");
		for(int i=0; i < 500; i++) {
			if(i%100 == 0) {
				System.out.println(i);
			}
		}
		System.out.println("Ends");
	}
}
