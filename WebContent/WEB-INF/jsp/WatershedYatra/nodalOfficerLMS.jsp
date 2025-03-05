<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>

<script type="text/javascript">

function validatenumber() { 
	var x = document.getElementById("mob").value; 
	if (x.length !== 10) { 
		alert("Mobile number should be 10 digits"); 
		document.getElementById("mob").value='';
		//return false; 
	} 
}

function validateEmail(emailField)
{
   // var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var reg =  /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	alert('kdy='+emailField.value);
    if (reg.test(emailField.value) == false) 
    {
        alert('Please enter a valid email address');
        document.getElementById("email").value='';
        document.getElementById("email").focus();
       // return false ;
    }
}

function validateemail1() { 
	var email = document.getElementById("email").value; 
	var emailPattern = /^[a-zA-Z0-9._+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,}$/; 
	if (!emailPattern.test(email)) 
	{ 
		alert("Please enter a valid email address"); 
		document.getElementById("email").value='';
		//return false; 
	} 
	//return true; 
}


</script>

<style> 
input[type=text] {
  width: 100%;
/*   padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid black;
  border-radius: 4px; */
}

input[type=email] {
  width: 100%;
 /* padding: 12px 20px;
   margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid black;
  border-radius: 4px; */
}


</style>


</head>
<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Details of Nodal Officers</u></h4> </div>
		<form name="updateConvergence" id="updateConvergence">
			<lable class="message badge badge-danger error"></lable>
			<hr/>
			<div class="row">
			
			<div class="form-group col-3">
      			<label for="district"><b> Level:</b> </label>
      			<span class="projectError"></span>
      			<select class="form-control district" id="level" name="level" >
    				<option value="">--Select--</option>
    				<c:forEach items="${level}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		
			<div class="form-group col-3">
			<label for="state">	<b> State Name:</b> </label>
			<span class="projectError"></span> <br/>
			<c:out value="${stateName}"></c:out>
			</div>
			
			
    		<div class="form-group col-3" >
    		<c:if test="${userType== 'SL' }">
      			<label class="d-none yeark"><b> District:</b> </label>
      			<span class="projectError"></span>
      			<select class="form-control col d-none yeark" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</c:if>
    			
    		<c:if test="${userType == 'PI'}">
        <label class="d-none yeark"><b> District:</b></label>
        <span class="projectError"></span>
        </br><label class="d-none yeark"><c:out value="${distName}"></c:out></label>
        <input type="hidden" id="district" name="district" value="${distCode}">
    </c:if>
    		</div>
    		
    		
    		<div class="form-group col-3" >
    		<c:if test="${userType== 'SL' }">
    			<label class="d-none mont"><b> Block:</b> </label>
      			<span class="activityError"></span>
      			<select class="form-control col d-none mont" id="block" name="block" >
    				<option value="">--Select Block--</option>
    			</select>
    			</c:if>
    			<c:if test="${userType== 'PI' }">
	    		<label class="d-none mont"><b>Block: </b></label>
      			<span class="activityError"></span>
      			<select class="form-control col d-none mont" id="block" name="block" >
    				<option value="">--Select Block--</option>
    				<c:forEach items="${blkList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
	    		</c:if>	
    		</div>
    		<!-- <div class="form-group col-3">
    			<label for="activity">Gram Panchayat Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="grampan" name="grampan" >
    				<option value="">--Select Gram Panchayat Name--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    			<label for="activity">Village Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="village" name="village" >
    				<option value="">--Select Village Name--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    			<label for="activity">Location(Nearby/Milestone) </label>
      			<input type="text" name="location" id="location"  />
    		</div> -->
    		
    		</div>
    		
    		<br/><br/>
    		
    		<div class="row">
			
			<div class="form-group col-3">
      			<label for="district"><b>Name : </b></label>
      			 <input type="text" id="name" name="name" placeholder="Enter Name" autocomplete="off" required>
      			
    		</div>
    		
			<div class="form-group col-3">
				<label for="state"><b>	Designation:</b> </label>
				<span class="projectError"></span>
				<input type="text" id="designation" name="designation" placeholder="Enter Designation" autocomplete="off" required>
			
			</div>
			
    		<div class="form-group col-3">
      			<label for="district"><b> Mobile:</b> </label>
      			<span class="projectError"></span>
      			<input type="text" id="mob" name="mob" placeholder="Enter Mobile Number" autocomplete="off" min="10"
								pattern="^\d{10}$" maxlength="10"  
								                     oninput="this.value=this.value.replace(/[^0-9]/g,'');" onblur="validatenumber();"   required />
    		</div>
    		<div class="form-group col-3">
    			<label for="activity"> <b> Email Id:</b> </label>
      			<input type="text" id="email" name="email" placeholder="Enter Email Id" autocomplete="off" onblur="return validateemail1();" required>
    		</div>
    		
    		</div>
    		
    		<br/>
     		<br/>
    		
			 <div class="form-row">
				<div class="form-group col-8">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="btnSave" name="btnSave"  value ="Save"/>
     			</div>
     		</div> 
     		
     		
     		
     		<br/>
     		<br/>
     		<br/>
     		<br/>
     		
     		
     		
     		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>Draft List of Nodal Officer</u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="convergenceTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th style="width:2%">S.No. &nbsp; <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /></th> 
								<th style="width:5%">Level</th>
								<th style="width:5%">State Name</th>
								<th style="width:5%">District Name</th>
								<th style="width:5%">Block Name</th>
								<th style="width:5%">Name</th>
								<th style="width:5%">Designation</th>
								<th style="width:5%">Mobile</th>
								<th style="width:5%">Email Id</th>
								
							</tr>

						</thead>
						
						<c:set var="proj" value="" />
						<c:forEach items="${draftList}" var="dataV" varStatus="count">
							<tr>
							 	<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${dataV.nodal_id}"  name="${dataV.nodal_id}" value="${dataV.nodal_id}"/> </td>
								<c:if test="${dataV.level eq 'state'}">
									<td> <c:out value="State" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'district'}">
									<td> <c:out value="District" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'block'}">
									<td> <c:out value="Block/Project" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'village'}">
									<td> <c:out value="Village/Van Standing Point" /></td>	
								</c:if>
								<c:choose>
									<c:when test="${proj ne dataV.stname}">
										<c:set var="proj" value="${dataV.stname}" />
										<td> <c:out value="${dataV.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<td> <c:out value="${dataV.district}" /></td>
								<td> <c:out value="${dataV.blockname}" /></td>
								<td> <c:out value="${dataV.nodal_name}" /></td>
								<td> <c:out value="${dataV.designation}" /></td>
								<td> <c:out value="${dataV.mobile}" /></td>
								<td> <c:out value="${dataV.email}" /></td>
								
							</tr>
						</c:forEach>
						<c:if test="${draftListSize eq 0}">
							<tr>
								<td align="center" colspan="9" class="required" style="color:red;">Data Not Found</td>
							</tr>
						</c:if>
		</table>
		</div>
		</div>
		
		<c:if test="${draftListSize gt 0}">
		<div class="form-row">
				<div class="form-group col">
     				<input type="button" class="btn btn-info" id="updateapprove" name="updateapprove" value ="Approve Nodal Officer"/>
     				<input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete Draft Nodal Officer"/>
     			</div>
     		</div>
     	</c:if>
     	
     	
     	<br/>
     		<br/>
     		<br/>
     		<br/>
     		
     		
     		
     		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>Approved List of Nodal Officer</u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="convergenceTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th style="width:2%">S.No. </th> 
								<th style="width:5%">Level</th>
								<th style="width:5%">State Name</th>
								<th style="width:5%">District Name</th>
								<th style="width:5%">Block Name</th>
								<th style="width:5%">Name</th>
								<th style="width:5%">Designation</th>
								<th style="width:5%">Mobile</th>
								<th style="width:5%">Email Id</th>
								
							</tr>

						</thead>
						
						<c:set var="proj" value="" />
						<c:forEach items="${completetList}" var="dataV" varStatus="count">
							<tr>
							<c:if test="${userType== 'SL' }">
							<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${dataV.nodal_id}"  name="${dataV.nodal_id}" value="${dataV.nodal_id}"/> </td>
							</c:if>
							
							 <c:if test="${userType== 'PI' }">	
							 <td><c:out value='${count.count}' />  </td>
							 </c:if>	
							 	
								<c:if test="${dataV.level eq 'state'}">
									<td> <c:out value="State" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'district'}">
									<td> <c:out value="District" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'block'}">
									<td> <c:out value="Block/Project" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'village'}">
									<td> <c:out value="Village/Van Standing Point" /></td>	
								</c:if>
								<c:choose>
									<c:when test="${proj ne dataV.stname}">
										<c:set var="proj" value="${dataV.stname}" />
										<td> <c:out value="${dataV.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<td> <c:out value="${dataV.district}" /></td>
								<td> <c:out value="${dataV.blockname}" /></td>
								<td> <c:out value="${dataV.nodal_name}" /></td>
								<td> <c:out value="${dataV.designation}" /></td>
								<td> <c:out value="${dataV.mobile}" /></td>
								<td> <c:out value="${dataV.email}" /></td>
								
							</tr>
						</c:forEach>
						<c:if test="${completeListSize eq 0}">
							<tr>
								<td align="center" colspan="9" class="required" style="color:red;">Data Not Found</td>
							</tr>
						</c:if>
		</table>
		</div>
		</div>
     	<c:if test="${userType== 'SL' }">
     	<c:if test="${completeListSize gt 0}">
		<div class="form-row">
				<div class="form-group col">
     				<input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete Draft Nodal Officer"/>
     			</div>
     		</div>
     	</c:if>
     	</c:if>
     	
     	
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<c:if test="${userType== 'SL' }">
	<script src='<c:url value="/resources/js/WatershedYatraVillage.js" />'></script>
	</c:if>
	
	<c:if test="${userType== 'PI' }">
	<script src='<c:url value="/resources/js/WatershedYatraPIAVillage.js" />'></script>
	</c:if>
	
	
</body>
</html>