   <%@include file="/WEB-INF/jspf/headerpfms.jspf"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
 -->
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/js/pfms/bootstrap-select.min.css"/>">

<script src='<c:url value="/resources/js/pfms/jquery.min.js" />'></script>
<script src='<c:url value="/resources/js/pfms/popper.min.js" />'></script>
<script src='<c:url value="/resources/js/pfms/bootstrap.min.js" />'></script>
<script src='<c:url value="/resources/js/pfms/bootstrap-select.min.js" />'></script>
<script src='<c:url value="/resources/js/pfms/jquery-ui.js" />'></script>
<script src='<c:url value="/resources/js/jquery.validate.min.js" />'></script> 


<head>

<style>
.counts {
  display: none
}
table td {
    border: 1px solid #e9ecef;
}
</style>


</head>
<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>PFMS Transaction Mapping With Work Id</u></h4> </div>
		<form name="mapProjectWithTranxId" id="mapProjectWithTranxId">
			<lable class="message badge badge-danger error"></lable>
			<hr/>
			<div class="row">
    		<div class="form-group col-3">
      			<label for="project">Projects: </label>
      			<span class="projectError"></span>
      			<select class="form-control project" id="project" name="project" >
    				<option value="">--Select Project--</option>
    				<c:forEach items="${projectList}" var="project"> 
					<option value="<c:out value="${project.key}"/>"><c:out value="${project.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		
    		<div class="form-group col-md-3">
    <label for="year">Financial Year: </label>
   <span class="yearError"></span>
    <select class="form-control year" id="year" name="year">
      <option value="">--Select Year--</option>
      <c:forEach var="list" items="${yearList}">
			<c:if test="${fyear eq list.key }">
				<option value="${list.key }" selected><c:out value="${list.value }" /></option>
			</c:if>
			<c:if test="${fyear ne list.key }">
				<option value="${list.key }"><c:out value="${list.value }" /></option>
			</c:if>
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
     		<div>
     <div>
     <p style="color:red; font-size:20px;">*If Work ID is not created then get Project Physical Plan filled by PIA user's.</p>
     <p style="color:red; font-size:20px;">*Add Expenditure values cannot be greater or less than the Total Amount of the Transaction's.</p>
     
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of Transactions </u></h5>
     <table class="table" id="tranxDetailTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th class="text-center">Transaction Id &nbsp;<!-- <input type="checkbox" id ="checkAllTranx"  onchange="selects(this);"/> --></th>
								<th>Transaction Date</th>
								<th>Component Code</th>
								<th>Component Name</th>
								<th>Invoice No.</th>
								<th>Total Amount</th>
								<th>Project Name</th>
								<th>Work Id</th>
								<th>Add Expenditure</th>
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
								<th>Work Id</th>
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
	<script src='<c:url value="/resources/js/pfmsMappingWithWorkId.js" />'></script>
</body>
</html>