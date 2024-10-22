<%@ include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">
<script src='<c:url value="/resources/js/projectMapUser.js"/>'></script>
 <script>
/*  $(function(){
     $("#projectselect").hide() 
  }); */
  
    function getMultipleSelectedValue()
    {
      var x = document.getElementById("projectselect");	
      var x=document.getElementById("project");
      var selectedText ="";   //= x.options[x.selectedIndex].text;
     
      for (var i = 0; i < x.options.length; i++) 
      {
         if(x.options[i].selected ==true)
         {
        	 selectedText+=x.options[i].text+",";
         }
      }
     // document.getElementById("projectselect").value=selectedText;
      document.getElementById("projectselect").innerHTML=selectedText;
     
    }
    </script>

<div class="card">
<div class="offset-md-2"></div>
	<div class="table">
		<c:if test="${newuserid ne null}"> 
		 	<div class="col"><p class="badge badge-success" style="font-size: 15px;">Account is activated successfully. with user Id: &nbsp; ${newuserid} and password: &nbsp; ${newpassword} . Kindly note it down for future use.</p></div>
		</c:if>
		<form:form  name="projectmap" id="projectmap" modelAttribute="userProjectMap" action="newUserProjectAssign" method="post">
		<c:if test="${message ne null}"> 
		 	<div class="col"><p class="badge badge-success"> ${message} </p></div>
		</c:if>
		<%-- <div class="col">
		<label class="badge badge-danger"><c:out value="${message}" ></c:out></label>
		</div> --%>
			<div class="col" style="text-align: center;">
				<h5>User Project Map</h5>
			</div>

			<div class="form-group row">
				<label for="application" class="col-sm-2 col-form-label">Application</label>
				<div class="col-sm-3">
				
					<label>WDC-PMKSY</label>
				</div>
			</div>
	
			<div class="form-group row state">
				<label for="state" class="col-sm-2 col-form-label">State</label>
				<div class="col-sm-3">
					<label><c:out value="${sessionScope.stName}" /></label>
				</div>
			</div>
			
			<div class="form-group row district">
				<label for="district" class="col-sm-2 col-form-label">District <span style="color: red;">*</span></label>
				<div class="col-sm-3">
				
					 <select name="district" title="District" id="district" onchange="this.form.submit();" class="form-control">
						<option value="">-- Select --</option>
						<c:if test="${not empty districtList}">
               					<c:forEach items="${districtList}" var="lists">
               						<c:if test="${lists.key eq district}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne district}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
							</c:if> 
					</select> 
					
				</div>
			</div>
			
			
			<div class="form-group row">
				<label for="inputPassword" class="col-sm-2 col-form-label">Users <span style="color: red;">*</span></label>
				<div class="col-sm-3">
					<select name="user" id="user" title="users" class="form-control" onchange="this.form.submit();">
						<option value="">-- Select Users --</option>
						<c:if test="${not empty useridList}">
               					<c:forEach items="${useridList}" var="lists"> 
               					<c:if test="${lists.key eq regId}">
       								<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       							</c:if>	
       							<c:if test="${lists.key ne regId}">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
       							</c:if>	
								</c:forEach>
						</c:if>
					</select>
				</div>
				<div class="col-sm-6"><c:if test="${projConcat ne null}"> <label ><p class="badge badge-info"> Already Assign project- </p> <c:out value="${projConcat}" /> </label></c:if></div>
			</div>
			
			<div class="form-group row">
				<label for="project" class="col-sm-2 col-form-label">Project <span style="color: red;">*</span></label>
				<div class="col-sm-3">
					 <select name="project" title="Project" id="project" onchange="getMultipleSelectedValue();" class="form-control" multiple style="height:100%">
						<option value="">-- Select Project--</option>
						<c:if test="${not empty projectList}">
               					<c:forEach items="${projectList}" var="lists">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
								</c:forEach>
						</c:if>
					</select> 
				</div>
				<div class="col-sm-6">
					<div class="badge badge-warning" id="projectselect">
					
					</div>
				</div>
			</div>

			<div class="form-group row">
				<label for="inputPassword" class="col-sm-2 col-form-label">Roles</label>
				<div class="col-sm-3">
					<select name="role" title="Roles" id="roleId" class="form-control" disabled="disabled">
						<!-- <option value=" ">-- Select Roles --</option> -->
						<c:forEach items="${allRoleList}" var="roleList">
						 <c:if test="${roleList.key eq roleId}">
							<option value="<c:out value="${roleList.key}"/>" selected="selected"> <c:out value="${roleList.value}" /> </option>
						</c:if>	
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="inputPassword" class="col-sm-6 col-form-label"><span style="color: red;">*</span>
					Indicates Mandatory</label>
				<div class="col-sm-3"></div>
			</div>

			<div class="form-group row">
				<div class="offset-sm-2"></div>
				<div class="col-sm-3">
				
					<input type="button" name="save" id="save" value="Save" class="btn btn-info" onclick="saveRoleUserMap();" />
					<!-- <input type="button" title="Update" id="update" value="Update" name="update" class="btn btn-info" onClick="javascript:updateRoleUserMap(this.form);"  />
					
					<input type="button" title="Cancel" name="cancel" id="cancel" value="Cancel" disabled="disabled" class="btn btn-info" 
						onclick="javascript:cancelRoleAssign(this.form);" />  -->
				</div>
			</div>

		</form:form>


	</div>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		$(".sidebar-btn").click(function() {
			$(".wrapper").toggleClass("collapse");
		});
	});
</script>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>