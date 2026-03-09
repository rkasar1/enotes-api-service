package com.enotesApiService.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest request){
		
		
		ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),request.getDescription(false));
	return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}




	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<?>
	 * handleGlobalException(Exception exception,WebRequest request){
	 * 
	 * 
	 * ErrorDetails errorDetails=new ErrorDetails(new
	 * Date(),exception.getMessage(),request.getDescription(false)); return new
	 * ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND); }
	 */
	
	
	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public
	 * ResponseEntity<?>
	 * handleMethodArgumentNotValidException(MethodArgumentNotValidException
	 * exception){ List<ObjectError> allErrors =
	 * exception.getBindingResult().getAllErrors();
	 * 
	 * Map<String,Object> error= new LinkedHashMap<>();
	 * 
	 * allErrors.stream().forEach(er->{ String msg=er.getDefaultMessage(); String
	 * field = ((FieldError)(er)).getField(); error.put(field,msg); });
	 * 
	 * 
	 * //ErrorDetails errorDetails=new ErrorDetails(new
	 * Date(),exception.getMessage(),request.getDescription(false)); return new
	 * ResponseEntity<>(error,HttpStatus.BAD_REQUEST); }
	 */
	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException exception){
	    
	    return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
	}



	@ExceptionHandler(ExistsCategoryException.class)
	public ResponseEntity<?> handleExistsCategoryException(ExistsCategoryException exception){
	    
	    return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}

	
	

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
	    
	    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}



