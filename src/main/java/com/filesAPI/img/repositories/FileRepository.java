package com.filesAPI.img.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filesAPI.img.model.FileUpload;

@Repository
public interface FileRepository extends JpaRepository<FileUpload, Integer> {

}
