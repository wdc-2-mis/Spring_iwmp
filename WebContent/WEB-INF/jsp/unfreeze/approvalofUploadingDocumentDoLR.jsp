<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/userUploadPractices.js" />'></script>
<script type="text/javascript">


function validation() 
{
	if (document.getElementById("state").value =='') 
	{
		alert("Please select state");
		document.getElementById("state").focus();
		return;
	}
	document.approvalUploadingDoLR.action="ApprovalUploadingDocumentDoLR";
	document.approvalUploadingDoLR.method="get";
	document.approvalUploadingDoLR.submit();
	
}

function selects(ele) {
    var checkboxes = document.getElementsByName('upid');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

function approve() 
{ 
	if (confirm("Do you want to Approve ?") == true) 
	{
			document.approvalUploadingDoLR.action="DoLRApprovalUploadingDocument";
			document.approvalUploadingDoLR.method="post";
			document.approvalUploadingDoLR.submit();
	}
}

</script>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>Approval of Uploading Documents/Success Stories/Best Practices</h5></div>
 
<c:if test="${messageUpload != null}">
	<script>
	    alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
  
 <form:form autocomplete="off" name="approvalUploadingDoLR" id="approvalUploadingDoLR" action="ApprovalUploadingDoLR" modelAttribute="approvalDoLR" >
 		<input type="hidden" name="upload_category" id="upload_category" value="" />
 		<input type="hidden" name="id" id="id" value="" />
 		<input type="hidden" name="filename" id="filename" value="" />
 		<input type="hidden" name="newsub" id="newsub" value="" />
 		<input type="hidden" name="newcat_id" id="newcat_id" value="" />
 		<input type="hidden" name="caption_id" id="caption_id" value="" />
 		<input type="hidden" name="publish_id" id="publish_id" value="" />
 		<input type="hidden" name="newcattxt" id="newcattxt" value="" />
      <table>
        <tr>
          	<td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state"  required="required">
              		<option value="0">--All State--</option>
              		
                  	<c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
       
          <td> <input type="button" class="btn btn-info" id="view" onclick="validation();" name="view" value='Get Data' /> </td>
         <!--  <td> <input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td> -->
       </tr>
      </table>
 

	<div class="table-responsive">
		<div class="col" style="text-align:center;"><h5>List of Uploaded Data</h5></div>
	</div>
        <table class="table">
          <tr>
            <td>
            	<table id="example" class="table">
              		<tr>
		              	    <th  align="center">Sr.No.&nbsp;<input type="checkbox"   onchange="selects(this);"/> </th>
		              	    <th  align="center">State Name</th>
                            <th  align="center">Category</th>
                            <th  align="center">Subject</th>
          					<th  align="center">File Name</th>
                            <th  align="center">New Caption</th>
                            <th  align="center">Publish</th>
               		</tr>
               		
            <c:if test="${dataListsize>0}">
				<c:forEach items="${dataList}" var="dataV" varStatus="count">
					<tr id="buttNullRow${count.index}">
						<td width="2%" align="center"><c:out value='${dataV[0]}' /> <input type="checkbox" id="upid" name="upid" value='${dataV[12]}' > </td>
						<td width="2%" align="center"><c:out value='${dataV[10]}' /> </td>
						<td width="5%" align="left"	id="catld${count.index}"><c:out value='${dataV[1]}' /></td>
						
						<td  align="left" id="categoryld${count.index}" style="display: none;">
						<select name="newcategory_type" id="cat${count.index}" size="1">
							<c:forEach items="${categoryType}" var="lists">
								<c:if test="${dataV[9] eq lists.key}">
									<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value='${lists.value}' /></option>
								</c:if>
								<c:if test="${dataV[9] ne lists.key}">
									<option value="<c:out value='${lists.key}'/>"	><c:out value='${lists.value}' /></option>
								</c:if>
							</c:forEach>
						</select>
						</td>

						<td  align="left" >
							<c:out value ='${dataV[7]}'></c:out>
						</td>
						<td class="altrow" width="10%" align="left"><a target="_blank"  href="<c:url value='${dataV[5]}'/>" ><c:out value='${dataV[3]}' /></a></td>
						
						
						
						<c:if test="${dataV[4] == false }">
						<td  align="left">
							<input type="checkbox" id="captioncheckstatusid${count.index}" value="f" name="captioncheckstatus" align="middle" disabled="disabled"/>
							<label>New Caption</label>
						</td>
						</c:if>
						<c:if test="${dataV[4] == 'true'}">
						<td  align="left">
							<input type="checkbox" id="captioncheckstatusid${count.index}" value="t" name="captioncheckstatus" checked="checked" align="middle" disabled="disabled"/>
							<label>New Caption</label>
						</td>
						</c:if>
						<c:if test="${dataV[6] == 'false' }">
						<td  align="left">
							<input type="checkbox" id="publishcheckstatusid${count.index}" name="publishcheckstatus" value="f" align="middle" disabled="disabled"/>
							<label>Publish</label>
						</td>
						</c:if>
						<c:if test="${dataV[6] == 'true'}">
						<td  align="left">
							<input type="checkbox" id="publishcheckstatusid${count.index}" name="publishcheckstatus" checked="checked" value="t" align="middle" disabled="disabled"/>
							<label>Publish</label>
						</td>
						</c:if>
						
					</tr>
				</c:forEach>
				<tr>
				<td> <input type="button" class="btn btn-info" id="view" onclick="approve();"  name="view" value='Approve' /> </td>
				
				</tr>
			</c:if>

              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
   
	</form:form>
</div>   

  <c:if test="${dataListsize<=0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
</div>

<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

</script>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>