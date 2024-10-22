<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<html>

<head>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/projectEvaluation.css" />"> --%>
<title>Fund Utilization</title>
<script type="text/javascript">
let formSubmitted = false;

function savedata(){
	
	
    if (formSubmitted) return false;
	
  
	/* $dCode = $('#dcode').val();
 alert("dfs1 "+$dCode);
	$dName = $('#distName').val();
 alert("dfs1 "+$dName);
	$pName = $('#projName').val();
 alert("dfs1 "+$pName);
	$pCode = $('#projid').val();
 alert("dfs1 "+$pCode);
	$mCode = $('#mcode').val();
 alert("dfs1 "+$mCode);
	$mName = $('#mname').val();
 alert("dfs1 "+$mName);
	$fCode = $('#fcode').val();
 alert("dfs1 "+$fCode);
	$fName = $('#fname').val();
 alert("dfs1 "+$fName);
 */

  $centralSharePrestatus = $('#preCentralShare').val();
  $centralShareMidstatus = $('#midCentralShare').val();
  $centralShareRemark = $('#rmkCentralShare').val();
  $stateSharePrestatus = $('#preStateShare').val();
  $stateShareMidstatus = $('#midStateShare').val();
  $stateShareRemark = $('#rmkStatelShare').val();
  $totalFundPrestatus = $('#preTotalFund').val();
  $totalFundMidstatus = $('#midTotalFund').val();
  $totalFundRemark = $('#rmkTotalFund').val();
  $totalFundPlannedPrestatus = $('#preConPlannedFund').val();
  $totalFundPlannedMidstatus = $('#midConPlannedFund').val();
  $totalFundPlannedRemark = $('#rmkConPlannedFund').val();
  $totalExpenditurePrestatus = $('#preExCon').val();
  $totalExpenditureMidstatus = $('#midExCon').val();
  $totalExpenditureRemark = $('#rmkExCon').val();
	$fromno1 = $('#fromno').val();
 
  	

	if ($centralSharePrestatus === "" && $centralShareMidstatus === "" && $stateSharePrestatus === "" 
		&& $stateShareMidstatus === "" && $totalFundPrestatus === "" && $totalFundMidstatus === "" 
		&& $totalFundPlannedPrestatus === "" && $totalFundPlannedMidstatus === "" 
		&& $totalExpenditurePrestatus === "" && $totalExpenditureMidstatus === "") 
  	{
     	alert("Please fill all the details");
    	return; 
 	}
	
	if($centralSharePrestatus==='')
	{
		alert('Please enter pre-project status of sanctioned central share');
		$('#preCentralShare').focus();
		return false;
	}
	if($centralShareMidstatus==='')
	{
		alert('Please enter mid-project status of sanctioned central share');
		$('#midCentralShare').focus();
		return false;
	}
	if($stateSharePrestatus==='')
	{
		alert('Please enter pre-project status of sanctioned state share');
		$('#preStateShare').focus();
		return false;
	}
	if($stateShareMidstatus==='')
	{
		alert('Please enter mid-project status of sanctioned state share');
		$('#midStateShare').focus();
		return false;
	}
	if($totalFundPlannedPrestatus==='')
	{
		alert('Please enter pre-project status of planned fund through convergence');
		$('#preConPlannedFund').focus();
		return false;
	}
	if($totalFundPlannedMidstatus==='')
	{
		alert('Please enter mid-project status of planned fund through convergence');
		$('#midConPlannedFund').focus();
		return false;
	}
	if($totalExpenditurePrestatus==='')
	{
		alert('Please enter pre-project status of incurred expenditure through convergence');
		$('#preExCon').focus();
		return false;
	}
	if($totalExpenditureMidstatus==='')
	{
		alert('Please enter mid-project status of incurred expenditure through convergence');
		$('#midExCon').focus();
		return false;
	}
	
	if(confirm("Do you want to save Fund Utilization?")) {
        formSubmitted = true;    //    saveFundUtilization
        document.getElementById('fundutilized').action = "saveFundUtilization";
        document.getElementById('fundutilized').method = "post";
        document.getElementById('fundutilized').submit();
    }

    return false;     
}

