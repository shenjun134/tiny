var DEF_TOOLTIP_DELAY = '{"show":"100", "hide":"3000"}';

function doUpload() {
  console.log('doUpload begin...');
  beforeUpload();
  showLoading();
  var formData = $('#mainUploadForm');
  var newFormData = new FormData(formData[0]);
  var checkType = '';
  var url = '/tiny/ocr/multiUpload.do?checkType=' + checkType;
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
      afterUpload();
      $('#tiff-show').html('');
      $('#tiff-show-next').html('');
      $('#tiff-show').append('<div class="head"><a href="' + resp.data.name + '" title="download"><span>' + resp.data.name + '</span></a><span>' + resp.data.length + '</span></div><label class="show-max" data-target="#dialog-image-cover" data-toggle="modal">Max</label><label id="cover-clean" class="clean" onclick="cleanCoverCanvas()" style="display: none;">Clean</label>');
      var showHtml = '';
      if (resp.data.type === 'image/tiff' || resp.data.type === 'image/tiff') {
        //handleTiff(resp)
        handleTiff2Png(resp);
      } else {
        showHtml = $('<img alt="display-upload-file" />')
          .attr({
            class: 'upload-img',
            width: 'auto',
            height: 'auto',
            src: resp.data.name
          });
        $('#tiff-show').append(
          showHtml
        );
      }

    },
    error: function (err) {
      console.error('tiff err handle', err);
      turnOffScan();
      hideLoading();
    }
  });
}

function beforeUpload(){
  doCoverClear();
  doTempClear();
  turnOffScan();
  $('.tiff-ct').each(function (index){
    $(this).css('background', '#999');
  });
  var message = '<p class="blank">Please upload a tiff image</p>';
  $('#tiff-show').html(message);
  $('#tiff-show-next').html(message);
}

function afterUpload(){
  turnOnScan();
  $('.tiff-ct').each(function (index){
    $(this).css('background', 'rgb(34, 34, 34)');
  });
}


function handleTiff(resp){
  var xhr = new XMLHttpRequest();
  xhr.open('GET', resp.data.name);
  try {
    xhr.responseType = 'arraybuffer';
  } catch (e){
    console.log('before onload ... set responseType error', e);
  }
  xhr.onload = function (e) {
    var tiff = new Tiff({ buffer: xhr.response });
    var pageHtml = '<div class="page-ct">';

    for (var i = 0, len = tiff.countDirectory(); i < len; ++i) {
      var tiffClz = i === 1 ? 'cell on' : 'cell off';
      tiff.setDirectory(i);
      var canvas = tiff.toCanvas();
      var width = canvas.width;
      var height = canvas.height;
      var tiffContainer = document.createElement("div");
      tiffContainer.setAttribute("class", tiffClz);
      tiffContainer.appendChild(canvas);
      if (i === 0) {
        var tiffContainerCover = document.createElement("div");
        tiffContainerCover.setAttribute("class", 'cell on');
        tiffContainerCover.appendChild(canvas);
        $('#tiff-show').append(tiffContainerCover);
      }else{
        $('#tiff-show-next').append(tiffContainer);
      }
      if(i !== 0){
        var pageIndex = i + 1;
        var pageCls = i === 1 ? 'page on' : 'page off';
        var pi = i -1;
        pageHtml = pageHtml + '<label class="' + pageCls + '" onclick="turnPage(' + pi + ')">Page-' + pageIndex + '</label>';
      }
    }
    pageHtml = pageHtml + '</div>';
    $('#tiff-show-next').append(pageHtml);
  };
  xhr.send();
  try {
    xhr.responseType = 'arraybuffer';
  } catch (e){
    console.log('after send ... set responseType error', e);
  }
}


