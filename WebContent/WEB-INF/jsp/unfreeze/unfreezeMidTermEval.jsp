<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
function validateDropdowns() {
    const dropdowns = [
        { id: "state", errorId: "stateError" },
        { id: "district", errorId: "districtError" },
        { id: "project", errorId: "projectError" }
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
		document.unfreezemidterm.action="showMidTermDetails";
		document.unfreezemidterm.method="post";
		document.unfreezemidterm.submit();
	}
	 else {
         console.log("All dropdowns are required.");
     }
	
}

function unfreezedata(){
	if (confirm("Do you want to Unfreeze ?") == true) {
        document.unfreezemidterm.action = "unfreezeMidTermData";
        document.unfreezemidterm.method = "post";
        document.unfreezemidterm.submit();
    }
	
}

</script>

</head>

<div class="col formheading" style=""><h4><u>Unfreeze Physical Achievement</u></h4></div>
<c:if test="${messageUpload != null}">
	<script>
		alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
<hr />
<div class ="card">
<form:form autocomplete="off" name="unfreezemidterm" id="unfreezemidterm" action="unfreezeMidTerm" method="get">
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
			<td class="label">Project <span style="color: red;">*</span></td>
			<td>
				<select name="project" id="project" required="required" style="width:100%;">
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
			  <td align="left"> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value="Submit" />
        
			
			
</tr>
</table>
<div style="width: 100%;">
	<c:if test="${not empty midtermSize}">
	<hr />
        <table id="tbleshgdDetails" style="width:100%">
            <thead>
            <tr>
            <c:forEach var="list" items="${midtermList}" varStatus="status">
            <td class="label"><b>Fin Year:</b></td><td><c:out value="${list.fin_yr}"/></td>
            <td class="label"><b>Month:</b></td><td><c:out value="${list.monthname}"/></td>
            <td class="label"><b>Name of Project Evaluation Agency:</b></td><td><c:out value="${list.pagency}"/></td>
            </c:forEach>
            </tr>
            
            <tr>
								<td colspan=6 align="center">
									<input type="button" class="btn btn-info" id="Unfreeze" onclick="unfreezedata();" name="Unfreeze" value='Unfreeze' />
								    
								</td>
							</tr>
            </thead>
            </table>
            </c:if>
            </div>
</form:form>
</div>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
			