<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#project").change(function() {
	    var selectedProject = $(this).val();
	    alert(selectedProject);
	     if (selectedProject) {
			 $.ajax({
	            url: "checkProjIdExists",  // URL mapped to Spring Controller
	            type: "GET",
	            data: { projectId: selectedProject },
	            contentType: "application/x-www-form-urlencoded",
	            success: function(response) {
	            	 console.log("Response received:", response);
	            	 if (response.exists) { 
						var baseUrl = "";
						if (response.status === "D") {
	                        baseUrl = "getProjectProfile?";
	                    } else if (response.status === "C") {
	                        baseUrl = "getfinalProjectProfile";
	                    } else {
	                        return; // Do nothing if status is not D or C
	                    }
	                    
	                    var url = baseUrl
	                            + "project=" + response.projId
	                            + "&district=" + response.distCode
	                            + "&distName=" + encodeURIComponent(response.distName)
	                            + "&projName=" + encodeURIComponent(response.projName)
	                            + "&finyear=" + response.finYearCode
	                            + "&finName=" + encodeURIComponent(response.finYearDesc)
	                            + "&month=" + response.monthId
	                            + "&monthName=" + encodeURIComponent(response.monthName);
	                    
	                    window.location.href = url;
	                }
	            },
	            error: function() {
	                console.log("Error checking project existence.");
	            }
	        });
	    }
	});
	
	 $("#view").click(function(event) {
	        event.preventDefault();

	        var district = $("#district").val();
	        var project = $("#project").val();
	        var finyear = $("#finyear").val();
	        var month = $("#month").val();

	        if (!district) {
	            alert("Please select district!");
	            $("#district").focus();
	            return;
	        }
	        if (!project) {
	            alert("Please select project!");
	            $("#project").focus();
	            return;
	        }
	        if (!finyear) {
	            alert("Please select Financial Year!");
	            $("#finyear").focus();
	            return;
	        }
	        if (!month) {
	            alert("Please select Month!");
	            $("#month").focus();
	            return;
	        }

	        $("#distName").val($("#district option:selected").text());
	        $("#projName").val($("#project option:selected").text());
	        $("#finName").val($("#finyear option:selected").text());
	        $("#monthName").val($("#month option:selected").text());

	        document.forms["getProfileStart"].action = "getProjectProfile";
	        document.forms["getProfileStart"].method = "GET";
	        document.forms["getProfileStart"].submit();
	    });
	});
</script>
</head>
<body>
<div class="maindiv">
<div class="col formheading" style="">
		<h4>
			<u>Project Evaluation</u>
		</h4>
	</div>
<hr />
<form:form class="form-inline" name="getProfileStart"
		id="getProfileStart" modelAttribute="getProfileStart"
		action="getProfileStart" method="get" autocomplete="off">
    <input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projName" id="projName" value="" />
	<input type="hidden" name="finName" id="finName" value="" />
	<input type="hidden" name="monthName" id="monthName" value="" />
	
<table style="width:100%; align-content: center;" >
        <c:if test="${not empty error}">
					<div class="col">

						<label class="alert alert-danger"> ${error}</label>
					</div>
				</c:if>
        <tr align="center" >

<td><b>District: <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" name="district" id="district" onchange="this.form.submit();">
             <option value="">--Select District--</option>
             <c:if test="${not empty districtList}">
     <c:forEach items="${districtList}" var="dist">
							<c:if test="${dist.key eq district}">
       									<option value="<c:out value='${dist.key}'/>" selected="selected" ><c:out value="${dist.value}" /></option>
       								</c:if>
       							<c:if test="${dist.key ne district}">	
							<option value="<c:out value="${dist.key}"/>"><c:out
									value="${dist.value}" /></option>
								</c:if>	
						</c:forEach>
						</c:if>
    </select>
          </td>  
  
  
  <td><b>Project Name: <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" name="project" id="project">
              		<option value="">--Select Project--</option>
                  	 <c:if test="${not empty projectList}">
               					<c:forEach items="${projectList}" var="lists">
               						<c:if test="${lists.key eq project}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne project}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if>
					</select>
          </td>
          
  <td><b>Financial Year: <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control finyear" name="finyear" id="finyear">
              		<!-- <option value="">--Select Year--</option> -->
                  	 <c:if test="${not empty finYear}">
               					<c:forEach items="${finYear}" var="lists">
               						<c:if test="${lists.key eq finyear}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne finyear}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if>
					</select>
          </td>  
        
     <td><b>Month: <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control month" name="month" id="month">
              		<option value="">--Select Month--</option>
                  	 <c:if test="${not empty monthList}">
               					<c:forEach items="${monthList}" var="lists">
               						<c:if test="${lists.key eq month}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne month}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if>
					</select>
          </td>         
  </tr>
  <td></td><td></td><td></td>
  <c:if test="${not empty error}">
  <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="view" name="view"  onclick="forwardRecords(this);" value="Submit" disabled=""/>
         </td>
  </c:if>
  <c:if test="${empty error}">
  <td  align="center" class="label"> 
          <input type="button" class="btn btn-info" id="view" name="view" onclick="forwardRecords(this);" value="Submit" />
         </td>
   </c:if>   
  
  </table>
  </form:form>
  
</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<%-- <script src='<c:url value="/resources/js/projectevaluation/profilestart.js" />'></script>  --%>
</body>
