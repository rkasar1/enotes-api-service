package com.enotesApiService.service;

import java.util.List;

import com.enotesApiService.entity.Category;

public interface CategoryService {

	public boolean saveCategory(Category category);

	public List<Category> getAllCategory();

}
