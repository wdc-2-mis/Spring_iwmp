<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	<head>
		<title>
		Report O8- District wise Comparison of Distribution of Total Area as Per 
		Classification of Land and No of Crops as on Date with Baseline
		</title>
	
<script type="text/javascript">

function downloadPDF(stcd, stName)
{
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	document.getreport.action="downloadPDFbslserveydetail";
	document.getreport.method="post";
	document.getreport.submit();
} 

function exportExcel(stcd, stName)
{
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	document.getreport.action="downloadExcelbslserveydetail";
	document.getreport.method="post";
	document.getreport.submit();
} 

</script>
</head>

<form:form action="distbslserveydetailreport" method="get" name="getreport">
	<input type="hidden" name="stcd" id="stcd" value="" />
	<input type="hidden" name="stName" id="stName" value="" />

</form:form>

<div class="container-fluid">
	
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>Report O8- District wise Comparison of Distribution of Total Area as Per 
		Classification of Land and No. of Crops as on Date with Baseline</h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${stName}')"	class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}', '${stName}')"	class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	
</div>

<table id="tblReport" class="table">
	<thead>
		<tr>
			<th class="text-left" colspan="12">State :  ${stName}</th>
			<th class="text-right" colspan="12">All area in ha. </th>
		</tr>
		<tr>
			<th rowspan="4" class="text-center">S.No.</th>
			<th rowspan="4" class="text-center">District</th>
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

	<tbody id="distwiseAreaRptTbody">
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
			<th class="text-center">13</th>
			<th class="text-center">14</th>
			<th class="text-center">15</th>
			<th class="text-center">16</th>
			<th class="text-center">17</th>
			<th class="text-center">18</th>
			<th class="text-center">19</th>
			<th class="text-center">20</th>
			<th class="text-center">21</th>
			<th class="text-center">22</th>
			<th class="text-center">23</th>
			<th class="text-center">24</th>
		</tr>

		<c:forEach items="${DistWiseAreaDtlList}" var="dtl" varStatus="sno">
			<tr>
				<td class="text-center"><c:out value="${sno.count}" /></td>
				<td><c:out value="${dtl.dist_name}" /></td>
				<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${dtl.project_area}" /></fmt:formatNumber></td>
				<td class="text-right"><c:out value="${dtl.plot_area}" /></td>
				<td class="text-right"><c:out value="${dtl.cultivable_wasteland}" /></td>
				<td class="text-right"><c:out value="${dtl.cultivable_wasteland_ach}" /></td>  
 				<td class="text-right"><c:out value="${dtl.degraded}" /></td>
 				<td class="text-right"><c:out value="${dtl.degraded_ach}" /></td> 
 				<td class="text-right"><c:out value="${dtl.rainfed}" /></td> 
 				<td class="text-right"><c:out value="${dtl.rainfed_ach}" /></td> 
				<td class="text-right"><c:out value="${dtl.plantation}" /></td> 
				<td class="text-right"><c:out value="${dtl.plantation_ach}" /></td> 
				<td class="text-right"><c:out value="${dtl.no_plantation}" /></td>
				<td class="text-right"><c:out value="${dtl.no_plantation_ach}" /></td> 
				<td class="text-right"><c:out value="${dtl.others_classification}" /></td> 
 				<td class="text-right"><c:out value="${dtl.others_classification_ach}" /></td> 
			 	<td class="text-right"><c:out value="${dtl.no_crop}" /></td>
			 	<td class="text-right"><c:out value="${dtl.no_crop_ach}" /></td>  
				<td class="text-right"><c:out value="${dtl.single_crop}" /></td> 
				<td class="text-right"><c:out value="${dtl.single_crop_ach}" /></td>
 				<td class="text-right"><c:out value="${dtl.double_crop}" /></td> 
 				<td class="text-right"><c:out value="${dtl.double_crop_ach}" /></td> 
 				<td class="text-right"><c:out value="${dtl.multiple_crop}" /></td> 
 				<td class="text-right"><c:out value="${dtl.multiple_crop_ach}" /></td> 
 			</tr> 
 			
			<c:set var="totalproject_area"
 			value="${totalproject_area+dtl.project_area}" />
			<c:set var="totalplot_area"
 			value="${totalplot_area+dtl.plot_area}" /> 
			<c:set var="totalcultivable_wasteland"
			value="${totalcultivable_wasteland+dtl.cultivable_wasteland}" /> 
			<c:set var="totalcultivable_wasteland_ach"
			value="${totalcultivable_wasteland_ach+dtl.cultivable_wasteland_ach}" />
			<c:set var="totaldegraded"
			value="${totaldegraded+dtl.degraded}" />
			<c:set var="totaldegraded_ach"
			value="${totaldegraded_ach+dtl.degraded_ach}" />
			<c:set var="totalrainfed"
			value="${totalrainfed+dtl.rainfed}" />
			<c:set var="totalrainfed_ach"
			value="${totalrainfed_ach+dtl.rainfed_ach}" />
			<c:set var="totalplantation"
			value="${totalplantation+dtl.plantation}" />
			<c:set var="totalplantation_ach"
			value="${totalplantation_ach+dtl.plantation_ach}" />
			<c:set var="totalno_plantation"
			value="${totalno_plantation+dtl.no_plantation}" />
			<c:set var="totalno_plantation_ach"
			value="${totalno_plantation_ach+dtl.no_plantation_ach}" />
			<c:set var="totalothers_classification"
			value="${totalothers_classification+dtl.others_classification}" />
			<c:set var="totalothers_classification_ach"
			value="${totalothers_classification_ach+dtl.others_classification_ach}" />
			<c:set var="totalno_crop"
			value="${totalno_crop+dtl.no_crop}" />
			<c:set var="totalno_crop_ach"
			value="${totalno_crop_ach+dtl.no_crop_ach}" />
			<c:set var="totalsingle_crop"
			value="${totalsingle_crop+dtl.single_crop}" />
			<c:set var="totalsingle_crop_ach"
			value="${totalsingle_crop_ach+dtl.single_crop_ach}" />
			<c:set var="totaldouble_crop"
			value="${totaldouble_crop+dtl.double_crop}" />
			<c:set var="totaldouble_crop_ach"
			value="${totaldouble_crop_ach+dtl.double_crop_ach}" />
			<c:set var="totalmultiple_crop"
			value="${totalmultiple_crop+dtl.multiple_crop}" />
			<c:set var="totalmultiple_crop_ach"
			value="${totalmultiple_crop_ach+dtl.multiple_crop_ach}" />
					
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
				
		<c:if test="${DistWiseAreaDtlListSize==0}">
			<tr>
				<td align="center" colspan="24" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
		
	</tbody>
</table>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</html>