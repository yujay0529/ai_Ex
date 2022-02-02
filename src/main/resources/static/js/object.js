/**
 * object.js
 */
 
 $(function(){
	
	$('#objectForm').on('submit', function(event){	
		event.preventDefault();
		 var formData = new FormData($('#objectForm')[0]);
		 
		 // 업로드된 파일명 알아오기
		 var fileName = $('#uploadFile').val().split("\\").pop();
		 //alert(fileName);
		 
		$.ajax({
			url:"objectDetect",
			enctype:'multipart/form-data',
			type:"post",
			data:formData,
			processData: false,  // 필수
			contentType: false,  // 필스
			success:function(result){
				drawCanvas(result, fileName);
			},
			error:function(e){
				alert("오류가 발생했습니다." + e)
			}
		});
		
		function drawCanvas(result, fileName){
			//mycanvas 캔버스 태그 객체로 생성
			var canvas = document.getElementById("objectCanvas");
			var context = canvas.getContext("2d");
		
			var objectImage = new Image();
			objectImage.src ="/images/" + fileName; 
			objectImage.width = canvas.width;
			objectImage.height = canvas.height;
			
			objectImage.onload = function(){
				context.drawImage(objectImage,0, 0, objectImage.width, objectImage.height );				
				
				var values = "";
									
				$.each(result, function(){
					// 사각형 좌표 
					var x1 = this.x1 * objectImage.width;
					var y1 = this.y1 * objectImage.height;
					var x2 = this.x2 * objectImage.width;
					var y2 = this.y2 * objectImage.height;
					
					context.font = '15px batang';
					context.fillStyle = "rgb(255, 0, 255)";	
					
					context.strokeStyle = "red";//선색상
					context.lineWidth = 2; // 선 굵기
					
					context.fillText(this.name, y1, x1);
					context.strokeRect(y1, x1, y2-y1, x2-x1);
					values += this.name + "(" + this.x1 + ", " + this.y1 + ", " + this.x2 + ", " + this.y2 + ") <br>";
							 
				});
				
				
				$('#resultDiv').html(values);
			};
		}	// function 끝	
	});		
});