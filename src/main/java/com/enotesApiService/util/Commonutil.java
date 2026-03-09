package com.enotesApiService.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enotesApiService.handler.GenericResponse;

public class Commonutil {

	
	public static ResponseEntity<?> createBuildResponse(Object data,HttpStatus status){
		GenericResponse genericResponse=GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.msg("success")
				.data(data)
				.build();
		return genericResponse.create();
	}
	
	
	public static ResponseEntity<?> createBuildResponseMessage(String msg,HttpStatus status){
		GenericResponse genericResponse=GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.msg(msg)
				.build();
		return genericResponse.create();
	}
	
	public static ResponseEntity<?> createErrorResponse(Object data,HttpStatus status){
		GenericResponse genericResponse=GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
		.msg("failed")
		.data(data)
				.build();
		return genericResponse.create();
	}
	
	public static ResponseEntity<?> createErrorResponseMessage(String msg,HttpStatus status){
		GenericResponse genericResponse=GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
		.msg(msg)
				.build();
		return genericResponse.create();
	}
	
}
