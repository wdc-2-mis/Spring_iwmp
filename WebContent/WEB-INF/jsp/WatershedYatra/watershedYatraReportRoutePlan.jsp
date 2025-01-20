<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<%-- <script src='<c:url value="/resources/js/unfreezeProjectLocation.js" />'></script> --%>
<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var block = $('#block').val();
	var gp = $('#grampan').val();
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
	if(block==='')
	{
		alert('Please select block ');
		$('#block').focus();
		e.preventDefault();
	}
	else{
		
		document.routePlan.action="getRoutePlanReportData";
		document.routePlan.method="post";
		document.routePlan.submit();
	}
	return false;
}



</script>

</head>
<div class ="card">

<div class="table-responsive">
<br/>
<div class="col" style="text-align:center;"><h5>Route Plan for Van Traveling/Watershed Mahotsawa</h5></div>
 <form:form autocomplete="off" name="routePlan" id="routePlan"  action="getWatershedYatraReport" method="get">
 		<br/>
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
              		<option value="0">--All District--</option>
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
          
           <td class="label">Block &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="block" id="block" required="required" onchange="this.form.submit();">
              <option value="0">--All Block--</option>
              	<c:if test="${not empty blockList}">
               					<c:forEach items="${blockList}" var="lists">
               						<c:if test="${lists.key eq blkd}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne blkd}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
           <td class="label">Gram Panchyat &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="grampan" id="grampan" required="required" >
              <option value="0">--All Gram Panchyat--</option>
              	<c:if test="${not empty gpList}">
               					<c:forEach items="${gpList}" var="lists">
               						<c:if test="${lists.key eq grampn}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne grampn}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>

 <br/>
        <table class="table">
          <tr>
            <td>
            	<table id="tblReport" class="table">
            	<thead>
              		<tr>	<th class="displ" align="center">Sl. No.</th>
              				<th class="displ" align="center">Date and Time</th>
              			 	<th class="displ" align="center">State Name</th>
              			 	<th class="displ" align="center">District Name</th>
              			 	<th class="displ" align="center">Block Name</th>
              			 	<th class="displ" align="center">Gram Panchyat Name</th>
              			 	<th class="displ" align="center">Village Name</th>
              			 	<th class="displ" align="center">Location (Nearby/Milestone)</th>
              			 	
               		</tr>
               		</thead>
               		<tbody>
               		<c:set var="statename" value="" />
               		<c:set var="dist" value="" />
					<c:set var="flagwis" value="" />
					<c:set var="ac" value="1" />
                	<c:if test="${routePlanList ne null}"> 
                	<c:forEach var="dataV" items="${routePlanList}" varStatus="status">
	                	<tr>
								<c:choose>
									<c:when test="${flagwis ne dataV.flagwise}">
										<c:set var="flagwis" value="${dataV.flagwise}" />
										<td><c:out value="${ac}" /></td>
										 <c:set var="ac" value="${ac+1}" /> 
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<c:if test="${dataV.date1 ne null }">
									<td> <c:out value="${dataV.date1}" /></td>
								</c:if>
								<c:if test="${dataV.date1 eq null }">
									<td> <c:out value="${dataV.date2}" /></td>
								</c:if>
								<c:choose>
									<c:when test="${statename ne dataV.stname}">
										<c:set var="statename" value="${dataV.stname}" />
										<td> <c:out value="${dataV.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${dist ne dataV.district}">
										<c:set var="dist" value="${dataV.district}" />
										<td> <c:out value="${dataV.district}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<%-- <td> <c:out value="${dataV.district}" /></td> --%>
								<td> <c:out value="${dataV.blockname}" /></td>
								<td> <c:out value="${dataV.gramname}" /></td>
								<td> <c:out value="${dataV.villagename}" /></td>
								
								<c:if test="${dataV.location1 ne null }">
									<td> <c:out value="${dataV.location1}" /></td>
								</c:if>
								<c:if test="${dataV.location1 eq null }">
									<td> <c:out value="${dataV.location2}" /></td>
								</c:if>
								
							</tr>
                	</c:forEach>
                	</c:if>
                	<c:if test="${routePlanListSize eq 0}">
							<tr>
								<td align="center" colspan="8" class="required" style="color:red;">Data Not Found</td>
							</tr>
					</c:if>
                	</tbody>
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