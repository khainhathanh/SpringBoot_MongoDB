package com.demospringboot.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAPI {

	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse internalServerException(InternalServerException ex) {
		 return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()); 
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse badRequestException(BadRequestException ex) {
		 return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()); 
	}
}
