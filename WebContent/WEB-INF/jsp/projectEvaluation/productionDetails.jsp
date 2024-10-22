<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>
<script type="text/javascript"></script>
</head>

<div class="maindiv">
	
<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${dname}"/>&projName=<c:out value="${pname}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${mname}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${fname}"/>" style="position: absolute; left: 0;">
    	<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
	</a>
	<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Project Evaluation - Production Details</span>
	</h4>
</div>

	<hr />
	<form name="productionDetails" id="productionDetails">
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
				<tr>
					<th><b>Sl.No.</b></th>
					<th><b>Production Details</b></th>
					<th><b>Project Area Details</b></th>
					<th><b> Controlled Area Details</b></th>
<!-- 					<th><b> Remarks</b></th> -->

					<c:if test="${wdcPrdDtlListSize > 0}">
						<c:forEach items="${wdcPrdDtlList}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Milk Production of Milch Cattle(Kl/Yr.)" /></b></td>
								<td><input type="text" id="milch" name="milch"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.milchCattle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="milchError"></span></td>
								<td><input type="text" id="cmilch" name="cmilch"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_milch_cattle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cmilchError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Fodder Production(Qt./Yr.)" /></b></td>
								<td><input type="text" id="fodder" name="fodder"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.fodderProduction}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="fodderError"></span></td>
								<td><input type="text" id="cfodder" name="cfodder"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_fodder_production}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cfodderError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Annual Migration from rural to urban area in project area(Nos.)" /></b></td>
								<td><input type="text" id="ruralUrban" name="ruralUrban"
									value=<c:out value="${list.ruralUrban}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="ruralUrbanError"></span></td>
								<td><input type="text" id="cruralUrban" name="cruralUrban"
									value=<c:out value="${list.control_rural_urban}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cruralUrbanError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="No. of springs rejuvenated(if applicable)" /></b></td>
								<td><input type="text" id="spring" name="spring"
									value=<c:out value="${list.springRejuvenated}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="springError"></span></td>
								<td><input type="text" id="cspring" name="cspring"
									value=<c:out value="${list.control_spring_rejuvenated}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cspringError"></span></td>
							</tr>


							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b><c:out value="No. of persons benefitted due to rejuvenation of springs" /></b></td>
								<td><input type="text" id="benefit" name="benefit"
									value=<c:out value="${list.personBenefitte}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="benefitError"></span></td>
								<td><input type="text" id="cbenefit" name="cbenefit"
									value=<c:out value="${list.control_person_benefitte}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cbenefitError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="6." /></b></td>
								<td><b> <c:out value="No. of Community Based Organization" /></b></td>
								<td></td>
								<td></td>
							</tr>
							
							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td><input type="text" id="shg" name="shg"
									value=<c:out value="${list.communityBasedShg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="shgError"></span></td>
								<td><input type="text" id="cshg" name="cshg"
									value=<c:out value="${list.control_community_based_shg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="cshgError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td><input type="text" id="fpo" name="fpo"
									value=<c:out value="${list.communityBasedFpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="fpoError"></span></td>
								<td><input type="text" id="cfpo" name="cfpo"
									value=<c:out value="${list.control_community_based_fpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="cfpoError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td><input type="text" id="ug" name="ug"
									value=<c:out value="${list.communityBasedUg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="ugError"></span></td>
								<td><input type="text" id="cug" name="cug"
									value=<c:out value="${list.control_community_based_ug}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="cugError"></span></td>
							</tr>
							
							<tr>
								<td></td>
								<td><b><c:out
											value="Total No. of Community Based Organization" /></b></td>
								<td><input type="number" id="noOfCom" name="noOfCom"
									class=""
									value=<c:out value="${list.communityBasedShg + list.communityBasedFpo + list.communityBasedUg}"/>
									readonly="readonly" /></td>
								<td><input type="number" id="cnoOfCom" name="cnoOfCom"
									class=""
									value=<c:out value="${list.control_community_based_shg + list.control_community_based_fpo + list.control_community_based_ug}"/>
									readonly="readonly" /></td>
							</tr>
							
							<tr>
								<td><b><c:out value="7." /></b></td>
								<td><b> <c:out value="No. of Memebers in Community Based Organization" /></b></td>
								<td></td>
								<td></td>
							</tr>
							

							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td><input type="text" id="mshg" name="mshg"
									value=<c:out value="${list.memberBasedShg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="mshgError"></span></td>
								<td><input type="text" id="cmshg" name="cmshg"
									value=<c:out value="${list.control_member_based_shg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="cmshgError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td><input type="text" id="mfpo" name="mfpo"
									value=<c:out value="${list.memberBasedFpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="mfpoError"></span></td>
								<td><input type="text" id="cmfpo" name="cmfpo"
									value=<c:out value="${list.control_member_based_fpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="cmfpoError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td><input type="text" id="mug" name="mug"
									value=<c:out value="${list.memberBasedUg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="mugError"></span></td>
								<td><input type="text" id="cmug" name="cmug"
									value=<c:out value="${list.control_member_based_ug}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="cmugError"></span></td>
							</tr>
							
							<tr>
								<td></td>
								<td><b><c:out
											value="Total No. of Members Community Based Organization" /></b></td>
								<td><input type="number" id="mnoOfCom" name="mnoOfCom"
									class=""
									value=<c:out value="${list.memberBasedShg + list.memberBasedFpo + list.memberBasedUg}"/>
									readonly="readonly" /></td>
								<td><input type="number" id="cmnoOfCom" name="cmnoOfCom"
									class=""
									value=<c:out value="${list.control_member_based_shg + list.control_member_based_fpo + list.control_member_based_ug}"/>
									readonly="readonly" /></td>
							</tr>

							<tr>
								<td><b><c:out value="8." /></b></td>
								<td><b> <c:out value="Avergage Annual Turnover of FPOs(Rs.)" /></b></td>
								<td><input type="text"
									id="trunoverFpo" name="trunoverFpo"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.trunoverFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="trunoverFpoError"></span></td>
								<td><input type="text"
									id="ctrunoverFpo" name="ctrunoverFpo"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_trunover_fpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="ctrunoverFpoError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="9." /></b></td>
								<td><b> <c:out value="Average annual net income of an FPO Member(Rs.)" /></b></td>
								<td><input type="text" id="incomeFpo"
									name="incomeFpo" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.incomeFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="incomeFpoError"></span></td>
								<td><input type="text" id="cincomeFpo"
									name="cincomeFpo" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.control_income_fpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cincomeFpoError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="10." /></b></td>
								<td><b><c:out
											value="Average annual net income of an SHG Member(Rs.)" /></b>
								</td>
								<td><input type="text" id="annualIncomeShg"
									name="annualIncomeShg" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.annualIncomeShg}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="annualIncomeShgError"></span></td>
								<td><input type="text" id="cannualIncomeShg"
									name="cannualIncomeShg" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.annualIncomeShg}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cannualIncomeShgError"></span></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${wdcPrdDtlListSize eq 0}">
						<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Milk Production of Milch Cattle(Kl/Yr.)" /></b></td>
								<td><input type="text" id="milch" name="milch"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="milchError"></span></td>
								<td><input type="text" id="cmilch" name="cmilch"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cmilchError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Fodder Production(Qt./Yr.)" /></b></td>
								<td><input type="text" id="fodder" name="fodder"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="fodderError"></span></td>
								<td><input type="text" id="cfodder" name="cfodder"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cfodderError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Annual Migration from rural to urban area in project area(Nos.)" /></b></td>
								<td><input type="text" id="ruralUrban" name="ruralUrban"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="ruralUrbanError"></span></td>
								<td><input type="text" id="cruralUrban" name="cruralUrban"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cruralUrbanError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="No. of springs rejuvenated(if applicable)" /></b></td>
								<td><input type="text" id="spring" name="spring"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="springError"></span></td>
								<td><input type="text" id="cspring" name="cspring"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cspringError"></span></td>
							</tr>


							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b><c:out value="No. of persons benefitted due to rejuvenation of springs" /></b></td>
								<td><input type="text" id="benefit" name="benefit"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="benefitError"></span></td>
								<td><input type="text" id="cbenefit" name="cbenefit"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cbenefitError"></span></td>
							</tr>
							<tr>
								<td><b><c:out value="6." /></b></td>
								<td><b> <c:out value="No. of Community Based Organization" /></b></td>
								<td></td>
								<td></td>
							</tr>
							
							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td><input type="text" id="shg" name="shg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="shgError"></span></td>
								<td><input type="text" id="cshg" name="cshg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="cshgError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td><input type="text" id="fpo" name="fpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="fpoError"></span></td>
								<td><input type="text" id="cfpo" name="cfpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="cfpoError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td><input type="text" id="ug" name="ug"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="ugError"></span></td>
								<td><input type="text" id="cug" name="cug"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="cugError"></span></td>
							</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total No. of Community Based Organization" /></b></td>
							<td><input type="number" id="noOfCom" name="noOfCom"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cnoOfCom" name="cnoOfCom"
								class="" readonly="readonly" /></td>
						</tr>

						<tr>
								<td><b><c:out value="7." /></b></td>
								<td><b> <c:out value="No. of Memebers in Community Based Organization" /></b></td>
								<td></td>
								<td></td>
							</tr>
							

							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td><input type="text" id="mshg" name="mshg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="mshgError"></span></td>
								<td><input type="text" id="cmshg" name="cmshg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="cmshgError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td><input type="text" id="mfpo" name="mfpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="mfpoError"></span></td>
								<td><input type="text" id="cmfpo" name="cmfpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="cmfpoError"></span></td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td><input type="text" id="mug" name="mug"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="mugError"></span></td>
								<td><input type="text" id="cmug" name="cmug"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="cmugError"></span></td>
							</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total No. of Members Community Based Organization" /></b></td>
							<td><input type="number" id="mnoOfCom" name="mnoOfCom"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cmnoOfCom" name="cmnoOfCom"
								class="" readonly="readonly" /></td>
						</tr>

						<tr>
								<td><b><c:out value="8." /></b></td>
								<td><b> <c:out value="Avergage Annual Turnover of FPOs(Rs.)" /></b></td>
								<td><input type="text"
									id="trunoverFpo" name="trunoverFpo"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="trunoverFpoError"></span></td>
								<td><input type="text"
									id="ctrunoverFpo" name="ctrunoverFpo"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="ctrunoverFpoError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="9." /></b></td>
								<td><b> <c:out value="Average annual net income of an FPO Member(Rs.)" /></b></td>
								<td><input type="text" id="incomeFpo"
									name="incomeFpo" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="incomeFpoError"></span></td>
								<td><input type="text" id="cincomeFpo"
									name="cincomeFpo" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cincomeFpoError"></span></td>
							</tr>

							<tr>
								<td><b><c:out value="10." /></b></td>
								<td><b><c:out
											value="Average annual net income of an SHG Member(Rs.)" /></b>
								</td>
								<td><input type="text" id="annualIncomeShg"
									name="annualIncomeShg" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="annualIncomeShgError"></span></td>
								<td><input type="text" id="cannualIncomeShg"
									name="cannualIncomeShg" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cannualIncomeShgError"></span></td>
							</tr>
					</c:if>
				<tr>
					<th colspan="4" style="align-content: center;">
						&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<input type="button" name="viewProd" id="viewProd" value="Confirm"
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