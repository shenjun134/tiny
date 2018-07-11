//<p class="no-matched">No signature matched</p>
var canvas = null;
var fillColor = 'rgba(0, 0, 0, 0.3)';
function doSignUpload() {
  console.log('doUpload begin...');
  beforeSignUpload();
  showLoading();
  var formData = $('#signUploadForm');
  var newFormData = new FormData(formData[0]);
  var checkType = '';
  var url = '/tiny/ocr/signUpload.do?checkType=' + checkType;
  $.ajax({
    url: url,
    type: 'POST',
    data: newFormData,
    cache: false,
    contentType: false,
    processData: false,
    enctype: 'multipart/form-data',
    success: function (resp) {
      console.log('doUpload ret...', resp);
      hideLoading();
      if (resp.status === false) {
        $('#tiff-show').html('<p class="error">' + resp.message + '</p>');
        return;
      }
      if (resp.data === undefined || resp.data === null) {
        $('#tiff-show').html('<p class="warning">No File info Return....</p>');
        return;
      }
      handleUploadRespBefore(resp);
      if (resp.data.type === 'image/tiff' || resp.data.type === 'image/tiff') {
        split2Png(resp);
      } else if(resp.data.type === 'application/pdf' || resp.data.type === 'application/pdf'){
        handlePdf2Png(resp);
      }else {
        handleSinglePageUploadResp(resp);
        showMessage('success', 'Upload successfully');
      }

    },
    error: function (err) {
      console.error('tiff err handle', err);
      hideLoading();
			showMessage('error', 'Upload failed.' + err);
    }
  });
}

function handleUploadRespBefore(resp){
    afterSignUpload();
    $('#tiff-show').html('');
    $('#tiff-show-next').html('');
    $('#tiff-show').append('<div class="head"><a href="' + resp.data.name + '" title="download"><span>' + resp.data.name + '</span></a><span>' + resp.data.length + '</span></div>');

}

function handleSinglePageUploadResp(resp){

    var optionArea = '';
    var markArea = '<div class="mark-ct"></div>';
    var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
    var image = '<div class="cell on "><img id="upload-signature-pre" alt="display-upload-file" class="upload-img upload-img-'+0+'"  src="'+resp.data.name+'" data-path="'+resp.data.path+'" data-sname="'+resp.data.shortName+'"/>'+ optionArea + markArea + canvasCover + '</div>';
    $('#tiff-show').append(image);
    $('#dialog-image-signature .modal-body').html(image);

    setTimeout( function() {
      afterImageLoad();
    }, 1000);
}

function handlePdf2Png(resp){
  if(resp.data.subFileList && resp.data.subFileList.length > 0){
    split2Png(resp);
  }else{
    showObjectPDF(resp);
  }
}

function split2Png(resp){
  var fileInfo = resp.data;
  var pageHtml = '<div class="page-ct">';
  var firstImage = fileInfo.subFileList[0].name;
  var imageStartStr = firstImage.substr(0, firstImage.lastIndexOf('-') + 1);
  var imageEndStr = firstImage.substr(firstImage.lastIndexOf('.'), firstImage.length);
  var pageJumpHtml = '<select class="current-pick">';
  var maxPageShow = 5;
  for(let i = 0, len = fileInfo.subFileList.length; i < len; i++){
    var subTemp = fileInfo.subFileList[i];
    var tiffClz = i === 0 ? 'cell on' : 'cell off';

    if (i === 0) {
      var markArea = '<div class="mark-ct"></div>';
      var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
      var image = '<div class="cell on "><img id="upload-signature-pre" alt="display-upload-file" class="upload-img upload-img-'+i+'"  src="'+subTemp.name+'" data-path="'+subTemp.data.path+'" data-sname="'+subTemp.data.shortName+'" />'+ markArea + canvasCover + '</div>';
      $('#tiff-show').append(image);
      $('#dialog-image-signature .modal-body').html(image);
    }
    if(i < maxPageShow){
      var pageIndex = i + 1;
      var pageCls = i === 1 ? 'page off' : 'page off';
      var pi = i -1;
      pageHtml = pageHtml + '<label class="' + pageCls + '" image-type="'+imageEndStr+'" image-data="'+imageStartStr+'" page-index="'+i+'" onclick="turnPage(this)">Page-' + pageIndex + '</label>';
    }
    var pageIndex = i + 1;
    pageJumpHtml = pageJumpHtml + '<option value="' + i + '">'+ pageIndex + '</option>';
  }
  pageJumpHtml = '<div class="jump-ct"><label class="page" onclick="pageJump(this)" image-type="'+imageEndStr+'" image-data="'+imageStartStr+'">Go to</label>'+  pageJumpHtml + '</select></div>';
  pageHtml = pageHtml + pageJumpHtml + '</div><div id="current-p-index">Current:<span>1</span></div>';
  $('#tiff-show').append(pageHtml);
  setTimeout( function() {
    afterImageLoad();
  }, 1000);
}


