/**
 * object.js
 */
 
 $(function(){
	
	$('#sttForm').on('submit', function(event){	
		event.preventDefault();
		 var formData = new FormData($('#sttForm')[0]);
		 
		 // 업로드된 파일명 알아오기
		 var fileName = $('#uploadFile').val().split("\\").pop();
		// alert($('#uploadFile').val());
		$('audio').prop("src", '/voice/' + fileName);
		 
		$.ajax({
			url:"clovaSTT2",
			enctype:'multipart/form-data',
			type:"post",
			data:formData,
			processData: false,  // 필수
			contentType: false,  // 필스
			success:function(result){
				$('#resultDiv').text(result);  
			},
			error:function(e){
				alert("오류가 발생했습니다." + e)
			}
		});
		
	});		
});