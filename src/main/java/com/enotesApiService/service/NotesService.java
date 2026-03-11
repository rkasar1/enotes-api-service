package com.enotesApiService.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotesApiService.dto.NotesDto;
import com.enotesApiService.entity.Notes;

public interface NotesService {
	public boolean saveNotes(String notesdto,MultipartFile File) throws Exception;
	
	public List<NotesDto> getAllNotes();

}
