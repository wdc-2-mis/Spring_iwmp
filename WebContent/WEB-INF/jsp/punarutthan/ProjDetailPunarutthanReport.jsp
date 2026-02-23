<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<title>Report WP1 - Project Detail wise Watershed Punarutthan Details</title>

<html>
<script type="text/javascript">
let currentIndex = 0;
let totalImages = 0;
let currentImages = [];
let imageList = [];

$(document).ready(function() {
    
    // Handle Plan Images Click
    $(document).on('click', '.showPlanImage', function(e) {
        e.preventDefault();
        
        // Get the comma-separated image paths from data attribute
        let imagePaths = $(this).data('paths');
        console.log("Plan Image Paths: ", imagePaths);
        
        if (!imagePaths || imagePaths.trim() === '') {
            alert('No images available');
            return;
        }
        
        // Split and clean the paths
        currentImages = imagePaths.split(',').map(path => path.trim()).filter(path => path !== '');
        
        if (currentImages.length === 0) {
            alert('No images available');
            return;
        }
        
     	// Store all image URLs for navigation
        imageList = [];
        for (let i = 0; i < currentImages.length; i++) {
            let filename = extractFilename(currentImages[i]);
            let imageUrl = getImageUrl(filename, 'plan');
            imageList.push(imageUrl);
        }
        
     	// If only one image, open directly in large popup
        if (currentImages.length === 1) {
        	openSingleImage(imageList[0]);
        } else {
            // Multiple images - show thumbnails in small popup
            displayImages(currentImages, 'plan', imageList);
        }
    });
    
    // Handle Implementation Images Click
    $(document).on('click', '.showImplImage', function(e) {
        e.preventDefault();
        
        // Get the comma-separated image paths from data attribute
        let imagePaths = $(this).data('paths');
        console.log("Implementation Image Paths: ", imagePaths);
        
        if (!imagePaths || imagePaths.trim() === '') {
            alert('No images available');
            return;
        }
        
        // Split and clean the paths
        currentImages = imagePaths.split(',').map(path => path.trim()).filter(path => path !== '');
        
        if (currentImages.length === 0) {
            alert('No images available');
            return;
        }
        
     	// Store all image URLs for navigation
        imageList = [];
        for (let i = 0; i < currentImages.length; i++) {
            let filename = extractFilename(currentImages[i]);
            let imageUrl = getImageUrl(filename, 'impl');
            imageList.push(imageUrl);
        }
        
     	// If only one image, open directly in large popup
        if (currentImages.length === 1) {
        	openSingleImage(imageList[0]);
        } else {
            // Multiple images - show thumbnails in small popup
            displayImages(currentImages, 'impl', imageList);
        }
    });
});

// Function to extract filename from full Windows path
function extractFilename(fullPath) {
    if (!fullPath) return '';
    
    // Handle both backslash and forward slash paths
    let filename = fullPath.split('\\').pop().split('/').pop();
    return filename;
}

// Function to get the correct image URL based on environment and type
function getImageUrl(filename, type) {
    let baseUrl = '';

    // Local development
    if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
        baseUrl = '${pageContext.request.contextPath}/resources/images/watershedyatra/';
    } 
    // Test environment
    else if (window.location.href.includes('/TEST/')) {
        if (type === 'plan') {
            baseUrl = 'https://wdcpmksy.dolr.gov.in/TEST/filepath/punarutthan/planing/';
        } else {
            baseUrl = 'https://wdcpmksy.dolr.gov.in/TEST/filepath/punarutthan/implementation/';
        }
    } 
    // Production environment
    else {
        if (type === 'plan') {
            baseUrl = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/punarutthan/planing/';
        } else {
            baseUrl = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/punarutthan/implementation/';
        }
    }

    return baseUrl + filename;
}

// Display images function (exactly like working report)
function displayImages(images, type, imageUrls) {
    // Create image list HTML
    let list = '<ul style="list-style: none; padding: 0; margin: 0; display: flex; flex-wrap: wrap; justify-content: center;">';
    
    for (let i = 0; i < images.length; i++) {

    	let imageUrl = imageUrls[i];
        
        list += '<li style="margin: 10px; list-style: none;">' +
                '<img src="' + imageUrl + '" alt="Image ' + (i+1) + '" ' +
                'style="width: 150px; height: 150px; object-fit: cover; cursor: pointer; border: 2px solid #ddd; border-radius: 5px;" ' +
                'onclick="openLargeImage(\'' + imageUrl + '\', ' + i + ', ' + images.length + ')" />' +
                '</li>';
    }
    list += '</ul>';
    
    document.getElementById('imageList').innerHTML = list;
    document.getElementById('imagePopup').style.display = 'block';
}

