<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report CW4- State-wise, District-wise, Project-wise, Year-wise and Scheme-wise Convergence Work Details</title>

<script src='<c:url value="/resources/js/convergedExpntr.js" />'></script>
<script type="text/javascript">
function validation() 
{	
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("finyear").options[document.getElementById("finyear").selectedIndex].text;
    var schemeName = document.getElementById("scheme").options[document.getElementById("scheme").selectedIndex].text;
   	
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
    document.getElementById("schemeName").value=schemeName;
		
    document.getConvergedExpndtr.action="getConvergedExpndtrReport";
	document.getConvergedExpndtr.method="post";
	document.getConvergedExpndtr.submit();
	return false;
}

function downloadPDF(stcd,dcode,projid,sid,finyr,stName,distName,projName,schemeName,yearName)
{
	document.getElementById("stcd").value=stcd;
    document.getElementById("dcode").value=dcode;
    document.getElementById("projid").value=projid;
    document.getElementById("finyr").value=finyr;
    document.getElementById("sid").value=sid;
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("schemeName").value=schemeName;
    document.getElementById("yearName").value=yearName;
    document.getConvergedExpndtr.action="downloadPDFConvergedExpndtr";
	document.getConvergedExpndtr.method="post";
	document.getConvergedExpndtr.submit();
}
	
function exportExcel(stcd,dcode,projid,sid,finyr,stName,distName,projName,schemeName,yearName)
{
    document.getElementById("stcd").value=stcd;
    document.getElementById("dcode").value=dcode;
    document.getElementById("projid").value=projid;
    document.getElementById("finyr").value=finyr;
    document.getElementById("sid").value=sid;
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("schemeName").value=schemeName;
    document.getElementById("yearName").value=yearName;
    document.getConvergedExpndtr.action="downloadExcelConvergedExpndtr";
	document.getConvergedExpndtr.method="post";
	document.getConvergedExpndtr.submit();
}

</script>
</head>


<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;">
	<h5><label id="head"></label>Report CW4- State-wise, District-wise, Project-wise, Year-wise and Scheme-wise Convergence Work Details</h5>
</div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getConvergedExpndtrReport" method="post" name="getConvergedExpndtr" id="getConvergedExpndtr">
 	
 	<input type="hidden" id="stcd" name="stcd" value="${stcd}" />
 	<input type="hidden" id="dcode" name="dcode" value="${dcode}" />
	<input type="hidden" id="projid" name="projid" value="${projid}" />
	<input type="hidden" id="sid" name="sid" value="${sid}" />
	<input type="hidden" id="finyr" name="finyr" value="${finyr}" />
 		
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projName" id="projName" value="" />
	<input type="hidden" name="schemeName" id="schemeName" value="" />
	<input type="hidden" name="yearName" id="yearName" value="" />
	
    <table style="width:100%; align-content: center;">
    	<tr align="center">
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All States--</option>
     			<c:forEach items="${stateList}" var="state">
					<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stcd ?'selected':'' }"/>>
					<c:out value="${state.value}" /></option>
				</c:forEach>
    		</select>
          </td>
          <td><b>District <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control district" id="district" name="district" required="required" >
      			<option value="0">--All--</option>
    		</select>
          </td>
          <td><b>Project <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="project" name="project" required="required">
      			<option value="0">--All--</option>
    		</select>
          </td>
          <td><b>Scheme Name <span style="color: red;">*</span></b></td>
          <td>
              <select class="form-control scheme" id="scheme" name="scheme" required="required">
      			<option value="0">--All--</option>
      			<c:forEach items="${SchemeList}" var="schList">
					<option value="<c:out value="${schList.key}"/>" <c:out value="${schList.key== sid ?'selected':'' }"/>>
					<c:out 	value="${schList.value}" /></option>
				</c:forEach>
    		  </select>
          </td>
          <td><b>Financial Year <span style="color: red;">*</span></b></td>
          <td>
              <select class="form-control finyear" id="finyear" name="finYear" required="required">
      			<option value="0">--All--</option>
      			<c:forEach items="${yearList}" var="yearl">
					<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== finyr ?'selected':'' }"/>>
					<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
          </td>
                   
          <td align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport" onclick="validation()" name="btnGetReport" value="Get Report" />
       </tr>
     </table>
 </form:form>
