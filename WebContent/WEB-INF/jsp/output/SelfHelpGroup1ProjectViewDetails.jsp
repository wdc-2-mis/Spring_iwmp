<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<html>
<head>
<title>Report OC1- Project wise details of Newly Created Self Help Group(SHG)</title>
</head>
<script type="text/javascript">

function exportPDFNew(projectid,projname, headtype, stname, distname) {
	document.getElementById("projectid").value=projectid;
	document.getElementById("projname").value=projname;
	document.getElementById("distname").value=distname;
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("shgHead").value="shgNew";
	document.getElementById("stname").value=stname;
	document.rptSHGUserGroup.action="downloadUserSelfHelpGroupProjectDetailViewPDF";
	document.rptSHGUserGroup.method="post";
	document.rptSHGUserGroup.submit();
}
function exportPDFExisted(projectid,projname, headtype, stname, distname) {
	document.getElementById("projectid").value=projectid;
	document.getElementById("projname").value=projname;
	document.getElementById("distname").value=distname;
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("shgHead").value="shgExisted";
	document.getElementById("stname").value=stname;
	document.rptSHGUserGroup.action="downloadUserSelfHelpGroupProjectDetailViewPDF";
	document.rptSHGUserGroup.method="post";
	document.rptSHGUserGroup.submit();
}
function exportExcelNew(projectid,projname, headtype, stname, distname) {
	document.getElementById("projectid").value=projectid;
	document.getElementById("projname").value=projname;
	document.getElementById("distname").value=distname;
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("shgHead").value="shgNew";
	document.getElementById("stname").value=stname;
	document.rptSHGUserGroup.action="downloadExcelProjDetailSHG";
	document.rptSHGUserGroup.method="post";
	document.rptSHGUserGroup.submit();
}
function exportExcelExisted(projectid,projname, headtype, stname, distname) {
	document.getElementById("projectid").value=projectid;
	document.getElementById("projname").value=projname;
	document.getElementById("distname").value=distname;
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("shgHead").value="shgExisted";
	document.getElementById("stname").value=stname;
	document.rptSHGUserGroup.action="downloadExcelProjDetailSHG";
	document.rptSHGUserGroup.method="post";
	document.rptSHGUserGroup.submit();
}
function showOnlyProjectWise(projectid, projname, distname, headtype, stname)
{
	document.getElementById("projectid").value=projectid;
	document.getElementById("projname").value=projname;
	document.getElementById("distname").value=distname;
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("stname").value=stname;
	document.rptSHGUserGroup.action="getSelfHelpGroupListProject";
	document.rptSHGUserGroup.method="post";
	document.rptSHGUserGroup.submit();
}
</script>

