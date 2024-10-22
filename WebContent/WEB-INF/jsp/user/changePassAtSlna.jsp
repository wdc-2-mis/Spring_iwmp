
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script src='<c:url value="/resources/js/profile.js" />'></script>
<script src='<c:url value="/resources/js/cpas.js" />'></script>

<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">


<form:form name="userInfoAtSlna" action="userInfoAtSlna"
	id="userInfoAtSlna" autocomplete="off">


	<br></br>
	<br></br>
	<table width="100%" cellpadding="0" cellspacing="0" border="0"
		align="center">
		<tr>
			<td>
				<h5 class="text-center font-weight-bold">
					<u>Change Password of WCDC/PIA </u>
				</h5>
				<table width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td class="labelReport" width="15%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>User
								ID</b></td>
						<td class="labelReport" width="30%" align="right"><input
							oninput="this.value = this.value.toUpperCase()" type="text"
							id="pia" name="pia" maxlength="15"></input></td>
						<td align="center"><input type="submit" align="left"
							value="Search" /></td>
					</tr>

				</table> <br></br> <br></br>
			</td>
			<td>&nbsp;</td>
		</tr>
		<c:if test="${updMessage!=null}">
			<b><font color="red"><c:out value="${updMessage}" /></font></b>
		</c:if>
		<c:if test="${message!=null}">
			<b><font color="red"><c:out value="${message}" /></font></b>
		</c:if>


		<c:if test="${dataListSize>0}">
			<!-- <td align="center" >Change Password of  PIA Users</td> -->
			<c:forEach items="${userbeans}" var="data" varStatus="count">
				<tr class="tabs">
					<c:if test="${(data.userType) eq 'PI'}">
						<td align="center">Change Password of PIA Users</td>
					</c:if>
					<c:if test="${(data.userType) eq 'DI'}">
						<td align="center">Change Password of WCDC Users</td>
					</c:if>
				</tr>
				<tr>
					<td>
						<!-- Report Header & Data  Table No Width at TD level-->
						<table width="100%" border="0" cellpadding="2" cellspacing="1"
							class="gen_table">
							<tr>
								<td class="label">User Type</td>
								<td class="field"><c:if test="${(data.userType) eq 'PI'}">
										<c:out value="PIA" />
									</c:if> <c:if test="${(data.userType) eq 'DI'}">
										<c:out value="WCDC" />
									</c:if></td>
							</tr>
							<tr>
								<td class="label">User Id</td>
								<td class="field"><c:out value="${data.userId}" /></td>
								<td><c:set var="passParam"
										value="${data.userId}###${data.userType}" /> <input
									type="button" name="usertype" align="left"
									value="Change Password"
									onclick="javascript:changePasswordPage('<c:out value="${passParam}"/>');" /></td>

							</tr>
							<tr>
								<td class="label">State</td>
								<td class="field"><c:out value="${data.statename}" /></td>
							</tr>
							<tr>
								<td class="label">District</td>
								<td class="field"><c:out value="${data.distName}" /></td>
							</tr>
							<c:if test="${(data.userType) ne 'DI'}">
								<tr>

								</tr>
								<tr>
									<td class="label">Project</td>
									<td class="field"><c:out value="${data.projectName}" /></td>
								</tr>
							</c:if>
						</table>
					</td>
				</tr>

			</c:forEach>

		</c:if>
	</table>
</form:form>

<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

