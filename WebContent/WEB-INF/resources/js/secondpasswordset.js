/**
 * 
 */

$(document).ready(function(){
	$('#state').prop( "disabled", true );
	$('#district').prop( "disabled", true );
	$('#project').prop( "disabled", true );
	//$("#message").fadeOut(5000);
	});
	
	
	function userTypeChange(){
		var userType = $('#userType').val();
		if(userType===''){
			resetTag('#state');
			resetTag('#district');
			resetTag('#project');
			$('#state').prop( "disabled", true );
			$('#district').prop( "disabled", true );
			$('#project').prop( "disabled", true );
		}
		if(userType=== '8' || userType=== '2'){
			resetTag('#state');
			resetTag('#district');
			resetTag('#project');
			$('#state').prop( "disabled", true );
			$('#district').prop( "disabled", true );
			$('#project').prop( "disabled", true );
		}
		if(userType === '3' || userType === '9' || userType === '15' || userType === '12'){
			resetTag('#state');
			resetTag('#district');
			resetTag('#project');
				$('#state').prop( "disabled", false );
				$('#district').prop( "disabled", true );
				$('#project').prop( "disabled", true );
				getStateList();
			}
			if(userType === '5'){
				resetTag('#state');
			resetTag('#district');
			resetTag('#project');
				$('#state').prop( "disabled", false );
				$('#district').prop( "disabled", false );
				$('#project').prop( "disabled", true );
				getStateList();
			}
			if(userType === '6'){
				resetTag('#state');
			resetTag('#district');
			resetTag('#project');
				$('#state').prop( "disabled", false );
				$('#district').prop( "disabled", false );
				$('#project').prop( "disabled", false );
				getStateList();
			}
	}
	
	function onStateChange(stCode){
		var url = "getDistrictByStateCodeWithDcode";
		$.ajax({  
            url:url,
            type: "post",  
            data: {id:stCode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $ddlDistrict = $('#district');
						$ddlDistrict.empty();
        				$ddlDistrict.append('<option value=""> -- Select --</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlDistrict.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
	}
	
	function getStateList(){
		
		var url = "getAllState";
		$.ajax({  
            url:url,
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $ddlState = $('#state');
						$ddlState.empty();
        				$ddlState.append('<option value=""> -- Select --</option>');
						$ddlState.append('<option value="0">All</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlState.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
	}
	
	function getProjectByDcode(distCode){
		var url = "getProjNACByDcode";
		$.ajax({  
            url:url,
            type: "post",  
            data: {dCode:distCode},
            error:function(xhr,status,er){
	var err = eval("(" + xhr.responseText + ")");
                console.log(err.Message);
            },
            success: function(data) {
						var $ddlProject = $('#project');
						$ddlProject.empty();
        				$ddlProject.append('<option value=""> -- Select --</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlProject.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
	}
	
	function resetTag(tagName){
		var $ddlTag = $(tagName);
		$ddlTag.empty();
		$ddlTag.append('<option value=""> -- Select --</option>');
	}
	
	function validate(e){
	var userType = $('#userType').val();
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
	var newPassword = $('#newPassword').val();
	
	var confirmPassword = $('#confirmPassword').val();
	
//	alert("userrType: "+userType+" state: "+state+" district: "+district+" project: "+project);
	if(userType==='' && state==='' && district==='' && project==='' && newPassword==='' && confirmPassword===''){
		//alert('Please provide at least one input ');
		$('#error').html('Please provide at least one input ');
		$('#userType').focus();
		addInputFieldErrorCss('#userType');
		e.preventDefault();
	}else{
		removeInputFieldErrorCss('#userType');
	}if((userType==='3' || userType==='12' || userType==='15' || userType === '5' || userType === '6' || userType==='9') && state===""){
		//alert("Please Enter State");
		$('#error').html("Please Enter State");
		$('#state').focus();
		addInputFieldErrorCss('#state');
		return false;
	}else{
		removeInputFieldErrorCss('#state');
	}if((userType === '5' || userType === '6') && district===""){
		//alert("Please Enter District");
		$('#error').html("Please Enter District");
		$('#district').focus();
		addInputFieldErrorCss('#district');
		return false;
	}else{
		removeInputFieldErrorCss('#district');
	}if(userType === '6' && project===""){
		//alert("Please Enter Project");
		$('#error').html("Please Enter Project");
		$('#project').focus();
		addInputFieldErrorCss('#project');
		return false;
	}else{
		removeInputFieldErrorCss('#project');
	}if(newPassword == ""){
			 //alert("Please Enter Password");
			$('#error').html("Please Enter Password");
		     $('#newPassword').focus();
addInputFieldErrorCss('#newPassword');
		     return false;
		}else{
		removeInputFieldErrorCss('#newPassword');
	}
		if(confirmPassword == ""){
			 //alert("Please Enter confirm Password");
			$('#error').html("Please Enter confirm Password");
		    $('#confirmPassword').focus();
addInputFieldErrorCss('#confirmPassword');
		     return false;
		}else{
		removeInputFieldErrorCss('#confirmPassword');
	}
		if(newPassword!=confirmPassword){
			 //alert("Passwords do not match.");
			$('#error').html("Passwords do not match.");
		     $('#confirmPassword').focus();
addInputFieldErrorCss('#confirmPassword');
		     return false;
		}else{
		removeInputFieldErrorCss('#confirmPassword');
	}
	//	alert(newPassword);
		if(!validatePassword(newPassword)){
		//alert("Password must conatin atleast 1 upper and lower case letter and special character and should more than 6 latter. ");
		$('#error').html("Password must conatin atleast 1 upper and lower case letter and special character and should more than 6 latter. ");
		$('#newPassword').focus();
		addInputFieldErrorCss('#newPassword');
		return false;
	}else{
		var encryptedNewPwd=sha512(newPassword); 
	$('#newPassword').val(encryptedNewPwd);
	var encryptedConfirmPwd=sha512(confirmPassword);
	$('#confirmPassword').val(encryptedConfirmPwd); 
	document.secondpasswordset.action="saveSecondPassword";
	document.secondpasswordset.method="post";
    document.secondpasswordset.submit();
	}
	
	return false;
}
	function validatePassword(password) {
    var minMaxLength = /^[\s\S]{6,32}$/,
        upper = /[A-Z]/,
        lower = /[a-z]/,
        number = /[0-9]/,
        special = /[ !"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]/;

  /* alert( 'lenth= '+minMaxLength.test(password) +' , upper= '+
    upper.test(password) +', lower= '+
    lower.test(password) +', number= '+
    number.test(password) +', special= '+
    special.test(password));*/
    
    if (minMaxLength.test(password) &&
        upper.test(password) &&
        lower.test(password) &&
        number.test(password) &&
        special.test(password)
    ) {
        return true;
    }

    return false;
}

function btnResetClick(){
	resetTag('#state');
			resetTag('#district');
			resetTag('#project');
			resetTag('#userType');
			$('#newPassword').val('');
			$('#confirmPassword').val('');
			$('#state').prop( "disabled", true );
	$('#district').prop( "disabled", true );
	$('#project').prop( "disabled", true );
	getUserTypeList();
}


function getUserTypeList(){
		
		var url = "getUserTypeFromDB";
		$.ajax({  
            url:url,
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $ddlState = $('#userType');
						$ddlState.empty();
        				$ddlState.append('<option value=""> -- Select --</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $ddlState.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
	}