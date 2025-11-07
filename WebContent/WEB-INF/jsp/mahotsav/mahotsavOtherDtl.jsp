<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavheader.jspf"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<script type="text/javascript">
function showChangedata(){
	
	
    document.mohotsav.action="otherMohotsav";
	document.mohotsav.method="post";
	document.mohotsav.submit();
}

</script>

<div class="container mt-5">
    <div class="card shadow p-4">
        <h4 class="text-center text-primary mb-4">Content Description (Reels / Photography)</h4>

        <form name="mohotsav" action="submitRegistration" method="post">
        <input type="hidden" name="name" value="${name}">
        <input type="hidden" name="phone" value="${phone}">
        <input type="hidden" name="email" value="${email}">
        <input type="hidden" name="address" value="${address}">
        
         <div class="row mb-3">
                <div class="col-md-4">
                    <label>State</label>
                     <select name="state" id="state" class="form-select" onchange="showChangedata();" required="required">
              		<option value="">--Select State--</option>
              		
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
                </div>
                
               

                <div class="col-md-4">
                    <label>District</label>
                    <select name="district" id="district" class="form-select" onchange="showChangedata();" required="required">
              		<option value="">--Select District--</option>
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
                </div>

              
              

                <div class="col-md-4">
                    <label>Block</label>
                <select name="block" id="block" required="required" class="form-select" onchange="showChangedata();">
                <option value="">--Select Block--</option>
              	<c:if test="${not empty blockList}">
               					<c:forEach items="${blockList}" var="lists">
               						<c:if test="${lists.key eq blkd}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne blkd}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
                </select>
                </div>
                
                
                <div class="col-md-4">
                    <label>Village</label>
                    <select name="Village" class="form-select">
                        <option value="">Select Village</option>
                    </select>
                </div>
                <div class="col-md-4">
                <label>Longitude</label>
                <input type="text" name="longitute" class="form-control" placeholder="Enter Longitude">
            </div>
               <div class="col-md-4">
                <label>Latitude</label>
                <input type="text" name="latitude" class="form-control" placeholder="Enter Latitude">
            </div>
            </div>
            <h4 class="text-center text-primary mb-4">List of URL's</h4>
            
            <div class="mb-3">
                <label>1. Facebook</label>
                <input type="url" name="facebook" class="form-control" placeholder="Enter Facebook video URL">
            </div>

            <div class="mb-3">
                <label>2. YouTube</label>
                <input type="url" name="youtube" class="form-control" placeholder="Enter YouTube video URL">
            </div>

            <div class="mb-3">
                <label>3. Instagram</label>
                <input type="url" name="instagram" class="form-control" placeholder="Enter Instagram video URL">
            </div>
            
            <div class="mb-3">
                <label>4. Twitter</label>
                <input type="url" name="twitter" class="form-control" placeholder="Enter Twitter video URL">
            </div>
            
            <div class="mb-3">
                <label>5. Linkedin</label>
                <input type="url" name="linkedin" class="form-control" placeholder="Enter Linkedin video URL">
            </div>
           
             
           

            <div class="text-center">
                <button type="submit" class="btn btn-success px-5">Submit</button>
            </div>
        </form>
    </div>
</div>
<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>