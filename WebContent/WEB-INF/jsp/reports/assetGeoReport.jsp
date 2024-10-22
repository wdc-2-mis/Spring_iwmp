<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<title>Report GT2- Work Code Status with Geotagging Details</title>

</head>
<script type= "text/javascript">
function downloadPDF()
{
		document.getreports.action="downloadAllAssetGeoDataPDF";
		document.getreports.method="post";
		document.getreports.submit();
	
} 
function exportExcel()
{
		document.getreports.action="downloadExcelAllAssetGeoData";
		document.getreports.method="post";
		document.getreports.submit();
	
} 
function downloadDistrictPDF(stname, stcode)
{
		document.getElementById("stname").value=stname;
		document.getElementById("stcode").value=stcode;
		document.getreports.action="downloadDistwiseAllAssetGeoDataPDF";
		document.getreports.method="post";
		document.getreports.submit();
	
} 
function exportDistExcel(stname, stcode)
{
		document.getElementById("stname").value=stname;
		document.getElementById("stcode").value=stcode;
		document.getreports.action="downloadDistExcelAllAssetGeoData";
		document.getreports.method="post";
		document.getreports.submit();
	
} 
function downloadProjectPDF(distname, dcode, stname)
{		
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.getElementById("stname").value=stname;
		document.getreports.action="downloadProjectwiseAllAssetGeoDataPDF";
		document.getreports.method="post";
		document.getreports.submit();
	
} 
function exportProjectExcel(distname, dcode, stname)
{		
		document.getElementById("distname").value=distname;
		document.getElementById("dcode").value=dcode;
		document.getElementById("stname").value=stname;
		document.getreports.action="downloadProjectExcelAllAssetGeoData";
		document.getreports.method="post";
		document.getreports.submit();
	
} 
function downloadProjDetailPDF(projid,projname,distname,stname)
{		
		document.getElementById("projname").value=projname;
		document.getElementById("projid").value=projid;
		document.getElementById("distname").value=distname;
		document.getElementById("stname").value=stname;
		document.getreports.action="downloadProjDetailAllAssetGeoDataPDF";
		document.getreports.method="post";
		document.getreports.submit();
	
} 

function downloadProjDetailExcel(projid,projname,distname,stname)
{		
	document.getElementById("projname").value=projname;
	document.getElementById("projid").value=projid;
	document.getElementById("distname").value=distname;
	document.getElementById("stname").value=stname;
	document.getreports.action="downloadProjDetailAllAssetGeoDataExcel";
	document.getreports.method="post";
	document.getreports.submit();

} 
</script>

<body>
<br>
<form action="downloadAllAssetGeoDataPDF" method="post" id="getreports" name="getreports">
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="stcode" id="stcode" value="" />
			<input type="hidden" name="distname" id="distname" value="" />
			<input type="hidden" name="dcode" id="dcode" value="" />
			<input type="hidden" name="projname" id="projname" value="" />
			<input type="hidden" name="projid" id="projid" value="" />
			
		</form>
<c:if test = "${allStateListsize gt 0 }">
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report GT2 - State Wise Work Code Status with Geotagging Details </h5></div>
		</div>
	</div>
