<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<title>Report OC3</title>


<script type="text/javascript">
function showProjectWise(dCode, distname, pCode, headtype, stname)
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=dCode;
	document.getElementById("distname").value=distname;
	document.getElementById("projcode").value=pCode;
	document.getElementById("stname").value=stname;
	document.LiveProdSEpa.action="getRptLiveProdEpaSingleProjectWise";
	document.LiveProdSEpa.method="post";
	document.LiveProdSEpa.submit();
}
function downloadPDF(stname, distname, headtype, distid )
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.LiveProdSEpa.action="downloadProjectLivelihoodPDF";
	document.LiveProdSEpa.method="post";
	document.LiveProdSEpa.submit();
}
function downloadProdPDF(stname, distname, headtype, distid )
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.LiveProdSEpa.action="downloadProjectProductionPDF";
	document.LiveProdSEpa.method="post";
	document.LiveProdSEpa.submit();
}

function exportExcell(stname, distname, headtype, distid)
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=distid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.LiveProdSEpa.action="downloadExcelRptLiveProdEpaProjectAvgWise";
	document.LiveProdSEpa.method="post";
	document.LiveProdSEpa.submit();
}

</script>

<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<br/>
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

		 <form action="getRptLiveProdEpaSingleProjectWise" method="post" id="LiveProdSEpa" name="LiveProdSEpa">
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
			
			<button name="exportExcel" id="exportExcel" onclick="exportExcell('${stname}','${distname}','${headtype}','${distid}')"class="btn btn-info">Excel</button>
			
				<c:if test="${headtype eq 'livelihood'}">
			<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stname}','${distname}','${headtype}','${distid}')"
				class="btn btn-info">PDF</button> </c:if>
				<c:if test="${headtype eq 'production'}">
			<button name="exportPDF" id="exportPDF" onclick="downloadProdPDF('${stname}','${distname}','${headtype}','${distid}')"
				class="btn btn-info">PDF</button> </c:if>
				
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			<table id="dtBasicExample" cellspacing="0" style="width: 80%; margin-left: auto; margin-right: auto;">
				<thead>
					<tr>
						<th colspan="10" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; 
						District : &nbsp; <c:out value='${distname}' /> &nbsp;&nbsp;&nbsp; 
						Head : &nbsp;<c:if test="${headtype eq 'production'}"> Production System</c:if> <c:if test="${headtype eq 'epa'}">Entry point Activities(EPAs)</c:if>
						<c:if test="${headtype eq 'livelihood'}"> Livelihood Activities for the Asst-less Person</c:if></th>
					</tr>
					<tr>
						<th rowspan="2" width="5%" style="text-align: center;">S.No.</th>
						<th rowspan="2" width="15%" style="text-align: center;">Project Name</th>
						<th rowspan="2" width="5%" style="text-align: center;">Total No. of Activities</th>
						<c:if test="${headtype ne 'epa'}">	
							<th rowspan="2" width="5%" style="text-align: center;">Average Income of Beneficiaries Prior to 
							<c:if test="${headtype eq 'livelihood'}"> Livelihood activities(in Rs.)</c:if>
							<c:if test="${headtype eq 'production'}"> Production System(in Rs.)</c:if>
							</th>
							<th rowspan="2" width="5%" style="text-align: center;">Average Income of Beneficiaries Post to 
							<c:if test="${headtype eq 'livelihood'}"> Livelihood activities(in Rs.)</c:if>
							<c:if test="${headtype eq 'production'}"> Production System(in Rs.)</c:if>
							</th>
							<th rowspan="2" width="5%" style="text-align: center;">Difference</th>
						</c:if>
						
					</tr>
				
				</thead>
				<c:set var="cnt" value="0" />
				<c:set var="count" value="1" />
				<c:if test="${projavgwiseListSize gt 0}">
				<c:forEach items="${projavgwiseList}" var="rpt" varStatus="status">
					<tr>

					<%-- 	<td align="center">${status.count}</td>
						<td>${rpt.proj_name}</td> --%>
				    	<td align="center">${status.count}</td>				
						<td><a href="#" onclick="showProjectWise('<c:out value='${distcode}'/>', '<c:out value='${distname}'/>', '<c:out value='${rpt.proj_id}'/>', '<c:out value='${headtype}'/>', '${stname}');">${rpt.proj_name}</a></td>		
						<td align="right">${rpt.noactivity}</td>
						<c:if test="${headtype ne 'epa'}">	
							
							<td align="right"><fmt:formatNumber minFractionDigits="2"> ${rpt.pre_total_avg_income}</fmt:formatNumber></td>
							<td align="right"><fmt:formatNumber minFractionDigits="2">${rpt.post_total_avg_income}</fmt:formatNumber></td>
							<td align="right"><fmt:formatNumber minFractionDigits="2">${rpt.difference}</fmt:formatNumber></td>
						</c:if>
						
					</tr>
					
					<c:set var="totalnoactivity" 
					value="${totalnoactivity+rpt.noactivity}" /> 
					
					<c:set var="cnt" value="${cnt+1}" />
					<c:set var="totalpre_avg_income"  
  					value="${totalpre_avg_income+rpt.pre_total_avg_income}" />
  					
  					<c:set var="totalpost_avg_income"  
  					value="${totalpost_avg_income+rpt.post_total_avg_income}" />
  					
  					<c:set var="difference"  
  					value="${difference+rpt.difference}" />
				</c:forEach>
				
				<c:if test="${headtype ne 'epa'}">
				<tr> 
				<td class="table-primary"></td>
				
	 				<td align="right"  class="table-primary"><b>Grand Total</b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td> 
					 <td  align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalpre_avg_income/cnt}"/></fmt:formatNumber></b></td>  
	 				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${totalpost_avg_income/cnt}"/></fmt:formatNumber></b></td>
					<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="2"><c:out value="${difference/cnt}"/></fmt:formatNumber></b></td>
				</tr> 
				</c:if>
				
				</c:if>
				
					<c:if test="${projavgwiseListSize==0}">
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


