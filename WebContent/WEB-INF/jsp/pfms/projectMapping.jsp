<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/masterModify.js" />'></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<body>

	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">

				<div class="col-sm-6">
					<br>
					<h5>List of Invoices to Mapped with Project Code</h5>

				</div>
			</div>

		</div>


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
		<form:form class="row row-cols-lg-auto g-3 align-items-left"
			name="masterlist" id="masterlist" modelAttribute="invoiceData"
			action="updateMasterList" method="post">

			<div class="form-row">
				<div class="form-group col-md-2.2">
					<label for="inputState" style="width: 50px;"><b>State:</b></label>
					<label><c:out value="${sessionScope.stName}" /></label>

				</div>

				<div class="form-group col-md-3.8">
					<label style="width: 150px;"><b>Invoice No:</b></label>
<%-- 					<form:select id="dl" path="directoryLevel" --%>
<%-- 						onchange="levelselection();"> --%>
<%-- 						<form:option value="0" label="--Master Level--"></form:option> --%>
<%-- 						<form:option value="1" label="District Master"></form:option> --%>
<%-- 						<form:option value="2" label="Block Master"></form:option> --%>
<%-- 						<form:option value="3" label="Gram Panchayat Master"></form:option> --%>
<%-- 						<form:option value="4" label="Village Master"></form:option> --%>
<%-- 					</form:select> --%>
<%-- 					<form:errors path="directoryLevel" cssclass="errormsg" /> --%>
				</div>
				<div class="form-group col-md-3.1">
					<label style="width: 125px;"><b>Invoice No:</b></label>
					
<%-- 					<form:errors path="distCode" cssclass="errormsg" /> --%>
				</div>

			</div>


			<div class="container bg-light">
				<div class="col-md-12 text-center">
					<input type="submit" class="btn btn-info" name="list"
						value="Submit">
				</div>
			</div>
<table id="inactiveVillageList1"
				class="table table-striped table-hover">
				<thead>
					<tr>
						<th class="displ" align="center">Sl. No.</th>
						<!-- 						<th><input type="checkbox" onClick="selectall(this)" />Select -->
						<!-- 							All<br /></th> -->

						<th class="displ" align="center">State Name</th>
						<th class="displ" align="center">Invoice No</th>
						<th class="displ" align="center">Head Component Name</th>
						<th class="displ" align="center">Sub Head Component Name</th>
						<th class="displ" align="center">Financial Year</th>
						<th class="displ" align="center">Invoice Date</th>
						<th class="displ" align="center">Amouont</th>
						<th class="displ" align="center">Action</th>
					</tr>
					<tr>
						<th align="center"><b> 1 </b></th>
						<th align="center"><b> 2 </b></th>
						<th align="center"><b> 3 </b></th>
						<th align="center"><b> 4 </b></th>
						<th align="center"><b> 5 </b></th>
						<th align="center"><b> 6 </b></th>

						<th align="center"><b> 7 </b></th>

						<th align="center"><b> 8 </b></th>


						<th align="center"><b> </b></th>
					</tr>
				</thead>


				<c:if test="${not empty invoiceData}">
					<c:forEach var="list" items="${invoiceData}" varStatus="status">


						<tr>

							<td>${status.count}</td>
							<td><c:out value="${list.stateName}" /></td>
							<td><c:out value="${list.invoiceno}" /></td>
							<td><c:out value="${list.componentName}" /></td>
							<td><c:out value="${list.finCode}" /></td>
							<td><c:out value="${list.invoiceDt}" /></td>
							<td><c:out value="${list.amount}" /></td>
							


							<td><a href="#editMasteractDistrictModal"
								class="editdistrict" data-toggle="modal"><i
									class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
								<input type="hidden" id="id" value="${list.pfmsid}"></td>
						</tr>
					</c:forEach>


				</c:if>

			</table>

			
		</form:form>
	</div>


<!-- 	<div id="editMasteractDistrictModal" class="modal fade"> -->
<!-- 		<div class="modal-dialog"> -->
<!-- 			<div class="modal-content"> -->
<!-- 				<form method="post" -->
<%-- 					action="${pageContext.request.contextPath}/updateVillageData"> --%>
<!-- 					<div class="modal-header"> -->
<!-- 						<h4 id="pheader" class="modal-title">Mapped with Project</h4> -->
<!-- 						<button type="reset" class="close" -->
<!-- 							onClick="window.location.reload();" data-dismiss="modal" -->
<!-- 							aria-hidden="true">&times;</button> -->
<!-- 					</div> -->
<!-- 					<div class="modal-body"> -->
<!-- 						<input type="hidden" id="lgdd" name="id1"> <input -->
<!-- 							type="hidden" id="leveld" name="lvl1"> -->


<!-- 						<div class="form-group"> -->
<!-- 							<label id="vname">Project Code</label> <input type="text" -->
<!-- 								class="form-control" id="districtNamed" required="required" -->
<!-- 								autocomplete="off" name="villageName1" value=""> -->

<!-- 						</div> -->

<!-- 					</div> -->
<!-- 					<div class="modal-footer"> -->
<!-- 						<input type="button" class="btn btn-default" data-dismiss="modal" -->
<!-- 							onClick="window.location.reload();" value="Cancel"> <input -->
<!-- 							type="submit" class="btn btn-info" value="Update"> -->

<!-- 					</div> -->
<!-- 				</form> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->

	
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>
