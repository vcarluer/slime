(function($) {

    $.slideShow = function(element, options) {

        var defaults = {
			slidingSpeed : 3500,
			animationSpeed: 800,
			width : 600,
			height : 360,
			maxWidth : 600,
			maxHeight : 360			
        }

        var plugin = this;
        plugin.settings = {};

        var $element = $(element),
             element = element;
		
		var currIndex = 0;
		var selectedIndex = 0;
		plugin.timer = null;
		plugin.isSliding = false;
		plugin.nextSlideTo = null;
		
        plugin.init = function() {
            plugin.settings = $.extend({}, defaults, options);
				
			setSize();
			
			var $paginate = $("<ul class='pagination'></ul>");
			var numSlides = $element.find(".slider .slide").each(function(index){
				$(this).attr("slide-id",index);
				$paginate.append("<li slide-target='"+ index + "'>&nbsp;</li>");
			}).click(function() {
				stopSlider();
				nextSlide();
				startSlider();
			}).length;
			$(".slideshow .slider ").css("width", (numSlides*100) + "%");
			$paginate.find("li").click(function(){
				gotoSlide($(this).attr("slide-target"));
			});
			$paginate.appendTo($element);
			setActivePage(0);
			startSlider();
			$(window).resize(function() {
				setSize();
			});
        }
		var setSize = function() {
			$element.attr("style","");
			plugin.settings.width = $element.width();
			plugin.settings.height = $element.height();
			if(plugin.settings.width>plugin.settings.maxWidth)  plugin.settings.width=plugin.settings.maxWidth;
			if(plugin.settings.height>plugin.settings.maxHeight)  plugin.settings.height=plugin.settings.maxHeight;
			$element.width(plugin.settings.width);
			$element.height(plugin.settings.height);
			$element.children(".slideShowContainer").width(plugin.settings.width);
			$element.children(".slideShowContainer").height(plugin.settings.height);
			$element.find(".slide img").width(plugin.settings.width);		
		}
		var startSlider = function () {
			plugin.timer = setInterval(nextSlide,plugin.settings.slidingSpeed);
		}
		var stopSlider = function () {
			clearInterval(plugin.timer);
			
		}
		var setActivePage = function(pageNum) {
			$element.find("li.active").removeClass("active");
			$element.find("li[slide-target='" + pageNum + "']").addClass("active");
		}
        var nextSlide = function() {
			if(plugin.isSliding) {
				plugin.nextSlideTo = pageNum;
				return;
			}
			plugin.isSliding = true;
			$element.find(".slider").animate (
				{marginLeft:- plugin.settings.width},
				plugin.settings.animationSpeed,
				function() {
					$(this).css({marginLeft:0})
						.children("figure:last")
						.after($(this).children("figure:first"));  
					currIndex = $(this).children("figure:first").attr("slide-id");
					setActivePage(currIndex);
					plugin.isSliding = false;	
					if(plugin.nextSlideTo) {
						gotoSlide(plugin.nextSlideTo);
						plugin.nextSlideTo = null;
					}
				}) ; 		
		};
		var gotoSlide = function(pageNum) {
			if(plugin.isSliding) {
				plugin.nextSlideTo = pageNum;
				return;
			}
			selectedIndex = $element.find("figure[slide-id='" + pageNum +"']").index();
			stopSlider();
			plugin.isSliding = true;
			$element.find(".slider").animate (
				{marginLeft:-(selectedIndex * plugin.settings.width)},
				plugin.settings.animationSpeed,
				function() {						
					$(this).css({marginLeft:0})
						.children("figure:last")
						.after($(this).children("figure:lt(" + selectedIndex +")"));  
					startSlider();
					plugin.isSliding = false;
					if(plugin.nextSlideTo) {
						gotoSlide(plugin.nextSlideTo);
						plugin.nextSlideTo = null;
					}
			});
			setActivePage(pageNum);			
		};
		plugin.init();
    }

    $.fn.slideShow = function(options) {
        return this.each(function() {
            if (undefined == $(this).data('slideShow')) {
                var plugin = new $.slideShow(this, options);
                $(this).data('slideShow', plugin);
            }
        });
    }

})(jQuery);