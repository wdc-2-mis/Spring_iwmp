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
<title>BaseLine Dashboard</title>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;1,100;1,200;1,400&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/stylenew.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/responsive.css" />"/>

</head>
<body>	


 <div class ="grid-container">
 
 <header class="header">
 <div class="menu-icon" onclick="openSidebar()">
 <span class="material-icons-outlined">menu</span>
 </div>
 
 
 <div class="header-left">
 <ul class="list">
 <li class="list-item">
 <a href="#">
 <span class="material-icons-outlined">dashboard</span>DASHBOARD
 </a>
 </li>
 <li class="list-item">
 <a href="#">
 <span class="material-icons-outlined">home</span>HOME
 </a>
 </li>
 <li class="list-item">
 <a href="#">
 <span class="material-icons-outlined">poll</span>REPORT
 </a>
 </li>
 </ul>
 </div>
  
 <div class="header-right">
 <ul class="list">
 <li class="list-item">
 <a href="#">
 <span class="material-icons-outlined">login</span>LOGIN
 </a>
 </li>
 </ul>
 </div>
 </header>
 
 <div id="sidebar">
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
 <a href="tarAchProgDashboardData"><span class="material-icons-outlined">dashboard</span>Target/Achievement (Progressive)</a>
 </li>
 
 <li class="sidebar-list-item">
<a class="active" href="baselineSurveyDashBoard"> <span class="material-icons-outlined">grid_view</span>Classification of Land at the time of Baseline Survey</a>
  </li>
  </ul>
  
  
 </div>
 

<main class="main-container"> 

