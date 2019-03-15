var selectWidthScale = true;

function cleanMatchSign() {
  $('#recon-sign-result #area-record').html('');
}

function appendSignAdd() {
  var signAddElement = $('#sign-template .temp-add-sign').clone()
  // signAddElement.attr('class', '')
  signAddElement.appendTo('#recon-sign-result #area-record')
}

function appendMatchSign(matchArea, index, newArea) {
  var strGuid = sampleGuid();
  var nameList = matchArea.nameList;
  var signShowElement = $('#sign-template .temp-show-sign').clone();
  signShowElement.attr('id', strGuid);
  signShowElement.attr('data-name-id', nameList[0].id);
  signShowElement.attr('data-name-id-src', nameList[0].id);
  signShowElement
    .find('.area-index')
    .each(function () {
      $(this).html(index);
    });
  signShowElement
    .find('.recon-area-ct .area-label')
    .each(function () {
      $(this).html(nameList[0].full);
    });
  signShowElement
    .find('.recon-area-ct .area-sub-label')
    .each(function () {
      $(this).html(nameList[0].email);
    });
  signShowElement
    .find('.area-action')
    .each(function () {
      $(this).attr('data-index', strGuid);
    });
  if (newArea) {
    signShowElement.attr('data-new', 'true');
    signShowElement.insertBefore($('#recon-sign-result .temp-add-sign'))
  } else {
    signShowElement.appendTo('#recon-sign-result #area-record')
  }

  var imageSelector = '#recon-sign-ct .preview-ct img';
  var scale = getScale(imageSelector);
  appendAreaBox(matchArea, strGuid, scale, newArea, index);
  renderNameList(matchArea.nameList, strGuid);
}

function renderMatchSign(matchAreaList) {
  // console.log('renderMatchSign', matchAreaList.length)
  cleanMatchSign();
  updateMatchCount(matchAreaList ? matchAreaList.length : 0);
  if (matchAreaList && matchAreaList.length > 0) {
    for (var i = 0; i < matchAreaList.length; i++) {
      var matchArea = matchAreaList[i]
      appendMatchSign(matchArea, i + 1)
    }
  }
  appendSignAdd();
}

function updateMatchCount(matchCount) {
  $('#recon-sign-result #match-count').html(matchCount);
}

function updateValidateId(resp) {
  $('#recon-sign-result input#validate-id').val(resp.data.faxId ? resp.data.faxId : '');
}

function renderNameList(nameList, strGuid) {
  if (nameList && nameList.length > 0) {
    nameList.sort(function (a, b) {
      var ap = parseFloat(a.rate);
      var bp = parseFloat(b.rate);
      return bp - ap;
    });
    var html = '<div class="matched-name-ct" id="' + strGuid + '">';
    for (var i = 0; i < nameList.length; i++) {
      var nameInfo = nameList[i];
      html = html + renderNameArea(nameInfo, strGuid);
    }
    html = html + '</div>';
    $('#sign-template').append(html);
  }
}

function renderNameArea(nameInfo, strGuid) {
  var nameHtml = '<span class="label-name">' + nameInfo.full + '</span>';
  var rateHtml = '<span class="label-rate">' + nameInfo.rate + '%</span>';
  var html = '<div class="matched-name" onclick="pickName(this);" data-index="' + strGuid + '" data-name-id="' + nameInfo.id + '" >' + nameHtml + rateHtml + '</div>';
  return html;
}

function pickName(e) {
  var strGuid = $(e).attr('data-index');
  var nameId = $(e).attr('data-name-id');
  var nameInfo = getNameInfo(nameId);
  updateName(strGuid, nameInfo);
  closeConfirm();
}

function rollbackMatchName(strGuid) {
  var signShowElement = $('#recon-sign-result #area-record #' + strGuid);
  var nameId = signShowElement.attr('data-name-id-src');
  if (nameId) {
    var nameInfo = getNameInfo(nameId);
    updateName(strGuid, nameInfo);
  }
}

