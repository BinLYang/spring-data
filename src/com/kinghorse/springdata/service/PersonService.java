package com.kinghorse.springdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kinghorse.springdata.Person;
import com.kinghorse.springdata.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Transactional
	public void updatePersonEmail(String email, Integer id){
		personRepository.updatePersonEmail(email, id);
	}
	
	@Transactional
	public void savePerson(List<Person> persons){
		personRepository.save(persons);
	}
}
