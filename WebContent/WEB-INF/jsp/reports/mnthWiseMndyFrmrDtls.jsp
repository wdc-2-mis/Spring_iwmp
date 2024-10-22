<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Report O10- Financial Year and Month wise Mandays Generated and Farmers Benefitted Details</title>
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
</head>
<script type= "text/javascript">

function validation1() 
{
	var fromYear = document.getElementById("finyear").value;
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var yearName = document.getElementById("finyear").options[document.getElementById("finyear").selectedIndex].text;
//    alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("yearName").value=yearName;
	
	if(fromYear=='')
	{
		alert('Please select Year ');
		document.getElementById("finyear").focus();
		e.preventDefault();
	}
	else{
		
		document.outcome.action="getMonthWiseStatusAdditionalParameter";
		document.outcome.method="post";
		document.outcome.submit();
	}
	return false;

}


function exportExcel(stCode,distCode,projId,fYear){
	 var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
	    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
	    var yearName = document.getElementById("finyear").options[document.getElementById("finyear").selectedIndex].text;
	    document.getElementById("stName").value=stName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.getElementById("yearName").value=yearName;
	
	
	
	document.getElementById("stCode").value=stCode;
	document.getElementById("distCode").value=distCode;
	document.getElementById("projId").value=projId;
	document.getElementById("fYear").value=fYear;
	document.outcome.action="getMonthwiseMandaysExcel";
	document.outcome.method="post";
	document.outcome.submit();
}
function downloadPDF(stCode,distCode,projId,fYear){
	 var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
	    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
	    var yearName = document.getElementById("finyear").options[document.getElementById("finyear").selectedIndex].text;
	    document.getElementById("stName").value=stName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.getElementById("yearName").value=yearName;
	    document.getElementById("stCode").value=stCode;
	    document.getElementById("distCode").value=distCode;
	    document.getElementById("projId").value=projId;
	    document.getElementById("fYear").value=fYear;
	    document.outcome.action="downloadMonthwiseStatusAddPrmPDF";
	    document.outcome.method="post";
	  document.outcome.submit();
}
</script>
<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O10- Financial Year and Month wise Mandays Generated and Farmers Benefitted Details</h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	<c:set var ="finYrName" value =""/>
	<form action="getMonthWiseStatusAdditionalParameter" method="post" id="outcome" name="outcome">
	<input type="hidden" id="stCode" name="stCode" value="${stcode}" />
 	<input type="hidden" id="distCode" name="distCode" value="${dcode}" />
	<input type="hidden" id="projId" name="projId" value="${projid}" />
	<input type="hidden" id="fYear" name="fYear" value="${fyear}" />
	
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
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stcode ?'selected':'' }"/>>
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
              <select class="form-control finyear" id="finyear" name="finyear" required="required">
      			<option value="">--Select Year--</option>
      			<c:forEach items="${yearList}" var="yearl">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== fyear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
          </td>
         
          <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport1" onclick="validation1()" name="btnGetReport1" value="Get Report" />
         <!--  <input type="submit" class="btn btn-info form-control" id="btnGetReport2" name="btnGetReport" value="Get Report"> </td> -->
       </tr>
      </table>
	</form>
	<c:if test = "${listsize > 0 }">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcode}','${dcode}','${projid}','${fyear}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcode}','${dcode}','${projid}','${fyear}')" class="btn btn-info">PDF</button>
	</c:if>

	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 20%;">Parameter Names</th>
      		<c:forEach var="user" items="${netTreatledata}">
     			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value='${user.month_name}' /> </th>
     		</c:forEach>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">Total</th>
       	</tr>
<!--      	<tr> -->
<%--      		<c:forEach var="user" items="${netTreatledata}"> --%>
<%--      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value='${user.month_name}' /> </th> --%>
<%--      		</c:forEach> --%>
<!-- 		</tr> -->
		
  	</thead>
  	<tbody>
    	<tr>
    		<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var="no" value="1" />
			<c:if test = "${listsize >0 }">
      			<c:forEach var="user" items="${netTreatledata}">
     				<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value='${no +2}' /> </th>
     				<c:set var="no" value="${no+1}" />
     			</c:forEach>
     		<th class="text-center"><c:out value='${no +2}' /></th>
      		</c:if>
      		<c:if test = "${listsize == 0 || listsize == null }">
      			<th style="text-align:center; vertical-align: middle; width: 7%;">3 </th>
