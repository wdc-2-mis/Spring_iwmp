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
			<select class="form-control col project" id="project" name="project" onchange="this.form.submit();">
				<option value="">--Select Project--</option>
				<c:if test="${not empty projectList}">
				<c:forEach items="${projectList}" var="lists">
				<c:if test="${lists.key eq project}">
					<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out
							value="${lists.value}" /></option>
							</c:if>
							<c:if test="${lists.key ne project}">
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
			<label for="yeark" class="d-none yeark"><b>Financial Year : &nbsp; </b></label> 
			<select class="form-control col d-none yeark" id="financial" name="financial">
				<option value="">--Select Year--</option>
			<%--	<option value="23">2023-24</option>
				 <c:if test="${not empty financialYear}">
					<c:forEach items="${financialYear}" var="fyear">
						<c:if test="${fyear.key eq financial}">
							<option value="<c:out value='${fyear.key}'/>" selected="selected"><c:out
									value="${fyear.value}" /></option>
						</c:if>
						<c:if test="${fyear.key ne financial}">
							<option value="<c:out value="${fyear.key}"/>"><c:out
									value="${fyear.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if> --%>
			</select>
			
		</div> 

<div class="form-group col-md-3">
			<label for="mont" class="d-none mont"><b>Period : &nbsp; </b></label> 
			<select class="form-control col d-none mont" id="month" name="month">
				<option value="">--Select Half Yearly--</option>
			<%--	<option value="3">October-March</option>
				 <c:if test="${not empty monthList}">
				<c:forEach items="${monthList}" var="lists">
				<c:if test="${lists.key eq month}">
							<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out
									value="${lists.value}" /></option>
						</c:if>
					<c:if test="${lists.key ne month}">
							<option value="<c:out value="${lists.key}"/>"><c:out
									value="${lists.value}" /></option>
						</c:if>
				</c:forEach>
				</c:if> --%>
			</select>
		</div>


  
  <!-- <input type="button" name="view"  id = "view" value="Get Data" class="btn btn-info" onclick="selectOutcome();"/> -->
</form:form>

<hr />
<div>
<table style="width:100%">
 <lable class="message badge badge-danger error"></lable>
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
		<tr class="d-none trk">
			<td width="40%"><b>Change in Farmer Income (in %)</b></td>
			<td><input type="text" id="farmer" name="farmer" autocomplete = "off" onfocusin="decimalToFourPlace(event)"  /></td>
		</tr>
		<tr class="d-none trk">
			<td width="40%"><b>Change in Cropped Area (in ha.)</b></td>
			<td>  <input type="text" id="changecorp" name="changecorp" autocomplete = "off" onfocusin="decimalToFourPlace(event)"   /></td>
		</tr>
		
		<tr>
<td></td>

<td>
<button class="btn btn-info d-none draft" id="draftSave" name="draftSave">Save as Draft</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button class="btn btn-info d-none complete" id="complete" name="complete">Complete</button>
</td>
</tr>

<%-- </c:if> 

<c:if test="${draftsize ne 0}">
    <c:forEach var="list" items="${draft}" varStatus="status">
    <input type="text" name="additional_brought_id" id="additional_brought_id" value="${list.additional_brought_id}" class="form-control" />
		<tr class="d-none mont">
			<td width="40%"><b>Additional area brought under diversified crops/change in cropping system</b></td>
			<td><input type="text" id="diversified" name="diversified" autocomplete = "off" onfocusin="decimalToFourPlace(event)" value="${list.diversified}" /></td>
		</tr>
		<tr class="d-none mont">
			<td width="40%"><b> Area brought from no crop/single crop to single/multiple crop</b></td>
			<td>  <input type="text" id="chnagesingle" name="chnagesingle"  autocomplete = "off" onfocusin="decimalToFourPlace(event)" value="${list.chnagesingle}" /></td>
		</tr>
		<tr class="d-none trk">
			<td width="40%"><b>Change in farmer income</b></td>
			<td><input type="text" id="farmer" name="farmer" autocomplete = "off" onfocusin="decimalToFourPlace(event)" value="${list.farmer_income}" /></td>
		</tr>
		<tr class="d-none trk">
			<td width="40%"><b>Change in croped area</b></td>
			<td>  <input type="text" id="changecorp" name="changecorp" autocomplete = "off" onfocusin="decimalToFourPlace(event)"  value="${list.change_corp}" /></td>
		</tr>
	</c:forEach>	
		<tr>
<td></td>

<td>
<button class="btn btn-info d-none draft" id="draftSave" name="draftSave">Save as Draft</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <button class="btn btn-info" id="complete" name="complete">Complete</button> 
</td>
</tr>

</c:if>	
--%>
					
</table>

<c:if test="${completesize ne 0}">
 <table class="table">
		 <tr>
		 <td>
		 <div class="col formheading" style="">
				<h4>
					<b>List of Complete Data</b>
				</h4>
		</div>
		 </td> 
		 </tr>
          <tr>
            <td>
            
            	<table id="dtBasicExample" class="table table-bordered table-striped table-highlight w-auto">
            	<thead>
              		<tr align="center">
              			<th align="center" width="2">S.No.</th>
		              	<th align="center" width="25%">Project Name</th>
                        <th align="center" width="8">Financial Year</th>
                        <th align="center" width="8">Achievement Type</th>
                        <th align="center" width="8">Period</th>
                        <th align="center" width="8">Additional area brought under diversified crops/change in cropping system</th>
                        <th align="center" width="8">Area brought from no crop/single crop to single/multiple crop</th>
                        <th align="center" width="8">Change in Farmer Income</th> 
                        <th align="center" width="8">Change in Cropped Area</th>
               		</tr>	</thead>
               	<tbody>	
               		<tr>
	               		<th align="center"><b> 1 </b></th>
	               		<th align="center"><b> 2 </b></th>
	               		<th align="center"><b> 3 </b></th>
		               	<th align="center"><b> 4 </b></th>
		               	<th align="center"><b> 5 </b></th>
	               		<th align="center"><b> 6 </b></th>
	               		<th align="center"><b> 7 </b></th>
	               		<th align="center"><b> 8 </b></th>
	               		<th align="center"><b> 9 </b></th>
               		</tr><c:set var="proj" value="" />
               		<c:set var="fin" value="" />
						<c:forEach items="${complete}" var="dataV" varStatus="count">
							<tr>
							<td><c:out value='${count.count}' /></td>
								
								<c:choose>
									<c:when test="${proj ne dataV.project}">
										<c:set var="proj" value="${dataV.project}" />
										<td> <c:out value="${dataV.project}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${fin ne dataV.finyear}">
										<c:set var="fin" value="${dataV.finyear}" />
										<td> <c:out value="${dataV.finyear}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<td>
								
								<c:if test="${dataV.achiev_type eq 'Month-Wise'}">
								
									<c:out value="Half-Yearly" />
								
								</c:if>
								<c:if test="${dataV.achiev_type eq 'Year-Wise'}">
									<c:out value='${dataV.achiev_type}' />
								</c:if>
								</td>
								<td><c:out value='${dataV.month}' /></td>
								<td align="center"><c:out value='${dataV.diversified}' /></td>
								<td align="center"><c:out value='${dataV.chnagesingle}' /></td>
								<td align="center"><c:out value='${dataV.farmer_income}' /></td>
								<td align="center"><c:out value='${dataV.change_corp}' /></td>
							</tr>
						</c:forEach>
					
              </tbody>
              </table>
            </td>
          </tr>
         
     </table>

</c:if>


							
</div>

</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/additionalBroughtFarmerCropArea.js" />'></script>
</body>
</html>