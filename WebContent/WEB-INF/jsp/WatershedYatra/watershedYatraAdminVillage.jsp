<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/report.css" />">
<%-- <script src='<c:url value="/resources/js/userDetailSA.js" />'></script> --%>

<script type="text/javascript">

function showReportSTVill(e){
	var state = $('#state').val();
	var district = $('#district').val();
	var block = $('#block').val();
	var gp = $('#gp').val();
	var village = $('#village').val();
	
	if(state==='')
	{
		alert('Please select state');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District');
		$('#district').focus();
		e.preventDefault();
	}
	if(block==='')
	{
		alert('Please select Block');
		$('#block').focus();
		e.preventDefault();
	}
	if(gp==='')
	{
		alert('Please select Gram Panchayat');
		$('#gp').focus();
		e.preventDefault();
	}
	if(village==='')
	{
		alert('Please select Village');
		$('#village').focus();
		e.preventDefault();
	}
	else{
		if(confirm("Do you want to save Duplicate Village?")) {
			document.wyvlg.action="saveDupWatershedYatraVlgData";
			document.wyvlg.method="post";
			document.wyvlg.submit();
		}
		return true;
	}
	return false;
}

function deleteRecord(watershed_id){
    if(confirm("Are you sure you want to delete this record?")) {
        document.wyvlg.action="deleteVlgData";
        document.wyvlg.method="post";
        document.wyvlg.watershed_id.value=watershed_id;
        document.wyvlg.submit();
    }
}

</script>

</head>

<body>

	<c:if test="${message != null}">
		<script>
	    	alert("<c:out value='${message}'/>");
		</script>
	</c:if>

	

			<div class="col" style="text-align: center;">
				<h5>Watershed Yatra Duplicate Village Entries</h5>
			</div>
			<form:form autocomplete="off" name="wyvlg" id="wyvlg" action="getDupWatershedYatraVlg" method="get">
			
				<input type="hidden" id="watershed_id" name="watershed_id" />
				
				<table>
					<tr>
						<td class="label">State <span style="color: red;">*</span></td>
						<td><select name="state" id="state" onchange="this.form.submit();">
								<option value="">--Select State--</option>
								<c:if test="${not empty stateList}">
									<c:forEach items="${stateList}" var="lists">
										<c:if test="${lists.key eq state}">
											<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
										</c:if>
										<c:if test="${lists.key ne state}">
											<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
										</c:if>
									</c:forEach>
								</c:if>
						</select></td>

						<td class="label">District <span style="color: red;">*</span></td>
						<td><select name="district" id="district" onchange="this.form.submit();">
								<option value="">--Select--</option>
								<c:if test="${not empty districtList}">
									<c:forEach items="${districtList}" var="lists">
										<c:if test="${lists.key eq district}">
											<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
										</c:if>
										<c:if test="${lists.key ne district}">
											<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
										</c:if>
									</c:forEach>
								</c:if>
						</select></td>

						<td class="label">Block <span style="color: red;">*</span></td>
						<td><select name="block" id="block" onchange="this.form.submit();">
								<option value="">--Select--</option>
								<c:if test="${not empty blockList}">
									<c:forEach items="${blockList}" var="lists">
										<c:if test="${lists.key eq block}">
											<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
										</c:if>
										<c:if test="${lists.key ne block}">
											<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
										</c:if>
									</c:forEach>
								</c:if>
						</select></td>

						<td class="label">Gram Panchayat <span style="color: red;">*</span></td>
						<td><select name="gp" id="gp" onchange="this.form.submit();">
								<option value="">--Select--</option>
								<c:if test="${not empty gpList}">
									<c:forEach items="${gpList}" var="lists">
										<c:if test="${lists.key eq gp}">
											<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
										</c:if>
										<c:if test="${lists.key ne gp}">
											<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
										</c:if>
									</c:forEach>
								</c:if>
						</select></td>

						<td class="label">Village <span style="color: red;">*</span></td>
						<td><select name="village" id="village">
								<option value="">--Select--</option>
								<c:if test="${not empty vlgList}">
									<c:forEach items="${vlgList}" var="lists">
										<c:if test="${lists.key eq village}">
											<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
										</c:if>
										<c:if test="${lists.key ne village}">
											<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
										</c:if>
									</c:forEach>
								</c:if>
						</select></td>

						<td align="center"><input type="button" class="btn btn-info" id="view" onclick="showReportSTVill(this);" name="view" value='Submit' /></td>
					</tr>
				</table>
			</form:form>

			<hr />
<!-- 			<div class="card"> -->

		<div class="table-responsive">
			<h5 class="text-center font-weight-bold" style="text-decoration:underline;">
				List of Watershed Yatra Duplicate Village Entries
			</h5>

			<table id="tblReport" class="table">
				<thead>
					<tr>
						<th class="displ" align="center">S.No.</th>
						<th class="displ" align="center">State Name</th>
						<th class="displ" align="center">District Name</th>
						<th class="displ" align="center">Block Name</th>
						<th class="displ" align="center">Gram Panchayat Name</th>
						<th class="displ" align="center">Villages Name</th>
						<th class="displ" align="center">Action</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th align="center"><b> 1 </b></th>
						<th align="center"><b> 2 </b></th>
						<th align="center"><b> 3 </b></th>
						<th align="center"><b> 4 </b></th>
						<th align="center"><b> 5 </b></th>
						<th align="center"><b> 6 </b></th>
						<th align="center"><b> 7 </b></th>
					</tr>
					<c:if test="${dupWatershedYatraVlgDataList ne null}">
						<c:forEach items="${dupWatershedYatraVlgDataList}" var="data" varStatus="count">
							<tr>
								<td><c:out value='${count.count}' />
								<td><c:out value="${data.stname}" /></td>
								<td><c:out value="${data.distname}" /></td>
								<td><c:out value="${data.blockname}" /></td>
								<td><c:out value="${data.gpname}" /></td>
								<td><c:out value="${data.villagename}" /></td>
								<td class="text-center"><button class="btn btn-info" onclick="deleteRecord(${data.watershed_id})">Delete</button></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${dupWatershedYatraVlgDataListSize eq 0}">
						<tr>
							<td align="center" colspan="7" class="required" style="color: red;">Data Not Found</td>
						</tr>
					</c:if>
				</tbody>
			</table>

			<br>

		</div>
<!-- 	</div> -->


	<footer class=" ">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>