<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
function openModal() {
    document.getElementById("myModal").style.display = "block";
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}
</script>
</head>
<body>
<div class="maindiv">
	
<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	
	<c:choose>
    <c:when test="${status == 'C'.charAt(0)}">
        <a href="getProfileStart" style="position: absolute; left: 0;">
            <img src="<c:url value='/resources/images/home_PE.png' />" alt="Back" style="height: 40px; width: 40px;">
        </a>
    </c:when>
    <c:when test="${status == 'D'.charAt(0)}">
        <a href="getProjectProfile?district=<c:out value='${dcode}' />&project=<c:out value='${pcode}' />&distName=<c:out value='${dname}' />&projName=<c:out value='${pname}' />&finyear=<c:out value='${fcode}' />&finName=<c:out value='${fname}' />&month=<c:out value='${mcode}' />&monthName=<c:out value='${mname}' />" 
           style="position: absolute; left: 0;">
            <img src="<c:url value='/resources/images/backbutton_PE.png' />" alt="Back" style="height: 40px; width: 40px;">
        </a>
    </c:when>
</c:choose>	
<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Mid Term Project Evaluation - View & Complete</span>
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
		
	<div class="form-group">
			State : &nbsp; <b><c:out value='${stName}' /></b>, &nbsp;&nbsp;&nbsp; District : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp; Block : &nbsp; <c:choose>  <c:when test="${fn:length(blockList) == 2}"><b>${blockList.values().toArray()[0]}</b>, <b>${blockList.values().toArray()[1]}</b></c:when> <c:otherwise><b>${blockList.values().toArray()[0]}</b>, <b>${blockList.values().toArray()[1]}</b>     <a href="#" onclick="openModal()"><b>....more</b></a></c:otherwise></c:choose>
			 ,&nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${fname}' /></b>, &nbsp;&nbsp;&nbsp; Month : &nbsp; <b><c:out value='${mname}' />,</b>
			<br>
			 Name of Project Evaluation Agency: &nbsp; <b><c:out value='${pagency}' /></b>
			</div>

	
	<div class="col-10"  id="exportHtmlToPdf">
	<p align="right"> </p>
<!-- 	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${projProfId}','${dname}','${mname}','${fname}','${pname}','${dcode}','${fcode}','${pcode}','${mcode}')" class="btn btn-info">PDF</button>
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
   <b><c:out	value="Total Sanctioned Project Area (in ha.)"></c:out></b>
    </td><td>
     ${listUser.sanctioned_area}
     </td>
	<td width="40%">
      <b><c:out	value="Total Sanctioned Cost of the Project (Rs. Crore)"></c:out></b>
     </td><td>
     ${listUser.sanctioned_cost}
     </td>
     
     
	</tr>
	
	<tr>
	<td width="40%">
    <b><c:out	value="Central Share (Rs. Crore)"></c:out></b>
    </td><td>
    ${listUser.central}
 </td>
	<td width="40%">
   <b><c:out	value="State Share (Rs. Crore)"></c:out></b>
  </td><td>
     ${listUser.state}
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
   <b><c:out value="Number of Households Covered in the Project area"></c:out></b>
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
                        <td colspan="3"><b><c:out value="Administrative Mechanism"/></b></td>
