<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report O9- District-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">

function downloadPDF(state, year, quarter,stName,finName,quartename)
{
    document.getElementById("state").value=state;
    document.getElementById("year").value=year;
    document.getElementById("quarter").value=quarter;
    document.getElementById("stName").value=stName;
    document.getElementById("finName").value=finName;
    document.getElementById("quartename").value=quartename;
    document.indicators.action="getDistWiseQuarterReportPDF";
	document.indicators.method="post";
	document.indicators.submit();
}
function exportExcel(state, year, quarter,stName,finName,quartename)
{
    document.getElementById("state").value=state;
    document.getElementById("year").value=year;
    document.getElementById("quarter").value=quarter;
    document.getElementById("stName").value=stName;
    document.getElementById("finName").value=finName;
    document.getElementById("quartename").value=quartename;
    document.indicators.action="getDistWiseTargetAchievementofIndicators";
	document.indicators.method="post";
	document.indicators.submit();
}
// function downloadPDF(state, year, quarter){
	
// 	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
//     var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
//     var quartename = document.getElementById("quarter").options[document.getElementById("quarter").selectedIndex].text;
//     alert('jh');
//     document.getElementById("stName").value=stName;
//     document.getElementById("finName").value=finName;
//     document.getElementById("quartename").value=quartename;
	
//     document.getElementById("state").value=state;
//     document.getElementById("year").value=year;
//     document.getElementById("quarter").value=quarter;
//     document.indicators.action="getDistWiseQuarterReportPDF";
// 	document.indicators.method="post";
// 	document.indicators.submit();
// 	}

// function exportExcel(state, year, quarter){
	
// 	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
//     var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
//     var quartename = document.getElementById("quarter").options[document.getElementById("quarter").selectedIndex].text;
//   //  alert(stName+distName+projName+yearName);
//     document.getElementById("stName").value=stName;
//     document.getElementById("finName").value=finName;
//     document.getElementById("quartename").value=quartename;
	
//     document.getElementById("state").value=state;
//     document.getElementById("year").value=year;
//     document.getElementById("quarter").value=quarter;
//     document.indicators.action="getDistWiseTargetAchievementofIndicators";
// 	document.indicators.method="post";
// 	document.indicators.submit();
// 	}

</script>
</head>


<body onload="updateQuarterOptions();">
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O9- District-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators </h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="indicators" name="indicators" id="indicators" method="get">
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
		
    <input type="hidden" name="state" id="state" value="" />
	<input type="hidden" name="year" id="year" value="" />
	<input type="hidden" name="quarter" id="quarter" value="" /> 
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="finName" id="finName" value="" />
	<input type="hidden" name="quartename" id="quartename" value="" /> 

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
	
	<div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
<c:if test="${distWiseDataList != null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${state}','${year}','${quarter}', '${stName}','${finName}','${quartename}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${year}','${quarter}', '${stName}','${finName}','${quartename}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <tr>
	 	<th colspan="7" style="text-align:left; ">State : ${stName} &emsp; Financial Year : ${finName} &emsp; Quarter : ${quartename}  </th>
		<th colspan="6" style="text-align:right; ">All Area in Ha.</th>
	</tr>
	
	 <tr>
      <th style="text-align:center; vertical-align: middle;">S.No.</th>
      <th style="text-align:center; vertical-align: middle;">District Name</th> 
      <th style="text-align:center" >Area of degraded land covered/Rainfed area developed</th>
      <th style="text-align:center" >Area covered with soil and moisture conservation activities</th>
      <th style="text-align:center" >Area brought under plantation (Afforestation/Horticulture)</th>
      <th style="text-align:center" >No. of water harvesting structure created/renovated</th>
    
      <th style="text-align:center" >No. of farmers benefitted</th>
      <th style="text-align:center" >Area brought under protective irrigation</th>
      <th style="text-align:center" >No. of man-days generated</th>
      <th style="text-align:center" >Additional area brought under diversified crops/change in cropping system</th>
      <th style="text-align:center" >Area brought from no crop/single crop to single/multiple crop</th>
      <th style="text-align:center" >Increase in cropped area</th>
      <th style="text-align:center" >Average Increase in farmers income (<b>%</b>)</th>      
     </tr>
  

	
	
	
