<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>PFMS Mapping With Project</u></h4> </div>
		<form name="mapProjectWithTranxId" id="mapProjectWithTranxId">
			<lable class="message badge badge-danger error"></lable>
			<hr/>
			<div class="row">
			<div class="form-group col-3">
			<c:if test="${userType== 'SL' }">
				<label for="srchMaster">State Name: <c:out value="${stateName}"></c:out></label>
				<input type ="hidden" class="form-control srchMaster" id="srchMaster" name="srchMaster" value ="5"/>
			</c:if>
			<c:if test="${userType== 'DI' }">
				<label for="srchMaster">District Name: <c:out value="${distName}"></c:out></label>
				<input type ="hidden" class="form-control srchMaster" id="srchMaster" name="srchMaster" value ="4"/>
			</c:if>
			<c:if test="${userType== 'PI' }">
      			<label for="srchMaster">Search by Block/Gram/Village:</label>
      			<span class="srchMasterError"></span>
      			<select class="form-control srchMaster" id="srchMaster" name="srchMaster" >
    				<option value="">--Select--</option>
					<option value="3">Block</option>
					<option value="2">Gram Panchayat</option>
					<option value="1">Village</option>
    			</select>
    		</c:if>
    		</div>
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
     <h5 class="text-center font-weight-bold"><u>List of Transactions </u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxDetailTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th class="text-center">Transaction Id &nbsp;<input type="checkbox" id ="checkAllTranx"  onchange="selects(this);"/></th>
								<th>Transaction Date</th>
								<th>Component Code</th>
								<th>Component Name</th>
								<th>Invoice No.</th>
								<th>Total Amount</th>
								<th>Project Name</th>
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
     <h5 class="text-center font-weight-bold"><u>List of Final Transactions </u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxCompleteDetailTable">
						<thead class ="theadcomplist" id = "theadcomplist">
							<tr>
								<th class="text-center">Transaction Id &nbsp;<input type="checkbox" id ="checkAllTranxId"  onchange="selectAll(this);"/></th>
								<th>Transaction Date</th>
								<th>Component Code</th>
								<th>Component Name</th>
								<th>Invoice No.</th>
								<th>Total Amount</th>
								<th>Project Name</th>
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
	<script src='<c:url value="/resources/js/pfmsMappingWithProject.js" />'></script>
</body>
</html>