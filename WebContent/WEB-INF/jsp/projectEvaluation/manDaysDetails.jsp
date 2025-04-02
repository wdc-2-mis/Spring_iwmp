<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script type="text/javascript">
let formSubmitted = false;  

function savedata(event){
    event.preventDefault();  

    if (formSubmitted) return false;  

/*  var area;
    
    if($('#projectArea:checked').val()!=null && $('#projectArea:checked').val() !='undefined')
	{
		
    	area=$('#projectArea:checked').val();
		
	}
	if($('#controlledArea:checked').val()!=null && $('#controlledArea:checked').val() !='undefined')
	{
		
		area=$('#controlledArea:checked').val();
		
	} */
	
	if($('#pre_farmer_income').val()==='')
	{
		alert('Please enter pre farmer income for Project Area.');
		$('#pre_farmer_income').focus();
		return false;
	}
	if($('#mid_farmer_income').val()==='')
	{
		alert('Please enter mid farmer income for Project Area.');
		$('#mid_farmer_income').focus();
		return false;
	}
	
	if($('#control_farmer_income').val()==='')
	{
		alert('Please enter farmer income for Controlled Area.');
		$('#control_farmer_income').focus();
		return false;
	}
	
	if($('#farmer_benefited').val()==='')
	{
		alert('Please enter farmer benefited for Project Area.');
		$('#farmer_benefited').focus();
		return false;
	}
	if($('#control_farmer_benefited').val()==='')
	{
		alert('Please enter farmer benefited for Controlled Area.');
		$('#control_farmer_benefited').focus();
		return false;
	}
	
	if($('#mandays_generated').val()==='')
	{
		alert('Please enter mandays generated for Project Area.');
		$('#mandays_generated').focus();
		return false;
	}
	if($('#control_mandays_generated').val()==='')
	{
		alert('Please enter mandays generated for Controlled Area.');
		$('#control_mandays_generated').focus();
		return false;
	}
	
	if($('#pre_dug_well').val()==='')
	{
		alert('Please enter pre dug well for Project Area.');
		$('#pre_dug_well').focus();
		return false;
	}
	if($('#mid_dug_well').val()==='')
	{
		alert('Please enter mid dug well for Project Area.');
		$('#mid_dug_well').focus();
		return false;
	}
	if($('#control_dug_well').val()==='')
	{
		alert('Please enter dug well for Controlled Area.');
		$('#control_dug_well').focus();
		return false;
	}
	
	if($('#pre_tube_well').val()==='')
	{
		alert('Please pre enter tube well');
		$('#pre_tube_well').focus();
		return false;
	}
	if($('#mid_tube_well').val()==='')
	{
		alert('Please mid enter tube well');
		$('#mid_tube_well').focus();
		return false;
	}
	if($('#control_tube_well').val()==='')
	{
		alert('Please enter tube well');
		$('#control_tube_well').focus();
		return false;
	}	
	
    if(confirm("Do You Want to save No. of Man-days Details?")) {
        formSubmitted = true;    ////    saveprojectProfile
        document.getElementById('mandays').action = "saveMandaysDetails";
        document.getElementById('mandays').method = "post";
        document.getElementById('mandays').submit();
    }

    return false;
}
</script>


</head>
<body>
<div class="maindiv">
	
<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${distName}"/>&projName=<c:out value="${projName}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${month}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${finyear}"/>" style="position: absolute; left: 0;">
    	<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
	</a>
	<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Project Evaluation - No. of Man-days, Farmer and Water Details</span>
	</h4>
</div>

<hr />
<form:form name="mandays" id="mandays" modelAttribute="mandays"
		action="saveMandaysDetails" method="post" autocomplete="off">
		
		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${distName}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${projName}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${month}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${finyear}' /> />
		<input type="hidden" name="fromno" id="fromno" value="6" />
		

<div class="form-group">
	District Name : &nbsp; <b><c:out value='${distName}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${projName}' /></b>, &nbsp;&nbsp;&nbsp; 
	Month Name : &nbsp; <b><c:out value='${month}' /></b>, &nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${finyear}' /></b>
</div>
  
<hr />
	
	
		
		
	<table id="projectprofile" cellspacing="0" class="table"   width="auto">
