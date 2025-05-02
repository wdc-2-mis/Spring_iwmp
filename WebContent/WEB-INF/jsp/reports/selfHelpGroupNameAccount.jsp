<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>selfHelpGroupNameAccount</title>

<script type="text/javascript">

$(document).on('change', '#state', function(e) {
	
	e.preventDefault();
	
	$("select#district")[0].selectedIndex = 0;
	$("select#project")[0].selectedIndex = 0;
	
	});


function getselfHelpGroupNameAccountRpt(){
	
		
		document.selfHelpGroupNameAccount.action="selfHelpGroupNameAccountReport";
		document.selfHelpGroupNameAccount.method="post";
		document.selfHelpGroupNameAccount.submit();
	
}

	function downloadPDF(){
		document.selfHelpGroupNameAccount.action="selfHelpGroupNameAccountReportPDF";
		document.selfHelpGroupNameAccount.method="post";
		document.selfHelpGroupNameAccount.submit();
	}

	function exportExcel(){
		document.selfHelpGroupNameAccount.action="selfHelpGroupNameAccountReportExcel";
		document.selfHelpGroupNameAccount.method="post";
		document.selfHelpGroupNameAccount.submit();
	}
	
	
	
	
</script>

</head>
<body>



<!-- <div  style="text-align:center;"><h5>Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee </h5></div> -->



<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>
<label id="head"> State, District and Project-wise self Help Group Name with Bank Account Details</label></h5></div>


<br>
<div class ="card">
<div class="row">
<div class="col-2" ></div>
<div class="col-9">

<div class="table-responsive">

 <form:form autocomplete="off" name="selfHelpGroupNameAccount" id="selfHelpGroupNameAccount"   action="selfHelpGroupNameAccountReport" method="get">
 
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
          <td class="label">SHG Type <span style="color: red;">*</span></td>
          <td align="center">
              <select name="shg" id="shg" >
                  	
               			<c:forEach items="${shgType}" var="lists">
               				<c:if test="${lists.key eq shg}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne shg}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					
              </select>
          </td>
         
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="getselfHelpGroupNameAccountRpt();" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="container-fluid">
	
 	<c:if test="${dataListSize>0}">
	 <button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button> 
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if> 
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
		              	<th align="center">Registration Date</th>
                        <th align="center">SHG Name</th>
                        <th align="center">Account Number</th>
                        <th align="center">IFSC Code </th>
                        <th align="center">SHG Type </th>
               		</tr>	
               	</thead>
               	<tbody id="janbhagidariPratiyogitaReport">	
               	<c:set var="st" value="" />
               	<c:set var="proj" value="" />
               	<c:set var="dist" value="" />
               		<c:if test="${dataListSize>0}">
						<c:forEach items="${dataList}" var="data" varStatus="count">
							<tr>
								<td>
 								<c:out value='${count.count}' />
 								</td>
 								<c:choose>
									<c:when test="${st ne data.st_name}">
										<c:set var="st" value="${data.st_name}" />
										<td> <c:out value="${data.st_name}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${dist ne data.dist_name}">
										<c:set var="dist" value="${data.dist_name}" />
										<td> <c:out value="${data.dist_name}" /></td>
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
 							<%--<td class="text-right"> <c:out value="${data.stname}" /></td>	
 								<td class="text-right"> <c:out value="${data.distname}" /></td>
 								<td class="text-right"> <c:out value="${data.proj_name}" /></td> --%>
 								<td> <c:out value="${data.reg_date}" /></td>
								<td> <c:out value="${data.name}" /></td>
		 						<td> <c:out value="${data.account_detail}" /></td>
								<td> <c:out value="${data.ifsc_code}" /></td>
								<td> 
								
								<c:if test="${data.group_type eq 'newSHG' }">
									<c:out value="New SHG" />
								</c:if>
								
								<c:if test="${data.group_type eq 'oldSHG' }">
									<c:out value="Old SHG" />
								</c:if>
							<%-- 	<c:out value="${data.group_type}" /> --%>
								
								
								
								
								</td>
								
 							</tr>
									
						</c:forEach>
						
					
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
<%-- <script src='<c:url value="/resources/js/janbhagidariPratiyogitaReport.js" />'></script> --%>
</body>
</html>