<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/VillageWatershed.js" />'></script>
<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var block = $('#block').val();
	var gp = $('#grampan').val();
	var userdate = $('#userdate').val();
	var userdateto = $('#userdateto').val();
	
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	if(block==='')
	{
		alert('Please select Block ');
		$('#block').focus();
		e.preventDefault();
	}
	
	/*  if(userdateto!='')
	{
		if(userdate==='')
		{
			$('#userdateto').val('');
			alert('Please select From Date ');
			$('#userdate').focus();
			e.preventDefault();
		}
	}  */
/*	 	if(userdateto!='' && userdate!='')
	{
		const dateTimeStr = $('#userdate').val();
		const dateTimeStrto = $('#userdateto').val();
		const dateObj = new Date(dateTimeStr); 
		const dateObjto = new Date(dateTimeStrto); 
	if (dateObjto < dateObj) {
			alert('From date Can not be greater than To date');
			$('#userdate').val('');
			$('#userdateto').val('');
		}  
	} */
	else{
		
		document.routePlan.action="showWatershedYatraVillageReport";
		document.routePlan.method="post";
		document.routePlan.submit();
	}
	return false;
} 

function downloadPDF(state, district, blkd, grampn,userdate, dateto){
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var blkName = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    var gpkName = document.getElementById("grampan").options[document.getElementById("grampan").selectedIndex].text;
  
 
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("blkName").value=blkName;
    document.getElementById("gpkName").value=gpkName;
    document.getElementById("userdate1").value=userdate;
    document.getElementById("userdate2").value=dateto;
	
    document.routePlan.action="downloadVillageYatraReportPDF";
	document.routePlan.method="post";
	document.routePlan.submit();
}

function showChangedata(){
	
	
    document.routePlan.action="getWatershedYatraVillageReport";
	document.routePlan.method="get";
	document.routePlan.submit();
}

function downloadExcel(state, district, blkd, grampn,userdate, dateto){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var blkName = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    var gpkName = document.getElementById("grampan").options[document.getElementById("grampan").selectedIndex].text;
 
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("blkName").value=blkName;
    document.getElementById("gpkName").value=gpkName;
    document.getElementById("userdate1").value=userdate;
    document.getElementById("userdate2").value=dateto;
	
    document.routePlan.action="downloadExcelVillageYatraReport";
	document.routePlan.method="post";
	document.routePlan.submit();
}

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }


function closeLargeImagePopup() {
    document.getElementById('largeImagePopup').style.display = 'none';
}

function openLargeImage(imageSrc, index, total) {
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/WatershedYatraVillage/' + imageSrc;			//PRD
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/vanyatradoc/WatershedYatraVillage/' + imageSrc;	//TEST
// 	document.getElementById('largeImage').src = 'resources/images/WatershedYatraVillage/' + imageSrc;												//Local
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

<style>

#imagePopup {
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

/* Popup content */
.popup-content {
  background-color: #fefefe;
  margin-left: 300px;
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
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 3;
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


/* Popup content */
.large-image-popup-content {
  background-color: #fefefe;
  width: 100%;
  height: auto;
  max-height: 80vh; /* Set a max height to avoid overflowing */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  display: flex;
  justify-content: center; /* Center the image horizontally */
  align-items: center; /* Center the image vertically */
  position: relative;
}

/* Large image */
#largeImage {
  width: 100%; /* Ensure it fits inside the popup */
  height: auto;
  max-height: 80vh; /* Restrict height to 80% of the viewport height */
  object-fit: contain; /* Ensure the aspect ratio is maintained */
}

.nav-arrow {
  color: black;
  font-size: 40px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2;
}

#prevImage {
  left: 20px;
}

#nextImage {
  right: 20px;
}

</style>

</head>


<div class ="card">

