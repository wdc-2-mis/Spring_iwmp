<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>
<script src='<c:url value="/resources/js/trainingCapicityBuilding.js" />'></script>

<title>Report OT3- List of Vibrant Villages</title>

<script type="text/javascript">

function downloadPDF(stcd,sts){
	document.getElementById("stcd").value=stcd;
	document.getElementById("sts").value=status;
	document.getreports.action="downloadPDFVibrantVillage";
	document.getreports.method="post";
	document.getreports.submit();
}

function exportExcel(stcd,sts){
	document.getElementById("stcd").value=stcd;
	document.getElementById("sts").value=status;
	document.getreports.action="downloadExcelVibrantVillage";
	document.getreports.method="post";
	document.getreports.submit();
}

$(document).on('click','#view',function(e){
	document.getreports.action="vibrantvillage";
	document.getreports.method="post";
	document.getreports.submit();
})

</script>

</head>

<form action = "vibrantvillage" method = "post" name = "getreports" id = "getreports">
	<input type = "hidden" name = "stcd" id = "stcd" value="" />
	<input type = "hidden" name = "sts" id = "sts" value="" />
	<br>
	<div class="offset-md-3 col-6 formheading" style="textl-align: center;">
		<h5>Report OT3- List of Vibrant Villages</h5>
	</div>
	<br>
	<div class = "form-group row col-8">
		<label for="state" class = "col-sm-1 col-form-label">State</label>
		<div class="col-sm-3">
			<select id="state" name="state" required="required">
			<option value="0">--ALL--</option>
			<c:if test="${not empty stateList}">
				<c:forEach var="list" items="${stateList}">
     				<c:if test="${stCode eq list.key }">
     				<option value="<c:out value='${list.key}'/>" selected="selected" ><c:out value="${list.value }"/></option>
     				</c:if>
     				<c:if test="${stCode ne list.key}">
     				<option value="<c:out value='${list.key}'/>"><c:out value="${list.value }"/></option>
     				</c:if>
     			</c:forEach>
     		</c:if>
     		</select>
	</div>
	
	<div class="col-sm-3">
	<c:if test="${statuss eq null }">
	<input type="radio" id = "status" name="status" value="false"> All Villages
     	<br>
     	<input type="radio" id = "status" name="status" value="true"> Vibrant Villages exist in WDCPMKSY MIS-2.0<br>
	</c:if>
	
	<c:if test="${statuss eq false }">
     	<input type="radio" id = "status" name="status" value="false" checked="checked" > All Villages
     	<br>
     	<input type="radio" id = "status" name="status" value="true"> Vibrant Villages exist in WDCPMKSY MIS-2.0<br>
     </c:if>	
     	
     	<c:if test="${statuss eq true }">
     	<input type="radio" id = "status" name="status" value="false"> All Villages
     	<br>
     	<input type="radio" id = "status" name="status" value="true" checked="checked"> Vibrant Villages exist in WDCPMKSY MIS-2.0<br>
     	</c:if>
    </div>
    
    <div class="col-sm-3"><input type="submit" class="btn btn-info" id="view"  name="view" value='Get Report' /></div>
  </div>
	
</form>
<div class="container-fluid">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}','${sts}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}','${sts}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	
</div>

<table id="tblReport" class="table">
	<thead>
		<tr>
			<th class="text-center" >S.No.</th>
			<th class="text-center" >State Name</th>
			<th class="text-center" >District Name</th>
			<th class="text-center" >Block Name</th>
			<th class="text-center" >Village Name</th>
			<th class="text-center" >LGD Code</th>
			<th class="text-center" >International Border</th>
			<th class="text-center" >Distance from LAC</th>
		</tr>
	</thead>
	
	<tbody id="vibrantVlgRptTbody">
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
		<c:forEach items="${villageList}" var="village" varStatus="sno">
			<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${village.st_name}" /></td>
					<td class="text-left"><c:out value="${village.district}" /></td>
					<td class="text-left"><c:out value="${village.block}" /></td>
					<td class="text-left"><c:out value="${village.village}" /></td>
					<td class="text-right"><c:out value="${village.lgd_code_village}" /></td>
					<td class="text-left"><c:out value="${village.international_border}" /></td>
					<td class="text-right"><c:out value="${village.distancefromlac}" /></td>
			</tr>
		</c:forEach>
	
	<c:if test="${villageListSize==0}">
			<tr>
				<td align="center" colspan="8" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
	</tbody>
</table>
</html>