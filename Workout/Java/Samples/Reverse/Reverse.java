class Reverse {
	public static void main(String[] args) {
		String p = "Hi hello";
		String w[] = p.split(" ");
		String result = "";
		System.out.println("Input  : " + p);
		for(int i=0; i < w.length; i++) {
			char[] c = w[i].toCharArray();
			for(int j=c.length-1; j >= 0; j--) {
				result += c[j];
			}
			result += " ";
		}
		System.out.println("Result : " + result);
	}
}