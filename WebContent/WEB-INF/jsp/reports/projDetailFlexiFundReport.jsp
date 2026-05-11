<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Report FF1 - Project Detail wise Watershed Flexi Fund Details</title>

<html>
<script type="text/javascript">

var currentImages = [];
var currentImageIndex = 0;


function showImages(photoUrls, activityName, workDesc, photoCount) {
    if (!photoUrls || photoUrls === '' || photoCount == 0) {
        alert('No images uploaded for this work');
        return;
    }
    
    var images = photoUrls.split(',');
    currentImages = images;
    currentImageIndex = 0;
    

    document.getElementById('popupActivityName').innerHTML = activityName;
    var shortDesc = workDesc.length > 100 ? workDesc.substring(0, 100) + '...' : workDesc;
    document.getElementById('popupWorkDesc').innerHTML = shortDesc;
    
    var imageContainer = document.getElementById('imageList');
    imageContainer.innerHTML = '';
    
    var ul = document.createElement('ul');
    
    for (var i = 0; i < images.length; i++) {
        var li = document.createElement('li');
        var img = document.createElement('img');
        img.src = 'getFlexiFundImage?name=' + encodeURIComponent(images[i]);
        img.alt = 'Image ' + (i + 1);
        img.title = 'Click to enlarge';
        img.onclick = (function(index) {
            return function() { 
                openLargeImageFromThumbnail(index);
            };
        })(i);
        li.appendChild(img);
        ul.appendChild(li);
    }
    
    imageContainer.appendChild(ul);
    document.getElementById('imagePopup').style.display = 'block';
}

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
}

function openLargeImageFromThumbnail(index) {
    if (!currentImages[index]) return;
    
    currentImageIndex = index;
    var largeModal = document.getElementById('largeImageModal');
    var largeImg = document.getElementById('largeImage');
    var imageCounter = document.getElementById('imageCounter');
    
    largeImg.src = 'getFlexiFundImage?name=' + encodeURIComponent(currentImages[index]);
    imageCounter.innerHTML = (index + 1) + ' / ' + currentImages.length;
    largeModal.style.display = 'block';
}

function closeLargeImage() {
    document.getElementById('largeImageModal').style.display = 'none';
}

function prevImage() {
    if (currentImageIndex > 0) {
        currentImageIndex--;
        var largeImg = document.getElementById('largeImage');
        var imageCounter = document.getElementById('imageCounter');
        
        largeImg.src = 'getFlexiFundImage?name=' + encodeURIComponent(currentImages[currentImageIndex]);
        imageCounter.innerHTML = (currentImageIndex + 1) + ' / ' + currentImages.length;
    }
}

function nextImage() {
    if (currentImageIndex < currentImages.length - 1) {
        currentImageIndex++;
        var largeImg = document.getElementById('largeImage');
        var imageCounter = document.getElementById('imageCounter');
        
        largeImg.src = 'getFlexiFundImage?name=' + encodeURIComponent(currentImages[currentImageIndex]);
        imageCounter.innerHTML = (currentImageIndex + 1) + ' / ' + currentImages.length;
    }
}


document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeLargeImage();
        closePopup();
    }
});


window.onclick = function(event) {
    var largeModal = document.getElementById('largeImageModal');
    var imagePopup = document.getElementById('imagePopup');
    if (event.target == largeModal) {
        closeLargeImage();
    }
    if (event.target == imagePopup) {
        closePopup();
    }
}


window.onclick = function(event) {
    var imagePopup = document.getElementById('imagePopup');
    if (event.target == imagePopup) {
        imagePopup.style.display = 'none';
    }
}


//Function to show expenditure history popup
function showExpenditureHistory(ffId, activityName, workDesc) {
    if (!ffId || ffId == 0) {
        alert('No expenditure record found');
        return;
    }
    
    // Show modal with loading
    $('#expenditureModal').modal('show');
    $('#expenditureModal #popupreporttitle').html('List of Expenditure for ' + activityName);
    
    // Show loading in modal body
    $('#expenditureModal .modal-body').html('<div class="text-center"><i class="fa fa-spinner fa-spin fa-2x"></i><br/>Loading...</div>');
    
    // Fetch expenditure history via AJAX
    $.ajax({
        url: 'getExpenditureHistory',
        type: 'POST',
        data: { flexiFundId: ffId },
        dataType: 'json',
        success: function(data) {
            if (data && data.length > 0) {
                displayExpenditureInModal(data, activityName, workDesc);
            } else {
                $('#expenditureModal .modal-body').html('<div class="text-center" style="color: red; padding: 20px;">No expenditure records found</div>');
            }
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
            $('#expenditureModal .modal-body').html('<div class="text-center" style="color: red; padding: 20px;">Error fetching expenditure history</div>');
        }
    });
}

