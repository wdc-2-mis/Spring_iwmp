<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="card">

	<div class="table-responsive">

		<div class="col" style="text-align: center;">
			<h5></h5>
		</div>
		<div class="container-fluid">
			<div class="row">
				<div class="container mt-2">
					<div class="tab-content">
						<div id="physicalAnnualActionPlan"
							class="container tab-pane active">
							<br>
							<h3>Updation of Plot wise Details</h3>
							<form name="createHeadActivity" id="createHeadActivity">
								<lable class="message badge badge-danger error"></lable>
								<hr />
								<div class="form-row">
									<div class="form-group col-md-6">
										<label for="project">Projects: </label> <select 
											class="form-control project" id="project" name="project">
											<option value="">--Select Project--</option>
											<c:forEach items="${projectList}" var="project">
												<option value="<c:out value="${project.key}"/>"><c:out
														value="${project.value}" /></option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group col-md-6">
										<label for="year">Village: </label> <span class="villageError"></span>
										<select class="form-control block" id="village" name="village">
											<option value="">--Select Village--</option>

										</select>
									</div>
								</div>
								<div class="form-row">
									<div class="form-group col-6">
										<label for="startDate">Plot No./ Survey No. / Gata
											No.:</label> <span class="plotnoError"></span>
										<!-- 											<input type="text" id="plotno" name="plotno" class="form-control" maxlength="10" /> -->
										<select class="form-control block" id="plotno" name="plotno">
											<option value="">--Select Plot No--</option>

										</select>
									</div>

									<div class="form-group col-6">
										<label for="startDate">Plot Area (in
											ha.):</label> <span class="projectAreaError"></span> <input
											type="hidden" id="blsOutDetailId" name="blsOutDetailId"
											class="form-control" /> <input type="text" id="projectArea"
											name="projectArea" class="form-control"
											onfocusin="decimalToFourPlace(event)" readonly="readonly" />
									</div>

									<div class="form-group col-6">
										<label for="startDate">Irrigation Status:</label> <span
											class="ddlIrrigationStatusError"></span> <select
											id="ddlIrrigationStatus" name="ddlIrrigationStatus"
											class="ddlIrrigationStatus form-control"><option
												value="">--Select--</option></select>
									</div>
									
									
									<div class="form-group col-6">
										<label for="startDate" class ="d-none ddlMicroIrrigationArea">Micro-Irrigation Area:</label> <span
											class="ddlMicroIrrigationError"></span> <input type ="text"
											id="ddlMicroIrrigation" name="ddlMicroIrrigation"
											class="d-none ddlMicroIrrigation form-control" onfocusin="decimalToFourPlace(event)">
									</div>

								</div>



								<div class="form-row">
									<div class="form-group col">
										<h5 class="text-center font-weight-bold"></h5>
										<table class="listOwnerLand table" id="listOwnerLand"
											name="listOwnerLand">
											<thead>
												<tr>
													<th class="text-center">Ownership</th>
													<th class="text-center ownername d-none">Owner Name</th>
													<th class="text-center">Classification of Land</th>
													<!--next line added by Yogesh -->
													<th class="text-center subClassLand d-none">Sub Classification of Land</th>
													<th class="noofCrop">No. of Crop</th>
													<th class="forestlandType d-none">Forest Land Type</th>
												</tr>
											</thead>
											<tbody id="listActivityHeadWiseTbody">
												<tr>
													<td><span class="ownershipError"></span><select id="ddlOwnership" name="ddlOwnership"
														class="ddlOwnership form-control"><option
																value="">--Select--</option></select></td>
													<td class="d-none ownername"><span class="ownernameError"></span><input type="text"
														id="ownername" name="ownername"
														class="ownername form-control" /></td>
													<td><span class="landClassificationError"></span><select id="ddlLandClass" name="ddlLandClass"
														class="ddlLandClass form-control"><option
																value="">--Select--</option></select></td>
																<!--next lines for subClassLand added by Yogesh -->
													<td class="d-none subClassLand"><span class="landSubClassError"></span><input type="text"
														id="subClassLand" name="subClassLand"
														class="subClassLand form-control" /></td>
<td class="noofCrop"><select id="ddlNoofCrop" name="ddlNoofCrop" class="noofCrop form-control"><option value="">--Select--</option></select> <input type="hidden"
														id="cropNo" name="cropNo" class="form-control"
														maxlength="10" /></td>
													<td class="forestlandType d-none"><select
														id="ddlForestlandType" name="ddlForestlandType"
														class="ddlForestlandType form-control"><option
																value="">--Select--</option></select></td>
												</tr>
											</tbody>
										</table>

									</div>
								</div>

								<div class="form-row">
									<div class="form-group ">
										<!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->

										<div class="form-group col cropPlotDeatilDiv">
											<h5 class="text-center font-weight-bold">Added Crop
												Detail</h5>
											<table class="tblCropPlotData" id="tblCropPlotData"
												name="tblCropPlotData" style="width: 100%">
												<thead>
													<tr>
														<th class="text-center">Sl. No.</th>
														<th class="text-center" id="existSeason">Season</th>
														<th class="text-center">Crop Type</th>
														<th class="text-center">Area(in ha)</th>
														<th class="text-center">Crop Production per Hectare
															(in Quintal)</th>
														<th class="text-center">Avg. Income per Quintal (in
															Rs.)</th>
														<th class="text-center">Total Income (in Rs.)</th>
														<th class="text-center">Delete</th>