<!--       			<th class="text-center">4</th> -->
      		</c:if>
		</tr>
		<c:set var="count" value="1" />
		<c:set var="mndy" value="" />
		<c:set var="sc" value="" />
		<c:set var="st" value="" />
		<c:set var="other" value="" />
		<c:set var="total" value="" />
		<c:set var="female" value="" />
		<c:set var="smallfrmr" value="" />
		<c:set var="mrgnlFrmr" value="" />
		<c:set var="lndlss" value="" />
		<c:set var="bpl" value="" />
		<c:choose>
			<c:when test="${listsize >0}"> 
				<c:forEach var="listUser" items="${netTreatledata}">
					<c:set var="manday" value="${manday + listUser.man_day }" />
					<c:set var="sc" value="${sc +listUser.sc }" />
					<c:set var="st" value="${st + listUser.st }" />
					<c:set var="other" value="${other + listUser.other }" />
					<c:set var="total" value="${total + listUser.total }" />
					<c:set var="female" value="${female + listUser.female }" />
					<c:set var="small" value="${small + listUser.small }" />
					<c:set var="mirginal" value="${mirginal + listUser.mirginal }" />
					<c:set var="landless" value="${landless + listUser.landless }" />
					<c:set var="bpl" value="${bpl + listUser.bpl }" />
				</c:forEach>
			
		<c:forEach var = "i" begin = "0" end = "12">
			<c:if test = "${count  ==1}">
				<tr><th colspan="15" style="text-align:center; vertical-align: middle; width: 63%;">Total Number Of Mandays Generated</th></tr>
			</c:if>
			<c:if test = "${count  ==2}">
				<tr><th colspan="15" style="text-align:center; vertical-align: middle; width: 63%;">Total Number Of Farmer Benefitted</th></tr>
			</c:if>
			<c:if test = "${count <11}">
				<tr>
					<td><c:out value='${count}' /></td>
					<c:if test = "${count == 1}">
						<td align="left"><c:out value='Mandays' /></td>
					</c:if>
					<c:if test = "${count == 2}">
						<td align="left"><c:out value='SC' /></td>
					</c:if>
					<c:if test = "${count == 3}">
						<td align="left"><c:out value='ST' /></td>
					</c:if>
					<c:if test = "${count == 4}">
						<td align="left"><c:out value='Others' /></td>
					</c:if>
					<c:if test = "${count == 5}">
						<td align="left"><c:out value='Total' /></td>
					</c:if>
					<c:if test = "${count == 6}">
						<td align="left"><c:out value='Female' /></td>
					</c:if>
					<c:if test = "${count == 7}">
						<td align="left"><c:out value='Small Farmers' /></td>
					</c:if>
					<c:if test = "${count == 8}">
						<td align="left"><c:out value='Marginal Farmers' /></td>
					</c:if>
					<c:if test = "${count == 9}">
						<td align="left"><c:out value='Landless' /></td>
					</c:if>
					<c:if test = "${count == 10}">
						<td align="left"><c:out value='BPL' /></td>
					</c:if>
						
					<c:forEach var="list" items="${netTreatledata}">
						<c:if test = "${count == 1}">
							<td align="right"><c:out value='${list.man_day}' /></td>
						</c:if>
						<c:if test = "${count == 2}">
							<td align="right"><c:out value='${list.sc}' /></td>
						</c:if>
					<c:if test = "${count == 3}">
						<td align="right"><c:out value='${list.st}' /></td>
					</c:if>
					<c:if test = "${count == 4}">
						<td align="right"><c:out value='${list.other}' /></td>
					</c:if>
					<c:if test = "${count == 5}">
						<td align="right"><c:out value='${list.total}' /></td>
					</c:if>
					<c:if test = "${count == 6}">
						<td align="right"><c:out value='${list.female}' /></td>
					</c:if>
					<c:if test = "${count == 7}">
						<td align="right"><c:out value='${list.small}' /></td>
					</c:if>
					<c:if test = "${count == 8}">
						<td align="right"><c:out value='${list.mirginal}' /></td>
					</c:if>
					<c:if test = "${count == 9}">
						<td align="right"><c:out value='${list.landless}' /></td>
					</c:if>
					<c:if test = "${count == 10}">
						<td align="right"><c:out value='${list.bpl}' /></td>
					</c:if>
					</c:forEach>
					
					<c:if test = "${count == 1}">
							<td align="right"><c:out value='${manday}' /></td>
						</c:if>
						<c:if test = "${count == 2}">
							<td align="right"><c:out value='${sc}' /></td>
						</c:if>
					<c:if test = "${count == 3}">
						<td align="right"><c:out value='${st}' /></td>
					</c:if>
					<c:if test = "${count == 4}">
						<td align="right"><c:out value='${other}' /></td>
					</c:if>
					<c:if test = "${count == 5}">
						<td align="right"><c:out value='${total}' /></td>
					</c:if>
					<c:if test = "${count == 6}">
						<td align="right"><c:out value='${female}' /></td>
					</c:if>
					<c:if test = "${count == 7}">
						<td align="right"><c:out value='${small}' /></td>
					</c:if>
					<c:if test = "${count == 8}">
						<td align="right"><c:out value='${mirginal}' /></td>
					</c:if>
					<c:if test = "${count == 9}">
						<td align="right"><c:out value='${landless}' /></td>
					</c:if>
					<c:if test = "${count == 10}">
						<td align="right"><c:out value='${bpl}' /></td>
					</c:if>
					
					</tr>
					</c:if>
					<c:set var="count" value="${count+1}" />
					
					
					
		</c:forEach>
	</c:when>
			<c:otherwise>
			<tr>
				<td colspan="${listsize + 3}" style="text-align:center; vertical-align: middle; width: 63%;">-----No Data Available----</td>
			</tr>
			</c:otherwise>
	</c:choose>
			
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