<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header_new.jspf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/contactstyles.css" />" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>

<body>
<section>
<div class="container">
 <div class="contactinfo">
  <div>
   <h2 style="font-size:1vw;"><u><spring:message code="label.helpdesk"/></u></h2>
   <ul class="info">
  <li> 
<span> 
<br>
<h5 style="font-size:1vw;"><b><spring:message code="label.emailto"/> <br></b> <spring:message code="label.support"/><br></h5><br>
<h6 style=" font-size:1vw; animation: blinker 3s linear infinite;color:yellow;"><b><spring:message code="label.ifproblem"/></b></h6><br>
<h5 style="font-size:1vw;"><b><spring:message code="label.kindlyemail"/></b>  <spring:message code="label.omemail"/> </h5>
</span>
</li>

   </ul>	
  </div>
 </div>

    <div class="contactForm">
     <h2 style="font-size:1vw;"><u><b><spring:message code="label.teammember"/></b></u></h2>
      
   <div class="col-lg-1 offset-1 col-md-2 col-sm-2 col-2">

  </div>
<br>
<div class="col-lg-10 col-md-9 col-sm-9 col-9">

<h4 style="font-size:1vw;"><i class="bi bi-person-fill"></i> <spring:message code="label.seemantinee"/></h4>
<h6 style="font-size:1vw;"><spring:message code="label.ddg"/></h6>
<h6 style="font-size:1vw;"><spring:message code="label.ssen"/></h6>
</div>
<br>
<div class="col-lg-1 offset-1 col-md-2 col-sm-2 col-2">

  </div>
  
  <div class="col-lg-10 col-md-9 col-sm-9 col-9"><br>
     
<h4 style="font-size:1vw;"><i class="bi bi-person-fill"></i> <spring:message code="label.inderpal"/></h4>
<h6 style="font-size:1vw;"><spring:message code="label.std"/></h6>
<h6 style="font-size:1vw;"><spring:message code="label.sethi"/></h6>
    </div>
    
<%-- <div class="col-lg-10 col-md-9 col-sm-9 col-9">
<h4 style="font-size:1vw;"><i class="bi bi-person-fill"></i> <spring:message code="label.naveen"/></h4>
<h6 style="font-size:1vw;"><spring:message code="label.std"/></h6>
<h6 style="font-size:1vw;"><spring:message code="label.naveenemail"/></h6>
</div> --%>
<br>
<div class="col-lg-1 offset-1 col-md-2 col-sm-2 col-2">

  </div>
<div class="col-lg-10 col-md-9 col-sm-9 col-9">
<h4 style="font-size:1vw;"><i class="bi bi-person-fill"></i> <spring:message code="label.omlata"/></h4>
<h6 style="font-size:1vw;"><spring:message code="label.c"/></h6>
<h6 style="font-size:1vw;"><spring:message code="label.omemail"/></h6>
</div>
    
    </div>
</div>
</section>
<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>