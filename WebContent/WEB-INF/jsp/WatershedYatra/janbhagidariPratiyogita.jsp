<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/janbhagidariPratiyogita.js" />'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
<script type="text/javascript">

function validatePercentage(input) {
    let val = input.value.trim();

    // Allow only numbers and decimal point
    if (!/^\d*\.?\d{0,4}$/.test(val)) {
        input.value = val.slice(0, -1);  // Remove last character if invalid
        return;
    }

    let num = parseFloat(val);

    if (val !== "" && (isNaN(num) || num < 0 || num > 100)) {
        alert("Percentage must be between 0 and 100.");
        input.value = "";
    }
}

function getMultipleSelectedValue()
{
  var x = document.getElementById("projectselect");	
  var x=document.getElementById("project");
  var selectedText ="";   //= onchange="getMultipleSelectedValue();" 
 
  for (var i = 0; i < x.options.length; i++) 
  {
     if(x.options[i].selected ==true)
     {
    	 selectedText+=x.options[i].text+",";
     }
  }
  document.getElementById("projectselect").innerHTML=selectedText;
 
}

function myOverNoGP(e) { 
	
	$dCode = $('#district option:selected').val();
	$nogp = $('#nogp').val();
		$.ajax({
			url: "getTotalNoofGP",
			type: "post",
			data: {dCode: $dCode },
			error: function(xhr, status, er) {
					   console.log(er);
			},
			success: function(data) 
			{
					if(data < $nogp)
					{
								alert('Total No. of Gram Panchayat is more then You have Enter');
								$('#nogp').val('');
					}
			}
	});
}

function myOverNoVill(e) { 
	
	$dCode = $('#district option:selected').val();
	$novillage = $('#novillage').val();
		$.ajax({
			url: "getTotalNoofVill",
			type: "post",
			data: {dCode: $dCode },
			error: function(xhr, status, er) {
					   console.log(er);
			},
			success: function(data) 
			{
					if(data < $novillage)
					{
								alert('Total No. of Village is more then You have Enter');
								$('#novillage').val('');
					}
			}
	});
}

	
</script>

<meta charset="ISO-8859-1">
<title>janbhagidari Pratiyogita</title>

<style>
input[type=text] {
	width: 100px;
	
}

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
#largeImagePopup {
  display: none; /* Hidden by default */
  position: fixed;
  top: 300px;
  left: 60%;
  width: 30%;
  transform: translateX(-50%);
  z-index: 1000;
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

.is-invalid {
    border: 2px solid red !important;
}
</style>

</head>
<body>
<!--  
<c:if test="${result != null}">
	<script>
	    alert("<c:out value='${result}'/>");
	</script>
</c:if>
-->

<c:if test="${not empty result}">
    <div class="alert alert-info" style="color: green;">${result}</div>
     <script>
	    alert("<c:out value='${result}'/>");
	</script> 
</c:if>

<c:if test="${not empty result1}">
    <div class="alert alert-info" style="color: red;">${result1}</div>
     <script>
	    alert("<c:out value='${result1}'/>");
	</script> 
</c:if>

	<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed - Janbhagidari Cup under WDC-PMKSY2.0</h4> </div>
		
		<form:form autocomplete="off" method="post" name="janbhagidari" id="janbhagidari" action="saveJanbhagidariPratiyogita" modelAttribute="janbhagidari" >
			  <hr/>
			  
			 
			<div class="row">
			<div class="form-group col-3">
    <label for="state"><b>State Name:</b></label><br/>
    <c:out value="${stateName}" /><br/>
</div>

<div class="form-group col-3">
    <label for="district"><b>District Name:</b></label><br/>

    <c:if test="${userType == 'SL'}">
        <span class="projectError"></span>
        <select class="form-control district" id="district" name="district" required>
            <option value="">--Select District--</option>
            <c:forEach items="${distList}" var="dist"> 
                <option value="${dist.key}"><c:out value="${dist.value}" /></option>
            </c:forEach>
        </select>
    </c:if>

    <c:if test="${userType == 'PI'}">
        <c:out value="${distName}" /><br/>
        <input type="hidden" id="district" name="district" value="${distCode}">
    </c:if>
</div>

