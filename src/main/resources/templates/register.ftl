<#import "${ctx}/ftl/base.ftl" as ListPage>

<@ListPage.Html title="注册 - ${companyName?if_exists}"
headAttr='
    <link rel="stylesheet" href="${ctx}/css/register.css" type="text/css"/>

    <script type="text/javascript" src="${ctx}/js/RSA.js"></script>
<script type="text/javascript" src="${ctx}/js/BigInt.js"></script>
<script type="text/javascript" src="${ctx}/js/Barrett.js"></script>
'
css='

'>
<div class="aw-register-box">
    <div class="mod-head">
        <h1>注册新用户</h1>
    </div>
    <div class="mod-body">
        <form class="aw-register-form" action="${ctx}/register" method="post" id="register_form">

            <ul>
                <li class="alert alert-danger hide error_message text-left">
                    <i class="icon icon-delete"></i> <em></em>
                </li>
                <li>
                    <input class="aw-register-email form-control" type="text" placeholder="邮箱" name="username"
                           data-toggle="tooltip"
                           title="请输入你常用的电子邮箱作为你的账号" value="">
                </li>
                <li>
                    <input class="aw-register-pwd form-control" type="password" id="realPassword" name="realPassword"
                           placeholder="密码">
                    <input type="hidden" id="password" name="password"/>
                </li>
                <li class="aw-register-verify">
                    <img class="pull-right" id="verifyCodeImg" onclick="this.src='${ctx}/verifyCode?d='+new Date()*1"
                         src="${ctx}/verifyCode">

                    <input type="text" class="form-control required" name="verifyCode" id="verifyCode"
                           placeholder="验证码">
                </li>
                <li class="last">
                    <label><input type="checkbox" checked="checked" value="agree" name="agreement_chk"> 我同意</label> <a
                        href="javascript:;" class="aw-agreement-btn">用户协议</a>
                    <a href="${ctx}/login/" class="pull-right">已有账号?</a>
                    <div id="aw-regiter-agreement" style="display: none;">
                        <div class="well" id="register_agreement">当您申请用户时，表示您已经同意遵守本规章。<br>
                            欢迎您加入本站点参与交流和讨论，本站点为社区，为维护网上公共秩序和社会稳定，请您自觉遵守以下条款：<br>
                            <br>
                            一、不得利用本站危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益，不得利用本站制作、复制和传播下列信息：<br>
                            　（一）煽动抗拒、破坏宪法和法律、行政法规实施的；<br>
                            　（二）煽动颠覆国家政权，推翻社会主义制度的；<br>
                            　（三）煽动分裂国家、破坏国家统一的；<br>
                            　（四）煽动民族仇恨、民族歧视，破坏民族团结的；<br>
                            　（五）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；<br>
                            　（六）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；<br>
                            　（七）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；<br>
                            　（八）损害国家机关信誉的；<br>
                            　（九）其他违反宪法和法律行政法规的；<br>
                            　（十）进行商业广告行为的。<br>
                            <br>
                            二、互相尊重，对自己的言论和行为负责。<br>
                            三、禁止在申请用户时使用相关本站的词汇，或是带有侮辱、毁谤、造谣类的或是有其含义的各种语言进行注册用户，否则我们会将其删除。<br>
                            四、禁止以任何方式对本站进行各种破坏行为。<br>
                            五、如果您有违反国家相关法律法规的行为，本站概不负责，您的登录信息均被记录无疑，必要时，我们会向相关的国家管理部门提供此类信息。
                        </div>
                    </div>

                </li>
                <li class="clearfix">
                    <button class="btn btn-large btn-blue btn-block" type="submit" name="submit" id="submit">注册</button>
                </li>
                <div id="verifyCodeCheckResult" style="display:<#if captchaError ??> block<#else> none</#if>"
                     class="alert alert-danger alert-dismissable text-center">
                    ${captchaError?if_exists}
                </div>
                <#if error ??>
    <div class="alert alert-danger alert-dismissable text-center">
        <button type="button" class="close" data-dismiss="alert"
                aria-hidden="true">
            &times;
        </button>
        ${error}
    </div>
                </#if>
            </ul>
        </form>
    </div>
    <div class="mod-footer"></div>
</div>
<script type="text/javascript">
    <#--显示/隐藏“用户协议”-->
    $('.aw-agreement-btn').click(function () {
        if ($('#aw-regiter-agreement').is(':visible')) {
            $('#aw-regiter-agreement').hide();
        }
        else {
            $('#aw-regiter-agreement').show();
        }
    });
    // 验证码模块
    $('#verifyCode').focus(function () {
        $("#submit").attr("disabled", false);
    });
    $('#verifyCode').blur(function () {
        var url = "${ctx}/verifyCodeCheck";
        var data = $('#verifyCode').val().trim();
        AjaxHelper.post(url, data, function (response) {
            if (Utils.isNotEmpty(response.message)) {
                $('#verifyCodeCheckResult').html('<button type="button" class="close" data-dismiss="alert"\n' +
                        '                            aria-hidden="true">\n' +
                        '                        &times;\n' +
                        '                    </button>' + response.message);
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
        $("#register_form").submit();
    });

    $(function () {
        $("[data-toggle='tooltip']").tooltip();
    });

</script>
</@ListPage.Html>