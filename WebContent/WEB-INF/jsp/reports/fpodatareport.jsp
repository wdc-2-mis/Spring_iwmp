<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<title>Report OC2- State-wise/District-wise and Project-wise Details of Farmer Producer Organisations (FPOs)</title>

<script type="text/javascript">
function exportExcel(state){
	document.getElementById("state").value=state;
	document.rptfpo.action="getStateWiseFPOExcel";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}

function exportDExcel(state, stname){
	document.getElementById("state").value=state;
	document.getElementById("stname").value=stname;
	document.rptfpo.action="getDistrictWiseFPOExcel";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}

function exportPExcel( dcode, fpoType,stcode,stname){
	document.getElementById("dcode").value=dcode;
	document.getElementById("fpoType").value=fpoType;
	document.getElementById("stcode").value=stcode;
	document.getElementById("stname").value=stname;
	document.rptfpo.action="downloadExcelnewFPOProj";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}

function exportPDExcel( stcode, fpoType,stname){
	document.getElementById("stcode").value=stcode;
	document.getElementById("stname").value=stname;
	document.getElementById("fpoType").value=fpoType;
	document.rptfpo.action="downloadExcelnewFPOAllProj";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}

function downloadPDF(state){
	document.getElementById("state").value=state;
	document.rptfpo.action="downloadFpoDataPDF";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}

function downloadDistrictPDF(state, stname){
	document.getElementById("state").value=state;
	document.getElementById("stname").value=stname;
	document.rptfpo.action="downloadDistrictFpoDataPDF";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}
function downloadProjectPDF( dcode, fpoType,stcode,stname){
	document.getElementById("dcode").value=dcode;
	document.getElementById("fpoType").value=fpoType;
	document.getElementById("stcode").value=stcode;
	document.getElementById("stname").value=stname;
	document.rptfpo.action="downloadProjectFpoDataPDF";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}
function downloadProjectDetailPDF( stcode, fpoType,stname){
	document.getElementById("stcode").value=stcode;
	document.getElementById("stname").value=stname;
	document.getElementById("fpoType").value=fpoType;
	document.rptfpo.action="downloadProjectDetailFpoDataPDF";
	document.rptfpo.method="post";
	document.rptfpo.submit();
}

</script>
</head>

<form action="downloadFpoDataPDF" method="post" id="getfpo" name="rptfpo">
			<div class="form-row">
			<input type="hidden" name="state" id="state" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="dcode" id="dcode" value="" />
			<input type="hidden" name="stcode" id="stcode" value="" />
			<input type="hidden" name="fpoType" id="fpoType" value="" />
			</div>
		</form>

<div class="container-fluid">
	<div class="row">
			<br />
			<div class="offset-md-3 col-6 formheading" style="text-align:center;"  >
			<c:if test="${state eq null }">

			<h5><label id="head">Report OC2- State-wise/District-wise and Project-wise Details of Farmer Producer Organisations (FPOs)</label></h5></c:if>

			<c:if test="${projectList ne null}">

				<h5><label id="head">Report OC2- State-wise Details of Farmer Producer Organisations (FPOs)</label></h5></c:if>

			<c:if test="${projectDList ne null}">

				<h5><label id="head">Report OC2- District-wise Details of Farmer Producer Organisations (FPOs)</label></h5></c:if>

			<c:if test="${projectpList ne null}">

			<h5><label id="head">Report OC2- Project-wise Details of Farmer Producer Organisations (FPOs)</label></h5></c:if>

			<c:if test="${projectAllList ne null}">

			<h5><label id="head">Report OC2- Project-wise Details of Farmer Producer Organisations (FPOs)</label></h5></c:if>

		</div>
</div>

