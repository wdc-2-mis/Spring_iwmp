
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<link href="WEB-INF/css/main.css?timer=${timer}" rel="stylesheet" type="text/css" />
<script type="text/javascript">



function submitAddMenuForm() 
{
	

	
	if(document.getElementById("userType").value=='')
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
	
	
	if(document.getElementById("userName").value=='')
	{
		alert("Enter User Name !");
		document.getElementById("userName").focus();
		return false;
	}
	if(document.getElementById("userDesignation").value=='')
	{
		alert("Enter User Designation !");
		document.getElementById("userDesignation").focus();
		return false;
	}
	if(document.getElementById("userDepartment").value=='')
	{
		alert("Enter User Departments !");
		document.getElementById("userDepartment").focus();
		return false;
	}
	if(document.getElementById("userMobileNo").value=='' )
	{
		alert("Enter mobile number !");
		document.getElementById("userMobileNo").focus();
		return false;
	}if(document.getElementById("userPhoneNo").value=='' )
	{
		alert("Enter phone number !");
		document.getElementById("userPhoneNo").focus();
		return false ;
	}
	if(j!=10 )
	{
		alert("Enter mobile number only 10 digits!");
		document.getElementById("userMobileNo").focus();
		return false;
	}
	if(document.getElementById("userEmailId").value=='')
	{
		alert("Enter Email !");
		document.getElementById("userEmailId").focus();
		return false;
	}
	
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

function phone()
{ 
	var me = document.getElementById('userPhoneNo').value;
    var result = isNaN(me);
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
       document.addnomination.email.focus() ;
       return false;
   }
   return( true );
} */


function validateEmail(emailField){
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

    if (reg.test(emailField.value) == false) 
    {
        alert('Invalid Email Address');
        return false;
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
	
</script>

<c:if test="${message != null}">
<script>
    alert("<c:out value='${message}'/>");
    
</script>
</c:if>

<style>
#menu1 {
    width: 160px; 
    text-align: left;
}

</style>
</head>
<body>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-offset-2 col-sm-2"></div>
		<div class="col-sm-8 registrationform">
		<h4 class="text-center">New Menu</h4>
		<hr></hr>
			<form:form id="regForm" name="registerform" modelAttribute="userBean" action="register" method="post">
			<input type="hidden" id="userregid" name="userregid" value="${userregid}"  />
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputUserType">Role of the User *</label> 
						<select name="userType"  id="userType" class="form-control form-control-sm"  >
							<option>--Select--</option>
							<option>WCDC</option>
				            <option>DoLR</option>
				            <option>SLNA</option>
				            <option>PIA</option>
						</select>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputUserType">Menu Type</label> 
						<select name="userType"  id="userType" class="form-control form-control-sm"  >
							<option>--Select--</option>
							<option>Parent </option>
				            <option>Child</option>
				         </select>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputName">Menu Name</label> 
						<input type="text" class="form-control" id="menuName" name="menuName" placeholder="MenuName"/>
					</div>
				</div>
				
				<div class="form-row">	
					<div class="form-group col-md-6">
						<label for="inputDepartment">URL*</label> 
						<input type="text" class="form-control" id="menuUrl" name="menuUrl" placeholder="Url">
					</div>
					</div>
					<div class="form-row">	
					
					<div class="form-group col-md-6">
						<label for="inputDesignation">Level *</label> 
						<input type="text" class="form-control" id="menuLevel" name="menuLevel" placeholder="Level">
					</div>
				</div>
				</form:form>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-5"></div>
				<div class="col-sm-2">
					<input type="submit" value="Add Menu" onclick="submitAddMenuForm();" />
				</div>
				</div>
		</div>
	</div>
</div>
<!-- Footer -->
<footer class="container-fluid text-center footer">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
