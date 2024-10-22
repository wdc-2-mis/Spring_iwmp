<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

<title>ES2- Entry Status of Converged Work Details</title>

<script type="text/javascript">

function downloadPDF(){
		document.getConWorksDetails.action="downloadPDFStatusConWorks";
		document.getConWorksDetails.method="post";
		document.getConWorksDetails.submit();
}

function exportExcel(){
		document.getConWorksDetails.action="downloadExcelStatusConWorks";
		document.getConWorksDetails.method="post";
		document.getConWorksDetails.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">ES2- Entry Status of Converged Work Details</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getConWorksDetails" name="getConWorksDetails" id="getConWorksDetails" method="get">
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
	<c:if test="${not empty statusConList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			<tr>
				<th>S.No.</th>
				<th>District Name</th>
				<th>Total No. of Project</th>
				<th>Total No. of Works</th>
				<th>Total No. of Converged Works</th>
				<th>Total No. of Works Entered Convergence Detail</th>
				<th>Total No. of Remaining Works</th>
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
			</tr>
			<c:forEach items="${statusConList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.dist_name}" /></td>
					<td class="text-right"><c:out value="${dt.totalproject}" /></td>
					<td class="text-right"><c:out value="${dt.totalworks}" /></td>
					<td class="text-right"><c:out value="${dt.convergedworks}" /></td>
					<td class="text-right"><c:out value="${dt.enteredcon}" /></td>
					<c:if test = "${dt.remaining > 0}">
						<td align="right"><a href="#" data-id=<c:out value="${dt.dcode}"/> class="remain" id="remain" name="remain" >
						<c:out value='${dt.remaining}'></c:out></a></td>
					</c:if>
					<c:if test = "${dt.remaining == 0}">
						<td class="text-right"><c:out value="${dt.remaining}" /></td>
					</c:if>
					
				</tr>
			
  				
 				<c:set var = "totproj" 
 				value = "${totproj + dt.totalproject}" /> 
				<c:set var = "totwrks" 
 				value = "${totwrks + dt.totalworks}" />
 				<c:set var = "conwrks" 
 				value = "${conwrks + dt.convergedworks}" /> 
 				<c:set var = "enteredwrks" 
 				value = "${enteredwrks + dt.enteredcon}" /> 
 				<c:set var = "remainingwrks" 
 				value = "${remainingwrks + dt.remaining}" />

			</c:forEach>
			<c:if test="${statusConListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totwrks}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${conwrks}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${enteredwrks}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${remainingwrks}" /></b></td>
				</tr>
			</c:if>
			<c:if test="${statusConListSize==0}">
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
	<script src='<c:url value="/resources/js/convergenceWorks.js" />'></script>
</body>
</html>