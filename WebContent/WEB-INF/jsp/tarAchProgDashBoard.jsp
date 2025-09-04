<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%-- <%@include file="/WEB-INF/jspf/header.jspf"%>  --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Progressive</title>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;1,100;1,200;1,400&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styleprogressive.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/responsive.css" />"/>
  <link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/bootstrap.min.dashboard.css" />"> 
 </head>
<body>	


 <div class ="grid-container">
 
 <header class="header">
 <!-- <div class="menu-icon" onclick="openSidebar()">
 <span class="material-icons-outlined">menu</span>
 </div> -->
 
 
 <div class="header-left">
 <ul class="list">
 <li class="list-item">
 <a href="dolrDashBoard">
 <span class="material-icons-outlined">dashboard</span>DASHBOARD
 </a>
 </li>
 <li class="list-item">
 <a href="./">
 <span class="material-icons-outlined">home</span>HOME
 </a>
 </li>
 <li class="list-item">
 <a href="reports">
 <span class="material-icons-outlined">poll</span>REPORT
 </a>
 </li>
 <li class="list-item">
 <a href="tarachDashBoard">
 <span class="material-icons-outlined">dashboard</span>TARGET/ACHIEVEMENT
 </a>
 </li>
 </ul>
 </div>
  
 <div class="header-right">
 <ul class="list">
 <li class="list-item">
 <a href="login">
 <span class="material-icons-outlined">login</span>LOGIN
 </a>
 </li>
 </ul>
 </div>
 </header>
 
 <!-- <div id="sidebar">
 <div class="sidebar-title">
 <div class="sidebar-brand">
 <span class="material-icons-outlined">menu</span>Dashboard
  
 </div>
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
 </div>
 
 <ul class="sidebar-list">
 
 <li class="sidebar-list-item">
 <a href="tarachDashBoard"><span class="material-icons-outlined">grid_view</span>Target/Achievement</a>
 </li>
  
 <li class="sidebar-list-item">
 <a class="active" href="tarAchProgDashboardData"><span class="material-icons-outlined">dashboard</span>Target/Achievement (Progressive)</a>
 </li>
 
 <li class="sidebar-list-item">
<a href="baselineSurveyDashBoard"> <span class="material-icons-outlined">grid_view</span>Classification of Land at the time of Baseline Survey</a>
  </li>
  </ul>
  
  
 </div> -->
 

<main class="main-container" > 
<form action="getTarAchProDashData" method="get">
  <div class="main-title">
  <table>
  <tr>
  <td style="color:#000000">
  <b>State Name:</b></td>
  		<td align="left">
<select name="state" id="state">
              		<option value="0">--All State--</option>
                  	<c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
									
  </td>
 
 <td></td><td></td><td></td><td></td>
  <td style="color:#000000">
  <b>Physical Head:</b></td>
 <td>
 <select name="head" id="head">
              		<c:if test="${not empty headList}">
               			<c:forEach items="${headList}" var="lists">
               				<c:if test="${lists.key eq head}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne head}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
 
 </td>
 <td></td><td></td>
  <td>
  <input type="button" class="btn btn-info" id="getData" value="Get Data" onclick="this.form.submit();">
  </td>
 
 
 </tr>
  </table>
  </div>
  </form> 
 
 <div class="graphBox">
  <div class="box">
   <div class="rowheader">
   <c:forEach items="${headname}" var="rpt">
  <h6><center>${rpt.headname}&nbsp(in ${rpt.unit})</center></h6>
 </c:forEach>
 </div>
  <canvas id="mixedChart"></canvas>
  <c:forEach var="list" items="${progressiveData}">
  <input type="hidden" name = "yeardesc[]" id = "yeardesc" value="${list.descr }">
 <input type= "hidden" name = "achievement[]" id = "achievement" value="${list.achiev }">
  <input type="hidden" name = "progachievment[]" id = "progachievment" value="${list.progachieve }">
  </c:forEach>
  </div>
 
 </div>
 <div class="row">
 <c:if test="${stCode eq '0'}">
 
  <div class="details">
  <div class="rowheader">
  <c:forEach items="${headname}" var="rpt">
  <h6><b>Physical Head </b>: &nbsp; ${rpt.headname}</h6>
 </c:forEach>
  </div>
 <table class="table table-hover">
 <thead>
 <tr class="table-primary">
 <td><b>S No.</b></td>
 <td><b>State Name</b></td>
 <c:forEach items="${headname}" var="rpt">
 <td><b>Achievement &nbsp;(in ${rpt.unit})</b></td>
 </c:forEach>
 </tr>
 </thead>
 <tbody>
 
 <c:forEach items="${statewiseProgressive}" var="rpt" varStatus="status">
					<tr class="table-danger" name="state" value="${rpt.stname}" id=${rpt.stcode},<c:out value ='${headcode}'/> onmouseover="myOverStateFunction(this)" onmouseout="myOutStateFunction(this)">
						
						<td align="center">${status.count} <input type="hidden" name="headname" value=${rpt.headname}}/></td>
						<td style="text-align: left;">${rpt.stname}</td>
						<td style="text-align: left;">${rpt.achievement}</td>
						</tr>
						
					<c:set var="totalachievement"
					value="${totalachievement+rpt.achievement}" />	