<div class="table-responsive">
<br/>
<div class="col" style="text-align:center;"><h5>List of Watershed Yatra at Village Level </h5></div>
 <form:form autocomplete="off" name="routePlan" id="routePlan"  action="getWatershedYatraVillageReport" method="get">
 		<br/>
 		<input type="hidden" name="stName" id="stName" value="" />
		<input type="hidden" name="distName" id="distName" value="" />
		<input type="hidden" name="blkName" id="blkName" value="" />
		<input type="hidden" name="gpkName" id="gpkName" value="" />
		<input type="hidden" name="userdate1" id="userdate1" value="" />
		<input type="hidden" name="userdate2" id="userdate2" value="" />
		
      <table >
        <tr>
          <td class="label">State&nbsp;<span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="showChangedata();" required="required">
              		<option value="0">--All State--</option>
              		
                  	<c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
           <td class="label">District&nbsp;<span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="showChangedata();" required="required">
              		<option value="0">--All District--</option>
                  	 <c:if test="${not empty districtList}">
               					<c:forEach items="${districtList}" var="lists">
               						<c:if test="${lists.key eq district}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne district}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 
              </select>
          </td>
          
           <td class="label">Block&nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="block" id="block" required="required" onchange="showChangedata();">
              <option value="0">--All Block--</option>
              	<c:if test="${not empty blockList}">
               					<c:forEach items="${blockList}" var="lists">
               						<c:if test="${lists.key eq blkd}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne blkd}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
           <td class="label">Gram Panchayat&nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="grampan" id="grampan" required="required" >
              <option value="0">--All Gram Panchayat--</option>
              	<c:if test="${not empty gpList}">
               					<c:forEach items="${gpList}" var="lists">
               						<c:if test="${lists.key eq grampn}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne grampn}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
           <td>
           <div class="row">
    			<div class="form-group col-12">
    			
      		  <label for="date">From Date : </label>
      		  <input type="date" name="userdate" id="userdate" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
			</div>
			 </td>
			 
			 <td>
           <div class="row">
    			<div class="form-group col-12">
    			
      		  <label for="date">To Date&nbsp; : </label>
      		  <input type="date" name="userdateto" id="userdateto" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
			</div>
			 </td>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>

 <br/>
<c:if test="${not empty dataList}">

<button name="exportExcel" id="exportExcel" onclick="downloadExcel('${state}','${district}','${blkd}','${grampn}','${userdate}', '${dateto}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${district}','${blkd}','${grampn}', '${userdate}', '${dateto}')" class="btn btn-info">PDF</button>

