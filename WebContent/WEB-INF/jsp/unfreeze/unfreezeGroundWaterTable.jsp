<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value='/resources/css/report.css' />">
<script type="text/javascript">

function showReport(e) {
    var state = $('#state').val();
    var district = $('#district').val();
    var proj = $('#proj').val();
    var level = $('#level').val();

    if (state === '') {
        alert('Please select State');
        $('#state').focus();
        return false;
    }
    if (district === '') {
        alert('Please select District');
        $('#district').focus();
        return false;
    }
    if (proj === '') {
        alert('Please select Project');
        $('#proj').focus();
        return false;
    }
    if (level === '') {
        alert('Please select Level');
        $('#level').focus();
        return false;
    }

    document.unfreezeGW.action = "unfreezeListGWT";
    document.unfreezeGW.method = "post";
    document.unfreezeGW.submit();
}

function selectAll(ele) {
    var checkboxes = document.getElementsByName('proj_id');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = ele.checked;
    }
}

function completebls() {
    var checkboxes = document.getElementsByName('proj_id');
    var isAnyChecked = false;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isAnyChecked = true;
            break;
        }
    }

    if (isAnyChecked) {
        if (confirm("Do you want to Unfreeze ?")) {
            document.unfreezeGW.action = "unfreezeGWTData";
            document.unfreezeGW.method = "post";
            document.unfreezeGW.submit();
        }
    } else {
        alert("Please select at least one record to Unfreeze.");
    }
}
</script>
</head>

<div class="card">
<div class="table-responsive">
<c:if test="${messageUpload != null}">
	<script>
	    alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
<div class="col" style="text-align:center;">
    <h5>Unfreeze Ground Water Table (GWT) Details</h5> 
</div>
<form:form autocomplete="off" name="unfreezeGW" id="unfreezeGW" action="unfreezeGroundWaterTable" method="get">
    <table style="width:80%">
        <tr>
            <td class="label">State <span style="color:red;">*</span></td>
            <td>
                <select name="state" id="state" onchange="this.form.submit();" required style="width:100%;">
                    <option value="">--Select State--</option>
                    <c:forEach items="${stateList}" var="list">
                        <option value="${list.key}" ${list.key eq state ? 'selected' : ''}>${list.value}</option>
                    </c:forEach>
                </select>
            </td>

            <td class="label">District <span style="color:red;">*</span></td>
            <td>
                <select name="district" id="district" onchange="this.form.submit();" required style="width:100%;">
                    <option value="">--Select District--</option>
                    <c:forEach items="${districtList}" var="list">
                        <option value="${list.key}" ${list.key eq district ? 'selected' : ''}>${list.value}</option>
                    </c:forEach>
                </select>
            </td>

            <td class="label">Project <span style="color:red;">*</span></td>
            <td>
                <select name="proj" id="proj" onchange="this.form.submit();" required style="width:100%;">
                    <option value="0">--All Project--</option>
                    <c:forEach items="${projectList}" var="list">
                        <option value="${list.key}" ${list.key eq proj ? 'selected' : ''}>${list.value}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <tr>
            <td class="label">At the time of <span style="color:red;">*</span></td>
            <td>
                <select name="level" id="level" onchange="this.form.submit();" required style="width:100%;">
                    <option value="">--Select Level--</option>
                    <c:forEach items="${maphead}" var="list">
                        <option value="${list.key}" ${list.key eq level ? 'selected' : ''}>${list.value}</option>
                    </c:forEach>
                </select>
            </td>

            <c:if test="${level eq 'project'}">
                <td class="label">Financial Year <span style="color:red;">*</span></td>
                <td>
                    <select name="year" id="year"  required style="width:100%;">
                        <option value="">--Select Financial Year--</option>
                        <c:forEach items="${finYear}" var="list">
                            <option value="${list.key}" ${list.key eq year ? 'selected' : ''}>${list.value}</option>
                        </c:forEach>
                    </select>
                </td>
            </c:if>

            <td align="left">&nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value="Submit" />
            </td>
        </tr>
    </table>

    <div style="width: 85%;">
        <c:if test="${gwList ne null}">
        <c:if test="${level eq 'project'}">
            <table id="tblGwtDetails">
                <thead>
                    <tr>
                        <th style="width:3%"><input type="checkbox" onchange="selectAll(this);"/> &nbsp;S.No.</th>
                        <th style="width:25%">Project Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="getGwt" items="${gwList}" varStatus="cntt">
                        <tr>
                            <td align="center">
                                <input type="checkbox" name="proj_id" value="${getGwt.proj_id}"/>
                                &nbsp;&nbsp;${cntt.count}
                            </td>
                            <td>${getGwt.proj_name}</td>
                        </tr>
                    </c:forEach>

                    <c:if test="${gwListSize > 0}">
                        <tr>
                            <td colspan="4" align="center">
                                <input type="button" class="btn btn-info" onclick="completebls();" value="Unfreeze" />
                            </td>
                        </tr>
                    </c:if>

                    <c:if test="${gwListSize == 0}">
                        <tr>
                            <td colspan="6" class="required" style="color:red; text-align:center;">Data Not Found</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            </c:if>
            
             <c:if test="${level eq 'basel'}">
            <table id="tblGwtDetails">
                <thead>
                    <tr>
                        <th style="width:3%"><input type="checkbox" onchange="selectAll(this);"/> &nbsp;S.No.</th>
                        <th style="width:25%">Project Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="getGwt" items="${gwList}" varStatus="cnt">
                        <tr>
                            <td align="center">
                                <input type="checkbox" name="proj_id" value="${getGwt.proj_id}"/>
                                &nbsp;&nbsp;${cnt.count}
                            </td>
                            <td>${getGwt.proj_name}</td>
                        </tr>
                    </c:forEach>

                    <c:if test="${gwListSize > 0}">
                        <tr>
                            <td colspan="4" align="center">
                                <input type="button" class="btn btn-info" onclick="completebls();" value="Unfreeze" />
                            </td>
                        </tr>
                    </c:if>

                    <c:if test="${gwListSize == 0}">
                        <tr>
                            <td colspan="6" class="required" style="color:red; text-align:center;">Data Not Found</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            </c:if>
            </c:if>
    </div>
</form:form>

</div>
</div>

<footer>
    <%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</html>
