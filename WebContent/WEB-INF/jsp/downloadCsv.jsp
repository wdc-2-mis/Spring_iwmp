<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header2.jspf"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CSV File Download</title>
</head>
<body>

	<div class="container">
		<div class="table-wrapper">

			<table class="table table-striped table-hover" id="distacttable">
				<thead>

					<tr>
						<td colspan="4">
							<h5>
								Download Master of WDC-PMKSY2.0 for integration with NRSC
								(Shristi / Drishti) Application</b>
							</h5>

						</td>
					</tr>
					<tr>
						<th>Location Masters</th>
						<th>Head and Activity Masters</th>
						<th>Financial Year</th>
						<th>Project</th>

					</tr>
				</thead>
				<tr>
					<td>
						<p>
							<a href="downloadStateCSV">State Master</a>
						</p>
						<p>
							<a href="downloadDistrictCSV">District Master</a>
						</p>
						<p>
							<a href="downloadBlockCSV">Block Master</a>
						</p>
						<p>
							<a href="downloadGrampanchayatCSV">Gram Panchayat Master</a>
						</p>
						<p>
							<a href="downloadVillageCSV">Village Master</a>
						</p>
					</td>
					<td>
						<p>
							<a href="downloadHeadCSV">Head Code</a>
						</p>
						<p>
							<a href="downloadActivityCSV">Activity Code</a>
						</p>
						<p>
							<a href="downloadSubActivityCSV">Activity Type Code</a>
						</p>
						
						<p>
							<a href="downloadEPAActivityCSV">Entry Point Activity (EPAs) Master</a>
						</p>
						
						<p>
							<a href="downloadLivelihoodActivityCSV">Livelihood Activities Master</a>
						</p>
						
						<p>
							<a href="downloadProductionActivityCSV">Production System Master</a>
						</p>
					</td>

					<td>
						<p>
							<a href="downloadFiancialyearCSV">Financial Year</a>
						</p>

					</td>
					<td>

						<p>
							<a href="downloadSanctionedProjectCSV">Project Details</a>
						</p>

					</td>

				</tr>


				<tr>
					<td colspan="4">
					       Note:
					       1. There are only two levels for watershed works i.e. Head and Activity. </br>
					       </t>2. Activity Type is only applicable for some activity of Hotriculture only.
					</td>
				</tr>
			</table>


		</div>
	</div>

</body>
</html>


