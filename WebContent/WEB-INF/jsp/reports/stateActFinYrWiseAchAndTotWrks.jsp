<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
<title>Report A4- State-wise, Activities Wise and Year-wise Details of Target and Achievements and Total Work as per List of Activities</title>
</head>


<script type="text/javascript">


	function showReport(e){
		e.preventDefault();
		$stCode = $('#assetstate option:selected').val();
		$fyCode = $('#year option:selected').val();
		$headCode = $('#head option:selected').val();
		if($stCode===''){
			$('#assetstate').focus();
			$('.error-assetstate').html('Please select State');
			return false;
		}else{
			$('.error-assetstate').html('');
		}
		if($fyCode===''){ 
			$('#year').focus();
			$('.error-year').html('Please select FY');
			return false;
		}else{
			$('.error-year').html('');
		}
		
		if($headCode===''){
			$('#head').focus();
			$('.error-head').html('Please select Head');
			return false;
		}else{
			$('.error-head').html('');
		}
		
		document.assetrpt.action="getStateActYearWiseTarAchWorks";
		document.assetrpt.method="post";
		document.assetrpt.submit();
		
	}
	
	function downloadPDF(){
		var stCode = $('#assetstate').val();
		var fyCode = $('#year').val();
		var headCode = $('#head').val();
		
		document.getElementById("stCode").value=stCode;
	    document.getElementById("fyCode").value=fyCode;
	    document.getElementById("headCode").value=headCode;
	    
	    document.assetrpt.action="downloadAssetReportPDF";
		document.assetrpt.method="post";
		document.assetrpt.submit();
		}

	function exportExcell(){
		var stCode = $('#assetstate').val();
		var fyCode = $('#year').val();
		var headCode = $('#head').val();
		
		document.getElementById("stCode").value=stCode;
	    document.getElementById("fyCode").value=fyCode;
	    document.getElementById("headCode").value=headCode;
	    
	    document.assetrpt.action="downloadExcelassetReport";
		document.assetrpt.method="post";
		document.assetrpt.submit();
		}
	
	</script>
	
	
	
<div class="container-fluid">
<div class="row">
<div class="col text-center">
<br/>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report A4- State-wise, Activities Wise and Year-wise Details of Target and Achievements and Total Work as per List of Activities</h5></div>
<hr/>
</div>
</div>
<div class="row">

<div class="col-12">
<form action="getStateActYearWiseTarAchWorks" method="post" name="assetrpt" id="assetrpt">
	
	<input type="hidden" name="stCode" id="stCode" value="" />
	<input type="hidden" name="fyCode" id="fyCode" value="" />
	<input type="hidden" name="headCode" id="headCode" value="" />
<div class="form-row">
<div class="col-md-2"></div>
  <div class="form-group col-md-2">
    <label for="state">State: <span class="text-danger">*</span><span class="text-danger error-assetstate"></span></label>
    <select class="form-control project" id="assetstate" name="assetstate" required>
<!--     <option value="">--Select State--</option> -->
    <option value=0>--All--</option>
     <c:forEach items="${stateList}" var="state">
    
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${state.value}" /></option>
						</c:forEach>
    </select>
  </div>
  
   <div class="form-group col-md-2">
    <label for="head">Head: <span class="text-danger">*</span><span class="text-danger error-head"></span></label>
    <select class="form-control head" id="head" name="head" required>
<!--       <option value="">--Select Head--</option> -->
      <option value=0>--All--</option>
     <c:forEach items="${headList}" var="head">
		<option value="<c:out value="${head.key}"/>" <c:out value="${head.key== headCode ?'selected':'' }"/>>
		<c:out 	value="${head.value}" /></option>
	</c:forEach>
    </select>
  </div>
  
  <div class="form-group col-md-2">
    <label for="year">Financial Year: <span class="text-danger">*</span><span class="text-danger error-year"></span></label>
    <select class="form-control year" id="year" name="year" required>
