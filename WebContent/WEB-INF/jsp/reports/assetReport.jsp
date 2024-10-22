<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
<title>Report A2- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements According to Activity Type/Work as per List of Activities</title>
</head>


<script type="text/javascript">
	
	function downloadPDF(){
		var stCode = $('#assetstate').val();
		var distCode = $('#assetdistrict').val();
		var projId = $('#assetproject').val();
		var fyCode = $('#year').val();
		var headCode = $('#head').val();
		var activityCode =$('#activity').val();
		var subActivityCode = $('#subactivity').val();
		var month = $('#month').val();
		var status = $('#status').val();
		
		document.getElementById("stCode").value=stCode;
	    document.getElementById("distCode").value=distCode;
	    document.getElementById("projId").value=projId;
	    document.getElementById("fyCode").value=fyCode;
	    document.getElementById("headCode").value=headCode;
	    document.getElementById("activityCode").value=activityCode;
	    document.getElementById("subActivityCode").value=subActivityCode;
	    document.getElementById("monthid").value=month;
	    document.getElementById("statusk").value=status;
	    
	    document.assetrpt.action="downloadAssetReportPDF";
		document.assetrpt.method="post";
		document.assetrpt.submit();
		}

	function exportExcell(){
		var stCode = $('#assetstate').val();
		var distCode = $('#assetdistrict').val();
		var projId = $('#assetproject').val();
		var fyCode = $('#year').val();
		var headCode = $('#head').val();
		var activityCode =$('#activity').val();
		var subActivityCode = $('#subactivity').val();
		var month = $('#month').val();
		var status = $('#status').val();
		
		document.getElementById("stCode").value=stCode;
	    document.getElementById("distCode").value=distCode;
	    document.getElementById("projId").value=projId;
	    document.getElementById("fyCode").value=fyCode;
	    document.getElementById("headCode").value=headCode;
	    document.getElementById("activityCode").value=activityCode;
	    document.getElementById("subActivityCode").value=subActivityCode;
	    document.getElementById("monthid").value=month;
	    document.getElementById("statusk").value=status;
	    
	    document.assetrpt.action="downloadExcelassetReport";
		document.assetrpt.method="post";
		document.assetrpt.submit();
		}
	
	</script>
	
	
	<form action="downloadAssetReportPDF" method="post" name="assetrpt" id="assetrpt">

	<input type="hidden" name="stCode" id="stCode" value="" />
	<input type="hidden" name="distCode" id="distCode" value="" />
	<input type="hidden" name="projId" id="projId" value="" />
	<input type="hidden" name="fyCode" id="fyCode" value="" />
	<input type="hidden" name="headCode" id="headCode" value="" />
	<input type="hidden" name="activityCode" id="activityCode" value="" />
	<input type="hidden" name="subActivityCode" id="subActivityCode" value="" />
	<input type="hidden" name="subActivityCode" id="subActivityCode" value="" />
	<input type="hidden" name="subActivityCode" id="subActivityCode" value="" />
	    <input type="hidden" name="monthid" id="monthid" value="" />
	    <input type="hidden" name="statusk" id="statusk" value="" />
	</form>
	
<div class="container-fluid">
<div class="row">
<div class="col text-center">
<br/>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report A2- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements According to Activity Type/Work as per List of Activities</h5></div>
<hr/>
</div>
</div>
<div class="row">

