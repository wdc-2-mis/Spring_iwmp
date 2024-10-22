/**
 * 
 */

$(document).ready(function(){
$project='';
$geoArea="";
$projectArea="";
$degradedland='';
$wasteland="";
$forestwasteland="";
$cerealsDCrops="";
$pulsesDCrops="";
$oilseedsDCrops="";
$fibreDCrops="";
$othersDCrops="";
//alert($('.btn').html());

/********************************************** Project Onchange ************************************************************ */
$(document).on( 'change', '#project', function (e) {
	$projId = $(this).val();
	e.preventDefault();
	if($projId!='')
$.ajax({  
            url:"getSanctionedArea",
            type: "post", 
			data:{projId:$projId}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			$('#sancArea').val(data);
			}
			
		});
		$msg="";
		if($projId!=''){
		$.ajax({  
            url:"getPreFilledData",
            type: "post", 
			data:{projId:$projId}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			//$('#sancArea').val(data);
			
			if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(data[key].status==='D'){
								$('.form').removeClass('d-none');
								$('.complete').addClass('d-none');
								$('#geoarea').prop('readonly', false);
								$('#groupType').prop('readonly', false);
								if(data[key].userType==='PI'){
								$('#btnEdit').html('Back');
								}else if(data[key].userType==='SL'){
									$('#btnComplete').show();
									$('#btnEdit').show();
								}
							$('#geoarea').val(data[key].total_geo_area);
							$('#groupType').val(data[key].area_covering_type);
							$('#grossCroppedArea').val(data[key].total_gross_cropped_area);
							$('#netSownArea').val(data[key].total_net_sown_area);
							$('#sc').val(data[key].total_sc);
							$('#st').val(data[key].total_st);
							$('#houseOthers').val(data[key].total_others);
							$('#population').val(data[key].total_population);
							$('#householdLandlessPeople').val(data[key].no_of_landless_household);
							$('#bplHousehold').val(data[key].no_of_bpl_household);
							$('#smallFarmer').val(data[key].small_farmer_household);
							$('#marginalFarmer').val(data[key].marginal_farmer_household);
							$('#seasonalMigration').val(data[key].person_days_migration);
							
							$('#whsfarmponds').val(data[key].whs_farm_pond);
							$('#whscheckdams').val(data[key].whs_check_dam);
							$('#whsnallahbunds').val(data[key].whs_nallah_bund);
							$('#whspercolationtanks').val(data[key].whs_percolation_tank);
							$('#whsgwrs').val(data[key].whs_gwrs);
							$('#whsgullyplugs').val(data[key].whs_gully_plug);
							$('#whsothers').val(data[key].whs_other);
							$('#pifarmponds').val(data[key].pi_farm_pond);
							$('#picheckdams').val(data[key].pi_check_dam);
							$('#pinallahbunds').val(data[key].pi_nallah_bund);
							$('#piothers').val(data[key].pi_other);
							$('#houseTotal').val(data[key].household_total);
							$('#scPopulation').val(data[key].population_sc);
							$('#stPopulation').val(data[key].population_st);
							$('#houseOthersPopulation').val(data[key].population_other);
							$('#houseTotalPopulation').val(data[key].population_total);
							$('#householdLandlessPeoplePopulation').val(data[key].population_landless_people);
							$('#bplHouseholdPopulation').val(data[key].population_bpl);
							$('#smallFarmerPopulation').val(data[key].population_small_farmers);
							$('#marginalFarmerPopulation').val(data[key].population_marginal_farmers);
							
							
							
							if(typeof data[key].outcome_detail_id != 'undefined'){
							$('#'+data[key].outcome_detail_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].outcome_id+'_'+data[key].outcome_detail_id).val(data[key].area_of_activity);
							//console.log(data[key].outcome_detail_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].outcome_id+'_'+data[key].outcome_detail_id);
							}else if(typeof data[key].outcome_id != 'undefined'){
							$('#'+data[key].outcome_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','').replaceAll('.','')+'_'+data[key].outcome_id+'_0').val(data[key].area_of_activity);	
							console.log(data[key].outcome_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','').replaceAll('.','')+'_'+data[key].outcome_id+'_0');
							}else if(typeof data[key].phy_activity !='undefined'){
								$('#'+data[key].activity_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].phy_head+'_'+data[key].phy_activity).val(data[key].area_of_activity);
							//console.log(data[key].activity_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].phy_head+'_'+data[key].phy_activity);
							}else if(typeof data[key].phy_head !='undefined' && typeof data[key].phy_activity ==='undefined'){
								$('#'+data[key].head_desc.replaceAll(' ','').replace('.','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].phy_head+'_0').val(data[key].area_of_activity);
							console.log('#'+data[key].head_desc.replaceAll(' ','').replace('.','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].phy_head+'_0');
							}
							
							}else if(data[key].status==='C'){
								$msg="Data for the selected project "+$('#project option:selected').text()+" has been already completed !";
								$('.form').addClass('d-none');
								$('.complete').removeClass('d-none');
								$('#geoarea').prop('readonly', true);
								$('#groupType').prop('readonly', true);
								
							$('#geoarea').val(data[key].total_geo_area);
							$('#groupType').val(data[key].area_covering_type);
							$('#grossCroppedAreaC').val(data[key].total_gross_cropped_area);
							$('#netSownAreaC').val(data[key].total_net_sown_area);
							$('#totalScC').val(data[key].total_sc);
							$('#totalStC').val(data[key].total_st);
							$('#totalOthersC').val(data[key].total_others);
							
							$('#populationC').val(data[key].total_population);
							$('#householdLandlessPeopleC').val(data[key].no_of_landless_household);
							$('#bplHouseholdC').val(data[key].no_of_bpl_household);
							$('#smallFarmerC').val(data[key].small_farmer_household);
							$('#marginalFarmerC').val(data[key].marginal_farmer_household);
							$('#personDaysMigrationC').val(data[key].person_days_migration);
							//$('#projectId').val(data[key].proj_id);
							
							$('#whsfarmpondsC').val(data[key].whs_farm_pond);
							$('#whscheckdamsC').val(data[key].whs_check_dam);
							$('#whsnallahbundsC').val(data[key].whs_nallah_bund);
							$('#whspercolationtanksC').val(data[key].whs_percolation_tank);
							$('#whsgwrsC').val(data[key].whs_gwrs);
							$('#whsgullyplugsC').val(data[key].whs_gully_plug);
							$('#whsothersC').val(data[key].whs_other);
							$('#pifarmpondsC').val(data[key].pi_farm_pond);
							$('#picheckdamsC').val(data[key].pi_check_dam);
							$('#pinallahbundsC').val(data[key].pi_nallah_bund);
							$('#piothersC').val(data[key].pi_other);
							$('#houseTotalC').val(data[key].household_total);
							$('#scPopulationC').val(data[key].population_sc);
							$('#stPopulationC').val(data[key].population_st);
							$('#houseOthersPopulationC').val(data[key].population_other);
							$('#houseTotalPopulationC').val(data[key].population_total);
							$('#householdLandlessPeoplePopulationC').val(data[key].population_landless_people);
							$('#bplHouseholdPopulationC').val(data[key].population_bpl);
							$('#smallFarmerPopulationC').val(data[key].population_small_farmers);
							$('#marginalFarmerPopulationC').val(data[key].population_marginal_farmers);
							
							
							if(typeof data[key].outcome_detail_id != 'undefined'){
							$('#'+data[key].outcome_detail_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].outcome_id+'_'+data[key].outcome_detail_id+'C').val(data[key].area_of_activity);
							//console.log(data[key].outcome_detail_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].outcome_id+'_'+data[key].outcome_detail_id+'C');
							}else if(typeof data[key].outcome_id != 'undefined'){
							$('#'+data[key].outcome_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','').replaceAll('.','')+'_'+data[key].outcome_id+'_0C').val(data[key].area_of_activity);	
							//console.log(data[key].outcome_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].outcome_id+'_0');
							}else if(typeof data[key].phy_head !='undefined'){
								$('#'+data[key].activity_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].phy_head+'_'+data[key].phy_activity+'C').val(data[key].area_of_activity);
							console.log(data[key].activity_desc.replaceAll(' ','').replace('(','').replace(')','').replaceAll('/','')+'_'+data[key].phy_head+'_'+data[key].phy_activity);
							}
							}else{
								$('.form').removeClass('d-none');
								$('.complete').addClass('d-none');
								$('#geoarea').prop('readonly', false);
								$('#groupType').prop('readonly', false);
							}
							}
							}
							}else{
								/*$('.form').each(function(){
									$(this).reset();
								});*/
								$('.form').trigger("reset");
								$('#geoarea').val("");
								$('#groupType').val("");
								$('.form').removeClass('d-none');
								$('.complete').addClass('d-none');
								$('#geoarea').prop('readonly', false);
								$('#groupType').prop('readonly', false);
							}
							$('#errorMessage').html($msg);
			}
			
		});
		}else{
			$('.form').trigger("reset");
			$('#geoarea').val("");
			$('#groupType').val("");
			$('#sancArea').val("");
			$('.form').removeClass('d-none');
			$('.complete').addClass('d-none');
			$('#geoarea').prop('readonly', false);
			$('#groupType').prop('readonly', false);
			$('#errorMessage').html("");
		}
		
	
	});
