<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Report CW3- State-wise, District-wise and Project-wise Convergence Work with Expenditure Details</title>
<script src='<c:url value="/resources/js/convergenceWorks.js" />'></script>
<script type="text/javascript">
function Go() 
{
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var dName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var pName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
    var stcd = document.getElementById("state").value;
    var dcode = document.getElementById("district").value;
    var projid = document.getElementById("project").value;
   
    document.getElementById("stName").value=stName;
    document.getElementById("dName").value=dName;
    document.getElementById("pName").value=pName;
    document.getElementById("stcd").value=stcd;
    document.getElementById("dcode").value=dcode;
    document.getElementById("projid").value=projid;

	document.getConvergenceWorks.action="getConvergenceWorkDetails";
	document.getConvergenceWorks.method="post";
	document.getConvergenceWorks.submit();

	return false;

}

function exportPDF(stcd, dcode, projid, stName, dName, pName){
	document.getElementById("stcd").value=stcd;
	document.getElementById("dcode").value=dcode;
	document.getElementById("projid").value=projid;
	document.getElementById("stName").value=stName;
	document.getElementById("dName").value=dName;
	document.getElementById("pName").value=pName;
	document.getConvergenceWorks.action="downloadPDFConvergenceWorks";
	document.getConvergenceWorks.method="post";
	document.getConvergenceWorks.submit();
}

function exportExcel(stcd, dcode, projid, stName, dName, pName){
	document.getElementById("stcd").value=stcd;
	document.getElementById("dcode").value=dcode;
	document.getElementById("projid").value=projid;
	document.getElementById("stName").value=stName;
	document.getElementById("dName").value=dName;
	document.getElementById("pName").value=pName;
	document.getConvergenceWorks.action="downloadExcelConvergenceWorks";
	document.getConvergenceWorks.method="post";
	document.getConvergenceWorks.submit();
}

</script>
</head>


<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;">
<h5><label id="head"></label>Report CW3- State-wise, District-wise and Project-wise Convergence Work with Expenditure Details</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getConvergenceWorks" method="get" name="getConvergenceWorks" id="getConvergenceWorks">
 	<input type="hidden" id="stName" name="stName" value="${stName}" />
 	<input type="hidden" id="dName" name="dName" value="${dName}" />
	<input type="hidden" id="pName" name="pName" value="${pName}" />
	
	<input type="hidden" name="stcd" id="stcd" value="${stcd}" />
	<input type="hidden" name="dcode" id="dcode" value="${dcode}" />
	<input type="hidden" name="projid" id="projid" value="${projid}" />
	
     <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All States--</option>
     			<c:forEach items="${stateList}" var="state">
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stcd ?'selected':'' }"/>>
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
         
          <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="btnGetReport" onclick="Go()" name="btnGetReport" value="Get Report" />
       </tr>
      </table>
 </form:form>
 

 <button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${dcode}', '${projid}', '${stName}', '${dName}', '${pName}')" class="btn btn-info">Excel</button>
 <button name="exportPDF" id="exportPDF" onclick="exportPDF('${stcd}', '${dcode}', '${projid}', '${stName}', '${dName}', '${pName}')" class="btn btn-info">PDF</button>
 <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>	


<table id = "tblReport" class = "table">
	<thead>
		<tr>
			<th colspan=6 class="text-left">State : ${stName} &emsp; District : ${dName} &emsp; Project : ${pName}</th>
		</tr>
		<tr>
			<th class="text-center">S.No.</th>
			<th class="text-center">Head Name</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Expenditure Under WDC-PMKSY 2.0 (in Rs.)</th>
			<th class="text-center">Expenditure Under Converged Scheme(in Rs.)</th>
		</tr>
	</thead>
	<tbody id = "convergenceWorksRptTbody">
		<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
		</tr>
		<c:forEach items="${convergenceList}" var="work" varStatus="sno">
			<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${work.head_desc}" /></td>
					<td class="text-right"><c:out value="${work.totalworks}" /></td>
					<td class="text-right"><c:out value="${work.convergedworks}" /></td>
					<td class="text-right"><c:out value="${work.expndtr_wdc2}" /></td>
					<td class="text-right"><c:out value="${work.expndtr_cnvrgd_scheme}" /></td>
			</tr>
			
			<c:set var = "totalworks"
			value = "${totalworks + work.totalworks}" />
			<c:set var = "totconvergedworks"
			value = "${totconvergedworks + work.convergedworks}" />
			<c:set var = "totexpndtr"
			value = "${totexpndtr + work.expndtr_wdc2}" />
			<c:set var = "totexpndtrscheme"
			value = "${totexpndtrscheme + work.expndtr_cnvrgd_scheme}" />
			
		</c:forEach>

		<tr>
			<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
			<td align="right" class=" table-primary"><b><c:out value="${totalworks}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totconvergedworks}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totexpndtr}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totexpndtrscheme}" /></b></td>
		</tr>

		<c:if test="${convergenceListSize==0}">
			<tr>
				<td align="center" colspan="6" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
	</tbody>
</table>

 </div>
 </div>
 </div>
 </div>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>