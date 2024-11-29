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
<title>Report T3- State-wise, District-wise, Project-wise and Year-wise Details of Annual Works as per EPA/Livelihood/Production Activities</title>
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
    var headName = document.getElementById("headother").options[document.getElementById("headother").selectedIndex].text;
//    alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
    document.getElementById("headName").value=headName;
	var fromYear = document.getElementById("fromyear").value;
	var state= document.getElementById("state").value;
	if(state=='')
	{
		alert('Please select State ');
		document.getElementById("state").focus();
		e.preventDefault();
	}
	if(fromYear=='')
	{
		alert('Please select Year ');
		document.getElementById("fromyear").focus();
		e.preventDefault();
	}
	else{
		
		document.activityWiseUpto.action="epaLivProdWiseYearlyWork";
		document.activityWiseUpto.method="post";
		document.activityWiseUpto.submit();
	}
	return false;

}


function downloadPDF()
{
	 var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	 var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
	 var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
	 var yearName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
//	    alert(stName+distName+projName+yearName);
	 document.getElementById("stName").value=stName;
	 document.getElementById("distName").value=distName;
	 document.getElementById("projName").value=projName;
	 document.getElementById("yearName").value=yearName;
	var fromYear = document.getElementById("fromyear").value;
	var state= document.getElementById("state").value;
	if(state=='')
	{
		alert('Please select State ');
		document.getElementById("state").focus();
		e.preventDefault();
	}
	if(fromYear=='')
	{
		alert('Please select Year ');
		document.getElementById("fromyear").focus();
		e.preventDefault();
	}
	else{
		
		document.activityWiseUpto.action="activityWiseUptoPlanAchievWorkPDF";
		document.activityWiseUpto.method="post";
		document.activityWiseUpto.submit();
	}
	return false;
	
}

function exportExcel()
{
	 var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	 var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
	 var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
	 var yearName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
//	    alert(stName+distName+projName+yearName);
	 document.getElementById("stName").value=stName;
	 document.getElementById("distName").value=distName;
	 document.getElementById("projName").value=projName;
	 document.getElementById("yearName").value=yearName;
	var fromYear = document.getElementById("fromyear").value;
	var state= document.getElementById("state").value;
	if(state=='')
	{
		alert('Please select State ');
		document.getElementById("state").focus();
		e.preventDefault();
	}
	if(fromYear=='')
	{
		alert('Please select Year ');
		document.getElementById("fromyear").focus();
		e.preventDefault();
	}
	else{
		
		document.activityWiseUpto.action="downloadExcelactivityWiseUptoPlanAchievWork";
		document.activityWiseUpto.method="post";
		document.activityWiseUpto.submit();
	}
	return false;
	
}

</script>
</head>


<body>
 <br>
<div class="offset-md-2 col-8 formheading" style="text-align:center;"  ><h5><label id="head"></label>Report T3- State-wise, District-wise, Project-wise and Year-wise Details of Annual Works as per EPA/Livelihood/Production Activities</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="epaLivProdWiseYearlyWork" method="post" name="activityWiseUpto" id="activityWiseUpto">
 	<input type="hidden" id="stCode" name="stCode" value="${stCode}" />
 	<input type="hidden" id="distCode" name="distCode" value="${distCode}" />
	<input type="hidden" id="projId" name="projId" value="${projectId}" />
	<input type="hidden" id="fYear" name="fYear" value="${fromYear}" />
	<input type="hidden" id="head" name="head" value="${head}" />
	
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projName" id="projName" value="" />
	<input type="hidden" name="yearName" id="yearName" value="" />
	<input type="hidden" name="headName" id="headName" value="" />
	
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All State--</option>
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
          
          <td ><b>Head <span style="color: red;">*</span></b></td>
          <td >
             <select class="form-control headother" id="headother" name="headother">
			     <option value="">--Select Head--</option>
			     <option value="E" <c:out value ="${head == 'E' ? 'selected':''}"/>>Entry Point Activity (EPAs)</option>
			     <option value="L" <c:out value ="${head == 'L' ? 'selected':''}"/>>Livelihood Activities for the Asst-less Person</option>
			     <option value="P" <c:out value ="${head == 'P' ? 'selected':''}"/>>Production System</option>
			 </select>
          </td>
         
          <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport1" onclick="validation1()" name="btnGetReport1" value="Get Report" />
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
	<c:if test="${dataListSize > 0}">
	<br>
	
