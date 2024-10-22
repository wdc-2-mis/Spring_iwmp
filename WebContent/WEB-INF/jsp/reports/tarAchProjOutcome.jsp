<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@include file="/WEB-INF/jspf/header.jspf"%>

<div class="container-fluid">
<div class="row">
<div class="col text-center">
<br/>
<h5>Format O1- Target/Outcome of Projects Periodically</h5>
<hr/>
</div>
</div>

<div class="row">
<div class="col-2" ></div>
 <div class="col-12">
 
<form action="tarAchProjOutcome" method="post">
<input type="hidden" id="distCode" name="distCode" value="${distCode}" />
<input type="hidden" id="projId" name="projId" value="${projectId}" />
<input type="hidden" id="fYear" name="fYear" value="${fromYear}" />
<input type="hidden" id="tYear" name="tYear" value="${toYear}" />
<div class="form-row">
 
 <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
  
  <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All--</option>
    			<c:forEach items="${stateList}" var="state">
							<option  value="<c:out value="${state.key}"/>" 
							<c:out value="${state.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${state.value}" /></option>
				</c:forEach>
    		</select>
          </td>
  
  <td><b>District <span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control project" id="district" name="district" required>
      			<option value="0">--All--</option>
    		  
    		   <%-- <c:forEach items="${distact}" var="district">
							<option  value="<c:out value="${district.key}"/>" 
							<c:out value="${district.key== distCode ?'selected':'' }"/>>
							<c:out 	value="${district.value}" /></option>
				</c:forEach> --%>
				
    		<c:forEach var="district" items="${distact}">
    		<c:if test="${district.key eq distCode}">
    		<option value="${district.key}" selected >${district.value}</option>
    		</c:if>
       <option value="${district.key}" >${district.value}</option>
    </c:forEach>
    
    		</select>
          </td>
          
  <td ><b>Project <span style="color: red;">*</span></b></td>
          <td >
             <select class="form-control project" id="project" name="project" required>
      			<option value="0">--All--</option>
    		<c:forEach items="${proj}" var="project">
							<option  value="<c:out value="${project.key}"/>" 
							<c:out value="${project.key== projectId ?'selected':'' }"/>>
							<c:out 	value="${project.value}" /></option>
				</c:forEach>
    		
    		
    		
    		</select>
          </td>
     <tr align="center" >     
  <td ><b>From Year: <span style="color: red;">*</span></b></td>
          <td >
              <select class="form-control finyear" id="fromyear" name="fromYear" required>
      			<option value="0">--All--</option>
    		  </select>
          </td>
  
  <td ><b>To Year: <span style="color: red;">*</span></b></td>
          <td >
              <select class="form-control tofinyear" id="toyear" name="toYear" required>
      			<option value="0">--All--</option>
    		  </select>
          </td>
  
  <td><b>Target:</b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input type="checkbox" value="t" name="t" checked/>
  <td><b>Achievement:</b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input type="checkbox"  value = "a" name="a" checked/>
  
  
  
  <td  align="center" class="label"> 
  <input type="submit" class="btn btn-info form-control" id="tarachrpt" name="tarachrpt" value="View Report">
  </td>
 </tr>
 </tr>
 </table>
  </div>
  </form>
  <hr/>
</div> 

<div class="col-1"></div>
<div class="col" id="reportDiv">
<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="exportPDF()" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<%-- <c:if test="${yrNotIncluded ne null &&  yrNotIncluded ne ''}"><label class="text-danger">Note:- Data of annual action plan for <c:out value="${yrNotIncluded}" /> is not available.</label></c:if>
 --%>
 
 
