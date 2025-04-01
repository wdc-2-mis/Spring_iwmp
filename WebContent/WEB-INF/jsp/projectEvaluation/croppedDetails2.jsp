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
			<table style="width: 100%">
				<tr>
					<th><b>Sl.No.</b></th>
					<th><b>Crop Details</b></th>
					<th><b>Project Area Details</b></th>
					<th><b> Controlled Area Details</b></th>
					<th><b> Remarks</b></th>

					<c:if test="${wdcCrpDtlListSize > 0}">
						<c:forEach items="${wdcCrpDtlList}" var="list">
						
						    <tr>
							<td><b><c:out value="1." /></b></td>
							<td><b> <c:out value="Area covered under diversified crops/ change in cropping system (Ha.)" /></b></td>
							<td><input type="text" id="diversifiedcrops" name="diversifiedcrops"
								onfocusin="decimalToFourPlace(event)" value=<c:out value="${list.projectDiversifiedChange}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="diversifiedcropsError"></span></td>
							<td><input type="text" id="cdiversifiedcrops"
								name="cdiversifiedcrops" onfocusin="decimalToFourPlace(event)" value=<c:out value="${list.controlDiversifiedChange}"/>
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="cdiversifiedcropsError"></span></td>
								<td><textArea id="diversifiedcropsremark" name="diversifiedcropsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkDiversifiedChange}"/></textArea>
							<span class="diversifiedcropsError"></span></td>
						</tr>
						    
							 <tr>
								<td><b><c:out value="2." /></b></td>
								<td><b><c:out value="Area brought from Nil/Single crop to double or more crop(ha.)" /></b></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Nil to single crop(ha.)" /></b></td>
								<td><input type="text" id="niltosingle" name="niltosingle"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.projectNillSingle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="niltosingleError" autocomplete = "off"></span></td>
								<td><input type="text" id="cniltosingle" name="cniltosingle"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlNillSingle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="cniltosingleError" autocomplete = "off"></span></td>
									<td><textArea id="niltosingleremark" name="niltosingleremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkNillSingle}"/></textArea>
							<span class="niltosingleremarkremarkError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Single to double or more crop(ha.)" /></b></td>
								<td><input type="text" id="sdcrop" name="sdcrop"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.projectSingleDoublemore}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="sdcropError" autocomplete = "off"></span></td>
								<td><input type="text" id="csdcrop" name="csdcrop"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlSingleDoublemore}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
									class="csdcropError" autocomplete = "off"></span></td>
									<td><textArea id="sdcropremark" name="sdcropremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkSingleDoublemore}"/></textArea>
							        <span class="sdcropremarkError"></span></td>
							</tr>
							
							<tr>
								<td></td>
								<td><b><c:out value="Total Gross Cropped Area(ha)" /></b></td>
								<td><input type="text" id="totalArea" name="totalArea"
									autocomplete="off" maxlength="15"
									value=<c:out value="${list.projectNillSingle + list.projectSingleDoublemore}"/> readonly="readonly"/>
								</td>
								<td><input type="text" id="ctotalArea" name="ctotalArea"
									autocomplete="off" maxlength="15"
									value=<c:out value="${list.controlNillSingle + list.controlSingleDoublemore}"/> readonly="readonly"/>
								</td>
							</tr>

							 <tr>
							<td><b><c:out value="3." /></b></td>
							<td><b> <c:out value="No. of Water Harvesting Structure (WHS) constructed /rejuvenated" /></b></td>
							<td><input type="text" id="WHSConReju" name="WHSConReju"
								 value=<c:out value="${list.projectWhsConstructedRejuvenated}"/>
								placeholder="Only Numeric" onmousedown="numericOnly(event);" autocomplete="off" maxlength="10" /><span class="WHSConRejuError"></span></td>
							<td><input type="text" id="cWHSConReju"
								name="cWHSConReju"  value=<c:out value="${list.controlWhsConstructedRejuvenated}"/>
								class="" placeholder="Only Numeric" onmousedown="numericOnly(event);" autocomplete="off" maxlength="10" /><span
								class="cWHSConRejuError"></span></td>
								<td><textArea id="WHSConRejuremark" name="WHSConRejuremark" autocomplete = "off" rows="2" cols="22" maxlength="200" ><c:out value="${list.remarkWhsConstructedRejuvenated}"/></textArea>
							<span class="WHSConRejuremarkError"></span></td>
						</tr>
                        
                        <tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Area Covered with soil and Moisture (Ha.)" /></b></td>
							<td><input type="text" id="soilandmoiscrops" name="soilandmoiscrops"
								onfocusin="decimalToFourPlace(event)" value=<c:out value="${list.projectSoilMoisture}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="soilandmoiscropsError"></span></td>
							<td><input type="text" id="csoilandmoiscrops" value=<c:out value="${list.controlSoilMoisture}"/>
								name="csoilandmoiscrops" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="csoilandmoiscropsError"></span></td>
								<td><textArea id="soilandmoiscropsremark" name="soilandmoiscropsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"> <c:out value="${list.remarkSoilMoisture}"/></textArea>
							<span class="soilandmoiscropsError"></span></td>
						</tr>
						
						<tr>
							<td><b><c:out value="5." /></b></td>
							<td><b> <c:out value="Area of degraded land covered /rainfed area developed (Ha.)" /></b></td>
							<td><input type="text" id="degradedrainfed" name="degradedrainfed"
								onfocusin="decimalToFourPlace(event)" class="" value=<c:out value="${list.projectDegradedRainfed}"/>
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="degradedrainfedError"></span></td>
							<td><input type="text" id="cdegradedrainfed"
								name="cdegradedrainfed" onfocusin="decimalToFourPlace(event)" value=<c:out value="${list.controlDegradedRainfed}"/>
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="cdegradedrainfedError"></span></td>
								<td><textArea id="degradedrainfedremark" name="degradedrainfedremark" autocomplete = "off" rows="2" cols="22" maxlength="200"><c:out value="${list.remarkDegradedRainfed}"/></textArea>
							<span class="degradedrainfedError"></span></td>
						</tr>
						</c:forEach>
					</c:if>

					<c:if test="${wdcCrpDtlListSize eq 0}">
					
					    <tr>
							<td><b><c:out value="1." /></b></td>
							<td><b> <c:out value="Area covered under diversified crops/ change in cropping system (Ha.)" /></b></td>
							<td><input type="text" id="diversifiedcrops" name="diversifiedcrops"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="diversifiedcropsError"></span></td>
							<td><input type="text" id="cdiversifiedcrops"
								name="cdiversifiedcrops" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="cdiversifiedcropsError"></span></td>
								<td><textArea id="diversifiedcropsremark" name="diversifiedcropsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="diversifiedcropsError"></span></td>
						</tr>
						
						<tr>
							<td><b><c:out value="2." /></b></td>
							<td><b><c:out value="Area brought from Nil/Single crop to double or more crop (Ha.)" /></b></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b><c:out value="a." /></b></td>
							<td><b> <c:out value="Nil to single crop (Ha.)" /></b></td>
							<td><input type="text" id="niltosingle" name="niltosingle"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="niltosingleError"></span></td>
							<td><input type="text" id="cniltosingle" name="cniltosingle"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="cniltosingleError"></span></td>
							<td><textArea id="niltosingleremark" name="niltosingleremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="niltosingleremarkError"></span></td>
						</tr>

						<tr>
							<td style="text-align: right;"><b><c:out value="b." /></b></td>
							<td><b> <c:out value="Single to double or more crop (Ha.)" /></b></td>
							<td><input type="text" id="sdcrop" name="sdcrop" 
							onfocusin="decimalToFourPlace(event)" class="" 
							placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="sdcropError"></span></td>
							<td><input type="text" id="csdcrop" name="csdcrop"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" onchange="calSum()" /><span
								class="csdcropError"></span></td>
							<td><textArea id="sdcropremark" name="sdcropremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="sdcropremarkError"></span></td>
						</tr>

						<tr>
							<td></td>
							<td><b><c:out value="Total Nil to Single / Single to double Area (Ha)" /></b></td>
							<td><input type="number" id="totalArea" name="totalArea"
								autocomplete="off" maxlength="15" readonly="readonly" /></td>
							<td><input type="number" id="ctotalArea" name="ctotalArea"
								autocomplete="off" maxlength="15" readonly="readonly" /></td>
						</tr>


                        <tr>
							<td><b><c:out value="3." /></b></td>
							<td><b> <c:out value="No. of Water Harvesting Structure (WHS) constructed /rejuvenated" /></b></td>
							<td><input type="text" id="WHSConReju" name="WHSConReju"
								 class=""
								placeholder="Only Numeric" onmousedown="numericOnly(event);" autocomplete="off" maxlength="10" /><span class="WHSConRejuError"></span></td>
							<td><input type="text" id="cWHSConReju"
								name="cWHSConReju" 
								class="" placeholder="Only Numeric" onmousedown="numericOnly(event);" autocomplete="off" maxlength="10" /><span
								class="cWHSConRejuError"></span></td>
								<td><textArea id="WHSConRejuremark" name="WHSConRejuremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="WHSConRejuremarkError"></span></td>
						</tr>
                        
                        <tr>
							<td><b><c:out value="4." /></b></td>
							<td><b> <c:out value="Area Covered with soil and Moisture (Ha.)" /></b></td>
							<td><input type="text" id="soilandmoiscrops" name="soilandmoiscrops"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="soilandmoiscropsError"></span></td>
							<td><input type="text" id="csoilandmoiscrops"
								name="csoilandmoiscrops" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="csoilandmoiscropsError"></span></td>
								<td><textArea id="soilandmoiscropsremark" name="soilandmoiscropsremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="soilandmoiscropsError"></span></td>
						</tr>
						
						<tr>
							<td><b><c:out value="5." /></b></td>
							<td><b> <c:out value="Area of degraded land covered /rainfed area developed (Ha.)" /></b></td>
							<td><input type="text" id="degradedrainfed" name="degradedrainfed"
								onfocusin="decimalToFourPlace(event)" class=""
								placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span class="degradedrainfedError"></span></td>
							<td><input type="text" id="cdegradedrainfed"
								name="cdegradedrainfed" onfocusin="decimalToFourPlace(event)"
								class="" placeholder="Only Decimal" autocomplete="off" maxlength="15" /><span
								class="cdegradedrainfedError"></span></td>
								<td><textArea id="degradedrainfedremark" name="degradedrainfedremark" autocomplete = "off" rows="2" cols="22" maxlength="200"></textArea>
							<span class="degradedrainfedError"></span></td>
						</tr>
                        
                        
					</c:if>
				<tr>
					<th colspan="5" style="align-content: center;">
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