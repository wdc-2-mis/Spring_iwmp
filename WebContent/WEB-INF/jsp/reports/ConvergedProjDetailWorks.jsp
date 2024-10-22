<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<title>Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details</title>

<script type="text/javascript">

function downloadPDF(projid, stName, distName){
// 	document.getElementById("finyr").value=finyr;
// 	document.getElementById("finName").value=finName;
	document.getElementById("projid").value=projid;
	document.getElementById("stName").value=stName;
	document.getElementById("distName").value=distName;
	document.getConvergedProjDetailWorks.action="downloadPDFConvergedProjDetailWorks";
	document.getConvergedProjDetailWorks.method="post";
	document.getConvergedProjDetailWorks.submit();
}

function exportExcel(projid, stName, distName){
// 	document.getElementById("finyr").value=finyr;
// 	document.getElementById("finName").value=finName;
	document.getElementById("projid").value=projid;
	document.getElementById("stName").value=stName;
	document.getElementById("distName").value=distName;
	document.getConvergedProjDetailWorks.action="downloadExcelConvergedProjDetailWorks";
	document.getConvergedProjDetailWorks.method="post";
	document.getConvergedProjDetailWorks.submit();
}
	
</script>


<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details</label></h5></div>
<br>
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getConvergedProjDetailWorks" name="getConvergedProjDetailWorks" id="getConvergedProjDetailWorks" method="get">
		
<!--     <input type="hidden" name="finyr" id="finyr" value="" /> -->
<!-- 	<input type="hidden" name="finName" id="finName" value="" /> -->
	<input type="hidden" name="stcd" id="stcd" value="" />
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="dcode" id="dcode" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projid" id="projid" value="" />
	
	
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<br/>
	<br/>
	 <c:if test="${not empty ConvergedProjDetailWorksList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${projid}', '${stName}','${distName}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${projid}', '${stName}','${distName}')" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id = "tblReport" class = "table">
	<thead>
		<tr>
			<th colspan=11 class="text-left">State : ${stName} &emsp; District : ${distName}</th>
		</tr>
		<tr>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">S.No.</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Project Name</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Block Name</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Gram Panchayat Name</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Village Name</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Work Code</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Head Name</th>
			<th rowspan = "2" style="text-align:center; vertical-align: middle;">Activity Name</th>
			<th colspan = "3" style="text-align:center; vertical-align: middle;">Stage</th>
		</tr>
		<tr>
       		<th style="text-align:center; vertical-align: middle;">Pre Implementation</th>
       		<th style="text-align:center; vertical-align: middle;">Mid Implementation</th>
      		<th style="text-align:center; vertical-align: middle;">Post Implementation</th>
       	</tr>
	</thead>
	<tbody id = "convergedWorksRptTbody">
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
		</tr>
<%-- 		<c:forEach items="${ConvergedProjDetailWorksList}" var="work" varStatus="sno"> --%>
<!-- 			<tr> -->
<%-- 					<td class="text-left"><c:out value="${sno.count}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.proj_name}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.blockname}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.grampanchayatname}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.villagename}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.workcode}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.headname}" /></td> --%>
<%-- 					<td class="text-left"><c:out value="${work.actname}" /></td> --%>
<%-- 					<c:if test="${work.collection_sno eq null}"> --%>
<%-- 						<td class="text-center"><c:out value="View" /></td> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${work.collection_sno gt 0}"> --%>
<%-- 						<td style="text-align: center;"><a href = "https://bhuvan-app1.nrsc.gov.in/wdc2.0/?collection_sno=${work.collection_sno}" target="_blank"><c:out value="View" /></a></td> --%>
<%-- 					</c:if> --%>
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
			
		<c:set var ="count" value ="1"/>
		<c:set var ="wrkcd" value ="0"/>
		
		<c:forEach var="work" items="${ConvergedProjDetailWorksList}">
			<tr>
				<c:if test="${work.workcode ne  wrkcd}">
					<td><c:out value="${count}" /></td>
					<td class="text-left"><c:out value="${work.proj_name}" /></td>
					<td class="text-left"><c:out value="${work.blockname}" /></td>
					<td class="text-left"><c:out value="${work.grampanchayatname}" /></td>
					<td class="text-left"><c:out value="${work.villagename}" /></td>
					<td class="text-left"><c:out value="${work.workcode}" /></td>
					<td class="text-left"><c:out value="${work.headname}" /></td>
					<td class="text-left"><c:out value="${work.actname}" /></td>
					
					<c:forEach var="map" items="${stgeMap}">
						<c:if test="${work.workcode eq fn:substringAfter(map.key,',')}">
							<td style="text-align: center;">
								<c:forEach var="submap" items="${map.value}">
									<br/><a href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/?collection_sno=${submap.key}" target="_blank"><c:out value="${submap.value} " /></a>
								</c:forEach>
							</td>
						</c:if>
					</c:forEach>
					
					<c:set var="count" value="${count + 1}" />
					<c:set var="wrkcd" value="${work.workcode}" />
				</c:if>
			</tr>
		</c:forEach>

		<c:if test="${ConvergedProjDetailWorksListSize==0}">
			<tr>
				<td align="center" colspan="11" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
	</tbody>
</table>

 </div>
 
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>