</div>
<br>

	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 20%;">State Name</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 20%;">Total Projects</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">NRM Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">EPA Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">Livelihood Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">Production Works</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 9%;">Total Work Code excluding Foreclosed (NRM(5+6+7) + EPA(9+10+11) + LIV(13+14+15) + PROD(17+18+19))</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 10%;">Total Geotag Work Code</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 15%;">Total Non Geotag Work Code (20-21)</th>
       	</tr>
       	<tr>

       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
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
			
		</tr>
		<c:set var ="count" value ="1"/>
		<c:set var ="totproj" value ="0"/>
		<c:set var ="totcreated_n" value ="0"/>
		<c:set var ="totunstarted_n" value ="0"/>
		<c:set var ="totongoing_n" value ="0"/>
		<c:set var ="totcompleted_n" value ="0"/>
		<c:set var ="totcreated_e" value ="0"/>
		<c:set var ="totunstarted_e" value ="0"/>
		<c:set var ="totongoing_e" value ="0"/>
		<c:set var ="totcompleted_e" value ="0"/>
		<c:set var ="totcreated_l" value ="0"/>
		<c:set var ="totunstarted_l" value ="0"/>
		<c:set var ="totongoing_l" value ="0"/>
		<c:set var ="totcompleted_l" value ="0"/>
		<c:set var ="totcreated_p" value ="0"/>
		<c:set var ="totunstarted_p" value ="0"/>
		<c:set var ="totongoing_p" value ="0"/>
		<c:set var ="totcompleted_p" value ="0"/>
		<c:set var ="totworkcode" value ="0"/>
		<c:set var ="totgeorefwrkcd" value ="0"/>
		<c:set var ="totnongeorefwrkcd" value ="0"/>
							<c:forEach var="list" items="${allStateList}">
								<tr>
									<td><c:out value="${count}" /></td>
									<td><a
										href="getDistWiseAssetGeoData?stcode=${list.stcode}&stname=${list.stname}"><c:out
 												value="${list.stname}" /></a></td> 
									<td class="text-right"><c:out value="${list.totproj}" /></td>
									<td class="text-right"><c:out value="${list.n_created}" /></td>
									<td class="text-right"><c:out value="${list.n_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.n_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.n_completed}" /></td>
									<td class="text-right"><c:out value="${list.e_created}" /></td>
									<td class="text-right"><c:out value="${list.e_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.e_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.e_completed}" /></td>
									<td class="text-right"><c:out value="${list.l_created}" /></td>
									<td class="text-right"><c:out value="${list.l_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.l_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.l_completed}" /></td>
									<td class="text-right"><c:out value="${list.p_created}" /></td>
									<td class="text-right"><c:out value="${list.p_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.p_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.p_completed}" /></td>
									<td class="text-right"><c:out value="${list.totworkcode}" /></td>
									<td class="text-right"><c:out value="${list.totgeorefwrkcd}" /></td>
									<td class="text-right"><c:out value="${list.totnongeorefwrkcd}" /></td>
								</tr>
								<c:set var="count" value="${count + 1}" />
								<c:set var="totproj" value="${totproj + list.totproj}" />
								<c:set var="totcreated_n" value="${totcreated_n + list.n_created}" />
								<c:set var="totunstarted_n" value="${totunstarted_n + list.n_unstarted}" />
								<c:set var="totongoing_n" value="${totongoing_n + list.n_ongoing}" />
								<c:set var="totcompleted_n" value="${totcompleted_n + list.n_completed}" />
								<c:set var="totcreated_e" value="${totcreated_e + list.e_created}" />
								<c:set var="totunstarted_e" value="${totunstarted_e + list.e_unstarted}" />
								<c:set var="totongoing_e" value="${totongoing_e + list.e_ongoing}" />
								<c:set var="totcompleted_e" value="${totcompleted_e + list.e_completed}" />
								<c:set var="totcreated_l" value="${totcreated_l + list.l_created}" />
								<c:set var="totunstarted_l" value="${totunstarted_l + list.l_unstarted}" />
								<c:set var="totongoing_l" value="${totongoing_l + list.l_ongoing}" />
								<c:set var="totcompleted_l" value="${totcompleted_l + list.l_completed}" />
								<c:set var="totcreated_p" value="${totcreated_p + list.p_created}" />
								<c:set var="totunstarted_p" value="${totunstarted_p + list.p_unstarted}" />
								<c:set var="totongoing_p" value="${totongoing_p + list.p_ongoing}" />
								<c:set var="totcompleted_p" value="${totcompleted_p + list.p_completed}" />
								<c:set var="totworkcode" value="${totworkcode + list.totworkcode}" />
								<c:set var="totgeorefwrkcd" value="${totgeorefwrkcd + list.totgeorefwrkcd}" />
								<c:set var="totnongeorefwrkcd" value="${totnongeorefwrkcd + list.totnongeorefwrkcd}" />
							</c:forEach>
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_n}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_e}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_l}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_p}" /></b></td>
								
								<td align="right" class="table-primary"><b><c:out value="${totworkcode}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totgeorefwrkcd}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totnongeorefwrkcd}" /></b></td>
							</tr>
						</tbody>
  </table>