<div class="row">
	<div class="col-2"></div>
	<div class="col-12">

		<form action="getRptshg" method="post" id="getRptshg" name="rptSHGUserGroup">
			<div class="form-row">
			<input type="hidden" name="projectid" id="projectid" value="" />
			<input type="hidden" name="projname" id="projname" value="" />
			<input type="hidden" name="headtypeh" id="headtypeh" value="" />
			<input type="hidden" name="shgHead" id="shgHead" value=""/>
			<input type="hidden" name="distname" id="distname" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
				<%-- <table style="width: 59%; margin-left: auto; margin-right: auto;">
					<tr align="center">


						<td><b>State <span style="color: red;">*</span></b></td>
						<td><select class="form-control project" id="state"
							name="state" required>
								<option value="0">--All--</option>
								<c:forEach items="${stateList}" var="state">
									<option value="<c:out value="${state.key}"/>"
										<c:out value="${state.key== stCode ?'selected':'' }"/>>
										<c:out value="${state.value}" /></option>
								</c:forEach>
						</select></td>

						<td><b>Head <span style="color: red;">*</span></b></td>
						<td>
							<select class="form-control project" name="headtype" required="required">
								<option value="">--Select Head--</option>
								<c:if test="${not empty headtype}">
	               					<c:forEach items="${headtype}" var="lists">
		               					<c:if test="${lists.key eq headtypec}">
		       								<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
		       							</c:if>
		       							<c:if test="${lists.key ne headtypec}">
		       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
		       							</c:if>
									</c:forEach>
								</c:if> 
								<!-- 
								<option value="livelihood">Livelihood Activities for the Asst-less</option>
								<option value="production">Production</option>
								<option value="epa">EPA</option> -->
							</select>
						</td>

						<td align="center" class="label"><input type="submit"
							class="btn btn-info form-control" id="livelihood"
							name="livelihood" value="Submit"></td>
					</tr>
				</table> --%>
			</div>
		</form>

		<div class="col-1"></div>
		<div class="col" id="reportDiv">
		
			 
		<!--	<button name="exportPDF" id="exportPDF" onclick="exportPDF()"
				class="btn btn-info">PDF</button> -->
				
			
				<c:if test="${ListShgCreatedBeanSize gt 0}">
				
				
				<div class="col text-center">
					<br />
					<h5><div class="offset-md-3 col-6 formheading" style="text-align:center;"  >Report OC1- Project wise details of Newly Created Self Help Group(SHG) </div></h5>
					<br />
				</div>
				
				<button name="exportExcel" id="exportExcel" onclick="exportExcelNew('${projectid}','${projname}', '${headtype}', '${stname}', '${distname}')"
				class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="exportPDFNew('${projectid}','${projname}', '${headtype}', '${stname}', '${distname}')"
				class="btn btn-info">PDF</button>
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
				<c:set var="shg_id" value="" />
				<c:set var="count" value="1" />
				<table id="tblReport" cellspacing="0"  style="width: 80%; margin-left: auto; margin-right: auto;">
					<tr>
						<th colspan="16" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp;
						Project : &nbsp; <c:out value='${projname}' /> &nbsp;&nbsp;&nbsp; Head : &nbsp;Newly Created Self Help Group(SHG) </th>
						
					</tr>
					<tr>
						<th rowspan="2" style="text-align: center;" width="2%">S.No.</th>
						<th rowspan="2" style="text-align: center;" width="3%">No. of Newly Created SHG</th>
						<th rowspan="2" style="text-align: center;" width="6%">Name of SHG</th>
						<th rowspan="2" style="text-align: center;" width="6%">Department/ Scheme</th>
						<th rowspan="2" style="text-align: center;" width="4%">Date of registration</th>
						<th rowspan="2" style="text-align: center;" width="3%">Amount of Revolving Fund (in Rs.)</th>
						<th colspan="4" class="text-center " width="12%">Total Members</th>
						<th rowspan="2" class="text-center " width="4%">Core Activity</th>
						<th rowspan="2" class="text-center " width="3%">No. of SHG members having PM Bima Yojana</th>
						<th rowspan="2" class="text-center " width="3%">Thrift & Credit</th>
						<th rowspan="2" class="text-center " width="3%">Avg. turnover of SHG</th>
						<th rowspan="2" class="text-center " width="3%">Avg. Income Per Annum</th>
						<th rowspan="2" class="text-center " width="3%">Whether Fedrated</th>
					</tr>
					<tr>
						<th style="text-align: center;" width="3%">SC</th>
						<th style="text-align: center;" width="3%">ST</th>
						<th style="text-align: center;" width="3%">Other</th>
						<th style="text-align: center;" width="3%">Women</th>
					</tr>
					<tr>
						<th style="text-align: center;">1</th>
						<th style="text-align: center;">2</th>
						<th style="text-align: center;">3</th>
						<th style="text-align: center;">4</th>
						<th style="text-align: center;">5</th>
						<th style="text-align: center;">6</th>
						<th style="text-align: center;">7</th>
						<th style="text-align: center;">8</th>
						<th style="text-align: center;">9</th>
						<th style="text-align: center;">10</th>
						<th style="text-align: center;">11</th>
						<th style="text-align: center;">12</th>
						<th style="text-align: center;">13</th>
						<th style="text-align: center;">14</th>
						<th style="text-align: center;">15</th>
						<th style="text-align: center;">16</th>
					</tr>
				<c:forEach items="${ListShgCreatedBean}" var="rpt" varStatus="status">
					<tr>
					
						<c:choose>
							<c:when test="${shg_id ne rpt.shg_id}">
								<c:set var="shg_id" value="${rpt.shg_id}" />
								<td align="center"><c:out value='${count}' /></td>
								<td align="center">${rpt.totalno}</td>
								<c:set var="count" value="${count+1}" />
							</c:when>	
							<c:otherwise>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td align="left">${rpt.name}</td>
						<td align="left">${rpt.schemename}</td>
						<td align="right">${rpt.reg_date}</td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">${rpt.revolving_amount}</fmt:formatNumber></td>
						<td align="right">${rpt.sc}</td>
						<td align="right">${rpt.st}</td>
						<td align="right">${rpt.other}</td>
						<td align="right">${rpt.women}</td>
						<td align="center">${rpt.coreactivity}</td>
						<td align="right">${rpt.pm_bima_yogana}</td>
						<td align="center">${rpt.thrift}</td>
						<td align="right">${rpt.avg_turnover}</td>
						<td align="right">${rpt.avg_income}</td>
						<td align="center">${rpt.federated}</td>
					</tr>
					<c:set var="totalrevolving_amount"
					value="${totalrevolving_amount+rpt.revolving_amount}" />
					<c:set var="totalsc"
					value="${totalsc+rpt.sc}" />
					<c:set var="totalst"
					value="${totalst+rpt.st}" />
					<c:set var="totalother"
					value="${totalother+rpt.other}" />
					<c:set var="totalwomen"
					value="${totalwomen+rpt.women}" />
					<c:set var="totalpm_bima_yogana"
					value="${totalpm_bima_yogana+rpt.pm_bima_yogana}" />
					<c:set var="totalavg_turnover"
					value="${totalavg_turnover+rpt.avg_turnover}" />
					<c:set var="totalavg_income"
					value="${totalavg_income+rpt.avg_income}" />
				</c:forEach>
				<tr>
				<td align="right" colspan="5" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalrevolving_amount}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalsc}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalother}" /></b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totalwomen}" /></b></td>
				<td align="right" class="table-primary"></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpm_bima_yogana}" /></b></td>
				<td align="right" class="table-primary"></td>
				<td align="right" class="table-primary"><b><c:out value="${totalavg_turnover}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalavg_income}"/></b></td> 
				<td align="right" class="table-primary"></td>
				
			</tr>
				</table>
			</c:if>
				<br/>
				
				
				
				<c:if test="${ListShgExistedBeanSize gt 0}">
				
				
				<c:set var="shg_id" value="" />
				<c:set var="count" value="1" />
				<div class="col text-center">
					<br />
					<h5><div class="offset-md-3 col-6 formheading" style="text-align:center;"  >Report OC1- Project wise details of Existing Created Self Help Group(SHG)</div> </h5>
					<br />
				</div>
				<button name="exportExcel" id="exportExcel" onclick="exportExcelExisted('${projectid}','${projname}', '${headtype}', '${stname}', '${distname}')"
				class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="exportPDFExisted('${projectid}','${projname}', '${headtype}', '${stname}', '${distname}')"
				class="btn btn-info">PDF</button>
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
				<table id="tblReport" cellspacing="0"  style="width: 80%; margin-left: auto; margin-right: auto;">
					<tr>
						<th colspan="16" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp;
						Project : &nbsp; <c:out value='${projname}' /> &nbsp;&nbsp;&nbsp; Head : &nbsp; Existing Created Self Help Group(SHG) </th>
					</tr>
					<tr>
						<th rowspan="2" style="text-align: center;" width="2%">S.No.</th>
						<th rowspan="2" style="text-align: center;" width="3%">No. of Existing Created SHG</th>
						<th rowspan="2" style="text-align: center;" width="6%">Name of SHG</th>
						<th rowspan="2" style="text-align: center;" width="6%">Department/ Scheme</th>
						<th rowspan="2" style="text-align: center;" width="4%">Date of registration</th>
						<th rowspan="2" style="text-align: center;" width="3%">Amount of Revolving Fund (in Rs.)</th>
						<th colspan="4" class="text-center " width="12%">Total Members</th>
						<th rowspan="2" class="text-center " width="4%">Core Activity</th>
						<th rowspan="2" class="text-center " width="3%">No. of SHG members having PM Bima Yojana</th>
						<th rowspan="2" class="text-center " width="3%">Thrift & Credit</th>
						<th rowspan="2" class="text-center " width="3%">Avg. turnover of SHG</th>
						<th rowspan="2" class="text-center " width="3%">Avg. Income Per Annum</th>
						<th rowspan="2" class="text-center " width="3%">Whether Fedrated</th>
					</tr>
					<tr>
						<th style="text-align: center;" width="3%">SC</th>
						<th style="text-align: center;" width="3%">ST</th>
						<th style="text-align: center;" width="3%">Other</th>
						<th style="text-align: center;" width="3%">Women</th>
					</tr>
					<tr>
						<th style="text-align: center;">1</th>
						<th style="text-align: center;">2</th>
						<th style="text-align: center;">3</th>
						<th style="text-align: center;">4</th>
						<th style="text-align: center;">5</th>
						<th style="text-align: center;">6</th>
						<th style="text-align: center;">7</th>
						<th style="text-align: center;">8</th>
						<th style="text-align: center;">9</th>
						<th style="text-align: center;">10</th>
						<th style="text-align: center;">11</th>
						<th style="text-align: center;">12</th>
						<th style="text-align: center;">13</th>
						<th style="text-align: center;">14</th>
						<th style="text-align: center;">15</th>
						<th style="text-align: center;">16</th>
					</tr>
				<c:forEach items="${ListShgExistedBean}" var="rpt" varStatus="status">
					<tr>
						<c:choose>
							<c:when test="${shg_id ne rpt.shg_id}">
								<c:set var="shg_id" value="${rpt.shg_id}" />
								<td align="center"><c:out value='${count}' /></td>
								<td align="center">${rpt.totalno}</td>
								<c:set var="count" value="${count+1}" />
							</c:when>	
							<c:otherwise>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td align="left">${rpt.name}</td>
						<td align="left">${rpt.schemename}</td>
						<td align="right">${rpt.reg_date}</td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">${rpt.revolving_amount}</fmt:formatNumber></td>
						<td align="right">${rpt.sc}</td>
						<td align="right">${rpt.st}</td>
						<td align="right">${rpt.other}</td>
						<td align="right">${rpt.women}</td>
						<td align="center">${rpt.coreactivity}</td>
						<td align="right">${rpt.pm_bima_yogana}</td>
						<td align="center">${rpt.thrift}</td>
						<td align="right">${rpt.avg_turnover}</td>
						<td align="right">${rpt.avg_income}</td>
						<td align="center">${rpt.federated}</td>
					</tr>
					<c:set var="totalexrevolving_amount"
					value="${totalexrevolving_amount+rpt.revolving_amount}" />
					<c:set var="totalexsc"
					value="${totalexsc+rpt.sc}" />
					<c:set var="totalexst"
					value="${totalexst+rpt.st}" />
					<c:set var="totalexother"
					value="${totalexother+rpt.other}" />
					<c:set var="totalexwomen"
					value="${totalexwomen+rpt.women}" />
					<c:set var="totalexpm_bima_yogana"
					value="${totalexpm_bima_yogana+rpt.pm_bima_yogana}" />
					<c:set var="totalexavg_turnover"
					value="${totalexavg_turnover+rpt.avg_turnover}" />
					<c:set var="totalexavg_income"
					value="${totalexavg_income+rpt.avg_income}" />
				</c:forEach>
				<tr>
				<td align="right" colspan="5" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalexrevolving_amount}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalexsc}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalexst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalexother}" /></b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totalexwomen}" /></b></td>
				<td align="right" class="table-primary"></td>
				<td align="right" class="table-primary"><b><c:out value="${totalexpm_bima_yogana}" /></b></td>
				<td align="right" class="table-primary"></td>
				<td align="right" class="table-primary"><b><c:out value="${totalexavg_turnover}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalexavg_income}"/></b></td> 
				<td align="right" class="table-primary"></td>
				
			</tr>
				</table>
			</c:if>
			
			<c:if test="${ListUserGBeanSize gt 0}">	
			
			<div class="col text-center">
					<br />
					<h5><div class="offset-md-3 col-6 formheading" style="text-align:center;"  > Report OC1- Project details of User Group</div> </h5>
					<br />
				</div>
				
				<button name="exportExcel" id="exportExcel" onclick="exportExcelNew('${projectid}','${projname}', '${headtype}', '${stname}', '${distname}')"
				class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="exportPDFNew('${projectid}','${projname}', '${headtype}', '${stname}', '${distname}')"
				class="btn btn-info">PDF</button>
			<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			<c:set var="shg_id" value="" />
			<c:set var="count" value="1" />
			
			<table id="tblReport" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;" >
				<thead>
					<tr>
						<th colspan="13" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp;
						Project : &nbsp; <c:out value='${projname}' /> &nbsp;&nbsp;&nbsp; Head : &nbsp; User Group </th>
					</tr>
					<tr>
						<th rowspan="2" width="3%" style="text-align: center;">S.No.</th>
						<th rowspan="2" width="3%" style="text-align: center;">No. of User Group</th>
						<th rowspan="2" width="6%" style="text-align: center;">Name of User Groups</th>
						<th colspan="4" class="text-center " width="10%">Total Members</th>
					</tr>
					<tr>
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Women</th>
					</tr>	
					<tr>
						<th style="text-align: center;">1</th>
						<th style="text-align: center;">2</th>
						<th style="text-align: center;">3</th>
						<th style="text-align: center;">4</th>
						<th style="text-align: center;">5</th>
						<th style="text-align: center;">6</th>
						<th style="text-align: center;">7</th>
					</tr>
				<c:forEach items="${ListUserGBean}" var="rpt" varStatus="status">
					<tr>

						<c:choose>
							<c:when test="${shg_id ne rpt.shg_id}">
								<c:set var="shg_id" value="${rpt.shg_id}" />
								<td align="center"><c:out value='${count}' /></td>
								<td align="center">${rpt.totalno}</td>
								<c:set var="count" value="${count+1}" />
							</c:when>	
							<c:otherwise>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td>${rpt.name}</td>
						<td align="right">${rpt.sc}</td>
						<td align="right">${rpt.st}</td>
						<td align="right">${rpt.other}</td>
						<td align="right">${rpt.women}</td>
					</tr>
					<c:set var="totalsc"
					value="${totalsc+rpt.sc}" />
				<c:set var="totalst"
					value="${totalst+rpt.st}" />
				<c:set var="totalother"
					value="${totalother+rpt.other}" />
				<c:set var="totalwomen"
					value="${totalwomen+rpt.women}" />
					
				</c:forEach>
				
				<tr>
				<td align="right" colspan="3" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalsc}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalother}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalwomen}" /></b></td>
				
			</tr>
				
			</table>
			</c:if>
		</div>

	</div>
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/>
<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>


</body>
</html>


