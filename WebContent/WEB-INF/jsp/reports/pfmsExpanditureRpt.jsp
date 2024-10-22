<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<title>Report PF3- State-wise Project Expenditure</title>


<script type="text/javascript">
function downloadPDF(){
	document.expndtrReport.action="downloadStateExpndtrPFMSPDF";
	document.expndtrReport.method="post";
	document.expndtrReport.submit();
}
function downloadDistPDF(stname,stcode){
	document.getElementById("stcode").value=stcode;
	document.getElementById("stname").value=stname;
	document.expndtrReport.action="downloadDistrictExpndtrPFMSPDF";
	document.expndtrReport.method="post";
	document.expndtrReport.submit();
}
function downloadProjectPDF(dcode,stname,dname){
	document.getElementById("dcode").value=dcode;
 	document.getElementById("stname").value=stname;
 	document.getElementById("dname").value=dname;
	document.expndtrReport.action="downloadProjectExpndtrPFMSPDF";
	document.expndtrReport.method="post";
	document.expndtrReport.submit();
}

function exportExcel(){
	document.expndtrReport.action="downloadExcelStateWiseExpenditure";
	document.expndtrReport.method="post";
	document.expndtrReport.submit();
}

function exportdistExcel(stname,stcode){
	document.getElementById("stcode").value=stcode;
	document.getElementById("stname").value=stname;
	document.expndtrReport.action="downloadExcelDistWiseExpenditure";
	document.expndtrReport.method="post";
	document.expndtrReport.submit();
	}

function exportProjExcel(dcode,stname,dname){
	document.getElementById("dcode").value=dcode;
 	document.getElementById("stname").value=stname;
 	document.getElementById("dname").value=dname;
	document.expndtrReport.action="downloadExcelTranxMappedwithProj";
	document.expndtrReport.method="post";
	document.expndtrReport.submit();
}

</script>
<form action ="downloadStateExpndtrPFMSPDF" method="post" name="expndtrReport">
<div class="form-row">
			<input type="hidden" name="stname" id="stname" value="" />
			<input type="hidden" name="stcode" id="stcode" value="" />
			<input type="hidden" name="dcode" id="dcode" value="" />
			<input type="hidden" name="dname" id="dname" value="" />
			</div>
