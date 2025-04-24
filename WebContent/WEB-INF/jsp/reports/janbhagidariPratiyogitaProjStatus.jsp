<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<title>Project wise Current Status of Watersheed Janbhagidari</title>
	<script type="text/javascript">
	function exportExcel(){
		document.rptStateWiseCurrentStatus.action="downloadExcelStateWiseCurrentStatus";
		document.rptStateWiseCurrentStatus.method="post";
		document.rptStateWiseCurrentStatus.submit();
	}
	
	function downloadPDF()
	{
			document.rptStateWiseCurrentStatus.action="downloadStatewiseCurrentStatusPDF";
			document.rptStateWiseCurrentStatus.method="post";
			document.rptStateWiseCurrentStatus.submit();
		
	} 
	
	</script>

<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Project wise Current Status of Watersheed Janbhagidari</label></h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10" >
	<form action="getProjectWiseCurrentStatusJanbhagidari" method="post" id="getJanbhagidariStatus" name="getJanbhagidariStatus">
			<div class="form-row">
			</div>
		</form>
	
	<!-- <button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>     -->
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
  	<tr> 
	 	<th colspan="15" style="text-align:left; ">State Name : <c:out value="${stname}" />  &nbsp; District Name : <c:out value="${distname}" /> </th>
	</tr>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
      		
      		<th colspan="4" style="text-align:center; vertical-align: middle; width: 28%;">Basic Information about Project</th>
      		<th colspan="3" style="text-align:center; vertical-align: middle; width: 28%;">Identification & Engagement of NGOs</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Opening of SWCK Bank Account at Gram Panchayat</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 28%;">Fund Utilization under WDC-PMKSY2.0</th>
     	</tr>
     	<tr>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Gram Panchayat</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Villages</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area Allocated for Project (ha.)</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Project Outlay (Rs. In Lakh)</th>
			
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of NGOs</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Gram Panchayat NGO</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Villages NGO</th>
			
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Fund Outlay</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Fund Expenditure</th>
			
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
								<td><c:out value='${count.count}' /></td>
 								<%-- <td><a href="getProjectWiseCurrentStatusJanbhagidari?id=<c:out value='${data.dcode}' />&distname=${data.dist_name} &stid=<c:out value='${stcode}' />&stname=${stname}" > <c:out value="${data.dist_name}" /></a></td> --%>
 								<td> <c:out value="${data.proj_name}" /></td>	
 								
 								<td class="text-right"> <c:out value="${data.no_gp}" /></td>
								<td class="text-right"> <c:out value="${data.no_village}" /></td>
		 						<td class="text-right"> <c:out value="${data.proj_area}" /></td>
								<td class="text-right"> <c:out value="${data.proj_outlay}" /></td>
								
								<%-- <td class="text-right"> <a href="#" data-id="${data.st_code}" class='activity' data-toggle="modal" style ="color: blue;"><c:out value="${data.no_ngo_name}" /> </a></td> --%>
								<td class="text-right"> <c:out value="${data.no_ngo_name}" /></td>
								<td class="text-right"> <c:out value="${data.no_ngo_gp}" /></td>
								<td class="text-right"> <c:out value="${data.no_ngo_vill}" /></td>
								
								<%-- <td class="text-right"><a href="#" data-id="${data.st_code}" class='swck' data-toggle="modal" style ="color: blue;"> <c:out value="${data.no_swck_gp}" /></a></td> --%>
 								<td class="text-right"> <c:out value="${data.no_swck_gp}" /></td>
 								<td class="text-right"> <c:out value="${data.fund_outlayex}" /></td>
								<td class="text-right"> <c:out value="${data.fund_expenditure}" /> </td>
 							</tr>
						</c:forEach>
					</c:if>
		
		
		
		
			<tr>
				<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td>
				
				<td align="right" class="table-primary" ><b><c:out value='${totno_gp}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${totno_village}' /> </b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${totproj_area}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${totproj_outlay}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><c:out value='${totno_ngo_name}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${totno_ngo_gp}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${totno_ngo_vill}' /> </b></td>
				
				<td align="right" class="table-primary" ><b><c:out value='${totno_swck_gp}' /> </b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${fund_outlayex}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${totfund_expenditure}' /></fmt:formatNumber></b></td>
				
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