function calSum(){
	
	var pc = parseFloat(document.getElementById('preCentralShare').value);
	var ps = parseFloat(document.getElementById('preStateShare').value);
	var mc = parseFloat(document.getElementById('midCentralShare').value);
	var ms = parseFloat(document.getElementById('midStateShare').value);
	var psum = pc + ps;
	var msum = mc + ms;
	document.getElementById('preTotalFund').value = psum.toFixed(4);
	document.getElementById('midTotalFund').value = msum.toFixed(4);
}

</script>


</head>

<body>

<div class="maindiv">

<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${dname}"/>&projName=<c:out value="${pname}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${mname}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${fname}"/>" style="position: absolute; left: 0;">
    	<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
	</a>
	<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Project Evaluation - Fund Utilization</span>
	</h4>
</div>

<hr />

<form:form name="fundutilized" id="fundutilized" modelAttribute="fundutilized" action="saveFundUtilization" method="post">
 		
 		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${dname}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${pname}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${mname}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${fname}' /> />
 		
		<input type="hidden" name="fromno" id="fromno" value="3" />
		
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col-md-6"> -->
<%-- 			<label for="district" class="">District Name: <b><c:out value="${dname}"/></b></label>  --%>
<!-- 		</div> -->

<!-- 		<div class="form-group col-md-6"> -->
<%-- 			<label for="project" class="">Project Name: <b><c:out value="${pname}"/></b></label>  --%>
<!-- 		</div> -->
<!-- 	</div> -->
		
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col-1"> -->
<!-- 			<label for="project" class="">Area:</label>  -->
<!-- 		</div> -->
<!-- 		<div class="form-group col-6"> -->
<!-- 			<input type='radio' class='projectArea' value='P' name = 'projectArea' id='projectArea' checked='checked'><b>Project Area</b> -->
<!-- 			<input type='radio' class='controlledArea' value='C' name = 'projectArea' id='projectArea'><b>Controlled Area</b> -->
<!-- 		</div> -->
<!-- 	</div> -->
		
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="month" class="">Month: <b><c:out value="${mname}"/></b></label> --%>
<!-- 		</div> -->
		
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="finyear" class="">Financial Year: <b><c:out value="${fname}"/></b></label> --%>
<!-- 		</div>  -->
<!-- 	</div> -->

<div class="form-group">
	District Name : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp; 
	Month Name : &nbsp; <b><c:out value='${mname}' /></b>, &nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${fname}' /></b>
</div>
  
<hr />
<div>
<table style="width:100%">
	<thead>
		<tr>
			<th><b>Sl.No.</b></th>
			<th><b>Indicators</b></th>
			<th><b>Pre-Project Status</b></th>
			<th><b>Mid-Project Status Details</b></th>
			<th><b>Remarks</b></th>
		</tr>
	</thead>	
	<tbody>	
		<tr>
			<td>
				<b><c:out value="2."/></b>
			</td>
			<td colspan="4">
				<b><c:out value="Fund Utilization"/></b>
			</td>
<!-- 			<td> -->
<!--      			<input type="text" id="preFundUtilized" name="preFundUtilized" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class=""  /> -->
<!-- 			</td> -->
<!-- 			<td> -->
<!--      			<input type="text" id="midFundUtilized" name="midFundUtilized" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class=""  /> -->
<!-- 			</td> -->
<!-- 			<td> -->
<!--      			<input type="text" id="rmkFundUtilized" name="rmkFundUtilized" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class=""  /> -->
<!-- 			</td> -->
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="a"/></b>
			</td>
			<td>
				<b><c:out value="Amount of sanctioned Central share received (Rs. Crores)"/></b>
			</td>
			<td>
     			<input type="text" id="preCentralShare" name="preCentralShare" value="${preCentralShare}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class="" onchange="calSum()" />
			</td>
			<td>
     			<input type="text" id="midCentralShare" name="midCentralShare" value="${midCentralShare}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class="" onchange="calSum()"/>
			</td>
			<td>