<div class="form-group col-3">
    <label for="projid"><b>Project Name:</b></label><br/>

    <c:if test="${userType == 'SL'}">
        <span class="activityError"></span>
        <select class="form-control activity" id="projid" name="projid">
            <option value="">--Select Project--</option>
            
        </select>
    </c:if>

    <c:if test="${userType == 'PI'}">
        <span class="activityError"></span>
        <select class="form-control activity" id="projid" name="projid" required>
            <option value="">--Select Project--</option>
            <c:forEach items="${projList}" var="proj"> 
                <option value="${proj.key}"><c:out value="${proj.value}" /></option>
            </c:forEach>
        </select>
    </c:if>
</div>
			
	    		
    		<div class="form-group col-3">
    			
    		</div>
    		
    		<!-- <div class="form-group col-3">
    			
      		  <label for="date">Project Inception Date: </label>
      		  <input type="date" name="datein" id="datein" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
    		
    		<div class="form-group col-3">
    			
      		  <label for="date">Proposed Project Completion Date: </label>
      		  <input type="date" name="datecom" id="datecom" class="form-control activity" style="width: 100%;" />
       		 
    		</div> -->
    		
    		
    		</div>
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=2 class="text-left">A. Basic Information about Project</th>
     	</tr>
     	<tr>
     		<td>Total No. of Gram Panchayat</td>
     		<td><input type="text" id="nogp" name="nogp" autocomplete="off"  onblur="myOverNoGP(this)"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Total No. of Villages</td>
     		<td><input type="text" id="novillage" name="novillage" autocomplete="off" onblur="myOverNoVill(this)"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td>Total Area Allocated for Project (ha.)</td>
     		<td><input type="text" id="projarea" name="projarea" autocomplete="off"
								 onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     	</tr>
     	<tr>
     		<td>Total Project Outlay (Rs. In Lakh)</td>
     		<td><input type="text" id="projoutlay" name="projoutlay" autocomplete="off"
								 onfocusin="decimalToFourPlace(event)" maxlength="10" required/></td>
     		
     	</tr>
     	
     	</table>
     	<table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">B. Preparatory Work</th>
     		
     	</tr>
     	
     	<tr>
     		<td colspan=3>1. Identification & Engagement of NGOs:</td>
     		<td>Action</td>
     	</tr>
     	<!-- <tr>
     		<td>1.1 Name of NGOs</td>
     		<td>
     			<input type="text" class="form-control activity" name="name_ngo" id="name_ngo" autocomplete="off" style="width: 100%; max-width: 800px;" required />
			</td>
     	</tr> 
     	
     	<tr>
     		<td>1.1 No. of NGOs</td>
     		<td>
     			<input type="text" id="no_ngo" name="no_ngo" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required />
			</td>
     	</tr>
     	
     	<tr>
     		<td>1.2 Total No. of Gram Panchayat to be covered by NGO</td>
     		<td>
     			<input type="text" id="no_gp_ngo" name="no_gp_ngo" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required />
			</td>
     	</tr>
     	
     	<tr>
     		<td>1.3 Total No. of Villages to be covered by NGO</td>
     		<td>
     			<input type="text" id="no_vill_ngo" name="no_vill_ngo" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required />
			</td>
     	</tr>
     	
     	<tr>
     		<td colspan=3>Details of NGOs</td>
     	</tr> -->
     	<tbody id="listvillageGPWiseTbody">
     	<tr>
     		<td>Name of NGO &nbsp; <input type="text" class="form-control activity" name="name_ngo" id="name_ngo" class="name_ngo form-control" autocomplete="off" style="width: 100%; max-width: 400px;" required /></td>
     		<td>
     			Name of Gram Panchayat to be Covered by NGO &nbsp; <select id="ddlgp" name="ddlgp" class="ddlgp form-control" multiple="multiple"><option value="">--Select Gram Panchayat--</option></select>
			<div class="selected-gp-list" style="margin-top: 5px; font-size: 14px; color: #333;"></div>
			</td>
			
			<td>
				Name of Villages to be Covered by NGO &nbsp; <select id="ddlvill" name="ddlvill" class="ddlvill form-control" multiple="multiple"><option value="">--Select Village--</option></select>
			<div class="selected-village-list" style="margin-top: 5px; font-size: 14px; color: #333;"></div>
			</td>
			
			 <td style="vertical-align: bottom;">
                <button type="button" class="btn btn-success btnAddRow">+</button>
            </td>
     	</tr>
     	</tbody>
     	<tr>
    <td colspan="4">
        <div style="margin-top: 10px;">
            <label>No of NGOs: </label>
            <input type="text" id="ngoCount" readonly style="width: 50px; margin-right: 20px;" />

            <label>No of Gram Panchayats: </label>
            <input type="text" id="gpCount" readonly style="width: 50px; margin-right: 20px;" />

            <label>No of Villages: </label>
            <input type="text" id="villCount" readonly style="width: 50px;" />
        </div>
    </td>