<thead>
    <tr>
        <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
        <th rowspan="5" style="text-align:left; vertical-align: middle;">Indicators</th>
        <th colspan="2" style="text-align:center; vertical-align: middle;">Project Area</th>
        <th rowspan="2" style="text-align:left; vertical-align: middle;">Controlled Area</th>
        <th rowspan="2" style="text-align:left; vertical-align: middle;">Remarks</th>
    </tr>
    <tr>
        <th style="text-align:center; vertical-align: middle;">Pre Project Status(Aggregate)</th>
        <th style="text-align:center; vertical-align: middle;">Mid Project Status(Aggregate)</th>
    </tr>
</thead>
 		<tr>
 			<td width="4%"><b><c:out value="1."></c:out></b></td>
  			<td width="40%"><b><c:out value="Farmer`s Average Household Income per Annum (Rs. in Lakhs)"></c:out></b></td>
  			<td>
     			<input type="text" id="pre_farmer_income" name="pre_farmer_income" value="${pre_farmer_income}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="mid_farmer_income" name="mid_farmer_income" value="${mid_farmer_income}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="control_farmer_income" name="control_farmer_income" value="${control_farmer_income}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			 <td> 
                 <textarea id="remark_farmer_income" name="remark_farmer_income" autocomplete="off" rows="2" cols="22" maxlength="200">${remark_farmer_income}</textarea>
             </td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="2."></c:out></b></td>
  			<td width="40%"><b><c:out value="No. of Farmers Benefited"></c:out></b></td>
  			<td colspan="2">
     			<input type="text" id="farmer_benefited" name="farmer_benefited" maxlength="5" value="${farmer_benefited}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
 			<td>
     			<input type="text" id="control_farmer_benefited" name="control_farmer_benefited" maxlength="5" value="${control_farmer_benefited}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
 			 <td> 
                       <textarea id="remark_farmer_benefited" name="remark_farmer_benefited" autocomplete="off" rows="2" cols="22" maxlength="200">${remark_farmer_benefited}</textarea>
                       </td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="3."></c:out></b></td>
  			<td width="40%"><b><c:out value="No. of Persondays Generated (man-days)"></c:out></b></td>
  			<td colspan="2">
     			<input type="text" id="mandays_generated" name="mandays_generated" maxlength="5" value="${mandays_generated}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
 			<td>
     			<input type="text" id="control_mandays_generated" name="control_mandays_generated" maxlength="5" value="${control_mandays_generated}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
 			 <td> 
                       <textarea id="remark_mandays_generated" name="remark_mandays_generated" autocomplete="off" rows="2" cols="22" maxlength="200">${remark_mandays_generated}</textarea>
                       </td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="4."></c:out></b></td>
  			<td width="40%"><b><c:out value="Average depth of Water table in dug wells (mts.)- Summer Season"></c:out></b></td>
  			<td>
     			<input type="text" id="pre_dug_well" name="pre_dug_well" value="${pre_dug_well}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
 			<td>
     			<input type="text" id="mid_dug_well" name="mid_dug_well" value="${mid_dug_well}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
 			<td>
     			<input type="text" id="control_dug_well" name="control_dug_well" value="${control_dug_well}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
 			 <td> 
                       <textarea id="remark_dug_well" name="remark_dug_well" autocomplete="off" rows="2" cols="22" maxlength="200">${remark_dug_well}</textarea>
                       </td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="5."></c:out></b></td>
  			<td width="40%"><b><c:out value="Average depth of Water table in tube wells (mts.)- Summer Season"></c:out></b></td>
  			<td>
     			<input type="text" id="pre_tube_well" name="pre_tube_well" value="${pre_tube_well}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="mid_tube_well" name="mid_tube_well" value="${mid_tube_well}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="control_tube_well" name="control_tube_well" value="${control_tube_well}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			 <td> 
                       <textarea id="remark_tube_well" name="remark_tube_well" autocomplete="off" rows="2" cols="22" maxlength="200">${remark_tube_well}</textarea>
                       </td>
		</tr>

		<tr>
			<td  colspan="6"   align="center" >
			 	<input type="button" name="view"  id = "view" value="Confirm" class="btn btn-info" onclick="savedata(event);"/>
				<!--  <button  class="btn btn-info col-2" id="btnSave" name="btnSave">Submit</button> -->
			</td>
		</tr>

	</table>
</form:form>
</div>
    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
   <%--  <script src='<c:url value="/resources/js/projectevaluation/mandaysdetails.js" />'></script> --%>
</body>
