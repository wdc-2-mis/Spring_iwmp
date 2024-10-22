<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="container-fluid">
<div class="row">
<div class="col-12 text-center">
<h5>&nbsp;</h5>
</div>
</div>
<div class="row">
<!-- <div class="col-md-1 offset-md-1">&nbsp;</div> -->
<div class="col-md-12 text-center">
 <div class="">
 <%--  --%>
 <h3 style="font-family:time new roman;" class="list-group-item list-group-item-info"> <spring:message code="label.wdcpmksy"/> </h3>
 <br/>
 </div>                                           
</div>
</div>
<div class="row">
<!-- <div class="col-md-1 offset-md-1">&nbsp;</div> -->
 <div class="col-md-12 text-justify">
  <img src="<c:url value="/resources/images/logo - Copy.png" />" class="img-thumbnail"  style="border:1px solid #e6f4f2;width: 100px; height: 100px; margin-right: 10px; margin-bottom: 10px; float: left;box-shadow: 1px 3px #e6f4f2;" />
                                                <!-- <h4 style="border-bottom:1px solid #e2e2e2;box-shadow: 1px 3px #e6f4f2;font-family:time new roman;"> Watershed Development Component of PMKSY (erstwhile IWMP) </h4> -->
 
 <p class="text-justify">  <spring:message code="label.wdcdetail"/>
</p> 

<h5 class="list-group-item list-group-item-info text-center"><spring:message code="label.objective"/> </h5>
<p class="text-justify"><spring:message code="label.objdesc"/> </p>

</div> 

</div>

<div class="row">
<!-- <div class="col-md-1 offset-md-1">&nbsp;</div> -->
<div class="col-md-12">
<h5 class="list-group-item list-group-item-info"><spring:message code="label.nxtgen"/></h5>
<ul class="list-group">

<li class="list-group-item"><spring:message code="label.emp"/></li>
<li class="list-group-item">	<spring:message code="label.transition"/> </li>
<li class="list-group-item">	<spring:message code="label.diligent"/>  </li>
<li class="list-group-item">	<spring:message code="label.diversification"/> </li>
<li class="list-group-item">	<spring:message code="label.adaptation"/> </li>
<li class="list-group-item">	<spring:message code="label.economic"/> </li>
<li class="list-group-item">	<spring:message code="label.eco"/> </li>
<li class="list-group-item">	<spring:message code="label.focus"/></li>
<li class="list-group-item">	<spring:message code="label.rejuv"/> </li>
<li class="list-group-item">    <spring:message code="label.know"/></li>
</ul>
<h5 class="list-group-item list-group-item-info"><spring:message code="label.cost"/></h5>

<ul class="list-group">
<li class="list-group-item "><spring:message code="label.unitcost"/></li>

<li class="list-group-item">	<spring:message code="label.plainareas"/> </li>
<li class="list-group-item">	<spring:message code="label.desertareas"/></li>
<li class="list-group-item">	<spring:message code="label.iapdistrict"/> </li>

<li class="list-group-item"><spring:message code="label.abovethecost"/></li>
<li class="list-group-item"><spring:message code="label.compdevelopment"/></li>
<li class="list-group-item"><spring:message code="label.mappingresources"/></li>
</ul>


<h5 class="list-group-item list-group-item-info"><spring:message code="label.endresult"/></h5>
<ul class="list-group">

<li class="list-group-item">	<spring:message code="label.plannedwork"/> </li>
<li class="list-group-item">	<spring:message code="label.thegram"/> </li>
<li class="list-group-item">	<spring:message code="label.commorg"/> </li>
<li class="list-group-item">	<spring:message code="label.thefpo"/> </li>
<li class="list-group-item">	<spring:message code="label.increses"/> </li>
<li class="list-group-item">	<spring:message code="label.alternateliv"/> </li>
<li class="list-group-item">	<spring:message code="label.theprojectcomm"/> </li>
<li class="list-group-item">	<spring:message code="label.increaseincrop"/> </li>
<li class="list-group-item">	<spring:message code="label.variousregul"/> </li>
<li class="list-group-item">	<spring:message code="label.increaseinaveg"/> </li>
<li class="list-group-item">	<spring:message code="label.theprojcomm"/></li>
</ul>

</div>
</div>
</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>