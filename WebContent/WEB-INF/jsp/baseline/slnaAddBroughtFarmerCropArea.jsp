<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
<style>
.pendingFarmerTable {
    width: 100%;
    table-layout: fixed;   /* KEY POINT */
    font-size: 13px;
}

.pendingFarmerTable th,
.pendingFarmerTable td {
    padding: 8px;
    text-align: center;
    vertical-align: middle;
    word-wrap: break-word;
    white-space: normal;
}

.pendingFarmerTable th {
    font-weight: 600;
}

.pendingFarmerTable textarea {
    width: 100%;
    min-height: 50px;
    resize: vertical;
}



</style>
</head>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">


<ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="listOfAddFarmer" data-toggle="tab" href="#listofaddfarmer">Pending List Of OOMF Additional Achievement Details</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" id="viewAddFarmerTab" data-toggle="tab" href="#viewaddFarmer">List of Completed OOMF Additional Achievement Details</a>
    </li>
    
  </ul>
    
  <div class="tab-content">
    <div id="listofaddfarmer" class="tab-pane fade show active"><br>
      <h3>OOMF Additional Achievement Details (Pending)</h3>
     <form name="createHeadActivity" id="createHeadActivity">
     <lable class="message badge badge-danger error"></lable>
      <hr/>
      <div class="form-row">
      
   <div class="form-group col-md-3">
			<label for="project" class=""><b>District : &nbsp; </b></label> 
			<select class="form-control col district" id="district" name="district">
				<option value="">--Select District--</option>
				<c:if test="${not empty distList}">
				<c:forEach items="${distList}" var="lists">
				<c:if test="${lists.key eq district}">
					<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out
							value="${lists.value}" /></option>
							</c:if>
							<c:if test="${lists.key ne district}">
							<option value="<c:out value="${lists.key}"/>"><c:out
									value="${lists.value}" /></option>
						</c:if>
				</c:forEach>
				</c:if>
			</select>
		</div>
		<div class="form-group col-5">
      <label for="project"><b>Projects: </b></label>
    <select class="form-control project" id="pendingProject" name="pendingProject" >
    <option value="">--Select Projects--</option>
     <c:forEach items="${pendingProjectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
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
      
     <div class="form-group col-1">
      <label for="project"> &nbsp;</label>
     <button class="btn btn-info" id="getPendingAsset" name="getPendingAsset" >Get Pending Crop</button>
     </div>
</div>

<div class="form-row">
     <div class="form-group col-12">
     <div class="table-responsive mt-3">
  <table class="pendingFarmerTable" id="pendingFarmerTable" name="pendingFarmerTable" style="width:100%; font-size:14px;">
     <thead><tr><th><input type="checkbox" id="chkSelectAll" name="chkSelectAll" />Select All</th><th>Project Name</th><th>Financial Year</th><th>Achievement Type</th><th>Additional area brought under diversified crops/change in cropping system (in ha.)</th><th>Area brought from no crop/single crop to single/multiple crop (in ha.)</th><th>Increase in Gross Cropped Area (in ha.)</th><th style="width:17%">Remarks</th></tr></thead>
     <tbody id="farmerTableBody"> </tbody>
</table>


</div>
<div class="row mt-4" id="actionButtons" style="display:none;">
    <div class="col-12 d-flex justify-content-center gap-3">
        <button type="button" class="btn btn-success px-4" id="btnApprove">
            Approve
        </button>

        <button type="button" class="btn btn-danger px-4" id="btnReject">
            Reject
        </button>
    </div>
</div>
</div>


</div>
</form>
</div>

    <div id="viewaddFarmer" class="tab-pane fade"><br>
      <h3>Completed OOMF Additional Achievement Details</h3>
      
      <div class="table-responsive mt-3">
  <table id="completeRecords" class="table table-bordered table-striped" style="width:100%; font-size:14px;">
    <thead>
        <tr align="center">
            <th>S.No.</th>
            <th>Project Name</th>
            <th>Financial Year</th>
            <th>Achievement Type</th>
            <th>Period</th>
            <th>Additional area brought under diversified crops/change in cropping system</th>
            <th>Area brought from no crop/single crop to single/multiple crop</th>
            <th>Increase in Farmer Income</th>
            <th>Increase in Gross Cropped Area</th>
            <th>Increase in Pulses Area</th>
            <th>Increase in Oilseeds Area</th>
        </tr>
    </thead>
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
	               		<th align="center"><b> 10 </b></th>
	               		<th align="center"><b> 11 </b></th>
               		</tr><c:set var="proj" value="" />
               		<c:set var="fin" value="" />
						<c:forEach items="${completeSLNA}" var="dataV" varStatus="count">
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
								<td align="center"><c:out value='${dataV.pulses}' /></td>
								<td align="center"><c:out value='${dataV.oilseeds}' /></td>
							</tr>
						</c:forEach>
					
              </tbody>
</table>
  </div>
  </div>
  </div>
  
  
</div>
</div>
</div>
</div>
</div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/slnaAddBroughtList.js" />'></script>
</body>
</html>  