<%--                         <td>${am}</td> --%>
<%--                         <td>${amd}</td> --%>
                    </tr>
                    <tr>
                        <td class="text-right"><b><c:out value="a"/></b></td>
                        <td><b><c:out value="Whether DPR approved by SLNA"/></b></td>
                        <td>
                             <c:if test="${dpr == 'N'.charAt(0)}">
                             No
                            
                           </c:if>
                            <c:if test="${dpr == 'Y'.charAt(0)}">
                             Yes
                           </c:if>
                        </td>
                        <td>${dprremark}</td>
                    </tr>
                    <tr>
                        <td class="text-right"><b><c:out value="b"/></b></td>
                        <td><b><c:out value="Whether all manpower positions at the sanctioned level as per the guidelines"/></b></td>
                        <td>
                         <c:if test="${mp == 'Y'.charAt(0)}">
                             Yes
                            </c:if>
                            <c:if test="${mp == 'N'.charAt(0)}">
                             No
                            </c:if>
                            
                        </td>
                        <td>${mpremark}</td>
                    </tr>
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
			<th><b>Details</b></th>
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
     			${centralShare}
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
     			${stateShare}
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
				${totalFund}
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
     			${conPlannedFund}
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
     			${exCon}
			</td>
			<td>
     			${rmkExCon}	
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<b><c:out value="f"/></b>
			</td>
			<td>
				<b><c:out value="Total WDF (Watershed Development Fund) collected so far (Rs. Crores)"/></b>
			</td>
			<td>
     			${wdf}
			</td>
			<td>
     			${rmkWdf}
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
	
	<th rowspan ="2"><b>Sl.No.</b></th>
					<th rowspan ="2"><b>Crop Details</b></th>
					<th class ="" colspan ="2" style ="padding-left: 180px;"><b>Project Area Details</b></th>
					<th rowspan ="2"><b> Controlled Area Details</b></th>
					<th rowspan ="2"><b> Remarks</b></th>
					
					<tr>
					<th>Pre Project Status(Aggregate)</th>
					<th>Mid Project Status(Aggregate)</th>
				    </tr>
					
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
								<td>${list.pregrossKharifCropArea}</td>
								<td>${list.midgrossKharifCropArea}</td>
								<td>${list.control_gross_kharif_crop_area}</td>
								<td>${list.kharifCropremark}</td>
					</tr>	
					<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Area under rabi crop(ha)" /></b></td>
								<td>${list.pregrossRabiCropArea}</td>
								<td>${list.midgrossRabiCropArea}</td>
								<td>${list.control_gross_rabi_crop_area}</td>
								<td>${list.rabiCropremark}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Area under Third crop(ha)" /></b></td>
								<td>${list.pregrossThirdCropArea}</td>
								<td>${list.midgrossThirdCropArea}</td>
								<td>${list.control_gross_third_crop_area}</td>
								<td>${list.thirdCropremark}</td>
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
								<td>${list.predifferentCropCereals}</td>
								<td>${list.middifferentCropCereals}</td>
								<td>${list.control_different_crop_cereals}</td>
								<td>${list.cerealsremark}</td>
					</tr>	
					<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Pulses" /></b></td>
								<td>${list.predifferentCropPulses}</td>
								<td>${list.middifferentCropPulses}</td>
								<td>${list.control_different_crop_pulses}</td>
								<td>${list.pulsesremark}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Oil seed" /></b></td>
								<td>${list.predifferentCropOilSeed}</td>
								<td>${list.middifferentCropOilSeed}</td>
								<td>${list.control_different_crop_oil_seed}</td>
								<td>${list.oilSeedremark}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="d" /></b></td>
								<td><b> <c:out value="Millets" /></b></td>
								<td>${list.predifferentCropMillets}</td>
								<td>${list.middifferentCropMillets}</td>
								<td>${list.control_different_crop_millets}</td>
								<td>${list.milletsremark}</td>
					</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="e" /></b></td>
								<td><b> <c:out value="Others" />&nbsp (${list.othercrop})</b></td>
								<td>${list.predifferentCropOther}</td>
								<td>${list.middifferentCropOther}</td>
								<td>${list.control_different_crop_other}</td>
								<td>${list.othersremark}</td>
					</tr>	
					
					<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Area of horticulture crop(ha)" /></b>
								</td>
								<td>${list.prehorticultureArea}</td>
								<td>${list.midhorticultureArea}</td>
								<td>${list.control_horticulture_area}</td>
								<td>${list.horticultureremark}</td>
							</tr>

							<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="Net Sown Area(ha)" /></b></td>
								<td>${list.prenetsownArea}</td>
								<td>${list.midnetsownArea}</td>
								<td>${list.control_netsown_area}</td>
								<td>${list.netSownremark}</td>
							</tr>

							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b> <c:out value="Cropping Intensity (%)" /></b></td>
								<td>${list.precroppingIntensity}</td>
								<td>${list.midcroppingIntensity}</td>
								<td>${list.control_cropping_intensity}</td>
								<td>${list.cropIntensityremark}</td>
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
					<th><b> Remarks</b></th>
					
			<c:forEach items="${wdcCrpDtlList2}" var="list">
							<tr>
								<td><b><c:out value="1." /></b></td>
								<td><b> <c:out value="Area covered under diversified crops/ change in cropping system (Ha.)" /></b>
								</td>
								<td>${list.projectDiversifiedChange}</td>
								<td>${list.controlDiversifiedChange}</td>
								<td>${list.remarkDiversifiedChange}</td>
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
								<td>${list.projectNillSingle}</td>
								<td>${list.controlNillSingle}</td>
								<td>${list.remarkNillSingle}</td>
							</tr>

							<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Single to double or more crop(ha.)" /></b></td>
								<td>${list.projectSingleDoublemore}</td>
								<td>${list.controlSingleDoublemore}</td>
								<td>${list.remarkSingleDoublemore}</td>
							
							
                           </tr>
                              <tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="No. of Water Harvesting Structure (WHS) constructed /rejuvenated" /></b>
								</td>
								<td>${list.projectWhsConstructedRejuvenated}</td>
								<td>${list.controlWhsConstructedRejuvenated}</td>
								<td>${list.remarkWhsConstructedRejuvenated}</td>
							</tr>
							
                              <tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="Area Covered with soil and Moisture (Ha.)" /></b>
								</td>
								<td>${list.projectSoilMoisture}</td>
								<td>${list.controlSoilMoisture}</td>
								<td>${list.remarkSoilMoisture}</td>
							</tr>
							
							<tr>
								<td><b><c:out value="5." /></b></td>
								<td><b> <c:out value="Area of degraded land covered /rainfed area developed (Ha.)" /></b>
								</td>
								<td>${list.projectDegradedRainfed}</td>
								<td>${list.controlDegradedRainfed}</td>
								<td>${list.remarkDegradedRainfed}</td>
							</tr>
							
						</c:forEach>		
	</table>
	
	<table cellspacing="0" class="table"   width="auto">
	<thead>
	<tr>
	<th colspan="24" ><center><h4><u>Cropped Details-3</u></h4></center></th>
	</tr>
	</thead>
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
	 			
				<c:forEach items="${wdcCrpDtlList3}" var="list">
				
				           <tr>
								<td><b><c:out value="1." /></b></td>
								<td><b> <c:out value="Area under plantation cover(Ha.)" /></b></td>
								<td>${list.prePlantationCover}</td>
								<td>${list.midPlantationCover}</td>
								<td>${list.controlPlantationCover}</td>
								<td>${list.remarkPlantationCover}</td>
							</tr>
							<tr>
								<td><b><c:out value="2." /></b></td>
								<td><b><c:out value="Yield per hectare of major crops(Qt./Ha.)" /></b></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
					<tr>
								<td style="text-align: right;"><b><c:out value="a" /></b></td>
								<td><b> <c:out value="Rice" /></b></td>
								<td>${list.preRice}</td>
								<td>${list.midRice}</td>
								<td>${list.controlRice}</td>
								<td>${list.remarkRice}</td>
					</tr>	
					
					<tr>
								<td style="text-align: right;"><b><c:out value="b" /></b></td>
								<td><b> <c:out value="Wheat" /></b></td>
								<td>${list.preWheat}</td>
								<td>${list.midWheat}</td>
								<td>${list.controlWheat}</td>
								<td>${list.remarkWheat}</td>
					</tr>
					
					<tr>
								<td style="text-align: right;"><b><c:out value="c" /></b></td>
								<td><b> <c:out value="Pulses" /></b></td>
								<td>${list.prePulses}</td>
								<td>${list.midPulses}</td>
								<td>${list.controlPulses}</td>
								<td>${list.remarkPulses}</td>
					</tr>
					
					<tr>
								<td style="text-align: right;"><b><c:out value="d" /></b></td>
								<td><b> <c:out value="Millets" /></b></td>
								<td>${list.preMillets}</td>
								<td>${list.midMillets}</td>
								<td>${list.controlMillets}</td>
								<td>${list.remarkMillets}</td>
					</tr>
					
					<tr>
								<td style="text-align: right;"><b><c:out value="e" /></b></td>
								<td><b> <c:out value="Oil seeds" /></b></td>
								<td>${list.preOilSeed}</td>
								<td>${list.midOilSeed}</td>
								<td>${list.controlOilSeed}</td>
								<td>${list.remarkOilSeed}</td>
					</tr>
					
					<tr>
								<td style="text-align: right;"><b><c:out value="f" /></b></td>
								<td><b> <c:out value="Other Crops" />&nbsp (${list.othercrop})</b></td>
								<td>${list.preOther}</td>
								<td>${list.midOther}</td>
								<td>${list.controlOther}</td>
								<td>${list.remarkOther}</td>
					</tr>
					<tr>
								<td><b><c:out value="3." /></b></td>
								<td><b> <c:out value="Area of culturable wasteland(Ha.)" /></b></td>
								<td>${list.preCulturableWasteland}</td>
								<td>${list.midCulturableWasteland}</td>
								<td>${list.controlCulturableWasteland}</td>
								<td>${list.remarkCulturableWasteland}</td>
							</tr>
							
					<tr>
								<td><b><c:out value="4." /></b></td>
								<td><b> <c:out value="Area under protective irrigation(Ha.)" /></b></td>
								<td>${list.preProtectiveIrrigation}</td>
								<td>${list.midProtectiveIrrigation}</td>
								<td>${list.controlProtectiveIrrigation}</td>
								<td>${list.remarkProtectiveIrrigation}</td>
							</tr>		
					</c:forEach>
	</table>
	
	<table cellspacing="0" class="table" width="auto" border="1">
  <thead>
    <tr>
      <th colspan="6" style="text-align: center;">
        <h4><u>No. of Man-days, Farmer and Water Table Details</u></h4>
      </th>
    </tr>
    <tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Indicators</th>
      <th colspan="2" style="text-align:center; vertical-align: middle;">Project Area</th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Controlled Area</th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Remarks</th>
    </tr>
    <tr>
      <th style="text-align:center; vertical-align: middle;">Pre Project Status (Aggregate)</th>
      <th style="text-align:center; vertical-align: middle;">Mid Project Status (Aggregate)</th>
    </tr>
  </thead>

  <tbody>
    <tr>
      <td><b><c:out value="1." /></b></td>
      <td><b><c:out value="Farmer`s Average Household Income per Annum (Rs. in Lakhs)" /></b></td>
      <td>${pre_farmer_income}</td>
      <td>${mid_farmer_income}</td>
      <td>${control_farmer_income}</td>
      <td>${remark_farmer_income}</td>
    </tr>

    <tr>
      <td><b><c:out value="2." /></b></td>
      <td><b><c:out value="Average depth of Water table in dug wells (mts.)- Summer Season(February - March)" /></b></td>
      <td>${pre_dug_well}</td>
      <td>${mid_dug_well}</td>
      <td>${control_dug_well}</td>
      <td>${remark_dug_well}</td>
    </tr>

    <tr>
      <td><b><c:out value="3." /></b></td>
      <td><b><c:out value="Average depth of Water table in tube wells (mts.)- Summer Season(February - March)" /></b></td>
      <td>${pre_tube_well}</td>
      <td>${mid_tube_well}</td>
      <td>${control_tube_well}</td>
      <td>${remark_tube_well}</td>
    </tr>
    
     <tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Indicators</th>
      <th colspan="2" style="text-align:center; vertical-align: middle;">Project Area</th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Controlled Area</th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Remarks</th>
    </tr>
    <tr>
