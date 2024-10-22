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
      <a class="nav-link active" id="createPhysicalAnnualActionPlan" data-toggle="tab" href="#approvalRejection">Approval/Rejection of Baseline Monthly Status</a>
    </li>
   
    <li class="nav-item">
      <a class="nav-link" id="viewMovement" data-toggle="tab" href="#movement">View Baseline Monthly Status Detail/ Movement</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link" id="CompletedAchPlan" data-toggle="tab" href="#completedAchievement">View Monthly Completed Baseline Status</a>
    </li>
    
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
  <div id="approvalRejection" class="container tab-pane active"><br>
     <h3>Approval/Rejection of Baseline Monthly Status</h3>
     <hr/>
      <table class="achievement" id="aapAchievement" name="aapAchievement">
     <thead><tr><th>Project</th><th>Year</th><th>Month</th><th>Status</th><th>Currently at Level</th><th >Remarks</th><th >Action</th></tr></thead>
     <tbody id="tbodyAapAchievement">
       <c:forEach items="${projectList}" var="projList">
      <%-- <c:forEach items="${projList.value}" var="list"> --%>
     <tr>
     <td><c:out value="${projList.projectdesc }" /></td>
     <td><c:out value="${projList.finyrdesc }" /></td>
     <td><c:out value="${projList.monthdesc }" /></td>
     <td><c:out value="${projList.status }" /></td>
     <td><c:out value="${projList.currentlyat }" /></td>
     <td>${projList.remarks }</td>
     <c:if test="${projList.currentlyat eq projList.usertype && projList.status ne 'Completed'}">
     <td><a href="#" data-id="${projList.achievementid }" class="action" id="action" name="action">Action</a></td>
     </c:if>
     </tr>
		</c:forEach>
		<c:if test="${projectList.size()<=0 }">
		<tr>
     <td colspan="7" class="text-center">Data not found !</td>
     </tr>
		</c:if>
		<%-- </c:forEach>  --%>
	</tbody>			
     </table>
    
  </div>
   <div id="movement" class="container tab-pane fade"><br>
     <h3>View Movement of Baseline Monthly Status</h3>
     <hr/>
      <table class="achievementMovement" id="achievementMovement" name="achievementMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Status</th><th>Currently at Level</th><th >Remarks</th><th>View Achievement Detail</th><th>View Achievement Movement</th></tr></thead>
     <tbody id="tbodyAchievementMovement"></tbody>
     </table>
    </div>
 
 <div id="completedAchievement" class="container tab-pane fade"><br>
     <h3>Completed Baseline Monthly Status </h3>
     <hr/>
      <table class="completeAchMovement" id="completeAchMovement" name="completeAchMovement">
     <thead><tr><th>Project</th><th>Year</th><th>Month</th><th>Status</th><th>Currently at Level</th><th >Remarks</th><th>View Achievement Detail</th></tr></thead>
     <tbody id="tbodyCAchievementMovement"></tbody>
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
<script src='<c:url value="/resources/js/baselineUpdateAchievement.js" />'></script>
</body>
</html>