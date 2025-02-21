
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${sessionScope.loginid eq null }">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
	</c:when>
	<c:otherwise>
		<%@include file="/WEB-INF/jspf/header2.jspf"%>
	</c:otherwise>
</c:choose>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/inauguration.js" />'></script>
<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var block = $('#blockk').val();
	var userdate = $('#userdate').val();
	$('#userdate').val(userdate);
	
	var userdateto = $('#userdateto').val();
	
	const dateTimeStr = $('#userdate').val();
	const dateTimeStrto = $('#userdateto').val();
	const dateObj = new Date(dateTimeStr); 
	const dateObjto = new Date(dateTimeStrto); 

	if(state==='')
	{
		alert('Please select State');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District');
		$('#district').focus();
		e.preventDefault();
	}
	if(block==='')
	{
		alert('Please select Block');
		$('#blockk').focus();
		e.preventDefault();
	}
	/* if(userdate==='')
	{
		alert('Please select From Date');
		$('#userdate').focus();
		e.preventDefault();
	}
	if(userdateto==='')
	{
		alert('Please select To Date ');
		$('#userdateto').focus();
		e.preventDefault();
	}
	
	if (dateObjto < dateObj) {
		alert('From date Can not be greater than To date');
		$('#userdate').val('');
		$('#userdateto').val('');
	}  */
	else{
		document.inauguration.action="getInaugurationReportData";
		document.inauguration.method="post";
		document.inauguration.submit();
	}
	return false;
}

function showChangedata(){
	
    document.inauguration.action="getInaugurationReport";
	document.inauguration.method="get";
	document.inauguration.submit();
}


function downloadPDF(state, district, blkd, udate, udateto){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var blkName = document.getElementById("blockk").options[document.getElementById("blockk").selectedIndex].text;
 
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("blkName").value=blkName;
    document.getElementById("udate").value=udate;
    document.getElementById("userdate2").value=udateto;
	
    document.inauguration.action="downloadPDFInaugurationReport";
	document.inauguration.method="post";
	document.inauguration.submit();
}

function downloadExcel(state, district, blkd, udate, udateto){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var blkName = document.getElementById("blockk").options[document.getElementById("blockk").selectedIndex].text;
 
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("blkName").value=blkName;
    document.getElementById("udate").value=udate;
    document.getElementById("userdate2").value=udateto;
	
    document.inauguration.action="downloadExcelInaugurationReport";
	document.inauguration.method="post";
	document.inauguration.submit();
}

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }
  
function openLargeImage(imageSrc, index, total) {
	document.getElementById('imagePopup').style.display = 'none';
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/Inauguration/' + imageSrc;			//PRD
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/vanyatradoc/Inauguration/' + imageSrc;	//TEST
// 	document.getElementById('largeImage').src = 'resources/images/watershedyatra/' + imageSrc;												//Local
	document.getElementById('largeImagePopup').style.display = 'block';
	currentIndex = index;
	totalImages = total;
}

function closeLargeImagePopup() {
	document.getElementById('largeImagePopup').style.display = 'none';
}

function showNextImage() {
	if (currentIndex < totalImages - 1) {
		currentIndex++;
		let nextImageSrc = $('.image-container img')[currentIndex].src;
		document.getElementById('largeImage').src = nextImageSrc;
	}
}

function showPrevImage() {
	if (currentIndex > 0) {
		currentIndex--;
		let prevImageSrc = $('.image-container img')[currentIndex].src;
		document.getElementById('largeImage').src = prevImageSrc;
	}
}

</script>

<style type="text/css">

/* Popup container */
#imagePopup {
display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ 
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}

/* Popup content */
.popup-content {
  background-color: #fefefe;
  margin-left: 500px;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px; /* Increased max-width */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
}

/* Close button */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

/* Image list */
.image-container ul {
  list-style-type: none;
  padding: 30px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); /* Adjust minmax values as needed */
  gap: 10px; /* Adds equal space between images */
}

.image-container li {
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-container img {
  max-width: 100%;
  max-height: 100px;
  border-radius: 5px;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
}

/* Large image pop-up */
/*#largeImagePopup {
  display: none; /* Hidden by default 
  position: fixed;
  top: 300px;
  left: 60%;
  width: 30%;
  transform: translateX(-50%);
  z-index: 1000;
}
*/
#largeImagePopup {
  display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ */
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}

/* Large image pop-up content */
.large-image-popup-content {
  background-color: #fefefe;
  border: 1px solid #888;
  width: 100%;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative; /* Add this line */
}

/* Adjust close button position for large image pop-up */
.large-image-popup-content .close {
  position: absolute; /* Change from float to absolute */
  top: 10px; /* Adjust as needed */
  right: 10px; /* Adjust as needed */
  color: #aaa;
  font-size: 28px;
  font-weight: bold;
}

.large-image-popup-content .close:hover,
.large-image-popup-content .close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

#largeImage {
  width: 80%;
  height: auto;
  max-height: 80vh; /* Adjust this value as needed */
}

.nav-arrow {
  color: black;
  font-size: 40px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

#prevImage {
  left: 20px;
}

