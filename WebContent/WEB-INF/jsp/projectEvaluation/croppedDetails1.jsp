<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>
<script type="text/javascript"></script>
</head>

<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${dname}"/>&projName=<c:out value="${pname}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${mname}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${fname}"/>" style="position: absolute; left: 0;">
    	<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
	</a>
	<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Project Evaluation - Cropped Details-1</span>
	</h4>
</div>

	<hr />
	<form name="croppedDetails1" id="croppedDetails1">
		<input type="hidden" class="project" id="pcode" name="pcode"
			value=<c:out value = "${pcode}"/> /> <input type="hidden"
			class="district" id="dcode" name="dcode"
			value=<c:out value = "${dcode}"/> /> <input type="hidden"
			class="finYear" id="fcode" name="fcode"
			value=<c:out value = "${fcode}"/> /> <input type="hidden"
			class="month" id="mcode" name="mcode"
			value=<c:out value = "${mcode}"/> /> <input type="hidden"
			class="projectProfileId" id="projProfId" name="projProfId"
			value=<c:out value = "${projProId}"/> /> <input type="hidden"
			class="district" id="dname" name="dname"
			value="<c:out value = "${dname}"/>" /> <input type="hidden"
			class="project" id="pname" name="pname"
			value="<c:out value = "${pname}"/>" /> <input type="hidden"
			class="finYear" id="fname" name="fname"
			value=<c:out value = "${fname}"/> /> <input type="hidden"
			class="month" id="mname" name="mname"
			value=<c:out value = "${mname}"/> />
		
<div class="form-group">
	District Name : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp; 
	Month Name : &nbsp; <b><c:out value='${mname}' /></b>, &nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${fname}' /></b>
</div>
		



		<hr />
		<div>
			<table style="width: 80%">
