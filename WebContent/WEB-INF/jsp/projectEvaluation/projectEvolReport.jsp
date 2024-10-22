<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<title>Report - Project Evaluation</title>

<html>
<body>
<div class="maindiv">
    <div class="col formheading">
        <h4><u>Project Evaluation Report</u></h4>
    </div>
    <hr />

    <form id="reportForm">
        <table style="width:100%; align-content: center;">
            <!-- Display error message if present -->
            <c:if test="${not empty error}">
                <div class="col">
                    <label class="alert alert-danger">${error}</label>
                </div>
            </c:if>

            <tr align="center">
                <!-- District Dropdown -->
                <td><b>District: <span style="color: red;">*</span></b></td>
                <td>
                    <select class="form-control" name="district" id="district">
                        <option value="">--Select District--</option>
                        <c:forEach items="${districtList}" var="dist">
                            <option value="${dist.key}" ${dist.key eq district ? 'selected="selected"' : ''}>${dist.value}</option>
                        </c:forEach>
                    </select>
                </td>

                <!-- Project Dropdown -->
                <td><b>Project Name: <span style="color: red;">*</span></b></td>
                <td>
                    <select class="form-control" name="project" id="project">
                        <option value="">--Select Project--</option>
                        <c:forEach items="${projectList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq project ? 'selected="selected"' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                </td>

                <!-- Submit Button -->
                <td align="center" class="label">
                    <input type="button" class="btn btn-info" id="view" value="Submit" />
                </td>
            </tr>
        </table>
        <br>

        <!-- Table for displaying project evaluation report -->
        <div class="form-row">
            <div class="form-group col-12">
                <table class="movement" id="aapMovement" style="width:100%">
                    <thead>
                        <tr>
                            <th>S NO.</th>
                            <th>District</th>
                            <th>Project</th>
                            <th>Fin Year</th>
                            <th>Month</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="tbodyProjEvolRpt"></tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

<!-- Script file for handling form submission and AJAX logic -->
<script src="<c:url value='/resources/js/projectevaluation/profilestart.js' />"></script>
</body>
</html>
