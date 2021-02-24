package com.dev.lima.resources;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dev.lima.dtos.PersonDTO;
import com.dev.lima.dtos.PersonDTOUpdate;
import com.dev.lima.enums.Sexo;
import com.dev.lima.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PersonResource.class)
public class ResourcePersonTest {
	
	private PersonDTO personDTO;
	
    @Autowired
    private MockMvc mockMvc;
	
	private PersonDTOUpdate personDTOUpdate;
	
	@MockBean
	private PersonService personService;

	@Autowired
	private ObjectMapper mapperJackson;
	
	@BeforeEach
	public void setup() throws JsonProcessingException {
		
		
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
	public void shouldReturnIsokWhenUserListPerson() throws Exception {
		
		Mockito.when(personService.listAll()).thenReturn(List.of(personDTO));
		
		mockMvc.perform(get("/persons")
				.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	
	@Test
	public void shouldReturnUnauthorizationWhenUserNotAuthenticatedDeletePerson() throws Exception {
		
		Mockito.when(personService.delete(personDTO.getCpf())).thenReturn(true);
		Mockito.when(personService.findPersonByCPF(personDTO.getCpf())).thenReturn(personDTO);
		
		mockMvc.perform(delete("/person/18626735006")
				.content(MediaType.APPLICATION_JSON_VALUE))		
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(username = "israel")
	public void shouldReturnSuccessWhenUserAuthenticatedDeletePersonCPFExists() throws Exception {
		
		Mockito.when(personService.delete(personDTO.getCpf())).thenReturn(true);
		Mockito.when(personService.findPersonByCPF(personDTO.getCpf())).thenReturn(personDTO);
		
		mockMvc.perform(delete("/person/18626735006")
				.content(MediaType.APPLICATION_JSON_VALUE));
		
		assertTrue(personService.delete(personDTO.getCpf()) != false);
	}
	
	@Test
	@WithMockUser(username = "israel")
	public void shouldSuccessWhenUserAuthenticatedDeletePersonCPFNotExists() throws Exception {
		
		Mockito.when(personService.delete(personDTO.getCpf())).thenReturn(true);
		Mockito.when(personService.findPersonByCPF(personDTO.getCpf())).thenReturn(personDTO);
		
		mockMvc.perform(delete("/person/18626735006")
				.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError());
		
		assertTrue(personService.delete(personDTO.getCpf()) != false);
	}
	
	@Test
	public void shouldReturnUnauthorizationWhenUsuarioNotAuthenticatedCreatedPerson() throws Exception {
		
		Mockito.when(personService.savePerson(personDTO)).thenReturn(personDTO);
		
		mockMvc.perform(post("/persons", personDTO))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(username = "israel")
	public void shouldReturnCreatedWhenUsuarioAuthenticatedCreatedPerson() throws Exception {
		
		String jsonContentPersonDTO = mapperJackson.writeValueAsString(personDTO);
		
		Mockito.when(this.personService.savePerson(this.personDTO)).thenReturn(this.personDTO);
		
		mockMvc.perform(
				post("/persons")
				.content(jsonContentPersonDTO)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.cpf", Matchers.equalTo(personDTO.getCpf())))
			.andExpect(MockMvcResultMatchers.header()
					.string("Location", Matchers.containsString("/persons/"+personDTO.getCpf())));
	}
	
	@Test
	@WithMockUser(username = "israel")
	public void shouldReturnUnprocessablePersonInvalid() throws Exception {
		
		personDTO.setName("");
		personDTO.setEmail("israelslf22gmail.com");
		personDTO.setDateBirth(null);
		personDTO.setCpf("");

		String jsonContentPersonDTO = mapperJackson.writeValueAsString(personDTO);
		
		mockMvc.perform(post("/persons")
				.content(jsonContentPersonDTO)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	@WithMockUser(username = "israel")
	public void shouldReturnSuccessWhenUserAuthenticatedUpdatePerson() throws Exception {
		
		Mockito.when(this.personService.update(this.personDTOUpdate, "18626735006")).thenReturn(this.personDTO);
		String jsonContentPersonDTO = mapperJackson.writeValueAsString(personDTO);
		
		mockMvc.perform(put("/persons/18626735006")
				.content(jsonContentPersonDTO)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
