<%@ include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<html>

<head>
    <title>Evaluation Form</title>
     
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%--     <script src='<c:url value="/resources/js/projectevaluation/IndicatorEvaluation.js"/>'></script> --%>


<script type="text/javascript">
let formSubmitted = false;  
function savedata(){
  
    if (formSubmitted) return false;  
//	var profile_id = $('#profileid').val();
	var fromno = $('#fromno').val();
    var admiMechanism = $('#am').val(); // Administrative Mechanism
    var admiMechanismRemark = $('#amd').val(); // Administrative Mechanism Remark
    var dprSlna = $('input[name="dpr"]:checked').val(); // DPR approval status
    var dprSlnaRemark = $('#dprremark').val(); // DPR Remark
    var allManpower = $('input[name="mp"]:checked').val(); // Manpower status
    var allManpowerRemark = $('#mpremark').val(); // Manpower Remark
    var wcdc = $('#wdc').val(); // WCDC Level
    var wcdcRemark = $('#wdcd').val(); // WCDC Remark
    var pia = $('#pi').val(); // PIA Level
    var piaRemark = $('#pid').val(); // PIA Remark
    var wc = $('#wc').val(); // Watershed Committee Level
    var wcRemark = $('#wcd').val(); // Watershed Committee Remark
	
// 	alert('dprSlnaRemark=='+dprSlnaRemark);
    
	$flag=true;
	if(wcdc==='')
		wcdc=0;
	
	if(pia==='')
		pia=0;
		
	if(wc==='')
		wc=0;

	if (allManpower === 'F') {
    if (wcdc === '' || wcdc === undefined || wcdc === null || wcdc===0) {
        alert('Please fill WCDC Level Details');
        $('#wdc').focus();
        $flag = false; 
        return false;
    }
	if (pia === '' || pia === undefined || pia === null || pia===0) {
	       alert('Please fill PIA Level Details');
	       $('#pi').focus();
	       $flag = false; 
	       return false;
	   }
	   
	   if (wc === '' || wc === undefined || wc === null || wc===0) {
	          alert('Please fill Watershed Committee Level Details');
	          $('#wc').focus();
	          $flag = false; 
	          return false;
	      }
	
	}

		   if(admiMechanism=='' || admiMechanism == undefined || admiMechanism == null)
		   {
		   	alert('Please fill Administrative Mechanism Details')
		   	$('#am').focus();
		   	$flag=false;
		   	return false;
		   }
		   
		   if(dprSlna=='' || dprSlna == undefined || dprSlna == null)
		   {
		   	alert('Please Check Whether DPR approved by SLNA')
// 		   	$('#dpr').focus();
		   	$flag=false;
		   	return false;
		   }
		   if(allManpower=='' || allManpower == undefined || allManpower == null)
		   {
		   	alert('Please Check Whether all manpower positions in place')
		   	$('#am').focus();
		   	$flag=false;
		   	return false;
		   }
	
	
    if(confirm("Do You Want to Save Indicators For Evaluation?")) {
        formSubmitted = true;    ////    saveprojectProfile
        document.getElementById('evaluation').action = "saveIndicatorEvaluationDetails";
        document.getElementById('evaluation').method = "post";
        document.getElementById('evaluation').submit();
    }

    return false;
}


</script>

</head>


<body>
    <div class="maindiv">
        <div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
			<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${distName}"/>&projName=<c:out value="${projName}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${month}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${finyear}"/>" style="position: absolute; left: 0;">
    			<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
			</a>
			<h4 style="margin: 0;">
				<span style="text-decoration:underline;">Mid Term Project Evaluation - Indicators for Evaluation</span>
			</h4>
		</div>
        <hr />
        <form name="evaluation" id="evaluation" method="post" action="saveIndicatorEvaluationDetails">
        <input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${distName}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${projName}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${month}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${finyear}' /> />
		<input type="hidden" name="fromno" id="fromno" value="2" />
           
