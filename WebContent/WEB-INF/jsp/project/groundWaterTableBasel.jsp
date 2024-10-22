<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.input {    width: inherit !important;}
.halfwidth {width:50px !important;}
</style>

<div class="maindiv">

<div class="col formheading" style=""><h4><b>Add Ground Water Table </b></h4> </div>
    <hr/>
    <label class="errormessage"></label>
    
    
<form:form class="form-inline" autocomplete="off" id="gwtform" name="gwtform" action="gwtDetaildataBasel" method="post" >
	<c:if test="${messageGWT ne null}">
	<script>
	    alert('<c:out value="${messageGWT}"/>');
	</script>
	</c:if>
		<input type="hidden" name="gwtid" id="gwtid" value="" />
 		<input type="hidden" name="gwtdtlid" id="gwtdtlid" value="" />
 		<input type="hidden" name="projid" id="projid" value="" />
 		
  <div class="form-group offset-md-3 col-md-3">
    <label for="project" class=""><b>Project:-</b> &nbsp; </label>
     <select class="form-control col project" id="project" name="project" onchange="this.form.submit();" style="text-align:center; width: 10%; ">
    <option value="">--Select Project--</option>
     <c:forEach items="${projectList}" var="project">
				<option value="<c:out value="${project.key}"/>" <c:out value="${project.key== projectcd ?'selected':'' }"/>>
						<c:out value="${project.value}" /></option>
		</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-3">
    <label for="line" class=""><b>At the time of:-</b> &nbsp;</label>
     <select class="form-control col groupType" id="groupType" name="groupType" >
	    <option value="">--Select Type--</option>
	      <c:forEach items="${groupType}" var="group">
				<option value="<c:out value="${group.key}"/>"><c:out value="${group.value}" /></option>
		  </c:forEach> 
    </select>
  </div>
   <div class="form-group col-md-3 ">
    	<label for="year" class="d-none year">Financial Year </label>
     	<select class="form-control col d-none year" id="fyear" name="fyear" >
	      	<option value="">--Select Year--</option>
      			<c:forEach items="${yearList}" var="yearl">
					<option value="<c:out value="${yearl.key}"/>" ><c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    	</select>
    	 <br/><br/> 
  </div> 
 
  <!-- <div class="form-group col-md-3 ">
    <label for="txtNoOf" class="lblNoOf noOf d-none"></label>
    <input type="text" class="form-control col-3 noOf d-none" id="noOf" name="noOf" onmousedown="numericOnly(event)">
  </div>
  <div class="form-group col-md-2">
  <label for="txtNoOf" class="">&nbsp;</label>
   <button  class="btn btn-info d-none" id="btnGo" name="btnGo">Go</button>
  </div> -->