#nextImage {
  right: 20px;
}

</style>

</head>
<div class="card">

	<div class="table-responsive">
		<br />
		<div class="col" style="text-align: center;">
			<h5>List of Watershed Yatra - Inauguration Program Details</h5>
		</div>
		<form:form autocomplete="off" name="inauguration" id="inauguration" action="getInaugurationReport" method="get">
			<br />
			<input type="hidden" name="stName" id="stName" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
			<input type="hidden" name="blkName" id="blkName" value="" />
			<input type="hidden" name="udate" id="udate" value="" />
			<input type="hidden" name="userdate2" id="userdate2" value="" />

			<table>
				<tr>
					<td class="label">State <span style="color: red;">*</span></td>
					<td><select name="state" id="state" onchange="showChangedata();" required="required">
							<option value="0">--All State--</option>
							<c:if test="${not empty stateList}">
								<c:forEach items="${stateList}" var="lists">
									<c:if test="${lists.key eq state}">
										<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
									</c:if>
									<c:if test="${lists.key ne state}">
										<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
					</select></td>

					<td class="label">District <span style="color: red;">*</span></td>
					<td><select name="district" id="district" onchange="showChangedata();" required="required">
							<option value="0">--All District--</option>
							<c:if test="${not empty districtList}">
								<c:forEach items="${districtList}" var="lists">
									<c:if test="${lists.key eq district}">
										<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
									</c:if>
									<c:if test="${lists.key ne district}">
										<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
					</select></td>

					<td class="label">Block <span style="color: red;">*</span></td>
					<td><select name="blockk" id="blockk" required="required" >
							<option value="0">--All Block--</option>
							<c:if test="${not empty blockList}">
								<c:forEach items="${blockList}" var="lists">
									<c:if test="${lists.key eq blkd}">
										<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
									</c:if>
									<c:if test="${lists.key ne blkd}">
										<option value="<c:out value='${lists.key}'/>"><c:out value="${lists.value}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
					</select></td>
					
					<td class="label">From Date </td>
					<td><input type="date" name="userdate" id="userdate" value="udate" class="form-control activity" style="width: 100%;" /></td>
					<td class="label">To Date </td>
					<td><input type="date" name="userdateto" id="userdateto" value="" class="form-control activity" style="width: 100%;" /></td>

					<td align="left">&nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value='Get Data' /></td>
				</tr>
			</table>

			<br />
			<c:if test="${not empty inaugurationList}">
				<button name="exportExcel" id="exportExcel" onclick="downloadExcel('${state}','${district}','${blkd}','${udate}','${dateto}')" class="btn btn-info">Excel</button>
				<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${district}','${blkd}','${udate}','${dateto}')" class="btn btn-info">PDF</button>
			</c:if>
			<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
			<br />

			<table id="tblReport" class="table">
				<thead class="theadlist" id="theadlist">
				<tr><td colspan="37" align="left"> <b>From Date :</b> ${fromDateStr} &nbsp; &nbsp; <b> To Date :</b> ${toDateStr}</td></tr>
					<tr>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">S.No.</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Date</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">State Name</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">District Name</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Block Name</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Location</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Remarks</th>
						<th colspan="11" style="text-align:center; vertical-align: middle;">Number of Participation</th>
						<th colspan="19" style="text-align:center; vertical-align: middle;">Activities</th>
					</tr>
					<tr>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Participants/Villagers</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Ministers</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Member of Parliament</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Legislative Members</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Other Public Representatives</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Government Officials</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Gram Sabha completed before the arrival of the van</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Prabhat Pheri completed before the arrival of the van</th>

						<th rowspan="2" style="text-align:center; vertical-align: middle;">Flag off of Van</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Launch of Theme Song</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Bhoomi Poojan</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Lokarpan</th>
						<th colspan="3" style="text-align:center; vertical-align: middle;">Shramdaan</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Plantation</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Award Distribution (Felicitation)</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of stalls of Departments</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of stalls of SHGs/FPOs</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of LakhPati Didi Participated</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">No of Uploaded Photographs</th>
					</tr>
					<tr>
						<th style="text-align:center; vertical-align: middle;">Male</th>
						<th style="text-align:center; vertical-align: middle;">Female</th>
						<th style="text-align:center; vertical-align: middle;">Central Level</th>
						<th style="text-align:center; vertical-align: middle;">State Level</th>
						<th style="text-align:center; vertical-align: middle;">Assembly</th>
						<th style="text-align:center; vertical-align: middle;">Council</th>
						<th style="text-align:center; vertical-align: middle;">Number of Works</th>
						<th style="text-align:center; vertical-align: middle;">Cost of Total works (in Lakh)</th>
						<th style="text-align:center; vertical-align: middle;">Number of Works</th>
						<th style="text-align:center; vertical-align: middle;">Cost of Total works (in Lakh)</th>
						<th style="text-align:center; vertical-align: middle;">Number of Locations</th>
						<th style="text-align:center; vertical-align: middle;">No. of people participated</th>
						<th style="text-align:center; vertical-align: middle;">No. of Man Hours</th>
						<th style="text-align:center; vertical-align: middle;">Area (in ha.)</th>
						<th style="text-align:center; vertical-align: middle;">No. of Agro forestry / Horticultural Plants (No. of Sapling)</th>
					</tr>
					<tr>
						<% for (int i = 1; i <= 34; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
				</thead>
				
				<tbody>
					<c:set var="statename" value="" />
					<c:set var="dist" value="" />
					<c:set var="flagwis" value="" />
					<c:set var="ac" value="1" />
					<c:if test="${inaugurationList ne null}">
 						<c:forEach items="${inaugurationList}" var="data" varStatus="count">
 							<tr>
								<td><c:out value='${count.count}' /></td>
								<td> <c:out value="${data.date}" /></td>
								<c:choose>
									<c:when test="${statename ne data.stname}">
										<c:set var="statename" value="${data.stname}" />
										<td> <c:out value="${data.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
<%-- 								<td class="text-left"> <c:out value="${data.stname}" /></td> --%>
								<td class="text-left"> <c:out value="${data.distname}" /></td>
 								<td class="text-left"> <c:out value="${data.blockname}" /></td>
								<td class="text-left"> <c:out value="${data.location}" /></td>
								<td class="text-left"> <c:out value="${data.remarks}" /></td>
 								<td class="text-right"> <c:out value="${data.male_participants}" /></td>
								<td class="text-right"> <c:out value="${data.female_participants}" /></td>
 								<td class="text-right"> <c:out value="${data.central_ministers}" /></td>
								<td class="text-right"> <c:out value="${data.state_ministers}" /></td>
 								<td class="text-right"> <c:out value="${data.parliament}" /></td>
 								<td class="text-right"> <c:out value="${data.assembly_members}" /></td>
 								<td class="text-right"> <c:out value="${data.council_members}" /></td>
								<td class="text-right"> <c:out value="${data.others}" /></td>
 								<td class="text-right"> <c:out value="${data.gov_officials}" /></td>
 								<td class="text-left"> <c:out value="${data.gram_sabha == 'true' ? 'Yes' : 'No'}" /></td>
 								<td class="text-left"> <c:out value="${data.prabhat_pheri == 'true' ? 'Yes' : 'No'}" /></td>
								<td class="text-left"> <c:out value="${data.flagoff == 'true' ? 'Yes' : 'No'}" /></td>
 								<td class="text-left"> <c:out value="${data.themesong == 'true' ? 'Yes' : 'No'}" /></td>
								<td class="text-right"> <c:out value="${data.no_works_bhoomipoojan}" /></td>
 								<td class="text-right"> <c:out value="${data.tot_works_bhoomipoojan}" /></td>
 								<td class="text-right"> <c:out value="${data.no_works_lokarpan}" /></td>
								<td class="text-right"> <c:out value="${data.tot_works_lokarpan}" /></td>
 								<td class="text-right"> <c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.no_people_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.man}" /></td>
 								<td class="text-right"> <c:out value="${data.area_plantation}" /></td>
								<td class="text-right"> <c:out value="${data.no_plantation}" /></td>
 								<td class="text-right"> <c:out value="${data.no_awards}" /></td>
 								<td class="text-right"> <c:out value="${data.dept_stalls}" /></td>
 								<td class="text-right"> <c:out value="${data.shg_fpo_stalls}" /></td>
 								<td class="text-right"> <c:out value="${data.no_lakhpati_didi}" /></td>
