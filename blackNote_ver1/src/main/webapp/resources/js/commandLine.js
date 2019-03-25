
$(document).ready(function(){ 

	cssContainerHandler();


	$(window).resize(function() { 
		
		cssContainerHandler();
	});

	enterEvent();

	folderChangeNoneEnum( folder );
	folderChangeNoneEnum( folder0 );
	folderChangeNoneEnum( folder1 );
	folderChangeNoneEnum( folder2 );
	folderChangeNoneEnum( folder00 );
	folderChangeNoneEnum( folder01 );
	folderChangeNoneEnum( folder02 );

});

function folderChangeNoneEnum( folder ){
	Object.defineProperty( folder, 'path', {enumerable : false} );
	Object.defineProperty( folder, 'name', {enumerable : false} );
	Object.defineProperty( folder, 'type', {enumerable : false} );
	
}





var folder00 = {
	path : '/0번/갈매기',
	name : '갈매기',
	type : 'ol'
};
var folder01 = {
	path : '/0번/매',
	name : '매',
	type : 'ol'
};
var folder02 = {
	path : '/0번/기러기',
	name : '기러기',
	type : 'ol'
};

var folder0 = { 
	path : '/0번',
	name : '0번',
	type : 'ol',
	'갈매기' : folder00,
	'기러기' : folder02,
	'매' : folder01
 };


var folder1 = {
	path : '/1번',
	name : '1번',
	type : 'ol'
};
var folder2 = {
	path : '/2번',
	name : '2번',
	type : 'ol'
};

var folderProperties = [ 'path', 'name', 'type'];

var folder = {
	path : '/',
	name : '/',
	type : 'ol',
	'0번' : folder0,
	'1번' : folder1,
	'2번' : folder2
};

var currentPath = '/';
var currentPathObj = folder;

function cssContainerHandler(){
	
	var bottomCoordinate = $('.commandLine').offset().top;
	var containerHeight = bottomCoordinate - 80;

	$('.modal-table').css('height', containerHeight);
}


function enterEvent(){
	$('.commandLine').keydown( function(key){
		if(key.keyCode == 13){

			var commandParagraph = $('.commandLine').val();
			var commands = commandParagraph.trim().split(' ');

			if(commands[0] == 'create'){
				if( commands[1] == 'folder' ){

					if( typeof commands[2] != 'string' ){
						alert('폴더 이름을 입력해주세요.');
					}else{
						createFolder( commands[2], commands[3] );// (folderName, type)
						applyFolderToTag( folder );
					}

				}else if( commands[1] == 'page' ){

				}
				
			}else if( commands[0] == 'select'){
				if( commands[1] == 'folder' ){ 
					selectFolder(commands[2]);
				}
			}else if( commands[0] == 'currentPath' ){

				alert(currentPath);
			}else if( commands[0] == 'pwd'){
				pwd(currentPathObj);
			}
		}
	});
}



//패키지 관리 툴을 만들어야 한다.
//공백 혹은 /혹은 undefined 최상위 폴더로 한다.
function selectFolder( command ){

	//공백 혹은 /일 경우 최상위
	if( command == undefined || command.trim().length == 0  || command.trim() == '/'  ){
		currentPath = '/';
		currentPathObj = folder;
		alert( '해당 경로가 선택되었습니다 : ' + currentPath );
		return;
	}

	var reqPath = command.split('/');
	var processingDepth = folder; 
	var selectedFolderObj = findPath( reqPath, processingDepth );

	if( typeof selectedFolderObj == 'object' ){

		alert('경로가 선택되었습니다 : '+ command);
		currentPath = command; // 명령어로 지정한 경로가 존재한다면.
		currentPathObj = selectedFolderObj;
	}else{

		alert('경로가 존재하지 않습니다.');
	}

	//return folderObject or undefined
	function findPath( reqPath, processingDepth ){

		//경로가 /으로 시작될 때.. 
		if( reqPath[0].trim() == '' ){

			reqPath.splice(0,1); //현재 depth 폴더 제거
			return findPath( reqPath, processingDepth );	
		} 

		var reqPath0 = reqPath[0];
		reqPath.splice(0,1) //현재 depth 폴더 제거

		if( typeof processingDepth[ reqPath0 ] == 'object' ){

			return ( reqPath.length < 1 )?	
				processingDepth[ reqPath0 ] : 
				findPath( reqPath, processingDepth[reqPath0] );
		}else{
			
			return undefined;
		}	
	}

}

//현재 경로에 하나의 폴더를 만드는 기능을 우선적으로 구현.
//현재 경로의 depth를 구해야 한다.
//현재 경로( 만들 폴더의 상위 폴더 )에 폴더객체를 저장한다.
function createFolder( folderName, type ){

	var newFolder = new Folder( currentPath, folderName , type );
	folderChangeNoneEnum( newFolder );
	currentPathObj[ folderName ] = newFolder;

	alert( folderName+'폴더가 생성되었습니다.' );
}


function Folder( path, name, type ){

	this.path = ( path.slice(path.length - 1) == '/') ? 	path + name : 
															path + '/' + name;
	this.name = name;
	this.type = type;
}


function pwd( currentPathObj ){
	
	var affiliatedFolders = '';
	
	for( var e in currentPathObj ){
		
		affiliatedFolders += e +', ';
	}

	var strLength = affiliatedFolders.length;

	alert( '현재 경로의 폴더목록 : ' + affiliatedFolders.slice( 0, strLength-2 ) );
}


function applyFolderToTag( folder ){

	// var type = folder['type'];
	
	//0 : start tag
	//1 : end tag
	var tag = {
		'ul0' : '<ul ',
		'ul1' : '</ul>',
		'ol0' : '<ol ',
		'ol1' : '</ol>',
		'li0' : '<li ',
		'li1' : '</li>'
	};


	var outerStart = 'class = "outerTag';
	var innerStart = 'class = "innerTag';
	var classEnd = '" >';
	var separator = '_';
	var innerHtml = '';
	
	var html = createOuterTag( folder, outerStart, innerStart, '' );

	console.log(html);
	$('.box').append(html);

	function createOuterTag( folder, outerStart, innerStart, suffix ){

		var type = folder['type'];
		var html = '';

			html += tag[ type+'0' ] + 
					outerStart + 
					// separator + 
					suffix + 
					classEnd + 
					'\n' + 
					createInnerTag( folder, outerStart, innerStart, suffix ) + 
					tag[ type+'1' ] + 
					'\n';
		
		return html;
	}

	//inner, outter 차이 : 
	//type
	//tag
	//tag의 text를 넣는 내용이 다르다.
	function createInnerTag( folder, outerStart, innerStart, suffix ){
		
		// suffix += 0;
		var order = 0;
		var html = '';
		var innerFolder = folder; 


		//폴더의 내용이 비어있다면 작동하지 않는다.
		for( var property in folder ){
		
			html += tag['li0'] + 
					innerStart +
					suffix + 
					separator + 
					order + 
					classEnd + 
					'\n'+ 
					property;

			html += ( isEmpty( folder, property ) ) ?
							tag['li1'] +   '\n' :
							createOuterTag( folder[ property ], outerStart, innerStart, separator+order )
							+ tag['li1'] + '\n';
			
			order++;		
		}
		
		return html;

	}

	function isEmpty( folder, property ){

		return ( Object.keys(folder[property]).length == 0 ) ? true : false ;
	}
}








