<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/jquery-ui.css" />">
	
	<script src='<c:url value="/resources/js/jquery-1.8.2.js" />'></script>
	<script src='<c:url value="/resources/js/jquery-ui.js" />'></script>
<!-- <script src="http://code.jquery.com/jquery-1.8.2.js"></script> -->
<!-- <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script> -->
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
<!--  <script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Project</title>
<style>
</style>
<script type="text/javascript">
$('body').on('focus',".datepicker_recurring_start", function(){
    $(this).datepicker(
//     	    {changeMonth: true, changeYear: true}
    		 { changeMonth: true,
    		        changeYear: true,
        		 onClose: function(){
    			 if(getFinyear($(this).val(),$(this).attr('id'))==false)
    				 $(this).datepicker('setDate', null);
    		     },
		     });
});
	function add() {
		// Denotes total number of rows 
		var rowIdx = 0;

		// Adding a row inside the tbody. 
		$('#tbody')
				.append(
						"<tr id='R${rowIdx+1}'> "
								+ " 	<td align='center'>${rowIdx+1}</td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].projectSeqNo'	value='${proj.projectSeqNo}' /></td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].iwmpMFinYear.finYrCd'	value='${proj.iwmpMFinYear.finYrCd}' /></td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].projectStartDt'	value='${proj.projectStartDt}' /></td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].areaProposed'	value='${proj.areaProposed}' /></td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].projectCost'	value='${proj.projectCost}' /></td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].centralShareAmt'	value='${proj.centralShareAmt}' /></td>"
								+ " 	<td align='center'><input name='iwmpMProjectList[${rowIdx+1}].stateShareAmt'	value='${proj.stateShareAmt}' /></td>"
								+ "<td class='text-center'><button class='btn btn-danger remove' type='button'>Remove</button></td></tr>");
	};
	
// 	function getSequence(param1) {

// 		var form = document.getElementById('projectDetail');
// 		$(form)
// 				.append(
// 						'<input type="hidden" name="id" value="'+param1 +'" /><input type="hidden" name="onsequence" value="onsequence">');
// 			form.submit();
// 	};

	function getSequence(param1) {
		//alert('hi');
		 var  fyear=document.getElementById("iwmpMFinYear["+param1+"]");
		 var  dcode= document.getElementById("iwmpDistrict["+param1+"]");//$("#iwmpDistrict["+param1+"]").val();
		 var  seq= document.getElementById("projectSeqNo["+param1+"]");//$("#projectSeqNo["+param1+"]").val();
			  $.ajax({
		  	  type: 'POST',
		        url: "checkSequence",
		        data: {"dcode" : dcode.value,
		               "seq"  : seq.value,
		               "fcode": fyear.value},
		
		      success: function(data){
			   	if(data.length>0)
		 		  		{ alert(data);
			    seq.value='';}
		      },
		      error:function(xhr,status,er){
			        alert("error");
		          console.log(er);
		         seq.value='';
		      }
		  })
	};
	 function getFinyear(param1,param2) {
		//   var yid="iwmpMProjectList["+param2+"].iwmpMFinYear.finYrCd";
		//alert('hi');
		   var vSkill =document.getElementById("iwmpMFinYear["+param2+"]");
		   var vSkillText = vSkill.options[vSkill.selectedIndex].innerHTML;
		   var today = new Date(param1);
		    var curMonth = today.getMonth();
		//     alert('curMonth'+curMonth);
		    var fiscalYr = "";
		    if (curMonth > 2) { //
		        var nextYr1 = (today.getFullYear() + 1).toString();
		        fiscalYr = today.getFullYear().toString() + "-" + nextYr1.charAt(2) + nextYr1.charAt(3);
		    } else {
		        var nextYr2 = today.getFullYear().toString();
		        fiscalYr = (today.getFullYear() - 1).toString() + "-" + nextYr2.charAt(2) + nextYr2.charAt(3);
		    }
		 //   alert("aa"+fiscalYr);
		  //  alert("vSkillText"+vSkillText);
		     if(fiscalYr.localeCompare(vSkillText)!=0)			    
		   	{
		   		alert("Project Sanction Date must be in Selected Financial Year.");
		   		return false;
		   	}
		 };
		 function chkdate(param1) {
			 var vdate = document.getElementById(param1);
			 //  alert(vdate);
			 if(vdate.value!="")
				 {
				   var today = new Date(vdate.value);
				   var yid="iwmpMProjectList["+param1+"].iwmpMFinYear.finYrCd";
				   var vSkill = document.getElementById(yid);
				  // alert(vSkill);
				   var vSkillText = vSkill.options[vSkill.selectedIndex].innerHTML;
				  
				    var curMonth = today.getMonth();
				     
				    var fiscalYr = "";
				    if (curMonth > 3) { //
				        var nextYr1 = (today.getFullYear() + 1).toString();
				        fiscalYr = today.getFullYear().toString() + "-" + nextYr1.charAt(2) + nextYr1.charAt(3);
				    } else {
				        var nextYr2 = today.getFullYear().toString();
				        fiscalYr = (today.getFullYear() - 1).toString() + "-" + nextYr2.charAt(2) + nextYr2.charAt(3);
				    }
				    if(fiscalYr.localeCompare(vSkillText)!=0)			    
				   	{
				   		alert("Selected Financial Year not valid for Project Sanction Date.");
				   		vSkill.selectedIndex=0;
				   		vdate.value="";
				   		return false;
				   	}
				 }
			 };
	
