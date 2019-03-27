<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/resources/css/upload.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
	
	<div class="uploadDiv">	
		<input type="file" name="uploadFile" multiple>
	</div>
	<button id='uploadBtn'>upload</button>
	
	<div class="uploadResult">
		<ul>
		
		</ul>
	</div>
	

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
		
		var cloneUploadDiv = $('.uploadDiv').clone();
		
		$('#uploadBtn').on( 'click', function(e){
			
			var formData = new FormData(),
				inputFile = $('input[name="uploadFile"]'),
				files = inputFile[0].files;
			
			/* console.log( files ); */
			
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
					showResult( result );
					console.log( result[0]['fileName'] );
					$('.uploadDiv').html( cloneUploadDiv.html() );
				}
			});//ajax
			
		});//#uploadBtn
	});//document ready
	
	function showResult( resultArr ){
		
		var arrLength = resultArr.length;
		var str = '';
		
		resultArr.forEach( function( obj, i ){
			
			if( obj.image ){
				str += '<li>' + '<image src="/resources/img/attach.jpg">'+ obj.fileName + '</li>';
			}else{
				str += '<li>' + obj['fileName'] + '</li>';			
			}
		});
		
		$('.uploadResult ul').append(str);
	}
</script>

</body>
</html>






























