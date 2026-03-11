package com.enotesApiService.util;

import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enotesApiService.handler.GenericResponse;

public class Commonutil {

	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
		GenericResponse genericResponse = GenericResponse.builder().responseStatus(status).status("success")
				.msg("success").data(data).build();
		return genericResponse.create();
	}

	public static ResponseEntity<?> createBuildResponseMessage(String msg, HttpStatus status) {
		GenericResponse genericResponse = GenericResponse.builder().responseStatus(status).status("success").msg(msg)
				.build();
		return genericResponse.create();
	}

	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {
		GenericResponse genericResponse = GenericResponse.builder().responseStatus(status).status("failed")
				.msg("failed").data(data).build();
		return genericResponse.create();
	}

	public static ResponseEntity<?> createErrorResponseMessage(String msg, HttpStatus status) {
		GenericResponse genericResponse = GenericResponse.builder().responseStatus(status).status("failed").msg(msg)
				.build();
		return genericResponse.create();
	}

	/*
	 * public static String getContentType(String originalFileName) { // TODO
	 * Auto-generated method stub
	 * 
	 * String extension = FilenameUtils.getExtension(originalFileName);
	 * 
	 * if (extension.equals("pdf")) { return "application/pdf"; } if
	 * (extension.equals("jpg")) { return "image/jpg"; }
	 * 
	 * if (extension.equals("png")) { return "image/png";
	 * 
	 * } if (extension.equals("xlxs")) { return
	 * "application/vnd.openxmlformat-officedocument.spreadsheettml.sheet"; } if
	 * (extension.equals("txt")) { return "text/plan"; }
	 * 
	 * if (extension.equals("jpeg")) { return "image/jpeg";
	 * 
	 * }
	 * 
	 * else { return "application/octet-stream"; }
	 * 
	 * 
	 * 
	 * 
	 * switch(extension) { case "pdf" : return "application/pdf";
	 * 
	 * case "xlxs" : return
	 * "application/vnd.openxmlformat-officedocument.spreadsheettml.sheet";
	 * 
	 * case "txt" : return "text/plan"; case "png" : return "image/png";
	 * 
	 * case "jpeg" : return "image/jpeg"; default: return
	 * "application/octet-stream";
	 * 
	 * }
	 * 
	 * }
	 */

	

	public static String getContentType(String originalFileName) {
		
		 final Map<String, String> CONTENT_TYPES = Map.of(
			        "pdf", "application/pdf",
			        "jpg", "image/jpg",
			        "jpeg", "image/jpeg",
			        "png", "image/png",
			        "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			        "txt", "text/plain"
			);
	    String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();
	    return CONTENT_TYPES.getOrDefault(extension, "application/octet-stream");
	}
}
