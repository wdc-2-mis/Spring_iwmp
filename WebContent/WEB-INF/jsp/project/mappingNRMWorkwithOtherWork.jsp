<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

/* function selectOutcome(){
	var project = $('#project').val();
	
	var financial = $('#year').val();
	if (document.getElementById("project").value =='') 
	{
		alert("Please select Project");
		document.getElementById("project").focus();
		return;
	}
	
	if (document.getElementById("year").value =='') 
	{
		alert("Please select Financial Year");
		document.getElementById("year").focus();
		return;
	}
	document.getElementById('createHeadActivity').action ="getmappingNRMWork";
	document.getElementById('createHeadActivity').method="POST";
	document.getElementById('createHeadActivity').submit();
}
 */

</script>









<div class ="card">

	<div class="table-responsive">

		<div class="col" style="text-align:center;"><h5></h5></div>
 		<div class="container-fluid">
			<div class="row">
				<div class="container mt-2">

					<ul class="nav nav-tabs">
					    <li class="nav-item">
					      <a class="nav-link active" id="createAssetIdOther" data-toggle="tab" href="#relateAssetIdOtherId">NRM Work-Id related with Other work-Id</a>
					    </li>
					    
					    <li class="nav-item">
					      <a class="nav-link" id="viewAssetCompleteOther" data-toggle="tab" href="#relateAssetIdOtherIdCompleted">List of NRM Work-Id related with Other work-Id Completed</a>
					    </li>
				    
				  	</ul>
 
  					<div class="tab-content">
    					<div id="relateAssetIdOtherId" class="container tab-pane active"><br>
						    <div class="col formheading" style="">
						      	<h3> NRM Work-Id related with EPAs, Livelihood and Production Work Details</h3>
						    </div> 
						    
     					<form name="createHeadActivity" id="createHeadActivity">
      						<hr/>
  							<div class="form-row">
  							<table border="1"> <tr>
								  <div class="form-group col-md-3">
								    	<label for="project">Projects: <span class="text-danger">*</span></label>
								    	<select class="form-control project" id="project" name="project" required>
								    			<option value="">--Select Project--</option>
								     			<c:forEach items="${projectList}" var="project">
															<option value="<c:out value="${project.key}"/>"> <c:out value="${project.value}" /></option>
												</c:forEach>
								    </select>
								  </div>
								  
								  <div class="form-group col-md-3">
								     	<label for="year">Financial Year: <span class="text-danger">*</span></label>
								     	<select class="form-control year" id="year" name="year" required>
								        	<option value="">--Select Year--</option>
								       
								    	</select>
								  </div>
								  <div class="form-group col-md-3">
								    	<label for="head">Head: <span class="text-danger">*</span><span class="text-danger error-head"></span></label>
								    	<select class="form-control head" id="head" name="head" required>
								      		<option value="">--Select Head--</option>
								       		<option value="0">--All--</option>
								     		<c:forEach items="${headList}" var="head">
												<option value="<c:out value="${head.key}"/>" <c:out value="${head.key== stCode ?'selected':'' }"/>>
														<c:out 	value="${head.value}" /></option>
											</c:forEach>
								    	</select>
								  </div>
  
								  <div class="form-group col-md-3">
								    	<label for="year">Activity: <span class="text-danger">*</span></label>
								    	<select class="form-control activity" id="activity" name="activity" required>
								      		<option value="">--Select Activity--</option>
								      		<option value="0">--All--</option> 
								    	</select>
								  </div>
								  </tr>
								  <tr>
								  <br/>
								  <div class="form-group col-md-12">
								     <h3 align="center" style="color: green">OR </h3>
								    	
								  </div>
								  </tr>
								  <br/>
								  <tr>
								  <div class="form-group col-md-12">
								    	<label for="nrm">Enter NRM Work-Id: </label>
								    	<input type="text" name="nrmwork" id="nrmwork"  onmousedown="numericOnly(event);" maxlength="8" />
								  </div>
  								</tr>
  								
  								<tr>
								  <br/>
								  <div class="form-group col-md-12">
								  
								    	
								  </div>
								  </tr>
								  <br/>
  								
  								<tr>
  								<br/>
								  <div class="form-group col-md-3">
								    	<button class="btn btn-info form-control" id="btnGetAsset" name="btnGetAsset" >Get Data</button>
								  </div>
								  </tr>
  							</table>
  							</div>
  						</form>
  						<br/>
  
   						<div >
    
     <table  id="assetWiseStatusId" name="assetWiseStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th><input type="checkbox" id="chkSelectAll" name="chkSelectAll" /> &nbsp;NRM Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Location of Work(District, Block, Village)</th>
     <th>Related with Other Head</th><th>Activity under selected Head</th><th>Work code under selected Activity with Location of Work (District, Block, Village )</th></tr></thead>
     <tbody id="tbodyassetWiseStatusId"></tbody>
     
     
     </table>
     					</div>
  
  
  <br>
  <br>
  
   <div >
    <div class="col formheading" style="">
      Draft list of NRM Work-Id related with EPAs, Livelihood and Production Work Details
     </div> 
     <br>
     <table  id="listofDraftData" name="listofDraftData" style="width:100%">
     <thead><tr><th>S No.</th><th><input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /> &nbsp; NRM Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Location of Work(District, Block, Village)</th>
     <th>Related with Other Head</th><th>Work Code under Mapping Head</th></tr></thead>
     <tbody id="listofDraftDatakd"></tbody>
     
     
     </table>
    
     </div>
  
   <br/>
    </div>
    
    
      <div id="relateAssetIdOtherIdCompleted" class="container tab-pane fade"><br>
	      <div class="col formheading" style="">
	      	<h4>	List of Completed NRM Work-Id related with EPAs, Livelihood and Production Work Details </h4>
	     </div> 
      	
      <br/>
     		<div class="form-row">
       			<div class="form-group col-md-3">
    				<label for="project">Projects: <span class="text-danger">*</span></label>
    				<select class="form-control project" id="projectpi" name="projectpi" >
    					<option value="">--Select Project--</option>
	     				<c:forEach items="${projectList}" var="project">
								<option value="<c:out value="${project.key}"/>"> <c:out value="${project.value}" /></option>
						</c:forEach>
    				</select>
  				</div>
     
			    <div class="form-group col-md-3">
			    	<label for="year">Head: <span class="text-danger">*</span></label>
			    	<select class="form-control headother" id="headother" name="headother">
			     		<option value="">--Select Head--</option>
			      		<option value="e">Entry Point Activity (EPAs)</option>
			       		<option value="l">Livelihood Activities for the Asst-less Person</option>
			       		<option value="p">Production System</option>
			    	</select>
			  </div>
      
		     <div class="form-group col-1">
		      	<label for="project"> &nbsp;</label>
		     	<button class="btn btn-info" id="getCompleteAsset" name="getCompleteAsset" >Get Approved Work</button>
		     </div>
   
		     <div class="form-group col-12 confirmation">
		    
		     	<table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
		     	<thead><tr><th>S.No.</th><th>NRM Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Location of Work( Block, Gram Panchayat, Village)</th><th class="text-center">Name of Activity Selected Head</th><th>Work-Id under Selected Head</th></tr></thead>
		    	<tbody id="tbodyfinalListActivityAssetId"></tbody> 
		     </table>
		     </div>
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
<script src='<c:url value="/resources/js/mappingNRMWorkwithOtherWork.js" />'></script>
</body>
</html>