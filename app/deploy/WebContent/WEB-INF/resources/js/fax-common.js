function showLoading() {
  $('#g-load-ct').show();
}

function hideLoading() {
  $('#g-load-ct').hide();
}

function sendNotification(type, message) {
  $.notify(message, {
    'className': type,
    'position': 'bottom right'
  });
}

function closeConfirm() {
  $('.jconfirm-closeIcon').click();
}

function isBlankStr(str) {
  if (str && str.trim().length > 0) {
    return false;
  }
  return true;
}


function isEmptyStr(str) {
  if (str && str.length > 0) {
    return false;
  }
  return true;
}

function isNotBlankStr(str) {
  return !isBlankStr(str);
}

function isNotEmptyStr(str) {
  return !isEmptyStr(str);
}

function applyNiceScroll(selector) {
  $(selector).niceScroll({ "cursorcolor": "#212636" });
}

function applyNiceScrollHiddenBar(selector) {
  $(selector).niceScroll({ "cursorcolor": "#212636", "autohidemode": "hidden" });
}


function updateNiceScroll(deplayMillSec) {
  console.log('updateNiceScroll with deplayMillSec', deplayMillSec);
  var deplay = deplayMillSec ? deplayMillSec : 0;
  setTimeout(
    function () {
      applyNiceScroll(".scroll-ct");
      applyNiceScrollHiddenBar('.scroll-ct-hidden-bar');
    }, deplay);

}