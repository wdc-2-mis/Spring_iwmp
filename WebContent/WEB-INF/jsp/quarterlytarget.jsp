<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<script type="text/javascript">

function selectannualtarget(e){
	var project = $('#project').val();
	var financial = $('#financial').val();

	if (document.getElementById("project").value =='') 
	{
		alert("Please select Project");
		document.getElementById("project").focus();
		return;
	}
	
	if (document.getElementById("financial").value =='') 
	{
		alert("Please select Financial Year");
		document.getElementById("financial").focus();
		return;
	}
	
	document.getElementById('quarterlytarget').action ="quadtarget";
	document.getElementById('quarterlytarget').method="get";
	document.getElementById('quarterlytarget').submit();
	
}
function checkValue(temp) {
 	$quarter1 = $('#quarter1'+temp).val();
    $quarter2 = $('#quarter2'+temp).val();
 	$quarter3 = $('#quarter3'+temp).val();
 	$quarter4 = $('#quarter4'+temp).val();
 	$annual = $('#annual'+temp).val();
 	$final = parseFloat($annual);
    $tot = parseFloat($quarter1);
 	$total = parseFloat($quarter1) + parseFloat($quarter2);
 	$total1 = parseFloat($quarter1) + parseFloat($quarter2) + parseFloat($quarter3);
 	$total2 = parseFloat($quarter1) + parseFloat($quarter2) + parseFloat($quarter3) + parseFloat($quarter4);

   
 	if(temp > 10){
 	if($tot > $final && $final > 0){
    alert("Quarters value cann't be greater then Annual Target");
    document.getElementById("quarter1"+temp).value = ""; 
    }
 	
     if($total > $final && $final > 0 || $quarter2 > $final && $final > 0){
        alert("Quarters values cann't be greater then Annual Target");
        document.getElementById("quarter2"+temp).value = ""; 
     	}

     if($total1 > $final && $final > 0 || $quarter3 > $final && $final > 0){
         alert("Quarters values cann't be greater then Annual Target");
         document.getElementById("quarter3"+temp).value = ""; 
      	}
   	
     if($total2 > $final && $final > 0 || $quarter4 > $final && $final > 0){
         alert("Quarters values cann't be greater then Annual Target");
         document.getElementById("quarter4"+temp).value = ""; 
      	}
 	}
 	 if(temp < 10)
 	 	{
 		if($tot > $final){
 		    alert("Quarters value cann't be greater then Annual Target");
 		    document.getElementById("quarter1"+temp).value = ""; 
 		    }
 		 	
 		     if($total > $final || $quarter2 > $final){
 		        alert("Quarters values cann't be greater then Annual Target");
 		        document.getElementById("quarter2"+temp).value = ""; 
 		     	}

 		     if($total1 > $final|| $quarter3 > $final){
 		         alert("Quarters values cann't be greater then Annual Target");
 		         document.getElementById("quarter3"+temp).value = ""; 
 		      	}
 		   	
 		     if($total2 > $final|| $quarter4 > $final){
 		         alert("Quarters values cann't be greater then Annual Target");
 		         document.getElementById("quarter4"+temp).value = ""; 
 		      	}
 		 	}
}

