<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Report ME3- State wise Current Status of Baseline Survey and Net Treatable Area</title>

<script type="text/javascript">


function downloadPDF(){
	document.getrpt.action="downloadStateWiseStatusBaselNetTreatAreaPDF";
	document.getrpt.method="post";
	document.getrpt.submit();
}
function exportExcel(){
	document.getrpt.action="downloadExcelStateWiseStatusBaselNetTreatArea";
	document.getrpt.method="post";
	document.getrpt.submit();
}

</script>

<body>
<br>
<form action="downloadStateWiseStatusBaselNetTreatAreaPDF" method="post" id="getrpt" name="getrpt">
			<div class="form-row">
			<input type="hidden" name="id" id="id" value="" />
			</div>
		</form>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report ME3- State wise Current Status of Baseline Survey and Net Treatable Area</label></h5></div> 
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>    
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total no. of Districts</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Projects Sanctioned</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Project Area Sanctioned</th>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Project Area Covered for Baseline Survey</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area of Plots Covered for Baseline Survey</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;"><b>%</b> of Total Area Covered for Plot-Wise Baseline Survey</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Net Treatable Area(in Lakhs Ha.)</th>
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
			
		</tr>
		<c:set var="count" value="1" />
		<c:forEach var="list" items="${netTreatledata}">
			<c:forEach var="listUser" items="${list.value}" >
				<tr>
					<td><c:out value='${count}' /></td>
					<!--  <td><c:out value='${listUser.st_name}' /></td>-->
					<td><a href = "getDistrictWiseStatusBaselNetTreatArea?id= <c:out value='${listUser.st_code}' />&stname=${listUser.st_name}"><c:out value='${listUser.st_name}' /></a></td>
					<td align="right"><c:out value='${listUser.total_dist}' /></td>
					<td align="right"><c:out value='${listUser.total_project}' /></td>
					<td align="right"><c:out value='${listUser.project_area}' /></td>
					<td align="right"><c:out value='${listUser.proj_area_coverbasel}' /></td>
					<td align="right"><c:out value='${listUser.plot_area_basel}' /></td>
					<td align="right"><c:out value='${listUser.percentage_area}' />%</td>
					
					<td align="right"><c:out value='${listUser.net_treat_area}' /></td>
					<c:set var="count" value="${count+1}" />
				</tr>
			</c:forEach>
		</c:forEach>
	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary"></td>
				<td align="left" class="table-primary" ><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
				
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td>
			<!-- 	<td class="table-primary"></td> -->
 				<td align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> %</b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /></b></td>
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