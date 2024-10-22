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
	document.getElementById('outcomeparam').action ="outcomeaddpara";
	document.getElementById('outcomeparam').method="get";
	document.getElementById('outcomeparam').submit();
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


function checkValuefemale(female){
	$sc = parseFloat($('#sc').val());
	$st = parseFloat($('#st').val());
	$others = parseFloat($('#others').val());
	$female = parseFloat($('#female').val());
    $total = $sc + $st + $others
    $smallfarmer = parseFloat($('#smallfarmer').val());
    $marginalfarmer = parseFloat($('#marginalfarmer').val());
    $landless = parseFloat($('#landless').val());
    $bpl = parseFloat($('#bpl').val());
    
	
	if($female > $total){
	    alert("Female Farmer value cannot be SC+ST+Others values");
	    document.getElementById("female").focus(); 
	    document.getElementById("female").value = "";
	    return false;
	    }

	if($smallfarmer > $total){
	    alert("Small Farmer value cannot be SC+ST+Others values");
	    document.getElementById("smallfarmer").focus(); 
	    document.getElementById("smallfarmer").value = "";
	    return false;
	    }

	if($smallfarmer > $total){
	    alert("Small Farmer value cannot be SC+ST+Others values");
	    document.getElementById("smallfarmer").focus(); 
	    document.getElementById("smallfarmer").value = "";
	    return false;
	    }
	if($marginalfarmer > $total){
	    alert("Marginal Farmer value cannot be SC+ST+Others values");
	    document.getElementById("marginalfarmer").focus(); 
	    document.getElementById("marginalfarmer").value = "";
	    return false;
	    }
	if($landless > $total){
	    alert("Landless Farmer value cannot be SC+ST+Others values");
	    document.getElementById("landless").focus(); 
	    document.getElementById("landless").value = "";
	    return false;
	    }
	if($bpl > $total){
	    alert("BPL Farmer value cannot be SC+ST+Others values");
	    document.getElementById("bpl").focus(); 
	    document.getElementById("bpl").value = "";
	    return false;
	    }
	}
</script>


</head>





<div class="maindiv">
<div class="col formheading" style="">
		<h4>
			<u>Add data related to Outcome</u>
		</h4>
	</div>
<hr />
<form:form class="form-inline" name="outcomeparam"
		id="outcomeparam" modelAttribute="outcomeparam"
		action="fetchOutcomePara1" method="post" autocomplete="off">
		<input type="hidden" name="res" id="res" value="" class="form-control" />

<div class="form-group col-md-3">
			<label for="project" class=""><b>Project :- </b></label> <select
				class="form-control col project" id="project" name="project">
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
			<label for="project" class=""><b>Financial Year:- </b></label> <select
				class="form-control col project" id="financial" name="financial">
				<option value="">--Select Year--</option>
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
				</c:if>
			</select>
			
		</div> 
  		

<div class="form-group col-md-3">
			<label for="project" class=""><b>Month :- </b></label> <select
				class="form-control col project" id="month" name="month">
				<option value="">--Select Month--</option>
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
				</c:if>
			</select>
		</div>


  <input type="button" name="view"  id = "view" value="Submit"
					class="btn btn-info" onclick="selectOutcome();"/>
</form:form>
<hr />
<div>
<table style="width:100%">
<c:if test="${indi eq null}">
<tr>
<td width="4%">
<b><c:out	value="1."></c:out></b>
</td>
<td width="40%">
<b><c:out	value="Area of Degraded Land Covered / Rainfed Developed (in ha)"></c:out></b>
</td><td>
     <input type="text" id="degraded" name="degraded" onblur="checkValue(degraded)" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class=""  />
</td>
</tr>
<tr>
<td width="3%">
<b><c:out	value="2."></c:out></b>
</td>
<td width="40%">
   <b> <c:out	value="No. of Mandays generated (in mandays)"></c:out></b>
  </td><td>  
     <input type="text" id="mandays" name="mandays"   onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<b><c:out	value="3"></c:out></b>
