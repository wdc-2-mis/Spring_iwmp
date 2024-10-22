<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<title>Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details</title>

<script type="text/javascript">

// function showReport(e) 
// {
//     var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
//     var fincode = document.getElementById("year").value;
    
//     document.getElementById("finName").value=finName;
//     document.getElementById("fincode").value=fincode;
    
// 	document.getConvergedWorks.action="getConvergedWorksDetails";
// 	document.getConvergedWorks.method="post";
// 	document.getConvergedWorks.submit();

// 	return false;

// }

function downloadPDF(){
// 		document.getElementById("fincode").value=finyr;
// 		document.getElementById("finName").value=finName;
		document.getConvergedWorks.action="downloadPDFConvergedWorks";
		document.getConvergedWorks.method="post";
		document.getConvergedWorks.submit();
}

function exportExcel(){
// 		document.getElementById("fincode").value=finyr;
// 		document.getElementById("finName").value=finName;
		document.getConvergedWorks.action="downloadExcelConvergedWorks";
		document.getConvergedWorks.method="post";
		document.getConvergedWorks.submit();
}
	
</script>


<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details</label></h5></div>
<br>
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">

<div class="table-responsive" id="exportHtmlToPdf1">
<input type="hidden" name="stName" id="stName" value="" />
 <form:form action="getConvergedWorks" name="getConvergedWorks" id="getConvergedWorks" method="get">
<!-- 	<div id="waitDiv"  -->
<%-- 			style="display: none; line-height: 20px; z-index: 98; position: absolute; background: #ffffff; left: 25px;  height: 800px; --%>
<%-- <!-- 			 width: 1600px; filter: alpha(opacity = 60); -moz-opacity: .60; opacity: .60; text-align: center; float: left;"> --> --%>
<!-- 			<table>   -->
<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<div align="center"> -->
<!-- 							<span style="padding-right:3px;  display:inline-block; width: 1600px;"> -->
<!-- 									<img class="manImg" src="resources/images/load.gif"></img> -->
<!-- 							</span> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table>  -->
<!-- 			</div> -->
		
<!--     <input type="hidden" name="fincode" id="fincode" value="" /> -->
<!-- 	<input type="hidden" name="finName" id="finName" value="" /> -->
	<input type="hidden" name="stName" id="stName" value="" />
	
<!--       <table style="width:100%; align-content: center;" > -->
<!--         <tr align="center" > -->
         
<!--           <td><b>Financial Year <span style="color: red;">*</span></b></td> -->
<!--            <td> -->
<!--               <select name="year" id="year"  required="required" onchange="showReport();"> -->
<!--               		<option value="0">--All Financial Years--</option> -->
<%-- 						  <c:if test="${not empty financialYear}"> --%>
<%--                					<c:forEach items="${financialYear}" var="lists"> --%>
<%--                					<c:if test="${lists.finYrCd eq finyr && lists.finYrCd ne 21}"> --%>
<%--        								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option> --%>
<%--        							</c:if>	 --%>
<%--        							<c:if test="${lists.finYrCd ne finyr && lists.finYrCd ne 21}"> --%>
<%--        								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option> --%>
<%--        							</c:if> --%>
<%-- 								</c:forEach> --%>
<%-- 						</c:if>   --%>
<!--               </select> -->
<!--           </td> -->
<!--           <td width="50%"> -->
<!--           </td> -->
        
           
<!--        </tr> -->
<!--       </table> -->
<!--       <div id="previewDiv" class="hiddenDivStyle" align="center" -->
<!-- 			style="position: absolute; top: 100px; left: 25px; display: none; width: 300px; height: 50px; vertical-scrol: auto; background-color: gray;"> -->
<!-- 			<table align="center"> -->
<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<div align="center"> -->
<!-- 							<span style="font-size: 25px;">Please Wait ...</span> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 		</div> -->
 </form:form>
 </div>
 </div>
<br>
	</div>
	

	<br/>
	<br/>
	 <c:if test="${not empty ConvergedWorksList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id = "tblReport" class = "table">
	<thead>
<!-- 		<tr> -->
<%-- 			<th colspan=18 class="text-left">Financial Year : <c:out value='${finName}' /></th> --%>
<!-- 		</tr> -->
		<tr>
			<th rowspan=2 class="text-center">S.No.</th>
			<th rowspan=2 class="text-center">State Name</th>
			<th rowspan=2 class="text-center">Total Works</th>
			<th rowspan=2 class="text-center">Convergence Works</th>
			<th colspan=2 class="text-center">Area brought under Afforestation / Agriculture / Pasture etc.</th>
			<th colspan=2 class="text-center">Area brought under Horticulture</th>
			<th colspan=2 class="text-center">Area covered under Soil and Moisture conservation activities</th>
			<th colspan=2 class="text-center">Water Harvesting Structure (New created)</th>
			<th colspan=2 class="text-center">Water Harvesting Structure (Renovated)</th>
			<th colspan=2 class="text-center">Vegetative and Engineering Structure</th>
			<th colspan=2 class="text-center">Springsheds</th>
		</tr>
		<tr>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
			<th class="text-center">Total Works</th>
			<th class="text-center">Convergence Works</th>
		</tr>
	</thead>
	<tbody id = "convergedWorksRptTbody">
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
		</tr>
		<c:forEach items="${ConvergedWorksList}" var="work" varStatus="sno">
			<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
