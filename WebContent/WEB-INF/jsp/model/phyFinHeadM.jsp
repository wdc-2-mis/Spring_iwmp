
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">


<script src='<c:url value="/resources/js/phyFinHead.js" />'></script>


<body>
	<!-- <div class ="container"> -->
	<div class="table-wrapper">


		<div class="table-title">
			<div class="row">

				<div class="col-sm-6">
					<h6>
						Details of Physical Head <b>(iwmp_m_phy_heads)</b>
					</h6>

				</div>
				<div class="col-sm-6">
					<a href="#addEmployeeModal" id="findseqno" class="btn btn-success"
						data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add
							New Records</span></a>
				</div>
			</div>
		</div>
		<c:if test="${not empty message}">
			<div class="alert alert-danger">
				<label style="color: blue; font-size: 20px;">${message}</label>
			</div>
		</c:if>
		<table class="table table-striped table-hover" id="phyheadtable">

			<thead>
				<tr>

					<th>Head Code</th>
					<th>Head Description</th>
					<th>SeqNO</th>
					<th>Status</th>
					<th>Base Line Required</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>

			<tbody>

				<c:forEach var="list" items="${headdata}">
					<c:forEach var="listUser" items="${list.value}">
						<tr>
							<td><c:out value="${listUser.headcode }"></c:out></td>
							<td><c:out value="${listUser.headdesc }"></c:out></td>
							<td><c:out value="${listUser.seqno }"></c:out></td>
							<td><c:out value="${listUser.status }"></c:out></td>
							<td><c:out value="${listUser.bls_required }"></c:out></td>

							<td><a href="#editEmployeeModal" class="edit"
								data-toggle="modal"><i class="material-icons"
									data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
								type="hidden" id="id" value="${listUser.headcode}"></td>
							<td><a href="#deleteEmployeeModal" class="delete"
								data-toggle="modal"><i class="material-icons"
									data-toggle="tooltip" title="Delete">&#xE872;</i></a> <input
								type="hidden" id="id" value="${listUser.headcode}"></td>
						</tr>

					</c:forEach>
				</c:forEach>

			</tbody>
		</table>


	</div>


	<!-- add data  -->
	<div id="addEmployeeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath }/saveData">
					<div class="modal-header">
						<h4 class="modal-title">Add Physical-Head</h4>
						<button type="button" class="close"
							onClick="window.location.reload();" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label>Head Description</label> <input type="text"
								class="form-control" autocomplete="off" required="required"
								name="hdesc">
						</div>

						<div class="form-group">
							<label>Sequence No</label> <input type="text"
								class="form-control" id="seqno" required="required"
								autocomplete="off" name="seqno">
						</div>

						<div class="form-group">
							<label for="name">Status: </label> <select name="status"
								id="status" class="form-control" style="height: 100%"
								required="required" name="status">
								<option value="">--Select--</option>
								<option value="A">Active</option>
								<option value="I">In-Active</option>
							</select>

						</div>

						<div class="form-group">
							<label for="name">Base Line Required: </label> <select
								name="bline" id="bline" class="form-control"
								style="height: 100%" required="required">
								<option value="">--Select--</option>
								<option value="true">Yes</option>
								<option value="false">No</option>
							</select>

						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-info" value="Save">
					</div>
				</form>
			</div>
		</div>
	</div>


	<!-- Edit Modal HTML -->
	<div id="editEmployeeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath}/updateData">
					<div class="modal-header">
						<h4 class="modal-title">Edit Physical-Head</h4>
						<button type="button" class="close"
							onClick="window.location.reload();" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label>Head Description</label> <input type="text" id="hdesc"
								class="form-control" autocomplete="off" required="required"
								name="hdesc"> <input type="hidden" id="headId"
								class="form-control" name="id">
						</div>
						<div class="form-group">
							<label>Sequence No</label> <input type="text"
								class="form-control" id="seqno1" required="required"
								autocomplete="off" name="seqno1">
						</div>
						<div class="form-group">
							<label for="name">Status: </label> <select name="status"
								id="status" class="form-control" style="height: 100%"
								required="required">
								<option value="">--Select--</option>
								<option value="A">Active</option>
								<option value="I">In-Active</option>
							</select>

						</div>

						<div class="form-group">
							<label for="name">Base Line Required: </label> <select
								name="bline" id="bline" class="form-control"
								style="height: 100%" required="required">
								<option value="">--Select--</option>
								<option value="true">Yes</option>
								<option value="false">No</option>
							</select>

						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-info" value="Save"> <input
							type="hidden" name="id" id="id">
					</div>
				</form>
			</div>
		</div>
	</div>



	<!-- delete data -->
	<div id="deleteEmployeeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath}/deleteData">
					<div class="modal-header">
						<h4 class="modal-title">Delete Physical-Head</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to delete these Records?</p>

					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-danger" value="Delete"> <input
							type="hidden" name="id" id="id">
					</div>
				</form>
			</div>
		</div>

	</div>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>

</body>
</html>