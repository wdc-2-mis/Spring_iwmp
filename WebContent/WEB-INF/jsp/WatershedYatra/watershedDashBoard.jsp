<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/dashboardheader.jspf"%>

<!DOCTYPE html>
<html>
<head>
    <title>Watershed Dashboard</title>
     <style>
.component-container {
	display: flex;
	flex-direction: column;
	background-color: #FAF9F6;
	margin: 20px;
	padding: 10px;
	border: 1px solid gray;
	border-radius: 10px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.field-row {
	display: flex;
	flex-wrap: wrap;
}

.field-container {
	display: flex;
	flex-direction: column;
	background-color: white;
	margin: 10px;
	padding-top: 10px;
	border: 1px solid #ddd;
	border-radius: 10px;
	width: 250px;
	height: 130px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.component-field {
	margin-left: 10px;
	color: Green;
}

.field-title {
	position: relative;
	margin-left: 10px;
    padding-left: 20px; /* Adjust padding to create space for the logo */ 
}

.field-title::before {
    content: "";
    background: url('resources/images/chart_logo.jpg') no-repeat center center;
    background-size: 15px 15px; /* Adjust the size */
    width: 15px;  /* Adjust width as needed */
    height: 15px; /* Adjust height as needed */
    position: absolute;
    left: 0;
    top: 12px;
    transform: translateY(-50%);
}

.field-value {
	color: cornflowerblue;
	font-weight: bold;
	font-size: xx-large;
	text-align: center;
}

.panel-underline {
	border-top: 2px solid black;
	margin-left: 10px;
}
</style>
</head>
<body>

<h2 class="panel-title" style ="text-align: center; margin: 10px;">Watershed Yatra Dashboard</h2>
<script>
 $(document).ready(function(){
	 $('#loading').hide();
 });
 </script>
 <div class="panel-body" style ="text-align: center; margin: 20px;"></div>
<div>
    <c:forEach var="entry" items="${map}">
        <div class="component-container">
            <c:if test ="${entry.key eq 'ing'}">
            <h3 class ="component-field">Inauguration</h3>
            <div class="panel-underline"></div>
            <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
                    <div class="field-container">
                        <div class="field-title">Total States</div>
                        <div class="field-value">${bean.totstates}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Participants</div>
                        <div class="field-value">${bean.totparticipants}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Bhoomi Poojan Works</div>
                        <div class="field-value">${bean.totbhumipujanworks}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Lokarpan Works</div>
                        <div class="field-value">${bean.totlokarpanworks}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Shramdaan on Total Location</div>
                        <div class="field-value">${bean.totshramdannlocationno}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Award Distribution</div>
                        <div class="field-value">${bean.totawarddistribution}</div>
                    </div>
                    
                </c:forEach>
            </div>
            </c:if>
            <c:if test ="${entry.key eq 'wtr'}">
            <h3 class ="component-field">Watershed Yatra</h3>
            <div class="panel-underline"></div>
            <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
                    <div class="field-container">
                        <div class="field-title">Total States</div>
                        <div class="field-value">${bean.totstates}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Participants</div>
                        <div class="field-value">${bean.totparticipants}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of Yatra Location</div>
                        <div class="field-value">${bean.totyatraloc}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of AR Experienced People</div>
                        <div class="field-value">${bean.totarexperiencedpeople}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of People Participated in Quiz</div>
                        <div class="field-value">${bean.totquizparticipants}</div>
                    </div>
                   
                </c:forEach>
            </div>
            </c:if>
            <c:if test ="${entry.key eq 'pre'}">
            <h3 class ="component-field">Pre Yatra</h3>
            <div class="panel-underline"></div>
            <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
                    <div class="field-container">
                        <div class="field-title">Total States</div>
                        <div class="field-value">${bean.totstates}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Gram Sabha</div>
                        <div class="field-value">${bean.totgrabsabha}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Prabhat Pheri</div>
                        <div class="field-value">${bean.totprabhatpheri}</div>
                    </div>
                </c:forEach>
            </div>
            </c:if>
        </div>
    </c:forEach>
</div>






	<footer class="text-center">
    <%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>