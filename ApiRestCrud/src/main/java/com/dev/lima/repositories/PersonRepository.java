package com.dev.lima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.lima.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
	
	long deleteByCpf(String cpf);
	
}
