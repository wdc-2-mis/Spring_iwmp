<!DOCTYPE html>
<html>
<head>

<title>Report SMC3 - State Wise Total Watershed Mahotsav Social Media Views Screenshot Uploaded </title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" />
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script type="text/javascript">

function downloadPDF(){
    document.mohotsavRpt.action="downloadWMTotNoOfScrnshtUploadedPDF";
	document.mohotsavRpt.method="post";
	document.mohotsavRpt.submit();
}

function downloadExcel(){
    document.mohotsavRpt.action="downloadExcelWMTotNoOfScrnshtUploaded";
	document.mohotsavRpt.method="post";
	document.mohotsavRpt.submit();
}


</script>

</head>

<body>
	<div class="maindiv">

		

			<div class="col formheading" style="text-decoration: underline;"><h4>Report SMC3 - State Wise Total Watershed Mahotsav Social Media Views Screenshot Uploaded</h4> </div>

			<br>
			<form name="mohotsavRpt" id="mohotsavReport" action="getWMSocialMediaCompAnalysis" method="post">
			


				<div class="nav-item text-left mb-2">
				<c:if test="${wmListSize > 0}">
					<button type="button" name="exportExcel" id="exportExcel" class="btn btn-info" onclick="downloadExcel()">Excel</button>
					<button type="button" name="exportPDF" id="exportPDF" class="btn btn-info" onclick="downloadPDF()">PDF</button>
				</c:if>
				<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
				</div>
			</form>
			

			<table class="table table-bordered table-striped" id="stWMR">
				<thead>
					<tr>
						<th rowspan = "2"  style="text-align: center; vertical-align: middle;">S.No.</th>
						<th rowspan = "2"  style="text-align: center; vertical-align: middle;">State Name</th>
						<th  colspan ="2" style="text-align: center; vertical-align: middle;">Total Number of Screenshot Uploaded</th>
					</tr>
						<tr>
							<th style="text-align: center; vertical-align: middle;">Total Videos</th>
							<th style="text-align: center; vertical-align: middle;">Total Photos</th>
						</tr>
						<tr>
                            <th style="text-align: center;">1</th>
                            <th style="text-align: center;">2</th>
                            <th style="text-align: center;">3</th>
                            <th style="text-align: center;">4</th>
                        </tr>
				</thead>

				<tbody id="tbodyWMSocialMediaRpt">
				<c:if test="${wmListSize > 0}">
				<c:set var ="videos" value = "0"/>
				<c:set var ="photos" value = "0"/>
					<c:forEach items="${wmList}" var="dt" varStatus="s">
						<tr>
							<td class="text-center">${s.index + 1}</td>
							<td class="text-left">${dt.stname}</td>
							<td class="text-center">${dt.totvideos}</td>
							<td class="text-center">${dt.totphotos}</td>
						</tr>
						<c:set var ="videos" value = "${videos + dt.totvideos}"/>
						<c:set var ="photos" value = "${photos + dt.totphotos}"/>
					</c:forEach>
					<tr>
						<td colspan ="2" align="center" class="table-primary">Grand Total</td>
						<td align="center" class="table-primary">${videos}</td>
						<td align="center" class="table-primary">${photos}</td>
					</tr>
				</c:if>
				
				<c:if test="${wmListSize eq 0}">
					<tr>
						<td colspan="4" class="text-center text-danger">No records found</td>
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
