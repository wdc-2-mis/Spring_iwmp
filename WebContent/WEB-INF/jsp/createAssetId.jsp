<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">
 <!--  <h2>Toggleable Tabs</h2>
  <br> -->
  <!-- Nav tabs -->
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="createAssetId" data-toggle="tab" href="#assetId">Create Work</a>
    </li>
     <li class="nav-item">
      <a class="nav-link" id="viewforwardedAsset" data-toggle="tab" href="#forwardedAsset"><c:if test="${forwardedAssetList.size() gt 0 }"><span class="badge badge-pill badge-warning forward"><strong><c:out value="${forwardedAssetList.size()}" /> </strong> </span></c:if>&nbsp;View Forwarded List of Work</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" id="viewRejectedAsset" data-toggle="tab" href="#rejectedAsset"><c:if test="${rejectedAssetList.size() gt 0 }"><span class="badge badge-pill badge-danger reject"><strong><c:out value="${rejectedAssetList.size()}" /></strong> </span></c:if>&nbsp;View Rejected List of Work</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" id="viewAssetCreatedkd" data-toggle="tab" href="#assetCreated">List of Work already approved by SLNA</a>
    </li>
    
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="assetId" class="container tab-pane active"><br>
      <h3>Create Work</h3>
     <form name="createHeadActivity" id="createHeadActivity">
     <lable class="message badge badge-danger error"></lable>
      <hr/>
  <div class="form-row">
  <div class="form-group col-md-6">
    <label for="project">Projects: </label>
    <select class="form-control project" id="project" name="project" >
    <option value="">--Select Project--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-6">
    <label for="year">Financial Year: </label>
    <select class="form-control block" id="year" name="year">
      <option value="">--Select Year--</option>
       
    </select>
  </div>
  </div>
  <!-- <div class="form-row">
   <div class="form-group col-6">
    <label for="startDate">Achievement Start Date:</label>
     <input type="text" id="startDate" name="startDate"  class="form-control" readonly/>
  </div>
    
