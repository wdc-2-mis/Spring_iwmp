/**
 * 
 */

function validate(e){
	var userId = $('#userId').val();
	var name = $('#userName').val();
	var designation = $('#designation').val();
	var department = $('#department').val();
	var email = $('#email').val();
	var userType = $('#userType').val();
	var status = $('#status').val();
	//alert(userId+'==='+name+'==='+designation+'==='+department+'==='+email+'==='+userType+'===' +status+'===');
	if(userId==='' && name==='' && designation==='' && department==='' && email==='' && userType==='' && status===''){
		//alert('Please provide at least one input ');
		$('#error').html('Please provide at least one input ');
		$('#userId').focus();
		e.preventDefault();
	}else{
		document.usersearch.action="slnausersrch";
	document.usersearch.method="post";
    document.usersearch.submit();
	}
	
	return false;
}

function activateUser(regId,userType){
	//if(confirm("Do you want to activate the user ?"))
	confirmAlert("Do you want to activate the user ?");
	$("#ok").click(function(){
	$.ajax({  
            url:"activateUser",
            type: "post",  
            data: {regId:regId,status:'Active',userType:userType},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	console.log('Data: '+data); 
				var array = (""+data+"").split(',');
				var url = 'addExistUserProject';
				var form;
console.log('Data: '+array[0]);
if(array.length>2){
//alert("The user with id: "+array[1]+" is activated with password: "+array[0]+" . Kindly note it down for future use."); 
successAlert(""+array[2]+"'s account is activated successfully. with user Id: "+array[1]+" and password: "+array[0]+" . Kindly note it down for future use.");
form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + array[1] + '" />' +
'<input type="text" name="plainpassword" value="' + array[0] + '" />' +
'<input type="text" name="username" value="' + array[2] + '" />' +
'<input type="text" name="userstatus" value="Activate" />' +
  '</form>');
}
else{
	//alert("The user with id: "+array[0]+" is activated !"); 
	successAlert("<font color='red'>"+array[1]+"'s </font> account is activated successfully. His/Her user Id:<font color='red'> "+array[0]+"</font> and <font color='red'>with old password</font>");
	form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
    '<input type="text" name="userid" value="' + array[0] + '" />' +
'<input type="text" name="username" value="' + array[1] + '" />' +
'<input type="text" name="userstatus" value="Activate" />' +
  '</form>');
}
$("#successok").click(function(){
$('body').append(form);
form.submit();  
});  
$(".close").click(function(){
	$('body').append(form);
form.submit();
	});  
            }
        });
});
	return false;
	e.preventDefault();
}



function inAactivateUser(regId,userType){
	confirmAlert('Do you want to inActivate the user ?');
	$("#ok").click(function(){
	$.ajax({  
            url:"inActivateUser",
            type: "post",  
            data: {regId:regId,status:'InActive',userType:userType},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	if(data.length>0){
		   	console.log('Data: '+data); 
				var array = (""+data+"").split(',');
console.log('Data: '+array[0]);
successAlert("<font color='red'>"+array[1]+"'s </font> account is inactivated successfully !."); 
var url = 'slnausersrch';
$("#successok").click(function(){
var form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + array[0] + '" />' +
'<input type="text" name="username" value="' + array[1] + '" />' +
'<input type="text" name="userstatus" value="InActivate" />' +
  '</form>');
$('body').append(form);
form.submit();
});

$(".close").click(function(){
var form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + array[0] + '" />' +
'<input type="text" name="userstatus" value="InActivate" />' +
  '</form>');
$('body').append(form);
form.submit();
});
	}else{
		$('#error').html("There is some error while InActivation of user !");
		
	}
              
            }
        });
});
	return false;
	e.preventDefault();
}

function deleteUser(regId){
	confirmAlert("Do you want to delete the user ?");
	$("#ok").click(function(){
	$.ajax({  
            url:"deleteUser",
            type: "post",  
            data: {regId:regId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	if(data.length>0){
		   	console.log('Data: '+data); 
				var array = (""+data+"").split(',');
console.log('Data: '+array[0]);
successAlert("<font color='red'>"+array[0]+"'s</font> account is deleted successfully !.");
var url = 'slnausersrch';
var form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + array[0] + '" />' +
'<input type="text" name="username" value="' + array[0] + '" />' +
'<input type="text" name="userstatus" value="Delete" />' +
  '</form>');
$("#successok").click(function(){
$('body').append(form);
form.submit();
});
$(".close").click(function(){
	$('body').append(form);
form.submit();
	});
	}else{
		alert("There is some error while Deletion of user !"); 
		
	}
              
            }
        });
});
	return false;
	e.preventDefault();
}