</c:if> 

<c:if test = "${allDistListsize gt 0 }">
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<div class="offset-md-3 col-6 formheading" style="text-align:center;" >
				<h5>Report GT2 - District Wise Work Code Status with Geotagging Details for State "<c:out value='${stname}'/>"</h5>
			</div>
		</div>
	</div>
</div>
<br>

	<button name="exportExcel" id="exportExcel" onclick="exportDistExcel('${stcode}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadDistrictPDF('${stcode}','${stname}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 20%;">District Name</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 20%;">Total Projects</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">NRM Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">EPA Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">Livelihood Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">Production Works</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 9%;">Total Work Code excluding Foreclosed (NRM(5+6+7) + EPA(9+10+11) + LIV(13+14+15) + PROD(17+18+19))</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 10%;">Total Geotag Work Code</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 15%;">Total Non Geotag Work Code (20-21)</th>
       	</tr>
       	<tr>

       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
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
			
			
			
		</tr>
		<c:set var ="totproj" value ="0"/>
		<c:set var ="totcreated_n" value ="0"/>
		<c:set var ="totunstarted_n" value ="0"/>
		<c:set var ="totongoing_n" value ="0"/>
		<c:set var ="totcompleted_n" value ="0"/>
		<c:set var ="totcreated_e" value ="0"/>
		<c:set var ="totunstarted_e" value ="0"/>
		<c:set var ="totongoing_e" value ="0"/>
		<c:set var ="totcompleted_e" value ="0"/>
		<c:set var ="totcreated_l" value ="0"/>
		<c:set var ="totunstarted_l" value ="0"/>
		<c:set var ="totongoing_l" value ="0"/>
		<c:set var ="totcompleted_l" value ="0"/>
		<c:set var ="totcreated_p" value ="0"/>
		<c:set var ="totunstarted_p" value ="0"/>
		<c:set var ="totongoing_p" value ="0"/>
		<c:set var ="totcompleted_p" value ="0"/>
		<c:set var ="totworkcode" value ="0"/>
		<c:set var ="totgeorefwrkcd" value ="0"/>
		<c:set var ="totnongeorefwrkcd" value ="0"/>
		<c:set var ="count" value ="1"/>
							<c:forEach var="list" items="${allDistList}">
								<tr>
									<td><c:out value="${count}" /></td>
									<td><a href="getProjWiseAssetGeoData?dcode=${list.dcode}&stname=${stname}&distname=${list.distname}"><c:out	value="${list.distname}" /></a></td>
									<td class="text-right"><c:out value="${list.totproj}" /></td>
									<td class="text-right"><c:out value="${list.n_created}" /></td>
									<td class="text-right"><c:out value="${list.n_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.n_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.n_completed}" /></td>
									<td class="text-right"><c:out value="${list.e_created}" /></td>
									<td class="text-right"><c:out value="${list.e_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.e_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.e_completed}" /></td>
									<td class="text-right"><c:out value="${list.l_created}" /></td>
									<td class="text-right"><c:out value="${list.l_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.l_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.l_completed}" /></td>
									<td class="text-right"><c:out value="${list.p_created}" /></td>
									<td class="text-right"><c:out value="${list.p_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.p_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.p_completed}" /></td>
									<td class="text-right"><c:out value="${list.totworkcode}" /></td>
									<td class="text-right"><c:out value="${list.totgeorefwrkcd}" /></td>
									<td class="text-right"><c:out value="${list.totnongeorefwrkcd}" /></td>
								</tr>
								<c:set var="count" value="${count + 1}" />
								<c:set var="totproj" value="${totproj + list.totproj}" />
								<c:set var="totcreated_n" value="${totcreated_n + list.n_created}" />
								<c:set var="totunstarted_n" value="${totunstarted_n + list.n_unstarted}" />
								<c:set var="totongoing_n" value="${totongoing_n + list.n_ongoing}" />
								<c:set var="totcompleted_n" value="${totcompleted_n + list.n_completed}" />
								<c:set var="totcreated_e" value="${totcreated_e + list.e_created}" />
								<c:set var="totunstarted_e" value="${totunstarted_e + list.e_unstarted}" />
								<c:set var="totongoing_e" value="${totongoing_e + list.e_ongoing}" />
								<c:set var="totcompleted_e" value="${totcompleted_e + list.e_completed}" />
								<c:set var="totcreated_l" value="${totcreated_l + list.l_created}" />
								<c:set var="totunstarted_l" value="${totunstarted_l + list.l_unstarted}" />
								<c:set var="totongoing_l" value="${totongoing_l + list.l_ongoing}" />
								<c:set var="totcompleted_l" value="${totcompleted_l + list.l_completed}" />
								<c:set var="totcreated_p" value="${totcreated_p + list.p_created}" />
								<c:set var="totunstarted_p" value="${totunstarted_p + list.p_unstarted}" />
								<c:set var="totongoing_p" value="${totongoing_p + list.p_ongoing}" />
								<c:set var="totcompleted_p" value="${totcompleted_p + list.p_completed}" />
								<c:set var="totworkcode" value="${totworkcode + list.totworkcode}" />
								<c:set var="totgeorefwrkcd" value="${totgeorefwrkcd + list.totgeorefwrkcd}" />
								<c:set var="totnongeorefwrkcd" value="${totnongeorefwrkcd + list.totnongeorefwrkcd}" />
							</c:forEach>
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_n}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_e}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_l}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_p}" /></b></td>
								
								<td align="right" class="table-primary"><b><c:out value="${totworkcode}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totgeorefwrkcd}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totnongeorefwrkcd}" /></b></td>
							</tr>
		
	</tbody>
  </table>