// Function to display expenditure data in modal - Matching reference report style
function displayExpenditureInModal(data, activityName, workDesc) {
    var totalAmount = 0;
    var tblData = "";
    
    // Get the status from first record (all entries have same status for this activity)
    var overallStatus = data.length > 0 ? (data[0].exp_status === 'C' ? 'Completed' : 'Ongoing') : '-';
    
    for (var i = 0; i < data.length; i++) {
        var exp = data[i];
        var amount = parseFloat(exp.exp_cost) || 0;
        totalAmount += amount;
        
        tblData += "<tr>";
        tblData += "<td class='text-center'>" + (i + 1) + "</td>";
        tblData += "<td class='text-center'>" + (exp.expdate || '-') + "</td>";
        tblData += "<td class='text-right'>" + amount.toFixed(2) + "</td>";
        tblData += "</tr>";
    }
    
    // Add total row
    tblData += "<tr class='table-primary'>";
    tblData += "<td colspan='2' align='right'><strong>Total Expenditure</strong></td>";
    tblData += "<td class='text-right'><strong>" + totalAmount.toFixed(2) + "</strong></td>";
    tblData += "</tr>";
    
    var tableHtml = '<form id="frmExpenditureHistory">' +
        '<center>' +
        '<div style="margin-bottom: 15px; text-align: left;">' +
        '<strong>Work Description:</strong> ' + (workDesc || '-') + '<br/>' +
        '<strong>Status:</strong> <span class="badge ' + (overallStatus === 'Completed' ? 'badge-success' : 'badge-warning') + '">' + overallStatus + '</span>' +
        '</div>' +
        '<table style="width:100%">' +
        '<thead class="bg-primary text-white">' +
        '<tr>' +
        '<th class="text-center">S.No.</th>' +
        '<th class="text-center">Expenditure Date</th>' +
        '<th class="text-center">Expenditure Cost (Rs in Lakhs)</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' + tblData + '</tbody>' +
        '</table>' +
        '</center>' +
        '</form>';
    
    $('#expenditureModal .modal-body').html(tableHtml);
    $('#expenditureModal .modal-footer').html('<button type="button" id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
}

function closeExpenditureModal() {
    $('#expenditureModal').modal('hide');
}

function downloadPDF(pcode, pName, dcode, dName, stName){
	document.getElementById("pcode").value=pcode;
    document.getElementById("pName").value=pName;
    document.getElementById("dcode").value=dcode;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getElementById('ffForm').action = "downloadPDFProjDtlFlexiFundRpt";
    document.getElementById('ffForm').method = "post";
    document.getElementById('ffForm').submit();
}

function exportExcel(pcode, pName, dcode, dName, stName){
	document.getElementById("pcode").value=pcode;
    document.getElementById("pName").value=pName;
    document.getElementById("dcode").value=dcode;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getElementById('ffForm').action = "downloadExcelProjDtlFlexiFundRpt";
    document.getElementById('ffForm').method = "post";
    document.getElementById('ffForm').submit();
}
</script>

<style>
/* Popup container */
#imagePopup {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1000;
    background: white;
    padding: 0;
    width: 550px;
    max-width: 90%;
    border-radius: 10px;
    box-shadow: 0 5px 25px rgba(0, 0, 0, 0.3);
    overflow: hidden;
}

/* Popup content wrapper */
.popup-content {
    background-color: #ffffff;
    padding: 0;
    width: 100%;
    border-radius: 10px;
    position: relative;
}

/* Close button - elegant gray */
.close {
    color: #888;
    float: right;
    font-size: 28px;
    font-weight: bold;
    position: absolute;
    right: 15px;
    top: 10px;
    z-index: 10;
    cursor: pointer;
}

.close:hover,
.close:focus {
    color: #333;
    text-decoration: none;
}

/* Header section for activity name */
.popup-header-section {
    background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%); /* #007bff */
    padding: 15px 20px;
    border-bottom: 1px solid #e0e0e0;
}

.popup-header-section h4 {
    margin: 0;
    font-size: 18px;
    color: white;
    font-weight: 600;
    padding-right: 30px;
}

