<#import "${ctx}/ftl/base.ftl" as ListPage>

<@ListPage.Html title="登录" headAttr='
<script type="text/javascript" src="${ctx}/js/RSA.js"></script>
<script type="text/javascript" src="${ctx}/js/BigInt.js"></script>
<script type="text/javascript" src="${ctx}/js/Barrett.js"></script>
'
css='
body{
    background: url("${ctx}/img/login.jpg");
    background-repeat: no-repeat;
}
.row{width: 100%;}
.form-group{padding-left: 25px;}
.form{background: rgba(255,255,255,0.2);margin:120px auto;}
/*阴影*/
.fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}
input[type="text"],input[type="password"]{padding-left:26px;}
.checkbox{padding-left:21px;}
'
>
<div class="container">
    <form id="loginForm" action="/user/login" method="post" class="form-horizontal" role="form">
        <div class="form row">
            <div class="col-md-offset-4">
                <h3 class="form-title" style="margin-left: 10px;">登录</h3>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group ">
                            <i class="fa fa-user fa-lg"></i>
                            <input class="form-control required " type="text" placeholder="用户名" id="username"
                                   name="username" autofocus="autofocus"/>
                        </div>
                        <div class="form-group ">
                            <i class="fa fa-lock fa-lg"></i>
                            <input class="form-control required" type="password" placeholder="密码" id="realPassword"
                                   name="realPassword"/>
                            <input type="hidden" id="password" name="password"/>
                        </div>
                        <div class="form-group ">
                            <div class="row">
                                <div class="col-md-8 col-xs-6  ">

                                    <i class="fa fa-shield" aria-hidden="true"></i>
                                    <input class="form-control required" type="text" name="verifyCode" placeholder="验证码"
                                           id="verifyCode"/>

                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <img id="verifyCodeImg" class="pull-right" alt="验证码"
                                         onclick="this.src='${ctx}/verifyCode?d='+new Date()*1" src="${ctx}/verifyCode"
                                         style="padding-top:20px;"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="checkbox">
                                <input type="checkbox" name="remember-me"/>记住我
                            </label>
                        </div>

                        <div class="form-group ">
                            <button type="submit" class="btn btn-success pull-right" name="submit" id="submit"
                                    style="margin-bottom:10px;">登录
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="well text-center">
                <span>还没有账号?</span>&nbsp;&nbsp;
                <a href="${ctx}/showRegisterPage">立即注册</a>
            </div>

            <div id="verifyCodeCheckResult" style="display:<#if captchaError ??> block<#else> none</#if>"
                 class="alert alert-danger alert-dismissable text-center">
            </div>

                         <#if error ??>
    <div class="alert alert-danger alert-dismissable text-center">
        <button type="button" class="close" data-dismiss="alert"
                aria-hidden="true">
            &times;
        </button>
        用户名或密码错误，请重新输入！
    </div>
                         </#if>
        </div>
    </form>

</div>
<script type="text/javascript">
    $('#verifyCode').focus(function () {
        $("#submit").attr("disabled", false);
    });
    $('#verifyCode').blur(function () {
        var url = "${ctx}/verifyCodeCheck";
        var data = $('#verifyCode').val().trim();
        AjaxHelper.post(url, data, function (response) {
            if (Utils.isNotEmpty(response.message)) {
                $('#verifyCodeCheckResult').text(response.message);
                $('#verifyCodeCheckResult').show();
                $('#verifyCodeImg').click();
                $("#verifyCode").val("");
                $("#submit").attr("disabled", true);
            } else {
                $('#verifyCodeCheckResult').hide();
                $("#submit").attr("disabled", false);
            }
        });
    });

    $('#submit').click(function () {
        var password = $("#realPassword").val();
        // 密码加密
        $("#password").val(encryptedString(getKey(), password));
        $("#loginForm").submit();
    });

</script>

</@ListPage.Html>