//	function getFinyear(param1) {
		

// 		var a = document.getElementById('projectDetail');
// 		$(form)
// 				.append(
// 						'<input type="hidden" name="id" value="'+param1 +'" /><input type="hidden" name="ondate" value="ondate">');
// 			form.submit();
//	};
	
	
	function getValue(param1) {
		 var  ap=document.getElementById("areaProposed["+param1+"]");
		 var  at= document.getElementById("iwmpMAreaType["+param1+"]");
		 var stCode = $('.custom-select').val();//$("#iwmpDistrict["+param1+"]").val();
		 if(ap.value!="" && at.value>0)
			 {
				 var  pc= document.getElementById("projectCost["+param1+"]");//$("#projectSeqNo["+param1+"]").val();
				 var  csa= document.getElementById("centralShareAmt["+param1+"]");
				 var  ssa= document.getElementById("stateShareAmt["+param1+"]");
					  $.ajax({
				  	  type: 'POST',
				        url: "onChange",
				        data: {"ap" : ap.value,
				               "at"  : at.value,
				               "stCode":stCode
				              },
				
				      success: function(data){
					   	if(data.length>0)
						   	{
						 		pc.value=data[0];
				 		  		csa.value=data[1];
				 		  		ssa.value=data[2];
						   	}
				      },
				      error:function(xhr,status,er){
					        alert("error"+er);
				          console.log(er);
				      }
		 		 })
			 }
// 		var form = document.getElementById('projectDetail');
// 		$(form)
// 				.append(
// 						'<input type="hidden" name="id" value="'+param1 +'" /><input type="hidden" name="onchange" value="onchange">');
// 			form.submit();
	};
	function deleterecord(param1) {
		 var id=param1;
		var form = document.getElementById('projectDetail');
		$(form)
		.append(
				'<input type="hidden" name="id" value="'+param1 +'" />');
		form.submit();
	};

