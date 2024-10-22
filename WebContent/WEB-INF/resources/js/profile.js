
function saveprofile() 
{
	var j=document.getElementById('userMobileNo').value.length;
	//var ph = document.getElementById('userPhoneNo').value.length;
	
	if(document.getElementById("userName").value=='')
	{
		alert("Enter User Name !");
		document.getElementById("userName").focus();
		return false;
	}
	if(document.getElementById("userDepartment").value=='')
	{
		alert("Enter User Departments !");
		document.getElementById("userDepartment").focus();
		return false;
	}
	if(document.getElementById("userDesignation").value=='')
	{
		alert("Enter User Designation !");
		document.getElementById("userDesignation").focus();
		return false;
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
	
 	if(confirm("Do you want to Update profile ?"))
	{
	    document.getElementById('editprofile').action = "profileSave";
	    document.getElementById('editprofile').method="post";
	    document.getElementById('editprofile').submit();
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

function PhoneNoValidation() {

    var numbers = /^[0-9]+([,-][0-9]+)?$/;
    var txt = document.getElementById('userPhoneNo');
    if (txt.value.match(numbers)) 
    {
        return true;
    }
    else {
        
        document.getElementById("userPhoneNo").value="";
        return false;
    }
}

function phone1()
{ 
	var ph = document.getElementById('userPhoneNo').value;
	/*if(ph!=11 )
	{
		alert("Enter numeric phone number only 11 digits!");
		document.getElementById("userPhoneNo").focus();
		return false;
	}*/
    var result = isNaN(ph);
	if (result) 
	{
    	alert("Enter numeric phone number only 11 digits !.");
    	document.getElementById('userPhoneNo').focus();
    	return false;
	} 
}

/* function validateEmail()
{
   var emailID = document.getElementById("userEmailId").value;
   atpos = emailID.indexOf("@");
   dotpos = emailID.lastIndexOf(".");
   
   if (atpos < 1 || ( dotpos - atpos < 2 )) 
   {
       alert("Please enter correct email ID");
       document.getElementById("userEmailId").focus();
       return false;
   }
   return( true );
} */

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


    function pwdchange1(){
    
    window.location.href='pwdchange';
    }
function resetForm()
{
  document.loginForm.reset();
}



function editUserDetails(e){
	e.preventDefault();
	var regId=$('#regId').val();
	var userType = $('#userType').val();
	var userId = $('#userId').val();
	var url = 'editUser';
				var form = $('<form action="' + url + '" method="post" modelAttribute="user">' +
				  '<input type="text" name="userId" value="' + userId + '" />' +
				  '<input type="text" name="regId" value="' + regId + '" />' +
		'<input type="text" name="userType" value="' + userType + '" />' +
				  '</form>');
				$('body').append(form);
				form.submit();
	/*$.ajax({  
            url:url,
            type: "post",  
            data: {regId:regId,userType:userType},
            error:function(xhr,status,er){
                console.log(er);
				alert("There is some error while fetching record of user !"); 
            },
            success: function(data) {
	//alert(data);
				window.location.href = data;
					
					}
	});*/
}

function callBackPage(e){
	e.preventDefault();
	window.location.href = 'usersrch';
}

function printPage(e){
	e.preventDefault();
var css = '<link rel="stylesheet" type="text/css"	href="resources/css/loginstyle.css" />'
	var divToPrint=document.getElementById('userDetails');
	var newWin=window.open('','Print-Window');
	//newWin.document.open();
  	newWin.document.write('<html><head>'+css+'</head><body >'+divToPrint.innerHTML+'</body></html>');
  	newWin.document.close();
// setTimeout(function(){newWin.close();},0);
//newWin.onfocus=function(){ newWin.close();}
newWin.focus();
newWin.print();
newWin.close();
}