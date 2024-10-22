<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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


<script src='<c:url value="/resources/js/allactivitymaster.js" />'></script>

<div class="table-responsive">

	<div class="col" style="text-align: center;">
		<h5></h5>
	</div>
	<div class="container-fluid">
		<div class="row">

			<!--  <h2>Toggleable Tabs</h2>
  <br> -->
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="nav-item"><a class="nav-link active"
					id="vieweditlivelihood" data-toggle="tab" href="#livelihood">Livelihood</a>
				</li>

				<li class="nav-item"><a class="nav-link"
					id="vieweditprodsystem" data-toggle="tab" href="#prodsystem">Production
						System</a></li>
				<li class="nav-item"><a class="nav-link" id="viewepa"
					data-toggle="tab" href="#epa">EPA</a></li>
				<li class="nav-item"><a class="nav-link" id="viewfpoactivity"
					data-toggle="tab" href="#fpoactivity">FPO Core Activity</a></li>
				<li class="nav-item"><a class="nav-link" id="viewshgactivity"
					data-toggle="tab" href="#shgactivity">SHG Core Activity</a></li>
				<li class="nav-item"><a class="nav-link"
					id="viewtrainingsubject" data-toggle="tab" href="#trainingsubject">Training
						Subject Area</a></li>
				<li class="nav-item"><a class="nav-link"
					id="project_prepare" data-toggle="tab" href="#projectpreparemaster">Preparedness </a></li>		

			</ul>

			<div class="tab-content" style="width: 100%">
				<!-- Livelihood Modal Start -->
				<div id="livelihood" class="table-responsive tab-pane active">
					<c:if test="${not empty message}">
						<div class="alert alert-danger">
							<label style="color: blue; font-size: 20px;">${message}</label>
						</div>
					</c:if>
					<div class="table-wrapper">
						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										Livelihood <b>(m_livelihood_coreactivity)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addlivelihoodModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New
											Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="physubacttable">
							<thead>
								<tr>

									<th style="width: 10%">Activity Code</th>
									<th>Livelihood Activity Description</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${livelihooddata}">
									<tr>
										<td><c:out value="${list.livelihoodCoreactivityId }"></c:out></td>
										<td><c:out value="${list.livelihoodCoreactivityDesc }"></c:out></td>
										<td><c:out value="${list.isActive }"></c:out></td>
										<td><a href="#editlivelihoodModal" class="editl"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
											type="hidden" id="id"
											value="${list.livelihoodCoreactivityId}"></td>

										<td><a href="#deletelivelihoodModal" id="delete"
											class="delete" data-toggle="modal"><i
												class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
											<input type="hidden" id="id"
											value="${list.livelihoodCoreactivityId}"> <input
											type="hidden" id="model" value="Livelihood"></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add livelihood data -->
					<div id="addlivelihoodModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add Livelihood Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Livelihood Activity Description</label> <input
												type="text" class="form-control" required="required"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" name="id" value="Livelihood">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>

					<!-- Edit Livelihood Modal HTML -->
					<div id="editlivelihoodModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateactivitymasterData">
									<div class="modal-header">
										<h4 class="modal-title">Edit Livelihood Activity</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Livelihood Activity Description</label> <input
												type="text" class="form-control" id="actdescl"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" id="subactivityId" name="id"> <input
											type="hidden" value="Livelihood" name="modal">

										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" 
											value="Cancel"> <input type="submit"
											class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete Livelihood Modal -->
					<div id="deletelivelihoodModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete Livelihood Activity</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> <input
											type="hidden" name="modal" value="Livelihood">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<!-- Production Modal Start -->
				<div id="prodsystem" class="table-responsive tab-pane fade">
					<div class="table-wrapper">
						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										Production System <b>(m_productivity_coreactivity)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addprodsystemModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New
											Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="physubacttable">
							<thead>
								<tr>

									<th style="width: 10%">Activity Code</th>
									<th>Production System Description</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${productiondata}">
									<tr>
										<td><c:out value="${list.productivityCoreactivityId }"></c:out></td>
										<td><c:out value="${list.productivityCoreactivityDesc }"></c:out></td>
										<td><c:out value="${list.isActive }"></c:out></td>
										<td><a href="#editProductionModal" class="editp"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
											type="hidden" id="id"
											value="${list.productivityCoreactivityId}"></td>

										<td><a href="#deleteProductionModal" id="delete"
											class="delete" data-toggle="modal"><i
												class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
											<input type="hidden" id="id"
											value="${list.productivityCoreactivityId}"> <input
											type="hidden" id="model" value="Production"></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add Production System data -->
					<div id="addprodsystemModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add Production System Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Production System Activity Description</label> <input
												type="text" class="form-control" required="required"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" name="id" value="Production">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>

					<!-- Edit Production Modal HTML -->
					<div id="editProductionModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateactivitymasterData">
									<div class="modal-header">
										<h4 class="modal-title">Edit Production System Activity</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Production System Activity Description</label> <input
												type="text" class="form-control" id="actdescp"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" id="subactivitypId" name="id"> <input
											type="hidden" value="Production" name="modal">

										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" 
											value="Cancel"> <input type="submit"
											class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete Production Data -->
					<div id="deleteProductionModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete Production System Activity</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> <input
											type="hidden" name="modal" value="Production">
									</div>
								</form>
							</div>
						</div>
					</div>


				</div>

				<!-- EPA Modal Start -->
				<div id="epa" class="table-responsive tab-pane fade">
					<div class="table-wrapper">
						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										EPA <b>(m_epa_coreactivity)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addepaModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New
											Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="physubacttable">
							<thead>
								<tr>

									<th style="width: 10%">Activity Code</th>
									<th>EPA Description</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${epadata}">
									<tr>
										<td><c:out value="${list.epaActivityId}"></c:out></td>
										<td><c:out value="${list.epaDesc}"></c:out></td>
										<td><c:out value="${list.isActive}"></c:out></td>
										<td><a href="#editepaModal" class="edite"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
											type="hidden" id="id" value="${list.epaActivityId}"></td>

										<td><a href="#deleteEPAModal" id="delete" class="delete"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Delete">&#xE872;</i></a> <input
											type="hidden" id="id" value="${list.epaActivityId}">
											<input type="hidden" id="model" value="EPA"></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add EPA data -->
					<div id="addepaModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add EPA Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>EPA Activity Description</label> <input type="text"
												class="form-control" required="required" autocomplete="off"
												name="actdesc">
										</div>
										<input type="hidden" name="id" value="EPA">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Edit EPA Modal HTML -->
					<div id="editepaModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateactivitymasterData">
									<div class="modal-header">
										<h4 class="modal-title">Edit EPA Activity</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>EPA Activity Description</label> <input type="text"
												class="form-control" id="actdesce" autocomplete="off"
												name="actdesc">
										</div>
										<input type="hidden" id="subactivityeId" name="id"> <input
											type="hidden" value="EPA" name="modal">

										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" 
											value="Cancel"> <input type="submit"
											class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete EPA Modal -->
					<div id="deleteEPAModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete EPA Activity</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> <input
											type="hidden" name="modal" value="EPA">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<!-- FPO Modal Start -->
				<div id="fpoactivity" class="table-responsive tab-pane fade">
					<div class="table-wrapper">

						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										FPO Core Activity <b>(m_fpo_coreactivity)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addfpoModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New
											Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="fpotable">
							<thead>
								<tr>

									<th style="width: 15%">Core Activity Code</th>
									<th>FPO Description</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${fpodata}">
									<tr>
										<td><c:out value="${list.fpoCoreactivityId }"></c:out></td>
										<td><c:out value="${list.fpoCoreactivityDesc }"></c:out></td>
										<td><c:out value="${list.isActive }"></c:out></td>
										<td><a href="#editfpoModal" class="editf"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
											type="hidden" id="id" value="${list.fpoCoreactivityId}"></td>

										<td><a href="#deleteFPOModal" id="delete" class="delete"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Delete">&#xE872;</i></a> <input
											type="hidden" id="id" value="${list.fpoCoreactivityId}">
											<input type="hidden" id="model" value="FPO"></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add FPO data -->
					<div id="addfpoModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add FPO Core Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>FPO Core Activity Description</label> <input
												type="text" class="form-control" required="required"
												id="actdesc" autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" name="id" value="FPO">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" id="savedata" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Edit FPO Modal HTML -->
					<div id="editfpoModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateactivitymasterData">
									<div class="modal-header">
										<h4 class="modal-title">Edit FPO Core Activity</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>FPO Core Activity Description</label> <input
												type="text" class="form-control" id="actdescf"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" id="subactivityfId" name="id"> <input
											type="hidden" value="FPO" name="modal">

										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" 
											value="Cancel"> <input type="submit"
											class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete FPO Modal -->
					<div id="deleteFPOModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete FPO Sub-Activity</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> <input
											type="hidden" name="modal" value="FPO">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<!-- SHG Modal Start -->
				<div id="shgactivity" class="table-responsive tab-pane fade">

					<div class="table-wrapper">
						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										SHG Core Activity <b>(m_shg_coreactivity)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addshgModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New
											Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="shgtable">
							<thead>
								<tr>

									<th style="width: 15%">Core Activity Code</th>
									<th>SHG Description</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${shgdata}">
									<tr>
										<td><c:out value="${list.shgCoreactivityId }"></c:out></td>
										<td><c:out value="${list.shgCoreactivityDesc }"></c:out></td>
										<td><c:out value="${list.isActive }"></c:out></td>
										<td><a href="#editshgModal" class="edits"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
											type="hidden" id="id" value="${list.shgCoreactivityId}"></td>

										<td><a href="#deleteSHGModal" id="delete" class="delete"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Delete">&#xE872;</i></a> <input
											type="hidden" id="id" value="${list.shgCoreactivityId}">
											<input type="hidden" id="model" value="SHG"></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add SHG data -->
					<div id="addshgModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add SHG Core Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>SHG Core Activity Description</label> <input
												type="text" class="form-control" required="required"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" name="id" value="SHG">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- add SHG data -->
					<div id="addshgModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add SHG Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>SHG Activity Description</label> <input type="text"
												class="form-control" required="required" autocomplete="off"
												name="actdesc">
										</div>
										<input type="hidden" name="id" value="SHG">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Edit SHG Modal HTML -->
					<div id="editshgModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateactivitymasterData">
									<div class="modal-header">
										<h4 class="modal-title">Edit SHG Core Activity</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>SHG Core Activity Description</label> <input
												type="text" class="form-control" id="actdescs"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" id="subactivitysId" name="id"> <input
											type="hidden" value="SHG" name="modal">

										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" 
											value="Cancel"> <input type="submit"
											class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete SHG Modal -->
					<div id="deleteSHGModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete SHG Sub-Activity</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> <input
											type="hidden" name="modal" value="SHG">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<!-- Training Start -->
				<div id="trainingsubject" class="table-responsive tab-pane fade">
					<div class="table-wrapper">
						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										Training Subject Area <b>(m_training_subject)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addtrainingModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New
											Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="trainingtable">
							<thead>
								<tr>

									<th style="width: 10%">Activity Code</th>
									<th>Training Subject Area Description</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${trainingdata}">
									<tr>
										<td><c:out value="${list.trainingSubId }"></c:out></td>
										<td><c:out value="${list.trainingSubDesc }"></c:out></td>
										<td><c:out value="${list.isActive }"></c:out></td>
										<td><a href="#edittrainingModal" class="editt"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
											type="hidden" id="id" value="${list.trainingSubId}"></td>

										<td><a href="#deleteTrainingModal" id="delete"
											class="delete" data-toggle="modal"><i
												class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
											<input type="hidden" id="id" value="${list.trainingSubId}">
											<input type="hidden" id="model" value="Training">
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add Training data -->
					<div id="addtrainingModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveAllMasterData">
									<div class="modal-header">
										<h4 class="modal-title">Add Training Subject Area
											Activity</h4>
										<button type="button" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Training Subject Area Description</label> <input
												type="text" class="form-control" required="required"
												autocomplete="off" name="actdesc">
										</div>
										<input type="hidden" name="id" value="Training_Area">
										<div class="form-group">
											<label for="name">Status </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select Status--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"
											> <input
											type="submit" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Edit Training Area HTML -->
					<div id="edittrainingModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateactivitymasterData">
									<div class="modal-header">
										<h4 class="modal-title">Edit Training Area Activity</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Training Area Description</label> <input type="text"
												class="form-control" id="actdesct" autocomplete="off"
												name="actdesc">
										</div>
										<input type="hidden" id="subactivitytId" name="id"> <input
											type="hidden" value="Training" name="modal">

										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>

										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" 
											value="Cancel"> <input type="submit"
											class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete Training Modal -->
					<div id="deleteTrainingModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete Training Subject Area</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> <input
											type="hidden" name="modal" value="Training">
									</div>
								</form>
							</div>
						</div>
					</div>


				</div>
				
				<!-- project prepare started -->
				
				<div id="projectpreparemaster" class="table-responsive tab-pane fade">
					<c:if test="${not empty message}">
						<div class="alert alert-danger">
							<label style="color: blue; font-size: 20px;">${message}</label>
						</div>
					</c:if>
					<div class="table-wrapper">
						<div class="table-title">

							<div class="row">

								<div class="col-sm-6">
									<h5>
										Project Preparedness master <b>(iwmp_m_project_prepare)</b>
									</h5>

								</div>
								<div class="col-sm-6">
									<a href="#addProjectPrepareModal" id="add" class="btn btn-success"
										data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a>

								</div>
							</div>
						</div>

						<table class="table table-striped table-hover" id="physubacttable">
							<thead>
								<tr>

									<th>Preparedness Code</th>
									<th>Preparedness Description</th>
									<th>Short Name</th>
									<th>Option 1st</th>
									<th>Option 2nd</th>
									<th>Sequence</th>
									<th>Status</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${preparedness}">
									<tr>
										<td><c:out value="${list.projectMprepareId}" /> </td>
										<td><c:out value="${list.prepareDesc}" /> </td>
										<td><c:out value="${list.shortDesc}" /> </td>
										<td><c:out value="${list.selectedDesc1}" /> </td>
										<td><c:out value="${list.selectedDesc2}" /> </td>
										<td><c:out value="${list.sequence}" /> </td>
										<td><c:if test="${list.active eq true}"> Active </c:if> <c:if test="${list.active ne true}"> InActive </c:if> </td>
										
										<td><a href="#editProjectPrepareModal" class="editPreparedness"
											data-toggle="modal"><i class="material-icons"
												data-toggle="tooltip" title="Edit">&#xE254;</i></a> 
												<input type="hidden" id="id" value="${list.projectMprepareId}"></td>

										<td><a href="#deleteProjectPrepareModal" id="delete"
											class="delete" data-toggle="modal"><i
												class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
											<input type="hidden" id="id" value="${list.projectMprepareId}"> 
											<input type="hidden" id="model" value="Preparedness"></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
					<!-- add ProjectPrepare data -->
					<div id="addProjectPrepareModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath }/saveProjectPrepareData">
									<div class="modal-header">
										<h4 class="modal-title">Add Project Preparedness</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Preparedness Description</label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="pdesc">
										</div>
										<div class="form-group">
											<label for="name">Short Name </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="srtname">
										</div>
										<div class="form-group">
											<label for="name">Option 1st </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="status1">
										</div>
										<div class="form-group">
											<label for="name">Option 2nd </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="status2">
										</div>
										<div class="form-group">
											<label for="name">Sequence </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="seqno" id="seqno" onfocusin="numericOnly(event)">
										</div>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-info" value="Save">

									</div>
								</form>
							</div>
						</div>
					</div>

					<!-- Edit ProjectPrepare Modal HTML -->
					<div id="editProjectPrepareModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/updateProjectPrepareData">
									<div class="modal-header">
										<h4 class="modal-title">Edit Project Preparedness Master</h4>
										<button type="reset" class="close"  data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">

										<div class="form-group">
											<label>Preparedness Description</label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="prepareDesc" id="prepareDesc">
										</div>
										<div class="form-group">
											<label for="name">Short Name </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="shortDesc" id="shortDesc">
										</div>
										<div class="form-group">
											<label for="name">Option 1st </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="selectedDesc1" id="selectedDesc1">
										</div>
										<div class="form-group">
											<label for="name">Option 2nd </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="selectedDesc2" id="selectedDesc2">
										</div>
										<div class="form-group">
											<label for="name">Sequence </label> 
											<input type="text" class="form-control" required="required" autocomplete="off" name="sequence" id="sequence" onfocusin="numericOnly(event)">
										</div>
										<div class="form-group">
											<label for="name">Status: </label> <select name="status"
												id="status" class="form-control" style="height: 100%"
												required="required" name="status">
												<option value="">--Select--</option>
												<option value="true">Active</option>
												<option value="false">Inactive</option>
											</select>
										</div>
										<input type="hidden" id="projectMprepareId" name="id"> 
										<input type="hidden" value="Preparedness" name="modal">
									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default" data-dismiss="modal"  value="Cancel"> 
										<input type="submit" class="btn btn-info" value="Save">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Delete ProjectPrepare Modal -->
					<div id="deleteProjectPrepareModal" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post"
									action="${pageContext.request.contextPath}/deleteallactivityData">
									<div class="modal-header">
										<h4 class="modal-title">Delete Project Prepare</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>


									<div class="modal-body">

										<p>Are you sure you want to delete this Records?</p>

									</div>
									<div class="modal-footer">
										<input type="button" class="btn btn-default"
											data-dismiss="modal" value="Cancel"> <input
											type="submit" class="btn btn-danger" value="Delete">
										<input type="hidden" name="id" id="id"> 
										<input type="hidden" name="modal" value="Preparedness">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>

	</div>
</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/physicalactionplan.js" />'></script>
</body>
</html>
