<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<html>
<head>
<title>Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee</title>

<script type="text/javascript">

function getprojLocDtlRpt(){
	var state = $('#state').val();
	var dist = $('#district').val();
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	if(dist==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	else{
		
		document.projLocDtlRpt.action="getProjLocDtlRpt";
		document.projLocDtlRpt.method="post";
		document.projLocDtlRpt.submit();
	}
	return false;
};

	function downloadPDF(){
		document.projLocDtlRpt.action="downloadprojectLocationDetailsPDF";
		document.projLocDtlRpt.method="post";
		document.projLocDtlRpt.submit();
	}

	function exportExcel(){
		document.projLocDtlRpt.action="downloadExcelProjLocDtlRpt";
		document.projLocDtlRpt.method="post";
		document.projLocDtlRpt.submit();
	}
	
</script>

</head>
<body>



<!-- <div  style="text-align:center;"><h5>Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee </h5></div> -->



<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>
<label id="head">Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee </label></h5></div>


<br>
<div class ="card">
<div class="row">
<div class="col-3" ></div>
<div class="col-9">

<div class="table-responsive">

 <form:form autocomplete="off" name="projLocDtlRpt" id="projLocDtlRpt"  modelAttribute="projLocDtlRpt" action="projLocDtlRpt" method="get">
 
      <table >
        <tr align="center">
        
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();">
              		<option value="">--Select--</option>
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
          
          <td class="label">District <span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="this.form.submit();">
              		<option value="0">--SelectAll--</option>
                  	<c:if test="${not empty districtList}">
               			<c:forEach items="${districtList}" var="lists">
               				<c:if test="${lists.key eq district}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne district}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
          <td class="label">Project <span style="color: red;">*</span></td>
          <td align="center">
              <select name="project" id="project" >
              		<option value="0" style="text-align:center;">--All--</option>
                  	<c:if test="${not empty projectList}">
               			<c:forEach items="${projectList}" var="lists">
               				<c:if test="${lists.key eq project}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne project}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
         
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="getprojLocDtlRpt();" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8">
	<c:if test="${dataListSize>0}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if>
        <table class="table">
          <tr>
            <td>
            
            	<table id="dtBasicExample" class="table table-bordered table-striped table-highlight w-auto">
            	<thead>
              		<tr align="center">
              			<th align="center">S.No.</th>
		              	<th align="center">Project Name</th>
                        <th align="center">Block Name</th>
                        <th align="center">Gram Panchayat/Village Council</th>
                        <th align="center">Watershed Committee</th>
                        <th align="center">Village Name</th>
                       <!--  <th align="center">MWC Description</th> -->
               		</tr>	</thead>
               	<tbody>	
               		<tr>
	               		<th align="center"><b> 1 </b></th>
	               		<th align="center"><b> 2 </b></th>
	               		<th align="center"><b> 3 </b></th>
		               	<th align="center"><b> 4 </b></th>
		               	<th align="center"><b> 5 </b></th>
	               		<th align="center"><b> 6 </b></th>
	               		<!-- <th align="center"><b> 7 </b></th> -->
               		</tr>
               
               		<c:if test="${dataListSize>0}">
						<c:forEach items="${dataList}" var="dataV" varStatus="count">
							<tr>
								<td><c:out value='${dataV[0]}' /></td>
								<td><c:out value='${dataV[1]}' /></td>
								<td><c:out value='${dataV[2]}' /></td>
								<td><c:out value='${dataV[3]}' /></td>
								<td><c:out value='${dataV[5]}' /></td>
								<td><c:out value='${dataV[4]}' /></td>
							</tr>
						</c:forEach>
						
						<tr>
			
				<td align="right" class="table-primary" colspan="3"><b> Total </b></td>
<%-- 				<td align="right" class="table-primary" ><b><c:out value='${projSize}' /> </b></td> --%>
<%-- 				<td align="right" class="table-primary" ><b><c:out value='${blkSize}' /> </b></td> --%>
				<td align="right" class="table-primary" ><b><c:out value='${gpSize}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${wcSize}' /> </b></td>
				<td align="right" class="table-primary" ><b><c:out value='${villSize}' /> </b></td>
							
			</tr>
						
					</c:if>
              </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td>
            	<c:if test="${dataListSize<=0 }">
   					<table width="100%" border="0" cellspacing="0" cellpadding="0">
          				<tr class="tabs">
            				<td><center><span style="color: red;"> No Data Found !</span></center></td>
            				
          				</tr>
        			</table>
  				</c:if>
  			</td>
          </tr>
        </table>
        </div>
        </div>
    <br>
	
	</div>

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>