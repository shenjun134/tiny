//<p class="no-matched">No signature matched</p>
var canvas = null;
var previewCanvas = null;
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
      if (resp.status === false) {
        $('#tiff-show').html('<p class="error">' + resp.message + '</p>');
        hideLoading();
        return;
      }
      if (resp.data === undefined || resp.data === null) {
        $('#tiff-show').html('<p class="warning">No File info Return....</p>');
        hideLoading();
        return;
      }
      handleUploadRespBefore(resp);
      if (resp.data.type === 'image/tiff' || resp.data.type === 'image/tiff') {
        split2Png(resp);
      } else if (resp.data.type === 'application/pdf' || resp.data.type === 'application/pdf') {
        handlePdf2Png(resp);
      } else {
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

function handleUploadRespBefore(resp) {
  afterSignUpload();
  $('#tiff-show').html('');
  $('#tiff-show-next').html('');
  $('#tiff-show').append('<div class="head"><a href="' + resp.data.name + '" title="download"><span>' + resp.data.name + '</span></a><span>' + resp.data.length + '</span></div>');

}

function handleSinglePageUploadResp(resp) {

  var optionArea = '';
  var markArea = '<div class="mark-ct"></div>';
  var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
  var image = '<div class="cell on "><img id="upload-signature-pre" alt="display-upload-file" class="upload-img upload-img-' + 0 + '"  src="' + resp.data.name + '" data-path="' + resp.data.path + '" data-sname="' + resp.data.shortName + '"/>' + optionArea + markArea + canvasCover + '</div>';
  $('#tiff-show').append(image);
  $('#dialog-image-signature .modal-body').html(image);

  setTimeout(function () {
    afterImageLoad();
  }, 1000);
}

function handlePdf2Png(resp) {
  if (resp.data.subFileList && resp.data.subFileList.length > 0) {
    split2Png(resp);
  } else {
    showObjectPDF(resp);
  }
}

function split2Png(resp) {
  var fileInfo = resp.data;
  var pageHtml = '<div class="page-ct">';
  var firstImage = fileInfo.subFileList[0].name;
  var imageStartStr = firstImage.substr(0, firstImage.lastIndexOf('-') + 1);
  var imageEndStr = firstImage.substr(firstImage.lastIndexOf('.'), firstImage.length);
  var pageJumpHtml = '<select class="current-pick">';
  var maxPageShow = 5;
  for (let i = 0, len = fileInfo.subFileList.length; i < len; i++) {
    var subTemp = fileInfo.subFileList[i];
    var tiffClz = i === 0 ? 'cell on' : 'cell off';

    if (i === 0) {
      var markArea = '<div class="mark-ct"></div>';
      var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
      var image = '<div class="cell on "><img id="upload-signature-pre" alt="display-upload-file" class="upload-img upload-img-' + i + '"  src="' + subTemp.name + '" data-path="' + subTemp.data.path + '" data-sname="' + subTemp.data.shortName + '" />' + markArea + canvasCover + '</div>';
      $('#tiff-show').append(image);
      $('#dialog-image-signature .modal-body').html(image);
    }
    if (i < maxPageShow) {
      var pageIndex = i + 1;
      var pageCls = i === 1 ? 'page off' : 'page off';
      var pi = i - 1;
      pageHtml = pageHtml + '<label class="' + pageCls + '" image-type="' + imageEndStr + '" image-data="' + imageStartStr + '" page-index="' + i + '" onclick="turnPage(this)">Page-' + pageIndex + '</label>';
    }
    var pageIndex = i + 1;
    pageJumpHtml = pageJumpHtml + '<option value="' + i + '">' + pageIndex + '</option>';
  }
  pageJumpHtml = '<div class="jump-ct"><label class="page" onclick="pageJump(this)" image-type="' + imageEndStr + '" image-data="' + imageStartStr + '">Go to</label>' + pageJumpHtml + '</select></div>';
  pageHtml = pageHtml + pageJumpHtml + '</div><div id="current-p-index">Current:<span>1</span></div>';
  $('#tiff-show').append(pageHtml);
  setTimeout(function () {
    afterImageLoad();
  }, 1000);
}


function showObjectPDF(resp) {
  // <object data="/pdf/sample-3pp.pdf#page=2" type="application/pdf" width="100%" height="100%">
  //   <p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>.</p>
  // </object>
  // <iframe src="/pdf/sample-3pp.pdf#page=2" width="100%" height="100%">
  // This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>
  // </iframe>

  var height = $('#sign-tiff-ct').height();
  var showHtml = '<object id="upload-signature-pre" class="upload-img" data="' + resp.data.name + '" type="application/pdf" width="100%" height="' + height + 'px">';
  showHtml = showHtml + '<p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it:';
  showHtml = showHtml + '<a href="' + resp.data.name + '">Download PDF</a>.</p></object>';
  $('#tiff-show').append(showHtml);

  var canvasCt = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>'
  $('#tiff-show').append(canvasCt);

  var image = '<object id="upload-signature-org" class="upload-img" data="' + resp.data.name + '" type="application/pdf" width="100%" height="100%">';
  image = image + '<p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it:';
  image = image + '<a href="' + resp.data.name + '">Download PDF</a>.</p></object>';

  $('#dialog-image-signature .modal-body').html(image);
  showMessage('success', 'Upload successfully');
}

function beforeSignUpload() {
  $('button#sign-val').addClass('btn-off');
  $('button#sign-sub').addClass('btn-off');
  $('button#sign-fix').addClass('btn-off');
  $('#sign-tiff-ct').css('background', '#aaa');
  $('.option-ct').css('display', 'none');
  $('.syn-option-ct').css('display', 'none');
  $('#writer-list').addClass('opt-off');
  $('#step-sign').removeClass('active');
  $('#step-layout').removeClass('active');
  $('#step-content').removeClass('active');
  document.getElementById("writer-list").options[0].selected = true;
  resetStew();
  cleanValResult();
  cleanImagInfo();
  cleanRectangleInfo();
  canvasOff();
  preCanvasOff();
  cleanCut();
  canvas = null;
  cleanNextInfo();
}

function cleanNextInfo() {
  $('i#sign-next').addClass('btn-off');
  $('button#layout-recon').addClass('btn-off');
  $('button#content-recon').addClass('btn-off');

  //$('input#layout-type').val('');
  $('select#layout-type').val('');
  $('input#content-type').val('');
  $('#ocr-result').html('<p class="blank">No data</p>');


  $('#layout-tiff-ct').css('background', '#999');
  $('#tiff-show-layout').html('<p class="blank">Please upload a signature image</p>');
  $('div#type-list').html('<button class="dropdown-item" type="button">No Data</button>');
  cleanMatchedLayout();
  previewCanvas = null;
}

function enableNextInfo() {
  setTimeout(function () {
    $('i#sign-next').removeClass('btn-off');
    $('button#layout-recon').removeClass('btn-off');
    $('button#content-recon').removeClass('btn-off');
    synUploadImage();
  }, 200);
}

function resetStew() {
  $('#stew').val(0);
  $("#upload-signature-pre").css('-ms-transform', 'rotate(0deg)');
  $("#upload-signature-pre").css('-webkit-transform', 'rotate(0deg)');
  $("#upload-signature-pre").css('transform', 'rotate(0deg)');
}

function afterSignUpload() {
  $('button#sign-val').removeClass('btn-off');
  $('#sign-tiff-ct').css('background', 'rgb(34, 34, 34)');
  $('.option-ct').css('display', 'block');
  $('.syn-option-ct').css('display', 'block');
  $('#writer-list').removeClass('opt-off');

  var stewInput = document.getElementById('stew');
  stewInput.addEventListener("keyup", function (event) {
    event.preventDefault();
    if (event.keyCode != 13) {
      return;
    }
    stewPress();
  }
  );
  enableNextInfo();
}

function afterValidate() {
  $('button#sign-sub').removeClass('btn-off');
  $('button#sign-fix').removeClass('btn-off');
}

function removeCanvas() {
  //var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
}

function turnPage(element) {
  //console.log('turnPageturnPage', element);
  var currentPage = $('#current-p-index span').html();
  currentPage = parseInt(currentPage);

  var pageIndex = $(element).attr('page-index');
  pageIndex = parseInt(pageIndex);
  if (currentPage === (pageIndex + 1)) {
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
  var imageHtml = '<img id="upload-signature-pre" alt="display-upload-file" class="upload-img "  src="' + srcUrl + '"/>';
  var markArea = '<div class="mark-ct"></div>';
  var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
  $('#tiff-show  .cell').html(imageHtml)
  $('#tiff-show  .cell').append(markArea);
  $('#tiff-show  .cell').append(canvasCover);

  //$('#tiff-show img').attr('src', imageUrl + pageIndex + imageType);
  $('#dialog-image-signature img').attr('src', imageUrl + pageIndex + imageType);
  $('#current-p-index span').html(pageIndex + 1);
  setTimeout(function () {
    hideLoading();
  }, 300);
  setTimeout(function () {
    afterImageLoad();
  }, 1000);
}

function pageJump(element) {
  //console.log('pageJumppageJump', element);
  var currentPage = $('#current-p-index span').html();
  currentPage = parseInt(currentPage);

  var pageIndex = $('select.current-pick').val();
  pageIndex = parseInt(pageIndex);

  if (currentPage === (pageIndex + 1)) {
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
  var imageHtml = '<img id="upload-signature-pre" alt="display-upload-file" class="upload-img "  src="' + srcUrl + '"/>';
  var markArea = '<div class="mark-ct"></div>';
  var canvasCover = '<canvas id="sign-canvas" width="0" height="0" style="display: none;"></canvas>';
  $('#tiff-show  .cell').html(imageHtml)
  $('#tiff-show  .cell').append(markArea);
  $('#tiff-show  .cell').append(canvasCover);

  // $('#tiff-show img').attr('src', imageUrl + pageIndex + imageType);
  $('#dialog-image-signature img').attr('src', imageUrl + pageIndex + imageType);
  $('#current-p-index span').html(pageIndex + 1);
  setTimeout(function () {
    hideLoading();
  }, 1000);
  setTimeout(function () {
    afterImageLoad();
  }, 1000);
}

function canvasSwitchClick(e) {
  var currentClz = $('#sign-canvas-switch i').attr('class');
  if (currentClz.indexOf('fa-toggle-on') > -1) {
    canvasOff();
  } else {
    canvasOn();
  }
}

function preCanvasSwitchClick(e) {
  var currentClz = $('#sign-canvas-switch-pre i').attr('class');
  if (currentClz.indexOf('fa-toggle-on') > -1) {
    preCanvasOff();
  } else {
    preCanvasOn();
  }
}

function canvasOff() {
  $('#sign-canvas-switch i').removeClass('fa-toggle-on');
  $('#sign-canvas-switch i').addClass('fa-toggle-off');
  displayCanvas(false);
  disableCut()
}

function preCanvasOff() {
  $('#sign-canvas-switch-pre i').removeClass('fa-toggle-on');
  $('#sign-canvas-switch-pre i').addClass('fa-toggle-off');
  displayPreCanvas(false);
  disablePreCut()
}

function canvasOn() {
  $('#sign-canvas-switch i').removeClass('fa-toggle-off');
  $('#sign-canvas-switch i').addClass('fa-toggle-on');
  if (canvas === null) {
    afterPDFLoad();
  }
  displayCanvas(true);
  activeCut();
}

function preCanvasOn() {
  $('#sign-canvas-switch-pre i').removeClass('fa-toggle-off');
  $('#sign-canvas-switch-pre i').addClass('fa-toggle-on');
  displayPreCanvas(true);
  activePreCut();
}


function displayCanvas(show) {
  var canvasObj = $('#sign-canvas');
  if (canvasObj === undefined || canvasObj === null) {
    return;
  }
  $('.cell .canvas-container').css('display', show ? 'block' : 'none');
  $('#sign-canvas').css('display', show ? 'block' : 'none');
  $('.cell .upper-canvas ').css('display', show ? 'block' : 'none');
}

function displayPreCanvas(show) {
  var canvasObj = $('#preview-canvas');
  if (canvasObj === undefined || canvasObj === null) {
    return;
  }
  $('.preview-ct .canvas-container').css('display', show ? 'block' : 'none');
  $('#preview-canvas').css('display', show ? 'block' : 'none');
  $('.preview-ct .upper-canvas ').css('display', show ? 'block' : 'none');
}




function doSignVal() {

  var degree = $('#stew').val();
  if (degree != '0') {
    showMessage('warning', 'Your rotated image has not been saved!');
    return;
  }
  var uploadLink = $('#tiff-show a');
  if (uploadLink === undefined) {
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if (link === undefined) {
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
    success: function (resp) {
      hideLoading();
      afterValidate();
      // showMessage('success', 'Validate successfully');
      if (resp.status) {
        markStatus('success', resp.message);
        $('#signature-val-result input#name').val(resp.data.name ? resp.data.name : '');
        $('#signature-val-result input#email').val(resp.data.email ? resp.data.email : '');
        $('#signature-val-result input#writerId').val(resp.data.id ? resp.data.id : '');
        $('#signature-val-result input#comments').val(resp.data.comments ? resp.data.comments : '');
        $('#signature-val-result input#validateImageId').val(resp.data.faxId ? resp.data.faxId : '');
        createMarkBox(resp.data.matchArea);
      } else {
        markStatus('danger', resp.message);
      }

    },
    error: function (err) {
      hideLoading();
      //  showMessage('error', 'Validate fail!');
      markStatus('danger', 'Validate error!');
    }
  });
}

function doSignNext() {
  if ($('#sign-next').hasClass('btn-off')) {
    showMessage('warning', 'Please upload a image first!!!');
    return;
  }
  console.log('do next begin....');
  //synUploadImage();
  var liWidth = $('#th-main').width();
  $('#th-main').attr('class', '');
  $('#th-detail').attr('class', 'on');

  $('#tc-main').css('display', 'none');
  $('#tc-detail').css('display', 'block');

  $('.contents .title-list p').stop(false, true).animate({
    'left': 1 * liWidth + 'px'
  }, 300);
  console.log('do next end.');
}

function doDetailBack() {
  console.log('do back begin....');
  var liWidth = $('#th-detail').width();
  var liWidth2 = $('#th-main').width();
  $('#th-main').attr('class', 'on');
  $('#th-detail').attr('class', '');

  $('#tc-main').css('display', 'block');
  $('#tc-detail').css('display', 'none');

  $('.contents .title-list p').stop(false, true).animate({
    'left': -0 * liWidth + 'px'
  }, 300);
  console.log('do back end.');
}

function synUploadImage() {
  //tiff-show-layout
  showLoading();
  $('#layout-tiff-ct').css('background', 'rgb(34, 34, 34)');
  $('#tiff-show-layout').html('<div class="cell on"></div>');
  var uploadImg = $('#tiff-show .cell > img');
  var cloneImg = uploadImg.clone();
  cloneImg.attr('id', 'syn-signature-pre');
  cloneImg.appendTo('#tiff-show-layout .cell');
  $('#tiff-show-layout .cell').append('<div class="mark-ct"></div>');

  setTimeout(
    function () {
      hideLoading();
      showPreviewImg();
    }, 500
  );
}

function markStatus(type, message) {
  var alert = 'alert-success';
  var icon = 'fa-check';
  if (type === 'success') {
    alert = 'alert-success';
    icon = 'fa-check';
  } else if (type === 'info') {
    alert = 'alert-info';
    icon = 'fa-check';
  } else if (type === 'danger') {
    alert = 'alert-danger';
    icon = 'fa-times';
  } else if (type === 'warning') {
    alert = 'alert-warning';
    icon = 'fa-exclamation';
  } else if (type === 'light') {
    alert = 'alert-light';
  } else if (type === 'dark') {
    alert = 'alert-dark';
  } else if (type === 'primary') {
    alert = 'alert-primary';
  } else if (type === 'secondary') {
    alert = 'alert-secondary';
  }
  var html = '<div class="alert ' + alert + ' " style="display: flex; margin: 0px" role="alert">';
  html = html + '<h4 class="alert-heading" style="padding-right: 10px; margin: 0px;">' + message + '</h4>';
  html = html + '<i class="fa ' + icon + '" aria-hidden="true"></i></div>';

  $('#signature-val-result #status').html(html);

}

function cleanStatus() {
  $('#signature-val-result #status').html('');
}


function doSignSumbit() {
  var uploadLink = $('#tiff-show a');
  if (uploadLink === undefined) {
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if (link === undefined) {
    console.log('doSignSumbit no link here');
    return;
  }
  var page = $('#current-p-index span');
  if (page == undefined && page.html() === undefined) {
    console.log('no page info here');
    return;
  }
  var boxOn = $('.mark-ct .box-on');
  if (boxOn === undefined) {
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

  var url = '/tiny/ocr/signSub.do';
  var writerId = '';
  var whois = '';
  var email = '';
  var comments = '';
  var validateImageId = '';
  $('#signature-val-result input').each(function (index) {
    var value = $(this).val();
    var id = $(this).attr('id');
    //console.log('doSignSumbit', id + '- ' + value);
    if (value === undefined) {
      value = '';
    }
    if (id === 'name') {
      whois = value;
    }
    if (id === 'writerId') {
      writerId = value;
    }
    if (id === 'email') {
      email = value;
    }
    if (id === 'comments') {
      comments = value;
    }
    if (id === 'validateImageId') {
      validateImageId = value;
    }
  });
  if (validateImageId === undefined || validateImageId === null || validateImageId.trim().length === 0) {
    showMessage('warning', 'Please validate first!');
    return;
  }
  var body = { 'name': whois, 'writerId': writerId, 'email': email, 'comments': comments, 'fixedImage': link, 'validateId': validateImageId, 'pageIndex': pageIndex, 'confirmArea': confirmArea };
  showLoading();
  $.ajax({
    type: "POST",
    url: url,
    contentType: "application/json",
    data: JSON.stringify(body),
    dataType: 'json',
    success: function (resp) {
      hideLoading();
      var type = resp.status ? 'success' : 'error';
      var message = resp.message ? resp.message : 'Submit ' + type + ' !';
      stepSignFinish();
      showMessage(type, message);
    },
    error: function (err) {
      hideLoading();
      showMessage('error', 'Submit fail!');
    }
  });
}

function doSignFix() {
  var uploadLink = $('#tiff-show a');
  if (uploadLink === undefined) {
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if (link === undefined) {
    console.log('doSignSumbit no link here');
    return;
  }
  var page = $('#current-p-index span');
  if (page == undefined && page.html() === undefined) {
    console.log('no page info here');
    return;
  }
  var pageIndex = page.html();
  var url = '/tiny/ocr/signSub.do';
  var writerId = '';
  var whois = '';
  var email = '';
  var comments = '';
  var validateImageId = '';
  $('#signature-val-result input').each(function (index) {
    var value = $(this).val();
    var id = $(this).attr('id');
    if (value === undefined) {
      value = '';
    }
    if (id === 'name') {
      whois = value;
    }
    if (id === 'writerId') {
      writerId = value;
    }
    if (id === 'email') {
      email = value;
    }
    if (id === 'comments') {
      comments = value;
    }
    if (id === 'validateImageId') {
      validateImageId = value;
    }
  });
  if (validateImageId === undefined || validateImageId === null || validateImageId.trim().length === 0) {
    showMessage('warning', 'Please validate first!');
    return;
  }

  var x = $('#box-x').val();
  var y = $('#box-y').val();
  var w = $('#box-w').val();
  var h = $('#box-h').val();
  var fixedArea = {};
  if (x === undefined || x === null || x === '') {
    fixedArea = {};
  } else {
    fixedArea = { 'x': x, 'y': y, 'w': w, 'h': h };
  }
  var body = { 'name': whois, 'writerId': writerId, 'email': email, 'comments': comments, 'fixedImage': link, 'validateId': validateImageId, 'fixedArea': fixedArea, 'pageIndex': pageIndex };
  showLoading();
  $.ajax({
    type: "POST",
    url: url,
    contentType: "application/json",
    data: JSON.stringify(body),
    dataType: 'json',
    success: function (resp) {
      hideLoading();
      var type = resp.status ? 'success' : 'error';
      var message = resp.message ? resp.message : 'Fix ' + type + ' !';

      showMessage(type, message);
    },
    error: function (err) {
      hideLoading();
      showMessage('error', 'Fix Submit fail!');
    }
  });
}

function cleanValResult() {
  $('#signature-val-result input').each(function (index) {
    $(this).val('');
  });
  $('#signature-val-result input#validateImageId').val('');
  cleanStatus();
  cleanMarkBox();
}


function cleanRectangle() {
  cleanRectangleInfo();
  cleanCanvas();
  cleanCut();
}

function afterImageLoad() {
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

function afterPDFLoad() {
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

function printImagInfo() {
  var myImg = document.querySelector("#upload-signature-pre");
  var realWidth = myImg.naturalWidth;
  var realHeight = myImg.naturalHeight;
  var width = $("#upload-signature-pre").width();
  var scale = (width / realWidth).toFixed(2);
  $("#image-w").val(realWidth);
  $("#image-h").val(realHeight);
  $("#image-scale").attr('data-org-w', width);
  $("#image-scale").attr('data-real-w', realWidth);
  $("#image-scale").val(scale);
}

function printPDFInfo() {
  //page = $('object').contents().find('div');
  var myImg = document.querySelector("object#upload-signature-pre");
  var selector = '#viewerContainer .page';
  console.log('printPDFInfo', myImg);
  var canvasCt = $(selector);
  console.log('printPDFInfo', canvasCt);

  var realWidth = canvasCt.width();
  var realHeight = canvasCt.height();
  var width = $("#viewerContainer .page").width();
  var scale = (width / realWidth).toFixed(2);
  $("#image-w").val(realWidth);
  $("#image-h").val(realHeight);
  $("#image-scale").attr('data-org-w', width);
  $("#image-scale").attr('data-real-w', realWidth);
  $("#image-scale").val(scale);
}

function cleanImagInfo() {
  $("#image-w").val('');
  $("#image-h").val('');
  $("#image-scale").val('');
  $("#stew").val('0');
}

function cleanRectangleInfo() {
  $("#box-w").val('');
  $("#box-h").val('');
  $("#box-x").val('');
  $("#box-y").val('');
}

function cleanPreviewRectangleInfo() {
  $("#recon-box-w").val('');
  $("#recon-box-h").val('');
  $("#recon-box-x").val('');
  $("#recon-box-y").val('');
}

function activeCut() {
  $('#sign-image-cut').css('cursor', 'pointer');
  $('#sign-image-cut').css('opacity', '1');
  $('#sign-image-cut').css('color', '#fff');
  $('#sign-image-cut').css('background', '#555');
}

function disableCut() {
  $('#sign-image-cut').css('cursor', 'pointer');
  $('#sign-image-cut').css('opacity', '0.6');
  $('#sign-image-cut').css('color', '#000');
  $('#sign-image-cut').css('background', '#fff');
}

function activePreCut() {
  $('#sign-image-cut-pre').css('cursor', 'pointer');
  $('#sign-image-cut-pre').css('opacity', '1');
  $('#sign-image-cut-pre').css('color', '#fff');
  $('#sign-image-cut-pre').css('background', '#555');
}

function disablePreCut() {
  $('#sign-image-cut-pre').css('cursor', 'pointer');
  $('#sign-image-cut-pre').css('opacity', '0.6');
  $('#sign-image-cut-pre').css('color', '#000');
  $('#sign-image-cut-pre').css('background', '#fff');
}

function drawRectangleInfoXY(x, y) {
  var scale = getScale();

  $("#box-x").val((x / scale).toFixed(2));
  $("#box-y").val((y / scale).toFixed(2));
}

function drawPreviewRectangleInfoXY(x, y) {
  var scale = getPreviewScale();
  scale = 1/scale;
  console.log('drawPreviewRectangleInfoWH', scale);

  $("#preview-recon-position #recon-box-x").val((x / scale).toFixed(2));
  $("#preview-recon-position #recon-box-y").val((y / scale).toFixed(2));
}

function drawRectangleInfoWH(w, h) {
  var scale = getScale();

  $("#box-w").val((w / scale).toFixed(2));
  $("#box-h").val((h / scale).toFixed(2));
}

function drawPreviewRectangleInfoWH(w, h) {
  var scale = getPreviewScale();
  scale = 1/scale;
  console.log('drawPreviewRectangleInfoWH', scale);

  $("#preview-recon-position #recon-box-w").val((w / scale).toFixed(2));
  $("#preview-recon-position #recon-box-h").val((h / scale).toFixed(2));
}

function makeCanvas(id, width, height, offsetX, offsetY, containerDiv) {
  $('#' + id).attr('width', '' + width);
  $('#' + id).attr('height', '' + height);
  var offsetTopScroll = 0;
  var fillColor = 'rgba(250, 0, 0, 0.2)';
  canvas = new fabric.Canvas(id, { selection: false });

  var rect, isDown, origX, origY;

  canvas.on('mouse:down', function (o) {
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

  canvas.on('mouse:move', function (o) {
    if (!isDown) return;
    var pointer = canvas.getPointer(o.e);
    var currentX = pointer.x;
    var currentY = pointer.y;
    var realX = currentX;
    var realY = currentY;

    if (origX > realX) {
      rect.set({ left: Math.abs(realX) });
    }
    if (origY > realY) {
      rect.set({ top: Math.abs(realY) });
    }

    if (origX > realX && origY > realY) {
      drawRectangleInfoXY(currentX, currentY);
    } else if (origY > realY && origX < realX) {
      drawRectangleInfoXY(origX, currentY);
    } else if (origX > realX && origY < realY) {
      drawRectangleInfoXY(currentX, origY);
    }


    var width = Math.abs(origX - currentX);
    var height = Math.abs(origY - currentY);

    rect.set({ width: width });
    rect.set({ height: height });
    drawRectangleInfoWH(width, height);

    canvas.renderAll();
  });

  canvas.on('mouse:up', function (o) {
    isDown = false;
    imageCut();
  });
}

/**
 * fill oppsite
 */
function makeCanvas2(id, width, height, offsetX, offsetY, containerDiv) {
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


  canvas.on('mouse:down', function (o) {
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

  canvas.on('mouse:move', function (o) {
    if (!isDown) return;
    $('.show-upload .canvas-container .upper-canvas').css('background', 'transparent');
    var pointer = canvas.getPointer(o.e);
    var currentX = pointer.x;
    var currentY = pointer.y;

    if (currentX > origX) {
      x1 = origX;
      x2 = currentX;
    } else {
      x2 = origX;
      x1 = currentX;
    }

    if (currentY > origY) {
      y1 = origY;
      y2 = currentY;
    } else {
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

  canvas.on('mouse:up', function (o) {
    isDown = false;
    imageCut();
  });
}



function cleanCanvas() {
  if (canvas) {
    canvas.clear();
    $('.show-upload .canvas-container .upper-canvas').css('background', fillColor);
  }
}

function cleanCut() {
  var innerHTML = '<canvas class="cut-image-ct" width="460" height="260" ></canvas>'
  $('#cut-ct').html(innerHTML);
  $('#cut-ct').css('background', '#fff');
  $('#cut-ct .cut-image-ct').attr('id', 'cut-result');
}


function imageCut() {
  var originalShow = false;

  console.log('begin to cut image...');
  var uploadLink = $('#tiff-show img');
  if (uploadLink === undefined) {
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('src');
  if (link === undefined) {
    console.log('doSignVal no link here');
    return;
  }


  var sourceX = $('#box-x').val();
  var sourceY = $('#box-y').val();
  var sourceWidth = $('#box-w').val();
  var sourceHeight = $('#box-h').val();
  if (sourceX === undefined || sourceX === null || sourceX === '') {
    console.log('No area to cut!');
    return;
  }
  cleanCut();

  $('#cut-ct').css('background', 'rgb(34, 34, 34)');
  if (originalShow) {
    $('#cut-result').attr('width', '' + sourceWidth);
    $('#cut-result').attr('height', '' + sourceHeight);
  }

  // scale = parseFloatH(scale);
  var scale = 1;

  sourceX = parseFloatH(sourceX) / scale;
  sourceY = parseFloatH(sourceY) / scale;

  sourceWidth = parseFloatH(sourceWidth) / scale;
  sourceHeight = parseFloatH(sourceHeight) / scale;

  var cutCanvas = document.getElementById('cut-result');
  var cutContext = cutCanvas.getContext('2d');
  var imageObj = new Image();

  imageObj.onload = function () {
    // draw cropped image

    var destWidth = sourceWidth;
    var destHeight = sourceHeight;
    // var destX = cutCanvas.width / 2 - destWidth / 2;
    // var destY = cutCanvas.height / 2 - destHeight / 2;

    var defW = 460;
    var defH = 260;

    var wScale = 1;
    var hScale = 1;

    if (cutCanvas.width < destWidth) {
      wScale = cutCanvas.width / destWidth;
    }
    if (cutCanvas.height < destHeight) {
      hScale = cutCanvas.height / destHeight;
    }
    if (!originalShow) {
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

function parseFloatH(numStr) {
  var defNum = 0;
  try {
    if (numStr) {
      var num = parseFloat(numStr);
      if (num === NaN) {
        return defNum
      }
      return num;
    }
    return defNum
  } catch (e) {
    return defNum;
  }
}

function createMarkBox(matchArea) {
  if (matchArea === undefined || matchArea === null || matchArea.length === 0) {
    return;
  }
  var scale = getScale();
  for (var i = 0, len = matchArea.length; i < len; i++) {
    var temp = matchArea[i];
    var w = round(parseFloatH(temp.w) * scale, 2);
    var h = round(parseFloatH(temp.h) * scale, 2);
    var x = round(parseFloatH(temp.x) * scale, 2);
    var y = round(parseFloatH(temp.y) * scale, 2);
    var rate = parseFloatH(temp.rate).toFixed(2);
    var nameList = temp.nameList;
    var nameStr = JSON.stringify(nameList);

    var innerText = buildNameStr(nameStr);
    var dataSrc = 'width:' + w + 'px;height:' + h + 'px;left:' + x + 'px;top:' + y + 'px;';
    var dataScale = 'width:' + w + 'px; height:' + h + 'px; left:' + x + 'px; top:' + y + 'px; line-height:' + h + 'px; text-align: center;';
    var box = '<div id="mb-' + i + '" data-x="' + x + '" data-y="' + y + '" data-w="' + w + '" data-h="' + h + '" data-rate="' + rate + '"  onclick="markBoxClick(this)" class="mark-box box-off" style="' + dataScale + '">' + innerText + '</div>';
    $('#tiff-show .mark-ct').append(box);
  }
  setTimeout(function () {
    showTopBox();
  }, 100);
}

function buildNameStr(nameStr) {
  var innerText = "<input type=hidden value='" + nameStr + "'></input>";
  return innerText;
}

function getNameStrHtml(id) {
  var nameStr = $('#' + id + ' input').val();
  return buildNameStr(nameStr);
}

function round(num, scale) {
  return parseFloatH(num.toFixed(scale));
}

function showTopBox() {
  var topId = '';
  var rate = -1;
  var topInnerHtml = '';
  $('.mark-ct > .mark-box').each(function (index) {
    var currentId = $(this).attr('id');
    var currentRate = $(this).attr('data-rate');
    currentRate = parseFloatH(currentRate);
    $(this).addClass('box-off');
    var innerHtml = getNameStrHtml(currentId);
//    console.log('showTopBox innerHtml', innerHtml);

    $(this).html('<i class="fa fa-question-circle" aria-hidden="true"></i>');
    //    $(this).append('<label>'+currentRate+'</label>');
    $(this).append('<label>' + '' + '</label>');
    $(this).append(innerHtml);
    if (currentRate > rate) {
      rate = currentRate;
      topId = currentId;
      topInnerHtml = innerHtml;
    }
  });
  if (topId === '') {
    console.log("no top id found");
    return;
  }
  $('#' + topId).removeClass('box-off');
  $('#' + topId).addClass('box-on');
  $('#' + topId).html('<i class="fa fa-check-circle-o" aria-hidden="true"></i>');
  $('#' + topId).append('<label>' + rate + '</label>');
  $('#' + topId).append(topInnerHtml);
  showNameList(topId);
}

function markBoxClick(e) {
  var id = $(e).attr('id');
  showNameList(id);
  $('.mark-ct > .mark-box').each(function (index) {
    var tempId = $(this).attr('id');
    var currentRate = $(this).attr('data-rate');
    var innerHtml = getNameStrHtml(tempId);
    console.log('markBoxClick innerHtml', innerHtml);

    $(this).addClass('box-off');
    $(this).html('<i class="fa fa-question-circle" aria-hidden="true"></i>');
    $(this).append('<label>' + currentRate + '</label>');
    $(this).append(innerHtml);
    if (tempId === id) {
      $(this).removeClass('box-off');
      $(this).addClass('box-on');
      $(this).html('<i class="fa fa-check-circle-o" aria-hidden="true"></i>');
      $(this).append('<label>' + currentRate + '</label>');
      $(this).append(innerHtml);
    }
  });
}

function showNameList(id) {
  var nameStr = $('#' + id + ' input').val();
//  console.log('showNameList', id);
//  console.log('showNameList', nameStr);
  var nameList = JSON.parse(nameStr);
  cleanCut();
  $('#cut-ct').html('');
  $('#cut-ct').css('background', 'rgb(34, 34, 34)');
  $('#cut-ct').css('color', '#fff');
  if (nameList === undefined || nameList === null || nameList.length === 0) {
    $('#cut-ct').append('No Name matched');
    return;
  }
  for (var i = 0, len = nameList.length; i < len; i++) {
    var name = nameList[i];
    var html = buildName(name, i);
    $('#cut-ct').append(html);
    if (i === 0) {
      printName(name);
    }
  }
}

function buildName(name, i) {
  var nameStr = JSON.stringify(name);
  var clz = i === 0 ? 'name-ct name-active' : 'name-ct';
  var html = "<div class='" + clz + "' data-name='" + nameStr + "' onclick='pickName(this)'>";
  html = html + '<label class="name">' + (name.full ? name.full : '') + '</label>';
  html = html + '<label class="email">' + (name.email ? name.email : '') + '</label>';
  html = html + '<label class="flg"><i class="fa fa-check-circle-o" aria-hidden="true"></i></label>';
  html = html + '</div>';
  return html;
}

function pickName(e) {
  var nameStr = $(e).attr('data-name');
  var name = JSON.parse(nameStr);
  printName(name);
  $('.name-ct').each(function (index) {
    $(this).removeClass('name-active');
  });

  $(e).addClass('name-active');
}


function printName(name) {
  $('#signature-val-result input#name').val(name.full);
  $('#signature-val-result input#email').val(name.email);
  $('#signature-val-result input#writerId').val(name.id);
  $('#signature-val-result input#comments').val(name.comments);
}


function cleanMarkBox() {
  $('#tiff-show .mark-ct').html('');
}

function getScale() {
  var orgW = $("#image-scale").attr('data-org-w');
  var realW = $("#image-scale").attr('data-real-w');
  return parseFloatH(orgW) / parseFloatH(realW);
}
function getPreviewScale() {
  var orgW = $("#recon-pos-ct #recon-image-w").val();
  var realW = $("#syn-signature-max").width();
  return parseFloatH(orgW) / realW;
}



document.onkeyup = function (e) {
  var e = e || window.event; // for IE to cover IEs window event-object
  if (e.ctrlKey && e.altKey && e.which == 65) {
    canvasSwitchClick(e);
    return false;
  }
  if (e.ctrlKey && e.altKey && e.which == 67) {
    cleanRectangle();
    return false;
  }
}

function imagePlus() {
  var src = $("#stew").val();
  var degree = parseFloatH(src);
  degree = degree + 1;
  if (degree > 360) {
    degree = 0;
  } else if (degree < -360) {
    degree = 0;
  }
  $("#stew").val(degree);

  imageStewApply();
}

function imageMinus() {
  var src = $("#stew").val();
  var degree = parseFloatH(src);
  degree = degree - 1;
  if (degree > 360) {
    degree = 0;
  } else if (degree < -360) {
    degree = 0;
  }
  $("#stew").val(degree);
  imageStewApply();
}

function stewPress() {
  var src = $("#stew").val();
  var degree = parseFloatH(src);

  if (degree > 360 || degree < -360) {
    degree = 0;
  }
  $("#stew").val(degree);
  imageStewApply();
}

function imageStewApply() {
  //upload-signature-pre
  var src = $("#stew").val();
  var rotate = "rotate(" + src + "deg)"

  $("#upload-signature-pre").css('-ms-transform', rotate);
  $("#upload-signature-pre").css('-webkit-transform', rotate);
  $("#upload-signature-pre").css('transform', rotate);
}

function imageSave() {
  var src = $("#stew").val();
  var degree = parseFloatH(src);
  if (degree % 360 === 0) {
    showMessage('warning', 'No degree rotate!!!');
    return;
  }

  var transparency = $('#transparency').val();
  //upload-signature-pre
  var imgObj = $('#upload-signature-pre');
  var imgPath = imgObj.attr("data-path");
  var imgName = imgObj.attr("data-sname");
  var url = '/tiny/ocr/image/rotate.do?imgPath=' + imgPath + '&imgName=' + imgName + '&degree=' + degree + "&transparency=" + transparency;

  showLoading();

  $.ajax({
    type: "POST",
    url: url,
    contentType: "application/json",
    success: function (resp) {
      hideLoading();

      if (resp.status) {
        showMessage('success', resp.message);
        beforeSignUpload();

        handleUploadRespBefore(resp);
        handleSinglePageUploadResp(resp);

      } else {
        showMessage('error', resp.message);
      }

    },
    error: function (err) {
      hideLoading();
      showMessage('error', 'Save fail!');

    }
  });
}


function optionSwitch(e) {
  var display = $(".option-ct").css("display");
  console.log('optionSwitch', display);
  if (display !== undefined && display === 'none') {
    $(".option-ct").css("display", "block");
  } else {
    $(".option-ct").css("display", "none");
  }
}

function onWriterChange(element) {
  var id = $(element).val();
  var name = $("#writer-list option:selected").text();

  $('#signature-val-result input#writerId').val(id);
  $('#signature-val-result input#name').val(name);
  $('#signature-val-result input#email').val('');
  $('#signature-val-result input#comments').val('fixed writer id:' + id);
}

/** ****************************************OCR*************************************************** */
function doLayoutRecon() {
  var imgObj = $('#tiff-show-layout img');

  if (imgObj === undefined || imgObj === null) {
    showMessage('error', 'Please upload an image first!!!');
    return;
  }
  var imgPath = imgObj.attr("data-path");
  var imgName = imgObj.attr("data-sname");

  if (imgPath === undefined || imgName === undefined) {
    showMessage('error', 'Please upload an image first!!!');
    return;
  }

  var body = { 'imageWebPath': imgPath, 'reconImage': imgName };
//  $('input#layout-type').val('');
  $('select#layout-type').val('');
  $('div#type-list').html('<button class="dropdown-item" type="button">No Data</button>');
  var url = "/tiny/api/layout/recon.do";
  showLoading();
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(body),
    contentType: "application/json",
    success: function (resp) {
      hideLoading();

      if (resp.status) {
        if (resp.data && resp.data.length > 0) {
          renderTypeList(resp.data);
          showMessage('success', resp.message);
          stepLayoutFinish();
        } else {
          showMessage('warning', 'Cannot recognition layout of this image');
        }
      } else {
        showMessage('error', resp.message);
      }

    },
    error: function (err) {
      hideLoading();
      showMessage('error', 'Layout Recongnition fail!');
    }
  });
}

function renderTypeList(data){
    data.sort(function(a, b){
        var ap = parseFloat(a.probability);
        var bp = parseFloat(b.probability);
        return bp - ap;
    })
     var html = '';
     for(var i = 0; i < data.length; i++){
        var temp = data[i];
        var prob = parseFloat(parseFloat(temp.probability).toFixed(0));
        var tag = temp.tag ? temp.tag : 'Unknown';
        var id = temp.id ? temp.id : 'Unknown';
//        var showMsg = 'Type:' + temp.type + ', tag:' + tag + ', probability:' + prob;
        var showMsg = '' + temp.type + '<div class="probability-bar"><div class="current-prob" style="width: '+prob+'%"></div><div class="prob-text">'+prob+'%</div></div>';
        if(i == 0){
//            $('input#layout-type').val(showMsg);
              $('select#layout-type').val(id);
              markMatchedLayout(id);
        }
        html = html + '<button class="dropdown-item" style="display: flex;" type="button" onclick="applyLayoutRe(this);" data-type="'+temp.type+'" data-tag="'+temp.tag+'" data-id="'+temp.id+'" data-probability="'+temp.probability+'" data-comments="'+temp.comments+'">'+showMsg+'</button>';
     }
     $('div#type-list').html(html);
}


function applyLayoutRe(element){
    var type = $(element).attr('data-type');
    var probability = $(element).attr('data-probability');
    var tag = $(element).attr('data-tag');
    var id = $(element).attr('data-id');
    tag = tag ? tag : 'Unknown';
    id = id ? id : '';
    probability = parseFloat(parseFloat(probability).toFixed(0));
    //var showMsg = 'Type:' + type + ', tag:' + tag + ', probability:' + probability;
    //$('input#layout-type').val(showMsg);
    $('select#layout-type').val(id);
    markMatchedLayout(id);
}

function doContentRecon() {
  var imgObj = $('#tiff-show-layout img');
  if (imgObj === undefined || imgObj === null) {
    showMessage('error', 'Please upload an image first!!!');
    return;
  }
  var imgPath = imgObj.attr("data-path");
  var imgName = imgObj.attr("data-sname");
  if (imgPath === undefined || imgName === undefined) {
    showMessage('error', 'Please upload an image first!!!');
    return;
  }
  var body = { 'imageWebPath': imgPath, 'reconImage': imgName };
  var url = "/tiny/api/detail/recon.do";
  showLoading();
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(body),
    contentType: "application/json",
    success: function (resp) {
      hideLoading();

      if (resp.status) {
        stepContentFinish();
        if (resp.data && resp.data.result && resp.data.result.type === 'table-ly') {
          $('input#content-type').val('table-ly');
          renderTableResult(resp);
          renderReconPos(resp);
          showMessage('success', resp.message);
        } else if (resp.data && resp.data.result && resp.data.result.type === 'grid-ly') {
          $('input#content-type').val('grid-ly');
          renderGridResult(resp);
          renderReconPos(resp);
          showMessage('success', resp.message);
        } else {
          showMessage('warning', 'Unknow result...');
        }

      } else {
        showMessage('error', resp.message);
      }

    },
    error: function (err) {
      hideLoading();
      showMessage('error', 'Content Recongnition fail!');
    }
  });
}

function renderReconPos(resp){
    var result = resp.data.result;
    var reconPosElement = $('#temp-recon-position').clone();
    reconPosElement.attr('id', 'recon-pos-ct');
    reconPosElement.css('display', 'none');
    reconPosElement.appendTo('#ocr-result');
    setTimeout(function(){
              $('#recon-pos-ct #recon-image-w').val(result.width ? result.width : 0);
              $('#recon-pos-ct #recon-image-h').val(result.height ? result.height : 0);
              showAllMark();
    }, 100);
}

function renderTableResult(resp) {
  var result = resp.data.result;
  var tableHtml = '<table class="recon-ct">';
  var isHeader = false;
  var i = 0, len = result.allList.length;
  var colSize = 0;
  var lastRow = false;

    var markHtml = '';
    var previewMarkHtml = '';
    var scalePer = 1;
    var myImg = document.querySelector("#syn-signature-pre");
    var realWidth = myImg.width;
    var realHeight = myImg.height;
    scalePer = realWidth/result.width;

  var previewImg = document.querySelector("#syn-signature-max");
  var realPreviewWidth = previewImg.width;
  var previewScalePer = 1;
  previewScalePer = realPreviewWidth/result.width;


  for (; i < len; i++) {
    isHeader = i === 0;
//    lastRow = i === len - 1;


    var rowList = result.allList[i];
    colSize = rowList.length;
    var rowHtmlResult = renderRowHtml(i, rowList, isHeader, lastRow, scalePer, previewScalePer, result);
    tableHtml = tableHtml + rowHtmlResult.show;
    markHtml = markHtml + rowHtmlResult.mark;
    previewMarkHtml = previewMarkHtml + rowHtmlResult.previewMark;
  }

  var colspan = colSize + 2;

  var lastRowHtml = '<tr><td colspan="' + colspan + '" style="text-align: center;">';
  var submitHtml = '<span data-length="' + len + '" data-col="' + colSize + '" class="opera opera-submit" onclick="reconSubAll(this);">Submit</span>';
  var resetHtml = '<span data-length="' + len + '" data-col="' + colSize + '" class="opera opera-reset" onclick="reconResetAll(this);">Reset</span>';
  lastRowHtml = lastRowHtml + submitHtml + resetHtml + '</td></tr>';

  tableHtml = tableHtml + lastRowHtml;
    $('#tiff-show-layout .cell .mark-ct').html(markHtml);

    $('#dialog-recon-detail .preview-ct').append('<div class="preview-mark-ct">' + previewMarkHtml + '</div>');
  $('#ocr-result').html('<div class="detail-ct table-ly">' + tableHtml + '</table><div class="end-padding"></div></div>');
  appendToolbar();
}

function renderRowHtml(index, rowList, isHeader, lastRow, scalePer, previewScalePer, result) {
  var resultHtml = '';
  var markHtml = '';
  var previewMarkHtml = '';
  if(!isHeader && index == 0){
    console.log('renderRowHtml');
    var thHtml = '<tr class="recon-row"><th>#</th>';
    var colSize = rowList ? rowList.length : 0;
    for(var i = 0; i < colSize; i++){
        var srcId = 'biz-tag-' + i;
        var bizTagHtml = cloneBizTag(srcId, srcId);
        thHtml = thHtml + '<th>' + bizTagHtml + '</th>';
    }
    thHtml = thHtml + '<th>Operation</th></tr>';
    resultHtml = resultHtml + thHtml;
    console.log('renderRowHtml', resultHtml);
  }
  resultHtml = resultHtml + '<tr>';
  if (isHeader) {
    var cellHtml = '<th>#</th>'
    resultHtml = resultHtml + cellHtml;
  } else {
    resultHtml = resultHtml + '<tr class="recon-row" id="rowt-' + index + '">';
    var cellHtml = '<td id="rowi-' + index + '"><div style="width:100%; height:100%; display: flex;"><i style="padding-right: 5px;" class="fa fa-question-circle" aria-hidden="true"></i>' + index + '</div></td>'
    resultHtml = resultHtml + cellHtml;
  }
  var i = 0, len = rowList.length;
  for (; i < len; i++) {
    var cellObj = rowList[i];
    var cellHtmlResult = renderCellHtml(index, i, cellObj, isHeader, scalePer, previewScalePer, result);
    resultHtml = resultHtml + cellHtmlResult.show;
    markHtml = markHtml + cellHtmlResult.mark;
    previewMarkHtml = previewMarkHtml + cellHtmlResult.previewMark;
  }
  if (isHeader) {
    var cellHtml = '<th>Operation</th>'
    resultHtml = resultHtml + cellHtml;
  } else {
    var style = 'border-left: 0px solid transparent; border-top: 0px solid transparent;';
    if(index == 0){
        style = 'border-left: 0px solid transparent;';
    }
    style = lastRow ? style + 'border-bottom: 0px solid transparent;' : style;
    var submitHtml = '<span data-row="' + index + '" data-length="' + len + '" class="opera opera-submit" onclick="reconSub(this);">Submit</span>';
    var resetHtml = '<span data-row="' + index + '" data-length="' + len + '" class="opera opera-reset" onclick="reconReset(this);">Reset</span>';
    var cellHtml = '<td class="row-opt" style="' + style + '"><div>' + submitHtml + resetHtml + '</div></td>'
    resultHtml = resultHtml + cellHtml;
  }

  var showHtml = resultHtml + '</tr>';
  return {'show': showHtml, 'mark': markHtml, 'previewMark': previewMarkHtml};
}

function renderCellHtml(rowIndex, colIndex, cellObj, isHeader, scalePer, previewScalePer, result) {
//onclick="showMarkCell(this);
  var srcId = '' + rowIndex + '-' + colIndex;
  var markResult = renderMarkRect(srcId, cellObj, result, scalePer, previewScalePer);
  var srcText = htmlSpecialChars(cellObj.text);
  var dataInfo = 'data-src="' + srcText + '" data-row="' + rowIndex + '" data-col="' + colIndex + '" data-w="'+cellObj.width+'" data-h="'+cellObj.height+'" data-x="'+cellObj.xmin+'" data-y="'+cellObj.ymin+'"';
  var begin = '<td onclick="showMarkTableCell(this);" '+dataInfo+'>';
  var end = '</td>';
  var value = '';
  if (isHeader) {
    begin = '<th onclick="showMarkTableCell(this);" '+dataInfo+'>';
    end = '</th>';
    value = srcText;
  } else {
    var id = 'pos-' + rowIndex + '-' + colIndex;
//    value = '<input id="' + id + '" type="text" value="' + srcText + '" '+dataInfo+'>'
    value = '<textarea id="' + id + '" type="text" '+dataInfo+'>'+srcText+'</textarea>'
  }
  var html = begin + value + end;
  return {'show': html, 'mark': markResult.mark, 'previewMark': markResult.previewMark};
}


function renderGridResult(resp) {
  var result = resp.data.result;
  var resultHtml = '';
  var markHtml = '';
  var previewMarkHtml = '';
  var scalePer = 1;
  var myImg = document.querySelector("#syn-signature-pre");
  var realWidth = myImg.width;
  var realHeight = myImg.height;
  scalePer = realWidth/result.width;

  var previewImg = document.querySelector("#syn-signature-max");
  var realPreviewWidth = previewImg.width;
  var previewScalePer = 1;
  previewScalePer = realPreviewWidth/result.width;
  
  for (var i = 0, len = result.allList.length; i < len; i++) {
    var rectObj = result.allList[i];
    var rendenResult = renderRectHtml(rectObj, result, scalePer, previewScalePer);
    resultHtml = resultHtml + rendenResult.show;
	markHtml = markHtml + rendenResult.mark;
	previewMarkHtml = previewMarkHtml + rendenResult.previewMark;
  }
  $('#ocr-result').html('<div class="detail-ct grid-ly">' + resultHtml + '</div>');
  $('#tiff-show-layout .cell .mark-ct').html(markHtml);

  $('#dialog-recon-detail .preview-ct').append('<div class="preview-mark-ct">' + previewMarkHtml + '</div>');
  appendToolbar();
}

function renderRectHtml(rectObj, result, scalePer, previewScalePer) {
  var style = '';
  var innerStyle = '';
  var paddingLeft = 0;
  var paddinGTop = 0;
  var paddingRight = 10;
  var paddinGBottom = 10;
  var xOffset = result.beginX - paddingLeft;
  var yOffset = result.beginY - paddinGTop;

  style = style + 'left:' + (rectObj.xmin - xOffset) + 'px;';
  style = style + 'top:' + (rectObj.ymin - yOffset) + 'px;';
  style = style + 'width:' + (rectObj.width + paddingLeft + paddingRight) + 'px;';
  style = style + 'max-width:' + (rectObj.width + paddingLeft + paddingRight) + 'px;';
  style = style + 'height:' + (rectObj.height + paddinGTop + paddinGBottom) + 'px;';

  innerStyle = innerStyle + 'width:' + (rectObj.width) + 'px;';
  innerStyle = innerStyle + 'max-width:' + (rectObj.width) + 'px;';
  innerStyle = innerStyle + 'height:' + (rectObj.height) + 'px;';

  var srcId = rectObj.id ? rectObj.id : sampleGuid();

  var id = 'rect-' + srcId;
  var srcText = htmlSpecialChars(rectObj.text);

  var srcInfo = ' data-id="' + srcId + '" data-src="' + srcText + '" data-xmin="' + rectObj.xmin + '" data-ymin="' + rectObj.ymin + '" data-width="' + rectObj.width + '" data-height="' + rectObj.height + '" ';

  var value = '<textarea id="' + id + '" value="' + srcText + '" ' + srcInfo + '>' + srcText + '</textarea>';

  var submitHtml = '<span data-id="' + id + '"  class="opera opera-submit" onclick="reconRectSub(this);">Submit</span>';
  var resetHtml = '<span data-id="' + id + '"  class="opera opera-reset" onclick="reconRectReset(this);">Reset</span>';
  var bizTagHtml = renderBizTag(rectObj, srcId, id);
  var msgCtHtml = '<span id="msg-' + id + '"  class="opera opera-readonly"></span>';

  var operaHtml = '<div class="opera-ct">' + submitHtml + resetHtml + bizTagHtml + msgCtHtml + '</div>';
  // var value = textFormat(rectObj.text);
  var html = '<div class="rect-ct" '+srcInfo+'  onclick="showMarkCell(this);" style="' + style + '"><div class="rect" style="' + innerStyle + '">' + value + operaHtml + '</div></div>';

  var markResult = renderMarkRect(srcId, rectObj, result, scalePer, previewScalePer);
  
  return {'show': html, 'mark': markResult.mark, 'previewMark': markResult.previewMark};
}

function renderBizTag(rectObj, srcId, id){
//    var html = '<input id="biz-tag-'+srcId+'" type="hidden" data-id="' + id + '"  value=""></input>';
    var html = '';
    var bizTagList = $('#biz-tag-list-temp #biz-tag-list').clone();
    bizTagList.attr('id', 'biz-tag-'+srcId);
    html = html + '<select id="biz-tag-'+srcId+'" data-id="' + srcId + '">' + bizTagList.html() + '</select>';
    return html;
}

function cloneBizTag(srcId, id){
//    var html = '<input id="biz-tag-'+srcId+'" type="hidden" data-id="' + id + '"  value=""></input>';
    var html = '';
    var bizTagList = $('#biz-tag-list-temp #biz-tag-list').clone();
    bizTagList.attr('id', 'biz-tag-'+srcId);
    html = html + '<select id="biz-tag-'+srcId+'" data-id="' + srcId + '">' + bizTagList.html() + '</select>';
    return html;
}

function renderBizTag2(rectObj, srcId, id){
//    var html = '<input id="biz-tag-'+srcId+'" type="hidden" data-id="' + id + '"  value=""></input>';
    var html = '';
    var bizTagList = $('#biz-tag-list-temp #biz-tag-list').clone();
    bizTagList.attr('id', 'biz-tag-'+srcId);
    html = html + '<div id="dropdown-'+srcId+'" class="dropup" tooltip="Choose Tag" flow="up" >';
    html = html + '<button class="dropdown-toggle" data-id="' + srcId + '" type="button" onclick="fixTagDropdown(this);" data-toggle="dropdown" aria-haspopup="true"  aria-expanded="false" style="margin-left: 4px; font-size: 12px; line-height: 14px; border-radius: 4px;">';
    html = html + '<i class="fa fa-tags" aria-hidden="true"></i>';
    html = html + '</button>';
    html = html + '<div class="dropdown-menu" aria-labelledby="dropdownMenu2" style="left:-90px">';
    html = html + '<select id="biz-tag-'+srcId+'" data-id="' + srcId + '">' + bizTagList.html() + '</select>';
    html = html + '<br/><span data-id="' + srcId + '" onclick="unfixTagDropdown(this);" style="padding-left: 10px;"><i class="fa fa-wrench" aria-hidden="true"></i></span></div>';
    html = html + '</div>';
    return html;
}

function fixTagDropdown(element){
    console.log('fixTagDropdown', element);
    var id = $(element).attr('data-id');
    $('#dropdown-'+id + ' button.dropdown-toggle').attr('data-toggle', 'collapse');
}

function unfixTagDropdown(element){
    console.log('unfixTagDropdown', element);
    var id = $(element).attr('data-id');
    $('#'+id + ' button.dropdown-toggle').attr('data-toggle', 'dropdown');
}

function renderMarkRect(srcId, rectObj, result, scalePer, previewScalePer){
	var id = 'rect-src-' + srcId;
	var style = 'left:' + rectObj.xmin*scalePer + 'px; top:' + rectObj.ymin*scalePer + 'px; width:' + rectObj.width*scalePer + 'px; height:' + rectObj.height*scalePer + 'px;';
	var previewStyle = 'left:' + rectObj.xmin*previewScalePer + 'px; top:' + rectObj.ymin*previewScalePer + 'px; width:' + rectObj.width*previewScalePer + 'px; height:' + rectObj.height*previewScalePer + 'px;';

	var html = '<div id="'+id+'" class="cell-ct cell-hide" style="'+style+'">';
	var charList = '';
	for(var i = 0, len = rectObj.charList.length; i < len; i++){
		var charObj = rectObj.charList[i];
		charList = charList + renderCharHtml(charObj, scalePer);
	}
	html = html + charList + '</div>';

	var previewHtml = '<div id="'+id+'" class="cell-ct cell-hide" style="'+previewStyle+'"></div>';
	return {"mark": html, "previewMark": previewHtml};
}

function renderCharHtml(rectObj, scalePer){
	var style = 'left:' + rectObj.xmin*scalePer + 'px; top:' + rectObj.ymin*scalePer + 'px; width:' + rectObj.width*scalePer + 'px; height:' + rectObj.height*scalePer + 'px;'
	var html = '<div class="cell-char" style="'+style+'"></div>';
	return html;
}

function appendToolbar() {
  var cloneToolbar = $('#toolbar-template .toolbar-ct').clone();
  cloneToolbar.attr('id', 'toolbar-form');
  cloneToolbar.appendTo('#ocr-result');
}

function textFormat(text) {
  return text.replace(/\n/g, "<br />");
}


function reconSub(e) {
  console.log('reconSub', e);
  var rowIndex = $(e).attr('data-row');
  var colLen = $(e).attr('data-length');
  if (colLen && colLen > 0 && rowIndex) {
    var detailList = [];
    for (var i = 0; i < colLen; i++) {
      var id = 'pos-' + rowIndex + '-' + i;
      var obj = convertInputObj(id);
      if (obj) {
        detailList.push(obj);
      }
    }
    submitFixRecon(detailList);
  }
}

function convertInputObj(id) {
  var inputElement = $('td textarea#' + id);
  if (inputElement === undefined) {
    return false;
  }
  var value = inputElement.val();
  value = value ? value : '';
  var srcVal = inputElement.attr('data-src');
  var rowIndex = inputElement.attr('data-row');
  var colIndex = inputElement.attr('data-col');
  return { 'id': id, 'fixText': value, 'srcText': srcVal, 'rowIndex': rowIndex, 'colIndex': colIndex };
}


function reconReset(e) {
  console.log('reconSub', e);
  var rowIndex = $(e).attr('data-row');
  var colLen = $(e).attr('data-length');
  if (colLen && colLen > 0 && rowIndex) {
    for (var i = 0; i < colLen; i++) {
      var id = 'pos-' + rowIndex + '-' + i;
      resetTDInput(id);
    }
  }
}

function reconRectSub(e) {
  console.log('reconRectSub', e);
  var detailList = [];
  var id = $(e).attr('data-id');
  var obj = convertTextareaObj(id);
  if (obj) {
    detailList.push(obj);
  }
  console.log('reconRectSub', obj)
  submitFixRecon(detailList);
}

function convertTextareaObj(id) {
  console.log('convertTextareaObj', id);
  var textareaEl = $('textarea#' + id);
  if (textareaEl === undefined) {
    return false;
  }
  var value = textareaEl.val();
  value = value ? value : '';
  var srcVal = textareaEl.attr('data-src');
  var xmin = textareaEl.attr('data-xmin');
  var ymin = textareaEl.attr('data-ymin');
  var width = textareaEl.attr('data-width');
  var height = textareaEl.attr('data-height');
  return { 'id': id, 'fixText': value, 'srcText': srcVal, 'xmin': xmin, 'ymin': ymin, 'width': width, 'height': height };
}

function reconRectReset(e) {
  var textareaId = $(e).attr('data-id');
  var dataSrc = $('#' + textareaId).attr('data-src');
  dataSrc = dataSrc === undefined ? '' : dataSrc;
  $('#' + textareaId).val(dataSrc);
  $('.recon-detail #' + textareaId).val(dataSrc);
}

function reconSubAll(e) {
  var detailList = [];
  $('#ocr-result td textarea').each(function (index) {
    var id = $(this).attr('id');
    var obj = convertInputObj(id);
    if (obj) {
      detailList.push(obj);
    }
  });
  submitFixRecon(detailList);
}

function reconResetAll(e) {
  resetReonTable();
}

function resetReonTable() {
  $('#ocr-result td textarea').each(function (index) {
    var dataSrc = $(this).attr('data-src');
    dataSrc = dataSrc === undefined ? '' : dataSrc;
    $(this).val(dataSrc);
  });
}

function resetReonGrid() {
  $('#ocr-result textarea').each(function (index) {
    var dataSrc = $(this).attr('data-src');
    dataSrc = dataSrc === undefined ? '' : dataSrc;
    $(this).val(dataSrc);
  });
}

function reconRectSubAll() {
  var detailList = [];
  $('#ocr-result textarea').each(function (index) {
    var id = $(this).attr('id');
    var obj = convertTextareaObj(id);
    if (obj) {
      detailList.push(obj);
    }
  });
  submitFixRecon(detailList);
}

function resetTDInput(id) {
//  var inputElement = $('td input#' + id);
  var inputElement = $('td textarea#' + id);
  if (inputElement === undefined) {
    return;
  }
  var dataSrc = inputElement.attr('data-src');
  dataSrc = dataSrc === undefined ? '' : dataSrc;
  inputElement.val(dataSrc);
}

function resetAll() {
  resetReonTable();
  resetReonGrid();
}

function submitAll() {
  var layoutType = $('input#content-type').val();
  if (layoutType && layoutType.length > 0) {
    if (layoutType === 'table-ly') {
      reconSubAll();
    } else {
      reconRectSubAll();
    }
  }
}

function markAsReadonly() {
  $('#ocr-result td textarea').each(function (index) {
    $(this).attr('readonly', 'true');
    $(this).addClass('textarea-readonly');
  });

  $('#ocr-result textarea').each(function (index) {
    $(this).attr('readonly', 'true');
    $(this).addClass('textarea-readonly');
  });
}

function markAsEdit() {
  $('#ocr-result td textarea').each(function (index) {
    $(this).removeAttr("readonly")
    $(this).removeClass('input-readonly');
  });

  $('#ocr-result textarea').each(function (index) {
    $(this).removeAttr("readonly")
    $(this).removeClass('textarea-readonly');
  });
}

function zoomInRecon(scale) {
  var result = 'scale(' + scale + ', ' + scale + ')';
  $('#ocr-result .detail-ct').css("transform", result);
}

function zoomInApply() {
  var scale = $('#zoom-in-ops').val();
  zoomInRecon(scale);
}

function submitFixRecon(detailList) {
  var imgObj = $('#tiff-show-layout img');
  if (imgObj === undefined || imgObj === null) {
    showMessage('error', 'Please upload an image first!!!');
    return;
  }
  var imgPath = imgObj.attr("data-path");
  var imgName = imgObj.attr("data-sname");
  if (imgPath === undefined || imgName === undefined) {
    showMessage('error', 'Please upload an image first!!!');
    return;
  }
  var layoutType = $('input#content-type').val();
  var body = { 'imagePath': imgPath, 'imageName': imgName, 'layoutType': layoutType, 'detailList': detailList };
  var url = "/tiny/api/detail/subRecon.do";
  showLoading();
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(body),
    contentType: "application/json",
    success: function (resp) {
      hideLoading();

      if (resp.status) {
        showMessage('success', resp.message);
        markAlreadySubmit(detailList);
      } else {
        showMessage('error', resp.message);
      }

    },
    error: function (err) {
      hideLoading();
      showMessage('error', 'Submit fixed content Recongnition fail!');
    }
  });
}

function markAlreadySubmit(detailList) {
  var layoutType = $('input#content-type').val();
  if (layoutType === 'table-ly') {
    batchMarkTableSubmit(detailList);
  } else if (layoutType === 'grid-ly') {
    batchMarkGridSubmit(detailList);
  } else {
    showMessage('warning', 'Unknown layout mark!');
  }
}

function batchMarkTableSubmit(detailList) {
  var rowArr = [];
  for (var i = 0, len = detailList.length; i < len; i++) {
    var temp = detailList[i];
    var rowIndex = temp.rowIndex;
    if (rowArr.indexOf(rowIndex) > -1) {
      continue;
    }
    rowArr.push(rowIndex);
    markTableSubmit(rowIndex);
  }
}

function batchMarkGridSubmit(detailList) {
  var idArr = [];
  for (var i = 0, len = detailList.length; i < len; i++) {
    var temp = detailList[i];
    var id = temp.id;
    if (idArr.indexOf(id) > -1) {
      continue;
    }
    idArr.push(id);
    markGridSubmit(id);
  }
}

function markTableSubmit(rowId) {
  $('td#rowi-' + rowId + ' i').attr('class', 'fa fa-check-circle');
  $('tr#rowt-' + rowId).attr('class', 'recon-row recon-row-sub');
  $('td#rowi-' + rowId + ' i').css('color', 'green');
  $('td#rowi-' + rowId + ' div').attr('flow', 'right');
  $('td#rowi-' + rowId + ' div').attr('tooltip', getSubmitMsg());
}

function markGridSubmit(id) {
  var message = getSubmitMsg();
  $('textarea#' + id).css('background', 'lightgreen');
  $('span#msg-' + id).html(message);
  $('span#msg-' + id).attr('flow', 'down');
  $('span#msg-' + id).attr('tooltip', message);
}

function getSubmitMsg() {
  var message = 'Submit at ' + (new Date()).toLocaleString();
  return message;
}

function showMaxRecon() {
  //console.log('showMaxRecon ... ');
  var ocrResultHtml = $('#ocr-result');
  $('#dialog-recon-detail .recon-detail').html('');
//  $('#dialog-recon-detail .recon-image').html('');
  var cloneOcrResult = ocrResultHtml.clone();
  cloneOcrResult.attr('id', 'ocr-result-clone');
  cloneOcrResult.css('height', 'calc(100% - 50px)');
  cloneOcrResult.css('border-radius', '6px');
  cloneOcrResult.css('margin-top', '50px');
  cloneOcrResult.css('padding-top', '0px');
  cloneOcrResult.appendTo('#dialog-recon-detail .recon-detail');
//  showPreviewImg();
    setTimeout(function(){
        var canvasContainer = $('.preview-ct .canvas-container');
        if(canvasContainer && canvasContainer.length > 0){
            console.log('showMaxRecon....has canvasContainer', canvasContainer);
            return;
        }
        var id = 'preview-canvas';
        var width = $('#syn-signature-max').width();
        var height = $('#syn-signature-max').height();
        console.log('showPreviewImg', width);
        console.log('showPreviewImg', height);
        makePreviewCanvas(id, width, height);
        autoScalePreviewMark();
    }, 1000);
}

function showPreviewImg(){
    //syn-signature-pre
    $('#dialog-recon-detail .preview-ct').html('');
    var img = $('img#syn-signature-pre').clone();
    img.attr('id', 'syn-signature-max');
    img.css('margin-bottom', '10px');
    img.css('width', '100%');
    img.appendTo('#dialog-recon-detail .preview-ct');

    //append preview canvas
    var canvasCover = '<canvas id="preview-canvas" width="0" height="0" style="display: none;"></canvas>';
    $('#dialog-recon-detail .preview-ct').append(canvasCover);

//    setTimeout(function(){
//            var id = 'preview-canvas';
//            var width = $('#syn-signature-max').width();
//            var height = $('#syn-signature-max').height();
//            console.log('showPreviewImg', width);
//            console.log('showPreviewImg', height);
//            makePreviewCanvas(id, width, height);
//        }, 1000);

}

function showMarkCell(element){
    var slow = 200;
	hideAllMark();
	hideAllPreviewMark();
	var id = $(element).attr('data-id');
	var width = $(element).attr('data-width');
	var height = $(element).attr('data-height');
	var xmin = $(element).attr('data-xmin');
	var ymin = $(element).attr('data-ymin');
	$('#rect-src-' + id).removeClass('cell-hide');
	$('#rect-src-' + id).addClass('cell-show');

    //
    var cellTop = $('#rect-src-' + id).css('top');
        cellTop = parseFloat(cellTop.replace('px', ''));
     $("#layout-tiff-ct").animate({scrollTop: cellTop},slow);

    var previewTop = $('.preview-mark-ct #rect-src-' + id).css('top');
    previewTop = parseFloat(previewTop.replace('px', ''));
	$('.preview-mark-ct #rect-src-' + id).removeClass('cell-hide');
    $('.preview-mark-ct #rect-src-' + id).addClass('cell-show');

    $(".preview-ct").animate({scrollTop: previewTop},slow);

	$('#recon-pos-ct #recon-box-w').val(width ? width : 0);
	$('#recon-pos-ct #recon-box-h').val(height ? height : 0);
	$('#recon-pos-ct #recon-box-x').val(xmin ? xmin : 0);
	$('#recon-pos-ct #recon-box-y').val(ymin ? ymin : 0);
}

function showMarkTableCell(element){
	var slow = 200;
    hideAllMark();
    hideAllPreviewMark();
	var row = $(element).attr('data-row');
	var col = $(element).attr('data-col');
	var width = $(element).attr('data-w');
	var height = $(element).attr('data-h');
	var xmin = $(element).attr('data-x');
	var ymin = $(element).attr('data-y');
	var id = "" + row + '-' + col;
	$('#rect-src-' + id).removeClass('cell-hide');
	$('#rect-src-' + id).addClass('cell-show');

	var cellTop = $('#rect-src-' + id).css('top');
    cellTop = parseFloat(cellTop.replace('px', ''));
    $("#layout-tiff-ct").animate({scrollTop: cellTop},slow);

    var previewTop = $('.preview-mark-ct #rect-src-' + id).css('top');
    previewTop = parseFloat(previewTop.replace('px', ''));
    $('.preview-mark-ct #rect-src-' + id).removeClass('cell-hide');
    $('.preview-mark-ct #rect-src-' + id).addClass('cell-show');

    $(".preview-ct").animate({scrollTop: previewTop},slow);

	$('#recon-pos-ct #recon-box-w').val(width ? width : 0);
	$('#recon-pos-ct #recon-box-h').val(height ? height : 0);
	$('#recon-pos-ct #recon-box-x').val(xmin ? xmin : 0);
	$('#recon-pos-ct #recon-box-y').val(ymin ? ymin : 0);
}
function hideAllMark(){
	$('#tiff-show-layout .cell .mark-ct .cell-ct').each(function (index){
		$(this).attr('class', 'cell-ct cell-hide');
	});
}

function hideAllPreviewMark(){
	$('#dialog-recon-detail .preview-mark-ct .cell-ct').each(function (index){
		$(this).attr('class', 'cell-ct cell-hide');
	});
}

function showAllMark(){
	$('#tiff-show-layout .cell .mark-ct .cell-ct').each(function (index){
		$(this).attr('class', 'cell-ct cell-show');
	});
}

function stepSignFinish(){
    $('#step-sign').addClass('active');
}

function stepLayoutFinish(){
    $('#step-layout').addClass('active');
}

function stepContentFinish(){
    $('#step-content').addClass('active');
}

function switchLayout(){
    $('#ocr-result .rect-ct').each(function(index){
        var staticDisplay = $(this).hasClass('rect-static');
        if(staticDisplay){
            $(this).removeClass('rect-static');
        }else{
            $(this).addClass('rect-static');
        }
    });
}

function markMatchedLayout(typeId){
    cleanMatchedLayout();
    var item = $('#dialog-image-layout #type-'+typeId);
    console.log('markMatchedLayout', item)
    if(item && item.length > 0){
        item.addClass('ly-match');
    }else{
        $('#dialog-image-layout #type-unknown').addClass('ly-match');
    }
}

function cleanMatchedLayout(){
    $('#dialog-image-layout .layout-view').each(function (index){
        $(this).removeClass('ly-match');
    });
}

/*****************************************************preview auto size **************************************************/
/**
 * fill oppsite
 */
function makePreviewCanvas(id, width, height) {
  $('#' + id).attr('width', '' + width);
  $('#' + id).attr('height', '' + height);
  var offsetTopScroll = 0;
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

  previewCanvas = new fabric.Canvas(id, { selection: false });


  previewCanvas.on('mouse:down', function (o) {
    cleanPreviewRectangleInfo();

    previewCanvas.clear();
    $('.preview-ct .canvas-container .upper-canvas').css('background', fillColor);
    isDown = true;

    var pointer = previewCanvas.getPointer(o.e);
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
    previewCanvas.add(leftRect);
    previewCanvas.add(upRect);
    previewCanvas.add(downRect);
    previewCanvas.add(rightRect);

  });

  previewCanvas.on('mouse:move', function (o) {
    if (!isDown) return;
    $('.preview-ct .canvas-container .upper-canvas').css('background', 'transparent');
    var pointer = previewCanvas.getPointer(o.e);
    var currentX = pointer.x;
    var currentY = pointer.y;

    if (currentX > origX) {
      x1 = origX;
      x2 = currentX;
    } else {
      x2 = origX;
      x1 = currentX;
    }

    if (currentY > origY) {
      y1 = origY;
      y2 = currentY;
    } else {
      y2 = origY;
      y1 = currentY;
    }
    var offset = 0.05;
    leftRect.set({ left: 0, top: 0, width: x1 + offset, height: totalH });
    upRect.set({ left: x1, top: 0, width: x2 - x1, height: y1 });
    downRect.set({ left: x1, top: y2, width: x2 - x1, height: totalH - y2 });
    rightRect.set({ left: x2 - offset, top: 0, width: totalW - x2, height: totalH });


    drawPreviewRectangleInfoXY(x1, y1);
    drawPreviewRectangleInfoWH(x2 - x1, y2 - y1);

    previewCanvas.renderAll();
  });

  previewCanvas.on('mouse:up', function (o) {
    isDown = false;
    //imageCut();
  });
}

function autoScalePreviewMark(){
    var scale = getPreviewScale();
//    console.log('autoScalePreviewMark', scale);
    $('.preview-mark-ct .cell-ct').each(function (index){
//        console.log('autoScalePreviewMark loop', index);
        var width = $(this).css('width').replace('px','');
        var height = $(this).css('height').replace('px','');
        var left = $(this).css('left').replace('px','');
        var top = $(this).css('top').replace('px','');

        $(this).css('width', '' + (parseFloat(width)/scale) + 'px');
        $(this).css('height', '' + (parseFloat(height)/scale) + 'px');
        $(this).css('left', '' + (parseFloat(left)/scale) + 'px');
        $(this).css('top', '' + (parseFloat(top)/scale) + 'px');

    });
}

function htmlSpecialChars(str)
{
    str = str.replace(/&/g, '&amp;');
    str = str.replace(/</g, '&lt;');
    str = str.replace(/>/g, '&gt;');
    str = str.replace(/"/g, '&quot;');
    str = str.replace(/'/g, '&#039;');
    return str;
}