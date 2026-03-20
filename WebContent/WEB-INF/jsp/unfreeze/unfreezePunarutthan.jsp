<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/unfreezePunarutthan.js" />'></script> 
<script type="text/javascript">

function showReport(e){
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
	$flagoff = $('input[name="punarutthan"]:checked').val();
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	if(project==='')
	{
		alert('Please select Project ');
		$('#project').focus();
		e.preventDefault();
	}
	if ($flagoff === '' || typeof $flagoff === 'undefined') {
		alert('Please select Punarutthan Planing or Implementation');
		$('input[name="punarutthan"]').first().focus();
		return false;
	}
	else{
		
		document.unfreezepunarutthan.action="getUnfreezePunarutthan";
		document.unfreezepunarutthan.method="post";
		document.unfreezepunarutthan.submit();
	}
	return false;
}

	function bls_Delete(count,  projid) 
	{
		document.getElementById("projid").value=projid;
		if (confirm(" Do you want to Unfreeze Baseline Survey Plot wise ?") == true) 
		{
			document.unfreezeprojectlocation.action="unfreezeBaselineSurveyPlotWise";
			document.unfreezeprojectlocation.method="post";
			document.unfreezeprojectlocation.submit();
		}
	}
		function delete_temp(count,  projid) 
		{
			document.getElementById("projid").value=projid;
			if (confirm("It will delete the Data form temp table.\n\n Do you want to continue ?") == true) 
			{
					document.unfreezeprojectlocation.action="deletePhysicalWorkIdTemp";
					document.unfreezeprojectlocation.method="post";
					document.unfreezeprojectlocation.submit();
			}
		}
		
		function do_Unfreeze(count,  projid) 
		{
			document.getElementById("projid").value=projid;
			if (confirm("Do you want to Project Location ?") == true) 
			{
					document.unfreezeprojectlocation.action="unfreezeProjectLocatin";
					document.unfreezeprojectlocation.method="post";
					document.unfreezeprojectlocation.submit();
			}
		}

</script>

</head>
<div class ="card">

<div class="table-responsive">
<c:if test="${messageUpload != null}">
	<script>
	    alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
