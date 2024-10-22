<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script src='<c:url value="/resources/js/wscommittee.js" />'></script>

<div class="container-fluid">
<div class="row">
<div class="container mt-2">
 <!--  <h2>Toggleable Tabs</h2>
  <br> -->
  <!-- Nav tabs -->
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="addProjectLocation" data-toggle="tab" href="#addLocation">Add Project Location</a>
    </li>
   <!--  <li class="nav-item">
      <a class="nav-link" id="addWatershedCommittee" data-toggle="tab" href="#watershedCommittee">Add Watershed Committee</a>
    </li> -->
    <li class="nav-item">
      <a class="nav-link" id="mappingOfVillageWatershedCommittee" data-toggle="tab" href="#villageWatershedMapping">Mapping of Village and Watershed Committee</a>
    </li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="addLocation" class="container tab-pane active"><br>
      <h3>Add Project Location(Village covered in the project area)</h3>
     <form name="addProjectLocationForm" id="addProjectLocationForm">
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
    <label for="block">Block(s): </label>
    <select class="form-control block" id="block" name="block" >
      <option value="">--Select Block--</option>
      
    </select>
  </div>
  <div class="form-group col-md-6">
    <label for="multiblock">Blocks: (<span style="color:red;">Kindly select the blocks which are beign covered in the project area</span>)</label>
    <select multiple class="form-control multiblock" id="multiblock" name="multiblock" style="height:90%">
     <option value="">--Select Blocks--</option>
    </select>
    <label ><span style="color:red;">Note:</span> To select more than one block, Use control key in combination with mouse click</label>
  </div>
  </div>
  <div class="form-row">
   <div class="form-group col-6">
    <label for="village">Villages:(<span style="color:red;">To select more than one village, Use control key in combination with mouse click</span>)
    &nbsp; Display Villages in form of Village Name (Gram panchayat) </label>
     <select id="village" name="village"  class="form-control" multiple data-live-search="true" style="height:90%">
            <option value="">--Select Village--</option>
        </select> 
   
  </div>
    
<div class="form-group col-6"></div>
 
  </div>
 <div class="form-row"> <div class="form-group col-6"></div><div class="form-group col-6"></div></div><br><br>
  <div class="form-row">
   <div class="form-group col-6">
   <label>After selecting the village(s) press on Go button: </label>
   <button class="btn btn-info" id="save" name="save" onclick="done(event)">Go</button>
  </div>
  </div>
    
     <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>List of villages covered in the project area</u></h5>
     <table class="projectLocationTable" id="projectLocationTable" name="projectLocationTable">
     <thead><tr><th>Block</th><th>GramPanchayat</th><th>Village</th><th></th></tr></thead>
     <tbody id="tbody"></tbody>
     </table>
     </div>
     </div>
     <div class="form-row">
   <div class="form-group col-md-6">
   <button class="btn btn-info" id="draftSave" name="draftSave" onclick="saveDraft(event)">Save as Draft</button>
   <button class="btn btn-info" id="complete" name="complete" onclick="completeData(event)" >Complete</button>
  </div>
  </div>
   </form>
    </div>
    
    
    <div id="watershedCommittee" class="container tab-pane fade"><br>
      <div class="offset-md-2"></div>
	  <div class="table">

		<form:form  name="wscommittee" id="wscommittee" modelAttribute="wscommittee" action="piaListWSComittee" method="post">
		
			<%--  <c:if test="${message ne null}">  --%>
			 	<div style="text-align: center;"  ><p class="message badge badge-success"> </p></div>
			<%-- </c:if>  --%>
			<div class="form-group row" style="text-align: center;">
				<h5></h5>
			</div>
			<div class="col" style="text-align: center;">
				<h5>Add/View Watershed Committee</h5>
			</div>
			
			<div class="form-group row" style="text-align: center;">
				<h5></h5>
			</div>
			
			 <div class="form-group row">
				<label for="project" class="col-sm-1 col-form-label">Project <span style="color: red;">*</span></label>
				<div class="col-sm-3">
					 <select name="projectId" title="Project" id="usreproject"  class="form-control"  style="width:110%" >
						<option value="">-- Select Project--</option>
						<c:if test="${not empty projectList}">
               					<c:forEach items="${projectList}" var="lists">
               					<c:if test="${lists.key eq ProjId}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected"><c:out value="${lists.value}" /></option>
       							</c:if>
       							<c:if test="${lists.key ne ProjId}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       							</c:if>	
								</c:forEach>
						</c:if>
					</select> 
				</div>
			&nbsp;&nbsp;&nbsp;	<label class="col-sm-3 col-form-label">Enter Watershed Committee <span style="color: red;">*</span></label>
				<div class="form-group col-md-3 wscname"><input type="text" name="wcname" id="wscname" value=""  class="form-control"/>
				<input type="hidden" name="wcId" id="wcId" value=""  class="form-control"/></div>
				
				<div class="col-sm-1">
					<input type="button" name="savewc" id="savewc" value="Save" class="btn btn-info" onclick="saveWatershed();" />
					<!-- <input type="button" name="update" id="update" value="Update" style="display:none;" class="btn btn-info" onclick="updateWatershed(this);" /> -->
				</div>
			</div> 


 		<div class="form-group col watershedTable">

 			<%-- <c:if test="${not empty wcCommitteeList}"> --%>
				 <div  style="text-align: center;">
						<h5 align="left">List of Watershed Committee added</h5>
						<table id="wcCommittee" class="table">	 
				<%--	<tr>
					<th>Select All &nbsp<input type="checkbox"  onchange="selects(this);"/></th><th>Watershed Name</th>
				</tr>	 
			 	 <c:forEach items="${wcCommitteeList}" var="wclist">
			 	<tr>
			        <td width="4%" height="26" > <input type="checkbox" id="wc_code" name="wc_code" value="<c:out value='${wclist.key}'/>"> </td>
			        
			        <td width="13%" >
			        <a href="#"  onclick="linkClicked('${wclist.value}','${wclist.key}')"><c:out value="${wclist.value}" /></a> </td>
			    </tr>
			    </c:forEach> 
			    <tr>
			        <td >
			    		<input type="button" name="delete" id="delete" value="Delete"  class="btn btn-info" onclick="deleteWatershed(this);" />
			    	</td>
			    </tr>--%>
			    </table>
				</div>
				
			<%-- </c:if> --%>
 		</div>
 
		</form:form>
		

	</div>
     
    </div>
    <div id="villageWatershedMapping" class="container tab-pane fade"><br>
      <h3>Mapping of Village and Watershed Committee</h3>
     