function handleTiff2Png(resp){
  var fileInfo = resp.data;
  if(fileInfo.subFileList === undefined || fileInfo.subFileList === null || fileInfo.subFileList.length === 0){
    console.log('no sub file list...');
    return;
  }
  var pageHtml = '<div class="page-ct">';
  var firstImage = fileInfo.subFileList[0].name;
  var imageStartStr = firstImage.substr(0, firstImage.lastIndexOf('-') + 1);
  var imageEndStr = firstImage.substr(firstImage.lastIndexOf('.'), firstImage.length);
  var pageJumpHtml = '<select class="current-pick">';
  var maxPageShow = 5;
  for(let i = 0, len = fileInfo.subFileList.length; i < len; i++){
    var subTemp = fileInfo.subFileList[i];
    var tiffClz = i === 1 ? 'cell on' : 'cell off';

    if (i === 0) {
      var canvasCover = '<canvas id="cover-canvas" width="0" height="0"></canvas>';
      var image = '<div class="cell on "><img alt="display-upload-file" class="upload-img upload-img-'+i+'"  src="'+subTemp.name+'"/>'+canvasCover+'</div>';
      $('#tiff-show').append(image);
      $('#dialog-image-cover .modal-body').html(image);
    }else if(i === 1){
      var canvasTemp = '<canvas id="temp-canvas" width="0" height="0" style="display: none;"></canvas>';
      var image = '<div class="'+tiffClz+' "><img alt="display-upload-file" class="upload-img upload-img-'+i+'" src="'+subTemp.name+'"/>'+canvasTemp+'<div class="mark-ct"></div></div>';
      $('#tiff-show-next').append(image);
      $('#dialog-image-temp .modal-body').html(image);
    }
    if(i !== 0 && i < maxPageShow + 1){
      var pageIndex = i + 1;
      var pageCls = i === 1 ? 'page off' : 'page off';
      var pi = i -1;
      pageHtml = pageHtml + '<label class="' + pageCls + '" image-type="'+imageEndStr+'" image-data="'+imageStartStr+'" page-index="'+i+'" onclick="turnPage(this)">Page-' + pageIndex + '</label>';
    }
    if(i !== 0){
      var pageIndex = i + 1;
      pageJumpHtml = pageJumpHtml + '<option value="' + i + '">'+ pageIndex + '</option>';
    }
  }
  pageJumpHtml = '<div class="jump-ct"><label class="page" onclick="pageJump(this)" image-type="'+imageEndStr+'" image-data="'+imageStartStr+'">Go to</label>'+  pageJumpHtml + '</select></div>';
  pageHtml = pageHtml + pageJumpHtml + '<label class="show-max" data-target="#dialog-image-temp" data-toggle="modal">Max</label></div><div id="current-p-index">Current:<span>2</span></div><label id="temp-clean" class="clean" onclick="cleanTempCanvas()" style="display: none;">Clean</label>';
  $('#tiff-show-next').append(pageHtml);
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
  doTempClear();
  showLoading();

  var imageUrl = $(element).attr('image-data');
  var imageType = $(element).attr('image-type');
  $('#tiff-show-next img').attr('src', imageUrl + pageIndex + imageType);
  $('#dialog-image-temp img').attr('src', imageUrl + pageIndex + imageType);
  $('#current-p-index span').html(pageIndex + 1);
  setTimeout( function(){
    hideLoading();
  }, 300);
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
  doTempClear();
  var imageUrl = $(element).attr('image-data');
  var imageType = $(element).attr('image-type');
  $('#tiff-show-next img').attr('src', imageUrl + pageIndex + imageType);
  $('#dialog-image-temp img').attr('src', imageUrl + pageIndex + imageType);
  $('#current-p-index span').html(pageIndex + 1);
  setTimeout( function(){
    hideLoading();
  }, 1000);
}

