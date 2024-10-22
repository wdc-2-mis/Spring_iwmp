
<%@include file="/WEB-INF/jspf/header2.jspf"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.deleteAllCSS {
	text-align: center;
	width: 15%;
	margin: 0 auto;
	background: #001a33;
	padding: 8px;
	border-radius: 6px;
	margin-bottom: 3%;
	border-bottom: solid 4px #627295;
}

.form-control.txtnum {
	width: 70%;
}

.input {
	width: inherit !important;
}

.halfwidth {
	width: 70px !important;
}
}
</style>

<script>

//javascript:deleterecord

		function checkAllCheckBox(isChecked){
				if(isChecked) {
					$('input[name="type"]').each(function() {

						this.checked = true;

						});

						} else {

					$('input[name="type"]').each(function() {

		this.checked = false;

});

}

}

		function deleterecordepa(param1) {

			var id=param1;

			var form = document.getElementById('maintable');

			$(form)

			.append(

			'<input type="hidden" name="id" value="'+param1 +'" />');

				form.submit();

				};

			function hide(){

			//alert('hi');

			if(document.getElementById('iwmpMProject.projectId').value < 0)

					{

				alert("Select Project");

				return ;

					}

				var form = document.getElementById('maintable');

// if(document.getElementById('headType').value=='livelihood')

// {

// $('#tbleLivelihoodDetails').removeClass('d-none');

// $('#tblEpaDetails').addClass('d-none');

// $('#tbleProductionDetails').addClass('d-none');

// }

// if(document.getElementById('headType').value=='production')

// {

// $('#tbleLivelihoodDetails').addClass('d-none');

// $('#tbleProductionDetails').removeClass('d-none');

// $('#tblEpaDetails').addClass('d-none');

// }

// if(document.getElementById('headType').value=='epa')

// {

// $('#tbleLivelihoodDetails').addClass('d-none');

// $('#tbleProductionDetails').addClass('d-none');

// $('#tblEpaDetails').removeClass('d-none');

// }

			form.action="onChangeLivelihood";

			form.submit();

// alert('hi');

};

function hidecrop(param1){

var id=param1;

if(document.getElementById('productionDetailList'+param1+'.mProductivityCoreactivity.productivityCoreactivityId').value=='16')

{

document.getElementById('productionDetailList'+param1+'.mCroptype.cropId').value='0';

document.getElementById('productionDetailList'+param1+'.mCroptype.cropId').disabled=false;

// $('#dvPassport'+param1).show();

}

if(document.getElementById('productionDetailList'+param1+'.mProductivityCoreactivity.productivityCoreactivityId').value!='16')

{

document.getElementById('productionDetailList'+param1+'.mCroptype.cropId').disabled=true;

//$('#dvPassport'+param1).hide();

}

};

</script>

