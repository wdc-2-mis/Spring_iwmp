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
  <!-- <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="createPhysicalAnnualActionPlan" data-toggle="tab" href="#physicalAnnualActionPlan">Add Base Line Survey</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" id="viewHeadActivity" data-toggle="tab" href="#viewHead">View Base Line Survey</a>
    </li>
   
  </ul> -->

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="physicalAnnualActionPlan" class="container tab-pane active"><br>
      <h3>Add Baseline Survey Plot wise</h3>
     <form name="createHeadActivity" id="createHeadActivity" autocomplete="off">
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
    <label for="year">Village: </label>
    <span class="villageError"></span>
    <select class="form-control block" id="village" name="village">
      <option value="">--Select Village--</option>
       
    </select>
  </div>
  </div>
  <div class="form-row">
   <div class="form-group col-6">
    <label for="startDate">Plot No./ Survey No. / Gata No.:</label>
    <span class="plotnoError"></span>
     <input type="text" id="plotno" name="plotno"  class="form-control" maxlength="20" onpaste="return false;" ondrop="return false;" autocomplete="off"/>
  </div>
    
<div class="form-group col-6">
    <label for="startDate">Plot Area (in ha.):</label>
    <span class="projectAreaError"></span>
     <input type="text" id="projectArea" name="projectArea"  class="form-control" onfocusin="decimalToFourPlace(event)" onpaste="return false;" ondrop="return false;" autocomplete="off" />
  </div>
  
  <div class="form-group col-6">
    <label for="startDate">Irrigation Status:</label>
    <span class="ddlIrrigationStatusError"></span>
     <select id="ddlIrrigationStatus" name="ddlIrrigationStatus" class="ddlIrrigationStatus form-control"><option value="">--Select--</option></select>
  </div>
 
  </div>
 
 
    
     <div class="form-row">
     <div class="form-group col">
     <h5 class="text-center font-weight-bold"></h5>
     <table class="listOwnerLand table" id="listOwnerLand" name="listOwnerLand">
<!--      change made by yogesh in line no. 81 and 87 -->
     <thead><tr><th class="text-center">Ownership</th><th class="text-center ownername d-none">Owner Name</th><th class="text-center">Classification of Land</th><th class="text-center landSubClass d-none">Sub Classification of Land</th><th class="noofCrop">No. of Crop</th><th class="forestlandType d-none">Forest Land Type</th></tr></thead>
     <tbody id="listActivityHeadWiseTbody">
     <tr>
     <td><select id="ddlOwnership" name="ddlOwnership" class="ddlOwnership form-control"><option value="">--Select--</option></select></td>
     <td class="d-none ownername"><input type="text" id="ownername" name="ownername" class="ownername form-control"  /></td>
     <td><select id="ddlLandClass" name="ddlLandClass" class="ddlLandClass form-control"><option value="">--Select--</option></select></td>
     <td class="d-none landSubClass"><input type="text" id="landSubClass" name="landSubClass" class="landSubClass form-control" maxlength="100"  /></td>
     <td class="noofCrop"><select id="ddlNoofCrop" name="ddlNoofCrop" class="form-control"><option value="">--Select--</option></select></td>
      <td class="forestlandType d-none"><select id="ddlForestlandType" name="ddlForestlandType" class="ddlForestlandType form-control"><option value="">--Select--</option></select></td></tr></tbody>
     </table>
     
     </div>
     </div>
     
  <div class="form-row">
     <div class="form-group col otherDetailsDiv">
      
     </div>
     </div>
     
     <div class="form-row">
     <div class="form-group col cropDeatilDiv d-none">
     <h5 class="text-center font-weight-bold">Added Crop Detail</h5>
     <table class="tblCropDeatil table" id="tblCropDeatil" name="tblCropDeatil"><thead><tr><th class="text-center" id="disSeason">Season</th><th class="text-center">Crop Type</th><th class="text-center">Crop Area (in ha)<br/>(col-A)</th>
	<th class="text-center">Crop production per Hectare (in Quintal)<br/>(col-B)</th><th class="text-center">Avg. Income per Quintal (in Rs.)<br/>(col-C)</th><th class="text-center">Total Income (in Rs.)<br/>(col-A*col-B*col-C)</th><th>Action</th></tr></thead><tbody id="tblCropDeatilTbody">
	</tbody>
	</table>
	
     </div>
     </div>
     <div class="form-row">
   <div class="form-group col-md-6">
   <button class="btn btn-info d-none" id="draftSave" name="draftSave" >Save as Draft</button>
  </div>
  </div>
   </form>
   <br/>
    </div>
    
    
   
    <div id="viewHead" class="container tab-pane fade"><br>
      <h3>View Base Line Survey</h3>
      <hr/>
      <table class="headActivity" id="aapHeadActivity" name="aapHeadActivity">
     <thead><tr><th>S.No</th><th>Head</th><th>Activity</th></tr></thead>
     <tbody id="tbodyHeadActivity"></tbody>
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
<script src='<c:url value="/resources/js/blsOutcome.js" />'></script>
</body>
</html>