<div class="form-group">
			District Name : &nbsp; <b><c:out value='${distName}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${projName}' /></b>, &nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${finyear}' /></b>, &nbsp;&nbsp;&nbsp; Month Name : &nbsp;<b> <c:out value='${month}' /></b>
		
			</div>

            <hr />
            <div>
                <table style="width:90%">
                    <tr>
                        <th><b>Sl.No.</b></th>
                        <th><b>Indicators Description</b></th>
                        <th><b>Details</b></th>
                        <th><b>Remarks</b></th>
                    </tr>
                    <tr>
                        <td><b><c:out value="1."/></b></td>
                        <td><b><c:out value="Administrative Mechanism"/></b></td>
                        <td><input type="text" id="am" name="am" value="${am}" autocomplete="off" /></td>
                        <td>
                        <textarea id="amd" name="amd" autocomplete="off" rows="2" cols="22" maxlength="200">${amd}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-right"><b><c:out value="a"/></b></td>
                        <td><b><c:out value="Whether DPR approved by SLNA"/></b></td>
                        <td>
                        
                        <c:if test="${dpr eq null}">
                            <input type="radio" id="dpryes" name="dpr" value="F" value="${dpr}" /> Fully
                            <input type="radio" id="dprno" name="dpr" value="P"  value="${dpr}"/> Partially
                            
                           </c:if>
                           <c:if test="${dpr == 'P'.charAt(0)}">
                            <input type="radio" id="dpryes" name="dpr" value="F" value="${dpr}" /> Fully
                            <input type="radio" id="dprno" name="dpr" value="P" checked="checked"  value="${dpr}"/> Partially
                           </c:if>
                            <c:if test="${dpr == 'F'.charAt(0)}">
                            <input type="radio" id="dpryes" name="dpr" value="F" checked="checked" value="${dpr}" /> Fully
                            <input type="radio" id="dprno" name="dpr" value="P" value="${dpr}"/> Partially
                            
                           </c:if>
                           
                        </td>
                       <td> 
                       <textarea id="dprremark" name="dprremark" autocomplete="off" rows="2" cols="22" maxlength="200">${dprremark}</textarea>
                       </td>
                    </tr>
                    <tr>
                        <td class="text-right"><b><c:out value="b"/></b></td>
                        <td><b><c:out value="Whether all manpower positions in place at"/></b></td>
                        <td>
                        <c:if test="${mp eq null }">
                            <input type="radio" id="mpyes" name="mp" value="F" value="${mp}" /> Fully
                            <input type="radio" id="mpno" name="mp" value="P" value="${mp}" /> Partially
                            </c:if>
                             <c:if test="${mp == 'F'.charAt(0)}"> 
                            <input type="radio" id="mpyes" name="mp" value="F" checked="checked" value="${mp}" /> Fully
                            <input type="radio" id="mpno" name="mp" value="P" value="${mp}" /> Partially
                            </c:if>
                            <c:if test="${mp == 'P'.charAt(0)}">
                            <input type="radio" id="mpyes" name="mp" value="F"  value="${mp}" /> Fully
                            <input type="radio" id="mpno" name="mp" value="P" checked="checked" value="${mp}" /> Partially
                            </c:if>
                            
                        </td>
                        <td>
                        <textarea id="mpremark" name="mpremark" autocomplete="off" rows="2" cols="22" maxlength="200">${mpremark}</textarea>
                        </td>
                    </tr>
                    <tr id="wdcRow">
                        <td class="text-center"><b><c:out value="I"/></b></td>
                        <td><b><c:out value="WCDC Level"/></b></td>
                        <td><input type="text" id="wdc" name="wdc" value="${wdc}" onmousedown="numericOnly(event);" maxlength="5" placeholder="Only numeric" autocomplete = "off"/></td>
                        <td>
                         <textarea id="wdcd" name="wdcd" autocomplete="off" rows="2" cols="22" maxlength="200">${wdcd}</textarea>
                        </td>
                    </tr>
                    <tr id="piRow">
                        <td class="text-center"><b><c:out value="II"/></b></td>
                        <td><b><c:out value="PIA Level"/></b></td>
                        <td><input type="text" id="pi" name="pi" value="${pi}" onmousedown="numericOnly(event);" maxlength="5" placeholder="Only numeric" autocomplete = "off" /></td>
                        <td>
                        <textarea id="pid" name="pid" autocomplete="off" rows="2" cols="22" maxlength="200">${pid}</textarea>
                        </td>
                    </tr>
                    <tr id ="wcRow">
                        <td class="text-center"><b><c:out value="III"/></b></td>
                        <td><b><c:out value="Watershed Committee Level"/></b></td>
                        <td><input type="text" id="wc" name="wc" value="${wc}"onmousedown="numericOnly(event);" maxlength="5" placeholder="Only numeric" autocomplete = "off" /></td>
                        <td>
                        <textarea id="wcd" name="wcd" autocomplete="off" rows="2" cols="22" maxlength="200">${wcd}</textarea>
                        </td>
                    </tr>
                    <tr>
					<th colspan="5" style="align-content: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" name="view"  id = "view" value="Confirm" class="btn btn-info" onclick="savedata();"/>

					</th>
				</tr>
                </table>
            </div>
        </form>

        <footer class="text-center">
            <%@ include file="/WEB-INF/jspf/footer2.jspf"%>
        </footer>
    </div>
</body>
</html>