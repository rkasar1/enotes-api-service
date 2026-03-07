package com.enotesApiService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotesApiService.dto.CategoryDto;
import com.enotesApiService.dto.CategoryResponse;
import com.enotesApiService.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return new ResponseEntity<>("Saved success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}

	}
	
	
	@GetMapping("/active")
	public ResponseEntity<?> getAllActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getAllActiveCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer  id) {
		CategoryDto categoryDto = categoryService.getCategory(id);
		if (ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>("not found with categeory" +id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(categoryDto , HttpStatus.OK);
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer  id) {
		Boolean deletedCategory = categoryService.deleteCategory(id);
		if (deletedCategory) {
			return new ResponseEntity<>("category deleted" ,HttpStatus.OK);
		} else {
			return new ResponseEntity<>("not deleted" , HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