function doCoverScan(){
  var uploadLink = $('#tiff-show a');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if(link === undefined){
    console.log('doCoverScan no link here');
    return;
  }
  doCoverClear();
  showLoading();
  var url = '/tiny/ocr/coverScan.do?fileName=' + link;
  $.ajax({
    url: url,
    type: 'POST',
    cache: false,
    contentType: false,
    processData: false,
    success: function(resp){
      hideLoading();
      if(resp && resp.status && resp.data !== undefined && resp.data !== null){
        $('input[name="recipientUser"]').val(resp.data.recipientUser);
        $('input[name="lineName"]').val(resp.data.receivedFaxLineName);
        $('input[name="date"]').val(resp.data.receivedDateTime);
        $('input[name="totalPage"]').val(resp.data.totalPage);
        $('input[name="faxStatus"]').val(resp.data.faxStatus);
        $('input[name="seqNumber"]').val(resp.data.faxSeqNumber);
        $('input[name="senderInfo"]').val(resp.data.senderInformation);
        showMessage('success', 'Cover scan successfully!');
        drawCoverCanvas();
        validateForm('#cover-result input');
      }else{
        console.error('cover scan fail---', resp);
        showMessage('error', 'Cover scan fail');
      }
    },
    error: function(err){
      hideLoading();
      console.error('Cover scan error---', err);
      showMessage('error', 'Cover scan error');
    }
  });
}

function createMarkBox(fieldList, parentWidth, parentHeight){
  var enableScale = true;
  if(fieldList === undefined || fieldList.length === 0){
    console.log('no mark list here...');
    return;
  }
  var containerWidth = $('#tiff-show-next').width();
  $('#tiff-show-next').attr('data-src-w', '' + parentWidth);
  $('#tiff-show-next').attr('data-src-h', '' + parentHeight);
  var scalePer = 1;
  if(enableScale){
    scalePer = containerWidth / parseInt(parentWidth);
  }
  console.log('begin to scale - containerWidth', containerWidth);
  console.log('begin to scale - parentWidth', parentWidth);
  console.log('begin to scale - scalePer', scalePer);
  cleanMarkBox();

  var size = 3;
  var offsetY = -1 * size;
  var offsetX = -1 * size;
  var offsetW = 1 * 2 * size;
  var offsetH = 1 * size;

  // var offsetY = 0;
  // var offsetX = 0;
  // var offsetW = 0;
  // var offsetH = 0;
  var markHtml = '';
  for(let i = 0, len = fieldList.length; i < len; i++){
    var temp = fieldList[i];
    if(temp.width === 0 || temp.width === '0' ){
      console.log('mark box is no width');
      continue;
    }
    var id = temp.id;
    var width = Math.floor(temp.width * scalePer) + offsetW;
    var height = Math.floor(temp.height * scalePer) + offsetH;
    var x = Math.floor(temp.x * scalePer) + offsetX;
    var y = Math.floor(temp.y * scalePer) + offsetY;
    var innderText = '';
    var dataSrc = 'width:' + temp.width + 'px;height:' + temp.height + 'px;left:' + temp.x + 'px;top:' + temp.y + 'px;';
    var dataScale = 'width:' + width + 'px; height:' + height + 'px; left:' + x + 'px; top:' + y + 'px; opacity: 0;';
    var box = '<div id="mb-' + id + '" data-x="' + temp.x + '" data-y="'+temp.y+'" data-w="'+temp.width+'" data-h="'+temp.height+'" class="mark-box" style="' + dataScale + '">'+innderText+'</div>';
    $('#tiff-show-next .mark-ct').append(box);
  }
  //mark-ct
}

$( window ).resize(function(){
  var containerWidth = $('#tiff-show-next').width();
  console.log('mark box ct resize event', containerWidth);
  var parentWidth = $('#tiff-show-next').attr('data-src-w');
  var parentHeight = $('#tiff-show-next').attr('data-src-h');
  if(parentWidth === undefined || parentWidth === 0 || parentWidth === ''){
    console.log('no parent mark box ct here...');
    return;
  }
  var enableScale = true;
  var scalePer = 1;
  if(enableScale){
    scalePer = containerWidth / parseInt(parentWidth);
  }
  var size = 3;
  var offsetY = -1 * size;
  var offsetX = -1 * size;
  var offsetW = 1 * 2 * size;
  var offsetH = 1 * size;

  $('#tiff-show-next .mark-box ').each(function (index){
    var srcW = $(this).attr('data-w');
    var srcH = $(this).attr('data-h');
    var srcX = $(this).attr('data-x');
    var srcY = $(this).attr('data-y');

    var width = Math.floor(srcW * scalePer) + offsetW;
    var height = Math.floor(srcH * scalePer) + offsetH;
    var x = Math.floor(srcX * scalePer) + offsetX;
    var y = Math.floor(srcY * scalePer) + offsetY;

    $(this).css('width', '' + width + 'px');
    $(this).css('height', '' + height + 'px');
    $(this).css('top', '' + y + 'px');
    $(this).css('left', '' + x + 'px');
  });
});


