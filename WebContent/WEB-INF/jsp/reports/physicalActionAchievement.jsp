<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <c:choose> --%>
<%-- 	<c:when test="${sessionScope.loginid eq null }"> --%>
<%-- 		<%@include file="/WEB-INF/jspf/header.jspf"%> --%>
<%-- 	</c:when> --%>
<%-- 	<c:otherwise> --%>
<%-- 		<%@include file="/WEB-INF/jspf/header2.jspf"%> --%>
<%-- 	</c:otherwise> --%>
<%-- </c:choose> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report A1- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements Against Annual Physical Action Plan Targets as per List of Activities</title>
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
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">

<script src='<c:url value="/resources/js/physicalActionAchievement.js" />'></script>
<script type="text/javascript">
function validation1() 
{
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
//    alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
	var fromYear = document.getElementById("fromyear").value;
	
	if(fromYear=='')
	{
		alert('Please select Year ');
		document.getElementById("fromyear").focus();
		e.preventDefault();
	}
	else{
		
		document.monthWisePhysicalAchievement.action="monthWisePhyActAchievement";
		document.monthWisePhysicalAchievement.method="post";
		document.monthWisePhysicalAchievement.submit();
	}
	return false;

}

function exportExcel(stCode,distCode,projId,fYear){
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
	document.getElementById("stCode").value=stCode;
    document.getElementById("distCode").value=distCode;
    document.getElementById("projId").value=projId;
    document.getElementById("fYear").value=fYear;
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
    document.monthWisePhysicalAchievement.action="getMnthWisePhyActAchievExcel";
	document.monthWisePhysicalAchievement.method="post";
	document.monthWisePhysicalAchievement.submit();
	}
function downloadPDF(stCode,distCode,projId,fYear){
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
	document.getElementById("stCode").value=stCode;
    document.getElementById("distCode").value=distCode;
    document.getElementById("projId").value=projId;
    document.getElementById("fYear").value=fYear;
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
    document.monthWisePhysicalAchievement.action="downloadMonthWisePhyActPlanPDF";
	document.monthWisePhysicalAchievement.method="post";
	document.monthWisePhysicalAchievement.submit();
	}

/* function generatePDF() {
	 var doc = new jsPDF();  //create jsPDF object
	  doc.fromHTML(document.getElementById("exportHtmlToPdf1"), // page element which you want to print as PDF
	  15,
	  15, 
	  {
	    'width': 100  //set width
	  },
	  function(a) 
	   {
	    doc.save("HTML2PDF.pdf"); // save file name as HTML2PDF.pdf
	  });
	} */

</script>
</head>


<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head"></label>Report A1- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements Against Annual Physical Action Plan Targets as per List of Activities</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="monthWisePhyActAchievement" method="post" name="monthWisePhysicalAchievement" id="monthWisePhysicalAchievement">
 	<input type="hidden" id="stCode" name="stCode" value="${stCode}" />
 	<input type="hidden" id="distCode" name="distCode" value="${distCode}" />
	<input type="hidden" id="projId" name="projId" value="${projectId}" />
	<input type="hidden" id="fYear" name="fYear" value="${fromYear}" />
	
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projName" id="projName" value="" />
	<input type="hidden" name="yearName" id="yearName" value="" />
	
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All States--</option>
     			<c:forEach items="${stateList}" var="state">
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${state.value}" /></option>
				</c:forEach>
    		</select>
          </td>
          <td><b>District <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control district" id="district" name="district" required="required" >
      			<option value="0">--All--</option>
    		</select>
          </td>
          <td ><b>Project <span style="color: red;">*</span></b></td>
          <td >
             <select class="form-control project" id="project" name="project" required="required">
      			<option value="0">--All--</option>
    		</select>
          </td>
          <td ><b>Financial Year <span style="color: red;">*</span></b></td>
          <td >
              <select class="form-control finyear" id="fromyear" name="fromYear" required="required">
      			<option value="">--Select Year--</option>
      			<c:forEach items="${yearList}" var="yearl">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== fromYear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
          </td>
         
          <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport" onclick="validation1()" name="btnGetReport" value="Get Report" />
         <!--  <input type="submit" class="btn btn-info form-control" id="btnGetReport2" name="btnGetReport" value="Get Report"> </td> -->
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
 <c:if test="${monthListSize ne null}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}','${distCode}','${projectId}','${fromYear}')" class="btn btn-info" >Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}','${distCode}','${projectId}','${fromYear}')" class="btn btn-info" >PDF</button>	
