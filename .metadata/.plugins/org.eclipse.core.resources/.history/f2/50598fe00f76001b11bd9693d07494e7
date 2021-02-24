package com.dev.lima.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.dev.lima.enums.Sexo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public PersonDTO() {
		
	}

	private String cpf;

	private String nome;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	private String email;

	private LocalDate dataNascimento;

	private String naturalidade;

	private String nacionalidade;

}
