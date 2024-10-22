<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<script>
$(function(){
	$(document).on("click", "a", function(){
	    //$(this).text("It works!");
	    $(this).removeClass('active show');
	});
	
});
</script>
<div class="col formheading" style=""><h4><u>Add/ View Baseline Survey (Pre-Project Status)</u></h4> </div>
<div class="container-fluid">
<div class="row">
<div class="container mt-2">

  <div class="tab-content">
  <p id="errorMessage" class="errormessage"></p>
  <div id="projectBasic" class="container tab-pane fade show active"><br>
     <h3>Project Basic</h3>
     <p id="errorMessage" class="errormessage"></p>
     <hr/>
     
  <div class="form-row">
  <div class="form-group col-md-3">
    <label for="project" class="">Project: </label>
     <select class="form-control" id="project" name="project">
    <option value="">--Select Project--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-4">
    <label for="project" class="">Total Sanctioned Area of Project(in ha): </label>
     <input type="text" name="sancArea" id="sancArea" value="" class="form-control" readonly/>
  </div>
  
  <div class="form-group col-md-4">
    <label for="project" class="">Total Geographical Area of Project: </label>
     <input type="text" name="geoarea" id="geoarea" value="" class="form-control" onfocusin="decimalCheck(event)" />
  </div>
  <div class="form-group col-md-3">
    <label for="project" class="">Project Area Covering: </label>
     <select class="form-control" id="groupType" name="groupType" >
    <option value="">--Select--</option>
      <c:forEach items="${areaCover}" var="area">
	<option value="<c:out value="${area.key}"/>"><c:out	value="${area.value}" /></option>
	</c:forEach> 
    </select>
  </div>
  </div>
  <form class="form" autocomplete="off" modelAttribute="baselinemain">
  <h3>In the Sanctioned Project Area</h3>
     <hr/>
     <div id="outHeadActivity">
      <div class="form-row">
     <c:forEach items="${outHeadActivity}" var="act">
    
	<c:choose>
					<c:when test="${act.value.size() gt 0 }">
					<div class="form-group col-md-10 ">
					<label for="" class="">Total Existing <c:out	value="${act.key.outcomeDesc }" /> :</label>
					</div>
					<c:forEach items="${act.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class=""><c:out	value="${val.value }" /> :</label>
						<input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(fn:replace(fn:replace(val.value,' ',''),'.',''),'/','')}_${act.key.outcomeId}_${val.key}" name="${fn:replace(fn:replace(val.value,' ',''),'/','')}_${act.key.outcomeId }_${val.key }" onfocusin="numericOnly(event)" onblur="getNetSownArea()">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-4 ">
    <label for="" class="">Total Existing <c:out	value="${act.key.outcomeDesc }" /> :</label>
    <input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(act.key.outcomeDesc,' ',''),'.',''),'/',''),'(',''),')','')}_${act.key.outcomeId}_0" name="${fn:replace(fn:replace(fn:replace(fn:replace(act.key.outcomeDesc,' ',''),'/',''),'(',''),')','')}_${act.key.outcomeId}_0" onfocusin="numericOnly(event)" onblur="checkValue(this)">
  </div>
					</c:otherwise>
				</c:choose>
				
	</c:forEach> 
	</div>
	<div class="form-row">
  <div class="form-group col-md-3 ">
    <label for="" class="">Total Existing Gross Cropped Area:</label>
    <input type="text" class="form-control" id="grossCroppedArea" name="grossCroppedArea" onfocusin="decimalCheck(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total Existing Net Sown Area:</label>
    <input type="text" class="form-control" id="netSownArea" name="netSownArea" onfocusin="decimalCheck(event)" readonly>
  </div>
  </div>
  </div>
  
  <div class="form-row">
  <div class="form-group">
  <!-- <a href="#projectBasic" class="btn btn-info" data-toggle="tab">Back</a> -->
  <a href="#" class="btn btn-info" data-toggle="tab" id="next">Next</a>
  </div>
  </div>
  </form> 
    
  </div>
  
  <div id="cropland" class="container tab-pane fade"><br>
     <h3>In the Sanctioned Project Area</h3>
     <hr/>
     <form class="form" autocomplete="off">
  
  <div id="phyHeadActivity1">
  <c:forEach items="${phyHeadActivity1}" var="phyAct">
     <div class="form-row">
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class="">Total Existing   <c:out	value="${phyAct.key.headDesc }" /> :</label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class="">   <c:out	value="${val.value }" /> :</label>
						<input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(val.value,')',''),'(',''),'/',''),'.',''),' ','')}_${phyAct.key.headCode }_${val.key}" name="${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /> :</label>
    <input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(phyAct.key.headDesc,')',''),'(',''),'/',''),'.',''),' ','')}_${phyAct.key.headCode }_0" name="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(phyAct.key.headDesc,')',''),'(',''),'/',''),'.',''),' ','')}_${phyAct.key.headCode}_0" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach> 
	</div>
 
  <div class="form-row">
  <div class="form-group">
  <a href="#projectBasic" class="btn btn-info" data-toggle="tab">Back</a>
  <a href="#" class="btn btn-info" data-toggle="tab" id="phynext1">Next</a>
  </div>
  </div>
  </form>
  </div>
  
 <%--  <div id="horticulture" class="container tab-pane fade"><br>
     <h3>In the Sanctioned Project Area 2</h3>
     <hr/>
     <form class="form" autocomplete="off">
     <div id="phyHeadActivity2">
  <c:forEach items="${phyHeadActivity2}" var="phyAct">
     <div class="form-row">
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class="">Total Existing   <c:out	value="${phyAct.key.headDesc }" /> :</label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class="">   <c:out	value="${val.value }" /> :</label>
						<input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(val.value,' ','')}_${phyAct.key.headCode}_${val.key}" name="${fn:replace(val.value,' ','')}_${phyAct.key.headCode}_${val.key}" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /> :</label>
    <input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode}_" name="${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode}_" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach> 
	</div>
  
  <div class="form-row">
  <div class="form-group">
  <a href="#cropland" class="btn btn-info" data-toggle="tab">Back</a>
  <a href="#" class="btn btn-info" data-toggle="tab" id="phynext2">Next</a>
  </div>
  </div>
  </form>
  </div>  --%>
  
  
   <%-- <div id="protectiveIrrigation" class="container tab-pane fade"><br>
     <h3>In the Sanctioned Project Area 3</h3>
     <hr/>
     <form class="form" autocomplete="off">
     <div id="phyHeadActivity3">
   <c:forEach items="${phyHeadActivity3}" var="phyAct">
     <div class="form-row">
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class="">Total Existing   <c:out	value="${phyAct.key.headDesc }" /> :</label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class="">No. of   <c:out	value="${val.value }" /> :</label>
						<input type="text" class="form-control" onfocusin="numericOnly(event)" id="${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" name="${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /> :</label>
    <input type="text" class="form-control" onfocusin="decimalCheck(event)" id="${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode }_" name="${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode }_" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach> 
	</div>
  
  <div class="form-row">
  <div class="form-group">
  <a href="#horticulture" class="btn btn-info" data-toggle="tab">Back</a>
  <a href="#" class="btn btn-info" data-toggle="tab" id="phynext3">Next</a>
  </div>
  </div>
  </form>
  </div>  --%>
  <div id="whs" class="container tab-pane fade"><br>
    <h3>In the Sanctioned Project Area</h3>
     
     <hr/>
      <form class="form" autocomplete="off">
     
      
      
      <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total Existing Water Harvesting Structure:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Farm Ponds:</label>
    <input type="text" class="form-control" id="whsfarmponds" name="whsfarmponds" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Check Dams:</label>
    <input type="text" class="form-control" id="whscheckdams" name="whscheckdams" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Nallah Bunds:</label>
    <input type="text" class="form-control" id="whsnallahbunds" name="whsnallahbunds" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Percolation tanks:</label>
    <input type="text" class="form-control" id="whspercolationtanks" name="whspercolationtanks" onfocusin="numericOnly(event)">
  </div>
  
  <div class="form-group col-md-3 ">
    <label for="" class="">Ground Water recharge Structure:</label>
    <input type="text" class="form-control" id="whsgwrs" name="whsgwrs" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Gully Plugs:</label>
    <input type="text" class="form-control" id="whsgullyplugs" name="whsgullyplugs" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="whsothers" name="whsothers" onfocusin="numericOnly(event)">
  </div>
  
  </div>
  
  
  <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total Existing Area brought under Protective Irrigation(Command Area):</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Farm Ponds:</label>
    <input type="text" class="form-control" id="pifarmponds" name="pifarmponds" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Check Dams:</label>
    <input type="text" class="form-control" id="picheckdams" name="picheckdams" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Nallah Bunds:</label>
    <input type="text" class="form-control" id="pinallahbunds" name="pinallahbunds" onfocusin="numericOnly(event)">
  </div>
  
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="piothers" name="piothers" onfocusin="numericOnly(event)">
  </div>
  
  </div>
  
  <div class="form-row">
  <div class="form-group">
  <a href="#cropland" class="btn btn-info" data-toggle="tab">Back</a>
  <a href="#" class="btn btn-info" data-toggle="tab" id="whsnext">Next</a>
  </div>
  </div>
      </form>
    </div>
    
    <div id="household" class="container tab-pane fade"><br>
    <h3>In the Sanctioned Project Area</h3>
     
     <hr/>
      <form class="form" autocomplete="off">
     
      
      
      <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total No. of Household:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">SC:</label>
    <input type="text" class="form-control" id="sc" name="sc" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">ST:</label>
    <input type="text" class="form-control" id="st" name="st" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="houseOthers" name="houseOthers" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total:</label>
    <input type="text" class="form-control" id="houseTotal" name="houseTotal" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-10 ">
					<label><b>Population:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">SC:</label>
    <input type="text" class="form-control" id="scPopulation" name="scPopulation" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">ST:</label>
    <input type="text" class="form-control" id="stPopulation" name="stPopulation" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="houseOthersPopulation" name="houseOthersPopulation" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total:</label>
    <input type="text" class="form-control" id="houseTotalPopulation" name="houseOthersPopulation" onfocusin="numericOnly(event)">
  </div>
  </div>
  <div class="form-row">
  <div class="form-group col-md-10 ">
					<label><b>No. of Household:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Landless People:</label>
    <input type="text" class="form-control" id="householdLandlessPeople" name="householdLandlessPeople" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">BPL Household:</label>
    <input type="text" class="form-control" id="bplHousehold" name="bplHousehold" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Small Farmer`s:</label>
    <input type="text" class="form-control" id="smallFarmer" name="smallFarmerHousehold" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Marginal Farmer`s:</label>
    <input type="text" class="form-control" id="marginalFarmer" name="marginalFarmerHousehold" onfocusin="numericOnly(event)">
  </div>
  
  <div class="form-group col-md-10 ">
					<label><b>Population:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Landless People:</label>
    <input type="text" class="form-control" id="householdLandlessPeoplePopulation" name="householdLandlessPeoplePopulation" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">BPL Household:</label>
    <input type="text" class="form-control" id="bplHouseholdPopulation" name="bplHouseholdPopulation" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Small Farmer`s:</label>
    <input type="text" class="form-control" id="smallFarmerPopulation" name="smallFarmerHouseholdPopulation" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Marginal Farmer`s:</label>
    <input type="text" class="form-control" id="marginalFarmerPopulation" name="marginalFarmerHouseholdPopulation" onfocusin="numericOnly(event)">
  </div>
  </div>
  <div class="form-row">
  
  
  <div class="form-group col-md-4 ">
    <label for="" class="">No. of Person-Days of Seasonal Migration:</label>
    <input type="text" class="form-control" id="seasonalMigration" name="seasonalMigration" onfocusin="numericOnly(event)">
  </div>
  </div>
  <div class="form-row">
  <div class="form-group">
  <a href="#whs" class="btn btn-info" data-toggle="tab">Back</a>
  <a href="#" class="btn btn-info" data-toggle="tab" id="householdnext">Next</a>
  </div>
  </div>
      </form>
    </div>
    <div id="review" class="container tab-pane fade"><br>
     <h3>Review Filled Details</h3>
     <hr/>
      <form class="review" autocomplete="off">
      <div id="finalDiv">
