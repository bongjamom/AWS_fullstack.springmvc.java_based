package co.yakkwa.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import co.yakkwa.domain.AttachFileDTO;
import co.yakkwa.service.BoardService;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

@Controller
@Log4j
public class UploadController {
	@Getter
	private static final String PATH = "c:/upload";
	@Autowired
	private BoardService boardService;
	
	@GetMapping("upload")
	public void upload() {
		log.info("upload");
	}
	
	@GetMapping("uploadAjax")
	public void uploadAjax() {
		log.info("uploadAjax");
	}
	
	@PostMapping(value="uploadAjax", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody 
	@PreAuthorize("isAuthenticated()")
	public List<AttachFileDTO> uploadAjax(MultipartFile[] files) {
		log.info("uploadAjax post");
		List<AttachFileDTO> list = new ArrayList<>();
		File uploadPath = new File(PATH, getFolder());
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile m : files) {
			log.info(m.getOriginalFilename());
			// 실제 스트림 전송
			
			String uuidStr = UUID.randomUUID().toString();
			String tName = uuidStr + "_" + m.getOriginalFilename();
			File f = new File(uploadPath, tName);
			boolean image = false;
			try {
				image = isImage(f);
				m.transferTo(f);
				if(image) {
					// 섬네일 처리
					File f2 = new File(uploadPath, "s_" + tName);
					Thumbnails.of(f).crop(Positions.CENTER).size(200, 200).toFile(f2);
				}
			} 
			catch (UnsupportedFormatException e) {
				
				image = false;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			AttachFileDTO dto = new AttachFileDTO();
			dto.setUuid(uuidStr);
			dto.setPath(getFolder());
			dto.setImage(image);
			dto.setName(m.getOriginalFilename());
			list.add(dto);
			
		}
		return list;
	}
	
	@GetMapping("deleteFile") 
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String deleteFile(AttachFileDTO dto) {
		return boardService.deleteFile(dto);
	}
	
	@GetMapping("display") 
	@ResponseBody
	public ResponseEntity<byte[]> getFile(AttachFileDTO dto, Boolean thumb) {
		// fileName : path + uuid + name
		log.info(dto);
		String s = PATH + "/" + dto.getPath() + "/" + (thumb != null && thumb ? "s_" : "") + dto.getUuid() + "_" + dto.getName();
		 
		File file = new File(s);
		log.info("exist() : " + file.exists());
		log.info(thumb);
		
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping("download") @ResponseBody
	public ResponseEntity<byte[]> download(AttachFileDTO dto) {
		// fileName : path + uuid + name
		log.info(dto);
		String s = PATH + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();
		
		File file = new File(s);
		
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
			headers.add("Content-Disposition", "attachment; filename=" + new String(dto.getName().getBytes("utf-8"), "iso-8859-1"));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping("list") @ResponseBody // ResponseBody : 응답타입 제어...list를 입력해도 jsp파일을 찾으러 가지 않는다
	public List<String> test() {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		return list;
	}
	
	@GetMapping(value="dto", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody
	public AttachFileDTO getDto() {
		AttachFileDTO dto = new AttachFileDTO();
		dto.setImage(true);
		dto.setName("파일명.png");
		return dto;
	}
	
	private String getFolder() {
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	// file >> mime
	private boolean isImage(File file) throws IOException {
		List<String> excludes = Arrays.asList("ico", "webp");
		int idx = file.toString().lastIndexOf(".");
		if(idx == -1) {
			return false;
		}
		String ext = file.toString().substring(idx+1);
		if(excludes.contains(ext)) {
			return false;
		}
		
		String mime = Files.probeContentType(file.toPath());
		return mime != null && mime.startsWith("image");
	}
	
	@PostMapping("/ckImage") @ResponseBody
	public Map<String, Object> uploadAjax(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		log.info(request);
		MultipartFile multipartFile = request.getFile("upload");
		
		String origin = multipartFile.getOriginalFilename();
		
		String uuidStr = UUID.randomUUID().toString();
		String tName = uuidStr + "_" + origin;
		File f = new File(PATH, tName);
		AttachFileDTO dto = new AttachFileDTO();
		dto.setUuid(uuidStr);
		dto.setPath("");
		dto.setName(origin);
		
		multipartFile.transferTo(f);
		
		Map<String, Object> map = new HashMap<>();
		map.put("uploaded", true);
		
		map.put("url", "/display"+dto.getUrl());
		
		log.info(map);
		return map;
	}
}
	

