
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header_new.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">

 $(document).ready(function(){
    $("#userName").keydown(function(event){
        var inputValue = event.which;
        if(!(inputValue >= 65 && inputValue <= 120) 
        		&& (inputValue != 32 && inputValue != 8 && inputValue != 46 && inputValue != 37 && inputValue != 39 ) ) { 
            event.preventDefault(); 
        }
    });
});
 
 $(document).ready(function(){
	    $("#userDepartment").keydown(function(event){
	        var inputValue = event.which;
	        if(!(inputValue >= 65 && inputValue <= 120) 
	        		&& (inputValue != 32 && inputValue != 8 && inputValue != 46 && inputValue != 37 && inputValue != 39 ) ) { 
	            event.preventDefault(); 
	        }
	    });
	});
 
 $(document).ready(function(){
	    $("#userDesignation").keydown(function(event){
	        var inputValue = event.which;
	        if(!(inputValue >= 65 && inputValue <= 120) 
	        		&& (inputValue != 32 && inputValue != 8 && inputValue != 46 && inputValue != 37 && inputValue != 39 ) ) { 
	            event.preventDefault(); 
	        }
	    });
	});
 
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
 
 $(function() {
     $('#userName12').on('keypress', function(e) {
         if (e.which == 32){
             console.log('Space Detected');
             alert('space not allow');
             return false;
         }
     });
});
 
</script>
<script src='<c:url value="/resources/js/registation.js" />'></script>

<c:if test="${messagerj != null}">
	<script>
	    alert("<c:out value='${message}'/>\n  Your registration ID is  <c:out value='${regid}' /> \n <c:out value='${message1}' />");
	</script>
</c:if>
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

