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
<script src='<c:url value="/resources/js/janbhagidariStatusUpdate.js" />'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>



</head>

<body>
<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Status Update Activity Cup under WDC-PMKSY2.0</h4> </div>
		
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
                <option value="${dist.key}" <c:if test="${dist.key == district}">selected</c:if>><c:out value="${dist.value}" /></option>
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

    <select class="form-control activity" id="projid" name="projid" onchange="filterByProject()">
        <option value="0">--All Project--</option>

        <c:if test="${not empty selectedProjId && not empty projList}">
            <c:forEach items="${projList}" var="proj">
                <option value="${proj.value}" <c:if test="${proj.value == selectedProjId}">selected</c:if>>
                    <c:out value="${proj.key}" />
                </option>
            </c:forEach>
         </c:if>
      </select>
   </c:if>

    <c:if test="${userType == 'PI'}">
        <span class="activityError"></span>
        <select class="form-control activity" id="projid" name="projid" onchange="filterByProject()">
            <option value="">--All Project--</option>
            <c:forEach items="${projList}" var="proj"> 
                <option value="${proj.key}" <c:if test="${proj.key == selectedProjId}">selected</c:if>> <c:out value="${proj.value}" /></option>
            </c:forEach>
        </select>
    </c:if>
</div>
			
	    		
    		<div class="form-group col-3">
    			
    		</div>
   </div>
  
  
   
   </form:form>
   </div>
   
   <div class="form-row">
	     <div class="form-group col">
	     <hr/>
	    <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="2">S.No.</th>
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
		
             <c:choose>
                <c:when test="${not empty dataList}">
                    <c:forEach var="record" items="${dataList}" varStatus="loop">
                        <tr>
                            <td>${loop.count}&nbsp;
                                <input type="checkbox" class="chkIndividual" id="${record.typeofwork_id}" name="${record.typeofwork_id}" value="${record.typeofwork_id}">
                            </td>
                            <td>${record.projname}</td>
                             <td>${record.gpname}</td>
                            <td>${record.villname}</td>
                            <td>${record.work_desc}</td>
                            <td>${record.es_work}</td>
                            <td>${record.village}</td>
                            <td>${record.ngo}</td>
                            <td>${record.corporate}</td>
                            <td><select class="form-control compWork" name="compWork" id="compWork"  disabled>
                             <option value="Y">Yes</option>
                             <option value="N" selected="selected">No</option>
                             </select></td>
                            <td class="completedDate"></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="10" style="text-align: center;  color:red;" >Data Not Found</td>
                    </tr>
                </c:otherwise>
            </c:choose> 
            
        </table>
        <c:if test="${dataListSize ne 0}">
        <div class="form-group text-center">
     				<input type="button" class="btn btn-info" id="update" name="update" value ="update"/>
     				<!-- <input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/> -->
     	</div>
     	</c:if>
    </div>
</div>
	
   <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>	
   </body>
   </html>
   