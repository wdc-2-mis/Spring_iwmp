
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">

<html>
<head>
<meta charset="ISO-8859-1">
<title>projectPreparedness</title>
<script type="text/javascript">

function selectProjectPrepare(e){
	var usreproject = $('#usreproject').val();
	var rowCount = $('#tbody tr').length;
	var res="";
	for(var i=1;i<=rowCount;i++)
	{
		if($('#yes'+i+':checked').val()!=null && $('#yes'+i+':checked').val() !='undefined')
		{
			if(res==="")
				res=$('#yes'+i+':checked').val();
			else
				res=res+","+$('#yes'+i+':checked').val();	
		}
		if($('#no'+i+':checked').val()!=null && $('#no'+i+':checked').val() !='undefined')
		{
			if(res==="")
				res=$('#no'+i+':checked').val();
			else
				res=res+","+$('#no'+i+':checked').val();
		}
	}
	document.getElementById("res").value=res;
	
	document.getElementById('projectPreparedness').action ="projectPreparedness";
	document.getElementById('projectPreparedness').method="get";
	document.getElementById('projectPreparedness').submit();
	
}


function saveProjectPrepare(e){
	var usreproject = $('#usreproject').val();
	var rowCount = $('#tbody tr').length;
	var res="";
	for(var i=1;i<=rowCount;i++)
	{
		if($('#yes'+i+':checked').val()!=null && $('#yes'+i+':checked').val() !='undefined')
		{
			if(res==="")
			{
				res=$('#yes'+i+':checked').val();
			}
			else{
				res=res+","+$('#yes'+i+':checked').val();
			}
		}
		if($('#no'+i+':checked').val()!=null && $('#no'+i+':checked').val() !='undefined')
		{
			if(res==="")
			{
				res=$('#no'+i+':checked').val();
			}
			else{
					res=res+","+$('#no'+i+':checked').val();
			}
		}
			
	}
	//alert("final Data "+res);
	
	document.getElementById("res").value=res;
	if(usreproject==='')
	{
		alert('Please select project ');
		$('#usreproject').focus();
		e.preventDefault();
	}
	else{
		if(confirm("Do you want to save status of Project Preparedness ?"))
		{
			document.getElementById('projectPreparedness').action ="saveProjectPreparedness";
		    document.getElementById('projectPreparedness').method="post";
		   document.getElementById('projectPreparedness').submit();
			
		}
	}
	return false;
}

/* $(function(){
	 $("#yes3").click(function() {

		 $('#4').show();
		 $('#yes4:checked').prop('checked', false);
		   });
	 $("#no3").click(function() {
		 document.getElementById('projectPrepareId4').value=null;
		 $('#4').hide();
		 $('#no4:checked').prop('checked', false);
		   });
}); */

</script>

</head>
<body>

