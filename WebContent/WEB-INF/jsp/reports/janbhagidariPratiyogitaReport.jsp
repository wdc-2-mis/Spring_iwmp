<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>janbhagidariPratiyogitaReport</title>

<script type="text/javascript">

$(document).on('change', '#state', function(e) {

	e.preventDefault();
	
	$("select#district")[0].selectedIndex = 0;
	$("select#project")[0].selectedIndex = 0;
	
	});

function getjanbhagidariPratiyogitaRpt(){
	
		
		document.janbhagidari.action="janbhagidariPratiyogitaALLReport";
		document.janbhagidari.method="post";
		document.janbhagidari.submit();
	
}

	function downloadPDF(){
		document.projLocDtlRpt.action="downloadprojectLocationDetailsPDF";
		document.projLocDtlRpt.method="post";
		document.projLocDtlRpt.submit();
	}

	function exportExcel(){
		document.projLocDtlRpt.action="downloadExcelProjLocDtlRpt";
		document.projLocDtlRpt.method="post";
		document.projLocDtlRpt.submit();
	}
	
	 function openPopup() {
         window.open("janbhagidariPratiyogitaPopup.jsp", "PopupWindow", "width=600,height=400");
     }

	
	
</script>

</head>
<body>



<!-- <div  style="text-align:center;"><h5>Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee </h5></div> -->



<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>
<label id="head"> State, District and Project-wise Janbhagidari Cup under WDC-PMKSY2.0 Report</label></h5></div>


<br>
<div class ="card">
<div class="row">
<div class="col-3" ></div>
<div class="col-9">

<div class="table-responsive">

 <form:form autocomplete="off" name="janbhagidari" id="janbhagidari"   action="janbhagidariPratiyogitaReport" method="get">
 
      <table >
        <tr align="center">
        
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();">
              		<option value="0" style="text-align:center;">--Select All--</option>
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
              <select name="district" id="district" onchange="this.form.submit();">
              		<option value="0" style="text-align:center;">--Select All--</option>
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
          
          <td class="label">Project <span style="color: red;">*</span></td>
          <td align="center">
              <select name="project" id="project" >
              		<option value="0" style="text-align:center;">--Select All--</option>
                  	<c:if test="${not empty projectList}">
               			<c:forEach items="${projectList}" var="lists">
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
          
         
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="getjanbhagidariPratiyogitaRpt();" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="container-fluid">
	
