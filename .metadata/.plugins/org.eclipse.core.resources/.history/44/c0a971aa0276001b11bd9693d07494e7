package com.dev.lima.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.dev.lima.enums.Sexo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;

	private String nome;

	private Sexo sexo;

	private String email;

	private LocalDate dataNascimento;

	private String naturalidade;

	private String nacionalidade;

}
