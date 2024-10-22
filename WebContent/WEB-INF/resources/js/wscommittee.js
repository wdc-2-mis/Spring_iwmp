function getwscommittee(e){
	var usreproject = $('#usreproject').val();
	
	if(usreproject==='')
	{
		alert('Please select project ');
		$('#usreproject').focus();
		e.preventDefault();
	}
	else{
		
		document.wscommittee.action="piaListWSComittee";
		document.wscommittee.method="get";
		document.wscommittee.submit();
	}
	return false;
}


$(function(){
	$('#addWatershedCommittee').on("click" ,function() {
		$('.watershedTable').hide();
		
		var $projectId = $('#usreproject');
		$projectId.val("");
		
		var $wcname = $('#wscname');
		$wcname.val("");
		
		$('#wscommittee .message').hide();
		
		$('#update').css("display","none");
		$('#savewc').css("display","block");
		
	});
	$('.watershedTable').hide();
	$('#wscommittee .message').hide();
	$('#usreproject').on("change" ,function(e) {
		var status=false;
		var projectId = $('#usreproject option:selected').val();
		$.ajax({  
            url:"checkWSCommittee",
            type: "post",  
            data: {projectId:projectId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	
            	if(data==='success'){
            		status=true;
            		alert('Project location is already Completed. ');
            		$('#wscommittee .message').show();
            		$('#wscommittee .message').html("Project location is already Completed.");
            		document.getElementById("wscname").setAttribute("disabled", status);
            		$('#delete').prop("disabled",true);
            		
            	}else{
            		status=false;
            		$('#wscommittee .message').hide();
            		$('#wscommittee .message').html(' ');
            		document.getElementById("wscname").removeAttribute('disabled');
            	}
            }
		});
		
		$.ajax({  
            url:"piaListWSComitteeList",
            type: "post",  
            data: {projectId:projectId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	//$('#wscommittee .message').hide();
            	console.log(data);
            	if (!$.trim(data)){   
            	   // alert("data if: " + data);
            		// $('.watershedTable').show();
            	}
            	else{   
            	  //  alert("data else: " + data);
            	    $('.watershedTable').hide();
            	}
            	$('#wcCommittee').empty();
            	$('#wcCommittee').append('<tr ><th style="text-align: center;">Select All &nbsp<input type="checkbox"  onchange="selects(this);"/></th><th style="text-align: center;">Watershed Committee Name</th></tr>');
            	for ( var key in data) {
				  	if (data.hasOwnProperty(key)) 
				  	{
						console.log("key"+key);
				  		$('.watershedTable').show();
            	$('#wcCommittee').append('<tr><td  align="center">'+
            			'<input type="checkbox" id="wc_code" name="wc_code" value='+key+'> </td>'+
            			'<td  align="center">'+data[key]+'</td>'+
            			'</tr>');
				  	}
            	}
            	$('#wcCommittee').append('<tr><td colspan="2" >'+
            			'<input type="button" name="delete" id="delete" value="Delete"  class="btn btn-info" onclick="deleteWatershed(this);" />'+
            			'</td></tr>');
            	
            }
		});
		e.preventDefault();
	});
});

