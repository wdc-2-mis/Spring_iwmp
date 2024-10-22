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

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
	var year = $('#year').val();
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
	if(year==='')
	{
		alert('Please select year ');
		$('#year').focus();
		e.preventDefault();
	}
	else{
		
		document.unfreezeworkstatus.action="getUnfreezeWorkStatusData";
		document.unfreezeworkstatus.method="post";
		document.unfreezeworkstatus.submit();
	}
	return false;
}

function selects(ele) {
    var checkboxes = document.getElementsByName('workcode');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

	function completebsl() 
	{ 
		if (confirm("Do you want to Unfreeze ?") == true) 
		{
				document.unfreezeworkstatus.action="unfreezeWorkStatusComplete";
				document.unfreezeworkstatus.method="post";
				document.unfreezeworkstatus.submit();
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
<div class="col" style="text-align:center;"><h5>Unfreeze Work Status</h5></div>
 <form:form autocomplete="off" name="unfreezeworkstatus" id="unfreezeworkstatus"  action="unfreezeWorkStatusData" method="get">
 		<input type="hidden" name="projid" id="projid" value="" />
      <table >
        <tr>
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();" required="required">
              		<option value="">--Select State--</option>
              		
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
          
           <td class="label">Project &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="project" id="project" required="required" onchange="this.form.submit();">
              <option value="0">--All--</option>
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
           <td class="label">Financial Year &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="year" id="year" required="required" >
              <option value="0">--All--</option>
              	 <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       								  
       							</c:if>	
       							<c:if test="${lists.finYrCd ne year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
								</c:forEach>
						</c:if>  
              </select>
          </td>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>

 
        <table class="table">
          <tr>
            <td>
            	<table id="tblReport" class="table">
            	<thead>
              		<tr>	<th class="displ" align="center">SelectAll<input type="checkbox"   onchange="selects(this);"/>&nbsp;&nbsp; S.No.</th>
              				<c:if test="${project eq 0}">
              				<th class="displ" align="center">Project Name</th>
              				</c:if>
              				<th class="displ" align="center">Work Id</th>
              				<th class="displ" align="center">Head Name</th>
              			 	<th class="displ" align="center">Activity Name</th>
              			 	<th class="displ" align="center">Block Name</th>
              			 	<th class="displ" align="center">Village Name</th>
              			 	<th class="displ" align="center">Convergence</th>
              			 	<th class="displ" align="center">Start Date</th>
              			 	<th class="displ" align="center">Complete Date</th>
              			 	<th class="displ" align="center">Status</th>
               		</tr>
               		</thead>
               		<tbody>
               		
                	<c:if test="${projlistUnfreezeBasel ne null}"> 
                	<c:forEach var="list" items="${projlistUnfreezeBasel}" varStatus="status">
	                	<tr>
	                		<td align="left"><input type="checkbox" id="workcode" name="workcode" value='${list.assetid}' > &nbsp;&nbsp; <c:out value="${status.count}" /></td>
	                		<c:if test="${project eq 0}">
	                		<td>
	                		<c:if test="${distname ne list.proj_name}">
			           						 <c:out value='${list.proj_name}' />
			           						<c:set value="${list.proj_name}" var="distname"/>
			           		</c:if>
	                		</td>
	                		</c:if>
	                		<td> <c:out value='${list.assetid}' /></td>
						    <td> <c:out value='${list.head_desc}' /> </td>
						    <td> <c:out value='${list.activity_desc}' /> </td>
						    <td> <c:out value='${list.block}' /> </td>
						    <td> <c:out value='${list.villagename}' /> </td>
						    <td> <c:out value='${list.convergence}' /> </td>
						    <td> <c:out value='${list.startdate}' /> </td>
						    <td> <c:out value='${list.completiondate}' /> </td>
						    <td> <c:out value='${list.status}' /> </td>
						    
	                	</tr>
                	</c:forEach>
                	<tr>
                	<td></td>
                	<td>
                	 <input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' /> 
                	 </td>
                	 </tr>
                	</c:if>
                	</tbody>
               		
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        
         </form:form>
    <br>
    
   
  <c:if test="${projlistUnfreezeBaselSize==0 }">
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