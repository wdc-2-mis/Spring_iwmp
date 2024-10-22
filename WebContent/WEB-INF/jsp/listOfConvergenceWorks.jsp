<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Work Convergence Details</u></h4> </div>
		<form name="mapProjectWithTranxId" id="mapProjectWithTranxId">
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
    		
    		</div>
    		
			<div class="form-row">
				<div class="form-group col">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="getTranxDetail" name="getTranxDetail"  value ="Get Details"/>
     			</div>
     		</div>
     		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of Convergence Works</u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxDetailTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th style="width:5%">Work Code</th>
								<th style="width:5%">Head Description</th>
								<th style="width:10%">Activity Description</th>
								<th style="width:2%">Status</th>
								<th style="width:5%">Achievement</th>
								<th style="width:2%">Unit</th>
								<th style="width:10%">Convergence</th>
								<th style="width:10%">Convergence With Scheme Code</th>
								<th style="width:15%">Expenditure Under WDC-PMKSY2.0(in Rs.)</th>
								<th style="width:20%">Expenditure Under Converged Scheme(in Rs.)</th>
								
							</tr>

						</thead>
						<tbody id ="tbodyTranxDetails">
							
						</tbody>
		</table>
		</div>
		</div>
		<div class="form-row">
				<div class="form-group col">
     				<input type="button" class="d-none btn btn-info" id="saveAsDraft" name="saveAsDraft" value ="Save As Draft"/>
     			</div>
     		</div>
     	<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of Final Convergence Works </u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxCompleteDetailTable">
						<thead class ="theadcomplist" id = "theadcomplist">
							<tr>
								<th class="text-center">Work Code &nbsp;<input type="checkbox" id ="checkAllTranxId"  onchange="selectAll(this);"/></th>
								<th>Head Description</th>
								<th>Activity Description</th>
								<th>Status</th>
								<th>Achievement</th>
								<th>Unit</th>
								<th>Convergence</th>
								<th>Convergence With Scheme Code</th>
								<th>Expenditure Under WDC-PMKSY2.0(in Rs.)</th>
								<th>Expenditure Under Converged Scheme(in Rs.)</th>
								<th>Action</th>
							</tr>

						</thead>
						<tbody id ="tbodyCompleteTranxDetails">
							
						</tbody>
		</table>
		</div>
		</div>
		<div class="form-row">
     		<div class="form-group">
     	 	<label for="btnGetActivity"> &nbsp;</label>
     		<input type="button" class="d-none btn btn-info" id="complete" name="complete" value='Complete/Locked the data of selected Transactions' /> 
     		</div>
  		</div>
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<script src='<c:url value="/resources/js/listOfConvergence.js" />'></script>
</body>
</html>