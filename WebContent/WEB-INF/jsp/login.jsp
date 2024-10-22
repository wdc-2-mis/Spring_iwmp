<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header_login.jspf"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jsSHA/3.2.0/sha512.js"></script>
<style>
.error {
	color: red;
}
</style>
<style>
.forgot{
display: flex;
justify-content: space-between;
font-size: 14.5px;
margin: -15px 0 15px;
}
</style>

<script>
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>

</head>
<body> <c:out value="${user.user_id }" />
<c:if test="${ user.user_id ne '' && user.user_id ne null}">
<script>
if(window.confirm('You have already Logged-In. Please Logout first then Login again.')==true){
	
	var form = document.createElement("form");
    var element1 = document.createElement("input"); 
    form.method = "POST";
    form.action = "logout"; 
    element1.value="<c:out value="${user.user_id }" />";
    element1.name="login";
    form.appendChild(element1);  
    document.body.appendChild(form);

    form.submit();
   
}else {
	alert('You cannot press cancel button. Please Logout first then Login again.')
	var form = document.createElement("form");
    var element1 = document.createElement("input"); 
    form.method = "POST";
    form.action = "logout"; 
    element1.value="<c:out value="${user.user_id }" />";
    element1.name="login";
    form.appendChild(element1);  
    document.body.appendChild(form);

    form.submit();
   
}</script>
</c:if> 

  <div class="container-fluid" style="background-image:  url('resources/images/login.png'); background-size: contain;  width: 100%; height:auto;
  background-repeat: no-repeat;
  background-size: cover;"> 
<div class="row" >
<div class=" col-md-offset-3 col-md-4"></div>
<div class="col">

<div class="box1" style="margin-top:5%; margin-bottom:5%">
<h2><spring:message code="label.login"/></h2>

<form:form id ="loginForm"  name="loginForm" modelAttribute="login" >
<label class="text-danger"></label>

 <div class="inputBox">

<input type="text" name="user_id"  id="user_id" required  autocomplete = "off" maxlength="10"/>

<label><spring:message code="label.username"/></label>
<i class="fas fa-check-circle"></i>
<i class="fas fa-exclamation-circle"></i>
<small>Error message</small>
 </div>
				
				
<div class="inputBox">
<input type="password" name="encrypted_pass" id="encrypted_pass" required id = "plain_pass" maxlength="200">
 
<label><spring:message code="label.password"/></label>
<i class="fas fa-check-circle"></i>
<i class="fas fa-exclamation-circle"></i>
<small>Error message</small>
</div>

<div class="inputBox">	
<img id="captcha_id" name="imgCaptcha" src="captcha.jpg"/>
<a href="javascript:;"  onclick="document.getElementById('captcha_id').src ='captcha.jpg?' + Math.random();  return false">
<img src="resources/images/refresh.png" />
</a>
</div>
<div class="inputBox">&nbsp;	</div>
<div class="inputBox">	
<input type="text" id="captcha" name = "captcha" autocomplete = "off" maxlength="10"/>		
<label ><spring:message code="label.security"/></label>	
<i class="fas fa-check-circle"></i>
<i class="fas fa-exclamation-circle"></i>
<small>Error message</small>		
</div>
<a href="forgotpassword" style="color:white;"><p style="text-align:right">Forget password?</p></a>
   
   <center>             
 <input type="submit" class="btn btn-info" id="login" value="LogIn" /> </center>
<!-- <button id="btnLogin" onclick="javascript:checkInputs();">Login</button> -->
	
	<div class="d-flex justify-content-center links">
	
					&nbsp;&nbsp;&nbsp;&nbsp;<p style="color:white;">Sign up for</p> &nbsp;&nbsp;&nbsp; <a href="register"><u>New User</u></a>
				
				</div>

</form:form>
</div>
</div>
</div>
</div>

<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>