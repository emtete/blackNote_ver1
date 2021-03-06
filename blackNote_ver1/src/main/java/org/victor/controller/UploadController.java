package org.victor.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.victor.domain.AttachFileDTO;

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
	
	@PostMapping( value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction( MultipartFile[] uploadFile ) {
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "/Users/victor/work/upload";
		
		//makeFolder
		File uploadPath = new File( uploadFolder, getFolder() );
		log.info("uploadPath : "+ uploadPath);
		
		if( uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for( MultipartFile multipartFile : uploadFile ) {
			
			UUID uuid = UUID.randomUUID();
			AttachFileDTO attachDTO = new AttachFileDTO(); 
			list.add( attachDTO );
			
			String uploadFileName = uuid.toString() + "_" + multipartFile.getOriginalFilename();
			attachDTO.setFileName( multipartFile.getOriginalFilename() );
			attachDTO.setUuid( uuid.toString() );
			attachDTO.setUploadPath( getFolder() );
			
			log.info("-------------------------------------------");
			log.info("Upload file Name : "+ uploadFileName );
			log.info("Upload File Size : "+ multipartFile.getSize() );
			
//			File saveFile = new File( uploadFolder, multipartFile.getOriginalFilename() );
		
			try {
				File saveFile = new File( uploadPath, uploadFileName );
				multipartFile.transferTo( saveFile );
				
				if( checkImageType(saveFile) ) {
					
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream( new File(uploadPath, "s_"+uploadFileName) );
					Thumbnailator.createThumbnail( multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//uploadAjaxAction()
	
	
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

















