/**
 * ocr.js
 */
 
 $(function(){
	$('#poseForm').on('submit', function(event){
		event.preventDefault();
		 var formData = new FormData($('#poseForm')[0]);
		 
		 // 업로드된 파일명 알아오기
		 var fileName = $('#uploadFile').val().split("\\").pop();
		 //alert(fileName);
		 
		$.ajax({
			url:"poseDetect",
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
			var canvas = document.getElementById("poseCanvas");
			var context = canvas.getContext("2d");
		
			var poseImage = new Image();
			poseImage.src ="/images/" + fileName; 
			poseImage.width = canvas.width;
			poseImage.height = canvas.height;
			
			poseImage.onload = function(){
				context.drawImage(poseImage,0, 0, poseImage.width, poseImage.height );
					
				var colors = ["red", "blue", "yellow", "yellow","yellow","green", "green",
									"green", "skyblue","skyblue","skyblue","black","black","black",
									"pink","gold", "orange","brown"];		
									
				var position = ["코", "목", "오른쪽 어깨", "오른쪽 팔굼치", "오른쪽 손목", 
											"왼쪽 어깨", "왼쪽 팔굼치", "왼쪽 손목", "오른쪽 엉덩이", "오른쪽 무릎",
											"오른쪽 발목", "왼쪽 엉덩이", "왼쪽 무릎", "왼쪽 발목", "오른쪽 눈",
											"왼쪽 눈", "오른쪽 귀", "왼쪽 귀"];				
				
				var values = "";
									
				$.each(result, function(i){
					 if(this.x !=0 || this.y !=0){
							context.strokeStyle=colors[i];//선색상
				        	context.strokeRect(this.x * poseImage.width, this.y*poseImage.height, 2, 2);
				        	var text = this.x.toFixed(2) + "," + this.y.toFixed(2);
				        	context.font = '10px serif';
				        	context.strokeText(text, this.x * poseImage.width, this.y*poseImage.height);							        					        				        			        	
					 };		
					 values += position[i] + "(" + this.x + ", " + this.y + ") <br>"; 					 
				});
				
				$('#resultDiv').html(values);
			};
		}	// function 끝	
	});		
});