<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/unfreezeProjectLocation.js" />'></script>
<script type="text/javascript">

function showReport(e){
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
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
	else{
		
		document.unfreezeprojectlocation.action="getUnfreezeProjectLocation";
		document.unfreezeprojectlocation.method="post";
		document.unfreezeprojectlocation.submit();
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
<div class="col" style="text-align:center;"><h5>Unfreeze Project Location</h5></div>
 <form:form autocomplete="off" name="unfreezeprojectlocation" id="unfreezeprojectlocation"  action="unfreezeProjectLocation" method="get">
 		<input type="hidden" name="projid" id="projid" value="" />
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
          
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>
 </form:form>
 
        <table class="table">
          <tr>
            <td>
            	<table id="tblReport" class="table">
            	<thead>
              		<tr>	<th class="displ" align="center">Sl. No.</th>
              			 	<th class="displ" align="center" style="width:25%">Project Name</th>
              			 	<th class="displ" align="center">Project Location Exist</th>
              			 	<th class="displ" align="center">Permanent Work-Id Exist</th>
		              	    <th class="displ" align="center">Baseline Survey Data</th>
		              	    <th class="displ" align="center">Delete Baseline</th>
		              	    <th class="displ" align="center">Temporary Work-Id Exist</th>
		              	    <th class="displ" align="center">Delete Temporary Work-Id</th>
		              	    <th class="displ" align="center">Unfreeze</th>
               		</tr>
               		</thead>
               		<tbody>
               		
                	<c:if test="${projlistUnfreezeBasel ne null}"> 
                	<c:forEach var="list" items="${projlistUnfreezeBasel}" varStatus="status">
	                	<tr>
	                		<td align="center"> 	
		           						<c:out value="${status.count}"></c:out>
		           			</td>
	                	    <td> 	
		           						<c:out value="${list.proj_name}"></c:out>
		           			</td>
		           			<td align="center"> 	
		           						<c:out value="${list.location_status}"></c:out>
		           			</td>
		           			<td align="center"> 	
		           						<c:out value="${list.asset_status}"></c:out>
		           			</td>
		           			<td align="center"> 	
		           						<c:out value="${list.blsstatus}"></c:out>
		           			</td>
		           			<c:if test="${list.blsstatus eq 'Yes'}">
						    	<td align="center"> 
	                	    		<input type="button" id="butt${status.count}" value="Delete" name="delete"  
										onclick="javascript:bls_Delete('${status.count}','${list.proj_id}');" />
						    	</td>
						    </c:if>
						    <c:if test="${list.blsstatus ne 'Yes'}">
						    	<td align="center"> 
	                	    		<input type="button" disabled="disabled" value="Delete" name="del${status.count}" />
						    	</td>
						    </c:if>	
						    
						    <td align="center"> 	
		           						<c:out value="${list.asset_temp_status}"></c:out>
		           			</td>
		           			<c:if test="${list.asset_temp_status eq 'Yes'}">
						    	<td align="center"> 
	                	    		<input type="button" id="tempid${status.count}" value="Delete" name="tempid"  
										onclick="javascript:delete_temp('${status.count}','${list.proj_id}');" />
						    	</td>
						    </c:if>
						    <c:if test="${list.asset_temp_status ne 'Yes'}">
						    	<td align="center"> 
	                	    		<input type="button" disabled="disabled" value="Delete" name="tempid${status.count}" />
						    	</td>
						    </c:if>	
						    
						    <c:if test="${list.asset_status eq 'Yes'}">
						    	<c:if test="${list.location_status ne 'Yes'}">
							    	<td align="center"> 
		                	    		<input type="button" class="btn btn-info" disabled="disabled" id="unfreeze${status.count}" value="Unfreeze" name="unfreeze"  />
							    	</td>
						    	</c:if>
						    </c:if>
						    <c:if test="${list.asset_status ne 'Yes'}">
						    
						    	<c:if test="${list.location_status eq 'Yes'}">
							    	<td align="center"> 
		                	    		<input type="button" class="btn btn-info" value="Unfreeze" name="unfreeze" id="unfreeze"
		                	    		 onclick="javascript:do_Unfreeze('${status.count}','${list.proj_id}');"/>
							    	</td>
						    	</c:if>
						    	<c:if test="${list.location_status ne 'Yes'}">
							    	<td align="center"> 
		                	    		<input type="button" class="btn btn-info" disabled="disabled" id="unfreeze${status.count}" value="Unfreeze" name="unfreeze"  />
							    	</td>
						    	</c:if>
						    </c:if>	
						    
	                	</tr>
                	</c:forEach>
                	</c:if>
                	</tbody>
               		
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
    <br>
    
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
  <c:if test="${Listofstatetovill.size()<=0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
	
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