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
      <a class="nav-link active" id="createPhysicalAnnualActionPlan" data-toggle="tab" href="#physicalAnnualActionPlan">Create Outcome Target</a>
    </li>
    
   <!--  <li class="nav-item">
      <a class="nav-link" id="viewHeadActivity" data-toggle="tab" href="#viewHead">View Head/Activity</a>
    </li> -->
    <li class="nav-item">
      <a class="nav-link" id="viewMovement" data-toggle="tab" href="#movement">View Outcome Target Detail/ Movement</a>
    </li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="physicalAnnualActionPlan" class="container tab-pane active"><br>
      <h3>Create Outcome Target</h3>
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
  
 
    
     <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>List of Activity</u></h5>
     <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise" style="width:100%">
     <thead><tr><th>S.No.</th><th class="text-center">Name of Activity</th><th class="text-center">Target</th></tr></thead>
     <tbody id="listActivityHeadWiseTbody"></tbody>
     </table>
     
     </div>
     </div>
     <div class="form-row">
   <div class="form-group col-md-6">
   <button class="btn btn-info" id="draftSave" name="draftSave" >Save as Draft</button>
   <button class="btn btn-info" id="btnForward" name="btnForward" >Forward</button>
   <select class="form-control ddlUser d-none" id="ddlUser" name="ddlUser">
      <option value="">--Select User--</option>
       
    </select>
  </div>
  </div>
  <!-- <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>Final List of Activity</u></h5>
     <table class="finalListActivityHeadWise" id="finalListActivityHeadWise" name="finalListActivityHeadWise" style="width:100%">
     <thead><tr><th>Name of Activity</th><th class="text-center">Target</th><th></th></tr></thead>
     <tbody id="tbodyPhysicalAnnualActionPlan"></tbody>
     </table>
     </div>
     </div> -->
     <div class="form-row">
     <div class="form-group col-12 confirmation">
     
     </div>
   <div class="col-md-12">
   <div class="col d-inline-block remarks">
    <label>Remarks:- </label>
   <textarea class="form-control" rows="5" style="height:100%" id="remarks" name="remarks" placeholder="Remarks if any" value=""></textarea>
    </div>
   <div class="col-md-2 d-inline-block">
   <button class="btn btn-info d-none" id="forward" name="forward" >Forward</button>
   </div>
    <!-- <button class="btn btn-info" id="forward" name="forward" >Complete and Forward to SLNA</button> -->
    <div class="col-md-6 d-inline-block">
    <select class="form-control forward d-none" id="user" name="user"></select>
    </div>
  </div>
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
     <h3>Movement of Outcome Target</h3>
     <hr/>
      <table class="movement" id="aapMovement" name="aapMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Currently at Level</th><th >Remarks</th><th>View Outcome Detail</th><th>View Movement</th></tr></thead>
     <tbody id="tbodyMovement"></tbody>
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
<script src='<c:url value="/resources/js/outcometarget.js" />'></script>
</body>
</html>