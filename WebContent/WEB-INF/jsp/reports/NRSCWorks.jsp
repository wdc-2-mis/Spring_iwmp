<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>
<script src='<c:url value="/resources/js/trainingCapicityBuilding.js" />'></script>

<title>Report GT1- Total No. of Work Code Shared with NRSC for Geo References</title>

<script type="text/javascript">

function exportPDF(){
	document.getreports.action="downloadPDFNRSCWorks";
	document.getreports.method="post";
	document.getreports.submit();
}

function exportExcel(){
	document.getreports.action="downloadExcelNRSCWorks";
	document.getreports.method="post";
	document.getreports.submit();
}

</script>
</head>

<form action = "downloadExcelNRSCWorks" method = "post" name = "getreports" id = "getreports">
	<br>
<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<div class="offset-md-3 col-6 formheading" style="textl-align: center;">
				<h5>Report GT1- Total No. of Work Code Shared with NRSC for Geotagging</h5>
			</div>
		</div>
	</div>
</div>	
	<br>
</form>	

	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="exportPDF()" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>	

<table id = "tblReport" class = "table">
	<thead>
		<tr>
			<th rowspan=2 class="text-center">S.No.</th>
			<th rowspan=2 class="text-center">State Name</th>
			<th rowspan=2 class="text-center">Total Projects</th>
			<th colspan=5 class="text-center">Total NRM Works</th>
			<th colspan=5 class="text-center">Total EPA Works</th>
			<th colspan=5 class="text-center">Total Livelihood Works</th>
			<th colspan=5 class="text-center">Total Production Works</th>
			<th colspan=5 class="text-center">Grand Total (NRM+EPA+LIV+PROD)</th>
		</tr>
		<tr>
			<th class="text-center">Total Works</th>
			<th class="text-center">Not Started (Status Not Submitted)</th>
			<th class="text-center">Ongoing</th>
			<th class="text-center">Completed</th>
			<th class="text-center">Foreclosed</th>
			
			<th class="text-center">Total Works</th>
			<th class="text-center">Not Started (Status Not Submitted)</th>
			<th class="text-center">Ongoing</th>
			<th class="text-center">Completed</th>
			<th class="text-center">Foreclosed</th>
			
			<th class="text-center">Total Works</th>
			<th class="text-center">Not Started (Status Not Submitted)</th>
			<th class="text-center">Ongoing</th>
			<th class="text-center">Completed</th>
			<th class="text-center">Foreclosed</th>
			
			<th class="text-center">Total Works</th>
			<th class="text-center">Not Started (Status Not Submitted)</th>
			<th class="text-center">Ongoing</th>
			<th class="text-center">Completed</th>
			<th class="text-center">Foreclosed</th>
			
			<th class="text-center">Total Works</th>
			<th class="text-center">Not Started (Status Not Submitted)</th>
			<th class="text-center">Ongoing</th>
			<th class="text-center">Completed</th>
			<th class="text-center">Foreclosed</th>
		</tr>
	</thead>
	
	<tbody id = "nrscWorksRptTbody">
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
			<th class="text-center">24</th>
			<th class="text-center">25</th>
			<th class="text-center">26</th>
			<th class="text-center">27</th>
			<th class="text-center">28</th>
		</tr>
		<c:forEach items="${nrscWorksList}" var="works" varStatus="sno">
			<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td><a href = "getNRSCDistWorks?stcd=${works.st_code}&stname=${works.st_name}"><c:out value='${works.st_name}' /></a></td>
					<td class="text-right"><c:out value="${works.projects}" /></td>
					
					<td class="text-right"><c:out value="${works.n_created}" /></td>
					<td class="text-right"><c:out value="${works.n_unstarted}" /></td>
					<td class="text-right"><c:out value="${works.n_ongoing}" /></td>
					<td class="text-right"><c:out value="${works.n_completed}" /></td>
					<td class="text-right"><c:out value="${works.n_forclosed}" /></td>
					
					<td class="text-right"><c:out value="${works.e_created}" /></td>
					<td class="text-right"><c:out value="${works.e_unstarted}" /></td>
					<td class="text-right"><c:out value="${works.e_ongoing}" /></td>
					<td class="text-right"><c:out value="${works.e_completed}" /></td>
					<td class="text-right"><c:out value="${works.e_forclosed}" /></td>
					 
					<td class="text-right"><c:out value="${works.l_created}" /></td>
					<td class="text-right"><c:out value="${works.l_unstarted}" /></td>
					<td class="text-right"><c:out value="${works.l_ongoing}" /></td>
					<td class="text-right"><c:out value="${works.l_completed}" /></td>
					<td class="text-right"><c:out value="${works.l_forclosed}" /></td>
					
					<td class="text-right"><c:out value="${works.p_created}" /></td>
					<td class="text-right"><c:out value="${works.p_unstarted}" /></td>
					<td class="text-right"><c:out value="${works.p_ongoing}" /></td>
					<td class="text-right"><c:out value="${works.p_completed}" /></td>
					<td class="text-right"><c:out value="${works.p_forclosed}" /></td>
					
					<td class="text-right"><c:out value="${works.t_created}" /></td>
					<td class="text-right"><c:out value="${works.t_unstarted}" /></td>
					<td class="text-right"><c:out value="${works.t_ongoing}" /></td>
					<td class="text-right"><c:out value="${works.t_completed}" /></td>
					<td class="text-right"><c:out value="${works.t_forclosed}" /></td>
			</tr>
			
			<c:set var = "totalprojects"
			value = "${totalprojects + works.projects}" />
			
			<c:set var = "totalcreated_n"
			value = "${totalcreated_n + works.n_created}" />
			<c:set var = "totalunstarted_n"
			value = "${totalunstarted_n + works.n_unstarted}" />
			<c:set var = "totalongoing_n"
			value = "${totalongoing_n + works.n_ongoing}" />
			<c:set var = "totalcompleted_n"
			value = "${totalcompleted_n + works.n_completed}" />
			<c:set var = "totalforclosed_n"
			value = "${totalforclosed_n + works.n_forclosed}" />
			
			<c:set var = "totalcreated_e"
			value = "${totalcreated_e + works.e_created}" />
			<c:set var = "totalunstarted_e"
			value = "${totalunstarted_e + works.e_unstarted}" />
			<c:set var = "totalongoing_e"
			value = "${totalongoing_e + works.e_ongoing}" />
			<c:set var = "totalcompleted_e"
			value = "${totalcompleted_e + works.e_completed}" />
			<c:set var = "totalforclosed_e"
			value = "${totalforclosed_e + works.e_forclosed}" />
			
			<c:set var = "totalcreated_l"
			value = "${totalcreated_l + works.l_created}" />
			<c:set var = "totalunstarted_l"
			value = "${totalunstarted_l + works.l_unstarted}" />
			<c:set var = "totalongoing_l"
			value = "${totalongoing_l + works.l_ongoing}" />
			<c:set var = "totalcompleted_l"
			value = "${totalcompleted_l + works.l_completed}" />
			<c:set var = "totalforclosed_l"
			value = "${totalforclosed_l + works.l_forclosed}" />
			
			<c:set var = "totalcreated_p"
			value = "${totalcreated_p + works.p_created}" />
			<c:set var = "totalunstarted_p"
			value = "${totalunstarted_p + works.p_unstarted}" />
			<c:set var = "totalongoing_p"
			value = "${totalongoing_p + works.p_ongoing}" />
			<c:set var = "totalcompleted_p"
			value = "${totalcompleted_p + works.p_completed}" />
			<c:set var = "totalforclosed_p"
			value = "${totalforclosed_p + works.p_forclosed}" />
			
			<c:set var = "totalcreated_t"
			value = "${totalcreated_t + works.t_created}" />
			<c:set var = "totalunstarted_t"
			value = "${totalunstarted_t + works.t_unstarted}" />
			<c:set var = "totalongoing_t"
			value = "${totalongoing_t + works.t_ongoing}" />
			<c:set var = "totalcompleted_t"
			value = "${totalcompleted_t + works.t_completed}" />
			<c:set var = "totalforclosed_t"
			value = "${totalforclosed_t + works.t_forclosed}" />
			
		</c:forEach>