function backupScanResult(fieldList){
   if(fieldList === undefined || fieldList.length === 0){
    console.log('no field list here...');
    return;
  }
  $('#temp-scan-result').html('');
  for(var i = 0, len = fieldList.length; i < len; i++){
    var temp = fieldList[i];
    var id = 'src-' + temp.id;
    var value = temp.value;
    var inputHtml = '<input id="'+id+'" value="'+value+'">';
    $('#temp-scan-result').append(inputHtml);
  }

}

function cleanMarkBox(){
  $('#tiff-show-next .mark-ct').html('');
  $('#temp-click-mark').addClass('btn-off');
}


function doCoverClear(){
  $('#cover-result input').each(function (index){
    $(this).val('');
  });
  $('#cover-clean').css('display', 'none');
  cleanValidateResult('#cover-result input');
}



function doTempScan(){
  var uploadLink = $('#tiff-show a');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if(link === undefined){
    console.log('doTempScan no link here');
    return;
  }

  //var page = $('#tiff-show-next label.page.on');
  var page = $('#current-p-index span');
  if (page == undefined && page.html() === undefined)
  {
    console.log('no page info here');
    return;
  }

  showLoading()
  doTempClear();
  var tempName = $('#scan-template-name');
  if (tempName == undefined || tempName.html() == undefined)
  {
    console.log('no temp name here')
    return;
  }

  var url = '/tiny/ocr/tempScan.do?fileName=' + link + "&page=" + page.html() + '&tempName=' + tempName.html();

  $.ajax({
    url: url,
    type: 'POST',
    cache: false,
    contentType: false,
    processData: false,
    success: function(resp){
      hideLoading();
      if(resp && resp.status && resp.data !== undefined && resp.data !== null){
          var temp = resp.data.temp;
          if(temp.fieldList && temp.fieldList.length > 0){
            for(var i = 0, len = temp.fieldList.length; i < len; i++){
              var field = temp.fieldList[i];
              $('input[name="'+field.id+'"]').val(field.value);
              $('input[name="'+field.id+'"]').attr('data-src', field.value);
            }
          }
          showMessage('success', 'Template scan successfully');
          createMarkBox(temp.fieldList, resp.data.parentWidth, resp.data.parentHeight);
          drawTempCanvas();
          validateForm('#scan-temp-ct input');
          //backupScanResult(temp.fieldList);
      }else{
        console.error('temp scan fail---', resp);
        showMessage('error', 'Template scan fail');
      }
    },
    error: function(err){
      hideLoading();
      console.error('temp scan error---', err);
      showMessage('error', 'Template scan error');
    }
  });
}


function doTempClear(){
  $('#scan-temp-ct input').each(function (index){
    $(this).val('');
  });
  cleanMarkBox();
  cleanTempSubmit();
}


