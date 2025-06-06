<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Report ME2- District-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details</title>

<script type="text/javascript">
	function exportExcel(id, stname){
		document.getElementById("id").value=id;
		document.getElementById("stname").value=stname;
		document.getCurrentStatusOther.action="downloadExcelDistrictWiseCurrentStatusOther";
		document.getCurrentStatusOther.method="post";
		document.getCurrentStatusOther.submit();
	}
function downloadPDF(id,stname){
	document.getElementById("id").value=id;
	document.getElementById("stname").value=stname;
	document.getCurrentStatusOther.action="downloadDistrictWiseCurrentStatusOtherPDF";
	document.getCurrentStatusOther.method="post";
	document.getCurrentStatusOther.submit();
}
	</script>
 
<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report ME2- District-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details for State '<c:out value="${stname}"/>' </label> </h5></div>
<br>

<form action="downloadExcelDistrictWiseCurrentStatusOther" method="post" id="getCurrentStatusOther" name="getCurrentStatusOther">
	<input type="hidden" name="id" id="id" value="" />
	<input type="hidden" name="stname" id="stname" value="" />
			<div class="form-row">
			</div>
		</form>

<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${id}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${id}','${stname}')" class="btn btn-info">PDF</button>    
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 20%;">District Name</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total no. of Villages</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Projects Sanctioned</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project for Permission given</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project with Baseline Ground Water</th> 
			<!-- <th style="text-align:center; vertical-align: middle; width: 7%;">No. of WCDC Account Created</th>  --> 
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of PIA Account Created</th>
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
			<!-- <th class="text-center">9</th> -->
		</tr>
		<c:set var="count" value="1" />
		<c:forEach var="list" items="${netTreatledata}">
			<c:forEach var="listUser" items="${list.value}" >
				<tr>
					<td><c:out value='${count}' /></td>
					<td><a
						href="projWiseCurrentStatusOther?dcode=<c:out value="${listUser.dcode}"/>"><c:out
								value="${listUser.dist_name}" /></a></td>
<%-- 					<td><c:out value='${listUser.dist_name}' /></td> --%>
					<td align="right"><c:out value='${listUser.total_village}' /></td>
					<td align="right"><c:out value='${listUser.total_project}' /></td>
					<td align="right"><c:out value='${listUser.tot_proj_permission}' /></td>
					<td align="right"><c:out value='${listUser.total_project_plan}' /></td>
					<td align="right"><c:out value='${listUser.tot_proj_basel_ground}' /></td>
					<%-- <td align="right"><c:out value='${listUser.total_wcdc}' /></td> --%>
					<td align="right"><c:out value='${listUser.total_pia}' /></td>
					<c:set var="count" value="${count+1}" />
				</tr>
			</c:forEach>
		</c:forEach>
	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary"></td>
				<td align="right" class="table-primary" ><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
				<%-- <td align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td> --%>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /> </b></td>
				
			</tr>
		</c:forEach> 
	</tbody>
  </table>
</div>
</div>
</div>


<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>