<div class="form-row">
  <div class="form-group col-md-4">
    <label for="project" class="">Project </label>
    <input type="text" name="reviewProject" id="reviewProject" value="" class="form-control" readonly/>
    <input type="hidden" name="projectId" id="projectId" value="" class="form-control" readonly/>
  </div>
  <div class="form-group col-md-5">
    <label for="project" class="">Total existing Geographical Area of Project </label>
     <input type="text" name="totalGeoArea" id="totalGeoArea" value="" class="form-control" readonly/>
  </div>
  <div class="form-group col-md-3">
    <label for="project" class="">Project Area Covering </label>
      <input type="text" name="reviewProjectArea" id="reviewProjectArea" value="" class="form-control" readonly/>
      <input type="hidden" name="areaCoveringType" id="areaCoveringType" value="" class="form-control" readonly/>
  </div>
  </div>
  <div class="col-12 text-center"><br/><b>In the Sanctioned Project Area</b><hr/></div>
   <div class="form-row">
 <c:forEach items="${outHeadActivity}" var="act">
    
	<c:choose>
					<c:when test="${act.value.size() gt 0 }">
					<div class="form-group col-md-10 ">
					<label for="" class="">Total Existing   <c:out	value="${act.key.outcomeDesc }" /></label>
					</div>
					<c:forEach items="${act.value}" var="val">
					<div class="form-group col-md-3 ">
						<label for="" class="">   <c:out	value="${val.value }" /></label>
						<input type="text" readonly class="form-control" id="review_out_${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(val.value,')',''),'(',''),'/',''),'.',''),' ','')}_${act.key.outcomeId}_${val.key}" name="review_out_${fn:replace(val.value,' ','')}_${act.key.outcomeId }_${val.key }" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-4 ">
    <label for="" class="">Total Existing <c:out	value="${act.key.outcomeDesc }" /></label>
    <input type="text" readonly class="form-control" id="review_out_${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(act.key.outcomeDesc,' ',''),'.',''),'/',''),'(',''),')','')}_${act.key.outcomeId}_0" name="review_out_${fn:replace(fn:replace(fn:replace(fn:replace(act.key.outcomeDesc,' ',''),'/',''),'(',''),')','')}_${act.key.outcomeId}_0" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				
	</c:forEach>
	 <div class="form-group col-md-3 ">
    <label for="" class=""> Existing Gross Cropped Area</label>
    <input type="text" readonly class="form-control" id="totalGrossCroppedArea" name="totalGrossCroppedArea" onfocusin="numericOnly(event)">
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class=""> Existing Net Sown Area</label>
    <input type="text" readonly class="form-control" id="totalNetSownArea" name="totalNetSownArea" onfocusin="numericOnly(event)">
  </div>
	</div>
  
  <div class="form-row">
 
  </div>
  
  <c:forEach items="${phyHeadActivity1}" var="phyAct">
     <div class="form-row">
    
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class="">Total Existing   <c:out	value="${phyAct.key.headDesc }" /></label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class=""><c:out	value="${val.value }" /></label>
						<input type="text" readonly class="form-control" id="review_phy_${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(val.value,'(',''),')',''),'/',''),'.',''),' ','')}_${phyAct.key.headCode }_${val.key eq null ?0:val.key}" name="review_phy_${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
						 
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /></label>
    <input type="text" readonly class="form-control" id="review_phy_${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(phyAct.key.headDesc,')',''),'(',''),'/',''),'.',''),' ','')}_${phyAct.key.headCode }_0" name="review_phy_${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(phyAct.key.headDesc,')',''),'(',''),'/',''),'.',''),' ','')}_${phyAct.key.headCode}_0" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach>
	<%-- <c:forEach items="${phyHeadActivity2}" var="phyAct">
     <div class="form-row">
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class="">Total Existing   <c:out	value="${phyAct.key.headDesc }" /></label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class="">   <c:out	value="${val.value }" /></label>
						<input readonly type="text" class="form-control" id="review_phy_${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" name="review_phy_${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /></label>
    <input type="text" readonly class="form-control" id="review_phy_${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode }_" name="review_phy_${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode}_" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach> --%>
	
  <%-- <c:forEach items="${phyHeadActivity3}" var="phyAct">
     <div class="form-row">
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class="">Total Existing   <c:out	value="${phyAct.key.headDesc }" /></label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class="">No. of   <c:out	value="${val.value }" /></label>
						<input type="text" readonly class="form-control" id="review_phy_${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" name="review_phy_${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /></label>
    <input type="text" readonly class="form-control" id="review_phy_${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode }_" name="review_phy_${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode }_" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach>  --%>
	
	
      
      
      <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total Existing Water Harvesting Structure:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Farm Ponds:</label>
    <input type="text" class="form-control" id="review_whsfarmponds" name="review_whsfarmponds" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Check Dams:</label>
    <input type="text" class="form-control" id="review_whscheckdams" name="review_whscheckdams" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Nallah Bunds:</label>
    <input type="text" class="form-control" id="review_whsnallahbunds" name="review_whsnallahbunds" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Percolation tanks:</label>
    <input type="text" class="form-control" id="review_whspercolationtanks" name="review_whspercolationtanks" onfocusin="numericOnly(event)" readonly>
  </div>
  
  <div class="form-group col-md-3 ">
    <label for="" class="">Ground Water recharge Structure:</label>
    <input type="text" class="form-control" id="review_whsgwrs" name="review_whsgwrs" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Gully Plugs:</label>
    <input type="text" class="form-control" id="review_whsgullyplugs" name="review_whsgullyplugs" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="review_whsothers" name="review_whsothers" onfocusin="numericOnly(event)" readonly>
  </div>
  
  </div>
  
  
  <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total Existing Area brought under Protective Irrigation(Command Area):</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Farm Ponds:</label>
    <input type="text" class="form-control" id="review_pifarmponds" name="review_pifarmponds" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Check Dams:</label>
    <input type="text" class="form-control" id="review_picheckdams" name="review_picheckdams" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Nallah Bunds:</label>
    <input type="text" class="form-control" id="review_pinallahbunds" name="review_pinallahbunds" onfocusin="numericOnly(event)" readonly>
  </div>
  
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="review_piothers" name="review_piothers" onfocusin="numericOnly(event)" readonly>
  </div>
  
  </div>
  
  
  <div class="form-row">
 
       <div class="form-group col-md-10 ">
					<label><b>Total No. of Household:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">SC:</label>
    <input type="text" class="form-control" id="review_totalSc" name="totalSc" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">ST:</label>
    <input type="text" class="form-control" id="review_totalSt" name="totalSt" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="review_totalOthers" name="totalOthers" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total:</label>
    <input type="text" class="form-control" id="review_houseTotal" name="houseTotal" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-10 ">
					<label><b>Population:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">SC:</label>
    <input type="text" class="form-control" id="review_scPopulation" name="scPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">ST:</label>
    <input type="text" class="form-control" id="review_stPopulation" name="stPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="review_houseOthersPopulation" name="houseOthersPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total:</label>
    <input type="text" class="form-control" id="review_houseTotalPopulation" name="houseTotalPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  
  </div>
  <div class="form-row">
  <div class="form-group col-md-10 ">
					<label><b>No. of Household:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Landless People:</label>
    <input type="text" class="form-control" id="review_householdLandlessPeople" name="householdLandlessPeople" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">BPL Household:</label>
    <input type="text" class="form-control" id="review_bplHousehold" name="bplHousehold" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Small Farmer`s:</label>
    <input type="text" class="form-control" id="review_smallFarmer" name="smallFarmerHousehold" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Marginal Farmer`s:</label>
    <input type="text" class="form-control" id="review_marginalFarmer" name="marginalFarmerHousehold" onfocusin="numericOnly(event)" readonly>
  </div>
  
  <div class="form-group col-md-10 ">
					<label><b>Population:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Landless People:</label>
    <input type="text" class="form-control" id="review_householdLandlessPeoplePopulation" name="householdLandlessPeoplePopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">BPL Household:</label>
    <input type="text" class="form-control" id="review_bplHouseholdPopulation" name="bplHouseholdPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Small Farmer`s:</label>
    <input type="text" class="form-control" id="review_smallFarmerPopulation" name="smallFarmerHouseholdPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Marginal Farmer`s:</label>
    <input type="text" class="form-control" id="review_marginalFarmerPopulation" name="marginalFarmerHouseholdPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  </div>
  <div class="form-row">
  
  <div class="form-group col-md-4 ">
    <label for="" class="">No. of Person-Days of Seasonal Migration</label>
    <input type="text" readonly class="form-control" id="review_personDaysMigration" name="personDaysMigration" onfocusin="numericOnly(event)">
  </div>
  </div>
  
  
  <div class="form-group col text-center">
  <!--  <button  class="btn btn-info" id="btnDraft" name="btnDraft">Save as Draft</button>&nbsp;&nbsp; -->
   <button  class="btn btn-info" id="btnComplete" name="btnComplete">Complete</button> 
    <a href="#projectBasic" class="btn btn-info" id="btnEdit" data-toggle="tab">Edit</a>
  </div>
  </div>