/****************************************** Next Button CLick Script ****************************************************** */
$(document).on( 'click', '#next', function (e) {
e.preventDefault();
$outflag=false;

if($('#project option:selected').val()==''){console.log('a'+$('#project option:selected').val()+'b');
	$('#errorMessage').html('Required Field !');
	$('#project').focus();
	return false;
	}else{
	$('#reviewProject').val($('#project option:selected').text());
	$('#projectId').val($('#project option:selected').val());
	}
	
	if($('#geoarea').val()!='')
	$('#totalGeoArea').val($('#geoarea').val());
	else{
		$('#errorMessage').html('Required Field !');
	$('#geoarea').focus();
	return false;
	}
	
	if($('#groupType option:selected').val()==''){
		$('#errorMessage').html('Required Field !');
	$('#groupType').focus();
	return false;
	}else{
		$('#reviewProjectArea').val($('#groupType option:selected').text());
	$('#areaCoveringType').val($('#groupType option:selected').val());
	}
	
	$('#outHeadActivity input').each(function (e) {
	if(this.value===''){
	$('#errorMessage').html('Required Field !');
	$('#'+this.id).focus();
	$outflag=false;
	return false;
}else{
$('#review_out_'+this.id).val(this.value);
$outflag=true;
}
});
	if($('#grossCroppedArea').val()!='')
	$('#totalGrossCroppedArea').val($('#grossCroppedArea').val());
	else{
	$('#errorMessage').html('Required Field !');
	$('#grossCroppedArea').focus();	
	return false;
	}
	
	if($('#netSownArea').val()!='')
	$('#totalNetSownArea').val($('#netSownArea').val());
	else{
	$('#errorMessage').html('Required Field !');
	$('#netSownArea').focus();	
	return false;
	}
	


if($outflag){
	$('#errorMessage').html('');
	$('#projectBasic').removeClass('show active');
	$('#cropland').addClass('show active');
}
});