</c:if>

<c:if test = "${allProjListsize gt 0 }">

<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report GT2 - Project Wise Work Code Status with Geotagging Details for District "<c:out value="${distname}"/>" of State "<c:out value="${stname}"/>"</h5></div>
		</div>
	</div>
</div>
<br>

	<button name="exportExcel" id="exportExcel" onclick="exportProjectExcel('${dcode}','${distname}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadProjectPDF('${dcode}','${distname}','${stname}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">NRM Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">EPA Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">Livelihood Works</th>
      		<th colspan = "4" style="text-align:center; vertical-align: middle; width: 7%;">Production Works</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 9%;">Total Work Code excluding Foreclosed (NRM(4+5+6) + EPA(8+9+10) + LIV(12+13+14) + PROD(16+17+18))</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 10%;">Total Geotag Work Code</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 15%;">Total Non Geotag Work Code (19-20)</th>
       	</tr>
       	<tr>

       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
       		
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Total Works</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Not Started (Status Not Submitted)</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Ongoing</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Completed</th>
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
		</tr>
		
		<c:set var ="count" value ="1"/>
		<c:set var ="totcreated_n" value ="0"/>
		<c:set var ="totunstarted_n" value ="0"/>
		<c:set var ="totongoing_n" value ="0"/>
		<c:set var ="totcompleted_n" value ="0"/>
		<c:set var ="totcreated_e" value ="0"/>
		<c:set var ="totunstarted_e" value ="0"/>
		<c:set var ="totongoing_e" value ="0"/>
		<c:set var ="totcompleted_e" value ="0"/>
		<c:set var ="totcreated_l" value ="0"/>
		<c:set var ="totunstarted_l" value ="0"/>
		<c:set var ="totongoing_l" value ="0"/>
		<c:set var ="totcompleted_l" value ="0"/>
		<c:set var ="totcreated_p" value ="0"/>
		<c:set var ="totunstarted_p" value ="0"/>
		<c:set var ="totongoing_p" value ="0"/>
		<c:set var ="totcompleted_p" value ="0"/>
		<c:set var ="totworkcode" value ="0"/>
		<c:set var ="totgeorefwrkcd" value ="0"/>
		<c:set var ="totnongeorefwrkcd" value ="0"/>
		
							<c:forEach var="list" items="${allProjList}">
								<tr>
									<td><c:out value="${count}" /></td>
									<c:if test="${list.totgeorefwrkcd eq 0}">
										<td><c:out value="${list.projname}" /></td>
									</c:if>
									<c:if test="${list.totgeorefwrkcd > 0}">
										<td><a href="getProjDtlAssetGeoData?projid=${list.projid}&stname=${stname}&distname=${distname}&projname=${list.projname}"><c:out value="${list.projname}" /></a></td>
									</c:if>
									<td class="text-right"><c:out value="${list.n_created}" /></td>
									<td class="text-right"><c:out value="${list.n_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.n_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.n_completed}" /></td>
									<td class="text-right"><c:out value="${list.e_created}" /></td>
									<td class="text-right"><c:out value="${list.e_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.e_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.e_completed}" /></td>
									<td class="text-right"><c:out value="${list.l_created}" /></td>
									<td class="text-right"><c:out value="${list.l_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.l_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.l_completed}" /></td>
									<td class="text-right"><c:out value="${list.p_created}" /></td>
									<td class="text-right"><c:out value="${list.p_unstarted}" /></td>
									<td class="text-right"><c:out value="${list.p_ongoing}" /></td>
									<td class="text-right"><c:out value="${list.p_completed}" /></td>
									<td class="text-right"><c:out value="${list.totworkcode}" /></td>
									<td class="text-right"><c:out value="${list.totgeorefwrkcd}" /></td>
									<td class="text-right"><c:out value="${list.totnongeorefwrkcd}" /></td>
								</tr>
								<c:set var="count" value="${count + 1}" />
								<c:set var="totcreated_n" value="${totcreated_n + list.n_created}" />
								<c:set var="totunstarted_n" value="${totunstarted_n + list.n_unstarted}" />
								<c:set var="totongoing_n" value="${totongoing_n + list.n_ongoing}" />
								<c:set var="totcompleted_n" value="${totcompleted_n + list.n_completed}" />
								<c:set var="totcreated_e" value="${totcreated_e + list.e_created}" />
								<c:set var="totunstarted_e" value="${totunstarted_e + list.e_unstarted}" />
								<c:set var="totongoing_e" value="${totongoing_e + list.e_ongoing}" />
								<c:set var="totcompleted_e" value="${totcompleted_e + list.e_completed}" />
								<c:set var="totcreated_l" value="${totcreated_l + list.l_created}" />
								<c:set var="totunstarted_l" value="${totunstarted_l + list.l_unstarted}" />
								<c:set var="totongoing_l" value="${totongoing_l + list.l_ongoing}" />
								<c:set var="totcompleted_l" value="${totcompleted_l + list.l_completed}" />
								<c:set var="totcreated_p" value="${totcreated_p + list.p_created}" />
								<c:set var="totunstarted_p" value="${totunstarted_p + list.p_unstarted}" />
								<c:set var="totongoing_p" value="${totongoing_p + list.p_ongoing}" />
								<c:set var="totcompleted_p" value="${totcompleted_p + list.p_completed}" />
								<c:set var="totworkcode" value="${totworkcode + list.totworkcode}" />
								<c:set var="totgeorefwrkcd" value="${totgeorefwrkcd + list.totgeorefwrkcd}" />
								<c:set var="totnongeorefwrkcd" value="${totnongeorefwrkcd + list.totnongeorefwrkcd}" />
							</c:forEach>
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_n}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_n}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_e}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_e}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_l}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_l}" /></b></td>
			
								<td align="right" class="table-primary"><b><c:out value="${totcreated_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totunstarted_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totongoing_p}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totcompleted_p}" /></b></td>
								
								<td align="right" class="table-primary"><b><c:out value="${totworkcode}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totgeorefwrkcd}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totnongeorefwrkcd}" /></b></td>
							</tr>
		
	</tbody>
  </table>
