<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<title>Report OC1- Project wise details</title>
<script type="text/javascript">

function exportExcel(distcd, headtype, stname, distname) {
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distcd;
	document.getElementById("stname").value=stname;
	document.getElementById("distname").value=distname;
	document.rptSHGUserGroup.action="downloadExcelProjSHG";
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
	document.rptSHGUserGroup.action="getSelfHelpGroupProjectDetailView";
	document.rptSHGUserGroup.method="post";
	document.rptSHGUserGroup.submit();
}

function downloadPDF(distcd, headtype, stname, distname)
{
		document.getElementById("headtypeh").value=headtype;
		document.getElementById("distid").value=distcd;
		document.getElementById("stname").value=stname;
		document.getElementById("distname").value=distname;
		document.rptSHGUserGroup.action="downloadPDFProjSHG";
		document.rptSHGUserGroup.method="post";
		document.rptSHGUserGroup.submit();
	
}




</script>
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<br />
			<h5><div class="offset-md-3 col-6 formheading" style="text-align:center;"  >Report OC1- Project wise details of <c:if test="${headtype eq 'shg'}">
								 Self Help Group (SHGs)  </c:if> 
								
								 <c:if test="${headtype eq 'ug'}"> User Group  </c:if>
								</div> </h5>
			<hr />
		</div>
	</div>