<br />
		 <br />
		<br/>
		
  <div style="margin-left:auto;margin-right:auto;">
  
  <br/>
		<br />
  
  <table class="table table-bordered table-striped table-highlight w-auto">
  
  <thead >
	  <tr>
	  	<th class="text-center" colspan="2">Depth of Ground Water  (in meter) </th>
	 	<th class="text-center" colspan="8">Water Quality (except pH are in mg/l)</th>
	 </tr>
	 
	  <tr>
	  	<th class="text-center">Pre-Monsoon </th>
	  	<th class="text-center">Post-Monsoon</th>
	  	
	  	<th class="text-center">pH </th>
	  	<th class="text-center">Total Alkalinity</th>
	  	<th class="text-center">Total Hardness</th>
	  	<th class="text-center">Calcium</th>
	  	<th class="text-center">Chloride</th>
	  	<th class="text-center">Nitrate</th>
	  	<th class="text-center">Total Dissolved</th>
	  	<th class="text-center">Fluoride</th>
	  </tr>
	  <tr>
	  	<td><input type="text" size="8" id="preMonsoon" name="preMonsoon" class="form-control input" onmousedown="decimalCheck(event);" maxlength="8" placeholder="Decimal Value"/></td>
	   	<td><input type="text" size="8" id="postMonsoon" name="postMonsoon" class="form-control input" onmousedown="decimalCheck(event);" maxlength="8" placeholder="Decimal Value"/></td>
	   	
	   	<td><input type="text" size="8" id="ph" name="ph" class="form-control input" onmousedown="decimalCheck(event);" maxlength="5"  placeholder="Decimal Value"/></td>
	   	<td><input type="text" size="8" id="alkalinity" name="alkalinity" class="form-control input" onkeypress="return isNumberKey(event)" onblur="alkalinity();"  maxlength="8" placeholder="Number Value"/></td>
	   	<td><input type="text" size="8" id="hardness" name="hardness" class="form-control input" onkeypress="return isNumberKey(event)"  onblur="hardness();" maxlength="5" placeholder="Number Value"/></td>
	   	<td><input type="text" size="8" id="calcium" name="calcium" class="form-control input" onkeypress="return isNumberKey(event)" onblur="calcium();" maxlength="5" placeholder="Number Value"/></td>
	   	<td><input type="text" size="8" id="chloride" name="chloride" class="form-control input" onkeypress="return isNumberKey(event)" onblur="chloride();"  maxlength="5" placeholder="Number Value"/></td>
	   	<td><input type="text" size="8" id="nitrate" name="nitrate" class="form-control input" onmousedown="decimalCheck(event);" maxlength="5"  placeholder="Decimal Value"/></td>
	   	<td><input type="text" size="8" id="dissolved" name="dissolved" class="form-control input" onkeypress="return isNumberKey(event)" onblur="dissolved();" maxlength="5" placeholder="Number Value"/></td>
	   	<td><input type="text" size="8" id="fluoride" name="fluoride" class="form-control input" onmousedown="decimalCheck(event);" maxlength="8"  placeholder="Decimal Value"/></td>
	   	
	  </tr>
  </thead>
  
  </table>
  <div class="form-group col text-center ">
  <br/><br/>
   
    <button  class="btn btn-info col-2" id="btnSave" name="btnSave">Save</button>
  </div>
  </div>
  
 
  
  
</form:form>
 
</div>
<c:if test="${dataList ne null}">
<div style="margin-left:auto;margin-right:auto; width:100%;">
  <br />
	
		
   <table class="table table-bordered table-striped table-highlight w-auto">
  
  <thead>
 
	  <tr>
	  <th rowspan="2" style="text-align:center; vertical-align: middle; width:20%" > Project Name</th>
	 <!--  <th rowspan="2" style="text-align:center; vertical-align: middle;"> Period</th> -->
	  <th style="text-align:center" colspan="2">Depth of Ground Water (in meter)</th>
	  <th style="text-align:center" colspan="8">Water Quality (except pH are in mg/l)</th>
	   <th rowspan="2" style="text-align:center; vertical-align: middle;"> Action</th>
	  </tr>
	  <tr>
	    <th class="text-center">Pre-Monsoon </th>
	  	<th class="text-center">Post-Monsoon</th>
	  	
	  	<th class="text-center">pH </th>
	  	<th class="text-center">Total Alkalinity</th>
	  	<th class="text-center">Total Hardness</th>
	  	<th class="text-center">Calcium</th>
	  	<th class="text-center">Chloride</th>
	  	<th class="text-center">Nitrate</th>
	  	<th class="text-center">Total Dissolved</th>
	  	<th class="text-center">Fluoride</th>
	  </tr>
  </thead>
  
  <c:forEach items="${dataList}" var="dataV" varStatus="count">
					<tr>
						<td align="left"><c:out value='${dataV.proj_name}' /> </td>
						<%-- <c:if test="${dataV.finyear ne null}">
						<td align="center"><c:out value='${dataV.finyear}' /> </td>
						</c:if>
						<c:if test="${dataV.finyear eq null}">
						<td align="center">Base line survey </td>
						</c:if> --%>
						<td align="center"><c:out value='${dataV.depth_premonsoon}' /> </td>
						<td align="center"><c:out value='${dataV.depth_postmonsoon}' /> </td>
						<td align="center"><c:out value='${dataV.ph}' /> </td>
						<td align="center"><c:out value='${dataV.alkalinity}' /> </td>
						<td align="center"><c:out value='${dataV.hardness}' /> </td>
						<td align="center"><c:out value='${dataV.calcium}' /> </td>
						<td align="center"><c:out value='${dataV.chloride}' /> </td>
						<td align="center"><c:out value='${dataV.nitrate}' /> </td>
						<td align="center"><c:out value='${dataV.dissolved_solid}' /> </td>
						<td align="center"><c:out value='${dataV.fluoride}' /> </td>
						<td>
						<c:if test="${dataV.status eq 'C'}">
							<%-- <input type="button"  value="Delete" name="delete" align="middle" disabled="disabled"
								onclick="javascript:do_Delete('${dataV.groundwater_id}','${dataV.groundwater_detail_id}','${dataV.proj_id}');" />
								&nbsp;&nbsp; --%>
							<input type="button"  value="Data Locked" name="LockOn" align="middle" disabled="disabled"
								onclick="javascript:do_Lock('${dataV.groundwater_id}','${dataV.groundwater_detail_id}','${dataV.proj_id}');" />	
						</c:if>
						<c:if test="${dataV.status ne 'C'}">
							<input type="button"  value="Delete" name="delete" align="middle" 
								onclick="javascript:do_Delete('${dataV.groundwater_id}','${dataV.groundwater_detail_id}','${dataV.proj_id}');" />
								&nbsp;&nbsp; <br/>
							<c:if test="${dataV.depth_premonsoon ne null && dataV.depth_postmonsoon ne null}">	
							<input type="button"  value="Complete" name="LockOn" align="middle" 
								onclick="javascript:do_Lock('${dataV.groundwater_id}','${dataV.groundwater_detail_id}','${dataV.proj_id}');" />	
							</c:if> 
						</c:if>
						</td>
						
						
  
  	</tr>
  	</c:forEach>	
  </table>
  
  </div>
