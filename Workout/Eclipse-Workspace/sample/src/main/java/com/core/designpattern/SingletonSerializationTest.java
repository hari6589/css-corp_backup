package com.core.designpattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SingletonSerializationTest {

	public static void main(String[] args) throws ClassNotFoundException {
		SingletonTest singletonTest = SingletonTest.singletonTest;
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream("file.txt"));
			out.writeObject(singletonTest);
			out.close();
			
			ObjectInput in = new ObjectInputStream(new FileInputStream("file.txt"));
			SingletonTest st = (SingletonTest) in.readObject();
			in.close();
			
			System.out.println(singletonTest.hashCode());
			System.out.println(st.hashCode());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class SingletonTest implements Serializable {
	
	public static SingletonTest singletonTest = new SingletonTest();
	
	private SingletonTest() {}
	
	protected Object readResolve() {
		return singletonTest;
	}
	
}