function showObjectPDF(resp){
  // <object data="/pdf/sample-3pp.pdf#page=2" type="application/pdf" width="100%" height="100%">
  //   <p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>.</p>
  // </object>
  // <iframe src="/pdf/sample-3pp.pdf#page=2" width="100%" height="100%">
  // This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>
  // </iframe>

  var height = $('#sign-tiff-ct').height();
  var showHtml = '<object id="upload-signature-pre" class="upload-img" data="'+resp.data.name+'" type="application/pdf" width="100%" height="'+height+'px">';
  showHtml = showHtml + '<p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it:';
  showHtml = showHtml + '<a href="'+resp.data.name+'">Download PDF</a>.</p></object>';
  $('#tiff-show').append(showHtml);

  var canvasCt = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>'
  $('#tiff-show').append(canvasCt);

  var image = '<object id="upload-signature-org" class="upload-img" data="'+resp.data.name+'" type="application/pdf" width="100%" height="100%">';
  image = image + '<p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it:';
  image = image + '<a href="'+resp.data.name+'">Download PDF</a>.</p></object>';

  $('#dialog-image-signature .modal-body').html(image);
  showMessage('success', 'Upload successfully');
}

function beforeSignUpload(){
    $('button#sign-val').addClass('btn-off');
    $('button#sign-sub').addClass('btn-off');
    $('button#sign-fix').addClass('btn-off');
    $('#sign-tiff-ct').css('background', '#aaa');
    $('.option-ct').css('display', 'none');
    resetStew();
    cleanValResult();
    cleanImagInfo();
    cleanRectangleInfo();
    canvasOff();
    cleanCut();
    canvas = null;
}

function resetStew(){
    $('#stew').val(0);
    $("#upload-signature-pre").css('-ms-transform', 'rotate(0deg)');
    $("#upload-signature-pre").css('-webkit-transform',  'rotate(0deg)');
    $("#upload-signature-pre").css('transform',  'rotate(0deg)');
}

function afterSignUpload(){
    $('button#sign-val').removeClass('btn-off');
    $('#sign-tiff-ct').css('background', 'rgb(34, 34, 34)');
    $('.option-ct').css('display', 'block');

    var stewInput = document.getElementById('stew');
    stewInput.addEventListener("keyup", function(event){
        event.preventDefault();
        if(event.keyCode != 13 ){
            return;
        }
        stewPress();
    }
    );


}

function afterValidate(){
    $('button#sign-sub').removeClass('btn-off');
    $('button#sign-fix').removeClass('btn-off');
}

function removeCanvas(){
  //var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
}

function turnPage(element) {
  //console.log('turnPageturnPage', element);
  var currentPage = $('#current-p-index span').html();
  currentPage = parseInt(currentPage);

  var pageIndex = $(element).attr('page-index');
  pageIndex = parseInt(pageIndex);
  if(currentPage === (pageIndex + 1)){
    console.log('turnPageturnPage no need to turn page');
    return;
  }
  showLoading();
  beforeSignUpload();
  afterSignUpload();
  removeCanvas();

  var imageUrl = $(element).attr('image-data');
  var imageType = $(element).attr('image-type');
  var srcUrl = imageUrl + pageIndex + imageType;
  var imageHtml = '<img id="upload-signature-pre" alt="display-upload-file" class="upload-img "  src="'+srcUrl+'"/>';
  var markArea = '<div class="mark-ct"></div>';
  var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
  $('#tiff-show  .cell').html(imageHtml)
  $('#tiff-show  .cell').append(markArea);
  $('#tiff-show  .cell').append(canvasCover);

  //$('#tiff-show img').attr('src', imageUrl + pageIndex + imageType);
  $('#dialog-image-signature img').attr('src', imageUrl + pageIndex + imageType);
  $('#current-p-index span').html(pageIndex + 1);
  setTimeout( function(){
    hideLoading();
  }, 300);
   setTimeout( function() {
    afterImageLoad();
  }, 1000);
}

function pageJump(element){
  //console.log('pageJumppageJump', element);
  var currentPage = $('#current-p-index span').html();
  currentPage = parseInt(currentPage);

  var pageIndex = $('select.current-pick').val();
  pageIndex = parseInt(pageIndex);

  if(currentPage === (pageIndex + 1)){
    console.log('pageJumppageJump no need to jump page');
    return;
  }
  showLoading();
  beforeSignUpload();
  afterSignUpload();
  removeCanvas();

  var imageUrl = $(element).attr('image-data');
  var imageType = $(element).attr('image-type');
  var srcUrl = imageUrl + pageIndex + imageType;
  var imageHtml = '<img id="upload-signature-pre" alt="display-upload-file" class="upload-img "  src="'+srcUrl+'"/>';
  var markArea = '<div class="mark-ct"></div>';
  var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
  $('#tiff-show  .cell').html(imageHtml)
  $('#tiff-show  .cell').append(markArea);
  $('#tiff-show  .cell').append(canvasCover);

  // $('#tiff-show img').attr('src', imageUrl + pageIndex + imageType);
  $('#dialog-image-signature img').attr('src', imageUrl + pageIndex + imageType);
  $('#current-p-index span').html(pageIndex + 1);
  setTimeout( function(){
    hideLoading();
  }, 1000);
  setTimeout( function() {
    afterImageLoad();
  }, 1000);
}

