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
import com.enotesApiService.util.Commonutil;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {

			return Commonutil.createBuildResponseMessage("saved success", HttpStatus.CREATED);
			// return new ResponseEntity<>("Saved success",);
		} else {
			return Commonutil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			// return new ResponseEntity<>("not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return Commonutil.createBuildResponse(allCategory, HttpStatus.OK);
			// return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}

	}

	@GetMapping("/active")
	public ResponseEntity<?> getAllActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getAllActiveCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			// return new ResponseEntity<>(allCategory, HttpStatus.OK);
			return Commonutil.createBuildResponse(allCategory, HttpStatus.OK);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception {
		CategoryDto categoryDto = categoryService.getCategory(id);
		if (ObjectUtils.isEmpty(categoryDto)) {
			return Commonutil.createErrorResponseMessage("not found with category" + id,
					HttpStatus.INTERNAL_SERVER_ERROR);
			// return new ResponseEntity<>("not found with categeory" +id,
			// HttpStatus.NOT_FOUND);
		} else {
			return Commonutil.createBuildResponse(categoryDto, HttpStatus.OK);
			// return new ResponseEntity<>(categoryDto , HttpStatus.OK);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
		Boolean deletedCategory = categoryService.deleteCategory(id);
		if (deletedCategory) {
			// return new ResponseEntity<>("category deleted" ,HttpStatus.OK);
			return Commonutil.createErrorResponseMessage("category deleted" + id, HttpStatus.OK);
		} else {
			return Commonutil.createErrorResponseMessage("not deleted" + id, HttpStatus.INTERNAL_SERVER_ERROR);
			// return new ResponseEntity<>("not deleted" ,
			// HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
