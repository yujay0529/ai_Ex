package com.ai.ex.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.ai.ex.model.FaceVO;

@ Service 
public class CFRFaceRecogService {
	public ArrayList<FaceVO> faceRecog(String filePathName) {
		 StringBuffer reqStr = new StringBuffer();
		 String clientId = "7bu4gtupcd";//애플리케이션 클라이언트 아이디값";
	     String clientSecret = "Mew80Tkqfe2Wsy2MB5XWvYQxo754BjPLlRsCpjKm";//애플리케이션 클라이언트 시크릿값";
	        
	        ArrayList<FaceVO> faceList = faceList = new ArrayList<FaceVO>();

	        try {
	            String paramName = "image"; // 파라미터명은 image로 지정
	            //String imgFile = "C:/ai/family.jpg";
	            String imgFile = filePathName;
	            File uploadFile = new File(imgFile);
	            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision/v1/face"; // 얼굴 감지
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setUseCaches(false);
	            con.setDoOutput(true);
	            con.setDoInput(true);
	            // multipart request
	            String boundary = "---" + System.currentTimeMillis() + "---";
	            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
	            OutputStream outputStream = con.getOutputStream();
	            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
	            String LINE_FEED = "\r\n";
	            // file 추가
	            String fileName = uploadFile.getName();
	            writer.append("--" + boundary).append(LINE_FEED);
	            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
	            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
	            writer.append(LINE_FEED);
	            writer.flush();
	            FileInputStream inputStream = new FileInputStream(uploadFile);
	            byte[] buffer = new byte[4096];
	            int bytesRead = -1;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	            outputStream.flush();
	            inputStream.close();
	            writer.append(LINE_FEED).flush();
	            writer.append("--" + boundary + "--").append(LINE_FEED);
	            writer.close();
	            BufferedReader br = null;
	            int responseCode = con.getResponseCode();
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 오류 발생
	                System.out.println("error!!!!!!! responseCode= " + responseCode);
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            }
	            String inputLine;
	            if(br != null) {
	                StringBuffer response = new StringBuffer();
	                while ((inputLine = br.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                br.close();
	                System.out.println(response.toString()); // 서버로부터 반환된 결과 출력 (JSON 형태)
	                faceList = jsonToVoList(response.toString());
	            } else {
	                System.out.println("error !!!");
	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	        
	        return faceList;
	}
	
	// API 서버로부터 받은 JSON 형태의 데이톨부터 value와 confidence 추출해서
	// VO 리스트 만들어 반환
	public ArrayList<FaceVO> jsonToVoList(String jsonResultStr){
		ArrayList<FaceVO> faceList = faceList = new ArrayList<FaceVO>();
		
		try {			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonResultStr);
			JSONArray faceArray = (JSONArray) jsonObj.get("faces");
			
			//JSON 형태의 문자열에서 JSON 오브젝트 "faces" 추출해서 JSONArray에 저장
			if(faceArray != null) {
				// JSONArray의 각 요소에서 value와 confidence 추출
				for(int i=0; i < faceArray.size(); i++) {
					FaceVO vo = new FaceVO();
					JSONObject tempObj = (JSONObject) faceArray.get(i);
					
					String value = "";
					double confidence = 0;
					
					// gender  추출
					JSONObject genObj = (JSONObject) tempObj.get("gender");
					value = (String) genObj.get("value");
					confidence = (double) genObj.get("confidence");
					// vo gender 값 설정
					vo.setGender(value);
					vo.setGenderConf(confidence);					
					
					// age  추출
					JSONObject ageObj = (JSONObject) tempObj.get("age");
					value = (String) ageObj.get("value");
					confidence = (double) ageObj.get("confidence");
					// vo age 값 설정
					vo.setAge(value);
					vo.setAgeConf(confidence);					
					
					// emotion  추출
					JSONObject emotObj = (JSONObject) tempObj.get("emotion");
					value = (String) emotObj.get("value");
					confidence = (double) emotObj.get("confidence");
					// vo emotion 값 설정
					vo.setEmotion(value);
					vo.setEmotionConf(confidence);					
					
					// pose  추출
					JSONObject poseObj = (JSONObject) tempObj.get("pose");
					value = (String) poseObj.get("value");
					confidence = (double) poseObj.get("confidence");
					// vo pose 값 설정
					vo.setPose(value);
					vo.setPoseConf(confidence);
					
					// FaceVO에 담아 리스트에 추가
					faceList.add(vo);
				}
				
			} else {
				//감지한 얼굴이 없는 경우 ("faces":[])
				FaceVO vo = new FaceVO();
				vo.setGender("없음");
				vo.setGenderConf(0);
				vo.setAge("없음");
				vo.setAgeConf(0);
				vo.setEmotion("없음");
				vo.setEmotionConf(0);
				vo.setPose("없음");
				vo.setPoseConf(0);				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return faceList;
	}	
}