.popup-header-section p {
    margin: 5px 0 0 0;
    font-size: 13px;
    color: #e0e0e0;
    word-wrap: break-word;
}

/* Image container - fixed grid layout */
.image-container {
    padding: 20px;
    max-height: 400px;
    overflow-y: auto;
}

.image-container ul {
    list-style-type: none;
    padding: 0;
    margin: 0;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 15px;
}

.image-container li {
    display: flex;
    justify-content: center;
    align-items: center;
}

.image-container img {
    width: 100%;
    height: 100px;
    object-fit: cover;
    border-radius: 6px;
    border: 1px solid #ddd;
    cursor: pointer;
    transition: transform 0.2s;
}

.image-container img:hover {
    transform: scale(1.05);
    border-color: #007bff;
}

/* Scrollbar styling */
.image-container::-webkit-scrollbar {
    width: 6px;
}

.image-container::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 3px;
}

.image-container::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 3px;
}

.work-link {
    color: #0066cc;
    text-decoration: underline;
    cursor: pointer;
}

.work-link:hover {
    color: #ff6600;
}

/* Large image modal */
.large-image-modal {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.9);
    z-index: 2000;
}

.large-image-content {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    max-width: 90%;
    max-height: 90%;
    text-align: center;
}

.large-image-content img {
    max-width: 100%;
    max-height: 85vh;
    border: 3px solid white;
    border-radius: 5px;
}

.close-large {
    position: absolute;
    top: -50px;
    right: -50px;
    color: white;
    font-size: 40px;
    cursor: pointer;
}

.nav-arrow {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    color: white;
    font-size: 50px;
    cursor: pointer;
    background: rgba(0,0,0,0.5);
    padding: 10px 20px;
    border-radius: 50%;
}

.prev-arrow {
    left: -70px;
}

.next-arrow {
    right: -70px;
}


/* Expenditure History Popup Styles */
.badge {
    display: inline-block;
    padding: 4px 10px;
    font-size: 11px;
    font-weight: 600;
    border-radius: 10px;
    text-align: center;
}

.badge-success {
    background-color: #28a745;
    color: white;
}

.badge-warning {
    background-color: #ffc107;
    color: #333;
}

.badge-secondary {
    background-color: #6c757d;
    color: white;
}

.modal-header {
    background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
    color: white;
    padding: 15px;
}

.modal-header .close {
    color: #888;
    float: right;
    font-size: 28px;
    font-weight: bold;
    position: absolute;
    right: 15px;
    top: 10px;
    z-index: 10;
    cursor: pointer;
    outline: none;
    box-shadow: none;
}

.modal-header .close:hover,
.modal-header .close:focus {
    color: #333;
    text-decoration: none;
    outline: none;
    box-shadow: none;
}

.modal-body {
    padding: 20px;
    max-height: 500px;
    overflow-y: auto;
}

.modal-body table {
    width: 100%;
    border-collapse: collapse;
}

.modal-body table th,
.modal-body table td {
    padding: 10px;
    border: 1px solid #dee2e6;
}

.modal-body table thead th {
    background-color: #007bff;
    color: white;
    position: sticky;
    top: 0;
}

.modal-footer {
    padding: 15px;
    border-top: 1px solid #dee2e6;
}

</style>

