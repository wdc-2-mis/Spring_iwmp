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
		<span style="text-decoration:underline;">Mid Term Project Evaluation - Cropped Details-1</span>
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
	State : &nbsp; <b><c:out value='${stName}' /></b>, &nbsp;&nbsp;&nbsp; 
	District : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp; 
	Financial Year : &nbsp; <b><c:out value='${fname}' /></b>, &nbsp;&nbsp;&nbsp;  Month : &nbsp; <b><c:out value='${mname}' /></b>
</div>
		



		<hr />
		<div>
			<table style="width: 100%">
<!-- 				<tr> -->
<%-- 		<td colspan="4" >District Name : &nbsp; <c:out value='${dname}' /> &nbsp;&nbsp;&nbsp; Project Name: &nbsp; <c:out value='${pname}' /> &nbsp;&nbsp;&nbsp;Month : &nbsp; <c:out value='${mname}' /> &nbsp;&nbsp;&nbsp;  --%>
<%-- 		Financial Year : &nbsp; <c:out value='${fname}' />  </td> --%>
<!-- 	</tr> -->
				<tr>
					<th rowspan ="2"><b>Sl.No.</b></th>
					<th rowspan ="2"><b>Crop Details</b></th>
					<th class ="" colspan ="2" style ="padding-left: 180px;"><b>Project Area Details</b></th>
					<th rowspan ="2"><b> Controlled Area Details</b></th>
					<th rowspan ="2"><b> Remarks</b></th>
				</tr>
				<tr>
					<th>Pre Project Status(Aggregate)</th>
					<th>Mid Project Status(Aggregate)</th>
				</tr>

					<c:if test="${wdcCrpDtlListSize > 0}">
						<c:forEach items="${wdcCrpDtlList}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td colspan ="5"><b><c:out value="Gross Cropped Area(ha)" /></b></td>
							</tr>
							<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Area under kharif crop(ha)" /></b></td>
							<td><input type="text" id="prekharif" name="prekharif"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.pregrossKharifCropArea}"/>						   
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="prekharifError"></span></td>
							<td><input type="text" id="midkharif" name="midkharif"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midgrossKharifCropArea}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midkharifError"></span></td>
							<td><input type="text" id="ckharif" name="ckharif"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_gross_kharif_crop_area}"/>	  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="ckharifError"></span></td>
							<td><textArea id="kharifremark" name="kharifremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.kharifCropremark}"/></textArea>
							<span class="kharifremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Area under rabi crop(ha)" /></b></td>
							<td><input type="text"
								id="prerabi" name="prerabi" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.pregrossRabiCropArea}"/>						 
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="prerabiError"></span></td>
							<td><input type="text"
								id="midrabi" name="midrabi" onfocusin="decimalToFourPlace(event)"
								class="" value=<c:out value="${list.midgrossRabiCropArea}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midrabiError"></span></td>
							<td><input type="text" id="crabi" name="crabi"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_gross_rabi_crop_area}"/>	
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="crabiError"></span></td>
							<td><textArea id="rabiremark" name="rabiremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.rabiCropremark}"/></textArea>
							<span class="rabiremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Area under Third crop(ha)" /></b></td>
							<td><input type="text" id="prethirdCrop" name="prethirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.pregrossThirdCropArea}"/>						  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="prethirdCropError"></span></td>
							<td><input type="text" id="midthirdCrop" name="midthirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midgrossThirdCropArea}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midthirdCropError"></span></td>
							<td><input type="text" id="cthirdCrop" name="cthirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_gross_third_crop_area}"/>		 
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="cthirdCropError"></span></td>
							<td><textArea id="thirdCropremark" name="thirdCropremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.thirdCropremark}"/></textArea>
							<span class="thirdCropremarkError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out value="Total Gross Cropped Area(ha)" /></b></td>
							<td><input type="number" id="precropedArea" name="precropedArea"
								autocomplete="off" value=<c:out value="${list.pregrossKharifCropArea + list.pregrossRabiCropArea + list.pregrossThirdCropArea}"/> readonly="readonly" /></td>
							<td><input type="number" id="midcropedArea" name="midcropedArea"
								autocomplete="off" 
								value=<c:out value="${list.midgrossKharifCropArea + list.midgrossRabiCropArea + list.midgrossThirdCropArea}"/>
								readonly="readonly" /></td>
							<td><input type="number" id="ccropedArea" name="ccropedArea"
								autocomplete="off"
								value=<c:out value="${list.control_gross_kharif_crop_area + list.control_gross_rabi_crop_area + list.control_gross_third_crop_area}"/> readonly="readonly"/>
								</td>
							<td></td>
						</tr>

						<tr>
							<td><b><c:out value="2." /></b></td>
							<td colspan ="5"><b><c:out value="Area under different Crops(ha)" /></b></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Cereals" /></b></td>
							<td><input type="text" id="precereals" name="precereals"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.predifferentCropCereals}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="precerealsError"></span></td>
							<td><input type="text" id="midcereals" name="midcereals"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.middifferentCropCereals}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midcerealsError"></span></td>
							<td><input type="text" id="ccereals" name="ccereals"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_different_crop_cereals}"/>		  
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="ccerealsError"></span></td>
							<td><textArea id="cerealsremark" name="cerealsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.cerealsremark}"/></textArea>
							<span class="cerealsremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Pulses" /></b></td>
							<td><input type="text" id="prepulses" name="prepulses"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.predifferentCropPulses}"/>						   
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="prepulsesError"></span></td>
							<td><input type="text" id="midpulses" name="midpulses"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.middifferentCropPulses}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midpulsesError"></span></td>
							<td><input type="text" id="cpulses" name="cpulses"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_different_crop_pulses}"/>		 
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cpulsesError"></span></td>
							<td><textArea id="pulsesremark" name="pulsesremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.pulsesremark}"/></textArea>
							<span class="pulsesremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Oil seed" /></b></td>
							<td><input type="text" id="preoilSeed" name="preoilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.predifferentCropOilSeed}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="preoilSeedError"></span></td>
							<td><input type="text" id="midoilSeed" name="midoilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.middifferentCropOilSeed}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midoilSeedError"></span></td>
							<td><input type="text" id="coilSeed" name="coilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_different_crop_oil_seed}"/>	   
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="coilSeedError"></span></td>
							<td><textArea id="oilSeedremark" name="oilSeedremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.oilSeedremark}"/></textArea>
							<span class="oilSeedremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="d." /></b></td>
							<td><b> <c:out value="Millets" /></b></td>
							<td><input type="text" id="premillets" name="premillets"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.predifferentCropMillets}"/>	
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="premilletsError"></span></td>
							<td><input type="text" id="midmillets" name="midmillets"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.middifferentCropMillets}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midmilletsError"></span></td>
							<td><input type="text" id="cmillets" name="cmillets"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_different_crop_millets}"/>		  
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cmilletsError"></span></td>
							<td><textArea id="milletsremark" name="milletsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.milletsremark}"/></textArea>
							<span class="milletsremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Other Crops" /></b></td>
							<td><input type="text" id="preothers" name="preothers"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.predifferentCropOther}"/>						  
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="preothersError"></span></td>
							<td><input type="text" id="midothers" name="midothers"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.middifferentCropOther}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midothersError"></span></td>
							<td><input type="text" id="cothers" name="cothers"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_different_crop_other}"/>	
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cothersError"></span></td>
							<td><textArea id="othersremark" name="othersremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.othersremark}"/></textArea>
							<span class="othersremarkError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total Area under different Crops(ha)" /></b></td>
							<td><input type="number" id="prediffCrop" name="prediffCrop"
								class="" value=<c:out value="${list.predifferentCropCereals + list.predifferentCropPulses + list.predifferentCropOilSeed + list.predifferentCropMillets + list.predifferentCropOther}"/>
								readonly="readonly" /></td>
							<td><input type="number" id="middiffCrop" name="middiffCrop"
								class="" value=<c:out value="${list.middifferentCropCereals + list.middifferentCropPulses + list.middifferentCropOilSeed + list.middifferentCropMillets + list.middifferentCropOther}"/>
								readonly="readonly" /></td>
							<td><input type="number" id="cdiffCrop" name="cdiffCrop"
								value=<c:out value="${list.control_different_crop_cereals + list.control_different_crop_pulses + list.control_different_crop_oil_seed + list.control_different_crop_millets + list.control_different_crop_other}"/>
								class="" readonly="readonly" /></td>
							<td></td>
						</tr>

						<tr>
							<td><b><c:out value="3." /></b></td>
							<td><b> <c:out value="Area of horticulture crop(ha)" /></b></td>
							<td><input type="text" id="prehorticulture" name="prehorticulture"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.prehorticultureArea}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="prehorticultureError"></span></td>
							<td><input type="text" id="midhorticulture" name="midhorticulture"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midhorticultureArea}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midhorticultureError"></span></td>
							<td><input type="text" id="chorticulture"
								name="chorticulture" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.control_horticulture_area}"/>
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="chorticultureError"></span></td>
							<td><textArea id="horticultureremark" name="horticultureremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.horticultureremark}"/></textArea>
							<span class="horticultureremarkError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Net Sown Area(ha)" /></b></td>
							<td><input type="text" id="prenetSown" name="prenetSown"
								value=<c:out value="${list.prenetsownArea}"/>	
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="prenetSownError"></span></td>
							<td><input type="text" id="midnetSown" name="midnetSown"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midnetsownArea}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midnetSownError"></span></td>
							<td><input type="text" id="cnetSown" name="cnetSown" 	  
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.control_netsown_area}"/>	
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cnetSownError"></span></td>
							<td><textArea id="netSownremark" name="netSownremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.netSownremark}"/></textArea>
							<span class="netSownremarkError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="5." /></b></td>
							<td><b> <c:out value="Cropping Intensity (%)" /></b></td>
							<td><input type="text" id="precropIntensity"
								name="precropIntensity" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.precroppingIntensity}"/>						 
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="precropIntensityError"></span></td>
							<td><input type="text" id="midcropIntensity"
								name="midcropIntensity" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.midcroppingIntensity}"/>	
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="midcropIntensityError"></span></td>
							<td><input type="text" id="ccropIntensity"
								name="ccropIntensity" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.control_cropping_intensity}"/> 
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="ccropIntensityError"></span></td>
							<td><textArea id="cropIntensityremark" name="cropIntensityremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.cropIntensityremark}"/></textArea>
							<span class="cropIntensityremarkError"></span></td>
						</tr>


						</c:forEach>
					</c:if>


					<c:if test="${wdcCrpDtlListSize eq 0}">
						<tr>
							<td><b><c:out value="1." /></b></td>
							<td colspan ="5"><b><c:out value="Gross Cropped Area(ha)" /></b></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Area under kharif crop(ha)" /></b></td>
							<td><input type="text" id="prekharif" name="prekharif"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="prekharifError"></span></td>
							<td><input type="text" id="midkharif" name="midkharif"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midkharifError"></span></td>
							<td><input type="text" id="ckharif" name="ckharif"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="ckharifError"></span></td>
							<td><textArea id="kharifremark" name="kharifremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="kharifremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Area under rabi crop(ha)" /></b></td>
							<td><input type="text"
								id="prerabi" name="prerabi" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="prerabiError"></span></td>
							<td><input type="text"
								id="midrabi" name="midrabi" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midrabiError"></span></td>
							<td><input type="text" id="crabi" name="crabi"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="crabiError"></span></td>
							<td><textArea id="rabiremark" name="rabiremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="rabiremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Area under Third crop(ha)" /></b></td>
							<td><input type="text" id="prethirdCrop" name="prethirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="prethirdCropError"></span></td>
							<td><input type="text" id="midthirdCrop" name="midthirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midthirdCropError"></span></td>
							<td><input type="text" id="cthirdCrop" name="cthirdCrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="cthirdCropError"></span></td>
							<td><textArea id="thirdCropremark" name="thirdCropremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="thirdCropremarkError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out value="Total Gross Cropped Area(ha)" /></b></td>
							<td><input type="number" id="precropedArea" name="precropedArea"
								autocomplete="off" readonly="readonly" /></td>
							<td><input type="number" id="midcropedArea" name="midcropedArea"
								autocomplete="off" readonly="readonly" /></td>
							<td><input type="number" id="ccropedArea" name="ccropedArea"
								autocomplete="off" readonly="readonly" /></td>
							<td></td>
						</tr>

						<tr>
							<td><b><c:out value="2." /></b></td>
							<td colspan ="5"><b><c:out value="Area under different Crops(ha)" /></b></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Cereals" /></b></td>
							<td><input type="text" id="precereals" name="precereals"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="precerealsError"></span></td>
							<td><input type="text" id="midcereals" name="midcereals"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midcerealsError"></span></td>
							<td><input type="text" id="ccereals" name="ccereals"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="ccerealsError"></span></td>
							<td><textArea id="cerealsremark" name="cerealsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="cerealsremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Pulses" /></b></td>
							<td><input type="text" id="prepulses" name="prepulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="prepulsesError"></span></td>
							<td><input type="text" id="midpulses" name="midpulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midpulsesError"></span></td>
							<td><input type="text" id="cpulses" name="cpulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cpulsesError"></span></td>
							<td><textArea id="pulsesremark" name="pulsesremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="pulsesremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Oil seed" /></b></td>
							<td><input type="text" id="preoilSeed" name="preoilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="preoilSeedError"></span></td>
							<td><input type="text" id="midoilSeed" name="midoilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midoilSeedError"></span></td>
							<td><input type="text" id="coilSeed" name="coilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="coilSeedError"></span></td>
							<td><textArea id="oilSeedremark" name="oilSeedremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="oilSeedremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="d." /></b></td>
							<td><b> <c:out value="Millets" /></b></td>
							<td><input type="text" id="premillets" name="premillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="premilletsError"></span></td>
							<td><input type="text" id="midmillets" name="midmillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midmilletsError"></span></td>
							<td><input type="text" id="cmillets" name="cmillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cmilletsError"></span></td>
							<td><textArea id="milletsremark" name="milletsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="milletsremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Other Crops" /></b>
							</td>
							<td><input type="text" id="preothers" name="preothers"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="preothersError"></span></td>
							<td><input type="text" id="midothers" name="midothers"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="midothersError"></span></td>
							<td><input type="text" id="cothers" name="cothers"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="diffcalSum()" maxlength="15"/><span
								class="cothersError"></span></td>
							<td><textArea id="othersremark" name="othersremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="othersremarkError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total Area under different Crops(ha)" /></b></td>
							<td><input type="number" id="prediffCrop" name="prediffCrop"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="middiffCrop" name="middiffCrop"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cdiffCrop" name="cdiffCrop"
								class="" readonly="readonly" /></td>
							<td></td>
						</tr>

						<tr>
							<td><b><c:out value="3." /></b></td>
							<td><b> <c:out value="Area of horticulture crop(ha)" /></b></td>
							<td><input type="text" id="prehorticulture" name="prehorticulture"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="prehorticultureError"></span></td>
							<td><input type="text" id="midhorticulture" name="midhorticulture"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midhorticultureError"></span></td>
							<td><input type="text" id="chorticulture"
								name="chorticulture" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="chorticultureError"></span></td>
							<td><textArea id="horticultureremark" name="horticultureremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="horticultureremarkError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Net Sown Area(ha)" /></b></td>
							<td><input type="text" id="prenetSown" name="prenetSown"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="prenetSownError"></span></td>
							<td><input type="text" id="midnetSown" name="midnetSown"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midnetSownError"></span></td>
							<td><input type="text" id="cnetSown" name="cnetSown"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cnetSownError"></span></td>
							<td><textArea id="netSownremark" name="netSownremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="netSownremarkError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="5." /></b></td>
							<td><b> <c:out value="Cropping Intensity (%)" /></b></td>
							<td><input type="text" id="precropIntensity"
								name="precropIntensity" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="precropIntensityError"></span></td>
							<td><input type="text" id="midcropIntensity"
								name="midcropIntensity" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="midcropIntensityError"></span></td>
							<td><input type="text" id="ccropIntensity"
								name="ccropIntensity" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="ccropIntensityError"></span></td>
							<td><textArea id="cropIntensityremark" name="cropIntensityremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="cropIntensityremarkError"></span></td>
						</tr>


					</c:if>
				<tr>
					<th colspan="6" style="padding-left: 650px;">
						<input type="button" name="view" id="view" value="Confirm"
						class="btn btn-info" />

					</th>
				</tr>
			</table>
		</div>
	</form>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/projectevaluation/croppedDetails1.js" />'></script>
</body>
</html>