<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<title>Report ME6- District wise Current Status of OOMF Project Activities</title>
<script type="text/javascript">
function exportExcel(stcd,stName)
{
document.getElementById("stcd").value=stcd;
document.getElementById("stName").value=stName;
document.OOMFProjectActivities.action="getOOMFCurrentStatusDistrictReportExcel";
document.OOMFProjectActivities.method="post";
document.OOMFProjectActivities.submit();
}

function downloadPDF(stcd,stName)
{
document.getElementById("stcd").value=stcd;
document.getElementById("stName").value=stName;
document.OOMFProjectActivities.action="getOOMFCurrentStatusDistrictReportPDF";
document.OOMFProjectActivities.method="post";
document.OOMFProjectActivities.submit();

}

</script>

<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report ME6- District and Activities wise Project Achievement Status for the Financial Year '<c:out value='${finyr}' />' and Month '<c:out value=' ${month}' />' for State '<c:out value=' ${stName}' />' </label></h5></div>
<br>
<div class ="card">


<div class="row">
<div class="col-1" ></div>
<div class="col-10" >
<form action="getOOMFCurrentStatusReport" method="post" id="OOMFProjectActivities" name="OOMFProjectActivities">
<input type="hidden" name="stcd" id="stcd" value="" />
<input type="hidden" name="stName" id="stName" value="" />

<div class="form-row">
</div>
</form>

<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${stName}')" class="btn btn-info">Excel</button>  
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}', '${stName}')" class="btn btn-info">PDF</button>  
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="dtBasicExample" cellspacing="0" class="table" >
  <thead>
    <tr>
      <th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      <th style="text-align:center; vertical-align: middle; width: 20%;">District Name</th>
      <th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project</th>
      <th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Area of Degraded Land Covered/Rainfed Area Developed</th>
      <th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Area covered with soil and moisture conservation activities</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Area Brought under Plantation (Afforestation/Horticulture)</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted No. of Water Harvesting Structure (created/renovated)</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted No. of Farmers Benefited</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Area Brought under Protective Irrigation (created/renovated)</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted No. of man-days generated</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Half Year Wise Additional area brought under diversified crops/change in cropping system</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Half Year Wise  Area brought from no crop/single crop to single/multiple crop</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Year Wise Increase in Cropped Area</th>
<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project submitted Increase in Farmer`s Income </th>
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
<th class="text-center">13</th>
<th class="text-center">14</th>

</tr>
<c:set var="count" value="1" />

<c:if test="${dataListSize>0}">
<c:forEach items="${dataList}" var="data" varStatus="count">
<tr>
<td><c:out value='${count.count}' /></td>
<td><a href="getProjOOMFCurrentStatusReport?dcode=<c:out value='${data.dcode}' />&stName=<c:out value='${stName}' />&distName=${data.dist_name}" > <c:out value="${data.dist_name}" /></a></td>
<%--   <td> <c:out value="${data.dist_name}" /></td> --%>
  <td class="text-right"> <c:out value="${data.totalproject}" /></td>
  <td class="text-right"> <c:out value="${data.degraded_land_proj_no}" /></td>
  <td class="text-right"> <c:out value="${data.soilmoisture_proj_no}" /></td>
  <td class="text-right"> <c:out value="${data.afforestation_horticulture_proj_no}" /></td>
<td class="text-right"> <c:out value="${data.water_harvest_proj_no}" /></td>
<td class="text-right"> <c:out value="${data.farmer_benefitte_proj_no}" /></td>
<td class="text-right"> <c:out value="${data.protective_irrigation_proj_no}" /></td>
<td class="text-right"> <c:out value="${data.mandays_generated_proj_no}" /></td>

<td class="text-right"> <c:out value="${data.halfyearfill}" /></td>
<td class="text-right"> <c:out value="${data.halfyearfill}" /></td>
<td class="text-right"> <c:out value="${data.yearwisefill}" /></td>
<td class="text-right"> <c:out value="${data.yearwisefill}" /></td>

  </tr>
</c:forEach>
</c:if>

<tr>
<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td>
<td align="right" class="table-primary" ><b><c:out value='${totalproject1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${degraded_land_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${soilmoisture_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${afforestation_horticulture_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${water_harvest_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${farmer_benefitte_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${protective_irrigation_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${mandays_generated_proj_no1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${halfyearfill1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${halfyearfill1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${yearwisefill1}' /> </b></td>
<td align="right" class="table-primary" ><b><c:out value='${yearwisefill1}' /> </b></td>
</tr>

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