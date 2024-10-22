<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@include file="/WEB-INF/jspf/header_new.jspf"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<div id="frameDiv" style=" height:800px"> </div>
<head>

<script src="https://tejasvi.gov.in/staging/assets/js/bitoolJS.js"></script>
<script>
  window.onload =  function(){
	  doFrameBI("https://tejasvi.gov.in/staging/public-page-view/5575A5351DC3E53300318878CD150B35E50599871D20A7F8109625F239B7D1519BA367AD8BDBE576F8FE7E8C3C96B6B5", 'frameDiv');
  }
</script>



</head>



<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</html>