</c:if> 

<c:if test = "${allProjDtlListsize gt 0 }">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report GT2 - Project Wise Work Code Status with Geotagging Details for Project "<c:out value="${projname}"/>" of District "<c:out value="${distname}"/>" of State "<c:out value="${stname}"/>"</h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8" >
	<button name="exportExcel" id="exportExcel" onclick="downloadProjDetailExcel('${projid}','${projname}','${distname}','${stname}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadProjDetailPDF('${projid}','${projname}','${distname}','${stname}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 20%;">Work Code</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 9%;">Head Name</th>
      		<th rowspan = "2" style="text-align:center; vertical-align: middle; width: 10%;">Activity Name</th>
      		<th colspan = "3" style="text-align:center; vertical-align: middle; width: 10%;">Stage</th>
       	</tr>
       	<tr>
       		<th style="text-align:center; vertical-align: middle; width: 9%;">Pre Implementation</th>
       		<th style="text-align:center; vertical-align: middle; width: 2%;">Mid Implementation</th>
      		<th style="text-align:center; vertical-align: middle; width: 20%;">Post Implementation</th>
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
		</tr>
		<c:set var ="count" value ="1"/>
		<c:set var ="wrkcd" value ="0"/>
		<c:set var = "hdcd" value = ""/>
		<c:set var ="epa" value ="0"/>
		<c:set var ="liv" value ="0"/>
		<c:set var = "prod" value = "0"/>
		<c:set var = "dpr" value = "0"/>
		<c:set var ="totpre" value ="0"/>
		<c:set var ="totmid" value ="0"/>
		<c:set var = "totpost" value = "0"/>
							
							<c:forEach var="list" items="${allProjDtlList}">
								<c:set var = "hdcd" >${list.headcode}</c:set>
								<c:if test="${hdcd eq  'E'}">
									<c:if test="${epa eq 0}">
										<tr style ="background-color:grey">
											<td colspan="8" class="text-center"><b>EPA</b></td>
										</tr>
										<c:set var="pre" value="0" />
										<c:set var="mid" value="0" />
										<c:set var="post" value="0" />
									</c:if>
									<tr>
										<c:if test="${list.workcode ne  wrkcd}">
											<td><c:out value="${count}" /></td>
											<td><c:out value="${list.workcode}" /></td>
											<td><c:out value="${list.headdes}" /></td>
											<td><c:out value="${list.actdes}" /></td>
											<c:forEach var="map" items="${stgeMap}">
												<c:if test="${list.workcode eq fn:substringAfter(map.key,',')}">
														<td style="text-align: center;">
														<c:forEach var="submap" items="${map.value}">
															<br/><a href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/?collection_sno=${submap.key}" target="_blank"><c:out value="${submap.value} " /></a>
														</c:forEach>
														</td>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Pre Implementation'}"> --%>