<head>
<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>
</head>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h5>Report FF1 - Project Detail wise Watershed Flexi Fund Details</h5>
    </div>
    <br>
    <div class="container-fluid">
	<div class="row">
		<div class="col text-left">
                <br>
                <c:if test="${not empty flexiFundProjDtlReportList}">
                    <button name="exportExcel" id="exportExcel" onclick="exportExcel('${pcode}', '${pName}', '${dcode}', '${dName}', '${stName}')" class="btn btn-info">Excel</button>
                    <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${pcode}', '${pName}', '${dcode}', '${dName}', '${stName}')" class="btn btn-info">PDF</button>
                </c:if>
                <p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
   		</div>
   	</div>
   	</div>         
                <form action="downloadExcelProjDtlFlexiFundRpt" name="ffForm" id="ffForm" method="post">
                
                <input type="hidden" name="pcode" id="pcode" value="" />
            	<input type="hidden" name="pName" id="pName" value="" />
            	<input type="hidden" name="dcode" id="dcode" value="" />
            	<input type="hidden" name="dName" id="dName" value="" />
            	<input type="hidden" name="stName" id="stName" value="" />
                
                    <table class="table table-bordered" id="ffTable">
                        <thead>
                        <tr>
                        	<th colspan="11" class="text-left">State Name : ${stName}, &nbsp;&nbsp; District Name: ${dName}, &nbsp;&nbsp; Project Name: ${pName}</th>
                        	<th colspan="9" class="text-right">All Amount is Rs in Lakhs</th>
                        </tr>
                            <tr class="bg-primary text-white">
                                <th rowspan="2" class="text-center">S.No.</th>
                                <th rowspan="2" class="text-center">Gram Panchayat Name</th>
                                <th colspan="3" class="text-center">Cactus</th> 
                                <th colspan="3" class="text-center">Spring shed PPR Preparation</th>
                                <th colspan="3" class="text-center">Model Watershed</th>
                                <th colspan="3" class="text-center">Restoration of Waterbodies</th>
                                <th colspan="3" class="text-center">Watershed Janbhagidari Cup (Prize Money)</th>
                                
                                <th rowspan="2" class="text-center">Total Estimated Cost (4+7+10+13+16)</th>
                                <th rowspan="2" class="text-center">Total Expenditure Cost (5+8+11+14+17)</th>
                            </tr>
                            <tr>
                            	<% for(int i = 1; i <= 5; i++) {  %>
            						<th class="text-center">Work</th>
            						<th class="text-center">Estimated Cost</th>
            						<th class="text-center">Expenditure Cost</th>
            					<% } %>
                            </tr>
                            
                            <tr>
                            	<% for(int i = 1; i <= 19; i++) {  %>
            						<th class="text-center"><%= i %></th>
            					<% } %>
                            </tr>
                        </thead>
                        <tbody>
                        
                        <c:set var="gpName" value="" />
                        
                            <c:forEach items="${flexiFundProjDtlReportList}" var="dt" varStatus="sno">
                      <tr>
                          <td class="text-center"><c:out value="${sno.count}" /></td>
<%--                                     <td class="text-left"><c:out value="${dt.gp_name}" /></td> --%>

							<c:choose>
								<c:when test="${dt.gp_name != gpName}">
									<td class="text-left"><c:out value="${dt.gp_name}" /></td>
									<c:set var="gpName" value="${dt.gp_name}" />
								</c:when>
								<c:otherwise>
									<td class="text-left"></td>
								</c:otherwise>
							</c:choose>
							
							<!-- Cactus -->
							
							<td class="text-left">
								<c:choose>
									<c:when test="${dt.cactus > 0}">
										<a href="javascript:void(0)" class="work-link" 
                                       onclick="showImages('${dt.cactus_photos}', 'Cactus', '${dt.cactus_desc}', ${dt.cactus})">
                                        <c:out value="${dt.cactus_desc}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.cactus_desc}" />
                                	</c:otherwise>
								</c:choose>
							</td>
							
<%-- 									<td class="text-left"><c:out value="${dt.cactus_desc}" /></td> --%>
                            <td class="text-right"><c:out value="${dt.cactus_est}" /></td>
<%--                             <td class="text-right"><c:out value="${dt.cactus_exp}" /></td> --%>
                            
                            <td class="text-right">
								<c:choose>
									<c:when test="${dt.cactus_exp > 0 && dt.cactus_id > 0}">
										<a href="javascript:void(0)" class="expenditure-link" 
                                       onclick="showExpenditureHistory('${dt.cactus_id}', 'Cactus', '${dt.cactus_desc}')">
                                        <c:out value="${dt.cactus_exp}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.cactus_exp}" />
                                	</c:otherwise>
								</c:choose>
							</td> 
                                    
                             <!-- Springshed -->
                             
                             <td class="text-left">
								<c:choose>
									<c:when test="${dt.springshed > 0}">
										<a href="javascript:void(0)" class="work-link" 
                                       onclick="showImages('${dt.springshed_photos}', 'Spring shed PPR Preparation', '${dt.springshed_desc}', ${dt.springshed})">
                                        <c:out value="${dt.springshed_desc}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.springshed_desc}" />
                                	</c:otherwise>
								</c:choose>
							</td>
							 
<%--                                     <td class="text-left"><c:out value="${dt.springshed_desc}" /></td> --%>
                            <td class="text-right"><c:out value="${dt.springshed_est}" /></td>
