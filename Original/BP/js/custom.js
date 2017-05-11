$(document).ready(function() {

    //鼠标触发弹出子菜单
    $('li.dropdown').mouseover(function() {
        $(this).addClass('open');
    }).mouseout(function() {
        $(this).removeClass('open');
    });


    //关闭click.bs.dropdown.data-api事件，一级菜单恢复href属性
    $(document).off('click.bs.dropdown.data-api');

    //判断footer的位置是否固定
    // var windowH = $(window).height();
    // var bodyH = $(document.body).height();
    // if (windowH > bodyH) {
    //     $("footer#footFixed").addClass("footer-fixed");
    // } else {
    //     $("footer#footFixed").removeClass("footer-fixed");
    // };

    //              alert($(window).height()); //浏览器时下窗口可视区域高度
    //              alert($(document).height()); //浏览器时下窗口文档的高度
    //              alert($(document.body).height()); //浏览器时下窗口文档body的高度
    //              alert($(document.body).outerHeight(true)); //浏览器时下窗口文档body的总高度 包括border padding margin
    //              alert($(window).width()); //浏览器时下窗口可视区域宽度
    //              alert($(document).width()); //浏览器时下窗口文档对于象宽度
    //              alert($(document.body).width()); //浏览器时下窗口文档body的高度
    //              alert($(document.body).outerWidth(true)); //浏览器时下窗口文档body的总宽度 包括border padding margin
    //          })
    //
    //          $(document).ready(function() {


    //选项卡背景切换
    /*$(".auction .nav-tabs li:nth-child(1)").click(function() {
        $(".auction .nav-tabs").css("background-position-y", "0");
        $("div#auction-record").addClass("hide");
        $(".record-list").addClass("hide");
        $(".record-show").animate({
            left: 348
        }, 0);
    });
    $(".auction .nav-tabs li:nth-child(2)").click(function() {
        $(".auction .nav-tabs").css("background-position-y", "-50px");
        $("div#auction-record").removeClass("hide");
        // $("footer#footFixed").addClass("footer-fixed");
    });
    $(".auction .nav-tabs li:nth-child(3)").click(function() {
        $(".auction .nav-tabs").css("background-position-y", "-100px");
        $("div#auction-record").addClass("hide");
        // $("footer#footFixed").addClass("footer-fixed");
        $(".record-list").addClass("hide");
        $(".record-show").animate({
            left: 348
        }, 0);
    });*/

    // 竞拍行业选择
    $('.industry > button').each(function(index, item) {
        $(this).click(function() {

            if (index == 0) {
                $("#industry-Type").html('饮料香烟');
            } else if (index == 1) {
                $("#industry-Type").html('休闲食品');
            } else if (index == 2) {
                $("#industry-Type").html('干性杂货');
            } else if (index == 3) {
                $("#industry-Type").html('日配');
            } else if (index == 4) {
                $("#industry-Type").html('清洁用品');
            } else if (index == 5) {
                $("#industry-Type").html('家庭用品');
            } else if (index == 6) {
                $("#industry-Type").html('文体用品');
            } else if (index == 7) {
                $("#industry-Type").html('家电');
            } else if (index == 8) {
                $("#industry-Type").html('纺织用品');
            } else if (index == 9) {
                $("#industry-Type").html('酒品');
            } else if (index == 10) {
                $("#industry-Type").html('干货');
            } else if (index == 11) {
                $("#industry-Type").html('水果');
            } else if (index == 12) {
                $("#industry-Type").html('蔬菜');
            } else if (index == 13) {
                $("#industry-Type").html('家禽');
            } else if (index == 14) {
                $("#industry-Type").html('猪肉');
            } else if (index == 15) {
                $("#industry-Type").html('冰鲜');
            } else if (index == 16) {
                $("#industry-Type").html('贝类');
            } else if (index == 17) {
                $("#industry-Type").html('活鲜');
            } else if (index == 18) {
                $("#industry-Type").html('男正装');
            } else if (index == 19) {
                $("#industry-Type").html('男休闲');
            } else if (index == 20) {
                $("#industry-Type").html('女正装');
            } else if (index == 21) {
                $("#industry-Type").html('女休闲');
            } else if (index == 22) {
                $("#industry-Type").html('鞋');
            } else if (index == 23) {
                $("#industry-Type").html('童装');
            } else if (index == 24) {
                $("#industry-Type").html('内衣');
            } else if (index == 25) {
                $("#industry-Type").html('皮具');
            } else if (index == 26) {
                $("#industry-Type").html('熟食');
            } else if (index == 27) {
                $("#industry-Type").html('包点');
            } else if (index == 28) {
                $("#industry-Type").html('烘焙');
            } else if (index == 29) {
                $("#industry-Type").html('铁板');
            } else if (index == 30) {
                $("#industry-Type").html('果吧');
            } else if (index == 31) {
                $("#industry-Type").html('鲑鱼');
            }
        })
    })


    /*//显示隐藏本期竞拍记录
    $(".record-show").click(function() {
        if ($(".record-list").hasClass("hide")) {
            $(".record-list").removeClass("hide");
            $(".record-show").animate({
                left: 0
            }, 0);
        } else {
            $(".record-list").addClass("hide");
            $(".record-show").animate({
                left: 348
            }, 0);
        }
    });*/


    //列表全选
    var checkAll = $("#check_all");
    var checkList = $("#check_list").find("input[type=checkbox]");
    checkAll.click(function() {
        if ($(this).is(":checked")) {
            checkList.each(function() {
                $(this).prop("checked", true);
            })
        } else {
            checkList.each(function() {
                $(this).prop("checked", false);
            })
        }
    })













})