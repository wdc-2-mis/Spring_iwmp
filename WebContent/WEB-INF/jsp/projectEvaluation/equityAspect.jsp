<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<html>

<head>
<title>Equity Aspect</title>
<script type="text/javascript">
let formSubmitted = false;

function savedata(){
	
	
    if (formSubmitted) return false;
    
    
    
 /* var area;
    
    if($('#projectArea:checked').val()!=null && $('#projectArea:checked').val() !='undefined')
	{
		
    	area=$('#projectArea:checked').val();
		
	}
	if($('#controlledArea:checked').val()!=null && $('#controlledArea:checked').val() !='undefined')
	{
		
		area=$('#controlledArea:checked').val();
		
	}
	 */
	 
	 
    var pwc = $("input[name='pWatershedCom']:checked").val();
    if(pwc=== null || pwc===undefined){
    	alert('Please check whether landless poor and women have a place in watershed committee for the project area');
    	return false;
    }
			
    var cwc = $("input[name='cWatershedCom']:checked").val();
    if(cwc=== null || cwc===undefined){
      	alert('Please check whether landless poor and women have a place in watershed committee for the controlled area');
      	return false;
    }
    	
    var pfs = $("input[name='pFpoShgVli']:checked").val();
    if(pfs=== null || pfs===undefined){
      	alert('Please check whether landless poor and women are active members of FPO, SHG, Village level Institutions (VLIs) and various UGs for the project area');
      	return false;
    }
    
    var cfs = $("input[name='cFpoShgVli']:checked").val();
    if(cfs=== null || cfs===undefined){
      	alert('Please check whether landless poor and women are active members of FPO, SHG, Village level Institutions (VLIs) and various UGs for the controlled area');
      	return false;
    }
    
    var plh = $("input[name='pLivelihood']:checked").val()
    if(plh=== null || plh===undefined){
      	alert('Please check whether landless and asset-less poor benefited from activities that promote alternate livelihood options for the project area');
      	return false;
    }
    
    var clh = $("input[name='cLivelihood']:checked").val();
    if(clh=== null ||   clh===undefined){
      	alert('Please check whether landless and asset-less poor benefited from activities that promote alternate livelihood options for the controlled area');
      	return false;
    }
     
	
	if(confirm("Do you want to save Equity Aspect?")) {
        formSubmitted = true;    //    saveEquityAspect
        document.getElementById('equityaspect').action = "saveEquityAspect";
        document.getElementById('equityaspect').method = "post";
        document.getElementById('equityaspect').submit();
    }

    return false;     
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
		<span style="text-decoration:underline;">Project Evaluation - Equity Aspect</span>
	</h4>
</div>

<hr />

<form:form name="equityaspect" id="equityaspect" modelAttribute="equityaspect" action="saveEquityAspect" method="post">
 		
 		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${dname}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${pname}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${mname}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${fname}' /> />  
 		
		<input type="hidden" name="fromno" id="fromno" value="9" />
		<input type="hidden" id="WatershedCompc" name="WatershedCompc" />
		
<!-- 	<div class = "form-row"> -->
<!-- 		<div class="form-group col-md-6"> -->
<%-- 			<label for="district" class="">District Name: <b><c:out value="${dname}"/></b></label>  --%>
<!-- 		</div> -->

<!-- 		<div class="form-group col-md-6"> -->
<%-- 			<label for="project" class="">Project Name: <b><c:out value="${pname}"/></b></label>  --%>
<!-- 		</div> -->
<!-- 	</div>				 -->
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
<table style="width:85%">
	<thead>
		<tr>
			<th><b>Sl.No.</b></th>
			<th style="width:35%"><b>Aspect Description</b></th>
			<th><b>Project Area</b></th>
			<th><b>Controlled Area</b></th>
			<th><b>Remarks</b></th>
		</tr>
	</thead>	
	<tbody>	
		<tr>
			<td>
				<b><c:out value="1"/></b>
			</td>
			<td>
				<b><c:out value="Whether landless poor and women find a place in watershed units like watershed committee"/></b>
			</td>
			<td>
<!--      			<input type="radio"  value="Yes" /><label>Yes</label> -->
<!-- 				<input type="radio"  value="No" /><label>No</label> -->
<%-- 				<input type="radio" id="pWatershedCom" name="pWatershedCom" value="true" checked="checked"  value="${nr}"/> Yes --%>
<%-- 				<input type="radio" id="pWatershedCom" name="pWatershedCom" value="false" value="${nr}"/> No --%>
				<c:if test="${pWatershedCom eq null }">
					<input type="radio" id="pWatershedCom" name="pWatershedCom" value="true" /> Yes
					<input type="radio" id="pWatershedCom1" name="pWatershedCom" value="false" /> No
				</c:if>
				<c:if test="${pWatershedCom eq true }">
					<input type="radio" id="pWatershedCom" name="pWatershedCom" value="true" checked="checked" /> Yes
					<input type="radio" id="pWatershedCom1" name="pWatershedCom" value="false" /> No
				</c:if>
				<c:if test="${pWatershedCom eq false }">
					<input type="radio" id="pWatershedCom" name="pWatershedCom" value="true" /> Yes
					<input type="radio" id="pWatershedCom1" name="pWatershedCom" value="false" checked="checked" /> No
				</c:if>
			</td>
			<td>
				<c:if test="${cWatershedCom eq null }">
					<input type="radio" id="cWatershedCom" name="cWatershedCom" value="true" /> Yes
					<input type="radio" id="cWatershedCom1" name="cWatershedCom" value="false" /> No
				</c:if>
				<c:if test="${cWatershedCom eq true }">
					<input type="radio" id="cWatershedCom" name="cWatershedCom" value="true" checked="checked" /> Yes
					<input type="radio" id="cWatershedCom1" name="cWatershedCom" value="false" /> No
				</c:if>
				<c:if test="${cWatershedCom eq false }">
					<input type="radio" id="cWatershedCom" name="cWatershedCom" value="true" /> Yes
					<input type="radio" id="cWatershedCom1" name="cWatershedCom" value="false" checked="checked" /> No
				</c:if>
			</td>
			<td>
<%-- 				<input type="text" id="rmkWatershedCom" name="rmkWatershedCom" value="${rmkWatershedCom}" autocomplete = "off"/> --%>
				<textarea id="rmkWatershedCom" name="rmkWatershedCom" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkWatershedCom}</textarea>
			</td>
		</tr>
		<tr>
			<td>
				<b><c:out value="2"/></b>
			</td>
			<td>
				<b><c:out value="Whether landless poor and women are active member of FPO, SHG, Village level Institutions (VLIs) and various UGs"/></b>
			</td>
			<td>
				<c:if test="${pFpoShgVli eq null }">
					<input type="radio" id="pFpoShgVli" name="pFpoShgVli" value="true" /> Yes
					<input type="radio" id="pFpoShgVli1" name="pFpoShgVli" value="false" /> No
				</c:if>
				<c:if test="${pFpoShgVli eq true }">
					<input type="radio" id="pFpoShgVli" name="pFpoShgVli" value="true" checked="checked" /> Yes
					<input type="radio" id="pFpoShgVli1" name="pFpoShgVli" value="false" /> No
				</c:if>
				<c:if test="${pFpoShgVli eq false }">
					<input type="radio" id="pFpoShgVli" name="pFpoShgVli" value="true" /> Yes
					<input type="radio" id="pFpoShgVli1" name="pFpoShgVli" value="false" checked="checked" /> No
				</c:if>
			</td>
			<td>
				<c:if test="${cFpoShgVli eq null }">
					<input type="radio" id="cFpoShgVli" name="cFpoShgVli" value="true" /> Yes
					<input type="radio" id="cFpoShgVli1" name="cFpoShgVli" value="false" /> No
				</c:if>
				<c:if test="${cFpoShgVli eq true }">
					<input type="radio" id="cFpoShgVli" name="cFpoShgVli" value="true" checked="checked" /> Yes
					<input type="radio" id="cFpoShgVli1" name="cFpoShgVli" value="false" /> No
				</c:if>
				<c:if test="${cFpoShgVli eq false }">
					<input type="radio" id="cFpoShgVli" name="cFpoShgVli" value="true" /> Yes
					<input type="radio" id="cFpoShgVli1" name="cFpoShgVli" value="false" checked="checked" /> No
				</c:if>
			</td>
			<td>
