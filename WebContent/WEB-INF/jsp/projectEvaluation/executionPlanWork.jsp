<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script type="text/javascript">
let formSubmitted = false;  

function savedata(event){
    event.preventDefault();  

    if (formSubmitted) return false;  

	if($('#created_work').val()==='')
	{
		alert('Please enter No. of Work IDs Created.');
		$('#created_work').focus();
		return false;
	}
	if($('#completed_work').val()==='')
	{
		alert('Please enter No. of Work IDs Completed.');
		$('#completed_work').focus();
		return false;
	}
	if($('#ongoing_work').val()==='')
	{
		alert('Please enter No. of Work IDs Ongoing.');
		$('#ongoing_work').focus();
		return false;
	}
		
    if(confirm("Do You Want to save Execution of Planned Works against Targets?")) {
        formSubmitted = true;    ////    saveprojectProfile
        document.getElementById('planwork').action = "saveExecutionPlanWork";
        document.getElementById('planwork').method = "post";
        document.getElementById('planwork').submit();
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
		<span style="text-decoration:underline;">Project Evaluation - Execution of Planned Works against Targets</span>
	</h4>
</div>

<hr />
<form:form name="planwork" id="planwork" modelAttribute="planwork"
		action="savePlanWorkDetails" method="post" autocomplete="off">
		
		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${distName}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${projName}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${month}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${finyear}' /> />
		<input type="hidden" name="fromno" id="fromno" value="10" />
		
		 <%-- <table style="width:100%; align-content: center;" >
        	<tr align="center" >
          		<td>District Name:-</td>
          		<td>
    					<b><c:out value="${distName}"/> GWALIOR </b>
          		</td>
          		<td>Project Name:-</td>
          		<td>
            		<b><c:out value="${projName}"/> GWALIOR-WDC - 1 /2021-22</b>
          		</td>
          		<!-- <td >Area:-</td>
          		<td >
             		<b>Project Area</b> &nbsp;<input type='radio' class='projectArea' value='P' name = 'projectArea' id='projectArea' checked='checked'> &nbsp;
					&nbsp; <input type='radio' class='controlledArea' value='C' name = 'projectArea' id='projectArea'> &nbsp; <b>Controlled Area</b>
          		</td> -->
          		<td >Month:-</td>
          		<td >
              		<b><c:out value="${month}"/> July</b>
          		</td>
         		<td >Financial Year:-</td>
          		<td >
              		<b><c:out value="${finyear}"/> 2024-25</b>
          		</td>
          
       	</tr>
      </table> --%>
      
<!--       <div class = "form-row"> -->
<!-- 			<div class="form-group col-md-6"> -->
<%-- 				<label for="district" class="">District Name: &nbsp;<b><c:out value="${distName}"/> </b></label>  --%>
<!-- 			</div> -->
<!-- 	<br/> <br/> -->
<!-- 			<div class="form-group col-md-10"> -->
<%-- 				<label for="project" class="">Project Name: &nbsp;<b><c:out value="${projName}"/></b></label>  --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<br/> <br/><br/> -->
	
		
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="month" class="">Month: &nbsp;<b><c:out value="${month}"/></b></label> --%>
<!-- 		</div> -->
<!-- 		<br/> <br/> -->
<!-- 		<div class="form-group col"> -->
<%-- 			<label for="finyear" class="">Financial Year: &nbsp;<b><c:out value="${finyear}"/></b></label> --%>
<!-- 		</div>  -->
<!-- 	</div> -->
      	
		
<!-- 		<br/><br/><br/><br/> -->
		
		
<!-- 		<div class = "form-row"> -->
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
	<%-- <tr>
		<th colspan="24" >Project Name : &nbsp; Shimla-wdc-1/2021-22<c:out value='${stName}' /> &nbsp;&nbsp;&nbsp; Month : &nbsp; July &nbsp;&nbsp;&nbsp; 
		Financial Year : &nbsp; 2024-25  </th>
	</tr> --%>
	<tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="5" style="text-align:left; vertical-align: middle;"></th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Details</th>
      <th rowspan="4" style="text-align:left; vertical-align: middle;">Remarks</th>
      </tr>
      </thead>
 <tr>
      <td width="4%">
       <b><c:out	value="1."></c:out></b>
     </td>
     <td width="40%">
      <b><c:out	value="Total No. of Work IDs Created"></c:out></b>
     </td>
     <td>
     <input type="text" id="created_work" name="created_work" value="${crw}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
	</td>
	<td>
     	<textarea  id="created_work_remark" name="created_work_remark" rows="2" cols="22" maxlength="200" autocomplete = "off" > ${crwre}</textarea>
	</td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="2."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out value="Total No. of Works Completed"></c:out></b>
  </td><td>
     <input type="text" id="completed_work" name="completed_work" value="${comw}"  autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric" />
 </td>
 <td>
     	<textarea  id="completed_work_remark" name="completed_work_remark" rows="2" cols="22" maxlength="200" autocomplete = "off"> ${comwre} </textarea>
	</td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="3."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out value="Total No. of Works Ongoing"></c:out></b>
  </td><td>
     <input type="text" id="ongoing_work" name="ongoing_work" value="${ongw}" autocomplete = "off" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric"  />
 </td>
 <td>
     	<textarea  id="ongoing_work_remark" name="ongoing_work_remark" rows="2" cols="22" maxlength="200" autocomplete = "off" > ${ongwre} </textarea>
	</td>
</tr> 


<tr>
<td  colspan="4"   align="center" >
<input type="button" name="view"  id = "view" value="Confirm" class="btn btn-info" onclick="savedata(event);"/>
 <!-- <button  class="btn btn-info col-2" id="execbtnSave" name="execbtnSave">Submit</button> -->
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
