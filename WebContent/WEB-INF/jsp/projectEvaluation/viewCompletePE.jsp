<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script type="text/javascript">
$(document).ready(function() {
    // Function to toggle visibility based on the selected radio button
    function toggleWdcRow() {
        var isYesSelected = $('#mpyes').is(':checked');
        if (isYesSelected) {
            $('#wdcRow').show();
            $('#piRow').show();
            $('#wcRow').show();// Show the row
        } else {
            $('#wdcRow').hide();
            $('#piRow').hide();
            $('#wcRow').hide();// Hide the row
        }
    }

    // Initial call to set the correct state on page load
    toggleWdcRow();

    // Set up event listeners to handle changes in radio button selection
    $('input[name="mp"]').change(function() {
        toggleWdcRow();
    });
});

function downloadPDF(projProfId,dname,mname,fname,pname,dcode,fcode,pcode,mcode)
{
		document.getElementById("projProfId").value=projProfId;
		document.getElementById("dname").value=dname;
		document.getElementById("mname").value=mname;
		document.getElementById("fname").value=fname;
		document.getElementById("pname").value=pname;
		document.getElementById("dcode").value=dcode;
		document.getElementById("fcode").value=fcode;
		document.getElementById("pcode").value=pcode;
		document.getElementById("mcode").value=mcode;
		
		document.projectProfiledata.action="downloadViewCompleteFormPDF";
		document.projectProfiledata.method="post";
		document.projectProfiledata.submit();
	
} 

</script>
</head>
<body>
<div class="maindiv">
	
<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${dname}"/>&projName=<c:out value="${pname}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${mname}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${fname}"/>" style="position: absolute; left: 0;">
    	<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
	</a>
	<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Project Evaluation - View & Complete</span>
	</h4>
</div>

<hr />
<form:form name="projectProfiledata" id="projectProfiledata" modelAttribute="projectProfiledata" action="saveprojectProfile" method="post">
		<input type="hidden" id="projProfId" name="projProfId" value= <c:out value='${projProfId}' /> />
		
		<input type="hidden" id="dname" name="dname" value= "<c:out value='${dname}' />" />
		<input type="hidden" id="pname" name="pname" value= "<c:out value='${pname}' />" />
		<input type="hidden" id="pcode" name="pcode" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${month}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${finyear}' /> />
<%-- 		<input type="hidden" name="projProfId" id="projProfId" <c:out value='${projProfId }' /> /> --%>

 	<table cellspacing="0" class="table"   width="auto">
  	
<%--   	<b>District Name : &nbsp; <c:out value='${dname}' />, &nbsp;&nbsp;&nbsp; Project Name: &nbsp; <c:out value='${pname}' />, &nbsp;&nbsp;&nbsp;Month : &nbsp; <c:out value='${mname}' />, &nbsp;&nbsp;&nbsp;  --%>
<%-- 		Financial Year : &nbsp; <c:out value='${fname}' />  </b> --%>
		
	District Name : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp; 
	Month Name : &nbsp; <b><c:out value='${mname}' /></b>, &nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${fname}' /></b>
	
<!-- 	<div class ="card"> -->
<!-- 	<div class="row"> -->
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
	<p align="right"> </p>