function canvasSwitchClick(e){
  var currentClz = $('#sign-canvas-switch i').attr('class');
  if(currentClz.indexOf('fa-toggle-on') > -1){
    canvasOff();
  }else{
    canvasOn();
  }
}

function canvasOff(){
  $('#sign-canvas-switch i').removeClass('fa-toggle-on');
  $('#sign-canvas-switch i').addClass('fa-toggle-off');
  displayCanvas(false);
  disableCut()
}

function canvasOn(){
  $('#sign-canvas-switch i').removeClass('fa-toggle-off');
  $('#sign-canvas-switch i').addClass('fa-toggle-on');
  if(canvas === null){
    afterPDFLoad();
  }
  displayCanvas(true);
  activeCut();
}


function displayCanvas(show){
  var canvasObj = $('#sign-canvas');
  if(canvasObj === undefined || canvasObj === null){
    return;
  }
  $('.canvas-container').css('display', show ? 'block' : 'none');
  $('#sign-canvas').css('display', show ? 'block' : 'none');
  $('.upper-canvas ').css('display', show ? 'block' : 'none');
}



function doSignVal(){

    var degree = $('#stew').val();
    if(degree != '0'){
        showMessage('warning', 'Your rotated image has not been saved!');
        return;
    }
	var uploadLink = $('#tiff-show a');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if(link === undefined){
    console.log('doSignVal no link here');
    return;
  }
  cleanValResult();
	showLoading();
  var page = $('#current-p-index span');
//  if (page == undefined || page.html() === undefined)
//  {
//    console.log('no page info here');
//    return;
//  }
  //var url = '/tiny/ocr/signVal.do?fileName=' + link + '&page=' + page.html();
  var imgObj = $('#upload-signature-pre');
  var imgPath = imgObj.attr("data-path");
  var imgName = imgObj.attr("data-sname");
  var url = '/tiny/ocr/signVal.do?fileName=' + link + '&page=' + page.html() + '&imgPath=' + imgPath + '&imgName=' + imgName;
	$.ajax({
           type: "POST",
           url: url,
           contentType: "application/json",
           success: function(resp)
           {
              hideLoading();
              afterValidate();
              // showMessage('success', 'Validate successfully');
              if(resp.status){
                markStatus('success', resp.message);
                $('#signature-val-result input#name').val(resp.data.name ? resp.data.name : '');
                $('#signature-val-result input#email').val(resp.data.email ? resp.data.email : '');
                $('#signature-val-result input#comments').val(resp.data.comments ? resp.data.comments : '');
                createMarkBox(resp.data.matchArea);
              }else{
                markStatus('danger', resp.message);
              }

           },
           error: function(err){
             hideLoading();
            //  showMessage('error', 'Validate fail!');
             markStatus('danger', 'Validate error!');
           }
    });
}

function markStatus(type, message){
  var alert = 'alert-success';
  var icon = 'fa-check';
  if(type === 'success'){
    alert = 'alert-success';
    icon = 'fa-check';
  }else if(type === 'info'){
    alert = 'alert-info';
    icon = 'fa-check';
  }else if(type === 'danger'){
    alert = 'alert-danger';
    icon = 'fa-times';
  }else if(type === 'warning'){
    alert = 'alert-warning';
    icon = 'fa-exclamation';
  }else if(type === 'light'){
    alert = 'alert-light';
  }else if(type === 'dark'){
    alert = 'alert-dark';
  }else if(type === 'primary'){
    alert = 'alert-primary';
  }else if(type === 'secondary'){
    alert = 'alert-secondary';
  }
  var html = '<div class="alert '+alert+' " style="display: flex; margin: 0px" role="alert">';
  html = html + '<h4 class="alert-heading" style="padding-right: 10px; margin: 0px;">'+message+'</h4>';
  html = html + '<i class="fa '+icon+'" aria-hidden="true"></i></div>';

  $('#signature-val-result #status').html(html);

}

function cleanStatus(){
  $('#signature-val-result #status').html('');
}


