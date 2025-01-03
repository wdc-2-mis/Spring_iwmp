<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script type="text/javascript">

function showReport(e)
{

	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
	var head = $('#head').val(); 

	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	if(project==='')
	{
		alert('Please select Project ');
		$('#project').focus();
		e.preventDefault();
	}
	if(head==='')
	{
		alert('Please select Head ');
		$('#head').focus();
		e.preventDefault();
	}
	else
	{
// alert(head);
		document.unfreezeFpo.action="unfreezeListFpoDetails";
		document.unfreezeFpo.method="post";
		document.unfreezeFpo.submit();
	}
	return false;
}

function selects(ele) {
    var checkboxes = document.getElementsByName('fpo_detail_id');
    for (var i = 0; i < fpo_detail_id.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}
function completebsl()
{ 
    var checkboxes = document.getElementsByName('fpo_detail_id');
    var isAnyChecked = false;
    
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isAnyChecked = true;
            break;
        }
    }
    
    if (isAnyChecked) {
        if (confirm("Do you want to Unfreeze ?") == true) {
            document.unfreezeFpo.action = "unfreezeFpoDetailsData";
            document.unfreezeFpo.method = "post";
            document.unfreezeFpo.submit();
        }
    }
}


</script>
</head>

<div class="card">
<div class="table-responsive">
<c:if test="${messageUpload != null}">
	<script>
		alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
<div class="col" style="text-align:center;">
	<h5>Unfreeze FPO Details</h5>
</div>

<form:form autocomplete="off" name="unfreezeFpo" id="unfreezeFpo" action="unfreezeFPODetails" method="get">
	<table style="width:80%">
		<tr>
			<td class="label">State <span style="color: red;">*</span></td>
			<td>
				<select name="state" id="state" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select State--</option>
					<c:if test="${not empty stateList}">
						<c:forEach items="${stateList}" var="list">
							<c:if test="${list.key eq state}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne state}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
			</td>

			<td class="label">District <span style="color: red;">*</span></td>
			<td>
				<select name="district" id="district" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select District--</option>
					<c:if test="${not empty districtList}">
						<c:forEach items="${districtList}" var="list">
							<c:if test="${list.key eq district}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne district}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
			</td>
			<td class="label">Project &nbsp;<span style="color: red;">*</span></td>
			<td>
				<select name="project" id="project" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select Project--</option>
					<c:if test="${not empty projectList}">
						<c:forEach items="${projectList}" var="list">
							<c:if test="${list.key eq project}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne project}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			
			<td class="label">Head &nbsp;<span style="color: red;">*</span></td>
			<td>
				<select name="head" id="head" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select Head--</option> 
					
					<c:if test="${not empty maphead}">
						<c:forEach items="${maphead}" var="list">
							<c:if test="${list.key eq head}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne head}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
			</td>

          <td align="left"> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value="Submit" />
        </tr>
	</table>
	
	<div style="width: 85%;">
		<c:if test="${head ne null}">
				<table id="tbleshgdDetails">
					<thead>
						<tr>
						<th style="width:3%"><input type="checkbox" onchange="selects(this);"/> &nbsp;S.No.</th>
						<th style="width: 200px">No. of FPOs</th>
						<th style="width: 200px">Name of FPOs</th>
						<th style="width: 100px">Dept. /Org. /Scheme.</th>
						<th style="width: 100px">Registration No.</th>
						<th style="width: 150px">Date of Registration</th>
						<th style="width: 100px">No. of members of FPO</th>
						<th style="width: 350px">Core Activity</th>
						<th style="width: 100px">Avg. turnover of FPO(in rs.)</th>
						<th style="width: 200px">No. of Farmer Associated with FPO</th>
					</tr>
					</thead>
					<tbody>
					<c:if test="${fpoList ne null}">
							<c:forEach var="getFpo" items="${fpoList}" varStatus="cntt">
								<tr>
									<c:choose>
									<c:when test="${proj ne getFpo.fpo_id}">
										<c:set var="proj" value="${getFpo.fpo_id}" />
										<td align="center"><input type="checkbox" id="fpo_detail_id" name="fpo_detail_id" value='${getFpo.fpo_id}'>&nbsp;&nbsp;<c:out value="${cntt.count}"/></td>
										<td><c:out value="${getFpo.totalno}"/></td>									
									</c:when>	
									<c:otherwise>
										<td></td>
										<td></td>
									</c:otherwise>
									</c:choose>
									
									<td><c:out value="${getFpo.fpo_name}"/></td>
									<td><c:out value="${getFpo.dept_org}"/></td>
									<td><c:out value="${getFpo.registration_no}"/></td>
									<td><c:out value="${getFpo.registration_date}"/></td>
									<td><c:out value="${getFpo.no_of_members}"/></td>
									<td><c:out value="${getFpo.coreactivity}"/></td>
									<td><c:out value="${getFpo.turnover}"/></td>
									<td><c:out value="${getFpo.no_of_farmer_associated}"/></td>
									
								</tr>
							</c:forEach>
							<c:if test="${fpoListSize>0}">
					<tr>
								<td colspan=10 align="center">
									<input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' />
								</td>
							</tr>
							</c:if>
							
							<c:if test="${fpoListSize==0}">
			<tr>
				<td align="center" colspan="6" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>
							</c:if>
							</tbody>
							</table>

				</c:if>
	</div>
	
</form:form>

</div>
</div>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</html>