<div class="maindiv">

	<div class="col formheading" style="">

		<h4>

			<u>Add/Edit Livelihood / Production / EPA </u>

		</h4>

	</div>



	<form:form class="form-inline" action="saveLivelihoodProductionEpa"
		method="post" modelAttribute="maintable" autocomplete="off">

		<body>

          	<c:if test="${not empty error}">

				<div class="form-group col-md-12">

					<div class="errormessage">

						<label class="alert alert-danger"> ${error}</label>

					</div>

				</div>

			</c:if>

			<c:if test="${not empty msg}">

				<div class="form-group col-md-12">

					<div class="col">

						<label class="alert alert-success"> ${msg} </label>

					</div>

				</div>

			</c:if>

			<div class="form-group col-md-4">

				<label for="project" class="">Project:- </label>

				<form:select class="form-control col project"
					onchange="javascript:hide();" path="iwmpMProject.projectId">

					<form:option value="-1" label="---Select---"></form:option>

					<form:options items="${projectList}" />

				</form:select>

				<form:errors path="iwmpMProject.projectId" cssclass="errormsg" />

			</div>

			<div class="form-group col-md-6">

				<label for="project" class="">Head:-</label>

				<form:select class="form-control col head"
					onchange="javascript:hide();" path="headType">

					<form:option value="-1">--Select Type--</form:option>

					<form:option value="livelihood">Livelihood Activities for the Assetless Persons & Microenterprises</form:option>

					<form:option value="production">Production System</form:option>

					<form:option value="epa">Entry Point Activity (EPA)</form:option>

				</form:select>

			</div>

			<br />

			<br />

			<br>

			<br>

			<br />

			<div style="margin-left: auto; margin-right: auto;">

				<c:if test="${maintable.livelihoodDetailsList!=null}">

					<c:if test="${maintable.headType!='livelihood'}">

						<table id="tbleLivelihoodDetails"
							class="table table-bordered table-striped table-highlight w-auto d-none">

							</c:if>

							<c:if test="${maintable.headType=='livelihood'}">

								<table id="tbleLivelihoodDetails"
									class="table table-bordered table-striped table-highlight w-auto">

									</c:if>

									<tr>

										<th rowspan="2">No.</th>

										<th rowspan="2">Name of Activity</th>

										<th rowspan="2">No of Activities</th>

										<th style="width: 50%; text-align: center;" colspan="5">No.

											of Beneficiaries</th>

										<th rowspan="2">Average Income of Beneficiaries Prior to

											Livelihood activities (in Rs)</th>

										<th rowspan="2">Average Income of Beneficiaries Post

											Livelihood activities (in Rs)</th>

										<th rowspan="2">Action</th>

									</tr>

									<tr>

										<th>SC</th>

										<th>ST</th>

										<th>Others</th>

										<th>Total</th>

										<th>Women out of Total</th>

									</tr>

									<tr>

										<th>1</th>

										<th>2</th>

										<th>3</th>

										<th>4</th>

										<th>5</th>

										<th>6</th>

										<th>7</th>

										<th>8</th>

										<th>9</th>

										<th>10</th>

										<th>11</th>

									</tr>

									<c:forEach items="${maintable.livelihoodDetailsList}" var="liv" varStatus="status">

										<tr>

											<td align="center">${status.count}</td>
											<td><form:select class="custom-select-sm"
													path="livelihoodDetailsList[${status.index}].mLivelihoodCoreactivity.livelihoodCoreactivityId">

													<form:option value="0" label="---Select---"></form:option>

													<form:options items="${sourcelivelihoodList}" />

												</form:select> <form:errors
													path="livelihoodDetailsList[${status.index}].mLivelihoodCoreactivity.livelihoodCoreactivityId"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum"
												id="noActivities[${status.index}]"
												name="livelihoodDetailsList[${status.index}].noActivities"
												value="${liv.noActivities}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].noActivities"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum txtCal"
												style="width: 70px;" data-id="${status.index}l"
												id="lsc[${status.index}]"
												name="livelihoodDetailsList[${status.index}].sc"
												value="${liv.sc}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].sc"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum txtCal"
												style="width: 70px;" data-id="${status.index}l"
												id="lst[${status.index}]"
												name="livelihoodDetailsList[${status.index}].st"
												value="${liv.st}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].st"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum txtCal"
												style="width: 70px;" data-id="${status.index}l"
												id="lother[${status.index}]"
												name="livelihoodDetailsList[${status.index}].other"
												value="${liv.other}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].other"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum" type="text"
												style="width: 80px;" disabled="disabled"
												id="ltotal[${status.index}]" name="total[${status.index}]"
												value="${liv.sc+liv.st+liv.other}" /></td>

											<td><input class="form-control txtnum"
												id="women[${status.index}]"
												name="livelihoodDetailsList[${status.index}].women"
												value="${liv.women}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].women"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum"
												id="increaseIncome[${status.index}]"
												name="livelihoodDetailsList[${status.index}].preAvgIncome"
												value="${liv.preAvgIncome}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].preAvgIncome"
													cssclass="errormsg" /></td>

											<td><input class="form-control txtnum"
												id="increaseIncome[${status.index}]"
												name="livelihoodDetailsList[${status.index}].postAvgIncome"
												value="${liv.postAvgIncome}" /> <form:errors
													path="livelihoodDetailsList[${status.index}].postAvgIncome"
													cssclass="errormsg" /></td>

                                            
											<td><c:if test="${status.count>1}">

													<input type="submit" value="Delete" class="square_btn"
														name="ondelete"
														onclick="javascript:deleterecordepa(${status.index})">

												</c:if>
										</tr>

									</c:forEach>

									<tr>

										<td colspan="11" align="left">
											<input type="submit" class="square_btn" name="add" value="Add Row" /> 
											<input type="submit" class="square_btn" value="Save Livelihood" />
											<!-- <input type="button" class="square_btn" value="Delete Selected Row" onclick="javascript:deleteAllRow()"/> -->
										</td>

									</tr>

