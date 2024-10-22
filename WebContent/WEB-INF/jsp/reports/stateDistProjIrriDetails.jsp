<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>

<script type= "text/javascript">
function exportExcel(){
	document.outcome.action="getStWiseIrriExcel";
	document.outcome.method="post";
	document.outcome.submit();
}
function downloadPDF(){
	document.outcome.action="getStateWiseIrriPDF";
	document.outcome.method="post";
	document.outcome.submit();
}
function exportDistExcel(stname, stcode)
{		
		document.getElementById("stname").value=stname;
		document.getElementById("stcode").value=stcode;
		document.outcome.action="getDistWiseIrriExcel";
		document.outcome.method="post";
		document.outcome.submit();
	
}
function downloadDistPDF(stname, stcode)
{		
		document.getElementById("stname").value=stname;
		document.getElementById("stcode").value=stcode;
		document.outcome.action="getDistrictWiseIrriPDF";
		document.outcome.method="post";
		document.outcome.submit();
	
}

function exportProjExcel(distname, dcode, stname)
{		
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.getElementById("stname").value=stname;
		document.outcome.action="getProjWiseIrriExcel";
		document.outcome.method="post";
		document.outcome.submit();
	
}
function downloadProjPDF(distname, dcode, stname)
{		
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.getElementById("stname").value=stname;
		document.outcome.action="getProjWiseIrriPDF";
		document.outcome.method="post";
		document.outcome.submit();
	
}
</script>

<title>Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)</title>

</head>

<body>
<br>

<form method="post" id="outcome" name="outcome">
			<input type="hidden" name="stcode" id="stcode" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="dcode" id="dcode" value="" />
			<input type="hidden" name="distname" id="distname" value="" />
</form>

<c:if test ="${irriList ne null }">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  >
<h5>Report O12 - State Wise Additional Area Brought Under Irrigation Status</h5>
</div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">

	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th colspan="${yrMapSize}" style="text-align:center; vertical-align: middle; width: 7%;">Additional Area Brought Under Irrigation(Ha.)</th>
       	</tr>
       	<tr>
       		<c:forEach var="yr" items="${yrMap}">
      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value="${yr.value}"/></th>
      		</c:forEach>
       	</tr>
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var ="count" value ="1"/>
			<c:forEach var="yr" items="${yrMap}">
      			<th class="text-center"><c:out value="${count +2}"/></th>
      			<c:set var ="count" value ="${count +1}"/>
      		</c:forEach>
		</tr>
		<c:set var ="count" value ="1"/>
		<c:set var ="stcode" value =""/>
			<c:forEach var="irri" items="${irriList}">
				<tr>
					<c:if test = "${ stcode ne irri.stcode}">
						<td><c:out value="${count}"/></td>
						<td><a href="getDistWiseIrriDetails?stcode=${irri.stcode}&stname=${irri.stname}"><c:out value = "${irri.stname}"/></a></td>
					<c:forEach var="irriMap" items="${irriMap}">
						<c:if test ="${ irri.stcode eq fn:substringBefore(irriMap.key,',')}">
      						<td style="text-align:center;"><c:out value="${irriMap.value}"/></td>
      					</c:if>

      				</c:forEach>
      				<c:set var ="count" value ="${count +1}"/>
      				</c:if>
				</tr>
				<c:set var ="stcode" value ="${irri.stcode}"/>
			</c:forEach>
	 		<tr>
			<th colspan ="2" style="text-align:center;">Grand Total</th>
				<c:forEach var = "totMap" items ="${totMap}">
					<th style="text-align:center;"><c:out value ="${totMap.value}"/></th>
				</c:forEach>
			</tr>
	</tbody>
  </table>
</div>
</div>
</div>
</c:if>

