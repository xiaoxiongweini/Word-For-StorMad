layui.use('form', function() {
    var form = layui.form();
});

//获取链接参数
function getQueryString(name) {     
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");     
    var r = window.location.search.substr(1).match(reg);     
    if (r != null) return  unescape(r[2]);
    return null;
};

function NewAd(adId) {
    this.adId = adId || '';
    this.init();
}
NewAd.prototype = {
    //填充推广计划
    fillPlans: function(spId, sgId) {
        //填充推广计划下拉框
        var self = this;
        $.ajax({
            type: 'POST',
            url: yonghui.contextPath + '/api/ad/spread/findSpreadPlanPage.jsp',
            data: { 'pageNo': 1, 'pageSize': 100 },
            dataType: 'json',
            success: function(data) {
                if (data.errCode == 0) {
                    var list = data.obj.record;
                    var options = '<option value="">选择推广计划</option>';
                    for (var i = 0; i < list.length; i++) {
                        options += '<option value=\'' + list[i].spId + '\'>' + list[i].spName + '</option>';
                    }
                    layui.use('form', function() {
                        var form = layui.form();
                        $('#plans').html(options);
                        if (spId) {
                            $('#plans option[value="' + spId + '"]').attr('selected', true);
                            self.fillGroups(spId, sgId);
                        }
                        form.render('select');

                        //根据不同的推广计划，设置不同的推广组
                        form.on('select(plans)', function(data) {
                            self.fillGroups(data.value);
                        });
                    });
                } else {
                    layer.alert('查询推广计划失败!\r\n' + data.errMsg);
                }
            },
            error: function(data) {
                layer.alert('查询推广计划失败!\r\n' + data.errMsg);
            }
        });
    },
    //填充推广组下拉框
    fillGroups: function(spId, sgId) {
        $.ajax({
            type: 'POST',
            url: yonghui.contextPath + '/api/ad/spread/findSpreadGroupPage.jsp',
            data: { 'spId': spId },
            dataType: 'json',
            success: function(data) {
                if (data.errCode == 0) {
                    var list = data.obj.record;
                    var options = '<option value="">选择推广组</option>';
                    for (var i = 0; i < list.length; i++) {
                        options += '<option value=\'' + list[i].sgId + '\'>' + list[i].sgName + '</option>';
                    }
                    $('#groups').html(options);
                    if (sgId) {
                        $('#groups option[value="' + sgId + '"]').attr('selected', true);
                    }
                    layui.use('form', function() {
                        var form = layui.form();
                        form.render('select');
                    });
                } else {
                    layer.alert('查询推广组失败!\r\n' + data.errMsg);
                }
            },
            error: function(data) {
                layer.alert('查询推广组失败!\r\n' + data.errMsg);
            }
        });
    },
    //填充广告类型
    fillAdTypes: function(adType, asId) {
        //填充广告类型下拉框
        var self = this;
        $.ajax({
            type: 'POST',
            url: yonghui.contextPath + '/api/ad/getAllAdType.jsp',
            data: { 'pageNo': 1, 'pageSize': 100 },
            dataType: 'json',
            success: function(data) {
                if (data.errCode == 0) {
                    var list = data.obj;
                    var options = '<option value="">选择广告类型</option>';
                    for (var i = 0; i < list.length; i++) {
                        options += '<option value=\'' + list[i].first + '\'>' + list[i].second + '</option>';
                    }
                    layui.use('form', function() {
                        var form = layui.form();
                        $('#adTypes').html(options);
                        if (adType) {
                            $('#adTypes option[value="' + adType + '"]').attr('selected', true);
                            self.fillAdSizes(adType, asId);
                        }
                        form.render('select');
                        //根据选择不同的广告类型，刷新广告规格
                        form.on('select(adTypes)', function(data) {
                            self.fillAdSizes(data.value);
                        });
                    });
                } else {
                    layer.alert('查询广告类型失败!\r\n' + data.errMsg);
                }
            },
            error: function(data) {
                layer.alert('查询广告类型失败!\r\n' + data.errMsg);
            }
        });
    },
    //填充广告规格
    fillAdSizes: function(adType, asId) {
        if ((adType == 1) || (adType == 4)) {
            $('#imgDiv').show();
        } else {
            $('#imgDiv,.ad-previes').hide();
        }
        if (adType == 2) {
            $('#link').closest('.form-group').hide();
        } else {
            $('#link').closest('.form-group').show();
        }
        $.ajax({
            type: 'POST',
            url: yonghui.contextPath + '/api/ad/adsize/getAdSizesByAdType.jsp',
            data: { 'adType': adType },
            dataType: 'json',
            success: function(data) {
                if (data.errCode == 0) {
                    var list = data.obj;
                    var options = '<option value="">选择广告规格</option>';
                    for (var i = 0; i < list.length; i++) {
                        options += '<option value=\'' + list[i].asId + '\'>' + list[i].asName + '</option>';
                    }
                    $('#adSizes').html(options);
                    if (asId) {
                        $('#adSizes option[value="' + asId + '"]').attr('selected', true);
                    }
                    layui.use('form', function() {
                        var form = layui.form();
                        form.render('select');
                    });
                } else {
                    layer.alert('查询广告规格失败!\r\n' + data.errMsg);
                }
            },
            error: function(data) {
                layer.alert('查询广告规格失败!\r\n' + data.errMsg);
            }
        });
    },
    /**
     * [提交审核]
     */
    submitAd: function() {
        var self = this;
        var planId = $('#plans').val();
        if (planId == '' || planId == 0) {
            layer.alert('请选择推广计划');
            $('#plans').focus();
            return;
        }
        var groupId = $('#groups').val();
        if (groupId == '' || groupId == 0) {
            layer.alert('请选择推广组');
            $('#groups').focus();
            return;
        }
        var title = $('#title').val();
        if ('' === $.trim(title)) {
            layer.alert('请输入广告标题');
            return;
        }
        var typeId = $('#adTypes').val();
        if (typeId == '' || typeId == 0) {
            layer.alert('请选择广告类型');
            return;
        }
        var sizeId = $('#adSizes').val();
        if (sizeId == '' || sizeId == 0) {
            layer.alert('请选择广告规格');
            return;
        }
        if ((typeId == 1) || (typeId == 4)) {
            var imgKey = $('#imgKey').val();
            if (!imgKey || imgKey === '') {
                layer.alert('请上传广告图片');
                return;
            }
        }
        var content = $('#content').val();
        if ('' === $.trim(content)) {
            layer.alert('请输入广告文字');
            return;
        }
        var link = $('#link').val();
        if ((link.indexOf('http://') == -1) && (typeId != 2)) {
            layer.alert('链接地址请使用http://开头');
            return;
        }

        var adId = $('#adId').val();

        var dataPram = { 'title': title, 'adType': typeId, 'content': content, 'spId': planId, 'sgId': groupId, 'asId': sizeId };
        if (typeId == 3) {
            dataPram.link = link;
        } else if (typeId == 4) {
            dataPram.link = link;
            dataPram.imgKey = imgKey;
        }

        var url = yonghui.contextPath + '/api/ad/adinfo/addAdInfo.jsp';
        if (adId != null && adId != '') {
            dataPram.adId = self.adId;
            url = yonghui.contextPath + '/api/ad/adinfo/updateAdInfo.jsp';
        }
        $.ajax({
            type: 'POST',
            url: url,
            data: dataPram /*{ 'title': title, 'adType': typeId, 'content': content, 'link': link, 'spId': planId, 'sgId': groupId, 'asId': sizeId, 'imgKey': imgKey, 'adId': self.adId }*/ ,
            dataType: 'json',
            success: function(data) {
                if (data.errCode == -10000) {
                    layer.alert('你尚未登录系统，不能操作');
                    return;
                }
                if (data.errCode != 0) {
                    layer.alert(data.errMsg);
                    return;
                }

                layer.open({
                    type: 1,
                    area: ['580px', '318px'],
                    title: false, //隐藏默认标题
                    title: ['提示', 'color:#666;font-size:14px;font-weight:bold;'], //自定义标题
                    shadeClose: true, //点击遮罩关闭
                    content: $('#submitReview')
                });

            },
            error: function(data) {
                layer.alert(data.errMsg);
            }
        });
    },
    //如果是编辑广告，则填充内容
    fillAdInfo: function() {
        var self = this;
        $.ajax({
            type: 'POST',
            url: yonghui.contextPath + '/api/ad/adinfo/getOneAdInfo.jsp',
            data: { 'adId': self.adId },
            dataType: 'json',
            success: function(data) {
                if (data.errCode == 0) {
                    var adType = 0;
                    var adSize = 0;
                    if (data.obj.adType != null) {
                        adType = data.obj.adType.split(',')[0];
                    }
                    if (data.obj.adSize != null) {
                        adSize = data.obj.adSize.split(',')[0];
                    }

                    self.fillPlans(data.obj.spId, data.obj.sgId);
                    self.fillAdTypes(adType, data.obj.asId);

                    $('#title').val(data.obj.title);
                    $('#adId').val(data.obj.adId);
                    $('#content').val(data.obj.content);
                    $('#link').val(data.obj.link);
                    $('#plans').val(data.obj.spId);
                    $('#groups').val(data.obj.sgId);
                    $('#adTypes').val(adType);
                    $('#adSizes').val(adSize);
                    if ((adType == 1) || (adType == 4)) {
                        $('.ad-previes').show();
                        $('#adImg').attr('src', data.obj.imgUrl);
                    }
                    layui.use('form', function() {
                        var form = layui.form();
                    });
                } else {
                    layer.alert('查询广告失败!\r\n' + data.errMsg);
                }
            },
            error: function(data) {
                layer.alert('查询广告失败!\r\n' + data.errMsg);
            }
        });
    },
    bindEvent: function() {
        var self = this;
        //上传图片
        layui.use('upload', function() {
            layui.upload({
                url: '/api/ad/upload.jsp', //上传接口
                method: 'post',
                dataType: 'json',
                success: function(data) { //上传成功后的回调
                    if (data.errCode == 0) {
                        $('.ad-previes').show();
                        $('#imgKey').val(data.obj);
                        var imgUrl = yonghui.contextPath + '/api/showTempImg.jsp?key=' + data.obj;
                        $('#adImg').attr('src', imgUrl);
                    } else {
                        $('#errMsg').css('display', 'block');
                        $('.exclamation-icon').append(data.errMsg);
                    }
                },
                error: function(data) {
                    $('#errMsg').css('display', 'block');
                    $('.exclamation-icon').append(data.errMsg);
                }
            });
        });
        //新增广告
        $("#btnSubmit").click(function() {
            self.submitAd();
        });
        //返回广告列表
        $('#btnKnow').click(function() {
            location.href = 'ad-list.html';
        });

        //返回广告列表
        $('#btnBack').click(function() {
            location.href = 'ad-list.html';
        });
    },
    init: function() {
        if (this.adId !== '') {
            this.fillAdInfo();
        } else {
            this.fillPlans();
            this.fillAdTypes();
        }
        this.bindEvent();
    }
};

$(function() {
    if (getQueryString('adId')) {
        new NewAd(getQueryString('adId'));
    } else {
        new NewAd();
    }
})