function doTempSubmit(){
  var uploadLink = $('#tiff-show a');
  if(uploadLink === undefined){
    console.log('no file upload here');
    return;
  }
  var link = uploadLink.attr('href');
  if(link === undefined){
    console.log('doTempSubmit no link here');
    return;
  }
  cleanTempSubmit();
  var validateResult = validateForm('#scan-temp-ct input');
  if(!validateResult.status){
    if(validateResult.failCount === 1){
      $('#submit-result').append('<span class="result-error">Error: there is an invaild input!!!</span>');
    }else{
      $('#submit-result').append('<span class="result-error">Error: there are ' + validateResult.failCount + ' invaild inputs!!!</span>');
    }
    return;
  }
  showLoading();

  var fieldList = [];
  $('#scan-temp-ct input').each(function (index){
    var value = $(this).val();
    var id = $(this).attr('id');
    var srcValue = $(this).attr('data-src');
    if(value === undefined){
      value = '';
    }
    fieldList.push({'value': value, 'id': id});
    if(srcValue && srcValue.length > 0){
      fieldList.push({'value': srcValue, 'id': 'src-' + id});
    }
  });

  //  $('#temp-scan-result input').each(function (index){
  //   var value = $(this).val();
  //   var id = $(this).attr('id');
  //   if(value === undefined){
  //     value = '';
  //   }
  //   fieldList.push({'value': value, 'id': id});
  // });

  var tempKey = $('#templateName').val();

  var url = '/tiny/ocr/tempSub.do?fileName=' + link;
  $.ajax({
           type: "POST",
           url: url,
           contentType: "application/json",
           data: JSON.stringify({tempKey: tempKey, fieldList: fieldList}),
           dataType : 'json',
           success: function(resp)
           {
              hideLoading();
              if(resp && resp.status){
                $('#submit-result').append('<span class="result-succ">Successfully!</span>');
              }else{
                $('#submit-result').append('<span class="result-error">Fail! -- '+resp.message+'</span>');
              }
           },
           error: function(err){
             hideLoading();
             $('#submit-result').append('<span class="result-error">' + err + '</span>');
           }
    });
}

function cleanTempSubmit(){
  $('#submit-result').html('');
  cleanValidateResult('#scan-temp-ct input');
}


function clickMark(element){
  var classStr = $(element).attr('class');
  var markOn = false;
  var opacity = 0;
  var buttonText = 'Show Mark';
  if(classStr && classStr.indexOf('btn-off') > -1){
    markOn =  true;
    buttonText = 'Hide  Mark';
    opacity = 1;
  }
  var markBoxList = $('#tiff-show-next .mark-ct .mark-box');
  if(markBoxList === undefined || markBoxList.length === 0){
    console.log('no mark box here...');
    return;
  }
  markBoxList.each(function( index ){
    $(this).css('opacity', opacity);
  });
  if(markOn){
    $(element).removeClass('btn-off');
    $(element).html(buttonText);
    showTempCanvas();
  }else{
    $(element).addClass('btn-off');
    $(element).html(buttonText);
    hideTempCanvas();
  }
}

function turnOnScan(){
  $('button#cover-scan').removeClass('btn-off');
  $('button#temp-scan').removeClass('btn-off');
  $('button#temp-submit').removeClass('btn-off');
}

function turnOffScan(){
  turnOnScan();
  $('button#cover-scan').addClass('btn-off');
  $('button#temp-scan').addClass('btn-off');
  $('button#temp-submit').addClass('btn-off');
  $('#temp-click-mark').addClass('btn-off');
  $('#submit-result').html('');
}


function doNext(){
  console.log('do next begin....');
  var liWidth = $('#th-main').width();
  $('#th-main').attr('class', '');
  $('#th-template').attr('class', 'on');

  $('#tc-main').css('display', 'none');
  $('#tc-template').css('display', 'block');

  $('.contents .title-list p').stop(false, true).animate({
					'left' : 1 * liWidth + 'px'
				}, 300);
  console.log('do next end.');
}


$(function () {
		$('#templateName').html('');
    $('#scan-temp-ct').html('');
    $('#scan-template-name').html('');


    $(".scan-temp-tb").each(function( index ) {
      var value = $(this).attr('data-key');
      var label = $(this).attr('data-name');
      var option = '<option value="'+value+'">' + label + '</option>';
      $('#templateName').append(option);
      if(index === 0){
        $('#scan-temp-ct').html($(this).html());
        $('#scan-template-name').html(label);
      }
    });
});