<!--       <option value="">--Select Year--</option> -->
      <option value=0>--All--</option>
       <c:forEach items="${yearList}" var="year">
							<option value="<c:out value="${year.key}"/>" <c:out value="${year.key== finyr ?'selected':'' }"/>>
							<c:out 	value="${year.value}" /></option>
		</c:forEach>
    </select>
  </div>
  
  <div class="form-group col-md-2">
  	<label for="button">&nbsp;</label>
  	<!-- <input type="submit" class="btn btn-info form-control" id="btnGetAssetReport" name="btnGetAssetReport" value="Get Report"> -->
  		<button class="btn btn-info form-control" id="btnGetAssetReport" name="btnGetAssetReport" onclick="showReport(this);">Get Report</button>
  </div>
  
  
  <div class="col-md-2"></div>
  
 
  </div>
   
  </form>
  <hr/>
  </div>
 <div class="col-1"></div>
 <c:if test ="${not empty list}">
<div class="col" id="reportDiv">
<!-- <button name="exportExcel" id="exportExcel" onclick="exportExcell()" class="btn btn-info">Excel</button> -->
<!-- <button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> -->
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

<table id="tblReport" class="table">
  <thead >
    <tr>
      <th class="text-center" rowspan = "${check ? 3 : 2}">S No.</th>
      <th class="text-center" rowspan = "${check ? 3 : 2}">State</th>
      <c:forEach items ="${headmap}" var ="head">
      <c:choose>
      	<c:when test ="${head.key eq 8}">
      		<th class="text-center" colspan = "${4*headactmapsize}"><c:out value ="${head.value}"/></th>
      	</c:when>
      	<c:otherwise>
      		<th class="text-center" colspan = "4" rowspan="${check ? 2 : 1}"><c:out value ="${head.value}"/></th>
      	</c:otherwise>
      </c:choose>
      </c:forEach>
    </tr>
    <tr>
    <c:choose>
      		<c:when test ="${check}">
      			<c:forEach items ="${headactmap}" var ="headact">
      				<th class="text-center" colspan = "4"><c:out value ="${headact.value}"/></th>
      			</c:forEach>
      		</c:when>
      		<c:otherwise>
      		<c:forEach var ="i" begin="1" end = "${headmapsize}">
      			<th class="text-center">Physical Target</th>
      			<th class="text-center">Physical Achievement</th>
      			<th class="text-center">Achievement %</th>
      			<th class="text-center">Total Works</th>
      		</c:forEach>
      		</c:otherwise>
     </c:choose>
     
    </tr>
    <c:if test ="${check}">
    <tr>
    	<c:forEach var ="i" begin="1" end = "${headmapsize + headactmapsize-1}">	
      		<th class="text-center">Physical Target</th>
      		<th class="text-center">Physical Achievement</th>
      		<th class="text-center">Achievement %</th>
      		<th class="text-center">Total Works</th>
      	</c:forEach>
    </tr>
    </c:if>
     <tr>
     <c:forEach var ="i" begin="1" end = "${check ? (2 + 4 * ((headmapsize - 1) + headactmapsize)) : (2 + 4 * headmapsize)}">
		<th class="text-center"><c:out value = "${i}"/></th>
	</c:forEach>
	</tr>
  </thead>
	
	<c:set var ="chk" value ="true"/>
	<c:set var ="state" value =""/>
	<c:set var ="count" value = "1"/>
	<c:set var = "hdcd" value = "1"/>
	<c:set var = "actcd" value = "42"/>
	<tbody id="assetreportTbody">
		<c:forEach items ="${listmap}" var ="stmap">
  			<tr>
  				<td><c:out value ="${count}"/></td>
  				<td><c:out value ="${stmap.key}"/></td>
  				<c:forEach items ="${stmap.value}" var ="headlist">
  					<td><c:out value ="${headlist.value.plan}"/></td>
  					<td><c:out value="${headlist.value.achievement}"/></td>
  					<td><fmt:formatNumber value="${headlist.value.plan > 0 ? (headlist.value.achievement*100/headlist.value.plan) : 0}" type="number" maxFractionDigits="2"/></td>
  					<td><c:out value="${headlist.value.works}"/></td>
  				</c:forEach>
  				<c:set var ="count" value = "${count + 1}"/>
  			</tr>
  		</c:forEach>
  		
	</tbody>
</table>
   
<br/>  
</div>
</c:if>
<div class="col-1"></div>
</div>
</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>


</body>
</html>