<br>
<c:if test="${beanList ne null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}','${dcode}','${projid}','${sid}','${finyr}','${stName}','${distName}','${projName}','${schemeName}','${yearName}')" class="btn btn-info" >Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}','${dcode}','${projid}','${sid}','${finyr}','${stName}','${distName}','${projName}','${schemeName}','${yearName}')" class="btn btn-info" >PDF</button>
 <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 </c:if>
 <table id="dtBasicExample" cellspacing="0" class="table">
  <thead>
	<tr>
		<th colspan="9" >State : &nbsp; <c:out value='${stName}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distName}' /> &nbsp;&nbsp;&nbsp; 
		Project : &nbsp; <c:out value='${projName}' /> &nbsp;&nbsp;&nbsp;Scheme Name : &nbsp; <c:out value='${schemeName}' />
		&nbsp;&nbsp;&nbsp;Financial Year : &nbsp; <c:out value='${yearName}' /> </th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Name of the Activity</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Total No of Converged Works</th>
      <th colspan="3" style="text-align:center; vertical-align: middle; width: 2%;">Status of Converged Works</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Achievement</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Unit</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Expenditure Under WDC-PMKSY 2.0 (in Rs.)</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">Expenditure Under Converged Schemes (in Rs.)</th>
    </tr>
    <tr>
     	<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
     	<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
     	<th style="text-align:center; vertical-align: middle; width: 2%;">Foreclosed</th>
    </tr>				
  </thead>

  <tbody id = "convergedExpRptTbody">
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
	</tr>  				
  				
  		<c:set var="headVal" value="0" />
		<c:set var="actCount" value="1" />
		<c:set var="unit" value="" />
		<c:set var="totConWorks" value="0"/>
  		<c:set var="totOngoing" value="0" />
  		<c:set var="totCompleted" value="0" />
  		<c:set var="totForeclosed" value="0" />
  		<c:set var="totachievement" value="0.00" />
  		<c:set var="totExpndtrWDC2" value="0" />
  		<c:set var="totExpndtrCnvrgdScheme" value="0" />
  		<c:set var="totalExpndtrWDC2" value="0" />
  		<c:set var="totalExpndtrCnvrgdScheme" value="0" />
		
 		<c:forEach items="${beanList}" var="hd"> 		
			<c:if test="${hd.head_code ne headVal}">
				<c:if test ="${headVal ne 0 && headVal<8}">
					<tr>
						<th> Sub-Total </th>
						<th class="text-right"> <c:out value="${totConWorks}"/> </th>
						<th class="text-right"> <c:out value="${totOngoing}"/> </th>
						<th class="text-right"> <c:out value="${totCompleted}"/> </th>
						<th class="text-right"> <c:out value="${totForeclosed}"/> </th>
						<th class="text-right"> <c:out value="${totachievement}"/> </th>
						<th class="text-left"> <c:out value="${unit}"/> </th>
						<th class="text-right"> <c:out value="${totExpndtrWDC2}"/> </th>
						<th class="text-right"> <c:out value="${totExpndtrCnvrgdScheme}"/> </th>
													
						<c:set var="totConWorks" value="0" />
  						<c:set var="totOngoing" value="0" />
  						<c:set var="totCompleted" value="0" />
  						<c:set var="totForeclosed" value="0" />
  						<c:set var="totachievement" value="0.00" />
  						<c:set var="totExpndtrWDC2" value="0" />
  						<c:set var="totExpndtrCnvrgdScheme" value="0" />
					</tr>		
				</c:if>
   				<tr>
					<td class="text-left" colspan="9"><c:out value="${hd.head_code}.${hd.head_desc}"/></td>
				</tr>
   				<c:set var="headVal" value="${hd.head_code}" />
				<c:set var="actCount" value="1" />
   			</c:if>
 
   	<tr>
   		<td class="text-left"><c:out value="${hd.head_code}.${actCount} ${hd.actname}"/></td>
   		<td class="text-right"><c:out value="${hd.convergedworks }"/></td>
   		<td class="text-right"><c:out value="${hd.ongoing }"/></td>
   		<td class="text-right"><c:out value="${hd.completed }"/></td>
   		<td class="text-right"><c:out value="${hd.foreclosed }"/></td>
   		<td class="text-right"><c:out value="${hd.achievement }"/></td>
   		<td class="text-left"><c:out value="${hd.unitname }"/></td>
   		<td class="text-right"><c:out value="${hd.expndtr_wdc2 }"/></td>
   		<td class="text-right"><c:out value="${hd.expndtr_cnvrgd_scheme }"/></td>   
   </tr>
   
   <c:set var="unit" value="${hd.unitname}" />
   <c:set var="totConWorks" value="${totConWorks + hd.convergedworks}" />
   <c:set var="totOngoing" value="${totOngoing + hd.ongoing}" />
   <c:set var="totCompleted" value="${totCompleted + hd.completed}" />
   <c:set var="totForeclosed" value="${totForeclosed + hd.foreclosed}" />
   <c:set var="totachievement" value="${totachievement + hd.achievement}" />
   <c:set var="totExpndtrWDC2" value="${totExpndtrWDC2 + hd.expndtr_wdc2}" />
   <c:set var="totExpndtrCnvrgdScheme" value="${totExpndtrCnvrgdScheme + hd.expndtr_cnvrgd_scheme}" />
   <c:set var="totalExpndtrWDC2" value="${totalExpndtrWDC2 + hd.expndtr_wdc2}" />
   <c:set var="totalExpndtrCnvrgdScheme" value="${totalExpndtrCnvrgdScheme + hd.expndtr_cnvrgd_scheme}" />   
   <c:set var="actCount" value="${actCount + 1}"/>
   </c:forEach>	
    
   	<tr>
		<td colspan="7" align="right" class="table-primary"><b>Grand Total</b></td>
		<td align="right" class="table-primary"><b><c:out value="${totalExpndtrWDC2}" /></b></td>
		<td align="right" class="table-primary"><b><c:out value="${totalExpndtrCnvrgdScheme}" /></b></td>
	</tr> 
    
    	<c:if test="${beanListSize==0}">
			<tr>
				<td align="center" colspan="9" class="required">Data Not Found</td>
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