<tr>
<td colspan="12">
<label  style="text-align: left;"><span style="color:red;"><b>Note:</b></span> &nbspYou can edit the <b> &nbsp&nbsp"Avg. Income of Beneficiaries Post Production System activities" &nbsp&nbsp</b> once after the data is locked. &nbsp&nbsp&nbsp&nbsp&nbsp</label>
</td>
</tr>
									<c:set var="distname" value="" />

									<c:if test="${not empty livelihoodData}">

										<c:forEach items="${livelihoodData}" var="proj"
											varStatus="status">

											<c:if test="${distname eq '' }">

												<c:set var="distname"
													value="${proj.livelihoodEpaProd.iwmpMProject.projName}" />

											</c:if>

										</c:forEach>

										<tr>

											<th colspan="12" style="text-align: center;">Project :<c:out
													value="${distname }" /></th>

										</tr>

										<tr>

											<th rowspan="2">No.</th>

											<th rowspan="2">Name of Activity</th>

											<th rowspan="2">No of Activities</th>

											<th style="text-align: center;" colspan="5">No. of

												Beneficiaries</th>

											<th rowspan="2">Average Income of Beneficiaries Prior to

												Livelihood activities (in Rs)</th>

											<th rowspan="2">Average Income of Beneficiaries Post

												Livelihood activities (in Rs)</th>

											<th rowspan="2" colspan="2">Action</th>

										</tr>

										<tr>

											<th>SC</th>

											<th>ST</th>

											<th>Others</th>

											<th>Total</th>

											<th>Women out of Total</th>

										</tr>

										<tr>

											<th>1 Select All<input type="checkbox"
												onclick="checkAllCheckBox(this.checked)"></th>

											<th>2</th>

											<th>3</th>

											<th>4</th>

											<th>5</th>

											<th>6</th>

											<th>7</th>

											<th>8</th>

											<th>9</th>

											<th>10</th>

											<th colspan="2">11</th>

										</tr>

				<c:forEach items="${livelihoodData}" var="proj" varStatus="status">

					<c:set var="distname" value="${proj.livelihoodEpaProd.iwmpMProject.projName}" />

					<tr id="${proj.livelihoodDetailId}">

					<td align="center">
					<c:if test="${proj.status eq 'C'}">

						<input type="checkbox" disabled checked />

					</c:if> 
					<c:if test="${proj.status eq 'D'}">
					
		<input type="checkbox" class="chkIndividual" id="chkIndividual${proj.livelihoodDetailId}" name="type" value="${proj.livelihoodDetailId}"/>
						<%-- <input type="checkbox" name="type" value="${proj.livelihoodDetailId}l" /> --%>

					</c:if>
				</td>

		<%-- <td align="center"><input type="checkbox" name="type" value="${proj.livelihoodDetailId}l" id="checkBoxId${proj.livelihoodDetailId}"></td> --%>

				<td>${proj.MLivelihoodCoreactivity.livelihoodCoreactivityDesc}</td>

				<td>${proj.noActivities}</td>

				<td>${proj.sc}</td>

				<td>${proj.st}</td>

				<td>${proj.other}</td>

				<td>${proj.sc+proj.st+proj.other}</td>

				<td>${proj.women}</td>

				<td>${proj.preAvgIncome}</td>

				<td>${proj.postAvgIncome}
				
				<c:if test="${proj.status eq 'C'  && proj.poststatus ne 'C'}">
							<a href="#editlivelihoodpost" class="editl" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
							<input type="hidden" id="id" value="${proj.livelihoodDetailId}">
					</c:if>
				</td>

				<td colspan="2">
					<c:if test="${proj.status eq 'D'}">

						<a href="#" data-id="${proj.livelihoodDetailId}l" class="delete">Delete</a>

						<a href="#" data-id="${proj.livelihoodDetailId}l" class="confirm">Complete</a>

					</c:if> 
					<c:if test="${proj.status eq 'C'}">
							Locked
					</c:if>
				</td>

				</tr>

			</c:forEach>

		</c:if>

		</table>

	</c:if>

	<c:if test="${not empty livelihoodData}">

		<button id="btnWCDCForward" name="btnWCDCForward" class="btn btn-info">Complete</button>

	</c:if>

							</div>

							<div style="margin-left: auto; margin-right: auto;">

								<c:if test="${maintable.productionDetailList!=null}">

									<c:if test="${maintable.headType!='production'}">

										<table id="tbleProductionDetails" style="width: 150%"
											class="table table-bordered table-striped table-highlight w-auto d-none">

											</c:if>

											<c:if test="${maintable.headType=='production'}">

												<table id="tbleProductionDetails" style="width: 150%"
													class="table table-bordered table-striped table-highlight w-auto">

													</c:if>

													<tr>

														<th rowspan="2">No.</th>

														<th rowspan="2">Name of Activity</th>

														<th rowspan="2">Type of Tree Crop</th>

														<th rowspan="2">No of Activities</th>

														<th style="width: 60%; text-align: center;" colspan="5">No.

															of Beneficiaries</th>

														<th rowspan="2">Average Income of Beneficiaries Prior

															to Production System activities(in Rs)</th>

														<th rowspan="2">Average Income of Beneficiaries Post

															Production System activities(in Rs)</th>

														<th rowspan="2">Action</th>

													</tr>

													<tr>

														<th>SC</th>

														<th>ST</th>

														<th>Others</th>

														<th>Total</th>

														<th>Women out of Total</th>

													</tr>

													<tr>

														<th>1</th>

														<th>2</th>

														<th>3</th>

														<th>4</th>

														<th>5</th>

														<th>6</th>

														<th>7</th>

														<th>8</th>

														<th>9</th>

														<th>10</th>

														<th>11</th>

														<th>12</th>

													</tr>

													<c:forEach items="${maintable.productionDetailList}"
														var="liv" varStatus="status">

														<tr>

															<td align="center">${status.count}</td>

															<td><form:select class="custom-select-sm"
																	onchange="javascript:hidecrop(${status.index})"
																	path="productionDetailList[${status.index}].mProductivityCoreactivity.productivityCoreactivityId">

																	<form:option value="0" label="---Select---"></form:option>

																	<form:options items="${sourceProductionList}" />

																</form:select> <form:errors
																	path="productionDetailList[${status.index}].mProductivityCoreactivity.productivityCoreactivityId"
																	cssclass="errormsg" /></td>

															<td>

																<div id="dvPassport${status.index}">

																	<c:if
																		test="${liv.MProductivityCoreactivity.productivityCoreactivityId==16}">

																		<form:select class="custom-select-sm"
																			path="productionDetailList[${status.index}].mCroptype.cropId">

																			<form:option value="0" label="---Select---"></form:option>

																			<form:options items="${cropList}" />

																		</form:select>

																	</c:if>

																	<c:if
																		test="${liv.MProductivityCoreactivity.productivityCoreactivityId!=16}">

																		<form:select class="custom-select-sm" disabled="true"
																			path="productionDetailList[${status.index}].mCroptype.cropId">

																			<form:option value="0" label="---Select---"></form:option>

																			<form:options items="${cropList}" />

																		</form:select>

																	</c:if>

																	<form:errors
																		path="productionDetailList[${status.index}].mCroptype.cropId"
																		cssclass="errormsg" />

																</div>

															</td>

															<td><input class="form-control txtnum"
																id="noActivities[${status.index}]"
																name="productionDetailList[${status.index}].noActivities"
																value="${liv.noActivities}" /> <form:errors
																	path="productionDetailList[${status.index}].noActivities"
																	cssclass="errormsg" /></td>

															<td><input class="form-control txtnum txtPal"
																style="width: 70px;" id="psc[${status.index}]"
																name="productionDetailList[${status.index}].sc"
																value="${liv.sc}" /> <form:errors
																	path="productionDetailList[${status.index}].sc"
																	cssclass="errormsg" /></td>

															<td><input class="form-control txtnum txtPal"
																style="width: 70px;" id="pst[${status.index}]"
																name="productionDetailList[${status.index}].st"
																value="${liv.st}" /> <form:errors
																	path="productionDetailList[${status.index}].st"
																	cssclass="errormsg" /></td>

															<td><input class="form-control txtnum txtPal"
																style="width: 70px;" id="pother[${status.index}]"
																name="productionDetailList[${status.index}].other"
																value="${liv.other}" /> <form:errors
																	path="productionDetailList[${status.index}].other"
																	cssclass="errormsg" /></td>

															<td><input class="form-control txtnum" type="text"
																style="width: 80px;" id="ptotal[${status.index}]"
																disabled="disabled" name="total[${status.index}]"
																value="${liv.sc+liv.st+liv.other}" /></td>

															<td><input class="form-control txtnum"
																id="women[${status.index}]"
																name="productionDetailList[${status.index}].women"
																value="${liv.women}" /> <form:errors
																	path="productionDetailList[${status.index}].women"
																	cssclass="errormsg" /></td>

															<td><input class="form-control txtnum"
																id="increaseIncome[${status.index}]"
																name="productionDetailList[${status.index}].preAvgIncome"
																value="${liv.preAvgIncome}" /> <form:errors
																	path="livelihoodDetailsList[${status.index}].preAvgIncome"
																	cssclass="errormsg" /></td>

															<td><input class="form-control txtnum"
																id="increaseIncome[${status.index}]"
																name="productionDetailList[${status.index}].postAvgIncome"
																value="${liv.postAvgIncome}" /> <form:errors
																	path="livelihoodDetailsList[${status.index}].postAvgIncome"
																	cssclass="errormsg" /></td>

															<td><c:if test="${status.count>1}">

																	<input type="submit" value="Delete" class="square_btn"
																		name="ondelete"
																		onclick="javascript:deleterecordepa(${status.index})">

																</c:if></td>

														</tr>

													</c:forEach>

													<tr>
														<td colspan="12" align="left">
														<input type="submit" class="square_btn" name="add" value="Add Row" /> 
														<input type="submit" class="square_btn" value="Save Production" />
														</td>

													</tr>
                                                    
													<c:if test="${not empty productionData}">

														<c:forEach items="${productionData}" var="proj"
															varStatus="status">

															<c:if test="${distname eq '' }">

															<c:set var="distname" value="${proj.livelihoodEpaProd.iwmpMProject.projName}" />

															</c:if>

														</c:forEach>
