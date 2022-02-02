/**
 * ocr.js
 */
 
 $(function(){
	$('#ocrForm').on('submit', function(event){
		event.preventDefault();
		 var formData = new FormData($('#ocrForm')[0]);
		 
		 // 업로드된 파일명 알아오기
		 var fileName = $('#uploadFile').val().split("\\").pop();
		 //alert(fileName);
		 
		$.ajax({
			url:"clovaOCR",
			enctype:'multipart/form-data',
			type:"post",
			data:formData,
			processData: false,  // 필수
			contentType: false,  // 필스
			success:function(result){
				$('#resultH3').text("OCR : 텍스트 추출 결과"); // 제목 출력
				$('#resultDiv').text(result);  // 결과 텍스트 출력
				// 이미지 출력 : div에 append 시킴
				$('#image').empty();
				$('#image').append('<img src="/images/'+fileName+'"/>');
			},
			error:function(){
				alert("오류가 발생했습니다.")
			}
		});
		
	});		
});