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
	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; //5MB
	
	function checkExtension( fileName, fileSize ){
		
		if( fileSize >= maxSize ){
			alert('파일 사이즈 초');
			return false;
		}
		
		if( regex.test( fileName ) ){
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	$(document).ready(function(){
		
		$('#uploadBtn').on( 'click', function(e){
			
			var formData = new FormData(),
				inputFile = $('input[name="uploadFile"]'),
				files = inputFile[0].files;
			
			console.log( files );
			
			for( var i=0; i < files.length; i++ ){
				if( !checkExtension( files[i].name, files[i].size ) ){
					
					return false;
				}
				formData.append( 'uploadFile', files[i] );
			}
			
			$.ajax({
				url : '/uploadAjaxAction',
				processData : false,
				contentType : false,
				data : formData,
				type : 'POST',
				dataType : 'json',
				success : function( result ){
					console.log( result );
				}
			});//ajax
			
		});//#uploadBtn
	});//document ready
</script>

</body>
</html>






























