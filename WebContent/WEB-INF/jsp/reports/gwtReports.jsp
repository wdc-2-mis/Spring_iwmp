<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<title>Report O2- Project wise Ground Water Table</title>


<script type="text/javascript">
function showReport(e) 
{
	//alert('kedar');
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
  //  alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
 
	if(document.getElementById("state").value==='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	if(document.getElementById("district").value==='')
	{
		alert('Please select district ! ');
		$('#district').focus();
		e.preventDefault();
	}
	if(document.getElementById("project").value==='')
	{
		alert('Please select project !');
		$('#project').focus();
		e.preventDefault();
	}
	else{
		
		document.gwtReport.action="getGWTReport";
		document.gwtReport.method="post";
		document.gwtReport.submit();
	}
	return false;

}
$(document).on('change','#state',function(e){
	document.gwtReport.action="gwtReports";
	document.gwtReport.method="get";
	document.gwtReport.submit();
	
});
$(document).on('change','#district',function(e){
	document.gwtReport.action="gwtReports";
	document.gwtReport.method="get";
	document.gwtReport.submit();
	
});
function exportExcel(stName,distName,projName){
	document.getElementById("stName").value=stName;
	document.getElementById("distName").value=distName;
	document.getElementById("projName").value=projName;
	document.gwtReport.action="getProjectwiseGWTExcel";
	document.gwtReport.method="post";
	document.gwtReport.submit();
}

function downloadPDF(stName,distName,projName){
	document.getElementById("stName").value=stName;
	document.getElementById("distName").value=distName;
	document.getElementById("projName").value=projName;
	document.gwtReport.action="downloadGroundWaterTablePDF";
	document.gwtReport.method="post";
	document.gwtReport.submit();
}

</script>
</head>


<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head1">Report O2- Project wise Ground Water Table and Water Quality Parameter</label></h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="gwtReports" name="gwtReport" id="gwtReport" method="get">
	
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projName" id="projName" value="" />
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select  id="state" name="state" onchange="this.form.submit();" required="required">
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
          <td><b>District <span style="color: red;">*</span></b></td>
           <td>
              <select name="district" id="district" onchange="this.form.submit();" required="required">
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
          <td ><b>Project <span style="color: red;">*</span></b></td>
          <td>
              <select name="project" id="project" required="required">
              <option value="0">--All Project--</option>
              	<c:if test="${not empty ProjectList}">
               					<c:forEach items="${ProjectList}" var="lists">
               						<c:if test="${lists.key eq project}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne project}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
        
           <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Report' /> </td> </td> 
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
<c:if test ="${dataListsize ne null }">	
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stName}','${distName}','${projName}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stName}','${distName}','${projName}')" class="btn btn-info">PDF</button>
</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="pdfBasicExample" cellspacing="0" class="table"   width="auto">
  <thead>
	<tr>
		<th colspan="24" >State : &nbsp; <c:out value='${stName}' /> &nbsp;&nbsp;&nbsp; District : &nbsp; <c:out value='${distName}' /> &nbsp;&nbsp;&nbsp; 
		Project : &nbsp; <c:out value='${projName}' />  </th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <c:if test="${state=='0'}">
      <th rowspan="2" style="text-align:center; vertical-align: middle;">State Name</th> 
      </c:if>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">Project Name</th>
      <th style="text-align:center" colspan="2"> Depth of Ground Water (Baseline Survey)</th>
      <th style="text-align:center" colspan="8"> Water Quality (Baseline Survey)</th>
      <th style="text-align:center" colspan="3"> Depth of Ground Water (During Project Period)</th>
       <th style="text-align:center" colspan="8"> Water Quality (During Project Period)</th>
     </tr>
     <tr> 
      	<th class="text-center">Pre-Monsoon (in meter)</th>
		<th class="text-center">Post-Monsoon (in meter)</th>
		<th class="text-center">pH </th>
	  	<th class="text-center">Total Alkalinity</th>
	  	<th class="text-center">Total Hardness</th>
	  	<th class="text-center">Calcium</th>
	  	<th class="text-center">Chloride</th>
	  	<th class="text-center">Nitrate</th>
	  	<th class="text-center">Total Dissolved</th>
	  	<th class="text-center">Fluoride</th>
		
		<th style="text-align:center">Financial Year</th>	
		<th style="text-align:center">Pre-Monsoon (in meter)</th>		
      	<th style="text-align:center">Post-Monsoon (in meter)</th>
      	<th class="text-center">pH </th>
	  	<th class="text-center">Total Alkalinity</th>
	  	<th class="text-center">Total Hardness</th>
	  	<th class="text-center">Calcium</th>
	  	<th class="text-center">Chloride</th>
	  	<th class="text-center">Nitrate</th>
	  	<th class="text-center">Total Dissolved</th>
	  	<th class="text-center">Fluoride</th>
    </tr>
  </thead>

  <tbody>
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
		<th class="text-center">15</th>
		<th class="text-center">16</th>
		<th class="text-center">17</th>
		<th class="text-center">18</th>
		<th class="text-center">19</th>
		<th class="text-center">20</th>
		<th class="text-center">21</th>
		<th class="text-center">22</th>
		<th class="text-center">23</th>
		<c:if test="${state=='0'}">
		<th class="text-center">24</th>
		</c:if>
	</tr>
	
	<c:if test="${dataList != null}">
		
		<c:forEach items="${dataList}" var="dataV" varStatus="status">
		<tr>		
			<td><c:out value='${dataV[0]}' /></td>
			
			<c:if test="${state=='0'}">
				<c:if test="${dataV[27] == null}">
					<td><c:out value='${dataV[28]}' /></td>
				</c:if>
				<c:if test="${dataV[28] == null}">
					<td><c:out value='${dataV[27]}' /></td>
				</c:if>
				<c:if test="${dataV[27] != null && dataV[28] != null}">
					<td><c:out value='${dataV[27]}' /></td>
				</c:if>
			</c:if>
			
			<c:if test="${dataV[2] == null}">
				<td><c:out value='${dataV[10]}' /></td>
			</c:if>
			<c:if test="${dataV[10] == null}">
				<td><c:out value='${dataV[2]}' /></td>
			</c:if>
			<c:if test="${dataV[2] != null && dataV[10] != null}">
				<td><c:out value='${dataV[2]}' /></td>
			</c:if>
			<td align="right"><c:out value='${dataV[3]}' /></td>
			<td align="right"><c:out value='${dataV[4]}' /></td>
			
			<td align="right"><c:out value='${dataV[11]}' /></td>
			<td align="right"><c:out value='${dataV[12]}' /></td>
			<td align="right"><c:out value='${dataV[13]}' /></td>
			<td align="right"><c:out value='${dataV[14]}' /></td>
			<td align="right"><c:out value='${dataV[15]}' /></td>
			<td align="right"><c:out value='${dataV[16]}' /></td>
			<td align="right"><c:out value='${dataV[17]}' /></td>
			<td align="right"><c:out value='${dataV[18]}' /></td>
			
			<td align="right"><c:out value='${dataV[6]}' /></td>
			<td align="right"><c:out value='${dataV[7]}' /></td>
			<td align="right"><c:out value='${dataV[8]}' /></td>
			
			<td align="right"><c:out value='${dataV[19]}' /></td>
			<td align="right"><c:out value='${dataV[20]}' /></td>
			<td align="right"><c:out value='${dataV[21]}' /></td>
			<td align="right"><c:out value='${dataV[22]}' /></td>
			<td align="right"><c:out value='${dataV[23]}' /></td>
			<td align="right"><c:out value='${dataV[24]}' /></td>
			<td align="right"><c:out value='${dataV[25]}' /></td>
			<td align="right"><c:out value='${dataV[26]}' /></td>
			
		</tr>
					<c:set var="totalpremon"
					value="${totalpremon+dataV[3]}" />
					<c:set var="totalpostmon"
					value="${totalpostmon+dataV[4]}" />
					<c:set var="totalph"
					value="${totalph+dataV[11] }"/>
					<c:set var="totalalkalinity"
					value="${totalalkalinity+dataV[12] }"/>
					<c:set var="totalhardness"
					value="${totalhardness+dataV[13] }"/>
					<c:set var="totalcalcium"
					value="${totalcalcium+dataV[14] }"/>
					<c:set var="totalchloride"
					value="${totalchloride+dataV[15] }"/>
					<c:set var="totalnitrate"
					value="${totalnitrate+dataV[16] }"/>
					<c:set var="totaldissolved_solid"
					value="${totaldissolved_solid+dataV[17] }"/>
					<c:set var="totalfluoride"
					value="${totalfluoride+dataV[18] }"/>
					
					<c:set var="totalpremon1"
					value="${totalpremon1+dataV[7] }"/>
					<c:set var="totalpostmon1"
					value="${totalpostmon1+dataV[8] }"/>
					<c:set var="totalph1"
					value="${totalph1+dataV[19] }"/>
					<c:set var="totalalkalinity1"
					value="${totalalkalinity1+dataV[20] }"/>
					<c:set var="totalhardness1"
					value="${totalhardness1+dataV[21] }"/>
					<c:set var="totalcalcium1"
					value="${totalcalcium1+dataV[22] }"/>
					<c:set var="totalchloride1"
					value="${totalchloride1+dataV[23] }"/>
					<c:set var="totalnitrate1"
					value="${totalnitrate1+dataV[24] }"/>
					<c:set var="totaldissolved_solid1"
					value="${totaldissolved_solid1+dataV[25] }"/>
					<c:set var="totalfluoride1"
					value="${totalfluoride1+dataV[26] }"/>
					
					
		</c:forEach>
		<tr>	<c:if test ="${checkState eq 0 }">
				<td class="table-primary"></td>
				<td class="table-primary"></td>
				<td class="table-primary"><b>Grand Total</b></td></c:if>
				<c:if test ="${checkState ne 0 }">
				<td class="table-primary"></td>
				<td class="table-primary" ><b>Grand Total</b></td></c:if>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalpremon}"/></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalpostmon}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalph}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalalkalinity}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalhardness}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalcalcium}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalchloride}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalnitrate}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totaldissolved_solid}" /></fmt:formatNumber></b></td>
<!-- 				<td class="table-primary"></td> -->
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalfluoride}" /></fmt:formatNumber></b></td>
				<td class="table-primary"></td>
				
				
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalpremon1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalpostmon1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalph1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalalkalinity1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalhardness1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalcalcium1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalchloride1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalnitrate1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totaldissolved_solid1}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number" maxFractionDigits="2"><c:out value="${totalfluoride1}" /></fmt:formatNumber></b></td>
				</tr>
    </c:if>
    	<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="23" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>
    
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>