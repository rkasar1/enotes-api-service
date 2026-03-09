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

import com.enotesApiService.util.Commonutil;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {

		// ErrorDetails errorDetails = new ErrorDetails(new Date(),
		// exception.getMessage(), request.getDescription(false));

		return Commonutil.createErrorResponseMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
		// return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception) {

		return Commonutil.createErrorResponseMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	// ErrorDetails errorDetails=new ErrorDetails(new
	// Date(),exception.getMessage(),request.getDescription(false));
	// return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND); }

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
	public ResponseEntity<?> handleValidationException(ValidationException exception) {

		return Commonutil.createErrorResponse(exception.getErrors(), HttpStatus.NOT_FOUND);
	}
	// return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);

	@ExceptionHandler(ExistsCategoryException.class)
	public ResponseEntity<?> handleExistsCategoryException(ExistsCategoryException exception) {

		return Commonutil.createErrorResponseMessage(exception.getMessage(), HttpStatus.CONFLICT);
		// return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

		return Commonutil.createErrorResponseMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
		// return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
