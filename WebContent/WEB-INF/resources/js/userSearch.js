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
		document.usersearch.action="usersrch";
	document.usersearch.method="post";
    document.usersearch.submit();
	}
	
	return false;
}

function activateUser(regId,userType){
	confirmAlert("Do you want to activate the user ?");
	//if(confirm("Do you want to activate the user ?"))
	$("#ok").click(function(){
	$.ajax({  
            url:"activateUser",
            type: "post",  
            data: {regId:regId,status:'Active',userType:userType},
            error:function(xhr,status,er){
                console.log(er);
$('#error').html('There is some error while activating the user. Please try again !');
            },
            success: function(data) {
            	console.log('Data: '+data); 
				var array = (""+data+"").split(',');
				var url = 'userRoleMap';
				var form;
console.log('Data: '+array[2]);
if(array.length>2){
	successAlert(""+array[2]+"'s account is activated successfully. with user Id: "+array[1]+" and password: "+array[0]+" . Kindly note it down for future use.");
//alert("The user '"+array[2]+"' with id: "+array[1]+" is activated with password: "+array[0]+" . Kindly note it down for future use."); 
//$("#ok").attr("id","successok");
//$("#cancel").hide();
form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + array[1] + '" />' +
'<input type="text" name="plainpassword" value="' + array[0] + '" />' +
'<input type="text" name="username" value="' + array[2] + '" />' +
'<input type="text" name="userstatus" value="Activate" />' +
  '</form>');
$("#successok").click(function(){
$('body').append(form);
form.submit();  
});  
$(".close").click(function(){
	$('body').append(form);
form.submit();
	}); 
}
else{
	successAlert("<font color='red'>"+array[1]+"'s </font> account is activated successfully. His/Her user Id:<font color='red'> "+array[0]+"</font> and <font color='red'>with old password</font>");
	//alert("The user '"+array[1]+"' with id: "+array[0]+" is activated !"); 
	form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + array[0] + '" />' +
'<input type="text" name="username" value="' + array[1] + '" />' +
'<input type="text" name="userstatus" value="Activate" />' +
  '</form>');
$("#successok").click(function(){
$('body').append(form);
form.submit();  
});  
$(".close").click(function(){
	$('body').append(form);
form.submit();
	}); 
}

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
//alert("The user : "+array[0]+" is inactivated successfully !."); 
successAlert("<font color='red'>"+array[1]+"'s </font> account is inactivated successfully !.");
//$("#ok").attr("id","successok");
//$("#cancel").hide();
var url = 'usersrch';
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
		//alert("There is some error while InActivation of user !"); 
		
	}
              
            }
        });
});
	return false;
	e.preventDefault();
}


function deleteUser(regId){
	//if(confirm("Do you want to delete ?"))
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
//alert("The user : "+array[0]+" is deleted successfully !."); 
successAlert("<font color='red'>"+array[0]+"'s</font> account is deleted successfully !.");
//$("#ok").attr("id","successok");
//$("#cancel").hide();
var url = 'usersrch';
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
		$('#error').html("There is some error while Deletion of user !");
		//alert("There is some error while Deletion of user !"); 
		
	}
              
            }
        });
});
	return false;
	e.preventDefault();
}

function printPage(e){

	e.preventDefault();
    var css = '<link rel="stylesheet" type="text/css"	href="resources/css/loginstyle.css" />'
	var divToPrint=document.getElementById('onlineuser');
	var newWin=window.open('','Print-Window');
	newWin.document.write('<html><head>'+css+'</head><body >'+divToPrint.innerHTML+'</body></html>');
  	newWin.document.close();
    newWin.focus();
    newWin.print();
    newWin.close();
}

function regIdClick(val){
	var regId=val;
	$.ajax({  
            url:"getUserId",
            type: "post",  
            data: {regId:regId},
            error:function(xhr,status,er){
                console.log(er);
$('#error').html("There is some error while fetching record of user !");
			//	alert("There is some error while fetching record of user !"); 
            },
            success: function(data) {
				var url = 'getUserDetail';
				var form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
				  '<input type="text" name="regId" value="' + data + '" />' +
				  '</form>');
				$('body').append(form);
				form.submit();
					
					}
	});
}