</tr>

     	<tr>
     		<td colspan=4></td>
     	</tr>
     	
     	<tr>
     		<td colspan=4>2. Opening of SWCK Account at Gram Panchayat /Watershed Committee Level in Nationalized Bank:</td>
     		
     	</tr>
     	
     	
     	
     	<tr>
     		<td>2.1 Selection of Gram Panchayat where SWCK Account Opened</td>
     		<td><select id="swckgp" name="swckgp" class="swckgp form-control" multiple="multiple"><option value="">--Select Gram Panchayat--</option></select></td>
     		<td colspan=2> <div class="selected-swckgp-list" style="margin-top: 5px; font-size: 14px; color: #333;"></div></td>
     		
     	</tr>
     </table>
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=2 class="text-left">C. Fund Utilization under WDC-PMKSY2.0</th>
     	</tr>
     	<tr>
     		<td>Total Project Outlay of WDC-PMKSY 2.0</td>
     		<td><input type="text" id="funoutlay" name="funoutlay" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     	</tr>
     	<tr>
     		<td>Total Project Expenditure of WDC-PMKSY 2.0</td>
     		<td><input type="text" id="projexp" name="projexp" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     	</tr>
     	
     	<tr>
     		<td>Percentage of Expenditure (%)</td>
     		<td><input type="text" id="expper" name="expper" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" oninput="validatePercentage(this)" maxlength="5" required /></td>
     	</tr>
     	
     	
     	<tr>
     		<td colspan=2 align="center">

     			<!-- <input type="button" class="btn btn-info" id="saveAsDraft"  name="saveAsDraft" value='Save' /> -->
     			
     			<input type="button" class="btn btn-info" id="saveAsDraft" name="saveAsDraft" value ="Save As Draft"/>
     		</td>
     	</tr>
     	
     </table>
     
     
     
     </div>
		</div>  	
		</form:form>
		
	</div>
	
	
	
	 <div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Draft List of Watershed - Janbhagidari Cup<!-- Pratiyogita --> under WDC-PMKSY2.0</h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="2">S.No.  &nbsp; <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /></th> 
 								<th rowspan="2">State Name</th> 
								<th rowspan="2">District Name</th>
								<th rowspan="2">Project Name</th>
								<th colspan="4">Basic Information about Porject</th>
								<th colspan="4">Preparatory Work</th>
								<th colspan="3">Fund Utilization under WDC-PMKSY2.0</th>
							</tr>
							
							<tr>
								<th>Total No. of Gram Panchayat</th>
								<th>Total No. of Villages</th>
								<th>Total area Allocated for Project (ha.)</th>
								<th>Total Project Outlay (Rs. In Lakh)</th>
								
								<th>Name of NGOs</th>
								<th>Gram Panchayat to be covered by NGO</th>
								<th>Villages to be covered by NGO</th>
								<th>Selected Gram Panchayat where SWCK Account Opened</th>
								
								<th>Total Project Outlay of WDC-PMKSY 2.0</th>
								<th>Total Project Expenditure of WDC-PMKSY 2.0</th>
								<th>Percentage of Expenditure (%)</th>
								
								
							</tr>
						</thead>
							
 		<c:set var="previousPratiyogitaId" value="" />
<c:set var="serialNumber" value="1" />
<c:set var="st" value="" />
               <c:set var="proj" value="" />
               <c:set var="dist" value="" />
<c:forEach items="${dataList}" var="data" varStatus="count">
    <tr>
        <td>
            <!-- Display checkbox only for the first occurrence of a unique pratiyogita_id -->
            <c:if test="${data.pratiyogita_id != previousPratiyogitaId}">
                <input type="checkbox" class="chkIndividualkd" id="${data.pratiyogita_id}" name="${data.pratiyogita_id}" value="${data.pratiyogita_id}"/>
                <!-- Show serial number for first occurrence of each unique pratiyogita_id -->
                <c:out value="${serialNumber}" />
                <!-- Increment the serial number -->
                <c:set var="serialNumber" value="${serialNumber + 1}" />
            </c:if>
            <c:set var="previousPratiyogitaId" value="${data.pratiyogita_id}" />
        </td>

