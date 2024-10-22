<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css"> -->
	
<link rel="stylesheet" type="text/css"	href="<c:url  value="/resources/css/all.min.css" />">
	
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

<!-- <link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css"> -->
	
	<script src='<c:url value="/resources/js/1.12.4jquery.min.js" />'></script>
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> -->
<script src='<c:url value="/resources/js/3.3.7bootstrap.min.js" />'></script>

<link rel="stylesheet" type="text/css"	href="<c:url  value="/resources/css/phystyle.css" />">

<!-- <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css"> -->
<link rel="stylesheet" type="text/css"	href="<c:url  value="/resources/css/jquery.dataTables.min.css" />">

<!-- <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css"> -->
<link rel="stylesheet" type="text/css"	href="<c:url  value="/resources/css/buttons.dataTables.min.css" />">




<style>
/* cyrillic-ext */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu72xKOzY.woff2) format('woff2');
  unicode-range: U+0460-052F, U+1C80-1C88, U+20B4, U+2DE0-2DFF, U+A640-A69F, U+FE2E-FE2F;
}
/* cyrillic */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu5mxKOzY.woff2) format('woff2');
  unicode-range: U+0301, U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;
}
/* greek-ext */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu7mxKOzY.woff2) format('woff2');
  unicode-range: U+1F00-1FFF;
}
/* greek */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu4WxKOzY.woff2) format('woff2');
  unicode-range: U+0370-03FF;
}
/* vietnamese */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu7WxKOzY.woff2) format('woff2');
  unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
}
/* latin-ext */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu7GxKOzY.woff2) format('woff2');
  unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/roboto/v30/KFOmCnqEu92Fr1Mu4mxK.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}
/* hebrew */
@font-face {
  font-family: 'Varela Round';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/varelaround/v19/w8gdH283Tvk__Lua32TysjIfpcuPP9g.woff2) format('woff2');
  unicode-range: U+0590-05FF, U+200C-2010, U+20AA, U+25CC, U+FB1D-FB4F;
}
/* vietnamese */
@font-face {
  font-family: 'Varela Round';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/varelaround/v19/w8gdH283Tvk__Lua32TysjIfqMuPP9g.woff2) format('woff2');
  unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
}
/* latin-ext */
@font-face {
  font-family: 'Varela Round';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/varelaround/v19/w8gdH283Tvk__Lua32TysjIfqcuPP9g.woff2) format('woff2');
  unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Varela Round';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/varelaround/v19/w8gdH283Tvk__Lua32TysjIfp8uP.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
}

