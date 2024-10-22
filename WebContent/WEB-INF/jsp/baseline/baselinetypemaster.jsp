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


<script src='<c:url value="/resources/js/baselinetypemaster.js" />'></script>
<body>
<div class="table-wrapper">
<div class="table-title">
                <div class="row">
                
                    <div class="col-sm-6"><h5>Baseline Type Master Form <b>(m_bls_out_type)</b></h5>
                    
     </div>
     <div class="col-sm-6">
      <a href="#addbaselinetypemasterModal" id = "add" class="btn btn-success" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>
      
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

 <th>Seq no.</th>
 <th>Baseline Type Code</th>
 <th>Baseline Type Description</th>
 <th>SeqNO </th>
 <th>Edit</th>
 <th>Delete</th>
 </tr>
</thead> 

<tbody>
<c:forEach var="list" varStatus="status" items="${baselinetype}">
					 <tr>	
					 	  <td>
					 	  ${status.count}
					 	  </td>
					 	  
					 	  <td>	  
					 	  <c:out	value="${list.typeCode }"></c:out>
						  </td>
									  
						 <td>	  
					 	  <c:out	value="${list.description }"></c:out>
						 </td>
						 
						 <td>	  
					 	 <c:out	value="${list.seqNo }"></c:out>
						 </td>
						
						 <td>
						<a href="#editbaselinetypeModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                         <input type="hidden" id="id" value="${list.MBlsOutTypeIdPk}">
                         </td>
                        
                         <td>
                        <a href="#deletebaselinetypeModal" id="delete" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>  
						<input type="hidden" id="id" value="${list.MBlsOutTypeIdPk}">
						</td>		  
						</tr>
						</c:forEach>
									  

</tbody>
</table>
          

<div id="addbaselinetypemasterModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post" action="${pageContext.request.contextPath }/savebaseLinetypeMasterData">
     <div class="modal-header">      
      <h4 class="modal-title">Add Baseline Type Master Data</h4>
      <button type="button" class="close" onClick="window.location.reload();"  data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body"> 
     
     
     <div class="form-group">
       <label>Type Code</label>
       <input type="text" class="form-control" required="required" autocomplete = "off" name="typecode">
      </div>
     
     <div class="form-group">
       <label>Type Description</label>
       <input type="text" class="form-control" required="required" autocomplete = "off" name="desc">
      </div>
     
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno" required="required" autocomplete = "off" name="seqno" >
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
 <div id="editbaselinetypeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
    <form method="post"  action="${pageContext.request.contextPath}/updatebaselinetypeData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Baseline Type Master Data</h4>
      <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
     </div>
     <div class="modal-body">   
     <div class="form-group">
       <label>Baseline Type Code</label>
       <input type="text" class="form-control" id="typecode" required="required" autocomplete = "off" name="typecode">
      </div>
        <input type="hidden"  id ="baselinetypeId" name="id">
      <div class="form-group">
       <label>Baseline Type Description</label>
       <input type="text" class="form-control" id="desc" required="required" autocomplete = "off" name="desc">
      </div>
     
      <div class="form-group">
       <label>Sequence No</label>
       <input type="text" class="form-control" id="seqno1" required="required" autocomplete = "off" name="seqno" >
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
 <div id="deletebaselinetypeModal" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
     <form method="post" action="${pageContext.request.contextPath}/deletebaselinetypeData">
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