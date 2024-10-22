<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<title>Report O5- District wise Comparison of Distribution of Total Area as Per Irrigation Status and Ownership Status as On Date with Baseline</title>
<script type="text/javascript">

function downloadPDF(statename,stcd)
{
		document.getElementById("statename").value=statename;
		document.getElementById("stcd").value=stcd;
		document.getreports.action="getDistAreaWiseblserveyPDF";
		document.getreports.method="post";
		document.getreports.submit();
} 
function exportExcel(statename,stcd)
{
		document.getElementById("statename").value=statename;
		document.getElementById("stcd").value=stcd;
		document.getreports.action="getDistAreaWiseblserveyExcel";
		document.getreports.method="post";
		document.getreports.submit();
} 

</script>
</head>

<form action="downloadAreablsPDF" method="post" name="getreports">
	<input type="hidden" name="statename" id="statename" value="" />
	<input type="hidden" name="stcd" id="stcd" value="" />

</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>Report O5- District wise Comparison of Distribution of Total Area as Per Irrigation Status and Ownership Status as On Date with Baseline</h5>
	</div>
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stname}','${stcd}')" class="btn btn-info">Excel</button>
 	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stname}','${stcd}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	
	</div>

	<table id="tblReport" class="table">
		<thead>
			<tr>
				<th class="text-left" colspan="9">State : &nbsp; <c:out value="${stname}" /> </th>
				<th class="text-right" colspan="9">All area in ha. </th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">District Name</th>
				<th rowspan="3" class="text-center">Total Project Area</th>
				<th rowspan="3" class="text-center">Plot Area</th>
				<th colspan="6" class="text-center">Irrigation Status(Area)</th>
				<th colspan="8" class="text-center">Ownership(Area)</th>
<!-- 				<th colspan="6" class="text-center">Classification of Land(Area)</th> -->
<!-- 				<th colspan="4" class="text-center">No. of Crops(Area)</th> -->
			</tr>
			<tr>
				<th colspan="2" class="text-center">Protective Irrigation</th>
				<th colspan="2" class="text-center">Assured Irrigation</th>
				<th colspan="2" class="text-center">No Irrigation</th>
				<th colspan="2" class="text-center">Private</th>
				<th colspan="2" class="text-center">Government</th>
				<th colspan="2" class="text-center">Community</th>
				<th colspan="2" class="text-center">Others</th>
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
			</tr>

		</thead>

		<tbody id="statewiseAreaRptTbody">


			<c:forEach items="${statewiseAreaDtlList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
 					<%-- <td><a href="getDistAreaWiseblservey?id=<c:out value="${project.stcode}"/>"><c:out value="${project.distname}" /></a></td>  --%>
					<td><c:out value="${project.distname}" /></td>
					
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.plot_area}" /></td>
					<td class="text-right"><c:out value="${project.protective_irrigation}" /></td>
					<td class="text-right"><c:out value="${project.protective_irrigation_ach}" /></td>
					<td class="text-right"><c:out value="${project.assured_irrigation}" /></td>
					<td class="text-right"><c:out value="${project.assured_irrigation_ach}" /></td>
					<td class="text-right"><c:out value="${project.no_irrigation}" /></td>
					<td class="text-right"><c:out value="${project.no_irrigation_ach}" /></td>
					<td class="text-right"><c:out value="${project.private_owner}" /></td>
					<td class="text-right"><c:out value="${project.private_owner_ach}" /></td>
					<td class="text-right"><c:out value="${project.govt_owned}" /></td>
					<td class="text-right"><c:out value="${project.govt_owned_ach}" /></td>
					<td class="text-right"><c:out value="${project.community_owned}" /></td>
					<td class="text-right"><c:out value="${project.community_owned_ach}" /></td>
					<td class="text-right"><c:out value="${project.others_owned}" /></td>
					<td class="text-right"><c:out value="${project.others_owned_ach}" /></td>

				</tr>
					<c:set var="totalproject_area"
 					value="${totalproject_area+project.project_area}" />
					<c:set var="totalplot_area"
 					value="${totalplot_area+project.plot_area}" /> 
					<c:set var="totalprotective_irrigation"
					value="${totalprotective_irrigation+project.protective_irrigation}" /> 
					<c:set var="totalprotective_irrigation_ach"
					value="${totalprotective_irrigation_ach+project.protective_irrigation_ach}" />
					<c:set var="totalassured_irrigation"
					value="${totalassured_irrigation+project.assured_irrigation}" />
					<c:set var="totalassured_irrigation_ach"
					value="${totalassured_irrigation_ach+project.assured_irrigation_ach}" />
					<c:set var="totalno_irrigation"
					value="${totalno_irrigation+project.no_irrigation}" />
					<c:set var="totalno_irrigation_ach"
					value="${totalno_irrigation_ach+project.no_irrigation_ach}" />
					<c:set var="totalprivate_owner"
					value="${totalprivate_owner+project.private_owner}" />
					<c:set var="totalprivate_owner_ach"
					value="${totalprivate_owner_ach+project.private_owner_ach}" />
					<c:set var="totalgovt_owned"
					value="${totalgovt_owned+project.govt_owned}" />
					<c:set var="totalgovt_owned_ach"
					value="${totalgovt_owned_ach+project.govt_owned_ach}" />
					<c:set var="totalcommunity_owned"
					value="${totalcommunity_owned+project.community_owned}" />
					<c:set var="totalcommunity_owned_ach"
					value="${totalcommunity_owned_ach+project.community_owned_ach}" />
					<c:set var="totalothers_owned"
					value="${totalothers_owned+project.others_owned}" />
					<c:set var="totalothers_owned_ach"
					value="${totalothers_owned_ach+project.others_owned_ach}" />
					
					
					
				</c:forEach>
				
				<tr>
				<td  align="right" colspan="2" class="table-primary "><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproject_area}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplot_area}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalprotective_irrigation}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalprotective_irrigation_ach}"/></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalassured_irrigation}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalassured_irrigation_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalno_irrigation}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalno_irrigation_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalprivate_owner}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalprivate_owner_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalgovt_owned}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalgovt_owned_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalcommunity_owned}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcommunity_owned_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalothers_owned}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalothers_owned_ach}" /></b></td>

				</tr>


		</tbody>
	</table>
</div>