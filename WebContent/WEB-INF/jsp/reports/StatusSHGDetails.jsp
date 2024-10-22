<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

<title>ES4- Entry Status of SHG Account Details</title>

<script type="text/javascript">

function downloadPDF(){
		document.getSHGDetails.action="downloadPDFStatusSHGDetails";
		document.getSHGDetails.method="post";
		document.getSHGDetails.submit();
}

function exportExcel(){
		document.getSHGDetails.action="downloadExcelStatusSHGDetails";
		document.getSHGDetails.method="post";
		document.getSHGDetails.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">ES4- Entry Status of SHG Account Details</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getSHGDetails" name="getSHGDetails" id="getSHGDetails" method="get">
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
	<c:if test="${not empty statusSHGList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			<tr>
				<th rowspan="2">S.No.</th>
				<th rowspan="2">District Name</th>
				<th rowspan="2">Total No. of Project</th>
				<th colspan="2">Total No. of SHG</th>
				<th colspan="2">Total No. of SHG Entered Account Details</th>
				<th colspan="2">Total No. of SHG Pending Account Details</th>
			</tr>
			<tr>
				<th>Newly created</th>
				<th>Existing created</th>
				<th>Newly created</th>
				<th>Existing created</th>
				<th>Newly created</th>
				<th>Existing created</th>
			</tr>
		</thead>
		<tbody id = "SHGRptTbody">
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
			</tr>
			<c:forEach items="${statusSHGList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.dist_name}" /></td>
					<td class="text-right"><c:out value="${dt.totproj}" /></td>
					<td class="text-right"><c:out value="${dt.totalshg_new}" /></td>
					<td class="text-right"><c:out value="${dt.totalshg_old}" /></td>
					<td class="text-right"><c:out value="${dt.enteredshg_new}" /></td>
					<td class="text-right"><c:out value="${dt.enteredshg_old}" /></td>
					<c:if test = "${dt.remaining_new == 0}">
						<td align="right"><c:out value='${dt.remaining_new}'></c:out></td>
					</c:if>
					<c:if test = "${dt.remaining_new > 0}">
						<td align="right"><a href="#" data-id=<c:out value="${dt.dcode}" />  class="newshg" id="newshg" name="newshg"><c:out value='${dt.remaining_new}'></c:out></a></td>
					</c:if>
					<c:if test = "${dt.remaining_old == 0}">
						<td align="right"><c:out value='${dt.remaining_old}' /></td>
					</c:if>
					<c:if test = "${dt.remaining_old > 0}">
						<td align="right"><a href="#" data-id=<c:out value="${dt.dcode}"/> class="oldshg" id="oldshg" name="oldshg" ><c:out value='${dt.remaining_old}'></c:out></a></td>
					</c:if>
					
				</tr>
			
  				
 				<c:set var = "totproj" 
  				value = "${totproj + dt.totproj}" />
 				<c:set var = "totshg_new"
  				value = "${totshg_new + dt.totalshg_new}" />
  				<c:set var = "totshg_old"
  				value = "${totshg_old + dt.totalshg_old}" />
  				<c:set var = "enteredshg_new"
 				value = "${enteredshg_new + dt.enteredshg_new}" />
  				<c:set var = "enteredshg_old"
  				value = "${enteredshg_old + dt.enteredshg_old}" />
  				<c:set var = "remainshg_new"
 				value = "${remainshg_new + dt.remaining_new}" />
  				<c:set var = "remainshg_old"
  				value = "${remainshg_old + dt.remaining_old}" />

			</c:forEach>
			<c:if test="${statusSHGListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totshg_new}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totshg_old}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${enteredshg_new}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${enteredshg_old}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${remainshg_new}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${remainshg_old}" /></b></td>
				</tr>
			</c:if>
			<c:if test="${statusSHGListSize==0}">
				<tr>
					<td align="center" colspan="9" class="required" style="color:red;"><b>Data Not Found</b></td>
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
	<script src='<c:url value="/resources/js/ShgDetail.js" />'></script>
</body>
</html>