<!--       <th style="text-align:center; vertical-align: middle;">Pre Project Status (Aggregate)</th> -->
<!--       <th style="text-align:center; vertical-align: middle;">Mid Project Status (Aggregate)</th> -->
    </tr>
    
     <tr>
      <td><b><c:out value="4." /></b></td>
      <td><b><c:out value="No. of Farmers Benefited" /></b></td>
      <td colspan="2">${farmer_benefited}</td>
      <td>N/A</td>
      <td>${remark_farmer_benefited}</td>
    </tr>

    <tr>
      <td><b><c:out value="5." /></b></td>
      <td><b><c:out value="No. of Persondays Generated (man-days)" /></b></td>
      <td colspan="2">${mandays_generated}</td>
      <td>N/A</td>
      <td>${remark_mandays_generated}</td>
    </tr>
  </tbody>
</table>
	
	
	 <table cellspacing="0" class="table" width="auto" border="1">
  <thead>
    <tr>
      <th colspan="6" style="text-align: center;">
        <h4><u>Production, Spring and Community Details</u></h4>
      </th>
    </tr>
    <tr>
      <th rowspan="2" class="text-center"><b>Sl. No.</b></th>
      <th rowspan="2" class="text-center"><b>Production Details</b></th>
      <th colspan="2" class="text-center"><b>Project Area Details</b></th>
      <th rowspan="2" class="text-center"><b>Controlled Area Details</b></th>
      <th rowspan="2" class="text-center"><b>Remarks</b></th>
    </tr>
    <tr>
      <th class="text-center"><b>Pre-Project Status (Aggregate)</b></th>
      <th class="text-center"><b>Mid-Project Status (Aggregate)</b></th>
    </tr>
  </thead>

  <tbody>
    <c:forEach items="${wdcPrdDtlList}" var="list">
      <tr>
        <td><b>1.</b></td>
        <td><b>Milk Production of Milch Cattle (Kl/Yr.)</b></td>
        <td>${list.preMilchCattle}</td>
        <td>${list.midMilchCattle}</td>
        <td>${list.controlMilchCattle}</td>
        <td>${list.remarkMilchCattle}</td>
      </tr>

      <tr>
        <td><b>2.</b></td>
        <td><b>Fodder Production (Qt./Yr.)</b></td>
        <td>${list.preFodderProduction}</td>
        <td>${list.midFodderProduction}</td>
        <td>${list.controlFodderProduction}</td>
        <td>${list.remarkFodderProduction}</td>
      </tr>

      <tr>
        <td><b>3.</b></td>
        <td><b>Annual Migration from rural to urban area in project area (Nos.)</b></td>
        <td>${list.preRuralUrban}</td>
        <td>${list.midRuralUrban}</td>
        <td>${list.controlRuralUrban}</td>
        <td>${list.remarkRuralUrban}</td>
      </tr>
 <tr>
        <td><b>4.</b></td>
        <td><b>Average Annual Turnover of FPOs (Rs.)</b></td>
        <td>${list.preTrunoverFpo}</td>
        <td>${list.midTrunoverFpo}</td>
        <td>${list.controlTrunoverFpo}</td>
        <td>${list.remarkTrunoverFpo}</td>
      </tr>

      <tr>
        <td><b>5.</b></td>
        <td><b>Average Annual Net Income of an FPO Member (Rs.)</b></td>
        <td>${list.preIncomeFpo}</td>
        <td>${list.midIncomeFpo}</td>
        <td>${list.controlIncomeFpo}</td>
        <td>${list.remarkIncomeFpo}</td>
      </tr>

      <tr>
        <td><b>6.</b></td>
        <td><b>Average Annual Net Income of an SHG Member (Rs.)</b></td>
        <td>${list.preAnnualIncomeShg}</td>
        <td>${list.midAnnualIncomeShg}</td>
        <td>${list.controlAnnualIncomeShg}</td>
        <td>${list.remarkAnnualIncomeShg}</td>
      </tr>
      

      
        <tr>
							<th rowspan="2" class="text-center"><b>Sl.No.</b></th>
							<th rowspan="2" class="text-center"><b>Spring Details</b></th>
							<th colspan="2" class="text-center"><b>Project Area	Details</b></th>
							<th rowspan="2" class="text-center"><b>Controlled Area Details</b></th>
							<th rowspan="2" class="text-center"><b>Remarks</b></th>
						</tr>
						<tr>
							<th class="text-center"><b>Pre-Project Status(Aggregate)</b></th>
							<th class="text-center"><b>Mid-Project Status(Aggregate)</b></th>
						</tr>
    
    <tr>
        <td><b>7.</b></td>
        <td><b>No. of springs rejuvenated (if applicable)</b></td>
        <td>${list.preSpringRejuvenated}</td>
        <td>${list.midSpringRejuvenated}</td>
        <td>N/A</td>
        <td>${list.remarkSpringRejuvenated}</td>
      </tr>

      <tr>
        <td><b>8.</b></td>
        <td><b>No. of persons benefitted due to rejuvenation of springs</b></td>
        <td>${list.prePersonBenefitte}</td>
        <td>${list.midPersonBenefitte}</td>
        <td>N/A</td>
        <td>${list.remarkPersonBenefitte}</td>
      </tr>

