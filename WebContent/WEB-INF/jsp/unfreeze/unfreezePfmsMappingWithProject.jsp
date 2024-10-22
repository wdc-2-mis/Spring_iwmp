<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<body>
	<div class="maindiv">
		<div class="col" style="text-align: center;">
			<h4>
				<u>Unfreeze PFMS Mapping With Project</u>
			</h4>
		</div>
		<form:form autocomplete="off" name="unfreezePfmsMappingWithProject"
			id="unfreezePfmsMappingWithProject" action="unfreezePfmsMappingWithProject"
			method="get">
			<input type="hidden" name="projid" id="projid" value="" />
			<table>
				<tr>
					<td class="label">State <span style="color: red;">*</span></td>
					<td><span class="stateError"></span><select name="state" id="state"
						onchange="this.form.submit();" required="required">
							<option value="">--Select--</option>

							<c:if test="${not empty stateList}">
								<c:forEach items="${stateList}" var="lists">
									<c:if test="${lists.key eq state}">
										<option value="<c:out value='${lists.key}'/>"
											selected="selected"><c:out value="${lists.value}" /></option>
									</c:if>
									<c:if test="${lists.key ne state}">
										<option value="<c:out value='${lists.key}'/>"><c:out
												value="${lists.value}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
					</select></td>

					<td class="label">District <span style="color: red;">*</span></td>
					<td><span class="districtError"></span><select name="district" id="district"
						onchange="this.form.submit();" required="required">
							<option value="">--Select District--</option>
							<c:if test="${not empty districtList}">
								<c:forEach items="${districtList}" var="lists">
									<c:if test="${lists.key eq district}">
										<option value="<c:out value='${lists.key}'/>"
											selected="selected"><c:out value="${lists.value}" /></option>
									</c:if>
									<c:if test="${lists.key ne district}">
										<option value="<c:out value='${lists.key}'/>"><c:out
												value="${lists.value}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
					</select></td>

					<td class="label">Project  <span style="color: red;">*</span></td>
					<td><span class="projectError"></span>
					<select name="project" id="project" required="required">
							<option value="0">--All Project--</option>
							<c:if test="${not empty ProjectList}">
								<c:forEach items="${ProjectList}" var="lists">
									<c:if test="${lists.key eq project}">
										<option value="<c:out value='${lists.key}'/>"
											selected="selected"><c:out value="${lists.value}" /></option>
									</c:if>
									<c:if test="${lists.key ne project}">
										<option value="<c:out value='${lists.key}'/>"><c:out
												value="${lists.value}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
					</select></td>

					<td align="left">&nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="getComTranxDetail" name="getComTranxDetail" value='Get Data' />
					</td>
				</tr>
			</table>
		</form:form>


		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of Completed Transactions </u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="tranxCompletedDetailTable">
						<thead class ="theadcomplist" id = "theadcomplist">
							<tr>
								<th class="text-center">Transaction Id &nbsp;<input type="checkbox" id ="checkAllTranxIdc"  onchange="selectAllCom(this);"/></th>
								<th>Transaction Date</th>
								<th>Component Code</th>
								<th>Component Name</th>
								<th>Invoice No.</th>
								<th>Total Amount</th>
								<th>Project Name</th>
							</tr>

						</thead>
						<tbody id ="tbodyCompletedTranxDetails">
							
						</tbody>
		</table>
		</div>
		</div>
		<div class="form-row">
     		<div class="form-group">
     	 	<label for="btnGetActivity"> &nbsp;</label>
     		<input type="button" class="d-none btn btn-info" id="completed" name="completed" value='Unfreeze' /> 
     		</div>
  		</div>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<script src='<c:url value="/resources/js/pfmsMappingWithProject.js" />'></script>
</body>
</html>