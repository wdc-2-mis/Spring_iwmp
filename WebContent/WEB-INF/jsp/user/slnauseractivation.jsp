<%@include file="/WEB-INF/jspf/header2.jspf"%>
  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
 <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">
	<script src='<c:url value="/resources/js/slnaUserActivation.js" />'></script>
  
<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>User Search</h5></div>
 <form autocomplete="off" name="usersearch" id="usersearch" modelAttribute="userReg" >
 <label class="message badge badge-danger" id="error" name="error"></label>
 <c:if test="${newuserid ne null}">
        	<label class="message badge badge-<c:if test="${status == 'Activate'}">success</c:if><c:if test="${status == 'InActivate'}">warning</c:if><c:if test="${status == 'Delete'}">danger</c:if>"><font color='red'><c:out value="${username}" />'s </font> account is <c:if test="${status == 'Activate'}">activated</c:if><c:if test="${status == 'InActivate'}">inActivated</c:if><c:if test="${status == 'Delete'}">deleted</c:if> <c:if test="${newpassword ne null}">with password: <c:out value="${newpassword}" /> </c:if> successfully !</label>
</c:if>
      <table class="table">
        <tr>
          <td class="label">User Id</td>
          <td class="field"><input type="text" name="userId" id="userId" class="form-control" size="20"/></td>
          <td class="label">Name</td>
          <td class="field"><input type="text" name="userName" id="userName" class="form-control" size="20"/></td>
        </tr>
        <tr>
          <td class="label">Designation</td>
          <td class="field"><input type="text" name="designation" id="designation" class="form-control"  size="20"/></td>
          <td class="label">Department</td>
          <td class="field"><input type="text" name="department" id="department" class="form-control" size="20"/></td>
       </tr>
        <tr>
          <td class="label">User Email</td>
          <td class="field"><input type="text" name="email" id="email" class="form-control" size="20"/></td>
          <td class="label">User Type<span class="required"></span></td>
          <td class="field">
              <select name="userType" id="userType" class="form-control">
                  <option value="">-- Select --</option>
                  <c:if test="${userTypeList != null}">
                     <c:forEach var="entry" items="${userTypeList}">
                        <option value="${entry.key}" <c:out value="${entry.key=='PI'?'selected':'' }"/>><c:out value="${entry.value}"/></option>
                     </c:forEach>
                  </c:if>
              </select>
          </td>
        </tr>
        <tr>
          <td class="label">Status</td>
          <td class="field">
              <select name="status" id="status" class="form-control">
                  <option value="">-- Select --</option>
                  <c:if test="${userStatusList != null}">
                     <c:forEach var="entry" items="${userStatusList}">
                       <option value="${entry.key}" ><c:out value="${entry.value}"/></option>
                     </c:forEach>
                  </c:if>
              </select>
          </td>
        </tr>
        <tr>
        
        	
        	<td class="label">&nbsp;</td>
        	<td class="label">&nbsp;</td>
        	<td class="label">&nbsp;</td>
        
        	<td align="right"><input type="button" class="btn btn-info" id="search" onclick="validate(this);" name="search" value='Search' align="right"/></td>
       </tr>
      </table>
		</form>


	</div>
	
	 <c:if test="${userList  ne null}">
	 
  	 
      	
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>User Search Result</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
        <table class="table">
          <tr>
            <td><table id="example" class="table">
              <tr>
              	<td class="displ"><nobr>Registration Id</nobr></td>
                <td height="26" class="displ">State Code</td>
                <td class="displ">State</td>
                <td class="displ">User Type</td>
                <td class="displ">User Id</td>
                <td class="displ">User Name</td>
                <td class="displ">Designation</td>
                <td class="displ">Department</td>
                <td class="displ">Email</td>
                <td class="displ">Phone</td>
                <td class="displ">&nbsp;</td>
                <td class="displ">&nbsp;</td>
                </tr>
                <c:forEach var="list" items="${userList}">
					 <c:forEach var="listUser" items="${list.value}" >	
					 <c:if test="${listUser.regId eq null}">
					 <tr><td>Data Not found </td></tr>
					 </c:if>	
					 <c:if test="${listUser.regId ne null}">		
									<tr><td><c:out	value="WDC-PMKSY-000${listUser.regId }"></c:out></td>
									<td>
									<c:if test="${listUser.iwmpUserMaps ne null}">
									<c:forEach var="userMap" items="${listUser.iwmpUserMaps}">
									
									<c:out	value="${userMap.iwmpState.stCode}"></c:out>
									
									
									</c:forEach>
									</c:if>
									<c:if test="${listUser.iwmpUserMaps eq null}">
									<c:out value="" ></c:out>
									</c:if> 
									
									  </td>
									<td>
									<c:if test="${listUser.iwmpUserMaps ne null}">
									<c:forEach var="userMap" items="${listUser.iwmpUserMaps}">
									
									<c:out	value="${userMap.iwmpState.stName}"></c:out>
									
									
									</c:forEach>
									</c:if>
									<c:if test="${listUser.iwmpUserMaps eq null}">
									<c:out value="" ></c:out>
									</c:if>  
									</td>
									<td><c:if test="${listUser.userType == 'PI'}">PIA</c:if><c:if test="${listUser.userType == 'SL'}">SLNA</c:if>
									<c:if test="${listUser.userType == 'DI'}">WCDC</c:if>
									<c:if test="${listUser.userType == 'DL'}">DoLR</c:if></td>
									<td><c:out	value="${listUser.userId }"></c:out></td>
									<td><c:out	value="${listUser.userName }"></c:out></td>
									<td><c:out	value="${listUser.designation }"></c:out></td>
									<td><c:out	value="${listUser.department }"></c:out></td>
									<td><c:out	value="${listUser.email }"></c:out></td>
									<td><c:out	value="${listUser.mobileNo }"></c:out></td>
									<c:if test="${listUser.status eq 'Active' || listUser.status eq 'ACTIVE' }">
									<td><button class="btn btn-warning" id="btnInActivate" onclick="inAactivateUser('${listUser.regId}','${listUser.userType}');" name="btnInActivate">InActivate</button></td>
									</c:if>
									<c:if test="${listUser.status ne 'Active'}">
									<td><button class="btn btn-success" id="btnActivate" onclick="activateUser('${listUser.regId}','${listUser.userType}');" name="btnActivate" >Activate</button></td>
									</c:if>
									<c:if test="${listUser.status eq 'New' || listUser.status eq 'NEW'}">
									<td><button class="btn btn-danger" id="btnDelete" name="btnDelete" onclick="deleteUser('${listUser.regId}');">Delete</button></td>
									</c:if>
									</tr>
									</c:if>
									</c:forEach>
									
							</c:forEach>
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
      
  </c:if>
  <c:if test="${userList.size()<=0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
	
	</div>
	</div>
</div>
</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

</script>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>