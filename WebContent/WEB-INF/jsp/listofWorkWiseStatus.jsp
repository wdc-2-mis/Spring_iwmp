<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
 <script src='<c:url value="/resources/js/listofWorkWiseStatus.js" />'></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

<head>
<script>

function toggleFinancialYear() {
    let activityUpper = document.getElementById("activityid").value;
    let fyRow = document.getElementById("financialYearRow");

    if (activityUpper === "N") {
        fyRow.style.display = "block"; // show
    } else {
        fyRow.style.display = "none"; // hide
        document.getElementById("year").value = ""; // clear value
    }
}
window.onload = function() {
    toggleFinancialYear();
};
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("getworkwisedetails").addEventListener("submit", function (e) {
        e.preventDefault(); // Stop default submission first

        let district = document.getElementById("district").value.trim();
        let project = document.getElementById("projid").value.trim();
        let year = document.getElementById("year").value.trim();
        let activityUpper = document.getElementById("activityid").value.trim();
        let workStatus = document.getElementById("status").value.trim();

        let workId = document.getElementById("workid").value.trim();
        let activityLower = document.getElementById("activityid1").value.trim();

        let upperComplete = !!(district || project || activityUpper || workStatus || (activityUpper === "N" && year));
        let lowerComplete = !!(workId || activityLower);

        if (!upperComplete && !lowerComplete) {
            alert("Please fill either upper section or lower section");
            return false;
        }
        if (upperComplete && lowerComplete) {
            alert("Only fill either upper section or lower section");
            return false;
        }
        
        if (upperComplete) {
             if (!district) { alert("Please select District"); document.getElementById("district").focus(); return false; }
            if (!project) { alert("Please select Project"); document.getElementById("projid").focus(); return false; }
            if (!activityUpper) { alert("Please select Head Activity"); document.getElementById("activityid").focus(); return false; }
            if (!workStatus) { alert("Please select Work Wise Status"); document.getElementById("status").focus(); return false; }
            if (activityUpper === "N" && !year) { alert("Please select Financial Year for NRM"); document.getElementById("year").focus(); return false; }
            
            document.getworkwisedetails.action="listofWorkWiseStatus";
    		document.getworkwisedetails.method="post";
    		document.getworkwisedetails.submit();
    		
           /*  this.action = "upperCompleteURL"; 
            this.submit();
            return; */
        }

        if (lowerComplete) {
            if (!workId) { alert("Please Enter Work-Id"); document.getElementById("workid").focus(); return; }
            if (!activityLower) { alert("Please select Head Activity"); document.getElementById("activityid1").focus(); return; }

            this.action = "getworkWiseStatus";
            document.getworkwisedetails.method="post";
            this.submit();
            return false;
        }

        
    });
});
</script>


</head>

<body>
<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>List of Work Wise Status Details</h4> </div>
		
		<form:form autocomplete="off" method="post" name="getworkwisedetails" id="getworkwisedetails" action="getworkwisedetails" modelAttribute="workwisestatus" >
			  <hr/>
			  
			 
			<div class="row">
			<div class="form-group col-2">
    <label for="state"><b>State Name:</b></label><br/>
    <c:out value="${stateName}" /><br/>
</div>

<div class="form-group col-2">
    <label for="district"><b>District Name:</b></label><br/>

        <select class="form-control" id="district" name="district">
            <option value="">--Select District--</option>
            <c:forEach items="${distList}" var="dist">
            <c:if test="${dist.key eq district}">
             <option value="${dist.key}" selected="selected"><c:out
									value="${dist.value}" /></option>
             </c:if>
             <c:if test="${dist.key ne district}">
                <option value="${dist.key}"><c:out value="${dist.value}" /></option>
                </c:if>
            </c:forEach>
        </select>
     
</div>

<div class="form-group col-2">
    <label for="projid"><b>Project Name:</b></label><br/>
  <span class="activityError"></span>
        <select class="form-control" id="projid" name="projid">
            <option value="">--Select Project--</option>
        
        <c:if test="${not empty ProjectList}">
					<c:forEach items="${ProjectList}" var="proj">
						<c:if test="${proj.value eq projId}">
							<option value="<c:out value="${proj.value}"/>" selected="selected"><c:out
									value="${proj.key}" /></option>
						</c:if>
						<c:if test="${proj.value ne projId}">
							<option value="<c:out value="${proj.value}"/>"><c:out
									value="${proj.key}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
        
        </select>
    
