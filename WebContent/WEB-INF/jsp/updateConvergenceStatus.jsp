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
    		<div class="form-group col-3">
      			<label for="project">Projects: </label>
      			<span class="projectError"></span>
      			<select class="form-control project" id="project" name="project" >
    				<option value="">--Select--</option>
    				<c:forEach items="${projectList}" var="project"> 
					<option value="<c:out value="${project.key}"/>"><c:out value="${project.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="head">Head: </label>
      			<span class="headError"></span>
      			<select class="form-control head" id="head" name="head" >
    				<option value="0">--All--</option>
    				<c:forEach items="${headList}" var="head"> 
					<option value="<c:out value="${head.key}"/>"><c:out value="${head.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Activity: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="activity" name="activity" >
    				<option value="0">--All--</option>
    			</select>
    		</div>
    		
    		</div>
    		
			<div class="form-row">
				<div class="form-group col">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="getConvergenceDetail" name="getConvergenceDetail"  value ="Get Details"/>
     			</div>
     		</div>
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
	<script src='<c:url value="/resources/js/updateConvergenceStatus.js" />'></script>
</body>
</html>