function onSignatureChange(e) {
  var strGuid = $(e).attr('data-index');
  var nameId = $(e).val();
  var nameInfo = getNameInfo(nameId);
  updateName(strGuid, nameInfo);
}

function updateName(strGuid, nameInfo) {
  $('#recon-sign-result #area-record #' + strGuid).attr('data-name-id', nameInfo.id);
  $('#recon-sign-result #area-record #' + strGuid + ' .recon-area-ct .area-label').html(nameInfo.full);
  $('#recon-sign-result #area-record #' + strGuid + ' .recon-area-ct .area-sub-label').html(nameInfo.email);
}

function getNameInfo(nameId) {
  var nameCtSelector = '#sign-template .writer-list > #' + nameId;
  var full = $(nameCtSelector + ' #full').html();
  var email = $(nameCtSelector + ' #email').html();
  //TODO add first, second, ...
  return { 'full': full, 'email': email, 'id': nameId };
}

function refreshMatchSignIndex() {
  var index = 1;
  $('#recon-sign-result .temp-show-sign').each(function () {
    var displayStr = $(this).css('display')
    if (displayStr === 'none') {
      return
    }
    var strGuid = $(this).attr('id');
    $(this)
      .find('.area-index')
      .each(function () {
        $(this).html(index)
        synAreaBoxIndex(strGuid, index);
      })
    index = index + 1
  });
  $('#recon-sign-result .scroll-ct').css('overflow', 'auto');
  setTimeout(function () {
    applyOverflowScroll4Sign();
  }, 50);
}

function applyOverflowScroll4Sign() {
  var scrollWidth = $('#recon-sign-result .scroll-ct').prop('scrollWidth');
  var width = $('#recon-sign-result .scroll-ct').width();
  $('#recon-sign-result .scroll-ct').css('overflow', 'hidden');
  // console.log('applyOverflowScroll4Sign scrollWidth:' + scrollWidth + ', width:' + width);
  if (parseFloat('' + scrollWidth) > parseFloat('' + width)) {
    console.log('applyOverflowScroll4Sign apply');
    updateNiceScroll();
  }
}

function disableAllAreaRow() {
  $('#recon-sign-result .temp-show-sign').each(function () {
    $(this).removeClass('area-row-active');
  });
}

function addMatchArea(matchArea) {
  // console.log('addMatchArea', matchArea)
  appendMatchSign(matchArea, 0, true)
  refreshMatchSignIndex()
}

function override(e) {
  var strGuid = $(e).attr('data-index');
  var nameId = $('#recon-sign-result #area-record #' + strGuid).attr('data-name-id');
  var matchedNameList = $('#sign-template #' + strGuid).clone();
  var cloneNameSelect = $('#sign-template .sign-name-ct').clone();
  cloneNameSelect.find('select').each(function () {
    $(this).attr('data-index', strGuid);
    $(this).val(nameId);
  });
  cloneNameSelect.append(matchedNameList);
  $.confirm({
    theme: 'black',
    animation: 'rotateY',
    closeAnimation: 'rotateY',
    title: 'Select a Name!',
    backgroundDismiss: true,
    content: cloneNameSelect,
    buttons: false,
  });
  setTimeout(function () {
    $(".jconfirm-box-container .custom-select").customselect();
  }, 100);
}

function areaRowClick(e) {
  var strGuid = $(e).attr('id');
  disableAllAreaRow();
  $(e).addClass('area-row-active');
  activeAreaBox4Sign(strGuid);
}

function disgard(e) {
  // console.log('disgard', e);
  var strGuid = $(e).attr('data-index');
  showDisgradConfirm(strGuid);
}

function doDisgrad(strGuid) {
  var selector = '#recon-sign-result #area-record #' + strGuid;
  var trElement = $(selector);
  if (trElement) {
    trElement.css('display', 'none');
  }
  disgradAreaBox(strGuid);
  refreshMatchSignIndex()
}

