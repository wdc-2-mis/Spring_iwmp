<%@include file="/WEB-INF/jspf/header2.jspf"%>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">
<script src='<c:url value="/resources/js/userRoleMap.js" />'></script>

<div class="card">
<div class="offset-md-2"></div>
	<div class="table">

		<form autocomplete="off" name="userrolemap" id="userrolemap" modelattribut="userRoleMap">
		<div class="col">
		<label class="message badge badge-danger" id="error" name="error"></label>
		<label class="message badge badge-<c:if test="${status == 'success'}">success</c:if><c:if test="${status == 'fail'}">danger</c:if>"><c:out value="${message}" ></c:out></label>
		</div>
			<div class="col" style="text-align: center;">
				<h5>User Role Map</h5>
			</div>
 <c:if test="${newuserid ne null}">
        	
        	<label class="message badge badge-<c:if test="${status == 'Activate'}">success</c:if><c:if test="${status == 'InActivate'}">warning</c:if><c:if test="${status == 'Delete'}">danger</c:if>"><font color='red'><c:out value="${username}"/>'s</font> account is <c:if test="${status == 'Activate'}">activated</c:if><c:if test="${status == 'InActivate'}">inActivated</c:if><c:if test="${status == 'Delete'}">Deleted</c:if>  successfully. His/Her user id: <font color='red'><c:out value="${newuserid}" /></font> and password: <font color='red'><c:if test="${newpassword eq null}">with old password </c:if> <c:if test="${newpassword ne null}">  <c:out value="${newpassword}" /></c:if></font></label>
        	
        	</c:if>
			<div class="form-group row">
				<label for="application" class="col-sm-2 col-form-label">Application</label>
				<div class="col-sm-3">
				<%--  <select name="application" title="Application" id="application"
						onchange="getUserAssign(this)" class="form-control">
						<option value="">-- Select Application --</option>
						<c:forEach items="${allApplicationList}" var="app">
							<option value="<c:out value="${app.key}"/>"><c:out
									value="${app.value}" /></option>
						</c:forEach>
					</select>  --%>
					<label>WDC-PMKSY</label>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="userType" class="col-sm-2 col-form-label">UserType *</label>
				<div class="col-sm-3">
					 <select name="usertype" title="User Type" id="userType"
						onchange="getUser(this);userTypeChange();" class="form-control">
						<option value=" ">-- Select Type --</option>
						<c:forEach items="${userType}" var="user">
							<option value="<c:out value="${user.key}"/>"><c:out
									value="${user.value}" /></option>
						</c:forEach>
					</select> 
				</div>
			</div>
			
			<div class="form-group row state d-none">
				<label for="state" class="col-sm-2 col-form-label">State *</label>
				<div class="col-sm-3">
					 <select name="state" title="State" id="state"
						onchange="getDistrict();getStateUsers();" class="form-control">
						<option value=" ">-- Select --</option>
						<c:forEach items="${stateList}" var="state">
							<option value="<c:out value="${state.key}"/>"><c:out
									value="${state.value}" /></option>
						</c:forEach>
					</select> 
				</div>
			</div>
			
			<div class="form-group row district d-none">
				<label for="district" class="col-sm-2 col-form-label">District *</label>
				<div class="col-sm-3">
					 <select name="district" title="District" id="district"
						onchange="getDistrictUsers();getProjectByDistrict();" class="form-control">
						<option value=" ">-- Select --</option>
					
					</select> 
				</div>
			</div>
			
			
			<div class="form-group row">
				<label for="inputPassword" class="col-sm-2 col-form-label">Users *</label>
				<div class="col-sm-3">
					<select name="user" id="user" title="Users" onchange="getProjectByUser();getRoleAssigned();"
						class="form-control">
						<option value=" ">-- Select Users --</option>
					</select>
				</div>
			</div>
			
			<div class="form-group row project d-none">
				<label for="project" class="col-sm-2 col-form-label">Project *</label>
				<div class="col-sm-4">
					 <select name="project" title="Project" id="project"
						onchange="checkFieldProject();" class="form-control" multiple style="height:100%">
						<option value=" ">-- Select Project--</option>
						
					</select> 
				</div>
			</div>

			<div class="form-group row">
				<label for="inputPassword" class="col-sm-2 col-form-label">Roles *</label>
				<div class="col-sm-3">
					<select name="role" title="Roles" id="roleId"
						class="form-control" onchange="checkFieldroleId()">
						<option value=" ">-- Select Roles --</option>
						<c:forEach items="${allRoleList}" var="roleList">
							<option value="<c:out value="${roleList.key}"/>"><c:out
									value="${roleList.value}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="inputPassword" class="col-sm-6 col-form-label">*
					Indicates Mandatory</label>
				<div class="col-sm-3"></div>
			</div>

			<div class="form-group row">
				<div class="offset-sm-2"></div>
				<div class="col-sm-3">
					<input type="button" title="Save" id="save" name="save"
						class="btn btn-info" onClick="javascript:saveRoleUserMap();"
						value="Save" /> <input type="button" title="Update" id="update"
						name="update" class="btn btn-info"
						onClick="javascript:updateRoleUserMap(this.form);" value="Update" />
					<input type="button" title="Cancel" name="cancel" id="cancel"
						class="btn btn-info" value="Cancel" disabled="disabled"
						onclick="javascript:cancelRoleAssign(this.form);">
				</div>
			</div>

		</form>


	</div>
</div>
</div>
</div>
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