<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/report.css" />">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<title>Report OC4 - State and Project wise Work-Id details of Livelihood Activities / Production System Activities and Entry Point Activities(EPAs)</title>

<%-- <script src='<c:url value="/resources/js/bootstrapWithExport.js" />'></script> --%>

<script type="text/javascript">

function getlpeWorkid(){
	var state = $('#state').val();
	var dist = $('#district').val();
	var proj=$('#project').val();
	var scheme=$('#scheme').val();
	
	
	if(state==='')
	{
		alert('Please select State ');
		$('#state').focus();
		e.preventDefault();
	}
	if(dist==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	if(proj==='')
	{
		alert('Please select Project ');
		$('#project').focus();
		e.preventDefault();
	}
	if(scheme==='')
	{
		alert('Please select Head ');
		$('#scheme').focus();
		e.preventDefault();
	}
	else{
		
		document.lpeWorkid.action="livelihoodPrdouctionEPAWorkIdReport";
		document.lpeWorkid.method="post";
		document.lpeWorkid.submit();
	}
	return false;
}
function downloadPDF(state, district, project, scheme){
	document.getElementById("state").value=state;
	document.getElementById("district").value=district;
	document.getElementById("project").value=project;
	document.getElementById("scheme").value=scheme;
	document.lpeWorkid.action="downloadlivelihoodPrdouctionEPAWorkIdReportPDF";
	document.lpeWorkid.method="post";
	document.lpeWorkid.submit();
}

function exportExcel(state, district, project, scheme){
	document.getElementById("state").value=state;
	document.getElementById("district").value=district;
	document.getElementById("project").value=project;
	document.getElementById("scheme").value=scheme;
	document.lpeWorkid.action="downloadExcelLivelihoodProdEPAWorkIdReport";
	document.lpeWorkid.method="post";
	document.lpeWorkid.submit();
}

</script>

</head>
<body>


<div class="offset-md-3 col-6 formheading" style="text-align: center;"><h5><label id="head">OC4- State and Project wise Work-Id details of Livelihood Activities / Production System Activities and Entry Point Activities(EPAs)</label> </h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-9">

<div class="table-responsive">

 <form:form autocomplete="off" name="lpeWorkid" id="lpeWorkid"  modelAttribute="lpeWorkid" action="livelihoodPrdouctionEPAWorkIdReport" method="get">

      <table >
        <tr align="center">
        
          <td class="label">State<span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();">
              		<option value="">--Select--</option>
                  	<c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
          <td class="label">District<span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="this.form.submit();">
              		<option value="0">--SelectAll--</option>
                  	<c:if test="${not empty districtList}">
               			<c:forEach items="${districtList}" var="lists">
               				<c:if test="${lists.key eq district}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne district}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
          <td class="label">Project<span style="color: red;">*</span></td>
          <td align="center">
              <select name="project" id="project" >
              		<option value="0" style="text-align:center;">--SelectAll--</option>
                  	<c:if test="${not empty projectList}">
               			<c:forEach items="${projectList}" var="lists">
               				<c:if test="${lists.key eq project}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne project}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
          <td class="label">Head<span style="color: red;">*</span></td>
          <td align="center">
              <select name="scheme" id="scheme" >
              <option value="">--Select--</option>
                  	<c:if test="${not empty workHeadList}">
               			<c:forEach items="${workHeadList}" var="lists">
               				<c:if test="${lists.key eq scheme}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne scheme}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					</c:if> 
              </select>
          </td>
         
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="getlpeWorkid();" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-9">
	<c:if test="${dataListSize>0}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${state}','${district}','${project}','${scheme}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${district}','${project}','${scheme}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	</c:if>
        <table class="table">
          <tr>
            <td>
            
            	<table id="dtBasicExample" class="table">
            	<thead>
              		<tr align="center">
              			<th align="center">S.No.</th>
              			<th align="center">Project Name</th>
              			<th align="center">Work-Id</th>
		              	<th align="center">Activities</th>
                        <th align="center">Block Name</th>
                        <th align="center">Gram Panchayat</th>
                        <th align="center">Village Name</th>
                        <th align="center">Land Identification</th>
                        
               		</tr>	</thead>
               	<tbody>	
               		<tr>
	               		<th align="center"><b> 1 </b></th>
	               		<th align="center"><b> 2 </b></th>
	               		<th align="center"><b> 3 </b></th>
		               	<th align="center"><b> 4 </b></th>
		               	<th align="center"><b> 5 </b></th>
	               		<th align="center"><b> 6 </b></th>
	               		<th align="center"><b> 7 </b></th>
	               		<th align="center"><b> 8 </b></th>
               		</tr>
               
               		<c:if test="${dataListSize>0}">
						<c:forEach items="${dataList}" var="dataV" varStatus="count">
							<tr>
								<td><c:out value='${dataV[0]}' /></td>
								<td><c:out value='${dataV[1]}' /></td>
								<td align="center"><c:out value='${dataV[2]}' /></td>
								<td><c:out value='${dataV[3]}' /></td>
								<td><c:out value='${dataV[5]}' /></td>
								<td><c:out value='${dataV[6]}' /></td>
								<td><c:out value='${dataV[4]}' /></td>
								<td><c:out value='${dataV[7]}' /></td>
							</tr>
						</c:forEach>
					</c:if>
              </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td>
            	<c:if test="${dataListSize<=0 }">
   					<table width="100%" border="0" cellspacing="0" cellpadding="0">
          				<tr class="tabs">
            				<td><center><span style="color: red;"> No Data Found !</span></center></td>
            				
          				</tr>
        			</table>
  				</c:if>
  			</td>
          </tr>
        </table>
        </div>
        </div>
    <br>
	
	</div>

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>