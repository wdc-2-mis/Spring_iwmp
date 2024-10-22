
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
                
                    <div class="col-sm-6"><h6>Add/View Outcome Head Details<b>(wdcpmksy_m_outcome)</b></h6>
                    
     </div>
     <div class="col-sm-6">
      <a href="#addEmployeeModal" id = "findseqno" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Outcome Master</span></a>
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
 
 <th>Outcome Head Id</th>
 <th>Outcome Head Description</th>
 <th>Sequence No. </th>
 <th>Month </th>
 <th>Year </th>
 <th>Detail Exist</th>
 <th style="width: 110px;">Base Line Required</th>
 <th>Edit</th>
 <th>Delete</th>
 </tr>
</thead> 

<tbody>

 				<c:if test="${not empty outcomeMasterList}">
                	<c:forEach items="${outcomeMasterList}" var="list" varStatus="status">
				    <tr>
				        
				     	<td> <c:out value="${list.outcome_id}" /></td>
				        <td> <c:out value="${list.outcome_desc}" /></td>
				        <td> <c:out value="${list.seq_no}" /></td> 
				        <c:if test="${list.monthname ne null}">
				        	<td> <c:out value="${list.monthname}" /></td>
				        </c:if>
				        <c:if test="${list.monthname eq null}">
				        	<td> N/A </td>
				        </c:if>
				        <c:if test="${list.finyear ne null}">
				        	<td> <c:out value="${list.finyear}" /></td>
				        </c:if>
				        <c:if test="${list.finyear eq null}">
				        	<td> N/A </td>
				        </c:if>
				        
				        <c:if test="${list.detail_exist eq 'Y'.charAt(0)}">
				        	<td> Yes </td>
				        </c:if>
				        <c:if test="${list.detail_exist eq null}">
				        	<td>  </td>
				        </c:if>
				        <td><c:out value="${list.bls_required}" /></td>
				       <td>
						<a href="#editEmployeeModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                        <input type="hidden" id="id" value="${list.outcome_id}">
                        </td>
                        <td>
                        <a href="#deleteEmployeeModal" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						
						<input type="hidden" id="id" value="${list.outcome_id}"> 
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
    <form:form method="post"  name="addoutcome" id="addoutcome" modelAttribute="addoutcome" action="saveOutcomeMaster"  >
     <div class="modal-header">      
      <h4 class="modal-title">Add Outcome Head Detail</h4>
      <button type="button" class="close" data-dismiss="modal" onClick="window.location.reload();"  aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">     
      <div class="form-group">
       <label>Outcome Head Description <span style="color: red;">*</span>  </label>
       <input type="text" class="form-control" autocomplete = "off" required="required" name="outcome_desc" id="outcome_desc">
      </div>
      
      <div class="form-group">
       <label>Sequence No <span style="color: red;">*</span>  </label>
       <input type="text" class="form-control" id="seq_no" required="required" autocomplete = "off" name="seq_no" >
      </div>
      
       <div class="form-group">
 		<label for="name">Detail Exist: </label>
 		<select name="detail_exist" id="detail_exist" class="form-control" style="height:100%"  >
					      <option value="">--Select--</option>
					      <option value="Y">Yes</option>
		</select>
 	   </div> 
       <div class="form-group">
 		<label for="name">Financial Year </label>
 		<select name="fin_yr_cd" id="fin_yr_cd" class="form-control" style="height:100%" >
					      <option value="">--select Year--</option>
						  <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
								</c:forEach>
						</c:if>  
					    </select>
					  
 		</div> 
        <div class="form-group">
 			<label for="name">Month</label>
 			<select name="month_id" id="month_id" class="form-control" style="height:100%" >
					    <option value="">--select month--</option>
						<c:if test="${not empty months}">
               					<c:forEach items="${months}" var="lists">
       								<option value="<c:out value='${lists.monthId}'/>"  ><c:out value="${lists.monthName}" /></option>
								</c:forEach>
						</c:if> 
			</select>
 		</div> 
 		
 		<div class="form-group">
							<label for="name">Base Line Required: </label> <select
								name="bls_required" id="bls_required" class="form-control"
								style="height: 100%" required="required">
								<option value="">--Select--</option>
								<option value="true">Yes</option>
								<option value="false">No</option>
							</select>

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
    <form method="post" action="${pageContext.request.contextPath}/updateOutcomeMaster">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Outcome Head Detail</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="window.location.reload();" >&times;</button>
     </div>
     <div class="modal-body">     
      <div class="form-group">
       <label>Outcome Head Description</label>
       <input type="text" id="outcome_desc1" class="form-control" autocomplete = "off" required = "required"  name="outcome_desc1">
       <input type="hidden" id="doutcomeid" class="form-control"  name="doutcomeid">
      </div>
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno1" required="required" autocomplete = "off" name="seqno1" >
       </div>
       <div class="form-group">
 		<label for="name">Detail Exist: </label>
 		<select name="detail_exist1" id="detail_exist1" class="form-control" style="height:100%"  >
					      <option value="">--Select--</option>
					      <option value="Y">Yes</option>
		</select>
 	   </div> 
 	   
 	   <div class="form-group">
 		<label for="name">Financial Year </label>
 		 <select name="fin_yr_cd1" title="Project" id="fin_yr_cd1"   class="form-control" style="height:100%" >
 		
		</select>
					  
 		</div> 
        <div class="form-group">
 			<label for="name">Month</label>
 			<select name="month_id1" title="Project" id="month_id1"   class="form-control" style="height:100%" >
 		
			</select>
 		</div> 
 	   
 	   <div class="form-group">
							<label for="name">Base Line Required: </label> <select
								name="bls_required" id="bls_required" class="form-control"
								style="height: 100%" required="required">
								<option value="">--Select--</option>
								<option value="true">Yes</option>
								<option value="false">No</option>
							</select>

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
    <form method="post" action="${pageContext.request.contextPath}/deleteOutcomeMaster">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Outcome Head</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">     
      <p>Are you sure you want to delete these Records?</p>
      
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
      <input type="submit" class="btn btn-danger" value="Delete">
     <input type="hidden" name="id" id="id">
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