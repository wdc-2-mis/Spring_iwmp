<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report ME10</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">
function showReport(e)
{
	var finyear = $('#finyear').val();
// 	var state = $('#state').val();
// 	var district = $('#district').val();
// 	var activity = $('#activity').val();
// 	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
// 	document.getElementById("stName").value=stName;
	
// 	if(state==='')
// 	{
// 		alert('Please select state ');
// 		$('#state').focus();
// 		e.preventDefault();
// 	}
	
// 	if(district==='')
// 	{
// 		$('#district').val('');
// 		alert('Please select district ');
// 		$('#district').focus();
// 		e.preventDefault();
// 	}
// 	if(activity==='')
// 	{
// 		alert('Please select activity ');
// 		$('#activity').focus();
// 		e.preventDefault();
// 	}
	
	/* if(userdateto!='')
	{
		if(userdate==='')
		{
			$('#userdateto').val('');
			alert('Please select From Date ');
			$('#userdate').focus();
			e.preventDefault();
		}
	}  
	 	if(userdateto!='' && userdate!='')
	{
		const dateTimeStr = $('#userdate').val();
		const dateTimeStrto = $('#userdateto').val();
		const dateObj = new Date(dateTimeStr); 
		const dateObjto = new Date(dateTimeStrto); 
	if (dateObjto < dateObj) {
			alert('From date Can not be greater than To date');
			$('#userdate').val('');
			$('#userdateto').val('');
		}  
	} */
	if(finyear ===''){
		alert('Please select Financial Year');
		$('#finyear').focus();
		e.preventDefault();
	}
	else{
		
		document.workstatusr.action="getActivityNRMWorkJalSakati";
		document.workstatusr.method="post";
		document.workstatusr.submit();
	}
	return false;
} 

function downloadPDF(){
	
    document.workstatusr.action="getActivityNRMWorkJalSakatiPDF";
	document.workstatusr.method="post";
	document.workstatusr.submit();
}


function downloadExcel(){
	
    document.workstatusr.action="getActivityNRMWorkJalSakatiExcel";
	document.workstatusr.method="post";
	document.workstatusr.submit();
}


</script>
</head>



 <br>
 

<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report ME10- Water Scarce Districts in The Country Identified under JSA:CTR 2025</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getActivityNRMWorkReport" name="workstatusr" id="workstatusr" method="Get">
	
   		<input type="hidden" name="stName" id="stName" value="" />
   		<input type="hidden" name="distName" id="distName" value="" />
		<input type="hidden" name="finyr" id="finyr" value="" />
		<input type="hidden" name="headname" id="headname" value="" />
		<input type="hidden" name="actname" id="actname" value="" />
		
      <table style="width:100%; align-content: center;" >
     <%--    <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control state" id="state" name="state" onchange="this.form.submit();" required="required">
    			 <option value="">--Select--</option>
     			 <c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
				</c:if>  
    		</select>
          </td>
          
          <td><b>District <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control district" id="district" name="district" required="required">
    			 <option value="0">--All--</option>
     			 <c:if test="${not empty districtList}">
               			<c:forEach items="${districtList}" var="lists">
               				<c:if test="${lists.key eq dist}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne dist}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
				</c:if>  
    		</select>
          </td></tr> --%>
          <tr align="center" >
          <td><b>Financial Year <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control finyear" id="finyear" name="finyear" required="required">
    			 <option value="0">--All--</option>
     			 <c:if test="${not empty yearList}">
               			<c:forEach items="${yearList}" var="lists">
               				<c:if test="${lists.key eq yrcd}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne yrcd}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
				</c:if>  
    		</select>
          </td>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Report' /> </td>
          </tr>
       <%--     <tr align="center" >
          <td><b>Head<span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control head" id="head" name="head"  onchange="this.form.submit();" required="required">
    			<option value="">--Select--</option>
     			 <c:if test="${not empty headList}">
               			<c:forEach items="${headList}" var="lists">
               				<c:if test="${lists.key eq headcd}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne headcd}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
				</c:if>  
    		</select>
          </td>
          
           <td><b>Activity<span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control activity"  id="activity" name="activity" required="required">
             			<option value="">--Select--</option>
    			<!-- <option value="0">--All--</option> -->
     			 <c:if test="${not empty activityList}">
               			<c:forEach items="${activityList}" var="lists">
               				<c:if test="${lists.key eq actcd}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne actcd}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
				</c:if>  
    		</select>
          </td> --%>
        
           
      </table>
      <div id="previewDiv" class="hiddenDivStyle" align="center"
			style="position: absolute; top: 100px; left: 25px; display: none; width: 300px; height: 50px; vertical-scrol: auto; background-color: gray;">
			<table align="center">
				<tr>
					<td>
						<div align="center">
							<span style="font-size: 25px;">Please Wait ...</span>
						</div>
					</td>
				</tr>
			</table>
		</div>
 </form:form>
 
