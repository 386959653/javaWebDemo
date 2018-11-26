<#import "lib.ftl" as com>
<#macro Html title="" css="" headAttr="" bodyAttr="">

<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>${title}</title>
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <!-- 3.3.7版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${ctx}/css/bootstrap/css/bootstrap.min.css" type="text/css"/>

    <!-- 3.3.7版本的 Bootstrap 核心 JavaScript 文件 -->
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>

    <!-- layer弹层组件 JavaScript 文件 -->
    <script type="text/javascript" src="${ctx}/js/layer_mobile/layer.js"></script>

    <!-- CSS 动画库文件 -->
    <link rel="stylesheet" href="${ctx}/css/animate.min.css" type="text/css"/>

    <link rel="stylesheet" href="${ctx}/css/myStyle.css" type="text/css"/>
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.min.js"></script>
    <script src="${ctx}/js/utils.js"></script>
    <link rel="stylesheet" href="../../css/adminLTE/component/font-awesome/css/font-awesome.css" type="text/css"/>

    <!-- 回到顶部插件 -->
    <script src="${ctx}/js/jquery.scrollToTop.min.js"></script>

    ${headAttr}

    <style>
            ${css}
    </style>
</head>

<body ${bodyAttr}>
    <@com.MY_MODAL id="errorModal" title="错误消息"/>
    <@com.MY_MODAL id="tipModal" title="提示消息"/>
<div class="loading" style="display: none;">
    <i class="fa fa-refresh fa-spin"></i>
</div>
<div style="z-index:1030">
    <a href="#top" id="toTop" style="display: inline; font-size: 4em" data-toggle="tooltip" title="回到顶部">
        <span class="glyphicon glyphicon-circle-arrow-up"></span></a>
</div>
    <#nested>
<script>
    var errorModal = new Vue({
        el: '#errorModal',
        data: {
            content: ''
        }
    });
    var tipModal = new Vue({
        el: '#tipModal',
        data: {
            content: ''
        }
    });
    //    回到顶部
    $(function () {
        $("#toTop").scrollToTop();
    });
</script>
</body>

</html>

</#macro>