<div >
<div class="offset-md-2"></div>
	<div class="table">

		<form:form  name="projectPreparedness" id="projectPreparedness" modelAttribute="projectPreparedness" action="saveProjectPreparedness" method="post" autocomplete="off">
		  <input type="hidden" name="res" id="res" value=""  class="form-control"/>
		 <c:if test="${message ne null}"> 
		 	<div style="text-align: center;" ><p class="message badge badge-success"> ${message} </p></div>
		</c:if> 
		<div class="form-group row" style="text-align: center;">
				<h5></h5>
			</div>
			<div class="col" style="text-align: center;">
				<h5>Status of Project Preparedness</h5>
			</div>
			
			<div class="form-group row" style="text-align: center;">
				<h5></h5>
			</div>
			
			 <div class="form-group row">
				<label for="project" class="col-sm-1 col-form-label">Project <span style="color: red;">*</span></label>
				<div class="col-sm-3">
					 <select name="projectId" title="Project" id="usreproject"  onchange="selectProjectPrepare();" class="form-control" style="height:100%">
						<option value="">-- Select Project--</option>
						<c:if test="${not empty projectList}">
               					<c:forEach items="${projectList}" var="lists">
               					<c:if test="${lists.key eq ProjId}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected"><c:out value="${lists.value}" /></option>
       							</c:if>
       							<c:if test="${lists.key ne ProjId}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       							</c:if>	
								</c:forEach>
						</c:if>
					</select> 
				</div>
				
			</div> 
			<c:if test="${not empty ProjectPreparelist}">
 			<div class="form-group row3">
				<table>	 
					<tr>
					<th  align="center">S.No.</th><th align="center">Project Preparedness</th><th align="center" >Status</th>
				</tr>	
				<tbody id="tbody"> 
			 	<c:forEach items="${ProjectPreparelist}" var="list" varStatus="status">
			 	<c:if test="${not empty addedPrepareList}">
			 	<c:forEach items="${addedPrepareList}" var="apl" >
			 	
			 	<c:if test="${(list.projectMprepareId eq apl.prepare_id) and (apl.activitystatus eq 'true')}">
			 	<tr id="${list.projectMprepareId}">
			        <td width="2%" > ${status.count}</td>
			        <input type="hidden" name="projectPrepareId" id="projectPrepareId${list.projectMprepareId}" value="${list.projectMprepareId}"  class="form-control"/>
			         <td width="8%" ><c:out value="${list.prepareDesc}" />    </td>
			        <td width="7%" > 
				     <input type="radio" id="yes${status.count}" name="${list.projectMprepareId}" value="true-${list.projectMprepareId}"  checked="checked" disabled="disabled"/>  <label><c:out value="${list.selectedDesc1}" /></label> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	 <input type="radio" id="no${status.count}" name="${list.projectMprepareId}" value="false-${list.projectMprepareId}" disabled="disabled"> <label><c:out value="${list.selectedDesc2}" /></label>
			         </td>
			    </tr>
			    </c:if>
			    <c:if test="${(list.projectMprepareId eq apl.prepare_id) and (apl.activitystatus eq 'false')}">
			    <tr id="${list.projectMprepareId}">
			        <td width="2%" > ${status.count}</td>
			        <input type="hidden" name="projectPrepareId" id="projectPrepareId${list.projectMprepareId}" value="${list.projectMprepareId}"  class="form-control"/>
			         <td width="8%" ><c:out value="${list.prepareDesc}" />    </td>
			        <td width="7%" > 
				     <input type="radio" id="yes${status.count}" name="${list.projectMprepareId}" value="true-${list.projectMprepareId}" />  <label><c:out value="${list.selectedDesc1}" /></label> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	 <input type="radio" id="no${status.count}" name="${list.projectMprepareId}" value="false-${list.projectMprepareId}" checked="checked"> <label><c:out value="${list.selectedDesc2}" /></label>
			         </td>
			    </tr>
			    </c:if>
			    
			    <c:if test="${(list.projectMprepareId eq apl.prepare_id) and (apl.activitystatus eq null)}">
			    <tr id="${list.projectMprepareId}">
			        <td width="2%" > ${status.count}</td>
			        <input type="hidden" name="projectPrepareId" id="projectPrepareId${list.projectMprepareId}" value="${list.projectMprepareId}"  class="form-control"/>
			        <td width="8%" ><c:out value="${list.prepareDesc}" />    </td>
			        <td width="7%" > 
				     <input type="radio" id="yes${status.count}" name="${list.projectMprepareId}" value="true-${list.projectMprepareId}" />  <label><c:out value="${list.selectedDesc1}" /></label> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	 <input type="radio" id="no${status.count}" name="${list.projectMprepareId}" value="false-${list.projectMprepareId}" > <label><c:out value="${list.selectedDesc2}" /></label>
			         </td>
			    </tr>
			    </c:if>
			    
			    
			    </c:forEach>
			    </c:if>
			    <c:if test="${empty addedPrepareList}">
			    <tr id="${list.projectMprepareId}">
			        <td width="2%" > ${status.count}</td>
			        <input type="hidden" name="projectPrepareId" id="projectPrepareId${list.projectMprepareId}" value="${list.projectMprepareId}"  class="form-control"/>
			        <td width="8%" ><c:out value="${list.prepareDesc}" />    </td>
			        <td width="7%" > 
				     <input type="radio" id="yes${status.count}" name="${list.projectMprepareId}" value="true-${list.projectMprepareId}" />  <label><c:out value="${list.selectedDesc1}" /></label> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	 <input type="radio" id="no${status.count}" name="${list.projectMprepareId}" value="false-${list.projectMprepareId}" > <label><c:out value="${list.selectedDesc2}" /></label>
			         </td>
			    </tr>
			    </c:if>
			    </c:forEach>
			    </tbody>
			   </table>
 			</div>
 			<div class="col-sm-3">
					<input type="button" name="save" id="save" value="Save"  class="btn btn-info" onclick="saveProjectPrepare();" />
		    </div>
		    </c:if>
		</form:form>
		

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