</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 <br/>
        <table class="table">
          <tr>
          
          
            <td>
            	<!-- <h5 class="text-center font-weight-bold"><u> List of Watershed Yatra at Village Level</u></h5>  -->
     		<table class="table table-bordered table-striped table-highlight w-auto" id="convergenceTable"> 
 						<thead class ="theadlist" id = "theadlist"> 
 						<tr><td colspan="35" align="left"> <b>From Date :</b> ${fromDateStr} &nbsp; &nbsp; <b> To Date :</b> ${toDateStr}</td></tr>
							<tr> 
								<th rowspan="3" style="text-align:center; vertical-align: middle;">S.No.</th>  
								<th rowspan="3" style="text-align:center; vertical-align: middle;">Date</th> 
								<th rowspan="3" style="text-align:center; vertical-align: middle;">Time</th> 
								<th rowspan="3" style="text-align:center; vertical-align: middle;">State Name</th>
								<th rowspan="3" style="text-align:center; vertical-align: middle;">District Name</th> 
								<th rowspan="3" style="text-align:center; vertical-align: middle;">Block Name</th> 
 								<th rowspan="3" style="text-align:center; vertical-align: middle;">GP Name</th>  
								<th rowspan="3" style="text-align:center; vertical-align: middle;">Village Name</th>  
								<th rowspan="3" style="text-align:center; vertical-align: middle;">Location</th>
								<th rowspan="3" style="text-align:center; vertical-align: middle;">Remarks</th>
								<th colspan="9" style="text-align:center; vertical-align: middle;">Number of Participation</th> 
 								<th colspan="16" style="text-align:center; vertical-align: middle;">Activities</th> 
							</tr> 
							<tr>
							<th colspan="2" style="text-align:center; vertical-align: middle;">Participants/Villagers</th> 
								<th colspan="2" style="text-align:center; vertical-align: middle;">Ministers</th> 
 								<th rowspan="2" style="text-align:center; vertical-align: middle;">Member of Parliament</th> 
 								<th colspan="2" style="text-align:center; vertical-align: middle;">Legislative Members</th> 
 								<th rowspan="2" style="text-align:center; vertical-align: middle;">Other Public Representatives</th> 
 								<th rowspan="2" style="text-align:center; vertical-align: middle;">Government Officials</th> 
								
								<th rowspan="2" style="text-align:center; vertical-align: middle;">AR Experience</th> 
								<th rowspan="2" style="text-align:center; vertical-align: middle;">Shramdan</th> 
 								<th rowspan="2" style="text-align:center; vertical-align: middle;">Film on Yatra</th> 
								<th rowspan="2" style="text-align:center; vertical-align: middle;">People Participated in Quiz</th> 
 								<th rowspan="2" style="text-align:center; vertical-align: middle;">Cultural Activity</th> 
 								<th colspan="2" style="text-align:center; vertical-align: middle;">Bhoomi Poojan</th> 
 								<th colspan="2" style="text-align:center; vertical-align: middle;">Lokarpan</th> 
								<th colspan="3" style="text-align:center; vertical-align: middle;">Shramdaan</th> 
 								<th colspan="2" style="text-align:center; vertical-align: middle;">Plantation</th> 
								<th rowspan="2" style="text-align:center; vertical-align: middle;">Award Distribution (Felicitation)</th> 
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
								<% for (int i = 1; i <= 35; i++) { %>
									<th style="text-align:center; vertical-align: middle;"><%= i %></th>
								<% } %>
							</tr>
							
							
						</thead> 
					<c:set var="statename" value="" />
               		<c:set var="dist" value="" />
					
 						<c:forEach items="${dataList}" var="data" varStatus="count">
 							<tr>
								<td><c:out value='${count.count}' /></td>
								<td><c:out value="${data.date}" /></td>
								<td><c:out value="${data.timed}" /></td>
								
								<c:choose>
									<c:when test="${statename ne data.stname}">
										<c:set var="statename" value="${data.stname}" />
										<td> <c:out value="${data.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<%-- <td><c:out value="${data.stname}" /></td> --%>
								
								<c:choose>
									<c:when test="${dist ne data.distname}">
										<c:set var="dist" value="${data.distname}" />
										<td> <c:out value="${data.distname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<%-- <td><c:out value="${data.distname}" /></td> --%>
 								<td><c:out value="${data.blockname}" /></td>
 								<td><c:out value="${data.gpname}" /></td>
 								<td><c:out value="${data.villagename}" /></td>
								<td><c:out value="${data.location}" /></td>
								<td><c:out value="${data.remarks}" /></td>
 								<td class="text-right"><c:out value="${data.male_participants}" /></td>
								<td class="text-right"><c:out value="${data.female_participants}" /></td>
 								<td class="text-right"><c:out value="${data.central_ministers}" /></td>
								<td class="text-right"><c:out value="${data.state_ministers}" /></td>
 								<td class="text-right"><c:out value="${data.parliament}" /></td>
 								<td class="text-right"><c:out value="${data.assembly_members}" /></td>
 								<td class="text-right"><c:out value="${data.council_members}" /></td>
								<td class="text-right"><c:out value="${data.others}" /></td>
 								<td class="text-right"><c:out value="${data.gov_officials}" /></td>
 								<td class="text-right"><c:out value="${data.no_of_ar_experience_people}" /></td>
								<td><c:out value="${data.bhumi_jal_sanrakshan == 'true' ? 'Yes' : 'No'}" /></td>
 								<td><c:out value="${data.watershed_yatra_film == 'true' ? 'Yes' : 'No'}" /></td>
 								<td class="text-right"><c:out value="${data.quiz_participants}" /></td>
 								<td><c:out value="${data.cultural_name}" /></td>
								<td class="text-right"><c:out value="${data.no_works_bhoomipoojan}" /></td>
 								<td class="text-right"><c:out value="${data.tot_works_bhoomipoojan}" /></td>
 								<td class="text-right"><c:out value="${data.no_works_lokarpan}" /></td>
								<td class="text-right"><c:out value="${data.tot_works_lokarpan}" /></td>
 								<td class="text-right"><c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"><c:out value="${data.no_people_shramdaan}" /></td>
								<td class="text-right"><c:out value="${data.manhour}" /></td>
 								<td class="text-right"><c:out value="${data.area_plantation}" /></td>
								<td class="text-right"><c:out value="${data.no_plantation}" /></td>
 								<td class="text-right"><c:out value="${data.no_awards}" /></td>
								<td class="text-right">
									<a href="#" data-id="${data.watershed_yatra_id}" class="showImage"><c:out value="${data.image_count}" /></a> 
								</td>
 								
 							</tr>
 						</c:forEach>
						<c:if test="${dataListSize eq 0}">
							<tr>
								<td align="center" colspan="20" class="required" style="color:red;">Data Not Found</td>
							</tr>
						</c:if>
		</table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        
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