function doSignSumbit(){
	var uploadLink = $('#tiff-show a');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if(link === undefined){
    console.log('doSignSumbit no link here');
    return;
  }
  var page = $('#current-p-index span');
  if (page == undefined && page.html() === undefined)
  {
    console.log('no page info here');
    return;
  }
  var boxOn = $('.mark-ct .box-on');
  if(boxOn === undefined){
    console.log('doSignSumbit confirm area here');
    return;
  }
  var confirmArea = {};
  confirmArea.w = boxOn.attr('data-w');
  confirmArea.h = boxOn.attr('data-h');
  confirmArea.x = boxOn.attr('data-x');
  confirmArea.y = boxOn.attr('data-y');
  confirmArea.rate = boxOn.attr('data-rate');

  var pageIndex = page.html();
	showLoading();
	var url = '/tiny/ocr/signSub.do';
	var whois = '';
	var email = '';
	var comments = '';
  $('#signature-val-result input').each(function (index){
    var value = $(this).val();
    var id = $(this).attr('id');
    if(value === undefined){
      value = '';
    }
    if(id = 'name'){
			whois = value;
		}
		if(id = 'email'){
			email = value;
		}
		if(id = 'comments'){
			comments = value;
		}
  });
	var body = {'whois': whois, 'email': email, 'comments': comments, 'fileName': link, 'pageIndex': pageIndex, 'confirmArea': confirmArea};

	$.ajax({
           type: "POST",
           url: url,
           contentType: "application/json",
					 data: JSON.stringify(body),
           dataType : 'json',
           success: function(resp)
           {
              hideLoading();
              showMessage('success', 'Submit successfully');
           },
           error: function(err){
             hideLoading();
             showMessage('error', 'Submit fail!');
           }
    });
}

function doSignFix(){
  var uploadLink = $('#tiff-show a');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if(link === undefined){
    console.log('doSignSumbit no link here');
    return;
  }
	showLoading();
  var page = $('#current-p-index span');
  if (page == undefined && page.html() === undefined)
  {
    console.log('no page info here');
    return;
  }
  var pageIndex = page.html();
  var url = '/tiny/ocr/signSub.do';
	var whois = '';
	var email = '';
	var comments = '';
  $('#signature-val-result input').each(function (index){
    var value = $(this).val();
    var id = $(this).attr('id');
    if(value === undefined){
      value = '';
    }
    if(id = 'name'){
			whois = value;
		}
		if(id = 'email'){
			email = value;
		}
		if(id = 'comments'){
			comments = value;
		}
  });
  var x = $('#box-x').val();
  var y = $('#box-y').val();
  var w = $('#box-w').val();
  var h = $('#box-h').val();
  var fixedArea = {};
  if(x === undefined || x === null || x === ''){
    fixedArea = {};
  }else{
    fixedArea = {'x': x, 'y': y, 'w': w, 'h': h };
  }
	var body = {'whois': whois, 'email': email, 'comments': comments, 'fileName': link, 'fixedArea': fixedArea, 'pageIndex': pageIndex};
	$.ajax({
           type: "POST",
           url: url,
           contentType: "application/json",
					 data: JSON.stringify(body),
           dataType : 'json',
           success: function(resp)
           {
              hideLoading();
              showMessage('success', 'Fix Submit successfully');
           },
           error: function(err){
             hideLoading();
             showMessage('error', 'Fix Submit fail!');
           }
    });
}

function cleanValResult(){
	$('#signature-val-result input').each(function (index){
    $(this).val('');
  });
  cleanStatus();
  cleanMarkBox();
}


function cleanRectangle(){
  cleanRectangleInfo();
  cleanCanvas();
  cleanCut();
}

function afterImageLoad(){
  printImagInfo();
  //sign-canvas

  var selector = '#upload-signature-pre';
  var width = $(selector).width();
  var height = $(selector).height();

  // var offsetX = 585;
  // var offsetY = 130;
  var offsetX = 0;
  var offsetY = 0;

  makeCanvas2('sign-canvas', width, height, offsetX, offsetY, 'sign-tiff-ct');
}

function afterPDFLoad(){
  printPDFInfo();
  //sign-canvas

  var selector = '#viewerContainer .page';
  var canvasCt = $(selector);
  console.log('afterPDFLoad', canvasCt);
  var width = canvasCt.width();
  var height = canvasCt.height();

  // var offsetX = 585;
  // var offsetY = 130;
  var offsetX = 0;
  var offsetY = 0;

  makeCanvas2('sign-canvas', width, height, offsetX, offsetY, 'sign-tiff-ct');
}

function printImagInfo(){
  var myImg = document.querySelector("#upload-signature-pre");
  var realWidth = myImg.naturalWidth;
  var realHeight = myImg.naturalHeight;
  var width = $("#upload-signature-pre").width();
  var scale = (width/realWidth).toFixed(2);
  $("#image-w").val(realWidth);
  $("#image-h").val(realHeight);
  $("#image-scale").attr('data-org-w', width);
  $("#image-scale").attr('data-real-w', realWidth);
  $("#image-scale").val(scale);
}

