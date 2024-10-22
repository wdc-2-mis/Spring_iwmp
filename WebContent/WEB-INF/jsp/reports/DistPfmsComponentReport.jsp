<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report PF7</title>


<script type="text/javascript">

function exportExcel(stCode, comp, fromYear,stname){
	document.getElementById("stCode").value=stCode;
	document.getElementById("comp").value=comp;
	document.getElementById("fromYear").value=fromYear;
	document.getElementById("stname").value=stname;
	document.pfmscomp.action="getDistrictWiseComponentExpndtrExcel";
	document.pfmscomp.method="post";
	document.pfmscomp.submit();
}
function downloadPDF(stCode, comp, fromYear,stname){
	document.getElementById("stCode").value=stCode;
	document.getElementById("comp").value=comp;
	document.getElementById("fromYear").value=fromYear;
	document.getElementById("stname").value=stname;
	document.pfmscomp.action="downloadDistrictCompExpndtrPDF";
	document.pfmscomp.method="post";
	document.pfmscomp.submit();
}

</script>
</head>


<body>
 <br>
 <form action="downloadDistrictCompExpndtrPDF" method="post" id="pfmscomp" name="pfmscomp">
<div class="form-row">
			<input type="hidden" name="stCode" id="stCode" value="" />
			<input type="hidden" name="fromYear" id="fromYear" value="" />
			<input type="hidden" name="comp" id="comp" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
			</div>
</form>
<div class="offset-md-2 col-8 formheading" style="text-align:center;"  ><h5>Report PF7- District Wise Component Expenditure</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">


<div class="table-responsive" id="exportHtmlToPdf1">


 </div>
 </div>
<br>
	</div>
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}','${comp}','${fromYear}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}','${comp}','${fromYear}','${stname}')" class="btn btn-info">PDF</button> 
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="tblReport" cellspacing="0" class="table">
  <thead>
	
	<tr>
      		<th style="text-align:right;" colspan="${cmpntMapSize+2}">All amount in Rs.</th>
      	</tr>
						<tr>
							<th colspan="${cmpntMapSize+2}">State : &nbsp;
							
							<c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; Financial Year :
								&nbsp; <c:out value='${fromYear}-${fromYear+1}' /> &nbsp;&nbsp;&nbsp;

<%-- 								Component Name : &nbsp; <c:forEach var="cmp" items="${cmpntMap}"> --%>
<%-- 									<c:out value='${cmp.value}' />  --%>
<%-- 		</c:forEach> --%>
							</th>
						</tr>
						<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 1%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">District Name</th>
      		<th colspan="${cmpntMapSize}" style="text-align:center; vertical-align: middle; width: 7%;">Component Name </th>
       	</tr>
       	
     <tr>
       		<c:forEach var="cmp" items="${cmpntMap}">
      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value="${cmp.value}"/></th>
      		</c:forEach>
       	</tr>
  </thead>

  <tbody>
<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var ="count1" value ="1"/>
			<c:set var ="dcode" value ="0"/>
			<c:forEach var="cmp" items="${cmpntMap}">
      			<th class="text-center"><c:out value="${count +3}"/></th>
      			<c:set var ="count" value ="${count +1}"/>
      		</c:forEach>
		</tr>
   				   <c:forEach items="${dataList}" var="proj" varStatus="status">
								<tr>
								<c:if test="${proj.dcode ne dcode}">
								<td align="center">${count1}</td>
									
									<td><c:out value="${proj.distname}" /></td>
									<c:forEach var="distcmp" items="${distcmpMap}">
										<c:if test= "${proj.dcode eq  fn:substringBefore(distcmp.key,',')}">
											<td><c:out value="${distcmp.value}" /></td>
										</c:if>
									</c:forEach> 
									<c:set var ="count1" value ="${count1 + 1}"/>
								</c:if>
								<c:set var ="dcode" value ="${proj.dcode}"/>
</tr>
									</c:forEach>
									<tr>
			<td colspan="2" class="table-primary" align="right"><strong>Grand Total</strong></td>
				<c:forEach  var = "totdistmap" items ="${totdistmap}">
				<td class="table-primary" style="text-align:center;"><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${totdistmap.value}"/></b></td>
				</c:forEach>
			</tr>
									<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="${cmpntMapSize+2}" class="required">Data Not Found</td>
			</tr>
		</c:if>
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>

<script type="text/javascript">
		$(document).ready(function() {
			$(".sidebar-btn").click(function() {
				$(".wrapper").toggleClass("collapse");
			});
		});
	</script>
	<footer class=" text-center">

	</footer>
</body>
</html>