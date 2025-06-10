<%@ include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>
    <title>Evaluation Form</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
let formSubmitted = false; 

function savedata(){
//   alert('dfd');
    if (formSubmitted) return false;  
//	var profile_id = $('#profileid').val();
	var fromno = $('#fromno').val();
	
    var naturalresource = $('input[name="ntrlresource"]:checked').val(); // natural resource
    var naturalresourceRemark = $('#ntrlresourceremark').val(); // natural resourceRemark
    var norm = $('input[name="norm"]:checked').val(); // norms relating
    var normRemark = $('#normremark').val(); // norms relating Remark
    var antrlasset = $('input[name="ntlasset"]:checked').val(); // aNatural asset
    var antrlassetRemark = $('#ntlassetremark').val(); // anatural asset Remark
    var controlntlresource = $('input[name="controlntlresource"]:checked').val(); //contrl natural resource
    var controlnorm = $('input[name="controlnorm"]:checked').val(); // control norms relating
    var controlantrlasset = $('input[name="controlantrlasset"]:checked').val(); // control anatural asset
	
// 	alert('dprSlnaRemark=='+dprSlnaRemark);natural resources intervals
    $flag=true;
    if (naturalresource === '' ||naturalresource === undefined || naturalresource === null) {
            alert('Please check the  status of natural resources at intervals for Project Area');
            $flag = false; 
            return false;
        }
    if (controlntlresource === '' ||controlntlresource === undefined || controlntlresource === null) {
        alert('Please check the  status of natural resources at intervals for Controlled Area');
        $flag = false; 
        return false;
    }
    
    if (controlntlresource === 'true') {
    	if (naturalresourceRemark === '' ||naturalresourceRemark === undefined || naturalresourceRemark === null) {
            alert('Please give the remarks Controlled Area');
            $('#ntrlresourceremark').focus(); 
            return false;
        }
    }
    
    if (norm === '' ||norm === undefined || norm === null) {
        alert('Please check norms relating to sharing of usufructs rights for Project Area');
        $flag = false; 
        return false;
    }
    if (controlnorm === '' ||controlnorm === undefined || controlnorm === null) {
        alert('Please check norms relating to sharing of usufructs rights for Controlled Area');
       $flag = false; 
        return false;
	}
    if (controlnorm === 'true') {
    	if (normRemark === '' ||normRemark === undefined || normRemark === null) {
            alert('Please give the remarks Controlled Area');
            $('#normremark').focus(); 
            return false;
        }
    }
    if (antrlasset === '' ||antrlasset === undefined || antrlasset === null) {
        alert('Please check natural resources and assets created for Project Area');
        $flag = false; 
        return false;
    }
    
   if (controlantrlasset === '' ||controlantrlasset === undefined || controlantrlasset === null) {
        alert('Please check natural resources and assets created for Controlled Area');
        $flag = false; 
        return false;
   }
   if (controlantrlasset === 'true') {
   	if (antrlassetRemark === '' ||antrlassetRemark === undefined || antrlassetRemark === null) {
           alert('Please give the remarks Controlled Area');
           $('#ntlassetremark').focus(); 
           return false;
       }
   }
    
    
    if(confirm("Do You Want to Save Ecological Perspective?")) {
        formSubmitted = true;    ////    saveEcoPerspective
        document.getElementById('eco').action = "saveEcoPerspectiveDetails";
        document.getElementById('eco').method = "post";
        document.getElementById('eco').submit();
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
				<span style="text-decoration:underline;">Mid Term Project Evaluation - Ecological Perspective</span>
			</h4>
		</div>
       	
        <hr />
        <form name="eco" id="eco" method="post" action="saveEcoPerspectiveDetails">
        <input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${distName}' />" />
		<input type="hidden" id="projName" name="projName" value= "<c:out value='${projName}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${month}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${finyear}' /> />
		<input type="hidden" name="fromno" id="fromno" value="9" />
           
<div class="form-group">
				State : &nbsp; <b><c:out value='${stName}' /></b>, &nbsp;&nbsp;&nbsp; District : &nbsp; <b><c:out value='${distName}' /></b>, &nbsp;&nbsp;&nbsp; Project : &nbsp; <b><c:out value='${projName}' /></b>, &nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${finyear}' /></b>, &nbsp;&nbsp;&nbsp; Month : &nbsp; <b><c:out value='${month}' /></b> 
		
			</div>

            <hr />
            <div>
                <table style="width:85%">
                    <tr>
                        <th ><b>Sl.No.</b></th>
                        <th style="width:35%"><b>Perspective Description</b></th>
                        <th ><b>Project Area</b></th>
                        <th ><b>Controlled Area</b></th>
                        <th><b>Remarks</b></th>
                    </tr>
                    <tr>
                    
                        <td><b><c:out value="1"/></b></td>
                        <td><b><c:out value="Is there a system of auditing of status of natural resources at intervals"/></b></td>
                        <td>
                            <c:if test="${ntrlresource eq null }">
                            <input type="radio" id="naturalyes" name="ntrlresource" value="true" value="${ntrlresource}" /> Yes
                            <input type="radio" id="naturalno" name="ntrlresource" value="false" value="${ntrlresource}"/> No
                            
                            </c:if>
                            <c:if test="${ntrlresource eq true }">
                            <input type="radio" id="naturalyes" name="ntrlresource" value="true"  checked="checked"  value="${ntrlresource}" /> Yes
                            <input type="radio" id="naturalno" name="ntrlresource" value="false" value="${ntrlresource}"/> No
                            
                            </c:if>
                            <c:if test="${ntrlresource eq false }">
                            <input type="radio" id="naturalyes" name="ntrlresource" value="true" value="${ntrlresource}" /> Yes
                            <input type="radio" id="naturalno" name="ntrlresource" value="false"  checked="checked"  value="${ntrlresource}"/> No
                            
                            </c:if>
                            </td>
                            <td>
                            <c:if test="${controlntlresource eq null }">
                            <input type="radio" id="naturalyesp" name="controlntlresource" value="true" value="${controlntlresource}" /> Yes
                            <input type="radio" id="naturalnop" name="controlntlresource" value="false" value="${controlntlresource}"/> No
                            </c:if>
                             <c:if test="${controlntlresource eq true }">
                            <input type="radio" id="naturalyesp" name="controlntlresource" value="true" checked="checked" value="${controlntlresource}" /> Yes
                            <input type="radio" id="naturalnop" name="controlntlresource" value="false" value="${controlntlresource}"/> No
                            </c:if>
                             <c:if test="${controlntlresource eq false }">
                            <input type="radio" id="naturalyesp" name="controlntlresource" value="true" value="${controlntlresource}" /> Yes
                            <input type="radio" id="naturalnop" name="controlntlresource" value="false" checked="checked" value="${controlntlresource}"/> No
                            </c:if>
                            </td>
                            <td>
                            <textarea id="ntrlresourceremark" name="ntrlresourceremark" autocomplete="off" rows="2" cols="22" maxlength="200">${ntrlresourceremark}</textarea>
                            </td>
                    </tr>
                    <tr>
                        <td><b><c:out value="2"/></b></td>
                        <td><b><c:out value="Whether Gram Panchayats (GPs) and UGs enforcing the norms relating to sharing of usufructs rights"/></b></td>
                        <td>
                        <c:if test="${norm eq null }">
                            <input type="radio" id="usufructsyes" name="norm" value="true" value="${norm}" /> Yes
                            <input type="radio" id="usufructsno" name="norm" value="false"   value="${norm}"/> No
                       </c:if>
                       <c:if test="${norm eq true }">
                            <input type="radio" id="usufructsyes" name="norm" value="true" checked="checked" value="${norm}" /> Yes
                            <input type="radio" id="usufructsno" name="norm" value="false"   value="${norm}"/> No
                       </c:if>
                       <c:if test="${norm eq false }">
                            <input type="radio" id="usufructsyes" name="norm" value="true" value="${norm}" /> Yes
                            <input type="radio" id="usufructsno" name="norm" value="false" checked="checked"  value="${norm}"/> No
                       </c:if>
                        </td>
                        <td>
                        <c:if test="${controlnorm eq null }">
                        <input type="radio" id="usufructsyesp" name="controlnorm" value="true" value="${controlnorm}" /> Yes
                            <input type="radio" id="usufructsnop" name="controlnorm" value="false"  value="${controlnorm}"/> No
                            </c:if>
                            <c:if test="${controlnorm eq true }">
                        <input type="radio" id="usufructsyesp" name="controlnorm" value="true" checked="checked" value="${controlnorm}" /> Yes
                            <input type="radio" id="usufructsnop" name="controlnorm" value="false"  value="${controlnorm}"/> No
                            </c:if>
                            <c:if test="${controlnorm eq false }">
                        <input type="radio" id="usufructsyesp" name="controlnorm" value="true" value="${controlnorm}" /> Yes
                            <input type="radio" id="usufructsnop" name="controlnorm" value="false" checked="checked" value="${controlnorm}"/> No
                            </c:if>
                            </td>
                        
                        <td>
                        <textarea id="normremark" name="normremark" autocomplete="off" rows="2" cols="22" maxlength="200">${normremark}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td><b><c:out value="3"/></b></td>
                        <td><b><c:out value="Whether all members of GPs and UGs trained to maintain and monitor all the natural resources and assets created"/></b></td>
                        <td>
                        <c:if test="${ntlasset eq null }">
                            <input type="radio" id="assetyes" name="ntlasset" value="true" value="${ntlasset}" /> Yes
                            <input type="radio" id="assetno" name="ntlasset" value="false" value="${ntlasset}" /> No
                       </c:if>
                       
                        <c:if test="${ntlasset eq true }">
                            <input type="radio" id="assetyes" name="ntlasset" value="true"  checked="checked" value="${ntlasset}" /> Yes
                            <input type="radio" id="assetno" name="ntlasset" value="false" value="${ntlasset}" /> No
                       </c:if>
                       
                        <c:if test="${ntlasset eq false }">
                            <input type="radio" id="assetyes" name="ntlasset" value="true" value="${ntlasset}" /> Yes
                            <input type="radio" id="assetno" name="ntlasset" value="false"  checked="checked" value="${ntlasset}" /> No
                       </c:if>
                        </td>
                        <td>
                        <c:if test="${controlantrlasset eq null }">
                        <input type="radio" id="assetyespa" name="controlantrlasset" value="true" value="${controlantrlasset}" /> Yes
                            <input type="radio" id="assetnopa" name="controlantrlasset" value="false" value="${controlantrlasset}"/> No
                            </c:if>
                            <c:if test="${controlantrlasset eq true }">
                        <input type="radio" id="assetyespa" name="controlantrlasset" value="true" checked="checked" value="${controlantrlasset}" /> Yes
                            <input type="radio" id="assetnopa" name="controlantrlasset" value="false" value="${controlantrlasset}"/> No
                            </c:if>
                            <c:if test="${controlantrlasset eq false }">
                        <input type="radio" id="assetyespa" name="controlantrlasset" value="true" value="${controlantrlasset}" /> Yes
                            <input type="radio" id="assetnopa" name="controlantrlasset" value="false" checked="checked" value="${controlantrlasset}"/> No
                            </c:if>
                            
                            </td>
                        <td>
                        <textarea id="ntlassetremark" name="ntlassetremark" autocomplete="off" rows="2" cols="22" maxlength="200">${ntlassetremark}</textarea>
                        </td>
                    </tr>
                    <tr>
					<th colspan="6" style="align-content: center;">
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
