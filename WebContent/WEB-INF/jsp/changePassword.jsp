<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script src='<c:url value="/resources/js/profile.js" />'></script> 
<script src='<c:url value="/resources/js/changePwd.js" />'></script> 
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">
<form:form name = "chgpass" id = "chgpass" autocomplete="off" method="post" modelAttribute="changePassword">
<div class="container col-lg-7">
	<div class="card">
		<div class="card-header">
			<strong>
				<i class="fa fa-lock"></i> <spring:message code="label.change.password"/>
			</strong>
		</div>
		
		<div class="card-body">
		
		<div class="row">
				<label class="col-md-2 control-label"><spring:message code="label.current.user-id"/> : </label>
				<div class="col-md-4 mb-3">
					<c:out value="${sessionScope.loginid}" />
					</div>
					<label class="col-md-2 control-label"><spring:message code="label.current.user-name"/> : </label>
				<div class="col-md-4 mb-3">
					<c:out value="${sessionScope.username}" />
					</div>
					
			</div>
			<div class="row">
				<label class="col-md-3 control-label"><spring:message code="label.current.password"/> : </label>
				<div class="col-md-5 mb-3">
					<spring:message code="placeholder.current.password" var="currentPwdPlaceholder"/>
					<input class="form-control"  type= "password" id = "oldpwd" name="oldpwd" placeholder="${currentPwdPlaceholder}" required="true"/>
				</div>
			</div>
				
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
		<div class="card-footer text-right">
			<button type="submit" id="change" name="change" class="btn btn-sm btn-primary" onclick="javascript:changePwd();">
			<i class="fa fa-save"></i> <spring:message code="label.save"/>
			</button>
			
			<button type= "reset"  class="btn btn-sm btn-primary"  value="Reset">
				<i class="fa fa-cancel  "></i> <spring:message code="label.cancel"/>
				
			</button>
		</div>
		
	</div>
</div>
</form:form>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>