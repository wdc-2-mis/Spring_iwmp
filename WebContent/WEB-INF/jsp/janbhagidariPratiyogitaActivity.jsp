<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/janbhagidariPratiyogitaActivity.js" />'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
<script type="text/javascript">
function validatePercentage(input) {
    let val = input.value.trim();

    if (!/^\d*\.?\d{0,4}$/.test(val)) {
        input.value = val.slice(0, -1);  
        return;
    }

    let num = parseFloat(val);

    if (val !== "" && (isNaN(num) || num < 0 || num > 100)) {
        alert("Percentage must be between 0 and 100.");
        input.value = "";
    }
}
</script>

</head>

<body>
<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed - Janbhagidari Activities Cup under WDC-PMKSY2.0</h4> </div>
		
		<form:form autocomplete="off" method="post" name="janbhagidari" id="janbhagidari" action="saveJanbhagidariPratiyogita" modelAttribute="janbhagidari" >
			  <hr/>
			  
			 
			<div class="row">
			<div class="form-group col-3">
    <label for="state"><b>State Name:</b></label><br/>
    <c:out value="${stateName}" /><br/>
</div>

<div class="form-group col-3">
    <label for="district"><b>District Name:</b></label><br/>

    <c:if test="${userType == 'SL'}">
        <span class="projectError"></span>
        <select class="form-control district" id="district" name="district" required>
            <option value="">--All District--</option>
            <c:forEach items="${distList}" var="dist"> 
                <option value="${dist.key}"><c:out value="${dist.value}" /></option>
            </c:forEach>
        </select>
    </c:if>

    <c:if test="${userType == 'PI'}">
        <c:out value="${distName}" /><br/>
        <input type="hidden" id="district" name="district" value="${distCode}">
    </c:if>
</div>

<div class="form-group col-3">
    <label for="projid"><b>Project Name:</b></label><br/>

    <c:if test="${userType == 'SL'}">
        <span class="activityError"></span>
        <select class="form-control activity" id="projid" name="projid">
            <option value="">--Select Project--</option>
            
        </select>
    </c:if>

    <c:if test="${userType == 'PI'}">
        <span class="activityError"></span>
        <select class="form-control activity" id="projid" name="projid" required>
            <option value="">--Select Project--</option>
            <c:forEach items="${projList}" var="proj"> 
                <option value="${proj.key}"><c:out value="${proj.value}" /></option>
            </c:forEach>
        </select>
    </c:if>
</div>
			
	    		
    		<div class="form-group col-3">
    			
    		</div>
   </div>
   <div class="form-row">
   <div class="form-group col">
 <table id = "tblReport" class = "table">
 <thead>
 
 <tr>
				<th rowspan="2" class="text-center" style="width:18%">Gram Panchayat</th>
				<th rowspan="2" class="text-center" style="width:18%">Village</th>
				<th rowspan="2" class="text-center" style="width:10%">Type of Work</th>
				<th rowspan="2" class="text-center" style="width:09%">Estimated Value of Work</th>
				<th colspan="3" class="text-center">Tentative Contribution Percentage (%)</th>
				<th rowspan="2" class="text-center">Work Completed</th>
				<th rowspan="2" class="text-center">Work Completed Date</th>
				<th rowspan="2" class="text-center">Action</th>
</tr>	

<tr>
                <th data-toggle="tooltip" title="Percentage by Villages">Villagers</th>
                <th data-toggle="tooltip" title="Percentage by NGOs">NGOs</th>
                <th data-toggle="tooltip" title="Percentage by Corporated">Corporates</th>

