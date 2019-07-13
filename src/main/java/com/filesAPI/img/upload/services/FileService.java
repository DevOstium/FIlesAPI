package com.filesAPI.img.upload.services;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filesAPI.img.model.FileUpload;
import com.filesAPI.img.repositories.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository repo;
	
	@Autowired
	private HttpServletRequest request;

	public String write(String baseFolder, MultipartFile file) {

		String path = request.getServletContext().getRealPath("/" + baseFolder);
		System.out.println("Path : " + path);

		String homeUserLocal = System.getProperty("user.home");
		String baseFolderPath = homeUserLocal + "/" + baseFolder;
		String filePath = baseFolderPath + "/" + file.getOriginalFilename();

		FileUpload fileUpload = new FileUpload();  
		fileUpload.setCaminho_img(filePath);

		repo.save(fileUpload);

		try {
			file.transferTo(new File(filePath));
			return filePath;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public void salvar(FileUpload file) {
		repo.save(file);
	}
	
}
