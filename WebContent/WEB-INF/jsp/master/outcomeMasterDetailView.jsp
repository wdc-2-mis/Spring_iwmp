
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">

<script src='<c:url value="/resources/js/outcomemaster.js" />'></script>


<body>
<!-- <div class ="container"> -->
<div class="table-wrapper">


<div class="table-title">
                <div class="row">
                
                    <div class="col-sm-6"><h6>Add/View Outcome Activity Details <b>(wdcpmksy_m_outcome_detail)</b></h6>
                    
     </div>
     <div class="col-sm-6">
      <a href="#addEmployeeModal" id="finddseqno" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i><span>Add New Outcome Activity Detail</span></a>
     </div>
                </div>
            </div>
<c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
          </c:if>
 <table class="table table-striped table-hover" id="outcomemaster">

 <thead>
 <tr>
 	<th width="2">Outcome Head Id</th>
  	<th width="25">Outcome Head Description</th>
 	<th width="2">Outcome Activity Id</th> 
 	<th width="30">Outcome Activity Description</th>
 	<th width="3">Sequence No.</th>
 	<th width="2">Edit</th>
 	<th width="2">Delete</th>
 </tr>
</thead> 

<tbody>

 				<c:if test="${not empty outcomeDeatilList}">
 				
 				<c:set var="hedVal" value="" />
 				
                	<c:forEach items="${outcomeDeatilList}" var="list" varStatus="status">
				    <tr>
				   
					<c:choose> 
  						<c:when test="${hedVal != list.outcome_desc}">
  								<td width="2"> <c:out value="${list.outcome_id}" /></td>
    							<td width="25"> <c:out value="${list.outcome_desc}" /></td>
				         		<c:set var="hedVal" value="${list.outcome_desc}" />
  						</c:when>
  						<c:otherwise>
    						<td width="2"> </td>
    						<td width="25"> </td>
  						</c:otherwise>
					</c:choose>
				    
				     	<td width="2"> <c:out value="${list.outcome_detail_id}" /></td>
				        <td width="30"> <c:out value="${list.outcome_detail_desc}" /></td>
				        <td width="30"> <c:out value="${list.seq_no}" /></td>
				       
				       <td width="2">
						<a href="#editEmployeeModal" class="update" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                        <input type="hidden" id="id" value="${list.outcome_detail_id}">
                        </td>
                        <td width="2">
                        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						
						<input type="hidden" id="id" value="${list.outcome_detail_id}"> 
						<input type="hidden" id="idoutcomed" value="${list.outcome_id}"> 
				       </td>
				    </tr> 
			    	</c:forEach>
			    	 </c:if> 
</tbody> 
 </table>


</div>

 
 <!-- add data  -->
 <div id="addEmployeeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form:form method="post"  name="addoutcome" id="addoutcome" modelAttribute="addoutcome" action="saveOutcomeDetailMaster"  >
     <div class="modal-header">      
      <h4 class="modal-title">Add Outcome Activity Detail</h4>
      <button type="button" class="close" data-dismiss="modal" onClick="window.location.reload();"  aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">  
     <div class="form-group">
 		<label for="name">Outcome Head Description</label>
 		<select name="outcome_id" id="outcome_id" class="form-control" style="height:100%" required="required">
					      <option value="">--select Head--</option>
						  <c:if test="${not empty outcomeDesc}">
               					<c:forEach items="${outcomeDesc}" var="lists">
       								<option value="<c:out value='${lists.outcomeId}'/>" ><c:out value="${lists.outcomeDesc}" /></option>
								</c:forEach>
						</c:if>  
			</select>
					  
 		</div>    
      <div class="form-group">
       <label>Outcome Activity Description <span style="color: red;">*</span>  </label>
       <input type="text" class="form-control" autocomplete = "off" required="required" name="outcome_detail_desc" id="outcome_detail_desc">
      </div>
      
      <div class="form-group">
       <label>Sequence No. <span style="color: red;">*</span>  </label>
       <input type="text" class="form-control" id="dseq_no" required="required" autocomplete = "off" name="dseq_no">
      </div>
 		
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel" onClick="window.location.reload();" >
      <input type="submit" class="btn btn-info" value="Add">
     </div>
    </form:form>
   </div>
  </div>
 </div>
     
    
     <!-- Edit Modal HTML -->
 <div id="editEmployeeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath}/updateOutcomeDetailMaster">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Outcome Activity</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="window.location.reload();" >&times;</button>
     </div>
     <div class="modal-body">  
     
      <div class="form-group">
 		<label for="name">Outcome Head </label>
 		<select name="doutcomeidh" id="doutcomeidh" class="form-control" style="height:100%" disabled="disabled">
					     
			</select>
 	   </div> 
 	      
      <div class="form-group">
       <label>Outcome Activity Description</label>
       <input type="text" id="outcome_detail_desc1" class="form-control" autocomplete = "off" required = "required"  name="outcome_detail_desc1">
       <input type="hidden" id="doutcomedid" class="form-control"  name="doutcomedid">
      </div>
      
      <div class="form-group">
       <label>Sequence No. <span style="color: red;">*</span>  </label>
       <input type="text" class="form-control" id="dseq_no1" required="required" autocomplete = "off" name="dseq_no1">
      </div>
 	   
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel" onClick="window.location.reload();" >
      <input type="submit" class="btn btn-info" value="Save">
      
     </div>
    </form>
   </div>
  </div>
 </div>
     
     
      
 <!-- delete data --> 
 <div id="deleteEmployeeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath}/deleteOutcomeDetailMaster">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Outcome Activity Detail</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">     
      <p>Are you sure you want to delete these Records?</p>
      
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
      <input type="submit" class="btn btn-danger" value="Delete">
     <input type="hidden" name="id" id="id">
     <input type="hidden" name="idoutcomed" id="idoutcomed">
     
     </div>
    </form>
   </div>
  </div>
     
  </div>   
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

</body>
</html>