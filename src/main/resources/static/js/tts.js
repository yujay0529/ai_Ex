/**
 * object.js
 */
 
 $(function(){
	
	$('#ttsForm').on('submit', function(event){	
		event.preventDefault();
		 var formData = new FormData($('#ttsForm')[0]);
		 
		 // 업로드된 파일명 알아오기
		 var fileName = $('#uploadFile').val().split("\\").pop();
		// alert($('#uploadFile').val());		
		 
		$.ajax({
			url:"clovaTTS",
			enctype:'multipart/form-data',
			type:"post",
			data:formData,
			processData: false,  // 필수
			contentType: false,  // 필스
			success:function(result){
				$('audio').prop("src", '/voice/' + result);
				$('#resultDiv').text(result);  // // 저장된 음성 파일명 
			},
			error:function(e){
				alert("오류가 발생했습니다." + e)
			}
		});
		
	});		
});