function saveWatershed(e){
	var usreproject = $('#usreproject').val();
	var wscname = $('#wscname').val().trim();
	
	if(usreproject==='')
	{
		alert('Please select project ');
		$('#usreproject').focus();
		e.preventDefault();
	}
	if(wscname==='')
	{
		alert('Enter Watershed Committee');
		$('#wscname').focus();
		e.preventDefault();
	}
	else{
		if(confirm("Do you want to save Watershed Committee ?"))
		{
			$.ajax({  
	            url:"saveWSComittee",
	            type: "post",  
	            data: {projectId:usreproject,wcname:wscname},
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success: function(data) {
	            	
	            	console.log(data);
	            	if(data==='success'){
	            		$("#wscname").val(null);
	            		$('#wscommittee .message').show();
	            		$('#wscommittee .message').html("Watershed Committee Created successfully");
	            		$.ajax({  
	                        url:"piaListWSComitteeList",
	                        type: "post",  
	                        data: {projectId:usreproject},
	                        error:function(xhr,status,er){
	                            console.log(er);
	                        },
	                        success: function(data) {
	                        	$("#wscname").val("");
	                        	console.log(data);
	                        	$('#wcCommittee').empty();
	                        	$('#wcCommittee').append('<tr><th style="text-align: center;">Select All &nbsp<input type="checkbox"  onchange="selects(this);"/></th><th style="text-align: center;">Watershed Name</th></tr>');
	                        	for ( var key in data) {
	            				  	if (data.hasOwnProperty(key)) {
	            						console.log("key"+key);
	            				  		$('.watershedTable').show();
	                        			$('#wcCommittee').append('<tr><td  align="center" >'+
	                        			'<input type="checkbox" id="wc_code" name="wc_code" value='+key+'> </td>'+
	                        			'<td  align="center" >'+data[key]+'</td>'+
	                        			'</tr>');
	            				  	}
	                        	}
	                        	$('#wcCommittee').append('<tr><td >'+
	                        			'<input type="button" name="delete" id="delete" value="Delete"  class="btn btn-info" onclick="deleteWatershed(this);" />'+
	                        			'</td></tr>');
	                        }
	            		});
	            	}else{
	            		$('#wscommittee .message').html("Watershed Committee create Failed!");
	            	}
	            }
			});
		}
	}
	return false;
}

function updateWatershed(e){
	var usreproject = $('#usreproject').val();
	var wscname = $('#wscname').val().trim();
	var wcId = $('#wcId').val();
	if(usreproject==='')
	{
		alert('Please select project ');
		$('#usreproject').focus();
		e.preventDefault();
	}
	if(wscname==='')
	{
		alert('Enter Watershed Committee');
		$('#wscname').focus();
		e.preventDefault();
	}
	else{
		$('.watershedTable').hide();
		if(confirm("Do you want to Update Watershed Committee ?"))
		{
			$.ajax({  
	            url:"updateWSComittee",
	            type: "post",  
	            data: {projectId:usreproject,wcname:wscname,wcId:wcId},
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success: function(data) {
	            	
	            	console.log(data);
	            	if(data==='success'){
	            		$("#wscname").val(null);
	            		$('#wscommittee .message').show();
	            		$('#wscommittee .message').html("Watershed Committee Updated successfully");
	            		$.ajax({  
	                        url:"piaListWSComitteeList",
	                        type: "post",  
	                        data: {projectId:usreproject},
	                        error:function(xhr,status,er){
	                            console.log(er);
	                        },
	                        success: function(data) {
	                        	$('.watershedTable').show();
	                        	$("#wscname").val("");
	                        	console.log(data);
	                        	$('#wcCommittee').empty();
	                        	$('#update').css("display","none");
	                        	$('#savewc').css("display","block");
	                        	$('#wcCommittee').append('<tr><th style="text-align: center;">Select All &nbsp<input type="checkbox"  onchange="selects(this);"/></th><th style="text-align: center;">Watershed Name</th></tr>');
	                        	for ( var key in data) {
	            				  	if (data.hasOwnProperty(key)) {
	            						console.log("key"+key);
	            				  		$('.watershedTable').show();
	                        			$('#wcCommittee').append('<tr><td  align="center" >'+
	                        			'<input type="checkbox" id="wc_code" name="wc_code" value='+key+'> </td>'+
	                        			'<td  align="center"><a href="#"  onclick=linkClicked("'+data[key]+'",'+key+')>'+data[key]+'</a> </td>'+
	                        			'</tr>');
	            				  	}
	                        	}
	                        	$('#wcCommittee').append('<tr><td >'+
	                        			'<input type="button" name="delete" id="delete" value="Delete"  class="btn btn-info" onclick="deleteWatershed(this);" />'+
	                        			'</td></tr>');
	                        }
	            		});
	            	}else{
	            		$('#wscommittee .message').html("Watershed Committee Update Failed!");
	            	}
	            }
			});
		}
	}
	return false;
}

