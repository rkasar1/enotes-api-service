package com.enotesApiService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotesApiService.dto.NotesDto;
import com.enotesApiService.entity.FileDetails;
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

	
	
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadNotes(@PathVariable Integer id) throws Exception {
	
		
		FileDetails fileDetailes = notesService.getFileDetailes(id);
		byte[] data=notesService.downloadFile(fileDetailes);
		HttpHeaders  headers=new HttpHeaders();
		String contentType = Commonutil.getContentType(fileDetailes.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetailes.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(data);
		
		
	}

	
	/*
	 */
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> saveNotes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(saveNotes)) {
			return ResponseEntity.noContent().build();
		} else {
			return Commonutil.createBuildResponse(saveNotes, HttpStatus.OK);
		}
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
		         notesService.deleteNotes(id);
	
		         return Commonutil.createBuildResponseMessage("Deleted notes", HttpStatus.OK);
		}
	
	
	
@GetMapping("/restore/{id}")
public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
	         notesService.restoreNotes(id);

	         return Commonutil.createBuildResponseMessage("Restored notes", HttpStatus.OK);
	}


@GetMapping("/recycle-bin")
public ResponseEntity<?> getUserRecyclebinNotes( )throws Exception {
	         Integer userId=2;
	     List<NotesDto> notes   = notesService.getUserRecycleBinNotes(userId);
	     if(CollectionUtils.isEmpty(notes)) {
	    	 return Commonutil.createBuildResponseMessage("notes Empty", HttpStatus.OK);
	     }
	     else
	     return Commonutil.createBuildResponse(notes, HttpStatus.OK);
}

}

