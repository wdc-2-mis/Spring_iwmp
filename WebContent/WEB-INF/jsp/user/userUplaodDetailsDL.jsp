<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/userUploadPractices.js" />'></script>
<script type="text/javascript">

$( document ).ready(function(){
	
	$(document).on( 'change', '#category_type', function (e) {
		
//	alert(''+$('#category_type option:selected').val()); 
	
		if($('#category_type option:selected').val()==='10')
		{
			$('.linkpath').removeClass('d-none');
			$('.fileup').addClass('d-none');
		}
		else{
			$('.fileup').removeClass('d-none');
			$('.linkpath').addClass('d-none');
		}
		 if($('#category_type option:selected').val()==='9')
		{
			$('.filletyps').removeClass('d-none');
			$('.filletyp').addClass('d-none');
		}
		else{
			$('.filletyp').removeClass('d-none');
			$('.filletyps').addClass('d-none');
		} 
	
	
	
	});


});


function validation() 
{
	var allowedFiles = [ ".docx",".doc", ".pdf", ".ppt", ".pptx", ".jpg", ".jpeg",".png",".xlsx","xls"];
	var fileName = document.getElementById("theFile");
	<c:if test="${not empty fileName}">
	const str = fileName.value;
	let ext = str.match(/\.(.+)$/)[1];
	 if (ext.includes(".") || !allowedFiles.includes("."+ext)) {
         alert("Please upload files having extensions " + allowedFiles.join(', ') + " only.");
         return;
     }
	</c:if>
	
	var e = document.getElementById("category_type");
    var category = e.options[e.selectedIndex].text;
	document.getElementById("upload_category").value=category;
	
	if (document.getElementById("subject").value =='') 
	{
		alert("Please write subject");
		document.getElementById("subject").focus();
		return;
	}
	
	if (document.getElementById("category_type").value =='') 
	{
		alert("Please select Category");
		document.getElementById("category_type").focus();
		return;
	}
	document.useruploaddl.action="saveUserUploadDetailDL";
	document.useruploaddl.method="post";
	document.useruploaddl.submit();
	
}

function updateUploadDetail(index, FileName, sub, captioncheckstatus, publishcheckstatus, cat, id) 
{
	var buttVal = document.getElementById("buttE" + index).value;
	var buttVal1 = document.getElementById("txtFld" + index).value;
	var changecat = document.getElementById("cat" + index).value;
	if (buttVal == 'Edit') 
	{
		document.getElementById("buttE" + index).value = 'Update';
		document.getElementById("categoryld" + index).style.display = '';
		document.getElementById("catld" + index).style.display = 'none';
		document.getElementById("cancel" + index).style.display = '';
	} 
	else if (confirm("It will update the Data.\n\n Do you want to continue ?") == true) 
	{
		document.getElementById("buttE" + index).value = 'Edit';
		document.getElementById("catld" + index).style.display = '';
		document.getElementById("categoryld" + index).style.display = 'none';
		document.getElementById("cancel" + index).style.display = 'none';
		var cap = document.getElementById("captioncheckstatusid" + index).checked;
		var pub = document.getElementById("publishcheckstatusid" + index).checked;
		var cat = document.getElementById("cat" + index).value;
		var newsub=document.getElementById("newSub"+index).value;
		var e = document.getElementById("cat" + index);
	    var cattxt = e.options[e.selectedIndex].text;
	    
		document.getElementById("filename").value=FileName;
		document.getElementById("id").value=id;
		document.getElementById("newsub").value=newsub;
		document.getElementById("newcat_id").value=cat;
		document.getElementById("caption_id").value=cap;
		document.getElementById("publish_id").value=pub;
		document.getElementById("newcattxt").value=cattxt;
		
		if (document.getElementById("newSub" + index).value == '') 
		{
			alert("Please write subject");
			document.getElementById("newSub" + index).focus();
		}
		document.useruploaddl.action="updateUserUploadDetailDL";
		document.useruploaddl.method="post";
		document.useruploaddl.submit();
	}
}
function cancel(index) 
{
	document.getElementById("categoryld" + index).style.display = 'none';
	document.getElementById("buttE" + index).value = 'Edit';
	document.getElementById("cancel" + index).style.display = 'none';
	document.getElementById("catld" + index).style.display = '';
}
function do_Delete(index, FileName, subject, id) 
{
	document.getElementById("filename").value=FileName;
	document.getElementById("id").value=id;
	var buttVal = document.getElementById("butt"+index).value;
	if (confirm("It will delete the Data.\n\n Do you want to continue ?") == true) 
	{
		if (confirm("It will delete uploaded file also. If you want to continue,Press OK button") == true) 
		{
			document.getElementById("butt" + index).value = 'Delete';
			document.useruploaddl.action="deleteUserUploadDetailDL";
			document.useruploaddl.method="post";
			document.useruploaddl.submit();
		}
	}
}

</script>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>Uploading	of Circular/Messages on Home Page</h5></div>
 