function savequadDraftTarget(e){
	var project = $('#project').val();
	var financial = $('#financial').val();
	var res="";
    var status="D";
	document.getElementById("res").value=res;
    
	var fields = $('input.first');
	var secondfields = $('input.second');
	var thirdfields = $('input.third');
	var fourthfields = $('input.fourth');
	var x1 = false;
	var y1 = false;

	var x2 = false;
	var y2 = false;

	var x3 = false;
	var y3 = false;

	var x4 = false;
	var y4 = false;
	
	for(var i=0;i<fields.length;i++){
        if($(fields[i]).val()== ''){
            x1 = true;
       fields[i].val===''
 }
        if($(fields[i]).val()!= ''){
 y1= true;
        }
    } 
    var op1 = Boolean(x1);
    var op2 = Boolean(y1);
    
   
    if(op1 && op2){
    	alert("Please fill all 1st Quarters values");
        e.preventDefault();
     }

	for(var i=0;i<secondfields.length;i++){
        if($(secondfields[i]).val() == ''){
            x2 = true;
            secondfields[i].val==='';
        }
        if($(secondfields[i]).val()!= ''){
        	 y2= true;
        }
        }        

    var op3 = Boolean(x2);
    var op4 = Boolean(y2);
    if(op3 && op4){
            alert("Please fill all 2nd Quarters values");
            e.preventDefault();
        }


	for(var i=0;i<thirdfields.length;i++){
        if($(thirdfields[i]).val() == ''){
        	x3 = true;
        	thirdfields[i].val==='';
        }
        if($(thirdfields[i]).val()!= ''){
       	 y3= true;
       }
	}

	var op5 = Boolean(x3);
    var op6 = Boolean(y3);
    if(op5 && op6){
            alert("Please fill all 3rd Quarters values");
            e.preventDefault();
        }

    
	for(var i=0;i<fourthfields.length;i++){
        if($(fourthfields[i]).val() == ''){
        	x4 = true;
        	fourthfields[i].val==='';
        }
        if($(fourthfields[i]).val()!= ''){
          	 y4= true;
          }
	}
	var op7 = Boolean(x4);
    var op8 = Boolean(y4);
    if(op7 && op8){
            alert("Please fill all 4th Quarters values");
            e.preventDefault();
        }
   
	if(project==='')
	{
		alert('Please select project ');
		$('#project').focus();
		e.preventDefault();
	}
	if(financial==='')
	{
		alert('Please select Financial Year ');
		$('#financial').focus();
		e.preventDefault();
	}
	else{
		if(confirm("Do you want to save the Records ?"))
		{
			document.getElementById('quarterlytarget').action ="savequarterlytarget?status="+status;
		    document.getElementById('quarterlytarget').method="post";
		   document.getElementById('quarterlytarget').submit();
			
		}
		}
}
function savequadTarget(e){

	var project = $('#project').val();
	var financial = $('#financial').val();
	var res="";
    var status="S";
	document.getElementById("res").value=res;
    
	var fields = $('input.first');
	var secondfields = $('input.second');
	var thirdfields = $('input.third');
	var fourthfields = $('input.fourth');
	var x1 = false;
	var y1 = false;

	var x2 = false;
	var y2 = false;

	var x3 = false;
	var y3 = false;

	var x4 = false;
	var y4 = false;
	
	for(var i=0;i<fields.length;i++){
        if($(fields[i]).val()== ''){
            x1 = true;
       fields[i].val===''
 }
        if($(fields[i]).val()!= ''){
 y1= true;
        }
    } 
    var op1 = Boolean(x1);
    var op2 = Boolean(y1);
    
   
    if(op1 && op2){
    	alert("Please fill all 1st Quarters values");
        e.preventDefault();
     }

	for(var i=0;i<secondfields.length;i++){
        if($(secondfields[i]).val() == ''){
            x2 = true;
            secondfields[i].val==='';
        }
        if($(secondfields[i]).val()!= ''){
        	 y2= true;
        }
        }        

    var op3 = Boolean(x2);
    var op4 = Boolean(y2);
    if(op3 && op4){
            alert("Please fill all 2nd Quarters values");
            e.preventDefault();
        }


	for(var i=0;i<thirdfields.length;i++){
        if($(thirdfields[i]).val() == ''){
        	x3 = true;
        	thirdfields[i].val==='';
        }
        if($(thirdfields[i]).val()!= ''){
       	 y3= true;
       }
	}

	var op5 = Boolean(x3);
    var op6 = Boolean(y3);
    if(op5 && op6){
            alert("Please fill all 3rd Quarters values");
            e.preventDefault();
        }

    
	for(var i=0;i<fourthfields.length;i++){
        if($(fourthfields[i]).val() == ''){
        	x4 = true;
        	fourthfields[i].val==='';
        }
        if($(fourthfields[i]).val()!= ''){
          	 y4= true;
          }
	}
	var op7 = Boolean(x4);
    var op8 = Boolean(y4);
    if(op7 && op8){
            alert("Please fill all 4th Quarters values");
            e.preventDefault();
        }
   
	if(project==='')
	{
		alert('Please select project ');
		$('#project').focus();
		e.preventDefault();
	}
	if(financial==='')
	{
		alert('Please select Financial Year ');
		$('#financial').focus();
		e.preventDefault();
	}
	else{
		if(confirm("Do you want to forward the records to SLNA ?"))
		{
			document.getElementById('quarterlytarget').action ="savequarterlytarget?status="+status;
		    document.getElementById('quarterlytarget').method="post";
		   document.getElementById('quarterlytarget').submit();
			
		}
		}
}