<!-- 	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
<%-- 	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${projProfId}','${dname}','${mname}','${fname}','${pname}','${dcode}','${fcode}','${pcode}','${mcode}')" class="btn btn-info">PDF</button> --%>
<p align="right"> </p>
	
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Project Profile</u></h4></center></th>
	</tr>
	</thead>
	
	<c:forEach var="list" items="${projectList}" varStatus="status">
           <c:forEach var="listUser" items="${list.value}" >
	<tr>
	<td width="40%">
      <b><c:out	value="Total Sanctioned Cost of the Project (Rs. Crore)"></c:out></b>
     </td><td>
     ${listUser.sanctioned_cost}
     </td>
     
     <td width="40%">
   <b><c:out	value="Central Share (Rs. Crore)"></c:out></b>
  </td><td>
    ${listUser.central}
 </td>
	</tr>
	
	<tr>
	<td width="40%">
   <b><c:out	value="State Share (Rs. Crore)"></c:out></b>
  </td><td>
     ${listUser.state}
 </td>
 <td width="40%">
   <b><c:out	value="Total Sanctioned Project Area (in ha.)"></c:out></b>
  </td><td>
     ${listUser.sanctioned_area}
 </td>
	</tr>
	
	<tr>
	<td width="40%">
   <b><c:out	value="Number of Village Covered"></c:out></b>
  </td><td>
     ${listUser.no_vc}
 </td>
 <td width="40%">
   <b><c:out	value="Number of Watershed Committees"></c:out></b>
  </td><td>
     ${listUser.no_wc}
 </td>
	</tr>
	
	<tr>
	<td width="40%">
   <b><c:out	value="Number of Members in Watershed Committees"></c:out></b>
  </td><td>
  ${listUser.member_wc}
	</td>
	<td width="40%">
   <b><c:out	value="Number of Households Covered in the Project area"></c:out></b>
  </td><td>
  ${listUser.household}
  </td>
	
	</tr>
	</c:forEach>
	
	</c:forEach>
	</table>
	
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Indicators for Evaluation</u></h4></center></th>
	</tr>
	</thead>
	              
                        <th><b>Sl. No.</b></th>
                        <th><b>Indicators</b></th>
                        <th><b>Details</b></th>
                        <th><b>Remarks</b></th>
                    
                    <tr>
                        <td><b><c:out value="1."/></b></td>
                        <td><b><c:out value="Administrative Mechanism"/></b></td>
                        <td>${am}</td>
                        <td>${amd}</td>
                    </tr>
                    <tr>
                        <td class="text-right"><b><c:out value="a"/></b></td>
                        <td><b><c:out value="Whether DPR approved by SLNA"/></b></td>
                        <td>
                            <c:if test="${dpr eq false }">
                             No
                            
                           </c:if>
                            <c:if test="${dpr eq true }">
                             Yes
                           </c:if>
                        </td>
                        <td>${dprremark}</td>
                    </tr>
                    <tr>
                        <td class="text-right"><b><c:out value="b"/></b></td>
                        <td><b><c:out value="Whether all manpower positions in place at"/></b></td>
                        <td>
                         <c:if test="${mp eq true }">
                             Yes
                            </c:if>
                            <c:if test="${mp eq false }">
                             No
                            </c:if>
                            
                        </td>
                        <td>${mpremark}</td>
                    </tr>
                    <c:if test="${mp eq true }">
                    <tr>
                        <td class="text-center"><b><c:out value="I"/></b></td>
                        <td><b><c:out value="WCDC Level"/></b></td>
                        <td>${wdc}</td>
                        <td>${wdcd}</td>
                    </tr>
                    <tr>
                        <td class="text-center"><b><c:out value="II"/></b></td>
                        <td><b><c:out value="PIA Level"/></b></td>
                        <td>${pi}</td>
                        <td>${pid}</td>
                    </tr>
                    <tr>
                        <td class="text-center"><b><c:out value="III"/></b></td>
                        <td><b><c:out value="Watershed Committee Level"/></b></td>
                        <td>${wc}</td>
                        <td>${wcd}</td>
                    </tr>
               </c:if>
	</table>
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Fund Utilization</u></h4></center></th>
	</tr>
	</thead>
	        <thead>
	        <th><b>Sl.No.</b></th>
			<th><b>Indicators</b></th>
			<th><b>Pre-Project Status</b></th>
			<th><b>Mid-Project Status Details</b></th>
			<th><b>Remarks</b></th>
	</thead>
	<tbody>	
		<tr>
			<td>
				<b><c:out value="2."/></b>
			</td>
			<td colspan="4">
				<b><c:out value="Fund Utilization"/></b>
			</td>
			</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="a"/></b>
			</td>
			<td>
				<b><c:out value="Amount of sanctioned Central share received (Rs. Crores)"/></b>
			</td>
			<td>
     			${preCentralShare}
			</td>
			<td>
     			${midCentralShare}
			</td>
			<td>
     			${rmkCentralShare}
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="b"/></b>
			</td>
			<td>
				<b><c:out value="Amount of sanctioned State share received (Rs. Crores)"/></b>
			</td>
			<td>
     			${preStateShare}
			</td>
			<td>
     			${midStateShare}
			</td>
			<td>
     			${rmkStateShare}
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="c"/></b>
			</td>
			<td>
				<b><c:out value="Amount of Total funds (central + state share) Utilized (Rs. Crores)"/></b>
			</td>
			<td>
				${preTotalFund}
			</td>
			<td>
				${midTotalFund}
			</td>
			<td>
     			${rmkTotalFund}
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="d"/></b>
			</td>
			<td>
				<b><c:out value="Total funds planned through convergence in the project area (Rs. Crores)"/></b>
			</td>
			<td>
     			${preConPlannedFund}
			</td>
			<td>
     			${midConPlannedFund}
			</td>
			<td>
     			${rmkConPlannedFund}
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="e"/></b>
			</td>
			<td>
				<b><c:out value="Total expenditure incurred through convergence (Rs. Crores)"/></b>
			</td>
			<td>
     			${preExCon}
			</td>
			<td>
     			${midExCon}
			</td>
			<td>
     			${rmkExCon}	
			</td>
		</tr>
		</tbody>
			
	</table>
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Cropped Details-1</u></h4></center></th>
	</tr>
	</thead>
	
	<th><b>Sl.No.</b></th>
					<th><b>Crop Details</b></th>
					<th><b>Project Area Details</b></th>
					<th><b> Controlled Area Details</b></th>
					
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
								<td>${list.grossKharifCropArea}</td>
								<td>${list.control_gross_kharif_crop_area}</td>
					</tr>	
					<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Area under rabi crop(ha)" /></b></td>
								<td>${list.grossRabiCropArea}</td>
								<td>${list.control_gross_rabi_crop_area}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Area under Third crop(ha)" /></b></td>
								<td>${list.grossThirdCropArea}</td>
								<td>${list.control_gross_third_crop_area}</td>
					</tr>
					<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b><c:out value="Area under different Crops(ha)" /></b>
								</td>
								<td></td>
								<td></td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Cereals" /></b></td>
								<td>${list.differentCropCereals}</td>
								<td>${list.control_different_crop_cereals}</td>
					</tr>	
					<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Pulses" /></b></td>
								<td>${list.differentCropPulses}</td>
								<td>${list.control_different_crop_pulses}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Oil seed" /></b></td>
								<td>${list.differentCropOilSeed}</td>
								<td>${list.control_different_crop_oil_seed}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="d" /></b></td>
								<td><b> <c:out value="Millets" /></b></td>
								<td>${list.differentCropMillets}</td>
								<td>${list.control_different_crop_millets}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="e" /></b></td>
								<td><b> <c:out value="Others" /></b></td>
								<td>${list.differentCropOther}</td>
								<td>${list.control_different_crop_other}</td>
					</tr>	
					
					<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Area of horticulture crop(ha)" /></b>
								</td>
								<td>${list.horticultureArea}</td>
								<td>${list.control_horticulture_area}</td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="Net Sown Area(ha)" /></b></td>
								<td>${list.netsownArea}</td>
								<td>${list.control_netsown_area}</td>
							</tr>

							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b> <c:out value="Cropping Intensity" /></b></td>
								<td>${list.croppingIntensity}</td>
								<td>${list.control_cropping_intensity}</td>
							</tr>

							<tr>
								<td><b><c:out value="6." /></b></td>
								<td><b><c:out
											value="Area covered under diversified crops/change in cropping systems(ha)" /></b>
								</td>
								<td>${list.diversifiedChange}</td>
								<td>${list.control_diversified_change}</td>
							</tr>
											
		</c:forEach>								
	</table>
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Cropped Details-2</u></h4></center></th>
	</tr>
	</thead>
	
	<th><b>Sl.No.</b></th>
					<th><b>Crop Details</b></th>
					<th><b>Project Area Details</b></th>
					<th><b> Controlled Area Details</b></th>
					
			<c:forEach items="${wdcCrpDtlList2}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Area brought from Nil/Single crop to double or more crop(ha.)" /></b></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Nil to single crop(ha.)" /></b></td>
								<td>${list.nillSingle}</td>
								<td>${list.control_nill_single}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Single to double or more crop(ha.)" /></b></td>
								<td>${list.singelDoublemore}</td>
								<td>${list.control_singel_doublemore}</td>
							
							
                           </tr>
                              <tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Area under plantation cover" /></b>
								</td>
								<td>${list.plantationCover}</td>
								<td>${list.control_plantation_cover}</td>
							</tr>
							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b><c:out value="Yeild per hectare of major crops(Qtl./ha.)" /></b>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Rice" /></b></td>
								<td>${list.rice}</td>
								<td>${list.control_rice}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Wheat" /></b></td>
								<td>${list.wheat}</td>
								<td>${list.control_wheat}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Pulses" /></b></td>
								<td>${list.pulses}</td>
								<td>${list.control_pulses}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="d" /></b></td>
								<td><b> <c:out value="Millets" /></b></td>
								<td>${list.millets}</td>
								<td>${list.control_millets}</td>
							</tr>

