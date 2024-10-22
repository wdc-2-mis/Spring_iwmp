<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${sessionScope.loginid eq null }">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
	</c:when>
	<c:otherwise>
		<%@include file="/WEB-INF/jspf/header2.jspf"%>
	</c:otherwise>
</c:choose>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share)</title>
<script src='<c:url value="/resources/js/jquery-3.5.1.js" />'></script>
<script
	src='<c:url value="/resources/js/1.12.1jquery.dataTables.min.js" />'></script>
<script
	src='<c:url value="/resources/js/2.2.3dataTables.buttons.min.js" />'></script>
<script src='<c:url value="/resources/js/jszip.min.js" />'></script>
<script src='<c:url value="/resources/js/pdfmake.min.js" />'></script>
<script src='<c:url value="/resources/js/1.53vfs_fonts.js" />'></script>
<script
	src='<c:url value="/resources/js/export/buttons.html5.min.js" />'></script>
<%-- <script src='<c:url value="/resources/js/buttons.print.min.js" />'></script> --%>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/bootstrap/jquery.dataTables.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/2.2.3buttons.dataTables.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">



<title>P1 Project</title>
<style>
</style>
<script type="text/javascript">
	function getDistrict() {

		var selectedValue = $("#mySelect").val();
		var slctSubcat = $('#district'), option = "";
		slctSubcat.empty();
		slctSubcat.append('<option value="0"> --All District--</option>');
		$.ajax({
			type : 'POST',
			url : "getDistrictDataNew",
			data : {
				"stateCode" : selectedValue
			},

			success : function(data) {

				for ( var key in data) {
						
					slctSubcat.append('<option value='+data[key]+'>' +key+ '</option>');
				}
			},
			error : function(xhr, status, er) {
				alert("error");
				console.log(er);
			}

		})
	};
	function downloadPDF(stcode, distcode, finyear){
	    document.getElementById("stcode").value=stcode;
	    document.getElementById("distcode").value=distcode;
	    document.getElementById("finyear").value=finyear;
	    document.getRpt.action="downloaddisplayProjectPDF";
		document.getRpt.method="post";
		document.getRpt.submit();
		}

	function downloadExcell(stcode, distcode, finyear){
	    document.getElementById("stcode").value=stcode;
	    document.getElementById("distcode").value=distcode;
	    document.getElementById("finyear").value=finyear;
	    document.getRpt.action="downloadExcelDisplayProject";
		document.getRpt.method="post";
		document.getRpt.submit();
		}
	
	function exportsPDF(finyear){
	    document.getElementById("finyear").value=finyear;
	    document.getRpt.action="downloadAllStatesPDF";
		document.getRpt.method="post";
		document.getRpt.submit();
		}

	function exportExcell(finyear){
	    document.getElementById("finyear").value=finyear;
	    document.getRpt.action="downloadExcelAllStates";
		document.getRpt.method="post";
		document.getRpt.submit();
		}
	
	$(document).on('click','#gorpt',function(e){
		document.getRpt.action="displayProject";
		document.getRpt.method="post";
		document.getRpt.submit();
	})
	
</script>
</head>
<body>

	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">

				<div class="col-sm-9">
					<h5>
					<label id="head">
					Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) </label></h5>

				</div>

			</div>
		</div>
		<form:form action="displayProject" method="post" modelAttribute="iwmpProj" name="getRpt">

<input type="hidden" id="stcode" name="stcode" value="" />
 	<input type="hidden" id="distcode" name="distcode" value="" />
	<input type="hidden" id="finyear" name="finyear" value="" />

			<!-- 		<a href="#" onclick="exportPDF();" class="button">Run Code</a> -->
			<!-- 		<a href="#" onclick="demoFromHTML();" class="button">Run autoCode</a> -->
			<table
				class="table table-bordered table-striped table-highlight w-auto"
				style="width: 100%">
				<tr>
					<td align="left">
						<table
							class="table table-bordered table-striped table-highlight w-auto"
							style="width: 70%">
							<tr>
								<td>Select State</td>
								<td align="left"><form:select id="mySelect"
										onchange="javascript:getDistrict();"
										path="iwmpDistrict.iwmpState.stCode">
										<form:option value="0" label="---All State---"></form:option>
										<form:options items="${statelist}" />
									</form:select></td>
								<td>Select District</td>
								<td><form:select id="district" path="iwmpDistrict.dcode">
										<form:option value="0" label="---All District---"></form:option>
										<form:options items="${districtList}" />
									</form:select></td>
								<td>Select Financial Year</td>
								<td><form:select path="iwmpMFinYear.finYrCd">
										<form:option value="0" label="---All Financial Year---"></form:option>
										<form:options items="${financialYear}" itemLabel="finYrDesc"
											itemValue="finYrCd"></form:options>
									</form:select></td>

								<td align="left"><input type="submit" class="square_btn" id="gorpt"
									name="go" value="Go" /></td>
							</tr>
							<tr>
								<td colspan="7"></td>
							</tr>

							<c:if test="${(empty projectList) and (projectList!=null)}">

								<tr>
									<td colspan="7" class="alert alert-info" align="center">No
										Data to Display</td>
								</tr>
							</c:if>
							
							<c:if test ="${stcode eq '0'}">
							<c:if test="${(empty stateprojectList) and (stateprojectList!=null)}">

								<tr>
									<td colspan="7" class="alert alert-info" align="center">No
										Data to Display</td>
								</tr>
							</c:if>
							</c:if>

						</table>
					</td>
				</tr>
			</table>

			<c:choose>
			<c:when test ="${stcode eq '0'}">
			<c:if test="${not empty stateprojectList}">
				<div class="nav-item text-left">
				<button name="downloadExcel" id="downloadExcel" onclick="exportExcell('${finyear}')" class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="exportsPDF('${finyear}')" class="btn btn-info">PDF</button>
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
				
				</div>
				<div class="canvas_div_pdf">
					<table
						class="table table-bordered table-striped table-highlight w-auto"
						id="dtBasicExample">
						<thead>
						<tr>
						<c:if test = "${finyear eq '0'}">
						<th colspan ="4" class="text-left">Financial Year : All Financial Year</th>
						</c:if>
						<c:if test = "${finyear ne '0'}">
						<th colspan ="4" class="text-left">Financial Year : 20${finyear}-${finyear+1}</th>
						</c:if>
						<th colspan ="4" class="text-right">All amounts(in Rs lakhs), All Area in Ha.</th>
						</tr>
							<tr>
								<th class="text-center">S.No.</th>
								<th class="text-center">State Name</th>
								<th class="text-center">District</th>
								<th class="text-center">Project</th>
								<th class="text-center">Area Sanctioned</th>
								<th class="text-center">Total Cost</th>
								<th class="text-center">Central Share</th>
								<th class="text-center">State Share</th>
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
							</tr>
						<c:forEach items="${stateprojectList}" var="proj" varStatus="status">
							<tr>
									<td align="center">${status.count}</td>
									<td>${proj.st_name}</td>
									<td class="text-right"><c:out value='${proj.district}' /></td>
									<td class="text-right"><c:out value='${proj.project}' /></td>
									<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value='${proj.area}' /></fmt:formatNumber></td>
									<td class="text-right"><c:out value='${proj.cost}' /></td>
									<td class="text-right"><c:out value='${proj.central}' /></td>
									<td class="text-right"><c:out value='${proj.state}' /></td>
							</tr>
							
								<c:set var="totaldistrict"
								value="${totaldistrict+proj.district}" />
								<c:set var="totalproject"
								value="${totalproject+proj.project}" />
								<c:set var="totalareasanctioned"
 								value="${totalareasanctioned+proj.area}" /> 
								<c:set var="totalcost"
								value="${totalcost+proj.cost}" />
 								<c:set var="totalcentshare"
 								value="${totalcentshare+proj.central}" />
								<c:set var="totalstshare"
								value="${totalstshare+proj.state}" /> 
						</c:forEach>
				
			<tr>
				<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