<%--  								<td class="text-right"> <c:out value="${data.image_count}" /></td> --%>
 								<td class="text-right">
									<a href="#" data-id="${data.inauguaration_id}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${data.image_count}" /></a> 
								</td>
 							</tr>
 						</c:forEach>
					</c:if>
					<c:if test="${inaugurationListSize eq 0}">
						<tr>
							<td align="center" colspan="18" class="required" style="color:red;">Data Not Found</td>
							<td colspan="16" ></td>
						</tr>
					</c:if>
				</tbody>
				</tbody>

			</table>
			<!--           <tr> -->
			<!--             <td>&nbsp;</td> -->
			<!--           </tr> -->

		</form:form>
		<br>

	</div>
</div>

		<!-- Show Image Modal HTML -->
	<div id="imagePopup" class="popup" style="display:none;">
		<div class="popup-content">
			<span class="close" onclick="closePopup()">&times;</span>
			<div id="imageList" class="image-container"></div>
		</div>
	</div>

	<div id="largeImagePopup" class="popup" style="display: none;">
		<div class="large-image-popup-content">
			<span class="close" onclick="closeLargeImagePopup()">&times;</span>
			<div class="nav-arrow" id="prevImage" onclick="showPrevImage()">&#10094;</div>
			<img id="largeImage" src="" alt="Large Image" />
			<div class="nav-arrow" id="nextImage" onclick="showNextImage()">&#10095;</div>
		</div>
	</div>
	
<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});


document.getElementById('userdateto').addEventListener('change', function() {
    var fromDate = document.getElementById('userdate').value;
    if (!fromDate) {
        alert('Please select From Date first.');
        this.value = ''; // Reset the To Date
        $('#userdate').focus();
    }
});
</script>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>