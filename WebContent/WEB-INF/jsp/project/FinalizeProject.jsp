<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">

<title>Finalized Project</title>
<style>
</style>
<script type="text/javascript">
	
	function selectall(source) {
		//	alert(source.checked);
		var c = new Array();
		c = document.getElementsByTagName('input');

		for (var i = 0; i < c.length; i++) {
			if (c[i].type == 'checkbox') {
				c[i].checked = source.checked;
			}
		}
	}
	function getDistrict() {
		var selectedValue = $("#mySelect").val();
		//	   alert(selectedValue);
		var slctSubcat = $('#district'), option = "";
		slctSubcat.empty();
		slctSubcat.append('<option value="0"> --All District--</option>');
		$.ajax({
			type : 'POST',
			url : "getDistrictData",
			data : {
				"stateCode" : selectedValue
			},

			success : function(data) {

				for ( var key in data) {

					option = option + "<option value="+key + ">" + data[key]
							+ "</option>";
				}
				slctSubcat.append(option);
			},
			error : function(xhr, status, er) {
				alert("error");
				console.log(er);
			}

		});
	};

	function deleterecord(param1) {
		var id = param1;
		var form = document.getElementById('projectDetail');
		$(form).append(
				'<input type="hidden" name="projid" value="'+param1 +'" />');
		form.submit();
	};
</script>
</head>
<body>
	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">

				<div class="col-sm-9">
					<h5>List of Projects to finalized</h5>

				</div>

			</div>
		</div>

		<form:form action="sanctionedProject" method="post"
			modelAttribute="projectDetail">

			<c:if test="${not empty error}">
				<div class="col">
					<label class="alert alert-danger"> ${error}</label>
				</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="col">
					<label class="alert alert-success"> ${msg}</label>
				</div>
			</c:if>

			<table
				class="table table-bordered table-striped table-highlight w-auto"
				style="width: 100%">
				<tr>
					<td align="left">
						<table
							class="table table-bordered table-striped table-highlight w-auto"
							style="width: 70%">
							<tr>
								<td>Select State</td>
								<td align="left"><form:select id="mySelect"
										onchange="javascript:getDistrict();" path="stateCode">
										<form:option value="0" label="--- All States ---"></form:option>
										<form:options items="${statelist}" />
									</form:select></td>
								<td>Select District</td>
								<td><form:select path="distCode" id="district">
										<form:option value="0" label="---All Districts---"></form:option>
										<form:options items="${districtList}" />
									</form:select></td>
							<tr>
								<td>Select Financial Year</td>
								<td><form:select path="finCode">
										<form:option value="0" label="---All Financial Years---"></form:option>
										<form:options items="${financialYear}" itemLabel="finYrDesc"
											itemValue="finYrCd"></form:options>
									</form:select></td>
								<td>Status</td>
								<td><form:radiobutton name="status" path="status" value="D"
										label="New" checked="checked" /> <form:radiobutton
										name="status" path="status" value="C" label="On going" /></td>
							</tr>
							<tr>
								<td colspan="4"><input type="submit" class="square_btn"
									name="go" value="Go" /></td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="col">
										<label class="note  float-right"> All amounts(in
											Lakhs Rs.), All Area in Ha. </label>
									</div>
									<table
										class="table table-bordered table-striped table-highlight w-auto">
										<thead>
											<c:if test="${projectDetail.iwmpMProjectList!=null}">
												<c:if test="${empty projectDetail.iwmpMProjectList}">
													<tr>
														<td colspan="10">Data not found</td>
													</tr>
												</c:if>
												<c:if test="${not empty projectDetail.iwmpMProjectList}">
													<tr>
														<c:if test="${fn:contains(projectDetail.status, 'D')}">
															<th><input type="checkbox" onClick="selectall(this)" />Select
																All<br /></th>
														</c:if>
														<th>S.No.</th>
														<th>State Name</th>
														<th>Financial Year</th>
														<th>Project Name</th>
														<th>Project Start Date</th>
														<th>Area Type</th>
														<th>Area Sanctioned</th>
														<th>Total Cost</th>
														<th>Central Share</th>
														<th>State Share</th>
														<th>Current Phase</th>
														<th>Project Phase</th>
														<c:if test="${fn:contains(projectDetail.status, 'D')}">
															<th>Action</th>
														</c:if>
													</tr>
													<tr>
														<th>1</th>
														<th>2</th>
														<th>3</th>
														<th>4</th>
														<th>5</th>
														<th>6</th>
														<th>7</th>
														<th>8</th>
														<th>9</th>
														<th>10</th>
														<th>11</th>
														<th>12</th>
														<c:if test="${fn:contains(projectDetail.status, 'D')}">
															<th>13</th>

															<th>14</th>
														</c:if>
													</tr>
													<c:forEach items="${projectDetail.iwmpMProjectList}"
														var="proj" varStatus="status">
														<tr>
															<c:if test="${fn:contains(projectDetail.status, 'D')}">
																<td><form:checkbox
																		path="iwmpMProjectList[${status.index}].updatestatus" />
																	<form:hidden
																		path="iwmpMProjectList[${status.index}].projectId"
																		value="${proj.projectId}" /></td>
															</c:if>
															<td align="center">${status.count}</td>
															<td>${proj.iwmpDistrict.iwmpState.stName}</td>
															<td>${proj.iwmpMFinYear.finYrDesc}</td>
															<td>${proj.projName}</td>
															<td><fmt:formatDate value="${proj.projectStartDt}"
																	pattern="dd/MM/yyyy" /></td>
															<td>${proj.iwmpMAreaType.areaDesc}</td>		
															<td>${proj.areaProposed}</td>
															<td>${proj.projectCost}</td>
															<td>${proj.centralShareAmt}</td>
															<td>${proj.stateShareAmt}</td>
															<td><c:if test="${fn:contains(proj.status, 'P')}">
																Preparatory Phase
														</c:if> <c:if test="${fn:contains(proj.status, 'W')}">
      													 Work Phase		</c:if> <c:if
																	test="${fn:contains(proj.status, 'C')}">
         													Consolidation Phase
      														</c:if></td>
															<td></td>
															<c:if test="${fn:contains(proj.status, 'D')}">
																<td><input type="submit" value="Delete"
																	class="square_btn" name="ondelete"
																	onclick="javascript:deleterecord(${proj.projectId})">
																</td>
															</c:if>
														</tr>
													</c:forEach>
												</c:if>
											</c:if>
										</thead>

									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<c:if test="${fn:contains(projectDetail.status, 'D')}">
					<c:if test="${not empty projectDetail.iwmpMProjectList}">
						<tr>
							<td align="center"><input type="submit" value="Complete"
								class="square_btn"></td>
						</tr>
					</c:if>

				</c:if>

			</table>

		</form:form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".sidebar-btn").click(function() {
				$(".wrapper").toggleClass("collapse");
			});
		});
	</script>
	<footer class="">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>