<tr>
<td colspan="12">
<label  style="text-align: left;"><span style="color:red;"><b>Note:</b></span> &nbspYou can edit the <b> &nbsp&nbsp"Avg. Income of Beneficiaries Post Production System activities" &nbsp&nbsp</b> once after the data is locked. &nbsp&nbsp&nbsp&nbsp&nbsp</label>
</td>
</tr>
												<tr>
                                                          
															<th colspan="12" style="text-align: center;">Project

																:<c:out value="${distname }" />

															</th>
														<tr>

															<th rowspan="2">Sl. No.</th>

															<th rowspan="2">Name of Activity</th>

															<th rowspan="2">Type of Tree Crop</th>

															<th rowspan="2">No of Activities</th>

															<th colspan="5" style="text-align: center;">No. of

																Beneficiaries</th>

															<th rowspan="2">Avg. Income of Beneficiaries Prior

																to Production System activities(in Rs)</th>

															<th rowspan="2">Avg. Income of Beneficiaries Post

																Production System activities(in Rs)</th>

															<th rowspan="2">Action</th>

														</tr>

														<tr>

															<th>SC</th>

															<th>ST</th>

															<th>Others</th>

															<th>Total</th>

															<th>Women out of Total</th>

														</tr>

														<tr>

															<th>1 Select All<input type="checkbox"
																onclick="checkAllCheckBox(this.checked)">
															<th>
															<th>2</th>

															<th>3</th>

															<th>4</th>

															<th>5</th>

															<th>6</th>

															<th>7</th>

															<th>8</th>

															<th>9</th>

															<th>10</th>

															<th>11</th>

