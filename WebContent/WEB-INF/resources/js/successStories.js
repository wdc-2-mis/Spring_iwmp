
var _PDF_DOC; var _CURRENT_PAGE; var _TOTAL_PAGES; var _PAGE_RENDERING_IN_PROGRESS = 0; var _CANVAS = document.querySelector('#pdf-canvas');


async function showPDF(pdf_url) {
    document.querySelector("#pdf-loader").style.display = 'block';

    // get handle of pdf document
    try {
        _PDF_DOC = await pdfjsLib.getDocument({ url: pdf_url });
    }
    catch(error) {
        alert("ERROR: "+error.message);
document.querySelector("#pdf-loader").style.display = 'none';
    document.querySelector("#pdf-contents").style.display = 'none';
document.querySelector(".pdfIcon").style.display = 'block';
    
    }

    // total pages in pdf
    _TOTAL_PAGES = _PDF_DOC.numPages;
    
    // Hide the pdf loader and show pdf container
    document.querySelector("#pdf-loader").style.display = 'none';
    document.querySelector("#pdf-contents").style.display = 'block';
    document.querySelector("#pdf-total-pages").innerHTML = _TOTAL_PAGES;

    // show the first page
    showPage(1);
}

// load and render specific page of the PDF
async function showPage(page_no) {
    _PAGE_RENDERING_IN_PROGRESS = 1;
    _CURRENT_PAGE = page_no;

    // disable Previous & Next buttons while page is being loaded
    document.querySelector("#pdf-next").disabled = true;
    document.querySelector("#pdf-prev").disabled = true;

    // while page is being rendered hide the canvas and show a loading message
    document.querySelector("#pdf-canvas").style.display = 'none';
    document.querySelector("#page-loader").style.display = 'block';

    // update current page
    document.querySelector("#pdf-current-page").innerHTML = page_no;
    
    // get handle of page
    try {
        var page = await _PDF_DOC.getPage(page_no);
    }
    catch(error) {
        alert(error.message);
    }

    // original width of the pdf page at scale 1
    var pdf_original_width = page.getViewport(1).width;
   // alert(_CANVAS.width+" : "+window.innerWidth/2);
//_CANVAS.width=pdf_original_width;
    // as the canvas is of a fixed width we need to adjust the scale of the viewport where page is rendered
    var scale_required = (window.innerWidth/2) / pdf_original_width;//_CANVAS.width / pdf_original_width;
//alert(scale_required);
    // get viewport to render the page at required scale
    var viewport = page.getViewport(scale_required);

    // set canvas height same as viewport height
    _CANVAS.height = viewport.height;
_CANVAS.width=viewport.width;
    // setting page loader height for smooth experience
    document.querySelector("#page-loader").style.height =  _CANVAS.height + 'px';
    document.querySelector("#page-loader").style.lineHeight = _CANVAS.height + 'px';

    // page is rendered on <canvas> element
    var render_context = {
        canvasContext: _CANVAS.getContext('2d'),
        viewport: viewport
    };
        
    // render the page contents in the canvas
    try {await page.render(render_context);}
    catch(error) {
        alert(error.message);
    }

    _PAGE_RENDERING_IN_PROGRESS = 0;

    // re-enable Previous & Next buttons
    document.querySelector("#pdf-next").disabled = false;
    document.querySelector("#pdf-prev").disabled = false;

    // show the canvas and hide the page loader
    document.querySelector("#pdf-canvas").style.display = 'block';
    document.querySelector("#page-loader").style.display = 'none';
}

// click on "Show PDF" buuton
/*document.querySelector("#show-pdf-button").addEventListener('click', function() {
    this.style.display = 'none';
    showPDF('https://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf');
});*/



// click on the "Previous" page button
document.querySelector("#pdf-prev").addEventListener('click', function() {
    if(_CURRENT_PAGE != 1)
        showPage(--_CURRENT_PAGE);
});

// click on the "Next" page button
document.querySelector("#pdf-next").addEventListener('click', function() {
    if(_CURRENT_PAGE != _TOTAL_PAGES)
        showPage(++_CURRENT_PAGE);
});

function showPdf(path,fileHeading){console.log(fileHeading);
	//e.preventDefault();
	document.querySelector(".pdfIcon").style.display = 'none';
    document.querySelector("#pdf-main-container").style.display = 'block';
//	document.getElementsByClassName("pdfHeading").text("Hello World!");
//x.querySelector(".pdfHeading").innerHTML = "Hello World!";
	$(".pdfHeading").text(fileHeading);
	showPDF(path);
}

function pagination(value){
	return value();
}

function nextPage(){
var pageid = $("#next").attr('value');
window.location.href="successStories?id="+pageid;
}

function prevPage(){
var pageid = $("#prev").attr('value');
window.location.href="successStories?id="+pageid;
}