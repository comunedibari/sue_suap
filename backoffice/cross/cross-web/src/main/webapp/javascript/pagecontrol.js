$(document).ready(function() {
          $.ajaxSetup({
             type: "POST"
          });
   });

(function($) {
    $.isBlank = function(string) {
        return(!string || $.trim(string) === "");
    };
    $.fn.PageControl = function(options) {
        var defaults = {
        };
        var callbackFunction = options.callbackFunction;


        var $this = $(this)
                , $ul = $this.children("ul")
                , $selectors = $ul.find("li a")
                , $selector = $ul.find(".active a")
                , $frames = $this.find(".frames .frame")
                , $frame = $frames.children(".frame.active")
                ;

        var initSelectors = function(selectors) {
            selectors.on('click', function(e) {
                e.preventDefault();
                var $a = $(this);
                if (!$a.parent('li').hasClass('active')) {
                    $frames.hide();
                    $ul.find("li").removeClass("active");
                    var target = $($a.attr("href"));
                    target.show();
                    if (callbackFunction) {
                        eval(callbackFunction + "(target)");
                    }
                    target.trigger('cross-pc-tabshown');

                    $(this).parent("li").addClass("active");
                }
                if ($(this).parent("li").parent("ul").parent(".page-control").find(".menu-pull-bar").is(":visible")) {
                    $(this).parent("li").parent("ul").slideUp("fast", function() {
                        $(this).css("overflow", "").css("display", "");
                    });
                }
            });

            $(".page-control .menu-pull-bar").text($(".page-control ul li.active a").text());
            $(".page-control ul li a").click(function(e) {
                e.preventDefault();
                $(this).parent("li").parent("ul").parent(".page-control").find(".menu-pull-bar").text($(this).text());
            });
        }

        return this.each(function() {
            if (options) {
                $.extend(defaults, options)
            }

            initSelectors($selectors);
        });
    }

    $(function() {
        $('[data-role="page-control"]').each(function() {
            var callbackFunction = $(this).attr('callback');
            $(this).PageControl({
                'callbackFunction': callbackFunction
            });
        })
        $(window).resize(function() {
            if ($(window).width() >= 768) {
                $(".page-control ul").css({
                    display: "block"
                    , overflow: "visible"
                })
            }
            if ($(window).width() < 768 && $(".page-control ul").css("display") == "block") {
                $(".page-control ul").hide();
            }
        })
    })
})(window.jQuery);