.bg-image {
  /* The image used */
  background-image: url("resources/images/reg.png");
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
  
  width: 100%;
}
.blur {
   background: rgba(255, 255, 255, 0.2);  // Make sure this color has an opacity of less than 1
  backdrop-filter: blur(8px); // This be the blur
  height: 100vh;
	
}
</style>
<div class="container-fluid bg-image">
	<div class="row">
		<div class="col-sm-offset-3 col-sm-3"></div>
		<div class="col-sm-6 registrationform">
		<h4 class="text-center"><spring:message code="label.userreg"/></h4>
		<hr></hr>
			<form:form id="regForm" name="registerform" modelAttribute="userBean" action="register" method="post" autocomplete="off">
			<input type="hidden" id="userregid" name="userregid" value="${userregid}"  />
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputUserType"><spring:message code="label.usertype"/> <span style="color: red;">*</span></label> 
						<select name="userType"  id="userType" class="form-control form-control-sm"  onchange="this.form.submit();" >
							<option value="">--Select--</option>
				            <c:if test="${not empty userTypeList}">
               					<c:forEach items="${userTypeList}" var="lists">
               					 <c:if test="${lists.key eq userType}">
       								<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
       							</c:if>
       							<c:if test="${lists.key ne userType}">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
       							</c:if>
								</c:forEach>
							</c:if> 
						</select>
					</div>
					<div class="form-group col-md-6">
				 		<label for="inputState" ><spring:message code="label.state"/><span style="color: red;">*</span> </label>
				 		<c:if test="${userType eq 'DL'}">
     					<select id="userState" role="DL"  name="userState" style="height: 100px;" aria-labelledby="menu1" class="form-control form-control-sm" multiple="multiple">
							<option value="">--Select--</option>
				            <c:if test="${not empty stateList}">
               					<c:forEach items="${stateList}"  var="lists">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
								</c:forEach>
							</c:if> 
						</select>
						</c:if>
						
						<c:if test="${userType ne 'DL'}">
     					<select id="userState" name="userState"  onchange="this.form.submit();" class="form-control form-control-sm" >
							<option value="">--Select--</option>
				            <c:if test="${not empty stateList}">
               					<c:forEach items="${stateList}" var="lists">
               					<c:if test="${lists.key eq userState}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       							</c:if>	
       							<c:if test="${lists.key ne userState}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       							</c:if>
								</c:forEach>
							</c:if> 
						</select>
						</c:if>
    				</div>
					
				</div>
				<div class="form-row" class="height: 600px;">
					
    				<div class="form-group col-md-6">
    					<c:if test="${userType == 'DI' || userType == 'PI' || userType == 'NGO'}">
						<label for="inputDesignation"><spring:message code="label.district"/> <span style="color: red;">*</span></label> 
     					<select id="userDistrict" name="userDistrict"   class="form-control form-control-sm" >
							<option value="">--Select--</option>
				            <c:if test="${not empty districtList}">
               					<c:forEach items="${districtList}" var="lists">
               						<c:if test="${lists.key eq userDistrict}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne userDistrict}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
							</c:if> 
						</select>
						</c:if>
					</div>
    				<%-- <div class="form-group col-md-6">
    				<c:if test="${userType == 'PI'}">
						<label for="inputDesignation">Project*</label> 
     					<select id="userProject" name="userProject"  class="form-control form-control-sm" multiple="multiple">
							<option>--Select--</option>
				            <c:if test="${not empty projectList}">
               					<c:forEach items="${projectList}" var="lists">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
								</c:forEach>
							</c:if> 
						</select>
					</c:if>
					</div> --%>
					<div class="form-group col-md-6">
						<label for="inputName"><c:if test="${userType eq 'NGO'}"> NGO </c:if><spring:message code="label.name"/><span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userName" name="userName" placeholder="Name" maxlength="50" />
					</div>
				</div>
				
				<div class="form-row">
					<c:if test="${userType ne 'NGO'}">	
					<div class="form-group col-md-6">
						<label for="inputDepartment"> <spring:message code="label.dept"/><span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userDepartment" name="userDepartment" placeholder="Department" maxlength="50"  >
					</div>
					<div class="form-group col-md-6">
						<label for="inputDesignation"><spring:message code="label.designation"/> <span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userDesignation" name="userDesignation"  placeholder="Designation" maxlength="50"  >
					</div>
					</c:if>
					<c:if test="${userType eq 'NGO'}">
					<div class="form-group col-md-6">
						<label for="inputDepartment"><spring:message code="label.ngoid"/> <span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userNgoid" name="userNgoid" placeholder="NGO Id" maxlength="50"  >
					</div>
					<div class="form-group col-md-6">
						<label for="inputDesignation"> <spring:message code="label.regwith"/>  <span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userRegwith" name="userRegwith"  placeholder="Register with" maxlength="50"  >
					</div>
					</c:if>
					
					
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputEmail"><spring:message code="label.email"/><span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userEmailId"  name="userEmailId"  placeholder="Email Id" onblur="validateEmail(this);" maxlength="50">
					</div>
					<div class="form-group col-md-6">
						<label for="inputMobile"><spring:message code="label.mobile"/><span style="color: red;">*</span></label> 
						<input type="text" class="form-control" id="userMobileNo" name="userMobileNo" onkeypress="return isNumberKey(event)" placeholder="Mobile" maxlength="10" min="10" onblur="mobile1();">
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputPhone"><spring:message code="label.phone"/></label> 
						<input type="text" class="form-control" id="userPhoneNo" name="userPhoneNo" maxlength="12" min="12" placeholder="Phone" onchange="PhoneNoValidation();"  />
					</div>
					<div class="form-group col-md-6">
						<label for="inputFax"><spring:message code="label.fax"/></label> 
						<input type="text" class="form-control" id="userFaxNo" name="userFaxNo" maxlength="12" placeholder="Fax" >
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress"><spring:message code="label.address"/><span style="color: red;">*</span></label>
						<textarea id="userAddres" class="form-control" name="userAddres" placeholder="Address" rows=5 style="height:100%" maxlength="200"></textarea>
					</div>
					<div class="form-group col-md-6">&nbsp;
					</div>
					
  				</div>
  				
  
  	<div class="form-row">
  	<div class="form-group col-12"><br/>
					</div>
		<div class="form-group col-md-4">
      	<label for="inputCaptchaLabel"><spring:message code="label.typethechar"/> <span style="color: red;">*</span> </label>
    	</div>
    	
    	<div class="form-group col-md-3">
     	<img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
     	<a  href="javascript:;" onclick="document.getElementById('captcha_id').src ='captcha.jpg?' + Math.random();  return false">
     			<img src="<c:url value="/resources/images/refresh.png" />" width="30" height="30" alt="refresh" />
     	</a>
    	</div>
    	
    	<div class="form-group col-md-4">
      	<input type="text" class="form-control" id="inputCaptcha" name="inputCaptcha"  maxlength="8">
    	</div>
    	
	</div>
  
				
			<div class="form-row">
			<div class="form-group col-md-5">
				
				</div>
				<div class="form-group col-md-6">
					<input type="button" class="btn btn-info" id="register" name="register" value="Register" onclick="javascript:submitRegisterForm();" />
				</div>
				
				</div>
				</form:form>
		</div>
	</div>
	<div class="form-group row">
	<div class="offset-sm-5"></div>
	<div>
	 <a href="showRegistration" style="font-size:1vw;color:#fff"><b> <spring:message code="label.viewdown"/></b></a> 
</div>
</div>
	
</div>


<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