<tr>
								<td style="text-align: right;"><b><c:out value="e" /></b></td>
								<td><b> <c:out value="Oil Seed" /></b></td>
								<td>${list.oil_seed}</td>
								<td>${list.control_oil_seed}</td>
							</tr>
							<tr>
								<td style="text-align: right;"><b><c:out value="f" /></b></td>
								<td><b> <c:out value="Others" /></b>
								</td>
								<td>${list.other}</td>
								<td>${list.control_other}</td>
							</tr>

							
						</c:forEach>		
	</table>
	
			<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>No. of Man-days Details</u></h4></center></th>
	</tr>
	</thead>
	<thead>
		<tr>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
	      <th rowspan="5" style="text-align:left; vertical-align: middle;">Indicators</th>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">Project Area</th>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">Controlled Area</th>
      	</tr>
      </thead>
      <tr>
        	<td width="4%"> <b><c:out value="1."></c:out></b></td>
     		<td width="40%"><b><c:out value="Area of Culturable Wasteland (ha)"></c:out></b></td>
     		<td>
     			${culturablew}
			</td>
			<td>
     			${conculturablew}
			</td>
		</tr> 
		<tr>
 			<td width="4%"><b><c:out value="2."></c:out></b></td>
  			<td width="40%"><b><c:out value="Number of Water Harvesting Structures(WHS) constructed/rejuvenated"></c:out></b></td>
  			<td>
     			${whs}
 			</td>
 			<td>
     			${conwhs}
 			</td>
		</tr> 
		<tr>
 			<td width="4%"><b><c:out value="3."></c:out></b></td>
  			<td width="40%"><b><c:out value="Area Covered with Soil and Moisture Conservation Activities (ha)"></c:out></b></td>
  			<td>
     			${soil}
 			</td>
 			<td>
     			${consoil}
 			</td>
		</tr> 
		<tr>
 			<td width="4%"><b><c:out value="4."></c:out></b></td>
  			<td width="40%"><b><c:out value="Area under Protective Irrigation (ha)"></c:out></b></td>
  			<td>
     			${protective}
 			</td>
 			<td>
     			${conprotective}
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="5."></c:out></b></td>
  			<td width="40%"><b><c:out value="Area of degraded land covered/ rainfed area development (ha)"></c:out></b></td>
  			<td>
     			${degraded}
 			</td>
 			<td>
     			${condegraded}
 			</td>
		</tr>
 		<tr>
 			<td width="4%"><b><c:out value="6."></c:out></b></td>
  			<td width="40%"><b><c:out value="Farmer`s Average Household Income per Annum (Rs. in Lakhs)"></c:out></b></td>
  			<td>
     			${income}
 			</td>
 			<td>
     			${conincome}
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="7."></c:out></b></td>
  			<td width="40%"><b><c:out value="No. of Farmers Benefited"></c:out></b></td>
  			<td>
     			${benefited}
 			</td>
 			<td>
     			${conbenefited}
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="8."></c:out></b></td>
  			<td width="40%"><b><c:out value="No. of Persondays Generated (man-days)"></c:out></b></td>
  			<td>
     			${mandays}
 			</td>
 			<td>
     			${conmandays}
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="9."></c:out></b></td>
  			<td width="40%"><b><c:out value="Average depth of Water table in dug wells (mts.)- Summer Season"></c:out></b></td>
  			<td>
     			${dug}
 			</td>
 			<td>
     			${condug}
 			</td>
		</tr>
		<tr>
 			<td width="4%"><b><c:out value="10."></c:out></b></td>
  			<td width="40%"><b><c:out value="Average depth of Water table in tube wells (mts.)- Summer Season"></c:out></b></td>
  			<td>
     			${tube}
 			</td>
 			<td>
     			${contube}
 			</td>
		</tr>
	</table>	
	
		<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Production Details</u></h4></center></th>
	</tr>
	</thead>
	
	<thead>
		<tr>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
	      <th rowspan="5" style="text-align:left; vertical-align: middle;">Production Details</th>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">Project Area Details</th>
	      <th rowspan="2" style="text-align:left; vertical-align: middle;">Controlled Area Details</th>
      	</tr>
      </thead>
				<c:forEach items="${wdcPrdDtlList}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b><c:out value="Milk Production of Milch Cattle(Kl/Yr.)" /></b></td>
								<td>${list.milchCattle}</td>
								<td>${list.control_milch_cattle}</td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b> <c:out value="Fodder Production(Qt./Yr.)" /></b></td>
								<td>${list.fodderProduction}</td>
								<td>${list.control_fodder_production}</td>
							</tr>

							<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Annual Migration from rural to urban area in project area(Nos.)" /></b></td>
								<td>${list.ruralUrban}</td>
								<td>${list.control_rural_urban}</td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="No. of springs rejuvenated(if applicable)" /></b></td>
								<td>${list.springRejuvenated}</td>
								<td>${list.control_spring_rejuvenated}</td>
							</tr>


							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b><c:out value="No. of persons benefitted due to rejuvenation of springs" /></b></td>
								<td>${list.personBenefitte}</td>
								<td>${list.control_person_benefitte}</td>
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
								
								<td>${list.communityBasedShg}</td>
								<td>${list.control_community_based_shg}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td>${list.communityBasedFpo}</td>
								<td>${list.control_community_based_fpo}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td>${list.communityBasedUg}</td>
								<td>${list.control_community_based_ug}</td>
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
								
								<td>${list.memberBasedShg}</td>
								<td>${list.control_member_based_shg}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b." /></b></td>
								<td><b> <c:out value="FPO" /></b></td>
								<td>${list.memberBasedFpo}</td>
								<td>${list.control_member_based_fpo}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="c." /></b></td>
								<td><b> <c:out value="UG" /></b></td>
								<td>${list.memberBasedUg}</td>
								<td>${list.control_member_based_ug}</td>
							</tr>
							
						<tr>
								<td><b><c:out value="8." /></b></td>
								<td><b> <c:out value="Avergage Annual Turnover of FPOs" /></b></td>
								<td>${list.trunoverFpo}</td>
								<td>${list.control_trunover_fpo}</td>
							</tr>

							<tr>
								<td><b><c:out value="9." /></b></td>
								<td><b> <c:out value="Average annual net income of an FPO Member" /></b></td>
								<td>${list.incomeFpo}</td>
								<td>${list.control_income_fpo}</td>
							</tr>

							<tr>
								<td><b><c:out value="10." /></b></td>
								<td><b><c:out
											value="Average annual net income of an SHG Member" /></b>
								</td>
								<td>${list.annualIncomeShg}</td>
								<td>${list.annualIncomeShg}</td>
							</tr>
						</c:forEach>
	</table>
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Ecological Perspective</u></h4></center></th>
	</tr>
	</thead> 
	
	<tr>
                        <th ><b>Sl.No.</b></th>
                        <th style="width:35%"><b>Perspective Description</b></th>
                        <th ><b>Project Area</b></th>
                        <th ><b>Controlled Area</b></th>
                        <th><b>Remarks</b></th>
                    </tr>
                    <tr>
                    
                        <td><b><c:out value="1"/></b></td>
                        <td><b><c:out value="Is there a system of auditing of status of natural resources intervals"/></b></td>
                        <td>
                             <c:if test="${ntrlresource eq true }">
                             Yes
                            </c:if>
                            <c:if test="${ntrlresource eq false }">
                             No
                            </c:if>
                            </td>
                            <td>
                             <c:if test="${controlntlresource eq true }">
                             Yes
                            </c:if>
                             <c:if test="${controlntlresource eq false }">
                            No
                            </c:if>
                            </td>
                            <td>${ntrlresourceremark}</td>
                    </tr>
                    <tr>
                        <td><b><c:out value="2"/></b></td>
                        <td><b><c:out value="Whether Gram Panchayats (GPs) and UGs enforcing the norms relating to sharing of usufructs rights"/></b></td>
                        <td>
                        
                       <c:if test="${norm eq true }">
                             Yes
                      </c:if>
                       <c:if test="${norm eq false }">
                             No
                       </c:if>
                        </td>
                        <td>
                           <c:if test="${controlnorm eq true }">
                         Yes
                             </c:if>
                            <c:if test="${controlnorm eq false }">
                        No
                            </c:if>
                            </td>
                        
                        <td>${normremark}</td>
                    </tr>
                    <tr>
                        <td><b><c:out value="3"/></b></td>
                        <td><b><c:out value="Whether all members of GPs and UGs trained to maintain and monitor all the natural resources and assets created"/></b></td>
                        <td>
                        
                        <c:if test="${ntlasset eq true }">
                             Yes
                       </c:if>
                       
                        <c:if test="${ntlasset eq false }">
                             No
                       </c:if>
                        </td>
                        <td>
                        
                            <c:if test="${controlantrlasset eq true }">
                         Yes
                            </c:if>
                            <c:if test="${controlantrlasset eq false }">
                         No
                            </c:if>
                            
                            </td>
                        <td>${ntlassetremark}</td>
                    </tr>
                   
	</table>
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Equity Aspect</u></h4></center></th>
	</tr>
	</thead>
	
	<thead>
		<tr>
			<th><b>Sl.No.</b></th>
			<th style="width:35%"><b>Aspect Description</b></th>
			<th><b>Project Area</b></th>
			<th><b>Controlled Area</b></th>
			<th><b>Remarks</b></th>
		</tr>
	</thead>	
	<tbody>	
		<tr>
			<td>
				<b><c:out value="1"/></b>
			</td>
			<td>
				<b><c:out value="Whether landless poor and women find a place in watershed units like watershed committee"/></b>
			</td>
			<td>
				<c:if test="${pWatershedCom eq true }">
					 Yes
				</c:if>
				<c:if test="${pWatershedCom eq false }">
				 No
				</c:if>
			</td>
			<td>
				<c:if test="${cWatershedCom eq true }">
					 Yes
				</c:if>
				<c:if test="${cWatershedCom eq false }">
					 No
				</c:if>
			</td>
			<td>
				${rmkWatershedCom}
			</td>
		</tr>
		<tr>
			<td>
				<b><c:out value="2"/></b>
			</td>
			<td>
				<b><c:out value="Whether landless poor and women are active member of FPO, SHG, Village level Institutions (VLIs) and various UGs"/></b>
			</td>
			<td>
				
				<c:if test="${pFpoShgVli eq true }">
					 Yes
				</c:if>
				<c:if test="${pFpoShgVli eq false }">
					 No
				</c:if>
			</td>
			<td>
					<c:if test="${cFpoShgVli eq true }">
					 Yes
				</c:if>
				<c:if test="${cFpoShgVli eq false }">
					 No
				</c:if>
			</td>
			<td>
				${rmkFpoShgVli}
			</td>
		</tr>
		<tr>
			<td>
				<b><c:out value="3"/></b>
			</td>
			<td>
				<b><c:out value="Whether landless and asset-less poor benefited from activities that promote alternate livelihood options"/></b>
			</td>
			<td>
				<c:if test="${pLivelihood eq true }">
					 Yes
				</c:if>
				<c:if test="${pLivelihood eq false }">
					 No
				</c:if>
			</td>
			<td>
				<c:if test="${cLivelihood eq true }">
					 Yes
				</c:if>
				<c:if test="${cLivelihood eq false }">
					 No
				</c:if>
			</td>
			<td>
				${rmkLivelihood}
			</td>
		</tr>
	</table>
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Execution of Planned Works against Targets</u></h4></center></th>
	</tr>
	</thead>
	
	<thead>
		<tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="5" style="text-align:left; vertical-align: middle;"></th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Details</th>
      <th rowspan="4" style="text-align:left; vertical-align: middle;">Remarks</th>
      </tr>
      </thead>
 <tr>
      <td width="4%">
       <b><c:out	value="1."></c:out></b>
     </td>
     <td width="40%">
      <b><c:out	value="Total No. of Work IDs Created"></c:out></b>
     </td>
     <td>
     ${crw}
	</td>
	<td>
     	${crwre}
	</td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="2."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out value="Total No. of Works Completed"></c:out></b>
  </td><td>
     ${comw}
 </td>
 <td>
     	${comwre}
	</td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="3."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out value="Total No. of Works Ongoing"></c:out></b>
  </td><td>
     ${ongw}
 </td>
 <td>
     	${ongwre} 
	</td>