<%--                             <td class="text-right"><c:out value="${dt.springshed_exp}" /></td> --%>
                            
                            <td class="text-right">
								<c:choose>
									<c:when test="${dt.springshed_exp > 0 && dt.springshed_id > 0}">
										<a href="javascript:void(0)" class="expenditure-link" 
                                       onclick="showExpenditureHistory('${dt.springshed_id}', 'Spring shed PPR Preparation', '${dt.springshed_desc}')">
                                        <c:out value="${dt.springshed_exp}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.springshed_exp}" />
                                	</c:otherwise>
								</c:choose>
							</td> 
                                   
                            <!-- Watershed -->
                            
                            <td class="text-left">
								<c:choose>
									<c:when test="${dt.watershed > 0}">
										<a href="javascript:void(0)" class="work-link" 
                                       onclick="showImages('${dt.watershed_photos}', 'Model Watershed', '${dt.watershed_desc}', ${dt.watershed})">
                                        <c:out value="${dt.watershed_desc}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.watershed_desc}" />
                                	</c:otherwise>
								</c:choose>
							</td>      
                                    
<%--                                     <td class="text-left"><c:out value="${dt.watershed_desc}" /></td> --%>
                            <td class="text-right"><c:out value="${dt.watershed_est}" /></td>
<%--                             <td class="text-right"><c:out value="${dt.watershed_exp}" /></td> --%>
                            
                            <td class="text-right">
								<c:choose>
									<c:when test="${dt.watershed_exp > 0 && dt.watershed_id > 0}">
										<a href="javascript:void(0)" class="expenditure-link" 
                                       onclick="showExpenditureHistory('${dt.watershed_id}', 'Model Watershed', '${dt.watershed_desc}')">
                                        <c:out value="${dt.watershed_exp}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.watershed_exp}" />
                                	</c:otherwise>
								</c:choose>
							</td> 
                                  
                            <!-- Waterbodies -->
                            
                            <td class="text-left">
								<c:choose>
									<c:when test="${dt.waterbodies > 0}">
										<a href="javascript:void(0)" class="work-link" 
                                       onclick="showImages('${dt.waterbodies_photos}', 'Restoration of Waterbodies', '${dt.waterbodies_desc}', ${dt.waterbodies})">
                                        <c:out value="${dt.waterbodies_desc}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.waterbodies_desc}" />
                                	</c:otherwise>
								</c:choose>
							</td>      
                                    
<%--                                     <td class="text-left"><c:out value="${dt.waterbodies_desc}" /></td> --%>
                           <td class="text-right"><c:out value="${dt.waterbodies_est}" /></td>
<%--                            <td class="text-right"><c:out value="${dt.waterbodies_exp}" /></td> --%>
                           
                           <td class="text-right">
								<c:choose>
									<c:when test="${dt.waterbodies_exp > 0 && dt.waterbodies_id > 0}">
										<a href="javascript:void(0)" class="expenditure-link" 
                                       onclick="showExpenditureHistory('${dt.waterbodies_id}', 'Restoration of Waterbodies', '${dt.waterbodies_desc}')">
                                        <c:out value="${dt.waterbodies_exp}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.waterbodies_exp}" />
                                	</c:otherwise>
								</c:choose>
							</td>
                                    
                           <!-- Janbhagidari -->
                           
                           <td class="text-left">
								<c:choose>
									<c:when test="${dt.janbhagidari > 0}">
										<a href="javascript:void(0)" class="work-link" 
                                       onclick="showImages('${dt.janbhagidari_photos}', 'Watershed Janbhagidari Cup', '${dt.janbhagidari_desc}', ${dt.janbhagidari})">
                                        <c:out value="${dt.janbhagidari_desc}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.janbhagidari_desc}" />
                                	</c:otherwise>
								</c:choose>
							</td>         
                                    
<%--                                     <td class="text-left"><c:out value="${dt.janbhagidari_desc}" /></td> --%>
                           <td class="text-right"><c:out value="${dt.janbhagidari_est}" /></td>
