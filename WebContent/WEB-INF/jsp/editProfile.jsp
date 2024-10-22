<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

 <%-- <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />"> --%>
	<%-- <script src='<c:url value="/resources/js/userRoleMap.js" />'></script> --%>
  

 <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">
	<script src='<c:url value="/resources/js/userRoleMap.js" />'></script>
  <script src='<c:url value="/resources/js/cpas.js" />'></script>

<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">
<script src='<c:url value="/resources/js/userRoleMap.js" />'></script>
 <script src='<c:url value="/resources/js/profile.js" />'></script> 

<div class ="card">
<div class="offset-md-2"></div>
<div class="col" style="text-align:center;"><h5>Edit Profile</h5></div>
<div class="table">


 <form:form id="editprofile" name="editprofile" modelAttribute="userReg" action="profileSave" method="post">
 		<table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<tr><c:if test="${message ne null}"><p class="badge badge-success"> ${message} </p></c:if></tr>
          <tr class="tabs">
            <td width="162%" colspan="2" style="padding-left:20px">Request For Level User Account </td>
            <td width="19%" class="tabs">&nbsp;</td>
          </tr>
        </table>
  
  		<input type="hidden" name="regID" id="regId" value="${regId}"/>
  		<input type="hidden" name="userType" id="userType" value="${userType}"/>
  	    <input type="hidden" name="userId" id="userId" value="${userId}"/>
      <table width="50%" border="0"  >
      <tr>
          <td class="label">User Id:</td>
          <td class="field"><c:out value="${userId}" /></td>
        </tr>
        <tr>
          <td class="label">Registration Id:</td>
          <td class="field">PMKSY-000<c:out value="${regId}" /></td>
        </tr>
        <tr>
          <td class="label">State Name:</td>
          
          <td class="field">
          			<c:if test="${userType eq 'ADMIN'}">
     					<select id="userState"  name="userState" style="height: 100px;" aria-labelledby="menu1" class="form-control form-control-sm" multiple="multiple">
							<option value="">--Select--</option>
				            <c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 <c:forEach items="${list.value}"  var="stateList" >
               					<option value="<c:out value='${stateList.statecode}'/>" ${stateList.selected}><c:out value="${stateList.statename}" /></option>
               					</c:forEach> 
       							</c:forEach>
							</c:if> 
						</select>
					</c:if>
					
					<c:if test="${userType eq 'DL'}">
     					<select id="userState" role="DL"  name="userState" style="height: 100px;" aria-labelledby="menu1" class="form-control form-control-sm" multiple>
							<option value="">--Select--</option>
				            <c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					<%-- <option value=""><c:out value='${list.key}'/></option> --%>
               					  <c:forEach items="${list.value}"  var="stateList" >
               					<option value="<c:out value='${stateList.statecode}'/>" ${stateList.selected}><c:out value="${stateList.statename}" /></option>
               					</c:forEach>  
       							</c:forEach>
							</c:if> 
						</select>
					</c:if>
					
					<c:if test="${userType eq 'SL'}">
     												
				            <c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 <c:forEach items="${list.value}"  var="stateList" >
               					 <c:out value='${stateList.statename}'/>
               					
               					</c:forEach> 
       							</c:forEach>
							</c:if> 
       					
					</c:if>
          			<c:if test="${userType eq 'DI'}">
         
          					<c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 <c:forEach items="${list.value}"  var="stateList" >
               					 <c:out value='${stateList.statename}'/>
               					</c:forEach> 
       							</c:forEach>
							</c:if> 
       
       				</c:if>
       				
       				<c:if test="${userType eq 'PI'}">
         
          					<c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 <c:forEach items="${list.value}" end="0" var="stateList" >
               					 <c:out value='${stateList.statename}'/>
               					</c:forEach> 
       							</c:forEach>
							</c:if> 
       
       				</c:if>
       				
          </td>
        </tr>
        
        <c:if test="${userType eq 'DI'}">
        <tr>
          <td class="label">District Name:</td>
          <td>
          			<c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 <c:forEach items="${list.value}"  var="stateList" >
               					 
               					<c:out value='${stateList.districtname}'/>
               					</c:forEach> 
       							</c:forEach>
					</c:if> 
		</td>
       </tr>
       </c:if>
       
       <c:if test="${userType eq 'PI'}">
	       <tr>
	          <td class="label">District Name:</td>
	          <td class="field">
	          		<c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 	<c:forEach items="${list.value}" end="0"  var="stateList" >
               							  <c:out value='${stateList.districtname}'/>
               						</c:forEach> 
       							</c:forEach>
					</c:if>
	          
	          </td>
	       </tr>
	       <tr>
	          <td class="label">Project Name:</td>
	          <td class="field">
	          
	          <select id="userProject" role="PI"  name="userProject" style="height: 100px;" aria-labelledby="menu1" class="form-control form-control-sm" multiple="multiple" disabled>
							<option value="">--Select--</option>
				            <c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					<%-- <option value=""><c:out value='${list.key}'/></option> --%>
               					  <c:forEach items="${list.value}"  var="stateList" >
               					<option value="<c:out value='${stateList.projectcode}'/>" ${stateList.selected}><c:out value="${stateList.projectname}" /></option>
               					</c:forEach>  
       							</c:forEach>
							</c:if> 
						</select>
	          
	          </td>
	       </tr>
       </c:if>
       
      </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" colspan="2" style="padding-left:20px">User`s contact information </td>
           </tr>
          <tr> 
            <td width="100%" >___________________________________________________________________________________________________________</td>
          </tr>
        </table>
  	
      <table>
     	<c:forEach items="${list}" var="item">
        <tr>
          <td class="label">Name <span style="color: red;">*</span></td>
          <td class="field"><input type="text" name="userName" id="userName" value="<c:out value="${item.userName}" />" /></td>
        </tr>
        <tr>
          <td class="label">Designation <span style="color: red;">*</span></td>
          <td class="field"> <input type="text" name="userDesignation" id="userDesignation" value="<c:out value="${item.designation}" />" /></td>
        </tr>
        <tr>
          <td class="label">Department <span style="color: red;">*</span></td>
          <td class="field"> <input type="text" name="userDepartment" id="userDepartment" value="<c:out value="${item.department}" />" /></td>
       </tr>
       <tr>
          <td class="label">Email ID <span style="color: red;">*</span> </td>
          <td class="field"><input type="text" name="userEmailId" id="userEmailId" value="<c:out value="${item.email}" />"  onblur="validateEmail(this);" /> </td>
       </tr>
       <tr>
          <td class="label">Phone No. </td>
          <td class="field"><input type="text" name="userPhoneNo" id="userPhoneNo" maxlength="11" min="11" value="<c:out value="${item.phoneNo}" />" onchange="PhoneNoValidation();" /> </td>
       </tr>
       <tr>
          <td class="label">Mobile No. <span style="color: red;">*</span></td>
          <td class="field"><input type="text" name="userMobileNo" id="userMobileNo" maxlength="10" min="10" value="<c:out value="${item.mobileNo}" />" onkeypress="return isNumberKey(event);" onblur="mobile1();"/> </td>
       </tr>
       <tr>
          <td class="label">Fax No.:</td>
          <td class="field"><input type="text" name="userFaxNo" id="userFaxNo" value="<c:out value="${item.faxNo}" />" onkeypress="return isNumberKey(event)" /> </td>
       </tr>
       <tr>
          <td class="label">Address <span style="color: red;">*</span></td>
          <td> <textarea id="userAddres" name="userAddres"><c:out value="${item.curAddress}" /> </textarea> </td>
       </tr>
       
     </c:forEach>
      </table>
     
      <table>
       <tr>
           
        	<td align="left">
        	<input type="hidden" value="<%=app.util.Util.getCaptchaSession(request)%>" name="CAPTCHAcode" id="CAPTCHAcode"/>
        	<input type="button" class="btn btn-info" id="edit" name="edit" value='Update Profile' onclick="javascript: saveprofile();" /></td>
        	<!-- <td align="left"><input type="button" class="btn btn-info" id="print" name="print" value='Print' onclick="javascript:print();" /></td> -->
        	
        	 <c:forEach items="${list}" var="item">
        	<c:set var="passParam" value="${item.userId}###${item.userType}***${item.userName} "/>
        	<c:if test='${sessionScope.loginid!="USERADMIN"}'>
             <td align="left"><input type="button" class="btn btn-info" id="pwdchange" name="pwdchange" value="Change Password" onclick="javascript:changeCurrentPassword('<c:out value="${passParam}"/>');" /></td>
       		</c:if>
            </c:forEach>  
        </tr>
      
      </table>
      
      </form:form>
		

	</div>
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