// Open large image function (exactly like working report)
function openLargeImage(imageSrc, index, total) {
	// Close the small popup first
    document.getElementById('imagePopup').style.display = 'none';
	
    document.getElementById('largeImage').src = imageSrc;
    document.getElementById('largeImagePopup').style.display = 'block';
    currentIndex = index;
    totalImages = total;
    
    // Show/hide navigation arrows
    document.getElementById('prevImage').style.display = total > 1 ? 'block' : 'none';
    document.getElementById('nextImage').style.display = total > 1 ? 'block' : 'none';
}

//Open large image directly (for single images)
function openSingleImage(imageSrc) {
    document.getElementById('largeImage').src = imageSrc;
    document.getElementById('largeImagePopup').style.display = 'block';
    currentIndex = 0;
    totalImages = 1;
    
    // Hide navigation arrows for single image
    document.getElementById('prevImage').style.display = 'none';
    document.getElementById('nextImage').style.display = 'none';
}

// Close popup functions
function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
}

function closeLargeImagePopup() {
    document.getElementById('largeImagePopup').style.display = 'none';
}

// Navigation functions
function showNextImage() {
    if (currentIndex < totalImages - 1) {
        currentIndex++;
        console.log("Moving to index:", currentIndex, "URL:", imageList[currentIndex]);
        document.getElementById('largeImage').src = imageList[currentIndex];
    }
}

function showPrevImage() {
    if (currentIndex > 0) {
        currentIndex--;
        console.log("Moving to index:", currentIndex, "URL:", imageList[currentIndex]);
        document.getElementById('largeImage').src = imageList[currentIndex];
    }
}


function downloadPDF(pcode, pName, dName, stName){
    document.getElementById("pcode").value=pcode;
    document.getElementById("pName").value=pName;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getProjDtlPunarutthan.action="downloadPDFProjDtlPunarutthanRpt";
    document.getProjDtlPunarutthan.method="post";
    document.getProjDtlPunarutthan.submit();
}

function exportExcel(pcode, pName, dName, stName){
	document.getElementById("pcode").value=pcode;
    document.getElementById("pName").value=pName;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getProjDtlPunarutthan.action="downloadExcelProjDtlPunarutthanRpt";
    document.getProjDtlPunarutthan.method="post";
    document.getProjDtlPunarutthan.submit();
}
</script>

<style>
#imagePopup {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1000;
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0,0,0,0.5);
    max-width: 90%;
    max-height: 90%;
    overflow: auto;
}

#imagePopup .popup-content {
    position: relative;
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 100%;
    max-width: 800px;
    border-radius: 10px;
}

#imagePopup .close {
    position: absolute;
    top: 10px;
    right: 10px;
    color: #aaa;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
    background: transparent;
    width: auto;
    height: auto;
    border-radius: 0;
    display: block;
    box-shadow: none;
    z-index: 1001;
}

#imagePopup .close:hover {
    color: black;
    text-decoration: none;
}

#imagePopup .image-container ul {
    list-style-type: none;
    padding: 30px;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    gap: 15px;
    margin: 0;
}

#imagePopup .image-container li {
    display: flex;
    justify-content: center;
    align-items: center;
    list-style: none;
}

#imagePopup .image-container img {
    width: 150px;
    height: 150px;
    object-fit: cover;
    border-radius: 5px;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
    cursor: pointer;
    transition: transform 0.2s;
    border: 2px solid #ddd;
}

#imagePopup .image-container img:hover {
    transform: scale(1.05);
    border-color: #007bff;
}

#largeImagePopup {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1100;
    padding: 20px;
    width: 90%;
    max-width: 1200px;
    border-radius: 10px;
}

.large-image-popup-content {
    background-color: #fefefe;
    width: 100%;
    height: auto;
    max-height: 90vh;
    box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    padding: 20px;
}

