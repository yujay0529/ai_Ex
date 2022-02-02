package com.ai.ex.controller;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.ex.model.ObjectVO;
import com.ai.ex.model.PoseVO;
import com.ai.ex.service.ChatbotService;
import com.ai.ex.service.MychatbotService;
import com.ai.ex.service.OCRService;
import com.ai.ex.service.ObjectDetectionService;
import com.ai.ex.service.PoseEstimationService;
import com.ai.ex.service.STTService;
import com.ai.ex.service.TTSService;

@RestController
public class APIRestController {
	@Autowired
	private OCRService ocrService;
	
	@Autowired
	private PoseEstimationService poseService;	
	
	@Autowired
	private ObjectDetectionService objectService;
	
	@Autowired
	private STTService sttService;
	
	@Autowired
	private TTSService ttsService;
	
	@Autowired
	private ChatbotService chatService;
	
	@Autowired
	private MychatbotService mychatService;
	
	// OCR 요청 받아서 서비스 호출하고 결과 받아서 반환
	@RequestMapping("/clovaOCR")
	public String  faceRecogCel(@RequestParam("uploadFile") MultipartFile file) {		
		String result = "";
		
		try {
			// 1. 파일 저장 경로 설정 : 실제 서비스되는 위치 (프로젝트 외부에 저장)
			String uploadPath = "C:/upload/";
			
			// 2. 원본 파일 이름 알아오기
			String originalFileName = file.getOriginalFilename();
			String filePathName = uploadPath + originalFileName;
			
			// 3. 파일 생성
			File file1 = new File(filePathName);
			
			// 4. 서버로 전송
			file.transferTo(file1);				
			
			// 서비스에 파일 path와 파일명 전달  -> 서비스 메소드에서 변경
			// 서비스에서 반환된 텍스트를 result에 저장
			result = ocrService.clovaOCRService(filePathName);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 포즈 인식
	@RequestMapping("/poseDetect")
	public ArrayList<PoseVO>  poseDetect(@RequestParam("uploadFile") MultipartFile file) {		
		
		ArrayList<PoseVO> poseList = null; 
		
		try {
			// 1. 파일 저장 경로 설정 : 실제 서비스되는 위치 (프로젝트 외부에 저장)
			String uploadPath = "C:/upload/";
			
			// 2. 원본 파일 이름 알아오기
			String originalFileName = file.getOriginalFilename();
			String filePathName = uploadPath + originalFileName;
			
			// 3. 파일 생성
			File file1 = new File(filePathName);
			
			// 4. 서버로 전송
			file.transferTo(file1);				
			
			// 서비스에 파일 path와 파일명 전달  -> 서비스 메소드에서 변경
			// 서비스에서 반환된 PoseVO 리스트 저장
			poseList = poseService.poseEstimate(filePathName);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return poseList;
	}
	
	@RequestMapping("/objectDetect")
	public ArrayList<ObjectVO>  objectDetect(@RequestParam("uploadFile") MultipartFile file) {		
		
		ArrayList<ObjectVO>objectList = null; 
		
		try {
			// 1. 파일 저장 경로 설정 : 실제 서비스되는 위치 (프로젝트 외부에 저장)
			String uploadPath = "C:/upload/";
			
			// 2. 원본 파일 이름 알아오기
			String originalFileName = file.getOriginalFilename();
			String filePathName = uploadPath + originalFileName;
			
			// 3. 파일 생성
			File file1 = new File(filePathName);
			
			// 4. 서버로 전송
			file.transferTo(file1);				
			
			// 서비스에 파일 path와 파일명 전달  -> 서비스 메소드에서 변경
			// 서비스에서 반환된 PoseVO 리스트 저장
			objectList = objectService.objectDetect(filePathName);			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectList;
	}
	
	@RequestMapping("/clovaSTT")
	public String  clovaSTT(@RequestParam("uploadFile") MultipartFile file) {		
		
		String result = "";
		
		try {
			// 1. 파일 저장 경로 설정 : 실제 서비스되는 위치 (프로젝트 외부에 저장)
			String uploadPath = "C:/upload/";
			
			// 2. 원본 파일 이름 알아오기
			String originalFileName = file.getOriginalFilename();
			String filePathName = uploadPath + originalFileName;
			
			// 3. 파일 생성
			File file1 = new File(filePathName);
			
			// 4. 서버로 전송
			file.transferTo(file1);				
			
			// 서비스에 파일 path와 파일명 전달  -> 서비스 메소드에서 변경
			// 서비스에서 반환된 PoseVO 리스트 저장
			result = sttService.clovaSpeechToText(filePathName);			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	// language 언어 선택 추가
	@RequestMapping("/clovaSTT2")
	public String  clovaSTT2(@RequestParam("uploadFile") MultipartFile file,
											  @RequestParam("language") String language) {		
		
		String result = "";
		
		try {
			// 1. 파일 저장 경로 설정 : 실제 서비스되는 위치 (프로젝트 외부에 저장)
			String uploadPath = "C:/upload/";
			
			// 2. 원본 파일 이름 알아오기
			String originalFileName = file.getOriginalFilename();
			String filePathName = uploadPath + originalFileName;
			
			// 3. 파일 생성
			File file1 = new File(filePathName);
			
			// 4. 서버로 전송
			file.transferTo(file1);				
			
			// 서비스에 파일 path와 파일명 전달  -> 서비스 메소드에서 변경
			// 서비스에서 반환된 PoseVO 리스트 저장
			result = sttService.clovaSpeechToText2(filePathName, language);			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	// language 언어 선택 추가
		@RequestMapping("/clovaTTS")
		public String  clovaTTS(@RequestParam("uploadFile") MultipartFile file,
												@RequestParam("language") String language) {		
			
			String result = "";
			
			try {
				// 1. 파일 저장 경로 설정 : 실제 서비스되는 위치 (프로젝트 외부에 저장)
				String uploadPath = "C:/upload/";
				
				// 2. 원본 파일 이름 알아오기
				String originalFileName = file.getOriginalFilename();
				String filePathName = uploadPath + originalFileName;
				
				// 3. 파일 생성
				File file1 = new File(filePathName);
				
				// 4. 서버로 전송
				file.transferTo(file1);				
				
				// 서비스에 파일 path와 파일명 전달  -> 서비스 메소드에서 변경
				// 서비스에서 저장된 파일명 받아오기
				result = ttsService.clovaTextToSpeech2(filePathName, language);			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result; // 저장된 음성 파일명 반환
		}
		
		
		@RequestMapping("/chatbotCall")
		public String  chatbotCall(@RequestParam("message") String message ) {
			String result = chatService.main(message);
			return result;
		}
	
		
		@RequestMapping("/chatbotTTS")
		public String  chatbotTTS(@RequestParam("message") String message ) {
			String result = ttsService.chatbotTextToSpeech(message);
			return result;  // voiceFileName;  // 저장된 음성 파일명 반환
		}
		
		@RequestMapping("/chatbotCalIMgLink")
		public String  chatbotCalIMgLink(@RequestParam("message") String message ) {
			String result = chatService.imgLinkMainService(message);
			return result;
		}
		
		@RequestMapping("/mychatbot")
		public String  mychatbot(@RequestParam("message") String message ) {
			String result = mychatService.mychatbot(message);
			return result;
		}
		
		
}
