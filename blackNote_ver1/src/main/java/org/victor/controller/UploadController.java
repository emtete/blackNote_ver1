package org.victor.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("uploadForm");
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormAction( MultipartFile[] uploadFile, Model model ) {
		
		String uploadFolder = "/Users/victor/work/upload";
		
		for( MultipartFile multipartFile : uploadFile ) {
			log.info("-------------------------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename() );
			log.info("Upload File Size : " + multipartFile.getSize() );
			
			File saveFile = new File( uploadFolder, multipartFile.getOriginalFilename() );
			
			try {
				multipartFile.transferTo( saveFile );
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("uploadAjax..");
	}
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxAction( MultipartFile[] uploadFile ) {
		
		String uploadFolder = "/Users/victor/work/upload";
		
		//makeFolder
		File uploadPath = new File( uploadFolder, getFolder() );
		log.info("uploadPath : "+ uploadPath);
		
		if( uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for( MultipartFile multipartFile : uploadFile ) {
		
			UUID uuid = UUID.randomUUID();
			
			String uploadFileName = uuid.toString() + "_" + multipartFile.getOriginalFilename();
			log.info("-------------------------------------------");
			log.info("Upload file Name : "+ uploadFileName );
			log.info("Upload File Size : "+ multipartFile.getSize() );
			
//			File saveFile = new File( uploadFolder, multipartFile.getOriginalFilename() );
		
			try {
				File saveFile = new File( uploadPath, uploadFileName );
				multipartFile.transferTo( saveFile );
				
				if( checkImageType(saveFile) ) {
					
					FileOutputStream thumbnail = new FileOutputStream( new File(uploadPath, "s_"+uploadFileName) );
					Thumbnailator.createThumbnail( multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator );
	}
	
	
	private boolean checkImageType(File file) {
		
		try {
			String contentType = new Tika().detect(file.toPath()); 

			return contentType.startsWith("image");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}

















