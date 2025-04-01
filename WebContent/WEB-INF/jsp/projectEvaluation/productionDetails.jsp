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
	Financial Year : &nbsp; <b><c:out value='${fname}' /></b>, &nbsp;&nbsp;&nbsp; Month Name : &nbsp; <b><c:out value='${mname}' /></b>
</div>



		<hr />
		<div>
			<table>
				<tr>
					<th rowspan="2" class="text-center"><b>Sl.No.</b></th>
					<th rowspan="2" class="text-center"><b>Production Details</b></th>
					<th colspan="2" class="text-center"><b>Project Area Details</b></th>
					<th rowspan="2" class="text-center"><b>Controlled Area Details</b></th>
					<th rowspan="2" class="text-center"><b>Remarks</b></th>
				</tr>
				<tr>	
					<th class="text-center"><b>Pre-Project Status (Aggregate)</b></th>
					<th class="text-center"><b>Mid-Project Status (Aggregate)</b></th>
				</tr>	
					

					<c:if test="${wdcPrdDtlListSize > 0}">
						<c:forEach items="${wdcPrdDtlList}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Milk Production of Milch Cattle(Kl/Yr.)" /></b></td>
								<td><input type="text" id="preMilch" name="preMilch"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.preMilchCattle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="preMilchError"></span></td>
								<td><input type="text" id="midMilch" name="midMilch"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.midMilchCattle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="midMilchError"></span></td>
								<td><input type="text" id="cMilch" name="cMilch"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlMilchCattle}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cMilchError"></span></td>
							<td>
     							<textarea id="rmkMilch" name="rmkMilch" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkMilchCattle}</textarea> 
							</td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Fodder Production(Qt./Yr.)" /></b></td>
								<td><input type="text" id="preFodder" name="preFodder"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.preFodderProduction}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="preFodderError"></span></td>
								<td><input type="text" id="midFodder" name="midFodder"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.midFodderProduction}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="midFodderError"></span></td>
								<td><input type="text" id="cFodder" name="cFodder"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlFodderProduction}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cFodderError"></span></td>
								<td>
									<textarea id="rmkFodder" name="rmkFodder" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkFodderProduction}</textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Annual Migration from rural to urban area in project area(Nos.)" /></b></td>
								<td><input type="text" id="preRuralUrban" name="preRuralUrban"
									value=<c:out value="${list.preRuralUrban}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="preRuralUrbanError"></span></td>
								<td><input type="text" id="midRuralUrban" name="midRuralUrban"
									value=<c:out value="${list.midRuralUrban}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="midRuralUrbanError"></span></td>
								<td><input type="text" id="cRuralUrban" name="cRuralUrban"
									value=<c:out value="${list.controlRuralUrban}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cRuralUrbanError"></span></td>
								<td>
     								<textarea id="rmkRuralUrban" name="rmkRuralUrban" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkRuralUrban}</textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="No. of springs rejuvenated(if applicable)" /></b></td>
								<td colspan="2"><input type="text" id="spring" name="spring"
									value=<c:out value="${list.springRejuvenated}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="springError"></span></td>
								<td><input type="text" id="cSpring" name="cSpring"
									value=<c:out value="${list.controlSpringRejuvenated}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cSpringError"></span></td>
								<td>
     								<textarea id="rmkSpring" name="rmkSpring" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkSpringRejuvenated}</textarea> 
								</td>
							</tr>


							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b><c:out value="No. of persons benefitted due to rejuvenation of springs" /></b></td>
								<td colspan="2"><input type="text" id="benefit" name="benefit"
									value=<c:out value="${list.personBenefitte}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="benefitError"></span></td>
								<td><input type="text" id="cBenefit" name="cBenefit"
									value=<c:out value="${list.controlPersonBenefitte}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cBenefitError"></span></td>
								<td>
     								<textarea id="rmkBenefit" name="rmkBenefit" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkPersonBenefitte}</textarea> 
								</td>
							</tr>
							<tr>
								<td><b><c:out value="6." /></b></td>
								<td><b> <c:out value="No. of Community Based Organization" /></b></td>
								<td colspan="4"></td>
							</tr>
							
							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td colspan="2"><input type="text" id="shg" name="shg"
									value=<c:out value="${list.communityBasedShg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="shgError"></span></td>
								<td><input type="text" id="cShg" name="cShg"
									value=<c:out value="${list.controlCommunityBasedShg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="cShgError"></span></td>
								<td>
     								<textarea id="rmkShg" name="rmkShg" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkCommunityBasedShg}</textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td colspan="2"><input type="text" id="fpo" name="fpo"
									value=<c:out value="${list.communityBasedFpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="fpoError"></span></td>
								<td><input type="text" id="cFpo" name="cFpo"
									value=<c:out value="${list.controlCommunityBasedFpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="cFpoError"></span></td>
								<td>
     								<textarea id="rmkFpo" name="rmkFpo" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkCommunityBasedFpo}</textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td colspan="2"><input type="text" id="ug" name="ug"
									value=<c:out value="${list.communityBasedUg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="ugError"></span></td>
								<td><input type="text" id="cUg" name="cUg"
									value=<c:out value="${list.controlCommunityBasedUg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()"/><span
									class="cUgError"></span></td>
								<td>
     								<textarea id="rmkUg" name="rmkUg" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkCommunityBasedUg}</textarea> 
								</td>
							</tr>
							
							<tr>
								<td></td>
								<td><b><c:out
											value="Total No. of Community Based Organization" /></b></td>
								<td colspan="2"><input type="number" id="noOfCom" name="noOfCom"
									class=""
									value=<c:out value="${list.communityBasedShg + list.communityBasedFpo + list.communityBasedUg}"/>
									readonly="readonly" /></td>
								<td><input type="number" id="cnoOfCom" name="cnoOfCom"
									class=""
									value=<c:out value="${list.controlCommunityBasedShg + list.controlCommunityBasedFpo + list.controlCommunityBasedUg}"/>
									readonly="readonly" /></td>
								<td></td>
							</tr>
							
							<tr>
								<td><b><c:out value="7." /></b></td>
								<td><b> <c:out value="No. of Memebers in Community Based Organization" /></b></td>
								<td colspan="4"></td>
							</tr>
							

							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td colspan="2"><input type="text" id="mShg" name="mShg"
									value=<c:out value="${list.memberBasedShg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="mShgError"></span></td>
								<td><input type="text" id="cMshg" name="cMshg"
									value=<c:out value="${list.controlMemberBasedShg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="cMshgError"></span></td>
								<td>
     								<textarea id="rmkMshg" name="rmkMshg" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkMemberBasedShg}</textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td colspan="2"><input type="text" id="mFpo" name="mFpo"
									value=<c:out value="${list.memberBasedFpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="mFpoError"></span></td>
								<td><input type="text" id="cMfpo" name="cMfpo"
									value=<c:out value="${list.controlMemberBasedFpo}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="cMfpoError"></span></td>
								<td>
     								<textarea id="rmkMfpo" name="rmkMfpo" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkMemberBasedFpo}</textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td colspan="2"><input type="text" id="mUg" name="mUg"
									value=<c:out value="${list.memberBasedUg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="mUgError"></span></td>
								<td><input type="text" id="cMug" name="cMug"
									value=<c:out value="${list.controlMemberBasedUg}"/>
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()"/><span
									class="cMugError"></span></td>
								<td>
     								<textarea id="rmkMug" name="rmkMug" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkMemberBasedUg}</textarea> 
								</td>
							</tr>
							
							<tr>
								<td></td>
								<td><b><c:out
											value="Total No. of Members Community Based Organization" /></b></td>
								<td colspan="2"><input type="number" id="mnoOfCom" name="mnoOfCom"
									class=""
									value=<c:out value="${list.memberBasedShg + list.memberBasedFpo + list.memberBasedUg}"/>
									readonly="readonly" /></td>
								<td><input type="number" id="cmnoOfCom" name="cmnoOfCom"
									class=""
									value=<c:out value="${list.controlMemberBasedShg + list.controlMemberBasedFpo + list.controlMemberBasedUg}"/>
									readonly="readonly" /></td>
								<td></td>
							</tr>

							<tr>
								<td><b><c:out value="8." /></b></td>
								<td><b> <c:out value="Avergage Annual Turnover of FPOs(Rs.)" /></b></td>
								<td><input type="text"
									id="preTrunOverFpo" name="preTrunOverFpo"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.preTrunoverFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preTrunOverFpoError"></span></td>
								<td><input type="text"
									id="midTrunOverFpo" name="midTrunOverFpo"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.midTrunoverFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midTrunOverFpoError"></span></td>
								<td><input type="text"
									id="cTrunOverFpo" name="cTrunOverFpo"
									onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlTrunoverFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cTrunOverFpoError"></span></td>
								<td>
     								<textarea id="rmkTrunOverFpo" name="rmkTrunOverFpo" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkTrunoverFpo}</textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="9." /></b></td>
								<td><b> <c:out value="Average annual net income of an FPO Member(Rs.)" /></b></td>
								<td><input type="text" id="preIncomeFpo"
									name="preIncomeFpo" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.preIncomeFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preIncomeFpoError"></span></td>
								<td><input type="text" id="midIncomeFpo"
									name="midIncomeFpo" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.midIncomeFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midIncomeFpoError"></span></td>
								<td><input type="text" id="cIncomeFpo"
									name="cIncomeFpo" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlIncomeFpo}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cIncomeFpoError"></span></td>
								<td>
     								<textarea id="rmkIncomeFpo" name="rmkIncomeFpo" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkIncomeFpo}</textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="10." /></b></td>
								<td><b><c:out
											value="Average annual net income of an SHG Member(Rs.)" /></b>
								</td>
								<td><input type="text" id="preAnnualIncomeShg"
									name="preAnnualIncomeShg" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.preAnnualIncomeShg}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="preAnnualIncomeShgError"></span></td>
								<td><input type="text" id="midAnnualIncomeShg"
									name="midAnnualIncomeShg" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.midAnnualIncomeShg}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="midAnnualIncomeShgError"></span></td>
								<td><input type="text" id="cAnnualIncomeShg"
									name="cAnnualIncomeShg" onfocusin="decimalToFourPlace(event)"
									value=<c:out value="${list.controlAnnualIncomeShg}"/>
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cAnnualIncomeShgError"></span></td>
								<td>
     								<textarea id="rmkAnnualIncomeShg" name="rmkAnnualIncomeShg" autocomplete = "off" rows="2" cols="22" maxlength="200" >${list.remarkAnnualIncomeShg}</textarea> 
								</td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${wdcPrdDtlListSize eq 0}">
						<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Milk Production of Milch Cattle(Kl/Yr.)" /></b></td>
								<td><input type="text" id="preMilch" name="preMilch"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="preMilchError"></span></td>
								<td><input type="text" id="midMilch" name="midMilch"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="midMilchError"></span></td>
								<td><input type="text" id="cMilch" name="cMilch"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cMilchError"></span></td>
								<td>
     								<textarea id="rmkMilch" name="rmkMilch" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Fodder Production(Qt./Yr.)" /></b></td>
								<td><input type="text" id="preFodder" name="preFodder"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="preFodderError"></span></td>
								<td><input type="text" id="midFodder" name="midFodder"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="midFodderError"></span></td>
								<td><input type="text" id="cFodder" name="cFodder"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cFodderError"></span></td>
								<td>
     								<textarea id="rmkFodder" name="rmkFodder" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Annual Migration from rural to urban area in project area(Nos.)" /></b></td>
								<td><input type="text" id="preRuralUrban" name="preRuralUrban"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="preRuralUrbanError"></span></td>
								<td><input type="text" id="midRuralUrban" name="midRuralUrban"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="midRuralUrbanError"></span></td>
								<td><input type="text" id="cRuralUrban" name="cRuralUrban"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cRuralUrbanError"></span></td>
								<td>
     								<textarea id="rmkRuralUrban" name="rmkRuralUrban" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="No. of springs rejuvenated(if applicable)" /></b></td>
								<td colspan="2"><input type="text" id="spring" name="spring"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="springError"></span></td>
								<td><input type="text" id="cSpring" name="cSpring"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cSpringError"></span></td>
								<td>
     								<textarea id="rmkSpring" name="rmkSpring" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>


							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b><c:out value="No. of persons benefitted due to rejuvenation of springs" /></b></td>
								<td colspan="2"><input type="text" id="benefit" name="benefit"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="benefitError"></span></td>
								<td><input type="text" id="cBenefit" name="cBenefit"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5"/><span
									class="cBenefitError"></span></td>
								<td>
     								<textarea id="rmkBenefit" name="rmkBenefit" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>
							<tr>
								<td><b><c:out value="6." /></b></td>
								<td><b> <c:out value="No. of Community Based Organization" /></b></td>
								<td colspan="4"></td>
							</tr>
							
							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td colspan="2"><input type="text" id="shg" name="shg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="shgError"></span></td>
								<td><input type="text" id="cShg" name="cShg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="cShgError"></span></td>
								<td>
     								<textarea id="rmkShg" name="rmkShg" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td colspan="2"><input type="text" id="fpo" name="fpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="fpoError"></span></td>
								<td><input type="text" id="cFpo" name="cFpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="cFpoError"></span></td>
								<td>
     								<textarea id="rmkFpo" name="rmkFpo" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td colspan="2"><input type="text" id="ug" name="ug"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="ugError"></span></td>
								<td><input type="text" id="cUg" name="cUg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="pcalSum()" /><span
									class="cUgError"></span></td>
								<td>
     								<textarea id="rmkUg" name="rmkUg" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total No. of Community Based Organization" /></b></td>
							<td colspan="2"><input type="number" id="noOfCom" name="noOfCom"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cnoOfCom" name="cnoOfCom"
								class="" readonly="readonly" /></td>
							<td></td>
						</tr>

						<tr>
								<td><b><c:out value="7." /></b></td>
								<td><b> <c:out value="No. of Memebers in Community Based Organization" /></b></td>
								<td colspan="4"></td>
							</tr>
							

							<tr>
								<td style="text-align: right;"><b><c:out value="a." /></b></td>
								<td><b> <c:out value="SHG" /></b></td>
								
								<td colspan="2"><input type="text" id="mShg" name="mShg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="mShgError"></span></td>
								<td><input type="text" id="cMshg" name="cMshg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="cMshgError"></span></td>
								<td>
     								<textarea id="rmkMshg" name="rmkMshg" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td colspan="2"><input type="text" id="mFpo" name="mFpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="mFpoError"></span></td>
								<td><input type="text" id="cMfpo" name="cMfpo"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="cMfpoError"></span></td>
								<td>
     								<textarea id="rmkMfpo" name="rmkMfpo" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td colspan="2"><input type="text" id="mUg" name="mUg"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="mUgError"></span></td>
								<td><input type="text" id="cMug" name="cMug"
									placeholder="Only Numeric" autocomplete="off" onfocusin="numericOnly(event);" maxlength="5" onchange="mpcalSum()" /><span
									class="cMugError"></span></td>
								<td>
     								<textarea id="rmkMug" name="rmkMug" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

						<tr>
							<td></td>
							<td><b><c:out
										value="Total No. of Members Community Based Organization" /></b></td>
							<td colspan="2"><input type="number" id="mnoOfCom" name="mnoOfCom"
								class="" readonly="readonly" /></td>
							<td><input type="number" id="cmnoOfCom" name="cmnoOfCom"
								class="" readonly="readonly" /></td>
							<td></td>
						</tr>

						<tr>
								<td><b><c:out value="8." /></b></td>
								<td><b> <c:out value="Avergage Annual Turnover of FPOs(Rs.)" /></b></td>
								<td><input type="text"
									id="preTrunOverFpo" name="preTrunOverFpo"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preTrunOverFpoError"></span></td>
								<td><input type="text"
									id="midTrunOverFpo" name="midTrunOverFpo"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midTrunOverFpoError"></span></td>
								<td><input type="text"
									id="cTrunOverFpo" name="cTrunOverFpo"
									onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cTrunOverFpoError"></span></td>
								<td>
     								<textarea id="rmkTrunOverFpo" name="rmkTrunOverFpo" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="9." /></b></td>
								<td><b> <c:out value="Average annual net income of an FPO Member(Rs.)" /></b></td>
								<td><input type="text" id="preIncomeFpo"
									name="preIncomeFpo" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="preIncomeFpoError"></span></td>
								<td><input type="text" id="midIncomeFpo"
									name="midIncomeFpo" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="midIncomeFpoError"></span></td>
								<td><input type="text" id="cIncomeFpo"
									name="cIncomeFpo" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span class="cIncomeFpoError"></span></td>
								<td>
     								<textarea id="rmkIncomeFpo" name="rmkIncomeFpo" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>

							<tr>
								<td><b><c:out value="10." /></b></td>
								<td><b><c:out
											value="Average annual net income of an SHG Member(Rs.)" /></b>
								</td>
								<td><input type="text" id="preAnnualIncomeShg"
									name="preAnnualIncomeShg" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="preAnnualIncomeShgError"></span></td>
								<td><input type="text" id="midAnnualIncomeShg"
									name="midAnnualIncomeShg" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="midAnnualIncomeShgError"></span></td>
								<td><input type="text" id="cAnnualIncomeShg"
									name="cAnnualIncomeShg" onfocusin="decimalToFourPlace(event)"
									placeholder="Only Decimal" autocomplete="off" maxlength="15"/><span
									class="cAnnualIncomeShgError"></span></td>
								<td>
     								<textarea id="rmkAnnualIncomeShg" name="rmkAnnualIncomeShg" autocomplete = "off" rows="2" cols="22" maxlength="200" ></textarea> 
								</td>
							</tr>
					</c:if>
				<tr>
					<th colspan="6" style="align-content: center;">
						&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
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