<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

<style>
 .error {
            color: red;
            font-size: 14px;
        }
        
         .invalid-dropdown {
            background-color: #ffdddd;
            border: 1px solid red;
        }
</style>
<script type="text/javascript">

function validateDropdowns() {
    const dropdowns = [
        { id: "state", errorId: "stateError" },
        { id: "district", errorId: "districtError" },
        { id: "project", errorId: "projectError" },
        { id: "year", errorId: "yearError" },
        { id: "month", errorId: "monthError" }
    ];

    let allValid = true;

    // Loop through each dropdown to validate
    dropdowns.forEach(dropdown => {
        const element = document.getElementById(dropdown.id);
        const errorElement = document.getElementById(dropdown.errorId);

        if (element.value === "") {
            errorElement.style.display = "inline";
            element.classList.add("invalid-dropdown");
            allValid = false; 
        } else {
            // Hide error message and remove invalid style if selected
            errorElement.style.display = "none";
            element.classList.remove("invalid-dropdown");
        }
    });

    return allValid; // Return true if all dropdowns are valid
}

function showReport(e)
{
	 if (validateDropdowns())
	{
		document.unfreezephyach.action="showPhyAchDetails";
		document.unfreezephyach.method="post";
		document.unfreezephyach.submit();
	}
	 else {
         console.log("All dropdowns are required.");
     }
	
}

function unfreezedata(){
	if (confirm("Do you want to Unfreeze ?") == true) {
        document.unfreezephyach.action = "unfreezePhyAchData";
        document.unfreezephyach.method = "post";
        document.unfreezephyach.submit();
    }
	
}
</script>
</head>
<c:if test="${messageUpload != null}">
	<script>
		alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
 <div class="col formheading" style=""><h4><u>Unfreeze Physical Achievement</u></h4></div>

<div class ="card">
<form:form autocomplete="off" name="unfreezephyach" id="unfreezephyach" action="unfreezePhyAchievement" method="get">
	<table style="width:100%">
		<tr>
			<td class="label">State <span style="color: red;">*</span></td>
			<td>
				<select name="state" id="state" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select State--</option>
					<c:if test="${not empty stateList}">
						<c:forEach items="${stateList}" var="list">
							<c:if test="${list.key eq state}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne state}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
				 <span id="stateError" class="error" style="display:none;">Please select State.</span>
       
			</td>
		<td class="label">District <span style="color: red;">*</span></td>
			<td>
				<select name="district" id="district" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select District--</option>
					<c:if test="${not empty districtList}">
						<c:forEach items="${districtList}" var="list">
							<c:if test="${list.key eq district}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne district}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
				 <span id="districtError" class="error" style="display:none;">Please select District.</span>
			</td>
			<td class="label">Project &nbsp;<span style="color: red;">*</span></td>
			<td>
				<select name="project" id="project" onchange="this.form.submit();" required="required" style="width:100%;">
					<option value="">--Select Project--</option>
					<c:if test="${not empty projectList}">
						<c:forEach items="${projectList}" var="list">
							<c:if test="${list.key eq project}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne project}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
				<span id="projectError" class="error" style="display:none;">Please select Project.</span>
			</td>	
			
	</tr>
	<tr>
	<td class="label">Financial Year &nbsp;<span style="color: red;">*</span></td>
			<td>
				<select name="year" id="year" required="required" style="width:100%;">
					<option value="">--Select Financial Year--</option> 
					
					<c:if test="${not empty finYear}">
						<c:forEach items="${finYear}" var="list">
							<c:if test="${list.key eq year}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne year}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
				<span id="yearError" class="error" style="display:none;">Please select Financial Year.</span>
			</td>
		<td class="label">Month&nbsp;<span style="color: red;">*</span></td>
			<td>
				<select name="month" id="month"  required="required" style="width:100%;">
					<option value="">--Select Month--</option> 
					
					<c:if test="${not empty NCmonth}">
						<c:forEach items="${NCmonth}" var="list">
							<c:if test="${list.key eq month}">
								<option value="<c:out value='${list.key}'/>" selected="selected"><c:out value="${list.value}" /></option>
							</c:if>
							<c:if test="${list.key ne month}">
								<option value="<c:out value='${list.key}'/>"><c:out value="${list.value}" /></option>
							</c:if>
						</c:forEach>
					</c:if>
				</select>
				<span id="monthError" class="error" style="display:none;">Please select Month.</span>
			</td>
          <td align="left"> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value="Submit" />
        </tr>
	</table>
	
	<div style="width: 100%;">
	<c:if test="${not empty phyachSize}">
        <table id="tbleshgdDetails">
            <thead>
                <tr>
                    <th style="width:3%">S.No.</th>
                    <th style="width:25%">Head Description</th>
                    <th style="width:20%">Activity Description</th>
                    <th style="width:20%">Achievement</th>
                </tr>
            </thead>
            <c:if test="${not empty phyachList}">
            <tbody>
                <c:forEach var="list" items="${phyachList}" varStatus="status">
                    <tr>
                        <td><c:out value="${status.index + 1}"/></td> 
                        <td><c:out value="${list.headdesc}"/></td>
                        <td><c:out value="${list.activitydesc}"/></td>
                        <td><c:out value="${list.achievement}"/></td> 
                   <input type="hidden" name = "achievementid" value="${list.achievementid}"/>
                    </tr>
                </c:forEach>
                <tr>
								<td colspan=4 align="center">
									<input type="button" class="btn btn-info" id="Unfreeze" onclick="unfreezedata();" name="Unfreeze" value='Unfreeze' />
								    
								</td>
							</tr>
            </tbody>
        </c:if>
        <c:if test="${empty phyachList}">
         <tbody>
         <tr>
				<td align="center" colspan="4" class="required" style="color:red;">Data Not Found</td>
			</tr>
         </tbody>
        </c:if>
        </table>
   </c:if>
    
</div>

	
	</form:form>		
</div>

<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</html>
	