<c:choose>
									<c:when test="${st ne data.stname}">
										<c:set var="st" value="${data.stname}" />
										<td> <c:out value="${data.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${dist ne data.districtname}">
										<c:set var="dist" value="${data.districtname}" />
										<td> <c:out value="${data.districtname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
							<c:choose>
									<c:when test="${proj ne data.projname}">
										<c:set var="proj" value="${data.projname}" />
										<td> <c:out value="${data.projname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>	
        <%-- <td class="text-right"><c:out value="${data.stname}" /></td>
        <td class="text-right"><c:out value="${data.districtname}" /></td> 
        <td class="text-right"><c:out value="${data.projname}" /></td>--%>
        <td class="text-right"><c:out value="${data.nogp}" /></td>
        <td class="text-right"><c:out value="${data.novillage}" /></td>
        <td class="text-right"><c:out value="${data.projarea}" /></td>
        <td class="text-right"><c:out value="${data.projoutlay}" /></td>
        <td class="text-right"><c:out value="${data.ngo_name}" /></td>
        <td class="text-right"><c:out value="${data.gpnames}" /></td>
        <td class="text-right"><c:out value="${data.villnames}" /></td>
        <td class="text-right"><c:out value="${data.swck_gp_names}" /></td>
        <td class="text-right"><c:out value="${data.funoutlay}" /></td>
        <td class="text-right"><c:out value="${data.projexp}" /></td>
        <td class="text-right"><c:out value="${data.expper}" /></td>
    </tr>
</c:forEach>
 		

 		
 		
 		
 						
 						<tr>
								
								<td> <input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/> </td>
								<td> <input type="button" class="btn btn-info" id="complete" name="complete" value ="Complete"/> </td>
							</tr>
						<c:if test="${dataListSize eq 0}">
							<tr>
								<td align="center" colspan="15" class="required" style="color:red;">Data Not Found</td>
								<td colspan="16" ></td>
							</tr>
						</c:if>
		</table>
		
		
		</div>
		</div>
		
		
		<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Complete List of Watershed - Janbhagidari Cup under WDC-PMKSY2.0</h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="2">S.No. </th> 
								<th rowspan="2">State Name</th> 
								<th rowspan="2">District Name</th>
								<th rowspan="2">Project Name</th>
								<th colspan="4">Basic Information about Porject</th>
								<th colspan="4">Preparatory Work</th>
								<th colspan="3">Fund Utilization under WDC-PMKSY2.0</th>
							</tr>
							
							<tr>
								<th>Total No. of Gram Panchayat</th>
								<th>Total No. of Villages</th>
								<th>Total area Allocated for Project (ha.)</th>
								<th>Total Project Outlay (Rs. In Lakh)</th>
								
								<th>Name of NGOs</th>
								<th>Gram Panchayat to be covered by NGO</th>
								<th>Villages to be covered by NGO</th>
								<th>Selected Gram Panchayat where SWCK Account Opened</th>
								
								<th>Total Project Outlay of WDC-PMKSY 2.0</th>
								<th>Total Project Expenditure of WDC-PMKSY 2.0</th>
								<th>Percentage of Expenditure (%)</th>
								
								
								
							</tr>
						</thead>
						
 							
 						<c:forEach items="${compdataList}" var="data" varStatus="count">
 							<tr>
								
								<td>
 								<c:out value='${count.count}' />
 								</td>
 								<td class="text-right"> <c:out value="${data.stname}" /></td>	
 								<td class="text-right"> <c:out value="${data.districtname}" /></td>
 								<td class="text-right"> <c:out value="${data.projname}" /></td>
 								<td class="text-right"> <c:out value="${data.nogp}" /></td>
								<td class="text-right"> <c:out value="${data.novillage}" /></td>
		 						<td class="text-right"> <c:out value="${data.projarea}" /></td>
								<td class="text-right"> <c:out value="${data.projoutlay}" /></td>
								<td class="text-right"> <c:out value="${data.ngo_name}" /></td>
								<td class="text-right"> <c:out value="${data.gpnames}" /></td>
								<td class="text-right"> <c:out value="${data.villnames}" /></td>
								<td class="text-right"> <c:out value="${data.swck_gp_names}" /></td>
 								<td class="text-right"> <c:out value="${data.funoutlay}" /></td>
								<td class="text-right"> <c:out value="${data.projexp}" /></td>
 								<td class="text-right"> <c:out value="${data.expper}" /></td>
 											
 					</tr>
							
					
 						</c:forEach>
					
 						
						<c:if test="${compdataListSize eq 0}">
							<tr>
								<td align="center" colspan="15" class="required" style="color:red;">Data Not Found</td>
								
							</tr>
						</c:if>
		</table>
		
		
		</div>
		</div> 
		
	
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>