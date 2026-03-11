package com.enotesApiService.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotesApiService.dto.NotesDto;
import com.enotesApiService.dto.NotesDto.CategoryDto;
import com.enotesApiService.entity.FileDetails;
import com.enotesApiService.entity.Notes;
import com.enotesApiService.exception.ResourceNotFoundException;
import com.enotesApiService.repository.CategoryRepository;
import com.enotesApiService.repository.FileRepository;
import com.enotesApiService.repository.NotesRepository;
import com.enotesApiService.service.NotesService;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotesServiceimpl implements NotesService {

	@Autowired
	private NotesRepository repo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private FileRepository fileRepository;

	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public boolean saveNotes(String notesdto, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		// category validation
		ObjectMapper ob = new ObjectMapper();
		NotesDto notes = ob.readValue(notesdto, NotesDto.class);

		checkedCategoryExits(notes.getCategory());

		Notes notesmap = mapper.map(notes, Notes.class);

		FileDetails filedetails = saveFileDetails(file);
		if (!ObjectUtils.isEmpty(filedetails)) {
			notesmap.setFileDetails(filedetails);
		} else {
			notesmap.setFileDetails(null);
		}

		Notes saveNotes = repo.save(notesmap);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}

		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws Exception {
		FileDetails fileDetails = new FileDetails();
		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");

			if (!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("invalid file format! Upload only pdf,xlsx,jpg,png");
			}
			fileDetails.setOriginalFileName(originalFilename);
			fileDetails.setDisplayFileName(getDisplayFileName(originalFilename));
			String randomString = UUID.randomUUID().toString();

			String uploadFilename = randomString + "." + extension;
			fileDetails.setUploadFileName(uploadFilename);
			fileDetails.setFileSize(file.getSize());

			File saveFile = new File(uploadPath);

			if (!saveFile.exists())

			{
				saveFile.mkdir();

			}

			String storePath = uploadPath.concat(uploadFilename);

			fileDetails.setPath(storePath);

			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));

			if (upload != 0) {
				FileDetails savefileDetails = fileRepository.save(fileDetails);
				return savefileDetails;
			}

		}
		return null;

	}

	private String getDisplayFileName(String originalFilename) {

		String extension = FilenameUtils.getExtension(originalFilename);
		String removeExtension = FilenameUtils.removeExtension(originalFilename);
		if (removeExtension.length() > 8) {
			removeExtension = removeExtension.substring(0, 7);
		}
		removeExtension = removeExtension + "." + extension;
		return removeExtension;
	}

	private String getDisplayFileName(MultipartFile file) {
		// TODO Auto-generated method stub

		return null;
	}

	private void checkedCategoryExits(CategoryDto dto) throws Exception {
		// TODO Auto-generated method stub
		categoryRepo.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("Category invalid"));
	}

	@Override
	public List<NotesDto> getAllNotes() {
		// TODO Auto-generated method stub
		return repo.findAll().stream().map(notes -> mapper.map(notes, NotesDto.class)).toList();
	}

}
