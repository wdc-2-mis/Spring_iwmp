<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavheader.jspf"%>

<title>View the Social Media Url Details</title>
<script>
    function addMediaViewDetails(regno,videoid){
    	document.getElementById('regno').value=regno;
    	document.getElementById('videoid').value=videoid;
        document.mohotsav.action="addMediaViewDetails";
    	document.mohotsav.method="post";
    	document.mohotsav.submit();
    }
</script>

<div class="container mt-5">

    <form name="mohotsav" id="mohotsav" >
		<input type="hidden" name="regno" id="regno" value="${regno}"/>
		<input type="hidden" name="videoid" id="videoid" value= ""/>
<!--        	<input type="hidden" id="urlType" name="urlType"/> -->
    
        <div class="card shadow mt-1 p-5">
        
            <h4 class="text-center text-primary mb-4"><u>View the Social Media Url Details</u></h4>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>S.No.</th>
                            <th>State Name</th>
                            <th>District Name</th>
                            <th>Block Name</th>
                            <th>Village Name</th>
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
                        </tr>
                    </thead>
                    <tbody>

					<c:if test="${not empty WMList}">
                        <c:forEach items="${WMList}" var="row" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td><c:out value="${row.stname}" /></td>
                                <td class="text-end">${row.distname}</td>
                                <td class="text-end">${row.blkname}</td>
                                <td class="text-end">${row.villname}</td>
                                <td class="text-end">${row.mediaurl}</td>
                                <td class="text-end">${row.longitude}</td>
                                <td class="text-end">${row.latitude}</td>
                                <td ><button class="btn btn-warning btn-sm" onclick="addMediaViewDetails('${row.regno}','${row.videoid}')"> Add Views Details </button></td>
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