function printPDFInfo(){
  //page = $('object').contents().find('div');
  var myImg = document.querySelector("object#upload-signature-pre");
  var selector = '#viewerContainer .page';
  console.log('printPDFInfo', myImg);
  var canvasCt = $(selector);
  console.log('printPDFInfo', canvasCt);

  var realWidth = canvasCt.width();
  var realHeight = canvasCt.height();
  var width = $("#viewerContainer .page").width();
  var scale = (width/realWidth).toFixed(2);
  $("#image-w").val(realWidth);
  $("#image-h").val(realHeight);
  $("#image-scale").attr('data-org-w', width);
  $("#image-scale").attr('data-real-w', realWidth);
  $("#image-scale").val(scale);
}

function cleanImagInfo(){
  $("#image-w").val('');
  $("#image-h").val('');
  $("#image-scale").val('');
  $("#stew").val('0');
}

function cleanRectangleInfo(){
  $("#box-w").val('');
  $("#box-h").val('');
  $("#box-x").val('');
  $("#box-y").val('');
}

function activeCut(){
  $('#sign-image-cut').css('cursor', 'pointer');
  $('#sign-image-cut').css('opacity', '1');
  $('#sign-image-cut').css('color', '#fff');
  $('#sign-image-cut').css('background', '#555');
}

function disableCut(){
  $('#sign-image-cut').css('cursor', 'pointer');
  $('#sign-image-cut').css('opacity', '0.6');
  $('#sign-image-cut').css('color', '#000');
  $('#sign-image-cut').css('background', '#fff');
}

function drawRectangleInfoXY(x, y){
  var scale = getScale();

  $("#box-x").val((x / scale).toFixed(2));
  $("#box-y").val((y / scale).toFixed(2));
}

function drawRectangleInfoWH(w, h){
  var scale = getScale();

  $("#box-w").val((w / scale).toFixed(2));
  $("#box-h").val((h / scale).toFixed(2));
}

function makeCanvas(id, width, height, offsetX, offsetY, containerDiv){
  $('#' + id).attr('width', '' + width);
  $('#' + id).attr('height', '' + height);
  var offsetTopScroll = 0;
  var fillColor = 'rgba(250, 0, 0, 0.2)';
  canvas = new fabric.Canvas(id, { selection: false });

  var rect, isDown, origX, origY;

  canvas.on('mouse:down', function(o){
      cleanRectangleInfo();

      canvas.clear();
      isDown = true;
      var pointer = canvas.getPointer(o.e);

      var currentX = pointer.x;
      var currentY = pointer.y;
      var realX = currentX;
      var realY = currentY;

      drawRectangleInfoXY(currentX, currentY);

      origX = pointer.x;
      origY = pointer.y;

      var pointer = canvas.getPointer(o.e);

      var width = Math.abs(pointer.x - origX);
      var height = Math.abs(pointer.y - origY);

      rect = new fabric.Rect({
          left: realX,
          top: realY,
          originX: 'left',
          originY: 'top',
          width: width,
          height: height,
          angle: 0,
          fill: fillColor,
          transparentCorners: false
      });
      canvas.add(rect);

      drawRectangleInfoWH(width, height);
  });

  canvas.on('mouse:move', function(o){
      if (!isDown) return;
      var pointer = canvas.getPointer(o.e);
      var currentX = pointer.x;
      var currentY = pointer.y;
      var realX = currentX;
      var realY = currentY;

      if(origX > realX){
        rect.set({ left: Math.abs(realX) });
      }
      if(origY > realY){
        rect.set({ top: Math.abs(realY) });
      }

      if(origX > realX && origY > realY){
          drawRectangleInfoXY(currentX, currentY);
      }else if(origY > realY && origX < realX){
          drawRectangleInfoXY(origX, currentY);
      }else if(origX > realX && origY < realY){
          drawRectangleInfoXY(currentX, origY);
      }


      var width = Math.abs(origX - currentX);
      var height = Math.abs(origY - currentY);

      rect.set({ width: width });
      rect.set({ height: height });
      drawRectangleInfoWH(width, height);

      canvas.renderAll();
  });

  canvas.on('mouse:up', function(o){
    isDown = false;
    imageCut();
  });
}

/**
 * fill oppsite
 */
