package com.enotesApiService.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotesApiService.dto.NotesDto;
import com.enotesApiService.dto.NotesDto.CategoryDto;
import com.enotesApiService.dto.NotesDto.FilesDto;
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
		NotesDto notesDto = ob.readValue(notesdto, NotesDto.class);

		/*
		 * update notes if id is given from request
		 */

		if (!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNotes(file, notesDto);
		}
		checkedCategoryExits(notesDto.getCategory());

		Notes notesmap = mapper.map(notesDto, Notes.class);

		FileDetails filedetails = saveFileDetails(file);
		if (!ObjectUtils.isEmpty(filedetails)) {
			notesmap.setFileDetails(filedetails);
		} else if (ObjectUtils.isEmpty(notesDto.getId())) {

			notesmap.setFileDetails(null);
		}

		Notes saveNotes = repo.save(notesmap);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}

		return false;
	}

	private void updateNotes(MultipartFile file, NotesDto notesDto) throws Exception {
		// TODO Auto-generated method stub
		Notes existNotes = repo.findById(notesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Not found"));

		if (ObjectUtils.isEmpty(file)) {
			notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FilesDto.class));
		}
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

	@Override
	public FileDetails getFileDetailes(Integer id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		FileDetails fileDetails = fileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("file not found"));

		return fileDetails;
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetailes) throws Exception {
		// TODO Auto-generated method stub

		InputStream io = new FileInputStream(fileDetailes.getPath());
		return StreamUtils.copyToByteArray(io);

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