function onTempChange(element){
  $('#scan-temp-ct').html('');
  $('#scan-template-name').html('');

  var id = $(element).val();
  var name = $("#templateName option:selected").text();
  $('#scan-temp-ct').append('<form id="tempSubForm" action="/tiny/ocr/tempSub.do" method="POST">' + $('#' + id).html() + '</form>');
  $('#scan-template-name').append(name);
}


function showMarkBox(element){
  var id = 'mb-' + $(element).attr('id');
  $('#' + id).css('opacity', '1');
}


function hideMarkBox(element){
  var id = 'mb-' + $(element).attr('id');
  $('#' + id).css('opacity', '0');
}

$("#scan-temp-ct input").click(function() {
  $('#tiff-show-next .mark-ct .mark-box').each(function( index ){
    $(this).css('opacity', '0');
  });

  var id = 'mb-' + $(this).attr('id');
  $('#' + id).css('opacity', '1');
  console.log('#scan-temp-ct input click', id);
});


$("#scan-temp-ct input").blur(function() {
  var id = 'mb-' + $(this).attr('id');
  $('#' + id).css('opacity', '0');
  console.log('#scan-temp-ct input blur', id);
});


function drawCoverCanvas(){
  setTimeout( function (){
    var selector = '#tiff-show img';
    var width = $(selector).width();
    var height = $(selector).height();
    var offsetX = 585;
    var offsetY = 130;

    makeCanvas('cover-canvas', width, height, offsetX, offsetY, 'cover-tiff-ct');
    $('#cover-clean').css('display', 'block');

    console.log('drawCoverCanvas selector', selector);
    console.log('drawCoverCanvas width', width);
    console.log('drawCoverCanvas height', height);
  }, 200);
}

function drawTempCanvas(){
   setTimeout( function (){
    var selector = '#tiff-show-next img';
    var width = $(selector).width();
    var height = $(selector).height();

    var offsetX = 585;
    var offsetY = 130;
    makeCanvas('temp-canvas', width, height, offsetX, offsetY, 'temp-tiff-ct');

    console.log('drawTempCanvas selector', selector);
    console.log('drawTempCanvas width', width);
    console.log('drawTempCanvas height', height);
  }, 200);
}


function cleanCoverCanvas(){
  cleanCanvas('cover-canvas');
}

function cleanTempCanvas(){
  cleanCanvas('temp-canvas');
}

function showTempCanvas(){
  $('#temp-canvas').css('display', 'block');
  $('#temp-clean').css('display', 'block');
}

function hideTempCanvas(){
  $('#temp-canvas').css('display', 'none');
  $('#temp-clean').css('display', 'none');

}

function cleanCanvas(id){
  var el = document.getElementById(id);
  var ctx = el.getContext('2d');
  ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
}

function makeCanvas(id, width, height, offsetX, offsetY, containerDiv){
  $('#' + id).attr('width', '' + width);
  $('#' + id).attr('height', '' + height);

  var el = document.getElementById(id);
  var ctx = el.getContext('2d');
  ctx.strokeStyle = 'darkred';
  ctx.lineWidth = 3;
  ctx.lineJoin = ctx.lineCap = 'round';
  var offsetTopScroll = 0;


  var isDrawing, points = [ ];

  el.onmousedown = function(e) {
     offsetTopScroll = $('#' + containerDiv).scrollTop();
    if(offsetTopScroll === undefined){
      offsetTopScroll = 0;
    }
    console.log('onmousedown-' + containerDiv, offsetTopScroll);
    isDrawing = true;
    points.push({ x: e.clientX - offsetX, y: e.clientY - offsetY + offsetTopScroll });
  };

  el.onmousemove = function(e) {
    if (!isDrawing) return;

    points.push({ x: e.clientX - offsetX, y: e.clientY - offsetY + offsetTopScroll });

    //ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);

    var p1 = points[0];
    var p2 = points[1];

    ctx.beginPath();
    ctx.moveTo(p1.x, p1.y);
    //console.log(points);

    for (var i = 1, len = points.length; i < len; i++) {
      // we pick the point between pi+1 & pi+2 as the
      // end point and p1 as our control point
      var midPoint = midPointBtw(p1, p2);
      ctx.quadraticCurveTo(p1.x, p1.y, midPoint.x, midPoint.y);
      p1 = points[i];
      p2 = points[i+1];
    }
    // Draw last line as a straight line while
    // we wait for the next point to be able to calculate
    // the bezier control point
    ctx.lineTo(p1.x, p1.y);
    ctx.stroke();
  };

  el.onmouseup = function() {
    isDrawing = false;
    points.length = 0;
  };
}