<!-- <button name="exportPDF" id="exportPDF" onclick="exportPDF()" class="btn btn-info">PDF</button> -->
	</c:if>
	
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

<c:if test="${projectId != 0 && projectId ne null}">
		<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong> <b style="color: red">NS</b> represents Achievement <b style="color: red">Not Submitted</b>.</label>
</c:if>
<table id="dtBasicExample" cellspacing="0" class="table">
  <thead>
	<tr>
		<th colspan="17" >State : &nbsp; <c:out value='${stName}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distName}' /> &nbsp;&nbsp;&nbsp; 
		Project : &nbsp; <c:out value='${projName}' /> &nbsp;&nbsp;&nbsp;Financial Year : &nbsp; <c:out value='${yearName}' /> </th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Name of the Activity</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Unit</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Physical Target</th>
      <th style="text-align:center" colspan="<c:out value='${monthListSize+1}'/>">Physical Achievement</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Achievement % (16*100/3)</th>
      <c:if test="${monthListSize eq null}">
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Total</th>
      </c:if>
     </tr>
     <tr> 
      	<c:set var="count1" value="0" />
     	<c:choose>
			<c:when test="${monthListSize ne null}">
				<c:forEach items="${monthList}" var="monthListShow" begin="0" end="${monthListSize}">
					<th class="text-center"><c:out value='${monthListShow}' /></th>
				</c:forEach>
				<th style="text-align:center">Total</th>
			</c:when>
			<c:otherwise>
			<th></th>
			</c:otherwise>
			
		</c:choose>
		
      	
    </tr>
  </thead>

  <tbody>
    <tr>
		<th class="text-center">1</th>
		<th class="text-center">2</th>
		<c:set var="count1" value="3" />
		<c:if test="${monthListSize eq null}">
		<c:set var="monthListSize" value="1" />
		</c:if>
		<c:forEach begin="0" end="${monthListSize+2}">
			<c:set var="counter" value="${counter + 1}" />
			<th class="text-center"><c:out value='${count1}'/> <c:set
					var="count1" value="${count1+1}" /></th>
		</c:forEach>
	</tr>
	<c:forEach items="${monthList}" var="yrListSh" begin="0" end="0">
		<c:set var="startMon" value="${yrListSh}" />
	</c:forEach>
	<c:if test="${totalRec > 0}">
		<c:set var="counter" value="0" />
		<c:set var="count1" value="1" />
		<c:set var="headVal" value="" />
		<c:set var="actVal" value="" />
		<c:set var="hdCount" value="1" />
		<c:set var="actCount" value="1" />
		<c:set var="subActCount" value="1" />
		<c:set var="colTotal" value="0" />
		<c:set var="totalCnt" value="0" />

		<c:set var="countHead" value="0" />
		<c:set var="headOld" value="" />
		<c:set var="keyPlc" value="0" />
		<c:set var="headYrSum" value="0" />
		<c:set var="getPhyTargt" value="0.00" />
		<c:set var="getPhyAch1" value="0.00" />
		<c:set var="getPhyAchMay" value="0.00" />
		<c:set var="getPhyAchJune" value="0.00" />
		<c:set var="getPhyAchJuly" value="0.00" />
		<c:set var="getPhyAchAug" value="0.00" />
		<c:set var="getPhyAchSep" value="0.00" />
		<c:set var="getPhyAchOct" value="0.00" />
		<c:set var="getPhyAchNov" value="0.00" />
		<c:set var="getPhyAchDec" value="0.00" />
		<c:set var="getPhyAchJan" value="0.00" />
		<c:set var="getPhyAchFeb" value="0.00" />
		<c:set var="getPhyAchMar" value="0.00" />
		<c:set var="getPhyAchTot" value="0.00" />
		<c:set var="getPhyAchPer" value="0.00" />
		<c:set var="unitName" value="" />
		<c:forEach items="${dataList}" var="dataListSh">
			<c:if test="${countHead == 0}">
				<c:set var="countHead" value="1" />
				<c:set var="headOld" value="${dataListSh[2]}" />
			</c:if>

			<c:if test="${headOld != dataListSh[2]}">
				<c:set var="headYrSum" value="0" />
				
				<c:set var="countHead" value="${countHead + 1}" />
				<c:set var="headOld" value="${dataListSh[2]}" />
				<c:set var="keyPlc" value="${keyPlc + 1}" />
			</c:if>
			<c:choose>
				<c:when test="${dataListSh[1] == startMon}">
					<c:set var="counter" value="1" />
					<c:set var="colTotal" value="0.00" />
					<c:set var="colTotal" value="${colTotal + dataListSh[8]}" />
					<c:set var="count1" value="1" />
					
					<c:if test="${dataListSh[3] != null}">
						<c:if test="${headVal != dataListSh[2]}">
						<c:if test= "${hdCount != 1}">
						<c:set var="totalCnt" value="${totalCnt + 1}" />
						<c:if test = "${totalCnt lt 8 }">
						<tr>
							<th colspan ="2"> Sub-Total </th>
							<c:if test ="${ unitName =='nos' || dataListSh[9] =='nos' && totalCnt ==1}">
							<th><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${getPhyTargt}"/></th>
							</c:if>
							<c:if test ="${ unitName =='ha' || dataListSh[9] =='ha' && totalCnt ==1}">
							<th><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyTargt}"/></th>
							</c:if>
							<c:if test ="${ unitName !='ha' && unitName !='nos' && totalCnt !=1}">
							<th></th>
							</c:if>
							<c:set var="listCnt" value="1" />
							<c:forEach items="${monthList}" var="yrListSh" >
							<c:if test ="${listCnt ==1 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAch1 == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAch1 != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAch1}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==2 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchMay == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchMay != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchMay}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==3 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchJune == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchJune != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchJune}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==4 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchJuly == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchJuly != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchJuly}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==5 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchAug == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchAug != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchAug}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==6 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchSep == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchSep != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchSep}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==7 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchOct == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchOct != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchOct}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==8 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchNov == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchNov != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchNov}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==9 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchDec == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchDec != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchDec}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==10 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchJan == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchJan != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchJan}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==11 }">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchFeb == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchFeb != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchFeb}"/></c:if></th>
							</c:if>
							<c:if test ="${listCnt ==12}">
								<th style="text-align: right; padding-right: 5px;"><c:if test = "${getPhyAchMar == 0}"><c:out value =""/></c:if><c:if test = "${getPhyAchMar != 0}"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchMar}"/></c:if></th>
							</c:if>
							<c:set var="listCnt" value="${listCnt +1}" />
							</c:forEach>
							<th style="text-align: right; padding-right: 5px;"><fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${getPhyAchTot}"/></th>
							<c:if test ="${getPhyTargt eq 0 }">
							<th style="text-align: right; padding-right: 5px;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="0.00"/><b> % </b></th>
							</c:if>
							<c:if test ="${getPhyTargt ne 0 }">
							<th style="text-align: right; padding-right: 5px;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${(getPhyAchTot*100)/getPhyTargt}"/><b> % </b></th>
							</c:if>
						</tr>
						</c:if>
						<c:set var="getPhyTargt" value="0.00" />
						<c:set var="getPhyAch1" value="0.00" />
						<c:set var="getPhyAchMay" value="0.00" />
						<c:set var="getPhyAchJune" value="0.00" />
						<c:set var="getPhyAchJuly" value="0.00" />
						<c:set var="getPhyAchAug" value="0.00" />
						<c:set var="getPhyAchSep" value="0.00" />
						<c:set var="getPhyAchOct" value="0.00" />
						<c:set var="getPhyAchNov" value="0.00" />
						<c:set var="getPhyAchDec" value="0.00" />
						<c:set var="getPhyAchJan" value="0.00" />
						<c:set var="getPhyAchFeb" value="0.00" />
						<c:set var="getPhyAchMar" value="0.00" />
						<c:set var="getPhyAchTot" value="0.00" />
						<c:set var="getPhyAchPer" value="0.00" />
						<c:set var="unitName" value="${dataListSh[9]}" />
						</c:if>
							<tr>
								<td class="altrow">
								<SPAN>
								<c:out value="${dataListSh[3]}" /> 
								</SPAN> 
								<br> 
								<c:set	var="headVal" value="${dataListSh[2]}" /> 
								<c:set  var="hdCount" value="${hdCount + 1}" /> 
								<c:set var="actCount" value="1" /> 
								<c:set var="subActCount" value="1" />
								</td>
								<td></td>
								<td></td>
								<td></td>
								<c:forEach items="${monthList}" var="yrListSh" >
									<td></td>
								</c:forEach>
								<td></td>
							</tr>
						</c:if>
						
						<c:if test="${actVal != dataListSh[4]}">
							<tr>
								<td class="altrow">
									<span style="padding-left: 20px;">
										<c:out value="${dataListSh[5]}" /> 
									</span> 
									<br>
									<c:set var="actVal" value="${dataListSh[4]}" />
									<c:set var="actCount" value="${actCount + 1}" />
								</td>
								<td class="altrow">
									<c:out value="${dataListSh[9]}" />
								</td>
									
								<td class="altrow"> 
									<c:if test="${dataListSh[9]=='nos'}"> 
											<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataListSh[10]}"/>
									</c:if>
									<c:if test="${dataListSh[9]!='nos'}">
											<fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${dataListSh[10]}"/>
									</c:if>
									<c:set var="getPhyTargt" value="${getPhyTargt + dataListSh[10]}" />
								</td>
								<td class="altrow" style="text-align: right; padding-right: 5px;">
									<c:if test="${dataListSh[8] == null}">
											<c:out value="0.0" />
											<c:set var="getPhyAch1" value="${getPhyAch1 + 0.0}" />
									</c:if>
									<c:if test="${dataListSh[9]=='nos'}"> 
										<c:if test = "${dataListSh[11] == 'NS'}">
											<c:out value ="NS" />
										</c:if>
										<c:if test = "${dataListSh[11] != 'NS'}">
											<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataListSh[8]}"/>
										</c:if>
										<c:set var="getPhyAch1" value="${getPhyAch1 + dataListSh[8]}" />
									</c:if>
									<c:if test="${dataListSh[9]!='nos'}">
										<c:if test = "${dataListSh[11] == 'NS'}">
											<c:out value ="NS" />
										</c:if>
										<c:if test = "${dataListSh[11] != 'NS'}">
											<fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${dataListSh[8]}"/>
										</c:if>
										<c:set var="getPhyAch1" value="${getPhyAch1 + dataListSh[8]}" />
									</c:if>
								</td>
								<c:if test="${monthListSize == 1}">
									<td class="altrow" style="text-align: right; padding-right: 5px;">
										<c:if test="${target=='P' && dataListSh[9]=='nos'}">
											<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${colTotal}"/>
											<c:set var="getPhyAchTot" value="${getPhyAchTot + colTotal}" />
										</c:if>
										<c:if test="${target=='P' && dataListSh[9]!='nos' && colTotal!=0}">
											<fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${colTotal}"/>
											<c:set var="getPhyAchTot" value="${getPhyAchTot + colTotal}" />
										</c:if>
										<c:if test="${target=='P' && dataListSh[9]!='nos' && colTotal==0}">
  											<c:out value="0.0" />
  											<c:set var="getPhyAchTot" value="${getPhyAchTot + 0.00}" />
  										</c:if>
  									</td>
								</c:if>
							
						</c:if>
							
					</c:if>
			</c:when>
			<c:otherwise>
					<c:set var="colTotal" value="${colTotal + dataListSh[8]}" />
					<c:set var="count1" value="${count1 + 1}" />
					<td class="altrow" style="text-align: right; padding-right: 5px;">
						<!--  <c:out value="${dataListSh[0]}"/>  -->
						
						<c:if test="${dataListSh[8] == null}">
							<c:out value="0.0" /> 
						</c:if>
						<c:if test="${dataListSh[9]=='nos'}"> 
							<c:if test = "${dataListSh[11] == 'NS'}">
								<c:out value ="NS" />
							</c:if>
							<c:if test = "${dataListSh[11] != 'NS'}">
								<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataListSh[8]}"/>
							</c:if>
						</c:if>
						<c:if test="${dataListSh[9]!='nos'}">
							<c:if test = "${dataListSh[11] == 'NS'}">
								<c:out value ="NS" />
							</c:if>
							<c:if test = "${dataListSh[11] != 'NS'}">
								<fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${dataListSh[8]}"/>
							</c:if>
						</c:if>
						<c:if test="${counter ==1}">
							<c:set var="getPhyAchMay" value="${getPhyAchMay + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==2}">
							<c:set var="getPhyAchJune" value="${getPhyAchJune + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==3}">
							<c:set var="getPhyAchJuly" value="${getPhyAchJuly + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==4}">
							<c:set var="getPhyAchAug" value="${getPhyAchAug + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==5}">
							<c:set var="getPhyAchSep" value="${getPhyAchSep + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==6}">
							<c:set var="getPhyAchOct" value="${getPhyAchOct + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==7}">
							<c:set var="getPhyAchNov" value="${getPhyAchNov + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==8}">
							<c:set var="getPhyAchDec" value="${getPhyAchDec + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==9}">
							<c:set var="getPhyAchJan" value="${getPhyAchJan + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==10}">
							<c:set var="getPhyAchFeb" value="${getPhyAchFeb + dataListSh[8]}" />
						 </c:if>
						 <c:if test="${counter ==11}">
							<c:set var="getPhyAchMar" value="${getPhyAchMar + dataListSh[8]}" />
						 </c:if>
						
						 
						<c:set var="counter" value="${counter+1}" />
						 		  		<%-- <c:out value="${counter}"/> --%>
						 		  			
						<c:if test="${counter == monthListSize}">
							<td class="altrow" style="text-align: right; padding-right: 5px;">
								<c:if test="${target=='P' && dataListSh[9]=='nos'}">
									<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${colTotal}"/>
								</c:if> 
								<c:if test="${target=='P' && dataListSh[9]!='nos' && colTotal!=0}">
									<fmt:formatNumber type="number" maxFractionDigits="4" minFractionDigits="4" value="${colTotal}"/>
								</c:if> 
								<c:if test="${target=='P' && dataListSh[9]!='nos' && colTotal==0}">
  									<c:out value="0.0" /> 
  								</c:if>
  								<c:set var="getPhyAchTot" value="${getPhyAchTot + colTotal}" />
  							</td>
  							
  							<td style="text-align: right; padding-right: 5px;">
	  							<c:if test="${dataListSh[10] ne '0.00' }">
	  							
		  							<c:if test="${(colTotal*100)/dataListSh[10] gt '99.99' }">
		  									<c:out value="100.00" /> <b> % </b>
		  									<c:set var="getPhyAchPer" value="${getPhyAchPer + 100.00}" />
		  							</c:if>
	  								<c:if test="${(colTotal*100)/dataListSh[10] lt '99.99' }">
		  									<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${(colTotal*100)/dataListSh[10]}"/> <b> % </b>
		  									<c:set var="getPhyAchPer" value="${getPhyAchPer + (colTotal*100)/dataListSh[10]}" />
		  							</c:if>
	  								
								</c:if> 
								<c:if test="${dataListSh[10] eq '0.00' }">
	  								<c:out value="0.00" /> <b> % </b>
	  								<c:set var="getPhyAchPer" value="${getPhyAchPer + 0.00}" />
	  							</c:if>
  							</td>
						</c:if>   
					</td>
					<%-- <td><c:out value="${colTotal}" /> </td> --%>
						
			</c:otherwise>
		</c:choose>
	</c:forEach>
    
    	<c:if test="${dataListSize==0}">
			<tr>
				<td align="center" colspan="20" class="required">Data Not Found</td>
			</tr>
		</c:if>
    </c:if>
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>

<script type="text/javascript">
		$(document).ready(function() {
			$(".sidebar-btn").click(function() {
				$(".wrapper").toggleClass("collapse");
			});
		});
	</script>
	<footer class=" text-center">
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${sessionScope.loginid eq null }"> --%>
<%-- 				<%@include file="/WEB-INF/jspf/footer.jspf"%> --%>
<%-- 			</c:when> --%>
<%-- 			<c:otherwise> --%>
<%-- 				<%@include file="/WEB-INF/jspf/footer2.jspf"%> --%>
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>

	</footer>
</body>
</html>