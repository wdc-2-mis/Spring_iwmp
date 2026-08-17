<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script type="text/javascript">

$(document).ready(function () {
    var initialPendingCount = $(".projectCheckbox").length;
    if (initialPendingCount > 0) {
        $("#update").removeClass("d-none");
    } else {
        $("#update").addClass("d-none");
    }
    $("#district").on("change", function () {
        // Always uncheck Select All
        $("#checkAllTranxId").prop("checked", false);
        var dcode = $(this).val();
        $.ajax({
            url: '${pageContext.request.contextPath}/getProjectDetailsByDistrict',
            type: 'POST',
            data: {
                dcode: dcode
            },
            success: function (data) {
                var tbody = $("#tbodyProject");
                var completedTbody = $("#tbodyCompletedProject");
                // Clear both tables
                tbody.empty();
                completedTbody.empty();
                // Reset Select All
                $("#checkAllTranxId").prop("checked", false);
                var pendingCount = 0;
                var completedCount = 0;
                $.each(data, function (index, project) {
                    var finyear =
                        '20' + project.finyear +
                        '-' +
                        (parseInt(project.finyear) + 1);
                    if (project.projectstatus === "C") {
                        completedCount++;
                        completedTbody.append(
                            '<tr>' +
                                '<td>' +
                                    completedCount +
                                '</td>' +
                                '<td>' +
                                    project.projname +
                                '</td>' +
                                '<td>' +
                                    project.distname +
                                '</td>' +
                                '<td>' +
                                    project.sanctionedamount +
                                '</td>' +
                                '<td>' +
                                    finyear +
                                '</td>' +
                            '</tr>'
                        );
                    }
                    else {
                        pendingCount++;
                        tbody.append(
                            '<tr>' +
                                '<td>' +
                                    '<input type="checkbox" ' +
                                           'class="projectCheckbox" ' +
                                           'name="projectIds" ' +
                                           'value="' + project.projid + '">' +
                                '</td>' +
                                '<td>' +
                                    project.projname +
                                '</td>' +
                                '<td>' +
                                    project.distname +
                                '</td>' +
                                '<td>' +
                                    project.sanctionedamount +
                                '</td>' +
                                '<td>' +
                                    finyear +
                                '</td>' +
                            '</tr>'
                        );
                    }
                });

                if (pendingCount === 0) {
                    tbody.append(
                        '<tr>' +
                            '<td colspan="5" class="text-center">' +
                                'No projects available for update' +
                            '</td>' +
                        '</tr>'
                    );
                    $("#update").addClass("d-none");
                } else {
                    $("#update").removeClass("d-none");
                }

                if (completedCount === 0) {
                    completedTbody.append(
                        '<tr>' +
                            '<td colspan="5" class="text-center">' +
                                'No completed projects found' +
                            '</td>' +
                        '</tr>'
                    );
                }
            },
            error: function (xhr, status, error) {
                console.log("AJAX Error:", error);
                $("#tbodyProject").empty();
                $("#tbodyProject").append(
                    '<tr>' +
                        '<td colspan="5" class="text-center text-danger">' +
                            'Error while getting project details' +
                        '</td>' +
                    '</tr>'
                );
            }
        });
    });

    $(document).on("change", "#checkAllTranxId", function () {
        $(".projectCheckbox").prop(
            "checked",
            this.checked
        );
    });

    $(document).on("change", ".projectCheckbox", function () {
        var total = $(".projectCheckbox").length;
        var checked = $(".projectCheckbox:checked").length;
        $("#checkAllTranxId").prop(
            "checked",
            total > 0 && total === checked
        );
    });

    $(document).on("click", "#update", function () {
        var projectIds = [];
        $(".projectCheckbox:checked").each(function () {
            projectIds.push($(this).val());
        });
        if (projectIds.length === 0) {
            alert("Please select at least one project.");
            return;
        }
        if (!confirm("Are you sure you want to update selected projects?")) {
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/updateProjectStatus',
            type: 'POST',
            traditional: true,
            data: {
                projectIds: projectIds
            },
            success: function (response) {
                if (response === "SUCCESS") {
                    alert("Project status updated successfully.");
                    location.reload();
                } else {
                    alert("Unable to update project status.");
                }
            },
            error: function () {
                alert("Error while updating project status.");
            }
        });
    });
});

</script>


<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Update Project Status</u></h4> </div>
		<form name="updateProjectStatus" id="updateProjectStatus">
			<lable class="message badge badge-danger error"></lable>
			<hr/>
			<div class="row">
				<div class="form-group col-3">
					<label for="district"><b>District Name:</b></label><br/>
					<select class="form-control district" id="district" name="district" required>
						<option value="0">--All District--</option>
						<c:forEach items="${distList}" var="dist">
							<option value="${dist.key}"><c:out value="${dist.value}" /></option>
						</c:forEach>
					</select>
				</div>
    		</div>
    		
<!-- 			<div class="form-row"> -->
<!-- 				<div class="form-group col"> -->
<!-- 				<label for="btnGetDetails"> &nbsp;</label> -->
<!--      				<input type="button" class="btn btn-info" id="getProjectDetails" name="getProjectDetails"  value ="Get Details"/> -->
<!--      			</div> -->
<!--      		</div> -->
     		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of Projects</u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="projectTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th style="width:2%"><input type="checkbox" id ="checkAllTranxId"  onchange="selectAll(this);"/></th>
								<th style="width:5%">Project Name &nbsp;</th>
								<th style="width:5%"> District Name</th>
								<th style="width:10%">Sanctioned Amount</th>
								<th style="width:2%">Financial Year</th>
							</tr>

						</thead>
						<tbody id="tbodyProject">
							<c:forEach items="${projectDetailsList}" var="project">
								<c:if test="${project.projectstatus != 'C'}">
								<tr>
									<td><input type="checkbox" class="projectCheckbox"
										name="projectIds" value="${project.projid}" /></td>
									<td><c:out value="${project.projname}" /></td>
									<td><c:out value="${project.distname}" /></td>
									<td><c:out value="${project.sanctionedamount}" /></td>
									<td>20${project.finyear}-${project.finyear + 1}</td>
								</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
		</div>
		</div>
		<div class="form-row">
				<div class="form-group col">
     				<input type="button" class="btn btn-info d-none" id="update" name="update" value ="update"/>
     			</div>
     		</div>
			<h5 class="text-center font-weight-bold mt-4">
				<u>Completed Projects</u>
			</h5>

			<table
				class="table table-bordered table-striped table-highlight w-auto"
				id="completedProjectTable">

				<thead class="theadlist">
					<tr>
						<th style="width:2%">S.No.</th>
						<th style="width:10%">Project Name</th>
						<th style="width:8%">District Name</th>
						<th style="width:5%">Sanctioned Amount</th>
						<th style="width:5%">Financial Year</th>
					</tr>
				</thead>

				<tbody id="tbodyCompletedProject">
				<c:set var="completedCount" value="0" />
					<c:forEach items="${projectDetailsList}" var="project">
						<c:if test="${project.projectstatus == 'C'}">
						<c:set var="completedCount" value="${completedCount + 1}" />
							<tr>
								<td><c:out value="${completedCount}"/></td>
								<td><c:out value="${project.projname}" /></td>
								<td><c:out value="${project.distname}" /></td>
								<td><c:out value="${project.sanctionedamount}" /></td>
								<td>20${project.finyear}-${project.finyear + 1}</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>

			</table>
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>