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

import com.ai.ex.model.ObjectVO;

@Service
public class ObjectDetectionService {
	public  ArrayList<ObjectVO> objectDetect(String filePathName) {
		StringBuffer reqStr = new StringBuffer();
		String clientId = "7bu4gtupcd";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "Mew80Tkqfe2Wsy2MB5XWvYQxo754BjPLlRsCpjKm";//애플리케이션 클라이언트 시크릿값";
        
        ArrayList<ObjectVO> objectList = new ArrayList<ObjectVO>();

        try {
            String paramName = "image"; // 파라미터명은 image로 지정
            String imgFile = filePathName;
            File uploadFile = new File(imgFile);
            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
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
                System.out.println(response.toString());
                objectList = jsonToVoList(response.toString());
            } 
			/*   else {
			    System.out.println("error !!!");
			}*/
        } catch (Exception e) {
            System.out.println(e);
        }		
        
        return objectList;
	}
	
	// API 서버로부터 받은 JSON 형태의 데이톨부터 names과 x1,x2,y1,y2 추출해서
	// VO 리스트 만들어 반환	
	public ArrayList<ObjectVO> jsonToVoList(String jsonResultStr){
		ArrayList<ObjectVO> objectList = new ArrayList<ObjectVO>();
		
		try {
			// JSON 형태의 문자열에서 JSON 오브젝트 "predictions" 추출해서 JSONArray에 저장
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonResultStr);
			JSONArray poseArray = (JSONArray) jsonObj.get("predictions");			
			JSONObject obj0 = (JSONObject) poseArray.get(0);			
			
			JSONArray nameArray = (JSONArray) obj0.get("detection_names");	
			JSONArray boxArray = (JSONArray) obj0.get("detection_boxes");	
			
			for(int i=0; i<nameArray.size(); i++) {
				// name 추출
				String name = (String) nameArray.get(i);
				
				//x1, y1, x2, y2 추출
				JSONArray box = (JSONArray) boxArray.get(i);
				double x1 =(double) box.get(0);
				double y1 =(double) box.get(1);
				double x2 =(double) box.get(2);
				double y2 =(double) box.get(3);
				
				// VO에 저장
				ObjectVO vo = new ObjectVO();
				vo.setName(name);
				vo.setX1(x1);
				vo.setY1(y1);
				vo.setX2(x2);
				vo.setY2(y2);
				
				//리스트에 추가
				objectList.add(vo);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return objectList;
	}
}