$(document).on( 'click', '#phynext1', function (e) {
e.preventDefault();
$phyflag1=false;

$('#phyHeadActivity1 input').each(function () {
	if(this.value!=''){
$('#review_phy_'+this.id).val(this.value);
$phyflag1=true;
}else{
	$('#errorMessage').html('Required Field !');
	$('#'+this.id).focus();
	$phyflag1=false;
	return false;
}
});

if($phyflag1){
	$('#errorMessage').html('');
	$('#cropland').removeClass('show active');
	$('#whs').addClass('show active');
}

});

$(document).on( 'click', '#phynext2', function (e) {
e.preventDefault();
$phyflag2=false;

$('#phyHeadActivity2 input').each(function () {
	if(this.value!=''){
$('#review_phy_'+this.id).val(this.value);
$phyflag2=true;
}else{
	$('#errorMessage').html('Required Field !');
	$('#'+this.id).focus();
	$phyflag2=false;
	return false;
}
});

if($phyflag2){
	$('#errorMessage').html('');
	$('#horticulture').removeClass('show active');
	$('#protectiveIrrigation').addClass('show active');
}

});


$(document).on( 'click', '#phynext3', function (e) {
e.preventDefault();
$phyflag3=false;

$('#phyHeadActivity3 input').each(function () {
	if(this.value!=''){
$('#review_phy_'+this.id).val(this.value);
$phyflag3=true;
}else{
	$('#errorMessage').html('Required Field !');
	$('#'+this.id).focus();
	$phyflag3=false;
	return false;
}
});

console.log($phyflag3);
if($phyflag3){
	$('#errorMessage').html('');
	$('#protectiveIrrigation').removeClass('show active');
	$('#household').addClass('show active');
}

});

