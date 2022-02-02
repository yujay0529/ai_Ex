<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OCR</title>
		<script src="<c:url value='/js/jquery-3.6.0.min.js'/>"></script>
		<script src="<c:url value='/js/pose.js'/>"></script>		
		
	</head>
	<body>
		<!--  파일 업로드 -->
		<h3>포즈 인식</h3>
		<form id="poseForm" enctype="multipart/form-data">
			파일 : <input type="file" id="uploadFile" name="uploadFile"> 
			<input type="submit" value="결과 확인">		
		</form>
		<br><br>
		
		<!-- 결과 출력  -->
		<h3>포즈 인식 결과를 이미지에 좌표로 표시</h3>
		<canvas id="poseCanvas" width="600" height="600"></canvas>
		<br><br>
		
		<!-- 각 신체 부위와 좌표 값 출력 -->
		<div id="resultDiv"></div>
		
		<br><br>
		<a href="/">index 페이지로 이동</a>
		
	</body>
</html>