</td>
<td width="30%">
    <b><c:out	value="No. of Farmers benefitted"></c:out></b>
  </td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="i. SC"></c:out></b>
  </td><td>  
     <input type="text" id="sc" name="sc"   onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="ii. ST"></c:out></b>
  </td><td>  
     <input type="text" id="st" name="st"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="iii. Others"></c:out></b>
  </td><td>  
     <input type="text" id="others" name="others"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Female (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id=female name="female"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Small Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="smallfarmer" name="smallfarmer"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="Marginal Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="marginalfarmer" name="marginalfarmer"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Landless (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="landless" name="landless"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b> <c:out	value="BPL (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="bpl" name="bpl"   onfocusin="numericOnly(event)" class=""  />
</td>
</tr>	

<tr>

</tr>
</c:if>	

<c:if test="${indi ne null && draft eq null && complete eq null}">
<c:forEach var="list" items="${indi}" varStatus="status">
           <c:forEach var="listUser" items="${list.value}" >
<tr>
<td width="4%">
<b><c:out	value="1."></c:out></b>
</td>
<td width="40%">
<b><c:out	value="Area of Degraded Land Covered / Rainfed Developed (in ha):"></c:out></b>
</td><td>
     <input type="text" id="degradedrainf" name="degradedrainf"  onblur="checkValue(degradedrainf)" onfocusin="decimalToFourPlace(event)" class=""  />
</td>
<td>
<c:out value="${listUser.degraded_rainfed}"></c:out>
						 <input type="hidden" class="dc" id ="hiddendegradedrainf" name="hiddendegradedrainf"
									value="${listUser.degraded_rainfed}">
</td>
</tr>

<tr>
<td width="3%">
<b><c:out	value="2."></c:out></b>
</td>
<td width="40%">
   <b> <c:out	value="No. of Mandays generated (in mandays)"></c:out></b>
  </td><td>  
     <input type="text" id="mandays" name="mandays"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<b><c:out	value="3."></c:out></b>
</td>
<td width="30%">
    <b><c:out	value="No. of Farmers benefitted"></c:out></b>
  </td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="i. SC"></c:out></b>
  </td><td>  
     <input type="text" id="sc" name="sc"   onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="ii. ST"></c:out></b>
  </td><td>  
     <input type="text" id="st" name="st"   onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="iii. Others"></c:out></b>
  </td><td>  
     <input type="text" id="others" name="others"  onfocusin="numericOnly(event)"  class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Female (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id=female name="female"  onfocusin="numericOnly(event)" onblur="checkValuefemale(female)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Small Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="smallfarmer" name="smallfarmer"  onfocusin="numericOnly(event)" onblur="checkValuefemale(smallfarmer)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="Marginal Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="marginalfarmer" name="marginalfarmer"  onfocusin="numericOnly(event)"  onblur="checkValuefemale(marginalfarmer)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Landless (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="landless" name="landless"  onfocusin="numericOnly(event)" onblur="checkValuefemale(landless)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b> <c:out	value="BPL (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="bpl" name="bpl"   onfocusin="numericOnly(event)" onblur="checkValuefemale(bpl)" class=""  />
</td>
</tr>	

<tr>
<td></td>

<td>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<button class="btn btn-info" id="draftSave" name="draftSave">Save as Draft</button>
   <button class="btn btn-info" id="complete" name="complete">Complete</button>
</td>
</tr>
</c:forEach>
</c:forEach>
</c:if>	


<c:if test="${indi ne null && draft ne null && complete eq null}">
<c:forEach var="list" items="${draft}" varStatus="status">
           <c:forEach var="listUser" items="${list.value}" >
<tr>
<td width="4%">
<b><c:out	value="1."></c:out></b>
</td>
<td width="40%">
<b><c:out	value="Area of Degraded Land Covered / Rainfed Developed (in ha)"></c:out></b>
</td><td>
     <input type="text" id="degradedrainf" name="degradedrainf" value="${listUser.degradedrainf}" onblur="checkValue(degradedrainf)"  onfocusin="decimalToFourPlace(event)" class=""  />
</td>
<td>
<c:out value="${listUser.degraded_rainfed}"></c:out>
						 <input type="hidden" class="dc" id ="hiddendegradedrainf" name="hiddendegradedrainf"
									value="${listUser.degraded_rainfed}">
									</td>
</tr>

<tr>
<td width="3%">
<b><c:out	value="2."></c:out></b>
</td>
<td width="40%">
   <b> <c:out	value="No. of Mandays generated (in mandays)"></c:out></b>
  </td><td>  
     <input type="text" id="mandays" name="mandays"  value="${listUser.man_day_gen}"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<b><c:out	value="3."></c:out></b>
</td>
<td width="30%">
    <b><c:out	value="No. of Farmers benefitted"></c:out></b>
  </td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="i. SC"></c:out></b>
  </td><td>  
     <input type="text" id="sc" name="sc"  value="${listUser.farmer_sc}"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="ii. ST"></c:out></b>
  </td><td>  
     <input type="text" id="st" name="st"    value="${listUser.farmer_st}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="iii. Others"></c:out></b>
  </td><td>  
     <input type="text" id="others" name="others"    value="${listUser.others}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Female (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id=female name="female"   onblur="checkValuefemale(female)" value="${listUser.farmer_female}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Small Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="smallfarmer" name="smallfarmer"    onblur="checkValuefemale(smallfarmer)" value="${listUser.farmer_small}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="Marginal Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="marginalfarmer" name="marginalfarmer"  onblur="checkValuefemale(marginalfarmer)"  value="${listUser.farmer_mirginal}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Landless (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="landless" name="landless"   onblur="checkValuefemale(landless)" value="${listUser.farmer_landless}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b> <c:out	value="BPL (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="bpl" name="bpl"   onblur="checkValuefemale(bpl)" value="${listUser.farmer_bpl}" onfocusin="numericOnly(event)" class=""  />
     <input type="hidden" id="outcome2_id" name="outcome2_id"   value="${listUser.outcome2_id}" class=""  />
</td>
</tr>	

<tr>
<td></td>

<td>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<button class="btn btn-info" id="draftSave" name="draftSave">Save as Draft</button>
   <button class="btn btn-info" id="complete" name="complete">Complete</button>
</td>
</tr>
</c:forEach>
</c:forEach>
</c:if>	

<c:if test="${indi ne null && draft eq null && complete ne null}">
<c:forEach var="list" items="${complete}" varStatus="status">
           <c:forEach var="listUser" items="${list.value}" >
           
<tr>
<b style="color: #FF0000;"><c:out value="${listUser.project}, ${listUser.month}, ${listUser.finyear} data already completed"></c:out></b>
</tr>           
<tr>
<td width="4%">
<b><c:out	value="1."></c:out></b>
</td>
<td width="40%">
<b><c:out	value="Area of Degraded Land Covered / Rainfed Developed (in ha)"></c:out></b>
</td><td>
     <input type="text" id="degradedrainf" name="degradedrainf" value="${listUser.degradedrainf}" onblur="checkValue(degradedrainf)"  onfocusin="decimalToFourPlace(event)" class=""  />
</td>
<td>
<c:out value="${listUser.degraded_rainfed}"></c:out>
						 <input type="hidden" class="dc" id ="hiddendegradedrainf" name="hiddendegradedrainf"
									value="${listUser.degraded_rainfed}">
									</td>
</tr>


<tr>
<td width="3%">
<b><c:out	value="2."></c:out></b>
</td>
<td width="40%">
   <b> <c:out	value="No. of Mandays generated (in mandays)"></c:out></b>
  </td><td>  
     <input type="text" id="mandays" readonly name="mandays"  value="${listUser.man_day_gen}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<b><c:out	value="3"></c:out></b>
</td>
<td width="30%">
    <b><c:out	value="No. of Farmers benefitted"></c:out></b>
  </td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="i. SC"></c:out></b>
  </td><td>  
     <input type="text" id="sc" readonly name="sc"  value="${listUser.farmer_sc}"  onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="ii. ST"></c:out></b>
  </td><td>  
     <input type="text" id="st" readonly name="st"    value="${listUser.farmer_st}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="iii. Others"></c:out></b>
  </td><td>  
     <input type="text" id="others" readonly name="others"    value="${listUser.others}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Female (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id=female readonly name="female"    value="${listUser.farmer_female}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Small Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="smallfarmer" readonly name="smallfarmer"    value="${listUser.farmer_small}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b><c:out	value="Marginal Farmers (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="marginalfarmer" readonly name="marginalfarmer"    value="${listUser.farmer_mirginal}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
   <b> <c:out	value="Landless (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="landless" readonly name="landless"   value="${listUser.farmer_landless}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>

<tr>
<td width="3%">
<c:out	value=""></c:out>
</td>
<td width="30%">
    <b> <c:out	value="BPL (Out of i+ ii+ iii)"></c:out></b>
  </td><td>  
     <input type="text" id="bpl" name="bpl"   readonly value="${listUser.farmer_bpl}" class=""  />
     <input type="hidden" id="outcome2_id" name="outcome2_id"   value="${listUser.outcome2_id}" onfocusin="numericOnly(event)" class=""  />
</td>
</tr>	

<tr>
<td></td>


</tr>
</c:forEach>
</c:forEach>
</c:if>	
					
</table>
							
</div>

</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/outcomepara.js" />'></script>
</body>
</html>