<%--      			<input type="text" id="rmkCentralShare" name="rmkCentralShare" value="${rmkCentralShare}" autocomplete = "off" /> --%>
     			<textarea id="rmkCentralShare" name="rmkCentralShare" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkCentralShare}</textarea> 
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="b"/></b>
			</td>
			<td>
				<b><c:out value="Amount of sanctioned State share received (Rs. Crores)"/></b>
			</td>
			<td>
     			<input type="text" id="preStateShare" name="preStateShare" value="${preStateShare}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class="" onchange="calSum()" />
			</td>
			<td>
     			<input type="text" id="midStateShare" name="midStateShare" value="${midStateShare}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class="" onchange="calSum()" />
			</td>
			<td>
<%--      			<input type="text" id="rmkStatelShare" name="rmkStateShare" value="${rmkStateShare}" autocomplete = "off" /> --%>
     			<textarea id="rmkStatelShare" name="rmkStateShare" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkStateShare}</textarea>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="c"/></b>
			</td>
			<td>
				<b><c:out value="Amount of Total funds (central + state share) Utilized (Rs. Crores)"/></b>
			</td>
			<td>
				<input type="number" id="preTotalFund" name="preTotalFund" value="${preTotalFund}" readonly />
<!--      			<input type="text" id="preTotalFund" name="preTotalFund" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class=""  /> -->
			</td>
			<td>
				<input type="number" id="midTotalFund" name="midTotalFund" value="${midTotalFund}" readonly />
<!--      			<input type="text" id="midTotalFund" name="midTotalFund" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class=""  /> -->
			</td>
			<td>
<%--      			<input type="text" id="rmkTotalFund" name="rmkTotalFund" value="${rmkTotalFund}" autocomplete = "off" /> --%>
     			<textarea id="rmkTotalFund" name="rmkTotalFund" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkTotalFund}</textarea>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="d"/></b>
			</td>
			<td>
				<b><c:out value="Total funds planned through convergence in the project area (Rs. Crores)"/></b>
			</td>
			<td>
     			<input type="text" id="preConPlannedFund" name="preConPlannedFund" value="${preConPlannedFund}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class=""  />
			</td>
			<td>
     			<input type="text" id="midConPlannedFund" name="midConPlannedFund" value="${midConPlannedFund}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class=""  />
			</td>
			<td>
<%--      			<input type="text" id="rmkConPlannedFund" name="rmkConPlannedFund" value="${rmkConPlannedFund}" autocomplete = "off" /> --%>
     			<textarea id="rmkConPlannedFund" name="rmkConPlannedFund" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkConPlannedFund}</textarea>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="e"/></b>
			</td>
			<td>
				<b><c:out value="Total expenditure incurred through convergence (Rs. Crores)"/></b>
			</td>
			<td>
     			<input type="text" id="preExCon" name="preExCon" value="${preExCon}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class=""  />
			</td>
			<td>
     			<input type="text" id="midExCon" name="midExCon" value="${midExCon}" autocomplete = "off" maxlength="11" onfocusin="decimalToFourPlace(event)" placeholder="Only Decimal" class=""  />
			</td>
			<td>
<%--      			<input type="text" id="rmkExCon" name="rmkExCon" value="${rmkExCon}" autocomplete = "off" /> --%>
     			<textarea id="rmkExCon" name="rmkExCon" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkExCon}</textarea>
			</td>
		</tr>
		<tr>
			<th colspan="5" style="align-content: center;">
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						
				<input type="button" name="view" id="view" value="Confirm" class="btn btn-info" onclick="savedata();" />
			</th>
		</tr>
	</tbody>
</table>
</div>

</form:form>

</div>



<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<%-- <script src='<c:url value="/resources/js/projectevaluation/fundutilization.js" />'></script> --%>
</body>
</html>