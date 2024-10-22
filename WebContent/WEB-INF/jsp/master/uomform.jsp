<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<script src='<c:url value="/resources/js/uomdata.js" />'></script>	
	
<div class ="container">
<div class="table-wrapper">
<div class="table-title" >
                <div class="row" >
                 <div  class="col-sm-6" onclick="location.href='showuomform'"><h5>Unit Of Measurement <b>(iwmp_m_unit)</b></h5>
     </div>
     <div class="col-sm-6">
      <a href="#adduomModal" id = "adduomcode" class="btn btn-success" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>
      
                  </div>
                </div>
            </div>
            
            <c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
          </c:if>
          
          <table class="table table-striped table-hover" id="uomdatatable">
 <thead>
 <tr>
 
 <th>UOM Code</th>
 <th>UOM Description</th>
 <th>Edit</th>
 <th>Delete</th>
 </tr>
</thead> 
<tbody>
 <c:forEach var="list" items="${uomdata}">
					 <c:forEach var="listUser" items="${list.value}" >
					 <tr>	
					   <td><c:out	value="${listUser.unitcode}"></c:out>
									  </td>
									  <td>
									  
					<c:out	value="${listUser.unitdesc}"></c:out>
								     </td>
						
						<td>
						
						
						<a href="#editUOMModal" class="edit" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                         <input type="hidden" id="id"  value="${listUser.unitcode}"> 
                         </td>
                        
                         <td>
                        <a href="#deleteUOMModal" id="delete" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						<input type="hidden" id="id" value="${listUser.unitcode}"> 
						</td> 
						</tr>
									  
					 </c:forEach>
					 </c:forEach> 

</tbody> 
 </table>  
            </div>
            </div>
            <div id="adduomModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath }/saveuomData">
     <div class="modal-header">      
      <h4 class="modal-title">Add Unit Of Measurement</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body"> 
     <div class="form-group">
       <label>UOM Code</label>
       <input type="text" class="form-control" id = "uomcode" required="required" autocomplete = "off" name="uomcode">
      </div>
      <div class="form-group">
       <label>UOM Description</label>
       <input type="text" class="form-control" id="uomdesc" required="required" autocomplete = "off" name="uomdesc">
      </div>
      </div>
      <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel" onClick="window.location.reload();">
      <input type="submit" class="btn btn-info" value="Save">
     </div>
      </form>
      </div>
      </div>
      </div>
      
     <!-- Edit Modal HTML -->
 <div id="editUOMModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post"  action="${pageContext.request.contextPath}/updateuomData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Physical-Activity</h4>
      <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">   
     
      <div class="form-group">
       <label>UOM Code</label>
       <input type="text" id="uomcode1" disabled="disabled" class="form-control"  autocomplete = "off" required = "required"  name="uomcode">
      </div>
      
      <div class="form-group">
       <label>UOM Description</label>
       <input type="text" id="uomdesc1" class="form-control"  autocomplete = "off" required = "required"  name="uomdesc">
      </div>
      <input type="hidden"  id ="activityId" name="id">  
       
      
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" onClick="window.location.reload();" value="Cancel">
      <input type="submit" class="btn btn-info" value="Save">
      
     </div>
    </form>
   </div>
  </div>
 </div>  
      
      <!-- delete data --> 
 <div id="deleteUOMModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
     <form method="post" action="${pageContext.request.contextPath}/deleteuomData">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Unit Of Measurement</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">     
     <p>Are you sure you want to delete this Records?</p>
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
            