</script>
</head>

<div class="maindiv">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script
		src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>

	<div class="col formheading" style="">
		<div class="col formheading" style="">
			<h4>
				<u>Add Quarterly Target</u>
			</h4>
		</div>

	</div>
	<c:if test="${message ne null}">
		<div style="text-align: center;">
			<p class="message badge badge-success">${message}</p>
		</div>
	</c:if>
	<hr />
	<form:form class="form-inline" name="quarterlytarget"
		id="quarterlytarget" modelAttribute="quarterlytarget"
		action="savequarterlytarget" method="post" autocomplete="off">
		<input type="hidden" name="res" id="res" value="" class="form-control" />

		<tr></tr>

	
            <div class="form-row">
			<label for="project" class="">Project:- </label> <select
				class="form-control col project" id="project" name="project">
				<option value="">--Select Project--</option>
				<c:if test="${not empty projectList}">
					<c:forEach items="${projectList}" var="lists">
						<c:if test="${lists.key eq project}">
							<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out
									value="${lists.value}" /></option>
						</c:if>
						<c:if test="${lists.key ne project}">
							<option value="<c:out value="${lists.key}"/>"><c:out
									value="${lists.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
			</select>
		</div>

		 <div class="form-group col-md-4">
			<label for="project" class="">Financial Year:- </label> <select
				class="form-control col project" id="financial" name="financial" required>
				<option value="">--Select Financial Year--</option>
				<c:if test="${not empty financialYear}">
					<c:forEach items="${financialYear}" var="fyear">
						<c:if test="${fyear.key eq financial}">
							<option value="<c:out value="${fyear.key}"/>" selected="selected"><c:out
									value="${fyear.value}" /></option>
						</c:if>
						<c:if test="${fyear.key ne financial}">
							<option value="<c:out value="${fyear.key}"/>"><c:out
									value="${fyear.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
			</select>
			
		</div> 
