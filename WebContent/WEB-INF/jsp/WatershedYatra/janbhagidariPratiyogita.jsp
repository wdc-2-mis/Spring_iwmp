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
			<c:if test="${userType== 'SL' }">
				 State Name: <br/><br/>
				<c:out value="${stateName}"></c:out>
			</c:if>
			</div>
    		<div class="form-group col-3">
      			<label for="district">District Name: </label>
      			<select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="block">Project Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="projid" name="projid" >
    				<option value="">--Select Project--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    			
    		</div>
    		
    		<div class="form-group col-3">
    			
      		  <label for="date">Project Inception Date: </label>
      		  <input type="date" name="datein" id="datein" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
    		
    		<div class="form-group col-3">
    			
      		  <label for="date">Proposed Project Completion Date: </label>
      		  <input type="date" name="datecom" id="datecom" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
    		
    		</div>
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=2 class="text-left">A. Basic Information about Porject</th>
     	</tr>
     	<tr>
     		<td>Total No. of Gram Panchayat</td>
     		<td><input type="text" id="nogp" name="nogp" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Total No. of Villages</td>
     		<td><input type="text" id="novillage" name="novillage" autocomplete="off"
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
     		<th colspan=3 class="text-left">B. Preparatory Work</th>
     	</tr>
     	
     	<tr>
     		<td colspan=3>1. Identification & Engagement of NGOs:</td>
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
     			Name of Gram Panchyat to be Covered by NGO &nbsp; <select id="ddlgp" name="ddlgp" class="ddlgp form-control" multiple="multiple"><option value="">--Select Gram Panchayat--</option></select>
			</td>
			
			<td>
				Name of Villages to be Covered by NGO &nbsp; <select id="ddlvill" name="ddlvill" class="ddlvill form-control" multiple="multiple"><option value="">--Select Village--</option></select>
			</td>
     	</tr>
     	</tbody>
     	<tr>
     		<td colspan=3 align="left"><button id="btnAdd" name="btnAdd" class="btnAdd btn btn-info">Add New Row</button></td>
     	</tr>
     	
     	<tr>
     		<td colspan=3></td>
     	</tr>
     	
     	<tr>
     		<td colspan=3>2. Opening of SWC Kosh Bank Account at Gram Panchayat Level:</td>
     	</tr>
     	
     	
     	
     	<tr>
     		<td>2.1 Account is Opened in a Nationalized Bank</td>
     		<td><input type="radio" id="bankYes" name="bank" value="true" autocomplete="off"  />Yes <br/>
     		<input type="radio" id="bankNo" name="bank" value="false" autocomplete="off" />No </td>
     		
     	</tr>
     </table>
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=2 class="text-left">C. Fund Utilization under WDC-PMKSY2.0</th>
     	</tr>
     	<tr>
     		<td>Total Project Outlay</td>
     		<td><input type="text" id="funoutlay" name="funoutlay" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Total Project Expenditure</td>
     		<td><input type="text" id="projexp" name="projexp" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     	</tr>
     	
     	<tr>
     		<td>Percentage of Expenditure (%)</td>
     		<td><input type="text" id="expper" name="expper" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="5" required /></td>
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
								<th rowspan="2">Project Inception Date</th>
								<th rowspan="2">Proposed Project Completion Date</th>
								<th colspan="4">Basic Information about Porject</th>
								<th colspan="4">Preparatory Work</th>
								<th colspan="3">Fund Utilization under WDC-PMKSY2.0</th>
							</tr>
							
							<tr>
								<th>Total No. of Gram Panchayat</th>
								<th>Total No. of Villages</th>
								<th>Total area allocated for project (ha.)</th>
								<th>Total Project Outlay (Rs. In Lakh)</th>
								
								<th>Name of NGOs</th>
								<th>Gram Panchayat to be covered by NGO</th>
								<th>Villages to be covered by NGO</th>
								<th>Account is opened in a nationalized bank</th>
								
								<th>Total Project Outlay</th>
								<th>Total Project Expenditure</th>
								<th>Percentage of Expenditure (%)</th>
								
								
							</tr>
						</thead>
						<c:set var="id2" value="" />
						<c:set var="id1" value="" />
						<c:set var="id" value="" />
 						<c:set var="st" value="" />
 						<c:set var="incr" value="0" />
 						<c:set var="dist" value="" />
 						<c:set var="proj" value="" />
 						<c:set var="datein" value="" />
 						<c:set var="datecom" value="" />
 						<c:set var="datein" value="" />
 						
 						<c:forEach items="${dataList}" var="data" varStatus="count">
 							<tr>
								
								
 								<c:choose>
 									<c:when test="${id ne data.pratiyogita_id}">
 										<c:set var="incr" value="${incr+1}" />
 										<c:set var="id" value="${data.pratiyogita_id}" />
 										<td><c:out value="${incr}" /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.pratiyogita_id}"  name="${data.pratiyogita_id}" value="${data.pratiyogita_id}"/></td>
 											
 									</c:when>
 								<c:otherwise>
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
 								
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
 								
 								<c:choose>
 									<c:when test="${id1 ne data.pratiyogita_id}">
 										
 										<c:set var="id1" value="${data.pratiyogita_id}" />
 										<td class="text-left"> <c:out value="${data.datein}" /></td>
										<td class="text-left"> <c:out value="${data.datecom}" /></td>
		 								<td class="text-right"> <c:out value="${data.nogp}" /></td>
										<td class="text-right"> <c:out value="${data.novillage}" /></td>
		 								<td class="text-right"> <c:out value="${data.projarea}" /></td>
										<td class="text-right"> <c:out value="${data.projoutlay}" /></td>
 											
 									</c:when>
 								<c:otherwise>
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
								
								
								
 								<td class="text-right"> <c:out value="${data.ngo_name}" /></td>
 								<td class="text-right"> <c:out value="${data.gpname}" /></td>
 								<td class="text-right"> <c:out value="${data.villname}" /></td>
 								
 								<c:choose>
 									<c:when test="${id2 ne data.pratiyogita_id}">
 										
 										<c:set var="id2" value="${data.pratiyogita_id}" />
 										<td class="text-right"> <c:out value="${data.bank}" /></td>
								
 								<td class="text-right"> <c:out value="${data.funoutlay}" /></td>
								<td class="text-right"> <c:out value="${data.projexp}" /></td>
 								<td class="text-right"> <c:out value="${data.expper}" /></td>
 											
 									</c:when>
 								<c:otherwise>
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										
 								</c:otherwise>
 								</c:choose>
 								
 								
 								
								
 								
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
								<th rowspan="2">Project Inception Date</th>
								<th rowspan="2">Proposed Project Completion Date</th>
								<th colspan="4">Basic Information about Porject</th>
								<th colspan="4">Preparatory Work</th>
								<th colspan="3">Fund Utilization under WDC-PMKSY2.0</th>
							</tr>
							
							<tr>
								<th>Total No. of Gram Panchayat</th>
								<th>Total No. of Villages</th>
								<th>Total area allocated for project (ha.)</th>
								<th>Total Project Outlay (Rs. In Lakh)</th>
								
								<th>Name of NGOs</th>
								<th>Gram Panchayat to be covered by NGO</th>
								<th>Villages to be covered by NGO</th>
								<th>Account is opened in a nationalized bank</th>
								
								<th>Total Project Outlay</th>
								<th>Total Project Expenditure</th>
								<th>Percentage of Expenditure (%)</th>
								
								
							</tr>
						</thead>
						
 						<c:set var="cid2" value="" />
						<c:set var="cid1" value="" />
						<c:set var="cid" value="" />
 						<c:set var="cst" value="" />
 						<c:set var="cincr" value="0" />
 						<c:set var="cdist" value="" />
 						<c:set var="cproj" value="" />
 						<c:set var="cdatein" value="" />
 						<c:set var="cdatecom" value="" />
 						<c:set var="cdatein" value="" />
 						
 						<c:forEach items="${compdataList}" var="data" varStatus="count">
 							<tr>
								
								
 								<c:choose>
 									<c:when test="${cid ne data.pratiyogita_id}">
 										<c:set var="cincr" value="${cincr+1}" />
 										<c:set var="cid" value="${data.pratiyogita_id}" />
 										<td><c:out value="${cincr}" /> </td>
 											
 									</c:when>
 								<c:otherwise>
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
 								
 								<c:choose>
 									<c:when test="${cst ne data.stname}">
 										<c:set var="cst" value="${data.stname}" />
 										<td> <c:out value="${data.stname}" /></td>
 									</c:when>
 								<c:otherwise>
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
 								
 								<c:choose>
 									<c:when test="${cdist ne data.districtname}">
 										<c:set var="cdist" value="${data.districtname}" />
 										<td> <c:out value="${data.districtname}" /></td>
 									</c:when>
 								<c:otherwise>
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
 								
 								<c:choose>
 									<c:when test="${cproj ne data.projname}">
 										<c:set var="cproj" value="${data.projname}" />
 										<td> <c:out value="${data.projname}" /></td>
 									</c:when>
 								<c:otherwise>
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
 								
 								<c:choose>
 									<c:when test="${cid1 ne data.pratiyogita_id}">
 										
 										<c:set var="cid1" value="${data.pratiyogita_id}" />
 										<td class="text-left"> <c:out value="${data.datein}" /></td>
										<td class="text-left"> <c:out value="${data.datecom}" /></td>
		 								<td class="text-right"> <c:out value="${data.nogp}" /></td>
										<td class="text-right"> <c:out value="${data.novillage}" /></td>
		 								<td class="text-right"> <c:out value="${data.projarea}" /></td>
										<td class="text-right"> <c:out value="${data.projoutlay}" /></td>
 											
 									</c:when>
 								<c:otherwise>
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
 								</c:otherwise>
 								</c:choose>
								
								
								
 								<td class="text-right"> <c:out value="${data.ngo_name}" /></td>
 								<td class="text-right"> <c:out value="${data.gpname}" /></td>
 								<td class="text-right"> <c:out value="${data.villname}" /></td>
 								
 								<c:choose>
 									<c:when test="${cid2 ne data.pratiyogita_id}">
 										
 										<c:set var="cid2" value="${data.pratiyogita_id}" />
 										<td class="text-right"> <c:out value="${data.bank}" /></td>
								
 								<td class="text-right"> <c:out value="${data.funoutlay}" /></td>
								<td class="text-right"> <c:out value="${data.projexp}" /></td>
 								<td class="text-right"> <c:out value="${data.expper}" /></td>
 											
 									</c:when>
 								<c:otherwise>
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										<td></td> 
  										
 								</c:otherwise>
 								</c:choose>
 								
 								
 								
								
 								
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