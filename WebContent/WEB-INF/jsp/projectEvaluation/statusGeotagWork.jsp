<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<link href="css/projectEvaluation.css" rel="stylesheet" type="text/css" />
<head>
<script type="text/javascript">
let formSubmitted = false;  

function savedata(event){
    event.preventDefault();  

    if (formSubmitted) return false;  

	if($('#geo_tagg_work').val()==='')
	{
		alert('Please enter Total No. of Works Geo-Tagged.');
		$('#geo_tagg_work').focus();
		return false;
	}
    if(confirm("Do You Want to save Status of Geo-tagging of Works?")) {
        formSubmitted = true;    ////    saveprojectProfile
        document.getElementById('geoTag').action = "saveGeoTagDetails";
        document.getElementById('geoTag').method = "post";
        document.getElementById('geoTag').submit();
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
		<span style="text-decoration:underline;">Mid Term Project Evaluation - Status of Geo-tagging of Works</span>
	</h4>
</div>

<hr />
<form:form name="geoTag" id="geoTag" modelAttribute="geoTag"
		action="saveGeoTagDetails" method="post" autocomplete="off">
		
		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${distName}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${projName}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${month}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${finyear}' /> />
		<input type="hidden" name="fromno" id="fromno" value="13" />
		
		<%--  <table style="width:100%; align-content: center;" >
        	<tr align="center" >
          		<td>District Name:-</td>
          		<td>
    					<b><c:out value="${distName}"/> </b>
          		</td>
          		<td>Project Name:-</td>
          		<td>
            		<b><c:out value="${projName}"/> </b>
          		</td>
          		<!-- <td >Area:-</td>
          		<td >
             		<b>Project Area</b> &nbsp;<input type='radio' class='projectArea' value='P' name = 'projectArea' id='projectArea' checked='checked'> &nbsp;
					&nbsp; <input type='radio' class='controlledArea' value='C' name = 'projectArea' id='projectArea'> &nbsp; <b>Controlled Area</b>
          		</td> -->
          		<td >Month:-</td>
          		<td >
              		<b><c:out value="${month}"/></b>
          		</td>
         		<td >Financial Year:-</td>
          		<td >
              		<b><c:out value="${finyear}"/></b>
          		</td>
          
       	</tr>
      </table> --%>
      
<!--       	<div class = "form-row"> -->
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
	Financial Year : &nbsp; <b><c:out value='${finyear}' /></b>, &nbsp;&nbsp;&nbsp; Month Name : &nbsp; <b><c:out value='${month}' /></b> 
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
      <b><c:out	value="Total No. of Works Geo-Tagged"></c:out></b>
     </td>
     <td>
     <input type="text" id="geo_tagg_work" name="geo_tagg_work" value="${twork}" onfocusin="numericOnly(event);" maxlength="5" placeholder="Only numeric" autocomplete = "off" />
	</td>
	<td>
     	<textarea  id="geo_tagg_work_remark" name="geo_tagg_work_remark" rows="2" cols="22" maxlength="200" autocomplete = "off"> ${tworkre} </textarea>
	</td>
</tr> 



<tr>
<td  colspan="4"   align="center" >
<input type="button" name="view"  id = "view" value="Confirm" class="btn btn-info" onclick="savedata(event);"/>
<!-- <button  class="btn btn-info col-2" id="geotagbtnSave" name="geotagbtnSave">Submit</button> -->
</td>
</tr>

</table>
</form:form>
</div>
    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
  <%--   <script src='<c:url value="/resources/js/projectevaluation/mandaysdetails.js" />'></script> --%>
</body>
