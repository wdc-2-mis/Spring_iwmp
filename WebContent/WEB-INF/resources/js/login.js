/*
function checkInputs(){
	
	const username = document.getElementById('user_id');
	const password = document.getElementById('encrypted_pass');
    
	
    const usernameValue = username.value.trim();
	const passwordValue = password.value.trim();
	
	
		
	
if(usernameValue=== ''){
	SetErrorFor(username, 'Username cannot be blank');
}else{
	SetSuccessFor(username);
}
if(passwordValue=== ''){	
	SetErrorFor(password, 'Password cannot be blank');
	
}else{
	SetSuccessFor(password);
}

if(usernameValue!='' && passwordValue!='')
{
return forward();
}

}

function forward(){
    $.ajax({  
    	type:"POST",
    	url :"/loginProcess",
 	   });
    }


 
function SetErrorFor(input, message){
	
	const inputBox = input.parentElement;
	const small = inputBox.querySelector('small');
	
	small.innerText = message;
	inputBox.className = 'inputBox error';
}


function SetSuccessFor(input){
	const formControl = input.parentElement;
	formControl.className = 'inputBox success';
	
}

*/

function generateSalt(length) {
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var salt = '';
    for (var i = 0; i < length; i++) {
        salt += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return salt;
}


$(document).ready(function(){
	
	$("#login").click(function() {
		var salt = generateSalt(10);
		var plainPwd=$('#encrypted_pass').val();
		var password = sha512(plainPwd);
		var userid=$('#user_id').val().toUpperCase();
		password = sha512(userid+password);
		var encryptedPwd = sha512(password+salt);
		$('#encrypted_pass').val(encryptedPwd);
		var id= $('#user_id').val();
		$('#user_id').val(id.toUpperCase());
	var result=false;
	var field=[user_id,encrypted_pass,captcha];
	
	$.each( field, function( i, val ) {
     result= loginblankCheck(val);
	if(!result){
		SetErrorFor(val, ' cannot be blank');
		return false;
	}	
	else{
		SetSuccessFor(val);
		return true;
	}	
	});
	if(result)
		$.ajax({  
            url:"loginProcess",
            type: "post",  
            data: $('#loginForm').serialize()+ "&salt=" + encodeURIComponent(salt),
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				
            	console.log('Data: '+data); 
            	        if(data.indexOf("alreadylogin")>=0){
						$('.text-danger').html("You are already signed in with the same User Id. Kindly logout first.!");
						$('#inputCaptcha').val('');
						$('#encrypted_pass').val('');
						$('#captcha').val('');
						
						}			  
				        else if(data.indexOf("captchaerror")>=0){
						$('.text-danger').html("Captcha is not matched !");
						$('#inputCaptcha').val('');
						$('#encrypted_pass').val('');
						$('#captcha').val('');
						$('#captcha').focus();
						$("#captcha_id").attr("src", "captcha.jpg?"+ Math.random());
						}	
								
						else if(data.indexOf("failed")>=0){
						$('.text-danger').html("Unregistered User!");
						$('#inputCaptcha').val('');
						$('#encrypted_pass').val('');
						$('#captcha').val('');
						
						}
						else if(data.indexOf("loginsuccess")>=0){
							window.location.href="loginsuccess";
						}else if(data.indexOf("invalidcredentials")>=0){
							$('.text-danger').html("Invalid Credential !");
							$('#encrypted_pass').val('');
							$('#user_id').val('');
							$('#captcha').val('');
							$("#captcha_id").attr("src", "captcha.jpg?"+ Math.random());
						}  
						else if(data.indexOf("loginerror")>=0){
							$('.text-danger').html("User authentication failed. You have only one more chance to Login!");
							$('#encrypted_pass').val('');
							$('#user_id').val('');
							$('#captcha').val('');
							$("#captcha_id").attr("src", "captcha.jpg?"+ Math.random());
						}  
						else if(data.indexOf("userlocked")>=0){
							$('.text-danger').html("User authentication failed.Your user-id has been locked. !");
							$('#encrypted_pass').val('');
							$('#user_id').val('');
							$('#captcha').val('');
							$("#captcha_id").attr("src", "captcha.jpg?"+ Math.random());
						}  
						else if(data.indexOf("locked")>=0){
							$('.text-danger').html("Your user-id has been locked for some time!");
							$('#encrypted_pass').val('');
							$('#user_id').val('');
							$('#captcha').val('');
							$("#captcha_id").attr("src", "captcha.jpg?"+ Math.random());
						} 
						else if(data.indexOf("logout")>=0){
							$('.text-danger').html("Successfully Logout!!");
							$('#encrypted_pass').val('');
							$('#user_id').val('');
							$('#captcha').val('');
							$("#captcha_id").attr("src", "captcha.jpg?"+ Math.random());
						}             
            }
        });
	    return false;
	  });
});

function SetErrorFor(input, message){
	const inputBox = input.parentElement;
	const small = inputBox.querySelector('small');
	const label= inputBox.querySelector('label');
	small.innerText = label.innerText+' '+message;
	inputBox.className = 'inputBox error';
}


function SetSuccessFor(input){
	const formControl = input.parentElement;
	formControl.className = 'inputBox success';
	
}

function loginblankCheck(fieldId){
			$fieldValue = fieldId.value;
			if($fieldValue==''){
				fieldId.focus();
				return false;
			}else{
				return true;
			}
			 
	
}