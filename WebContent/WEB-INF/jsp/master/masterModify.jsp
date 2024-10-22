<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/masterModify.js" />'></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<body>

	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">

				<div class="col-sm-6">
					<br>
					<h5>List of Locations (Blocks / Grampanchayat / Villages) for
						Modification</h5>

				</div>
				<!-- 				<div class="col-sm-6"> -->
				<!-- 					<a href="#addphyactModal" id="add" class="btn btn-success" -->
				<!-- 						data-toggle="modal"><i class="material-icons" -->
				<!-- 						data-toggle="tooltip">&#xE147;</i> <span>Add New Records</span></a> -->

				<!-- 				</div> -->
			</div>

		</div>
		<%-- 			<label style="color: blue; font-size: 30px;">${message}</label> --%>

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
			name="masterlist" id="masterlist" modelAttribute="villageDetail"
			action="updateMasterList" method="post">
			
			<div class="form-row">
				
				<div class="form-group col-md-3.8">
					<label style="width: 150px;"><b>Master Updation:</b></label>
					<form:select id="dl" path="directoryLevel" onchange="levelselection();">
						<form:option value="0" label="--Master Level--"></form:option>
						<form:option value="1" label="District Master"></form:option>
						<form:option value="2" label="Block Master"></form:option>
						<form:option value="3" label="Gram Panchayat Master"></form:option>
						<form:option value="4" label="Village Master"></form:option>
					</form:select>
					<form:errors path="directoryLevel" cssclass="errormsg" />
				</div>
				
				<div class="form-group col-md-2.2">
					<label for="inputState" style="width: 50px;"><b>State:</b></label>
					<c:if test="${not empty sessionScope.stName}">
					<label><c:out value="${sessionScope.stName}" /></label>
					</c:if>
					<c:if test="${empty sessionScope.stName}">
						<form:select onchange="javascript:getDistrict();" id="state" path="stateCode"> 
							<form:option value="0" label="---Select State---"></form:option>
							<form:options items="${statelist}" /> 
						</form:select> 
<%-- 					 	<form:errors path="stateCode" cssclass="errormsg" />  --%>
					</c:if>
				</div>

				<div class="form-group col-md-3.1">
					<label style="width: 125px;"><b>District Name:</b></label>
					<form:select onchange="javascript:getBlock();" id="district"
						path="distCode">
						<form:option value="0" label="---Select All---"></form:option>
						<form:options items="${districtList}" />
					</form:select>
					<form:errors path="distCode" cssclass="errormsg" />
				</div>
				<div class="form-group col-md-3.1">
					<label for="name" style="width: 138px;"><b>Block Name:
					</b></label>
					<form:select id="block" path="blockCode">
						<form:option value="0" label="---Select All---"></form:option>
						<form:options items="${blockList}" />
					</form:select>

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
						<th class="displ" align="center">State LGD Code</th>
						<th class="displ" align="center">District Name</th>
						<th class="displ" align="center">District LGD Code</th>
						<c:if test="${villageDetail.directoryLevel>1}">
							<th class="displ" align="center">Block Name</th>
							<th class="displ" align="center">Block LGD Code</th>
							<c:if test="${villageDetail.directoryLevel>2}">
								<th class="displ" align="center">Gram Panchayat Name</th>
								<th class="displ" align="center">Gram Panchayat LGD Code</th>
								<c:if test="${villageDetail.directoryLevel>3}">
									<th class="displ" align="center">Villages Name</th>
									<th class="displ" align="center">Villages LGD Code</th>
								</c:if>
							</c:if>
						</c:if>
						<th class="displ" align="center">Action</th>
					</tr>
					<tr>
						<th align="center"><b> 1 </b></th>
						<th align="center"><b> 2 </b></th>
						<th align="center"><b> 3 </b></th>
						<th align="center"><b> 4 </b></th>
						<th align="center"><b> 5 </b></th>
<!-- 						<th align="center"><b> 6 </b></th> -->
						<c:if test="${villageDetail.directoryLevel>1}">
							<th align="center"><b> 6 </b></th>
							<th align="center"><b> 7 </b></th>
							<c:if test="${villageDetail.directoryLevel>2}">
								<th align="center"><b> 8 </b></th>
								<th align="center"><b> 9 </b></th>
								<c:if test="${villageDetail.directoryLevel>3}">
									<th align="center"><b> 10 </b></th>
									<th align="center"><b> 11 </b></th>
								</c:if>
							</c:if>
						</c:if>
						<th align="center"><b> </b></th>
					</tr>
				</thead>
				<tbody id="tbodyvillGrmBlkModifctn">
				<c:if test="${villageDetail.directoryLevel==1}">
					<c:if test="${villageDetail.districtList!=null}">
						<c:if test="${not empty villageDetail.districtList}">
							<c:forEach var="list" items="${villageDetail.districtList}"
								varStatus="status">


								<tr>

									<td>${status.count}</td>


<!-- 									<td> -->

