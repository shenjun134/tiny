function strEmpty(str) {
	if (str === undefined || str.trim() === "" || str === 'undefined') {
		return true;
	}
	return false;
}

function defaultStr(str, def) {
	if (strEmpty(str)) {
		return def;
	}
	return str;
}

function validateForm(formId) {
	$("#".concat(formId)).find('input:text').each(function () {
		var origVal = $(this).val();
		$(this).val(origVal ? origVal.trim() : '');
	});
}

$(function () {
	var tophtml = "<div id=\"shortcut_menu\" class=\"shortcut-menu\"><div class=\"btn btn-top\">TOP</div></div>";
	$("#top").html(tophtml);
	$("#shortcut_menu").each(function () {
		$(this).find(".btn-top").click(function () {
			$("html, body, #main_content").animate({
				"scroll-top": 0
			}, "fast");
		});
	});
	var lastRmenuStatus = false;
	$('#main_content').scroll(function () {
		var _top = $("#main_content").scrollTop();
		// console.log('_top', _top);
		if (_top > 200) {
			$("#shortcut_menu").data("expanded", true);
		}
		else {
			$("#shortcut_menu").data("expanded", false);
		}
		if ($("#shortcut_menu").data("expanded") != lastRmenuStatus) {
			lastRmenuStatus = $("#shortcut_menu").data("expanded");
			if (lastRmenuStatus) {
				$("#shortcut_menu .btn-top").slideDown();
				$("#shortcut_menu .btn-top").addClass('return-top-active');
			}
			else {
				$("#shortcut_menu .btn-top").slideUp();
				$("#shortcut_menu .btn-top").removeClass('return-top-active');
			}
		}
	});

});

$(function () {
		$("body").attrchange({
		trackValues: true, // set to true so that the event object is updated with old & new values
		callback: function (event) {
			console.log('body event', event);
			if (!$("#shortcut_menu .btn-top").hasClass('return-top-active')) {
				return;
			}
			if (event.attributeName == "class") { // which attribute you want to watch for changes
				if ($("body").hasClass("meny-active") === true) {
					$("#shortcut_menu .btn-top").fadeOut("slow");
				} else {
					$("#shortcut_menu .btn-top").fadeIn("slow");
				}
			}
		}
	});
});

$('[data-toggle="tooltip"]').tooltip({show: {effect:"none", delay:0}});