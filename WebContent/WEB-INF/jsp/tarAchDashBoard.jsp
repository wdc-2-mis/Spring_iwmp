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
<title>Target/Achievment Dashboard</title>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;1,100;1,200;1,400&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/responsive.css" />"/>

<style>
 #card-data {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  display: flex;
  border: none;
  color: white;
  justify-content: space-around;
}

#card-data td, #card-data th {
  border: 2px solid #ddd;
  padding: 15px;
  
}

#card-data th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  row-gap: 100px;
  color: white;
  border-top: none;
  border-left: none;
  
}
#card-data th:last-child {
  border-right: none;
}
#card-data tr:first-child td {
  border-top: none;
}

#card-data tr:last-child td {
  border-bottom: none;
}
#card-data tr td:first-child {
  border-left: none;
}

#card-data tr td:last-child {
  border-right: none;
}
</style>
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
 
 <div id="sidebar">
 <div class="sidebar-title">
 <div class="sidebar-brand">
 <span class="material-icons-outlined">menu</span>Dashboard
 
 </div>
 
 <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
 
 </div>
 
 <ul class="sidebar-list">
 
 <li class="sidebar-list-item">
 <a class="active" href="tarachDashBoard"><span class="material-icons-outlined">dashboard</span>Target/Achievement</a>
 </li>
  
 <li class="sidebar-list-item">
 <a href="tarAchProgDashboardData"><span class="material-icons-outlined">dashboard</span>Target/Achievement (Progressive)</a>
 </li> 
 
 <li class="sidebar-list-item">
<a href="baselineSurveyDashBoard"> <span class="material-icons-outlined">dashboard</span>Classification of Land at the time of Baseline Survey</a>
  </li>
  </ul>
  
   
 </div>
  <main class="main-container">
  <form action="getTarAchDashboardData" method="get">
  <div class="main-title">
  <table>
  <tr>
  <td>
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
  <td>
  <b>Financial Year:</b>
  <td >
              <select class="form-control finyear" id="finyear" name="finyear" required="required">
      			<!-- <option value="0">--All Financial Year--</option> -->
      			<c:if test="${not empty yearList}">
      			<c:forEach items="${yearList}" var="yearl">
      			<c:if test="${yearl.key eq fyear}">
       								<option value="<c:out value='${yearl.key}'/>"  selected="selected" ><c:out value="${yearl.value}" /></option>
       						</c:if>	
      			<c:if test="${yearl.key ne fyear}">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== fromYear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
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
  
  <div class="main-cards">
 
  <c:forEach var="list" items="${tarachdashdata}">
  <c:forEach var="listUser" items="${list.value}">
  
   <c:if test = "${listUser.headcode == 1}">
  <div class="card">
  <div class="card-inner">
  <h5><b><c:out	value="${listUser.headname} (in ha.)"></c:out></b>
  </h5>
  <span class="material-icons-outlined">agriculture</span>
  </div>
  
  <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
 <%--  <h5><c:out	value="${listUser.target }"></c:out> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <c:out	value="${listUser.achievement }"></c:out></h5> --%>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
  </div>
  
  
  
  </c:if>
  
  <c:if test = "${listUser.headcode == 2}">
 <div class="card">
 <div class="card-inner">
 <h5><c:out	value="${listUser.headname} (in ha.)"></c:out></h5>
 <!-- <div class="icon icon-shape background-orange text-primary"> -->
 <span class="material-icons-outlined">nature</span>
 </div>
 
 <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
 
 </div>
</c:if>

 <c:if test = "${listUser.headcode == 3}">
<div class="card">
 <div class="card-inner">
 <h5><c:out	value="${listUser.headname} (in ha.)"></c:out></h5>
 <span class="material-icons-outlined">water</span>
 </div>
 
 <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
 
 </div>
</c:if>

<c:if test = "${listUser.headcode == 4}">
<div class="card">
 <div class="card-inner">
 <h5><c:out	value="${listUser.headname} (in no.)"></c:out></h5>
 <span class="material-icons-outlined">water_drop</span>
 </div>
 <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
 
 </div>
 </c:if>
 
 <c:if test = "${listUser.headcode == 5}">
 <div class="card">
 <div class="card-inner">
 <h5><c:out	value="${listUser.headname} (in no.)"></c:out></h5>
 <!-- <div class="icon icon-shape background-red text-primary"> -->
 <span class="material-icons-outlined">waves</span>
 </div>
 
 <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
 </div>
 </c:if>
 
 <c:if test = "${listUser.headcode == 6}">
 <div class="card">
 <div class="card-inner">
 <h5><c:out	value="${listUser.headname} (in ha.)"></c:out> </h5>
 <!-- <div class="icon icon-shape background-green text-primary"> -->
 <span class="material-icons-outlined">area_chart</span>
 </div>
 
 <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
 </div>
 </c:if>
 
 <c:if test = "${listUser.headcode == 7}">
 <div class="card">
 <div class="card-inner">
 <h5><c:out	value="${listUser.headname} (in ha.)"></c:out> </h5>
 <!-- <div class="icon icon-shape background-green text-primary"> -->
 <span class="material-icons-outlined">area_chart</span>
 </div>
 
 <table id="card-data">
  <tr>
	<th>Target</th>
	<th>Achievement</th>
  </tr>
 
    <tr>
    <td><b><c:out value="${listUser.target}"/></b></td>
    <td><b><c:out value="${listUser.achievement}"/></b></td>
    </tr>
   
  </table>
 </div>
 </c:if>
 
 </c:forEach>
 </c:forEach>
 </div>
 
 
 <div class="charts">
 
