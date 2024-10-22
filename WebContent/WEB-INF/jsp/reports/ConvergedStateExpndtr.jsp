<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<title>Report - State-wise, District-wise, Project-wise Convergence Work Expenditure Details</title>

<script type="text/javascript">

function downloadPDF(){
		document.getConStateExpndtr.action="downloadPDFEnteredConWorks";
		document.getConStateExpndtr.method="post";
		document.getConStateExpndtr.submit();
}

function exportExcel(){
		document.getConStateExpndtr.action="downloadExcelEnteredConWorks";
		document.getConStateExpndtr.method="post";
		document.getConStateExpndtr.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report - State-wise, District-wise, Project-wise Convergence Work Expenditure Details</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getConStateExpndtr" name="getConStateExpndtr" id="getConStateExpndtr" method="get">
		<div id="waitDiv" 
			style="display: none; line-height: 20px; z-index: 98; position: absolute; background: #ffffff; left: 25px;  height: 800px;
			 width: 1600px; filter: alpha(opacity = 60); -moz-opacity: .60; opacity: .60; text-align: center; float: left;">
			<table>  
				<tr>
					<td>
						<div align="center">
							<span style="padding-right:3px;  display:inline-block; width: 1600px;">
									<img class="manImg" src="resources/images/load.gif"></img>
							</span>
						</div>
					</td>
				</tr>
			</table> 
		</div>
		
 	</form:form>
 
<br>
	<c:if test="${not empty ConStateExpndtrList}">
<!-- 	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
<!-- 	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>  -->
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			<tr>
				<th rowspan="3">S.No.</th>
				<th rowspan="3">State Name</th>
				<th rowspan="3">Total No. of Converged Works</th>
				<th rowspan ="2" colspan="3" class="text-center">Status of Converged Works</th>
				<th style="text-align:center" colspan="${SchemeListSize*3}">Scheme Name</th>
			</tr>
			<c:forEach items="${SchemeList}" var="shList">
				<th style="text-align:center" colspan="3"><c:out value='${shList.value}' /></th>
			</c:forEach>
			<tr>
				<th>Ongoing</th>
				<th>Completed</th>
				<th>Foreclosed</th>
				<c:forEach items="${SchemeList}">
					<th>Convergence Works</th>
					<th>Expenditure Under WDC-PMKSY 2.0 (in Rs.)</th>
					<th>Expenditure Under Converged Scheme(in Rs.)</th>
				</c:forEach>	
			</tr>
			
			<tr>
				<th class="text-center">1</th>
				<th class="text-center">2</th>
				<th class="text-center">3</th>
				<th class="text-center">4</th>
				<th class="text-center">5</th>
				<th class="text-center">6</th>
				<c:forEach var = "i" begin = "7" end = "${SchemeListSize*3 +6}">
         			<th class="text-center"><c:out value='${i}' /></th>
      			</c:forEach>
			</tr>
			
		</thead>
		
		<tbody id = "convergedWorksRptTbody">
			
			<c:set var ="count" value ="1"/>
			<c:set var="stcd" value="0" />
			<c:set var="totConWrk" value="0" />
			<c:set var="totExOthSh" value="0" />
			<c:set var="totExWDC" value="0" />
			
			<c:forEach items="${ConStateExpndtrList}" var="dt" varStatus="sno">
				
				<c:if test ="${stcd ne dt.st_code}">
				
				
				
				<tr>
					<td class="text-left"><c:out value="${count}" /></td>
					<td class="text-left"><c:out value="${dt.st_name}" /></td>
					<td class="text-right"><c:out value="${dt.convergedworks}" /></td>
					<td class="text-right"><c:out value="${dt.ongoing}" /></td>
					<td class="text-right"><c:out value="${dt.completed}" /></td>
					<td class="text-right"><c:out value="${dt.foreclosed}" /></td>
					
					<c:set var="stcd" value="${dt.st_code}" />
					
					 
					<c:forEach items="${schMap}" var="schMap" varStatus="status">
					
						<c:if test = "${fn:substringBefore(schMap.key,',') eq dt.st_code}">
							<td class="text-right "><c:out value='${schMap.value}' /></td>
						</c:if>
						
						
						
						
						<c:if test = "${fn:substringAfter(schMap.key,',') eq 'cw'}">
						<c:set var="totConWrk" value="${totConWrk + schMap.value}" />
						</c:if>
						
						<c:if test = "${fn:substringAfter(schMap.key,',') eq 'es'}">
						<c:set var="totExOthSh" value="${totExOthSh + schMap.value}" />
						</c:if>
						
						<c:if test = "${fn:substringAfter(schMap.key,',') eq 'ew'}">
						<c:set var="totExWDC" value="${totExWDC + schMap.value}" />
						</c:if>
						
					</c:forEach>
					
					
					<c:set var="count" value="${count + 1}" />
					
					
				</tr>
			
				<c:set var = "conwrks"
  				value = "${conwrks + dt.convergedworks}" />
  				<c:set var = "ongoing"
  				value = "${ongoing + dt.ongoing}" />
  				<c:set var = "cmplt"
  				value = "${cmplt + dt.completed}" />
 				<c:set var = "forclsd"
  				value = "${forclsd + dt.foreclosed}" />
  				
				</c:if>
			</c:forEach>
			
			<c:if test="${ConStateExpndtrListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${conwrks}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${ongoing}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${cmplt}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${forclsd}" /></b></td>
					<c:forEach var = "totMap" items ="${totMap}">
					<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${totMap.value}"/></b></td>
				</c:forEach>
				</tr>
			</c:if>
			<c:if test="${ConStateExpndtrListSize==0}">
				<tr>
					<td align="center" colspan="7" class="required" style="color:red;"><b>Data Not Found</b></td>
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