<c:choose>
<c:when test="${yrListSize ne null}">
<table id="tblReport" cellspacing="0" class="table" >
  <thead>
 
   <c:if test="${stCode ne null && stCode ne ''}">
   <c:if test="${valuesa ne '' &&  valuest ne ''}">
   <tr><th colspan="${yrList.size()*2+4}">
   <label>State: <c:out value="${statename}"/> , District: <c:out value="${getdistrict}"/> , Project: <c:out value="${projdesc}"/>, From date: <c:out value="${fromyear}"/>, To date: <c:out value="${toyear}"/> </label> 
   </th>
   </tr>
   </c:if>
   
   <c:if test="${valuesa eq '' ||  valuest eq ''}">
   <tr><th colspan="${yrList.size()+3}">
   <label>State: <c:out value="${statename}"/> , District: <c:out value="${getdistrict}"/> , Project: <c:out value="${projdesc}"/>, From date: <c:out value="${fromyear}"/>, To date: <c:out value="${toyear}"/> </label> 
   </th>
   </tr>
   </c:if>
   
   </c:if>
  
   <tr>
   <th>S.No.</th>
      <th>Outcome Key Parameters</th>
     <c:set var="count1" value="0" />
     
     <c:choose>
		<c:when test="${yrListSize ne null}">
		<c:forEach items="${yrList}" var="yrListShow" >
           	 <c:if test="${valuesa ne '' &&  valuest ne ''}">	
			<th colspan=2 class="text-center "><c:out value='${yrListShow}' /></th>
		     </c:if> 
		 <c:if test="${valuesa eq '' &&  valuest eq ''}">	
			<th colspan=2 class="text-center "><c:out value='${yrListShow}' /></th>
		     </c:if> 
		 <c:if test="${valuesa eq '' &&  valuest ne ''}">
		<th colspan=1 class="text-center "><c:out value='${yrListShow}' /></th>
		</c:if>
		<c:if test="${valuesa ne '' &&  valuest eq ''}">
		<th colspan=1 class="text-center "><c:out value='${yrListShow}' /></th>
		</c:if>
		
		</c:forEach>
	 </c:when>
	<c:otherwise>
	<th></th>
	</c:otherwise>
	</c:choose>
	  <c:if test="${valuesa eq '' &&  valuest eq ''}">
	  <th colspan=2 style="text-align:center">Total</th>
      </c:if>
      
      <c:if test="${valuesa ne '' &&  valuest ne ''}">
	  <th colspan=2 style="text-align:center">Total</th>
      </c:if>
      
       <c:if test="${valuest ne '' && valuesa eq ''}">
       <th colspan=1 style="text-align:center">Total</th>
       </c:if>
       
       <c:if test="${valuesa ne '' && valuest eq ''}">
       <th colspan=1 style="text-align:center">Total</th>
       </c:if>
       </tr>
       <tr>
      <th ></th>
      <th ></th>
      <c:forEach items="${yrList}" var="yrListShow" >
	  <c:if test="${valuesa ne null &&  valuest ne null}">
<!-- 	  <th style="text-align:left">Target</th> -->
<!--       <th style="text-align:left">Achievement</th> -->
     </c:if>
     
     <c:if test="${valuest ne ''}">
	  <th style="text-align:left">Target</th>
      </c:if>
     
     <c:if test="${valuesa ne ''}">
	  <th style="text-align:left">Outcome Achievement</th>
     </c:if>
     
     <c:if test="${valuesa eq '' &&  valuest eq ''}">
	  <th style="text-align:left">Target</th>
      <th style="text-align:left">Outcome Achievement</th>
     </c:if>
     
     </c:forEach>
    
     <c:if test="${valuesa eq '' &&  valuest eq ''}">
      <th style="text-align:left">Target</th>
      <th style="text-align:left">Outcome Achievement</th>
    </c:if>
    
    <c:if test="${valuest ne ''}">
    <th style="text-align:left">Target</th>
    </c:if>
    
    <c:if test="${valuesa ne ''}">
	  <th style="text-align:left">Outcome Achievement</th>
     </c:if>
     
    </tr> 
   </thead>
<tbody>
<%-- <tr>
		<c:set var="count1" value="1" />
		<c:if test="${yrListSize eq null}">
		<c:set var="yrListSize" value="1" />
		</c:if>
		<c:forEach begin="0" end="${yrListSize+1}">
			<c:set var="counter" value="${counter + 1}" />
			<td class="text-center"><c:out value='${count1}' /> <c:set
					var="count1" value="${count1+1}" /></td>
		</c:forEach>
	</tr> --%>
	
   <c:choose>
    <c:when test="${dataList ne null}">
    
		<c:forEach items="${dataList}" var="datalist" >
		<tr>
		
		<c:forEach items="${datalist}" var="data" >
		
			<td class="text-left ">
	 
		 <c:if test = "${!fn:contains(data, ',#')}">
		 <c:out value='${data}' />
		 </c:if>
		 <c:if test = "${fn:contains(data, ',#')}">
		 <a href="tarAchProjMnthWse">${fn:substringBefore(data, ",#")} </a>
<%-- 		 <c:out value='${fn:substringBefore(data, ",u")}' /> --%>
		 </c:if>
		
	</td>
			</c:forEach>
			
			</tr>
			
		</c:forEach>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
   </c:choose>
   
   
   </tbody>
  
  </table>
</c:when>
</c:choose>
</div>
</div> 
</div>
 <script src='<c:url value="/resources/js/tarAchProjOutcome.js" />'></script>
<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>


</body>
</html>