<%-- 					<td class="text-left"><c:out value="${work.st_name}" /></td> --%>
					<td><a href = "getConvergedDistWorks?stcd=${work.st_code}&stName=${work.st_name}"><c:out value='${work.st_name}'/></a></td>
					<td class="text-right"><c:out value="${work.totalworks}" /></td>
					<td class="text-right"><c:out value="${work.convergedworks}" /></td>
					<td class="text-right"><c:out value="${work.afforestation}" /></td>
					<td class="text-right"><c:out value="${work.afforestation_conv}" /></td>
					<td class="text-right"><c:out value="${work.horticulture}" /></td>
					<td class="text-right"><c:out value="${work.horticulture_conv}" /></td>
					<td class="text-right"><c:out value="${work.soil_moisture}" /></td>
					<td class="text-right"><c:out value="${work.soil_moisture_conv}" /></td>
					<td class="text-right"><c:out value="${work.water_harvesting_new}" /></td>
					<td class="text-right"><c:out value="${work.water_harvesting_new_conv}" /></td>
					<td class="text-right"><c:out value="${work.water_harvesting_renovated}" /></td>
					<td class="text-right"><c:out value="${work.water_harvesting_renovated_conv}" /></td>
					<td class="text-right"><c:out value="${work.vegetative_structure}" /></td>
					<td class="text-right"><c:out value="${work.vegetative_structure_conv}" /></td>
					<td class="text-right"><c:out value="${work.springsheds}" /></td>
					<td class="text-right"><c:out value="${work.springsheds_conv}" /></td>
			</tr>
			
			<c:set var = "totwrk" 
 			value = "${totwrk + work.totalworks}" />
 			<c:set var = "conwrk" 
 			value = "${conwrk + work.convergedworks}" />
 			<c:set var = "totafforestation" 
 			value = "${totafforestation + work.afforestation}" />
 			<c:set var = "totafforestationconv" 
 			value = "${totafforestationconv + work.afforestation_conv}" /> 
			<c:set var = "tothorticulture" 
 			value = "${tothorticulture + work.horticulture}" />
 			<c:set var = "tothorticultureconv" 
 			value = "${tothorticultureconv + work.horticulture_conv}" /> 
 			<c:set var = "totsoilmoisture" 
 			value = "${totsoilmoisture + work.soil_moisture}" />
 			<c:set var = "totsoilmoistureconv" 
 			value = "${totsoilmoistureconv + work.soil_moisture_conv}" /> 
 			<c:set var = "totnewwaterharvesting" 
 			value = "${totnewwaterharvesting + work.water_harvesting_new}" />
 			<c:set var = "totnewwaterharvestingconv" 
 			value = "${totnewwaterharvestingconv + work.water_harvesting_new_conv}" /> 
 			<c:set var = "totrenovatedwaterharvesting" 
 			value = "${totrenovatedwaterharvesting + work.water_harvesting_renovated}" />
 			<c:set var = "totrenovatedwaterharvestingconv" 
 			value = "${totrenovatedwaterharvestingconv + work.water_harvesting_renovated_conv}" /> 
 			<c:set var = "totvegetativestructure" 
 			value = "${totvegetativestructure + work.vegetative_structure}" />
 			<c:set var = "totvegetativestructureconv" 
 			value = "${totvegetativestructureconv + work.vegetative_structure_conv}" /> 
 			<c:set var = "totspringsheds" 
 			value = "${totspringsheds + work.springsheds}" />
 			<c:set var = "totspringshedsconv" 
 			value = "${totspringshedsconv + work.springsheds_conv}" /> 
		</c:forEach>

		<tr>
			<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
			<td align="right" class="table-primary"><b><c:out value="${totwrk}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${conwrk}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totafforestation}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totafforestationconv}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${tothorticulture}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${tothorticultureconv}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totsoilmoisture}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totsoilmoistureconv}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totnewwaterharvesting}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totnewwaterharvestingconv}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totrenovatedwaterharvesting}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totrenovatedwaterharvestingconv}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totvegetativestructure}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totvegetativestructureconv}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totspringsheds}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totspringshedsconv}" /></b></td>
		</tr>

		<c:if test="${ConvergedWorksListSize==0}">
			<tr>
				<td align="center" colspan="18" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
	</tbody>
</table>

 </div>

	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>