<div class="row">
	<div class="col-2"></div>
	<div class="col-12">

		<form action="getfpodata" method="post">
			<div class="canvas_div_pdf" id="reportDiv">
				<div class="form-row">

					<table style="width: 50%; align-content: center;">
						<tr align="center">


							<td><b>State <span style="color: red;">*</span></b></td>
							<td><select class="form-control project" id="state"
								name="state" required>
									<option value="">--Select State--</option>
									
									<c:if test = "${state == 0 }">
										<option value="0" selected>--All--</option>
									</c:if>
									<c:if test = "${state != 0 }">
										<option value="0">--All--</option>
									</c:if>
									<c:forEach items="${stateList}" var="stat">
										<option value="<c:out value="${stat.key}"/>"
											<c:out value="${stat.key== state ?'selected':'' }"/>>
											<c:out value="${stat.value}" /></option>
									</c:forEach>


							</select></td>
							<td align="center" class="label"><input type="submit"
								class="btn btn-info form-control" id="fpodata" name="fpodata"
								value="Submit"></td>
						</tr>
					</table>
				</div>
			</div>
		</form>

		<div class="col-1"></div>

		<div align="right">
			<button type="button" name="back" class="btn btn-info"
				onclick="history.back()">Back</button>
			<button type="button" name="back" class="btn btn-info"
				onclick="window.location.href='./';">Home</button>
		</div>
		<c:if
			test="${projectList ne null || projectDList ne null || projectpList ne null || projectAllList ne null}">
			<c:if test="${projectList ne null}">
			<button name="exportExcel" id="exportExcel" onclick="exportExcel('${state}')" class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}')" class="btn btn-info">PDF</button>
			</c:if>
			<c:if test="${projectDList ne null}">
			<button name="exportDExcel" id="exportDExcel" onclick="exportDExcel('${state}','${stname}')" class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloadDistrictPDF('${state}','${stname}')" class="btn btn-info">PDF</button>
			</c:if>
			<c:if test ="${projectpList ne null }">
			<button name="exportPExcel" id="exportPExcel" onclick="exportPExcel('${dcode }','${fpoType }','${stcode }','${stname}')" class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloadProjectPDF('${dcode }','${fpoType }','${stcode }','${stname}')" class="btn btn-info">PDF</button>
			</c:if>
			<c:if test ="${ projectAllList ne null}">
			<button name="exportPDExcel" id="exportPDExcel" onclick="exportPDExcel('${stcode }','${fpoType }','${stname}')" class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloadProjectDetailPDF('${stcode }','${fpoType }','${stname}')" class="btn btn-info">PDF</button>
			</c:if>
			<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			 
 
		</c:if>

		<c:if test="${projectList ne null}">
			<table id="dtBasicExample" class="table">
			<c:set var="totalnewfpono" value="0" />
			<c:set var="totalnooffarmerassociatednew" value="0" />
			<c:set var="totaloldfpono" value="0" />
			<c:set var="totalnooffarmerassociatedold" value="0" />

				<thead>
					<tr>
						<th>S.No.</th>
						<th>State.</th>
						<th colspan=2 class="text-center ">New FPO Created</th>
						<th colspan=2 class="text-center ">Existing FPO</th>
					</tr>
				<tbody>
					<tr>
						<th colspan=2 class="text-center "></th>
						<th>No.</th>
						<th>No. of Farmers Associated with FPO</th>
						<th>No.</th>
						<th>No. of Farmers Associated with FPO</th>
					</tr>
				</tbody>
				</thead>

				<c:forEach items="${projectList}" var="proj" varStatus="status">
					<tr>

						<td align="center">${status.count}</td>
						<td><a href="newFPODist?stcode=${proj.stcode}">${proj.st_name}</a></td>
						<td align="right">${proj.newfpono}</td>
						<td align="right" >${proj.fpofarmerasso}</td>
						<td align="right">${proj.oldfpono}</td>
						<td align="right">${proj.oldfarmerasso}</td>

					</tr>
					
					<c:set var="totalnewfpono"
					value="${totalnewfpono+proj.newfpono}" />
				<c:set var="totalnooffarmerassociatednew"
					value="${totalnooffarmerassociatednew+proj.fpofarmerasso}" />
				<c:set var="totaloldfpono"
					value="${totaloldfpono+proj.oldfpono}" />
				<c:set var="totalnooffarmerassociatedold"
					value="${totalnooffarmerassociatedold+proj.oldfarmerasso}" />
				</c:forEach>
				
				<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewfpono}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnooffarmerassociatednew}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldfpono}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnooffarmerassociatedold}" /></b></td>
				
			</tr>

			</table>
		</c:if>

		<c:if test="${projectDList ne null}">
			<table id="dtBasicExample" cellspacing="0" class="table">
			<c:set var="totalnewfpono" value="0" />
			<c:set var="totalnooffarmerassociatednew" value="0" />
			<c:set var="totaloldfpono" value="0" />
			<c:set var="totalnooffarmerassociatedold" value="0" />

				<thead>
					<tr>
						<th>S.No.</th>
						<th>District.</th>
						<th colspan=2 class="text-center ">New FPO Created</th>
						<th colspan=2 class="text-center ">Existing FPO</th>
					</tr>
				<tbody>
					<tr>
						<th colspan=2 class="text-center "></th>
						<th>No.</th>
						<th>No. of Farmers Associated with FPO</th>
						<th>No.</th>
						<th>No. of Farmers Associated with FPO</th>
					</tr>
				</tbody>
				</thead>

				<c:forEach items="${projectDList}" var="proj" varStatus="status">
					<tr>

						<td align="center">${status.count}</td>
						<td>${proj.distname}</td>
						<td><a href="newFPOProj?dcode=${proj.dcode}&stcode=${state}&fpoType=newFPO">${proj.newfpono}</a></td>
						<td align="right">${proj.fpofarmerasso}</td>
						<td align="right"><a href="newFPOProj?dcode=${proj.dcode}&stcode=${state}&fpoType=oldFPO">${proj.oldfpono}</a></td>
						<td align="right">${proj.oldfarmerasso}</td>

					</tr>
					
					<c:set var="totalnewfpono"
					value="${totalnewfpono+proj.newfpono}" />
				<c:set var="totalnooffarmerassociatednew"
					value="${totalnooffarmerassociatednew+proj.fpofarmerasso}" />
				<c:set var="totaloldfpono"
					value="${totaloldfpono+proj.oldfpono}" />
				<c:set var="totalnooffarmerassociatedold"
					value="${totalnooffarmerassociatedold+proj.oldfarmerasso}" />
				</c:forEach>

				<c:forEach items="${projectsList}" var="proj" varStatus="status">
					<tr>
						<td class="table-primary"></td>
						<td align="right" class="table-primary"><b>Grand Total</b></td>
						<td align="right" class="table-primary"><b><a
								href="newFPOAllProj?stcode=${proj.stcode}&fpoType=newFPO">${totalnewfpono}</a></b></td>
						<td align="right" class="table-primary"><b>${totalnooffarmerassociatednew}</b></td>
						<td align="right" class="table-primary"><b><a
								href="newFPOAllProj?stcode=${proj.stcode}&fpoType=oldFPO">${totaloldfpono}</a></b></td>
						<td align="right" class="table-primary"><b>${totalnooffarmerassociatedold}</b></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<c:if test="${projectpList ne null}">
			<table id="dtBasicExample" cellspacing="0" class="table">
				<thead>
					<tr>
						<th>S.No.</th>
						<th>Project.</th>
						<th>Name of FPO.</th>
						<th>Dept. /Org. /Scheme</th>
						<th>Registration No.</th>
						<th>Date of Registration</th>
						<th>No. of members of FPO.</th>
						<th>No. of Farmers Associated with FPO</th>
						<th>Average Turnover(in rs.)</th>
						
					</tr>

				</thead>
				<c:set var="proj1" value="" />
				<c:set var="cvb" value="0" />
				<c:forEach items="${projectpList}" var="proj" varStatus="status">
					<tr>

						<td align="center">${status.count}</td>

						<c:choose>
							<c:when test="${proj1 ne proj.fpoMain.iwmpMProject.projName}">
								<td>${proj.fpoMain.iwmpMProject.projName}</td>
								<c:set var="proj1" value='${proj.fpoMain.iwmpMProject.projName}' />
							</c:when>

							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td>${proj.fpoName}</td>
						<td>${proj.MDepartmentScheme.schemeDescription}</td>
						<td align="right">${proj.registrationNo}</td>
						<td align="right"><fmt:formatDate value="${proj.registrationDate}"
											pattern="dd/MM/yyyy" /></td>
						<td align="right">${proj.noOfMembers}</td>
						<td align="right">${proj.noOfFarmerAssociated}</td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">  ${proj.turnover}</fmt:formatNumber></td>
						
					</tr>
				<c:set var="totalnoofmembers"
					value="${totalnoofmembers+proj.noOfMembers}" />
					<c:set var="cvb" value="${cvb+1}" />
				<c:set var="totaltrnovr"
					value="${totaltrnovr+proj.turnover}" />
				<c:set var="totalnooffarmerassociated"
					value="${totalnooffarmerassociated+proj.noOfFarmerAssociated}" />
				</c:forEach>
			<tr>
				<td align="right" class="table-primary" colspan="6"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnoofmembers}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnooffarmerassociated}" /></b></td>
				<td align="right" class="table-primary" width="14%"><b>Average Total =<c:out value="${totaltrnovr/cvb}" /></b></td>
				
				
			</tr>
			</table>
		</c:if>

		<c:if test="${projectAllList ne null}">
			<table id="dtBasicExample" cellspacing="0" class="table">
				<thead>
					<tr>
						<th>S.No.</th>
						<th>District.</th>
						<th>Project.</th>
						<th>Name of FPO.</th>
						<th>Dept. /Org. /Scheme</th>
						<th>Registration No.</th>
						<th>Date of Registration</th>
						<th>No. of members of FPO.</th>
						<th>No. of Farmers Associated with FPO</th>
						<th>Average Turnover(in rs.)</th>
						
					</tr>

				</thead>
				<c:set var="proj2" value="" />
				<c:set var="project" value="" />
				<c:set var="cnt" value="0" />
				<c:forEach items="${projectAllList}" var="proj" varStatus="status">
					<tr>

						<td align="center">${status.count}</td>

						<c:choose>
							<c:when
								test="${proj2 ne proj.fpoMain.iwmpMProject.iwmpDistrict.distName}">
								<td>${proj.fpoMain.iwmpMProject.iwmpDistrict.distName}</td>
								<c:set var="proj2"
									value='${proj.fpoMain.iwmpMProject.iwmpDistrict.distName}' />
							</c:when>

							<c:otherwise>
								<td></td>
							</c:otherwise>

						</c:choose>

						<c:choose>
							<c:when test="${project ne proj.fpoMain.iwmpMProject.projName}">
								<td>${proj.fpoMain.iwmpMProject.projName}</td>
								<c:set var="project"
									value='${proj.fpoMain.iwmpMProject.projName}' />
							</c:when>

							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td>${proj.fpoName}</td>
						<td>${proj.MDepartmentScheme.schemeDescription}</td>
						<td>${proj.registrationNo}</td>
						<td><fmt:formatDate value="${proj.registrationDate}"
											pattern="dd/MM/yyyy" /></td>
						<td>${proj.noOfMembers}</td>
						<td>${proj.noOfFarmerAssociated}</td>
						<td>${proj.turnover}</td>
						
					</tr>
				<c:set var="totalnoofmembers"
					value="${totalnoofmembers+proj.noOfMembers}" />
					<c:set var="cnt" value="${cnt+1}" />
				<c:set var="totaltrnovr"
					value="${totaltrnovr+proj.turnover}" />
				<c:set var="totalnooffarmerassociated"
					value="${totalnooffarmerassociated+proj.noOfFarmerAssociated}" />
				</c:forEach>
			<tr>
				<td align="right" class="table-primary" colspan="7"><b>Grand Total</b></td>
				<td align="center" class="table-primary"><b><c:out value="${totalnoofmembers}" /></b></td>
				<td align="center" class="table-primary"><b><c:out value="${totalnooffarmerassociated}" /></b></td>
				<td align="center" class="table-primary" width="12%"><b>Average Total=<c:out value="${totaltrnovr/cnt}" /></b></td>
				
				
			</tr>

<%-- 				</c:forEach> --%>
<%-- 				<c:forEach items="${projectTotalList}" var="proj" varStatus="status"> --%>
					<tr>
<!-- 						<th colspan="7" >Grand Total</th> -->
<%-- 						<center></center></th> --%>
<%-- 						<c:if test="${fpoType eq 'newFPO'}"> --%>
<%-- 							<th><b>${proj.fpofarmerasso}</b></th> --%>
<%-- 						</c:if> --%>

<%-- 						<c:if test="${fpoType eq 'oldFPO'}"> --%>
<%-- 							<th><b>${proj.oldfarmerasso}</b></th> --%>
<%-- 						</c:if> --%>

					</tr>
<%-- 				</c:forEach> --%>
			</table>
		</c:if>
	</div>
</div>
</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>


</body>
</html>