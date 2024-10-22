function getUser(){
	var id=$('#userType').val();
	var url;
	var form =$('#userrolemap').attr('name');
	if(form==='userrolemap')
	url="getUserListUnAssigned";
	else if(form === 'updateuserrolemap')
	url = "getUserListAssigned";
	$.ajax({  
            url:url,
            type: "post",  
            data: {id:id},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $userRoleMapUser = $('#user');
						$userRoleMapUser.empty();
        				$userRoleMapUser.append('<option value=" "> --Select Users--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $userRoleMapUser.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
}

function getStateUsers(){
	var userType=$('#userType').val();
	var stateCode=$('#state').val();
	var url;
	var form =$('#userrolemap').attr('name');
	if(form==='userrolemap')
	url="getUserListUnAssignedByState";
	else if(form === 'updateuserrolemap')
	url = "getUserListAssignedByState";
	$.ajax({  
            url:url,
            type: "post",  
            data: {userType:userType,stateCode:stateCode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $userRoleMapUser = $('#user');
						$userRoleMapUser.empty();
        				$userRoleMapUser.append('<option value=" "> --Select Users--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $userRoleMapUser.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
}

function getDistrictUsers(){
	var userType=$('#userType').val();
	var stateCode=$('#state').val();
	var distCode=$('#district').val();
	var url;
	var form =$('#userrolemap').attr('name');
	if(form==='userrolemap')
	url="getUserListUnAssignedByDistrict";
	else if(form === 'updateuserrolemap')
	url = "getUserListAssignedByDistrict";
	$.ajax({  
            url:url,
            type: "post",  
            data: {userType:userType,stateCode:stateCode,distCode:distCode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $userRoleMapUser = $('#user');
						$userRoleMapUser.empty();
        				$userRoleMapUser.append('<option value=" "> --Select Users--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $userRoleMapUser.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
}

function userTypeChange(){
	var userType=$('#userType').val();
	if(userType==='SL'){
	$('.state').removeClass( "d-none" );
	$('.district').addClass( "d-none" );
	$('.project').addClass( "d-none" );
	getState();
	}
	if(userType==='DI'){
	$('.state').removeClass( "d-none" );
	$('.district').removeClass( "d-none" );
	$('.project').addClass( "d-none" );
	getState();
	}
	if(userType==='PI'){
	$('.state').removeClass( "d-none" );
	$('.district').removeClass( "d-none" );
	$('.project').removeClass( "d-none" );
	getState();
	}
	if(userType==='DL' || userType==='' || userType.length<=1){
	$('.state').addClass( "d-none" );
	$('.district').addClass( "d-none" );
	$('.project').addClass( "d-none" );
	}if($('#userType option:selected').text()==='-- Select Type --'){
		addInputFieldErrorCss('#userType');
	}else{
		removeInputFieldErrorCss('#userType');
	}
}

function getState(){
	var $ddlDistrict = $('#district');
	$ddlDistrict.empty();
	$ddlDistrict.append('<option value=" "> --Select--</option>');
	$.ajax({  
            url:"getAllState",
            type: "post",  
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $ddlState = $('#state');
						$ddlState.empty();
        				$ddlState.append('<option value=" "> --Select--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlState.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
}

function getDistrict(){
	var stateCode=$('#state').val();
	if($('#state option:selected').text()==' --Select--'){
		addInputFieldErrorCss('#state');
	}else{
		removeInputFieldErrorCss('#state');
	}
	$.ajax({  
            url:"getDistrictByStateCode",
            type: "post", 
			data:{id:stateCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $ddlDistrict = $('#district');
						$ddlDistrict.empty();
        				$ddlDistrict.append('<option value=" "> --Select--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlDistrict.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
}

function getProjectByDistrict(){
	var stateCode=$('#state').val();
	var distCode=$('#district').val();
	var userType=$('#userType').val();
	if($('#district option:selected').text()===' --Select--'){
		addInputFieldErrorCss('#district');
	}else{
		removeInputFieldErrorCss('#district');
	}
	if(userType==='PI'){
	$.ajax({  
            url:"getProjectByDistrict",
            type: "post", 
			data:{stateCode:stateCode,distCode:distCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				var $ddlProject = $('#project');
						$ddlProject.empty();
        				$ddlProject.append('<option value=" "> --Select All-- </option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlProject.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
}
}

function getProjectByUser(){
	var stateCode=$('#state').val();
	var distCode=$('#district').val();
	var user=$('#user').val();
	if($('#user option:selected').text()===' --Select Users--'){
		addInputFieldErrorCss('#user');
	}else{
		removeInputFieldErrorCss('#user');
	}
	var value;
	var form =$('#userrolemap').attr('name');
	var selectedUserType= $( "#user option:selected" ).text().substring(0, 2);
	var userType=$('#userType').val();
	if(form==='updateuserrolemap')
	if(userType==='PI' || selectedUserType === 'PI'){
		
		$('.project').removeClass( "d-none" );
	$.ajax({  
            url:"getProjectByUser",
            type: "post", 
			data:{stateCode:stateCode,distCode:distCode,user:user}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
	console.log(data);
				var $ddlProject = $('#project');
						$ddlProject.empty();
        				$ddlProject.append('<option value=" ">--Select All--</option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							value=data[key];
							for ( var k in value) {
						       if (value.hasOwnProperty(k)) {
							if(key==='0')
								$ddlProject.append('<option value='+k+' selected>' +value[k] + '</option>');
							if(key==='1')
								$ddlProject.append('<option value='+k+'>' +value[k] + '</option>');
									}
								}
						    }
						  }
                }
        });
}
else
		$('.project').addClass( "d-none" );
		else
		if(userType==='PI' || selectedUserType === 'PI'){
		
		$('.project').removeClass( "d-none" );
	$.ajax({  
            url:"getProjectByUser",
            type: "post", 
			data:{stateCode:stateCode,distCode:distCode,user:user}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
	console.log(data);
				var $ddlProject = $('#project');
						$ddlProject.empty();
        				$ddlProject.append('<option value=" " id="selectAll">--Select All--</option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							value=data[key];
							for ( var k in value) {
						       if (value.hasOwnProperty(k)) {
							/*if(key==='0')
								$ddlProject.append('<option value='+k+' selected>' +value[k] + '</option>');*/
							if(key==='1')
								$ddlProject.append('<option value='+k+'>' +value[k] + '</option>');
									}
								}
						    }
						  }
                }
        });
}
else
		$('.project').addClass( "d-none" );

}



function saveRoleUserMap()
{
	var userType=$('#userType').val();
	var selectedUserType= $( "#user option:selected" ).text().substring(0, 2);
	if(userType===' '){
		//alert("Please select User Type.");
		$('#error').html("Please select User Type.");
		addInputFieldErrorCss('#userType');
        $('#userType').focus();
        return false;
	}else{
		removeInputFieldErrorCss('#userType');
	}
	if(userType==='SL' && $('#state').val()===' '){
		//alert("Please select State.");
		$('#error').html("Please select State.");
		addInputFieldErrorCss('#state');
        $('#state').focus();
        return false;
	}else{
		removeInputFieldErrorCss('#state');
	}
	if(userType==='DI' && $('#state').val()===' '){
		//alert("Please select State.");
		$('#error').html("Please select State.");
		addInputFieldErrorCss('#state');
        $('#state').focus();
        return false;
	}else{
		removeInputFieldErrorCss('#state');
	}
	 if(userType==='DI' && $('#district').val()===' '){
		//alert("Please select District.");
		$('#error').html("Please select District.");
		addInputFieldErrorCss('#district');
        $('#district').focus();
        return false;
	}else{
		removeInputFieldErrorCss('#district');
	}
	if(userType==='PI' && $('#state').val()===' '){
		//alert("Please select State.");
		$('#error').html("Please select State.");
		addInputFieldErrorCss('#state');
        $('#state').focus();
        return false;
	}else{
		removeInputFieldErrorCss('#state');
	}
	 if(userType==='PI' && $('#district').val()===' '){
		//alert("Please select District.");
		$('#error').html("Please select District.");
		addInputFieldErrorCss('#district');
        $('#district').focus();
        return false;
	}
	else{
		removeInputFieldErrorCss('#district');
	}
	if($('#user').val()===" ")
    {
        //alert("Please select User.");
		$('#error').html("Please select User.");
		addInputFieldErrorCss('#user');
        $('#user').focus();
        return false;
    }else{
		removeInputFieldErrorCss('#user');
	}
	 if(userType==='PI' || selectedUserType ==='PI'){
		var options = $('#project > option:selected');
         if(options.length == 0){
           // alert("Please select Project.");
			$('#error').html("Please select Project.");
			addInputFieldErrorCss('#project');
        $('#project').focus();
        return false;
         }
		
	}else{
		removeInputFieldErrorCss('#project');
	}
	
    
    
    if($('#roleId').val()===" ")
    {
       // alert("Please select Role.");
		$('#error').html("Please select Role.");
		addInputFieldErrorCss('#roleId');
        $('#roleId').focus();
        return false;
    }else{
		removeInputFieldErrorCss('#roleId');
	}
    var form =$('#userrolemap').attr('name');
	if(form==='userrolemap' ){
	document.userrolemap.action="saveUnassignedUser";
	document.userrolemap.method="post";
    document.userrolemap.submit();
	}
	else if(form === 'updateuserrolemap'){
		confirmAlert("Do you want to update the data ?");
		$("#ok").click(function(){
	document.updateuserrolemap.action="updateAssignedUser";
	document.updateuserrolemap.method="post";
    document.updateuserrolemap.submit();
});
	}
	
	
    
}

function checkFieldroleId(){
	if($('#roleId option:selected').text()==='-- Select Roles --'){
		addInputFieldErrorCss('#roleId');
	}else{
		removeInputFieldErrorCss('#roleId');
	}
}

function checkFieldProject(){
	if($('#project option:selected').text()==='--Select All--'){
		addInputFieldErrorCss('#project');
	}else{
		removeInputFieldErrorCss('#project');
	}
}


function updateRoleUserMap(form){
	//alert(form);
	$('#userrolemap').attr('name', 'updateuserrolemap');
	$('#cancel').removeAttr( "disabled" );
	$('#update').attr( "disabled","disabled" );
	$('#userType').prop('selectedIndex',0);
	 $('#state').prop('selectedIndex',0);
$('#district').prop('selectedIndex',0);
$('#project').prop('selectedIndex',0);
$('#user').prop('selectedIndex',0);
$('#roleId').prop('selectedIndex',0);
userTypeChange();
var $userRoleMapUser = $('#user');
	$userRoleMapUser.empty();
    $userRoleMapUser.append('<option value=" "> --Select Users--</option>');
}

function cancelRoleAssign(){
	$('#userrolemap').attr('name', 'userrolemap');
	$('#update').removeAttr( "disabled" );
	$('#cancel').attr( "disabled","disabled" );
	$('#userType').prop('selectedIndex',0);
	 $('#state').prop('selectedIndex',0);
$('#district').prop('selectedIndex',0);
$('#project').prop('selectedIndex',0);
$('#user').prop('selectedIndex',0);
$('#roleId').prop('selectedIndex',0);
userTypeChange();
var $userRoleMapUser = $('#user');
	$userRoleMapUser.empty();
    $userRoleMapUser.append('<option value=" "> --Select Users--</option>');
}


function getRoleAssigned(){
	var regId=$('#user').val();
	 var form =$('#userrolemap').attr('name');

	if(form === 'updateuserrolemap'){
	$.ajax({  
            url:"getRoleAssignedForUser",
            type: "post", 
			data:{regId:regId}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				var $ddlRole = $('#roleId');
						$ddlRole.empty();
        				$ddlRole.append('<option value=" "> --Select-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							value=data[key];
							for ( var k in value) {
						       if (value.hasOwnProperty(k)) {
							if(key==='0')
								$ddlRole.append('<option value='+k+' selected>' +k+'-'+value[k] + '</option>');
							if(key==='1')
								$ddlRole.append('<option value='+k+'>' +value[k] + '</option>');
									}
								}
						    }
						  }
                }
        });
}
}


/*$(document).on('click', '#project', function() {
//$('#project option').prop('selected', true);
//$('#project option[value=" "]').prop('selected', false);
var selectedValue = $("#project option:selected").text();
	if(selectedValue==='--Select All--'){
		$('#project option').prop('selected', true);
		$('#project option[value=" "]').prop('selected', false);
	}
});*/