</tr>
</thead>			
 <tbody id="listvillageGPWiseTbody">
    <tr>
     	     <td>
     			<select id="ddlgp" name="ddlgp" class="ddlgp form-control" ><option value="">--Select Gram Panchayat--</option></select>
			<div class="selected-gp-list" style="margin-top: 5px; font-size: 14px; color: #333;"></div>
			</td>
			
			<td>
			   <select id="ddlvill" name="ddlvill" class="ddlvill form-control"><option value="">--Select Village--</option></select>
			<div class="selected-village-list" style="margin-top: 5px; font-size: 14px; color: #333;"></div>
			</td>
			
			<td style="width:15%">
			<select class="form-control workid" id="workid" name="workid" required>
            <option value="">--Select Work--</option>
            <c:forEach items="${WorkList}" var="work"> 
                <option value="${work.key}"><c:out value="${work.value}" /></option>
            </c:forEach>
            </select>
			</td>
			
			<td><input type="text" class="form-control estvalue" name="estvalue" id="estvalue" class="estvalue form-control" autocomplete="off"  onfocusin="decimalToFourPlace(event)" maxlength="15" /></td>
     		<td style="width:10%"><input type="text" class="form-control villagers" name="villagers" id="villagers" class="villagers form-control" autocomplete="off"   onfocusin="decimalToFourPlace(event)"  maxlength="5" /></td>
     		<td style="width:10%"><input type="text" class="form-control ngos" name="ngos" id="ngos" class="ngo form-control" autocomplete="off"   onfocusin="decimalToFourPlace(event)"  maxlength="5" /></td>
     		<td style="width:10%"><input type="text" class="form-control corporate" name="corporate" id="corporate" class="corporate form-control" autocomplete="off"  onfocusin="decimalToFourPlace(event)"  maxlength="5" /></td>
			
			<td>
			<select class="form-control compWork" name="compWork" id="compWork">
            <option value="">--Select--</option>
            <option value="Y">Yes</option>
            <option value="N">No</option>
            </select>
			</td>
			<td class="completedDate"></td>
			 <td style="vertical-align: bottom;">
                <button type="button" class="btn btn-success btnAddRow">+</button>
            </td>
     	
 </tr>
 
 <tr>
     		<td colspan=10 align="center">

     			<input type="button" class="btn btn-info" id="saveAsDraft" name="saveAsDraft" value ="Save As Draft"/>
     		</td>
     	</tr>
 </tbody>
 
 </table>   		
    		
  </div>
  </div>  		
    		
    		
    		</form:form>
    		</div>
    		
    <div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Draft List of Watershed - Janbhagidari Activities Cup under WDC-PMKSY2.0 (<c:out value="${stateName}"></c:out>)</h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="2">S.No.  &nbsp; <input type="checkbox" id="chkSelectAll" name="chkSelectAll" /></th> 
 								<th rowspan="2">District Name</th>
								<th rowspan="2">Project Name</th>
								<th rowspan="2">Gram Panchayat</th>
								<th rowspan="2">Village</th>
								<th rowspan="2">Type of Work</th>
								<th rowspan="2">Estimated Value of Work</th>
								<th colspan="3">Tentative Contribution Percentage (%)</th>
								<th rowspan="2">Work Completed</th>
								<th rowspan="2">Work Completed Date</th>
							</tr>
							
							<tr>
							   <th rowspan="2">Villagers</th> 
							   <th rowspan="2">NGOs</th>
							   <th rowspan="2">Corporates</th>
							</tr>
						
		</thead>
		<c:set var="proj" value="" />
        <c:set var="dist" value="" />

            <c:choose>
                <c:when test="${not empty dataList}">
                    <c:forEach var="record" items="${dataList}" varStatus="loop">
                        <tr>
                            <td>${loop.count}&nbsp;
                                <input type="checkbox" class="chkIndividual" id="${record.typeofwork_id}" name="${record.typeofwork_id}" value="${record.typeofwork_id}">
                            </td>
                            <td>${record.districtname}</td>
 							<td>${record.projname}</td>
                            <td>${record.gpname}</td>
                            <td>${record.villname}</td>
                            <td>${record.work_desc}</td>
                            <td>${record.es_work}</td>
                            <td>${record.village}</td>
                            <td>${record.ngo}</td>
                            <td>${record.corporate}</td>
                            <td>${record.work_status}</td>
                            <td><fmt:formatDate value="${record.workstatus_date}" pattern="dd-MM-yyyy" /></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="12" style="text-align: center;  color:red;" >Data Not Found</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            
        </table>
        <c:if test="${dataListSize ne 0}">
        <div class="form-group text-center">
     				<input type="button" class="btn btn-info" id="complete" name="complete" value ="Complete"/>
     				<input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/>
     	</div>
     	</c:if>
    </div>
</div>
		
		<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Complete List of Watershed - Janbhagidari Activities Cup under WDC-PMKSY2.0 (<c:out value="${stateName}"></c:out>)</h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="2">S.No.</th> 
 								<th rowspan="2">District Name</th>
								<th rowspan="2">Project Name</th>
								<th rowspan="2">Gram Panchayat</th>
								<th rowspan="2">Village</th>
								<th rowspan="2">Type of Work</th>
								<th rowspan="2">Estimated Value of Work</th>
								<th colspan="3">Tentative Contribution Percentage (%)</th>
								<th rowspan="2">Work Completed</th>
								<th rowspan="2">Work Completed Date</th>
							</tr>
							
							<tr>
							   <th rowspan="2">Villagers</th> 
							   <th rowspan="2">NGOs</th>
							   <th rowspan="2">Corporates</th>
							</tr>
						
		</thead>
		<c:set var="proj" value="" />
        <c:set var="dist" value="" />
			<c:forEach items="${compdataList}" var="data" varStatus="count">
 							<tr>
								
								<td>
 								<c:out value='${count.count}' />
 								</td>
 								<c:choose>
                                <c:when test="${dist ne data.districtname}">
                                    <c:set var="dist" value="${data.districtname}" />
                                    <td><c:out value="${data.districtname}" /></td>
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${proj ne data.projname}">
                                    <c:set var="proj" value="${data.projname}" />
                                    <td><c:out value="${data.projname}" /></td>
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
 								
 							<td class="text-right"> <c:out value="${data.gpname}" /></td>
 							<td class="text-right"> <c:out value="${data.villname}" /></td>
                            <td class="text-right"> <c:out value="${data.work_desc}" /></td>
                            <td class="text-right"> <c:out value="${data.es_work}" /></td>
                            <td class="text-right"> <c:out value="${data.village}" /></td>
                            <td class="text-right"> <c:out value="${data.ngo}" /></td>
                            <td class="text-right"> <c:out value="${data.corporate}" /></td>
 							<td class="text-right"> <c:out value="${data.work_status}" /></td>	
 							<td class="text-right"><fmt:formatDate value="${data.workstatus_date}" pattern="dd-MM-yyyy" /></td>			
 					</tr>
							
					
 						</c:forEach>
					
 						
						<c:if test="${compdataListSize eq 0}">
							<tr>
								<td align="center" colspan="15" class="required" style="color:red;">Data Not Found</td>
								
							</tr>
						</c:if>
	</table>
	</div>
	</div>
						 		
    	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>	
</body>
</html>
