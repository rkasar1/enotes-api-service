package com.enotesApiService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotesApiService.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {

}
