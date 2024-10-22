<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report O12 - District and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2) of <c:out value ="${stname}"/>.</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">

	function exportExcel(state, year, stName, yrName) {
		document.getElementById("stName").value = stName;
		document.getElementById("yrName").value = yrName;
		document.getElementById("state").value = state;
		document.getElementById("year").value = year;
		document.indicators.action = "getDistWiseFrmrIncmExcel";
		document.indicators.method = "post";
		document.indicators.submit();

	}
	
	function downloadPDF(state, year, stName, yrName) {
		document.getElementById("stName").value = stName;
		document.getElementById("yrName").value = yrName;
		document.getElementById("state").value = state;
		document.getElementById("year").value = year;
		document.indicators.action = "getDistWiseFrmrIncmPDF";
		document.indicators.method = "post";
		document.indicators.submit();

	}
</script>
</head>


<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O12 - District and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).</h5></div>

<br>	
	<div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
	<form:form action="indicators" name="indicators" id="indicators" method="get">
		<input type="hidden" name="stName" id="stName" value="" />
		<input type="hidden" name="yrName" id="yrName" value="" />
		<input type="hidden" name="state" id="state" value="" />
		<input type="hidden" name="year" id="year" value="" />
	</form:form>
	
	
<c:if test="${finalList != null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${state}','${year}','${stName}','${yrName}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${year}','${stName}','${yrName}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <tr>
	 	<th colspan ="2" style="text-align:left; ">State: <c:out value ="${stName}"/> &nbsp; Financial Year: <c:out value ="${yrName}"/></th>
		<th colspan="${finMapsize*2}" style="text-align:right; ">All Area in Ha.  </th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">District Name</th> 
      <th style="text-align:center" colspan="${finMapsize}">Change in Crop Area Year wise</th>
      <th style="text-align:center" colspan="${finMapsize}">Change in Farmer Income Year wise(in %)</th>
    </tr>
    
    <tr>
    <c:forEach var = "i" begin = "1" end = "2">
    	<c:forEach items="${finMap}" var="finMap" varStatus="status">
      		<th  class="text-center"><c:out value = "${finMap.value}"/></th>
     	</c:forEach>
     </c:forEach> 
    </tr>
    
  </thead>

  <tbody>
     <tr>
     	<c:forEach var = "i" begin = "1" end = "${(finMapsize*2) +2}">
     		<th class="text-center"><c:out value = "${i}"/></th>
     	</c:forEach> 
     </tr>

	 <c:if test="${finalList != null}">
		<c:set var = "count" value = "1"/>
		<c:set var = "area" value = "0"/>
		<c:set var = "frmr" value = "0"/>
		<c:forEach items="${finalList}" var="dataV" varStatus="status">
		
		<tr>		
			<td><c:out value='${count}' /></td>
			<td><a href="getProjWiseYrlyIncrmntInFrmrIncm?dcode=${dataV.dcode}&year=${year}&stName=${stName}&yrName=${yrName}&distName=${dataV.distname}"><c:out value='${dataV.distname}' /></a></td>
			<c:forEach items="${areaMap}" var="areaMap" varStatus="status">
				<c:if test ="${dataV.dcode eq fn:substringBefore(areaMap.key,',')}">
      					<td class="text-center"><c:out value = "${areaMap.value}"/></td>
      					<c:set var = "area" value = "${areaMap.value}"/>
				</c:if>
     		</c:forEach>
     		<c:forEach items="${frmrMap}" var="frmrMap" varStatus="status">
				<c:if test ="${dataV.dcode eq fn:substringBefore(frmrMap.key,',')}">
      					<td class="text-center"><c:out value = "${frmrMap.value}"/></td>
      					<c:set var = "frmr" value = "${frmrMap.value}"/>
				</c:if>
     		</c:forEach>
			
			
		</tr>	
		<c:set var = "count" value = "${count + 1}"/>	
		</c:forEach>
		
    </c:if>
    	<c:if test="${finalListSize==0}">
			<tr>
				<td align="center" colspan="${(finMapsize*2) +2}" class="required" style="color:red;">Data Not Found</td>
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