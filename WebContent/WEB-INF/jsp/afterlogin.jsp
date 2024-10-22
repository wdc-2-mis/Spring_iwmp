<%@include file="/WEB-INF/jspf/header2.jspf"%>
  
<link rel="stylesheet" href="resources/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
 <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">
<div class ="container-fluid">
<div class="row">
<%-- <div class="col" style="text-align:center;"><h5>Welcome ${sessionScope.username}</h5></div> --%>
</div>
<div class="row">
<div class="col">
<table class="table">
<thead><tr><th colspan="4" class="text-center">Welcome ${sessionScope.username}</th></tr></thead>
<tbody>
<tr><td>User Name: </td><td colspan="3">${sessionScope.username}</td></tr>
<tr><td>State: </td><td><c:if test="${userType eq 'ADMIN'}">
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
       
       				</c:if></td><td>District: </td><td><c:if test="${not empty listm}">
               					<c:forEach items="${listm}"  var="list" >
               					 <c:forEach items="${list.value}" end="0" var="stateList" >
               					 
               					<c:out value='${stateList.districtname}'/>
               					</c:forEach> 
       							</c:forEach>
					</c:if> </td></tr>
<tr><td>Email: </td><td>${email}</td><td>Phone: </td><td>${phone}</td></tr>
<tr><td>Mobile: </td><td>${mobile}</td><td>Fax: </td><td>${fax}</td></tr>
<tr><td>Address: </td><td colspan="3">${address}</td></tr>
</tbody>
</table>
<div class="col"><a class="btn btn-info" href="editprofile" >Update Profile</a></div>
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

<footer class="" style="margin-bottom:0px;">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>