<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Update Convergence Status Of NRM Works</u></h4> </div>
		<form name="updateConvergence" id="updateConvergence">
			<lable class="message badge badge-danger error"></lable>
			<hr/>
			<div class="row">
			<div class="form-group col-2">
			<c:if test="${userType== 'SL' }"><br/>
				State Name: <c:out value="${stateName}"></c:out>
			</c:if>
			</div>
    		<div class="form-group col-3">
      			<label for="district">District: </label>
      			<span class="projectError"></span>
      			<select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Block: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block" name="block" >
    				<option value="">--Select Block--</option>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Gram Panchayat Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="grampan" name="grampan" >
    				<option value="">--Select Gram Panchayat Name--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    			<label for="activity">Village Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="village" name="village" >
    				<option value="">--Select Village Name--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    			<label for="activity">Location(Nearby/Milestone) </label>
      			<input type="text" name="location" id="location"  />
    		</div>
    		
    		</div>
    		
			<!-- <div class="form-row">
				<div class="form-group col">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="getConvergenceDetail" name="getConvergenceDetail"  value ="Get Details"/>
     			</div>
     		</div> -->
     		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of Work Code with Convergence Status</u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="convergenceTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th style="width:2%"><input type="checkbox" id ="checkAllTranxId"  onchange="selectAll(this);"/></th>
								<th style="width:5%">Work Code &nbsp;</th>
								<th style="width:5%"> Block Name</th>
								<th style="width:10%">Gram Panchayat Name</th>
								<th style="width:2%">Village Name</th>
								<th style="width:5%">Convergence</th>
							</tr>

						</thead>
						<tbody id ="tbodyConvergence">
							
						</tbody>
		</table>
		</div>
		</div>
		<div class="form-row">
				<div class="form-group col">
     				<input type="button" class="d-none btn btn-info" id="update" name="update" value ="update"/>
     			</div>
     		</div>
     	
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<script src='<c:url value="/resources/js/WatershedYatraVillage.js" />'></script>
</body>
</html>