<c:if test="${not empty dataList}">

<button name="exportExcel" id="exportExcel" onclick="downloadExcel()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>

</c:if>

<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<br/>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <%-- <tr> 
	 	<th colspan="7" style="text-align:left; "><b>State :</b> ${stName} &emsp; </th>
	</tr> --%>
    <tr>
    	<th style="text-align:center; vertical-align: middle;">S.No.</th>
    	<th style="text-align:center; vertical-align: middle;">State</th>
    	<th style="text-align:center; vertical-align: middle;">District</th>
    	<th style="text-align:center; vertical-align: middle;">No. of Project</th>
     	<th style="text-align:center; vertical-align: middle;">Area Sanctioned (Ha)</th>  
		<th style="text-align:center; vertical-align: middle;">Project Cost (Rs. Lakh)</th>
    	<th style="text-align:center; vertical-align: middle;">Farm Ponds</th>
     	<th style="text-align:center; vertical-align: middle;">Check Dams</th>  
		<th style="text-align:center; vertical-align: middle;">Nallah Bunds</th>
		<th style="text-align:center; vertical-align: middle;">Percolation Tanks</th> 
		<th style="text-align:center; vertical-align: middle;">Ground water Recharge Structure</th>
 		<th style="text-align:center; vertical-align: middle;">Gully Plugs</th>  
		<th style="text-align:center; vertical-align: middle;">Amrit Sarovar</th>
		<th style="text-align:center; vertical-align: middle;">Others</th>
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
		<th class="text-center">8</th>
		<th class="text-center">9</th>
		<th class="text-center">10</th>
		<th class="text-center">11</th>
		<th class="text-center">12</th>
		<th class="text-center">13</th>
		<th class="text-center">14</th>
	</tr>
	 
	 			<c:if test="${dataList != null}">
		 			<c:set var="stname" value="" />
		 			<c:set var="sno" value="1" />
						<c:forEach items="${dataList}" var="data" varStatus="count">
 							<tr>
 							
	 							<c:choose>
									<c:when test="${stname ne data.statename}">
										<c:set var="stname" value="${data.statename}" />
										<td><c:out value='${sno}' /></td>
										<td><c:out value='${stname}' /></td>
										<c:set var="sno" value="${sno+1}" />
									</c:when>	
									<c:otherwise>
										<td></td>
										<td></td>
									</c:otherwise>
								</c:choose>
								
								<td><c:out value="${data.dist_name}" /></td>
								<td align="right"><c:out value="${data.projcount}" /></td>
								<td align="right"><c:out value="${data.area_proposed}" /></td>
								<td align="right"><c:out value="${data.project_cost}" /></td>
								<td align="right"><c:out value="${data.farmponds}" /></td>
								<td align="right"><c:out value="${data.checkdams}" /></td>
								<td align="right"><c:out value="${data.nallahbunds}" /></td>
 								<td align="right"><c:out value="${data.percolationtanks}" /></td>
 								<td align="right"><c:out value="${data.groundwaterrechargestructure}" /></td>
								<td align="right"><c:out value="${data.gullyplugs}" /></td>
								<td align="right"><c:out value="${data.amritsarovar}" /></td>
 								<td align="right"><c:out value="${data.others}" /></td>
 							</tr>
 						</c:forEach>
 					 	<tr>
							<td align="right" class="table-primary" colspan="3"><b>Grand Total </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${projcount}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${area_proposed}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${project_cost}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${farmponds}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${checkdams}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${nallahbunds}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${percolationtanks}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${groundwaterrechargestructure}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${gullyplugs}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${amritsarovar}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${others}' /> </b></td>
							
	 					</tr> 
	 				
	 				</c:if>
	 				
	
    			
    	<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="14" class="required" style="color:red;">Data Not Found</td>
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
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

document.getElementById('userdateto').addEventListener('change', function() {
    var fromDate = document.getElementById('userdate').value;
    if (!fromDate) {
        alert('Please select From Date first.');
        this.value = ''; // Reset the To Date
        $('#userdate').focus();
    }
});


</script>	

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>