<div class="col-12">
<form >
<div class="form-row">
<div class="col-md-2"></div>
  <div class="form-group col-md-2">
    <label for="state">State: <span class="text-danger">*</span><span class="text-danger error-assetstate"></span></label>
    <select class="form-control project" id="assetstate" name="assetstate" required>
    <option value="">--Select State--</option>
    <!-- <option value=0>--All--</option> -->
     <c:forEach items="${stateList}" var="state">
    
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${state.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-2">
    <label for="district">District: <span class="text-danger">*</span><span class="text-danger error-assetdistrict"></span></label>
    <select class="form-control district" id="assetdistrict" name="assetdistrict" required>
      <option value="">--Select District--</option>
      <option value=0>--All--</option>
    </select>
    
  </div>
  <div class="form-group col-md-2">
    <label for="project">Project: <span class="text-danger">*</span><span class="text-danger error-assetproject"></span></label>
    <select class="form-control project" id="assetproject" name="assetproject" required>
      <option value="">--Select Project--</option>
       
    </select>
  </div>
  <div class="form-group col-md-2">
    <label for="year">Financial Year: <span class="text-danger">*</span><span class="text-danger error-year"></span></label>
    <select class="form-control year" id="year" name="year" required>
      <option value="">--Select Year--</option>
      <option value=0>--All--</option>
       <c:forEach items="${yearList}" var="year">
							<option value="<c:out value="${year.key}"/>" <c:out value="${year.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${year.value}" /></option>
		</c:forEach>
    </select>
  </div>
   <div class="form-group col-md-2">
    <label for="year">Month: <span class="text-danger">*</span><span class="text-danger error-month"></span></label>
    <select class="form-control month" id="month" name="month" required>
      <option value="">--Select Month--</option>
       <c:forEach items="${yearList}" var="mnth">
							<option value="<c:out value="${mnth.key}"/>" <c:out value="${mnth.key== mnth ?'selected':'' }"/>>
							<c:out 	value="${mnth.value}" /></option>
						</c:forEach>
    </select>
  </div>
  
  <div class="col-md-2"></div>
  
 
  </div>
   <div class="form-row">
   <div class=" col-md-2"></div>
  <div class="form-group col-md-2">
    <label for="head">Head: <span class="text-danger">*</span><span class="text-danger error-head"></span></label>
    <select class="form-control head" id="head" name="head" required>
      <option value="">--Select Head--</option>
      <option value=0>--All--</option>
     <c:forEach items="${headList}" var="head">
		<option value="<c:out value="${head.key}"/>" <c:out value="${head.key== stCode ?'selected':'' }"/>>
		<c:out 	value="${head.value}" /></option>
	</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-2">
    <label for="toyear">Activity: <span class="text-danger">*</span><span class="text-danger error-activity"></span></label>
    <select class="form-control activity" id="activity" name="activity" required>
      <option value="">--Select Activity--</option>
      <option value=0>--All--</option>
     
    </select>
  </div>
  <div class="form-group col-md-2">
    <label for="toyear">Activity Type: <span class="text-danger">*</span><span class="text-danger error-subactivity"></span></label>
    <select class="form-control subactivity" id="subactivity" name="subactivity" required>
      <option value="">--Select Sub-Activity--</option>
      <option value=0>--All--</option>
       
    </select>
  </div>
  <div class="form-group col-md-2">
    <label for="toyear">Status <span class="text-danger">*</span><span class="text-danger error-subactivity"></span></label>
    <select class="form-control status" id="status" name="status" required>
      <option value='A'>--ALL--</option>
      <option value='C'>Complete</option>
      <option value='O'>onGoing</option> 
      <option value='F'>forClosed</option> 
      
    </select>
  </div>
  <div class="form-group col-md-2">
  <label for="button">&nbsp;</label>
  <!-- <input type="submit" class="btn btn-info form-control" id="btnGetAssetReport" name="btnGetAssetReport" value="Get Report"> -->
  <button class="btn btn-info form-control" id="btnGetAssetReport" name="btnGetAssetReport">Get Report</button>
  </div>
  <div class="col-md-2"></div>
  </div>
  <div class="form-row">
  <div class="offset-md-2 col-md-3"></div>
  
  </div>
  </form>
  <hr/>
  </div>
 <div class="col-1"></div>
<div class="col" id="reportDiv">
  <!--  <div class="col">
  <div class="offset-md-4"></div>
  <div class="col" id="buttonDiv">
  	<input type="button" id="pdf" name="pdf" value="pdf" onclick="pdfGenerate()"/>
  	<a href="#" onClick ="$('#tblReport').tableExport({type:'pdf',escape:'false'});">PDF</a> 
  	<a href="#" onClick ="importExcel()">Excel</a></div>
  </div> -->
<button name="exportExcel" id="exportExcel" onclick="exportExcell()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

<table id="tblReport" class="table">
  <thead >
    <tr>
      <th class="text-center">S No.</th>
      <th class="text-center">State</th>
      <th class="text-center">District</th>
      <th class="text-center">Block</th>
      <th class="text-center">Village</th>
      <th class="text-center">Project</th>
      <th class="text-center">FY</th>
      <th class="text-center">Head</th>
      <th class="text-center">Activity</th>
      <th class="text-center">Activity Type</th>
      <th class="text-center">Work-Id</th>
      <th class="text-center">Area Cover</th>
      <th class="text-center">Unit</th>
      <th class="text-center">Status</th>
      <th class="text-center">Completion Date</th>
    </tr>
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
		<th class="text-center">15</th>
	</tr>
  </thead>

  <tbody id="assetreportTbody">
   
	
  </tbody>
</table>
   
<br/>  
</div>
<div class="col-1"></div>
</div>
</div>

 <script src='<c:url value="/resources/js/assetreport.js" />'></script>
<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>


</body>
</html>