/* fallback */
@font-face {
  font-family: 'Material Icons';
  font-style: normal;
  font-weight: 400;
  src: url(https://fonts.gstatic.com/s/materialicons/v139/flUhRq6tzZclQEJ-Vdg-IuiaDsNc.woff2) format('woff2');
}

.material-icons {
  font-family: 'Material Icons';
  font-weight: normal;
  font-style: normal;
  font-size: 24px;
  line-height: 1;
  letter-spacing: normal;
  text-transform: none;
  display: inline-block;
  white-space: nowrap;
  word-wrap: normal;
  direction: ltr;
  -webkit-font-feature-settings: 'liga';
  -webkit-font-smoothing: antialiased;
}
</style>






<script>



	$(document).ready(function() {
		 $('#inactiveVillageList1').dataTable({
			//"sDom": 'rtp',
			"sProcessing" : "full_numbers", // "simple" option for 'Previous' and 'Next' buttons only
             "searching" : false,
			"ordering" : false,
			 "oLanguage" : {
				"sEmptyTable" : "Data is not Available"
			} 
		});
		
		$('.dataTables_length').addClass('bs-select'); 
		
		
		
		
		$('#exportExcel').click(function(e) {
			e.preventDefault();
		//	clearField();
			var now = new Date();
			var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
			var tableName = "#inactiveVillageList1";
			var colLength=$(tableName+" tr:first th").length;
			var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
			var reportName = "List of Mismatch LGD Directory (Blocks / Grampanchayat / Villages) to activate";
			var fileName="List of Mismatch LGD Directory (Blocks / Grampanchayat / Villages) to activate";
			 var headerHtml = "<table>"+
			 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
			    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
			    
			    "<tr><td></td></tr></table>";
			    var footerHtml = "<table>"+
			    "<tr><td colspan='"+colLength+"' style='text-align:center'>Report Generated by WDC-PMKSY software on: "+jsDate+"</td></tr></table>";  
			 exportTableToExcel('inactiveVillageList1', fileName,headerHtml,footerHtml);
			});
		
		$('#exportPDF').click(function(e) {
			e.preventDefault();
			var table="";
			var reportName = "List of Mismatch LGD Directory (Blocks / Grampanchayat / Villages) to activate";
			var tableName = "#inactiveVillageList1";
			var columnSpan =false;
			var colLength=$("#inactiveVillageList1 tr:first th").length;
			if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
				createDataTable(reportName,tableName,columnSpan,colLength);
			}
			//createDataTable(headerTitle,reportName,tableName);
			//trigger pdf button of datatable
			table=$(tableName).DataTable().buttons(0,1).trigger();
		//	table.buttons('.buttons-pdf').nodes().css("display", "none");
		//	table.buttons('.buttons-excel').nodes().css("display", "none");
		});
		
		
		
	});

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

		var selectedValue = $("#state").val();

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

		})
	};

	function getBlock() {
		var selectedValue = $("#district").val();
		var slctSubcat = $('#block'), option = "";
		slctSubcat.empty();
		slctSubcat.append('<option value="0"> --All Blocks--</option>');
		$.ajax({
			type : 'POST',
			url : "getBlockData",
			data : {
				"distCode" : selectedValue
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

		})
	};

	function getGrampanchayat() {

		var selectedValue = $("#block").val();
		//	alert(selectedValue);
		var slctSubcat = $('#gp'), option = "";
		slctSubcat.empty();
		slctSubcat.append('<option value="0"> --All Gram Panchayat--</option>');
		$.ajax({
			type : 'POST',
			url : "getGpData",
			data : {
				"blockCode" : selectedValue
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

		})
	};
	

	
</script>

<body>
	
		<div class="container-fluid">
        <div class="table-title">
				<div class="row">

					<div class="col-sm-9">
					<br>
						<h5>List of Mismatch LGD Directory (Blocks / Grampanchayat /
							Villages) to activate</h5>

					</div>

				</div>
			</div>
			<%-- 			<label style="color: blue; font-size: 30px;">${message}</label> --%>


			<form:form class="row row-cols-lg-auto g-3 align-items-left"
				name="listofInactivevill" id="listofInactivevill"
				modelAttribute="villageDetail" action="activateVillage"
				method="post">
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
				<div class="form-row">
					<div class="form-group col-md-2.2">
						<label for="inputState" style="width: 100px;"><b>State:</b></label> <label><c:out
								value="${sessionScope.stName}" /></label>
						<%-- 						<form:select onchange="javascript:getDistrict();" id="state" --%>
						<%-- 							path="stateCode"> --%>
						<%-- 							<form:option value="-1" label="---Select State---"></form:option> --%>
						<%-- 							<form:options items="${statelist}" /> --%>
						<%-- 						</form:select> --%>
						<%-- 						<form:errors path="stateCode" cssclass="errormsg" /> --%>
					</div>

					<div class="form-group col-md-3.8">
						<label style="width: 250px;"><b>Mismatch LGD Directory Level:</b></label>
						<form:select id="dl" path="directoryLevel">
							<form:option value="0" label="--Directory Level--"></form:option>
							<form:option value="1" label="Block Level"></form:option>
							<form:option value="2" label="Gram Panchayat Level"></form:option>
							<form:option value="3" label="Village Level"></form:option>
						</form:select>
						<form:errors path="directoryLevel" cssclass="errormsg" />
					</div>
					<div class="form-group col-md-3.5">
						<label style="width: 160px;"><b>District Name:</b></label>
						<form:select onchange="javascript:getBlock();" id="district"
							path="distCode">
							<form:option value="0" label="---All Districts---"></form:option>
							<form:options items="${districtList}" />
						</form:select>
						<form:errors path="distCode" cssclass="errormsg" />
					</div>




					<div class="form-group col-md-3.5">
						<label for="name" style="width: 100px;"><b>Block Name: </b></label>
						<form:select id="block" path="blockCode">
							<form:option value="0" label="---All Blocks---"></form:option>
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
				
				<div class="container bg-light">
					<div class="col-md-1 text-center">
						<input type="button" name="exportExcel" id="exportExcel"  value="Excel" style="align-content:left; "/>
					</div>
				</div>
		
	 <!--   	<input type="button" name="exportPDF" id="exportPDF"  value="PDF" /> -->

				<table id="inactiveVillageList1" 
					class="table table-striped table-hover">
					<thead>
						<tr>
							<th class="displ" align="center">Sl. No.</th>
							<th><input type="checkbox" onClick="selectall(this)" />Select
								All<br /></th>

							<th class="displ" align="center">State Name</th>
							<th class="displ" align="center">State LGD Code</th>
							<th class="displ" align="center">District Name</th>
							<th class="displ" align="center">District LGD Code</th>
							<th class="displ" align="center">Block Name</th>
							<th class="displ" align="center">Block LGD Code</th>
							<c:if test="${villageDetail.directoryLevel>1}">
								<th class="displ" align="center">Gram Panchayat Name</th>
								<th class="displ" align="center">Gram Panchayat LGD Code</th>
								<c:if test="${villageDetail.directoryLevel>2}">
									<th class="displ" align="center">Villages Name</th>
									<th class="displ" align="center">Villages LGD Code</th>
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
							<th align="center"><b> 6 </b></th>
							<th align="center"><b> 7 </b></th>
							<th align="center"><b> 8 </b></th>
							<c:if test="${villageDetail.directoryLevel>1}">
								<th align="center"><b> 9 </b></th>
								<th align="center"><b> 10 </b></th>
								<c:if test="${villageDetail.directoryLevel>2}">
									<th align="center"><b> 11 </b></th>
									<th align="center"><b> 12 </b></th>
								</c:if>
							</c:if>
							<th align="center"><b> </b></th>
						</tr>
					</thead>
					<c:if test="${villageDetail.directoryLevel==3}">
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
									<td>${status.count}</td>


									<td><c:if test="${list.active eq 'False'}">
											<form:checkbox
												path="inactivevillageList[${status.index}].updatestatus" />
											<form:hidden
												path="inactivevillageList[${status.index}].vcode" />
										</c:if></td>
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

									<td><c:if test="${list.active eq 'true'}">
											<c:out value="Active" />
										</c:if> <c:if test="${list.active eq 'False'}">
											<c:out value="Inactive" />
										</c:if></td>
									</tr>
								</c:forEach>


							</c:if>
						</c:if>
					</c:if>
					<c:if test="${villageDetail.directoryLevel==1}">
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
									<td>${status.count}</td>


									<td><c:if test="${list.active eq 'False'}">
											<form:checkbox
												path="inactiveblockList[${status.index}].updatestatus" />
											<form:hidden path="inactiveblockList[${status.index}].bcode" />
										</c:if></td>
									<td><c:out value="${list.iwmpDistrict.iwmpState.stName}" /></td>
									<td><c:out
											value="${list.iwmpDistrict.iwmpState.stateCodelgd}" /></td>
									<td><c:out value="${list.iwmpDistrict.distName}" /></td>
									<td><c:out value="${list.iwmpDistrict.districtCodelgd}" /></td>
									<td><c:out value="${list.blockName}" /></td>
									<td><c:out value="${list.blockCodelgd}" /></td>


									<td><c:if test="${list.active eq 'true'}">
											<c:out value="Active" />
										</c:if> <c:if test="${list.active eq 'false'}">
											<c:out value="Inactive" />
										</c:if></td>
									</tr>
								</c:forEach>


							</c:if>
						</c:if>
					</c:if>
					<c:if test="${villageDetail.directoryLevel==2}">
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
									<td>${status.count}</td>


									<td><c:if test="${list.active eq 'False'}">
											<form:checkbox
												path="inactivegpList[${status.index}].updatestatus" />
											<form:hidden path="inactivegpList[${status.index}].gcode" />
										</c:if></td>
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

									<td><c:if test="${list.active eq 'true'}">
											<c:out value="Active" />
										</c:if> <c:if test="${list.active eq 'False'}">
											<c:out value="Inactive" />
										</c:if></td>
									</tr>
								</c:forEach>


							</c:if>
						</c:if>
					</c:if>
				</table>

				<div class="col-auto">
					<c:if
						test="${villageDetail.inactivevillageList!=null ||villageDetail.inactiveblockList!=null ||villageDetail.inactivegpList!=null}">
						<c:if
							test="${not empty villageDetail.inactivevillageList || not empty villageDetail.inactiveblockList || not empty villageDetail.inactivegpList}">
							<c:if test="${villageDetail.directoryLevel==3}">
								<input type="submit" name="action2" class="square_btn"
									value="Activate Selected Villages">
							</c:if>
							<c:if test="${villageDetail.directoryLevel==2}">
								<input type="submit" name="action2" class="square_btn"
									value="Activate Selected Gram Panchayats">
							</c:if>
							<c:if test="${villageDetail.directoryLevel==1}">
								<input type="submit" name="action2" class="square_btn"
									value="Activate Selected Blocks">
							</c:if>
						</c:if>
					</c:if>
				</div>
				
			</form:form>
		</div>

	
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>
