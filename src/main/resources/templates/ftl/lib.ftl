<#--单引号或双引号后面遇到“<”格式化后会另起一行，引起js解析出错-->
<#assign escapeString="<">
<#macro MY_MODAL id="myModal" title="确认消息" content="" backdrop="static" keyboard="false">
	<!-- 模态框（Modal） -->
<div class="modal fade" id="${id}" tabindex="-1" role="dialog" aria-labelledby="myModalTitle"
     aria-hidden="true" data-backdrop="${backdrop}" data-keyboard="${keyboard}">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalTitle">
                    ${title}
                </h4>
            </div>
            <div class="modal-body">
                ${content}
    <#if title!="确认消息">{{ content }}</#if>
            </div>
            <#if title=="确认消息">
            <div class="modal-footer">
                <button id="${id}Close" type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button id="${id}Confirm" type="button" class="btn btn-primary">
                    确定
                </button>
            </div></#if>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</#macro>

<#macro webUploader thumbnailWidth="100" thumbnailHeight="100" serverUrl="">
<#--<script type="text/javascript">-->
    // 图片上传demo
    jQuery(function () {
        var $ = jQuery,
                $list = $('#fileList'),
                // 优化retina, 在retina下这个值是2
                ratio = window.devicePixelRatio || 1,

                // 缩略图大小
                thumbnailWidth = ${thumbnailWidth} *
        ratio,
                thumbnailHeight = ${thumbnailHeight} *
        ratio,

                // Web Uploader实例
                uploader;

        // 初始化Web Uploader
        uploader = WebUploader.create({

            // 自动上传。
            auto: true,

            // swf文件路径
            swf: 'http://cdn.staticfile.org/webuploader/0.1.0/Uploader.swf',

            // 文件接收服务端。
            server: '${serverUrl}',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker',

            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        // 当有文件添加进来的时候
        uploader.on('fileQueued', function (file) {
            var $li = $('${escapeString}div id = "' + file.id + '" class= "file-item thumbnail" > ' +'${escapeString}img>' +'${escapeString}div class= "info" > ' + file.name + ' ${escapeString}/div>' +'${escapeString}/div>');

            $img = $li.find('img');

            $list.append($li);

            // 创建缩略图
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                    $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo($li)
                        .find('span');
            }

            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file, response) {
            $('#' + file.id).addClass('upload-state-done');
    <#nested>
        });

        // 文件上传失败，现实上传出错。
        uploader.on('uploadError', function (file) {
            var $li = $('#' + file.id),
                    $error = $li.find('div.error');
            // 避免重复创建
            if (!$error.length) {
                $error = $('${escapeString}div class="error"></div>')
                        .appendTo($li);
            }
            $error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });
    });
</#macro>

<#macro RETURN_NO_ROW grid tip="请选择操作对象。">
<#--点击放大镜的cell，但没有选数据，表格数据为0也要报错-->
var rows2 = ${grid}.getDataLength();
  var rows = ${grid}.getSelectedRows();
  if (rows.length == 0 || rows2 == 0) {
      $('#tipModal').find('.modal-body').html("${tip}");
      $('#tipModal').modal('toggle');
      return false;
  }
</#macro>

<#macro MY_HR hrName="shortFat">
<#--自定义水平分隔线(horizontal rule)-->
    <#if hrName=="transparency" >
<label style="line-height:22px"></label>
    <#else>
<p style="margin:0 auto;border-top-style: solid;width: 3em;border-width: 5px;padding-bottom:8px;"></p>
        <p style="text-align:center;">
    </#if>
</#macro>

<#macro paging url>
<#--分页-->
<ul class="pagination inline">
<#--第一页，禁用“上一页”按钮-->
    <#if page.current gt 1>
        <li><a href="${url}/${page.current-1}">«</a></li>
    </#if>
<#--当前页的前3页和后3页-->
        <#list (page.current)..(page.pages) as index>
<li><a href="${url}/${index}">${index}</a></li>
        </#list>
<#--不是最后页有“下一页”按钮-->
    <#if page.current != page.pages>
    <li><a href="${url}/${page.current+1}">»</a></li>
    </#if>
</ul>
</#macro>

