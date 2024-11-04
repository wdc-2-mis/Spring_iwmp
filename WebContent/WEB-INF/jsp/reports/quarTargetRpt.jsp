<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report O12- State-wise, Year-wise Targets on Output Outcome Monitoring Framework(OOMF) Indicators</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">
function showReport(e) 
{
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	 var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
	 document.getElementById("stName").value=stName;
	 document.getElementById("finName").value=finName;
	 document.targets.action="getQuarTargetReport";
	 document.targets.method="post";
     document.targets.submit();
     
}
function downloadPDF( stCode,year,stName,finName) {
	 document.getElementById("stCode").value=stCode;
	document.getElementById("year").value=year;
	document.getElementById("stName").value=stName;
	 document.getElementById("finName").value=finName;
    document.targets.action = "downloadQuarTargetReportPDF";
    document.targets.method = "post";
    document.targets.submit();
}
function exportExcel( stCode,year,stName,finName) {
	 document.getElementById("stCode").value=stCode;
	document.getElementById("year").value=year;
	document.getElementById("stName").value=stName;
	 document.getElementById("finName").value=finName;
    document.targets.action = "downloadExcelQuarTargetReport";
    document.targets.method = "post";
    document.targets.submit();
}
</script>


</head>

<body>
 <br>
<div class="offset-md-2 col-8 formheading" style="text-align:center;"  ><h5><label id="head"></label>Report O12- State-wise, Year-wise Targets on Output Outcome Monitoring Framework(OOMF) Indicators</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

<form:form action="quadwisetargets" method="post" name="targets" id="targets">
<input type="hidden" name="stName" id="stName" value="" />
<input type="hidden" name="finName" id="finName" value="" />
<input type="hidden" name="stCode" id="stCode" value="" /> 


<table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All State--</option>
     			<c:forEach items="${stateList}" var="state">
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stCode ?'selected':'' }"/>
							><c:out 	value="${state.value}" /></option>
				</c:forEach>
    		</select>
          </td>
          <td ><b>Financial Year <span style="color: red;">*</span></b></td>
          
          <td>
              <select class="form-control finyear" name="year" id="year"  required="required">
              		<option value="0">--All Financial Year---</option>
						  <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>	
       							<c:if test="${lists.finYrCd ne year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
								</c:forEach>
						</c:if>  
              </select>
          </td>
          
     
          <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport" onclick="showReport()" name="btnGetReport" value="Get Report" />
        
       </tr>
      </table>
 </form:form>
 </div>
 </div>
 
 
 
 </div>
 </div>
 
 <div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
<c:if test="${dataList != null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}','${year}','${stName}','${finName}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}','${year}','${stName}','${finName}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <tr> 
	 	<th colspan="7" style="text-align:left; ">State : ${stName} &emsp; Financial Year : ${finName}   </th>
		
	</tr>
    <tr>
    
    <th style="text-align:center; vertical-align: middle;">S.No.</th>
      <th style="text-align:center; vertical-align: middle;">Name of the Activity</th> 
      <th style="text-align:center" >(April-June)</th>
      <th style="text-align:center" >(July-September)</th>
      <th style="text-align:center" >(October-December)</th>
      <th style="text-align:center" >(January-March)</th>
      <th style="text-align:center" >Total</th> 
      
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
		</tr>
		
	
	<c:forEach items="${dataList}" var="list" varStatus="sno">
	<tr>
	
								<td align="center">${sno.count}</td>
								<td>${list[0]}</td>
								<td>${list[1]}</td>
								<td>${list[2]}</td>
								<td>${list[3]}</td>
								<td>${list[4]}</td>
								<td>${list[5]}</td>
							
	</tr>
	
	</c:forEach>
	<c:if test="${dataListSize==0}">
			<tr>
				<td align="center" colspan="7" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	</div>
	</div>
	</div>
		
 
 <footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
 </body>
 </html>
 