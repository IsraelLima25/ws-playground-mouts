package com.dev.lima.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.lima.dtos.PersonDTO;
import com.dev.lima.dtos.PersonDTOUpdate;
import com.dev.lima.services.PersonService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/persons")
public class PersonResource {
	
	@Autowired
	private PersonService personService;
	
	@ApiOperation(value = "Salvar pessoa")
	@PostMapping
	public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO){

		PersonDTO personSave = this.personService.savePerson(personDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(personSave.getCpf()).toUri();
		
		return ResponseEntity.created(uri).body(personSave);
	}
	
	@ApiOperation(value = "Buscar pessoa por CPF")
	@GetMapping(value = "/{cpf}")
	public ResponseEntity<PersonDTO> findByCPF(@PathVariable String cpf){
		
		PersonDTO findPerson = personService.findPersonByCPF(cpf);
		return ResponseEntity.ok(findPerson);
	}
	
	@ApiOperation(value = "Listar todas as pessoas cadastradas")
	@GetMapping
	public ResponseEntity<List<PersonDTO>> listAll(){
		List<PersonDTO> persons = personService.listAll();
		return ResponseEntity.ok().body(persons);
	}
	
	@ApiOperation(value = "Atualizar pessoa")
	@PutMapping("/{cpf}")
	public ResponseEntity<PersonDTO> update(@Valid @RequestBody PersonDTOUpdate personDTOUpdate, @PathVariable String cpf){
		PersonDTO personDTO = personService.update(personDTOUpdate, cpf);
		return ResponseEntity.ok().body(personDTO);
	}
	
	@ApiOperation(value = "Apagar pessoa")
	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> delete(@PathVariable String cpf){
		PersonDTO finPersonDTO = personService.findPersonByCPF(cpf);
		personService.delete(finPersonDTO.getCpf());
		return ResponseEntity.noContent().build();
	}
}