<tr>
							<th rowspan="2" class="text-center"><b>Sl.No.</b></th>
							<th rowspan="2" class="text-center"><b>Community Details</b></th>
							<th colspan="2" class="text-center"><b>Project Area Details</b></th>
							<th rowspan="2" class="text-center"><b>Controlled Area Details</b></th>
							<th rowspan="2" class="text-center"><b>Remarks</b></th>
						</tr>
						<tr>
							<th class="text-center"><b>Pre-Project Status(Aggregate)</b></th>
							<th class="text-center"><b>Mid-Project Status(Aggregate)</b></th>
						</tr>
						
      <!-- Community Based Organization -->
      <tr>
        <td><b>9.</b></td>
        <td colspan="5"><b>No. of Community Based Organizations</b></td>
        
      </tr>

      <tr>
        <td style="text-align: right;"><b>a.</b></td>
        <td><b>SHG</b></td>
        <td>${list.preCommunityBasedShg}</td>
        <td>${list.midCommunityBasedShg}</td>
        <td>${list.controlCommunityBasedShg}</td>
        <td>${list.remarkCommunityBasedShg}</td>
      </tr>

      <tr>
        <td style="text-align: right;"><b>b.</b></td>
        <td><b>FPO</b></td>
        <td>${list.preCommunityBasedFpo}</td>
        <td>${list.midCommunityBasedFpo}</td>
        <td>${list.controlCommunityBasedFpo}</td>
        <td>${list.remarkCommunityBasedFpo}</td>
      </tr>

      <tr>
        <td style="text-align: right;"><b>c.</b></td>
        <td><b>UG</b></td>
        <td>${list.preCommunityBasedUg}</td>
        <td>${list.midCommunityBasedUg}</td>
        <td>${list.controlCommunityBasedUg}</td>
        <td>${list.remarkCommunityBasedUg}</td>
      </tr>
  <tr>
        <td style="text-align: right;"><b></b></td>
        <td><b>	Total No. of Community Based Organization</b></td>
        <td>${list.preCommunityBasedShg + list.preCommunityBasedFpo + list.preCommunityBasedUg}</td>
        <td>${list.midCommunityBasedShg + list.midCommunityBasedFpo + list.midCommunityBasedUg}</td>
        <td>${list.controlCommunityBasedShg + list.controlCommunityBasedFpo + list.controlCommunityBasedUg}</td>
        <td></td>
      </tr>
      <!-- Members in Community Based Organization -->
      <tr>
        <td><b>10.</b></td>
        <td colspan="5"><b>No. of Members in Community Based Organizations</b></td>
        
      </tr>

      <tr>
        <td style="text-align: right;"><b>a.</b></td>
        <td><b>SHG</b></td>
        <td>${list.preMemberBasedShg}</td>
        <td>${list.midMemberBasedShg}</td>
        <td>${list.controlMemberBasedShg}</td>
        <td>${list.remarkMemberBasedShg}</td>
      </tr>

      <tr>
        <td style="text-align: right;"><b>b.</b></td>
        <td><b>FPO</b></td>
        <td>${list.preMemberBasedFpo}</td>
        <td>${list.midMemberBasedFpo}</td>
        <td>${list.controlMemberBasedFpo}</td>
        <td>${list.remarkMemberBasedFpo}</td>
      </tr>

      <tr>
        <td style="text-align: right;"><b>c.</b></td>
        <td><b>UG</b></td>
        <td>${list.preMemberBasedUg}</td>
        <td>${list.midMemberBasedUg}</td>
        <td>${list.controlMemberBasedUg}</td>
        <td>${list.remarkMemberBasedUg}</td>
      </tr>
 <tr>
        <td style="text-align: right;"><b></b></td>
        <td><b>	Total No. of Members Community Based Organization</b></td>
        <td>${list.preMemberBasedShg + list.preMemberBasedFpo + list.preMemberBasedUg}</td>
        <td>${list.midMemberBasedShg + list.midMemberBasedFpo + list.midMemberBasedUg}</td>
        <td>${list.controlMemberBasedShg + list.controlMemberBasedFpo + list.controlMemberBasedUg}</td>
        <td></td>
      </tr>
     
    </c:forEach>
  </tbody>
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
                        <td><b><c:out value="Is there a system of auditing of status of natural resources at intervals"/></b></td>
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
			<th style="width:35%"><b>Equity Aspect Description</b></th>
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
				N/A
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



