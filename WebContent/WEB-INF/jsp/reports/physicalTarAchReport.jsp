<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report A3- State, District, Project and Year-wise Details of Achievements Against Annual Physical Action Plan Targets as per List of Activities</title>

<script src='<c:url value="/resources/js/physicalTarAch.js" />'></script>
<script type="text/javascript">
function validation1() 
{
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
   
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
		document.getPhysicalTarAchReport.action="getphysicalTarAchReport";
		document.getPhysicalTarAchReport.method="post";
		document.getPhysicalTarAchReport.submit();
	}
	return false;

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
    document.getPhysicalTarAchReport.action="downloadPhysicalTarAchPDF";
	document.getPhysicalTarAchReport.method="post";
	document.getPhysicalTarAchReport.submit();
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
    document.getPhysicalTarAchReport.action="getPhyTarAchievExcel";
	document.getPhysicalTarAchReport.method="post";
	document.getPhysicalTarAchReport.submit();
	}

</script>
</head>


<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head"></label>Report A3- State, District, Project and Year-wise Details of Achievements Against Annual Physical Action Plan Targets as per List of Activities</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getPhysicalTarAchReport" method="post" name="getPhysicalTarAchReport" id="getPhysicalTarAchReport">
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
      			<option value="0">--All--</option>
      			<c:forEach items="${yearList}" var="yearl">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== fromYear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
          </td>
         
          <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport" onclick="validation1()" name="btnGetReport" value="Get Report" />
       </tr>
      </table>
 </form:form>
<br>
<c:if test="${beanList ne null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}','${distCode}','${projectId}','${fromYear}')" class="btn btn-info" >Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}','${distCode}','${projectId}','${fromYear}')" class="btn btn-info" >PDF</button>
 <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 </c:if>
 <table id="dtBasicExample" cellspacing="0" class="table">
  <thead>
	<tr>
		<th colspan="5" >State : &nbsp; <c:out value='${stName}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distName}' /> &nbsp;&nbsp;&nbsp; 
		Project : &nbsp; <c:out value='${projName}' /> &nbsp;&nbsp;&nbsp;Financial Year : &nbsp; <c:out value='${yearName}' /> </th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Name of the Activity</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Unit</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Physical Target</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Physical Achievement</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Achievement % (4*100/3)</th>
     </tr>
			
		
  </thead>

  <tbody>
    <tr>
		<th class="text-center">1</th>
		<th class="text-center">2</th>
		<th class="text-center">3</th>
		<th class="text-center">4</th>
		<th class="text-center">5</th>
		
		
	</tr>
		<tbody id="assetreportTbody">
  				<c:set var="count" value="1"/>
  				<c:set var="no" value="1"/>
  				<c:set var="headcode" value="0"/>
  				<c:set var="head" value="0"/>
  				<c:set var="targetTotal" value="0.00" />
  				<c:set var="achievementTotal" value="0.00" />
  				<c:set var="perTotal" value="0.00" />
	<c:forEach items="${beanList}" var="hd" >
	
	
		<c:if test="${hd.head_code < 9 && hd.head_code != headcode && head !=0}">

						<tr>
							<th colspan ="2"> Sub-Total </th>
							<th> <c:out value="${targetTotal }"/> </th>
							<th> <c:out value="${achievementTotal }"/> </th>
							
							<c:if test="${targetTotal le 0 }" >
   								<th class="text_right">0.00%</th>
   										</c:if>
   										 <c:if test="${targetTotal gt 0 }" >
   
  						 <c:if test="${(achievementTotal * 100) / targetTotal le 100 }">
  								 <th class="text_right"><c:out value="${(achievementTotal * 100) / targetTotal}"/>%</th>
   										</c:if>
   
 									  <c:if test="${(achievementTotal * 100) / targetTotal gt 100 }">
 											  <th class="text_right">100%</th>
   											</c:if>

   								</c:if>
  
   										

							</tr>
							<c:set var="targetTotal" value="0.00" />
  							<c:set var="achievementTotal" value="0.00" />
  							<c:set var="perTotal" value="0.00" />
							</c:if>
	
   <tr>
   <c:if test="${hd.head_code ne headcode}">
   
	<td class="text-left" colspan="5"><c:out value="${count}.${hd.head_desc}"/></td>

   <c:set var="count" value="${count + 1}" />
   <c:set var="no" value="1"/>
   </c:if>
   </tr>
   <tr>
   <td class="text_left"><c:out value="${count-1}.${no} ${hd.activity_desc}"/></td>

   <td class="text_left"><c:out value="${hd.unitname }"/></td>
   <td class="text_right"><c:out value="${hd.plan }"/></td>
   <td class="text_right"><c:out value="${hd.achievement }"/></td>
   <c:set var="targetTotal" value="${targetTotal + hd.plan}" />
   <c:set var="achievementTotal" value="${achievementTotal + hd.achievement}" />
   
   <c:if test="${hd.plan le 0 }" >
   <td class="text_right">0.00%</td>
   </c:if>
   
   <c:if test="${hd.plan gt 0 }" >
   
   <c:if test="${(hd.achievement * 100) / hd.plan le 100 }">
   <td class="text_right"><c:out value="${(hd.achievement * 100) / hd.plan}"/>%</td>
   </c:if>
   
   <c:if test="${(hd.achievement * 100) / hd.plan gt 100 }">
   <td class="text_right">100%</td>
   </c:if>
   
   </c:if>
   
   </tr>
    <c:set var="head" value="${hd.head_code }"/>
   <c:set var="headcode" value="${hd.head_code }"/>
   <c:set var="no" value="${no + 1}"/>
   </c:forEach>	
    
    	<c:if test="${beanListSize==0}">
			<tr>
				<td align="center" colspan="5" class="required">Data Not Found</td>
			</tr>
		</c:if>
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>
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