<!-- 	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
<!-- 	<button name="exportPDF" id="exportPDF" onclick="downloadPDF();" class="btn btn-info">PDF</button> -->

 	</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="tblReport" cellspacing="0" class="table">
  <thead>
	<tr>
		<th colspan="10" >State : &nbsp; <c:out value='${stName}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distName}' /> &nbsp;&nbsp;&nbsp; 
		Project : &nbsp; <c:out value='${projName}' /> &nbsp;&nbsp;&nbsp;Financial Year : &nbsp; <c:out value='${yearName}' /> &nbsp;&nbsp;&nbsp;Head : &nbsp; <c:out value='${headName}' /> </th>
	</tr>
    <tr>
      <th style="text-align:center; vertical-align: middle; width: 5%;">S.No.</th>
      <th style="text-align:center; vertical-align: middle; width: 20%;">Name of the Activity</th>
      <th style="text-align:center; vertical-align: middle; width: 5%;">Total No. of Work</th>
      <th style="text-align:center; vertical-align: middle; width: 5%;">Total No. of Work Ongoing</th>
      <th style="text-align:center; vertical-align: middle; width: 5%;">Total No. of Work Completed</th>
      <th style="text-align:center; vertical-align: middle; width: 5%;">Total No. of Work Completed for  <c:out value='${yearName}' /></th>
      <th style="text-align:center; vertical-align: middle; width: 5%;">Total No. of Work forClosed</th>
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
		<c:set var="count" value="1" />
		<c:set var="grndtotWrk" value="0" />
		<c:set var="grndtotOnWrk" value="0" />
		<c:set var="grndtotCoWrk" value="0" />
		<c:set var="grndtotYrlyWrk" value="0" />
		<c:set var="grndtotClsWrk" value="0" />
	
	<c:forEach items="${dataList}" var="dataListSh">
		<tr>
			<td><c:out value= "${count}"/></td>
			<td><c:out value ="${dataListSh.activity_desc}"/></td>
			<td><c:out value ="${dataListSh.uptototwork}"/></td>
			<td><c:out value ="${dataListSh.uptoongoingwork}"/></td>
			<td><c:out value ="${dataListSh.uptocompletework}"/></td>
			<td><c:out value ="${dataListSh.yearcompletework}"/></td>
			<td><c:out value ="${dataListSh.uptoforclosework}"/></td>
		</tr>
		<c:set var="count" value="${count+1}" />
		<c:set var="grndtotWrk" value="${grndtotWrk + dataListSh.uptototwork}" />
		<c:set var="grndtotOnWrk" value="${grndtotOnWrk + dataListSh.uptoongoingwork}" />
		<c:set var="grndtotCoWrk" value="${grndtotCoWrk + dataListSh.uptocompletework}" />
		<c:set var="grndtotYrlyWrk" value="${grndtotYrlyWrk + dataListSh.yearcompletework}" />
		<c:set var="grndtotClsWrk" value="${grndtotClsWrk + dataListSh.uptoforclosework}" />
	
	</c:forEach>
		<c:if test="${dataListSize gt 0}">
			<tr>
				<th colspan ="2" class="text-center">Grand Total</th>
				<th class="text-left"><c:out value= "${grndtotWrk}"/></th>
				<th class="text-left"><c:out value= "${grndtotOnWrk}"/></th>
				<th class="text-left"><c:out value= "${grndtotCoWrk}"/></th>
				<th class="text-left"><c:out value= "${grndtotYrlyWrk}"/></th>
				<th class="text-left"><c:out value= "${grndtotClsWrk}"/></th>
				
			</tr>
		</c:if>
	
    
    	<c:if test="${dataListSize==0}">
			<tr>
				<td align="center" colspan="7" class="required">Data Not Found</td>
			</tr>
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