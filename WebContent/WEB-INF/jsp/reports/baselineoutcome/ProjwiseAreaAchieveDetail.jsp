<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>

<head>
<title>Report O6- Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date</title>
</head>

<script type="text/javascript">
function downloadPDF(distName, dcode, statename)
{
		document.getElementById("distName").value=distName;
		document.getElementById("dcode").value=dcode;
		document.getElementById("statename").value=statename;
		document.getblsprpt.action="downloadProjWiseAreaAchvDetailPDF";
		document.getblsprpt.method="post";
		document.getblsprpt.submit();
} 
function exportExcel(distName, dcode, statename)
{
	document.getElementById("distName").value=distName;
	document.getElementById("statename").value=statename;
	document.getElementById("dcode").value=dcode;
	document.getblsprpt.action="downloadExcelProjAreaAchvDetail";
	document.getblsprpt.method="post";
	document.getblsprpt.submit();
}
	
	</script>
<form action="downloadProjWiseAreaAchvDetailPDF" method="post" name="getblsprpt">

			<input type="hidden" name="statename" id="statename" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
   			<input type="hidden" name="dcode" id="dcode" value="" />
    

</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report O6- Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date</label></h5>
	</div>
	 <button name="exportExcel" id="exportExcel" onclick="exportExcel('${distName}','${dcode}','${statename}')" class="btn btn-info">Excel</button>
	 <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${distName}','${dcode}','${statename}')"	class="btn btn-info">PDF</button> 
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="pdfBasicExample" class="table">
<!-- 	<tbody id="dtBasicExample"> -->
		<thead>
			<tr>
				<th align="left" colspan="10">State: &nbsp;<c:out value="${statename}" />&nbsp;&nbsp;District: &nbsp;<c:out value="${distName}" />  </th>
				<th class="text-right" colspan="11">All area in ha. </th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">Project Name</th>
				<th rowspan="3" class="text-center">Total Project Area</th>
				<th rowspan="3" class="text-center">Plot Area</th>
				<th colspan="3" class="text-center">Irrigation Status(Area)</th>
				<th colspan="4" class="text-center">Ownership(Area)</th>
				<th colspan="6" class="text-center">Classification of Land(Area)</th>
				<th colspan="4" class="text-center">No. of Crops(Area)</th>
			</tr>
			<tr>
				<th rowspan="2" class="text-center">Protective Irrigation</th>
				<th rowspan="2" class="text-center">Assured Irrigation</th>
				<th rowspan="2" class="text-center">No Irrigation</th>
				<th rowspan="2" class="text-center">Private</th>
				<th rowspan="2" class="text-center">Government</th>
				<th rowspan="2" class="text-center">Community</th>
				<th rowspan="2" class="text-center">Others</th>
				<th rowspan="2" class="text-center">Culturable Wasteland</th>
				<th rowspan="2" class="text-center">Degraded Land</th>
				<th rowspan="2" class="text-center">Rainfed Land</th>
				<th colspan="2" class="text-center">Forest Land</th>
				<th rowspan="2" class="text-center">Others</th>
				<th rowspan="2" class="text-center">No Crops</th>
				<th rowspan="2" class="text-center">Single Crops</th>
				<th rowspan="2" class="text-center">Double Crops</th>
				<th rowspan="2" class="text-center">Multiple Crops</th>
			</tr>
			<tr>
				<th class="text-center">Plantation</th>
				<th class="text-center">No Plantation</th>
			</tr>
			<tbody id="dtBasicExample">
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
			</tr>

			<c:forEach items="${projAchList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${project.proj_name}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.plot_area_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.protective_irrigation_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.assured_irrigation_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.no_irrigation_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.private_owner_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.govt_owned_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.community_owned_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.others_owned_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.cultivable_wasteland_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.degraded_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.rainfed_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.plantation_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.no_plantation_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.others_classification_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.no_crop_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.single_crop_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.double_crop_ach}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.multiple_crop_ach}" /></fmt:formatNumber></td>
				</tr>

					<c:set var="totalproject_area"
					value="${totalproject_area+project.project_area}" />
				<c:set var="totalplot_area"
					value="${totalplot_area+project.plot_area_ach}" />
				<c:set var="totalprotective_irrigation"
					value="${totalprotective_irrigation+project.protective_irrigation_ach}" />
				<c:set var="totalassured_irrigation"
					value="${totalassured_irrigation+project.assured_irrigation_ach}" />
					<c:set var="totalno_irrigation"
					value="${totalno_irrigation+project.no_irrigation_ach}" />
					<c:set var="totalprivate_owner"
					value="${totalprivate_owner+project.private_owner_ach}" />
					
					<c:set var="totalgovt_owned"
					value="${totalgovt_owned+project.govt_owned_ach}" />
				<c:set var="totalcommunity_owned"
					value="${totalcommunity_owned+project.community_owned_ach}" />
				<c:set var="totalothers_owned"
					value="${totalothers_owned+project.others_owned_ach}" />
				<c:set var="totalcultivable_wasteland"
					value="${totalcultivable_wasteland+project.cultivable_wasteland_ach}" />
					<c:set var="totaldegraded"
					value="${totaldegraded+project.degraded_ach}" />
					<c:set var="totalrainfed"
					value="${totalrainfed+project.rainfed_ach}" />
					
					<c:set var="totalplantation"
					value="${totalplantation+project.plantation_ach}" />
				<c:set var="totalno_plantation"
					value="${totalno_plantation+project.no_plantation_ach}" />
				<c:set var="totalothers_classification"
					value="${totalothers_classification+project.others_classification_ach}" />
				<c:set var="totalno_crop"
					value="${totalno_crop+project.no_crop_ach}" />
					<c:set var="totalsingle_crop"
					value="${totalsingle_crop+project.single_crop_ach}" />
					<c:set var="totaldouble_crop"
					value="${totaldouble_crop+project.double_crop_ach}" />
					<c:set var="totalmultiple_crop"
					value="${totalmultiple_crop+project.multiple_crop_ach}" />
					
				</c:forEach>
				
				<tr>
				<td class="table-primary"></td>
								
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproject_area}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalplot_area}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalprotective_irrigation}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalassured_irrigation}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalno_irrigation}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalprivate_owner}"/></fmt:formatNumber></b></td> 
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalgovt_owned}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcommunity_owned}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalothers_owned}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcultivable_wasteland}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totaldegraded}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalrainfed}"/></fmt:formatNumber></b></td> 
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalplantation}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalno_plantation}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalothers_classification}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalno_crop}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalsingle_crop}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totaldouble_crop}"/></fmt:formatNumber></b></td> 
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmultiple_crop}"/></fmt:formatNumber></b></td>
			</tr>

		</tbody>
	</table>
</div>

	<footer class="text-center">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</footer>
</body>

</html>