function showDisgradConfirm(strGuid) {
  $.confirm({
    theme: 'black',
    title: false,
    content: '<div style="margin: 20px 0px; font-size: 16px; text-align: center;">Disgard the match?</div>',
    buttons: {
      Cancel: function () {
        console.log('Cancel disgard')
      },
      Yes: {
        text: 'Yes',
        btnClass: 'btn-danger',
        action: function () {
          doDisgrad(strGuid)
        }
      }
    }
  })
}

function doReconSign() {
  showLoading()
  var link = ''
  var imgObj = $('#recon-sign-ct img')
  var imgPath = imgObj.attr('data-path')
  var urlPrefix = imgObj.attr('data-sign-validate-url');
  imgPath = imgPath.substring(0, imgPath.lastIndexOf('/'))
  var imgName = imgObj.attr('data-name')
  var url = urlPrefix + '?fileName=' + link + '&page=' + 1 + '&imgPath=' + imgPath + '&imgName=' + imgName
  $.ajax({
    type: 'POST',
    url: url,
    contentType: 'application/json',
    success: function (resp) {
      hideLoading()
      if (resp.status) {
        // sendNotification('success', 'Validate successfully');
        updateValidateId(resp);
        renderMatchSign(resp.data.matchArea)
      } else {
        sendNotification('error', resp.message);
        appendSignAdd();
      }
    },
    error: function (err) {
      hideLoading()
      sendNotification('error', 'Validate fail!')
    }
  })
}

function drawSignCanvas() {
  // console.log('drawSignCanvas')
  var canvasSelector = '#dialog-image-draw .draw-ct #canvas'
  var imgSelector = '#dialog-image-draw .draw-ct img'
  var outputSelectorCt = '#dialog-image-draw .draw-output'
  var lineColor = '#FF0000'
  showLoading()
  setTimeout(function () {
    drawCanvas(canvasSelector, outputSelectorCt, imgSelector, lineColor)
    hideLoading()
  }, 500)
}

// draw-output
function drawCanvas(canvasSelector, outputSelectorCt, imgSelector, lineColor) {
  var canvas = document.querySelector(canvasSelector)
  if (canvas === undefined || canvas === null) {
    console.error('can not find ' + canvasSelector)
    return
  }
  updateDrawOutput(outputSelectorCt, '', '', '', '');
  var myImg = document.querySelector(imgSelector)
  var clientWidth = myImg.clientWidth;
  var clientHeight = myImg.clientHeight;
  var srcWidth = myImg.naturalWidth;
  var srcHeight = myImg.naturalHeight;

  $(canvasSelector).attr('width', '' + clientWidth)
  $(canvasSelector).attr('height', '' + clientHeight)

  $(imgSelector).attr('data-client-width', '' + clientWidth)
  $(imgSelector).attr('data-client-height', '' + clientHeight)
  $(imgSelector).attr('data-src-width', '' + srcWidth)
  $(imgSelector).attr('data-src-height', '' + srcHeight)

  var ctx = canvas.getContext('2d')
  ctx.strokeStyle = lineColor
  // Variables
  var canvasx = $(canvas).offset().left
  var canvasy = $(canvas).offset().top
  var last_mousex = last_mousey = 0
  var mousex = mousey = 0
  var mousedown = false

  // Mousedown
  $(canvas).on('mousedown', function (e) {
    last_mousex = parseFloatRound(e.clientX - canvasx)
    last_mousey = parseFloatRound(e.clientY - canvasy)
    mousedown = true
  })

  // Mouseup
  $(canvas).on('mouseup', function (e) {
    mousedown = false;
    updateDrawOutput(outputSelectorCt, last_mousex, last_mousey, mousex, mousey);
  })

  // Mousemove
  $(canvas).on('mousemove', function (e) {
    mousex = parseFloatRound(e.clientX - canvasx)
    mousey = parseFloatRound(e.clientY - canvasy)
    if (mousedown) {
      ctx.clearRect(0, 0, canvas.width, canvas.height) // clear canvas
      ctx.beginPath()
      var width = mousex - last_mousex
      var height = mousey - last_mousey
      ctx.rect(last_mousex, last_mousey, width, height)
      ctx.strokeStyle = lineColor
      ctx.lineWidth = 2
      ctx.stroke();
      updateDrawOutput(outputSelectorCt, last_mousex, last_mousey, '', '');
    }
    updateDrawOutputCurrent(outputSelectorCt, mousex, mousey);
  })
}

