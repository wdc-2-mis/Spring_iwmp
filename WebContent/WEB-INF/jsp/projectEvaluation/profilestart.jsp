<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script type="text/javascript">

function forwardRecords(e) 
{
	 var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
     var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
     var finName = document.getElementById("finyear").options[document.getElementById("finyear").selectedIndex].text;
     var monthName = document.getElementById("month").options[document.getElementById("month").selectedIndex].text;
     
     document.getElementById("distName").value=distName;
     document.getElementById("projName").value=projName;
     document.getElementById("finName").value=finName;
     document.getElementById("monthName").value=monthName;
     
    if(document.getElementById("district").value==='')
	{
		alert('Please select district ! ');
		$('#district').focus();
		e.preventDefault();
	}
	if(document.getElementById("project").value==='')
	{
		alert('Please select project !');
		$('#project').focus();
		e.preventDefault();
	}
	if(document.getElementById("finyear").value==='')
	{
		alert('Please select Financial year !');
		$('#finyear').focus();
		e.preventDefault();
	}
	if(document.getElementById("month").value==='')
	{
		alert('Please select Month !');
		$('#month').focus();
		e.preventDefault();
	}
   else{
		
		document.getProfileStart.action="getProjectProfile";
		document.getProfileStart.method="get";
		document.getProfileStart.submit();
	}
	return false;
}  
    
$(document).on('change','#district',function(e){
	document.getProfileStart.action="getProfileStart";
	document.getProfileStart.method="get";
	document.getProfileStart.submit();
	
});

$(document).on('change','#project',function(e){
	document.getProfileStart.action="getProfileStart";
	document.getProfileStart.method="get";
	document.getProfileStart.submit();
	
});

$(document).on('change','#month',function(e){
	document.getProfileStart.action="getProfileStart";
	document.getProfileStart.method="get";
	document.getProfileStart.submit();
	
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
             <select class="form-control project" name="project" id="project" onchange="this.form.submit();">
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
              		<option value="">--Select Year--</option>
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
             <select class="form-control month" name="month" id="month" onchange="this.form.submit();">
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
<%-- <script src='<c:url value="/resources/js/projectevaluation/profilestart.js" />'></script> --%>
</body>
