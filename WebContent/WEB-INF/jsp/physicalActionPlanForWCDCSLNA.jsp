<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script src="https://cdn.ckeditor.com/4.9.2/standard/ckeditor.js"></script>  
<script>  
    
    CKEDITOR.replace('editor2');  
  
    function getData() {  
        //Get data written in first Editor   
        var editor_data = CKEDITOR.instances['remarks'].getData();  
        //Set data in Second Editor which is written in first Editor  
       // CKEDITOR.instances['editor2'].setData(editor_data); 
        alert(editor_data) ;
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
      <a class="nav-link active" id="createPhysicalAnnualActionPlan" data-toggle="tab" href="#approvalRejection">Approval/Rejection of Plan</a>
    </li>
   
    <li class="nav-item">
      <a class="nav-link" id="viewMovement" data-toggle="tab" href="#movement">View Plan Detail/ Movement</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" id="viewCompletePlan" data-toggle="tab" href="#cplan">Completed Physical Plan</a>
    </li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
  <div id="approvalRejection" class="container tab-pane active"><br>
     <h3>Approval/Rejection of Plan</h3>
     <hr/>
     <table class="movement" id="aapMovement" name="aapAchievement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Currently at Level</th><th >Action</th></tr></thead>
     <tbody id="tbodyWCDCSLNAMovement">
       <c:forEach items="${planList}" var="projList">
      <%-- <c:forEach items="${projList.value}" var="list"> --%>
     <tr>
     <td><c:out value="${projList.projectdesc }" /></td>
     <td><c:out value="${projList.finyr }" /></td>
     <td><c:out value="${projList.status }" /></td>
     <td><c:out value="${projList.currentlevel }" /></td>
     <td>
     <c:if test="${projList.currentlevel eq projList.usertype && projList.status ne 'Completed'}">
     <a href="#" data-id="${projList.planid }" class="action" >Approval/Rejection</a>
     </c:if>
     </td>
     </tr>
		</c:forEach>
		<c:if test="${planList.size()<=0 }">
		<tr>
     <td colspan="7" class="text-center">Data not found !</td>
     </tr>
		</c:if>
		<%-- </c:forEach>  --%>
	</tbody>			
     </table>
    
  </div>
   <div id="movement" class="container tab-pane fade"><br>
     <h3>Movement of Physical Annual Action Plan</h3>
     <hr/>
      <table class="movement" id="aapMovement" name="aapMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Currently at Level</th><th >Remarks</th><th >Plan</th><th >Movement</th></tr></thead>
     <tbody id="tbodyMovement"></tbody>
     </table>
    </div>
    
    <div id="cplan" class="container tab-pane fade"><br>
     <h3>Completed Physical Plan</h3>
     <hr/>
      <table class="cplan" id="aapMovement" name="aapMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Currently at Level</th><th >Remarks</th></tr></thead>
     <tbody id="tbodyCMovement"></tbody>
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
<script src='<c:url value="/resources/js/physicalactionplan.js" />'></script>
</body>
</html>