<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE7-  State-wise Mid Term Evaluation of Mandays Details</title>

<script type="text/javascript">

function downloadPDF(){
		document.getmandays.action="downloadMandaysDetailsReportPdf";
		document.getmandays.method="post";
		document.getmandays.submit();
}

function exportExcel(){
		document.getmandays.action="downloadExcelMandaysDetailsReport";
		document.getmandays.method="post";
		document.getmandays.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE7-  State-wise Mid Term Evaluation of Mandays Details</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getConWorksDetails" name="getmandays" id="getmandays" method="get">

		
 	</form:form>
 
<br>
	<c:if test="${not empty mList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="3">S.No.</th>
            <th rowspan="3">State Name</th>
            <th rowspan="3">Total No. of Project</th>
            <th colspan="3" style="text-align: center">Farmer`s Average Household Income per Annum (Rs. in Lakhs)</th>
            <th colspan="2" style="text-align: center">No. of Farmers Benefited</th>
            <th colspan="2" style="text-align: center">No. of Persondays Generated (man-days)</th>
        </tr>
        <tr>
            <th rowspan="2">Pre - Project Status(Aggregate)</th>
            <th rowspan="2">Mid - Project Status(Aggregate)</th>
            <th rowspan="2">Controlled Area</th>
            <th>Project Area</th>
            <th>Controlled Area</th>
            <th>Project Area</th>
            <th>Controlled Area</th>
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
			</tr>
			<c:forEach items="${mList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
<%-- 					<td class="text-left"><c:out value="${dt.st_name}" /></td> --%>
					<td><a href = "getDistMandaysDetailsReport?stcd=${dt.st_code}&stName=${dt.st_name}"><c:out value='${dt.st_name}'/></a></td>
					<td class="text-right"><c:out value="${dt.total_project}" /></td>
					<td class="text-right"><c:out value="${dt.pre_farmer_income}" /></td>
					<td class="text-right"><c:out value="${dt.mid_farmer_income}" /></td>
					<td class="text-right"><c:out value="${dt.control_farmer_income}" /></td>
					<td class="text-right"><c:out value="${dt.farmer_benefited}" /></td>
					<td class="text-right"><c:out value="N/A" /></td>
					<td class="text-right"><c:out value="${dt.mandays_generated}" /></td>
					<td class="text-right"><c:out value="N/A" /></td>
					
				</tr>
				
 				<c:set var = "totproj" 
  				value = "${totproj + dt.total_project}" />  
 				<c:set var = "prefarmerincome"  
  				value = "${prefarmerincome + dt.pre_farmer_income}" /> 
  				<c:set var = "midfarmerincome"  
  				value = "${midfarmerincome + dt.mid_farmer_income}" />  
  				<c:set var = "controlfarmerincome"  
  				value = "${controlfarmerincome + dt.control_farmer_income}" /> 
  				<c:set var = "farmerbenefited"  
 				value = "${farmerbenefited + dt.farmer_benefited}" />
 				<c:set var = "controlfarmerbenefited"  
 				value = "${controlfarmerbenefited + dt.control_farmer_benefited}" />
 				<c:set var = "mandaysgenerated"  
 				value = "${mandaysgenerated + dt.mandays_generated}" />
 				<c:set var = "controlmandaysgenerated"  
 				value = "${controlmandaysgenerated + dt.control_mandays_generated}" />

			</c:forEach>
			<c:if test="${mListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${prefarmerincome}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${midfarmerincome}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${controlfarmerincome}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${farmerbenefited}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="N/A" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${mandaysgenerated}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="N/A" /></b></td>
					
				</tr>
			</c:if>
			<c:if test="${mListSize==0}">
				<tr>
					<td align="center" colspan="10" class="required" style="color:red;"><b>Data Not Found</b></td>
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