<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE6-  State and District-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops</title>

<script type="text/javascript">

function filterData(selectedValue) {
	document.getElementById("radioBtn").value = selectedValue;
	document.getElementById("excelpdf").style.display = "block";
    // Parse JSON safely
    var croppedData = JSON.parse('${mapJson}'.replace(/&quot;/g, '"'));
    
    // Get the tbody using jQuery
    let tbody = $('#croppedDetailsRptTbody');
    tbody.empty(); // Clear existing rows
    
    let totalProj = 0, totalCereals = 0, totalPulses = 0, totalOilseed = 0, totalMillets = 0, 
    totalOther = 0, totalRice = 0, totalWheat = 0, totalPulsesMain = 0, 
    totalOilseedMain = 0, totalMilletsMain = 0, totalOtherMain = 0;

    if (croppedData[selectedValue]) {
        croppedData[selectedValue].forEach((item, index) => {
        	
        	// Accumulate totals
            totalProj += item.totproj || 0;
            totalCereals += parseFloat(item.cropcereals) || 0;
            totalPulses += parseFloat(item.croppulses) || 0;
            totalOilseed += parseFloat(item.cropoilseed) || 0;
            totalMillets += parseFloat(item.cropmillets) || 0;
            totalOther += parseFloat(item.cropother) || 0;
            totalRice += parseFloat(item.rice) || 0;
            totalWheat += parseFloat(item.wheat) || 0;
            totalPulsesMain += parseFloat(item.pulses) || 0;
            totalOilseedMain += parseFloat(item.oilseed) || 0;
            totalMilletsMain += parseFloat(item.millets) || 0;
            totalOtherMain += parseFloat(item.other) || 0;
        	
            tbody.append(`<tr>
            	<td>`+(index+1)+`</td>
                <td><a href="#" onclick="cropDetails('`+item.stcode+`','`+item.stname+`')">`+item.stname+`</a></td>
                <td>`+item.totproj+`</td>
                <td>`+parseFloat(item.cropcereals).toFixed(4)+`</td>
                <td>`+parseFloat(item.croppulses).toFixed(4)+`</td>
                <td>`+parseFloat(item.cropoilseed).toFixed(4)+`</td>
                <td>`+parseFloat(item.cropmillets).toFixed(4)+`</td>
                <td>`+parseFloat(item.cropother).toFixed(4)+`</td>
                <td>`+parseFloat(item.rice).toFixed(4)+`</td>
                <td>`+parseFloat(item.wheat).toFixed(4)+`</td>
                <td>`+parseFloat(item.pulses).toFixed(4)+`</td>
                <td>`+parseFloat(item.oilseed).toFixed(4)+`</td>
                <td>`+parseFloat(item.millets).toFixed(4)+`</td>
                <td>`+parseFloat(item.other).toFixed(4)+`</td>
            </tr>`);
        });
        
        tbody.append(`<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
                <td colspan="2">Total</td>
                <td>`+totalProj+`</td>
                <td>`+totalCereals.toFixed(4)+`</td>
                <td>`+totalPulses.toFixed(4)+`</td>
                <td>`+totalOilseed.toFixed(4)+`</td>
                <td>`+totalMillets.toFixed(4)+`</td>
                <td>`+totalOther.toFixed(4)+`</td>
                <td>`+totalRice.toFixed(4)+`</td>
                <td>`+totalWheat.toFixed(4)+`</td>
                <td>`+totalPulsesMain.toFixed(4)+`</td>
                <td>`+totalOilseedMain.toFixed(4)+`</td>
                <td>`+totalMilletsMain.toFixed(4)+`</td>
                <td>`+totalOtherMain.toFixed(4)+`</td>
            </tr>`);
    }
}

function cropDetails(stcode, stname){
	document.getElementById("stcode").value = stcode;
	document.getElementById("stname").value = stname;
	document.getCrpDtl.action="getDistwiseCroppedDetailsReportData";
	document.getCrpDtl.method="post";
	document.getCrpDtl.submit();
	
}

function downloadPDF(){
		var radiobutton = document.querySelector('input[name="reportType"]:checked').value;
		document.getElementById("radioBtn").value=radiobutton;
		document.getCrpDtl.action="downloadCroppedDetailsReportPdf";
		document.getCrpDtl.method="post";
		document.getCrpDtl.submit();
}

function exportExcel(){
		var radiobutton = document.querySelector('input[name="reportType"]:checked').value;
		document.getElementById("radioBtn").value=radiobutton;
		document.getCrpDtl.action="downloadExcelCroppedDetailsReport";
		document.getCrpDtl.method="post";
		document.getCrpDtl.submit();
}

function downloadDPDF(stcode, stname, radioBtn){
	document.getElementById("stcode").value = stcode;
	document.getElementById("stname").value = stname;
	document.getElementById("radioBtn").value=radioBtn;
	document.getCrpDtl.action="downloadDistwiseCroppedDetailsReportPdf";
	document.getCrpDtl.method="post";
	document.getCrpDtl.submit();
}

function exportDExcel(stcode, stname, radioBtn){
	document.getElementById("stcode").value = stcode;
	document.getElementById("stname").value = stname;
	document.getElementById("radioBtn").value=radioBtn;
	document.getCrpDtl.action="downloadDistwiseExcelCroppedDetailsReport";
	document.getCrpDtl.method="post";
	document.getCrpDtl.submit();
}
	
</script>


<body>
<c:if test ="${mapJsonSize > 0}">
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE6- State-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getCroppedDetails" name="getCrpDtl" id="getCrpDtl" method="get">
		<input type="radio" name="reportType" value="pre" onclick="filterData('pre')"> Pre
    	<input type="radio" name="reportType" value="mid" onclick="filterData('mid')"> Mid
    	<input type="radio" name="reportType" value="control" onclick="filterData('control')"> Control
    	<input type="hidden" name="radioBtn" id="radioBtn" value="" />
    	<input type="hidden" name="stcode" id="stcode" value="" />
    	<input type="hidden" name="stname" id="stname" value="" />
 	</form:form>
 
