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
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.ai.ex.model.PoseVO;

@Service
public class PoseEstimationService {
	public ArrayList<PoseVO> poseEstimate(String filePathName) {
		
		StringBuffer reqStr = new StringBuffer();
		String clientId = "7bu4gtupcd";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "Mew80Tkqfe2Wsy2MB5XWvYQxo754BjPLlRsCpjKm";//애플리케이션 클라이언트 시크릿값";
        ArrayList<PoseVO> poseList = new ArrayList<PoseVO>();
        
        try {
            String paramName = "image"; // 파라미터명은 image로 지정
            String imgFile = filePathName;
            File uploadFile = new File(imgFile);
            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-pose/v1/estimate"; // 사람 인식
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
                
                // JSON 문자열 추출 결과 받음
                poseList = jsonToVoList(response.toString());
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return poseList;
	}
	
	// API 서버로부터 받은 JSON 형태의 결과 데이터를 전달받아서 index, x, y 추출하고
	// PoseVO 리스트 만들어 반환하는 함수
	public ArrayList<PoseVO> jsonToVoList(String jsonResultStr){
		ArrayList<PoseVO> poseList = new ArrayList<PoseVO>();
		double x, y;
		
		try {
			// JSON 형태의 문자열에서 JSON 오브젝트 "predictions" 추출해서 JSONArray에 저장
			// x, y 추출
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonResultStr);
			JSONArray poseArray = (JSONArray) jsonObj.get("predictions");			
			JSONObject obj0 = (JSONObject) poseArray.get(0);
			
			for(int i=0; i<18; i++) {
				// 신체 각 부위 이름이 "0", "1", 문자이므로  정수 i를 문자열로 변환
				// String.valueOf(i) 사용해서 문자열로 변환 또는 i+"" (정수+ 문자열 : 문자열 연산해서 결과 문자열)
				//if(obj0.get(String.valueOf(i)) != null) {
				if(obj0.get(i +"") != null) {
					//JSONObject tempObj = (JSONObject) obj0.get(String.valueOf(i));
					JSONObject tempObj = (JSONObject) obj0.get(i+"");
					x = (double) tempObj.get("x");
					y = (double) tempObj.get("y");
				} else {
					x = 0;
					y = 0;
				}
				// VO에 저장하고
				PoseVO vo = new PoseVO();
				vo.setIndex(i);
				vo.setX(x);
				vo.setY(y);			
			
				// 리스트에 추가
				poseList.add(vo);			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return poseList;
	}
}






