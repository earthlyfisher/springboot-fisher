jQuery(function($) {
	//左侧菜单栏右侧小箭头变化
	// $(".nav>li").each(function(){
	// 	$(this).click(function(){
	// 		$(".dropdown").find(".pull-right").removeClass("fa-angle-down").addClass("fa-angle-left");
	// 		if($(this).attr("class")==undefined||$(this).attr("class").indexOf("open")!=-1){
	// 			$(this).find(".pull-right").removeClass("fa-angle-down").addClass("fa-angle-left");
	// 		}else{
	// 			$(this).find(".pull-right").removeClass("fa-angle-left").addClass("fa-angle-down");
	// 		}
	// 	});
	// });
	//选择语言
    $(".globe-select>a").click(function(){
        $(".globe-option").toggle();
    });
    $(".globe-option li").click(function(){
        var text = $(this).find("a");
        $(".globe-select>a").css("background-image",text.css("background-image"));
        $(".globe-option").hide();
    });
    //左侧菜单栏宽屏窄屏显示切换
    $(".sidebar-toggle").click(function(){
    	if($("body").attr("class")=="sidebar-mini"){
    		$("body").removeClass("sidebar-mini");
    		$(".main-sidebar li.dropdown>a").attr("data-toggle","dropdown");
    	}else{
    		$("body").addClass("sidebar-mini");
    		$(".main-sidebar li.dropdown>a").attr("data-toggle","");
    	}
    });
})