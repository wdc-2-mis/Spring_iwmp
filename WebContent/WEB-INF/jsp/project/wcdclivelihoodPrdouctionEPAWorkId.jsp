<%@ include file="/WEB-INF/jspf/header2.jspf" %>
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
      <a class="nav-link active" id="listOfAsset" data-toggle="tab" href="#listofasset">Pending List Of Work-Id</a>
    </li>
  <%--   <c:if test="${userType eq 'DI'}">
      <li class="nav-item">
      <a class="nav-link" id="viewforwardedAsset" data-toggle="tab" href="#forwardedAsset"><c:if test="${forwardedAssetList.size() gt 0 }"><span class="badge badge-pill badge-warning"><strong><c:out value="${forwardedAssetList.size()}" /> </strong> </span></c:if>&nbsp;View Forwarded List of Work-Id</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" id="viewRejectedAsset" data-toggle="tab" href="#rejectedAsset"><c:if test="${rejectedAssetList.size() gt 0 }"><span class="badge badge-pill badge-danger"><strong><c:out value="${rejectedAssetList.size()}" /></strong> </span></c:if>&nbsp;View Rejected List of Asset by SLNA</a>
    </li> 
    </c:if> --%>
    <li class="nav-item">
      <a class="nav-link" id="viewAssetCreated1" data-toggle="tab" href="#assetCreated">List of Work-Id already approved</a>
    </li>
    
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="listofasset" class="container tab-pane active"><br>
      <h3>Work-Id List</h3>
     <form name="createHeadActivity" id="createHeadActivity">
     <lable class="message badge badge-danger error"></lable>
      <hr/>
      <div class="form-row">
   <%--    <c:if test="${userType ne 'DI'}">
     <div class="form-group col-4">
      <label for="project">District: </label>
    <select class="form-control district" id="pendingDistrict" name="pendingDistrict" >
    <option value="0">--All--</option>
     <c:forEach items="${distList}" var="dist">
							<option value="<c:out value="${dist.key}"/>"><c:out
									value="${dist.value}" /></option>
						</c:forEach>
    </select>
     </div>
     </c:if> --%>
     <div class="form-group col-3">
      <label for="project">Projects: </label>
    <select class="form-control project" id="pendingProject" name="pendingProject" >
    <option value="0">--All--</option>
     <c:forEach items="${pendingProjectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
     </div>
     
      <div class="form-group col-md-3">
    <label for="year">Head: </label>
    <select class="form-control head" id="schemed" name="schemed">
     	<option value="">--Select Head--</option>
      	<option value="epa">Entry Point Activity (EPAs)</option>
       	<option value="livelihood">Livelihood Activities</option>
       	<option value="production">Production System</option>
    </select>
  </div>
      
     <div class="form-group col-1">
      <label for="project"> &nbsp;</label>
     <button class="btn btn-info" id="getPendingAsset" name="getPendingAsset" >Get Pending Work</button>
     </div>
     </div>
     <div class="form-row">
     <div class="form-group col-12">
    <!--  <h5 class="text-center font-weight-bold"><u> List of Activities With Asset Id</u></h5> -->
     <table class="tempListActivityAssetId" id="tempListActivityAssetId" name="tempListActivityAssetId" style="width:100%">
     <thead><tr><th><input type="checkbox" id="chkSelectAll" name="chkSelectAll" />Select All</th><th>Project</th><th>Work-Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Remarks</th><th>Action</th></tr></thead>
     <tbody id="tbodyTempListActivityAssetId">
     <c:if test="${assetlist ne null}">
     <c:forEach items="${assetlist}" var="asset">
     <tr>
    <td><input type="checkbox" class="chkIndividual" id="chkIndividual${asset.tempassetid}" name="chkIndividual${asset.tempassetid}" value="${asset.tempassetid}"/></td>
     <td>
     <c:if test="${projectname ne asset.iwmpMProject.projName}">
		           		<c:out value="${asset.iwmpMProject.projName}"></c:out>
		           		<c:set value="${asset.iwmpMProject.projName}" var="projectname"/>
		           	</c:if>
	 </td>
    <%--   <td><c:out value="${asset.iwmpMFinYear.finYrDesc}" /></td> --%>
     <td><c:out value="${asset.tempassetid}" /></td>
     <td><c:out value="${asset.iwmpMPhyActivity.activityDesc}" /></td>
     <td><c:out value="${asset.iwmpVillage.iwmpGramPanchayat.iwmpBlock.blockName}" /></td>
     <td><c:out value="${asset.iwmpVillage.villageName}" /></td> 
     <%-- <td><c:out value="${asset.iwmpMPhySubactivity.subActivityDesc}" /></td>  --%>
     <td><textarea maxlength="1000" id="remarks${asset.tempassetid}" name="remarks${asset.tempassetid}" class="remarks"></textarea></td>
     <td width=15%><span><a href="#" data-id="${asset.tempassetid }" class="forwardLink">  <%-- <c:if test="${userType ne 'DI'}">Complete</c:if> --%>  <c:if test="${userType eq 'DI'}">Complete</c:if></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" data-id="${asset.tempassetid }" class="rejectLink">Reject</a></span></td> 
     </tr>
							
	</c:forEach>
	</c:if>
	<c:if test="${assetlist.size() eq 0}">
	<tr><td colspan="9" class="text-center">Data Not Found !</td></tr>
	</c:if>
     </tbody>
     
     </table>
     <br/>
     <div class="form-row">
     <div class="col-3">
    <%--   <c:if test="${userType eq 'DI'}">
     <button id="btnWCDCForward" name="btnWCDCForward" class="btn btn-info disabled" disabled="disabled">Forward</button>
     </c:if> --%>
      <c:if test="${userType eq 'DI'}">
     <button id="btnWCDCForward" name="btnWCDCForward" class="btn btn-info disabled" disabled="disabled">Complete</button>
     </c:if>
      <button id="btnWCDCReject" name="btnWCDCReject" class="btn btn-danger disabled" disabled="disabled">Reject</button>
     </div>
    
     </div>
     </div>
  </div>
  
   </form>
   <br/>
   
    </div>
    
  
    <div id="assetCreated" class="container tab-pane fade"><br>
      <h3>List of Work-Id already Created</h3>
      <hr/>
     <div class="form-row">
       <div class="form-group col-3">
      <label for="project">Projects: </label>
    <select class="form-control project" id="projectpi" name="projectpi" >
    <option value="0">--All--</option>
     <c:forEach items="${pendingProjectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
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