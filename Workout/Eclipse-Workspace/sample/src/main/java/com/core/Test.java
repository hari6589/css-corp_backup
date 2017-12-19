package com.core;

public class Test {

	private int id;
	private String name;
	
	public Test(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static void main(String[] args) {
		Test t1 = new Test(1, "Test");
		//Test t2 = new Test(1, "Test");
		Test t2 = t1;
		//t1 = t2;
		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());
		
		System.out.println(t1.toString());
		System.out.println(t2.toString());
		
		System.out.println(t1.equals(t2));
		System.out.println(t1==t2);
	}

}