$(document).on( 'click', '#whsnext', function (e) {
e.preventDefault();
$whsflag=false;
if($('#whsfarmponds').val()!=''){
	$('#review_whsfarmponds').val($('#whsfarmponds').val());
	$whsflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whsfarmponds').focus();
	$whsflag=false;
	return false;
	}
	
if($('#whscheckdams').val()!=''){
	$('#review_whscheckdams').val($('#whscheckdams').val());
	$whsflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whscheckdams').focus();
	$whsflag=false;
	return false;
	}
	
if($('#whsnallahbunds').val()!=''){
	$whsflag=true;
	$('#review_whsnallahbunds').val($('#whsnallahbunds').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whsnallahbunds').focus();
	$whsflag=false;
	return false;
	}	
	
	if($('#whspercolationtanks').val()!=''){
	$whsflag=true;
	$('#review_whspercolationtanks').val($('#whspercolationtanks').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whspercolationtanks').focus();
	$whsflag=false;
	return false;
	}	
	
	if($('#whsgwrs').val()!=''){
	$whsflag=true;
	$('#review_whsgwrs').val($('#whsgwrs').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whsgwrs').focus();
	$whsflag=false;
	return false;
	}	
	
	if($('#whsgullyplugs').val()!=''){
	$whsflag=true;
	$('#review_whsgullyplugs').val($('#whsgullyplugs').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whsgullyplugs').focus();
	$whsflag=false;
	return false;
	}
	
	if($('#whsothers').val()!=''){
	$whsflag=true;
	$('#review_whsothers').val($('#whsothers').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#whsothers').focus();
	$whsflag=false;
	return false;
	}	
	
	if($('#pifarmponds').val()!=''){
	$whsflag=true;
	$('#review_pifarmponds').val($('#pifarmponds').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#pifarmponds').focus();
	$whsflag=false;
	return false;
	}	

if($('#picheckdams').val()!=''){
	$('#review_picheckdams').val($('#picheckdams').val());
	$whsflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#picheckdams').focus();
	$whsflag=false;
	return false;
	}	
	
if($('#pinallahbunds').val()!=''){
	$('#review_pinallahbunds').val($('#pinallahbunds').val());
	$whsflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#pinallahbunds').focus();
	$whsflag=false;
	return false;
	}

if($('#piothers').val()!=''){
	$('#review_piothers').val($('#piothers').val());
	$whsflag=true;
	}
	else{
		$('#errorMessage').html('Required Field !');
	$('#piothers').focus();
	$whsflag=false;
	return false;
	}	

	
	if($whsflag){
		$('#errorMessage').html('');
	$('#household').addClass('show active');
	$('#whs').removeClass('show active');
}
	
});

$(document).on( 'click', '#householdnext', function (e) {
e.preventDefault();
$houseflag=false;
if($('#sc').val()!=''){
	$('#review_totalSc').val($('#sc').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#sc').focus();
	$houseflag=false;
	return false;
	}
	
if($('#st').val()!=''){
	$('#review_totalSt').val($('#st').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#st').focus();
	$houseflag=false;
	return false;
	}
	
if($('#houseOthers').val()!=''){
	$houseflag=true;
	$('#review_totalOthers').val($('#houseOthers').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#houseOthers').focus();
	$houseflag=false;
	return false;
	}	
	
	if($('#houseTotal').val()!=''){
	$houseflag=true;
	$('#review_houseTotal').val($('#houseTotal').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#houseTotal').focus();
	$houseflag=false;
	return false;
	}	
	
	if($('#scPopulation').val()!=''){
	$houseflag=true;
	$('#review_scPopulation').val($('#scPopulation').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#scPopulation').focus();
	$houseflag=false;
	return false;
	}	
	
	if($('#stPopulation').val()!=''){
	$houseflag=true;
	$('#review_stPopulation').val($('#stPopulation').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#stPopulation').focus();
	$houseflag=false;
	return false;
	}
	
	if($('#houseOthersPopulation').val()!=''){
	$houseflag=true;
	$('#review_houseOthersPopulation').val($('#houseOthersPopulation').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#houseOthersPopulation').focus();
	$houseflag=false;
	return false;
	}	
	
	if($('#houseTotalPopulation').val()!=''){
	$houseflag=true;
	$('#review_houseTotalPopulation').val($('#houseTotalPopulation').val());
	}else{
		$('#errorMessage').html('Required Field !');
	$('#houseTotalPopulation').focus();
	$houseflag=false;
	return false;
	}	

/*if($('#population').val()!=''){
	$('#totalPopulation').val($('#population').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#population').focus();
	$houseflag=false;
	return false;
	}	*/
	
if($('#householdLandlessPeople').val()!=''){
	$('#review_householdLandlessPeople').val($('#householdLandlessPeople').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#householdLandlessPeople').focus();
	$houseflag=false;
	return false;
	}	
	
if($('#bplHousehold').val()!=''){
	$('#review_bplHousehold').val($('#bplHousehold').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#bplHousehold').focus();
	$houseflag=false;
	return false;
	}

if($('#smallFarmer').val()!=''){
	$('#review_smallFarmer').val($('#smallFarmer').val());
	$houseflag=true;
	}
	else{
		$('#errorMessage').html('Required Field !');
	$('#smallFarmer').focus();
	$houseflag=false;
	return false;
	}	

if($('#marginalFarmer').val()!=''){
	$('#review_marginalFarmer').val($('#marginalFarmer').val());
	$houseflag=true;
	}
	else{
		$('#errorMessage').html('Required Field !');
	$('#marginalFarmer').focus();
	$houseflag=false;
	return false;
	}
	
	if($('#householdLandlessPeoplePopulation').val()!=''){
	$('#review_householdLandlessPeoplePopulation').val($('#householdLandlessPeoplePopulation').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#householdLandlessPeoplePopulation').focus();
	$houseflag=false;
	return false;
	}	
	
if($('#bplHousehold').val()!=''){
	$('#review_bplHouseholdPopulation').val($('#bplHouseholdPopulation').val());
	$houseflag=true;
	}else{
		$('#errorMessage').html('Required Field !');
	$('#bplHouseholdPopulation').focus();
	$houseflag=false;
	return false;
	}

if($('#smallFarmerPopulation').val()!=''){
	$('#review_smallFarmerPopulation').val($('#smallFarmerPopulation').val());
	$houseflag=true;
	}
	else{
		$('#errorMessage').html('Required Field !');
	$('#smallFarmerPopulation').focus();
	$houseflag=false;
	return false;
	}	

if($('#marginalFarmerPopulation').val()!=''){
	$('#review_marginalFarmerPopulation').val($('#marginalFarmerPopulation').val());
	$houseflag=true;
	}
	else{
		$('#errorMessage').html('Required Field !');
	$('#marginalFarmerPopulation').focus();
	$houseflag=false;
	return false;
	}	
	
if($('#seasonalMigration').val()!=''){
	$('#review_personDaysMigration').val($('#seasonalMigration').val());
	$houseflag=true;
	}
	else{
		$('#errorMessage').html('Required Field !');
	$('#seasonalMigration').focus();
	$houseflag=false;
	return false;
	}	
	if($houseflag){
		$('#errorMessage').html('');
	$('#review').addClass('show active');
	$('#household').removeClass('show active');
}
	
});
/****************************************** Next Button CLick Script End****************************************************** */

/****************************************** Save as Draft Button CLick Script ****************************************************** */
$(document).on( 'click', '#btnDraft', function (e) {
	e.preventDefault();
$.ajax({  
            url:"saveAsDraftBaseLineSurvey",
            type: "post", 
			data:{formData:JSON.stringify($('.review').serialize())}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			if(data==='success'){
				successAlert('Data has been saved successfully !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href="";
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			window.location.href="";
			});
			}else{
				successAlert('There is some error. Please try again !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			//window.location.href="";
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			//window.location.href="";
			});
			}
			}
			
			});
	
});
/****************************************** Save Button CLick Script End****************************************************** */

/****************************************** Complete Button CLick Script ****************************************************** */
$(document).on( 'click', '#btnComplete', function (e) {
	e.preventDefault();
	$projId=$('#project').val();
$.ajax({  
            url:"completeBaseLineSurvey",
            type: "post", 
			data:{projId:$projId}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			if(data==='success'){
				successAlert('Base Line Survey has been completed successfully !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href="";
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			window.location.href="";
			});
			}else{
				successAlert('There is some error. Please try again !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			//window.location.href="";
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			//window.location.href="";
			});
			}
			}
			
			});
	
});
/****************************************** Complete Button CLick Script End****************************************************** */

/****************************************** On Blur Script ****************************************************** */
$(document).on( 'blur', '#geoarea', function (e) {
	e.preventDefault();
	$sancArea=$('#sancArea').val();
	$geoarea = $(this).val();
	
	if(parseInt($sancArea)<=parseInt($geoarea)){
		$('#errorMessage').html("");
	}else{
		$('#errorMessage').html("Geographical area must be greater than or equal to sanctioned area of project.");
		$('#geoarea').focus();
	}
	
	});
	
	
/***************************************** On Blur Script End *************************************************** */	

/***************************************** Report Section Script Start *************************************************** */

/***************************************** State Change Script Start *************************************************** */
$(document).on( 'change', '#state', function (e) {
	e.preventDefault();
	$stCode = $(this).val();
	$.ajax({  
            url:"getDistrictByStateCode",
            type: "post", 
			data:{id:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			$ddlDistrict = $('#district');
			if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							$ddlDistrict.append("<option value='"+key+"'>"+data[key]+"</option>")
							}
						}
			}
			}
		});
	
});
/***************************************** State Change Script End *************************************************** */	


/***************************************** District Level Project Click Script Start *************************************************** */
$(document).on( 'click', 'a.project', function (e) {
	e.preventDefault();
	$projId = e.target.getAttribute('data-id');
	$('<form action="blsreportproject" method="post"><input type="hidden" id="value" name="value" value="'+$projId+'" /></form>').appendTo('body').submit();
});
/***************************************** District Level Project Click Script End *************************************************** */

/***************************************** State Level Dist Name Click Script Start *************************************************** */
$(document).on( 'click', 'a.distname', function (e) {
	e.preventDefault();
	$projId = e.target.getAttribute('data-id');
	$('<form action="blsreportwcdc" method="post"><input type="hidden" id="value" name="value" value="'+$projId+'" /></form>').appendTo('body').submit();
});
/***************************************** State Level Dist Name Click Script End *************************************************** */

});

/***************************************** Page Load Script End *************************************************** */

function checkValue(val){
	$geoarea=$('#geoarea').val();
	$area = $('#'+val.id).val();
	
	if(parseInt($geoarea)<=parseInt($area)){
		$('#errorMessage').html("Given area can not be greater than the Geographical area.");
		$('#'+val.id).focus();
		$('#next').prop('disabled', true);
	}else{
		$('#errorMessage').html("");
		$('#next').prop('disabled', false);
	}
}

function getNetSownArea(){
	$nil=0;
	$double=0;
	$multi=0;
	
	if($('#AreabroughtfromNilto1crop_5_7').val()!='')
	$nil=parseInt($('#AreabroughtfromNilto1crop_5_7').val());
	
	if($('#Areabroughtfrom1todoublecrops_5_8').val()!='')
	$double=parseInt($('#Areabroughtfrom1todoublecrops_5_8').val());
	
	if($('#Areabroughtfromdoubletomulticrops_5_9').val()!='')
	$multi=parseInt($('#Areabroughtfromdoubletomulticrops_5_9').val());
	
$('#netSownArea').val($nil+$double+$multi);
//$('#netSownArea').val(00);
}


/***************************************************** PDF Button Click ************************************* */
	function exportPDF(){
	var table="";
	var reportName = "Format SB1- Project-wise Details of BaseLine Survey";
	var tableName = "#tblReport";
	var columnSpan =false;
	var colLength=$(tableName+" tr:nth-child(1) th").length;
	var specialNote = true;
	var tbodyRowCount=0;
	var note="";
	var pageorientation = 'portrait';
	var pageSize= 'A4';
	if(specialNote)
		tbodyRowCount=$(tableName+" tbody tr").length+1;
	
	//Comment below 2(two) lines if you want to put custom note here or change it accordingly.
	/*if(typeof $('.text-danger').html() != "undefined")
		note=$('.text-danger').html();*/
		note="* All area in hactare";
		
	
	
	var td="";
	for(var i=1;i<colLength;i++){
		td=td+"<td></td>";
	}
 	
	$(tableName+' tbody tr:last').after('<tr><td width="" class="text-danger">'+note+'</td>'+td+'</tr>');

var headerHtml =" State: "+$('#state option:selected').text().trim()+" District: "+$('#district option:selected').text();

	if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
		createDataTable(reportName,tableName,columnSpan,colLength,tbodyRowCount,headerHtml,pageorientation,pageSize);
		}
	//createDataTable(headerTitle,reportName,tableName);
	//trigger pdf button of datatable
	table=$(tableName).DataTable().buttons(0,1).trigger();
	table.buttons('.buttons-pdf').nodes().css("display", "none");
	table.buttons('.buttons-excel').nodes().css("display", "none");
	$(tableName).css("width","100%");
	$(tableName).removeClass('dataTable');
	$('#tblReport tbody tr:last').remove();
}

function exportPDFWCDC(){
	var table="";
	var reportName = "Format SB1- Detail of Baseline Survey District wise of WDC-PMKSY 2.0";
	var tableName = "#tblReport";
	var columnSpan =true;
	var colLength=$(tableName+" tr:nth-child(2) th").length;
	var specialNote = true;
	var tbodyRowCount=0;
	var note="";
	var pageorientation = 'landscape';
	var pageSize= 'A4';

	if(specialNote)
		tbodyRowCount=$(tableName+" tbody tr").length+1;
	
	//Comment below 2(two) lines if you want to put custom note here or change it accordingly.
	/*if(typeof $('.text-danger').html() != "undefined")
		note=$('.text-danger').html();*/
		note="* All area in hactare";
		
	
	
	var td="";
	for(var i=1;i<colLength;i++){
		td=td+"<td></td>";
	}
 	
	$(tableName+' tbody tr:last').after('<tr><td width="" class="text-danger text-center">'+note+'</td>'+td+'</tr>');

var headerHtml ="";

	if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
		createDataTable(reportName,tableName,columnSpan,colLength,tbodyRowCount,headerHtml,pageorientation,pageSize);
		}
	//createDataTable(headerTitle,reportName,tableName);
	//trigger pdf button of datatable
	table=$(tableName).DataTable().buttons(0,1).trigger();
	table.buttons('.buttons-pdf').nodes().css("display", "none");
	table.buttons('.buttons-excel').nodes().css("display", "none");
	$(tableName).css("width","100%");
	$(tableName).removeClass('dataTable');
	$('#tblReport tbody tr:last').remove();
}

function exportPDFProject(){
	var table="";
	var reportName = "Format SB1- Detail of Baseline Survey District wise of WDC-PMKSY 2.0";
	var tableName = "#tblReport";
	var columnSpan =true;
	var colLength=$(tableName+" tr:nth-child(3) th").length;
	var specialNote = true;
	var tbodyRowCount=0;
	var note="";
	var pageorientation = 'landscape';
	var pageSize= 'A4';

	if(specialNote)
		tbodyRowCount=$(tableName+" tbody tr").length+1;
	
	//Comment below 2(two) lines if you want to put custom note here or change it accordingly.
	/*if(typeof $('.text-danger').html() != "undefined")
		note=$('.text-danger').html();*/
		note="* All area in hactare";
		
	
	
	var td="";
	for(var i=1;i<colLength;i++){
		td=td+"<td></td>";
	}
 	
	$(tableName+' tbody tr:last').after('<tr><td width="" class="text-danger text-center">'+note+'</td>'+td+'</tr>');

var headerHtml ="";

	if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
		createDataTable(reportName,tableName,columnSpan,colLength,tbodyRowCount,headerHtml,pageorientation,pageSize);
		}
	//createDataTable(headerTitle,reportName,tableName);
	//trigger pdf button of datatable
	table=$(tableName).DataTable().buttons(0,1).trigger();
	table.buttons('.buttons-pdf').nodes().css("display", "none");
	table.buttons('.buttons-excel').nodes().css("display", "none");
	$(tableName).css("width","100%");
	$(tableName).removeClass('dataTable');
	$('#tblReport tbody tr:last').remove();
}

/***************************************************** Excel Button Click ************************************* */
function exportExcel(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note="* All area in hactare";
	var headerRow =2;
	var tableName = "#tblReport";
	var colLength=$(tableName+" tr:nth-child(1) th").length;
	
	/*if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";*/

	var fileName="Format SB1- Project-wise Details of BaseLine Survey";
	 
	var reportName = "Format SB1- Project-wise Details of BaseLine Survey";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    "<tr><td>State: "+$('#state option:selected').text()+"</td><td>District: "+$('#district option:selected').text()+"</td></tr></table>";
	  
	 //exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
fnExcelReport('tblReport', fileName,headerHtml,colLength,jsDate,headerRow);
}

function exportExcelWCDC(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note="* All area in hactare";
	var headerRow =3;
	var tableName = "#tblReport";
	var colLength=$(tableName+" tr:nth-child(2) th").length;
	
	/*if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";*/

	var fileName="Format SB1- Project-wise Details of BaseLine Survey";
	 
	var reportName = "Format SB1- Project-wise Details of BaseLine Survey";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr></table>";
	  
	 //exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
fnExcelReport('tblReport', fileName,headerHtml,colLength,jsDate,headerRow);
}
	/*********************************************END*****************************************************************/	
