<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>

<title>Report ME5</title>

<script type="text/javascript">


function downloadPDF()
{
	document.me5.action="getStateWiseCurrentStatusPlanAchievePDF";
	document.me5.method="post";
	document.me5.submit();	
}

function exportExcel()
{
	document.me5.action="downloadExcelStateWiseCurrentStatusPlanAchieve";
	document.me5.method="post";
	document.me5.submit();	
}

</script>

</head>
<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report ME5- State and Year wise Current Status of Plan and Achievement </label> </h5></div>
<br>
<div class ="card">
<form action="getStateWiseCurrentStatusPlanAchieve" method="get" id="me5" name="me5">

</form>	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>	
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF();" class="btn btn-info">PDF</button>	
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExamplekd" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2"style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2"style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th rowspan="2"style="text-align:center; vertical-align: middle; width: 7%;">Total Projects Sanctioned</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2022-23)</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2023-24)</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2024-25)</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2025-26)</th>
      	</tr>	
      	<tr>	
      		<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
     	</tr>
     	
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
			<th class="text-center">7</th>
			<th class="text-center">8</th>
			<th class="text-center">9</th>
			<th class="text-center">10</th>
			<th class="text-center">11</th>
		</tr>
		<c:set var="count" value="1" />
		<c:forEach var="list" items="${netTreatledata}">
			<c:forEach var="listUser" items="${list.value}" >
				<tr>
					<td><c:out value='${count}' /></td>
					<td><a href = "getdistrictWiseCurrentStatusPlanAchieve?id= <c:out value='${listUser.st_code}' />&stname=${listUser.st_name}"><c:out value='${listUser.st_name}' /></a></td>
					<td align="right"><c:out value='${listUser.total_project}' /></td>
					<td align="right"><c:out value='${listUser.total_project_plan2022}' /></td>
					<td align="right"><c:out value='${listUser.total_project_achievement2022}' /></td>
					<td align="right"><c:out value='${listUser.total_project_plan2023}' /></td>
					<td align="right"><c:out value='${listUser.total_project_achievement2023}' /></td>
					<td align="right"><c:out value='${listUser.total_project_plan2024}' /></td>
					<td align="right"><c:out value='${listUser.total_project_achievement2024}' /></td>
					<td align="right"><c:out value='${listUser.total_project_plan2025}' /></td>
					<td align="right"><c:out value='${listUser.total_project_achievement2025}' /></td>
					<c:set var="count" value="${count+1}" />
				</tr>
			</c:forEach>
		</c:forEach>
	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary"></td>
				<td align="right" class="table-primary" ><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[7]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[8]}' /> </b></td>
			</tr>
		</c:forEach> 
	</tbody>
  </table>
</div>
</div>
</div>


<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>