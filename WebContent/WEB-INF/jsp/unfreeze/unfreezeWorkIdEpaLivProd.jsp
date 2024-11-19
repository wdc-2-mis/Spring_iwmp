<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/unfreezeProjectLocation.js" />'></script>
<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
	var head = $('#head').val();
	var act = $('#act').val();
	
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
	if(act==='')
	{
		alert('Please select Head Activity');
		$('#act').focus();
		e.preventDefault();
	}
	else
	{
		document.getElementById("submitClicked").value = "true";
		document.unfreezeWorkIdELP.action="getUnfreezeWorkIdEpaLivProd";
		document.unfreezeWorkIdELP.method="post";
		document.unfreezeWorkIdELP.submit();
	}
	return false;
}

function selects(ele) {
    var checkboxes = document.getElementsByName('elp_id');
    
 	// If select all checkbox is checked, check the all checkboxes
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

function checkAllCheckboxes() {
    var checkboxes = document.getElementsByName('elp_id');
    var selectAllCheckbox = document.getElementById('sno');

    // Check if all checkboxes are checked
    var allChecked = Array.prototype.every.call(checkboxes, function(checkbox) {
        return checkbox.checked;
    });

    // If all checkboxes are checked, check the select all checkbox
    if (allChecked) {
        selectAllCheckbox.checked = true;
    } else {
        selectAllCheckbox.checked = false;
    }
}

function completebsl()
{ 
    var checkboxes = document.getElementsByName('elp_id');
    var isAnyChecked = false;
    
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isAnyChecked = true;
            break;
        }
    }
    
    if (isAnyChecked) {
        if (confirm("Do you want to Unfreeze ?") == true) {
            document.unfreezeWorkIdELP.action = "unfreezeWorkIdEpaLivProdData";
            document.unfreezeWorkIdELP.method = "post";
            document.unfreezeWorkIdELP.submit();
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
	<h5><span style="text-decoration:underline;">Unfreeze Epa / Livelihood / Production Work Id Data</span></h5>
</div>

<form:form autocomplete="off" name="unfreezeWorkIdELP" id="unfreezeWorkIdELP" action="unfreezeWorkIdEpaLivProd" method="get">
	
	<input type="hidden" name="submitClicked" id="submitClicked" value="false"/>
	
	<table style="width:100%">
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

			<td class="label" style="width: 120px">District <span style="color: red;">*</span></td>
			<td>
				<select name="district" id="district" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="" style="width: 80px">--Select District--</option>
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
			<td class="label" style="width: 100px">Head<span style="color: red;">*</span></td>
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
			
			<td class="label">Head Activity<span style="color: red;">*</span></td>
			<td>
				<select name="act" id="act" required="required" style="width:100%;">
					<option value="0">--All Activities--</option> 
					
					<c:if test="${not empty mapact}">
						<c:forEach items="${mapact}" var="list">
							<c:if test="${list.key eq act}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne act}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
			</td>

          	<td align="left"> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value="Submit" />
        </tr>
	</table>
	<hr>
	<div style="width: 100%;">
		<c:if test="${not empty state and not empty district and not empty project and not empty head and not empty act}">
			<table id="tbleELPDetails" style="width: 100%;">
				<thead>
					<tr>
						<th style="text-align: center;"><input type="checkbox" id="sno" onchange="selects(this);"/> &nbsp;S.No.</th>
						<th style="text-align: center;">Work Id</th>
						<th style="text-align: center;">Activity Name</th>
						<th style="text-align: center;">Village Name</th>
						<th style="text-align: center;">Gram Panchayat Name</th>
						<th style="text-align: center;">Block Name</th>
						<th style="text-align: center;">Start Date</th>
						<th style="text-align: center;">Complete Date</th>
						<th style="text-align: center;">Status</th>
						<th style="text-align: center;">Land Identification</th>
					</tr>
					<tr>
						<th style="text-align: center;">1</th>
						<th style="text-align: center;">2</th>
						<th style="text-align: center;">3</th>
						<th style="text-align: center;">4</th>
						<th style="text-align: center;">5</th>
						<th style="text-align: center;">6</th>
						<th style="text-align: center;">7</th>
						<th style="text-align: center;">8</th>
						<th style="text-align: center;">9</th>
						<th style="text-align: center;">10</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${epaLivProdWorkIdListSize gt 0}">
						<c:forEach var="w" items="${epaLivProdWorkIdList}" varStatus="cnt">
							<tr>
								<td align="center"><input type="checkbox" id="elp_id" name="elp_id" onchange="checkAllCheckboxes();" value='${w.work_id}'>&nbsp;&nbsp;<c:out value="${cnt.count}"/></td>
								<td align="right"><c:out value="${w.work_id}"/></td>
								<td><c:out value="${w.elp_activity}"/></td>
								<td><c:out value="${w.village_name}"/></td>
								<td><c:out value="${w.gram_panchayat_name}"/></td>
								<td><c:out value="${w.block_name}"/></td>
								<td><c:out value="${w.startdate}"/></td>
								<td><c:out value="${w.completiondate}"/></td>
								<td><c:out value="${w.status}"/></td>
								<td><c:out value="${w.land_identification}"/></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan=10 align="center">
								<input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' />
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</c:if>
		
		<c:if test="${epaLivProdWorkIdListSize==0}">
			<table style="width: 100%;">
				<tr>
					<td align="center" colspan="10"  style="color:red;"><b>Data Not Found</b></td>
				</tr>
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