<%-- 														<c:set var="pre" value="${pre + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Mid Implementation'}"> --%>
<%-- 														<c:set var="mid" value="${mid + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Post Implementation'}"> --%>
<%-- 														<c:set var="post" value="${post + map.value}" /> --%>
<%-- 													</c:if> --%>
												</c:if>
												
											</c:forEach>
											<c:set var="count" value="${count + 1}" />
											<c:set var="wrkcd" value="${list.workcode}" />
										</c:if>
									</tr>
									<c:set var ="epa" value ="1"/>
								</c:if>
							</c:forEach>
<%-- 							<c:if test="${epa eq 1}"> --%>
<!-- 								<tr> -->
<!-- 									<th colspan="4" class="text-center">Total</th> -->
<%-- 									<th class="text-center"><c:out value="${pre}" /></th> --%>
<%-- 									<th class="text-center"><c:out value="${mid}" /></th> --%>
<%-- 									<th class="text-center"><c:out value="${post}" /></th> --%>
<!-- 								</tr> -->
<%-- 								<c:set var="totpre" value="${totpre + pre}" /> --%>
<%-- 								<c:set var="totmid" value="${totmid + mid}" /> --%>
<%-- 								<c:set var="totpost" value="${totpost + post}" /> --%>
<%-- 							</c:if> --%>
							<c:forEach var="list" items="${allProjDtlList}">
								<c:set var="hdcd">${list.headcode}</c:set>
								<c:if test="${hdcd eq  'L'}">
									<c:if test="${liv eq 0}">
										<tr style ="background-color:grey">
											<td colspan="8" class="text-center"><b>Livelihood</b></td>
										</tr>
										<c:set var="pre" value="0" />
										<c:set var="mid" value="0" />
										<c:set var="post" value="0" />
									</c:if>
									<tr>
										<c:if test="${list.workcode ne  wrkcd}">
											<td><c:out value="${count}" /></td>
											<td><c:out value="${list.workcode}" /></td>
											<td><c:out value="${list.headdes}" /></td>
											<td><c:out value="${list.actdes}" /></td>
											<c:forEach var="map" items="${stgeMap}">
												<c:if test="${list.workcode eq fn:substringAfter(map.key,',')}">
													<td style="text-align: center;">
														<c:forEach var="submap" items="${map.value}">
															<br/><a href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/?collection_sno=${submap.key}" target="_blank"><c:out value="${submap.value} " /></a>
														</c:forEach>
													</td>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Pre Implementation'}"> --%>
