<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 
<title>Report PF5- State/UT wise Expenditure</title>
<script type="text/javascript">
function downloadPDF()
{		
		document.ExpRpt.action="downloadStateExpenditurePFMSPDF";
		document.ExpRpt.method="post";
		document.ExpRpt.submit();
	
}
function downloadDistExpndtr(stname, stcode)
{		
		document.getElementById("stname").value=stname;
	document.getElementById("stcode").value=stcode;
		document.ExpRpt.action="downloadDistrictExpenditurePFMSPDF";
		document.ExpRpt.method="post";
		document.ExpRpt.submit();
	
}
function downloadProjectExpndtr(distname, dcode)
{		
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.ExpRpt.action="downloadProjectExpenditurePFMSPDF";
		document.ExpRpt.method="post";
		document.ExpRpt.submit();
	
}

function exportExcel()
{
		document.ExpRpt.action="downloadExcelpfmsStwiseExpndr";
		document.ExpRpt.method="post";
		document.ExpRpt.submit();
	
}

function exportDistExcel(stname, stcode)
{		
		document.getElementById("stname").value=stname;
		document.getElementById("stcode").value=stcode;
		document.ExpRpt.action="downloadExcelpfmsDistwiseExpndtr";
		document.ExpRpt.method="post";
		document.ExpRpt.submit();
	
}

function exportProjExcel(distname, dcode)
{		
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.ExpRpt.action="downloadExcelpfmsProjwiseExpndtr";
		document.ExpRpt.method="post";
		document.ExpRpt.submit();
	
}

</script>

<form action="downloadStateExpenditurePFMSPDF" method="post" id="ExpRpt" name="ExpRpt">
			  <input type="hidden" name="stname" id="stname" value="" />
			   <input type="hidden" name="stcode" id="stcode" value="" />
			    <input type="hidden" name="dcode" id="dcode" value="" />
			     <input type="hidden" name="distname" id="distname" value="" />
			</form>

