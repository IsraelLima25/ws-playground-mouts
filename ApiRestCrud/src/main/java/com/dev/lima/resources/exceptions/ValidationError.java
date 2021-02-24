package com.dev.lima.resources.exceptions;

import java.util.ArrayList;
import java.util.List;


public class ValidationError extends StandarError {
	
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> listError = new ArrayList<>();

	ValidationError(Long timeStamp, Integer status, String error, String msg, String path) {
		super(timeStamp, status, error, msg, path);
	}

	public void addError(String fieldMessage, String message) {
		listError.add(new FieldMessage(fieldMessage, message));
	}

}