<!--     <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">District Name</th> 
      <th style="text-align:center" colspan="2">Area of degraded land covered/Rainfed area developed</th>
      <th style="text-align:center" colspan="2">Area covered with soil and moisture conservation activities</th>
      <th style="text-align:center" colspan="2">Area brought under plantation (Afforestation/Horticulture)</th>
      <th style="text-align:center" colspan="2">No. of water harvesting structure created/renovated</th>
    
      <th style="text-align:center" colspan="2">No. of farmers benefitted</th>
      <th style="text-align:center" colspan="2">Area brought under protective irrigation</th>
      <th style="text-align:center" colspan="2">No. of man-days generated</th>
      <th style="text-align:center" colspan="2">Additional area brought under diversified crops/change in cropping system</th>
      <th style="text-align:center" colspan="2">Area brought from no crop/single crop to single/multiple crop</th>
      <th style="text-align:center" colspan="2">Increase in cropped area</th>
      <th style="text-align:center" colspan="2">Average Increase in farmers income (<b>%</b>)</th>      
     </tr>
     <tr> 
      	<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
		<th class="text-center">Target</th>
		<th class="text-center">Achievement</th>
    </tr> -->
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
		<th class="text-center">9</th>
		<th class="text-center">10</th>
		<th class="text-center">11</th>
		<th class="text-center">12</th>
		<th class="text-center">13</th>
		<!-- <th class="text-center">14</th>
		<th class="text-center">15</th>
		<th class="text-center">16</th>
		<th class="text-center">17</th>
		<th class="text-center">18</th>
		<th class="text-center">19</th>
		<th class="text-center">20</th> 
		<th class="text-center">21</th>
		<th class="text-center">22</th>
		<th class="text-center">23</th>
		<th class="text-center">24</th> -->
		
	</tr>
	
	 <c:if test="${distWiseDataListsize >0}">
		
		<c:forEach items="${distWiseDataList}" var="dataV" varStatus="status">
		<tr>		
			
			<td><c:out value='${dataV[0]}' /></td>
			<td><c:out value='${dataV[2]}' /></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[16]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[4]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[6]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[8]}"/></td>
			
			
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[10]}"/></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[12]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[14]}"/></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[18]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[20]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[22]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[24]}' /></fmt:formatNumber></td> 
			
			<%-- <td><c:out value='${dataV[0]}' /></td>
			<td><c:out value='${dataV[2]}' /></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[15]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[16]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[3]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[4]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[5]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[6]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[7]}"/></td>
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[8]}"/></td>
			
			
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[9]}"/></td>
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[10]}"/></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[11]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[12]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[13]}"/></td>
			<td align="right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataV[14]}"/></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[17]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[18]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[19]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[20]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[21]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[22]}' /></fmt:formatNumber></td>
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[23]}' /></fmt:formatNumber></td>
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[24]}' /></fmt:formatNumber></td>  --%>
			
		</tr>		
		</c:forEach>
	 	<c:forEach items="${distWiseDataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary"></td>
				<td align="right" class="table-primary" ><b>Grand Total </b></td>
				
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[13]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[1]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[3]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[5]}"/></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[7]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[9]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[11]}"/></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[15]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[17]}' /></fmt:formatNumber> </b></td>
			
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[19]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[21]/noofdistrict}' /></fmt:formatNumber> </b></td> 
				
				
				
				
				
				<%-- <td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[12]}' /></fmt:formatNumber> </b></td> 
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[13]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[0]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[1]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[2]}' /> </fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[3]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[4]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[5]}"/></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[6]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[7]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[8]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[9]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[10]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${netTotal[11]}"/></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[14]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[15]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[16]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[17]}' /></fmt:formatNumber> </b></td>
			
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[18]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[19]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[20]/noofdistrict}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[21]/noofdistrict}' /></fmt:formatNumber> </b></td> 
 --%>
			
			
			</tr>
	</c:forEach> 
    </c:if>
    	<c:if test="${distWiseDataListsize==0}">
			<tr>
				<td align="center" colspan="13" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>
    
  </tbody>
</table>
   
<br/>  
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