<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${completesize ne 0}">
 <table class="table">
		 <tr>
		 <td>
		 <div class="col formheading" style="">
				<h4>
					<b>List of Complete Data</b>
				</h4>
		</div>
		 </td> 
		 </tr>
          <tr>
            <td>
            
            	<table id="dtBasicExample" class="table table-bordered table-striped table-highlight w-auto">
            	<thead>
              		<tr align="center">
              			<th align="center" width="2">S.No.</th>
		              	<th align="center" width="25%">Project Name</th>
                        <th align="center" width="8">Financial Year</th>
                        <th align="center" width="8">Achievement Type</th>
                        <th align="center" width="8">Period</th>
                        <th align="center" width="8">Additional area brought under diversified crops/change in cropping system</th>
                        <th align="center" width="8">Area brought from no crop/single crop to single/multiple crop</th>
                        <th align="center" width="8">Increase in Farmer Income</th> 
                        <th align="center" width="8">Increase in Gross Cropped Area</th>
                        <th align="center" width="8">Increase in Pulses Area</th> 
                        <th align="center" width="8">Increase in Oilseeds Area</th>
               		</tr>	</thead>
               	<tbody>	
               		<tr>
	               		<th align="center"><b> 1 </b></th>
	               		<th align="center"><b> 2 </b></th>
	               		<th align="center"><b> 3 </b></th>
		               	<th align="center"><b> 4 </b></th>
		               	<th align="center"><b> 5 </b></th>
	               		<th align="center"><b> 6 </b></th>
	               		<th align="center"><b> 7 </b></th>
	               		<th align="center"><b> 8 </b></th>
	               		<th align="center"><b> 9 </b></th>
	               		<th align="center"><b> 10 </b></th>
	               		<th align="center"><b> 11 </b></th>
               		</tr><c:set var="proj" value="" />
               		<c:set var="fin" value="" />
						<c:forEach items="${complete}" var="dataV" varStatus="count">
							<tr>
							<td><c:out value='${count.count}' /></td>
								
								<c:choose>
									<c:when test="${proj ne dataV.project}">
										<c:set var="proj" value="${dataV.project}" />
										<td> <c:out value="${dataV.project}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${fin ne dataV.finyear}">
										<c:set var="fin" value="${dataV.finyear}" />
										<td> <c:out value="${dataV.finyear}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								
								<td>
								
								<c:if test="${dataV.achiev_type eq 'Month-Wise'}">
								
									<c:out value="Half-Yearly" />
								
								</c:if>
								<c:if test="${dataV.achiev_type eq 'Year-Wise'}">
									<c:out value='${dataV.achiev_type}' />
								</c:if>
								</td>
								<td><c:out value='${dataV.month}' /></td>
								<td align="center"><c:out value='${dataV.diversified}' /></td>
								<td align="center"><c:out value='${dataV.chnagesingle}' /></td>
								<td align="center"><c:out value='${dataV.farmer_income}' /></td>
								<td align="center"><c:out value='${dataV.change_corp}' /></td>
								<td align="center"><c:out value='${dataV.pulses}' /></td>
								<td align="center"><c:out value='${dataV.oilseeds}' /></td>
							</tr>
						</c:forEach>
					
              </tbody>
              </table>
            </td>
          </tr>
         
     </table>

</c:if>
