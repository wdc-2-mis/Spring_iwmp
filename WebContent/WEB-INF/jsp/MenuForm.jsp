<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<html>
<head>
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css"> -->
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/all.css" />"
	integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New/Edit Menu</title>

<script type="text/javascript">
	// window.addEventListener("load", function() {
	//     alert('Page is loaded');
	// });
	// 	window.onload = function() {

	// 		obj = document.getElementById("isParent");
	// 		alert(obj);
	// 		if (obj.value == 1) {
	// 			document.getElementById("parentId").value = 0;
	// 			document.getElementById("parentId").disabled = true;
	// 			ctrltxt = document.getElementById("role");
	// 			ctrltxt.style.display = 'none';
	// 			ctrltxt1 = document.getElementById("role1");
	// 			ctrltxt1.style.display = 'none';

	// 		} else {
	// 			document.getElementById("parentId").disabled = false;
	// 			ctrltxt = document.getElementById("role");
	// 			ctrltxt.style.display = 'block';
	// 			ctrltxt1 = document.getElementById("role1");
	// 			ctrltxt1.style.display = 'block';
	// 		}

	// 	};
	function getValue(obj) {
		//lert("You have selected Country: " + obj.value);
		
		
		if (obj.value == 1) {
			document.getElementById("iwmpMMenu.menuId").value = 0;
			document.getElementById("iwmpMMenu.menuId").disabled = true;
			ctrltxt = document.getElementById("role");
			ctrltxt.style.display = 'none';
			ctrltxt1 = document.getElementById("role1");
			ctrltxt1.style.display = 'none';

		} else {
			document.getElementById("iwmpMMenu.menuId").disabled = false;
			ctrltxt = document.getElementById("role");
			ctrltxt.style.display = 'block';
			ctrltxt1 = document.getElementById("role1");
			ctrltxt1.style.display = 'block';
		}

		//document.getElementById("parentId").style.visibility = "hidden"; 
	}
</script>



</head>
<body>
	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">

				<div class="col-sm-9">
					<h5>Add / Modify Menu</h5>

				</div>

			</div>
		</div>


		<form:form action="saveMenu" method="post" modelAttribute="menu">
			<table class="table table-striped table-highlight">
				<form:hidden path="menuId" />
				<tr>
					<td>Menu Name:</td>
					<td><form:input path="submenuName" /> <form:errors
							path="submenuName" class="alert alert-danger" /></td>
						
				</tr>
				<tr>
				<td>Menu Hindi Name:</td>
					<td><form:input path="submenuHindiName" /> <span class="alert alert-info">(Not for After Login Menu)</span><form:errors
							path="submenuHindiName" class="alert alert-danger" /></td>
					<td> </td>	
				</tr>
				<tr>
					<td>status</td>
					<td><form:checkbox path="isactive" /><span class="alert alert-info">(Checked
							when menu is active)</span></td>
				</tr>
				<tr>
					<td>Type of Menu</td>
					<td><form:radiobutton onchange="getValue(this);"
							name="isparent" path="isParent" value="1" label="Parent" /> <form:radiobutton
							onchange="getValue(this);" name="isparent" path="isParent"
							value="0" label="Child" /> </td>
				</tr>
				<tr>
					<td>Select Parent Menu ${menu.isParent}</td>
					<td><c:if test="${menu.isParent == 1}">
							<form:select path="iwmpMMenu.menuId"  disabled="true">
								<form:option value="0" label="---Select---"></form:option>
								<form:options items="${listMenu}" itemLabel="menuName"
									itemValue="menuId" />
							</form:select>
						</c:if> <c:if test="${menu.isParent!= 1}">
							<form:select path="iwmpMMenu.menuId">
								<form:option value="0" label="---Select---"></form:option>
								<form:options items="${listMenu}" itemLabel="menuName"
									itemValue="menuId" />
							</form:select>
						</c:if> <form:errors path="iwmpMMenu.menuId" class="alert alert-danger" />
						<c:if test="${not empty selectchild}">
							<div class="col">
								<label class="alert alert-danger"> Error: ${selectchild}</label>
							</div>
						</c:if></td>
				</tr>
				<tr>
					<td>Target:</td>
					<td><form:input path="target" /> <span
						class="alert alert-info">(Link is not mandatory for the
							Parent Menu)</span> <form:errors path="target"
							class="alert alert-danger" /> <c:if test="${not empty error}">
							<div class="col">
								<label class="alert alert-danger"> Error: ${error}</label>
							</div>
						</c:if></td>
				</tr>
				<tr>
					<td>Sequence:</td>
					<td><form:input path="seqNo" /> <form:errors path="seqNo"
							class="alert alert-danger" /></td>
				</tr>
				<tr>
					<td><c:if test="${menu.isParent eq 1}">
							<div id="role1" style="display: none">Role:</div>
						</c:if> <c:if test="${menu.isParent eq 0}">
							<div id="role1">Role:</div>
						</c:if></td>
					<td><c:if test="${menu.isParent eq 1}">
							<div id="role" style="display: none">
								<form:checkboxes path="mapRoleId" items="${listRole}"
									itemLabel="roleName" itemValue="roleId" delimiter="<br/>" />

								<form:errors path="mapRoleId" class="alert alert-danger" />
							</div>
						</c:if> <c:if test="${menu.isParent eq 0}">
							<div id="role">
								<form:checkboxes path="mapRoleId" items="${listRole}"
									itemLabel="roleName" itemValue="roleId" delimiter="<br/>" />

								<form:errors path="mapRoleId" class="alert alert-danger" />
							</div>
						</c:if></td>

				</tr>
				<tr>
					<td colspan="2" align="center"><input class="square_btn"
						type="submit" value="Save"></td>
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