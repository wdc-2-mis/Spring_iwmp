<%@include file="/WEB-INF/jspf/header2.jspf"%>
<script src='<c:url value="/resources/js/profile.js" />'></script> 


<div class ="card" id="userDetails">
<div class="offset-md-2"></div>
<div class="col" style="text-align:center;"><h5>Request For <c:out value="${userReg.userType eq'PI'?'PIA':userReg.userType eq 'DI'?'WCDC':userReg.userType eq 'SL'?'SLNA':'DoLR'}" /> Level User Account</h5></div>
<br/>
<form>
<hr/>
<table class="table table-responsive">
<tr>
<th>Registration Id: </th>
<c:set var="length" value="${(userReg.regId).toString().length()}"/>
 <%
    Integer len = 6-(Integer)pageContext.getAttribute("length");   //No exception.
  %> 
<td><c:out value="WDC-PMKSY-"></c:out>
      <% for(int i=0;i<len;i++){ out.print(0);} %><c:out value="${userReg.regId}"/></td>
</tr>
<tr>
<th>State: </th>
 <td> <c:forEach var="userMap" items="${userReg.iwmpUserMaps}">
<c:out value="${userMap.iwmpState.stName}"/><br/>
</c:forEach> </td> 
<th>District: </th>
 <td><c:forEach var="userMap" items="${userReg.iwmpUserMaps}">
<c:out value="${userMap.iwmpDistrict.distName}"/>
</c:forEach> </td>
</tr>

<%-- <tr>
<th>User Id: </th>
<td><c:out value="${userReg.userId}"/></td>
</tr> --%>
<tr><td colspan="4">User contact information<hr/></td></tr>
<tr>
<th>Name: </th>
<td><c:out value="${userReg.userName}"/></td>
<th>Designation: </th>
<td><c:out value="${userReg.designation}"/></td>
</tr>
<tr>
<th>Department: </th>
<td><c:out value="${userReg.department}"/></td>
<th>Email: </th>
<td><c:out value="${userReg.email}"/></td>
</tr>

<tr>
<th>Phone no: </th>
<td><c:out value="${userReg.phoneNo}"/></td>
<th>Mobile no: </th>
<td><c:out value="${userReg.mobileNo}"/></td>
</tr>

<tr>
<th>Fax no: </th>
<td><c:out value="${userReg.faxNo}"/></td>
</tr>
<tr>
<th>Current Address: </th>
<td colspan="3"><c:out value="${userReg.curAddress}"/></td>
</tr>
</table>
<input type="hidden" name="regId" id="regId" value="${userReg.regId}" />
<input type="hidden" name="userId" id="userId" value="${userReg.userId}" />
<input type="hidden" name="userType" id="userType" value="${userReg.userType}" />
<c:if test="${userReg.status eq 'Active' || userReg.status eq 'ACTIVE' }"><button class="btn btn-info" onclick="editUserDetails(event);">Edit</button></c:if>
<button class="btn btn-info" onclick="printPage(event);">Print</button>
<button class="btn btn-info" onclick="callBackPage(event);">Back</button>
</form>
                        
                     

</div>

</div></div></div></div>

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