<div class="form-group col-6"></div>
 
  </div> -->
 
    
     <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>List of Activities</u></h5>
     <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise" style="width:100%">
     <thead><tr><th class="text-center">Name of Head</th><th class="text-center">Name of Activity</th><th class="text-center">Unit</th><th class="text-center">Plan</th><th class="text-center">Work Created</th><th>Action</th></tr></thead>
     <tbody id="listActivityHeadWiseTbody"></tbody>
     </table>
     
     </div>
     </div>
     
  <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>Activities Mapping With Block and Village</u></h5>
     <table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
     <thead><tr><!-- <th>Asset Id</th> --><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Activity Type</th><th>Land Identification</th><th>Action</th></tr></thead>
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
     <h5 class="text-center font-weight-bold"><u>Draft List of Activities With Temporary Work Id</u></h5>
     <table class="tempListActivityAssetId" id="tempListActivityAssetId" name="tempListActivityAssetId" style="width:100%">
     <thead><tr><th>Temporary Work Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Activity Type</th><th>Land Identification</th><th>Action</th></tr></thead>
     <tbody id="tbodyTempListActivityAssetId"></tbody>
     </table>
     </div>
  </div>
  <div class="form-row">
   <div class="col-md-2 d-inline-block">
   <button class="btn btn-info d-none" id="forwardToWCDC" name="finalSave" >Forward</button>
   <button class="btn btn-info d-none" id="forwardToSLNA" name="finalSave" >Forward</button>
  </div>
  <div class="col-md-4 d-inline-block">
    <select class="form-control forward d-none" id="user" name="user"></select>
    </div>
  </div>
   </form>
   <br/>
    <p>Note:- Temporary Work-Id is just for reference purpose. It would be deleted and New Work-Id would be provided after completion of Work by the SLNA.</p>
    </div>
    
    
    <div id="forwardedAsset" class="container tab-pane fade"><br>
      <h3>Forwarded List of Work With Temporary Work Id</h3>
      <hr/>
       <div class="form-row">
      
     <div class="form-group col-6">
      <label for="project">Projects: </label>
    <select class="form-control project" id="projectfwd" name="projectfwd" >
    <option value="0">--All--</option>
     <c:forEach items="${forwardedProjectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
     </div>
      
     <div class="form-group col-1">
      <label for="project"> &nbsp;</label>
     <button class="btn btn-info" id="getForwardedAsset" name="getForwardedAsset" >Get Forwarded Work</button>
     </div>
     </div>
     <div class="form-row">
     <div class="form-group col-12 confirmation">
    <!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->
     <table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
    <thead><tr><th>Project</th><th>Year</th><th>Temporary Work Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th class="text-center">Activity Type</th><th class="text-center">Land Identification</th><th>Currently at</th></tr></thead>
     <tbody id="tbodyForwardedListActivityAssetId"></tbody>
     </table>
     </div>
  </div>
  <p>Note:- Temporary Work-Id is just for reference purpose. It would be deleted and New Work-Id would be provided after completion of Work by the SLNA.</p>
    </div> 
    
    <div id="rejectedAsset" class="container tab-pane fade"><br>
      <h3>Rejected List of Work </h3>
      <p>Note:- You can find/ re-submit rejected asset under the <strong>Create Work</strong> tab in <strong>Draft Work List</strong> section.</p>
      <hr/>
     <div class="form-row">
     <div class="form-group col-12 confirmation">
    <!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->
     <table class="rejectedListActivityAssetId" id="rejectedListActivityAssetId" name="rejectedListActivityAssetId" style="width:100%">
    <thead><tr><th>Project</th><th>Year</th><th>Temporary Work Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th class="text-center">Activity Type</th><th>Land Identification</th><th>Remarks</th><th>Rejected By</th></tr></thead>
     <tbody id="tbodyRejectedListActivityAssetId"></tbody>
     </table>
     </div>
  </div>
   <p>Note:- Temporary Work-Id is just for reference purpose. It would be deleted and New Work-Id would be provided after completion of Work by the SLNA.</p>
    </div> 
   
    <div id="assetCreated" class="container tab-pane fade"><br>
      <h3>List of Work already Created</h3>
      <hr/>
     <div class="form-row">
     <c:if test='${sessionScope.userType=="SL"}'>
     <div class="form-group col-md-6">
    <label for="project">District: </label>
    <select class="form-control project" id="district" name="district" >
    <option value="0">--ALL--</option>
    
     <c:forEach items="${districtList}" var="district">
							<option value="<c:out value="${district.key}"/>"><c:out
									value="${district.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-6">
    <label for="year">Projects: </label>
    <select class="form-control block" id="disproject" name="disproject">
      <option value="0">--ALL--</option>
   
       
    </select>
  </div>
  </c:if>
  
  <c:if test='${sessionScope.userType=="PI"}'>
    <div class="form-row">
  <div class="form-group col-md-12">
    <label for="project">Projects: </label>
    <select class="form-control project" id="projectkd" name="projectkd" >
    <option value="">--Select Project--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
  </div>
 <!--  <div class="form-group col-md-6">
    <label for="year">Financial Year: </label>
    <select class="form-control block" id="yearkd" name="yearkd">
      <option value="">--Select Year--</option>
       
    </select>
  </div> -->
  </div>
  </c:if>
  
  
  
  <c:if test='${sessionScope.userType=="DL"}'>
  <div class="form-group col-md-6">
    <label for="year">Projects: </label>
    <select class="form-control block" id="disproject" name="disproject">
      <option value="0">--ALL--</option>
   
       
    </select>
  </div>
  </c:if>
  
     <div class="form-group col-12 confirmation">
     
     <table class="finalListActivityAssetId" id="finalListActivityAssetId" name="finalListActivityAssetId" style="width:100%">
     <thead><tr><th>S No.</th><th>Project Name</th><th>Fin-Year</th><th>Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th class="text-center"> SubactivityType</th><th class="text-center"> Land Identification</th></tr></thead>
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
<script src='<c:url value="/resources/js/assetId.js" />'></script>
</body>
</html>