</form>
<div class="container-fluid">
	
	
	<c:if test="${pfmsExpndtreList ne null}">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF3 - State-wise Project Expenditure</label></h5>
	</div>
	
	<div class="col-2" ></div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	<table id="dtBasicExample" cellspacing="0" class="table"    >
  	<thead>

    	<tr>
    	<th class="text-right" colspan="9">All amounts are Rs.</th> </tr>
      	<tr>	
      		<th  class="text-center">S.No.</th>
      		<th  class="text-center">State Name</th>
      		<th  class="text-center">District Expenditure</th>
      		<th  class="text-center">Block Expenditure</th>
      		<th  class="text-center">Gram Expenditure</th>
      		<th  class="text-center">Village Expenditure</th>
      		<th  class="text-center">Total Transactions</th>
      		<th  class="text-center">Transactions Mapped with Project</th>
      		<th  class="text-center">Transactions Mapped with Work-Id</th>
      		</tr>
      		</thead>
      		
      		<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
			<th class="text-center">7</th>
			<th class="text-center">8</th>
			<th class="text-center">9</th>
			</tr>
			<c:forEach items="${pfmsExpndtreList}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td><a href="distwiseExpenditure?stcode=${proj.stcode}&stname=${proj.stname}">${proj.stname}</a></td>
								    <td align="right"><fmt:formatNumber value ="${proj.dexpndtr}"
								    type="number" minFractionDigits="2"/>
								    </td>
									<td align="right"><fmt:formatNumber value = "${proj.bexpndtr}"
									type="number" minFractionDigits="2"/>
									</td>
									<td align="right"><fmt:formatNumber value = "${proj.gexpndtr}"
									type="number" minFractionDigits="2"/>
									</td>
									<td align="right"><fmt:formatNumber value = "${proj.vexpndtr}"
									type="number" minFractionDigits="2"/>
									</td>
									<td align="right"><c:out value ="${proj.totaltranx}"/></td>
									<td align="right"><c:out value ="${proj.mappedtranx}"/></td>
									<td align="right"><c:out value ="${proj.mappedworkid}"/></td>
									</tr>
									<c:set var="totaldexpndtr"
											value="${totaldexpndtr+proj.dexpndtr}" />
									<c:set var="totalbexpndtr"
												value="${totalbexpndtr+proj.bexpndtr}" />
									<c:set var="totalgexpndtr"
												value="${totalgexpndtr+proj.gexpndtr}" />
									<c:set var="totalvexpndtr"
												value="${totalvexpndtr+proj.vexpndtr}" />
									<c:set var="totaltotaltranx"
												value="${totaltotaltranx+proj.totaltranx}" />
									<c:set var="totalmappedtranx"
												value="${totalmappedtranx+proj.mappedtranx}" />
												<c:set var="totalmappedworkid"
												value="${totalmappedworkid+proj.mappedworkid}" />
									</c:forEach>
									<tr>
									<td class="table-primary"></td>
									<td  align="right" class="table-primary"><b>Grand Total</b></td>
									<td align="right" class="table-primary"><b><c:out value="${totaldexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalbexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalgexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalvexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totaltotaltranx}"/></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalmappedtranx}"/></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalmappedworkid}"/></b></td>  
				
			</tr>
      		</table>
      		</c:if>
      		
      	<c:if test="${distwiseExpList ne null}">
      	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF3 - District-wise Project Expenditure for State '<c:out value="${stname}" />' </label></h5>
	</div>
	
	<div class="col-2" ></div>
	<button name="exportExcel" id="exportExcel" onclick="exportdistExcel('${stname}','${stcode}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadDistPDF('${stname}','${stcode}')" class="btn btn-info">PDF</button>
	<table id="dtBasicExample" cellspacing="0" class="table"    >
  	<thead>
  	<tr>
				<th class="text-left" colspan="2">State: ${stname}</th>
				<th class="text-right" colspan="7">All
					amounts are Rs.
				</th>
			</tr>
    	<tr>
      		<th  class="text-center">S.No.</th>
      		<th  class="text-center">District Name</th>
      		<th  class="text-center">District Expenditure</th>
      		<th  class="text-center">Block Expenditure</th>
      		<th  class="text-center">Gram Expenditure</th>
      		<th  class="text-center">Village Expenditure</th>
      		<th	 class="text-center">Total Transactions</th>
      		<th  class="text-center">Transactions Mapped with Project</th>
      		<th  class="text-center">Transactions Mapped with Work-Id</th>
      		</tr>
      		</thead>
      		
      		<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
			<th class="text-center">7</th>
			<th class="text-center">8</th>
			<th class="text-center">9</th>
			
			</tr>
			<c:forEach items="${distwiseExpList}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td><a href="getTranxMappedwithProj?dcode=${proj.dcode}&state=${stname}&distname=${proj.distname}"><c:out value ="${proj.distname}"/></a></td>
									 <td align="right"><fmt:formatNumber value ="${proj.dexpndtr}"
							    		type="number" minFractionDigits="2"/> </td>
									<td align="right"><fmt:formatNumber value = "${proj.bexpndtr}"
 									type="number" minFractionDigits="2"/> 
									</td>
									<td align="right"><fmt:formatNumber value = "${proj.gexpndtr}"
 									type="number" minFractionDigits="2"/> 
									</td>
									<td align="right"><fmt:formatNumber value = "${proj.vexpndtr}"
 									type="number" minFractionDigits="2"/> 
									</td>
									<td align="right"><c:out value ="${proj.totaltranx}"/></td>
									
									<td align="right"><c:out value ="${proj.mappedtranx}"/></td>
									<td align="right"><c:out value ="${proj.mappedworkid}"/></td>
									</tr>
									<c:set var="totaldexpndtr"
											value="${totaldexpndtr+proj.dexpndtr}" />
									<c:set var="totalbexpndtr"
												value="${totalbexpndtr+proj.bexpndtr}" />
									<c:set var="totalgexpndtr"
												value="${totalgexpndtr+proj.gexpndtr}" />
									<c:set var="totalvexpndtr"
												value="${totalvexpndtr+proj.vexpndtr}" />
									<c:set var="totaltotaltranx"
												value="${totaltotaltranx+proj.totaltranx}" />
									<c:set var="totalmappedtranx"
												value="${totalmappedtranx+proj.mappedtranx}" />
												<c:set var="totalmappedworkid"
												value="${totalmappedworkid+proj.mappedworkid}" />
									</c:forEach>
									<tr>
									<td class="table-primary"></td>
									<td  align="right" class="table-primary"><b>Grand Total</b></td>
									<td align="right" class="table-primary"><b><c:out value="${totaldexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalbexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalgexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalvexpndtr}" /></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totaltotaltranx}"/></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalmappedtranx}"/></b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalmappedworkid}"/></b></td>  
				
									
      		</table>
      		</c:if>
      		
      		<c:if test="${projWiseList ne null}">
      	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF3 - Transaction Details Mapped with Project</label></h5>
	</div>
	
	<div class="col-2" ></div>
	<button name="exportExcel" id="exportExcel" onclick="exportProjExcel('${dcode}','${stname}','${dname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadProjectPDF('${dcode}','${stname}','${dname}')" class="btn btn-info">PDF</button>
	<table id="dtBasicExample" cellspacing="0" class="table">
  	<thead>