function makeCanvas2(id, width, height, offsetX, offsetY, containerDiv){
  $('#' + id).attr('width', '' + width);
  $('#' + id).attr('height', '' + height);
  var offsetTopScroll = 0;


  // var leftCol = 'rgba(250, 0, 0, 0.1)';
  // var rightCol = 'rgba(250, 250, 0, 0.1)';
  // var upCol = 'rgba(0, 0, 250, 0.1)';
  // var downCol = 'rgba(250, 250, 250, 0.1)';
  var isDown;
  var origX, origY;
  var leftCol, rightCol, upCol, downCol;
  // var fillColor1 = 'rgba(0, 0, 0, 1)';
  leftCol = fillColor;
  rightCol = fillColor;
  upCol = fillColor;
  downCol = fillColor;

  var totalH, totalW;
  var leftRect, rightRect, upRect, downRect;
  var x1, y1, x2, y2;
  totalW = width;
  totalH = height;

  canvas = new fabric.Canvas(id, { selection: false });


  canvas.on('mouse:down', function(o){
      cleanRectangleInfo();

      canvas.clear();
      $('.show-upload .canvas-container .upper-canvas').css('background', fillColor);
      isDown = true;

      var pointer = canvas.getPointer(o.e);
      origX = pointer.x;
      origY = pointer.y;


       leftRect = new fabric.Rect({
          left: 0,
          top: 0,
          originX: 'left',
          originY: 'top',
          width: 0,
          height: 0,
          angle: 0,
          fill: leftCol,
          transparentCorners: false
      });

      upRect = new fabric.Rect({
          left: 0,
          top: 0,
          originX: 'left',
          originY: 'top',
          width: 0,
          height: 0,
          angle: 0,
          fill: upCol,
          transparentCorners: false
      });

      downRect = new fabric.Rect({
          left: 0,
          top: 0,
          originX: 'left',
          originY: 'top',
          width: 0,
          height: 0,
          angle: 0,
          fill: downCol,
          transparentCorners: false
      });

      rightRect = new fabric.Rect({
          left: 0,
          top: 0,
          originX: 'left',
          originY: 'top',
          width: 0,
          height: 0,
          angle: 0,
          fill: rightCol,
          transparentCorners: false
      });
      canvas.add(leftRect);
      canvas.add(upRect);
      canvas.add(downRect);
      canvas.add(rightRect);

  });

  canvas.on('mouse:move', function(o){
      if (!isDown) return;
      $('.show-upload .canvas-container .upper-canvas').css('background', 'transparent');
      var pointer = canvas.getPointer(o.e);
      var currentX = pointer.x;
      var currentY = pointer.y;

      if(currentX > origX){
        x1 = origX;
        x2 = currentX;
      }else{
        x2 = origX;
        x1 = currentX;
      }

      if(currentY > origY){
        y1 = origY;
        y2 = currentY;
      }else{
        y2 = origY;
        y1 = currentY;
      }
      var offset = 0.05;
      leftRect.set({ left: 0, top: 0, width: x1 + offset, height: totalH });
      upRect.set({ left: x1, top: 0, width: x2 - x1, height: y1 });
      downRect.set({ left: x1, top: y2, width: x2 - x1, height: totalH - y2 });
      rightRect.set({ left: x2 - offset, top: 0, width: totalW - x2, height: totalH });


      drawRectangleInfoXY(x1, y1);
      drawRectangleInfoWH(x2 - x1, y2 - y1);

      canvas.renderAll();
  });

  canvas.on('mouse:up', function(o){
    isDown = false;
    imageCut();
  });
}



function cleanCanvas(){
  if(canvas){
    canvas.clear();
    $('.show-upload .canvas-container .upper-canvas').css('background', fillColor);
  }
}

function cleanCut(){
  var innerHTML = '<canvas class="cut-image-ct" width="460" height="260" ></canvas>'
  $('#cut-ct').html(innerHTML);
  $('#cut-ct').css('background', '#fff');
  $('#cut-ct .cut-image-ct').attr('id', 'cut-result');
}


function imageCut(){
  var originalShow = false;

  console.log('begin to cut image...');
  var uploadLink = $('#tiff-show img');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('src');
  if(link === undefined){
    console.log('doSignVal no link here');
    return;
  }


  var sourceX = $('#box-x').val();
  var sourceY = $('#box-y').val();
  var sourceWidth = $('#box-w').val();
  var sourceHeight = $('#box-h').val();
  if(sourceX === undefined || sourceX === null || sourceX === ''){
    console.log('No area to cut!');
    return;
  }
  cleanCut();

  $('#cut-ct').css('background', 'rgb(34, 34, 34)');
  if(originalShow){
    $('#cut-result').attr('width','' + sourceWidth);
    $('#cut-result').attr('height','' + sourceHeight);
  }

  // scale = parseFloatH(scale);
  var scale = 1;

  sourceX = parseFloatH(sourceX)/scale;
  sourceY = parseFloatH(sourceY)/scale;

  sourceWidth = parseFloatH(sourceWidth)/scale;
  sourceHeight = parseFloatH(sourceHeight)/scale;

  var cutCanvas = document.getElementById('cut-result');
  var cutContext = cutCanvas.getContext('2d');
  var imageObj = new Image();

  imageObj.onload = function() {
    // draw cropped image

    var destWidth = sourceWidth;
    var destHeight = sourceHeight;
    // var destX = cutCanvas.width / 2 - destWidth / 2;
    // var destY = cutCanvas.height / 2 - destHeight / 2;

    var defW = 460;
    var defH = 260;

    var wScale = 1;
    var hScale = 1;

    if(cutCanvas.width < destWidth){
      wScale = cutCanvas.width / destWidth;
    }
    if(cutCanvas.height < destHeight){
      hScale = cutCanvas.height / destHeight;
    }
    if(!originalShow){
      scale = wScale > hScale ? hScale : wScale;
    }

    destWidth = sourceWidth * scale;
    destHeight = sourceHeight * scale;

    var destX = 0;
    var destY = 0;

    cutContext.drawImage(imageObj, sourceX, sourceY, sourceWidth, sourceHeight, destX, destY, destWidth, destHeight);
  };
  imageObj.src = link;

}

