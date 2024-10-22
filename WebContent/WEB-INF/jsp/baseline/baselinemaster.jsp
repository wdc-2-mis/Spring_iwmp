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
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">


<script src='<c:url value="/resources/js/baselinemaster.js" />'></script>
<body>
	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">

				<div class="col-sm-6">
					<h5>
						Baseline Survey Master Form <b>(m_bls_outcome)</b>
					</h5>

				</div>
				<div class="col-sm-6">
					<a href="#addbaselinemasterModal" id="add" class="btn btn-success"
						data-toggle="modal"><i class="material-icons"
						data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>

				</div>
			</div>
		</div>

		<c:if test="${not empty message}">
			<div class="alert alert-danger">
				<label style="color: blue; font-size: 20px;">${message}</label>
			</div>
		</c:if>
		<table class="table table-striped table-hover" id="baselinetable">
			<thead>
				<tr>

					<th>Seq no.</th>
					<th>Activity Code</th>
					<th>Baseline Type Code</th>
					<th>Baseline Type Description</th>
					<th>Baseline Description</th>
					<th>SeqNO</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="list" varStatus="status" items="${baseline}">
					<tr>
						<td>${status.count}</td>

                         <td><c:out value="${list.MBlsOutIdPk }"></c:out>
						</td>
						
                        <td><c:out value="${list.MBlsOutType.typeCode }"></c:out>
						</td>
						
						<td><c:out value="${list.MBlsOutType.description }"></c:out>
						</td>

						<td><c:out value="${list.description }"></c:out></td>

						<td><c:out value="${list.seqNo }"></c:out></td>

						<td><a href="#editbaselineModal" class="edit"
							data-toggle="modal"><i class="material-icons"
								data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
							type="hidden" id="id" value="${list.MBlsOutIdPk}"></td>

						<td><a href="#deletebaselineModal" id="delete" class="delete"
							data-toggle="modal"><i class="material-icons"
								data-toggle="tooltip" title="Delete">&#xE872;</i></a> <input
							type="hidden" id="id" value="${list.MBlsOutIdPk}"></td>
					</tr>
				</c:forEach>


			</tbody>
		</table>

		<div id="addbaselinemasterModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post"
						action="${pageContext.request.contextPath }/savebaseLineMasterData">
						<div class="modal-header">
							<h4 class="modal-title">Add Baseline Survey Master Data</h4>
							<button type="button" class="close"
								onClick="window.location.reload();" data-dismiss="modal"
								aria-hidden="true">&times;</button>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<label for="name">Baseline Type </label> <select name="btype"
									title="Project" id="btype" class="form-control"
									style="height: 100%" required="required">
								</select>
							</div>


							<div class="form-group">
								<label>Description</label> <input type="text"
									class="form-control" required="required" autocomplete="off"
									name="bdesc">
							</div>


							<div class="form-group">
								<label>Sequence No</label> <input type="text"
									class="form-control" id="seqno" required="required"
									autocomplete="off" name="seqno">
							</div>


						</div>
						<div class="modal-footer">
							<input type="button" class="btn btn-default" data-dismiss="modal"
								value="Cancel" onClick="window.location.reload();"> <input
								type="submit" class="btn btn-info" value="Save">
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- Edit baseline HTML -->
		<div id="editbaselineModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post"
						action="${pageContext.request.contextPath}/updatebaselineData">
						<div class="modal-header">
							<h4 class="modal-title">Edit Baseline Master Data</h4>
							<button type="reset" class="close"
								onClick="window.location.reload();" data-dismiss="modal"
								aria-hidden="true">&times;</button>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<label>Baseline Type Description</label> <select name="typedesc"
									title="Project" id="btype1" required="required"
									class="form-control" style="height: 100%">
								</select>
							</div>


							<input type="hidden" id="baselineId" name="id">
							<div class="form-group">
								<label>Baseline Description</label> <input type="text"
									class="form-control" id="bdesc" required="required"
									autocomplete="off" name="baslinedesc">
							</div>

							<div class="form-group">
								<label>Sequence No</label> <input type="text"
									class="form-control" id="seqno1" required="required"
									autocomplete="off" name="seqno">
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

		<!-- delete baseline record  -->
		<div id="deletebaselineModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post"
						action="${pageContext.request.contextPath}/deletebaselineData">
						<div class="modal-header">
							<h4 class="modal-title">Delete Baseline Type Data</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
						</div>


						<div class="modal-body">

							<p>Are you sure you want to delete this Records?</p>

						</div>
						<div class="modal-footer">
							<input type="button" class="btn btn-default" data-dismiss="modal"
								value="Cancel"> <input type="submit"
								class="btn btn-danger" value="Delete"> <input
								type="hidden" name="id" id="id">
						</div>
					</form>
				</div>
			</div>
		</div>

	</div>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>