<!-- 	
	<h5>Upload file</h5>
 <form method="post" action="uploadFilesub"
				enctype="multipart/form-data" id="fileUploadForm">
				<div class="pull-right">
					<label for="upload" class="fileUploadInput" id="fileNameLabel">
						Browse model to upload </label> <input id="upload" name="upload"
						type="file" style="display: none;"
						accept=".pdf"
						onclick="upload.value = null" ;/> &nbsp;&nbsp;
					<button type="submit" id="submitFile" 
						class="btn btn-danger btn-md">
						<b><span class="glyphicon glyphicon-upload"></span> Upload</b>
					</button>
				</div>
			</form>
  -->
  <c:if test="${messageUpload != null}">
	<script>
	    alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
 <form:form autocomplete="off" name="useruploaddl" id="useruploaddl" action="saveUserUploadDetailDL" modelAttribute="useruploadsl" enctype="multipart/form-data">
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
          	<td ><b>Subject<span style="color: red;">*</span></b></td>
	        <td> 
	          	  <textarea rows="6" cols="50" name="subject" id="subject"></textarea>
	        </td>
        </tr>
        <tr> 
          	<td colspan="3" class="fileup"><b>Select a file to upload:</b>&nbsp;&nbsp; <input type="file" name="theFile" id="theFile" size="40"></input>
          	&nbsp;&nbsp;  <span class="filletyp" style="color: red;">(*Note:File type Only .doc,.docx,.ppt,.pptx,.jpg,.jpeg,.png,.pdf,.xls,.xlsx supported) </span>
          	<span class="d-none filletyps" style="color: red;">(*Note:File type Only .pdf supported) </span>
          	</td>
          	
          	<td colspan="3" class="d-none linkpath"><b>Video file path: <span style="color: red;">*</span></b>&nbsp;&nbsp;
          	<textarea rows="1" cols="100" name="videopath" id="videopath"></textarea> </td>
          	
        </tr>
        <tr>
        	<td width="8%"><b>Category<span style="color: red;">*</span></b></td>
			<td width="12%">
				<select name="category_type" id="category_type" size="1" >
					<!-- <option value="">--Select--</option> -->
					<c:if test="${not empty categoryType}">
               			<c:forEach items="${categoryType}" var="lists">
               				<c:if test="${lists.key eq category}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne category}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
				</select>
			 </td>
			 
			 <td><b>Publish</b>&nbsp;&nbsp; <input type="checkbox" name="Publish" id="Publish"></input> &nbsp;&nbsp; &nbsp;&nbsp;
			 <b>New Caption</b>&nbsp;&nbsp;<input type="checkbox" name="New_File" id="New_File"></input>&nbsp;&nbsp; &nbsp;&nbsp;
			 
<b>Language</b>
 <select name="language" id="language" class="" style="height:100%;">
					      <option value="">--Select Language--</option>
					       <option value="en">English</option>
					      <option value="hi">Hindi</option>
					    </select>
					  
 </td>
        </tr>
        <tr>
          <td colspan="4" align="center"> <input type="button" class="btn btn-info" id="view" onclick="validation();" name="view" value='Submit' /> </td>
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
		              	    <th  align="center">Sr.No.</th>
		              	    <th  align="center">State Name</th>
                            <th  align="center">Category</th>
                            <th  align="center">Subject</th>
          					<th  align="center">File Name</th>
                            <th  align="center">New Caption</th>
                            <th  align="center">Publish</th>
                            <th  align="center">File Delete</th>
                            <th  align="center">Delete</th>
                            <th  align="center">Update</th>
               		</tr>
               		
            <c:if test="${dataListsize>0}">
				<c:forEach items="${dataList}" var="dataV" varStatus="count">
					<tr id="buttNullRow${count.index}">
						<td width="2%" align="center"><c:out	value='${dataV[0]}' /> </td>
						<td width="2%" align="center"><c:out	value='${dataV[10]}' /> </td>
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

						<td  align="center" id="txtFld${count.index}">
							<textarea rows="5" cols="30" name="newSub${count.index}"  id="newSub${count.index}"><c:out value ='${dataV[7]}'></c:out></textarea>
						</td>
						<td class="altrow" width="10%" align="left"><c:out value='${dataV[3]}' /></td>
						
						<c:if test="${dataV[4] == false }">
						<td  align="left">
							<input type="checkbox" id="captioncheckstatusid${count.index}" value="f" name="captioncheckstatus" align="middle" />
							<label>New Caption</label>
						</td>
						</c:if>
						<c:if test="${dataV[4] == 'true'}">
						<td  align="left">
							<input type="checkbox" id="captioncheckstatusid${count.index}" value="t" name="captioncheckstatus" checked="checked" align="middle" />
							<label>New Caption</label>
						</td>
						</c:if>
						<c:if test="${dataV[6] == 'false' }">
						<td  align="left">
							<input type="checkbox" id="publishcheckstatusid${count.index}" name="publishcheckstatus" value="f" align="middle" />
							<label>Publish</label>
						</td>
						</c:if>
						<c:if test="${dataV[6] == 'true'}">
						<td  align="left">
							<input type="checkbox" id="publishcheckstatusid${count.index}" name="publishcheckstatus" checked="checked" value="t" align="middle" />
							<label>Publish</label>
						</td>
						</c:if>
						<c:if test="${dataV[3] == null }">
							<td  align="center">
								<input type="checkbox"  value="delete" name="delete" align="middle" />
								<label>File Delete</label>
							</td>
						</c:if>
						<c:if test="${dataV[3] != null }">
							<td  align="center">
								<input type="checkbox" disabled="disabled" checked="checked" value="delete1" name="delete1" align="middle" />
								<label>File Delete</label>
							</td>
						</c:if>
						<td align="center">
							<input type="button" id="butt${count.index}" value="Delete" name="delete" align="middle" 
								onclick="javascript:do_Delete('${count.index}','${dataV[3]}','','${dataV[8]}');" />
						</td>
						<td  align="center">
							<input type="button" id="buttE${count.index}" value="Edit"  name="edit" align="middle"
								onclick="javascript:updateUploadDetail('${count.index}' ,'${dataV[3]}','','captioncheckstatus','publishcheckstatus','${dataV[1]}','${dataV[8]}');" />
							 <span><input type="button" id="cancel${count.index}" value="Cancel" style="display: none;" onclick="javascript:cancel('${count.index}');" /> </span>
						</td>
					</tr>
				</c:forEach>
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

  <c:if test="${userList.size()<=0 }">
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