<%--                            <td class="text-right"><c:out value="${dt.janbhagidari_exp}" /></td> --%>
                           
                           <td class="text-right">
								<c:choose>
									<c:when test="${dt.janbhagidari_exp > 0 && dt.janbhagidari_id > 0}">
										<a href="javascript:void(0)" class="expenditure-link" 
                                       onclick="showExpenditureHistory('${dt.janbhagidari_id}', 'Watershed Janbhagidari Cup', '${dt.janbhagidari_desc}')">
                                        <c:out value="${dt.janbhagidari_exp}" />
                                    </a>
									</c:when>
									<c:otherwise>
                                    	<c:out value="${dt.janbhagidari_exp}" />
                                	</c:otherwise>
								</c:choose>
							</td>
                            
                           <td class="text-right"><c:out value="${dt.total_estimation}" /></td>
                           <td class="text-right"><c:out value="${dt.total_expenditure}" /></td>
                    </tr>
                                
                                <c:set var="totCactusEst" value="${totCactusEst + dt.cactus_est}" />
                                <c:set var="totCactusExp" value="${totCactusExp + dt.cactus_exp}" />
                                <c:set var="totSpringshedEst" value="${totSpringshedEst + dt.springshed_est}" />
                                <c:set var="totSpringshedExp" value="${totSpringshedExp + dt.springshed_exp}" />
                                <c:set var="totWatershedEst" value="${totWatershedEst + dt.watershed_est}" />
                                <c:set var="totWatershedExp" value="${totWatershedExp + dt.watershed_exp}" />
                                <c:set var="totWaterbodiesEst" value="${totWaterbodiesEst + dt.waterbodies_est}" />
                                <c:set var="totWaterbodiesExp" value="${totWaterbodiesExp + dt.waterbodies_exp}" />
                                <c:set var="totJanbhagidariEst" value="${totJanbhagidariEst + dt.janbhagidari_est}" />
                                <c:set var="totJanbhagidariExp" value="${totJanbhagidariExp + dt.janbhagidari_exp}" />
                                <c:set var="totEstimation" value="${totEstimation + dt.total_estimation}" />
                                <c:set var="totExpenditure" value="${totExpenditure + dt.total_expenditure}" />
                                
                            </c:forEach>
                            
                            
                            <c:if test="${flexiFundProjDtlReportListSize > 0}">
                                <tr class="table-primary">
                                    <td colspan="2" align="right"><b>Grand Total</b></td>
                                    <td align="right"></td>
                                    <td align="right"><b><c:out value="${totCactusEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totCactusExp}" /></b></td>
                                    <td align="right"></td>
                                    <td align="right"><b><c:out value="${totSpringshedEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totSpringshedExp}" /></b></td>
                                    <td align="right"></td>
                                    <td align="right"><b><c:out value="${totWatershedEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totWatershedExp}" /></b></td>
                                    <td align="right"></td>
                                    <td align="right"><b><c:out value="${totWaterbodiesEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totWaterbodiesExp}" /></b></td>
                                    <td align="right"></td>
                                    <td align="right"><b><c:out value="${totJanbhagidariEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totJanbhagidariExp}" /></b></td>
                                    <td align="right"><b><c:out value="${totEstimation}" /></b></td>
                                    <td align="right"><b><c:out value="${totExpenditure}" /></b></td>
                                </tr>
                            </c:if>
                            
                            
                            <c:if test="${flexiFundProjDtlReportListSize == 0}">
                                <tr>
                                    <td align="center" colspan="19" class="required" style="color: red;"><b>Data Not Found</b></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </form>
            </div>

<!-- Image Popup Modal -->
<div id="imagePopup" class="popup" style="display:none;">
    <div class="popup-content">
        <span class="close" onclick="closePopup()">&times;</span>
        <div class="popup-header-section">
            <h4 id="popupActivityName"></h4>
            <p id="popupWorkDesc"></p>
        </div>
        <div id="imageList" class="image-container"></div>
    </div>
</div>

<!-- Large Image Modal -->
<div id="largeImageModal" class="large-image-modal">
    <div class="large-image-content"> 
        <span class="close-large" onclick="closeLargeImage()">&times;</span>
        <div class="nav-arrow prev-arrow" onclick="prevImage()">&#10094;</div>
        <img id="largeImage" src="" alt="Large Image" />
        <div class="nav-arrow next-arrow" onclick="nextImage()">&#10095;</div>
        <div style="position: absolute; bottom: -40px; left: 50%; transform: translateX(-50%); color: white; font-size: 14px;" id="imageCounter"></div>
    </div>
</div>

<!-- Expenditure History Model -->
<div class="modal fade" id="expenditureModal" tabindex="-1" role="dialog" aria-labelledby="expenditureModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="popupreporttitle">List of Expenditure</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <i class="fa fa-spinner fa-spin fa-2x"></i>
                    <br/>Loading...
                </div>
            </div>
<!--             <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>  -->
        </div>
    </div>
</div>



<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>