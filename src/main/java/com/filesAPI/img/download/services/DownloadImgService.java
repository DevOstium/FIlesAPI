package com.filesAPI.img.download.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.filesAPI.img.exceptions.RequestErrorsAPI;
import com.filesAPI.img.repositories.FileRepository;

@Service
public class DownloadImgService {

	@Autowired
	private FileRepository repo;

	public Resource getImage(Integer id) {

		return this.repo.findById(id).map(file -> {

			File arquivo = new File(file.getCaminho_img());

			Resource resource = new FileSystemResource(arquivo);

			return resource;
		}).orElseThrow(() -> new RequestErrorsAPI("PDF não encontrado"));
	}

}
