<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<body>
	<div class="maindiv">
		<div class="col formheading" style=""><h4><u>Add Village / Gram Panchayat / Block </u></h4> </div>
		<form name="addVillGrmPnchytBlk" id="addVillGrmPnchytBlk">
			<lable class="message badge badge-danger error"></lable>
			<hr/>
			<div class="row">
			<div class="form-group col-3">
      			<label for="addMaster">Add Master:</label>
      			<span class="addMasterError"></span>
      			<select class="form-control addMaster" id="addMaster" name="addMaster" >
    				<option value="">--Select--</option>
					<option value="3">Block</option>
					<option value="2">Gram Panchayat</option>
					<option value="1">Village</option>
    			</select>
    		</div>
			<div class="form-group col-3">
      			<label for="state">State:</label>
      			<span class="stateError"></span>
      			<select class="form-control state" id="state" name="state" >
    				<option value="">--Select--</option>
<%--     				<c:forEach items="${allStateList}" var="state"> --%>
<%-- 						<option value="<c:out value="${state.key}"/>"><c:out value="${state.value}" /></option> --%>
<%-- 					</c:forEach> --%>
    			</select>
    		</div>
    		<div class="form-group col-3">
      			<label for="district">District:</label>
      			<span class="districtError"></span>
      			<select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    			</select>
    		</div>
    		</div>
    		
    		<div class="row">
			<div class="form-group col-3">
      			<label for="block" id ="blockLabel" style="display: none;">Block:</label>
      			<span class="blockError"></span>
      			<select class="form-control block" id="block" name="block" style="display: none;" >
    				<option value="">--Select--</option>
    			</select>
    		</div>
    		<div class="form-group col-3">
      			<label for="gramPanchayat" id = "gpLabel" style="display: none;">Gram Panchayat:</label>
      			<span class="gramPanchayatError"></span>
      			<select class="form-control gramPanchayat" id="gramPanchayat" name="gramPanchayat" style="display: none;">
    				<option value="">--Select--</option>
    			</select>
    		</div>
    		</div>
    		
    		<div class="row">
    		<div class="form-group col-3">
      			<label for="lgdCode" id="lgdCodeLable" style="display: none;">LGD Code:</label>
      			<span class="lgdCodeError"></span>
    			<input type = "text" id="lgdCode" name="lgdCode" class="lgdCode form-control" style="display: none;"/>
    		</div>
    		<div class="form-group col-3">
      			<label for="lgdCode" id ="villLgdCode" style="display: none;">Village:</label>
      			<label for="lgdCode" id ="gpLgdCode" style="display: none;">Gram Panchayat:</label>
      			<label for="lgdCode" id ="blkLgdCode" style="display: none;">Block Name:</label>
      			<span class="villGrmBlkError"></span>
    			<input type = "text" id="villGrmBlk" name="villGrmBlk" class="villGrmBlk form-control" style="display: none;"/>
    		</div>
    		</div>
			<div class="form-row">
				<div class="form-group col">
     				<button class="btn btn-info" id="saveAsDraft" name="saveAsDraft" >Save</button>
     			</div>
     		</div>
     		<div class="form-row">
     <div class="form-group col">
     <hr/>
     <h5 class="text-center font-weight-bold"><u>List of LGD Code that already exist</u></h5>
     <table class="table table-bordered table-striped table-highlight w-auto" id="addVillGrmPnchytBlkList">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th >S.No.</th>
								<th>State Name</th>
								<th>State LGD Code</th>
								<th>District Name</th>
								<th>District LGD Code</th>
								<th class="text-center blockName d-none" id = "blockName">Block Name</th>
								<th class="text-center blockLgdCode d-none">Block LGD Code</th>
								<th class="text-center grmPnchytName d-none">Gram Panchayat Name</th>
								<th class="text-center grmPnchytLgdCode d-none">Gram Panchayat LGD Code</th>
								<th class="text-center villName d-none">Village Name</th>
								<th class="text-center villLgdCode d-none">Village LGD Code</th>
								<th>Status</th>
							</tr>

						</thead>
						<tbody id ="tbodylgdCodeList">
							
						</tbody>
		</table>
		</div>
		</div>
		</form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
<script src='<c:url value="/resources/js/addVillGrmPnchytBlkMstr.js" />'></script>
</body>
</html>