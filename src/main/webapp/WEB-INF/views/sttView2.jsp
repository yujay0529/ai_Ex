<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OCR</title>
		<script src="<c:url value='/js/jquery-3.6.0.min.js'/>"></script>
		<script src="<c:url value='/js/stt2.js'/>"></script>		
		
	</head>
	<body>
		<!--  파일 업로드 -->
		<h3>CSR : STT (음성을 텍스트로 변환)</h3>
		<form id="sttForm" enctype="multipart/form-data">
			파일 : <input type="file" id="uploadFile" name="uploadFile"><br><br>
			
			언어 : <select name="language">
						<option value="Kor">한국어</option>
						<option value="Eng">영어</option>
						<option value="Jpn">일본어</option>
						<option value="Chn">중국어</option>			
					</select><br><br>
			
			<input type="submit" value="결과 확인">		
		</form>
		<br><br>
		
		<!-- 결과 출력  -->	
		<h3>STT :  음성을 텍스트로 변환한 결과</h3> 	
		<div id="resultDiv"></div><br><br>
		
		<div><audio preload="auto" controls></audio></div>
		
		<br><br>
		<a href="/">index 페이지로 이동</a>
		
	</body>
</html>