<!-- 															<th></th> -->

														</tr>

		<c:forEach items="${productionData}" var="proj" varStatus="status">

		<tr>

		<td align="center">
		<c:if test="${proj.status eq 'C'}">

				<input type="checkbox" disabled checked />

		</c:if> 
		<c:if test="${proj.status eq 'D'}">
		<input type="checkbox" class="chkIndividual" id="chkIndividual${proj.productionDetailId}" 
							name="type" value="${proj.productionDetailId}"/>
			<%-- <input type="checkbox" name="type" value="${proj.productionDetailId}p" /> --%>

		</c:if>
		</td>

		<%-- <td align="center">${status.count}</td> --%>

		<td>${proj.MProductivityCoreactivity.productivityCoreactivityDesc}</td>

		<td>${proj.MCroptype.cropDesc}</td>

		<td>${proj.noActivities}</td>

		<td>${proj.sc}</td>

		<td>${proj.st}</td>

		<td>${proj.other}</td>

		<td>${proj.sc+proj.st+proj.other}</td>

		<td>${proj.women}</td>

		<td>${proj.preAvgIncome}</td>

		<td>${proj.postAvgIncome}
		<c:if test="${proj.status eq 'C' && proj.poststatus ne 'C'}">
							<a href="#editproductionpost" class="editp" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
							<input type="hidden" id="id" value="${proj.productionDetailId}">
					</c:if>
		</td>

		<td>
			<c:if test="${proj.status eq 'D'}">

			<a href="#" data-id="${proj.productionDetailId}p" class="delete">Delete</a>

			<a href="#" data-id="${proj.productionDetailId}p" class="confirm">Complete</a>

		</c:if> 
		<c:if test="${proj.status eq 'C'}">

			Locked

		</c:if>
		</td>

		</tr>

	</c:forEach>

	</c:if>

	</table>

