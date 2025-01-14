<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<style> 
input[type=text] {
  width: 200%;
  padding: 8px 12px;
  margin: 2px 0;
  box-sizing: border-box;
  border: 1px solid black;
  border-radius: 2px;
}

input[type=email] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid black;
  border-radius: 4px;
}


</style>


</head>
<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Route Plan for Van Traveling/Watershed Mahotsawa</u></h4> </div>
		<form name="updateConvergence" id="updateConvergence">
		<br/>
			<p style="font-size: 25px;"><b>Location- 01</b> </p>
			<hr/>
			
			<div class="form-row">
				<div class="form-group col-3">
					<c:if test="${userType== 'SL' }">
						<label for="state">	<b> State Name:</b> </label>
						<span class="projectError"></span> <br/>
						<c:out value="${stateName}"></c:out>
					</c:if>
				</div>
	    		<div class="form-group col-3">
	      			<label for="district"><b>District:</b> </label>
	      			<span class="projectError"></span>
	      			<select class="form-control district" id="district" name="district" required>
	    				<option value="">--Select District--</option>
	    				<c:forEach items="${distList}" var="dist"> 
						<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
						</c:forEach>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Block:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="block" name="block" required>
	    				<option value="">--Select Block--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Gram Panchayat Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="grampan" name="grampan" required>
	    				<option value="">--Select Gram Panchayat Name--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Village Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="village" name="village" required>
	    				<option value="">--Select Village Name--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	      		  	<label for="datetime"><b>Date and Time:</b> </label>
	       		 	<input type="datetime-local" name="datetime" id="datetime" class="form-control"  required/>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Location(Nearby/Milestone): </b></label>
	      			<input type="text" name="location" id="location"  required />
	    		</div>
    		
			</div>
    		<br/>
			
			<p style="font-size: 25px;"><b>Location- 02</b></p> 
			
			<hr/>
			<div class="form-row">
				<div class="form-group col-3">
					<c:if test="${userType== 'SL' }">
						<label for="state">	<b> State Name:</b> </label>
						<span class="projectError"></span> <br/>
						<c:out value="${stateName}"></c:out>
					</c:if>
				</div>
	    		<div class="form-group col-3">
	      			<label for="district"><b>District:</b> </label>
	      			<span class="projectError"></span>
	      			<select class="form-control district1" id="district1" name="district1" required>
	    				<option value="">--Select District--</option>
	    				<c:forEach items="${distList}" var="dist"> 
						<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
						</c:forEach>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Block:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="block1" name="block1" required>
	    				<option value="">--Select Block--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Gram Panchayat Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="grampan1" name="grampan1" required>
	    				<option value="">--Select Gram Panchayat Name--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Village Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="village1" name="village1" required>
	    				<option value="">--Select Village Name--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	      		  		<label for="datetime"><b>Date and Time: </b></label>
	       		 		<input type="datetime-local" name="datetime1" id="datetime1" class="form-control" required/>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Location(Nearby/Milestone):</b></label>
	      			<input type="text" name="location1" id="location1"  required/>
	    		</div>
		</div>
    	<br/>
		<div class="form-row">
			<div class="form-group col-8">
				<label for="btnGetDetails"> &nbsp;</label>
     			<input type="button" class="btn btn-info" id="btnSavekd" name="btnSavekd"  value ="Save"/>
     		</div>
     	</div> 
			
     <div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold"><u>List of Route Plan for Van Traveling/Watershed Mahotsawa</u></h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="convergenceTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th style="width:2%">S.No. &nbsp; <!-- <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /> --></th> 
								<th style="width:5%">State Name</th>
								<th style="width:5%">District Name</th>
								<th style="width:5%">Block Name</th>
								<th style="width:5%">Gram Panchyat Name</th>
								<th style="width:5%">Village Name</th>
								<th style="width:5%">Location (Nearby/Milestone)</th>
								<th style="width:5%">Date and Time </th>
								<!-- <th style="width:5%">Location2 (Nearby/Milestone)</th>
								<th style="width:5%">Date and Time Location2</th> -->
							</tr>
						</thead>
						
						<c:set var="proj" value="" />
						<c:forEach items="${draftList}" var="dataV" varStatus="count">
							<tr>
							 	<td><c:out value='${count.count}' /> <%-- &nbsp;<input type="checkbox" class="chkIndividualkd" id="${dataV.nodal_id}"  name="${dataV.nodal_id}" value="${dataV.nodal_id}"/> --%> </td>
								
								<c:choose>
									<c:when test="${proj ne dataV.stname}">
										<c:set var="proj" value="${dataV.stname}" />
										<td> <c:out value="${dataV.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<td> <c:out value="${dataV.district}" /></td>
								<td> <c:out value="${dataV.blockname}" /></td>
								<td> <c:out value="${dataV.gramname}" /></td>
								<td> <c:out value="${dataV.villagename}" /></td>
								
								<c:if test="${dataV.location1 ne null }">
									<td> <c:out value="${dataV.location1}" /></td>
								</c:if>
								<c:if test="${dataV.location1 eq null }">
									<td> <c:out value="${dataV.location2}" /></td>
								</c:if>
								
								<c:if test="${dataV.date1 ne null }">
									<td> <c:out value="${dataV.date1}" /></td>
								</c:if>
								<c:if test="${dataV.date1 eq null }">
									<td> <c:out value="${dataV.date2}" /></td>
								</c:if>
								
								<%-- <td> <c:out value="${dataV.location2}" /></td>
								<td> <c:out value="${dataV.date2}" /></td> --%>
							</tr>
						</c:forEach>
						<c:if test="${draftListSize eq 0}">
							<tr>
								<td align="center" colspan="10" class="required" style="color:red;">Data Not Found</td>
							</tr>
						</c:if>
		</table>
		</div>
		</div>
			<!-- <div class="form-row">
				<div class="form-group col">
     				<input type="button" class="d-none btn btn-info" id="update" name="update" value ="update"/>
     			</div>
     		</div> -->
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<script src='<c:url value="/resources/js/WatershedYatraVillage.js" />'></script>
	<script src='<c:url value="/resources/js/routeplanvantraveling.js" />'></script> 
</body>
</html>