</form>
    </div>
    <!-- ------------------------------- Completed BLS Form -------------------------------------- -->
    <div class="complete d-none">
  <h3>In the Sanctioned Project Area</h3>
     <hr/>
     <div id="outHeadActivity2">
     <div class="form-row">
     <c:forEach items="${outHeadActivity}" var="act">
     
	<c:choose>
					<c:when test="${act.value.size() gt 0 }">
					<div class="form-group col-md-10 ">
					<label for="" class=""><b>Total Existing <c:out	value="${act.key.outcomeDesc }" /></b> </label>
					</div>
					<c:forEach items="${act.value}" var="val">
					<div class="form-group col-md-3 ">
						<label for="" class=""> <c:out	value="${val.value }" /> </label>
						<input type="text" class="form-control" readonly onfocusin="decimalCheck(event)" id="${fn:replace(fn:replace(val.value,' ',''),'/','')}_${act.key.outcomeId}_${val.key}C" name="${fn:replace(fn:replace(val.value,' ',''),'/','')}_${act.key.outcomeId }_${val.key }C" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-4 ">
    <label for="" class="">Total Existing  <c:out	value="${act.key.outcomeDesc }" />.&nbsp; </label>
    <input type="text" class="form-control" readonly onfocusin="decimalCheck(event)" id="${fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(act.key.outcomeDesc,' ',''),'.',''),'/',''),'(',''),')','')}_${act.key.outcomeId}_0C" name="${fn:replace(fn:replace(fn:replace(fn:replace(act.key.outcomeDesc,' ',''),'/',''),'(',''),')','')}_${act.key.outcomeId}_0" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				
	</c:forEach> 
	</div>
	<div class="form-row">
  <div class="form-group col-md-4 ">
    <label for="" class="">Total Existing Gross Cropped Area</label>
    <input type="text" class="form-control" readonly id="grossCroppedAreaC" name="grossCroppedArea" onfocusin="decimalCheck(event)">
  </div>
  <div class="form-group col-md-4 ">
    <label for="" class="">Total Existing Net Sown Area</label>
    <input type="text" class="form-control" readonly id="netSownAreaC" name="netSownArea" onfocusin="decimalCheck(event)">
  </div>
  </div>
  </div>
  
  <div id="cropland" ><br>
  
  <div id="phyHeadActivity12">
  <c:forEach items="${phyHeadActivity1}" var="phyAct">
     <div class="form-row">
	<c:choose>
					<c:when test="${phyAct.value.size() gt 0 }">
					<div class="form-group col-md-9 ">
					<label for="" class=""><b>Total Existing   <c:out	value="${phyAct.key.headDesc }" /> </b></label>
					</div>
					<c:forEach items="${phyAct.value}" var="val">
					<div class="form-group col-md-4 ">
						<label for="" class="">   <c:out	value="${val.value }" /> </label>
						<input type="text" class="form-control" readonly onfocusin="decimalCheck(event)" id="${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}C" name="${fn:replace(val.value,' ','')}_${phyAct.key.headCode }_${val.key}C" onfocusin="numericOnly(event)">
					</div>	
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="form-group col-md-8 ">
    <label for="" class="">Total Existing <c:out	value="${phyAct.key.headDesc }" /></label>
    <input type="text" class="form-control" readonly onfocusin="decimalCheck(event)" id="${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode }_0C" name="${fn:replace(act.key.headDesc,' ','')}_${phyAct.key.headCode}_0" onfocusin="numericOnly(event)">
  </div>
					</c:otherwise>
				</c:choose>
				</div>
	</c:forEach> 
	</div>
 
  </div>
  
  <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total Existing Water Harvesting Structure:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Farm Ponds:</label>
    <input type="text" class="form-control" id="whsfarmpondsC" name="review_whsfarmponds" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Check Dams:</label>
    <input type="text" class="form-control" id="whscheckdamsC" name="review_whscheckdams" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Nallah Bunds:</label>
    <input type="text" class="form-control" id="whsnallahbundsC" name="review_whsnallahbunds" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Percolation tanks:</label>
    <input type="text" class="form-control" id="whspercolationtanksC" name="review_whspercolationtanks" onfocusin="numericOnly(event)" readonly>
  </div>
  
  <div class="form-group col-md-3 ">
    <label for="" class="">Ground Water recharge Structure:</label>
    <input type="text" class="form-control" id="whsgwrsC" name="review_whsgwrs" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Gully Plugs:</label>
    <input type="text" class="form-control" id="whsgullyplugsC" name="review_whsgullyplugs" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="whsothersC" name="review_whsothers" onfocusin="numericOnly(event)" readonly>
  </div>
  
  </div>
  
  <div class="form-row">
       <div class="form-group col-md-10 ">
					<label><b>Total Existing Area brought under Protective Irrigation(Command Area):</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Farm Ponds:</label>
    <input type="text" class="form-control" id="pifarmpondsC" name="review_pifarmponds" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Check Dams:</label>
    <input type="text" class="form-control" id="picheckdamsC" name="review_picheckdams" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Nallah Bunds:</label>
    <input type="text" class="form-control" id="pinallahbundsC" name="review_pinallahbunds" onfocusin="numericOnly(event)" readonly>
  </div>
  
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="piothersC" name="review_piothers" onfocusin="numericOnly(event)" readonly>
  </div>
  
  </div>
  
  
  <div class="form-row">
 
       <div class="form-group col-md-10 ">
					<label><b>Total No. of Household:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">SC:</label>
    <input type="text" class="form-control" id="totalScC" name="totalSc" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">ST:</label>
    <input type="text" class="form-control" id="totalStC" name="totalSt" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="totalOthersC" name="totalOthers" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total:</label>
    <input type="text" class="form-control" id="houseTotalC" name="houseTotal" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-10 ">
					<label><b>Population:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">SC:</label>
    <input type="text" class="form-control" id="scPopulationC" name="scPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">ST:</label>
    <input type="text" class="form-control" id="stPopulationC" name="stPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Others:</label>
    <input type="text" class="form-control" id="houseOthersPopulationC" name="houseOthersPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Total:</label>
    <input type="text" class="form-control" id="houseTotalPopulationC" name="houseTotalPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  
  </div>
  
  <div class="form-row">
  <div class="form-group col-md-10 ">
					<label><b>No. of Household:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Landless People:</label>
    <input type="text" class="form-control" id="householdLandlessPeopleC" name="householdLandlessPeople" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">BPL Household:</label>
    <input type="text" class="form-control" id="bplHouseholdC" name="bplHousehold" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Small Farmer`s:</label>
    <input type="text" class="form-control" id="smallFarmerC" name="smallFarmerHousehold" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Marginal Farmer`s:</label>
    <input type="text" class="form-control" id="marginalFarmerC" name="marginalFarmerHousehold" onfocusin="numericOnly(event)" readonly>
  </div>
  
  <div class="form-group col-md-10 ">
					<label><b>Population:</b></label>
					</div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Landless People:</label>
    <input type="text" class="form-control" id="householdLandlessPeoplePopulationC" name="householdLandlessPeoplePopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">BPL Household:</label>
    <input type="text" class="form-control" id="bplHouseholdPopulationC" name="bplHouseholdPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Small Farmer`s:</label>
    <input type="text" class="form-control" id="smallFarmerPopulationC" name="smallFarmerHouseholdPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  <div class="form-group col-md-3 ">
    <label for="" class="">Marginal Farmer`s:</label>
    <input type="text" class="form-control" id="marginalFarmerPopulationC" name="marginalFarmerHouseholdPopulation" onfocusin="numericOnly(event)" readonly>
  </div>
  </div>
  <div class="form-row">
  
  <div class="form-group col-md-4 ">
    <label for="" class="">No. of Person-Days of Seasonal Migration</label>
    <input type="text" readonly class="form-control" id="personDaysMigrationC" name="personDaysMigration" onfocusin="numericOnly(event)">
  </div>
  </div>

</div>

  </div>
</div>
</div>
</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/baselinesurvey.js" />'></script>
</body>
</html>