</c:if>

<c:if test="${not empty productionData}">

	<button id="btnWCDCForward" name="btnWCDCForward" class="btn btn-info">Complete</button>

</c:if>

											</div>

											<div style="margin-left: auto; margin-right: auto;">

												<c:if test="${maintable.epaDetailList!=null}">

													<c:if test="${maintable.headType!='epa'}">

														<table id="tblEpaDetails" class="table table-bordered table-striped table-highlight w-auto d-none">

															</c:if>

															<c:if test="${maintable.headType=='epa'}">

																<table id="tblEpaDetails"
																	class="table table-bordered table-striped table-highlight w-auto">

																	</c:if>

																	<tr>

																		<th>Sl. No.</th>

																		<th>Name of Activity</th>

																		<th>No of Activities</th>

																		<th>Action</th>

																	</tr>

																	<tr>

																		<th>1</th>

																		<th>2</th>

																		<th>3</th>

																		<th></th>

																	</tr>

																	<c:forEach items="${maintable.epaDetailList}" var="liv"
																		varStatus="status">

																		<tr>

																			<td align="center">${status.count}</td>

																			<td><form:select class="custom-select-sm"
																					path="epaDetailList[${status.index}].mEpaCoreactivity.epaActivityId">

																					<form:option value="0" label="---Select---"></form:option>

																					<form:options items="${sourceEpaList}" />

																				</form:select> <form:errors
																					path="epaDetailList[${status.index}].mEpaCoreactivity.epaActivityId"
																					cssclass="errormsg" /></td>

																			<td><input class="form-control txtnum"
																				id="noActivities[${status.index}]"
																				name="epaDetailList[${status.index}].noActivities"
																				value="${liv.noActivities}" /> <form:errors
																					path="epaDetailList[${status.index}].noActivities"
																					cssclass="errormsg" /></td>

																			<td><c:if test="${status.count>1}">
																					<input type="submit" value="Delete"
																						class="square_btn" name="ondelete"
																						onclick="javascript:deleterecordepa(${status.index})">

																				</c:if></td>

																		</tr>

																	</c:forEach>

																	<tr>
																	<tr>

																	<td colspan="12" align="left">
																	<input type="submit" class="square_btn" name="add" value="Add Row" /> 
																	<input type="submit" class="square_btn" name="saveEpa" value="Save EPA" />
																	</td>

																	</tr>

																	<c:if test="${not empty epaData}">

																		<c:forEach items="${epaData}" var="proj"
																			varStatus="status">

																			<c:if test="${distname eq '' }">

																				<c:set var="distname"
																					value="${proj.livelihoodEpaProd.iwmpMProject.projName}" />

																			</c:if>

																		</c:forEach>

																		<tr>

																			<th colspan="11" style="text-align: center;">Project

																				:<c:out value="${distname }" />

																			</th>
																		<tr>

																			<th>No.</th>

																			<th>Name of Activity</th>

																			<th>No of Activities</th>

																			<th>Action</th>

																		</tr>

																		<tr>

																			<th>1 Select All<input type="checkbox"
																				onclick="checkAllCheckBox(this.checked)" /></th>

																			<th>2</th>

																			<th>3</th>

																			<th>4</th>

																		</tr>

			<c:forEach items="${epaData}" var="proj" varStatus="status">

			<tr>

			<td align="center">
				<c:if test="${proj.status eq 'C'}">

				<input type="checkbox" disabled checked />

				</c:if> 
				<c:if test="${proj.status eq 'D'}">
					<input type="checkbox" class="chkIndividual" id="chkIndividual${proj.epaDetailId}" 
							name="type" value="${proj.epaDetailId}"/>
				<%-- 	<input type="checkbox" name="type" value="${proj.epaDetailId}e" /> --%>
				</c:if>
			</td>

			<td>${proj.MEpaCoreactivity.epaDesc}</td>

			<td>${proj.noActivities}</td>

			<td>
			<c:if test="${proj.status eq 'D'}">

			<a href="#" data-id="${proj.epaDetailId}e" class="delete">Delete</a>

			<a href="#" data-id="${proj.epaDetailId}e" class="confirm">Complete</a>

			</c:if> 
			<c:if test="${proj.status eq 'C'}">

			Locked

			</c:if>
			</td>

			</tr>

		</c:forEach>

	</c:if>

	</table>

	</c:if>

