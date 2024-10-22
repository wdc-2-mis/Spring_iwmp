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
		<span style="text-decoration:underline;">Project Evaluation - Cropped Details-2</span>
	</h4>
</div>

	<hr />
	<form name="croppedDetails2" id="croppedDetails2">
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
			District Name : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp;Month Name : &nbsp;<b> <c:out value='${mname}' /></b>, &nbsp;&nbsp;&nbsp; 
		Financial Year : &nbsp; <b><c:out value='${fname}' /></b>
			</div>

		<hr />
		<div>
			<table style="width: 80%">
				<tr>
					<th><b>Sl.No.</b></th>
					<th><b>Crop Details</b></th>
					<th><b>Project Area Details</b></th>
					<th><b> Controlled Area Details</b></th>

					<c:if test="${wdcCrpDtlListSize > 0}">
						<c:forEach items="${wdcCrpDtlList}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Area brought from Nil/Single crop to double or more crop(ha.)" /></b></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Nil to single crop(ha.)" /></b></td>
								<td><input type="text" id="niltosingle" name="niltosingle"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.nillSingle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="niltosingleError" autocomplete = "off"></span></td>
								<td><input type="text" id="cniltosingle" name="cniltosingle"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_nill_single}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="cniltosingleError" autocomplete = "off"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Single to double or more crop(ha.)" /></b></td>
								<td><input type="text" id="sdcrop" name="sdcrop"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.singelDoublemore}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="sdcropError" autocomplete = "off"></span></td>
								<td><input type="text" id="csdcrop" name="csdcrop"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_singel_doublemore}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="csdcropError" autocomplete = "off"></span></td>
							
							
							<tr>
								<td></td>
								<td><b><c:out value="Total Gross Cropped Area(ha)" /></b></td>
								<td><input type="text" id="totalArea" name="totalArea"
									autocomplete="off" maxlength="15"
									value=<c:out value="${list.nillSingle + list.singelDoublemore}"/> readonly="readonly"/>
								</td>
								<td><input type="text" id="ctotalArea" name="ctotalArea"
									autocomplete="off" maxlength="15"
									value=<c:out value="${list.control_nill_single + list.control_singel_doublemore}"/> readonly="readonly"/>
								</td>
							</tr>

                           </tr>
                              <tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Area under plantation cover" /></b>
								</td>
								<td><input type="text" id="plantation"
									name="plantation" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.plantationCover}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" autocomplete = "off" maxlength="15"/><span class="plantationError"></span></td>
								<td><input type="text" id="cplantation"
									name="cplantation" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_plantation_cover}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" autocomplete = "off" maxlength="15"/><span class="cplantationError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b><c:out value="Yeild per hectare of major crops(Qtl./ha.)" /></b>
								</td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Rice" /></b></td>
								<td><input type="text" id="rice" name="rice"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.rice}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="riceError" autocomplete = "off"></span></td>
								<td><input type="text" id="crice" name="crice"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_rice}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="criceError" autocomplete = "off"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Wheat" /></b></td>
								<td><input type="text" id="wheat" name="wheat"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.wheat}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="wheatError" autocomplete = "off"></span></td>
								<td><input type="text" id="cwheat" name="cwheat"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_wheat}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="cwheatError" autocomplete = "off"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Pulses" /></b></td>
								<td><input type="text" id="pulses" name="pulses"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.pulses}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="pulsesError" autocomplete = "off"></span></td>
								<td><input type="text" id="cpulses" name="cpulses"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_pulses}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="cpulsesError" autocomplete = "off"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="d" /></b></td>
								<td><b> <c:out value="Millets" /></b></td>
								<td><input type="text" id="millets" name="millets"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.millets}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="milletsError" autocomplete = "off"></span></td>
								<td><input type="text" id="cmillets" name="cmillets"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_millets}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="cmilletsError" autocomplete = "off"></span></td>
							</tr>

