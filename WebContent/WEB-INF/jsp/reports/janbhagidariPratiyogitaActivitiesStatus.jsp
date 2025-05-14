<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<title>State wise Current Status of Watershed Janbhagidari Activities</title>
	<script type="text/javascript">
	function exportExcel(){
		document.getJanbhagidariActStatus.action="ExcelJanbhagidariStateWiseActivitiesStatus";
		document.getJanbhagidariActStatus.method="post";
		document.getJanbhagidariActStatus.submit();
	}
	
	function downloadPDF()
	{
			document.getJanbhagidariActStatus.action="JanbhagidariStatewiseActivitiesStatusPDF";
			document.getJanbhagidariActStatus.method="post";
			document.getJanbhagidariActStatus.submit();
		
	} 
	
	</script>

<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report JP3- State wise Watershed Janbhagidari Activities Details</label></h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10" >
	<form action="getJanbhagidariActivitiesStatus" method="post" id="getJanbhagidariActStatus" name="getJanbhagidariActStatus">
			<div class="form-row">
			</div>
		</form>
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> 
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>    
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">No. of Projects where Watershed Janbhagidari Organized</th>
      		
      		<th colspan="4" style="text-align:center; vertical-align: middle; width: 35%;">No. of Work to be done through Janbhagidari Activities</th>
      		<th colspan="3" style="text-align:center; vertical-align: middle; width: 28%;">Tentative Community Contribution Percentage (%)</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Total Estimated Value of Work to be done through Janbhagidari (Rs. in Lakh)</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Achievements made so for (No. of Work Completed)</th>
     	</tr>
     	<tr>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">NRM/SWC Structure Creation</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">NRM/SWC Structure Repair /Rejuvenation</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Plantation Related Work</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Production System Related Work</th>
			
			<th style="text-align:center; vertical-align: middle; width: 7%;">Villagers</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">NGOs</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Corporates</th>
			
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
			
		</tr>
		<c:set var="count" value="1" />
		
				<c:if test="${dataListSize>0}">
						<c:forEach items="${dataList}" var="data" varStatus="count">							
							<tr>
								<td>
 								<c:out value='${count.count}' />
 								</td>
 								<td><a href="getdistrictWiseJanbhagidariActivitiesReport?id=<c:out value='${data.st_code}' />&stname=${data.st_name}" > <c:out value="${data.st_name}" /></a></td>
 								<%--  <td> <c:out value="${data.st_name}" /></td> --%>
 								<td class="text-right"> <c:out value="${data.proj_repoted}" /></td>
 								<td class="text-right"> <c:out value="${data.no_work_creation}" /></td>
 								<td class="text-right"> <c:out value="${data.no_work_repair}" /></td>
 								<td class="text-right"> <c:out value="${data.no_work_plant}" /></td>
								<td class="text-right"> <c:out value="${data.no_work_production}" /></td>
								
		 						<td class="text-right"> <c:out value="${data.village}" /></td>
								<td class="text-right"> <c:out value="${data.ngo}" /></td>
								<td class="text-right"> <c:out value="${data.corporate}" /></td>
								
								<td class="text-right"> <c:out value="${data.estimate_work}" /></td>
								<td class="text-right"> <c:out value="${data.no_work_comp}" /></td>
								
 							</tr>
						</c:forEach>
					</c:if>
		
		
		
		
		 	<tr>
				<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td>
				
				<td align="right" class="table-primary" ><b><c:out value='${proj_repotedt}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${no_work_creationt}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${no_work_repairt}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${no_work_plantt}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${no_work_productiont}' /> </b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${villaget/30}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${ngot/30}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${corporatet/30}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${estimate_workt}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><c:out value='${no_work_compt}' /> </b></td>
				
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