<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
function validation() 
{
	if (document.getElementById("project").value =='') 
	{
		alert("Please select project");
		document.getElementById("project").focus();
		return;
	}
	if (document.getElementById("year").value =='') 
	{
		alert("Please select year");
		document.getElementById("year").focus();
		return ;
	}
	
	document.createHeadActivity.action="getListofPhysicalActionPlanExist";
	document.createHeadActivity.method="post";
	document.createHeadActivity.submit();
}



	function updatePlan(){
// 		var activity = document.getElementById("activity").options[document.getElementById("activity").selectedIndex].val();
// 		alert('check : '+activity);
// 		document.getElementById("activity").value = activity
		document.updateActPlan.action = "updateAsExisting";
		document.updateActPlan.method = "post";
		document.updateActPlan.submit();
	}
	
</script>

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
      <a class="nav-link active" id="createPhysicalAnnualActionPlan" data-toggle="tab" href="#physicalAnnualActionPlan">Update/View Physical Annual Action Plan</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" id="viewHeadActivity" data-toggle="tab" href="#viewHead">View Head/Activity</a>
    </li>
    <!-- <li class="nav-item">
      <a class="nav-link" id="viewMovement" data-toggle="tab" href="#movement">View Plan Detail/ Movement</a>
    </li> -->
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="physicalAnnualActionPlan" class="container tab-pane active"><br>
      <h3>Update Activities of Physical Action Plan to Complete Project</h3>
     <form name="createHeadActivity" id="createHeadActivity">
     <c:if test="${not empty msg}">
     		<c:if test="${msg eq 'success'}">
     			<lable class="message badge badge-success" >Plan Updated Successfully</lable>
     		</c:if>
     		<c:if test="${msg eq 'fail'}">
     			<lable class="message badge badge-danger error">Plan not Updated, Plan remains Same.</lable>
     		</c:if>
     		<c:if test="${msg eq 'less'}">
     			<lable class="message badge badge-danger error">Plan should be Greater Than the Existing Plan</lable>
     		</c:if>
     </c:if>
      <hr/>
  <div class="form-row">
  <div class="form-group col-md-6">
    <label for="project">Projects: </label>
    <select class="form-control project" id="project" name="project" >
    <option value="">--Select Project--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"<c:out value="${project.key==projectcd?'selected':''}"/>><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-4">
    <label for="year">Financial Year: </label>
    <select class="form-control block" id="year" name="year">
      <option value="">--Select Year--</option>
      <c:if test ="${yearMapSize>0 }">
      	<c:forEach items="${yearMapList}" var="yrMap">
			<option value="<c:out value="${yrMap.key}"/>"<c:out value="${yrMap.key==yearcd?'selected':''}"/>><c:out value="${yrMap.value}" /></option>
		</c:forEach>
      </c:if>
       
    </select>
  </div>
  
   <div class="form-group col-1">
      <label for="btnGetActivity"> &nbsp;</label>
     <input type="button" class="btn btn-info" id="getFinalList" name="getFinalList" onclick ="validation()" value='Get Details' /> 
     </div>
  
  </div>
 <!--  <div class="form-row">
   <div class="form-group col-6">
    <label for="startDate">Achievement Start Date:</label>
     <input type="text" id="startDate" name="startDate"  class="form-control" readonly/>
  </div>
    
<div class="form-group col-6"></div>
 
  </div> -->
 
    
<!--      <div class="form-row"> -->
<!--      <div class="form-group col"> -->
<!--      <hr/>   -->
<!--      <h5 class="text-center font-weight-bold"><u>List of Activity</u></h5> -->
<!--      <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise"> -->
<!--      <thead><tr><th class="text-center">Name of Head</th><th class="text-center">Name of Activity</th><th class="text-center">Unit <br/>(ha/ nos/ Rmt/ Cubic meter)</th><th class="text-center"><span >Plan</span><br/>Quantity/ Area to be developed</th><th></th></tr></thead> -->
<!--      <tbody id="listActivityHeadWiseTbody"><tr><td><select id="ddlHead" name="ddlHead" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" readonly /><select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control"><option value="">--Select Unit--</option></select></td><td><input type="text" id="txtTarget" name="txtTarget" onkeyup="checkvalue(this)" onblur="checkInput(this)" class="txtTarget form-control" value=""/></td><td></td></tr></tbody> -->
<!--      </table> -->
     
<!--      </div> -->
<!--      </div> -->
<!--      <div class="form-row"> -->
<!--    <div class="form-group col-md-6"> -->
  <!--  <button id="btnAdd" name="btnAdd" class="btnAdd btn btn-info">Add New Row</button>  -->
