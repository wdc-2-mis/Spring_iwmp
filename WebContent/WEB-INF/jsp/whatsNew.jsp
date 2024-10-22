<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<div class="container-fluid">
<div class="row">
<div class="col-12 text-center">
<h5>Whats New</h5>
</div>
</div>
<div class="row">
<div class="col-sm-offset-3 col-sm-3"></div>
 
 <img src="<c:url value="/resources/images/webPageConstruction1.jpg" />" class="img-fluid" />
                                                
</div>
</div>

<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>