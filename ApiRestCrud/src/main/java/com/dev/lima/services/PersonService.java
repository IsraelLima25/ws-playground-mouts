package com.dev.lima.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.lima.dtos.PersonDTO;
import com.dev.lima.dtos.PersonDTOUpdate;
import com.dev.lima.entities.Person;
import com.dev.lima.exceptions.ResourceNotFounException;
import com.dev.lima.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repositoyPerson;

	@Autowired
	private ModelMapper modelMapper;

	public PersonDTO findPersonByCPF(String cpf) {

		Optional<Person> personOptional = repositoyPerson.findById(cpf);

		if (!personOptional.isPresent()) {
			throw new ResourceNotFounException();
		}

		Person person = personOptional.get();

		PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);

		return personDTO;
	}
	
	//@Transactional
	public PersonDTO savePerson(PersonDTO personDTO) {
		
		Person personMap = modelMapper.map(personDTO, Person.class);
		personMap.setDateCreated(LocalDate.now());
		Person savePerson = repositoyPerson.save(personMap);

		PersonDTO personDTOMapper = modelMapper.map(savePerson, PersonDTO.class);

		return personDTOMapper;
	}

	public List<PersonDTO> listAll() {
		List<Person> persons = repositoyPerson.findAll();

		List<PersonDTO> personsDTO = persons.stream().map(person -> modelMapper.map(person, PersonDTO.class))
				.collect(Collectors.toList());

		return personsDTO;
	}
	
	@Transactional
	public PersonDTO update(PersonDTOUpdate personDTOUpdate, String cpf) {
		
		PersonDTO personSave = findPersonByCPF(cpf);
		BeanUtils.copyProperties(personDTOUpdate, personSave, "dateCreated");
		
		Person person = modelMapper.map(personSave, Person.class);
		person.setDateUpdate(LocalDate.now());
		
		Person savePerson = repositoyPerson.save(person);

		PersonDTO personDTO = modelMapper.map(savePerson, PersonDTO.class);
		
		return personDTO;
	}
	
	public boolean delete(String cpf) {
		try {
			PersonDTO findPerson = findPersonByCPF(cpf);
			Person personMap = modelMapper.map(findPerson, Person.class);
			repositoyPerson.delete(personMap);
			return true;
		}catch (ResourceNotFounException e) {
			return false;
		}
	}
}