<%-- 														<c:set var="pre" value="${pre + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Mid Implementation'}"> --%>
<%-- 														<c:set var="mid" value="${mid + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Post Implementation'}"> --%>
<%-- 														<c:set var="post" value="${post + map.value}" /> --%>
<%-- 													</c:if> --%>
												</c:if>
											</c:forEach>
											<c:set var="count" value="${count + 1}" />
											<c:set var="wrkcd" value="${list.workcode}" />
										</c:if>
									</tr>
									<c:set var ="liv" value ="1"/>
								</c:if>
							</c:forEach>
<%-- 							<c:if test = "${liv eq 1}"> --%>
<!-- 							<tr> -->
<!-- 								<th colspan = "4" class="text-center">Total</th> -->
<%-- 								<th class="text-center"><c:out value = "${pre}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${mid}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${post}"/></th> --%>
<!-- 							</tr> -->
<%-- 							<c:set var="totpre" value="${totpre + pre}" /> --%>
<%-- 								<c:set var="totmid" value="${totmid + mid}" /> --%>
<%-- 								<c:set var="totpost" value="${totpost + post}" /> --%>
<%-- 							</c:if> --%>
							<c:forEach var="list" items="${allProjDtlList}">
								<c:set var="hdcd">${list.headcode}</c:set>
								<c:if test="${hdcd eq  'P'}">
									<c:if test="${prod eq 0}">
										<tr style ="background-color:grey">
											<td colspan="8" class="text-center"><b>Production</b></td>
										</tr>
										<c:set var="pre" value="0" />
										<c:set var="mid" value="0" />
										<c:set var="post" value="0" />
									</c:if>
									<tr>
										<c:if test="${list.workcode ne  wrkcd}">
											<td><c:out value="${count}" /></td>
											<td><c:out value="${list.workcode}" /></td>
											<td><c:out value="${list.headdes}" /></td>
											<td><c:out value="${list.actdes}" /></td>
											<c:forEach var="map" items="${stgeMap}">
												<c:if test="${list.workcode eq fn:substringAfter(map.key,',')}">
													<td style="text-align: center;">
														<c:forEach var="submap" items="${map.value}">
															<br/><a href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/?collection_sno=${submap.key}" target="_blank"><c:out value="${submap.value} " /></a>
														</c:forEach>
													</td>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Pre Implementation'}"> --%>
