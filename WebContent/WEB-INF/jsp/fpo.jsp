
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.input {
	width: inherit !important;
}

.halfwidth {
	width: 100px !important;
}
</style>

<script type="text/javascript">

function addRating() {
	var k=document.getElementById("noOf").value;
	//alert(k+ "kk");
	  if (k <= 5 && k >= 1) {
	    
	  } else {
		  document.getElementById("noOf").value="";
	    alert("Invalid Input. Please enter an integer between 1-5.");
	    return false;
	  }
	}


</script>


<div class="maindiv">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script
		src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>

	<div class="col formheading" style="">
		<h4>
			<c:forEach var="group" items="${groupType}" >
 <c:forEach var="files" items="${group.value}">
 <c:choose>
				<c:when test="${group.key == 'newFPO'}">
				<div class="col formheading" style=""><h4><u>Add/ Edit Newly Created Farmer Producer Organization(FPOs)</u></h4> </div>
				</c:when>
				<c:otherwise>
				<div class="col formheading" style=""><h4><u>Add/ Edit Existing Farmer Producer Organization(FPOs)</u></h4> </div>
				</c:otherwise>
				</c:choose>
			</c:forEach>
			</c:forEach>
			
		</h4>
	</div>
	<hr />
	<label class="errormessage"></label>
	<form class="form-inline" autocomplete="off">

		<div class="form-group col-md-3">
			<label for="project" class="">Project:- </label> <select
				class="form-control col project" id="project" name="project">
				<option value="">--Select Project--</option>
				<c:forEach items="${projectList}" var="project">
					<option value="<c:out value="${project.key}"/>"><c:out
							value="${project.value}" /></option>
				</c:forEach>
			</select>
		</div>

		<div class="form-group col-md-3">
			<label for="project" class="">Group Type:- </label> <select
				class="form-control col groupType" id="groupType" name="groupType">
				<option value="">--Select Type--</option>
				<c:forEach items="${groupType}" var="group">
					<option value="<c:out value="${group.key}"/>"><c:out
							value="${group.value}" /></option>
				</c:forEach>
			</select>
		</div>

		<div class="form-group col-md-3 ">
			<label for="txtNoOf" class="lblNoOf noOf d-none"></label> <input
				type="text" class="form-control col-3 noOf d-none" id="noOf"
				name="noOf" onmousedown="numericOnly(event)" onblur="addRating(this);">
		</div>

		<div class="form-group col-md-2">
			<label for="txtNoOf" class="">&nbsp;</label>
			<button class="btn btn-info d-none" id="btnGo" name="btnGo">Go</button>
		</div>
		<br /> <br />
		<hr />
		<div style="margin-right: auto; margin-right: auto;">
			<table id="tblFPODetails">

			</table>

			<div class="form-group col text-center d-none button">
				<br /> <br />
				<!--  <button  class="btn btn-info col-2" id="btnCancel" style = "margin-left:30%;" name="btnCancel">Reset</button>&nbsp;&nbsp;
   -->
				<button class="btn btn-info col-2" id="btnSave"
					style="margin-left: 30%;" name="btnSave">Save as Draft</button>
			</div>

			<br />


			<table class="projectFPODraftTable d-none" id="projectFPODraftTable"
				name="projectFPODraftTable">
				<thead>
					<tr>
						<th colspan=9 style="text-align: center">Draft Data</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th>S No.</th>
						<th style="width: 200px">Name of FPOs</th>
						<th style="width: 100px">Dept. /Org. /Scheme.</th>
						<th style="width: 100px">Registration No.</th>
						<th style="width: 150px">Date of Registration</th>
						<th style="width: 100px">No. of members of FPO</th>
						<th style="width: 350px">Core Activity</th>
						<th style="width: 100px">Avg. turnover of FPO(in rs.)</th>
						<th style="width: 200px">No. of Farmer Associated with FPO</th>
					</tr>
				</thead>
				<tbody id="fpodraftbody"></tbody>
			</table>


			<br />

			<table class="projectFPOFinalTable d-none" id="projectFPOFinalTable"
				name="projectFPOFinalTable">
				<thead>
					<tr>
						<th colspan=9 style="text-align: center">List of Lock Data</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th>S No.</th>
						<th style="width: 200px">Name of FPOs</th>
						<th style="width: 100px">Dept. /Org./Scheme.</th>
						<th style="width: 100px">Registration No.</th>
						<th style="width: 150px">Date of Registration</th>
						<th style="width: 220px">No. of members of FPO</th>
						<th style="width: 350px">Core Activity</th>
						<th style="width: 100px">Avg. turnover of FPO(in rs.)</th>
						<th style="width: 200px">No. of Farmer Associated with FPO</th>
					</tr>
				</thead>
				<tbody id="fpofinalbody"></tbody>
			</table>

		</div>
	</form>

</div>


<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/fpo.js" />'></script>
</body>
</html>
