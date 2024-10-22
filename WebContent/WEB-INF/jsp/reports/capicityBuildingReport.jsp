<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report CB1- State-wise and Year-wise number of  Training Conducted and Persons Trained At SLNA/WCDC/PIA/WC Level</title>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>
<script src='<c:url value="/resources/js/trainingCapicityBuilding.js" />'></script>
<script type="text/javascript">

function downloadPDF()
{
		
		document.getcpreport.action="downloadCapacityBuildingReportPDF";
		document.getcpreport.method="post";
		document.getcpreport.submit();
	
} 

function exportExcel()
{
		
		document.getcpreport.action="downloadExcelcapicityBuildingReport";
		document.getcpreport.method="post";
		document.getcpreport.submit();
	
}



</script>
</head>

<form action="downloadCapacityBuildingReportPDF" method="post" name="getcpreport"></form>
<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report CB1- State-wise and Year-wise number of  Training Conducted and Persons Trained At SLNA/WCDC/PIA/WC Level</h5></div>

<br>
<div class ="card">

	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

<!-- <button name="exportPDF"  onclick="javascript:generatePDF()" class="btn btn-info">PDF</button> -->

<table id="example" cellspacing="0" class="table"   width="auto">
  <thead>
	
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Financial Year</th>
      <th style="text-align:center" colspan="2">SLNA</th>
      <th style="text-align:center" colspan="2">WCDC</th>
      <th style="text-align:center" colspan="2">PIA</th>
      <th style="text-align:center" colspan="2">WC</th>
      <th style="text-align:center" colspan="2">Total</th>
     </tr>
     <tr> 
      	<th style="text-align:center">Total no. of Training conducted</th>
		<th style="text-align:center">Total no. of Persons trained</th>
		<th style="text-align:center">Total no. of Training conducted</th>
		<th style="text-align:center">Total no. of Persons trained</th>
		<th style="text-align:center">Total no. of Training conducted</th>
		<th style="text-align:center">Total no. of Persons trained</th>
		<th style="text-align:center">Total no. of Training conducted</th>
		<th style="text-align:center">Total no. of Persons trained</th>
		<th style="text-align:center">Total no. of Training conducted (4+6+8+10)</th>
		<th style="text-align:center">Total no. of Persons trained (5+7+9+11)</th>
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
	</tr>
	
	 <c:if test="${dataList != null}">
		<c:set var="stname" value="" />
		<c:set var="count" value="1" />
		<c:forEach items="${dataList}" var="dataV" varStatus="status">
		<tr>	
			<%-- <td><c:out value='${status.count}' /></td>	 --%>
			<c:choose>
				<c:when test="${stname ne dataV.statename}">
					<c:set var="stname" value="${dataV.statename}" />
					<td><c:out value='${count}' /></td>
					<td><c:out value='${stname}' /></td>
					<c:set var="count" value="${count+1}" />
				</c:when>	
				<c:otherwise>
					<td></td>
					<td></td>
				</c:otherwise>
			</c:choose>
			<td align="center"><c:out value='${dataV.finyear}' /></td>
			<td align="right"><c:out value='${dataV.sl_tcond}' /></td>
			<td align="right"><c:out value='${dataV.sl_tperson}' /></td>
			<td align="right"><c:out value='${dataV.di_tcond}' /></td>
			<td align="right"><c:out value='${dataV.di_tperson}' /></td>
			<td align="right"><c:out value='${dataV.pi_tcond}' /></td>
			<td align="right"><c:out value='${dataV.pi_tperson}' /></td>
			<td align="right"><c:out value='${dataV.wc_tcond}' /></td>
			<td align="right"><c:out value='${dataV.wc_tperson}' /></td>
			<td align="right"><c:out value='${dataV.totalcond}' /></td>
			<td align="right"><c:out value='${dataV.totalperson}' /></td>
			
		</tr>		
		</c:forEach>
		<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
		<tr>
			<td  align="right" class="table-primary" colspan="3"><b>Grand Total </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[7]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[8]}' /> </b></td>
			<td  align="right" class="table-primary" ><b><c:out value='${netTotal[9]}' /> </b></td>
		</tr>
		</c:forEach>
		
    </c:if> 
    	<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="13" class="required">Data Not Found</td>
			</tr>
		</c:if>
    
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>