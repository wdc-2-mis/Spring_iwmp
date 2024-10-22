<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<head>
<title>Report O7-District wise Distribution of Area According to Crop Type as On Date</title>
<script type="text/javascript">

function downloadPDF(stcode, statename)
{
		document.getElementById("stcode").value=stcode;
		document.getElementById("statename").value=statename;
		document.getrpt.action="downloadDistWiseblcropPDF";
		document.getrpt.method="post";
		document.getrpt.submit();
	
} 

function exportExcel(stcode, statename)
{
		document.getElementById("stcode").value=stcode;
		document.getElementById("statename").value=statename;
		document.getrpt.action="downloadExcelDistblscrpwiseareaoutdtlrpt";
		document.getrpt.method="post";
		document.getrpt.submit();
	
}



</script>
</head>

<form action="downloadDistWiseblcropPDF" method="post" name="getrpt">
			<input type="hidden" name="stcode" id="stcode" value="" />
			<input type="hidden" name="statename" id="statename" value="" />
   			
</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>Report O7-District wise Distribution of Area According to Crop Type as On Date for State '<c:out value="${statename}"/>' </h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${statename}','${stcode}')"	class="btn btn-info">Excel</button>
  <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${statename}','${stcode}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="tblReport" class="table">
		<thead>
			<tr>
				<th class="text-right" colspan="21">All area in ha. </th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">District</th>
				<th rowspan="3" class="text-center">Total Project Area</th>
				<th rowspan="3" class="text-center">Plot Area</th>
				<th colspan="16" class="text-center">Crop Type</th>
			</tr>
			<tr>
				<th colspan="2" class="text-center">Cereals</th>
				<th colspan="2" class="text-center">Pulses</th>
				<th colspan="2" class="text-center">Oil seeds</th>
				<th colspan="2" class="text-center">Fiber Crops</th>
				<th colspan="2" class="text-center">Pastures</th>
				<th colspan="2" class="text-center">Horticultures</th>
				<th colspan="2" class="text-center">Plantation</th>
				<th colspan="2" class="text-center">Others</th>
			</tr>
			<tr>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
			</tr>

		</thead>

		<tbody id="stwiseAreaRptTbody">


			<c:forEach items="${crpListt}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
<%-- 					<td><a href="blsrptdist?id=<c:out value="${project.stcode}"/>"><c:out --%>
<%-- 								value="${project.stname}" /></a></td> --%>
					<td><c:out value="${project.dist_name}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.plot_area_ach}" /></td>
					<td class="text-right"><c:out value="${project.cereals_ach}" /></td>
					<td class="text-right"><c:out value="${project.cereals_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.pulses_ach}" /></td>
					<td class="text-right"><c:out value="${project.pulses_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.oilseeds_ach}" /></td>
					<td class="text-right"><c:out value="${project.oilseeds_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.fiber_crops_ach}" /></td>
					<td class="text-right"><c:out value="${project.fiber_crops_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.pasture_ach}" /></td>
					<td class="text-right"><c:out value="${project.pasture_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.horticulture_ach}" /></td>
					<td class="text-right"><c:out value="${project.horticulture_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.plantation_crops_ach}" /></td>
					<td class="text-right"><c:out value="${project.plantation_crops_prod_ach}" /></td>
					<td class="text-right"><c:out value="${project.other_crops_ach}" /></td>
					<td class="text-right"><c:out value="${project.other_crops_prod_ach}" /></td>
				</tr>
					<c:set var="totalproject_area"
					value="${totalproject_area+project.project_area}" />
					<c:set var="totalplot_area_ach"
					value="${totalplot_area_ach+project.plot_area_ach}" />
					<c:set var="totalcereals_ach"
					value="${totalcereals_ach+project.cereals_ach}" />
					<c:set var="totalcereals_prod_ach"
					value="${totalcereals_prod_ach+project.cereals_prod_ach}" />
					<c:set var="totalpulses_ach"
					value="${totalpulses_ach+project.pulses_ach}" />
					<c:set var="totalpulses_prod_ach"
					value="${totalpulses_prod_ach+project.pulses_prod_ach}" />
					<c:set var="totaloilseeds_ach"
					value="${totaloilseeds_ach+project.oilseeds_ach}" />
					<c:set var="totaloilseeds_prod_ach"
					value="${totaloilseeds_prod_ach+project.oilseeds_prod_ach}" />
					<c:set var="totalfiber_crops_ach"
					value="${totalfiber_crops_ach+project.fiber_crops_ach}" />
					<c:set var="totalfiber_crops_prod_ach"
					value="${totalfiber_crops_prod_ach+project.fiber_crops_prod_ach}" />
					<c:set var="totalpasture_ach"
					value="${totalpasture_ach+project.pasture_ach}" />
					<c:set var="totalpasture_prod_ach"
					value="${totalpasture_prod_ach+project.pasture_prod_ach}" />
					<c:set var="totalhorticulture_ach"
					value="${totalhorticulture_ach+project.horticulture_ach}" />
					<c:set var="totalhorticulture_prod_ach"
					value="${totalhorticulture_prod_ach+project.horticulture_prod_ach}" />
					<c:set var="totalplantation_crops_ach"
					value="${totalplantation_crops_ach+project.plantation_crops_ach}" />
					<c:set var="totalplantation_crops_prod_ach"
					value="${totalplantation_crops_prod_ach+project.plantation_crops_prod_ach}" />
					<c:set var="totalother_crops_ach"
					value="${totalother_crops_ach+project.other_crops_ach}" />
					<c:set var="totalother_crops_prod_ach"
					value="${totalother_crops_prod_ach+project.other_crops_prod_ach}" />

			</c:forEach>

				<tr>
				<td  align="right" colspan="2" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproject_area}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplot_area_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcereals_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcereals_prod_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpulses_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpulses_prod_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totaloilseeds_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloilseeds_prod_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalfiber_crops_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalfiber_crops_prod_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpasture_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpasture_prod_ach}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalhorticulture_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalhorticulture_prod_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplantation_crops_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplantation_crops_prod_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalother_crops_ach}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalother_crops_prod_ach}"/></b></td> 
				
		</tbody>
	</table>
</div>