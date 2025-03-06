
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
<script src='<c:url value="/resources/js/watershedYatraParticipantReport.js" />'></script> 
<script type="text/javascript">

function showReport(e)
{
	
	var userdate = $('#userdate').val();
	var userdateto = $('#userdateto').val();
	
	if(userdate==='')
	{
		alert('Please select From date ');
		$('#userdate').focus();
		e.preventDefault();
	}
	if(userdateto==='')
	{
		alert('Please select To date ');
		$('#userdateto').focus();
		e.preventDefault();
	}
	
	else{
		
		document.routePlan.action="getWatershedYatraParticipantReport";
		document.routePlan.method="get";
		document.routePlan.submit();
	}
	return false;
} 

function downloadPDF(userdate, dateto){
	
    document.getElementById("userdate1").value=userdate;
    document.getElementById("userdate2").value=dateto;
	
    document.routePlan.action="downloadWatershedYatraParticipantReportPDF";
	document.routePlan.method="post";
	document.routePlan.submit();
}

function downloadExcel(userdate, dateto){
	
    document.getElementById("userdate1").value=userdate;
    document.getElementById("userdate2").value=dateto;
	
    document.routePlan.action="downloadExcelWatershedYatraParticipantReport";
	document.routePlan.method="post";
	document.routePlan.submit();
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
<div class="col" style="text-align:center;"><h5>State wise daily Public Participants </h5></div>
 <form:form autocomplete="off" name="routePlan" id="routePlan"  action="getWatershedYatraParticipantReport" method="get">
 		<br/>
 		
		<input type="hidden" name="userdate1" id="userdate1" value="" />
		<input type="hidden" name="userdate2" id="userdate2" value="" />
		
      <table >
        <tr align="center">
          <td class="label">From Date : </td>
          <td>
             <input type="date" name="userdate" id="userdate" class="form-control activity" style="width: 100%;" />
          </td>
          
           <td class="label">To Date : </td>
          <td>
             <input type="date" name="userdateto" id="userdateto" class="form-control activity" style="width: 100%;" />
          </td>
          
          
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>

 <br/>
<c:if test="${not empty ParticipantList}">
<button name="exportExcel" id="exportExcel" onclick="downloadExcel('${userdate}', '${dateto}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${userdate}', '${dateto}')" class="btn btn-info">PDF</button>
</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>


 <br/>
        <table class="table">
          <tr>
          
          
            <td>
            	<!-- <h5 class="text-center font-weight-bold"><u> List of Watershed Yatra at Village Level</u></h5>  -->
     		<table class="table table-bordered table-striped table-highlight w-auto" id="convergenceTable"> 
 						<thead class ="theadlist" id = "theadlist"> 
 						<%-- <tr><td  align="left"> <b>From Date :</b> ${fromDateStr} &nbsp; &nbsp; <b> To Date :</b> ${toDateStr}</td></tr> --%>
							<tr> 
								<th  style="text-align:center; vertical-align: middle;">S.No.</th>  
								<th  style="text-align:center; vertical-align: middle;">State Name</th>
								<c:choose>
									<c:when test="${monthListSize ne null}">
										<c:forEach items="${monthList}" var="monthListShow" begin="0" end="${monthListSize}">
											<th class="text-center"><c:out value='${monthListShow}' /></th>
										</c:forEach>
				
									</c:when>
									<c:otherwise>
										
									</c:otherwise>
								</c:choose>
 								<th  style="text-align:center; vertical-align: middle;">Total</th>
							</tr> 
							
						</thead> 
					<c:set var="statename" value="" />
					<c:set var="stname" value="" />
					<c:set var="check" value="true" />
               		<c:set var="checkdate" value="true" />
               		<c:set var="gtotal" value="0" />
               		<c:forEach items="${stateList}" var="data1" varStatus="status">
               		<c:set var="total" value="0" />
               			<tr>
               				<td><c:out value='${status.count}' /></td>
               				<td><c:out value="${data1}" /></td>
							<c:forEach items="${monthList}" var="monthListShow">
								
									<c:forEach items="${ParticipantList}" var="data">
<%-- 										<c:if test="${statename ne data.st_name && check}"> --%>
<%-- 											<td><c:out value="${data.st_name}" /></td> --%>
<%-- 											<c:set var="statename" value="${data.st_name}" /> --%>
<%-- 										</c:if> --%>
										<c:if test="${data1 eq data.st_name && monthListShow eq data.yatradate && check}">
											<td class="text-center"><c:out value='${data.total_participants}' /></td>
											<c:set var="total" value="${total + data.total_participants }" />
											<c:set var="checkdate" value="false" />
											<c:set var="check" value="false" />
										</c:if>

									</c:forEach>
									<c:if test ="${checkdate}">
										<td class="text-center"><c:out value='0' /></td>
									</c:if>
									<c:set var="check" value="true" />
									<c:set var="checkdate" value="true" />

							</c:forEach>
							<td class="text-center"><c:out value='${total}' /></td>
							<c:set var="gtotal" value="${gtotal + total }" />
							</tr>
						</c:forEach>

						<tr>
									<td class="table-primary"></td>
										<td align="right" class="table-primary" ><b>Grand Total </b></td>
								<c:forEach items="${listgrand}" var="data1">
										<td align="center" class="table-primary"><c:out value='${data1.total_participants}' /></td>
								
								</c:forEach>
						
								<td align="center" class="table-primary"><c:out value='${gtotal}' /></td>
						</tr>
						
						
						
						
							<%-- <c:forEach items="${ParticipantList}" var="data" varStatus="count">
 						
 							<tr>
 							<c:if test ="${statename ne data.st_name}">
 								<td><c:out value ="${data.st_name}"/></td>
 								<c:set var ="statename" value="${data.st_name}"/>
 							</c:if>

									<c:if test="${statename eq data.st_name}">
										<c:forEach items="${monthList}" var="monthListShow">
											
												<c:if test="${monthListShow eq data.yatradate}">
													<td class="text-center"><c:out value='${data.total_participants}' /></td>
													<c:set var="checkdate" value="false" />
												</c:if>
												
											
										</c:forEach>
										<c:if test ="${checkdate}">
											<td class="text-center"><c:out value='0' /></td>
											
										</c:if>
									</c:if>

								</tr>
 						
 							<c:set var="checkdate" value="true" />
 						</c:forEach> --%>
 							
 							
 							
 							
								
 								
 							
 						
						<c:if test="${ParticipantListSize eq 0}">
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
  //  alert(fromDate);
    if (!fromDate) {
        alert('Please select From Date first.');
        this.value = ''; // Reset the To Date
        $('#userdate').focus();
    }
    else {
        const currentDate = new Date();
        const dateToCheck = new Date(this.value);
        
        currentDate.setHours(0, 0, 0, 0);
        dateToCheck.setHours(0, 0, 0, 0);

        if (currentDate < dateToCheck) {
            alert("To date cannot be greater than the current date.");
            document.getElementById('userdateto').value = '';
            document.getElementById('userdateto').focus();
        }
    }
});

	
	
	

</script>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>