var Utils = (function () {
    function isEmpty(val) {
        return (val == undefined || val == null || val == '');
    }

    function isNaN(val) {
        return (val == 'NaN');
    }

    function isNotEmpty(val) {
        return !isEmpty(val);
    }

    function concat(items, separator) {
        var result = new Array();
        for (var i = 0; i < items.length; ++i) {
            if (!isEmpty(items[i])) {
                result.push(items[i]);
            }
        }
        return result.join(separator);
    }

    function isAllEmpty(items) {
        var r = true;
        $.each(items, function (idx, it) {
            if (!isEmpty(it)) {
                r = false;
            }
        });
        return r;
    }

    function mtConcat(titles, vals, separator, sep2) {
        var result = new Array();
        var sep = sep2 ? sep2 : '';
        for (var i = 0; i < titles.length; ++i) {
            var s = titles[i] + sep;
            if (!isEmpty(vals[i])) {
                s += vals[i];
            }
            result.push(s);
        }
        return result.join(separator);
    }

    function toMap(a) {
        var ret = {};
        $.map(a, function (n, i) {
            ret[n['name']] = n['value'];
        });
        return ret;
    }

    function clearFields(o, arr) {
        $.each(arr, function (idx, it) {
            o[it] = undefined;
        });
    }

    function copy(o, arr) {
        var ret = {};
        $.each(arr, function (idx, it) {
            ret[it] = o[it];
        });
        return ret;
    }

    function isNum(parm) {
        return $.isNumeric(parm);
    }

    //校验是否全由数字组成
    function isDigit(s) {
        var patrn = /^[0-9]{1,20}$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    //校验登录名：只能输入5-20个以字母开头、可带数字、“_”、“.”的字串
    function isRegisterUserName(s) {
        var patrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    //校验用户姓名：只能输入1-30个以字母开头的字串
    function isTrueName(s) {
        var patrn = /^[a-zA-Z]{1,30}$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    //校验密码：只能输入6-20个字母、数字、下划线
    function isPasswd(s) {
        var patrn = /^(\w){6,20}$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    //校验密码：只能输入8-12位的数字+字母混合(必须同时存在字母以及数字),
    function isPassword(s) {
        var reg = /^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
        if (!reg.test(s) || s.length < 8 || s.length > 12) {
            return true;
        } else {
            return false;
        }
    }

    //校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
    function isTel(s) {
        //var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?(\d){1,12})+$/;
        var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    //校验手机号码：必须以数字开头，除数字外，可含有“-”
    function isMobil(s) {
        var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    //校验邮政编码
    function isPostalCode(s) {
        //var patrn=/^[a-zA-Z0-9]{3,12}$/;
        var patrn = /^[a-zA-Z0-9 ]{3,12}$/;
        if (!patrn.exec(s)) return false;
        return true;
    }

    function isIP(str) {
        var arg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        if (str.match(arg) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    function isURL(str) {
        return str.match(/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i) != null;
    }

    function isBetween(val, lo, hi) {
        if ((val < lo) || (val > hi)) {
            return (false);
        }
        else {
            return (true);
        }
    }

    function isDate(theStr) {
        var the1st = theStr.indexOf('-');
        var the2nd = theStr.lastIndexOf('-');
        if (the1st == the2nd) {
            return (false);
        }
        else {
            var y = theStr.substring(0, the1st);
            var m = theStr.substring(the1st + 1, the2nd);
            var d = theStr.substring(the2nd + 1, theStr.length);
            var maxDays = 31;
            if (isInt(m) == false || isInt(d) == false || isInt(y) == false) {
                return (false);
            }
            else if (y.length < 4) {
                return (false);
            }
            else if (!isBetween(m, 1, 12)) {
                return (false);
            }
            else if (m == 4 || m == 6 || m == 9 || m == 11) maxDays = 30;
            else if (m == 2) {
                if (y % 4 > 0) maxDays = 28;
                else if (y % 100 == 0 && y % 400 > 0) maxDays = 28;
                else maxDays = 29;
            }
            if (isBetween(d, 1, maxDays) == false) {
                return (false);
            }
            else {
                return (true);
            }
        }
    }

    function isInt(theStr) {
        if (theStr.match(/^[-+]?\d*$/) == null) {
            return false;
        } else {
            return true;
        }
    }

    function isEmail(theStr) {
        if (theStr.match(/[A-Za-z0-9_-]+[@](\S*)(net|com|cn|org|cc|tv|[0-9]{1,3})(\S*)/g) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    function toDate(s) {
        if (jQuery.type(s) === "date") return s;
        var date = new Date(s.replace(/-/g, "/"));
        return date;
    }

    function toNum(s) {
        if (isEmpty(s)) {
            return 0;
        }
        if (jQuery.type(s) === "number") return s;
        return parseFloat(s);
    }

    function compareDate(date1, date2) {
        var d1 = Utils.toDate(date1);
        var d2 = Utils.toDate(date2);
        if (d1.getYear() > d2.getYear()) {
            return 1;
        }
        else if (d1.getYear() < d2.getYear()) {
            return -1;
        }
        else if (d1.getMonth() > d2.getMonth()) {
            return 1;
        }
        else if (d1.getMonth() < d2.getMonth()) {
            return -1;
        }
        else if (d1.getDate() > d2.getDate()) {
            return 1;
        } else if (d1.getDate() < d2.getDate()) {
            return -1;
        }
        return 0;
    }

    function lessNow(d) {
        return compareDate(d, new Date()) < 0;
    }

    function NumDescSort(a, b) {
        return b - a;
    }

    function NumAscSort(a, b) {
        return a - b;
    }

    //获取字符长度,一个中文2个字符
    function getByteLen(val) {
        var len = 0;
        for (var i = 0; i < val.length; i++) {
            var a = val.charAt(i);
            if (a.match(/[^\x00-\xff]/ig) != null) {
                len += 2;
            }
            else {
                len += 1;
            }
        }
        return len;
    }

    return {
        "isEmpty": isEmpty, "isNaN": isNaN, "isNotEmpty": isNotEmpty,
        "concat": concat, "mtConcat": mtConcat, "isAllEmpty": isAllEmpty,
        "toMap": toMap, "clearFields": clearFields, "copy": copy,
        "isEmail": isEmail, "isInt": isInt, "isDate": isDate, "isBetween": isBetween,
        "isURL": isURL, "isIP": isIP, "isPostalCode": isPostalCode,
        "isMobil": isMobil, "isTel": isTel, "isPassword": isPassword,
        "isPasswd": isPasswd, "isDigit": isDigit, "isNum": isNum, "toNum": toNum,
        "toDate": toDate, "compareDate": compareDate, "lessNow": lessNow,
        "NumDescSort": NumDescSort, "NumAscSort": NumAscSort, "getByteLen": getByteLen
    };
})();


var AjaxHelper = (function () {
    function post(url, data, callback, async) {
        var succeed = true;
        if (jQuery.isFunction(data)) {
            async = async || callback || true;
            callback = data;
            data = undefined;
        } else {
            data = JSON.stringify(data);
        }
        $.ajax({
            type: 'POST',
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            url: url,
            data: data,
            cache: false,
            async: async || true,
            success: function (response) {
                if (Utils.isEmpty(response.status)) {
                    if (callback) callback(response);
                }
                else if (response.status === 'error') {
                    alert('很遗憾', response.message, 'face-sad');
                    succeed = false;
                }
                else if (response.status === "needLogin") {
                    location.replace('/login');
                    succeed = false;
                } else {
                    if (callback) callback(response);
                }
            },
            error: function (xhr, textStatus, thrownError) {
                $('#ajaxErrorModal').find('.modal-body').html("<div>Http status: " + xhr.status + " "
                    + xhr.statusText + "</div>" + "<div>textStatus: "
                    + textStatus + "</div>" + "<div>thrownError:"
                    + thrownError + "</div>" + "<div>" + xhr.responseText
                    + "</div>")
                $('#ajaxErrorModal').modal('toggle');
                succeed = false;
            }
        });
        return succeed;
    }

    return {
        'post': post
    };
})();