$(function(){
	
	$(document).on( 'change', '#financial', function (e) {
		
		    $project = $('#project').val();
			$financial = $('#financial').val();
	    	
 $.ajax({  
            url:'FetchQuadTargetdata',
            type: "post",  
            data: {projId:$project, year:$financial},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			$tblData="";
			var d = data[key];
								for ( var k in d) {
									$tblData+='<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>'+d[k].plan+'</td>';
									
									}
								$tbody.append($tblData);	
			}
		}
	}
	});

		});
	
	$(document).on( 'click', '#quarterlytarget', function (e) {
		e.preventDefault();
		alert('hi');
		});
	
	
	});