<%-- 											<form:hidden path="districtList[${status.index}].dcode" /> --%>
<!-- 										</td> -->
									<td><c:out value="${list.iwmpState.stName}" /></td>
									<td><c:out value="${list.iwmpState.stateCodelgd}" /></td>
									<td><c:out value="${list.distName}" /></td>
									<td><c:out value="${list.districtCodelgd}" /></td>


									<td><a href="#editMasteractDistrictModal" class="editdistrict"
										data-toggle="modal"><i class="material-icons"
											data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
										type="hidden" id="id" value="${list.dcode}"><input
										type="hidden" id="lvl" value="${villageDetail.directoryLevel}"></td>
								</tr>
							</c:forEach>


						</c:if>
					</c:if>
				</c:if>
				<c:if test="${villageDetail.directoryLevel==4}">
					<c:if test="${villageDetail.inactivevillageList!=null}">
						<c:if test="${not empty villageDetail.inactivevillageList}">
							<c:forEach var="list"
								items="${villageDetail.inactivevillageList}" varStatus="status">
								<c:if test="${list.active eq 'false'}">
									<tr class="table-danger">
								</c:if>
								<c:if test="${list.active eq 'true'}">
									<tr>
								</c:if>
								<td>${status.count}<form:hidden path="inactivevillageList[${status.index}].vcode" /></td>


<%-- 								<td><c:if test="${list.active eq 'False'}"> --%>
<%-- 										<form:checkbox --%>
<%-- 											path="inactivevillageList[${status.index}].updatestatus" /> --%>
<%-- 										<form:hidden path="inactivevillageList[${status.index}].vcode" /> --%>
<%-- 									</c:if></td> --%>
								<td><c:out
										value="${list.iwmpGramPanchayat.iwmpBlock.iwmpDistrict.iwmpState.stName}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.iwmpBlock.iwmpDistrict.iwmpState.stateCodelgd}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.iwmpBlock.iwmpDistrict.distName}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.iwmpBlock.iwmpDistrict.districtCodelgd}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.iwmpBlock.blockName}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.iwmpBlock.blockCodelgd}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.gramPanchayatName}" /></td>
								<td><c:out
										value="${list.iwmpGramPanchayat.gramPanchayatLgdCode}" /></td>
								<td><c:out value="${list.villageName}" /></td>
								<td><c:out value="${list.villageLgdcode}" /></td>

								<td><a href="#editMasteractModal" class="edit"
									data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
									type="hidden" id="id" value="${list.vcode}"> <input
									type="hidden" id="lvl" value="${villageDetail.directoryLevel}"></td>
								</tr>
							</c:forEach>


						</c:if>
					</c:if>
				</c:if>
				<c:if test="${villageDetail.directoryLevel==2}">
					<c:if test="${villageDetail.inactiveblockList!=null}">
						<c:if test="${not empty villageDetail.inactiveblockList}">
							<c:forEach var="list" items="${villageDetail.inactiveblockList}"
								varStatus="status">
								<c:if test="${list.active eq 'false'}">
									<tr class="table-danger">
								</c:if>
								<c:if test="${list.active eq 'true'}">
									<tr>
								</c:if>
								<td>${status.count}<form:hidden path="inactiveblockList[${status.index}].bcode" /></td>


<%-- 								<td><c:if test="${list.active eq 'False'}"> --%>
<%-- 										<form:checkbox path="inactiveblockList[${status.index}].updatestatus" /> --%>
<%-- 										<form:hidden path="inactiveblockList[${status.index}].bcode" /> --%>
<%-- 									</c:if></td> --%>
								<td><c:out value="${list.iwmpDistrict.iwmpState.stName}" /></td>
								<td><c:out
										value="${list.iwmpDistrict.iwmpState.stateCodelgd}" /></td>
								<td><c:out value="${list.iwmpDistrict.distName}" /></td>
								<td><c:out value="${list.iwmpDistrict.districtCodelgd}" /></td>
								<td><c:out value="${list.blockName}" /></td>
								<td><c:out value="${list.blockCodelgd}" /></td>

								<td><a href="#editMasteractBlockModal" class="editblock"
									data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
									type="hidden" id="id" value="${list.bcode}"><input
									type="hidden" id="lvl" value="${villageDetail.directoryLevel}"></td>
								</tr>
							</c:forEach>


						</c:if>
					</c:if>
				</c:if>

				<c:if test="${villageDetail.directoryLevel==3}">
					<c:if test="${villageDetail.inactivegpList!=null}">
						<c:if test="${not empty villageDetail.inactivegpList}">
							<c:forEach var="list" items="${villageDetail.inactivegpList}"
								varStatus="status">

								<c:if test="${list.active eq 'false'}">
									<tr class="table-danger">
								</c:if>
								<c:if test="${list.active eq 'true'}">
									<tr>
								</c:if>
								<td>${status.count}	
								<form:hidden path="inactivegpList[${status.index}].gcode" /></td>


