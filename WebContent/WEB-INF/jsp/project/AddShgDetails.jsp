<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">


<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Add/View SHG Bank Account Details</u></h4> </div>
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
    		
    		<div class="form-group col-3">
      			<label for="grp">Head: </label>
      			<span class="projectErrors"></span>
      			<select class="form-control project" id="grp" name="grp" >
    				<option value="">--Select--</option>
    				
							<option value="1">Newly Created SHG</option>
							<option value="2">Existing SHG</option>
    			</select>
    		</div>
    		<div class="form-row">
    		<div class="form-group col-3">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="getShgDetail" name="getShgDetail"  value ="Get Details"/>
     			</div>
    		
    		</div>
<!-- 			<div class="form-row"> -->
				
     		</div>
     		<div class="form-row">
     

<div class="form-group col">
    <hr/>
    <h5 class="text-left font-weight-bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>SHG Bank Account Details</u></h5>
    <table class="table table-bordered table-striped table-highlight w-auto" id="shgDetailTable" style="width:90%">
        <thead class="theadlist" id="theadlist">
            <tr>
                <!-- <th class="text-center">Select &nbsp;<input type="checkbox" id ="checkAllTranxId"  onchange="selectAll(this);"/></th> -->
<!--                 <th style="width:0.001%">&nbsp;SHG Name</th> -->
<!--                 <th style="width:0.00021%">Account Details</th> -->
<!--                 <th style="width:0.00019%">IFSC Code</th> -->
				<th style="width:5%"><input type="checkbox" id ="checkAllTrans"  onchange="selectAll1(this);"/>&nbsp;Select All</th>
				<th style="width:3%">S.No.</th>
				<th style="width:30%"> &nbsp;SHG Name</th>
				<th style="width:35%">Department/ Scheme</th>
				<th style="width:25%">Date of Registration</th>
                <th style="width:30%">Bank Account Number</th>
                <th style="width:12%">IFSC</th>
            </tr>
        </thead>
        <tbody id="tbodyShgDetails">
            <!-- Your table body content goes here -->
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
     <h5 class="text-left font-weight-bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>Draft List of SHG Bank Account Details </u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxCompleteDetailTable" style="width:90%">
						<thead class ="theadcomplist" id = "theadcomplist">
							<tr>
								<th style="width:5%"><input type="checkbox" id="checkAllTranxId" onchange="selectAll(this);"/>Select All</th>
								<th style="width:3%">S.No.</th>
								<th style="width:30%">SHG Name</th>
								<th style="width:35%">Department/ Scheme</th>
								<th style="width:25%">Date of Registration</th>
								<th style="width:30%">Bank Account Number</th>
								<th style="width:20%">IFSC</th>
								<th style="width:10%">Action</th>
							</tr>

						</thead>
						<tbody id ="tbodyCompleteSHGDetails">
							
						</tbody>
		</table>
		</div>
		</div>
		<div class="form-row">
     		<div class="form-group">
     	 	<label for="btnGetActivity"> &nbsp;</label>
     		<input type="button" class="d-none btn btn-info" id="complete" name="complete" value='Complete' /> 
     		</div>
  		</div>
  		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-left font-weight-bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>Completed List of SHG Bank Account Details </u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxCompleteDetailTable" style="width:90%">
						<thead class ="theadcomplist" id = "theadcomplist">
							<tr>
								<th style="width:3%">S.No.</th>
								<th style="width:25%">SHG Name</th>
								<th style="width:30%">Department/ Scheme</th>
								<th style="width:25%">Date of Registration</th>
								<th style="width:30%">Bank Account Number</th>
								<th style="width:20%">IFSC</th>
<!-- 								<th style="width:10%">Action</th> -->
							</tr>

						</thead>
						<tbody id ="tbodyFinalCompleteSHGDetails">
							
						</tbody>
		</table>
		</div>
		</div>
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<script src='<c:url value="/resources/js/ShgDetail.js" />'></script>
</body>
</html>