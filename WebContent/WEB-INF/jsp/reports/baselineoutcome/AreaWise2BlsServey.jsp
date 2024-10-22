<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<title>
Report O8-State wise Comparison of Distribution of Total Area as Per 
		Classification of Land and No of Crops as on Date with Baseline
</title>
<script type="text/javascript">

function downloadPDF()
{
		
		document.getreport.action="downloadblsurveyDetailPDF";
		document.getreport.method="post";
		document.getreport.submit();
	
} 

function exportExcel()
{
		
		document.getreport.action="downloadExcelbslserveydetailreport";
		document.getreport.method="post";
		document.getreport.submit();
	
} 

</script>
</head>

<form action="downloadblsurveyDetailPDF" method="post" name="getreport"></form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>Report O8-State wise Comparison of Distribution of Total Area as Per 
		Classification of Land and No. of Crops as on Date with Baseline</h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()"	class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()"	class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	
	</div>

	<table id="tblReport" class="table">
		<thead>
			<tr>
				<th class="text-right" colspan="24">All area in ha. </th>
			</tr>
			<tr>
				<th rowspan="4" class="text-center">S.No.</th>
				<th rowspan="4" class="text-center">State</th>
				<th rowspan="4" class="text-center">Total Project Area</th>
				<th rowspan="4" class="text-center">Plot Area</th>
				<th colspan="12" class="text-center">Classification of Land(Area)</th>
				<th colspan="8" class="text-center">No of Crops(Area)</th>
			</tr>
			<tr>
				<th rowspan="2" colspan="2" class="text-center">Cultural Wasteland</th>
				<th rowspan="2" colspan="2" class="text-center">Degraded Land</th>
				<th rowspan="2" colspan="2" class="text-center">Rainfed Land</th>
				<th colspan="4" class="text-center">Forest Land</th>
				<th rowspan="2" colspan="2" class="text-center">Others Classification</th>
				<th rowspan="2" colspan="2" class="text-center">No Crops</th>
				<th rowspan="2" colspan="2" class="text-center">Single Crops</th>
				<th rowspan="2" colspan="2" class="text-center">Double Crops</th>
				<th rowspan="2" colspan="2" class="text-center">Multiple Crops</th>
			</tr>
			<tr>
				<th colspan="2" class="text-center">Plantation</th>
				<th colspan="2" class="text-center">No Plantation</th>
			</tr>
			
			<tr>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
				<th class="text-center">As on Baseline</th>
				<th class="text-center">As on date</th>
			</tr>

		</thead>

		<tbody id="statewiseAreaRptTbody">

			<c:forEach items="${statewiseAreaDtlList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
 					<td><a href="distbslserveydetailreport?stcd=${project.stcode}&stName=${project.stname}"><c:out value="${project.stname}" /></a></td> 
<%-- 					<td><c:out value="${project.stname}" /></td> --%>
					
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.plot_area}" /></td>
					<td class="text-right"><c:out value="${project.cultivable_wasteland}" /></td>
					<td class="text-right"><c:out value="${project.cultivable_wasteland_ach}" /></td>  
 					<td class="text-right"><c:out value="${project.degraded}" /></td>
 					<td class="text-right"><c:out value="${project.degraded_ach}" /></td> 
 					<td class="text-right"><c:out value="${project.rainfed}" /></td> 
 					<td class="text-right"><c:out value="${project.rainfed_ach}" /></td> 
					<td class="text-right"><c:out value="${project.plantation}" /></td> 
					<td class="text-right"><c:out value="${project.plantation_ach}" /></td> 
					<td class="text-right"><c:out value="${project.no_plantation}" /></td>
					<td class="text-right"><c:out value="${project.no_plantation_ach}" /></td> 
					<td class="text-right"><c:out value="${project.others_classification}" /></td> 
 					<td class="text-right"><c:out value="${project.others_classification_ach}" /></td> 
			 		<td class="text-right"><c:out value="${project.no_crop}" /></td>
			 		<td class="text-right"><c:out value="${project.no_crop_ach}" /></td>  
					<td class="text-right"><c:out value="${project.single_crop}" /></td> 
					<td class="text-right"><c:out value="${project.single_crop_ach}" /></td>
 					<td class="text-right"><c:out value="${project.double_crop}" /></td> 
 					<td class="text-right"><c:out value="${project.double_crop_ach}" /></td> 
 					<td class="text-right"><c:out value="${project.multiple_crop}" /></td> 
 					<td class="text-right"><c:out value="${project.multiple_crop_ach}" /></td> 
 					
				</tr>
					<c:set var="totalproject_area"
 					value="${totalproject_area+project.project_area}" />
					<c:set var="totalplot_area"
 					value="${totalplot_area+project.plot_area}" /> 
					<c:set var="totalcultivable_wasteland"
					value="${totalcultivable_wasteland+project.cultivable_wasteland}" /> 
					<c:set var="totalcultivable_wasteland_ach"
					value="${totalcultivable_wasteland_ach+project.cultivable_wasteland_ach}" />
					<c:set var="totaldegraded"
					value="${totaldegraded+project.degraded}" />
					<c:set var="totaldegraded_ach"
					value="${totaldegraded_ach+project.degraded_ach}" />
					<c:set var="totalrainfed"
					value="${totalrainfed+project.rainfed}" />
					<c:set var="totalrainfed_ach"
					value="${totalrainfed_ach+project.rainfed_ach}" />
					<c:set var="totalplantation"
					value="${totalplantation+project.plantation}" />
					<c:set var="totalplantation_ach"
					value="${totalplantation_ach+project.plantation_ach}" />
					<c:set var="totalno_plantation"
					value="${totalno_plantation+project.no_plantation}" />
					<c:set var="totalno_plantation_ach"
					value="${totalno_plantation_ach+project.no_plantation_ach}" />
					<c:set var="totalothers_classification"
					value="${totalothers_classification+project.others_classification}" />
					<c:set var="totalothers_classification_ach"
					value="${totalothers_classification_ach+project.others_classification_ach}" />
					<c:set var="totalno_crop"
					value="${totalno_crop+project.no_crop}" />
					<c:set var="totalno_crop_ach"
					value="${totalno_crop_ach+project.no_crop_ach}" />
					<c:set var="totalsingle_crop"
					value="${totalsingle_crop+project.single_crop}" />
					<c:set var="totalsingle_crop_ach"
					value="${totalsingle_crop_ach+project.single_crop_ach}" />
					<c:set var="totaldouble_crop"
					value="${totaldouble_crop+project.double_crop}" />
					<c:set var="totaldouble_crop_ach"
					value="${totaldouble_crop_ach+project.double_crop_ach}" />
					<c:set var="totalmultiple_crop"
					value="${totalmultiple_crop+project.multiple_crop}" />
					<c:set var="totalmultiple_crop_ach"
					value="${totalmultiple_crop_ach+project.multiple_crop_ach}" />
					
					
				</c:forEach>
				
				<tr>
				<td  colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproject_area}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplot_area}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcultivable_wasteland}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcultivable_wasteland_ach}"/></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaldegraded}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaldegraded_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalrainfed}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalrainfed_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplantation}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplantation_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalno_plantation}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalno_plantation_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalothers_classification}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalothers_classification_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalno_crop}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalno_crop_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalsingle_crop}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalsingle_crop_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totaldouble_crop}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaldouble_crop_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalmultiple_crop}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalmultiple_crop_ach}" /></b></td>

				</tr>
		</tbody>
	</table>
</div>