function updateDrawOutputCurrent(outputSelectorCt, mousex, mousey) {
  $(outputSelectorCt + ' #current-x').val(mousex)
  $(outputSelectorCt + ' #current-y').val(mousey)
}

function updateDrawOutput(outputSelectorCt, last_mousex, last_mousey, mousex, mousey) {
  $(outputSelectorCt + ' #begin-x').val(last_mousex)
  $(outputSelectorCt + ' #begin-y').val(last_mousey)
  $(outputSelectorCt + ' #fin-x').val(mousex)
  $(outputSelectorCt + ' #fin-y').val(mousey)
}

function confirmDrawSign() {
  var beginX = $('#dialog-image-draw input#begin-x').val();
  var beginY = $('#dialog-image-draw input#begin-y').val();
  if (isBlankStr(beginX) || isBlankStr(beginY)) {
    sendNotification('warn', 'No area selected!');
    return;
  }
  var endX = $('#dialog-image-draw input#fin-x').val();
  var endY = $('#dialog-image-draw input#fin-y').val();
  // console.log('confirmDrawSign beginX', beginX);
  // console.log('confirmDrawSign beginY', beginY);
  // console.log('confirmDrawSign endX', endX);
  // console.log('confirmDrawSign endY', endY);


  beginX = parseFloatRound(beginX);
  beginY = parseFloatRound(beginY);
  endX = parseFloatRound(endX);
  endY = parseFloatRound(endY);

  var x = beginX < endX ? beginX : endX;
  var y = beginY < endY ? beginY : endY;
  var w = Math.abs(beginX - endX);
  var h = Math.abs(beginY - endY);
  // console.log('confirmDrawSign x', x);
  // console.log('confirmDrawSign y', y);
  // console.log('confirmDrawSign w', w);
  // console.log('confirmDrawSign h', h);

  var img = $('#dialog-image-draw .draw-ct img')
  var clientWidth = img.attr('data-client-width')
  var clientHeight = img.attr('data-client-height')
  var srcWidth = img.attr('data-src-width')
  var srcHeight = img.attr('data-src-height')

  var scaleW = parseFloatRound(clientWidth) / parseFloatRound(srcWidth)
  var scaleH = parseFloatRound(clientHeight) / parseFloatRound(srcHeight)
  var scale = selectWidthScale ? scaleW : scaleH;
  var imgSelector = '#recon-sign-ct .preview-ct img';
  var myImg = document.querySelector(imgSelector);
  var currentClientWidth = myImg.clientWidth;
  var currentClientHeight = myImg.clientHeight;

  var realScale = selectWidthScale ? currentClientWidth / srcWidth / scale : currentClientHeight / srcHeight / scale;

  x = x / scale;
  y = y / scale;
  w = w / scale;
  h = h / scale;
  var firstWriterId = $('#sign-template .writer-list .writer-ct').attr('id');
  var nameInfo = getNameInfo(firstWriterId);

  console.log('confirmDrawSign scaleW:' + scaleW + ', scaleH:' + scaleH)
  var createAreaInfo = {
    'w': '' + w,
    'h': '' + h,
    'x': '' + x,
    'y': '' + y,
    'rate': '0',
    'nameList': [
      {
        'first': '',
        'second': '',
        'full': nameInfo.full,
        'email': nameInfo.email,
        'rate': '0',
        'comments': 'mocked',
        'id': nameInfo.id
      }
    ]
  }
  addMatchArea(createAreaInfo)
}

function rollbackSignRecon() {
  $('#recon-sign-result .temp-show-sign').each(function () {
    var newAdd = $(this).attr('data-new');
    if (newAdd === 'true') {
      $(this).remove();
      return
    }
    var strGuid = $(this).attr('id');
    $(this).css('display', '');
    //TODO rollback pick name
    rollbackMatchName(strGuid);
  });
  rollbackAreaBox();
  refreshMatchSignIndex();
}