function midPointBtw(p1, p2) {
  return {
    x: p1.x + (p2.x - p1.x) / 2,
    y: p1.y + (p2.y - p1.y) / 2
  };
}


function cleanValidateResult(inputSelector){
  $(inputSelector).each(function (index){
     markNormal(this);
  });
}

function validateForm(inputSelector){
  var result = true;
  var failCount = 0;
  $(inputSelector).each(function (index){
    var type = $(this).attr('data-type');
    var rule = $(this).attr('data-rule');
    var maxLength = $(this).attr('data-max-len');
    var minLength = $(this).attr('data-min-len');
    var required = $(this).attr('data-required');
    var value = $(this).val();
    type = (type === undefined || type === null) ? 'string' : type;
    var matchResult =  true;
    if(required && required === 'true'){
      if(value === undefined || value === null || value.length === 0){
        markError(this, 'Value is required!', failCount);
        result = false;
        failCount = failCount + 1;
        return;
      }
    }
    matchResult = fixLength(value, maxLength === minLength ? minLength : '');
    if(!matchResult){
       markError(this, 'Length must equal to ' + minLength + '!', failCount);
       result = false;
       failCount = failCount + 1;
       return;
    }

    matchResult = lengthMax(value, maxLength);
    if(!matchResult){
       markError(this, 'Length is more than ' + maxLength + '!', failCount);
       result = false;
       failCount = failCount + 1;
       return;
    }
    matchResult = lengthMin(value, minLength);
    if(!matchResult){
        markError(this, 'Length is less than ' + minLength + '!', failCount);
       result = false;
       failCount = failCount + 1;
       return;
    }
    if(type === 'number'){
      matchResult = isNumber(value);
      if(!matchResult){
        markError(this, 'Value is not a number!', failCount);
        result = false;
        failCount = failCount + 1;
        return;
      }
    }
    if(type === 'date'){
      matchResult = isDate(value);
      if(!matchResult){
        markError(this, 'Value is not a date!', failCount);
        result = false;
        failCount = failCount + 1;
        return;
      }
    }
    if(type === 'currency'){
      matchResult = isCurrency(value);
      if(!matchResult){
        markError(this, 'Value is not a currency!', failCount);
        result = false;
        failCount = failCount + 1;
        return;
      }
    }
    if(type === 'phone'){
      matchResult = isPhone(value);
      if(!matchResult){
        markError(this, 'Value is not a phone!', failCount);
        result = false;
        failCount = failCount + 1;
        return;
      }
    }
    if(type === 'email'){
      matchResult = isEmail(value);
      if(!matchResult){
        markError(this, 'Value is not a email!', failCount);
        result = false;
        failCount = failCount + 1;
        return;
      }
    }

    matchResult = ruleMatch(value, rule);
    if(!matchResult){
      markError(this, 'Value is not satisfied with rule!', failCount);
      result = false;
      failCount = failCount + 1;
      return;
    }
  });
  return {'status': result, 'failCount': failCount};

}