<%-- 		<c:set var = "nettotal" --%>
<%-- 			value = "${nettotal + totalstarted + totalepa + totallivelihood + totalproduction}" /> --%>
		<tr>
			<td colspan="2" align="right" class="table-primary"><b>Total</b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalprojects}" /></b></td>
			
			<td align="right" class="table-primary"><b><c:out value="${totalcreated_n}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalunstarted_n}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalongoing_n}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalcompleted_n}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalforclosed_n}" /></b></td>
			
			<td align="right" class="table-primary"><b><c:out value="${totalcreated_e}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalunstarted_e}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalongoing_e}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalcompleted_e}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalforclosed_e}" /></b></td>
			
			<td align="right" class="table-primary"><b><c:out value="${totalcreated_l}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalunstarted_l}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalongoing_l}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalcompleted_l}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalforclosed_l}" /></b></td>
			
			<td align="right" class="table-primary"><b><c:out value="${totalcreated_p}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalunstarted_p}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalongoing_p}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalcompleted_p}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalforclosed_p}" /></b></td>
			
			<td align="right" class="table-primary"><b><c:out value="${totalcreated_t}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalunstarted_t}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalongoing_t}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalcompleted_t}" /></b></td>
			<td align="right" class="table-primary"><b><c:out value="${totalforclosed_t}" /></b></td>
		</tr>
<!-- 		<tr> -->
<!-- 		<td colspan="5" align="right" class="table-primary"><b>Total Works</b></td> -->
<%-- 			<td colspan="3" align="left" class="table-primary"><b><c:out value="${nettotal}" /></b></td> --%>
<!-- 		</tr> -->
		
		<c:if test="${nrscWorksListSize==0}">
			<tr>
				<td align="center" colspan="28" class="required" style="color:red;"><b>Data Not Found</b></td>
			</tr>
		</c:if>
	</tbody>
</table>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

</html>