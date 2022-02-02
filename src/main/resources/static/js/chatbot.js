/**
 * chatbot.js
 */
 
 $(function(){	
	
	$('#chatForm').on('submit', function(event){
		event.preventDefault();
		
		$.ajax({
			url:"chatbotCall",
			type:"post",
			data:{message: $('#message').val()},		
			success:function(result){				
				$('#chatBox').text(result);  // 결과 텍스트 출력				
			},
			error:function(){
				alert("오류가 발생했습니다.")
			}
		});
		
		/* 입력란 비우기*/
		$('#message').val('');
	});		
});