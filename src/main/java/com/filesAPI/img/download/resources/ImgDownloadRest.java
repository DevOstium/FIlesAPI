package com.filesAPI.img.download.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filesAPI.img.download.services.DownloadImgService;

@RestController
@RequestMapping (value = "downloadpdfrest")
public class ImgDownloadRest {

	@Autowired
	private DownloadImgService service;

	@GetMapping(value = "/{id}"  )
	public ResponseEntity<Resource> read(@PathVariable Integer id) {

		Resource fileSystemResource = service.getImage(id);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileSystemResource);
		
	}
}