<%-- <div class="charts-card">
 <p class="chart-title">Year Wise Target & Achievement</p>
 <div id="area-chart"></div>
 <c:forEach var="list" items="${tarandachiev}">
 <input type="hidden" name = "targetdata[]" id = "targetdata" value="${list.target }">
 <input type="hidden" name = "achievementdata[]" id = "achievementdata" value="${list.achievement }">
 </c:forEach>
 </div> --%>
  
 <div class="charts-card">
 <h3 class="chart-title">Area brought under Afforestation / Agriculture / Pasture etc.</h3>
 <div id="bar-chart1"></div>
 <c:forEach var="list" items="${monthWiseAch1}">
 <input type="hidden" name = "afforestation1[]" id = "afforestation1" value="${list.achievement }">
 </c:forEach>
 </div>
 
 
 <div class="charts-card">
 <p class="chart-title">Area brought under Horticulture</p>
 <div id="bar-chart2"></div>
 <c:forEach var="list" items="${monthWiseAch2}">
 <input type="hidden" name = "afforestation2[]" id = "afforestation2" value="${list.achievement }">
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Area covered under Soil and Moisture conservation activities</p>
 <div id="bar-chart3"></div>
 <c:forEach var="list" items="${monthWiseAch3}">
 <input type="hidden" name = "afforestation3[]" id = "afforestation3" value="${list.achievement }">
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Water Harvesting Structure (New created)</p>
 <div id="bar-chart4"></div>
 <c:forEach var="list" items="${monthWiseAch4}">
 <input type="hidden" name = "afforestation4[]" id = "afforestation4" value="${list.achievement }">
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Water Harvesting Structure (Renovated)</p>
 <div id="bar-chart5"></div>
 <c:forEach var="list" items="${monthWiseAch5}">
 <input type="hidden" name = "afforestation5[]" id = "afforestation5" value="${list.achievement }">
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Area brought under Protective Irrigation (Command area - New created WHS)</p>
 <div id="bar-chart6"></div>
 <c:forEach var="list" items="${monthWiseAch6}">
 <input type="hidden" name = "afforestation6[]" id = "afforestation6" value="${list.achievement }">
 </c:forEach>
 </div>
 <!--  -->
 <div class="charts-card">
 <p class="chart-title">Area brought under Protective Irrigation (Command area - Renovated WHS) </p>
 <div id="bar-chart7"></div>
 <c:forEach var="list" items="${monthWiseAch7}">
 <input type="hidden" name = "afforestation7[]" id = "afforestation7" value="${list.achievement }">
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Area brought under Afforestation / Agriculture / Pasture etc</p>
 <div id="bar-chart8"></div>
 <c:forEach var="list" items="${topstates}">
 <input type="hidden" name = "topstatename[]" id = "topstatename" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent[]" id = "topachievpercent" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent[]" id = "topachievpercent" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Area brought under Horticulture</p>
 <div id="bar-chart9"></div>
 <c:forEach var="list" items="${topstates2}">
 <input type="hidden" name = "topstatename2[]" id = "topstatename2" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent2[]" id = "topachievpercent2" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent2[]" id = "topachievpercent2" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Area covered under Soil and Moisture conservation activities</p>
 <div id="bar-chart10"></div>
 <c:forEach var="list" items="${topstates3}">
 <input type="hidden" name = "topstatename3[]" id = "topstatename3" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent3[]" id = "topachievpercent3" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent3[]" id = "topachievpercent3" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Water Harvesting Structure (New created)</p>
 <div id="bar-chart11"></div>
 <c:forEach var="list" items="${topstates4}">
 <input type="hidden" name = "topstatename4[]" id = "topstatename4" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent4[]" id = "topachievpercent4" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent4[]" id = "topachievpercent4" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Water Harvesting Structure (Renovated)</p>
 <div id="bar-chart12"></div>
 <c:forEach var="list" items="${topstates5}">
 <input type="hidden" name = "topstatename5[]" id = "topstatename5" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent5[]" id = "topachievpercent5" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent5[]" id = "topachievpercent5" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Area brought under Protective Irrigation (Command area - New created WHS)</p>
 <div id="bar-chart13"></div>
 <c:forEach var="list" items="${topstates6}">
 <input type="hidden" name = "topstatename6[]" id = "topstatename6" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent6[]" id = "topachievpercent6" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent6[]" id = "topachievpercent6" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Top 10 States of Area brought under Protective Irrigation (Command area - Renovated WHS)</p>
 <div id="bar-chart14"></div>
 <c:forEach var="list" items="${topstates7}">
 <input type="hidden" name = "topstatename7[]" id = "topstatename7" value="${list.stname }">
 <c:if test="${list.achievpercent gt 100}">
 <input type="hidden" name = "topachievpercent7[]" id = "topachievpercent7" value="100">
 </c:if>
 <c:if test="${list.achievpercent le 100}">
 <input type="hidden" name = "topachievpercent7[]" id = "topachievpercent7" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <!--Below 10 States  -->
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Area brought under Afforestation / Agriculture / Pasture etc</p>
 <div id="bar-chart15"></div>
 <c:forEach var="list" items="${belowstates}">
 <input type="hidden" name = "belowstatename[]" id = "belowstatename" value="${list.stname }">
 <c:if test="${list.achievpercent  le 0.00}">
 <input type="hidden" name = "belowachievpercent[]" id = "belowachievpercent" value="0">
 </c:if>
 <c:if test="${list.achievpercent gt 0.00}">
 <input type="hidden" name = "belowachievpercent[]" id = "belowachievpercent" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Area brought under Horticulture</p>
 <div id="bar-chart16"></div>
 <c:forEach var="list" items="${belowstates2}">
 <input type="hidden" name = "belowstatename2[]" id = "belowstatename2" value="${list.stname }">
 <c:if test="${list.achievpercent le 0}">
 <input type="hidden" name = "belowachievpercent2[]" id = "belowachievpercent2" value="0">
 </c:if>
 <c:if test="${list.achievpercent gt 0}">
 <input type="hidden" name = "belowachievpercent2[]" id = "belowachievpercent2" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Area covered under Soil and Moisture conservation activities</p>
 <div id="bar-chart17"></div>
 <c:forEach var="list" items="${belowstates3}">
 <input type="hidden" name = "belowstatename3[]" id = "belowstatename3" value="${list.stname }">
 <c:if test="${list.achievpercent le 0}">
 <input type="hidden" name = "belowachievpercent3[]" id = "belowachievpercent3" value="0.00">
 </c:if>
 <c:if test="${list.achievpercent gt 0}">
 <input type="hidden" name = "belowachievpercent3[]" id = "belowachievpercent3" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Water Harvesting Structure (New created)</p>
 <div id="bar-chart18"></div>
 <c:forEach var="list" items="${belowstates4}">
 <input type="hidden" name = "belowstatename4[]" id = "belowstatename4" value="${list.stname }">
 <c:if test="${list.achievpercent le 0}">
 <input type="hidden" name = "belowachievpercent4[]" id = "belowachievpercent4" value="0.00">
 </c:if>
 <c:if test="${list.achievpercent gt 0}">
 <input type="hidden" name = "belowachievpercent4[]" id = "belowachievpercent4" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Water Harvesting Structure (Renovated)</p>
 <div id="bar-chart19"></div>
 <c:forEach var="list" items="${belowstates5}">
 <input type="hidden" name = "belowstatename5[]" id = "belowstatename5" value="${list.stname }">
 <c:if test="${list.achievpercent le 0.00}">
 <input type="hidden" name = "belowachievpercent5[]" id = "belowachievpercent5" value="0.00">
 </c:if>
 <c:if test="${list.achievpercent gt 0}">
 <input type="hidden" name = "belowachievpercent5[]" id = "belowachievpercent5" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Area brought under Protective Irrigation (Command area - New created WHS)</p>
 <div id="bar-chart20"></div>
 <c:forEach var="list" items="${belowstates6}">
 <input type="hidden" name = "belowstatename6[]" id = "belowstatename6" value="${list.stname }">
 <c:if test="${list.achievpercent le 0}">
 <input type="hidden" name = "belowachievpercent6[]" id = "belowachievpercent6" value="0.00">
 </c:if>
 <c:if test="${list.achievpercent gt 0}">
 <input type="hidden" name = "belowachievpercent6[]" id = "belowachievpercent6" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 
 <div class="charts-card">
 <p class="chart-title">Bottom 10 States of Area brought under Protective Irrigation (Command area - Renovated WHS)</p>
 <div id="bar-chart21"></div>
 <c:forEach var="list" items="${belowstates7}">
 <input type="hidden" name = "belowstatename7[]" id = "belowstatename7" value="${list.stname }">
 <c:if test="${list.achievpercent le 0}">
 <input type="hidden" name = "belowachievpercent7[]" id = "belowachievpercent7" value="0.00">
 </c:if>
 <c:if test="${list.achievpercent gt 0}">
 <input type="hidden" name = "belowachievpercent7[]" id = "belowachievpercent7" value="${list.achievpercent }">
 </c:if>
 </c:forEach>
 </div>
 </div>
 </main>
 </div>

<%-- <footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%> 
</footer>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/apexcharts/3.45.0/apexcharts.min.js"></script>
<script src="resources/js/tarachdashboardscript.js"></script>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<script>
var toggle = document.querySelector('.toggle');
var navigation = document.querySelector('.navigation');
var main = document.querySelector('.main');

toggle.onclick = function(){
	navigation.classList.toggle('active');
	main.classList.toggle('active');
}
</script>
</body>
</html>