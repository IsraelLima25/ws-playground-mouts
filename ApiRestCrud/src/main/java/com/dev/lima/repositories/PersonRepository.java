package com.dev.lima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.lima.entities.Person;

public interface PersonRepository extends JpaRepository<Person, String> {

}
