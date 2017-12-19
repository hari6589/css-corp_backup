package algorithams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class List_Class{
	
	static final int DEFAULT_INITIAL_CAPACITY=16;
	public static void main(String[] args) {
		
		Map<String,List<String>>map = new HashMap<String,List<String>>(DEFAULT_INITIAL_CAPACITY);
		
		
		List<String> s = new ArrayList<String>();
			s.add("Praneeth1");
			s.add("Praneeth2");
			s.add("Praneeth3");
			s.add("Praneeth4");
			s.add("Praneeth5");
			
			map.put("Praneeth1", s);
			map.put("Praneeth2", s);
			map.put("Praneeth", s);
			
		
		
			for(Map.Entry<String, List<String>> m:map.entrySet()){
				for(int i=0;i<s.size();i++){
					System.out.println(m.getKey()+" "+m.getValue().get(i));
				}
				System.out.println(m.hashCode());
			}
	}
}
