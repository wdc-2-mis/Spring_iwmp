<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report - Status of Village-wise Data Entries</title>

<script type="text/javascript">
function validation() 
{	
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("finyear").options[document.getElementById("finyear").selectedIndex].text;
    var schemeName = document.getElementById("scheme").options[document.getElementById("scheme").selectedIndex].text;
   	
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
    document.getElementById("schemeName").value=schemeName;
		
    document.getConvergedExpndtr.action="getConvergedExpndtrReport";
	document.getConvergedExpndtr.method="post";
	document.getConvergedExpndtr.submit();
	return false;
}

function downloadPDF()
{
    document.getVlgData.action="downloadPDFStatusVlgDataReport";
	document.getVlgData.method="post";
	document.getVlgData.submit();
}
	
function exportExcel()
{
    document.getVlgData.action="downloadExcelStatusVlgDataReport";
	document.getVlgData.method="post";
	document.getVlgData.submit();
}

</script>
</head>


<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;">
	<h5><label id="head"></label>Report - Status of Village-wise Data Entries</h5>
</div>

<br>
<!-- <div class ="card"> -->
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="downloadExcelStatusVlgDataReport" method="post" name="getVlgData" id="getVlgData">
 	
</form:form>

<c:if test="${statusVlgDataList ne null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info" >Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info" >PDF</button>
</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 
 <table id="tblVlgData" class="table">
  <thead>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">State Name</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Inauguration Date</th>
      <th colspan="3" style="text-align:center; vertical-align: middle;">Data Entered Details</th>
      <th colspan="2" style="text-align:center; vertical-align: middle;">Data Not Entered Details</th>
    </tr>
    <tr>
     	<th style="text-align:center; vertical-align: middle;">Start Date</th>
     	<th style="text-align:center; vertical-align: middle;">Last Date</th>
     	<th style="text-align:center; vertical-align: middle;">Total No. of Days</th>
     	
     	<th style="text-align:center; vertical-align: middle;">Missed Dates</th>
     	<th style="text-align:center; vertical-align: middle;">Total No. of Days</th>
    </tr>		
    
    <tr>
		<th class="text-center">1</th>
		<th class="text-center">2</th>
		<th class="text-center">3</th>
		<th class="text-center">4</th>
		<th class="text-center">5</th>
		<th class="text-center">6</th>
		<th class="text-center">7</th>
		<th class="text-center">8</th>
	</tr>		
  </thead>

  <tbody id = "VlgDataTbody">
  
					
					<c:if test="${statusVlgDataList ne null}">
 						<c:forEach items="${statusVlgDataList}" var="v" varStatus="count">
 							<tr>
								<td><c:out value='${count.count}' /></td>
								<td class="text-left"> <c:out value="${v.st_name}" /></td> 
								<td class="text-left"> <c:out value="${v.inauguration_date}" /></td>
 								<td class="text-left"> <c:out value="${v.start_date}" /></td>
								<td class="text-left"> <c:out value="${v.last_date}" /></td>
 								<td class="text-right"> <c:out value="${v.entered_days}" /></td>
								<td class="text-left"> <c:out value="${v.missed_dates}" /></td>
								<td class="text-right"> <c:out value="${v.missed_days}" /></td>
 							</tr>
 						</c:forEach>
					</c:if>
					<c:if test="${statusVlgDataListSize eq 0}">
						<tr>
							<td align="center" colspan="8" class="required" style="color:red;">Data Not Found</td>
						</tr>
					</c:if>
				</tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>
<!-- </div> -->


	<footer>
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>