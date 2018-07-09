$(function() {
	// tab switch
	$('.title-list li').mouseover(
			function() {
				var liindex = $('.title-list li').index(this);
				$(this).addClass('on').siblings().removeClass('on');
				$('.tab-wrap div.tab').eq(liindex).fadeIn(150)
						.siblings('div.tab').hide();
				var liWidth = $('.title-list li').width();
				$('.contents .title-list p').stop(false, true).animate({
					'left' : liindex * liWidth + 'px'
				}, 300);
			});

	// tab hover
	$('.tab-wrap .tab li').hover(function() {
		$(this).css("border-color", "#ff6600");
		$(this).find('p > a').css('color', '#ff6600');
	}, function() {
		$(this).css("border-color", "#fafafa");
		$(this).find('p > a').css('color', '#666666');
	});
});