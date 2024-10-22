<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<title>Report O1- Project wise Mandays Generated and Farmers benefitted Details</title>
<script type= "text/javascript">
function exportExcel(stname,distname,dcode,finYrName){
	document.getElementById("stname").value=stname;
	document.getElementById("distname").value=distname;
	document.getElementById("dcode").value=dcode;
	document.getElementById("finYrName").value=finYrName;
	document.outcomes.action="getProjectwiseMandaysExcel";
	document.outcomes.method="post";
	document.outcomes.submit();
}
	function downloadPDF(stname,distname,dcode,finYrName){
		document.getElementById("stname").value=stname;
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.getElementById("finYrName").value=finYrName;
		document.outcomes.action="downloadProjectWiseAdditionalParameterPDF";
		document.outcomes.method="post";
		document.outcomes.submit();
	}
</script>
<body>
<br>
<form action="downloadProjectWiseAdditionalParameterPDF" method="post" id="outcomes" name="outcomes">
<div class="form-row">
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="distname" id="distname" value="" />
			<input type="hidden" name="dcode" id="dcode" value="" />
			<input type="hidden" name="finYrName" id="finYrName" value="" />
			</div>
</form>
<div class="offset-md-3 col-6 formheading" style="text-align:center;" ><h5><label id ="head">Report O1- Project wise Mandays Generated and Farmers benefitted Details for <c:out value ="${finYrName}"/> Financial Year</label></h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
<%-- 	<form action="getStateWiseStatusAdditionalParameter" method="get"> --%>
<!-- 		<div class="form-group row col-8"> -->
<!-- 			<label for="state" class="col-sm-3 col-form-label"><b>Financial Year</b></label> -->
<!-- 			<div class="col-sm-3"> -->
<!-- 				<select id="fyear" name="fyear" class="form-control d-inline" onchange="this.form.submit();"><option -->
<!-- 						value="">--Select--</option> -->
<%-- 					<c:forEach var="list" items="${yearList}"> --%>
<%-- 						<c:if test="${fyear eq list.key }"> --%>
<%-- 							<option value="${list.key }" selected><c:out value="${list.value }" /></option> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${fyear ne list.key }"> --%>
<%-- 							<option value="${list.key }"><c:out value="${list.value }" /></option> --%>
<%-- 						</c:if> --%>
<%-- 					</c:forEach> --%>
<!-- 				</select> -->
<!-- 			</div> -->

<!-- 		</div> -->
<%-- 	</form> --%>
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stname}','${distname}','${dcode }','${finYrName}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stname}','${distname}','${dcode }','${finYrName}')" class="btn btn-info">PDF</button>    
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
  		<tr>
			<th colspan="12" class="text-left"> State Name : &nbsp; <c:out value='${stname}' /> &nbsp; &nbsp;District Name : &nbsp; <c:out value='${distname}' /></th>
		</tr>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
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
					<td><c:out value='${listUser.proj_name}' /></td>
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