<tr>
				<th class="text-left" colspan="4">State: ${stname} &nbsp;&nbsp; District: ${dname}</th>
				<th class="text-right" colspan="13">All
					amounts are Rs.
				</th>

    	</tr>
    	<tr>
      		<th  class="text-center">S.No.</th>
      		<th  class="text-center">Transaction Id</th>
      		<th  class="text-center">Transaction Level</th>
      		<th	 class="text-center">Agency Code</th>
      		<th  class="text-center">Agency Name</th>
      		<th  class="text-center">Transaction Date</th>
      		<th  class="text-center">Invoice No.</th>
      		<th  class="text-center">Block Name</th>
      		<th  class="text-center">Gram Name</th>
      		<th  class="text-center">Village Census Code</th>
      		<th  class="text-center">Transaction Amount</th>
      		<th  class="text-center">Project Id</th>
      		<th  class="text-center">Project Name</th>
      		</tr>
      		</thead>
      		
      		<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
			<th class="text-center">7</th>
			<th class="text-center">8</th>
			<th class="text-center">9</th>
			<th class="text-center">10</th>
			<th class="text-center">11</th>
			<th class="text-center">12</th>
			<th class="text-center">13</th>
			
			</tr>
			<c:forEach items="${projWiseList}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td><c:out value ="${proj.tranxid}"/></td>
									<td><c:out value ="${proj.tranxlevel}"/></td>
									 <td><c:out value ="${proj.agencycode}"/> </td>
									<td><c:out value = "${proj.agencyname}"/> </td>
									<td align="right"><c:out value = "${proj.tranxdate}"/> </td>
									<td align="right"><c:out value = "${proj.invoiceno}"/> </td>
									<td align="right"><c:out value = "${proj.blockname}"/> 
									</td>
									<td align="right"><c:out value = "${proj.gramname}"/> 
									</td>
									<td align="right"><c:out value = "${proj.villcode}"/> 
									</td>
									<td align="right"><c:out value ="${proj.totalamount}"/></td>
									<td align="right"><c:out value = "${proj.projid}"/> </td>
									<td align="center"><c:out value ="${proj.projname}"/></td>
									</tr>
									<c:set var="totalamountt"
											value="${totalamountt+proj.totalamount}" />
									</c:forEach>
									<tr>
									<td  align="right" class="table-primary" colspan="10"><b>Grand Total</b></td>
									<td align="right" class="table-primary"><b><c:out value="${totalamountt}" /></b></td>
										<td class="table-primary" colspan="2"></td>
			</tr>
									
      		</table>
      		</c:if>
      			
      		</div>
   <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>   		