function deleteWatershed(e){
	
	//$('.watershedTable').hide();
	var usreproject = $('#usreproject').val();
	var wc_code = $('#wc_code').val();
	 var val = [];
     $('#wc_code:checked').each(function(i){
       val[i] = $(this).val();
     });
     
  //   $('#wc_code').prop('checked');
  //   $('#wc_code').prop('checked', false);
     
	/*if( !$('#wc_code').prop('checked'))
	{
		alert('Click on Watershed Committee check Box !');
		$('#wc_code').focus();
		e.preventDefault();
	}
	else{*/
		if(confirm("Do you want to Delete Watershed Committee ?"))
		{
			$.ajax({  
	            url:"deleteWSCommittee",
	            type: "post",  
	            data: {projectId:usreproject,wc_code:""+val},
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success: function(data) {
	            	
	            	console.log(data);
	            	if(data==='success'){
	            		$('#wscommittee .message').show();
	            		$('#wscommittee .message').html("Watershed Committee Deleted successfully");
	            		$.ajax({  
	                        url:"piaListWSComitteeList",
	                        type: "post",  
	                        data: {projectId:usreproject},
	                        error:function(xhr,status,er){
	                            console.log(er);
	                        },
	                        success: function(data) {
	                        	console.log(data);
	                        	$('.watershedTable').show();
	                        	$('#wcCommittee').empty();
	                        	$('#wcCommittee').append('<tr><th style="text-align: center;">Select All &nbsp<input type="checkbox"  onchange="selects(this);"/></th><th style="text-align: center;">Watershed Name</th></tr>');
	                        	for ( var key in data) {
	            				  	if (data.hasOwnProperty(key)) {
	            						console.log("key"+key);
	            				  		$('.watershedTable').show();
	                        			$('#wcCommittee').append('<tr><td  align="center">'+
	                        			'<input type="checkbox" id="wc_code" name="wc_code" value='+key+'> </td>'+
	                        			'<td  align="center">'+data[key]+' </td>'+
	                        			'</tr>');
	            				  	}
	                        	}
	                        	$('#wcCommittee').append('<tr><td >'+
	                        			'<input type="button" name="delete" id="delete" value="Delete"  class="btn btn-info" onclick="deleteWatershed(this);" />'+
	                        			'</td></tr>');
	                        }
	            		});
	            	}else{
	            		alert('Watershed Committee has mapped with village. It can not be deleted.');
	            		$('#wscommittee .message').show();
	            		$('#wscommittee .message').html("Watershed Committee has mapped with village. It can not be deleted.");
	            	}
	            }
			});
		}
//	} 
	return false;
}

function checkWatershed(e){
	
	//$('.watershedTable').hide();
	var usreproject = $('#usreproject').val();
    
			$.ajax({  
	            url:"checkWSCommittee",
	            type: "post",  
	            data: {projectId:usreproject},
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success: function(data) {
	            	
	            	console.log(data);
	            	if(data==='success'){
	            		$('#wscommittee .message').show();
	            		$('#wscommittee .message').html("Project location is already Completed.");
	            		document.getElementById("wscname").setAttribute("disabled", true);
	            		$('#delete').prop("disabled",true);
	            		
	            	}else{
	            		$('#wscommittee .message').show();
	            		document.getElementById("wscname").setAttribute("disabled", false);
	            	}
	            }
			});
	
	return false;
}


function linkClicked(value,key){ 
//	alert(value+" : "+key);
	$('#wscname').val(value);
	$('#wcId').val(key);
	$('.watershedTable').hide();
	$('#update').css("display","block");
	$('#savewc').css("display","none");
	return false;
}
/* 
function selects(){  
    var ele=document.getElementsByName('wc_code');  
    for(var i=0; i<ele.length; i++){  
        if(ele[i].type=='checkbox')  
            ele[i].checked=true;  
    } 
}  
 */
function selects(ele) {
    var checkboxes = document.getElementsByName('wc_code');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
//     if (ele.checked) {
//     	  for (var i = 0; i < checkboxes.length; i++) {
//               if (checkboxes[i].type == 'checkbox') {
//                   checkboxes[i].checked = true;
//               }
//           }
//     } else {
//         for (var i = 0; i < checkboxes.length; i++) {
//             console.log(i)
//             if (checkboxes[i].type == 'checkbox') {
//                 checkboxes[i].checked = false;
//             }
//         }
//     }
}
 