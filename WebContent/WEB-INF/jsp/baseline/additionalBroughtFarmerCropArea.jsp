<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>
<script type="text/javascript">

function selectOutcome(e){
	var project = $('#project').val();
	var month = $('#month').val();
	var financial = $('#financial').val();
	if (document.getElementById("project").value =='') 
	{
		alert("Please select Project");
		document.getElementById("project").focus();
		return;
	}
	if (document.getElementById("month").value =='') 
	{
		alert("Please select Month");
		document.getElementById("month").focus();
		return;
	}
	if (document.getElementById("financial").value =='') 
	{
		alert("Please select Financial Year");
		document.getElementById("financial").focus();
		return;
	}
	document.getElementById('AdditionalBroughtFarmerCropArea').action ="getAdditionalBroughtFarmerCropArea";
	document.getElementById('AdditionalBroughtFarmerCropArea').method="get";
	document.getElementById('AdditionalBroughtFarmerCropArea').submit();
}

function checkValue(temp) {
	$degradedrainf = parseFloat($('#degradedrainf').val());
	$hiddendegradedrainf = parseFloat($('#hiddendegradedrainf').val());
	
	if($degradedrainf > $hiddendegradedrainf){
	    alert("Degraded / Rainfed Developed cannot be greater than Degraded / Rainfed Values");
	    document.getElementById("degradedrainf").focus(); 
	    document.getElementById("degradedrainf").value = "";
	    return false;
	    }
}



</script>


</head>





<div class="maindiv">
<div class="col formheading" style="">
		<h4>
			<b>Add OOMF Additional Achievement Details </b>
			
			<!--  Month-wise Diversified crops/change in cropping, no crop/single crop to single/multiple crop, Farmer income and change crop area -->
		</h4>
	</div>
<hr />
<form:form class="form-inline" name="AdditionalBroughtFarmerCropArea" id="AdditionalBroughtFarmerCropArea" modelAttribute="outcomeparam" action="getAdditionalBroughtFarmerCropArea" method="get" autocomplete="off">

		

<div class="form-group col-md-3">
  <label for="project" class=""><b>Project : &nbsp; </b></label> 
  <select class="form-control col project" id="project" name="project">
    <option value="">--Select Project--</option>
    <c:if test="${not empty projectList}">
      <c:forEach items="${projectList}" var="lists">
        <c:choose>
          <c:when test="${lists.key eq project}">
            <option value="<c:out value='${lists.key}'/>" selected="selected">
              <c:out value="${lists.value}" />
            </option>
          </c:when>
          <c:otherwise>
            <option value="<c:out value='${lists.key}'/>">
              <c:out value="${lists.value}" />
            </option>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </c:if>
  </select>
</div>

		
		<div class="form-group col-md-3">
			<label for="year" class=""><b>Financial Year : &nbsp; </b></label> 
			<select class="form-control col year" id="financial" name="financial">
				<option value="">--Select Year--</option>
				<c:if test="${not empty finYear}">
				<c:forEach items="${finYear}" var="lists">
				<c:if test="${lists.key eq financial}">
					<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out
							value="${lists.value}" /></option>
							</c:if>
							<c:if test="${lists.key ne financial}">
							<option value="<c:out value="${lists.key}"/>"><c:out
									value="${lists.value}" /></option>
						</c:if>
				</c:forEach>
				</c:if>
		</select>
		</div> 	
		
	<div class="form-group col-md-3">
    <label for="line" class=""><b>Achievement Type :</b> &nbsp;</label>
     <select class="form-control col groupType" id="groupType" name="groupType" >
	    <option value="">--Select Type--</option>
	      <c:forEach items="${groupType}" var="group">
				<option value="<c:out value="${group.key}"/>"><c:out value="${group.value}" /></option>
		  </c:forEach> 
    </select>
  </div>
		
		

<div class="form-group col-md-3">
			<label for="mont" class="d-none mont"><b>Period : &nbsp; </b></label> 
			<select class="form-control col d-none mont" id="month" name="month">
				<option value="">--Select Half Yearly--</option>
			
			</select>
		</div>


  
  <!-- <input type="button" name="view"  id = "view" value="Get Data" class="btn btn-info" onclick="selectOutcome();"/> -->
</form:form>

<hr />
<div>
<table style="width:100%">
 <div class="message badge badge-danger error"></div>
<%-- <c:if test="${draftsize eq 0}"> --%>
		<input type="hidden" name="additional_brought_id" id="additional_brought_id"  value=""/>
		<tr class="d-none montt">
			<td width="40%"><b>Additional area brought under diversified crops/change in cropping system (in ha.)</b></td>
			<td><input type="text" id="diversified" name="diversified" autocomplete = "off" onfocusin="decimalToFourPlace(event)" /></td>
		</tr>
		<tr class="d-none montt">
			<td width="40%"><b> Area brought from no crop/single crop to single/multiple crop (in ha.)</b></td>
			<td>  <input type="text" id="chnagesingle" name="chnagesingle"  autocomplete = "off" onfocusin="decimalToFourPlace(event)"  /></td>
		</tr>
		<!-- <tr class="d-none montt">
			<td width="40%"><b>Increase in Pulses Area</b></td>
			<td><input type="text" id="pulses" name="pulses" autocomplete = "off" onfocusin="decimalToFourPlace(event)" /></td>
		</tr>
		<tr class="d-none montt">
			<td width="40%"><b> Increase in Oilseeds Area</b></td>
			<td>  <input type="text" id="oilseeds" name="oilseeds"  autocomplete = "off" onfocusin="decimalToFourPlace(event)"  /></td>
		</tr>
		<tr class="d-none trk">
			<td width="40%"><b>Increase in Farmer Income (in %)</b></td>
			<td><input type="text" id="farmer" name="farmer" autocomplete = "off" onfocusin="decimalToFourPlace(event)"  /></td>
		</tr> -->
		<tr class="d-none trk">
			<td width="40%"><b>Increase in Gross Cropped Area (in ha.)</b></td>
			<td>  <input type="text" id="changecorp" name="changecorp" autocomplete = "off" onfocusin="decimalToFourPlace(event)"   /></td>
		</tr>
		
		<tr>
<td></td>

<td>
<button class="btn btn-info d-none draft" id="draftSave" name="draftSave">Save as Draft</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!-- <button class="btn btn-info d-none complete" id="complete" name="complete">Complete</button> -->
<button class="btn btn-info d-none" id="forwardSLNA">Forward To SLNA</button>
</td>
</tr>
			
</table>

<div id="completeTableContainer">
<c:if test="${completesize ne 0}">
                <jsp:include page="/WEB-INF/jsp/baseline/completeTable.jsp"/>
</c:if>
</div>

							
</div>

</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/additionalBroughtFarmerCropArea.js" />'></script>
</body>
</html>