<div class="graphHeader">
 <div class="boxHeader">
  <center><p class="chart-title">Classification of Land(Area)</p></center>
  </div>
  
  <div class="boxHeader1">
  <center><p class="chart-title">Irrigation Status(Area)</p></center>
  </div>
  </div>
  
 <div class="graphBox">
  <div class="box">
  <center><p class="chart-title">As on Baseline.</p></center>
  <canvas id="myChart"></canvas>
  <c:forEach var="list" items="${classificationbaseline}">
 <input type="hidden" name = "cultivable_wasteland" id = "cultivable_wasteland" value="${list.cultivable_wasteland }">
 <input type="hidden" name = "degraded_land" id = "degraded_land" value="${list.degraded_land }">
 <input type="hidden" name = "rainfed" id = "rainfed" value="${list.rainfed }">
 <input type="hidden" name = "forest_land" id = "forest_land" value="${list.forest_land }">
 <input type="hidden" name = "others" id = "others" value="${list.others }">
 </c:forEach>
  </div>
 
 <div class="box">
 <center><p class="chart-title">As on Date.</p></center>
  <canvas id="myChartBaseline"></canvas> 
  <c:forEach var="list" items="${classificationondate}">
 <input type="hidden" name = "cultivableondate" id = "cultivableondate" value="${list.cultivable_wasteland_achv }">
 <input type="hidden" name = "degradedondate" id = "degradedondate" value="${list.degraded_land_achv }">
 <input type="hidden" name = "rainfedondate" id = "rainfedondate" value="${list.rainfed_achv }">
 <input type="hidden" name = "forestondate" id = "forestondate" value="${list.forest_land_achv }">
 <input type="hidden" name = "othersondate" id = "othersondate" value="${list.others_achv }">
 </c:forEach>
  </div>
 <div class="box2">
  <center><p class="chart-title">As on Baseline.</p></center>
  <canvas id="irrgationBaseline"></canvas> 
  <c:forEach var="list" items="${irrigationbaseline}">
 <input type="hidden" name = "protectiveBase" id = "protectiveBase" value="${list.protective_irrigation }">
 <input type="hidden" name = "assuredBase" id = "assuredBase" value="${list.assured_irrigation }">
 <input type="hidden" name = "noirrBase" id = "noirrBase" value="${list.no_irrigation }">
 <input type="hidden" name = "otherBase" id = "otherBase" value="${list.others_owned }">
 
 </c:forEach>
  </div> 
 
 <div class="box2">
  <center><p class="chart-title">As on Date.</p></center>
 <canvas id="myChartirrigation"></canvas> 
  <c:forEach var="list" items="${irrigationondate}">
 <input type="hidden" name = "protectiveDate" id = "protectiveDate" value="${list.protective_irrigation_achv }">
 <input type="hidden" name = "assuredDate" id = "assuredDate" value="${list.assured_irrigation_achv }">
  <input type="hidden" name = "noirrDate" id = "noirrDate" value="${list.no_irrigation_achv }">
 <input type="hidden" name = "otherDate" id = "otherDate" value="${list.others_owned_achv }">   
 
 </c:forEach>
  </div> 
 </div>
 
 <div class="graphHeader1">
 <div class="boxHeader4">
  <center><p class="chart-title">Number of Crops(Area)</p></center>
  </div>
  
 <div class="boxHeader3">
  <center><p class="chart-title">Ownership(Area)</p></center>
  </div>
  
  
  </div>
  <div class="graphBox1">
  <div class="box4">
 <center><p class="chart-title">As on Baseline.</p></center>
 <canvas id="myChartcropBase"></canvas> 
  <c:forEach var="list" items="${noofcropsbaseline}">
 <input type="hidden" name = "nocropBase" id = "nocropBase" value="${list.no_crop }">
 <input type="hidden" name = "singlecropBase" id = "singlecropBase" value="${list.single_crop }">
  <input type="hidden" name = "doublecropBase" id = "doublecropBase" value="${list.double_crop }">
 <input type="hidden" name = "multicropBase" id = "multicropBase" value="${list.multiple_crop }">   
 </c:forEach>
  </div> 
  
  <div class="box4">
 <center><p class="chart-title">As on Date.</p></center>
 <canvas id="myChartcropDate"></canvas> 
  <c:forEach var="list" items="${noofcropsondate}">
 <input type="hidden" name = "nocropDate" id = "nocropDate" value="${list.no_crop_ach }">
 <input type="hidden" name = "singlecropDate" id = "singlecropDate" value="${list.single_crop_ach }">
  <input type="hidden" name = "doublecropDate" id = "doublecropDate" value="${list.double_crop_ach }">
 <input type="hidden" name = "multicropDate" id = "multicropDate" value="${list.multiple_crop_ach }">   
 </c:forEach>
  </div>
 <div class="box3">
 <center><p class="chart-title">As on Baseline.</p></center>
 <canvas id="myChartownership"></canvas> 
  <c:forEach var="list" items="${ownershipbaseline}">
 <input type="hidden" name = "privateBase" id = "privateBase" value="${list.private_owner }">
 <input type="hidden" name = "govtBase" id = "govtBase" value="${list.govt_owned }">
  <input type="hidden" name = "communityBase" id = "communityBase" value="${list.community_owned }">
 <input type="hidden" name = "otherBase" id = "otherBase" value="${list.others_owned }">   
 </c:forEach>
  </div> 
 
 <div class="box3">
 <center><p class="chart-title">As on Date.</p></center>
 <canvas id="myChartownerDate"></canvas> 
  <c:forEach var="list" items="${ownershipondate}">
 <input type="hidden" name = "privateDate" id = "privateDate" value="${list.private_owner_ach }">
 <input type="hidden" name = "govtDate" id = "govtDate" value="${list.govt_owned_ach }">
  <input type="hidden" name = "communityDate" id = "communityDate" value="${list.community_owned_ach }">
 <input type="hidden" name = "otherODate" id = "otherODate" value="${list.others_owned_ach }">   
 </c:forEach>
  </div> 
  
   
  
 </div>
 </main>
 </div>
 

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@3.5.1/dist/chart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="resources/js/baselinedashboardscript.js"></script>
<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
</body>
</html>