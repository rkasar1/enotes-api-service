package com.enotesApiService.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotesApiService.dto.CategoryDto;
import com.enotesApiService.dto.CategoryResponse;
import com.enotesApiService.entity.Category;
import com.enotesApiService.repository.CategoryRepository;
import com.enotesApiService.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean saveCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub

		/*
		 * Category category = new Category();
		 * 
		 * category.setName(categoryDto.getName());
		 * category.setDescription(categoryDto.getDescription());
		 * category.setIsActive(categoryDto.getIsActive());
		 */
		Category category = modelMapper.map(categoryDto, Category.class);
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepository.save(category);

		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}

		return true;

	}

	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findAll();
	List<CategoryDto> categoryDtoList=	categories.stream().map(cat->modelMapper.map(cat,CategoryDto.class)).toList();

		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getAllActiveCategory() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> categoryDtoList=	categories.stream().map(cat->modelMapper.map(cat,CategoryResponse.class)).toList();

			return categoryDtoList;
		}

}