<%-- 								<td><c:if test="${list.active eq 'False'}"> --%>
<%-- 										<form:checkbox --%>
<%-- 											path="inactivegpList[${status.index}].updatestatus" /> --%>

<%-- 									</c:if></td> --%>
								<td><c:out
										value="${list.iwmpBlock.iwmpDistrict.iwmpState.stName}" /></td>
								<td><c:out
										value="${list.iwmpBlock.iwmpDistrict.iwmpState.stateCodelgd}" /></td>
								<td><c:out value="${list.iwmpBlock.iwmpDistrict.distName}" /></td>
								<td><c:out
										value="${list.iwmpBlock.iwmpDistrict.districtCodelgd}" /></td>
								<td><c:out value="${list.iwmpBlock.blockName}" /></td>
								<td><c:out value="${list.iwmpBlock.blockCodelgd}" /></td>
								<td><c:out value="${list.gramPanchayatName}" /></td>
								<td><c:out value="${list.gramPanchayatLgdCode}" /></td>
								<td><a href="#editMasteractGramModal" class="editgram"
									data-toggle="modal"><i class="material-icons"
										data-toggle="tooltip" title="Edit">&#xE254;</i></a> <input
									type="hidden" id="id" value="${list.gcode}"> <input
									type="hidden" id="lvl" value="${villageDetail.directoryLevel}">
								</td>
								</tr>
							</c:forEach>


						</c:if>
					</c:if>
				</c:if>
				</tbody>
			</table>
		</form:form>
	</div>

	<!-- Edit Modal HTML -->
	<div id="editMasteractModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath}/updateVillageData">
					<div class="modal-header">
						<h4 id="pheader" class="modal-title">Edit Village Name</h4>
						<button type="reset" class="close"
							onClick="window.location.reload();" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">



						<input type="hidden" id="lgdv" name="id1" > 
            	        <input type="hidden" id="levelv" name="lvl1">

						<div class="form-group">
						<label id="gpName">Gram Panchayat Name</label>
							<select class="form-control grmPnchyt" id="grmPnchyt" name="grmPnchyt" >
    						<option value="">--Select--</option>
    						</select>
						
						</div>
						
						<div class="form-group">
							<label id="vname">Village Name</label> <input type="text"
								class="form-control" id="villageName1" required="required"
								autocomplete="off" name="villageName1">

						</div>

					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-info" value="Update">

					</div>
				</form>
			</div>
		</div>
	</div>

<div id="editMasteractDistrictModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath}/updateVillageData">
					<div class="modal-header">
						<h4 id="pheader" class="modal-title">Edit District Name</h4>
						<button type="reset" class="close"
							onClick="window.location.reload();" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
            	<input type="hidden" id="lgdd" name="id1" > 
            	<input type="hidden" id="leveld" name="lvl1">


						<div class="form-group">
							<label id="vname">District Name</label> <input type="text"
								class="form-control" id="districtNamed" required="required"
								autocomplete="off" name="villageName1" value="">

						</div>

					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-info" value="Update">

					</div>
				</form>
			</div>
		</div>
	</div>

<div id="editMasteractBlockModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath}/updateVillageData">
					<div class="modal-header">
						<h4 id="pheader" class="modal-title">Edit Block Name</h4>
						<button type="reset" class="close"
							onClick="window.location.reload();" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
            	<input type="hidden" id="lgdb" name="id1" > 
            	<input type="hidden" id="levelb" name="lvl1">
            	
            	
            			<div class="form-group">
						<label id="gpName">District Name</label>
							<select class="form-control distCode" id="distCode" name="distCode" >
    						<option value="">--Select--</option>
    						</select>
						
						</div>


						<div class="form-group">
							<label id="vname">Block Name</label> <input type="text"
								class="form-control" id="districtNameb" required="required"
								autocomplete="off" name="villageName1" value="">

						</div>

					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-info" value="Update">

					</div>
				</form>
			</div>
		</div>
	</div>

<div id="editMasteractGramModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post"
					action="${pageContext.request.contextPath}/updateVillageData">
					<div class="modal-header">
						<h4 id="pheader" class="modal-title">Edit Gram Name</h4>
						<button type="reset" class="close"
							onClick="window.location.reload();" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
            	<input type="hidden" id="lgdg" name="id1" > 
            	<input type="hidden" id="levelg" name="lvl1">
            	
            			<div class="form-group">
						<label id="gpName">Block Name</label>
							<select class="form-control blkCode" id="blkCode" name="blkCode" >
    						<option value="">--Select--</option>
    						</select>
						
						</div>


						<div class="form-group">
							<label id="vname">Gram Name</label> <input type="text"
								class="form-control" id="districtNameg" required="required"
								autocomplete="off" name="villageName1" value="">

						</div>

					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							onClick="window.location.reload();" value="Cancel"> <input
							type="submit" class="btn btn-info" value="Update">

					</div>
				</form>
			</div>
		</div>
	</div>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>
