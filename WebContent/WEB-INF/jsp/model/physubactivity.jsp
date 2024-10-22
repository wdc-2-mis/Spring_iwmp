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


<script src='<c:url value="/resources/js/physubact.js" />'></script>
<body>
<div class="table-wrapper">
<div class="table-title">
                <div class="row">
                
                    <div class="col-sm-6"><h5>Details of Physical Sub Activity <b>(iwmp_m_phy_subactivity)</b></h5>
                    
     </div>
     <div class="col-sm-6">
      <a href="#addphysubactModal" id = "add" class="btn btn-success" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>
      
      </div>
                </div>
            </div>
           <c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
          </c:if>
<table class="table table-striped table-hover" id="physubacttable">
 <thead>
 <tr>

 <th>Sub Act.Code</th>
 <th>Head Code(head description)</th>
 <th>Activity Code(Activity description)</th>
 <th>Sub Activity Description</th>
 <th>SeqNO </th>
 <th>Status</th>
 <th>Edit</th>
 <th>Delete</th>
 </tr>
</thead> 

<tbody>
<c:forEach var="list" items="${physubact}">
					 <tr>	
					 	  <td>	  
					 	  <c:out	value="${list.subActivityCode }"></c:out>
						  </td>
									  
						 <td>	  
					 	  <c:out	value="${list.iwmpMPhyActivity.iwmpMPhyHeads.headDesc }"></c:out>
						 </td>
						  
						 <td>	  
					 	  <c:out	value="${list.iwmpMPhyActivity.activityDesc }"></c:out>
						 </td>
						 
						 <td>	  
					 	 <c:out	value="${list.subActivityDesc }"></c:out>
						 </td>
						 
						 <td>	  
					 	 <c:out	value="${list.seqNo }"></c:out>
						 </td>
						 
						 <td>	  
					 	 <c:out	value="${list.status }"></c:out>
						 </td>	
						 
						 <td>
						<a href="#editPhysubactModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                         <input type="hidden" id="id" value="${list.subActivityCode}">
                         </td>
                        
                         <td>
                        <a href="#deletePhysubactModal" id="delete" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						<input type="hidden" id="id" value="${list.subActivityCode}">
						</td>		  
						</tr>
						</c:forEach>
									  

</tbody>
</table>

</div>

<div id="addphysubactModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath }/savephysubactData">
     <div class="modal-header">      
      <h4 class="modal-title">Add Physical-Sub Activity</h4>
      <button type="button" class="close" onClick="window.location.reload();"  data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body"> 
     <div class="form-group">
       <label>Head Code and Desc</label>
       <select  name="headcode" title="Project" id="headcode"  class="form-control" style="height:100%" required = "required" >
	  </select>
      </div>
      
      
        <div class="form-group">
       <label>Activity Code and Description</label>
       <select  name="actdesc" title="Project" id="actdesc"  class="form-control" style="height:100%" required = "required" >
	  <option value="">--Select Activity Data--</option>
	  </select>
      </div>
       
      <div class="form-group">
       <label>Sub Activity Description</label>
       <input type="text" class="form-control" required="required" autocomplete = "off" name="sbactdesc">
      </div>
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno" required="required" autocomplete = "off" name="seqno" >
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
 <div id="editPhysubactModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post"  action="${pageContext.request.contextPath}/updatesubactData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Physical-Sub Activity</h4>
      <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">   
     <div class="form-group">
       <label>Head Code & Desc</label>
      <input type="text" disabled="disabled" class="form-control" id= "headcode1"  autocomplete = "off" name="headcode1">
	  </div>
        
      <div class="form-group">
       <label>Activity Code and Description</label>
       <input type="text" disabled="disabled" class="form-control" id= "actdesc1"  autocomplete = "off" name="actdesc1">
       </div>
       
      <div class="form-group">
       <label>Sub Activity Description</label>
       <input type="text" class="form-control" id= "sbactdesc1" required="required" autocomplete = "off" name="sbactdesc1">
      </div>
      <input type="hidden"  id ="subactivityId" name="id">
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno1" required="required" autocomplete = "off" name="seqno1" >
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
 
 <div id="deletePhysubactModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
     <form method="post" action="${pageContext.request.contextPath}/deletephysubactData">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Physical-Sub Activity</h4>
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
