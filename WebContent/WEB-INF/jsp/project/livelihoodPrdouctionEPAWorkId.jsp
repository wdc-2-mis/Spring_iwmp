<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5> </h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">
 
 <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="createAssetId" data-toggle="tab" href="#assetId">Create Work-Id</a>
    </li>
     <li class="nav-item">
      <a class="nav-link" id="viewforwardedAsset1" data-toggle="tab" href="#forwardedAsset"><c:if test="${forwardedAssetList.size() gt 0 }"><span class="badge badge-pill badge-warning forward"><strong><c:out value="${forwardedAssetList.size()}" /> </strong> </span></c:if>&nbsp;View Forwarded List of Work</a>
    </li>
 <%--    <li class="nav-item">
      <a class="nav-link" id="viewRejectedAsset" data-toggle="tab" href="#rejectedAsset"><c:if test="${rejectedAssetList.size() gt 0 }"><span class="badge badge-pill badge-danger reject"><strong><c:out value="${rejectedAssetList.size()}" /></strong> </span></c:if>&nbsp;View Rejected List of Work</a>
    </li> --%>
    <li class="nav-item">
      <a class="nav-link" id="viewAssetCreated1" data-toggle="tab" href="#assetCreated">List of Work already approved</a>
    </li>
    
  </ul>
 
  <!-- Tab panes -->
  <div class="tab-content">
    <div id="assetId" class="container tab-pane active"><br>
      <h3>Create Work-Id (EPAs/Livelihood Activities/ Production System )</h3>
     <form name="createHeadActivity" id="createHeadActivity">
     <lable class="message badge badge-danger error"></lable>
      <hr/>
  <div class="form-row">
  <div class="form-group col-md-4">
    <label for="project">Projects: </label>
    <select class="form-control project" id="project" name="project" >
    <option value="">--Select Project--</option>
	     <c:forEach items="${projectList}" var="project">
				<option value="<c:out value="${project.key}"/>"> <c:out value="${project.value}" /></option>
		 </c:forEach>
    </select>
  </div>
  <div class="form-group col-md-4">
    <label for="year">Head: </label>
    <select class="form-control head" id="scheme" name="scheme">
     	<option value="">--Select Head--</option>
      	<option value="epa">Entry Point Activity (EPAs)</option>
       	<option value="livelihood">Livelihood Activities</option>
       	<option value="production">Production System</option>
    </select>
  </div>
  <div class="form-group col-md-4">
    <label for="year">Activity: </label>
    <select class="form-control head" id="activity" name="activity">
     	<option value="">--Select Activity--</option>
      	
    </select>
  </div>
  </div>
 
     <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>List of Activities</u></h5>
     <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise" style="width:100%">
     <thead><tr><th class="text-center">Name of Activity</th><th class="text-center">No. of Activities</th><th class="text-center">Work Created</th><th>Action</th></tr></thead>
     <tbody id="listActivityHeadWiseTbody"></tbody>
     </table>
     
     </div>
     </div>
     
  <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>Activities Mapping With Block and Village</u></h5>
     <table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
     <thead><tr><th>S.No.</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th id="areaco" class="d-none">Area Covered (in Ha)</th><th>Land Identification</th><th>Action</th></tr></thead>
     <tbody id="tbodyListActivityAssetId"></tbody>
     </table>
     </div>
     </div>
     <div class="form-row">
   <div class="form-group col-md-6">
   <button class="btn btn-info d-none" id="draftSave" name="draftSave" >Save as Draft</button>
  </div>
  </div>
     <div class="form-row">
     <div class="form-group col-12 confirmation">
      <hr/>  
     <h5 class="text-center font-weight-bold"><u>Draft List of Activities With Work Id</u></h5>
     <table class="tempListActivityAssetId" id="tempListActivityAssetId" name="tempListActivityAssetId" style="width:100%">
     <thead><tr><th>Work-Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th id="areacov" class="d-none">Area Covered (in Ha)</th><th>Land Identification</th><th>Action</th></tr></thead>
     <tbody id="tbodyTempListActivityAssetId"></tbody>
     </table>
     </div>
  </div>
  <div class="form-row">
   <div class="col-md-2 d-inline-block">
   <button class="btn btn-info d-none" id="forwardToWCDC" name="finalSave" >Forward TO District</button>
  <!--  <button class="btn btn-info d-none" id="forwardToSLNA" name="finalSave" >Forward</button> -->
  </div>
   <div class="col-md-4 d-inline-block">
    <select class="form-control forward d-none" id="user" name="user"></select>
    </div> 
  </div>
   </form>
   <br/>
    </div>
    
   
   <div id="assetCreated" class="container tab-pane fade"><br>
      <h3>List of Work-Id already Created</h3>
      <hr/>
     <div class="form-row">
       <div class="form-group col-md-3">
    <label for="project">Projects: </label>
    <select class="form-control project" id="projectpi" name="projectpi" >
    <option value="">--Select Project--</option>
	     <c:forEach items="${projectList}" var="project">
				<option value="<c:out value="${project.key}"/>"> <c:out value="${project.value}" /></option>
		 </c:forEach>
    </select>
  </div>
     
      <div class="form-group col-md-3">
    <label for="year">Head: </label>
    <select class="form-control head" id="head" name="head">
     	<option value="">--Select Head--</option>
      	<option value="epa">Entry Point Activity (EPAs)</option>
       	<option value="livelihood">Livelihood Activities</option>
       	<option value="production">Production System</option>
    </select>
  </div>
      
     <div class="form-group col-1">
      <label for="project"> &nbsp;</label>
     <button class="btn btn-info" id="getCompleteAsset" name="getCompleteAsset" >Get Approved Work</button>
     </div>
   
     <div class="form-group col-12 confirmation">
    
     <table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
     <thead><tr><th>S No.</th><th>Project Name</th><th>Work-Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th></tr></thead>
    <tbody id="tbodyfinalListActivityAssetId"></tbody> 
     </table>
     </div>
  </div>
    </div> 
   
   
    
    <div id="forwardedAsset" class="container tab-pane fade"><br>
      <h3>Forwarded List of Work Id</h3>
      <hr/>
       <div class="form-row">
      
     <div class="form-group col-3">
      <label for="project">Projects: </label>
    <select class="form-control project" id="projectfwd" name="projectfwd" >
     	<option value="">--Select Project--</option>
	     <c:forEach items="${projectList}" var="project">
				<option value="<c:out value="${project.key}"/>"> <c:out value="${project.value}" /></option>
		 </c:forEach>
    </select>
   
     </div>
     
    <div class="form-group col-md-3">
	    <label for="year">Head: </label>
	    <select class="form-control head" id="heads" name="heads">
	     	<option value="">--Select Head--</option>
	      	<option value="epa">Entry Point Activity (EPAs)</option>
	       	<option value="livelihood">Livelihood Activities</option>
	       	<option value="production">Production System</option>
	    </select>
  	</div>
      
     <div class="form-group col-1">
      <label for="project"> &nbsp;</label>
     <button class="btn btn-info" id="viewforwardedAssetEPL" name="viewforwardedAssetEPL" >Get Forwarded Work</button>
     </div>
     </div>
     <div class="form-row">
     <div class="form-group col-12 confirmation">
    <!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->
     <table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
    <thead><tr><th>Project</th><th>Work Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Currently at</th></tr></thead>
     <tbody id="tbodyForwardedListActivityAssetId"></tbody>
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
<script src='<c:url value="/resources/js/livelihoodPrdouctionEPAWorkId.js" />'></script>
</body>
</html>