<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavOtherheader.jspf"%>

<title>Details of the Social Media URLs </title>
<script>
    function addMediaViewDetails(regno,videoid,mediatype){
    	document.getElementById('regno').value=regno;
    	document.getElementById('videoid').value=videoid;
    	document.getElementById('mediatype').value=mediatype;
        document.mohotsav.action="addMediaViewDetails";
    	document.mohotsav.method="post";
    	document.mohotsav.submit();
    }
    
    window.onload = function() {
        if (!sessionStorage.getItem("noBack")) {
            sessionStorage.setItem("noBack", "true");
        }
        history.pushState(null, null, location.href);
        window.onpopstate = function () {
            history.go(1);
        };
    };
</script>
<style>
.container {
  max-width: 100% !important;  /* full page width */
}

.card {
  width: 100%;                 /* card stretches fully */
}

.table-responsive {
  overflow-x: visible;   /* allow full width, no scroll */
  white-space: normal;   /* let text wrap instead of forcing one line */
}

.table {
  table-layout: auto;    /* auto sizing instead of fixed */
  width: 100%;           /* stretch to container width */
}
</style>

<div class="container mt-5">

    <form name="mohotsav" id="mohotsav" >
		<input type="hidden" name="regno" id="regno" value="${regno}"/>
		<input type="hidden" name="videoid" id="videoid" value= ""/>
       	<input type="hidden" id="mediatype" name="mediatype" value=""/>
    
        <div class="card shadow mt-1 p-5">
        
            <h4 class="text-center text-primary mb-4"><u>Details of the Social Media URLs</u></h4>
            <div class="table-responsive">
                <table class="table table-bordered table-striped" >
                    <thead>
                        <tr>
                            <th>S.No.</th>
                            <th>State Name</th>
                            <th>District Name</th>
                            <th>Block Name</th>
                            <th>Village Name</th>
                            <th>Platform</th>
                            <th>Media URL</th>
                            <th>Longitude</th>
                            <th>Latitude</th>
                            <th>Action</th>
                        </tr>
                        <tr>
                            <th>1</th>
                            <th>2</th>
                            <th>3</th>
                            <th>4</th>
                            <th>5</th>
                            <th>6</th>
                            <th>7</th>
                            <th>8</th>
                            <th>9</th>
                            <th>10</th>
                        </tr>
                    </thead>
                    <tbody>

					<c:if test="${not empty WMList}">
                        <c:forEach items="${WMList}" var="row" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td><c:out value="${row.stname}" /></td>
                                <td class="text-center">${row.distname}</td>
                                <td class="text-center">${row.blkname}</td>
                                <td class="text-center">${row.villname}</td>
                                <td class="text-center">${row.mediaid eq 1?'Facebook':row.mediaid eq 2?'Youtube':row.mediaid eq 3?'Instagram':row.mediaid eq 4?'Twitter':'Linkedin'}</td>
                                <td class="text-center">${row.mediaurl}</td>
                                <td class="text-center">${row.longitude}</td>
                                <td class="text-center">${row.latitude}</td>
                                <td ><button class="btn btn-warning btn-sm" onclick="addMediaViewDetails('${row.regno}','${row.videoid}','${row.mediatype}')"> Add Views Details </button></td>
                            </tr>

                        </c:forEach>
					 </c:if>
						<c:if test="${empty WMList}">
							<div class="alert alert-info mt-4 text-center">Data Not
								Available.</div>
						</c:if>
					</tbody>
                </table>
            </div>
        </div>
   

    

    </form> 

</div>

<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>
