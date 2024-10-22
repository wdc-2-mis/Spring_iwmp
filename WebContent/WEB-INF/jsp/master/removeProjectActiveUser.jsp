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

<div class="card" style="background: rgba(0, 128, 0, 0.1)">
<div class="offset-md-2"></div>
	<div class="table" >
 
		<form:form  name="remprojectmap" id="remprojectmap" modelAttribute="userProjectMap" action="removeProjectActiveUser" method="post">
		
		<c:if test="${message != null}">
			<script>
			    alert("<c:out value='${message}'/>");
			</script>
		</c:if>
		
		<%-- <c:if test="${message ne null}"> 
		 	<div class="col"><p class="badge badge-success"> ${message} </p></div>
		</c:if> --%>
		<%-- <div class="col">
		<label class="badge badge-danger"><c:out value="${message}" ></c:out></label>
		</div> --%>
			<div class="col" style="text-align: center;">
				<h5>Remove project from pia User</h5>
			</div>

			<div class="form-group row">
				<label for="application" class="col-sm-2 col-form-label">Application</label>
				<div class="col-sm-3">
				
					<label><b>WDC-PMKSY</b></label>
				</div>
			</div>
			<c:if test="${sessionScope.userType eq 'DL'}">
			<div class="form-group row state">
				<label for="state" class="col-sm-2 col-form-label">State <span style="color: red;">*</span></label>
				<div class="col-sm-3">
					 <select class="form-control project" id="state" name="state" required   onchange="this.form.submit();" >
		    			<option value="">-- Select State --</option>
		     			<c:forEach items="${stateList}" var="states">
									<c:if test="${states.key eq statecd}">
       									<option value="<c:out value='${states.key}'/>" selected="selected" ><c:out value="${states.value}" /></option>
       								</c:if>	
       								<c:if test="${states.key ne statecd}">
       									<option value="<c:out value='${states.key}'/>"  ><c:out value="${states.value}" /></option>
       								</c:if>	
						</c:forEach>
    				</select>
				</div>
			</div>
			</c:if>
			<c:if test="${sessionScope.userType eq 'SL'}">
			<div class="form-group row state">
				<label for="state" class="col-sm-2 col-form-label">State</label>
				<div class="col-sm-3">
					<label><c:out value="${sessionScope.stName}" /></label>
				</div>
			</div>
			</c:if>
			<div class="form-group row district">
				<label for="district" class="col-sm-2 col-form-label">District <span style="color: red;">*</span></label>
				<div class="col-sm-3">
				
					 <select name="district" title="District" id="district" onchange="this.form.submit();" class="form-control">
						<option value="">-- Select District --</option>
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
				
			</div>
			
			<div class="form-group row">
				<label for="project" class="col-sm-2 col-form-label">Project <span style="color: red;">*</span></label>
				<div class="col-sm-3">
					 <select name="project" title="Project" id="project"  class="form-control" >
						<option value="">-- Select Project --</option>
						<c:if test="${not empty projectList}">
               					<c:forEach items="${projectList}" var="lists">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
								</c:forEach>
						</c:if>
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
				
					<input type="button" name="save" id="save" value="Remove Project" class="btn btn-info" onclick="deleteActiveUserProject();" />
					
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