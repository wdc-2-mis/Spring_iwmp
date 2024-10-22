<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<html>
<head>

<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/cupertino/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify Project Details</title>
<script type="text/javascript">
	$('body').on('focus', ".datepicker_recurring_start", function() {
		$(this).datepicker(
		//     	    {changeMonth: true, changeYear: true}
		{
			onClose : function() {
				if (getFinyear($(this).val(), $(this).attr('id')) == false)
					$(this).datepicker('setDate', null);
			},
		});
	});
	function getValue(param1) {
		var ap = document.getElementById("areaProposed");
		var at = document.getElementById("iwmpMAreaType");//$("#iwmpDistrict["+param1+"]").val();
		if (ap.value != "" && at.value > 0) {
			var pc = document.getElementById("projectCost");//$("#projectSeqNo["+param1+"]").val();
			var csa = document.getElementById("centralShareAmt");
			var ssa = document.getElementById("stateShareAmt");
			$.ajax({
				type : 'POST',
				url : "onChange",
				data : {
					"ap" : ap.value,
					"at" : at.value
				},

				success : function(data) {
					if (data.length > 0) {
						pc.value = data[0];
						csa.value = data[1];
						ssa.value = data[2];
					}
				},
				error : function(xhr, status, er) {
					alert("error" + er);
					console.log(er);
				}
			})
		}
	};
</script>
</head>
<body>
	<div align="center">
		<h3>Modify Project Details</h3>
		<form:form action="updateProject" method="post"
			modelAttribute="projectDetail">
			<form:hidden path="projectId" />
			<form:hidden path="iwmpDistrict.dcode" />
			<form:hidden path="iwmpMFinYear.finYrCd" />
			<form:hidden path="iwmpMCsShare.stateCentralCd" />
			<form:hidden path="projectCd" />
			<form:hidden path="projName" />
			<form:hidden path="status" />
			<form:hidden path="projectSeqNo" />
			<c:if test="${not empty msg}">
				<div class="col">
					<label class="successmsg"> Note: ${msg}</label>
				</div>
			</c:if>
			<table>

				<tr>
					<td>State Name</td>
					<td>${projectDetail.iwmpDistrict.iwmpState.stName}</td>
					<td>District Name</td>
					<td>${projectDetail.iwmpDistrict.distName}</td>

				</tr>
				<tr>
					<td>Project Name:</td>
					<td>${projectDetail.projName}</td>
					<td>Project Sanction Date:</td>
					<td><form:input path="projectStartDt"
							class="datepicker_recurring_start"></form:input> <form:errors
							path="projectStartDt" cssclass="errormsg" /></td>
				</tr>
				<tr>
					<td>Project Period (in Yrs)</td>
					<td><form:select id="iwmpMProjectPrd"
							path="iwmpMProjectPrd.prdCode">
							<form:option value="0" label="---All---"></form:option>
							<form:options items="${projectPrd}" itemLabel="prdDesc"
								itemValue="prdCode" />
						</form:select> <form:errors path="iwmpMProjectPrd.prdCode" cssclass="errormsg" /></td>
					<td>Area Proposed</td>
					<td><form:input path="areaProposed"
							onchange="javascript:getValue()" /> <form:errors
							path="areaProposed" cssclass="errormsg" /></td>
				</tr>
				<tr>
					<td>Type of Area:</td>
					<td><form:select id="iwmpMAreaType"
							path="iwmpMAreaType.areaCd" onchange="javascript:getValue()">
							<form:option value="0" label="---Select---"></form:option>
							<form:options items="${areaType}" itemLabel="areaDesc"
								itemValue="areaCd" />
						</form:select> <form:errors path="iwmpMAreaType.areaCd" cssclass="errormsg" /></td>

					<td>Total Project Cost:</td>
					<td><form:input path="projectCost" /> <form:errors
							path="projectCost" cssclass="errormsg" /></td>
				</tr>
				<tr>
					<td>Central Share:</td>
					<td><form:input path="centralShareAmt" /> <form:errors
							path="centralShareAmt" cssclass="errormsg" /></td>
					<td>State Share:</td>
					<td><form:input path="stateShareAmt" /> <form:errors
							path="stateShareAmt" cssclass="errormsg" /></td>
				</tr>
				<tr>
					<td>Remarks:</td>
					<td><form:input path="remarks" /> <form:errors path="remarks"
							cssclass="errormsg" /></td>
				<tr>
					<td colspan="4" align="center"><input type="submit"
						class="square_btn" value="Update"></td>
				</tr>
			</table>
		</form:form>

	</div>
	<!-- Footer -->
	<footer class="text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>