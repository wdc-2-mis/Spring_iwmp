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


<script src='<c:url value="/resources/js/phyothersubcategory.js" />'></script>
<body>
<div class="table-wrapper">
<div class="table-title">
                <div class="row">
                
                    <div class="col-sm-6"><h5>Details of Physical Other Sub Category <!-- <b>(wdcpmksy_m_phy_other_activity)</b> --></h5>
                    
     </div>
     <div class="col-sm-6">
      <a href="#addphysubotherModal" id = "add" class="btn btn-success" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE147;</i> <span>Add Other Sub Category</span></a>
      
      </div>
                </div>
            </div>
           <c:if test = "${not empty message}">
            <div class="alert alert-danger">
            <label  style="color:blue; font-size:20px;">${message}</label>
          </div>
          </c:if>
<table class="table table-striped table-hover" id="othersubcatetable">
 <thead>
 <tr>

 <th>Other Sub-Category Code</th>
 <th>Head Code(head description)</th>
 <th>Other Sub Category</th>
 <th>SeqNO </th>
 <th>Status</th>
 <th>Edit</th>
 <th>Delete</th>
 </tr>
</thead> 

<tbody>
<c:forEach var="list" items="${phyothrsubcat}">
					 <tr>	
					 	  <td>	  
					 	  <c:out	value="${list.otherActivityCode}"></c:out>
						  </td>
									  
						 <td>	  
					 	  <c:out	value="${list.iwmpMPhyActivity.iwmpMPhyHeads.headDesc}"></c:out>
						 </td>
						  
						 <td>	  
					 	 <c:out	value="${list.otherActivityDesc}"></c:out>
						 </td>
						 
						 <td>	  
					 	 <c:out	value="${list.seqNo}"></c:out>
						 </td>
						 
						 <td>	  
					 	 <c:out	value="${list.status}"></c:out>
						 </td>	
						 
						 <td>
						<a href="#editPhyOthrSubCatModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                         <input type="hidden" id="id" value="${list.otherActivityCode}">
                         </td>
                        
                         <td>
                        <a href="#deletePhyOthrSubCatModal" id="delete" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						<input type="hidden" id="id" value="${list.otherActivityCode}">
						</td>		  
						</tr>
						</c:forEach>
									  

</tbody>
</table>

</div>

<div id="addphysubotherModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath }/saveOthrSubCatData">
     <div class="modal-header">      
      <h4 class="modal-title">Add Physical-Sub Activity</h4>
      <button type="button" class="close" onClick="window.close();"  data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body"> 
     <div class="form-group">
       <label>Head Code and Desc</label>
       <select  name="headcode" title="Project" id="headcode"  class="form-control" style="height:100%" required = "required" >
	  </select>
      </div>
      
      
        <div class="form-group">
        <input type ="hidden" class="form-control" id ="stCode" name = "stCode" value = "${userType}" />
       <label>Activity Code and Description</label>
       <span class="actdescError"></span>
       <select  name="actdesc" title="Project" id="actdesc"  class="form-control" style="height:100%" required = "required">
	  <option value="">--Select Activity Data--</option>
	  </select>
      </div>
       
      <div class="form-group">
       <label>Other Sub Category</label>
       <input type="text" class="form-control" required="required" autocomplete = "off" name="othrsubcatdesc">
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
      <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel" onClick="window.close();">
      <input type="submit" class="btn btn-info" id ="saveSubAct" name = "saveSubAct" value="Save">
     </div>
    </form>
   </div>
  </div>
 </div>
 
 
   <!-- Edit Modal HTML -->
 <div id="editPhyOthrSubCatModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post"  action="${pageContext.request.contextPath}/updateothrsubcatData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Other-Sub Category</h4>
      <button type="reset" class="close" onClick="window.close();" data-dismiss="modal" aria-hidden="true">&times;</button>
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
       <label>Other Sub Category</label>
       <input type="text" class="form-control" id= "oscatdesc1" required="required" autocomplete = "off" name="oscatdesc1">
      </div>
      <input type="hidden"  id ="othrsubcatId" name="id">
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
      <input type="button" class="btn btn-default" data-dismiss="modal" onClick="window.close();" value="Cancel">
      <input type="submit" class="btn btn-info" value="Save">
      
     </div>
    </form>
   </div>
  </div>
 </div>
 
 <div id="deletePhyOthrSubCatModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
     <form method="post" action="${pageContext.request.contextPath}/deleteothrsubcatData">
     <div class="modal-header">      
      <h4 class="modal-title">Delete Other-Sub Category</h4>
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
