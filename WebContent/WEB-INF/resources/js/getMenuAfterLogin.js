/**
 * 
 */

$(document).ready(function(){
	//document.cookie = "menuid=" +null;
 $.ajax({  
            url:"fetchMenu",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	console.log(data);
var $sideMenu = $('#sidemenu');
$navbarDropdownAfterLogin = $('#navbarDropdownAfterLogin');
 for ( var key in data) {
    if (data.hasOwnProperty(key)) {
	var mainmenu="";
	var submenu="";
	var submenu1="";
	var navbarDropdownAfterLogin='';
	var navbarDropdownAfterLoginSubmenu="";
	var value="";
	navbarDropdownAfterLogin='<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"'+
	'aria-expanded="false" style="font-size:16px; line-height:16px;">'+key+'</a><div class="dropdown-menu" aria-labelledby="navbarDropdown">';
	if(key.indexOf("/") > -1){
		value=key.split("/").join("");
		value=value.split(" ").join("");
	}else{
		value=key.split(" ").join("");
	}
	mainmenu=' <li> <a href="#" data-toggle="collapse" name="'+value+'" data-target="#'+value+'" class="collapsed active mainmenu" > <i class="fa fa-th-large"></i> <span class="nav-label"> '+key+' </span> <span class="fa fa-chevron-left pull-right"></span> </a>'+
	'<ul class="sub-menu collapse" id="'+value+'">';
var val= data[key];
	for ( var k in val) {
		navbarDropdownAfterLoginSubmenu=navbarDropdownAfterLoginSubmenu+'<a class="dropdown-item" href="'+val[k].target+'">'+val[k].menuname+'</a>';
		
		submenu=submenu+'<li class="active"><a class = "dropdownmenu"  href="'+val[k].target+'" >'+
		
		val[k].menuname+'</a></li>';
	}
	navbarDropdownAfterLoginSubmenu=navbarDropdownAfterLoginSubmenu+'</div>';
	navbarDropdownAfterLogin=navbarDropdownAfterLogin+navbarDropdownAfterLoginSubmenu;
	navbarDropdownAfterLogin=navbarDropdownAfterLogin+'</li>';
	mainmenu=mainmenu+submenu;
	//$sideMenu.append('</li>');
	mainmenu=mainmenu+'</ul></li>';
	//alert(mainmenu);
	$navbarDropdownAfterLogin.append(navbarDropdownAfterLogin);
	$sideMenu.append(mainmenu);
		}
		}
		if(getCookie('menuid')!=null){
			$('a[name="'+getCookie('menuid')+'"]').removeClass('collapsed');
			$('a[name="'+getCookie('menuid')+'"]').addClass('collapse show');
			$('#'+getCookie('menuid')).addClass('collapse show');
		}
 

		
      }
	  });


$('#sidemenu').on( 'click', 'a.mainmenu', function (e) {
	var del = e.target.getAttribute('name');
	//alert($(this).attr('href'));
	document.cookie = "menuid=" + $(this).attr('name') + ";"
	});



$('#sidemenu').on( 'click', 'a.dropdownmenu', function (e) {
	var url = $(this).attr('href');
	$.ajax({  
            url:"userAction",
            type: "post",  
            data: {url:url},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	
	}
	});
	});

});

function getCookie(name) {
    // Split cookie string and get all individual name=value pairs in an array
    var cookieArr = document.cookie.split(";");

    
    // Loop through the array elements
    for(var i = 0; i < cookieArr.length; i++) {
        var cookiePair = cookieArr[i].split("=");
        
        /* Removing whitespace at the beginning of the cookie name
        and compare it with the given string */
        if(name == cookiePair[0].trim()) {
            // Decode the cookie value and return
            return decodeURIComponent(cookiePair[1]);
        }
    }
    
    // Return null if not found
    return null;
}
