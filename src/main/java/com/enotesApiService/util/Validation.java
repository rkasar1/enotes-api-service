package com.enotesApiService.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.enotesApiService.dto.CategoryDto;
import com.enotesApiService.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category shouldnt be null");
		}

		else {

			// validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name shouldnt be empty or null");
			} else if ((categoryDto.getName().length() < 3)) {
				error.put("name", "name length min 3");
			}
			if (categoryDto.getName().length() > 10) {
				error.put("name", "name length max 10");
			}

			// validation description field

			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description shouldnt be empty or null");

			}

			// validation isActive field

			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
			    error.put("isActive", "isActive shouldn't be null");
			}
		}

		
		if(!error.isEmpty())
		{
			throw new ValidationException(error);
		}
	}
}