<!-- 				<tr> -->
<%-- 		<td colspan="4" >District Name : &nbsp; <c:out value='${dname}' /> &nbsp;&nbsp;&nbsp; Project Name: &nbsp; <c:out value='${pname}' /> &nbsp;&nbsp;&nbsp;Month : &nbsp; <c:out value='${mname}' /> &nbsp;&nbsp;&nbsp;  --%>
<%-- 		Financial Year : &nbsp; <c:out value='${fname}' />  </td> --%>
<!-- 	</tr> -->
				<tr>
					<th><b>Sl.No.</b></th>
					<th><b>Crop Details</b></th>
					<th><b>Project Area Details</b></th>
					<th><b> Controlled Area Details</b></th>
				</tr>

					<c:if test="${wdcCrpDtlListSize > 0}">
						<c:forEach items="${wdcCrpDtlList}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Gross Cropped Area(ha)" /></b></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Area under kharif crop(ha)" /></b></td>
								<td><input type="text" id="kharif" name="kharif"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.grossKharifCropArea}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
									class="kharifError"></span></td>
								<td><input type="text" id="ckharif" name="ckharif"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_gross_kharif_crop_area}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
									class="ckharifError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Area under rabi crop(ha)" /></b></td>
								<td><input type="text" id="rabi" name="rabi"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.grossRabiCropArea}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
									class="rabiError"></span></td>
								<td><input type="text" id="crabi" name="crabi"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_gross_rabi_crop_area}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
									class="crabiError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Area under Third crop(ha)" /></b></td>
								<td><input type="text" id="thirdCrop" name="thirdCrop"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.grossThirdCropArea}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
									class="thirdCropError"></span></td>
								<td><input type="text" id="cthirdCrop" name="cthirdCrop"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_gross_third_crop_area}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
									class="cthirdCropError"></span></td>
							</tr>

							<tr>
								<td></td>
								<td><b><c:out value="Total Gross Cropped Area(ha)" /></b></td>
								<td><input type="number" id="cropedArea" name="cropedArea"
									autocomplete="off"
									value=<c:out value="${list.grossKharifCropArea + list.grossRabiCropArea + list.grossThirdCropArea}"/> readonly="readonly"/>
								</td>
								<td><input type="number" id="ccropedArea" name="ccropedArea"
									autocomplete="off"
									value=<c:out value="${list.control_gross_kharif_crop_area + list.control_gross_rabi_crop_area + list.control_gross_third_crop_area}"/> readonly="readonly"/>
								</td>
							</tr>

							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b><c:out value="Area under different Crops(ha)" /></b>
								</td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Cereals" /></b></td>
								<td><input type="text" id="cereals" name="cereals"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.differentCropCereals}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="cerealsError"></span></td>
								<td><input type="text" id="ccereals" name="ccereals"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_different_crop_cereals}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="ccerealsError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Pulses" /></b></td>
								<td><input type="text" id="pulses" name="pulses"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.differentCropPulses}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="pulsesError"></span></td>
								<td><input type="text" id="cpulses" name="cpulses"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_different_crop_pulses}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="cpulsesError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Oil seed" /></b></td>
								<td><input type="text" id="oilSeed" name="oilSeed"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.differentCropOilSeed}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="oilSeedError"></span></td>
								<td><input type="text" id="coilSeed" name="coilSeed"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_different_crop_oil_seed}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="coilSeedError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="d" /></b></td>
								<td><b> <c:out value="Millets" /></b></td>
								<td><input type="text" id="millets" name="millets"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.differentCropMillets}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="milletsError"></span></td>
								<td><input type="text" id="cmillets" name="cmillets"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_different_crop_millets}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="cmilletsError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="e" /></b></td>
								<td><b> <c:out value="Other Crops" /></b>
								</td>
								<td><input type="text" id="others" name="others"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.differentCropOther}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="othersError"></span></td>
								<td><input type="text" id="cothers" name="cothers"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_different_crop_other}"/>
									placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
									class="cothersError"></span></td>
							</tr>

							<tr>
								<td></td>
								<td><b><c:out
											value="Total Area under different Crops(ha)" /></b></td>
								<td><input type="number" id="diffCrop" name="diffCrop"
									class=""
									value=<c:out value="${list.differentCropCereals + list.differentCropPulses + list.differentCropOilSeed + list.differentCropMillets + list.differentCropOther}"/>
									readonly="readonly" /></td>
								<td><input type="number" id="cdiffCrop" name="cdiffCrop"
									class=""
									value=<c:out value="${list.control_different_crop_cereals + list.control_different_crop_pulses + list.control_different_crop_oil_seed + list.control_different_crop_millets + list.control_different_crop_other}"/>
									readonly="readonly" /></td>
							</tr>

							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Area of horticulture crop(ha)" /></b>
								</td>
								<td><input type="text" id="horticulture"
									name="horticulture" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.horticultureArea}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="horticultureError"></span></td>
								<td><input type="text" id="chorticulture"
									name="chorticulture" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_horticulture_area}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="chorticultureError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="Net Sown Area(ha)" /></b></td>
								<td><input type="text"
									id="netSown" name="netSown"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.netsownArea}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="netSownError"></span></td>
								<td><input type="text"
									id="cnetSown" name="cnetSown"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_netsown_area}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cnetSownError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b> <c:out value="Cropping Intensity" /></b></td>
								<td><input type="text" id="cropIntensity"
									name="cropIntensity" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.croppingIntensity}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cropIntensityError"></span></td>
								<td><input type="text" id="ccropIntensity"
									name="ccropIntensity" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_cropping_intensity}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="ccropIntensityError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="6." /></b></td>
								<td><b><c:out
											value="Area covered under diversified crops/change in cropping systems(ha)" /></b>
								</td>
								<td><input type="text" id="diversifiedCrop"
									name="diversifiedCrop" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.diversifiedChange}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="diversifiedCropError"></span></td>
								<td><input type="text" id="cdiversifiedCrop"
									name="cdiversifiedCrop" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_diversified_change}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cdiversifiedCropError"></span></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${wdcCrpDtlListSize eq 0}">
						<tr>
							<td><b><c:out value="1." /></b></td>
							<td><b><c:out value="Gross Cropped Area(ha)" /></b></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Area under kharif crop(ha)" /></b></td>
							<td><input type="text" id="kharif" name="kharif"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="kharifError"></span></td>
							<td><input type="text" id="ckharif" name="ckharif"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="ckharifError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Area under rabi crop(ha)" /></b></td>
							<td><input type="text"
								id="rabi" name="rabi" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="rabiError"></span></td>
							<td><input type="text" id="crabi" name="crabi"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="crabiError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Area under Third crop(ha)" /></b></td>
							<td><input type="text" id="thirdCrop" name="thirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="thirdCropError"></span></td>
							<td><input type="text" id="cthirdCrop" name="cthirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="cthirdCropError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out value="Total Gross Cropped Area(ha)" /></b></td>
							<td><input type="number" id="cropedArea" name="cropedArea"
								autocomplete="off" readonly="readonly" /></td>
							<td><input type="number" id="ccropedArea" name="ccropedArea"
								autocomplete="off" readonly="readonly" /></td>
						</tr>

						<tr>
							<td><b><c:out value="2." /></b></td>
							<td><b><c:out value="Area under different Crops(ha)" /></b></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Cereals" /></b></td>
							<td><input type="text" id="cereals" name="cereals"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cerealsError"></span></td>
							<td><input type="text" id="ccereals" name="ccereals"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="ccerealsError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Pulses" /></b></td>
							<td><input type="text" id="pulses" name="pulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="pulsesError"></span></td>
							<td><input type="text" id="cpulses" name="cpulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cpulsesError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Oil seed" /></b></td>
							<td><input type="text" id="oilSeed" name="oilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="oilSeedError"></span></td>
							<td><input type="text" id="coilSeed" name="coilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="coilSeedError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="d." /></b></td>
							<td><b> <c:out value="Millets" /></b></td>
							<td><input type="text" id="millets" name="millets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="milletsError"></span></td>
							<td><input type="text" id="cmillets" name="cmillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cmilletsError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Other Crops" /></b>
							</td>
							<td><input type="text" id="others" name="others"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="othersError"></span></td>
							<td><input type="text" id="cothers" name="cothers"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cothersError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total Area under different Crops(ha)" /></b></td>
							<td><input type="number" id="diffCrop" name="diffCrop"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cdiffCrop" name="cdiffCrop"
								class="" readonly="readonly" /></td>
						</tr>

						<tr>
							<td><b><c:out value="3." /></b></td>
							<td><b> <c:out value="Area of horticulture crop(ha)" /></b></td>
							<td><input type="text" id="horticulture" name="horticulture"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="horticultureError"></span></td>
							<td><input type="text" id="chorticulture"
								name="chorticulture" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="chorticultureError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Net Sown Area(ha)" /></b></td>
							<td><input type="text" id="netSown" name="netSown"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="netSownError"></span></td>
							<td><input type="text" id="cnetSown" name="cnetSown"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cnetSownError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="5." /></b></td>
							<td><b> <c:out value="Cropping Intensity" /></b></td>
							<td><input type="text" id="cropIntensity"
								name="cropIntensity" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="cropIntensityError"></span></td>
							<td><input type="text" id="ccropIntensity"
								name="ccropIntensity" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="ccropIntensityError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="6." /></b></td>
							<td><b> <c:out
										value="Area covered under diversified crops/change in cropping systems(ha)" /></b>
							</td>
							<td><input type="text" id="diversifiedCrop"
								name="diversifiedCrop" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="diversifiedCropError"></span></td>
							<td><input type="text" id="cdiversifiedCrop"
								name="cdiversifiedCrop" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="cdiversifiedCropError"></span></td>
						</tr>
					</c:if>
				<tr>
					<th colspan="4" style="align-content: center;">
						&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<input type="button" name="view" id="view" value="Confirm"
						class="btn btn-info" />

					</th>
				</tr>
			</table>
		</div>
	</form>
</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/projectevaluation/croppedDetails1.js" />'></script>
</body>
</html>