function changePwd()//for chgPassword.jsp
 { 
  var oldPass = document.chgpass.oldpwd.value;
  var newPass = document.chgpass.newpwd.value;
  var confPass = document.chgpass.confpwd.value;
  var pass = validatePwd(newPass);
 
   
   if(!pass){
		alert("Password must contain at least 1 upper and lower case letter and special character and should more than 6 letter. ");
		newPass.focus();
	     return false;
	}
  if(chkStngPswd(newPass)== false)
  {
	  alert("Password should contain at least one letter in Capital, Lower and Numeric with Special Character.");
	  newPass.focus();
	  return false;
  }
   if(oldPass==newPass)
  {
    alert("Old Password and New Password should not be same.");
    oldPass.value="";
    newPass.focus();
    return false;
  }
  
  else if(newPass!=confPass)
  {
    alert("New Password and Confirm Password should be same.");
    confPass.value="";
    confPass.focus();
    return false;
  }
  if(confirm('Do you want to change the password?')){
	 return forward();
  }

}
function forward(){
    $.ajax({  
            url:"changepassword",
            type: "post",  
            data: $('#chgpass').serialize(),
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {alert("data");
            	console.log('Data: '+data);   
            	if(data.indexOf("checkpasshistory")>=0){alert("You cannot use your last 3 password. !");
            	$('.text-danger').html("You cannot use your last 3 password. !");
            	}
            	else if(data.indexOf("datainsert")>=0){alert("Password has been changed successfully.");
						$('.text-danger').html("Password has been changed successfully.");
						}
					else if(data.indexOf("insertingerror")>=0){alert("Error on Changing Password.");
						$('.text-danger').html("Error on Changing Password.");
						}	
						
						
		}
 	   });
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
function chkStngPswd(chkPassword)
{
	var strCap = "QWERTYUIOPASDFGHJKLZXCVBNM";
	var strLwr = "qwertyuiopasdfghjklzxcvbnm";
	var strInt = "1234567890";

	var strCapFlag = false;
	var strLwrFlag = false;
	var strIntFlag = false; 

	var Char = "";

	//alert(chkPassword);

	for(i=0; i<chkPassword.length; i++)
	{
		Char = chkPassword.charAt(i);
		if (strCap.indexOf(Char) != -1)
		{
			strCapFlag = true;
		}

		if (strLwr.indexOf(Char) != -1)
		{
			strLwrFlag = true;
		}

		if (strInt.indexOf(Char) != -1)
		{
			strIntFlag = true;
		}
	}

	if(strCapFlag != true || strLwrFlag != true || strIntFlag != true)
	{
		return false;
	}
	else
	{
		return true;
	}
}