</c:forEach>	
                <tr class="table-dark">
                <td></td>
				<td  align="left" class="table-primary"><b>Grand Total</b></td>
				<td align="left" class="table-primary"><b><c:out value="${totalachievement}" /></b></td>
				</tr>					
 </tbody>
 </table>
 
 </div>
 
 <div class="stateRecords">
  <c:forEach items="${headname}" var="rpt">
  <h6 style="color:#0000FF"><center><u><b>State Wise:</b> &nbsp; ${rpt.headname}&nbsp; (in ${rpt.unit})</u></center></h6>
  </c:forEach>
  <c:if test="${not empty msg}">

				<div class="form-group col-md-12">

					<div class="col">
<tr>State:</tr>
						<label class="alert alert-success"> ${msg} </label>

					</div>

				</div>

			</c:if>
  
  <canvas id="mixedChart3"></canvas>
  <table class="table">
  <tbody id ="tbodyTranxStateDetails">
  
  </tbody>
  </table>
   </div>
</c:if>
 
 <c:if test="${stCode ne '0'}">
  
  <div class="districtdetails">
  <div class="rowheader">
  
  <!-- <h6>District Wise Records</h6> -->
  <c:forEach items="${headname}" var="rpt">
  <h6><b>Physical Head </b>: &nbsp; ${rpt.headname}</h6>
 </c:forEach>
  </div>
 <table class="table table-hover" id="tbl">
 <thead>
 <tr class="table-primary">
 <td><b>S No.</b></td>
 <td><b>District Name</b></td>
 <!-- <td><b>Physical Head</b></td> -->
 <c:forEach items="${headname}" var="rpt">
 <td><b>Achievement &nbsp;(in ${rpt.unit})</b></td>
 </c:forEach>
 </tr>
 </thead>
 <tbody>
 
 <c:forEach items="${districtwiseProgressive}" var="rpt" varStatus="status">
					<tr class="table-danger" name= "abc" value="${rpt.distname}" id=${rpt.dcode},<c:out value ='${headcode}'/>  onmouseover="myOverFunction(this)" onmouseout="myOutFunction(this)">
						<td align="center">${status.count}</td>
						
						<td style="text-align: left;">${rpt.distname}</td>
						<%-- <td style="text-align: left;">${rpt.headname}</td> --%>
						<td style="text-align: left;">${rpt.achievement}</td>
						</tr>
						
					<c:set var="totalachievement"
					value="${totalachievement+rpt.achievement}" />	
</c:forEach>	
                <tr class="table-dark">
                <td></td>
				<td  align="left" class="table-primary"><b>Grand Total</b></td>
				
				<td align="left" class="table-primary"><b><c:out value="${totalachievement}" /></b></td>
				</tr>					
 </tbody>
 </table>
 
 </div>
  
   <%-- <c:if test="${checkdata eq '1'}">
  
  <div class="districtRecords">
  
  <h6 style="color:#000000"><center><u>District Wise Recordsssss</u></center></h6>
  <canvas id="mixedChart2"></canvas>
  <table class="table">
  <tbody id="tbodyTranxState">
  <c:forEach var="list" items="${districtDtl}">
  <input type="hidden" name = "yeardesc2[]" id = "yeardesc2" value="${list.descr }">
 <input type= "hidden" name = "achievement2[]" id = "achievement2" value="${list.achiev }">
  <input type="hidden" name = "progachievment2[]" id = "progachievment2" value="${list.progachieve }">
  </c:forEach>
  </tbody>
  </table>
  </div> 
  
  </c:if>
  --%>
 
  <div class="districtRecords">
  <c:forEach items="${headname}" var="rpt">
  <h6 style="color:#0000FF"><center><u><b>District Wise:</b> &nbsp; ${rpt.headname}&nbsp; (in ${rpt.unit})</u></center></h6>
  </c:forEach>
  <c:if test="${not empty msg}">

				<div class="form-group col-md-12">

					<div class="col">
<tr>District:</tr>
						<label class="alert alert-success"> ${msg} </label>

					</div>

				</div>

			</c:if>
  
  <canvas id="mixedChart1"></canvas>
  <table class="table">
  <tbody id ="tbodyTranxDetails">
  
  </tbody>
  </table>
   </div>
 
 
 
 
 </c:if>
 
 
 
 </div>
 
 </main>
 
  
 </div>
 

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@3.5.1/dist/chart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="resources/js/tarachProgdashboardscript.js"></script>
<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
<script src='<c:url value="/resources/js/jquery.min.js" />'></script>
</body>
</html>