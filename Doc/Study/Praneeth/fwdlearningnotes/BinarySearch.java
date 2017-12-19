package algorithams;

public class BinarySearch {

	public static int binarySearch(int[] array,int key){
		
		int start = 0;
		int end=array.length-1;
		while(start<=end){
			int mid= (start+end)/2;
			if(key==array[mid]){
				System.out.println("Check"+mid);
				return mid;
			}
			if(key<array[mid]){
				end = mid-1;
			}else {
				start = mid+1;
			}
		}
		System.out.println("not found");
		return -1;
		
	}
	
	public static void main(String[] args) {
		int [] array1 = {1,5,10,32,56};
		System.out.println(binarySearch(array1,56));
	}
}