<%-- <tr>
<td  colspan="4"   align="center" >
<input type="button" name="view"  id = "view" value="Final Submit" class="btn btn-info"/>
<input type="hidden" name="projProfId" id = "projProfId" value="${projProfId}" />
</td>
</tr> --%>
</table>

<c:choose>
    <c:when test="${status == 'C'.charAt(0)}">
    <table cellspacing="0" class="table"   width="auto">
    <c:forEach var="list" items="${projectList}" varStatus="status">
           <c:forEach var="listUser" items="${list.value}" >
    
    <tr>
        <th colspan="4"><center><h4><u>Summary of Evaluation:</u></h4></center></th>
        </tr>
    <tr>
        <td colspan="4">
            ${listUser.summary}
        </td>
    </tr>
    
    <tr>
        <th colspan="4"><center><h4><u>Based on your Summary Project Grades:</u></h4></center></th>
        </tr>
    <tr>
        <td colspan="4">
    <c:choose>
        <c:when test="${listUser.grade == 'E'.charAt(0)}">
            Excellent
        </c:when>
        <c:when test="${listUser.grade == 'G'.charAt(0)}">
            Very Good
        </c:when>
        <c:when test="${listUser.grade == 'S'.charAt(0)}">
            Satisfactory
        </c:when>
        <c:when test="${listUser.grade == 'A'.charAt(0)}">
            Average
        </c:when>
        <c:otherwise>
            Not Assigned
        </c:otherwise>
    </c:choose>