function validateInput(element){
    markNormal(element);
    var type = $(element).attr('data-type');
    var rule = $(element).attr('data-rule');
    var maxLength = $(element).attr('data-max-len');
    var minLength = $(element).attr('data-min-len');
    var required = $(element).attr('data-required');
    var value = $(element).val();
    type = (type === undefined || type === null) ? 'string' : type;
    var matchResult =  true;
    var failCount = -1;
    if(required && required === 'true'){
      if(value === undefined || value === null || value.length === 0){
        markError(element, 'Value is required!', failCount);
        return;
      }
    }
    matchResult = fixLength(value, maxLength === minLength ? minLength : '');
    if(!matchResult){
      markError(element, 'Length must equal to ' + minLength + '!', failCount);
      return;
    }

    matchResult = lengthMax(value, maxLength);
    if(!matchResult){
      markError(element, 'Length is more than ' + maxLength + '!', failCount);
      return;
    }
    matchResult = lengthMin(value, minLength);
    if(!matchResult){
      markError(element, 'Length is less than ' + minLength + '!', failCount);
      return;
    }
    if(type === 'number'){
      matchResult = isNumber(value);
      if(!matchResult){
        markError(element, 'Value is not a number!', failCount);
        return;
      }
    }
    if(type === 'date'){
      matchResult = isDate(value);
      if(!matchResult){
        markError(element, 'Value is not a date!', failCount);
        return;
      }
    }
    if(type === 'currency'){
      matchResult = isCurrency(value);
      if(!matchResult){
        markError(element, 'Value is not a currency!', failCount);
        return;
      }
    }
    if(type === 'phone'){
      matchResult = isPhone(value);
      if(!matchResult){
        markError(element, 'Value is not a phone!', failCount);
        return;
      }
    }
    if(type === 'email'){
      matchResult = isEmail(value);
      if(!matchResult){
        markError(element, 'Value is not a email!', failCount);
        return;
      }
    }

    matchResult = ruleMatch(value, rule);
    if(!matchResult){
      markError(element, 'Value is not satisfied with rule!', failCount);
      return;
    }
}

function markError(element, message, failIndex){

  $(element).addClass('error');
  // $(element).attr('title', message);
  // $(element).attr('data-toggle', 'tooltip');
  // $(element).attr('data-placement', 'top');
  // $(element).attr('data-delay', DEF_TOOLTIP_DELAY);

  var id = 'err-' + $(element).attr('id');
  console.log('markError element', element);
  console.log('markError id', id);
  $('.left #' + id).html(message);
  $('.left #' + id).css('display', 'block');

  if(failIndex === 0){
    $(element).blur();
    $(element).focus();
  }
}

function markNormal(element){
  $(element).removeClass('error');
  // $(element).attr('title', '');
  // $(element).attr('data-toggle', '');
  // $(element).attr('data-placement', '');

  var id = 'err-' + $(element).attr('id');
  $('.left #' + id).html('');
  $('.left #' + id).css('display', 'none');
}

function fixLength(val, len){
  if(len && len.trim().length > 0){
    return parseInt(len) === val.length;
  }
  return true;
}

function lengthMax(val, maxLen){
  if(maxLen && maxLen.trim().length > 0){
    return parseInt(maxLen) >= val.length;
  }
  return true;
}

function lengthMin(val, minLen){
  if(minLen && minLen.trim().length > 0){
    return parseInt(minLen) <= val.length;
  }
  return true;
}


function ruleMatch(val, rule){
  if(rule && rule.length > 0){
    return rule.test(val);
  }
  return true;
}

function isNumber(val){
  if(val === undefined || val.length === 0){
    return true;
  }
  var reg = /^\d+(\.{1}\d+)?$/;
  var result = reg.test(val);
  if(!result){
    return reg.test(val.replace(/,/g, ''));
  }
  return result;
}

function isDate(val){
  // var reg = /^\d+(\.{1}\d+)?$/;
  // return reg.test(val);
  if(val && val.length > 0){
    var arr = val.split(" ");
    if(arr.length > 3){
      return false;
    }
  }

  return true;
}

function isCurrency(val){
  if(val && val.length === 3){
    return true;
  }
  return false;
}

function isPhone(val){
  // var reg = /^\d+(\.{1}\d+)?$/;
  // return reg.test(val);
   return true;
}

function isEmail(val){
  var reg =  /w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*/;
  return reg.test(val);
}


function isPostCode(val){
  // var reg = /^\d+(\.{1}\d+)?$/;
  // return reg.test(val);
  return true;
}