<tr>
								<td style="text-align: right;"><b><c:out value="e" /></b></td>
								<td><b> <c:out value="Oil Seed" /></b></td>
								<td><input type="text" id="oilseed" name="oilseed"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.oil_seed}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="oilseedError" autocomplete = "off"></span></td>
								<td><input type="text" id="coilseed" name="coilseed"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_oil_seed}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="coilseedError" autocomplete = "off"></span></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="f" /></b></td>
								<td><b> <c:out value="Others(Specify name of the crop)" /></b>
								</td>
								<td><input type="text" id="others" name="others"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.other}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="othersError" autocomplete = "off"></span></td>
								<td><input type="text" id="cothers" name="cothers"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_other}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
									class="cothersError" autocomplete = "off"></span></td>
							</tr>

							<tr>
								<td></td>
								<td><b><c:out
											value="Total Area under different Crops(ha)" /></b></td>
								<td><input type="text" id="diffCrop" name="diffCrop"
									class=""
									value=<c:out value="${list.rice + list.wheat + list.pulses + list.millets + list.oil_seed + list.other}"/>
									readonly="readonly" /></td>
								<td><input type="text" id="cdiffCrop" name="cdiffCrop"
									class=""
									value=<c:out value="${list.control_rice + list.control_wheat + list.control_pulses + list.control_millets + list.control_oil_seed + list.control_other}"/>
									readonly="readonly" /></td>
							</tr>

							
						</c:forEach>
					</c:if>

					<c:if test="${wdcCrpDtlListSize eq 0}">
						<tr>
							<td><b><c:out value="1." /></b></td>
							<td><b><c:out value="Area brought from Nil/Single crop to double or more crop(ha.)" /></b></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Nil to single crop(ha.)" /></b></td>
							<td><input type="text" id="niltosingle" name="niltosingle"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="niltosingleError"></span></td>
							<td><input type="text" id="cniltosingle" name="cniltosingle"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="cniltosingleError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Single to double or more crop(ha.)" /></b></td>
							<td><input type="text" id="sdcrop" name="sdcrop" 
							onfocusin="decimalToFourPlace(event)" class="" 
							placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="sdcropError"></span></td>
							<td><input type="text" id="csdcrop" name="csdcrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="csdcropError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out value="Total Nil to Single / Single to double Area(ha)" /></b></td>
							<td><input type="number" id="totalArea" name="totalArea"
								autocomplete="off" maxlength="15" readonly="readonly" /></td>
							<td><input type="number" id="ctotalArea" name="ctotalArea"
								autocomplete="off" maxlength="15" readonly="readonly" /></td>
						</tr>

                        <tr>
							<td><b><c:out value="2." /></b></td>
							<td><b> <c:out value="Area under plantation cover(ha.)" /></b></td>
							<td><input type="text" id="plantation" name="plantation"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="plantationError"></span></td>
							<td><input type="text" id="cplantation"
								name="cplantation" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="cplantationError"></span></td>
						</tr>
						<tr>
							<td><b><c:out value="3." /></b></td>
							<td><b><c:out value="Yield per hectare of major crops(Qtl./ha.)" /></b></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Rice" /></b></td>
							<td><input type="text" id="rice" name="rice"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="riceError"></span></td>
							<td><input type="text" id="crice" name="crice"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="criceError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Wheat" /></b></td>
							<td><input type="text" id="wheat" name="wheat"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="wheatError"></span></td>
							<td><input type="text" id="cwheat" name="cwheat"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="cwheatError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Pulses" /></b></td>
							<td><input type="text" id="pulses" name="pulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="pulsesError"></span></td>
							<td><input type="text" id="cpulses" name="cpulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="cpulsesError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="d." /></b></td>
							<td><b> <c:out value="Millets" /></b></td>
							<td><input type="text" id="millets" name="millets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="milletsError"></span></td>
							<td><input type="text" id="cmillets" name="cmillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="cmilletsError"></span></td>
						</tr>

                       <tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Oil seed" /></b>
							</td>
							<td><input type="text" id="oilseed" name="oilseed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="oilseedError"></span></td>
							<td><input type="text" id="coilseed" name="coilseed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="coilseedError"></span></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="f." /></b></td>
							<td><b> <c:out value="Others(Specify name of the crop)" /></b>
							</td>
							<td><input type="text" id="others" name="others"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="othersError"></span></td>
							<td><input type="text" id="cothers" name="cothers"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="diffcalSum()" /><span
								class="cothersError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total Yield per hectare of major crops(Qtl./ha.)" /></b></td>
							<td><input type="number" id="majorCrop" name="majorCrop"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cmajorCrop" name="cmajorCrop"
								class="" readonly="readonly" /></td>
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
<script src='<c:url value="/resources/js/projectevaluation/croppedDetails2.js" />'></script>
</body>
</html>