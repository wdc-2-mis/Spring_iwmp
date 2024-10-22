<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<title>Report OC3</title>


<script type="text/javascript">
function showProjectWise(dCode, distname, headtype, stname)

{
	
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=dCode;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.LiveProdEpa.action="getRptLiveProdEpaProjectWise";
	document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}

function downloadLivelihoodPDF(stname, distname, headtype, distid, projcode )
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.getElementById("projcode").value=projcode;
	document.LiveProdEpa.action="downloadProjectDetailsofLivelihoodPDF";
	document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}
function downloadProdctionPDF(stname, distname, headtype, distid, projcode )
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.getElementById("projcode").value=projcode;
	document.LiveProdEpa.action="downloadProjectDetailsofProductionPDF";
	document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}
function downloadEpaProjPDF(stname, distname, headtype, distid )
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.LiveProdEpa.action="downloadProjectDetailsOfEPAPDF";
	document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}

function exportExcell(stname, distname, headtype, distid, projcode)
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.getElementById("projcode").value=projcode;
	document.LiveProdEpa.action="downloadExcelRptLiveProdEpaSingleProjectWise";
	document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}
</script>


<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<br />
		<div class="offset-md-3 col-6 formheading" style="text-align: center;">	<h5><label id="head">
				Report OC3- Project wise details of
				<c:if test="${headtype eq 'production'}"> Production System</c:if>
				<c:if test="${headtype eq 'epa'}">Entry point Activities(EPAs)</c:if>
				<c:if test="${headtype eq 'livelihood'}"> Livelihood Activities for the Asst-less Person</c:if>
			</label></h5></div>
			<hr />
		</div>
	</div>
</div>

<div class="row">
	<div class="col-2"></div>
	<div class="col-12">

		 <form action="getRptLiveProdEpaProjectWise" method="post" id="LiveProdEpa" name="LiveProdEpa">
			<div class="form-row">
			<input type="hidden" name="distid" id="distid" value="" />
			<input type="hidden" name="headtypeh" id="headtypeh" value="" />
			<input type="hidden" name="distname" id="distname" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="projcode" id="projcode" value="" />
			
				
			</div>
		</form> 

		<div class="col-1"></div>
		<div class="col" id="reportDiv">
		
		<button name="exportExcel" id="exportExcel" onclick="exportExcell('${stname}','${distname}','${headtype}','${distid}','${projcode }')"class="btn btn-info">Excel</button>