</td>

        
    </tr>
    
  
    
    </c:forEach>
    </c:forEach>
    </table>
 </c:when>
 
 <c:when test="${status == 'D'.charAt(0)}">
    <table cellspacing="0" class="table"   width="auto">
	<thead>
<tr>
        <th colspan="4">Summary of Evaluation (a brief note in 500 words):</th>
        </tr>
        <tr>
        <td colspan="4">
            <textarea name="evaluationSummary" rows="5" cols="170" maxlength="500" placeholder="Enter your evaluation summary here..."></textarea>
        </td>
    </tr>
    <tr>
        <th colspan="4">Based on your Summary Project Grades:</th>
        </tr>
        <tr>
        <td>
           <label>
                <input type="radio" name="grade" value="E">
                <span>&#128516; Excellent</span>
            </label>
            <label>
                <input type="radio" name="grade" value="G">
                <span>&#128515; Very Good</span>
            </label>
            <label>
                <input type="radio" name="grade" value="S">
                <span>&#128578; Satisfactory</span>
            </label>
            <label>
                <input type="radio" name="grade" value="A">
                <span>&#128577; Average</span>
            </label>
        </td>
    </tr>
    <tr>
        <td  colspan="4"   align="center" >
<input type="button" name="view"  id = "view" value="Final Submit" class="btn btn-info"/>
<input type="hidden" name="projProfId" id = "projProfId" value="${projProfId}" />
</td>
    </tr>

	</table>
 </c:when>
 </c:choose>   



	
</form:form>
	
	<br/>  
        </div>
    <br>
	

    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
    <script src='<c:url value="/resources/js/projectevaluation/projevacomplete.js" />'></script>
</body>