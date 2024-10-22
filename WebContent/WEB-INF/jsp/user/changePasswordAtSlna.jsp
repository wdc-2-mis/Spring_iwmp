<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script src='<c:url value="/resources/js/profile.js" />'></script> 
<script src='<c:url value="/resources/js/cpas.js" />'></script> 
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">
<form:form name = "chgpass" id = "chgpass" autocomplete="off" >
<div class="container col-lg-7">
	<div class="card">
		<div class="card-header">
			<strong>
				<i class="fa fa-lock"></i> <spring:message code="label.change.password"/>
			</strong>
		</div>
		
		<div class="card-body">
		
		<div class="row">
				<label class="col-md-3 control-label"><spring:message code="label.current.user-id"/> : </label>
				<div class="col-md-5 mb-3">
				
					<input type="text" readonly="readonly" class="labelText" value="<c:out value="${userId}"/>" name="userId" />
					  <input type="hidden" value="<c:out value="${userType}"/>" name="userType"/> 
					   <input type="hidden" value="<c:out value="${sessionScope.userType}"/>" name="adminType" id="adminType"/>
					   <input type="hidden" value="<c:out value="${sessionScope.roleName}"/>" name="roleName" id="roleName"/>
					  	</div>
						
			</div>
			
			<c:if test='${sessionScope.userType!="ADMIN" && sessionScope.roleName!="SLNA Admin Role"}}'>
			<div class="row">	
				<label class="col-md-3 control-label"><spring:message code="label.old.password"/> : </label>
				 <div class="col-md-5 mb-3">	
					<spring:message code="placeholder.old.password" var="oldPwdPlaceholder"/>
					 <input class="form-control"  type= "password" id = "oldpwd" name="oldpwd" placeholder="${oldPwdPlaceholder}" required="true"/> 
				</div> 
			</div> 
			</c:if>	
			<div class="row">	
				<label class="col-md-3 control-label"><spring:message code="label.new.password"/> : </label>
				<div class="col-md-5 mb-3">	
					<spring:message code="placeholder.new.password" var="newPwdPlaceholder"/>
					<input class="form-control"  type= "password" id = "newpwd" name="newpwd" placeholder="${newPwdPlaceholder}" required="true"/>
				</div>
			</div>
				
			<div class="row">
				<label class="col-md-3 control-label"><spring:message code="label.retype.new.password"/> : </label>
				<div class="col-md-5 mb-3">
					<spring:message code="placeholder.retype.new.password" var="retypePwdPlaceholder"/>
					<input class="form-control"  type= "password" id = "confpwd" name="confpwd" placeholder="${retypePwdPlaceholder}" required="true"/>
				</div>
			</div>
		</div>	
		<tr>
            	<td class="field" colspan="4">
            	
            	<span style="color:red;"><i>Password must contain at least 1 upper and lower case letter and special character and should more than 6 letter.</i></span>
            	</td>
            </tr>		
		<div class="card-footer text-right">
			<button  id="change" name="change" class="btn btn-sm btn-primary" onclick="javascript:changePwd(event);">
			<i class="fa fa-save"></i> <spring:message code="label.save"/>
			</button>
			
			
			<button type= "reset"  class="btn btn-sm btn-primary"  value="Back" onCLick="history.back()">
				<i class="fa fa-cancel  "></i> <spring:message code="label.cancel"/>
				
			</button>
		</div>
		
	</div>
</div>
			
</form:form>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>