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
    
	if($('#culturable_wasteland').val()==='')
	{
		alert('Please enter Culturable Wasteland for Project Area.');
		$('#culturable_wasteland').focus();
		return false;
	}
	
	if($('#conculturable_wasteland').val()==='')
	{
		alert('Please enter Culturable Wasteland for Controlled Area.');
		$('#conculturable_wasteland').focus();
		return false;
	}
	
	
	if($('#whs_constructed_rejuvenated').val()==='')
	{
		alert('Please enter WHS for Project Area.');
		$('#whs_constructed_rejuvenated').focus();
		return false;
	}
	if($('#conwhs_constructed_rejuvenated').val()==='')
	{
		alert('Please enter WHS for Controlled Area.');
		$('#conwhs_constructed_rejuvenated').focus();
		return false;
	}
	
	if($('#soil_moisture').val()==='')
	{
		alert('Please enter area of Soil and moisture for Project Area.');
		$('#soil_moisture').focus();
		return false;
	}
	if($('#consoil_moisture').val()==='')
	{
		alert('Please enter area of Soil and moisture for Controlled Area.');
		$('#consoil_moisture').focus();
		return false;
	}
	
	if($('#protective_irrigation').val()==='')
	{
		alert('Please enter Protective Irrigation for Project Area.');
		$('#protective_irrigation').focus();
		return false;
	}
	if($('#conprotective_irrigation').val()==='')
	{
		alert('Please enter Protective Irrigation for Controlled Area.');
		$('#conprotective_irrigation').focus();
		return false;
	}
	
	if($('#degraded_rainfed').val()==='')
	{
		alert('Please enter Degraded land and rainfed Area for Project Area.');
		$('#degraded_rainfed').focus();
		return false;
	}
	if($('#condegraded_rainfed').val()==='')
	{
		alert('Please enter Degraded land and rainfed Area for Controlled Area.');
		$('#condegraded_rainfed').focus();
		return false;
	}
	
	if($('#farmer_income').val()==='')
	{
		alert('Please enter farmer income for Project Area.');
		$('#farmer_income').focus();
		return false;
	}
	if($('#confarmer_income').val()==='')
	{
		alert('Please enter farmer income for Controlled Area.');
		$('#confarmer_income').focus();
		return false;
	}
	
	if($('#farmer_benefited').val()==='')
	{
		alert('Please enter farmer benefited for Project Area.');
		$('#farmer_benefited').focus();
		return false;
	}
	if($('#confarmer_benefited').val()==='')
	{
		alert('Please enter farmer benefited for Controlled Area.');
		$('#confarmer_benefited').focus();
		return false;
	}
	
	if($('#mandays_generated').val()==='')
	{
		alert('Please enter mandays generated for Project Area.');
		$('#mandays_generated').focus();
		return false;
	}
	if($('#conmandays_generated').val()==='')
	{
		alert('Please enter mandays generated for Controlled Area.');
		$('#conmandays_generated').focus();
		return false;
	}
	
	if($('#dug_well').val()==='')
	{
		alert('Please enter dug well for Project Area.');
		$('#dug_well').focus();
		return false;
	}
	if($('#condug_well').val()==='')
	{
		alert('Please enter dug well for Controlled Area.');
		$('#condug_well').focus();
		return false;
	}
	
	if($('#tube_well').val()==='')
	{
		alert('Please enter tube well');
		$('#tube_well').focus();
		return false;
	}
	if($('#contube_well').val()==='')
	{
		alert('Please enter tube well');
		$('#contube_well').focus();
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
		<span style="text-decoration:underline;">Project Evaluation - No. of Man-days Details</span>
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
		
		
		
		
		
		<%--  <table style="width:100%; align-content: center;" >
        	<tr align="center" >
          		<td>District Name:-</td>
          		<td>
    					<b><c:out value="${distName}"/> GWALIOR </b>
          		</td>
          		
          		<td>Project Name:-</td>
          		<td>
            		<b><c:out value="${projName}"/> GWALIOR-WDC - 1 /2021-22</b>
          		</td>
          	</tr>	
          	<tr align="center" >	
          		<td >Area:-</td>
          		<td >
             		<b>Project Area</b> &nbsp;<input type='radio' class='projectArea' value='P' name = 'projectArea' id='projectArea' checked='checked'> &nbsp;
					&nbsp; <input type='radio' class='controlledArea' value='C' name = 'projectArea' id='controlledArea'> &nbsp; <b>Controlled Area</b>
          		</td>
          	</tr>
          	<tr align="center" >	
          		<td >Month:-</td>
          		<td >
              		<b><c:out value="${month}"/> July</b>
          		</td>
         		<td >Financial Year:-</td>
          		<td >
              		<b><c:out value="${finyear}"/> 2024-25</b>
          		</td>
       		</tr>
      	</table>  --%>
      	
      	
      	
<!--     	<div class = "form-row"> -->
<!-- 			<div class="form-group col-md-6"> -->
<%-- 				<label for="district" class="">District Name: &nbsp;<b><c:out value="${distName}"/> </b></label>  --%>
<!-- 			</div> -->
<!-- 	<br/> <br/> -->
<!-- 			<div class="form-group col-md-10"> -->
<%-- 				<label for="project" class="">Project Name: &nbsp;<b><c:out value="${projName}"/></b></label>  --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<br/> <br/><br/> -->
	<!-- 	<div class = "form-row">
			<div class="form-group col-md-2">
				<label for="project" class="">Area:</label> 
			</div>
			<div class="form-group col-md-7">
				<b>Project Area</b> &nbsp;<input type='radio' class='projectArea' value='P' name = 'projectArea' id='projectArea' checked='checked'> &nbsp;
						&nbsp; <input type='radio' class='controlledArea' value='C' name = 'projectArea' id='controlledArea'> &nbsp; <b>Controlled Area</b>
			</div>
		</div> -->
		
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="month" class="">Month: &nbsp;<b><c:out value="${month}"/></b></label> --%>
<!-- 		</div> -->
<!-- 		<br/> <br/> -->
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="finyear" class="">Financial Year: &nbsp;<b><c:out value="${finyear}"/></b></label> --%>
<!-- 		</div>  -->
<!-- 	</div> -->
      	
      	
      	
<!--       <br/> <br/><br/> <br/><br/> -->
		
	
	
	
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col-md-6"> -->
<%-- 			<label for="district" class="">District Name: <b><c:out value="${distName}"/></b></label>  --%>
<!-- 		</div> -->

<!-- 		<div class="form-group col-md-6"> -->
<%-- 			<label for="project" class="">Project Name: <b><c:out value="${projName}"/></b></label>  --%>
<!-- 		</div> -->
<!-- 	</div> -->
			
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="month" class="">Month: <b><c:out value="${month}"/></b></label> --%>
<!-- 		</div> -->
		
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="finyear" class="">Financial Year: <b><c:out value="${finyear}"/></b></label> --%>
<!-- 		</div>  -->
<!-- 	</div> -->

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
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">Project Area</th>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">Controlled Area</th>
      	</tr>
      </thead>
 		<tr>
        	<td width="4%"> <b><c:out value="1."></c:out></b></td>
     		<td width="40%"><b><c:out value="Area of Culturable Wasteland (ha)"></c:out></b></td>
     		<td>
     			<input type="text" id="culturable_wasteland" name="culturable_wasteland" maxlength="15" value="${culturablew}"  autocomplete = "off" onfocusin="decimalToFourPlace(event)"  placeholder="Only decimal" />
			</td>
			<td>
     			<input type="text" id="conculturable_wasteland" name="conculturable_wasteland" maxlength="15" value="${conculturablew}"  autocomplete = "off" onfocusin="decimalToFourPlace(event)"  placeholder="Only decimal" />
			</td>
		</tr> 
		<tr>
 			<td width="4%"><b><c:out value="2."></c:out></b></td>
  			<td width="40%"><b><c:out value="Number of Water Harvesting Structures(WHS) constructed/rejuvenated"></c:out></b></td>
  			<td>
     			<input type="text" id="whs_constructed_rejuvenated" name="whs_constructed_rejuvenated" maxlength="5" value="${whs}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"   />
 			</td>
 			<td>
     			<input type="text" id="conwhs_constructed_rejuvenated" name="conwhs_constructed_rejuvenated" maxlength="5" value="${conwhs}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"   />
 			</td>
		</tr> 
		<tr>
 			<td width="4%"><b><c:out value="3."></c:out></b></td>
  			<td width="40%"><b><c:out value="Area Covered with Soil and Moisture Conservation Activities (ha)"></c:out></b></td>
  			<td>
     			<input type="text" id="soil_moisture" name="soil_moisture" maxlength="15" value="${soil}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
 			<td>
     			<input type="text" id="consoil_moisture" name="consoil_moisture" maxlength="15" value="${consoil}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
		</tr> 
		<tr>
 			<td width="4%"><b><c:out value="4."></c:out></b></td>
  			<td width="40%"><b><c:out value="Area under Protective Irrigation (ha)"></c:out></b></td>
  			<td>
     			<input type="text" id="protective_irrigation" name="protective_irrigation" maxlength="15" value="${protective}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
 			<td>
     			<input type="text" id="conprotective_irrigation" name="conprotective_irrigation" maxlength="15" value="${conprotective}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="5."></c:out></b></td>
  			<td width="40%"><b><c:out value="Area of degraded land covered/ rainfed area development (ha)"></c:out></b></td>
  			<td>
     			<input type="text" id="degraded_rainfed" name="degraded_rainfed" maxlength="15" value="${degraded}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="condegraded_rainfed" name="condegraded_rainfed" maxlength="15" value="${condegraded}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
		</tr>
 		<tr>
 			<td width="4%"><b><c:out value="6."></c:out></b></td>
  			<td width="40%"><b><c:out value="Farmer`s Average Household Income per Annum (Rs. in Lakhs)"></c:out></b></td>
  			<td>
     			<input type="text" id="farmer_income" name="farmer_income" value="${income}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="confarmer_income" name="confarmer_income" value="${conincome}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="7."></c:out></b></td>
  			<td width="40%"><b><c:out value="No. of Farmers Benefited"></c:out></b></td>
  			<td>
     			<input type="text" id="farmer_benefited" name="farmer_benefited" maxlength="5" value="${benefited}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
 			<td>
     			<input type="text" id="confarmer_benefited" name="confarmer_benefited" maxlength="5" value="${conbenefited}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="8."></c:out></b></td>
  			<td width="40%"><b><c:out value="No. of Persondays Generated (man-days)"></c:out></b></td>
  			<td>
     			<input type="text" id="mandays_generated" name="mandays_generated" maxlength="5" value="${mandays}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
 			<td>
     			<input type="text" id="conmandays_generated" name="conmandays_generated" maxlength="5" value="${conmandays}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="9."></c:out></b></td>
  			<td width="40%"><b><c:out value="Average depth of Water table in dug wells (mts.)- Summer Season"></c:out></b></td>
  			<td>
     			<input type="text" id="dug_well" name="dug_well" value="${dug}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
 			<td>
     			<input type="text" id="condug_well" name="condug_well" value="${condug}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"   />
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="10."></c:out></b></td>
  			<td width="40%"><b><c:out value="Average depth of Water table in tube wells (mts.)- Summer Season"></c:out></b></td>
  			<td>
     			<input type="text" id="tube_well" name="tube_well" value="${tube}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
 			<td>
     			<input type="text" id="contube_well" name="contube_well" value="${contube}" maxlength="15" autocomplete = "off" onfocusin="decimalToFourPlace(event)" placeholder="Only decimal"  />
 			</td>
		</tr>

		<tr>
			<td  colspan="4"   align="center" >
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
