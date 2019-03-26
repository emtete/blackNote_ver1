<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- <form  method="post" enctype="multipart/form-data"> -->
	<div class="uploadDiv">	
		<input type="file" name="uploadFile" multiple>
	</div>
	<button id='uploadBtn'>upload</button>
	<!-- </form> -->

<script src="/resources/vendor/jquery/jquery.min.js" ></script>
<script>
	//ajax 구현
	//파일을 받아야 한다.
	$(document).ready(function(){
		
		$('#uploadBtn').on( 'click', function(e){
			
			var formData = new FormData(),
				inputFile = $('input[name="uploadFile"]'),
				file = inputFile[0].files;
			
			console.log( files );
			
		});
	});
</script>

</body>
</html>






