<br>
	<div id ="excelpdf" style="display: none;">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="2">S.No.</th>
            <th rowspan="2">State Name</th>
            <th rowspan="2">Total No. of Project</th>
            <th colspan="5" style="text-align: center">Area Under Different Crops(in ha.)</th>
            <th colspan="6" style="text-align: center">Yield per Hectare of Major Crops</th>
        </tr>
        <tr>
            <th>Cereals</th>
            <th>Pulses</th>
            <th>Oil Seeds</th>
            <th>Millet</th>
            <th>Other</th>
            
            <th>Rice</th>
            <th>Wheat</th>
            <th>Pulses</th>
            <th>Millet</th>
            <th>Oil Seeds</th>
            <th>Other</th>
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
		<tbody id = "croppedDetailsRptTbody">
			
			
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>

</c:if>

<c:if test ="${distListSize > 0}">
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE6 - District-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops 
			for <c:out value="${status}"/> of <c:out value="${stname}"/></label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getCroppedDetails" name="getCrpDtl" id="getCrpDtl" method="get">
    	<input type="hidden" name="radioBtn" id="radioBtn" value="" />
    	<input type="hidden" name="stcode" id="stcode" value="" />
    	<input type="hidden" name="stname" id="stname" value="" />
 	</form:form>
 
<br>
	<div id ="excelDpdf">
	<button name="exportDExcel" id="exportDExcel" onclick="exportDExcel('${stcode}','${stname}','${radioBtn}')" class="btn btn-info">Excel</button>
	<button name="exportDPDF" id="exportDPDF" onclick="downloadDPDF('${stcode}','${stname}','${radioBtn}')" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="2">S.No.</th>
            <th rowspan="2">District Name</th>
            <th rowspan="2">Total No. of Project</th>
            <th colspan="5" style="text-align: center">Area Under Different Crops(in ha.)</th>
            <th colspan="6" style="text-align: center">Yield per Hectare of Major Crops</th>
        </tr>
        <tr>
            <th>Cereals</th>
            <th>Pulses</th>
            <th>Oil Seeds</th>
            <th>Millet</th>
            <th>Other</th>
            
            <th>Rice</th>
            <th>Wheat</th>
            <th>Pulses</th>
            <th>Millet</th>
            <th>Oil Seeds</th>
            <th>Other</th>
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
		<tbody id = "distCroppedDetailsRptTbody">
			
			<c:set var = "totalProj" value ="0"/>
			<c:set var = "totalCereals" value ="0"/>
			<c:set var = "totalPulses" value ="0"/>
			<c:set var = "totalOilseed" value ="0"/>
			<c:set var = "totalMillets" value ="0"/>
			<c:set var = "totalOther" value ="0"/>
			<c:set var = "totalRice" value ="0"/>
			<c:set var = "totalWheat" value ="0"/>
			<c:set var = "totalPulsesMain" value ="0"/>
			<c:set var = "totalOilseedMain" value ="0"/>
			<c:set var = "totalMilletsMain" value ="0"/>
			<c:set var = "totalOtherMain" value ="0"/>
			
			<c:forEach items="${distList}" var= "item" varStatus="status">
				<c:set var = "totalProj" value ="${totalProj + item.totproj}"/>
				<c:set var = "totalCereals" value ="${totalCereals + item.cropcereals}"/>
				<c:set var = "totalPulses" value ="${totalPulses + item.croppulses}"/>
				<c:set var = "totalOilseed" value ="${totalOilseed + item.cropoilseed}"/>
				<c:set var = "totalMillets" value ="${totalMillets + item.cropmillets}"/>
				<c:set var = "totalOther" value ="${totalOther + item.cropother}"/>
				<c:set var = "totalRice" value ="${totalRice + item.rice}"/>
				<c:set var = "totalWheat" value ="${totalWheat + item.wheat}"/>
				<c:set var = "totalPulsesMain" value ="${totalPulsesMain + item.pulses}"/>
				<c:set var = "totalOilseedMain" value ="${totalOilseedMain + item.oilseed}"/>
				<c:set var = "totalMilletsMain" value ="${totalMilletsMain + item.millets}"/>
				<c:set var = "totalOtherMain" value ="${totalOtherMain + item.other}"/>
				
			<tr>
            	<td>${status.count}</td>
                <td>${item.distname}</td>
                <td>${item.totproj}</td>
                <td>${item.cropcereals}</td>
                <td>${item.croppulses}</td>
                <td>${item.cropoilseed}</td>
                <td>${item.cropmillets}</td>
                <td>${item.cropother}</td>
                <td>${item.rice}</td>
                <td>${item.wheat}</td>
                <td>${item.pulses}</td>
                <td>${item.oilseed}</td>
                <td>${item.millets}</td>
                <td>${item.other}</td>
            </tr>
			
			</c:forEach>
				<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
            	<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
                <td colspan="2">Total</td>
                <td>${totalProj}</td>
                <td>${totalCereals}</td>
                <td>${totalPulses}</td>
                <td>${totalOilseed}</td>
                <td>${totalMillets}</td>
                <td>${totalOther}</td>
                <td>${totalRice}</td>
                <td>${totalWheat}</td>
                <td>${totalPulsesMain}</td>
                <td>${totalOilseedMain}</td>
                <td>${totalMilletsMain}</td>
                <td>${totalOtherMain}</td>
            </tr>
		</tbody>
	</table>
			</div>
 		</div>
	</div>

</c:if>

	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>