<%-- 														<c:set var="pre" value="${pre + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Mid Implementation'}"> --%>
<%-- 														<c:set var="mid" value="${mid + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Post Implementation'}"> --%>
<%-- 														<c:set var="post" value="${post + map.value}" /> --%>
<%-- 													</c:if> --%>
												</c:if>
											</c:forEach>
											<c:set var="count" value="${count + 1}" />
											<c:set var="wrkcd" value="${list.workcode}" />
										</c:if>
									</tr>
									<c:set var ="prod" value ="1"/>
								</c:if>
							</c:forEach>
<%-- 							<c:if test = "${prod eq 1}"> --%>
<!-- 							<tr> -->
<!-- 								<th colspan = "4" class="text-center">Total</th> -->
<%-- 								<th class="text-center"><c:out value = "${pre}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${mid}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${post}"/></th> --%>
<!-- 							</tr> -->
<%-- 							<c:set var="totpre" value="${totpre + pre}" /> --%>
<%-- 								<c:set var="totmid" value="${totmid + mid}" /> --%>
<%-- 								<c:set var="totpost" value="${totpost + post}" /> --%>
<%-- 							</c:if> --%>
							<c:forEach var="list" items="${allProjDtlList}">
								<c:set var="hdcd">${list.headcode}</c:set>
								<c:if test="${hdcd.matches('[0-9]+') || hdcd eq 'C'}">
									<c:if test="${dpr eq 0}">
										<tr style ="background-color:grey">
											<td colspan="8" class="text-center"><b>NRM</b></td>
										</tr>
										<c:set var="pre" value="0" />
										<c:set var="mid" value="0" />
										<c:set var="post" value="0" />
									</c:if>
									<tr>
										<c:if test="${list.workcode ne  wrkcd}">
											<td><c:out value="${count}" /></td>
											<td><c:out value="${list.workcode}" /></td>
											<td><c:out value="${list.headdes}" /></td>
											<td><c:out value="${list.actdes}" /></td>
											<c:forEach var="map" items="${stgeMap}">
												<c:if test="${list.workcode eq fn:substringAfter(map.key,',')}">
													<td style="text-align: center;">
														<c:forEach var="submap" items="${map.value}">
															<br/><a href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/?collection_sno=${submap.key}" target="_blank"><c:out value="${submap.value} " /></a>
														</c:forEach>
													</td>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Pre Implementation'}"> --%>
<%-- 														<c:set var="pre" value="${pre + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Mid Implementation'}"> --%>
<%-- 														<c:set var="mid" value="${mid + map.value}" /> --%>
<%-- 													</c:if> --%>
<%-- 													<c:if test = "${fn:substringBefore(map.key,',') eq 'Post Implementation'}"> --%>
<%-- 														<c:set var="post" value="${post + map.value}" /> --%>
<%-- 													</c:if> --%>
												</c:if>
											</c:forEach>
											<c:set var="count" value="${count + 1}" />
											<c:set var="wrkcd" value="${list.workcode}" />
										</c:if>
									</tr>
									<c:set var ="dpr" value ="1"/>
								</c:if>
							</c:forEach>
<%-- 							<c:if test = "${dpr eq 1}"> --%>
<!-- 							<tr> -->
<!-- 								<th colspan = "4" class="text-center">Total</th> -->
<%-- 								<th class="text-center"><c:out value = "${pre}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${mid}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${post}"/></th> --%>
<!-- 							</tr> -->
<%-- 							<c:set var="totpre" value="${totpre + pre}" /> --%>
<%-- 								<c:set var="totmid" value="${totmid + mid}" /> --%>
<%-- 								<c:set var="totpost" value="${totpost + post}" /> --%>
<%-- 							</c:if> --%>
<!-- 							<tr> -->
<!-- 								<th colspan = "4" class="text-center">Grand Total</th> -->
<%-- 								<th class="text-center"><c:out value = "${totpre}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${totmid}"/></th> --%>
<%-- 								<th class="text-center"><c:out value = "${totpost}"/></th> --%>
<!-- 							</tr> -->
						</tbody>
  </table>
</div>
</div>
</div>
</c:if> 


<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>