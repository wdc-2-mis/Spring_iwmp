<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<%@ page import="app.watershedyatra.bean.PreYatraPreparationBean" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">

<style>
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        padding-top: 100px;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.8);
    }
    .modal-content {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 600px;
    }
    .close {
        position: absolute;
        top: 15px;
        right: 25px;
        color: white;
        font-size: 35px;
        font-weight: bold;
        cursor: pointer;
    }
</style>

<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var block = $('#block').val();
	var gp = $('#grampan').val();
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
		alert('Please select block ');
		$('#block').focus();
		e.preventDefault();
	}
	else{
		
		document.preYatra.action="getPreYatraPreparationReportData";
		document.preYatra.method="post";
		document.preYatra.submit();
	}
	return false;
} 

function downloadPDF(state, district, blkd, grampn){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var blkName = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    var gpkName = document.getElementById("grampan").options[document.getElementById("grampan").selectedIndex].text;
 
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("blkName").value=blkName;
    document.getElementById("gpkName").value=gpkName;
	
    document.preYatra.action="downloadPDFPreYatraPrepReport";
	document.preYatra.method="post";
	document.preYatra.submit();
}

function showChangedata(){
	
	
    document.preYatra.action="getPreYatraPreparationReport";
	document.preYatra.method="get";
	document.preYatra.submit();
}

function downloadExcel(state, district, blkd, grampn){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var blkName = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    var gpkName = document.getElementById("grampan").options[document.getElementById("grampan").selectedIndex].text;
 
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("blkName").value=blkName;
    document.getElementById("gpkName").value=gpkName;
	
    document.preYatra.action="downloadExcelPreYatraPrepReport";
	document.preYatra.method="post";
	document.preYatra.submit();
}

</script>

</head>
<div class ="card">

<div class="table-responsive">
<br/>
<div class="col" style="text-align:center;"><h5>List of Pre - Yatra Preparation Details</h5></div>
 <form:form autocomplete="off" name="preYatra" id="preYatra"  action="getPreYatraPreparationReport" method="get">
 		<br/>
 		<input type="hidden" name="stName" id="stName" value="" />
		<input type="hidden" name="distName" id="distName" value="" />
		<input type="hidden" name="blkName" id="blkName" value="" />
		<input type="hidden" name="gpkName" id="gpkName" value="" />
		
      <table >
        <tr>
          <td class="label">State <span style="color: red;">*</span></td>
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
          
           <td class="label">District <span style="color: red;">*</span></td>
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
          
           <td class="label">Block &nbsp;<span style="color: red;">*</span></td>
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
           <td class="label">Gram Panchayat &nbsp;<span style="color: red;">*</span></td>
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
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>
</form:form>
 <br/>
<c:if test="${not empty preYatraList}">
<%-- <button name="exportExcel" id="exportExcel" onclick="downloadExcel('${state}','${district}','${blkd}','${grampn}')" class="btn btn-info">Excel</button> --%>
<%-- <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${district}','${blkd}','${grampn}')" class="btn btn-info">PDF</button> --%>
</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 <br/>
        
	<table id="tblReport" class="table">
    	<thead>
        	<tr>
				<th>S.No.</th>
				<th>State</th>
				<th>District</th>
				<th>Block</th>
				<th>Gram Panchayat</th>
				<th>Village</th>
				<th>Yatra Type</th>
				<th>Entry Date</th>
				<th>Photo 1</th>
				<th>Photo1 longitude</th>
				<th>Photo1 latitude</th>
				<th>Photo1 Date</th>
				<th>Photo 2</th>
				<th>Photo2 longitude</th>
				<th>Photo2 latitude</th>
				<th>Photo2 Date</th>
			</tr>
			<tr>
				<% for (int i = 1; i <= 16; i++) { %>
				<th class="text-center"><%= i %></th>
				<% } %>
			</tr>
        </thead>
        
        <tbody>
<c:set var="proj" value="" />
			<c:choose>
				<c:when test="${not empty preYatraList}">
					<c:forEach var="record" items="${preYatraList}" varStatus="loop">
						<tr>
							<td>${loop.count}</td> <%-- Correct serial number --%>
							
							<c:choose>
									<c:when test="${proj ne record.stname}">
										<c:set var="proj" value="${record.stname}" />
										<td> <c:out value="${record.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
							
							<td>${record.districtname}</td>
							<td>${record.blockname}</td>
							<td>${record.gramname}</td>
							<td>${record.villagename}</td>
							<c:if test="${record.yatratype eq 'gramSabha'}">
									<td> <c:out value="Gram Sabha/Special Gram Sabha" /></td>	
								</c:if>
								<c:if test="${record.yatratype eq 'prabhatPheri'}">
									<td> <c:out value="Prabhat Pheri" /></td>	
								</c:if>
							
							<td>${record.entrydate}</td>

							<td>
								<button onclick="showImage('https://wdcpmksy.dolr.gov.in/filepath/TESTING/preyatraprep/${record.photo1}')">View</button>
							</td>
							
							<td>${record.photo1long}</td>
							<td>${record.photo1lang}</td>
							<td>${record.photo1time}</td>
							<td>
								<button onclick="showImage('https://wdcpmksy.dolr.gov.in/filepath/TESTING/preyatraprep/${record.photo2}')">View</button>
							</td>
							
							<td>${record.photo2long}</td>
							<td>${record.photo2lang}</td>
							<td>${record.photo2time}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td align="center" colspan="16" class="required" style="color:red;">Data Not Found</td>
					</tr>
				</c:otherwise>
			</c:choose>

				</tbody>
               		
    </table>
    
   <div id="imageModal" class="modal">
    	<span class="close" onclick="closeImage()">&times;</span>
    	<img class="modal-content" id="popupImage">
	</div>
	  

    <br>
<script>
    function showImage(src) {
        if (!src) {
            alert("No Image Available");
            return;
        }
        document.getElementById("popupImage").src = src;
        document.getElementById("imageModal").style.display = "block";
    }

    function closeImage() {
        document.getElementById("imageModal").style.display = "none";
    }
</script>

	</div>
	</div>


<script type="text/javascript">
	$(document).ready(function() {
		$(".sidebar-btn").click(function() {
			$(".wrapper").toggleClass("collapse");
		});
	});
</script>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

<script src='<c:url value="/resources/js/preyatraPrep.js" />'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
	
</body>
</html>