<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/jquery-ui.css" />">
	
	<script src='<c:url value="/resources/js/jquery-1.8.2.js" />'></script>
	<script src='<c:url value="/resources/js/jquery-ui.js" />'></script>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
<!--  <script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify Project Deatils</title>
<style>
</style>
<script type="text/javascript">

	function getDistrict() {
		   var  selectedValue= $("#mySelect").val();
		   var slctSubcat=$('#district'), option="";
		   slctSubcat.empty();
		   slctSubcat.append('<option value="0"> --All District--</option>');
		   $.ajax({
			 	  	type: 'POST',
			       	url: "getDistrictData",
			       	data: {"stateCode" : selectedValue},
	
	     			success: function(data){
				         for ( var key in data){
				             option = option + "<option value="+key + ">"+data[key] + "</option>";
	         				}
	        			slctSubcat.append(option);
	     				},
				        error:function(xhr,status,er){
					        alert("error");
				         console.log(er);
	     				}
				 });
		};

	function deleterecord(param1) {
		var id=param1;
		var form = document.getElementById('projectDetail');
		form.action="deleteProject";
		$(form)
		.append(
				'<input type="hidden" name="projid" value="'+param1 +'" />');
		
		form.submit();
	};
	function modifyrecord(param1) {
		var id=param1;
		var form = document.getElementById('projectDetail');
		form.action="modifyProject";
		$(form)
		.append(
				'<input type="hidden" name="projid" value="'+param1 +'" />');
		
		form.submit();
	};
</script>
</head>
<body>


	<form:form action="ModifyListProject" method="post"
		modelAttribute="projectDetail">
		<div class="table-wrapper">
			<div class="table-title">
				<div class="row">

					<div class="col-sm-9">
						<h5>Modify/View Project Details</h5>

					</div>

				</div>
			</div>
			<div class="col">
				<label class="note"> Note: All amounts(in Lakhs ₹), All Area
					in Ha. </label>
			</div>
			<c:if test="${not empty msg}">
				<div class="col">
					<label class="successmsg"> Note: ${msg}</label>
				</div>
			</c:if>
			<div align="center">

				<table style="width: 100%">
					<tr>
						<td>Select State</td>
						<td align="left"><form:select id="mySelect"
								onchange="javascript:getDistrict();" path="stateCode">
								<form:option value="0" label="--- All States---"></form:option>
								<form:options items="${statelist}" />
							</form:select></td>
						<td>Select District</td>
						<td><form:select id="district" path="distCode">
								<form:option value="0" label="---All Districts---"></form:option>
								<form:options items="${districtList}" />
							</form:select></td>
						<td>Select Financial Year</td>
						<td><form:select path="finCode">
								<form:option value="0" label="---All Financial Year---"></form:option>
								<form:options items="${financialYear}" itemLabel="finYrDesc"
									itemValue="finYrCd"></form:options>
							</form:select></td>
					</tr>
					<tr>

						<td>Project Period (in Yrs)</td>
						<td><form:select id="iwmpMProjectPrd" path="iwmpMProjectPrd">
								<form:option value="0" label="---All Periods---"></form:option>
								<form:options items="${projectPrd}" itemLabel="prdDesc"
									itemValue="prdCode" />
							</form:select></td>
						<td>Project Area Type</td>
						<td><form:select id="iwmpMAreaType" path="iwmpMAreaType">
								<form:option value="0" label="---All Area Type---"></form:option>
								<form:options items="${areaType}" itemLabel="areaDesc"
									itemValue="areaCd" />
							</form:select></td>
						<td>Status</td>
						<td><form:select id="projectStatus" path="projectStatus">
								<form:option value="0" label="---All Projects---"></form:option>
								<form:option value="D" label="New"></form:option>
								<form:option value="C" label="Ongoing"></form:option>

							</form:select></td>
					</tr>
					<tr>
					<tr>
						<td colspan="6" align="center"><input type="submit"
							value="Search" class="square_btn"></td>
					</tr>
				</table>
			</div>
			<div align="center">
				<c:if test="${projectList!=null}">
					<table>
						<thead>
							<tr>

								<th>S.No.</th>
								<th>State Name</th>
								<th>District Name</th>
								<th>Financial Year</th>
								<th>Project Name</th>
								<th>Block</th>
								<th>Project Start Date</th>
								<th>Project Period</th>
								<th>Area Sanctioned</th>
								<th>Area Type</th>
								<th>Total Cost</th>
								<th>Central Share</th>
								<th>State Share</th>
								<th>Remarks</th>
								<th>Action</th>
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
								<th>13</th>
								<th>14</th>
								<th>15</th>

							</tr>
						</thead>
						<tbody>
							<c:if test="${empty projectList}">
								<tr>
									<td colspan="10">No Data to Display</td>
								</tr>
							</c:if>
							<c:if test="${not empty projectList}">
								<c:forEach items="${projectList}" var="proj" varStatus="status">
									<tr>

										<td align="center">${status.count}</td>
										<td>${proj.iwmpDistrict.iwmpState.stName}</td>
										<td>${proj.iwmpDistrict.distName}</td>
										<td>${proj.iwmpMFinYear.finYrDesc}</td>
										<td>${proj.projName}</td>
										<td></td>
										<td><fmt:formatDate value="${proj.projectStartDt}"
												pattern="dd/MM/yyyy" /></td>
										<td>${proj.iwmpMProjectPrd.prdDesc}</td>
										<td>${proj.areaProposed}</td>
										<td>${proj.iwmpMAreaType.areaDesc}</td>
										<td>${proj.projectCost}</td>
										<td>${proj.centralShareAmt}</td>
										<td>${proj.stateShareAmt}</td>
										<td>${proj.remarks}</td>
											<td>
											<c:if test="${proj.status eq 'C'.charAt(0)}">
											
											<input type="submit" value="Modify"
											class="square_btn" name="onmodify"
											onclick="javascript:modifyrecord(${proj.projectId})">
<!-- 												<input type="submit" value="Delete" class="square_btn" -->
<!-- 													name="ondelete" -->
<%-- 													onclick="javascript:deleterecord(${proj.projectId})"> --%>
											</c:if></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</c:if>
			</div>
		</div>
	</form:form>
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