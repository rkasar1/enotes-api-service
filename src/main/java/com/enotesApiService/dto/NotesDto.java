package com.enotesApiService.dto;

import java.util.Date;

import com.enotesApiService.entity.FileDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {

	private Integer id;

	private String title;

	private String description;

	private CategoryDto category;

	// private MultipartFile fsile;

	private FilesDto fileDetails;

	// private Boolean isDeleted;
	private Boolean isDeleted;

	private Date deletedOn;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FilesDto {
		private Integer id;

		// @NotBlank(message = "name should not be null")
		// @Min(value = 10)
		// @Max(value = 100)
		private String originalFileName;

		// private String uploadFileName;

		private String displayFileName;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDto {
		private Integer id;

		// @NotBlank(message = "name should not be null")
		// @Min(value = 10)
		// @Max(value = 100)
		private String name;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FileDetails {
		private Integer id;

		// @NotBlank(message = "name should not be null")
		// @Min(value = 10)
		// @Max(value = 100)
		private String originalFileName;

		// private String uploadFileName;

		private String displayFileName;

	}
}
