package com.filesAPI.img.upload.resources;

import java.util.Arrays;
import java.util.concurrent.Callable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filesAPI.img.model.FileUpload;
import com.filesAPI.img.upload.services.FileService;

@RestController
@RequestMapping(value = "/uploadimgrest")
public class ImgUpload {

	@Autowired
	private FileService fileService;
	
	@RequestMapping ( method   = {  RequestMethod.POST, RequestMethod.PUT }
        			 ,consumes = {  "multipart/form-data" }
                    )
	public void upload( @RequestParam("file") MultipartFile file, @Valid FileUpload fileUpload  ) {
		String webPath = fileService.write("uploaded-img", file);
		fileUpload.setCaminho_img(webPath);
		fileService.salvar(fileUpload);
	}
	
	@PostMapping( value = "/multiple", consumes = { "multipart/form-data"} )
	public Callable<ResponseEntity<?>> uploadMultiple( @RequestParam("arquivos") MultipartFile[] arquivos ) {
		return () ->  {  
							Arrays.asList(arquivos).stream().forEach( file -> fileService.write("uploaded-img", file) );
						return ResponseEntity.ok().build();
					};
		}
}