/**************draw area box begin***************** */
function getScale(imgSelector) {
  var myImg = document.querySelector(imgSelector)
  var clientWidth = myImg.clientWidth;
  var clientHeight = myImg.clientHeight;
  var srcWidth = myImg.naturalWidth;
  var srcHeight = myImg.naturalHeight;
  var scaleW = parseFloatRound(clientWidth) / parseFloatRound(srcWidth);
  var scaleH = parseFloatRound(clientHeight) / parseFloatRound(srcHeight);
  return selectWidthScale ? scaleW : scaleH;
}

function appendAreaBox(matchArea, strGuid, scale, newArea, index) {
  var style = createBoxStyle(matchArea, scale);
  var innerHtml = '<span class="area-label">No.</span><span class="area-index">' + index + '</span>';
  var attr = createBoxAtt(matchArea, newArea);
  var boxHtml = '<div class="area-box" style="' + style + '" id="' + strGuid + '" ' + attr + ' onclick="selectSignRow(this);">' + innerHtml + '</div>';
  $('#recon-sign-ct .preview-ct .area-ct').append(boxHtml);
}

function createBoxStyle(matchArea, scale) {
  // console.log('createBoxStyle matchArea', matchArea);
  var x = parseFloatRound(matchArea.x) * scale;
  var y = parseFloatRound(matchArea.y) * scale;
  var w = parseFloatRound(matchArea.w) * scale;
  var h = parseFloatRound(matchArea.h) * scale;
  var style = 'top:' + y + 'px; left:' + x + 'px; width:' + w + 'px; height:' + h + 'px;';
  return style;
}

function createBoxAtt(matchArea, newArea) {
  var attr = newArea ? ' data-new="true"' : ' data-new="false"';
  attr = attr + ' data-x="' + matchArea.x + '"';
  attr = attr + ' data-y="' + matchArea.y + '"';
  attr = attr + ' data-w="' + matchArea.w + '"';
  attr = attr + ' data-h="' + matchArea.h + '"';
  return attr;
}

function disgradAreaBox(strGuid) {
  $('#recon-sign-ct .preview-ct .area-ct #' + strGuid).addClass('area-disgard');
}

function activeAreaBox4Sign(strGuid) {
  $('#recon-sign-ct .preview-ct .area-ct .area-box').each(function () {
    var id = $(this).attr('id');
    if (id === strGuid) {
      $(this).addClass('area-active');
    } else {
      $(this).removeClass('area-active');
    }
  });
}

function showAllAreaBox() {
  $('#recon-sign-ct .preview-ct .area-ct .area-box').each(function () {
    var clz = $(this).attr('class');
    $(this).removeClass('area-hide');
  });
}



function hideAllAreaBox() {
  $('#recon-sign-ct .preview-ct .area-ct .area-box').each(function () {
    var clz = $(this).attr('class');
    $(this).addClass('area-hide');
  });
}



function rollbackAreaBox() {
  $('#recon-sign-ct .preview-ct .area-ct .area-box').each(function () {
    var newArea = $(this).attr('data-new');
    if (newArea === 'true') {
      $(this).remove();
    } else {
      $(this).removeClass('area-disgard');
    }
  });
}

function synAreaBoxIndex(strGuid, index) {
  $('#recon-sign-ct .preview-ct .area-ct #' + strGuid + ' span.area-index').html(index);
}

/**************draw area box end***************** */

/*************************get recon data image start position left to edge */
function getReconDataImgLeft(imgSelector, ctSelector) {
  var myImg = document.querySelector(imgSelector)
  var clientWidth = myImg.clientWidth;
  var width = $(ctSelector).outerWidth();
  return (width - clientWidth) / 2;
}
/*************************get recon data image start position left to edge */



$(function () {
  setTimeout(function () {
    doReconSign();
  }, 100);
});
