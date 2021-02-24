package com.dev.lima.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dev.lima.exceptions.ResourceNotFounException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFounException.class)
	public ResponseEntity<StandarError> resourceNotFound(ResourceNotFounException e,
			HttpServletRequest request){
		
		StandarError err = new StandarError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), e.getMessage(), 
				"Recurso não encontrado", request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ValidationError> argumentNotValid(MethodArgumentNotValidException e, 
			HttpServletRequest request){
		
		ValidationError err = new ValidationError(System.currentTimeMillis(), 
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro na Validação dos campos", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

}