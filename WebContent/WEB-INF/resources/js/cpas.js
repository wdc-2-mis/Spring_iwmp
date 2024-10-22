function display()
{

	  if($(userInfoAtSlna.pia.value).length=(userInfoAtSlna.pia.value)==0)
	    {
	        alert('Please enter User Id.');
	        document.userInfoAtSlna.pia.focus();
	        return false;
	    }
var value=document.getElementById('pia').value;
var iChars = '!@#$%^&*()+=-[]\\\';,./{}|\':<>?';
       for (var i = 0; i<value.length; i++) {
       	if (iChars.indexOf(value.charAt(i)) != -1) {
       	alert ("Your user Id has special characters. \n These are not allowed.\n Please remove them and try again.");
        document.getElementById("pia").focus();
       	return ;
       	}
       }
    document.userInfoAtSlna.pia.value= document.userInfoAtSlna.pia.value.toUpperCase();
	return forward();
}

function changeCurrentPassword(value){
var url = 'changePasswordAtSlna';
var form;
var userId = value.substring(0,value.indexOf("###"));
var userType= value.substring(value.indexOf("###")+3,value.indexOf("***"));
//alert(userType);
var userName = value.substring(value.indexOf("***")+3);
/*var captcha = app.util.Util.getCaptchaSession(request);*/
form = $('<form action="' + url + '" method="get" modelAttribute="user">' +
  '<input type="text" name="userid" value="' + userId + '" />' +
'<input type="text" name="usertype" value="' + userType + '" />' +
'<input type="text" name="username" value="' + userName + '" />' +
'<input type="hidden" name="CAPTCHAcode" value=  <%=app.util.Util.getCaptchaSession(request)%>   />' +
  '</form>');
  $('body').append(form);
form.submit();
//window.location.href='changePasswordAtSlna';
}

function changePasswordPage(){
window.location.href='changePasswordAtSlna';
}

function forward(){
    $.ajax({  
            url:"userInfoAtSlna",
            type: "post",  
            data: $('#userInfoAtSlna').serialize(),
            error:function(xhr,status,er){
                console.log(xhr.responseText);
                alert('error'+er);
            },
            success: function(data) {
            	console.log('Data: '+data.indexOf("Not_authorised"));   
            	//alert('success'+data);
            	 if(data.indexOf("Not_authorised")>=0)
            	 {alert("You are not authorised to know the information  of this User ID.");
            	 window.location.href="changePassAtSlna";
            	 
            	 
						$('.text-danger').html("You are not authorised to know the information  of this User ID.");
						$('body').append(form);
						}
					else if(data.indexOf("success")>=0){
					window.location.href="changePassAtSlna";
						}
						
						
		}
 	   });
    }
    
    function checkPassword(str)
  {
    var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
    return re.test(str);
  }
    
function changePwd(e)//for chgPassword.jsp
 { 
e.preventDefault();
//alert('hi') 
var newPass = $('#newpwd');
 var oldPass = $('#oldpwd');
  var confPass = $('#confpwd');
var pass = validatePwd(newPass.val());
var userType = $('#adminType').val(); 
var roleName = $('#roleName').val(); 
//alert(userType);
 
if(oldPass.val()===''){
			successAlert('Please enter Old Password!');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$(oldPass).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$(oldPass).focus();
			});
			}

else if(newPass.val()===''){
			successAlert('Please enter New Password!');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$(newPass).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$(newPass).focus();
			});
			}

else if(confPass.val()===''){
			successAlert('Please enter Conform Password!');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$(confPass).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$(confPass).focus();
			});
			}
else if(newPass.val()==oldPass.val())
  {
    successAlert("New Password and Old Password should not be same.....");
$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#oldpwd').focus();
						});    
newPass.focus();
    newPass.val("");
    oldPass.val("");
    confPass.val("");
    e.preventDefault();
    return false;
    
  }						
else if(!pass){
		successAlert("Password must contain atleast 1 upper and lower case letter and special character and should more than 6 letter. ");
		$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#newpwd').focus();
						newPass.val("");
                        oldPass.val("");
                        confPass.val("");
						});
		newPass.focus();
	     e.preventDefault();
	     return false;
	}
  
 else if(newPass.val()!=confPass.val())
  {
    successAlert("New Password and Confirm Password should be same.....");
$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#newpwd').focus();
						newPass.val("");
    					oldPass.val("");
    					confPass.val("");
						});    
newPass.focus();
    newPass.val("");
    confPass.val("");
    e.preventDefault();
    return false;
    
  }
  
else (confirmAlert('Do you want to change the password?'))
$('#ok').click(function()
        
{
//alert(roleName);
if(userType!=='ADMIN' && roleName!=='SLNA Admin Role'){  
var plainPwd=$('#oldpwd').val();
var encryptedPwd=sha512(plainPwd); 
$('#oldpwd').val(encryptedPwd);
}

var plainPwd1=$('#newpwd').val();
var encryptedPwd1=sha512(plainPwd1); 
$('#newpwd').val(encryptedPwd1);
      
var plainPwd2=$('#confpwd').val();
var encryptedPwd2=sha512(plainPwd2); 
$('#confpwd').val(encryptedPwd2);   	 
$.ajax({  
            url:"changepwdatslna",
            type: "post",  
            data: $('#chgpass').serialize(),
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	            
            	console.log('Data: '+data);  

                if(data.indexOf("oldpassworderror")>=0){
	             successAlert('Incorrect Old Password... !');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='editprofile';
				});
		}
            	else if(data.indexOf("checkpasshistory")>=0){
	             successAlert('You cannot use your last 3 password. !');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='editprofile';
				});
		}
            	
            	else if(data.indexOf("datainsert")>=0){
	             successAlert('Password has been changed successfully.');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='editprofile';
				});
	
				}
					else if(data.indexOf("insertingerror")>=0){alert("Error on Changing Password.");
						$('.text-danger').html("Error on Changing Password.");
						window.location.href='changePasswordAtSlna';
						}	
						
						
		}
 	   });

  });
 e.preventDefault();
 }

function validatePwd(password) {
var minMaxLength = /^[\s\S]{6,32}$/,
    upper = /[A-Z]/,
    lower = /[a-z]/,
    number = /[0-9]/,
    special = /[ !"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]/;

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

function dataupdate(){
    $.ajax({  
            url:"changepwdatslna",
            type: "post",  
            data: $('#chgpass').serialize(),
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	            
            	console.log('Data: '+data);  

            	if(data.indexOf("checkpasshistory")>=0){
	             successAlert('You cannot use your last 3 password. !');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='changePasswordAtSlna';
				});
		}
            	
            	else if(data.indexOf("datainsert")>=0){
	             successAlert('Password has been changed successfully.');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='changePasswordAtSlna';
				});
	
				}
					else if(data.indexOf("insertingerror")>=0){alert("Error on Changing Password.");
						$('.text-danger').html("Error on Changing Password.");
						window.location.href='changePasswordAtSlna';
						}	
						
						
		}
 	   });
    }