</script>
</head>
<body>
<div class="table-wrapper">
	<div class="table-title">
				<div class="row">

					<div class="col-sm-9">
						<h5>Add Project</h5>

					</div>

				</div>
			</div>
	
		<form:form class="form-horizontal" action="saveProject" method="post"
			modelAttribute="projectDetail">
			<div class="table-responsive">
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
				<%-- 			 <form:errors path = "*" cssClass = "errorblock" element = "div" /> --%>
				<table width="100%"
					class="table table-bordered table-striped table-highlight">
					<tr>
						<td>Select State</td>
						<td align="left"><form:select class="custom-select"
								path="stateCode">
								<form:option value="0" label="---Select---"></form:option>
								<form:options items="${statelist}" />
							</form:select> <form:errors path="stateCode" cssclass="errormsg" /></td>
						<td align="left"><input type="submit" class="square_btn"
							name="go" value="Go" /></td>
					</tr>
					<tr>
						<td colspan="3"><br /> <br /></td>
					</tr>
				</table>
				<c:if test="${projectDetail.iwmpMProjectList!=null}">

					<table id="projDetail"
						class="table table-bordered table-striped table-highlight w-auto">
						<tr>
							<th>No.</th>
							<th>Financial Year</th>
							<th>District</th>
							<th>Project Seq.</th>
							<th>Project Sanction Date</th>
							<th>Project Period (Yrs)</th>

							<th>Area Proposed for <br />Treatment (in Hectares) *
							</th>

							<th>Area Type</th>
							<th>Total Project Cost (Rs. in lakh) *</th>
							<th>Central Share (Rs. in lakh) *</th>
							<th>State Share (Rs. in lakh) *</th>
							<th></th>

							<!-- 								<th>Phone</th> -->
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
							<th></th>
						</tr>
						<c:forEach items="${projectDetail.iwmpMProjectList}" var="proj"
							varStatus="status">
							<tr>
								<td align="center">${status.count}</td>
								<td><form:select id="iwmpMFinYear[${status.index}]"
										class="custom-select-sm"
										path="iwmpMProjectList[${status.index}].iwmpMFinYear.finYrCd"
										value="${proj.iwmpMFinYear.finYrCd}"
										onchange="javascript:chkdate(${status.index})">
										<form:option value="-1" label="---Select---"></form:option>
										<form:options items="${financialYear}" itemLabel="finYrDesc"
											itemValue="finYrCd" />
									</form:select> <form:errors
										path="iwmpMProjectList[${status.index}].iwmpMFinYear.finYrCd"
										class="errormsg" /></td>
								<td><form:select id="iwmpDistrict[${status.index}]"
										class="custom-select-sm"
										path="iwmpMProjectList[${status.index}].iwmpDistrict.dcode"
										value="${proj.iwmpDistrict.dcode}">
										<form:option value="0" label="---Select---"></form:option>
										<form:options items="${districtList}" />
									</form:select> <form:errors
										path="iwmpMProjectList[${status.index}].iwmpDistrict.dcode"
										cssclass="errormsg" /></td>
								<td><input class="form-control"
									id="projectSeqNo[${status.index}]"
									name="iwmpMProjectList[${status.index}].projectSeqNo"
									onchange="javascript:getSequence(${status.index})"
									value="${proj.projectSeqNo}" /> <form:errors
										path="iwmpMProjectList[${status.index}].projectSeqNo"
										cssclass="errormsg" /></td>
								<td class="col-sm-12 col-md-2"><input
									class="datepicker_recurring_start" type="text"
									id="${status.index}"
									path="iwmpMProjectList[${status.index}].projectStartDt"
									name="iwmpMProjectList[${status.index}].projectStartDt"
									value="<fmt:formatDate value="${proj.projectStartDt}" pattern="MM/dd/yyyy" />" />
									<!-- 										<input class="datepicker_recurring_start" --> <%-- 											name="iwmpMProjectList[${status.index}].projectStartDt" --%>
									<%-- 											value="${proj.projectStartDt}" /> --%> <form:errors
										path="iwmpMProjectList[${status.index}].projectStartDt"
										cssclass="errormsg" /></td>

								<td><form:select class="custom-select-sm"
										id="iwmpMProjectList[${status.index}].iwmpMProjectPrd.prdCode"
										path="iwmpMProjectList[${status.index}].iwmpMProjectPrd.prdCode"
										value="${proj.iwmpMProjectPrd.prdCode}">
										<form:option value="0" label="---Select---"></form:option>
										<form:options items="${projectPrd}" itemLabel="prdDesc"
											itemValue="prdCode" />
									</form:select> <form:errors
										path="iwmpMProjectList[${status.index}].iwmpMProjectPrd.prdCode"
										cssclass="errormsg" /></td>
								<td class="col-sm-12 col-md-2"><input
									id="areaProposed[${status.index}]" class="form-control"
									name="iwmpMProjectList[${status.index}].areaProposed"
									value="${proj.areaProposed}"
									onchange="javascript:getValue(${status.index})" /> <form:errors
										path="iwmpMProjectList[${status.index}].areaProposed"
										cssclass="errormsg" /></td>
								<td><form:select class="custom-select-sm"
										id="iwmpMAreaType[${status.index}]"
										path="iwmpMProjectList[${status.index}].iwmpMAreaType.areaCd"
										value="${proj.iwmpMAreaType.areaCd}"
										onchange="javascript:getValue(${status.index})">
										<form:option value="0" label="---Select---"></form:option>
										<form:options items="${areaType}" itemLabel="areaDesc"
											itemValue="areaCd" />
									</form:select> <form:errors
										path="iwmpMProjectList[${status.index}].iwmpMAreaType.areaCd"
										cssclass="errormsg" /></td>
								<td class="col-sm-12 col-md-2"><input
									id="projectCost[${status.index}]" class="form-control"
									name="iwmpMProjectList[${status.index}].projectCost"
									value="${proj.projectCost}" /> <form:errors
										path="iwmpMProjectList[${status.index}].projectCost"
										cssclass="errormsg" /></td>
								<td><input id="centralShareAmt[${status.index}]"
									class="form-control"
									name="iwmpMProjectList[${status.index}].centralShareAmt"
									value="${proj.centralShareAmt}" /> <form:errors
										path="iwmpMProjectList[${status.index}].centralShareAmt"
										cssclass="errormsg" /></td>
								<td><input id="stateShareAmt[${status.index}]"
									class="form-control"
									name="iwmpMProjectList[${status.index}].stateShareAmt"
									value="${proj.stateShareAmt}" /> <form:errors
										path="iwmpMProjectList[${status.index}].stateShareAmt"
										class="errormsg" /></td>
								<td><input type="submit" value="Delete" class="square_btn"
									name="ondelete"
									onclick="javascript:deleterecord(${status.index})"></td>

							</tr>
						</c:forEach>
						<tr>
							<td colspan="12" align="left"><input type="submit"
								class="square_btn" name="add" value="Add" /> <input
								type="submit" name="action2" class="square_btn" value="Save"></td>
						</tr>
					</table>
				</c:if>
			</div>
		</form:form>
	</div>

	<footer class="">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>