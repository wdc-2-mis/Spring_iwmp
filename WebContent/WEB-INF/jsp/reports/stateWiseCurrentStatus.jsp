<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<title>Report ME1- State wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No of Villages Covered under Baseline Survey</title>
	<script type="text/javascript">
	function exportExcel(){
		document.rptStateWiseCurrentStatus.action="downloadExcelStateWiseCurrentStatus";
		document.rptStateWiseCurrentStatus.method="post";
		document.rptStateWiseCurrentStatus.submit();
	}
	
	function downloadPDF()
	{
			document.rptStateWiseCurrentStatus.action="downloadStatewiseCurrentStatusPDF";
			document.rptStateWiseCurrentStatus.method="post";
			document.rptStateWiseCurrentStatus.submit();
		
	} 
	
	</script>

<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report ME1- State wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No of Villages Covered under Baseline Survey</label></h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8" >
	<form action="getStateWiseCurrentStatus" method="post" id="getStateWiseCurrentStatus" name="rptStateWiseCurrentStatus">
			<div class="form-row">
			</div>
		</form>
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>    
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Total no. of Districts</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">No. of District Covered in Project</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Total Projects Sanctioned</th>
      		<th colspan="4" style="text-align:center; vertical-align: middle; width: 28%;">Project Location</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Total Village covered in Baseline Survey</th>
     	</tr>
     	<tr>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project with Location Details</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Village Covered in Project</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total no. of Watershed Committee</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Villages Mapped with Watershed Committee</th>
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
		</tr>
		<c:set var="count" value="1" />
		<c:forEach var="list" items="${netTreatledata}">
			<c:forEach var="listUser" items="${list.value}" >
				<tr>
					<td><c:out value='${count}' /></td>
					<td><a href = "getDistWiseStatusBaselNetTreatArea?id= <c:out value='${listUser.st_code}' />&stname=${listUser.st_name}"><c:out value='${listUser.st_name}' /></a></td>
					<td align="right"><c:out value='${listUser.total_dist}' /></td>
					<td align="right"><c:out value='${listUser.total_distproj}' /></td>
					<td align="right"><c:out value='${listUser.total_project}' /></td>
					
					<td align="right"><c:out value='${listUser.tot_proj_loc}' /></td>
					<td align="right"><c:out value='${listUser.total_vill_project}' /></td>
					<td align="right"><c:out value='${listUser.total_wc}' /></td>
					<td align="right"><c:out value='${listUser.total_villin_wc}' /></td>
					
					<td align="right"><c:out value='${listUser.total_vill_basel}' /></td>
					<c:set var="count" value="${count+1}" />
				</tr>
			</c:forEach>
		</c:forEach>
	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary" ></td>
				<td align="right" class="table-primary" ><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[7]}' /> </b></td>
				
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