</div>

<div class="form-group col-2">
    <label for="activity"><b>Head Activity:</b></label><br/>
    <select class="form-control" id="activityid" name="activityid" onchange="toggleFinancialYear()">
        <option value="">--Select Activity--</option>
        <option value="N" ${hactivity == 'N' ? 'selected="selected"' : ''}>NRM Works</option>
        <option value="E" ${hactivity == 'E' ? 'selected="selected"' : ''}>EPA</option>
        <option value="L" ${hactivity == 'L' ? 'selected="selected"' : ''}>Livelihood</option>
        <option value="P" ${hactivity == 'P' ? 'selected="selected"' : ''}>Production System</option>
    </select>
</div>

<div class="form-group col-2">
    <label for="status"><b>Work Wise Status:</b></label><br/>
    <select class="form-control" id="status" name="status">
        <option value="">--Select Status--</option>
        <option value="O" ${wstatus == 'O' ? 'selected' : ''}>Ongoing</option>
        <option value="C" ${wstatus == 'C' ? 'selected' : ''}>Completed</option>
        <option value="F" ${wstatus == 'F' ? 'selected' : ''}>Foreclosed</option>
        <option value="N" ${wstatus == 'N' ? 'selected' : ''}>Not Started</option>
    </select>
</div>

   
   <div class="form-group col-2" id ="financialYearRow">
			<label for="financial"><b>Financial Year:- </b></label> <select
				class="form-control" id="year" name="year">
				<option value="">--Select Financial Year--</option>
				<c:if test="${not empty financial}">
					<c:forEach items="${financial}" var="fyear">
						<c:if test="${fyear.key eq finyear}">
							<option value="<c:out value="${fyear.key}"/>" selected="selected"><c:out
									value="${fyear.value}" /></option>
						</c:if>
						<c:if test="${fyear.key ne finyear}">
							<option value="<c:out value="${fyear.key}"/>"><c:out
									value="${fyear.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
			</select>
		</div> 
 
			<div class="form-group col-md-12 text-center mt-3">
            <h3 style="color: green">OR</h3>
            </div>
            <div class="row">
    <!-- NRM Work-Id -->
    <div class="form-group col-md-6">
        <label for="nrmwork"><b>Enter Work-Id:</b></label>
        <input type="text" name="workid" id="workid" class="form-control" onmousedown="numericOnly(event);" maxlength="8" />
    </div>

  <div class="form-group col-md-6">
    <label for="activity"><b>Head Activity:</b></label><br/>
    <select class="form-control" id="activityid1" name="activityid1">
        <option value="">--Select Activity--</option>
        <option value="N" ${hactivity1 == 'N' ? 'selected="selected"' : ''}>NRM Works</option>
        <option value="E" ${hactivity1 == 'E' ? 'selected="selected"' : ''}>EPA</option>
        <option value="L" ${hactivity1 == 'L' ? 'selected="selected"' : ''}>Livelihood</option>
        <option value="P" ${hactivity1 == 'P' ? 'selected="selected"' : ''}>Production System</option>
    </select>
</div>
        

   
</div>
<!-- Get Data Button -->
        <div class="form-group col-md-12 text-center mt-4">
            <button type="submit" class="btn btn-primary">Get Data</button>
        </div>
        <div class="w-100 border-bottom my-3"></div>
</form:form>