<input type="button" name="view"  id = "view" value="Get Details"
					class="btn btn-info" onclick="selectannualtarget();"/>
					
		<hr />
		<tr>
		</tr>
		<c:if test="${QuadTarget eq null}">
			<div>
				<table style="width: 100%">
					<thead>
						<tr>
							<th rowspan="2">S No.</th>
							<th rowspan="2">Indicator's Name</th>
							<th rowspan="2">1st Quarter</th>
							<th rowspan="2">2nd Quarter</th>
							<th rowspan="2">3rd Quarter</th>
							<th rowspan="2">4th Quarter</th>
							<th rowspan="2">Annual Target</th>
						</tr>
					</thead>
					<c:forEach var="list" items="${Target}" varStatus="status">
						<c:forEach var="listUser" items="${list.value}">


							<tr>
								<td align="center">${status.count}</td>
								<td><c:out value="${listUser.indicators_desc }">
									</c:out> <input type="hidden" class="dc" id="targetid"
									value="${listUser.indicators_id }">
								<td><input type="text" class="desc" id="targetdesc"
									maxlength="8" onfocusin="numericOnly(event)" name=""
									targetdesc="" required="required"></td>
								<td><input type="text" class="desc" id="targetdesc"
									maxlength="8" onfocusin="numericOnly(event)" name=""
									targetdesc="" required="required"></td>
								<td><input type="text" class="desc" id="targetdesc"
									maxlength="8" onfocusin="numericOnly(event)" name=""
									targetdesc="" required="required"></td>
								<td><input type="text" class="desc" id="targetdesc"
									maxlength="8" onfocusin="numericOnly(event)" name=""
									targetdesc="" required="required"></td>
								<%-- <c:if test="${!listUser.plan == null}">
					<td>
					<c:out	value="${listUser.plan }">
						  </c:out>
					</td>
					</c:if> --%>


							</tr>

						</c:forEach>
					</c:forEach>


					<td></td>
					<td></td>
					<!-- <td align="center" class="label"><input type="submit"
								class="btn btn-info form-control" id="quarterlytar" name="quarterlytar"
								value="Forward to SLNA"></td>   -->
				</table>
			</div>
		</c:if>

		<c:if test="${QuadTarget ne null}">
			<div>
				<table style="width: 100%">
					<thead>
						<tr>
							<th rowspan="2">S No.</th>
							<th rowspan="2">Indicator's Name</th>
							<th rowspan="2">1st Quarter</th>
							<th rowspan="2">2nd Quarter</th>
							<th rowspan="2">3rd Quarter</th>
							<th rowspan="2">4th Quarter</th>
							<th rowspan="2">Annual Target</th>
						</tr>
					</thead>
					<c:forEach var="list" items="${QuadTarget}" varStatus="status">
						<c:forEach var="listUser" items="${list.value}">


							<tr>
								<td align="center">${status.count}</td>
								<td><c:out value="${listUser.indicators_desc }" /> <input
									type="hidden" class="dc" id="targetid" name="indicatorid"
									value="${listUser.indicators_id }">
								<td><c:if
										test="${listUser.first_quad ne null && listUser.first_quad ne 0.00 && listUser.q1status eq 'D'.charAt(0)}">

										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="first"
												id="quarter1${listUser.indicators_id}" maxlength="8"
												value="${listUser.first_quad}"
												onblur="checkValue(${listUser.indicators_id})"
												onfocusin="numericOnly(event)" name="quarter1" targetdesc="" required>
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="first"
												id="quarter1${listUser.indicators_id}" maxlength="8"
												value="${listUser.first_quad}"
												onblur="checkValue(${listUser.indicators_id})"
												onfocusin="decimalCheck(event)" name="quarter1"
												targetdesc="">
										</c:if>
									</c:if> 
									
									<c:if test="${listUser.q1status eq 'C'.charAt(0)}">
										<input type="text" class="first" readonly
											id="quarter1${listUser.indicators_id}" maxlength="8"
											value="${listUser.first_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter1" targetdesc="">
									</c:if>
									
										<c:if test="${listUser.first_quad ne null && listUser.first_quad ne 0.00 && listUser.q1status eq 'S'.charAt(0)}">
										<input type="text" class="first" readonly
											id="quarter1${listUser.indicators_id}" maxlength="8"
											value="${listUser.first_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter1" targetdesc="">
									</c:if> <c:if
										test="${listUser.first_quad eq null  || listUser.q1status eq null}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="first"
												id="quarter1${listUser.indicators_id}" maxlength="8"
												onblur="checkValue(${listUser.indicators_id})"
												onfocusin="numericOnly(event)" name="quarter1" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="first"
												id="quarter1${listUser.indicators_id}" maxlength="8"
												onblur="checkValue(${listUser.indicators_id})"
												onfocusin="decimalCheck(event)" name="quarter1"
												targetdesc="">
										</c:if>
									</c:if> <input type="hidden" class="dc"
									id="annual${listUser.indicators_id}" value="${listUser.plan}">
									<input type="hidden" class="dc" name="quad1"
									value="${listUser.q1status}"></td>

								<td><c:if
										test="${listUser.second_quad ne null && listUser.second_quad ne 0.00 && listUser.q2status eq 'D'.charAt(0)}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="second"
												id="quarter2${listUser.indicators_id}" maxlength="8"
												value="${listUser.second_quad}"
												onblur="checkValue(${listUser.indicators_id})"
												onfocusin="numericOnly(event)" name="quarter2" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="second"
												id="quarter2${listUser.indicators_id}" maxlength="8"
												value="${listUser.second_quad}"
												onblur="checkValue(${listUser.indicators_id})"
												onfocusin="decimalCheck(event)" name="quarter2"
												targetdesc="">
										</c:if>
										
									</c:if> 
									<c:if
										test="${listUser.q2status eq 'C'.charAt(0)}">
										<input type="text" class="second" readonly
											id="quarter2${listUser.indicators_id}" maxlength="8"
											value="${listUser.second_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter2" targetdesc="">
									</c:if>
									
									<c:if
										test="${listUser.second_quad ne null && listUser.second_quad ne 0.00 && listUser.q2status eq 'S'.charAt(0)}">
										<input type="text" class="second" readonly
											id="quarter2${listUser.indicators_id}" maxlength="8"
											value="${listUser.second_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter2" targetdesc="">
									</c:if> <c:if
										test="${listUser.second_quad eq null || listUser.q2status eq null}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="second"
											id="quarter2${listUser.indicators_id}" maxlength="8"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter2" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="second"
											id="quarter2${listUser.indicators_id}" maxlength="8"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="decimalCheck(event)" name="quarter2" targetdesc="">
										</c:if>
										
									</c:if> <input type="hidden" class="dc"
									id="annual${listUser.indicators_id}" value="${listUser.plan}">
									<input type="hidden" class="dc" name="quad2"
									value="${listUser.q2status}"></td>

								<td><c:if
										test="${listUser.third_quad ne null && listUser.third_quad ne 0 && listUser.q3status eq 'D'.charAt(0)}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="third"
											id="quarter3${listUser.indicators_id}" maxlength="8"
											value="${listUser.third_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter3" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="third"
											id="quarter3${listUser.indicators_id}" maxlength="8"
											value="${listUser.third_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="decimalCheck(event)" name="quarter3" targetdesc="">
										</c:if>
									
									</c:if>
									
									<c:if
										test="${listUser.q3status eq 'C'.charAt(0)}">
										<input type="text" class="third" readonly
											id="quarter3${listUser.indicators_id}" maxlength="8"
											value="${listUser.third_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter3" targetdesc="">
									</c:if>
									
									 <c:if
										test="${listUser.third_quad ne null && listUser.third_quad ne 0.00 && listUser.q3status eq 'S'.charAt(0)}">
										<input type="text" class="third" readonly
											id="quarter3${listUser.indicators_id}" maxlength="8"
											value="${listUser.third_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter3" targetdesc="">
									</c:if> <c:if
										test="${listUser.third_quad eq null || listUser.q3status eq null}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="third"
											id="quarter3${listUser.indicators_id}" maxlength="8"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter3" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="third"
											id="quarter3${listUser.indicators_id}" maxlength="8"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="decimalCheck(event)" name="quarter3" targetdesc="">
										</c:if>
										
									</c:if> <input type="hidden" class="dc"
									id="annual${listUser.indicators_id}" value="${listUser.plan}">
									<input type="hidden" class="dc" name="quad3"
									value="${listUser.q3status}"></td>

								<td><c:if
										test="${listUser.fourth_quad ne null && listUser.fourth_quad ne 0.00 && listUser.q4status eq 'D'.charAt(0)}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="fourth"
											id="quarter4${listUser.indicators_id}" maxlength="8"
											value="${listUser.fourth_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter4" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="fourth"
											id="quarter4${listUser.indicators_id}" maxlength="8"
											value="${listUser.fourth_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="decimalCheck(event)" name="quarter4" targetdesc="">
										</c:if>
										
										
									</c:if> 
									
									<c:if
										test="${listUser.q4status eq 'C'.charAt(0)}">
										<input type="text" class="fourth" readonly
											id="quarter4${listUser.indicators_id}" maxlength="8"
											value="${listUser.fourth_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter4" targetdesc="">
									</c:if>
									
									<c:if
										test="${listUser.fourth_quad ne null && listUser.fourth_quad ne 0 && listUser.q4status eq 'S'.charAt(0)}">
										<input type="text" class="fourth" readonly
											id="quarter4${listUser.indicators_id}" maxlength="8"
											value="${listUser.fourth_quad}"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter4" targetdesc="">
									</c:if> <c:if
										test="${listUser.fourth_quad eq null || listUser.q4status eq null}">
										<c:if test="${listUser.area_type eq 'N'.charAt(0)}">
											<input type="text" class="fourth"
											id="quarter4${listUser.indicators_id}" maxlength="8"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="numericOnly(event)" name="quarter4" targetdesc="">
										</c:if>
										<c:if test="${listUser.area_type eq 'H'.charAt(0)}">
											<input type="text" class="fourth"
											id="quarter4${listUser.indicators_id}" maxlength="8"
											onblur="checkValue(${listUser.indicators_id})"
											onfocusin="decimalCheck(event)" name="quarter4" targetdesc="">
										</c:if>
										
									</c:if> <input type="hidden" class="dc"
									id="annual${listUser.indicators_id}" value="${listUser.plan}">
									<input type="hidden" class="dc" name="quad4"
									value="${listUser.q4status}"></td>
								<td><c:out value="${listUser.plan }">

									</c:out></td>
							</tr>

						</c:forEach>
					</c:forEach>


					<td></td>
					<td></td>



				</table>
			</div>
			<div class="left">
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp

