/*  onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)" */
function submitRegisterForm() 
{
	var j=document.getElementById('userMobileNo').value.length;
	var ph = document.getElementById('userPhoneNo').value.length;
	var userType=document.getElementById("userType").value;
//	alert(userType);
	if(document.getElementById("userType").value=='' )
	{
		alert("select User Type !");
		document.getElementById("userType").focus();
		return false;
	}
	if(document.getElementById("userState").value=='')
	{
		alert("select state !");
		document.getElementById("userState").focus();
		return false;
	}
	if(userType=='DI' || userType=='PI' || userType=='NGO')
	{	
		if(document.getElementById("userDistrict").value=='')
		{
			alert("select District !");
			document.getElementById("userDistrict").focus();
			return false;
		}
	}
	if(document.getElementById("userName").value=='')
	{
		alert("Enter Name !");
		document.getElementById("userName").focus();
		return false;
	}
	if(userType!='NGO'){
		if(document.getElementById("userDepartment").value=='')
		{
			alert("Enter Departments !");
			document.getElementById("userDepartment").focus();
			return false;
		}
		if(document.getElementById("userDesignation").value=='')
		{
			alert("Enter Designation !");
			document.getElementById("userDesignation").focus();
			return false;
		}
	}
	if(userType=='NGO'){
		if(document.getElementById("userNgoid").value=='')
		{
			alert("Enter Ngo Id !");
			document.getElementById("userNgoid").focus();
			return false;
		}
		if(document.getElementById("userRegwith").value=='')
		{
			alert("Enter Register with !");
			document.getElementById("userRegwith").focus();
			return false;
		}
	}
	
	if(document.getElementById("userEmailId").value=='')
	{
		alert("Enter Email !");
		document.getElementById("userEmailId").focus();
		return false;
	}
	if(document.getElementById("userMobileNo").value=='' )
	{
		alert("Enter mobile number !");
		document.getElementById("userMobileNo").focus();
		return false;
	}
	if(j!=10 )
	{
		alert("Enter mobile number only 10 digits!");
		document.getElementById("userMobileNo").focus();
		return false;
	}
	
	/*if(document.getElementById("userPhoneNo").value=='' )
	{
		alert("Enter phone number !");
		document.getElementById("userPhoneNo").focus();
		return false ;
	}
	
	if(ph!=11 )
	{
		alert("Enter numeric phone number only 11 digits!");
		document.getElementById("userPhoneNo").focus();
		return false;
	}*/
	
	if(document.getElementById("userAddres").value=='')
	{
		alert("Enter User Address !");
		document.getElementById("userAddres").focus();
		return false;
	}
	if(document.getElementById("inputCaptcha").value=='')
	{
		alert("Enter captcha !");
		document.getElementById("inputCaptcha").focus();
		return false ;
	}
 	if(confirm("Do you want to Register User ?"))
	{
	    document.getElementById('regForm').action = "registerUser";
	    document.getElementById('regForm').method="post";
	    document.getElementById('regForm').submit();
	}    
}

function mobile1()
{ 
	var me = document.getElementById('userMobileNo').value;
	var j=document.getElementById('userMobileNo').value.length;
    var result = isNaN(me);
	if (result) 
	{
    	alert("Enter numeric mobile number only 10 digits !.");
    	document.getElementById('userMobileNo').focus();
    	return false;
	}
}
function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
function phonenumber(inputtxt)
{
  var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
}

/*function phone()
{ 
	
	var ph = document.getElementById('userPhoneNo').value;
	if(ph!=11 )
	{
		alert("Enter numeric phone number only 11 digits!");
		document.getElementById("userPhoneNo").focus();
		return false;
	}
     var result = isNaN(ph);
	if (result) 
	{
    	alert("Enter numeric phone number only 11 digits !.");
    	document.getElementById('userPhoneNo').focus();
    	return false;
	} 
}*/

function validateEmail(emailField)
{
   // var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var reg =  /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

    if (reg.test(emailField.value) == false) 
    {
        alert('Invalid Email Address');
        document.getElementById("userEmailId").focus();
        return false ;
    }
    
    return true;
}

function removeSpaces(string)
{
	return string.split(' ').join('');
}

function validatecaptha()
{
	var str1=removeSpaces(document.registerform.imgCaptcha.value);
	if(str1 == " " || str1 ==null)
	{
		alert("Captcha cannot be blank !");
		
		return false;
	}
}