.large-image-popup-content .close {
    position: absolute;
    top: 10px;
    right: 10px;
    color: #aaa;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
    z-index: 1200;
    background: transparent;
    width: auto;
    height: auto;
    border-radius: 0;
    display: block;
    box-shadow: none;
}

.large-image-popup-content .close:hover {
    color: black;
    text-decoration: none;
}

#largeImage {
    width: 100%;
    height: auto;
    max-height: 80vh;
    object-fit: contain;
}

.nav-arrow {
    color: white;
    font-size: 50px;
    font-weight: bold;
    cursor: pointer;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    z-index: 1200;
    background: rgba(0, 0, 0, 0.3);
    padding: 10px 15px;
    text-decoration: none;
    user-select: none;
    transition: background-color 0.3s;
    width: auto;
    height: auto;
    border-radius: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: none;
}

#prevImage {
    left: 0;
    border-radius: 0 5px 5px 0;
}

#nextImage {
    right: 0;
    border-radius: 5px 0 0 5px;
}

.nav-arrow:hover {
    background: rgba(0, 0, 0, 0.8);
    color: white;
    text-decoration: none;
}

.image-link {
    color: blue;
    text-decoration: underline;
    cursor: pointer;
}

.image-link:hover {
    color: darkblue;
}

.no-image {
    color: gray;
    font-style: italic;
}