<!--    <button class="btn btn-info" id="draftSave" name="draftSave" >Update</button> -->
<!--   </div> -->
<!--   </div> -->
  <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>Final List of Activity</u></h5>
     <table class="finalListActivityHeadWise" id="finalListActivityHeadWise" name="finalListActivityHeadWise" style="width:100%">
     <thead><tr><th style="width:35%">Name of Head</th><th style="width:25%">Name of Activity</th><th style="width:15%">Unit</th><th class="text-center" style="width:15%"><span >Plan</span><br/>Quantity/ Area to be developed</th><th style="width:10%">Action</th></tr></thead>
     <tbody id="tbodyPhysicalAnnualActionPlan"></tbody>
     <c:if test="${phyActPlanExistListSize >0 }">
     		<c:forEach items="${phyActPlanExistList}" var="dataV" varStatus="count">
     			<tr><td><c:out value ="${dataV.headname}"/></td><td><c:out value ="${dataV.activityname}"/></td><td><c:out value ="${dataV.unitname}"/></td><td><c:out value ="${dataV.plan}"/></td><td><input type="button" data-toggle="modal" data-target='#updatePhyActPlan' value="Edit" data-id =<c:out value ="${dataV.aapid}"/> name="editPlan" id = "editPlan" align="center" /></td></tr>
     		</c:forEach>
     
     </c:if>
     </table>
     </div>
     </div>
     <div class="form-row">
     <div class="form-group col-12 confirmation">
     
     </div>
  <!--  <div class="col-md-12">
   <div class="col d-inline-block remarks">
    <label>Remarks:- </label>
   <textarea class="form-control" rows="5" style="height:100%" id="remarks" name="remarks" placeholder="Remarks if any" value=""></textarea>
    </div>
   <div class="col-md-2 d-inline-block">
   <button class="btn btn-info d-none" id="forward" name="forward" >Forward</button>
   </div>
    <button class="btn btn-info" id="forward" name="forward" >Complete and Forward to SLNA</button>
    <div class="col-md-6 d-inline-block">
    <select class="form-control forward d-none" id="user" name="user"></select>
    </div>
  </div> -->
  </div>
   </form>
   <br/>
    </div>
    
    
   
    <div id="viewHead" class="container tab-pane fade"><br>
      <h3>View Head/Activity</h3>
      <hr/>
      <table class="headActivity" id="aapHeadActivity" name="aapHeadActivity">
     <thead><tr><th>S.No</th><th>Head</th><th>Activity</th></tr></thead>
     <tbody id="tbodyHeadActivity"></tbody>
     </table>
    </div>
    <div id="movement" class="container tab-pane fade"><br>
     <h3>Movement of Physical Annual Action Plan</h3>
     <hr/>
      <table class="movement" id="aapMovement" name="aapMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Currently at Level</th><th >Remarks</th><th>View Plan Detail</th><th>View Movement</th></tr></thead>
     <tbody id="tbodyMovement"></tbody>
     </table>
    </div>
  </div>
</div>
</div>
</div>
 </div>
 </div>
 
      <div class="modal fade" id="updatePhyActPlan">  
    <div class="modal-dialog">  
      
      <!-- Modal content-->  
      <div class="modal-content">  
      	 <form name="updateActPlan" id="updateActPlan">
<!--         <form method="post" -->
<%-- 					action="${pageContext.request.contextPath}/updateData"> --%>
					<div class="modal-header">
						<h4 class="modal-title"> Update Plan Quantity/ Area to be developed</h4>
						<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
					</div>
					<div style="position: relative;-webkit-box-flex: 1;-ms-flex: 1 1 auto;flex: 1 1 auto;padding: 1rem;">

										<div class="form-group" >
											<label id="lblHead" >Name of Head</label>
											 <select class="form-control" title="Project" disabled="disabled" id="head" name="head" required="required" style="height: 100%">
											</select>
										</div>

										<div class="form-group">
											<label>Name of Activity</label> 
											<select class="form-control" title="Project" disabled="disabled" id="activity" name="activity" required="required" style="height: 100%">

											</select>
										</div>
										<div class="form-group">
											<label>Unit</label> <input type="text" class="form-control unit" id="unit" name="unit"
												required="required" disabled="disabled" name="unit"><span id="unitError"></span>
										</div>

										<div class="form-group">
											<label>Plan</label> <input type="text"
												class="form-control" id="plan" required="required"
												autocomplete="off" name="plan"><span id="planError"></span>
										</div>

<!-- 										<input type="hidden" id="ddhead" name="ddlhead"> -->
										<input type="hidden" id="ddactivity" name="ddactivity">
										<input type="hidden" id="ddproject" name="ddproject" value ="${projectcd}">
										<input type="hidden" id="ddyear" name="ddyear" value ="${yearcd}">
										<input type="hidden" id="ddaapid" name="ddaapid">
								
					</div>
					<div class="modal-footer">
						<button class="btn btn-info" id="planUpdate" name="planUpdate" onclick ="updatePlan()" >Update</button>
					</div>
				</form>  
      </div>  
        
    </div>  
  </div>
 
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/physicalactionplanexistupdate.js" />'></script>
</body>
</html>