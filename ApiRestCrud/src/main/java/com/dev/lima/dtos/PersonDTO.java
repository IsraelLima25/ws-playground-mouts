package com.dev.lima.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.dev.lima.enums.Sexo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@CPF
	private String cpf;
	
	@NotBlank
	private String name;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Email
	private String email;

	@NotNull
	private LocalDate dateBirth;

	private String naturalness;

	private String nationality;
	
	private LocalDate dateCreated;
	
	private LocalDate dateUpdate;
	
}
