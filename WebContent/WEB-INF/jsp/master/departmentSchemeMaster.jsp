<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">


<script src='<c:url value="/resources/js/deptschememaster.js" />'></script>
<body>
<div class="table-wrapper">
<div class="table-title">
                <div class="row">
                
                    <div class="col-sm-6"><h5>Department/Organization/Scheme Master Form <!-- <b>(m_department_scheme)</b> --></h5>
                    
     </div>
     <div class="col-sm-6">
      <a href="#adddeptschmasterModal" id ="add" class="btn btn-success" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>
      
      </div>
                </div>
            </div>
		<c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
        </c:if>
 <table class="table table-striped table-hover" id="baselinetypetable">
 <thead>
	 <tr>
		 <th>Sl. no.</th>
		 <th>Scheme Code</th>
		 <th>Department/Organization/Scheme Name</th>
		 <th>Types</th>
		 <th>Sequence No. </th>
		 <th>Status </th>
		 <th>Edit</th>
		 <th>Delete</th>
 	</tr>
</thead> 
<tbody>
		<c:forEach var="list" varStatus="status" items="${deptscheme}">
			<tr>	
				<td> ${status.count}</td>
				<td> <c:out value="${list.departmentSchemeIdPk }"></c:out> </td>
				<td> <c:out value="${list.schemeDescription }"></c:out> </td>
				<td> <c:out value="${list.types }"></c:out> </td>
				<td> <c:out value="${list.seqNo }"></c:out> </td>
				<td> <c:if test="${list.status eq true}">Active</c:if> <c:if test="${list.status ne true}">InActive</c:if></td>
				<td>
						<a href="#editDeptSchemeModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                         <input type="hidden" id="id" value="${list.departmentSchemeIdPk}">
                </td>
                <td>
                        <a href="#deleteDeptSchemeModal" id="delete" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						<input type="hidden" id="id" value="${list.departmentSchemeIdPk}">
						<input type="hidden" id="schemetype" value="${list.types}">
				</td>		  
			</tr>
		</c:forEach>
									  

</tbody>
</table>
          

<div id="adddeptschmasterModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath }/saveDeptSchemeMasterData">
     <div class="modal-header">      
      <h4 class="modal-title">Add Department/Organization/Scheme</h4>
      <button type="button" class="close"   data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body"> 
     
     
     	<div class="form-group">
       	<label>Department/Organization/Scheme Name</label>
       	<input type="text" class="form-control" required="required" autocomplete = "off" name="desc">
      	</div>
     
      	<div class="form-group">
       	<label>Sequence No</label>
       		<input type="text" class="form-control" id="seqno" required="required" autocomplete = "off" name="seqno" >
      	</div>
      	<div class="form-group">
			<label for="name">Types: </label> 
			<select name="type" id="type" class="form-control" 
				required="required" name="status">
				<option value="">--Select--</option>
				<option value="SHG">SHG</option>
				<option value="FPO">FPO</option>
			</select>
		</div>	
     
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel" >
      <input type="submit" class="btn btn-info" value="Save">
     </div>
    </form>
   </div>
  </div>
 </div>

   <!-- Edit Modal HTML -->
 <div id="editDeptSchemeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post"  action="${pageContext.request.contextPath}/updateDeptSchemeData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Department/Organization/Scheme Master Data</h4>
      <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">   
      <input type="hidden"  id ="deptschemeId" name="id">
      <div class="form-group">
       <label>Department/Organization/Scheme Description</label>
       <input type="text" class="form-control" id="desc" required="required" autocomplete = "off" name="desc">
      </div>
     
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno1" required="required" autocomplete = "off" name="seqno" >
      </div>
      <div class="form-group">
 		<label for="name">Status: </label>
 		<select name="status" id="status" class="form-control" required="required" name="status" >
				<option value="">--Select--</option>
				<option value="true">Active</option>
				<option value="false">InActive</option>
		</select>
 	</div>
 
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" onClick="window.location.reload();" value="Cancel">
      <input type="submit" class="btn btn-info" value="Save">
      
     </div>
    </form>
   </div>
  </div>
 </div>
 
 <!-- delete baseline type record  -->
 <div id="deleteDeptSchemeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
     <form method="post" action="${pageContext.request.contextPath}/deleteDeptSchemeData">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Baseline Type Data</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     
     
     <div class="modal-body">     
     
      <p>Are you sure you want to delete this Records?</p>
      
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
     <input type="submit" class="btn btn-danger" value="Delete">
     <input type="hidden" name="id" id="id">
     <input type="hidden" name="schemetype" id="schemetype">
     </div>
   </form>
    </div>
   </div>
  </div>
</div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>   
</body>