function parseFloatH(numStr){
    var defNum = 0;
    try{
        if(numStr){
            var num = parseFloat(numStr);
            if(num === NaN){
                return defNum
            }
            return num;
        }
        return defNum
    }catch(e){
        return defNum;
    }
}

function createMarkBox(matchArea){
  if(matchArea === undefined || matchArea === null || matchArea.length === 0){
    return;
  }
  var scale = getScale();
  for(var i = 0, len = matchArea.length; i < len; i++){
    var temp = matchArea[i];
    var w = round(parseFloatH(temp.w) * scale, 2);
    var h = round(parseFloatH(temp.h) * scale, 2);
    var x = round(parseFloatH(temp.x) * scale, 2);
    var y = round(parseFloatH(temp.y) * scale, 2);
    var rate = parseFloatH(temp.rate).toFixed(2);
    var nameList = temp.nameList;
    var nameStr = JSON.stringify(nameList);

    var innderText = buildNameStr(nameStr);
    var dataSrc = 'width:' + w + 'px;height:' + h + 'px;left:' + x + 'px;top:' + y + 'px;';
    var dataScale = 'width:' + w + 'px; height:' + h + 'px; left:' + x + 'px; top:' + y + 'px; line-height:' + h + 'px; text-align: center;';
    var box = '<div id="mb-'+i+'" data-x="' + x + '" data-y="'+y+'" data-w="'+w+'" data-h="'+h+'" data-rate="'+rate+'"  onclick="markBoxClick(this)" class="mark-box box-off" style="' + dataScale + '">'+innderText+'</div>';
    $('#tiff-show .mark-ct').append(box);
  }
  setTimeout( function () {
    showTopBox();
  }, 100);
}

function buildNameStr(nameStr){
  var innderText = "<input type=hidden value='" + nameStr + "'></input>";
  return innderText;
}

function getNameStrHtml(id){
   var nameStr = $('#' + id + ' input').val();
  return buildNameStr(nameStr);
}

function round(num, scale){
  return parseFloatH(num.toFixed(scale));
}

function showTopBox(){
  var topId = '';
  var rate = -1;
  var topInnerHtml = '';
  $('.mark-ct > .mark-box').each(function (index){
    var currentId = $(this).attr('id');
    var currentRate = $(this).attr('data-rate');
    currentRate = parseFloatH(currentRate);
    $(this).addClass('box-off');
    var innerHtml = getNameStrHtml(currentId);
    console.log('showTopBox innerHtml', innerHtml);


    $(this).html('<i class="fa fa-question-circle" aria-hidden="true"></i>');
//    $(this).append('<label>'+currentRate+'</label>');
    $(this).append('<label>'+''+'</label>');
    $(this).append(innerHtml);
    if(currentRate > rate){
      rate = currentRate;
      topId = currentId;
      topInnerHtml = innerHtml;
    }
  });
  if(topId === ''){
    console.log("no top id found");
    return;
  }
  $('#' + topId).removeClass('box-off');
  $('#' + topId).addClass('box-on');
  $('#' + topId).html('<i class="fa fa-check-circle-o" aria-hidden="true"></i>');
  $('#' + topId).append('<label>'+rate+'</label>');
  $('#' + topId).append(topInnerHtml);
  showNameList(topId);
}

function markBoxClick(e){
  var id = $(e).attr('id');
  showNameList(id);
  $('.mark-ct > .mark-box').each(function (index){
    var tempId = $(this).attr('id');
    var currentRate = $(this).attr('data-rate');
    var innerHtml = getNameStrHtml(tempId);
    console.log('markBoxClick innerHtml', innerHtml);

    $(this).addClass('box-off');
    $(this).html('<i class="fa fa-question-circle" aria-hidden="true"></i>');
    $(this).append('<label>'+currentRate+'</label>');
    $(this).append(innerHtml);
    if(tempId === id){
      $(this).removeClass('box-off');
      $(this).addClass('box-on');
      $(this).html('<i class="fa fa-check-circle-o" aria-hidden="true"></i>');
      $(this).append('<label>'+currentRate+'</label>');
      $(this).append(innerHtml);
    }
  });
}

