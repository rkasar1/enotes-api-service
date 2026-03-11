package com.enotesApiService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotesApiService.dto.NotesDto;
import com.enotesApiService.service.NotesService;
import com.enotesApiService.util.Commonutil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes,@RequestParam(required = false)   MultipartFile file ) throws Exception {
		boolean saveNotes = notesService.saveNotes(notes, file);
		if (saveNotes) {
			return Commonutil.createBuildResponseMessage("saved notes", HttpStatus.CREATED);
		} else {
			return Commonutil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> saveNotes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(saveNotes)) {
			return ResponseEntity.noContent().build();
		} else {
			return Commonutil.createBuildResponse(saveNotes, HttpStatus.OK);
		}
	}

}
