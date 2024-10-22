<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<title>Report OC3</title>

<script type="text/javascript">
function showProjectWise(dCode, distname, headtype, stname)
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("distid").value=dCode;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.LiveProdEpa.action="getRptLiveProdEpaProjectAvgWise";
    document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}

function downloadLivPDF( stname, stcd, headtype)
{		
		document.getElementById("headtypeh").value=headtype;
 		document.getElementById("stcd").value=stcd;
		document.getElementById("stname").value=stname;
		document.LiveProdEpa.action="downloadDistLivelihoodPDF";
		document.LiveProdEpa.method="post";
		document.LiveProdEpa.submit();
	
}
function downloadProductionPDF( stname, stcd, headtype)
{		
		document.getElementById("headtypeh").value=headtype;
 		document.getElementById("stcd").value=stcd;
		document.getElementById("stname").value=stname;
		document.LiveProdEpa.action="downloadProductionPDF";
		document.LiveProdEpa.method="post";
		document.LiveProdEpa.submit();
	
}
function downloadEPAPDF( stname, stcd, headtype)
{		
		document.getElementById("headtypeh").value=headtype;
 		document.getElementById("stcd").value=stcd;
		document.getElementById("stname").value=stname;
		document.LiveProdEpa.action="downloadEPAPDF";
		document.LiveProdEpa.method="post";
		document.LiveProdEpa.submit();
	
}

function exportExcel(stname, stcd, headtype)
{
		document.getElementById("headtypeh").value=headtype;
		document.getElementById("stcd").value=stcd;
		document.getElementById("stname").value=stname;
		document.LiveProdEpa.action="downloadExcelRptLiveProdEpaDist";
		document.LiveProdEpa.method="post";
		document.LiveProdEpa.submit();
}

</script>

<div class="col-2"></div>
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<br />
			<div class="offset-md-3 col-6 formheading" style="text-align: center;"><h5><label id="head">Report OC3- District wise details of <c:if test="${headtype eq 'production'}"> Production System</c:if> 
													<c:if test="${headtype eq 'epa'}">Entry point Activities(EPAs)</c:if>
						<c:if test="${headtype eq 'livelihood'}"> Livelihood Activities for the Asst-less Person</c:if> </label></h5></div>
			<hr />
		</div>
	</div>
</div>

<div class="row">
	<div class="col-2"></div>
	<div class="col-12">

		 <form action="getRptLiveProdEpaProjectAvgWise" method="post" id="LiveProdEpa" name="LiveProdEpa">
			<div class="form-row">
			<input type="hidden" name="distid" id="distid" value="" />
			<input type="hidden" name="headtypeh" id="headtypeh" value="" />
			<input type="hidden" name="distname" id="distname" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="stcd" id="stcd" value="" />
			
			</div>
		</form> 
		

		<div class="col-1"></div>
		<div class="col" id="reportDiv">
		
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stname}','${stcd}','${headtype }')"class="btn btn-info">Excel</button>
		
		<c:if test="${headtype eq 'livelihood'}">
		<button name="exportPDF" id="exportPDF" onclick="downloadLivPDF('${stname}','${stcd}','${headtype }')"  
				class="btn btn-info">PDF</button> </c:if>
				<c:if test="${headtype eq 'production'}">
		<button name="exportPDF" id="exportPDF" onclick="downloadProductionPDF('${stname}','${stcd}','${headtype }')"
				class="btn btn-info">PDF</button> </c:if>
			<c:if test="${headtype eq 'epa'}">
		<button name="exportPDF" id="exportPDF" onclick="downloadEPAPDF('${stname}','${stcd}','${headtype }')"
				class="btn btn-info">PDF</button> </c:if>
			
			
				<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			<table id="dtBasicExample" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;">
				<thead>
					<tr>
						<th colspan="9" >State : &nbsp; <c:out value='${stname}' /> &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
						Head : &nbsp;<c:if test="${headtype eq 'production'}"> Production System</c:if> <c:if test="${headtype eq 'epa'}">Entry point Activities(EPAs)</c:if>
						<c:if test="${headtype eq 'livelihood'}"> Livelihood Activities for the Asst-less Person</c:if></th>
					</tr>
					<tr>
						<th rowspan="2" width="5%" style="text-align: center;">S.No.</th>
						<th rowspan="2" width="15%" style="text-align: center;">District Name</th>
						<th rowspan="2" width="5%" style="text-align: center;">Total no. of Activities</th>
						<c:if test="${headtype ne 'epa'}">	
							<th colspan=5 class="text-center " width="35%">Total no. of beneficiaries</th>
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

				<c:forEach items="${distList}" var="rpt" varStatus="status">
					<tr>

						<td align="center">${status.count}</td>
						<td><a href="#" onclick="showProjectWise('<c:out value='${rpt.distcode}'/>', '<c:out value='${rpt.distname}'/>', '<c:out value='${headtype}'/>', '${stname}');">${rpt.distname}</a></td>
						<td align="right">${rpt.noactivity}</td>
						<c:if test="${headtype ne 'epa'}">	
							<td align="right">${rpt.sc}</td>
							<td align="right">${rpt.st}</td>
							<td align="right">${rpt.other}</td>
							<td align="right">${rpt.total}</td>
							<td align="right">${rpt.women}</td>
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
  					<c:set var="liveproducworkid"  
  					value="${liveproducworkid+rpt.liveproducwork}" />  
					
				</c:forEach>
				<c:if test="${headtype eq 'epa'}">
				<tr>
					<td class="table-primary"></td>
					<td class="table-primary" align="right"><b>Grand Total</b></td> 
	 				<td align="right"class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td> 
	 				<td align="right"class="table-primary"><b><c:out value="${liveproducworkid}"/></b></td>  
				 </tr>
				</c:if>
				<c:if test="${headtype ne 'epa'}">
				<tr> 
					<td class="table-primary"></td>
	 				<td align="right" class="table-primary" align="center"><b>Grand Total</b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td> 
					<td  align="right" class="table-primary"><b><c:out value="${totalsc}" /></b></td> 
	 				<td  align="right" class="table-primary"><b><c:out value="${totalst}" /></b></td> 
	 				<td  align="right" class="table-primary"><b><c:out value="${totalother}" /></b></td> 
	 				<td  align="right" class="table-primary"><b><c:out value="${totaltotal}" /></b></td> 
	 				<td align="right" class="table-primary"><b><c:out value="${totalwomen}"/></b></td>  
					<td align="right" class="table-primary"><b><c:out value="${liveproducworkid}"/></b></td>  
				</tr> 
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


