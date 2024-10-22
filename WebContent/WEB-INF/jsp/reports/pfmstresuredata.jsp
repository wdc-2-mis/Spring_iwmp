<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<title>Report PF2- Central/State Share Release from State Treasury</title>


<script type="text/javascript">
function downloadPDF(){
	document.pfmsReport.action="downloadPFMSPDF";
	document.pfmsReport.method="post";
	document.pfmsReport.submit();
}
	function downloaddPDF(stcode){	
		document.getElementById("stcode").value=stcode;	
		document.pfmsReport.action="downloadPFMSDetailPDF";	
		document.pfmsReport.method="post";	
		document.pfmsReport.submit();
		}

	function exportExcel(){
		document.pfmsReport.action="downloadExcelpfmstrerecipt";
		document.pfmsReport.method="post";
		document.pfmsReport.submit();
	}

	function exportExcelState(stcode){	
		document.getElementById("stcode").value=stcode;	
		document.pfmsReport.action="downloadExcelexpandtreasure";	
		document.pfmsReport.method="post";	
		document.pfmsReport.submit();
		}
	
</script>
<form action ="downloadPFMSPDF" method="post" name="pfmsReport">
<div class="form-row">
<input type="hidden" name="stcode" id="stcode" value="" /></div>
</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF2 - Central/State Share Release from State Treasury
			to Slna</label></h5>
	</div>

	<div class="col-2"></div>
	<c:if test="${pfmstresure ne null}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
</c:if>
		<c:if test="${treasureList ne null}">
			<button name="exportExcel" id="exportExcel" onclick="exportExcelState('${stcode}')" class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloaddPDF('${stcode}')" class="btn btn-info">PDF</button>
</c:if>
	<c:if test="${pfmstresure ne null}">
		<table id="dtBasicExample" cellspacing="0" class="table">
			<thead>
				<tr>
					<th class="text-right" colspan="6">All amounts are Rs.</th>
				</tr>
				<tr>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 2%;">S.No.</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 10%;">State Name</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 7%;">Financial Year</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 7%;">Treasury Receipt</th>
					<th colspan="2" class="text-center">Treasury Reciept</th>
				</tr>
				<tr>
					<th style="text-align: center; vertical-align: middle; width: 7%;">Central Share</th>
					<th style="text-align: center; vertical-align: middle; width: 7%;">State Share</th>
						
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

				</tr>
				<c:forEach items="${pfmstresure}" var="proj" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td><a href="expandtreasure?stcode=${proj.stCode}">${proj.stName}</a></td>
						<td align="right">${proj.financialYear}</td>
						<td align="right"><fmt:formatNumber value="${proj.treasuryReceipt}"
								type="number" minFractionDigits="2" /></td>
						<td align="right"><fmt:formatNumber value="${proj.centralShare}"
								type="number" minFractionDigits="2" /></td>
						<td align="right"><fmt:formatNumber value="${proj.stateShare}"
								type="number" minFractionDigits="2" /></td>
					</tr>
					
					<c:set var="totaltreasuryReceipt"
					value="${totaltreasuryReceipt+proj.treasuryReceipt}" />
				<c:set var="totalcentralShare"
					value="${totalcentralShare+proj.centralShare}" />
				<c:set var="totalstateShare"
					value="${totalstateShare+proj.stateShare}" />
				
					
				</c:forEach>
				
				<tr>
				<td class="table-primary"></td>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2">
				<c:out value="${totaltreasuryReceipt}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2">
				<c:out value="${totalcentralShare}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2">
				<c:out value="${totalstateShare}" /></fmt:formatNumber></b></td>
				
			</tr>
				
				
<%--  <c:forEach items="${pfmstresure}" var="proj" --%>
<%--  					varStatus="status">  --%>
<!--  					<tr>  -->
<%--  						<th colspan="3"><center>Grand Total</center></th>  --%>
<%--  						<th><b><fmt:formatNumber value="${proj.treasuryReceipt}"  --%>
<%--  									type="number" minFractionDigits="1" /> </b></th>  --%>
<%--  						<th><b><fmt:formatNumber value="${proj.centralShare}"  --%>
<%--  									type="number" minFractionDigits="1" /> </b></th> --%>
<%--  						<th><b><fmt:formatNumber value="${proj.stateShare}" --%>
<%--  						type="number" minFractionDigits="1" /></b></th>  --%>
<!--  					</tr>  -->
<%--  				</c:forEach>  --%>
				
		</table>
	</c:if>

	<c:if test="${treasureList ne null}">
		<table id="dtBasicExample" cellspacing="0" class="table">
			<thead>
				<tr>

					<th class="text-right" colspan="7">All amounts are Rs.</th>
				</tr>
				<tr>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 2%;">S.No.</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 10%;">State
						Name</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 7%;">Financial
						Year</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 7%;">Transaction
						Id</th>
					<th rowspan="2"
						style="text-align: center; vertical-align: middle; width: 7%;">Treasury
						Receipt</th>
					<th colspan="2" class="text-center">Treasury Receipt</th>
				</tr>
				<tr>
					<th style="text-align: center; vertical-align: middle; width: 7%;">Central
						Share</th>
					<th style="text-align: center; vertical-align: middle; width: 7%;">State
						Share</th>
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

				</tr>
				<c:forEach items="${treasureList}" var="proj" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td>${proj.iwmpState.stName}</td>
						<td align="right">${proj.financialYear}</td>
						<td align="right">${proj.transactionId}</td>
						<td align="right"><fmt:formatNumber value="${proj.treasuryReceipt}"
								type="number" minFractionDigits="2" /></td>
						<td align="right"><fmt:formatNumber value="${proj.centralShare}"
								type="number" minFractionDigits="2" /></td>
						<td align="right"><fmt:formatNumber value="${proj.stateShare}"
								type="number" minFractionDigits="2" /></td>
					</tr>
				</c:forEach>

				<c:forEach items="${treasuretotalList}" var="proj"
					varStatus="status">
					<tr>
					<td class="table-primary"></td>
					<td class="table-primary"></td>
					<td class="table-primary"></td>
						<td align="right" class="table-primary"><b>Grand Total</b></td>
						<td align="right" class="table-primary"><b><fmt:formatNumber value="${proj.treasuryReceipt}"
									type="number" minFractionDigits="2"> </fmt:formatNumber></b></td>
						<td align="right" class="table-primary"><b><fmt:formatNumber value="${proj.centralShare}"
									type="number" minFractionDigits="2" ></fmt:formatNumber> </b></td>
						<td align="right" class="table-primary"><b><fmt:formatNumber value="${proj.stateShare}"
						type="number" minFractionDigits="2"></fmt:formatNumber> 
						</b></td>
					</tr>
				</c:forEach>
		</table>
	</c:if>

</div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>
