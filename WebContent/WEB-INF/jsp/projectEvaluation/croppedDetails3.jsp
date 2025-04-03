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
		<span style="text-decoration:underline;">Mid Term Project Evaluation - Cropped Details-3</span>
	</h4>
</div>

	<hr />
	<form name="croppedDetails3" id="croppedDetails3">
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
	Financial Year : &nbsp; <b><c:out value='${fname}' /></b>, &nbsp;&nbsp;&nbsp;  Month Name : &nbsp; <b><c:out value='${mname}' /></b>
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
								<td><b><c:out value="Area under plantation cover(Ha.)" /></b></td>
								<td><input type="text" id="prePlantationCover" name="prePlantationCover"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.prePlantationCover}"/>						   
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="prePlantationCoverError"></span></td>
							<td><input type="text" id="midPlantationCover" name="midPlantationCover"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midPlantationCover}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midPlantationCoverError"></span></td>
							<td><input type="text" id="controlPlantationCover" name="controlPlantationCover"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlPlantationCover}"/>	  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="controlPlantationCoverError"></span></td>
							<td><textArea id="remarkPlantationCover" name="remarkPlantationCover" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkPlantationCover}"/></textArea>
							<span class="remarkPlantationCoverError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td colspan ="5"><b><c:out value="Yield per hectare of major crops(Qt./Ha.)" /></b></td>
							</tr>
							<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Rice" /></b></td>
							<td><input type="text" id="preRice" name="preRice"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.preRice}"/>						   
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="preRiceError"></span></td>
							<td><input type="text" id="midRice" name="midRice"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midRice}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midRiceError"></span></td>
							<td><input type="text" id="controlRice" name="controlRice"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlRice}"/>	  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="cRiceError"></span></td>
							<td><textArea id="remarkRice" name="remarkRice" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkRice}"/></textArea>
							<span class="remarkRiceError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Wheat" /></b></td>
							<td><input type="text"
								id="preWheat" name="preWheat" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.preWheat}"/>						 
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="preWheatError"></span></td>
							<td><input type="text"
								id="midWheat" name="midWheat" onfocusin="decimalToFourPlace(event)"
								class="" value=<c:out value="${list.midWheat}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midWheatError"></span></td>
							<td><input type="text" id="controlWheat" name="controlWheat"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlWheat}"/>	
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="controlWheatError"></span></td>
							<td><textArea id="remarkWheat" name="remarkWheat" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkWheat}"/></textArea>
							<span class="remarkWheatError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Pulses" /></b></td>
							<td><input type="text" id="prePulses" name="prePulses"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.prePulses}"/>						  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="prePulsesError"></span></td>
							<td><input type="text" id="midPulses" name="midPulses"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midPulses}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midPulsesError"></span></td>
							<td><input type="text" id="controlPulses" name="controlPulses"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlPulses}"/>		 
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlPulsesError"></span></td>
							<td><textArea id="remarkPulses" name="remarkPulses" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkPulses}"/></textArea>
							<span class="remarkPulsesError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="d." /></b></td>
							<td><b> <c:out value="Millets" /></b></td>
							<td><input type="text" id="preMillets" name="preMillets"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.preMillets}"/>	
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="preMilletsError"></span></td>
							<td><input type="text" id="midMillets" name="midMillets"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midMillets}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midMilletsError"></span></td>
							<td><input type="text" id="controlMillets" name="controlMillets"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlMillets}"/>		  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlMilletsError"></span></td>
							<td><textArea id="remarkMillets" name="remarkMillets" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkMillets}"/></textArea>
							<span class="remarkMilletsError"></span></td>
						</tr>
						
						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Oil seeds" /></b></td>
							<td><input type="text" id="preOilSeed" name="preOilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.preOilSeed}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="preOilSeedError"></span></td>
							<td><input type="text" id="midOilSeed" name="midOilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midOilSeed}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midOilSeedError"></span></td>
							<td><input type="text" id="controlOilSeed" name="controlOilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlOilSeed}"/>	   
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlOilSeedError"></span></td>
							<td><textArea id="remarkOilSeed" name="remarkOilSeed" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkOilSeed}"/></textArea>
							<span class="remarkOilSeedError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Other Crops" /></b></td>
							<td><input type="text" id="preOther" name="preOther"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.preOther}"/>						  
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="preOtherError"></span></td>
							<td><input type="text" id="midOther" name="midOther"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midOther}"/>
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midOtherError"></span></td>
							<td><input type="text" id="controlOther" name="controlOther"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlOther}"/>	
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlOtherError"></span></td>
							<td><textArea id="remarkOther" name="remarkOther" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkOther}"/></textArea>
							<span class="remarkOtherError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total Area under different Crops(ha)" /></b></td>
							<td><input type="number" id="prediffCrop" name="prediffCrop"
								class="" value=<c:out value="${list.preRice + list.preWheat + list.prePulses + list.preMillets + list.preOilSeed + list.preOther}"/>
								readonly="readonly" /></td>
							<td><input type="number" id="middiffCrop" name="middiffCrop"
								class="" value=<c:out value="${list.preRice + list.midWheat + list.midPulses + list.midMillets + list.midOilSeed + list.midOther}"/>
								readonly="readonly" /></td>
							<td><input type="number" id="cdiffCrop" name="cdiffCrop"
								value=<c:out value="${list.controlRice + list.controlWheat + list.controlPulses + list.controlMillets + list.controlOilSeed + list.controlOther}"/>
								class="" readonly="readonly" /></td>
							<td></td>
						</tr>

						<tr>
							<td><b><c:out value="3." /></b></td>
							<td><b> <c:out value="Area of culturable wasteland(Ha.)" /></b></td>
							<td><input type="text" id="preCulturableWasteland" name="preCulturableWasteland"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.preCulturableWasteland}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preCulturableWastelandError"></span></td>
							<td><input type="text" id="midCulturableWasteland" name="midCulturableWasteland"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midCulturableWasteland}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midCulturableWastelandError"></span></td>
							<td><input type="text" id="controlCulturableWasteland"
								name="controlCulturableWasteland" onfocusin="decimalToFourPlace(event)"
								value=<c:out value="${list.controlCulturableWasteland}"/>
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="controlCulturableWastelandError"></span></td>
							<td><textArea id="remarkCulturableWasteland" name="remarkCulturableWasteland" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkCulturableWasteland}"/></textArea>
							<span class="remarkCulturableWastelandError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Area under protective irrigation(Ha.)" /></b></td>
							<td><input type="text" id="preProtectiveIrrigation" name="preProtectiveIrrigation"
								value=<c:out value="${list.preProtectiveIrrigation}"/>	
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preProtectiveIrrigationError"></span></td>
							<td><input type="text" id="midProtectiveIrrigation" name="midProtectiveIrrigation"
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.midProtectiveIrrigation}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midProtectiveIrrigationError"></span></td>
							<td><input type="text" id="controlProtectiveIrrigation" name="controlProtectiveIrrigation" 	  
								onfocusin="decimalToFourPlace(event)" class=""
								value=<c:out value="${list.controlProtectiveIrrigation}"/>	
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="controlProtectiveIrrigationError"></span></td>
							<td><textArea id="remarkProtectiveIrrigation" name="remarkProtectiveIrrigation" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkProtectiveIrrigation}"/></textArea>
							<span class="remarkProtectiveIrrigationError"></span></td>
						</tr>

						</c:forEach>
					</c:if>


					<c:if test="${wdcCrpDtlListSize eq 0}">
						<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Area under plantation cover(Ha.)" /></b></td>
								<td><input type="text" id="prePlantationCover" name="prePlantationCover"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="prePlantationCoverError"></span></td>
							<td><input type="text" id="midPlantationCover" name="midPlantationCover"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midPlantationCoverError"></span></td>
							<td><input type="text" id="controlPlantationCover" name="controlPlantationCover"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="controlPlantationCoverError"></span></td>
							<td><textArea id="remarkPlantationCover" name="remarkPlantationCover" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkPlantationCoverError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td colspan ="5"><b><c:out value="Yield per hectare of major crops(Qt./Ha.)" /></b></td>
							</tr>
							<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Rice" /></b></td>
							<td><input type="text" id="preRice" name="preRice"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="preRiceError"></span></td>
							<td><input type="text" id="midRice" name="midRice"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midRiceError"></span></td>
							<td><input type="text" id="controlRice" name="controlRice"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="cRiceError"></span></td>
							<td><textArea id="remarkRice" name="remarkRice" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkRiceError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Wheat" /></b></td>
							<td><input type="text"
								id="preWheat" name="preWheat" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="preWheatError"></span></td>
							<td><input type="text"
								id="midWheat" name="midWheat" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="midWheatError"></span></td>
							<td><input type="text" id="controlWheat" name="controlWheat"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/>
								<span class="controlWheatError"></span></td>
							<td><textArea id="remarkWheat" name="remarkWheat" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkWheatError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="c." /></b></td>
							<td><b> <c:out value="Pulses" /></b></td>
							<td><input type="text" id="prePulses" name="prePulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="prePulsesError"></span></td>
							<td><input type="text" id="midPulses" name="midPulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midPulsesError"></span></td>
							<td><input type="text" id="controlPulses" name="controlPulses"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlPulsesError"></span></td>
							<td><textArea id="remarkPulses" name="remarkPulses" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkPulsesError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="d." /></b></td>
							<td><b> <c:out value="Millets" /></b></td>
							<td><input type="text" id="preMillets" name="preMillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="preMilletsError"></span></td>
							<td><input type="text" id="midMillets" name="midMillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midMilletsError"></span></td>
							<td><input type="text" id="controlMillets" name="controlMillets"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlMilletsError"></span></td>
							<td><textArea id="remarkMillets" name="remarkMillets" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkMilletsError"></span></td>
						</tr>
						
						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Oil seeds" /></b></td>
							<td><input type="text" id="preOilSeed" name="preOilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="preOilSeedError"></span></td>
							<td><input type="text" id="midOilSeed" name="midOilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midOilSeedError"></span></td>
							<td><input type="text" id="controlOilSeed" name="controlOilSeed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlOilSeedError"></span></td>
							<td><textArea id="remarkOilSeed" name="remarkOilSeed" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkOilSeedError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="e." /></b></td>
							<td><b> <c:out value="Other Crops" /></b></td>
							<td><input type="text" id="preOther" name="preOther"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="preOtherError"></span></td>
							<td><input type="text" id="midOther" name="midOther"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="midOtherError"></span></td>
							<td><input type="text" id="controlOther" name="controlOther"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" onchange="calSum()" maxlength="15"/><span
								class="controlOtherError"></span></td>
							<td><textArea id="remarkOther" name="remarkOther" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkOtherError"></span></td>
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
							<td><b> <c:out value="Area of culturable wasteland(Ha.)" /></b></td>
							<td><input type="text" id="preCulturableWasteland" name="preCulturableWasteland"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preCulturableWastelandError"></span></td>
							<td><input type="text" id="midCulturableWasteland" name="midCulturableWasteland"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midCulturableWastelandError"></span></td>
							<td><input type="text" id="controlCulturableWasteland"
								name="controlCulturableWasteland" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
								class="controlCulturableWastelandError"></span></td>
							<td><textArea id="remarkCulturableWasteland" name="remarkCulturableWasteland" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkCulturableWastelandError"></span></td>
						</tr>

						<tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Area under protective irrigation(Ha.)" /></b></td>
							<td><input type="text" id="preProtectiveIrrigation" name="preProtectiveIrrigation"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preProtectiveIrrigationError"></span></td>
							<td><input type="text" id="midProtectiveIrrigation" name="midProtectiveIrrigation"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midProtectiveIrrigationError"></span></td>
							<td><input type="text" id="controlProtectiveIrrigation" name="controlProtectiveIrrigation" 	  
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="controlProtectiveIrrigationError"></span></td>
							<td><textArea id="remarkProtectiveIrrigation" name="remarkProtectiveIrrigation" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="remarkProtectiveIrrigationError"></span></td>
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
<script src='<c:url value="/resources/js/projectevaluation/croppedDetails3.js" />'></script>
</body>
</html>