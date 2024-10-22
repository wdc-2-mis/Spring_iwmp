<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script type="text/javascript">
	function updateCrdntr(){
		document.updateCoordinator.action = "updateSLNACrdntrDetails";
		document.updateCoordinator.method = "post";
		document.updateCoordinator.submit();
	}

</script>

<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Update MIS-Coordinator of SLNA</h5></div>
<br>
<div class ="card">
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-9"  id="exportHtmlToPdf">
	<br/>
	<br/>
	<p align="right"> Updated as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<c:if test ="${not empty msg }">
		<c:if test="${msg eq 'success'}">
     			<lable class="message badge badge-success" >SLNA Coordinaters Details Updated Successfully</lable>
     		</c:if>
     		<c:if test="${msg eq 'fail'}">
     			<lable class="message badge badge-danger error">Details not Updated</lable>
     		</c:if>
	</c:if>
	<table id="example" cellspacing="0" class="table" width="50%">
  	<thead>
  		
    	<tr>
      		<th class="text-center">S.No.</th>
      		<th class="text-center">State</th>
      		<th class="text-center">Name</th>
      		<th class="text-center">E-mail (SLNA)</th>
      		<th class="text-center">Mobile</th>
      		<th class="text-center">Action</th>
      </tr>
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
		</tr>
		<c:if test="${dataListsize>0}">
		<c:set var="count" value="1" />
		 <c:forEach var="list" items="${dataList}">
		 <input type="hidden" id="ddstate${list.stcode}" name="ddstate" value="${list.stname}">
			<input type="hidden" id="ddname${list.stcode}" name="ddname" value ="${list.name}">
			<input type="hidden" id="ddemail${list.stcode}" name="ddemail" value ="${list.email}">
			<input type="hidden" id="ddmobile${list.stcode}" name="ddmobile" value ="${list.mobile}">
			<tr>
				<td align="left"><c:out value='${count}' /></td>
				<td align="left"><c:out value='${list.stname}' /></td>
				<td align="right"><c:out value='${list.name}' /></td>
				<td align="right"><c:out value='${list.email}' /></td>
				<td align="right"><c:out value='${list.mobile}' /></td>
				<td align="right"><i class="fas fa-edit" style='font-size:20px;color:green' data-toggle="modal" data-target='#updateSLNACoordinator' data-id =<c:out value ="${list.stcode}"/> id = "edit" ></i></td>	
				<c:set var="count" value="${count+1}" />
										
			</tr>
			
		</c:forEach>

<%-- 			<input type="hidden" id="ddstate" name="ddstate" value="${list.stname}"> --%>
<%-- 			<input type="hidden" id="ddname" name="ddname" value ="${list.name}"> --%>
<%-- 			<input type="hidden" id="ddemail" name="ddemail" value ="${list.email}"> --%>
<%-- 			<input type="hidden" id="ddmobile" name="ddmobile" value ="${list.mobile}"> --%>
		</c:if> 
		<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="6" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>

	</tbody>
  </table>
</div>
</div>
</div>

      <div class="modal fade" id="updateSLNACoordinator">  
    <div class="modal-dialog">  
      
      <!-- Modal content-->  
      <div class="modal-content">  
      	 <form name="updateCoordinator" id="updateCoordinator">
					<div class="modal-header">
						<h4 class="modal-title"> Update Details of SLNA Coordinator</h4>
						<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
					</div>
					<div style="position: relative;-webkit-box-flex: 1;-ms-flex: 1 1 auto;flex: 1 1 auto;padding: 1rem;">

										<div class="form-group" >
											<label >State</label>
											 <input type="text" class="form-control" title="State" disabled="disabled" id="state" name="state" style="height: 100%">
										</div>

										<div class="form-group">
											<label>Name</label> 
											<input type="text" class="form-control"  id="name" name="name" required="required" style="height: 100%">
										</div>
										<div class="form-group">
											<label>Email</label> <input type="text" class="form-control email" id="email" name="email"
												required="required" ><span id="emailError"></span>
										</div>

										<div class="form-group">
											<label>Mobile</label> <input type="text"
												class="form-control" id="mobile" required="required"
												autocomplete="off" name="mobile"><span id="mobileError"></span>
										</div>

										<input type="hidden" id="ddstcd" name="ddstcd">
								
					</div>
					<div class="modal-footer">
						<button class="btn btn-info" id="crdntrUpdate" name="crdntrUpdate" onclick ="updateCrdntr()" >Update</button>
					</div>
				</form>  
      </div>  
        
    </div>  
  </div>


<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/slnaCoordinators.js" />'></script>
</body>
</html>