<%-- 	<c:if test="${dataListSize>0}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if> --%>
        <table class="table">
          <tr>
            <td>
            
            	<table id="dtBasicExample" class="table table-bordered table-striped table-highlight w-auto">
            	<thead>
              		<tr align="center">
              			<th align="center">S.No.</th>
              			<th align="center">State Name</th>
              			<th align="center">District Name</th>
		              	<th align="center">Project Name</th>
                        <th align="center">Total No. of Gram Panchayat</th>
                        <th align="center">Total No. of Villages</th>
                        <th align="center">Total Area Allocated for Project (ha.) </th>
                        <th align="center">Total Project Outlay of WDC PMKSY (Rs. in Lakh) </th>
                        <th align="center">No. of NGOs Engaged in Project</th>
                        <th align="center">No. of Gram Panchayat to be Covered by NGO</th>
                        <th align="center">No. of Villages to be Covered by NGO</th>
                      	<th align="center">No. of Gram Panchayat where SWCK Account is Opened</th>
                      	<th align="center">Total Project Expenditure of WDC PMKSY 2.0 (In Lakh)</th>
                      	<th align="center">Percentage of Expenditure (%)</th>
               		</tr>	
               	</thead>
               	<tbody id="janbhagidariPratiyogitaReport">	
               	<c:set var="st" value="" />
               	<c:set var="proj" value="" />
               	<c:set var="dist" value="" />
               		<c:if test="${dataListSize>0}">
						<c:forEach items="${dataList}" var="data" varStatus="count">
						
								<c:if test ="${st ne data.stname && count.count ne 1}">
									<td align="right" class="table-primary" colspan="4"><b>State Total </b></td>
									<td align="right" class="table-primary" ><b><c:out value='${tno_gp}' /> </b></td>
									<td align="right" class="table-primary" ><b><c:out value='${tno_village}' /> </b></td>
									<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${tproj_area}' /></fmt:formatNumber></td>
									<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${tproj_outlay}' /></fmt:formatNumber></td>
									<td align="right" class="table-primary" ><b><c:out value='${tno_ngo_name}' /> </b></td>
									<td align="right" class="table-primary" ><b><c:out value='${tno_ngo_gp}' /> </b></td>
									<td align="right" class="table-primary" ><b><c:out value='${tno_ngo_vill}' /> </b></td>
									<td align="right" class="table-primary" ><b><c:out value='${tno_swck_gp}' /> </b></td>
									<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${tfund_expenditure}' /></fmt:formatNumber></td>
									<td align="right" class="table-primary" ></td>
									
									<c:set var="tno_gp" value="0" />
									<c:set var="tno_gp" value="${tno_gp + data.no_gp}" />
									<c:set var="tno_village" value="0" />
									<c:set var="tno_village" value="${tno_village + data.no_village}" />
									<c:set var="tproj_area" value="0" />
									<c:set var="tproj_area" value="${tproj_area + data.proj_area}" />
									<c:set var="tproj_outlay" value="0" />
									<c:set var="tproj_outlay" value="${tproj_outlay + data.proj_outlay}" />
									
									<c:set var="tno_ngo_name" value="0" />
									<c:set var="tno_ngo_name" value="${tno_ngo_name + data.no_ngo_name}" />
									<c:set var="tno_ngo_gp" value="0" />
									<c:set var="tno_ngo_gp" value="${tno_ngo_gp + data.no_ngo_gp}" />
									<c:set var="tno_ngo_vill" value="0" />
									<c:set var="tno_ngo_vill" value="${tno_ngo_vill + data.no_ngo_vill}" />
									<c:set var="tno_swck_gp" value="0" />
									<c:set var="tno_swck_gp" value="${tno_swck_gp + data.no_swck_gp}" />
									<c:set var="tfund_expenditure" value="0" />
									<c:set var="tfund_expenditure" value="${tfund_expenditure + data.fund_expenditure}" />
							</c:if>
						
							<tr>
								<td>
 								<c:out value='${count.count}' />
 								</td>
 								<c:choose>
									<c:when test="${st ne data.stname}">
										
										<td> <c:out value="${data.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${dist ne data.distname}">
										<c:set var="dist" value="${data.distname}" />
										<td> <c:out value="${data.distname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${proj ne data.proj_name}">
										<c:set var="proj" value="${data.proj_name}" />
										<td> <c:out value="${data.proj_name}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
 							<%-- 	<td class="text-right"> <c:out value="${data.stname}" /></td>	
 								<td class="text-right"> <c:out value="${data.distname}" /></td>
 								<td class="text-right"> <c:out value="${data.proj_name}" /></td> --%>
 								<td class="text-right"> <c:out value="${data.no_gp}" /></td>
								<td class="text-right"> <c:out value="${data.no_village}" /></td>
		 						<td class="text-right"> <c:out value="${data.proj_area}" /></td>
								<td class="text-right"> <c:out value="${data.proj_outlay}" /></td>
								<td class="text-right"> <a href="#" data-id="${data.proj_id}" class='activity' data-toggle="modal" style ="color: blue;"><c:out value="${data.no_ngo_name}" /> </a></td>
								<td class="text-right"> <c:out value="${data.no_ngo_gp}" /></td>
								<td class="text-right"> <c:out value="${data.no_ngo_vill}" /></td>
								<td class="text-right"><a href="#" data-id="${data.proj_id}" class='swck' data-toggle="modal" style ="color: blue;"> <c:out value="${data.no_swck_gp}" /></a></td>
 								<td class="text-right"> <c:out value="${data.fund_expenditure}" /></td>
								<td class="text-right"> <c:out value="${data.fund_per_exp}" /> <b>%</b></td>
 							</tr>
 							
 							<c:if test = "${st eq data.stname || count.count eq 1}">
									
									<c:set var="tno_gp" value="${tno_gp + data.no_gp}" />
									<c:set var="tno_village" value="${tno_village + data.no_village}" />
									<c:set var="tproj_area" value="${tproj_area + data.proj_area}" />
									<c:set var="tproj_outlay" value="${tproj_outlay + data.proj_outlay}" />
									<c:set var="tno_ngo_name" value="${tno_ngo_name + data.no_ngo_name}" />
									<c:set var="tno_ngo_gp" value="${tno_ngo_gp + data.no_ngo_gp}" />
									<c:set var="tno_ngo_vill" value="${tno_ngo_vill + data.no_ngo_vill}" />
									<c:set var="tno_swck_gp" value="${tno_swck_gp + data.no_swck_gp}" />
									<c:set var="tfund_expenditure" value="${tfund_expenditure + data.fund_expenditure}" />
							</c:if>
									<c:if test = "${ count.count eq fn:length(dataList)}">
									<tr>
										<td align="right" class="table-primary" colspan="4"><b>State Total </b></td>
										<td align="right" class="table-primary" ><b><c:out value='${tno_gp}' /> </b></td>
										<td align="right" class="table-primary" ><b><c:out value='${tno_village}' /> </b></td>
										<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${tproj_area}' /></fmt:formatNumber></td>
										<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${tproj_outlay}' /></fmt:formatNumber></td>
										<td align="right" class="table-primary" ><b><c:out value='${tno_ngo_name}' /> </b></td>
										<td align="right" class="table-primary" ><b><c:out value='${tno_ngo_gp}' /> </b></td>
										<td align="right" class="table-primary" ><b><c:out value='${tno_ngo_vill}' /> </b></td>
										<td align="right" class="table-primary" ><b><c:out value='${tno_swck_gp}' /> </b></td>
										<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${tfund_expenditure}' /></fmt:formatNumber></td>
										<td align="right" class="table-primary" ></td>
									</tr>
									
									</c:if>
									<c:set var="st" value="${data.stname}" />
						</c:forEach>
						
						<tr>
							<td align="right" class="table-primary" colspan="4"><b>Grand Total </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${totno_gp}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${totno_village}' /> </b></td>
							<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${totproj_area}' /></fmt:formatNumber></td>
							<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${totproj_outlay}' /></fmt:formatNumber></td>
							<td align="right" class="table-primary" ><b><c:out value='${totno_ngo_name}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${totno_ngo_gp}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${totno_ngo_vill}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${totno_swck_gp}' /> </b></td>
							<td align="right" class="table-primary"><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${totfund_expenditure}' /></fmt:formatNumber></td>
							<td align="right" class="table-primary" ></td>
						</tr> 
						
					</c:if>
              </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td>
            	<c:if test="${dataListSize<=0 }">
   					<table width="100%" border="0" cellspacing="0" cellpadding="0">
          				<tr class="tabs">
            				<td><center><span style="color: red;"> No Data Found !</span></center></td>
            				
          				</tr>
        			</table>
  				</c:if>
  			</td>
          </tr>
        </table>
     
    <br>
	</div>
	</div>

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
<script src='<c:url value="/resources/js/janbhagidariPratiyogitaReport.js" />'></script>
</body>
</html>