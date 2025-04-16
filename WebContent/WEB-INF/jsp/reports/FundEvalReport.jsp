<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE2-  State-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization</title>

<script type="text/javascript">

function downloadPDF(){
		document.getfundutl.action="downloadFundUtilizationEvalReportPdf";
		document.getfundutl.method="post";
		document.getfundutl.submit();
}

function exportExcel(){
		document.getfundutl.action="downloadExcelEnteredConWorks";
		document.getfundutl.method="post";
		document.getfundutl.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE2-  State-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization</label>
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
	<c:if test="${not empty fundList}">
<!-- 	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="4">S.No.</th>
            <th rowspan="4">State Name</th>
            <th rowspan="4">Total No. of Projects</th>
            <th rowspan="4">Sanctioned Project Area  (in ha.)</th>
            <th colspan="3" style="text-align: center">Sanctioned Cost</th>
            <th colspan="4" style="text-align: center">Fund Utilization</th>
        </tr>
        <tr>
            <th rowspan="2">Central Share (Rs. Crores)</th>
            <th rowspan="2">State Share (Rs. Crores)</th>
            <th rowspan="2">Total Share (Rs. Crores)</th>
            <th colspan="3" style="text-align: center">Fund Received</th>
            <th rowspan="3">Total Expenditure  (Rs. Crores)</th>
        </tr>
        <tr>
            <th>Central Share (Rs. Crores)</th>
            <th>State Share (Rs. Crores)</th>
            <th>Total Share (Rs. Crores)</th>
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
				<th class="text-center">9</th>
				<th class="text-center">10</th>
				<th class="text-center">11</th>
			</tr>
			<c:forEach items="${fundList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.st_name}" /></td>
					<td class="text-right"><c:out value="${dt.total_project}" /></td>
					<td class="text-right"><c:out value="${dt.total_project_area}" /></td>
					<td class="text-right"><c:out value="${dt.total_evaluation_central_share}" /></td>
					<td class="text-right"><c:out value="${dt.total_evaluation_state_share}" /></td>
					<td class="text-right"><c:out value="${dt.total_share_evaluation}" /></td>
					<td class="text-right"><c:out value="${dt.total_fund_central_share}" /></td>
					<td class="text-right"><c:out value="${dt.total_fund_state_share}" /></td>
					<td class="text-right"><c:out value="${dt.total_fund}" /></td>
					<td class="text-right"><c:out value="${dt.total_expenditure}" /></td>
					
				</tr>
				
 				<c:set var = "totproj" 
  				value = "${totproj + dt.total_project}" />  
 				<c:set var = "totalprojectarea"  
  				value = "${totalprojectarea + dt.total_project_area}" /> 
  				<c:set var = "centE"  
  				value = "${centE + dt.total_evaluation_central_share}" />  
  				<c:set var = "stateE"  
  				value = "${stateE + dt.total_evaluation_state_share}" />  
  				<c:set var = "totshare"  
 				value = "${totshare + dt.total_share_evaluation}" />
 				<c:set var = "centF"  
 				value = "${centF + dt.total_fund_central_share}" />
 				<c:set var = "stateF"  
 				value = "${stateF + dt.total_fund_state_share}" />
 				<c:set var = "totF"  
 				value = "${totF + dt.total_fund}" />
				<c:set var = "totExp"  
 				value = "${totExp + dt.total_expenditure}" />

			</c:forEach>
			<c:if test="${fundListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totalprojectarea}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${centE}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${stateE}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totshare}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${centF}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${stateF}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totF}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totExp}" /></b></td>
					
				</tr>
			</c:if>
			<c:if test="${fundListSize==0}">
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