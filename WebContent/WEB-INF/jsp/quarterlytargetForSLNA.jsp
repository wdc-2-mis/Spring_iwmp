<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script src="https://cdn.ckeditor.com/4.9.2/standard/ckeditor.js"></script>  

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">

<ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="createPhysicalAnnualActionPlan" data-toggle="tab" href="#approvalRejection">Approval/Rejection of Quarterly Target</a>
    </li>
   
    <li class="nav-item">
      <a class="nav-link" id="viewMovement" data-toggle="tab" href="#movement">View Completed Quarterly Target</a>
    </li>
  </ul>

<div class="tab-content">
  <div id="approvalRejection" class="container tab-pane active"><br>
     <h3>Approval/Rejection of Quarterly Target</h3>
     <hr/>
     <div class="form-row">
      
      <div class="form-group col-6">
     
      <label for="project">Project: </label>
    <select class="form-control district" id="pendingProject" name="pendingProject" >
    <option value="">--Select Project--</option>
     <!-- <option value="0">--All--</option>  -->
     <c:forEach items="${getproject}" var="proj">
							<option value="<c:out value="${proj.key}"/>"><c:out
									value="${proj.value}" /></option>
						</c:forEach>
    </select>
    </div>
    <div class="form-group col-1">
      <label for="project"> &nbsp;</label>
     <button class="btn btn-info" id="getPendingAsset" name="getPendingAsset" >Get Pending Work</button>
     </div>
     </div>
     
     <div class="form-row">
     <div class="form-group col-12">
     <table class="movement" id="aapMovement" name="aapMovement" style="width:100%">
      <thead><tr><th><input type="checkbox" id="chkSelectAll" name="chkSelectAll" />Select All</th><th>Project</th><th>Fin. Year</th><th>Quarter's</th><th>Remarks</th><th >Action</th></tr></thead>
      <tbody id="tbodyTempListActivityAssetId">
     
     </tbody>
     </table>
     <br/>
     <div class="form-row">
     <div class="col-3">
     <button id="btnWCDCForward" name="btnWCDCForward" class="btn btn-info disabled" disabled="disabled">Complete</button>
     <button id="btnWCDCReject" name="btnWCDCReject" class="btn btn-danger disabled" disabled="disabled">Reject</button>
     </div>
    
     </div>
     
     </div>
     </div>
     </div>
     
 <div id="movement" class="container tab-pane fade"><br>
     <h3>Completed Quarterly Target</h3>
     <hr/>
      <table class="movement" id="aapMovement" name="aapMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Completed Quarterly Target</th></tr></thead>
     <tbody id="tbodyMovement">
      <c:forEach items="${projectReport}" var="projList">
      <tr>
      <td><c:out value="${projList.proj_name }" /></td>
      <td><c:out value="${projList.fin_yr_desc }" /></td>
      <td><c:out value="COMPLETED" /></td>
      <td><a href="#" data-id="${projList.proj_id}#${projList.fin_yr_cd}" class="movement" id="movement" name="movement">View Details</a></td>
      </tr>
      
      </c:forEach>
     <c:if test="${projectReport.size()<=0 }">
		<tr>
     <td colspan="6" class="text-center">Data not found !</td>
     </tr>
		</c:if>
		</tbody>
     </table>
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
<script src='<c:url value="/resources/js/quarterlytargetassetId.js" />'></script>
</body>
</html>