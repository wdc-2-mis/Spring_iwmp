<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<title>Report PF4- State wise Expenditure in a Financial Year</title>

<%-- <script src='<c:url value="/resources/js/bootstrapWithExport.js" />'></script> --%>

<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css"> -->
<%-- <link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />"> --%>
<script type="text/javascript">

function ShowContent(d) 
{
	var dd = document.getElementById(d);
	var width = dd.style.width;
	var index = width.indexOf("px");
	document.getElementById("waitDiv").style.display = "";
	width = width.substring(0,index);
	dd.style.left = ((document.body.clientWidth-width)/2) + "px";
	dd.style.display = "";
}

function showReport(e) 
{
	//alert('kedar');
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
 
    document.getElementById("finName").value=finName;
 
	if(document.getElementById("year").value==='')
	{
		alert('Please select Financial Year ! ');
		$('#year').focus();
		e.preventDefault();
	}
	else{
		
		ShowContent("previewDiv");
		document.stateExpenditure.action="getStateExpenditureReport";
		document.stateExpenditure.method="post";
		document.stateExpenditure.submit();
	}
	return false;

}

	function downloadPDF(finName){
		document.getElementById("finName").value=finName;
		document.stateExpenditure.action="downloadStExpndtrPDF";
		document.stateExpenditure.method="post";
		document.stateExpenditure.submit();
}

	function exportExcel(finName){
		document.getElementById("finName").value=finName;
		document.stateExpenditure.action="downloadExcelStateExpenditureReport";
		document.stateExpenditure.method="post";
		document.stateExpenditure.submit();
}
	
</script>


<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report PF4- State wise Expenditure in a Financial Year</label></h5></div>
<br>
<div class ="card">
<div class="row">
<div class="col-2" ></div>
<div class="col-8">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getStateExpenditureReport" name="stateExpenditure" id="stateExpenditure" method="post">
	<div id="waitDiv" 
			style="display: none; line-height: 20px; z-index: 98; position: absolute; background: #ffffff; left: 25px;  height: 800px;
			 width: 1600px; filter: alpha(opacity = 60); -moz-opacity: .60; opacity: .60; text-align: center; float: left;">
			<table>  
				<tr>
					<td>
						<div align="center">
							<span style="padding-right:3px;  display:inline-block; width: 1600px;">
									<img class="manImg" src="resources/images/load.gif"></img>
							</span>
						</div>
					</td>
				</tr>
			</table> 
			</div>
		
    <input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="finName" id="finName" value="" />
	
      <table style="width:50%; align-content: center;" >
        <tr align="center" >
        
         
          <td><b>Financial Year <span style="color: red;">*</span></b></td>
           <td>
              <select name="year" id="year"  required="required" onchange="showReport(this);">
              		<option value="">--Select Year--</option>
						  <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq year && lists.finYrCd ne 21}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>	
       							<c:if test="${lists.finYrCd ne year && lists.finYrCd ne 21}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
								</c:forEach>
						</c:if>  
              </select>
          </td>
        
           
       </tr>
      </table>
      <div id="previewDiv" class="hiddenDivStyle" align="center"
			style="position: absolute; top: 100px; left: 25px; display: none; width: 300px; height: 50px; vertical-scrol: auto; background-color: gray;">
			<table align="center">
				<tr>
					<td>
						<div align="center">
							<span style="font-size: 25px;">Please Wait ...</span>
						</div>
					</td>
				</tr>
			</table>
		</div>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	<br/>
	<br/>
	 <c:if test="${not empty dataList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${finName}')" class="btn btn-info">Excel</button>
	
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${finName}')" class="btn btn-info">PDF</button> </c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table">
  	<thead>
  		<tr>
      		<th style="text-align:right;" colspan="6">All amount (Rs. in lakh)</th>
      	</tr>	
    	<tr>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 15%;">State Name</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Sanctioned Amount</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Central Share Released From Treasury to SLNA</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">State Share Released From Treasury to SLNA</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Expenditure</th>
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
		<c:set var="count" value="1" />
		 <c:forEach var="list" items="${dataList}">
			
				<tr>
					<td align="left"><c:out value='${count}' /></td>
					<td align="left"><c:out value='${list.stname}' /></td>
					<td align="right"><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value='${list.project_cost}' /></fmt:formatNumber></td>
					<td align="right"><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value='${list.centralshare}' /></fmt:formatNumber></td>
					<td align="right"><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value='${list.stateshare}' /></fmt:formatNumber></td>
					<td align="right"><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value='${list.stateexpen}' /></fmt:formatNumber></td>
					
					<c:set var="count" value="${count+1}" />
				</tr>
			<%-- <c:set var="totalproject_cost"
					value="${totalproject_cost+list.project_cost}" />
				<c:set var="totalcentralshare"
					value="${totalcentralshare+list.centralshare}" />
				<c:set var="totalstateshare"
					value="${totalstateshare+list.stateshare}" />
				<c:set var="totalstateexpen"
					value="${totalstateexpen+list.stateexpen}" /> --%>
					
		</c:forEach>

	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="2" value="${netTotal[0]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="2" value="${netTotal[1]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="2"  value="${netTotal[2]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="2"  value="${netTotal[3]}"/></b></td>
			</tr>
		</c:forEach>  

	<%-- 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
				<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[0]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[1]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[2]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[3]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[4]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[5]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[6]}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${netTotal[7]}' /> </b></td>
				
			</tr>
		</c:forEach>  --%>

		
	

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