.image-container {
    text-align: center;
    min-height: 200px;
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
        <h5>Report WP1 - Project Detail wise Watershed Punarutthan Details</h5>
    </div>
    <br>
    <div class="card">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10">
            <br>
    <c:if test="${not empty punarutthanRptProjDtlDataList}">
        <button name="exportExcel" id="exportExcel" onclick="exportExcel('${pcode}', '${pName}', '${dName}', '${stName}')" class="btn btn-info">Excel</button>
        <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${pcode}', '${pName}', '${dName}', '${stName}')" class="btn btn-info">PDF</button>
    </c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelProjDtlPunarutthanRpt" name="getProjDtlPunarutthan" id="getProjDtlPunarutthan" method="post">
        
            <input type="hidden" name="pcode" id="pcode" value="" />
            <input type="hidden" name="pName" id="pName" value="" />
            <input type="hidden" name="dName" id="dName" value="" />
            <input type="hidden" name="stName" id="stName" value="" />
                    
            <table class="table" id="projDtlPunarutthan">
                <thead>
                    <tr>
                        <th colspan="14">State Name : ${stName}, &nbsp;&nbsp; District Name: ${dName}, &nbsp;&nbsp; Project Name: ${pName}</th>
                    </tr>
                    <tr>
                        <th rowspan="3" class="text-center">S.No.</th>
                        <th rowspan="3" class="text-center">Name of Structure</th>
                        <th colspan="6" class="text-center">Planning</th>
                        <th colspan="6" class="text-center">Implementation</th>
                    </tr>
                    <tr>
                        <th rowspan="2" class="text-center">Maintenance Work Planned</th>
                        <th colspan="4" class="text-center">Estimated Cost (Rs in Lakhs) for Maintenance Works</th>
                        <th rowspan="2" class="text-center">Planned Images</th>
                        <th rowspan="2" class="text-center">Maintenance Work Implemented</th>
                        <th colspan="4" class="text-center">Expenditure (Rs in Lakhs) for Maintenance Works</th>
                        <th rowspan="2" class="text-center">Implemented Images</th>
                    </tr>
                    <tr>
                        <th class="text-center">Total Estimated Cost</th>
                        <th class="text-center">Cost from WDF</th>
                        <th class="text-center">Cost from VB G RAM G/MGNREGA</th>
                        <th class="text-center">Cost from Other Source (if any)</th>
                        <th class="text-center">Total Expenditure (11+12+13)</th>
                        <th class="text-center">Expenditure from WDF</th>
                        <th class="text-center">Expenditure from VB G RAM G/MGNREGA</th>
                        <th class="text-center">Expenditure from Other Source <br> (if any)</th>
                    </tr>
                    <tr>
                        <th class="text-center">1</th>
                        <th class="text-center">2</th>
                        <th class="text-center">3</th>
                        <th class="text-center">4</th> 
                        <th class="text-center">5</th>
                        <th class="text-center">6</th>
                        <th class="text-center">7</th>
                        <th class="text-center">8</th>
                        <th class="text-center">9</th>
                        <th class="text-center">10</th>
                        <th class="text-center">11</th>
                        <th class="text-center">12</th>
                        <th class="text-center">13</th>
                        <th class="text-center">14</th>
                    </tr>
                </thead>
                <tbody id="tbodyProjPunarutthan">
                    <c:forEach items="${punarutthanRptProjDtlDataList}" var="dt" varStatus="sno">
                        <tr>
                            <td class="text-left"><c:out value="${sno.count}" /></td>
                            <td class="text-left"><c:out value="${dt.structurename}" /></td>
                            <td class="text-left"><c:out value="${dt.work}" /></td>
                            <td class="text-right"><c:out value="${dt.totalcost}" /></td>
                            <td class="text-right"><c:out value="${dt.wdf_cost}" /></td>
                            <td class="text-right"><c:out value="${dt.mgnrega_cost}" /></td>
                            <td class="text-right"><c:out value="${dt.other_cost}" /></td>
                            <td class="text-center">
                                <c:if test="${not empty dt.plan_image}">
                                        <a href="#" class="showPlanImage" data-paths="${dt.plan_image}" style="color: blue;">View</a>
                                </c:if>

                            </td>
                            <td class="text-left"><c:out value="${dt.no_work}" /></td>
                            <td class="text-right"><c:out value="${dt.totalexp}" /></td>
                            <td class="text-right"><c:out value="${dt.wdf_exp}" /></td>
                            <td class="text-right"><c:out value="${dt.mgnrega_exp}" /></td>
                            <td class="text-right"><c:out value="${dt.other_exp}" /></td>
                            <td class="text-center">
                                <c:if test="${not empty dt.imp_image}">
                                        <a href="#" class="showImplImage" data-paths="${dt.imp_image}" style="color: blue;">View</a>
                                </c:if>
                            </td>
                        </tr>
                        
                        <c:set var="totCost" value="${totCost + dt.totalcost}" />
						<c:set var="totWdfCost" value="${totWdfCost + dt.wdf_cost}" />
						<c:set var="totMgnregaCost" value="${totMgnregaCost + dt.mgnrega_cost}" />
						<c:set var="totOtherCost" value="${totOtherCost + dt.other_cost}" />
						<c:set var="totExp" value="${totExp + dt.totalexp}" />
						<c:set var="totWdfExp" value="${totWdfExp + dt.wdf_exp}" />
						<c:set var="totMgnregaExp" value="${totMgnregaExp + dt.mgnrega_exp}" />
						<c:set var="totOtherExp" value="${totOtherExp + dt.other_exp}" />
                    </c:forEach>
                    
                    <c:if test="${punarutthanRptProjDtlDataListSize>0}">
							<tr>
								<td colspan="3" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totWdfCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMgnregaCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOtherCost}" /></b></td>
								<td align="right" class="table-primary"></td>
								<td align="right" class="table-primary"></td>
								<td align="right" class="table-primary"><b><c:out value="${totExp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totWdfExp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMgnregaExp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOtherExp}" /></b></td>
								<td align="right" class="table-primary"></td>
							</tr>
					</c:if>
                    
                    <c:if test="${punarutthanRptProjDtlDataListSize==0}">
                        <tr>
                            <td align="center" colspan="14" class="required" style="color: red;"><b>Data Not Found</b></td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </form>
    </div>
    </div>
    </div>
</div>

<!-- Image Popup Modal -->
<div id="imagePopup" class="popup" style="display:none;">
    <div class="popup-content">
        <span class="close" onclick="closePopup()">&times;</span>
        <div id="imageList" class="image-container"></div>
    </div>
</div>

<!-- Large Image Popup -->
<div id="largeImagePopup" class="popup" style="display: none;">
    <div class="large-image-popup-content">
        <span class="close" onclick="closeLargeImagePopup()">&times;</span>
        <div class="nav-arrow" id="prevImage" onclick="showPrevImage()">&#10094;</div>
        <img id="largeImage" src="" alt="Large Image" />
        <div class="nav-arrow" id="nextImage" onclick="showNextImage()">&#10095;</div>
    </div>
</div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>