<%-- 		<button name="exportExcel" id="exportExcel" onclick="exportProdDetailExcel('${stname}','${distname}','${headtype}','${distid}','${projcode }')"class="btn btn-info">Excel</button> --%>
			<c:if test="${headtype eq 'epa'}">
			<button name="exportPDF" id="exportPDF" onclick="downloadEpaProjPDF('${stname}','${distname}','${headtype}','${distid}')"
				class="btn btn-info">PDF</button> </c:if>
					
				<c:if test="${headtype eq 'livelihood'}">
			<button name="exportPDF" id="exportPDF" onclick="downloadLivelihoodPDF('${stname}','${distname}','${headtype}','${distid}','${projcode }')"
				class="btn btn-info">PDF</button> </c:if>
				
				<c:if test="${headtype eq 'production'}">
			<button name="exportPDF" id="exportPDF" onclick="downloadProdctionPDF('${stname}','${distname}','${headtype}','${distid}','${projcode }')"
				class="btn btn-info">PDF</button> </c:if>
				
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
				
			<table id="dtBasicExample" cellspacing="0" style="width: 80%; margin-left: auto; margin-right: auto;">
				<thead>
					<tr>
						<th colspan="12" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; 
						District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp; 
						Head : &nbsp;<c:if test="${headtype eq 'production'}"> Production System</c:if> <c:if test="${headtype eq 'epa'}">Entry point Activities(EPAs)</c:if>
						<c:if test="${headtype eq 'livelihood'}"> Livelihood Activities for the Asst-less Person</c:if></th>
					</tr>
					<tr>
						<th rowspan="2" width="5%" style="text-align: center;">S.No.</th>
						<th rowspan="2" width="15%" style="text-align: center;">Project Name</th>
						<th rowspan="2" width="5%" style="text-align: center;">Name of Activities</th>
						<th rowspan="2" width="5%" style="text-align: center;">No. of Activities</th>
						<c:if test="${headtype ne 'epa'}">	
							<th colspan=5 class="text-center " width="35%">No. of beneficiaries</th>
							<th rowspan="2" width="5%" style="text-align: center;">Average Income of Beneficiaries Prior to 
							<c:if test="${headtype eq 'livelihood'}"> Livelihood activities(in Rs.)</c:if>
							<c:if test="${headtype eq 'production'}"> Production System (in Rs.)</c:if>
							</th>
							<th rowspan="2" width="5%" style="text-align: center;">Average Income of Beneficiaries Post to 
							<c:if test="${headtype eq 'livelihood'}"> Livelihood activities(in Rs.)</c:if>
							<c:if test="${headtype eq 'production'}"> Production System (in Rs.)</c:if>
							</th>
						</c:if>
						<th rowspan="2" width="5%" style="text-align: center;">Total no. of Work Created</th>
					</tr>
				<c:if test="${headtype ne 'epa'}">	
					<tr>
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
					</tr>
				</c:if>
				</thead>
				<c:set var="cntt" value="0"/>
				<c:set var="count" value="1" />
				<c:if test="${projwiseListSize gt 0}">
				<c:forEach items="${projwiseList}" var="rpt" varStatus="status">
					<tr>

					<%-- 	<td align="center">${status.count}</td>
						<td>${rpt.proj_name}</td> --%>
						<c:choose>
									<c:when
										test="${projname ne rpt.proj_name}">
										<c:set var="projname"
											value="${rpt.proj_name}" />
										<td align="center"><c:out value="${count}" /></td>
										<td><c:out value='${projname}' /></td>
										<c:set var="count" value="${count+1}" />
									</c:when>
									<c:otherwise>
										<td></td>
										<td></td>
									</c:otherwise>
								</c:choose>
						<td>${rpt.activitydesc}</td>
						<td align="right">${rpt.noactivity}</td>
						<c:if test="${headtype ne 'epa'}">	
							<td align="right">${rpt.sc}</td>
							<td align="right">${rpt.st}</td>
							<td align="right">${rpt.other}</td>
							<td align="right">${rpt.total}</td>
							<td align="right">${rpt.women}</td>
							<td align="right"><fmt:formatNumber minFractionDigits="2"> ${rpt.pre_avg_income}</fmt:formatNumber></td>
							<td align="right"><fmt:formatNumber minFractionDigits="2">${rpt.post_avg_income}</fmt:formatNumber></td>
						</c:if>
						<td align="right">${rpt.liveproducwork}</td>
					</tr>
					<c:set var="totalnoactivity" 
					value="${totalnoactivity+rpt.noactivity}" />  
  					<c:set var="totalsc"  
 					value="${totalsc+rpt.sc}" />  
 					<c:set var="totalst" 
  					value="${totalst+rpt.st}" />  
 					<c:set var="totalother"  
  					value="${totalother+rpt.other}" />  
  					<c:set var="totaltotal"  
  					value="${totaltotal+rpt.total}" />  
  					<c:set var="totalwomen"  
  					value="${totalwomen+rpt.women}" /> 
  							<c:set var="cntt" value="${cntt+rpt.cnt}" />
  					<c:set var="totalpre_avg_income" 
  					value="${totalpre_avg_income+rpt.pre_avg_income}" /> 
  					<c:set var="totalpost_avg_income"  
  					value="${totalpost_avg_income+rpt.post_avg_income}" /> 
  					
  					<c:set var="liveproducworkid"  
  					value="${liveproducworkid+rpt.liveproducwork}" />  
					
				</c:forEach>
				<c:if test="${headtype eq 'epa'}">
				<tr>
					<td align="right" class="table-primary"></td>
					<td align="right" class="table-primary"></td>
					<td align="right"  class="table-primary" ><b>Grand Total</b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td> 
	 				<td  align="right" class="table-primary"><b><c:out value="${liveproducworkid}"/></b></td>  
				 </tr>
				</c:if>
				<c:if test="${headtype ne 'epa'}">
				<tr> 
				<td class="table-primary"></td>
				<td class="table-primary"></td>
	 				<td align="right"  class="table-primary"><b>Grand Total </b><c:out value="${cnt}"/></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td> 
					<td align="right" class="table-primary"><b><c:out value="${totalsc}" /></b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalst}" /></b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalother}" /></b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totaltotal}" /></b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalwomen}"/></b></td> 
	 				<td  align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalpre_avg_income/cntt}"/></fmt:formatNumber></b></td>  
	 				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalpost_avg_income/cntt}"/></fmt:formatNumber></b></td>  
					<td align="right" class="table-primary"><b><c:out value="${liveproducworkid}"/></b></td>  
				</tr> 
				</c:if>
				
</c:if>
				
					<c:if test="${projwiseListSize==0}">
				<tr> <td align="center"  class="table-primary" colspan="6"><b>Data Not Found</b></td> </tr>
					</c:if>
			</table>


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


