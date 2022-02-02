<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>얼굴 감지</h3>
	<!-- 서버로 파일 전송 -->
	<form id="faceRecogForm" method="post"
		action="<c:url value='/faceRecog'/>" enctype="multipart/form-data">
		파일 : <input type="file" id="uploadFile" name="uploadFile">  
		<input type="submit" value="결과 확인">
	</form>
	
	<c:if test="${not empty faceList}">
		<h3>얼굴 감지 결과</h3>

		<table border="1" width="800">
			<tr>
				<th>성별</th>
				<th>성별 정확도</th>
				<th>나이</th>
				<th>나이 정확도</th>
				<th>감정</th>
				<th>감정 정확도</th>
				<th>포즈</th>
				<th>포즈 정확도</th>
			</tr>
			<c:forEach items="${faceList }" var="face">
				<tr>
					<td>${face.gender }</td>
					<td><fmt:formatNumber value="${face.genderConf }"
							pattern="0.0000" /></td>
					<td>${face.age }</td>
					<td><fmt:formatNumber value="${face.ageConf }"
							pattern="0.0000" /></td>
					<td>${face.emotion }</td>
					<td><fmt:formatNumber value="${face.emotionConf }"
							pattern="0.0000" /></td>
					<td>${face.pose }</td>
					<td><fmt:formatNumber value="${face.poseConf }"
							pattern="0.0000" /></td>
				</tr>
			</c:forEach>

		</table>
	</c:if>
	<br><br>
	<c:if test="${not empty fileName}">
		<img src="<c:url value='/images/${fileName}'/>"/>
	</c:if>
	
	<br><br>
	<a href="/">index 페이지 이동</a>


</body>
</html>