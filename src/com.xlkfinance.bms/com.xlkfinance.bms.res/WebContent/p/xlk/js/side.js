define(function(require, exports, module) {
    //左侧随屏滚动
    var
        $side = $("div.sidebar"),
        top = $(".header").height() + 10;
    var setPosition = function() {
        var scrollTop = $(window).scrollTop(),
            footerScrollTop = $(".footer").position().top,
            initialTop = $side.position().top,
            sideHeight = $side.height();
        if (scrollTop < top) {
            $side.removeClass("s_fixed");
        } else if (scrollTop >= top) {
            scrollTop + sideHeight < footerScrollTop ? $side.addClass("s_fixed").removeClass("b_fixed") : $side.addClass("b_fixed").removeClass("s_fixed");
        }
    };
  //  setPosition();
  //  $(window).scroll(function() {
 //       setPosition();
  //  });

    $(".nav").delegate(".items a.f1","click", function() {
        var _t = $(this),
            items = _t.parent(".items");
        items.find("ul").slideDown().end().siblings().find("ul").slideUp();
    });
});
