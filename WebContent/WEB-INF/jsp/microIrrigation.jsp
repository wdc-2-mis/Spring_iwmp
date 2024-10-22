<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">

<div class="tab-content">
    <div id="physicalAnnualActionPlan" class="container tab-pane active"><br>
      <h3>Micro Irrigation Detail at the Time of Baseline Survey</h3>
     <form name="mirrgation" id="mirrgation" autocomplete="off">
     <lable class="message badge badge-danger error"></lable>
      <hr/>
      
    <c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
          </c:if>  
      
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
    <label for="year">Village: </label>
    <span class="villageError"></span>
    <select class="form-control block" id="village" name="village">
      <option value="">--Select Village--</option>
       
    </select>
  </div>
  
  <div class="form-group col-md-6">
    <label for="year">Plot No: </label>
    <span class="plotnoError"></span>
    <select class="form-control block" id="plotno" name="plotno">
      <option value="">--Select Plot No.--</option>
       
    </select>
  </div>
  
  
  <div class="form-group col-6">
    <label for="startDate">Plot Area (in ha.):</label>
    <span class="projectAreaError"></span>
     <input type="text" id="projectArea" name="projectArea"  readonly class="form-control"  />
  </div>
  
  <div class="form-group col-6">
    <label for="startDate">Irrigation Status:</label>
    <span class="ddlIrrigationStatusError"></span>
     <input type="text" id="irrgationstatus" name="irrgationstatus" readonly class="form-control" />
  </div>
  
  <div class="form-group col-6">
    <label for="startDate">Area Under Micro Irrigation:</label>
    <span class="ddlIrrigationStatusError"></span>
     <input type="text" id="microirr" name="microirr"  onfocusin="decimalToFourPlace(event)" class="form-control" />
  </div>
  
  
  
  
  </div>
     <div class="form-row">
   <div class="form-group col-md-6">
   <button class="btn btn-info" id="draftSave" name="draftSave">Save as Draft</button>
   <button class="btn btn-info" id="complete" name="complete">Complete</button>
  </div>
  </div> 
</form>
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
<script src='<c:url value="/resources/js/microirrigation.js" />'></script>
</body>
</html>