<c:if test="${not empty epaData}">

<button id="btnWCDCForward" name="btnWCDCForward" class="btn btn-info">Complete</button>

</c:if>

</div>
</body>

</form:form>

</div>

<div id="editlivelihoodpost" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
   
    <form method="post"  action="${pageContext.request.contextPath}/updatelivePostData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Post Livelihood Activities </h4>
       <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
   </div>
  
   
    <div class="modal-body">
   <div class="form-group">
       <label>Name of Activity</label>
       <input type="text" id="activity" disabled="disabled" class="form-control"  autocomplete = "off" required = "required"  name="activity">
      </div>
    
   <div class="form-group">
       <label>Average Income of Beneficiaries Post to Livelihood activities </label>
       <input type="text" id="livepost"  class="form-control"  autocomplete = "off" onfocusin="numericOnly(event)" required = "required"  name="livepost">
      </div> 
  <input type="hidden"  id ="activityId" name="id">    
   </div>
  
   <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" onClick="window.location.reload();" value="Cancel">
      <input type="submit" class="btn btn-info" value="Save">
      
     </div> 
  </form> 
</div>
</div>
</div>   

<div id="editproductionpost" class="modal fade">
  <div class="modal-dialog">
   <div class="modal-content">
   
    <form method="post"  action="${pageContext.request.contextPath}/updateprodPostData">
     <div class="modal-header">      
      <h4 class="modal-title">Edit Post Production Activities </h4>
       <button type="reset" class="close" onClick="window.location.reload();" data-dismiss="modal" aria-hidden="true">&times;</button>
   </div>
  
   
    <div class="modal-body">
   <div class="form-group">
       <label>Name of Activity</label>
       <input type="text" id="pactivity" disabled="disabled" class="form-control"  autocomplete = "off" required = "required"  name="pactivity">
      </div>
    
   <div class="form-group">
       <label>Average Income of Beneficiaries Post Production System activities </label>
       <input type="text" id="prodpost"  class="form-control"  autocomplete = "off" required = "required" onfocusin="numericOnly(event)" name="prodpost">
      </div> 
  <input type="hidden"  id ="pactivityId" name="id">    
   </div>
  
   <div class="modal-footer">
      <input type="button" class="btn btn-default" data-dismiss="modal" onClick="window.location.reload();" value="Cancel">
      <input type="submit" class="btn btn-info" value="Save">
      
     </div> 
  </form> 
</div>
</div>
</div> 


<footer class=" text-center">

	<%@include file="/WEB-INF/jspf/footer2.jspf"%>

</footer>

<script src='<c:url value="/resources/js/livelihood.js" />'></script>

</ body>

</html>