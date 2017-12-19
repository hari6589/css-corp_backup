package com.main;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.PersonDAO;
import com.model.Person;

public class HibernateMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		PersonDAO personDAO = context.getBean(PersonDAO.class);
		
		Person person = new Person();
		person.setName("Hari");
		person.setCountry("India");
		
		personDAO.savePerson(person);
		
		List<Person> personList = personDAO.listPerson();
		for(Person p : personList) {
			System.out.println(p.getName() + " _ " + p.getCountry());
		}
	}

}