<c:if test="${ListCount ge 0}">
    <table border="1" cellpadding="5" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>S No</th>
                <th>Work-id</th>

                <c:choose>
                    <c:when test="${hactivity == 'N'}">
                        <th>Name of Head</th>
                        <th>Name of Activity</th>
                    </c:when>
                    <c:otherwise>
                        <th>Name of Activity</th>
                    </c:otherwise>
                </c:choose>

                <th>Block</th>
                <th>Village</th>

                <c:choose>
                    <c:when test="${wstatus == 'O'}">
                        <th>Start Date</th>
                    </c:when>
                    <c:when test="${wstatus == 'C'}">
                        <th>Start Date</th>
                        <th>Complete Date</th>
                       <c:if test="${hactivity == 'N'}">
                        <th>Convergence with other scheme</th>
                        </c:if>
                    </c:when>
                    <c:when test="${wstatus == 'F'}">
                        <th>Start Date</th>
                        <th>Reason</th>
                        <c:if test="${hactivity == 'N'}">
                        <th>Convergence with other scheme</th>
                        </c:if>
                    </c:when>
                </c:choose>
            </tr>
        </thead>
        <tbody>
         <c:choose>
                <c:when test="${ListCount gt 0}">
            <c:forEach var="row" items="${ListofworkWiseStatus}" varStatus="st">
                <tr>
                    <td>${st.index + 1}</td>
                    <td>${row.asseteid}</td>

                    <c:choose>
                        <c:when test="${hactivity == 'N'}">
                            <td>${row.headdesc}</td>
                            <td>${row.activitydesc}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${row.activitydesc}</td>
                        </c:otherwise>
                    </c:choose>

                    <td>${row.bname}</td>
                    <td>${row.vname}</td>

                    <c:choose>
                        <c:when test="${wstatus == 'O'}">
                            <td>${row.sdate}</td>
                        </c:when>
                        <c:when test="${wstatus == 'C'}">
                            <td>${row.sdate}</td>
                            <td>${row.cdate}</td>
                            <c:if test="${hactivity == 'N'}">
                            <td>${row.convergence}</td>
                            </c:if>
                        </c:when>
                        <c:when test="${wstatus == 'F'}">
                            <td>${row.sdate}</td>
                            <td>${row.reason}</td>
                            <c:if test="${hactivity == 'N'}">
                            <td>${row.convergence}</td>
                            </c:if>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>
           </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="10" style="text-align:center;">Data not found</td>
                    </tr>
                </c:otherwise>
            </c:choose>
    </table>
</c:if>

<c:if test="${RecordsCount ge 0}">
    <table border="1" cellpadding="5" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>S No</th>
                <th>Work-id</th>
                <c:choose>
                    <c:when test="${hactivity1 == 'N'}">
                        <th>Name of Head</th>
                        <th>Name of Activity</th>
                    </c:when>
                    <c:otherwise>
                        <th>Name of Activity</th>
                    </c:otherwise>
                </c:choose>
                <th>Block</th>
                <th>Village</th>
                <c:choose>
                    <c:when test="${statusValue == 'O'.charAt(0)}">
                        <th>Start Date</th>
                    </c:when>
                    <c:when test="${statusValue == 'C'.charAt(0)}">
                        <th>Start Date</th>
                        <th>Complete Date</th>
                       <c:if test="${hactivity1 == 'N'}">
                        <th>Convergence with other scheme</th>
                        </c:if>
                    </c:when>
                    <c:when test="${statusValue == 'F'.charAt(0)}">
                        <th>Start Date</th>
                        <th>Reason</th>
                        <c:if test="${hactivity1 == 'N'}">
                        <th>Convergence with other scheme</th>
                        </c:if>
                    </c:when>
                </c:choose>
                <th>Status</th>
                
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${RecordsCount gt 0}">
                    <c:forEach var="row" items="${workWiseStatusRecords}" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td>${row.asseteid}</td>
                            <c:choose>
                        <c:when test="${hactivity1 == 'N'}">
                            <td>${row.headdesc}</td>
                            <td>${row.activitydesc}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${row.activitydesc}</td>
                        </c:otherwise>
                    </c:choose>
                            <td>${row.bname}</td>
                            <td>${row.vname}</td>
                            
                             <c:choose>
                        <c:when test="${statusValue == 'O'.charAt(0)}">
                            <td>${row.sdate}</td>
                        </c:when>
                        <c:when test="${statusValue == 'C'.charAt(0)}">
                            <td>${row.sdate}</td>
                            <td>${row.cdate}</td>
                            <c:if test="${hactivity1 == 'N'}">
                            <td>${row.convergence}</td>
                            </c:if>
                        </c:when>
                        <c:when test="${statusValue == 'F'.charAt(0)}">
                            <td>${row.sdate}</td>
                            <td>${row.reason}</td>
                            <c:if test="${hactivity1 == 'N'}">
                            <td>${row.convergence}</td>
                            </c:if>
                        </c:when>
                    </c:choose> 
                            
                            <td>${row.status}</td>
                       </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="7" style="text-align:center;">Work Id not found in this Head Activity</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</c:if>


</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>