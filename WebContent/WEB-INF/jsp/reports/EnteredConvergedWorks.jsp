<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report CW1- State-wise Entry Status of Converged Work Details</title>

<script type="text/javascript">

function downloadPDF(){
		document.getConWorksDetails.action="downloadPDFEnteredConWorks";
		document.getConWorksDetails.method="post";
		document.getConWorksDetails.submit();
}

function exportExcel(){
		document.getConWorksDetails.action="downloadExcelEnteredConWorks";
		document.getConWorksDetails.method="post";
		document.getConWorksDetails.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report CW1- State-wise Entry Status of Converged Work Details</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getConWorksDetails" name="getConWorksDetails" id="getConWorksDetails" method="get">
		<div id="waitDiv" 
			style="display: none; line-height: 20px; z-index: 98; position: absolute; background: #ffffff; left: 25px;  height: 800px;
			 width: 1600px; filter: alpha(opacity = 60); -moz-opacity: .60; opacity: .60; text-align: center; float: left;">
			<table>  
				<tr>
					<td>
						<div align="center">
							<span style="padding-right:3px;  display:inline-block; width: 1600px;">
									<img class="manImg" src="resources/images/load.gif"></img>
							</span>
						</div>
					</td>
				</tr>
			</table> 
		</div>
		
 	</form:form>
 
<br>
	<c:if test="${not empty enteredConList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			<tr>
				<th>S.No.</th>
				<th>State Name</th>
				<th>Total No. of District</th>
				<th>Total No. of Project</th>
				<th>Total No. of Works</th>
				<th>Total No. of Converged Works</th>
				<th>Total No. of Works Entered Convergence Detail</th>
			</tr>
		</thead>
		<tbody id = "convergedWorksRptTbody">
			<tr>
				<th class="text-center">1</th>
				<th class="text-center">2</th>
				<th class="text-center">3</th>
				<th class="text-center">4</th>
				<th class="text-center">5</th>
				<th class="text-center">6</th>
				<th class="text-center">7</th>
			</tr>
			<c:forEach items="${enteredConList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.st_name}" /></td>
					<td class="text-right"><c:out value="${dt.totaldist}" /></td>
					<td class="text-right"><c:out value="${dt.totalproject}" /></td>
					<td class="text-right"><c:out value="${dt.totalworks}" /></td>
					<td class="text-right"><c:out value="${dt.convergedworks}" /></td>
					<td class="text-right"><c:out value="${dt.enteredcon}" /></td>
				</tr>
			
 				<c:set var = "totdist" 
 				value = "${totdist + dt.totaldist}" />
 				<c:set var = "totproj" 
 				value = "${totproj + dt.totalproject}" /> 
				<c:set var = "totwrks" 
 				value = "${totwrks + dt.totalworks}" />
 				<c:set var = "conwrks" 
 				value = "${conwrks + dt.convergedworks}" /> 
 				<c:set var = "enteredwrks" 
 				value = "${enteredwrks + dt.enteredcon}" /> 

			</c:forEach>
			<c:if test="${enteredConListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totdist}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totwrks}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${conwrks}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${enteredwrks}" /></b></td>
				</tr>
			</c:if>
			<c:if test="${enteredConListSize==0}">
				<tr>
					<td align="center" colspan="7" class="required" style="color:red;"><b>Data Not Found</b></td>
				</tr>
			</c:if>
		</tbody>
	</table>
			</div>
 		</div>
	</div>


	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>