<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE12-  State-wise Mid Term Evaluation of Grade Report</title>

<script type="text/javascript">

function downloadPDF(){
		document.getfundutl.action="downloadFundUtilizationEvalReportPdf";
		document.getfundutl.method="post";
		document.getfundutl.submit();
}

function exportExcel(){
		document.getfundutl.action="downloadExcelFundUtilizationEvalReport";
		document.getfundutl.method="post";
		document.getfundutl.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE12-  State-wise Mid Term Evaluation of Grade Report</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getConWorksDetails" name="getfundutl" id="getfundutl" method="get">

		
 	</form:form>
 
<br>
<%-- 	<c:if test="${not empty gradeList}"> --%>
<!-- 	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
<!-- 	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>  -->
<%-- 	</c:if>    --%>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th>S.No.</th>
            <th>State Name</th>
             <th>Total No. of District</th>
            <th >Total No. of Project</th>
            <th >Total No. of Grade E</th>
            <th style="text-align: center">Total No. of Grade G</th>
            <th style="text-align: center">Total No. of Grade Avg</th>
            <th >Total No. of Grade S</th>
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
				<th class="text-center">8</th>
			</tr>
			<c:forEach items="${gradeList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.st_name}"/></td>
					<td class="text-right"><c:out value="${dt.totaldist}" /></td>
					<td class="text-right"><c:out value="${dt.total_project}" /></td>
					<td class="text-right"><c:out value="${dt.excelent_proj}" /></td>
					<td class="text-right"><c:out value="${dt.good_proj}" /></td>
					<td class="text-right"><c:out value="${dt.avg_proj}" /></td>
					<td class="text-right"><c:out value="${dt.satisfactory_proj}" /></td>
					
				</tr>
				<c:set var = "totdist" 
  				value = "${totdist + dt.totaldist}" />
 				<c:set var = "totproj" 
  				value = "${totproj + dt.total_project}" />  
 				<c:set var = "totalexc"  
  				value = "${totalexc + dt.excelent_proj}" /> 
  				<c:set var = "totGood"  
  				value = "${totGood + dt.good_proj}" />  
  				<c:set var = "totAvg"  
 				value = "${totAvg + dt.avg_proj}" />
 				<c:set var = "totSatisf"  
 				value = "${totSatisf + dt.satisfactory_proj}" />

			</c:forEach>
			<c:if test="${gradeListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totdist}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totalexc}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totGood}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totAvg}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totSatisf}" /></b></td>
					
				</tr>
			</c:if>
			<c:if test="${gradeListSize==0}">
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