</div>
<div class="row">
	<div class="col-2"></div>
	<div class="col-12">

		<form action="getRptshg" method="post" id="getRptshg" name="rptSHGUserGroup">
			<div class="form-row">
			<input type="hidden" name="projectid" id="projectid" value="" />
			<input type="hidden" name="projname" id="projname" value="" />
			<input type="hidden" name="headtypeh" id="headtypeh" value="" />
			<input type="hidden" name="distname" id="distname" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="distid" id="distid" value="" />
			
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
		<c:if test="${beanListGroupSize ne 0 || rptListSize ne 0}">	
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${distid}', '${headtype}', '${stname}', '${distname}')" class="btn btn-info">Excel</button> 
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${distid}', '${headtype}', '${stname}', '${distname}' );" class="btn btn-info">PDF</button>	
		</c:if>
			<!-- <button name="exportExcel" id="exportExcel" onclick="exportExcel()"
				class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="exportPDF()"
				class="btn btn-info">PDF</button> -->
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			<table id="tblReport" cellspacing="0"  style="width: 100%; margin-left: auto; margin-right: auto;">
				<c:if test="${rptListSize gt 0}">
				<tr>
						<th colspan="32" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp;
						Head : &nbsp;<c:if test="${headtype eq 'shg'}"> Self Help Group(SHG) </c:if> 
						<c:if test="${headtype eq 'ug'}"> User Group </c:if></th>
					</tr>
					<tr>
						<th rowspan="3" width="3%" style="text-align: center;">S.No.</th>
						<th rowspan="3" width="10%" style="text-align: center;">Project Name</th>
						<th colspan="10" class="text-center " width="35%">Newly created SHG</th>
						<th colspan="10" class="text-center " width="35%">Existing created SHG</th>
						<th colspan="10" class="text-center " width="35%">Total ( Newly created SHG + Existing created SHG )</th>
					</tr>
					<tr>
						<th rowspan="2" width="5%" style="text-align: center;">No.</th>
						<th rowspan="2" width="5%" style="text-align: center;">Total Revolving Fund (in Rs.)</th>
						<th colspan="8" class="text-center " >Total Members</th>
						<th rowspan="2" width="5%" style="text-align: center;">No.</th>
						<th rowspan="2" width="5%" style="text-align: center;">Total Revolving Fund (in Rs.)</th>
						<th colspan="8" class="text-center " >Total Members</th>
						<th rowspan="2" width="5%" style="text-align: center;">No.</th>
						<th rowspan="2" width="5%" style="text-align: center;">Total Revolving Fund (in Rs.)</th>
						<th colspan="8" class="text-center " >Total Members</th>
					</tr>
				
					<tr>
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
						<th style="text-align: center;">Thrift & Credit</th>
						<th style="text-align: center;">No. of SHG members having PM</th>
						<th style="text-align: center;">No. of Fedrated SHG</th>
						
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
						<th style="text-align: center;">Thrift & Credit</th>
						<th style="text-align: center;">No. of SHG members having PM</th>
						<th style="text-align: center;">No. of Fedrated SHG</th>
						
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
						<th style="text-align: center;">Thrift & Credit</th>
						<th style="text-align: center;">No. of SHG members having PM</th>
						<th style="text-align: center;">No. of Fedrated SHG</th>
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
						<th style="text-align: center;">17</th>
						<th style="text-align: center;">18</th>
						<th style="text-align: center;">19</th>
						<th style="text-align: center;">20</th>
						<th style="text-align: center;">21</th>
						<th style="text-align: center;">22</th>
						<th style="text-align: center;">23</th>
						<th style="text-align: center;">24</th>
						<th style="text-align: center;">25</th>
						<th style="text-align: center;">26</th>
						<th style="text-align: center;">27</th>
						<th style="text-align: center;">28</th>
						<th style="text-align: center;">29</th>
						<th style="text-align: center;">30</th>
						<th style="text-align: center;">31</th>
						<th style="text-align: center;">32</th>
					</tr>
				
				<c:forEach items="${rptList}" var="rpt" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td> <a href="#" onclick="showOnlyProjectWise('${rpt.proj_id}','${rpt.proj_name}', '${distname}', '${headtype}', '${stname}');"> ${rpt.proj_name}</a></td>
						<td style="text-align: right;">${rpt.newno}</td>
						<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${rpt.newrevolving}</fmt:formatNumber></td>
						<td style="text-align: right;">${rpt.newsc}</td>
						<td style="text-align: right;">${rpt.newst}</td>
						<td style="text-align: right;">${rpt.newother}</td>
						<td style="text-align: right;">${rpt.newtotal}</td>
						<td style="text-align: right;">${rpt.newwomen}</td>
						<td style="text-align: right;">${rpt.newthrift}</td>
						<td style="text-align: right;">${rpt.newbimayogana}</td>
						<td style="text-align: right;">${rpt.newfederated}</td>
						
						<td style="text-align: right;">${rpt.oldno}</td>
						<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${rpt.oldrevolving}</fmt:formatNumber></td>
						<td style="text-align: right;">${rpt.oldsc}</td>
						<td style="text-align: right;">${rpt.oldst}</td>
						<td style="text-align: right;">${rpt.oldother}</td>
						<td style="text-align: right;">${rpt.oldtotal}</td>
						<td style="text-align: right;">${rpt.oldwomen}</td>
						<td style="text-align: right;">${rpt.oldthrift}</td>
						<td style="text-align: right;">${rpt.oldbimayogana}</td>
						<td style="text-align: right;">${rpt.oldfederated}</td>
						
						<td style="text-align: right;">${rpt.totno}</td>
						<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2"> ${rpt.totrevolving}</fmt:formatNumber></td>
						<td style="text-align: right;">${rpt.totsc}</td>
						<td style="text-align: right;">${rpt.totst}</td>
						<td style="text-align: right;">${rpt.totother}</td>
						<td style="text-align: right;">${rpt.tottotal}</td>
						<td style="text-align: right;">${rpt.totwomen}</td>
						<td style="text-align: right;">${rpt.totthrift}</td>
						<td style="text-align: right;">${rpt.totbimayogana}</td>
						<td style="text-align: right;">${rpt.totfederated}</td>
						
					</tr>
					
					<c:set var="totalnewno"
					value="${totalnewno+rpt.newno}" />
				<c:set var="totalnewrevolving"
					value="${totalnewrevolving+rpt.newrevolving}" />
				<c:set var="totalnewsc"
					value="${totalnewsc+rpt.newsc}" />
				<c:set var="totalnewst"
					value="${totalnewst+rpt.newst}" />
					<c:set var="totalnewother"
					value="${totalnewother+rpt.newother}" />
					<c:set var="totalnewtotal"
					value="${totalnewtotal+rpt.newtotal}" />
					<c:set var="totalnewwomen"
					value="${totalnewwomen+rpt.newwomen}" />
				<c:set var="totalnewthrift"
					value="${totalnewthrift+rpt.newthrift}" />
				<c:set var="totalnewbimayogana"
					value="${totalnewbimayogana+rpt.newbimayogana}" />
				<c:set var="totalnewfederated"
					value="${totalnewfederated+rpt.newfederated}" />
					
					
					<c:set var="totaloldno"
					value="${totaloldno+rpt.oldno}" />
					<c:set var="totaloldrevolving"
					value="${totaloldrevolving+rpt.oldrevolving}" />
					<c:set var="totaloldsc"
					value="${totaloldsc+rpt.oldsc}" />
				<c:set var="totaloldst"
					value="${totaloldst+rpt.oldst}" />
				<c:set var="totaloldother"
					value="${totaloldother+rpt.oldother}" />
				<c:set var="totaloldtotal"
					value="${totaloldtotal+rpt.oldtotal}" />
					<c:set var="totaloldwomen"
					value="${totaloldwomen+rpt.oldwomen}" />
					<c:set var="totaloldthrift"
					value="${totaloldthrift+rpt.oldthrift}" />
					<c:set var="totaloldbimayogana"
					value="${totaloldbimayogana+rpt.oldbimayogana}" />
				<c:set var="totaloldfederated"
					value="${totaloldfederated+rpt.oldfederated}" />
					
					
				<c:set var="totaltotno"
					value="${totaltotno+rpt.totno}" />
				<c:set var="totaltotrevolving"
					value="${totaltotrevolving+rpt.totrevolving}" />
					<c:set var="totaltotsc"
					value="${totaltotsc+rpt.totsc}" />
				<c:set var="totaltotst"
					value="${totaltotst+rpt.totst}" />
				<c:set var="totaltotother"
					value="${totaltotother+rpt.totother}" />
				<c:set var="totaltottotal"
					value="${totaltottotal+rpt.tottotal}" />
					<c:set var="totaltotwomen"
					value="${totaltotwomen+rpt.totwomen}" />
					<c:set var="totaltotthrift"
					value="${totaltotthrift+rpt.totthrift}" />
					<c:set var="totaltotbimayogana"
					value="${totaltotbimayogana+rpt.totbimayogana}" />
				<c:set var="totaltotfederated"
					value="${totaltotfederated+rpt.totfederated}" />
				</c:forEach>
				
				<tr>
				<td align="right" colspan="2" class="table-primary"><b>Grand Total</b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totalnewno}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalnewrevolving}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewsc}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewother}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewtotal}"/></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewwomen}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewthrift}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewbimayogana}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewfederated}" /></b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totaloldno}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totaloldrevolving}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldsc}"/></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldother}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldtotal}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldwomen}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldthrift}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldbimayogana}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloldfederated}"/></b></td>
				
				
				<td align="right" class="table-primary"><b><c:out value="${totaltotno}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totaltotrevolving}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotsc}"/></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotother}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltottotal}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotwomen}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotthrift}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotbimayogana}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaltotfederated}"/></b></td>
				
			</tr>
			</c:if>
			</table>
			
			<c:if test="${beanListGroupSize gt 0}">	
			<table id="tblReport" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;" >
				<thead>
					<tr>
						<th colspan="8" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp;
						Head : &nbsp;<c:if test="${headtype eq 'shg'}"> Self Help Group(SHG) </c:if> 
						<c:if test="${headtype eq 'ug'}"> User Group </c:if></th>
					</tr>
					<tr>
						<th rowspan="3" width="3%" style="text-align: center;">S.No.</th>
						<th rowspan="3" width="10%" style="text-align: center;">Project Name</th>
						<th colspan="6" class="text-center " width="35%">User Group</th>
					</tr>
					<tr>
						<th rowspan="2" width="5%" style="text-align: center;">No.</th>
						<th colspan="5" class="text-center " >Total Members</th>
					</tr>
					<tr>
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
					</tr>	
				<c:forEach items="${beanListGroup}" var="rpt" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td> <a href="#" onclick="showOnlyProjectWise('${rpt.proj_id}','${rpt.proj_name}', '${distname}', '${headtype}', '${stname}');"> ${rpt.proj_name}</a></td>
						<td align="right">${rpt.newno}</td>
						<td align="right">${rpt.newsc}</td>
						<td align="right" >${rpt.newst}</td>
						<td align="right">${rpt.newother}</td>
						<td align="right" >${rpt.newtotal}</td>
						<td align="right">${rpt.newwomen}</td>
					</tr>
					<c:set var="totalnewno"
					value="${totalnewno+rpt.newno}" />
				<c:set var="totalnewsc"
					value="${totalnewsc+rpt.newsc}" />
				<c:set var="totalnewst"
					value="${totalnewst+rpt.newst}" />
				<c:set var="totalnewother"
					value="${totalnewother+rpt.newother}" />
					<c:set var="totalnewtotal"
					value="${totalnewtotal+rpt.newtotal}" />
					<c:set var="totalnewwomen"
					value="${totalnewwomen+rpt.newwomen}" />
					
				</c:forEach>
				
				<tr>
				<td align="right" colspan="2" class="table-primary"><b>Grand Total</b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewno}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewsc}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewst}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewother}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewtotal}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalnewwomen}"/></b></td>
				
			</tr>
					
			</table>
			</c:if>
			
			
			<c:if test="${beanListGroupSize eq 0 && rptListSize eq 0}">	
			<table id="tblReport" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;" >
				<thead>
					<tr>
						<th colspan="8" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp;
						Head : &nbsp;<c:if test="${headtype eq 'shg'}"> Self Help Group(SHG) </c:if> 
						<c:if test="${headtype eq 'ug'}"> User Group </c:if></th>
					</tr>
					<tr>
						<th rowspan="3" width="3%" style="text-align: center;">S.No.</th>
						<th rowspan="3" width="10%" style="text-align: center;">Project Name</th>
						<th colspan="6" class="text-center " width="35%">User Group</th>
					</tr>
					<tr>
						<th rowspan="2" width="5%" style="text-align: center;">No.</th>
						<th colspan="5" class="text-center " >Total Members</th>
					</tr>
					<tr>
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
					</tr>	
				
				
				<tr>
				<td align="center" colspan="8" class="table-primary"><b>No Data found!</b></td>
				
				
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