<!-- 														<th class="text-center">Edit</th> -->

													</tr>
												</thead>

												<tbody id="tblCropPlotDataBody">

												</tbody>
											</table>
										</div>
									</div>

									<div class="form-row">
										<div class="form-group col otherDetailsDiv"></div>
									</div>

									<div class="form-row">
										<div class="form-group col cropDeatilDiv d-none">
											<h5 class="text-center font-weight-bold">Add New Crop
												Detail</h5>
											<table class="tblCropDeatil table" id="tblCropDeatil"
												name="tblCropDeatil">
												<thead>
													<tr>

														<th class="text-center" id="disSeason">Season</th>
														<th class="text-center">Crop Type</th>
														<th class="text-center">Crop Area (in ha)<br />(col-A)
														</th>
														<th class="text-center">Crop production per Hectare
															(in Quintal)<br />(col-B)
														</th>
														<th class="text-center">Avg. Income per Quintal (in
															Rs.)<br />(col-C)
														</th>
														<th class="text-center">Total Income (in Rs.)<br />(col-A*col-B*col-C)
														</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody id="tblCropDeatilTbody">
												</tbody>
											</table>

										</div>
									</div>
									<div class="form-row">
										<div class="form-group col-md-6">
											<button class="btn btn-info d-none" id="draftSave"
												name="draftSave">Update</button>
										</div>
									</div>
							</form>
							<br />
						</div>



						<div id="viewHead" class="container tab-pane fade">
							<br>
							<h3>View Base Line Survey</h3>
							<hr />
							<table class="headActivity" id="aapHeadActivity"
								name="aapHeadActivity">
								<thead>
									<tr>
										<th>S.No</th>
										<th>Head</th>
										<th>Activity</th>
									</tr>
								</thead>
								<tbody id="tbodyHeadActivity"></tbody>
							</table>
						</div>
					</div>
					<!-- Edit Baseline Data -->
					<div id="editbaselinecomdata" class="modal fade">
						<div class="modal-dialog">
							<div class="modal-content">
							<form>
<!-- 								<form method="post" -->
<%-- 	onClick="window.location.reload();"								action="${pageContext.request.contextPath}/updateCropData"> --%>
									<div class="modal-header">
										<h4 class="modal-title">Edit Baseline Survey</h4>
										<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div style="position: relative;-webkit-box-flex: 1;-ms-flex: 1 1 auto;flex: 1 1 auto;padding: 1rem;">

										<div class="form-group" >
											<label id="lblSeason"  class="d-none">Season</label>
											 <select class="form-control d-none" name="season" title="Project" disabled="disabled"
												id="season" name="season" required="required"
												 style="height: 100%">



											</select>
										</div>

										<div class="form-group">
											<label>Crop Type</label> <select name="ctype" title="Project"
												id="ctype" name="ctype" required="required" disabled="disabled"
												class="form-control" style="height: 100%">

											</select>
										</div>
										<div class="form-group">
											<label>Area(in ha.)</label> <input type="text" onfocusin="decimalToFourPlace(event)"
												class="form-control areahac" id="areahac" name="areahac"
												required autocomplete="off" name="areahac"><span id="areahacError"></span>
										</div>

										<div class="form-group">
											<label>Crop Production per ha.</label> <input type="text"
												class="form-control" id="crop_prod" required="required"
												autocomplete="off" name="crop_prod">
										</div>

										<input type="hidden" id="bsl_crop_id" name="bsl_crop_id">
										<input type="hidden" id="plot_no" name="plot_no">
										<input type="hidden" id="vcode" name="vcode">
										<input type="hidden" id="proj_id" name="proj_id">

										<div class="form-group">
											<label>Avg. income per Quintal </label> <input type="text"
												class="form-control" id="avg_income" required="required"
												autocomplete="off" name="avg_income">
										</div>


<!-- 										<div class="form-group"> -->
<!-- 											<label for="name">Status: </label> <select name="status" -->
<!-- 												id="status" class="form-control" style="height: 100%" -->
<!-- 												required="required" name="status"> -->
<!-- 												<option value="">--Select--</option> -->
<!-- 												<option value="A">Active</option> -->
<!-- 												<option value="I">Inactive</option> -->
<!-- 											</select> -->

<!-- 										</div> -->

									</div>
									<div class="modal-footer">
<!-- 										<input type="button" class="btn btn-default" -->
<!-- 											data-dismiss="modal" onClick="window.location.reload();" -->
<!-- 											value="Cancel"> <input type="submit" -->
<!-- 											class="btn btn-info" value="Update"> -->
											
											<button class="btn btn-info" id="cropUpdate" 
												name="cropUpdate">Update</button>

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
<script src='<c:url value="/resources/js/blsOutcomeUpdate.js" />'></script>
</body>
</html>