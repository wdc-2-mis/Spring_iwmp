
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
		document.unfreezeShg.action="unfreezeListShgDetails";
		document.unfreezeShg.method="post";
		document.unfreezeShg.submit();
	}
	return false;
}

function selects(ele) {
    var checkboxes = document.getElementsByName('shg_detail_id');
    for (var i = 0; i < shg_detail_id.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}
function completebsl()
{ 
    var checkboxes = document.getElementsByName('shg_detail_id');
    var isAnyChecked = false;
    
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isAnyChecked = true;
            break;
        }
    }
    
    if (isAnyChecked) {
        if (confirm("Do you want to Unfreeze ?") == true) {
            document.unfreezeShg.action = "unfreezeShgDetailsData";
            document.unfreezeShg.method = "post";
            document.unfreezeShg.submit();
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
	<h5>Unfreeze SHG Details</h5>
</div>

<form:form autocomplete="off" name="unfreezeShg" id="unfreezeShg" action="unfreezeShgDetails" method="get">
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
		<td class="label">Shg Detail and Account&nbsp;<span style="color: red;">*</span></td>
			<td>
				<select name="headType" id="headType" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select--</option> 
					
					<c:if test="${not empty maphd2}">
						<c:forEach items="${maphd2}" var="list">
							<c:if test="${list.key eq headType}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne headType}">
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
		<c:if test="${headType=='ShgD'}">
				<table id="tbleshgdDetails">
					<thead>
						<tr>
								<th style="width:3%"><input type="checkbox" onchange="selects(this);"/> &nbsp;S.No.</th>
								<th style="width:25%">SHG Name</th>
								<th style="width:30%">Department/ Scheme</th>
								<th style="width:25%">Date of Registration</th>
							</tr>
					</thead>
					<tbody>
					<c:if test="${shgList ne null}">
							<c:forEach var="getShgg" items="${shgList}" varStatus="cntt">
								<tr>
								<td align="center"><input type="checkbox" id="shg_detail_id" name="shg_detail_id" value='${getShgg.shg_id}'>&nbsp;&nbsp;<c:out value="${cntt.count}"/></td>
									<td><c:out value="${getShgg.name}"/></td>
									<td><c:out value="${getShgg.scheme_description}"/></td>
									<td align="right"><c:out value="${getShgg.reg_date}"/></td>
								</tr>
							</c:forEach>
							<c:if test="${shgListSize>0}">
					<tr>
								<td colspan=4 align="center">
									<input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' />
								</td>
							</tr>
							</c:if>
							
							<c:if test="${shgListSize==0}">
			<tr>
				<td align="center" colspan="6" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>
							</c:if>
							</tbody>
							</table>
							</c:if>
			<c:if test="${headType=='ShgA'}">
				<table id="tbleshgDetails">
					<thead>
						<tr>
								<th style="width:3%"><input type="checkbox" onchange="selects(this);"/> &nbsp;S.No.</th>
								<th style="width:25%">SHG Name</th>
								<th style="width:30%">Department/ Scheme</th>
								<th style="width:25%">Date of Registration</th>
								<th style="width:30%">Bank Account Number</th>
								<th style="width:20%">IFSC</th>
							</tr>
					</thead>
					<tbody>
					<c:if test="${shgList ne null}">
							<c:forEach var="getShg" items="${shgList}" varStatus="cnt">
								<tr>
									<td align="center"><input type="checkbox" id="shg_detail_id" name="shg_detail_id" value='${getShg.shg_detail_id}'>&nbsp;&nbsp;<c:out value="${cnt.count}"/></td>
									<td><c:out value="${getShg.name}"/></td>
									<td><c:out value="${getShg.scheme_description}"/></td>
									<td align="right"><c:out value="${getShg.reg_date}"/></td>
									<td align="right"><c:out value="${getShg.account_detail}"/></td>
									<td align="right"><c:out value="${getShg.ifsc_code}"/></td>
								</tr>
							</c:forEach>
							<c:if test="${shgListSize>0}">
					<tr>
								<td colspan=4 align="center">
									<input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' />
								</td>
							</tr>
							</c:if>
							
							<c:if test="${shgListSize==0}">
			<tr>
				<td align="center" colspan="6" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>
		</c:if>
							</tbody>
				</table>
				</c:if>
				</c:if>
	</div>
	
</form:form>

</div>
</div>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</html>