</c:if>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/groundwatertableBasel.js" />'></script>
<script type="text/javascript">

function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}


function do_Delete(gwtid, gwtdtid, projid) 
{
	document.getElementById("gwtid").value=gwtid;
	document.getElementById("gwtdtlid").value=gwtdtid;
	document.getElementById("projid").value=projid;
	if(confirm("Do you want to delete ?"))
	{
			document.gwtform.action="deleteGWTdraftBasel";
			document.gwtform.method="post";
			document.gwtform.submit();
	}
}

function do_Lock(gwtid, gwtdtid, projid) 
{
	document.getElementById("gwtid").value=gwtid;
	document.getElementById("gwtdtlid").value=gwtdtid;
	document.getElementById("projid").value=projid;
	if(confirm("Do you want to complete/Lock data ?"))
	{
			document.gwtform.action="completeGWTDraftDataBasel";
			document.gwtform.method="post";
			document.gwtform.submit();
	}
}

function numberValidation() {
    event.preventDefault();
    let n = document.getElementById("alkalinity").value;

    if (isNaN(n)) {
       alert("Please enter Numeric value");
       document.getElementById("alkalinity").value="";
        return false;
    } 
}

function alkalinity()
{
   var numb = /^[0-9]+$/;
   if(!(document.getElementById("alkalinity").value.match(numb)))
   {
	   alert("Please enter Numeric value");
       document.getElementById("alkalinity").value="";
       return false;
   }
} 
function hardness()
{
   var numb = /^[0-9]+$/;
   if(!(document.getElementById("hardness").value.match(numb)))
   {
	   alert("Please enter Numeric value");
       document.getElementById("hardness").value="";
       return false;
   }
}
function calcium()
{
   var numb = /^[0-9]+$/;
   if(!(document.getElementById("calcium").value.match(numb)))
   {
	   alert("Please enter Numeric value");
       document.getElementById("calcium").value="";
       return false;
   }
}
function chloride()
{
	var numb = /^[0-9]+$/;
	if(!(document.getElementById("chloride").value.match(numb)))
	{
		alert("Please enter Numeric value");
	    document.getElementById("chloride").value="";
	    return false;
	}
}
function dissolved()
{
	var numb = /^[0-9]+$/;
	if(!(document.getElementById("dissolved").value.match(numb)))
	{
		alert("Please enter Numeric value");
		document.getElementById("dissolved").value="";
		return false;
	}
}



</script>
</body>
</html>