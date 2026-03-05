package com.enotesApiService.service;

import java.util.List;

import com.enotesApiService.dto.CategoryDto;
import com.enotesApiService.dto.CategoryResponse;
import com.enotesApiService.entity.Category;

public interface CategoryService {

	public boolean saveCategory(CategoryDto categoryDto);

	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getAllActiveCategory();

}
