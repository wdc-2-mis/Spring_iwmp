<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<title>Report SB2- State-wise Number of Watersheds, Total
			Geographical Area and Net Treatable Area</title>

<script type="text/javascript">

function downloadPDF(){
	document.SBReport.action="downloadnetTretatbleReportPDF";
	document.SBReport.method="post";
	document.SBReport.submit();
}

function exportExcel(){
	document.SBReport.action="downloadExcelNetTretatble";
	document.SBReport.method="post";
	document.SBReport.submit();
}

</script>

<body>
<form action ="downloadnetTretatbleReportPDF" method="post" name="SBReport">

</form>
	<br>
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>
		<label id="head">Report SB2- State-wise Number of Watersheds, Total
			Geographical Area and Net Treatable Area</label>
		</h5>
	</div>
	<br>
	<div class="card">


		<div class="row">
			<div class="col-1"></div>
			<div class="col-12" id="exportHtmlToPdf">

				<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
				<div class="canvas_div_pdf">
					<table
						class="table table-bordered table-striped table-highlight w-auto"
						id="dtBasicExample">
						<div class="nav-item text-right">
					<p align="right">
						Report as on:
						<%=app.util.Util.dateToString(null, "dd/MM/yyyy hh:mm aaa")%>
					</p>

				</div>
						<thead>
								<tr>
								<th colspan="13" style="text-align:right;">All amounts(in Rs lakhs), All Area in Lakh Ha.</th>
								</tr>
							<tr>
								<th style="text-align: center;">S.No.</th>
								<th style="text-align: left;">State Name</th>
								<th style="text-align: center;">Total no. of Districts</th>
								<th style="text-align: center;">Total no. of Blocks</th>
								<th style="text-align: center;">Total no. of Micro-watersheds</th>
								<th style="text-align: center;">Total geographical area</th>
								<th style="text-align: center;">Total untreatable area*</th>
								<th style="text-align: center;">Total Area covered under pre-IWMP schemes of DoLR</th>
								<th style="text-align: center;">Total Area covered under schemes of other Ministries</th>
								<th style="text-align: center;">Total Area covered under IWMP/WDC-PMKSY of DoLR (WDC 1.0)</th> 
								<th style="text-align: center;">Projects Area proposed under WDC-PMKSY 2.0</th>
								<th style="text-align: center;">Total area with assured irrigation</th>
								<th style="text-align: center;">Net treatable area(6-(7+8+9+10+11+12))</th>
							</tr>

						</thead>

						<tbody>
							<tr>
								<th>1</th>
								<th>2</th>
								<th>3</th>
								<th>4</th>
								<th>5</th>
								<th>6</th>
								<th>7</th>
								<th>8</th>
								<th>9</th>
								<th>10</th>
								<th>11</th>
								<th>12</th>
								<th>13</th>

							</tr>
							<c:set var="count" value="1" />
							<c:forEach var="list" items="${netTreatledata}">
								<c:forEach var="listUser" items="${list.value}">
									<tr>

										<td class="text-right"><c:out value='${count}' /></td>
										<td class="text-left"><c:out value='${listUser.st_name}' /></td>
										<td class="text-right"><c:out value='${listUser.no_of_dist}' /></td>
										<td class="text-right"><c:out value='${listUser.no_of_block}' /></td>
										<td class="text-right"><c:out value='${listUser.tot_no_micro_ws}' /></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.tot_geo_area_hec}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.tot_untreat_area_hec}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.area_by_preiwmp_proj}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.area_by_other_ws}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.iwmp_wdcpmksy_area}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.area_proposed}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.tot_area_asur_irrig}' /></fmt:formatNumber></td>
										<td class="text-right"><fmt:formatNumber  minFractionDigits="4">
										<c:out value='${listUser.total}' /></fmt:formatNumber></td>
										<c:set var="count" value="${count+1}" />

									</tr>
								</c:forEach>
							</c:forEach>
							<c:forEach items="${dataListNetTotal}" var="netTotal"
								varStatus="seqTotal">
								<tr>
								<td class="table-primary"></td>
								
									<td align="right" class="table-primary"><b>Grand
											Total </b></td>
									<td class="table-primary"align="right"><b><c:out
												value='${netTotal[0]}' /> </b></td>
									<td class="table-primary"align="right"><b><c:out
												value='${netTotal[1]}' /> </b></td>
									<td class="table-primary"align="right"><b><c:out
												value='${netTotal[2]}' /> </b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber minFractionDigits="4"><c:out
												value='${netTotal[3]}' /></fmt:formatNumber> </b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  maxFractionDigits="4"><c:out
												value='${netTotal[4]}' /> </fmt:formatNumber></b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  maxFractionDigits="4"><c:out
												value='${netTotal[5]}' /> </fmt:formatNumber></b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  maxFractionDigits="4"><c:out
												value='${netTotal[6]}' /></fmt:formatNumber> </b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  minFractionDigits="4"><c:out
												value='${netTotal[7]}' /></fmt:formatNumber> </b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  maxFractionDigits="4"><c:out
												value='${netTotal[8]}' /></fmt:formatNumber> </b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  maxFractionDigits="4"><c:out
												value='${netTotal[9]}' /> </fmt:formatNumber></b></td>
									<td class="table-primary"align="right"><b><fmt:formatNumber  minFractionDigits="4"><c:out
												value='${netTotal[10]}' /> </fmt:formatNumber></b></td>
								</tr>
							</c:forEach>


						</tbody>

					</table>
				</div>
				<br />
				<div>
					<font color="red">* Area under habitation, steep slopes,
						snow covered areas, roads, forests, rocky mountainous, etc.</font>
				</div>
			</div>
		</div>
	</div>

	<footer class="text-center">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</footer>
</body>

</html>