<c:if test="${checkstatus != 0}">
    <input type="button" name="save" id="quarterlyDraft" value="Draft"
           class="btn btn-info" onclick="savequadDraftTarget();" disabled/>
    <input type="button" name="save" id="quarterlyForward" value="Forward to SLNA"
           class="btn btn-info" onclick="savequadTarget();" disabled />
</c:if>
<c:if test="${checkstatus == 0}">    
    <input type="button" name="save" id="quarterlyDraft" value="Draft"
           class="btn btn-info" onclick="savequadDraftTarget();" />
    <input type="button" name="save" id="quarterlyForward" value="Forward to SLNA"
           class="btn btn-info" onclick="savequadTarget();" />
</c:if>

			
			</div>
		</c:if>




	</form:form>
	<c:if test="${indi ne null}">
		<div>
			<table style="width: 100%">
				<thead>
					<tr>
						<span style='color: red'>D*=Draft, S*=Forward to SLNA,
							C*=Completed</span>
					</tr>
					<tr>
						<th rowspan="2">S No.</th>
						<th rowspan="2">Project</th>
						<th rowspan="2">Fin Year</th>
						<th rowspan="2">Indicator's Name</th>
						<th rowspan="2">1st Quarter</th>
						<th rowspan="2">1st Quarter Status</th>
						<th rowspan="2">2nd Quarter</th>
						<th rowspan="2">2nd Quarter Status</th>
						<th rowspan="2">3rd Quarter</th>
						<th rowspan="2">3rd Quarter Status</th>
						<th rowspan="2">4th Quarter</th>
						<th rowspan="2">4th Quarter Status</th>
					</tr>
				</thead>


				<c:forEach var="data" items="${indi}" varStatus="status">
					<%--  <c:forEach var="dataUser" items="${data.value}" > --%>
					<tr>
						<td align="center">${status.count}</td>
						<td><c:out value="${data.proj_name }">
							</c:out></td>
						<td><c:out value="${data.fin_yr_desc }">
							</c:out></td>
						<td><c:out value="${data.indicators_desc }">
							</c:out></td>
						<td><c:out value="${data.first_quad }">
							</c:out></td>
						<td><c:out value="${data.q1status }">
							</c:out></td>

						<td><c:out value="${data.second_quad }">
							</c:out></td>
						<td><c:out value="${data.q2status }">
							</c:out></td>
						<td><c:out value="${data.third_quad }">
							</c:out></td>
						<td><c:out value="${data.q3status }">
							</c:out></td>
						<td><c:out value="${data.fourth_quad }">
							</c:out></td>
						<td><c:out value="${data.q4status }">
							</c:out></td>
					</tr>
				</c:forEach>
				<%-- </c:forEach> 	 --%>
			</table>
		</div>
	</c:if>

</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/quarterlytargetassetId.js" />'></script>
</body>
</html>
