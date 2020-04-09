$(function(){
	var ecWidth = $("#echartsTableCon").css("width");
	var nWidth = parseInt(ecWidth,10) - 220;
	$("#echartsTableCon .e-g-right").css("width",nWidth+'px')

	$(window).resize(function(){
		ecWidth = $("#echartsTableCon").css("width");
		nWidth = parseInt(ecWidth,10) - 220;
		$("#echartsTableCon .e-g-right").css("width",nWidth+'px')
	});
})