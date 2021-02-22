package com.dev.lima.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dev.lima.enums.Sexo;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "tbl_person")
@Data
@Builder
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String cpf;
	
	@SuppressWarnings("unused")
	private String nome;
	
	@SuppressWarnings("unused")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@SuppressWarnings("unused")
	private String email;
	
	@SuppressWarnings("unused")
	private LocalDate dataNascimento;
	
	@SuppressWarnings("unused")
	private String naturalidade;
	
	@SuppressWarnings("unused")
	private String nacionalidade;

}