<div class="container-fluid">
	
	
	<c:if test="${stwiselist ne null}">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id ="head">Report PF5 - State/UT wise Expenditure</label></h5>
	</div>
	<c:set var ="level" value ="1"/>
	<div class="col-2" ></div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<div class="form-group  col-md-12">
		<div class="message">
			<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong> This Report is based on the mapping of PFMS Transactions with their respective Project Names.</label>
		</div>
	</div> 
	<table id="dtBasicExample" cellspacing="0" class="table"    >
  	<thead>

    	<tr>
      		<th class="text-center">S.No.</th>
      		<th class="text-center">State Name</th>
      		<th class="text-center">Total Sanctioned Amount(In Lakhs)</th>
      		<th class="text-center">Central Share(In Lakhs)</th>
      		<th class="text-center">State Share(In Lakhs)</th>
      		<th class="text-center">Total Expenditure(In Lakhs)</th>
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
    	
		<c:forEach items="${stwiselist}" var="proj" varStatus="status">
		<c:if test = "${not fn:contains(proj.stname,'UT of')}">
		<c:set var ="cnt" value ="${cnt + 1}"/>
								<tr>
									<td align="center">${cnt}</td>
									<td><a href="pfmsDistwiseExpndtr?stcode=${proj.stcode}">${proj.stname}</a></td>
								    <td align="right"><fmt:formatNumber value ="${proj.project_cost}" type="number" minFractionDigits="2"/></td>
								    <td align="right"><fmt:formatNumber value ="${proj.centralshare}" type="number" minFractionDigits="2"/></td>
								    <td align="right"><fmt:formatNumber value ="${proj.stateshare}" type="number" minFractionDigits="2"/></td>
									<td align="right"><fmt:formatNumber value = "${proj.stateexpen}" type="number" maxFractionDigits="2"/></td>
								</tr>
								<c:set var ="totSancAmt" value= "${totSancAmt + proj.project_cost}"/>
								<c:set var ="totcentshre" value= "${totcentshre + proj.centralshare}"/>
								<c:set var ="totstshre" value= "${totstshre + proj.stateshare}"/>
								<c:set var ="totStExpndtr" value= "${totStExpndtr + proj.stateexpen}"/>
								</c:if>
		</c:forEach>
		<tr> 
		<td class="table-primary"></td>
		<td align="right" class="table-primary"><b>Grand Total </b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totSancAmt}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totcentshre}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totstshre}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totStExpndtr}" /></fmt:formatNumber></b></td>
		</tr>
		<tr>
      		<th class="text-center">S.No.</th>
      		<th class="text-center">UT Name</th>
      		<th class="text-center">Total Sanctioned Amount</th>
      		<th class="text-center">Central Share(In Lakhs)</th>
      		<th class="text-center">State Share(In Lakhs)</th>
      		<th class="text-center">Total Expenditure(In Lakhs)</th>
      		</tr>
      		<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
		</tr>
		<c:forEach items="${stwiselist}" var="proj" varStatus="status">
		<c:if test = "${fn:contains(proj.stname,'UT of')}">
		<c:set var ="cntUt" value ="${cntUt + 1}"/>
								<tr>
									<td align="center">${cntUt}</td>
									<td><a href="pfmsDistwiseExpndtr?stcode=${proj.stcode}">${proj.stname}</a></td>
								    <td align="right"><fmt:formatNumber value ="${proj.project_cost}" type="number" minFractionDigits="2"/></td>
								    <td align="right"><fmt:formatNumber value ="${proj.centralshare}" type="number" minFractionDigits="2"/></td>
								    <td align="right"><fmt:formatNumber value ="${proj.stateshare}" type="number" minFractionDigits="2"/></td>
									<td align="right"><fmt:formatNumber value = "${proj.stateexpen}" type="number" maxFractionDigits="2"/></td>
								</tr>
								<c:set var ="totUTSancAmt" value= "${totUTSancAmt + proj.project_cost}"/>
								<c:set var ="totUtcentshre" value= "${totUtcentshre + proj.centralshare}"/>
								<c:set var ="totUtshre" value= "${totUtshre + proj.stateshare}"/>
								<c:set var ="totUtExpndtr" value= "${totUtExpndtr + proj.stateexpen}"/>
								</c:if>
		</c:forEach>
		<tr>
		<td class="table-primary"></td>
		<td align="right" class="table-primary"><b>Grand Total </b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totUTSancAmt}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totUtcentshre}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totUtshre}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totUtExpndtr}" /></fmt:formatNumber></b></td>
		</tr>
      	</table>
      	</c:if>
      		
      	<c:if test="${distwiseExpList ne null}">
      	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF5 - District-wise Project Expenditure for State   "<c:out value="${stname}"/>" </label></h5>
	</div>
	<c:set var ="level" value ="2"/>
	<input type ="hidden" name = "state" id = "state" value ="${stname}" class="form-control"/>
	<div class="col-2" ></div>
		<button name="exportExcel" id="exportExcel" onclick="exportDistExcel('${stname}','${stcode }')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadDistExpndtr('${stname}','${stcode }')" class="btn btn-info">PDF</button> 
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<div class="form-group  col-md-12">
		<div class="message">
			<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong> This Report is based on the mapping of PFMS Transactions with their respective Project Names.</label>
		</div>
	</div>
	<table id="dtBasicExample" cellspacing="0" class="table"    >
  	<thead>

    	<tr>
      		<th class="text-center">S.No.</th>
      		<th class="text-center">District Name</th>
      		<th class="text-center">Total Sanctioned Amount(In Lakhs)</th>
      		<th class="text-center">Total Expenditure(In Lakhs)</th>
      		</tr>
      		
      		</thead>
      		
      		<tbody>
      		<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			
			</tr>
    	
			<c:forEach items="${distwiseExpList}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td><a href="pfmsProjwiseExpndtr?dcode=${proj.dcode}"><c:out value ="${proj.distname}"/></a></td>
									 <td align="right"><fmt:formatNumber value ="${proj.project_cost}"
							    		type="number" minFractionDigits="2"/> </td>
									<td align="right"><fmt:formatNumber value = "${proj.distexpen}"
 									type="number" maxFractionDigits="2"/> 
									</td>
									</tr>
									<c:set var ="totDistSancAmt" value= "${totDistSancAmt + proj.project_cost}"/>
								<c:set var ="totDistExpndtr" value= "${totDistExpndtr + proj.distexpen}"/>
									</c:forEach>
		<tr>
		<td class="table-primary"></td>
		<td align="right" class="table-primary"><b>Grand Total </b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"> <c:out value="${totDistSancAmt}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totDistExpndtr}" /></fmt:formatNumber></b></td>
		</tr>
									
      		</table>
      		</c:if>
      		
      		<c:if test="${projwiseExpList ne null}">
      	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF5 - Project-wise Expenditure for District '<c:out value="${distname}"/>' </label></h5>
	</div>
	<c:set var ="level" value ="3"/>
	<input type="hidden" name ="district" id ="district" value="${distname}" class ="form-control" />
	<div class="col-2" ></div>
	<button name="exportExcel" id="exportExcel" onclick="exportProjExcel('${distname}','${dcode }')" class="btn btn-info">Excel</button>

<button name="exportPDF" id="exportPDF" onclick="downloadProjectExpndtr('${distname}','${dcode }')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<div class="form-group  col-md-12">
		<div class="message">
			<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong> This Report is based on the mapping of PFMS Transactions with their respective Project Names.</label>
		</div>
	</div>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>

    	<tr>
      		<th class="text-center">S.No.</th>
      		<th class="text-center">Project Name</th>
      		<th class="text-center">Total Sanctioned Amount(In Lakhs)</th>
      		<th class="text-center">Total Expenditure(In Lakhs)</th>
      		</tr>

      		</thead>
      		
      		<tbody>
    	      		<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			</tr>
			<c:forEach items="${projwiseExpList}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td><c:out value ="${proj.projname}"/></td>
									<td align="right"><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value ="${proj.project_cost}"/></fmt:formatNumber></td>
									 <td align="right"><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value ="${proj.projexpen}"/></fmt:formatNumber> </td>
									</tr>
								<c:set var ="totProjSancAmt" value= "${totProjSancAmt + proj.project_cost}"/>
								<c:set var ="totProjExpndtr" value= "${totProjExpndtr + proj.projexpen}"/>
									</c:forEach>
		<tr>
		<td class="table-primary"></td>
		<td align="right" class="table-primary"><b>Grand Total </b></td>
		<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totProjSancAmt}" /></fmt:formatNumber></b></td>
		<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totProjExpndtr}" /></fmt:formatNumber></b></td>
		</tr>
      		</table>
      		</c:if>
      			
      		</div>
   <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>   		