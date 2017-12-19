package com.dao;

import java.util.List;
import com.model.Person;

public interface PersonDAO {
	
	public void savePerson(Person person);
	
	public List<Person> listPerson();
}