</tr> 
	</table>
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Quality of Project Shape Files</u></h4></center></th>
	</tr>
	</thead>
	<thead>
		<tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="5" style="text-align:left; vertical-align: middle;"></th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Details</th>
      <th rowspan="4" style="text-align:left; vertical-align: middle;">Remarks</th>
      </tr>
      </thead>
 <tr>
      <td width="4%">
       <b><c:out	value="1."></c:out></b>
     </td>
     <td width="40%">
      <b><c:out	value="Area of Shape File (ha)"></c:out></b>
     </td>
     <td>
     ${sfile_area}
	</td>
	<td>
     	${sfile_areare}
	</td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="2."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out value="Variation of area under shape file as compared to sanctioned project area (ha)"></c:out></b>
  </td><td>
    ${variationarea}
 </td>
 <td>
     	${variationareare}
	</td>
</tr> 
	</table>
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Status of Geo-tagging of Works</u></h4></center></th>
	</tr>
	</thead>
	<thead>
		<tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="5" style="text-align:left; vertical-align: middle;"></th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Details</th>
      <th rowspan="4" style="text-align:left; vertical-align: middle;">Remarks</th>
      </tr>
      </thead>
 <tr>
      <td width="4%">
       <b><c:out	value="1."></c:out></b>
     </td>
     <td width="40%">
      <b><c:out	value="Total No. of Works Geo-Tagged"></c:out></b>
     </td>
     <td>
     ${twork}
	</td>
	<td>
     	${tworkre}
	</td>
</tr> 



<tr>
<td  colspan="4"   align="center" >
<input type="button" name="view"  id = "view" value="Final Submit" class="btn btn-info"/>
<input type="hidden" name="projProfId" id = "projProfId" value="${projProfId}" />
</td>
</tr>

	</table>
	<br/>  
        </div>
    <br>
	
</form:form>
    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
    <script src='<c:url value="/resources/js/projectevaluation/projevacomplete.js" />'></script>
</body>