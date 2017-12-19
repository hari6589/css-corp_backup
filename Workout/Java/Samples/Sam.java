import java.util.*;

public class Sam {
	public static void main(String args[]) {
		List<Integer> nums = new ArrayList<Integer>();
		int count=0;
		nums = addElements();
		for(int i=0; i<nums.size(); i++) {
			for(int j=0; j<nums.size(); j++) {
				for(int k=0; k<nums.size(); k++) {
					if(nums.get(i) + nums.get(j) + nums.get(k) == 30)
						System.out.println(nums.get(i) + nums.get(j) + nums.get(k));
					//System.out.println(nums.get(i) + nums.get(j) + nums.get(k));
					count++;
				}
			}
		}
		System.out.println("Number of Combinations: " + count);
	}
	
	public static List<Integer> addElements() {
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(1);
		nums.add(3);
		nums.add(5);
		nums.add(7);
		nums.add(9);
		nums.add(11);
		nums.add(13);
		nums.add(15);
		return nums;
	}
}