<lable class="message badge badge-danger error"></lable>
<table width="100%" cellpadding="1" cellspacing="0">
      <tr>
        <td width="91%" height="23" colspan="2"><table width="100%" border="0" cellpadding="2" cellspacing="1" class="gen_table">
            <tr>
            
              <td width="25%" class="label">Project Name:</td>
             <td> 
             <select class="form-control project" id="mpdprojname" name="mpdprojname" >
    			<option value="">--Select Project--</option>
     			<c:forEach items="${getmappingproject}" var="mpdprojname">
						<c:forEach items="${mpdprojname.value}" var="projname">
							<option value="<c:out value="${projname.projid}"/>"><c:out value="${projname.mpdprojname}" /></option>
						</c:forEach>
				</c:forEach>
    </select>
             
           
              </td>
              
          </tr>
          
        </table></td>
      </tr>
     
</table>

<c:set var="rows" value="0"/>
 
    <table class="projectMappedTable" id="projectMappedTable" name="projectMappedTable">
     <thead><tr><th>Block</th><th>Villages</th><th>Watershed Committee</th></tr></thead>
     <tbody id="wsdtbody"></tbody>
     </table>
    
       
         <div class="form-row">
   <div class="form-group col-md-6">
   <button class="btn btn-info" id="draftWCSave" name="draftWCSave" id="draftWCSave" onclick="saveWCDraft(event)">Save as Draft</button>
   <c:set var="rows" value="0"/>
  </div>
  </div>
    <table class="projectMappedDraftTable" id="projectMappedDraftTable" name="projectMappedDraftTable">
     <thead><tr><th>Block</th><th>Villages</th><th>Watershed Committee</th><th></th></tr></thead>
     <tbody id="wsdtdraftbody"></tbody>
     </table>
    
    
   
   <button class="btn btn-info" id="completeWC" name="completeWC" onclick="SavecompleteWCData(event)">Complete</button>
  
    </div>
  </div>
</div>
</div>
</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/addExistingProjectLocationMapping.js" />'></script>
</body>
</html>