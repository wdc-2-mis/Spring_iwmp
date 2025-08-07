<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report O9- State-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">
/* function ShowContent(d) 
{
	var dd = document.getElementById(d);
	var width = dd.style.width;
	var index = width.indexOf("px");
	document.getElementById("waitDiv").style.display = "";
	width = width.substring(0,index);
	dd.style.left = ((document.body.clientWidth-width)/2) + "px";
	dd.style.display = "";
} */

function showReport(e) 
{
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();
	today = yyyy + '-' + mm + '-' + dd;
//	alert('kedar'+today);
	var stcd=document.getElementById("state").value;
	var ycd=document.getElementById("year").value;
	var qtr=document.getElementById("quarter").value;
	var year1='20'+ycd;
    var yy=parseInt(ycd)+parseInt(1);
	var year2='20'+yy
	
	var qtr1=year1+'-07-01';
	var qtr2=year1+'-10-01';
	var qtr3=year2+'-01-01';
	var qtr4=year2+'-04-01';
	var d1=new Date(qtr1);
	var d2=new Date(qtr2);
	var d3=new Date(qtr3);
	var d4=new Date(qtr4);
	var crunt=new Date(today);
	
	
	//alert('kedar'+d1+','+d2+','+d3+','+d4+','+crunt); 
	
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var quartename = document.getElementById("quarter").options[document.getElementById("quarter").selectedIndex].text;
    
//    alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("finName").value=finName;
    document.getElementById("quartename").value=quartename;
    
//     alert(stName+finName+quartename+stcd+ycd+qtr);
    
	if(document.getElementById("state").value=='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	if(document.getElementById("year").value=='')
	{
		alert('Please select Financial Year ! ');
		$('#year').focus();
		e.preventDefault();
	}
	if(document.getElementById("quarter").value=='')
	{
		alert('Please select Quarter !');
		$('#quarter').focus();
		e.preventDefault();
	}
/* 	var d1=new Date('2020-01-23'); //yyyy-mm-dd  
	var d2=new Date('2020-01-21'); //yyyy-mm-dd   */
	if(qtr==1){
		if(d1>=crunt)  
		{  
			alert("Selected Year and Quarter is greater than end date of Year and Quarter.");  
		  	return false;
		}
	}	
	if(qtr==2){
		if(d2>=crunt)  
		{  
			alert("Selected Year and Quarter is greater than end date of Quarter.");  
		  	return false;
		}
	}	
	if(qtr==3){
		if(d3>=crunt)  
		{  
			alert("Selected Year and Quarter is greater than end date of Quarter.");  
		  	return false;
		}
	}	
	if(qtr==4) {
		if(d4>=crunt)  
		{  
			alert("Selected Year and Quarter is greater than end date of Quarter.");  
		  	return false;
		} 
	}
	/* if(qtr==5 ) {
			if(d4>=crunt)  
			{  
				alert("Selected Year and Quarter is greater than end date of Quarter.");  
			  	return false;
			} 
	} */
	if(qtr==0 ) {
		if(d4>=crunt)  
		{  
			alert("Selected Year and Quarter is greater than end date of Quarter.");  
		  	return false;
		} 
	}
	else{
		
		ShowContent("previewDiv");
		document.indicators.action="getQuarterReport";
		document.indicators.method="post";
		document.indicators.submit();
		document.getElementById("view").disabled = true;
	}
	return false;

}

// function exportExcel(){
// 	var now = new Date();
// 	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
// 	var tableName = "#tblReport";
// 	 var colLength=$(tableName+" tr:first th").length;
// 	 var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
// 	var reportName = "Report O9- Target/Achievements on Output-Outcome Indicators";
// 	var fileName="Target/Achievements on Output-Outcome Indicators";
// 	 var headerHtml = "<table>"+
// 	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
// 	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    
// 	    "<tr><td></td></tr></table>";
// 	    var footerHtml = "<table>"+
// 	    "<tr><td colspan='"+colLength+"' style='text-align:center'>Report Generated by WDC-PMKSY software on: "+jsDate+"</td></tr></table>";  
// 	 exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
// 	}


function updateQuarterOptions() {
        var yearSelect = document.getElementById("year");
        var quarterSelect = document.getElementById("quarter");

        if (yearSelect.value === "0") {
            // If "All Financial Year" is selected, set "All Quarters" as selected and disable other options
            quarterSelect.value = "5"; // "5" is the value for "All Quarters"
            for (var i = 1; i < quarterSelect.options.length; i++) {
                if (quarterSelect.options[i].value !== "5") {
//                     quarterSelect.options[i].disabled = true;
                	quarterSelect.options[i].style.display = "none";
                }
            }
        } else {
            // If any other year is selected, enable all options in the quarter dropdown
            for (var i = 0; i < quarterSelect.options.length; i++) {
//                 quarterSelect.options[i].disabled = false;
            	quarterSelect.options[i].style.display = "";
            }
        }
    }



function downloadPDF(state, year, quarter){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var quartename = document.getElementById("quarter").options[document.getElementById("quarter").selectedIndex].text;
  //  alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("finName").value=finName;
    document.getElementById("quartename").value=quartename;
	
    document.getElementById("state").value=state;
    document.getElementById("year").value=year;
    document.getElementById("quarter").value=quarter;
    document.indicators.action="downloadQuarterReportPDF";
	document.indicators.method="post";
	document.indicators.submit();
	}

function exportExcel(state, year, quarter){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var quartename = document.getElementById("quarter").options[document.getElementById("quarter").selectedIndex].text;
  //  alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("finName").value=finName;
    document.getElementById("quartename").value=quartename;
	
    document.getElementById("state").value=state;
    document.getElementById("year").value=year;
    document.getElementById("quarter").value=quarter;
    document.indicators.action="downloadExceltargetAchievementofIndicators";
	document.indicators.method="post";
	document.indicators.submit();
	}

</script>
</head>


<body onload="updateQuarterOptions();">
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O9- State-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators </h5></div>

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
	
    <input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="finName" id="finName" value="" />
	<input type="hidden" name="quartename" id="quartename" value="" /> 
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select  id="state" name="state" required="required">
    			<option value="0">--All--</option>
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
          <td><b>Financial Year <span style="color: red;">*</span></b></td>
           <td>
              <select name="year" id="year"  required="required" onchange="updateQuarterOptions()">
               		<!-- <option value="">--Select--</option> -->
              		 <option value="0">--All---</option>
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
          <td ><b>Quarter <span style="color: red;">*</span></b></td>
          <td>
              <select name="quarter" id="quarter"  required="required">
              	 	<option value="5">--All--</option> 
						  <c:if test="${not empty quaterMaster}">
               					<c:forEach items="${quaterMaster}" var="lists">
               					<c:if test="${lists.quartCd eq quarter}">
       								<option value="<c:out value='${lists.quartCd}'/>" selected="selected" ><c:out value="${lists.quartDesc}" /></option>
       							</c:if>	
       							<c:if test="${lists.quartCd ne quarter}">
       								<option value="<c:out value='${lists.quartCd}'/>" ><c:out value="${lists.quartDesc}" /></option>
       							</c:if>	
								</c:forEach>
						</c:if>  
              </select>
          </td>
        
           <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Report' /> </td> </td> 
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
	</div>
	<div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
<c:if test="${dataList != null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${state}','${year}','${quarter}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${year}','${quarter}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <tr> 
	 	<th colspan="7" style="text-align:left; ">State : ${stName} &emsp; Financial Year : ${finName} &emsp; Quarter : ${quartename}  </th>
		<th colspan="7" style="text-align:right; ">All Area in Ha.  </th>
	</tr>
    <tr>
     <!--  <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">State Name</th> 
      <th style="text-align:center" colspan="2">Area of degraded land covered/Rainfed area developed</th>
      <th style="text-align:center" colspan="2">Area covered with soil and moisture conservation activities</th>
      <th style="text-align:center" colspan="2">Area brought under plantation (Afforestation/Horticulture)</th>
      <th style="text-align:center" colspan="2">No. of water harvesting structure (created/renovated)</th>
      <th style="text-align:center" colspan="2">No. of farmers benefited</th>
      <th style="text-align:center" colspan="2">Area brought under protective irrigation (created/renovated)</th>
      <th style="text-align:center" colspan="2">No. of man-days generated</th>
      <th style="text-align:center" colspan="2">Additional area brought under diversified crops/change in cropping system</th>
      <th style="text-align:center" colspan="2">Area brought from no crop/single crop to single/multiple crop</th>
      <th style="text-align:center" colspan="2">Increase in cropped area</th>
      <th style="text-align:center" colspan="2">Average Increase in farmers income (<b>%</b>)</th> -->
      
      
      <th style="text-align:center; vertical-align: middle;">S.No.</th>
      <th style="text-align:center; vertical-align: middle;">State Name</th> 
      <th style="text-align:center" >Area of degraded land covered/Rainfed area developed</th>
      <th style="text-align:center" >Area covered with soil and moisture conservation activities</th>
      <th style="text-align:center" >Area brought under plantation (Afforestation/Horticulture)</th>
      <th style="text-align:center" >No. of water harvesting structure (created/renovated)</th>
      <th style="text-align:center" >No. of farmers benefited</th>
      <th style="text-align:center" >Area brought under protective irrigation (created/renovated)</th>
      <th style="text-align:center" >No. of man-days generated</th>
      <th style="text-align:center" >Additional area brought under diversified crops/change in cropping system</th>
      <th style="text-align:center" >Area brought from no crop/single crop to single/multiple crop</th>
      <th style="text-align:center" >Increase in cropped area</th>
      <th style="text-align:center" >Average Increase in farmers income (<b>%</b>)
      <th style="text-align:center" >Average area of degraded land covered/Rainfed area developed (<b>%</b>)
      
      
      
     </tr>
    <!--  <tr>   
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
		<th class="text-center">14</th>
		<!--<th class="text-center">15</th>
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
	 
	 <c:if test="${dataList != null}">
		
		<c:forEach items="${dataList}" var="dataV" varStatus="status">
		<tr>	
		
			<td><c:out value='${dataV[0]}' /></td>
			<td><a href="getDistWiseQuarterReport?state=${dataV[1]}&year=${year}&quarter=${quarter}&stName=<c:out value='${dataV[2]}' />&finName=${finName}&quartename=${quartename}"><c:out value='${dataV[2]}' /></a></td>
		
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
			
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[25]}' /></fmt:formatNumber></td>
		
		
		
			
			<%-- <td><c:out value='${dataV[0]}' /></td>
			<td><a href="getDistWiseQuarterReport?state=${dataV[1]}&year=${year}&quarter=${quarter}&stName=<c:out value='${dataV[2]}' />&finName=${finName}&quartename=${quartename}"><c:out value='${dataV[2]}' /></a></td>
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
			<td align="right"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${dataV[24]}' /></fmt:formatNumber></td> --%>
			
		</tr>		
		</c:forEach>
	 	<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
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
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[15]}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[17]}' /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[19]}' /></fmt:formatNumber></b></td>

				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[21]/stcount}' /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[22]/stcount}' /></fmt:formatNumber></b></td>
				
				
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
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[14]}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[15]}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[16]}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[17]}' /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[18]}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[19]}' /></fmt:formatNumber></b></td>

			 	<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[20]/30}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[21]/30}' /></fmt:formatNumber></b></td>
 --%>
				
			</tr>
	</c:forEach> 
    </c:if>
    	<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="14" class="required" style="color:red;">Data Not Found</td>
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