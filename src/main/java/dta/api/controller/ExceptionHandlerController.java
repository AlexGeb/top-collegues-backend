package dta.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import dta.api.exceptions.CollegueNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CollegueNotFoundException.class)
	public Map<String,String> handleCollegueNotFound(CollegueNotFoundException e ,HttpServletResponse response) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", e.getMessage());
		return error;
	}
}