<c:if test ="${irriDList ne null }">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  >
<h5>Report O12 - District Wise Additional Area Brought Under Irrigation Status for <c:out value ="${stname}"/> State</h5>
</div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">

	<button name="exportDExcel" id="exportDExcel" onclick="exportDistExcel('${stname}','${stcode}')" class="btn btn-info">Excel</button>
	<button name="exportDPDF" id="exportDPDF" onclick="downloadDistPDF('${stname}','${stcode}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">District Name</th>
      		<th colspan="${yrMapSize}" style="text-align:center; vertical-align: middle; width: 7%;">Additional Area Brought Under Irrigation(Ha.)</th>
       	</tr>
       	<tr>
       		<c:forEach var="yr" items="${yrMap}">
      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value="${yr.value}"/></th>
      		</c:forEach>
       	</tr>
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var ="count" value ="1"/>
			<c:forEach var="yr" items="${yrMap}">
      			<th class="text-center"><c:out value="${count +2}"/></th>
      			<c:set var ="count" value ="${count +1}"/>
      		</c:forEach>
		</tr>
		<c:set var ="count" value ="1"/>
		<c:set var ="dcode" value =""/>
			<c:forEach var="irri" items="${irriDList}">
				<tr>
					<c:if test = "${ dcode ne irri.dcode}">
						<td><c:out value="${count}"/></td>
						<td><a href="getProjWiseIrriDetails?dcode=${irri.dcode}&stname=${stname}&distname=${irri.distname}"><c:out value = "${irri.distname}"/></a></td>
					<c:forEach var="irriMap" items="${irriDMap}">
						<c:if test ="${ irri.dcode eq fn:substringBefore(irriMap.key,',')}">
      						<td style="text-align:center;"><c:out value="${irriMap.value}"/></td>
      					</c:if>

      				</c:forEach>
      				<c:set var ="count" value ="${count +1}"/>
      				</c:if>
				</tr>
				<c:set var ="dcode" value ="${irri.dcode}"/>
			</c:forEach>
	 		<tr>
			<th colspan ="2" style="text-align:center;">Grand Total</th>
				<c:forEach var = "totMap" items ="${totMap}">
					<th style="text-align:center;"><c:out value ="${totMap.value}"/></th>
				</c:forEach>
			</tr>
	</tbody>
  </table>
</div>
</div>
</div>
</c:if>

<c:if test ="${irriPList ne null }">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  >
<h5>Report O12 - Project Wise Additional Area Brought Under Irrigation Status for <c:out value ="${distname}"/> District of <c:out value ="${stname}"/> State</h5>
</div>
<br>
<div class ="card">

	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8"  id="exportHtmlToPdf">
	<button name="exportPExcel" id="exportPExcel" onclick="exportProjExcel('${distname}','${dcode}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPPDF" id="exportPPDF" onclick="downloadProjPDF('${distname}','${dcode}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
      		<th colspan="${yrMapSize}" style="text-align:center; vertical-align: middle; width: 7%;">Additional Area Brought Under Irrigation(Ha.)</th>
       	</tr>
       	<tr>
       		<c:forEach var="yr" items="${yrMap}">
      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value="${yr.value}"/></th>
      		</c:forEach>
       	</tr>
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var ="count" value ="1"/>
			<c:forEach var="yr" items="${yrMap}">
      			<th class="text-center"><c:out value="${count +2}"/></th>
      			<c:set var ="count" value ="${count +1}"/>
      		</c:forEach>
		</tr>
		<c:set var ="count" value ="1"/>
		<c:set var ="pcode" value =""/>
			<c:forEach var="irri" items="${irriPList}">
				<tr>
					<c:if test = "${ pcode ne irri.projid}">
						<td><c:out value="${count}"/></td>
						<td><c:out value = "${irri.projname}"/></td>
					<c:forEach var="irriMap" items="${irriPMap}">
						<c:if test ="${ irri.projid eq fn:substringBefore(irriMap.key,',')}">
      						<td style="text-align:center;"><c:out value="${irriMap.value}"/></td>
      					</c:if>

      				</c:forEach>
      				<c:set var ="count" value ="${count +1}"/>
      				</c:if>
				</tr>
				<c:set var ="pcode" value ="${irri.projid}"/>
			</c:forEach>
	 		<tr>
			<th colspan ="2" style="text-align:center;">Grand Total</th>
				<c:forEach var = "totMap" items ="${totMap}">
					<th style="text-align:center;"><c:out value ="${totMap.value}"/></th>
				</c:forEach>
			</tr>
	</tbody>
  </table>
</div>
</div>
</div>
</c:if>

<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>