<%-- 				<input type="text" id="rmkFpoShgVli" name="rmkFpoShgVli" value="${rmkFpoShgVli}" autocomplete = "off"/> --%>
				<textarea id="rmkFpoShgVli" name="rmkFpoShgVli" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkFpoShgVli}</textarea>
			</td>
		</tr>
		<tr>
			<td>
				<b><c:out value="3"/></b>
			</td>
			<td>
				<b><c:out value="Whether landless and asset-less poor benefited from activities that promote alternate livelihood options"/></b>
			</td>
			<td>
				<c:if test="${pLivelihood eq null }">
					<input type="radio" id="pLivelihood" name="pLivelihood" value="true" /> Yes
					<input type="radio" id="pLivelihood1" name="pLivelihood" value="false" /> No
				</c:if>
				<c:if test="${pLivelihood eq true }">
					<input type="radio" id="pLivelihood" name="pLivelihood" value="true" checked="checked" /> Yes
					<input type="radio" id="pLivelihood1" name="pLivelihood" value="false" /> No
				</c:if>
				<c:if test="${pLivelihood eq false }">
					<input type="radio" id="pLivelihood" name="pLivelihood" value="true" /> Yes
					<input type="radio" id="pLivelihood1" name="pLivelihood" value="false" checked="checked" /> No
				</c:if>
			</td>
			<td>
				<c:if test="${cLivelihood eq null }">
					<input type="radio" id="cLivelihood" name="cLivelihood" value="true" /> Yes
					<input type="radio" id="cLivelihood1" name="cLivelihood" value="false" /> No
				</c:if>
				<c:if test="${cLivelihood eq true }">
					<input type="radio" id="cLivelihood" name="cLivelihood" value="true" checked="checked" /> Yes
					<input type="radio" id="cLivelihood1" name="cLivelihood" value="false" /> No
				</c:if>
				<c:if test="${cLivelihood eq false }">
					<input type="radio" id="cLivelihood" name="cLivelihood" value="true" /> Yes
					<input type="radio" id="cLivelihood1" name="cLivelihood" value="false" checked="checked" /> No
				</c:if>
			</td>
			<td>
<%-- 				<input type="text" id="rmkLivelihood" name="rmkLivelihood" value="${rmkLivelihood}" autocomplete = "off"/> --%>
				<textarea id="rmkLivelihood" name="rmkLivelihood" autocomplete = "off" rows="2" cols="22" maxlength="200" >${rmkLivelihood}</textarea>
			</td>
		</tr>
		<tr>
			<th colspan="5" style="align-content: center;">
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
				&nbsp; &nbsp; &nbsp;
						
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
<%-- <script src='<c:url value="/resources/js/projectevaluation/equityaspect.js" />'></script> --%>
</body>
</html>