<%-- 				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalareasanctioned}" /></fmt:formatNumber></b></td> --%>
				<td align="right" class="table-primary"><b><c:out value="${totaldistrict}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalproject}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalareasanctioned}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcost}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcentshare}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalstshare}" /></b></td>
				
			</tr>

						</tbody>
					</table>
				</div>
			</c:if>
			</c:when>

			<c:otherwise>  
			<c:if test="${not empty projectList}">
			
			
			
				<div class="nav-item text-left">
				<button name="downloadExcel" id="downloadExcel" onclick="downloadExcell('${stcode}','${distcode}','${finyear}')" class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcode}','${distcode}','${finyear}')" class="btn btn-info">PDF</button>
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

				</div>
				<div class="canvas_div_pdf">
					<table
						class="table table-bordered table-striped table-highlight w-auto"
						id="dtBasicExample">
						<thead>
						<tr>
						<th colspan ="10" class="text-right">All amounts(in Rs lakhs), All Area in Ha.</th>
						</tr>
							<tr>
								<th class="text-center">S.No.</th>
								<th class="text-center">State Name</th>
								<th class="text-center">District Name</th>
								<th class="text-center">Financial Year</th>
								<th class="text-center">Project Name</th>
								<th class="text-center">Project Sanction Date</th>
								<th class="text-center">Area Sanctioned</th>
								<th class="text-center">Total Cost</th>
								<th class="text-center">Central Share</th>
								<th class="text-center">State Share</th>
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
								<!-- 								<th>10</th> -->
								<!-- 								<th>11</th> -->
							</tr>
							<c:forEach items="${projectList}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td>${proj.iwmpDistrict.iwmpState.stName}</td>
									<td>${proj.iwmpDistrict.distName}</td>
									<td class="text-right">${proj.projectSanctionYr.finYrDesc}</td>
									<td>${proj.projName}</td>
									<td class="text-right"><fmt:formatDate value="${proj.projectSanctionDt}"
											pattern="dd/MM/yyyy" /></td>
									<td class="text-right"><fmt:formatNumber minFractionDigits="4"> ${proj.areaProposed}</fmt:formatNumber></td>
									<td class="text-right">${proj.projectCost}</td>
									<td class="text-right">${proj.centralShareAmt}</td>
									<td class="text-right">${proj.stateShareAmt}</td>
									
								</tr>
								<c:set var="totalareasanctioned"
					value="${totalareasanctioned+proj.areaProposed}" />
				<c:set var="totalcost"
					value="${totalcost+proj.projectCost}" />
				<c:set var="totalcentshare"
					value="${totalcentshare+proj.centralShareAmt}" />
				<c:set var="totalstshare"
					value="${totalstshare+proj.stateShareAmt}" />
				</c:forEach>
				
				<tr>
				<td colspan="6" align="right" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalareasanctioned}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcost}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcentshare}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalstshare}" /></b></td>
				
			</tr>

						</tbody>
					</table>
				</div>
			</c:if>
			</c:otherwise>
			</c:choose> 

		</form:form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".sidebar-btn").click(function() {
				$(".wrapper").toggleClass("collapse");
			});
		});
	</script>
	<footer class=" text-center">
		<c:choose>
			<c:when test="${sessionScope.loginid eq null }">
				<%@include file="/WEB-INF/jspf/footer.jspf"%>
			</c:when>
			<c:otherwise>
				<%@include file="/WEB-INF/jspf/footer2.jspf"%>
			</c:otherwise>
		</c:choose>

	</footer>
</body>
</html>