function showNameList(id){
  var nameStr = $('#' + id + ' input').val();
  console.log('showNameList', id);
  console.log('showNameList', nameStr);
  var nameList = JSON.parse(nameStr);
  cleanCut();
  $('#cut-ct').html('');
  $('#cut-ct').css('background', 'rgb(34, 34, 34)');
  $('#cut-ct').css('color', '#fff');
  if(nameList === undefined || nameList === null || nameList.length === 0){
    $('#cut-ct').append('No Name matched');
    return;
  }
  for(var i = 0, len = nameList.length; i < len; i++){
    var name = nameList[i];
    var html = buildName(name, i);
    $('#cut-ct').append(html);
    if(i === 0){
      printName(name);
    }
  }
}

function buildName(name, i){
  var nameStr = JSON.stringify(name);
  var clz = i === 0 ? 'name-ct name-active' : 'name-ct';
  var html = "<div class='"+clz+"' data-name='"+nameStr+"' onclick='pickName(this)'>";
  html = html + '<label class="name">' + (name.full ?  name.full : '') + '</label>';
  html = html + '<label class="email">' + (name.email ? name.email : '') + '</label>';
  html = html + '<label class="flg"><i class="fa fa-check-circle-o" aria-hidden="true"></i></label>';
  html = html + '</div>';
  return html;
}

function pickName(e){
  var nameStr = $(e).attr('data-name');
  var name = JSON.parse(nameStr);
  printName(name);
  $('.name-ct').each(function (index){
    $(this).removeClass('name-active');
  });

  $(e).addClass('name-active');
}


function printName(name){
  $('#signature-val-result input#name').val(name.full);
  $('#signature-val-result input#email').val(name.email);
  $('#signature-val-result input#comments').val(name.comments);
}


function cleanMarkBox(){
  $('#tiff-show .mark-ct').html('');
}

function getScale(){
  var orgW = $("#image-scale").attr('data-org-w');
  var realW = $("#image-scale").attr('data-real-w');
  return parseFloatH(orgW)/parseFloatH(realW);
}


document.onkeyup  = function(e){
  var e = e || window.event; // for IE to cover IEs window event-object
  if(e.ctrlKey && e.altKey && e.which == 65) {
    canvasSwitchClick(e);
    return false;
  }
  if(e.ctrlKey && e.altKey && e.which == 67) {
    cleanRectangle();
    return false;
  }
}

function imagePlus(){
    var src = $("#stew").val();
    var degree = parseFloatH(src);
    degree = degree + 1;
    if(degree > 360){
        degree = 0;
    }else if(degree < -360){
        degree = 0;
    }
    $("#stew").val(degree);

    imageStewApply();
}

function imageMinus(){
    var src = $("#stew").val();
    var degree = parseFloatH(src);
    degree = degree - 1;
    if(degree > 360){
        degree = 0;
    }else if(degree < -360){
        degree = 0;
    }
    $("#stew").val(degree);
    imageStewApply();
}

function stewPress(){
    var src = $("#stew").val();
    var degree = parseFloatH(src);

    if(degree > 360 || degree < -360){
        degree = 0;
    }
    $("#stew").val(degree);
    imageStewApply();
}

function imageStewApply(){
    //upload-signature-pre
    var src = $("#stew").val();
    var rotate = "rotate(" + src + "deg)"

    $("#upload-signature-pre").css('-ms-transform', rotate);
    $("#upload-signature-pre").css('-webkit-transform', rotate);
    $("#upload-signature-pre").css('transform', rotate);
}

function imageSave(){
    var src = $("#stew").val();
    var degree = parseFloatH(src);
    if(degree%360 === 0){
        showMessage('warning', 'No degree rotate!!!');
        return;
    }

    //upload-signature-pre
    var imgObj = $('#upload-signature-pre');
    var imgPath = imgObj.attr("data-path");
    var imgName = imgObj.attr("data-sname");
    var url = '/tiny/ocr/image/rotate.do?imgPath=' + imgPath + '&imgName=' + imgName + '&degree='+degree;

    showLoading();

    $.ajax({
           type: "POST",
           url: url,
           contentType: "application/json",
           success: function(resp)
           {
              hideLoading();

              if(resp.status){
                showMessage('success', resp.message);
                beforeSignUpload();

                handleUploadRespBefore(resp);
                handleSinglePageUploadResp(resp);

              }else{
                showMessage('error', resp.message);
              }

           },
           error: function(err){
             hideLoading();
             showMessage('error', 'Save fail!');

           }
    });
}


function optionSwitch(e){
    var display = $(".option-ct").css("display");
    console.log('optionSwitch', display);
    if(display !== undefined && display === 'none'){
        $(".option-ct").css("display", "block");
    }else{
        $(".option-ct").css("display", "none");
    }

}
