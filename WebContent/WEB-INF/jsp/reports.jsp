<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header_new.jspf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
</head>
<nav class ="sidebar " style="min-height:100px">
<div class="container-fluid">
<div class="row">
<div class="col">
<section class="panel panel-primary">
  <header class="panel-heading">
   <h3 class="panel-title">
   <center>
   <c:choose>
            <c:when test="${roleId == 1}">MIS Report</c:when>
            <c:otherwise>Watershed Yatra Report</c:otherwise>
         </c:choose>
   </center>
   </h3>
  </header>
  
  <div class="panel-body">
  
  </div>
</section>

 <div class="not-found" id="not-found" style="display:none">Not Found</div>
    <br>
  <!--   <input type="text" class="form-control" id="mySearch" onkeyup="searchReport()" placeholder="Search.." title="Type in a category"> -->
  </div>
  </div>
  <div class="row">
  <div class="col-md-1 offset-md-1">&nbsp;</div>
<div class="col-md-7">
   <ul id="myMenu" class="list-group">
  
   
        <c:forEach items="${dataList}" var="dataV" varStatus="status">
      <li>
      
      <c:if test="${dataV[2] ne '' && dataV[2] ne reporthead }">
      <c:set var="reporthead" value="${dataV[2]}"></c:set>
      	<a href="#" class="list-group-item list-group-item-info"><b><c:out value='${reporthead}' /></b></a>
      	
      </c:if>	
	      <ul class="list-group">
	      	<li class="list-group-item"><a href="<c:out value='${dataV[5]}' />" target="_blank"><c:out value='${dataV[4]}' /></a></li>
	      </ul>
      </li>
      </c:forEach>
      
      
   
    
   
  </ul>
  </div>
  <div class="col-5 hide" style="border:1px solid red;">
  </div>
  </div>
  </div>
  </nav>
  
  <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>