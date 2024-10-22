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


<script src='<c:url value="/resources/js/phyact.js" />'></script>
<body>

<div class="table-wrapper">
<div class="table-title">
                <div class="row">
                
                    <div class="col-sm-6"><h5>Details of Physical Activity <b>(iwmp_m_phy_activity)</b></h5>
                    
     </div>
     <div class="col-sm-6">
      <a href="#addphyactModal" id = "add" class="btn btn-success" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>
      
      </div>
                </div>
            </div>
<c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
          </c:if>
 <table class="table table-striped table-hover" id="phyacttable">
 <thead>
 <tr>
 
 <th>Activity Code</th>
 <th>Head Code (head description)</th>
 <th>Activity Description</th>
 <th>UOM</th>
 <th>SeqNO </th>
 <th>Work </th>
 <th>Status</th>
 <th>Edit</th>
 <th>Delete</th>
 </tr>
</thead> 

<tbody>
<c:forEach var="list" items="${phyact}">
					 <c:forEach var="listUser" items="${list.value}" >
					 
					 <tr>	
					 
									  <td>
									  <c:out	value="${listUser.activitycode }"></c:out>
									  </td>
									  <td>
									  
					<c:out	value="${listUser.headdesc }"></c:out>
								     </td>
								     <td>
									  
					<c:out	value="${listUser.actdesc }"></c:out>
								     </td>
					<td>
					<c:out	value="${listUser.unithdesc }"></c:out>
								     </td>
					<td>
					<c:out	value="${listUser.seqno }"></c:out>
								     </td>		
								     <td>
					<c:out	value="${listUser.asset }"></c:out>
								     </td>	     			     
								     <td>
					<c:out	value="${listUser.status }"></c:out>
								     </td>	  
						
						<td>
						<a href="#editPhyactModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                         <input type="hidden" id="id" value="${listUser.activitycode}">
                         </td>
                        
                         <td>
                        <a href="#deletePhyactModal" id="delete" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						<input type="hidden" id="id" value="${listUser.activitycode}">
						</td>
						</tr>
									  
					 </c:forEach>
					 </c:forEach>

</tbody> 
 </table>
</div>
 <!-- add data  -->
 <div id="addphyactModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath }/savephyactData">
     <div class="modal-header">      
      <h4 class="modal-title">Add Physical-Activity</h4>
      <button type="button" class="close" onClick="window.location.reload();"  data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body"> 
     <div class="form-group">
       <label>Head Code and Desc</label>
       <select  name="headcode" title="Project" id="headcode"  class="form-control" style="height:100%" required = "required" >
	  </select>
      </div>
      
         
      <div class="form-group">
       <label>Activity Description</label>
       <input type="text" class="form-control" required="required" autocomplete = "off" name="adesc">
      </div>
      
      <div class="form-group">
       <label>UOM Code</label>
       <select name="uomcode" title="Project" id="uomcode"  class="form-control" style="height:100%" required = "required">
	  </select>
      </div>
      
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno" required="required" autocomplete = "off" name="seqno" >
      </div>
      
      <div class="form-group">
 <label for="name">Work Required </label>
 <select name="assets" id="assets" class="form-control" style="height:100%" required="required" name="assets">
					      <option value="">--Select Works--</option>
					      <option value="0">No</option>
					      <option value="1">Single</option>
					      <option value="2">Multiple</option>
					    </select>
					  
 </div> 
      
      <div class="form-group">
 <label for="name">Status </label>
 <select name="status" id="status" class="form-control" style="height:100%" required="required" name="status">
					      <option value="">--Select Status--</option>
					      <option value="A">Active</option>
					      <option value="I">Inactive</option>
					    </select>
					  
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
 <div id="editPhyactModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post"  action="${pageContext.request.contextPath}/updateactData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Physical-Activity</h4>
      <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">   
     <div class="form-group">
       <label>Head Code & Desc</label>
       <select name="headcode1"  disabled="disabled" title="Project" id="headcode1"  class="form-control" style="height:100%" required = "required">
	 </select>
      </div>
        
      <div class="form-group">
       <label>Activity Description</label>
       <input type="text" id="adesc" class="form-control"  autocomplete = "off" required = "required"  name="adesc">
      </div>
      <input type="hidden"  id ="activityId" name="id">  
       
      <div class="form-group">
       <label>UOM Code</label>
       <select name="uomcode1" title="Project" id="uomcode1"  required = "required" class="form-control" style="height:100%">
	  
	  </select>
      </div> 
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno1" required="required" autocomplete = "off" name="seqno1" >
       
      </div>
      
      <div class="form-group">
 <label for="name">Works Required </label>
 <select name="assets1" id="assets1" class="form-control" style="height:100%" required="required">
					      <option value="">--Select Works--</option>
					      <option value="0">No</option>
					      <option value="1">Single</option>
					      <option value="2">Multiple</option>
					    </select>
					  
 </div> 
 
 <div class="form-group">
 <label for="name">Status: </label>
 <select name="status" id="status" class="form-control" style="height:100%" required="required" name="status">
					      <option value="">--Select--</option>
					      <option value="A">Active</option>
					      <option value="I">Inactive</option>
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
     
     
      
 <!-- delete data --> 
 <div id="deletePhyactModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
     <form method="post" action="${pageContext.request.contextPath}/deletephyactData">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Physical-Activity</h4>
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
     
     <div id="deletePhyactModalnew" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath}/deleteData">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Confirmation Physical-Activity</h4>
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">     
      <p>Once you delete this record you cannot be undo</p>
      
     </div>
     <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
      <input type="submit" class="btn btn-danger" value="Delete1">
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
    