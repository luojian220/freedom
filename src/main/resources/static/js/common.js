//iframe自适应
$(window).on('resize', function () {
    var $content = $('#mainApp');
    $content.height($(this).height());
    $content.find('iframe').each(function () {
        $(this).height($content.height() - 150);
    });
    var $rrapp = $('#rrapp').parent();
    $rrapp.height($(this).height());
    $(this).height($content.height());
}).resize();

$.ajaxSetup({
    dataType: "json",
    cache: false
});

//重写alert
window.alert = function (msg, callback) {
    // parent.layer.alert 弹出在iframe外的页面。
	 $(':focus').blur();
    layer.alert(msg, function (index) {
        layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};

//重写confirm式样框
window.confirm = function (msg, callback) {
    //如果没有定义回调函数，直接返回true
    if (!callback) {
        return true;
    }
    layer.confirm(msg, {
            skin: 'layui-layer-molv', btn: ['确定', '取消']
        },
        function () {//确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
};

/**
 *
 * @param options
 */
window.openWindow = function (options) {
    let globalParams = {
        skin: 'layui-layer-molv',//皮肤
        title: '标题',//标题
        type: 1,//打开窗口的类型 1：html里的div内容 2：iframe方式，页面的路径
        closeBtn: 1, //关闭按钮的形状 0、1
        anim: -1,
        isOutAnim: false,
        shadeClose: false,
        area: ['90%', '95%'],
        content: '',
        btn: false, //按钮
        top: false //窗口弹出是否在iframe上层
    };
    globalParams = $.extend(globalParams, options);
    if (globalParams.top) {
        parent.layer.open(globalParams);
    } else {
        layer.open(globalParams);
    }
};


/**
 * 翻译日期
 * @param date
 * @param fmt
 * @returns {*}
 */
function transDate(date, fmt) {
    if (date) {
        if (typeof date == 'number') {
            return new Date(date).dateFormat(fmt);
        } else if (typeof date == 'object') {
            return date.dateFormat(fmt);
        } else {
            try {
                return new Date(date.replace('-', '/').replace('-', '/')).dateFormat(fmt);
            } catch (e) {
                return '-';
            }
        }
    } else {
        return '-';
    }
};

/**
 * 用JS获取地址栏参数的方法
 * 使用示例 location.href = http://localhost:8080/index.html?id=123
 *          getQueryString('id') --> 123;
 * @param name
 * @returns {null}
 * @constructor
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/**
 * 主要功能:导出功能公共方法
 *
 * @param formId 表单ID,带'#'号,如'#formId'
 * @param url 请求后台地址
 * @param extraObj 往后台请求额外参数,对象格式,如:{'aaa': 111}
 */
function exportFile(formId, url, extraObj) {
    var form = $('<form>'); //定义一个form表单
    form.attr('style', 'display: none');
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', url);

    var json = getJson(formId);
    if (typeof extraObj != 'undefined') {
        json = $.extend(json, extraObj);
    }

    $('body').append(form);//将表单放置在web中
    for (var i in json) {
        var input = $('<input>');
        input.attr('type', 'hidden');
        input.attr('name', i);
        input.attr('value', json[i]);
        form.append(input);
    }

    form.submit();//表单提交
}

/**
 * 将form转化为json
 * @param form 传入 form表单的dom $("#baseFm")
 * @returns {___anonymous49_50}  序列化的键值对 {key:value,key2:value2,....}
 */
function getJson(form) {
    var o = {};
    var $form = $(form).find('input,textarea,select');
    $.each($form, function (i, item) {
        var $this = $(item);

        if ($this.attr("type") == 'radio') {
            o[$this.attr("name")] = $("input[name='" + $this.attr("name") + "']:checked").val();
            return true;
        }
        o[$this.attr("name")] = $this.val();
    })
    return o;
}

Ajax = function () {

    //var opt = { type:'GET',dataType:'json',resultMsg:true };
    function request(opt) {

        //添加遮罩层
        dialogLoading(true);

        if (typeof opt.cache == 'undefined') {
            opt.cache = false;
        }

        if (typeof opt == 'undefined') {
            return;
        }
        //opt = $.extend(opt, p);
        if (typeof(opt.type) == 'undefined') {
            opt.type = 'GET'
        }
        if (typeof(opt.async) == 'undefined') {
            opt.async = false;
        }
        if (typeof(opt.dataType) == 'undefined') {
            opt.dataType = 'json'
        }
        if (typeof(opt.contentType) == 'undefined') {
            opt.contentType = 'application/x-www-form-urlencoded;chartset=UTF-8'
        }
        if (typeof(opt.params) == 'undefined' || opt.params == null) {
            opt.params = {};
        }
        opt.params.date = new Date();
        if (typeof(opt.beforeSubmit) != 'undefined') {
            var flag = opt.beforeSubmit(opt);
            if (!flag) {
                return;
            }
        }

        if (typeof(opt.resultMsg) == 'undefined') {
            opt.resultMsg = true;
        }

        $.ajax({
            async: opt.async,
            url: opt.url,
            dataType: opt.dataType,
            contentType: opt.contentType,
            data: opt.params,
            crossDomain: opt.crossDomain || false,
            type: opt.type,
            cache: opt.cache,
            success: function (data) {
                //关闭遮罩
                dialogLoading(false);

                if (typeof data == 'string' && data.indexOf("exception") > 0 || typeof data.code != 'undefined' && data.code != '0') {
                    var result = {code: null};
                    if (typeof data == 'string') {
                        result = eval('(' + data + ')')
                    } else if (typeof data == 'object') {
                        result = data;
                    }

                    if (opt.resultMsg && result.msg) {
                        layer.alert(result.msg, {icon: 5});
                    }
                    return;
                }
                if (opt.resultMsg && data.msg) {
                    layer.alert(data.msg, {icon: 6}, function () {
                        if (typeof(opt.successCallback) != 'undefined') {
                            opt.successCallback(data);
                        }
                    });
                    return;
                }

                if (typeof(opt.successCallback) != 'undefined') {
                    opt.successCallback(data);
                }
                
            },
            error: function (err) {
                //关闭遮罩
                dialogLoading(false);
                if(err.responseText.indexOf("@on-enter=\"login\"")>0){
                	 parent.location.href = 'index.html';
                	 return;
                }
                layer.alert("此页面发生未知异常,请联系管理员", {icon: 5});
            }
        });
    }

    return {
        /**
         * Ajax调用request
         */
        request: request
    };
}();



function dialogLoading(flag) {
    if (flag) {
        top.layer.load(0, {
            shade: [0.5, '#fff'],
            time: 5000,
            content: '处理中...'
        });
    } else {
        top.layer.closeAll('loading');
    }
}

//获取一个月之前时间
function getOneMonthBeforeTime(){

    var nowdate = new Date();
    nowdate.setMonth(nowdate.getMonth()-1);
    return nowdate;

}

// 获取一个月之后时间
function getOneMonthAfterTime(){

    var endTime = new Date();
    endTime.setMonth(endTime.getMonth() + 1);
    return endTime;

}

/**
 * 用JS获取地址栏参数的方法(解决中文乱码)
 * 使用示例 location.href = http://localhost:8080/index.html?id=123
 *          getQueryString('id') --> 123;
 * @param name
 * @returns {null}
 * @constructor
 */
function getQueryStr(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return null;
}

function unique_typeName(array){
    var temp = [];
    var strArr = [];
    for(var i = 0; i < array.length; i++) {
        //如果当前数组的第i项在当前数组中第一次出现的位置是i，才存入数组；否则代表是重复的
        if(strArr.indexOf(array[i].name) <= 0){
            temp.push(array[i]);
            strArr.push(array[i].name);
        }
    }
    return temp;
}