<div class="col" style="text-align:center;"><h5 class="text-center font-weight-bold" style="text-decoration: underline;">Unfreeze Punarutthan</h5></div>
 <form:form autocomplete="off" name="unfreezepunarutthan" id="unfreezepunarutthan"  action="unfreezePunarutthan" method="get">
 		<input type="hidden" name="typeutthan" id="typeutthan" value="${punarutthant}" />
      <table >
        <tr>
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();" required="required">
              		<option value="">--Select--</option>
              		
                  	<c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
           <td class="label">District <span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="this.form.submit();" required="required">
              		<option value="">--Select District--</option>
                  	 <c:if test="${not empty districtList}">
               					<c:forEach items="${districtList}" var="lists">
               						<c:if test="${lists.key eq district}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne district}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 
              </select>
          </td>
          
           <td class="label">Project &nbsp;&nbsp;&nbsp;</td>
           <td>
              <select name="project" id="project" required="required">
              <option value="0">--All Project--</option>
              	<c:if test="${not empty ProjectList}">
               					<c:forEach items="${ProjectList}" var="lists">
               						<c:if test="${lists.key eq project}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne project}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
          <c:if test="${punarutthant== 'pln' }">
          <td class="label">
	          <input type="radio" id="pln" name="punarutthan" value="pln" checked="checked"> <label for="html">Planing</label> &nbsp;&nbsp;
	  		  <input type="radio" id="pln" name="punarutthan" value="imp"><label for="css">Implementation</label>
          </td>
          </c:if> 
          <c:if test="${punarutthant== 'imp' }">
          <td class="label">
	          <input type="radio" id="pln" name="punarutthan" value="pln"> <label for="html">Planing</label> &nbsp;&nbsp;
	  		  <input type="radio" id="pln" name="punarutthan" value="imp" checked="checked" ><label for="css">Implementation</label>
          </td>
          </c:if> 
          <c:if test="${empty punarutthant}">
          <td class="label">
	          <input type="radio" id="pln" name="punarutthan" value="pln"> <label for="html">Planing</label> &nbsp;&nbsp;
	  		  <input type="radio" id="pln" name="punarutthan" value="imp"><label for="css">Implementation</label>
          </td>
          </c:if> 
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>
 </form:form>
 
        <div class="form-row">
         <div class="form-group col">
         <hr/>
         <h5 class="text-center font-weight-bold" style="text-decoration: underline;">List of Watershed Punarutthan <c:if test="${punarutthant== 'pln' }">Planing </c:if> <c:if test="${punarutthant== 'imp' }">Implementation </c:if>Details</h5>
         <table class="table table-bordered table-striped table-highlight w-auto" id="prabhatpheriTable">
                        <thead class ="theadlist" id = "theadlist">
                            <tr>
                            	<!-- <th>Action</th> -->
                                <th>S.No.  &nbsp; <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /></th> 
                                <th>District Name</th>
                                <th>Project Name</th>
                                <th>Village Name</th>
                                <th>Structure Name</th>
                                <th>Maintenance work to do</th>
                                <th>Estimated Cost from WDF</th>
                                <th>Estimated Cost from VB G RAM G / MGNREGA</th>
                                <th>Estimated Cost from Other Source</th>
                                
                            </tr>
                          
                        </thead>
                        <c:set var="dist" value="" />
                        <c:set var="proj" value="" />
                        <c:forEach items="${UnfreezeUtthan}" var="data" varStatus="count">
 							<tr>
 								<%-- <td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.plan_id})"> Edit </button> --%>
								<c:if test="${punarutthant== 'pln' }">
								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.plan_id}"  name="${data.plan_id}" value="${data.plan_id}"/></td>
 								</c:if>
 								
 								<c:if test="${punarutthant== 'imp' }">
								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.implementation_id}"  name="${data.implementation_id}" value="${data.implementation_id}"/></td>
 								</c:if>
 								
 								
   								<c:choose>
  									<c:when test="${dist ne data.distname}">
  										<c:set var="dist" value="${data.distname}" />
  										<td> <c:out value="${dist}" /></td>
  									</c:when>
  								<c:otherwise> 
  										<td></td>
 								</c:otherwise>
 								</c:choose> 
 								
 								<c:choose>
  									<c:when test="${proj ne data.proj_name}">
  										<c:set var="proj" value="${data.proj_name}" />
  										<td> <c:out value="${proj}" /></td>
  									</c:when>
  								<c:otherwise> 
  										<td></td>
 								</c:otherwise>
 								</c:choose> 
 								
								<%-- <td class="text-left"> <c:out value="${data.distname}" /></td>
 								<td class="text-left"> <c:out value="${data.proj_name}" /></td> --%>
 								<td class="text-right"> <c:out value="${data.villagename}" /></td>
								<td class="text-right"> <c:out value="${data.structurename}" /></td>
 								<td class="text-right"> <c:out value="${data.no_work}" /></td>
								<td class="text-right"> <c:out value="${data.wdf}" /></td>
 								<td class="text-right"> <c:out value="${data.mgnrega}" /></td>
 								<td class="text-right"> <c:out value="${data.other}" /></td>
 								
								
 								
							<%--	<td class="text-right">
									<a href="#" data-id="${data.plan_id}" class="showImage" data-toggle="modal" style ="color: blue;">Image</a> 
								</td> --%>
					</tr>
 						</c:forEach> 
 						<c:if test="${UnfreezeUtthanSize gt 0}">
 						<tr>
								<td> <input type="button" class="btn btn-info" id="unfreeze" name="unfreeze" value ="Unfreeze"/> </td>
								
							</tr>
						</c:if>	
						<c:if test="${UnfreezeUtthanSize eq 0}">
							<tr>
								<td align="center" colspan="10" class="required" style="color:red;">Data Not Found</td>
							</tr>
						</c:if>
        </table>
        
        
        </div>
        </div>
   
    
    <%-- <c:if test="${Listofstatetovill.size()>0 }">
     <c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
	    <table>
	    <tr>
	    <td>
	   		<b>Note: &nbsp; <input type="checkbox" id="villname" name="villname" checked="checked" disabled="disabled" /> show InActive  </b>
	    </td>
	    </tr>
	    </table>  
	    </c:if>
	</c:if> --%>
  
	</div>
	</div>


<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

</script>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>