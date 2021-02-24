package com.dev.lima.resources;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.dev.lima.dtos.PersonDTO;
import com.dev.lima.dtos.PersonDTOUpdate;
import com.dev.lima.enums.Sexo;
import com.dev.lima.exceptions.ResourceNotFounException;
import com.dev.lima.services.PersonService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest
public class ResourcePersonTest {
	
	private PersonDTO personDTO;
	
	private PersonDTOUpdate personDTOUpdate;
	
	@Autowired
	private PersonResource personResource;

	@MockBean
	private PersonService personService;

	@BeforeEach
	public void setup() {
		
		RestAssuredMockMvc.standaloneSetup(this.personResource);
		
		this.personDTO = PersonDTO.builder()
				.cpf("18626735006")
				.dateBirth(LocalDate.of(2020, 3, 1))
				.email("israelslf22@gmail.com")
				.nationality("brasileiro")
				.naturalness("Salvador")
				.name("Israel Santos Lima Filho")
				.sexo(Sexo.MASCULINO)
				.build();
		
		this.personDTOUpdate = PersonDTOUpdate.builder()
		.dateBirth(LocalDate.of(2020, 3, 1))
		.email("israelslf22@gmail.com")
		.nationality("brasileiro")
		.naturalness("Salvador")
		.name("Israel Santos Lima Filho")
		.sexo(Sexo.MASCULINO)
		.build();
	}

	@Test
	public void shouldReturnSucessWhenListPerson() {

		Mockito.when(this.personService.listAll()).thenReturn(List.of(this.personDTO));

		RestAssuredMockMvc.given()
		.accept(ContentType.JSON)
		.when().get("/persons")
		.then()
		.statusCode(HttpStatus.OK.value())
		.body("$.size()", Matchers.equalTo(1));
	}
	
	@Test
	public void shouldReturnNoContentWhenDeletePersonCPFExists() {
		
		Mockito.when(this.personService.delete(this.personDTO.getCpf())).thenReturn(true);
		Mockito.when(this.personService.findPersonByCPF(this.personDTO.getCpf())).thenReturn(this.personDTO);
		
		RestAssuredMockMvc.given()
		.when()
		.delete("/persons/{cpf}", this.personDTO.getCpf())
		.then()
		.statusCode(HttpStatus.NO_CONTENT.value());
		
		assertTrue(this.personService.delete(this.personDTO.getCpf()) != false);
	}
	
	@Test
	public void shouldReturnNoContentWhenDeletePersonCPFNotExists() {
		
		Mockito.when(this.personService.delete(this.personDTO.getCpf())).thenReturn(false);
		Mockito.when(this.personService.findPersonByCPF("12")).thenThrow(ResourceNotFounException.class);
		
		try {
			
			RestAssuredMockMvc.given()
			.when()
			.delete("/persons/{cpf}", "12")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		}catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void shouldReturnCreatedAndHeaderUriWhenPersonSave(){
		
		String jsonPersonDTO = 
				"{\"cpf\": \"18626735006\", \"dateBirth\": \"2020-03-01\", "
				+ "\"email\": \"israelslf22@gmail.com\", \"nationality\": \"brasileiro\", "
				+ "\"naturalness\":\"Salvador\", \"name\":\"Israel Santos Lima Filho\", \"sexo\":\"MASCULINO\"}";
		
		Mockito.when(this.personService.savePerson(this.personDTO)).thenReturn(this.personDTO);
		
		 RestAssuredMockMvc.given()
		.contentType("application/json")
		.body(jsonPersonDTO)
		.when()
		.post("/persons")
		.then()
		.statusCode(HttpStatus.CREATED.value())
		.header("Location", Matchers.containsString("/persons/18626735006"));
	}
	
	@Test
	public void shouldReturnBadRequestNamePersonNullOrBlank() {
		
		Mockito.when(this.personService.savePerson(this.personDTO)).thenReturn(this.personDTO);
		
		String jsonPersonDTO = 
				"{\"cpf\": \"18626735006\", \"dateBirth\": \"2020-03-01\", "
				+ "\"email\": \"israelslf22@gmail.com\", \"nationality\": \"brasileiro\", "
				+ "\"naturalness\":\"Salvador\", \"name\": \"\", \"sexo\":\"MASCULINO\"}";
		
		RestAssuredMockMvc.given()
		.contentType("Application/json")
		.body(jsonPersonDTO)
		.when()
		.post("/persons")
		.then()
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldReturnBadRequestEmailPersonInvalid() {
		
		Mockito.when(this.personService.savePerson(this.personDTO)).thenReturn(this.personDTO);
		
		String jsonPersonDTO = 
				"{\"cpf\": \"18626735006\", \"dateBirth\": \"2020-03-01\", "
				+ "\"email\": \"israelslf22gmail.com\", \"nationality\": \"brasileiro\", "
				+ "\"naturalness\":\"Salvador\", \"name\": \"Israel Santos Lima Filho\", \"sexo\":\"MASCULINO\"}";
		
		RestAssuredMockMvc.given()
		.contentType("Application/json")
		.body(jsonPersonDTO)
		.when()
		.post("/persons")
		.then()
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldReturnBadRequestDataNascimentoPersonBlanckOrNull() {
		
		Mockito.when(this.personService.savePerson(this.personDTO)).thenReturn(this.personDTO);
		
		String jsonPersonDTO = 
				"{\"cpf\": \"18626735006\", \"dateBirth\": \"2020-03-0\", "
				+ "\"email\": \"israelslf22gmail.com\", \"nationality\": \"brasileiro\", "
				+ "\"naturalness\":\"Salvador\", \"name\": \"Israel Santos Lima Filho\", \"sexo\":\"MASCULINO\"}";
		
		RestAssuredMockMvc.given()
		.contentType("Application/json")
		.body(jsonPersonDTO)
		.when()
		.post("/persons")
		.then()
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldReturnBadRequestCPFNullOrInvalid() {
		
		Mockito.when(this.personService.savePerson(this.personDTO)).thenReturn(this.personDTO);
		
		String jsonPersonDTO = 
				"{\"cpf\": \"186\", \"dateBirth\": \"2020-03-0\", "
				+ "\"email\": \"israelslf22gmail.com\", \"nationality\": \"brasileiro\", "
				+ "\"naturalness\":\"Salvador\", \"name\": \"Israel Santos Lima Filho\", \"sexo\":\"MASCULINO\"}";
		
		RestAssuredMockMvc.given()
		.contentType("Application/json")
		.body(jsonPersonDTO)
		.when()
		.post("/persons")
		.then()
		.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldReturnSuccessWhenUpdatePerson() {
		
		Mockito.when(this.personService.update(this.personDTOUpdate, "18626735006")).thenReturn(this.personDTO);
		
		String jsonPersonDTO = 
				"{\"dateBirth\": \"2020-03-01\","
				+ "\"email\": \"israelslf22@gmail.com\", \"nationality\": \"brasileiro\", "
				+ "\"naturalness\":\"Salvador\", \"name\": \"Israel Santos Lima Filho\", \"sexo\":\"MASCULINO\"}";
		
		RestAssuredMockMvc.given()
		.contentType("Application/json")
		.body(jsonPersonDTO)
		.when()
		.put("/persons/{cpf}", "18626735006")
		.then()
		.statusCode(HttpStatus.OK.value());
	}
	
	
	
}