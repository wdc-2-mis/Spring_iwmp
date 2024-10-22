<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Report O1- State wise Mandays Generated and Farmers benefitted Details</title>

</head>
<script type= "text/javascript">
function exportExcel(finyear){
	var finYrName = document.getElementById("fyear").options[document.getElementById("fyear").selectedIndex].text;
	document.getElementById("finyear").value=finyear;
	document.getElementById("finYrName").value=finYrName;
	document.outcome.action="getStatewiseMandaysExcel";
	document.outcome.method="post";
	document.outcome.submit();
}
function downloadPDF(finyear){
	var finYrName = document.getElementById("fyear").options[document.getElementById("fyear").selectedIndex].text;
	document.getElementById("finYrName").value=finYrName;
	document.getElementById("finyear").value=finyear;
	document.outcome.action="downloadAdditionalParameterPDF";
	document.outcome.method="post";
	document.outcome.submit();
}
</script>
<body>
<br>
<form action="downloadAdditionalParameterPDF" method="post" id="outcome" name="outcome">
<!-- 			<div class="form-row"> -->
			<input type="hidden" name="finyear" id="finyear" value="" />
			<input type="hidden" name="finYrName" id="finYrName" value="" />
<!-- 			</div> -->
		</form>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O1- State wise Mandays Generated and Farmers benefitted Details</h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	<c:set var ="finYrName" value =""/>
	<form action="getStateWiseStatusAdditionalParameter" method="get">
		<div class="form-group row col-8">
			<label for="state" class="col-sm-3 col-form-label"><b>Financial Year</b></label>
			<div class="col-sm-3">
				<select id="fyear" name="fyear" class="form-control d-inline" onchange="this.form.submit();">
				<option value="">--All--</option>
					<c:forEach var="list" items="${yearList}">
						<c:if test="${fyear eq list.key }">
							<option value="${list.key }" selected><c:out value="${list.value }" /></option>
							<c:set var ="finYrName" value ="${list.value }"/>
						</c:if>
						<c:if test="${fyear ne list.key }">
							<option value="${list.key }"><c:out value="${list.value }" /></option>
						</c:if>
					</c:forEach>
				</select>
			</div>

		</div>
	</form>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${fyear}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${fyear}')" class="btn btn-info">PDF</button>
<!-- 	<button name="exportPDF" id="exportPDF" onclick="exportPDF()" class="btn btn-info">PDF</button>     -->
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">No. of Mandays generated (in mandays) </th>
      		<th colspan="9" style="text-align:center; vertical-align: middle; width: 63%;">No. of Farmers benefitted </th>
       	</tr>
     	<tr>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">SC </th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">ST</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Others</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Female</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Small Farmers</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Marginal Farmers</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Landless </th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">BPL </th>
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
			<th class="text-center">12</th>
		</tr>
		<c:set var="count" value="1" />
		<c:forEach var="list" items="${netTreatledata}">
			<c:forEach var="listUser" items="${list.value}" >
				<tr>
					<td><c:out value='${count}' /></td>
					<td><a href="getDistWiseStatusAdditionalParameter?stcode=<c:out value="${listUser.st_code}"/>&fyear=<c:out value="${fyear}"/>&fyrname=<c:out value="${finYrName}"/>"><c:out value='${listUser.st_name}' /> </a></td>
					<td align="right"><c:out value='${listUser.man_day}' /></td>
					<td align="right"><c:out value='${listUser.sc}' /></td>
					<td align="right"><c:out value='${listUser.st}' /></td>
					<td align="right"><c:out value='${listUser.other}' /></td>
					<td align="right"><c:out value='${listUser.total}' /></td>
					<td align="right"><c:out value='${listUser.female}' /></td>
					<td align="right"><c:out value='${listUser.small}' /></td>
					<td align="right"><c:out value='${listUser.mirginal}' /></td>
					<td align="right"><c:out value='${listUser.landless}' /></td>
					<td align="right"><c:out value='${listUser.bpl}' /></td>
					
					<c:set var="count" value="${count+1}" />
				</tr>
			</c:forEach>
		</c:forEach>
	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
<!-- 				<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td> -->
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[7]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[8]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[9]}' /> </b></td>
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