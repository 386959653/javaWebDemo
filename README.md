# javaWebDemo
- java web demo，以后开发web应用可以基于这个demo开发
- 使用方法：
    1. idea中checkout from version control
    2. 数据库初始化：在mysql数据库中执行sql脚本文件doc/sqlScript/databaseInit.sql
    3. 在idea中配置tomcat，默认端口8080
    4. 用浏览器访问 http://localhost:8080/hello2
- 主要功能：
    1. mvc结构完整，便于后期基于这个骨架实现业务逻辑
    2. 实现了mybatis热加载
    3. 实现了在开发环境中控制台打印sql语句
    4. 实现了利用maven让应用在不同的环境使用不同的配置
    5. 实现基于springSecurity可以在数据库里配置的登录验证（默认用户名admin，密码是：123）
    7. 有完整的日志功能，获取日志语句：private Logger logger = LoggerFactory.getLogger(this.getClass());
    8. 提供了mybatis自动生成代码插件
    9. 实现了mybatis的分页功能
    10. 实现了把maven依赖库和插件远程仓库配置在pom.xml中（目前是阿里云的仓库）
    11. 既支持通过 @RestController 注解生成restful风格（前后端分离用），也支持通过 @Controller 注解，把页面传到freemarker
    12. 实现了mybatisPlus的通用CRUD功能
    13. 利用Druid实现性能监控，用浏览器访问 http://localhost:8080/druid 
    (用户名和密码在application.properties中的druid部分配置)
    14. 实现了子角色默认继承父角色权限，用户admin具有admin角色，用户test具有user角色，admin是user的子角色，
    密码都是123，访问http://localhost:8080/hello2需要user角色，访问http://localhost:8080/userList需要admin角色
    ，如果具有了admin角色，就可以访问只有user角色可以访问的链接,具体查看MyAccessDecisionManager.java 和
     MyInvocationSecurityMetadataSourceService.java，在sys_permission表中配置要过滤的链接，不仅支持全匹配
     也支持通配符匹配，比如/dashboardController/**，就能匹配所有/dashboardController/下的链接
    15. 实现退出系统功能
    16. 实现登录错误提示功能
    17. 实现jsonResult
    18. 实现自定义spring security 的 UserDetails
    19. 通过包装类AjaxHelper，实现Ajax错误提示
    20. 引入弹框组件artDialog，并通过DlgUtils包装，使它更好用
    21. 需要重新登录时候，实现了就算是Ajax也能直接跳转到登录页面
    22. 在DefaultView类中设置默认首页
    23. 实现自定义Freemarker共享变量，配置类MyFreemarkerView.java
    25. 实现把主键id用fastjson序列化时候，自动从Long转为String类型，具体类：LongToStringSerializer.java,
    再在SuperDomain.java的id上配置@JSONField(serializeUsing = LongToStringSerializer.class)
    26. 实现loading遮罩效果，js控制：$(".loading").toggle();
    27. 实现响应式模态框，定义在lib.ftl的宏MY_MODAL
    28. 实现maven只过滤application.properties，以免影响其它位于resource文件夹中的文件
    30. 实现“错误消息”和“提示消息”，定义在base.ftl，“错误消息”js api： 
    
            errorModal.content="内容";
            $('#errorModal').modal('toggle');
        “提示消息”js api： 
     
            tipModal.content="内容";
            $('#tipModal').modal('toggle');
       
    28. 实现web.xml设置session失效时间，目前为24小时
    29. 实现ctx放进共享变量,freemarker里直接用
    
            ${ctx}
    30. 实现freemarker分页模板在lib.ftl的page宏里，用法示例：
    
            <@com.paging url="${ctx}/dashboardController/product"/>
    31. 实现响应式登陆页面
    32. 实现验证码
    33. 实现“记住我”功能，默认记住7天，在 BrowerSecurityConfig.java配置
    34. 实现maven自动部署到远程服务器，在maven的verify阶段触发。需要在tomcat挂载根目录先上传autoDeploy.sh脚本，
    该脚本在doc文件夹里，如果项目名称改变脚本内容需要相应更改。
    35. 实现xml和javabean互转，具体类是XmlUtil.java
    36. 引入日期控件bootstrap-datetimepicker
    37. DateUtils增加“获取距离当前日期前几天的日期”方法
    38. 引入joda-time包实现@RequestParam可以直接把前端传过来的字符串转为日期
    39. 注入application.properties中自定义的配置(my.开头)到MyApplicationProperties，使用方法
    
            @Autowired
                private MyApplicationProperties myApplicationProperties;
                
    40. 实现用户注册功能
    41. 引入剪切复制js库clipboard.js api： 
        
            引入js：
            
                <!--引用剪切复制js-->
                    <script src="${ctx}/js/clipboard/clipboard.min.js"></script>
            html中的复制按钮：
                                      
                <a href="#" data-clipboard-target="#activationCode" class="js-copy btn btn-success btn-sm">复制</a>
            写在script中的： 
         
                <#--复制剪切功能-->
                    var clipboard = new ClipboardJS('.js-copy');
                    //复制成功执行的回调，可选
                    clipboard.on('success', function(e) {
                        layer.open({
                                    content: '复制成功！'
                                    ,skin: 'msg'
                                    ,time: 2 //2秒后自动关闭
                                });
                    });
                    //复制失败执行的回调，可选
                    clipboard.on('error', function(e) {
                        layer.open({
                                    content: '复制失败，请手动复制！'
                                    ,skin: 'msg'
                                    ,time: 2 //2秒后自动关闭
                                });
                    });
                
    42. 把系统参数放在数据库里，系统启动后，读入缓存，具体实现类MyApplicationRunner.java，对应数据库表sys_config，
    读取系统参数的api
    
            Cache.getSysConfigMap()
            
    43. 实现了同步、异步(采用了线程池，实现类 AsyncTaskConfig)邮件发送功能，实现类 MailUtils，api
            
                    @Autowired
                    private JavaMailSenderImpl mailSender;
                    
                    String[] receiver = {"386959653@qq.com"};
                            String subject = "This is a simple email";
                            String content = "This is a simple content";
                            // 异步发送邮件
                            mailUtils.sendSimpleEmailAsync(receiver, null, subject, content);
                    //        同步发送邮件
                            mailUtils.sendSimpleEmail(receiver, null, subject, content);
                    
    44. 实现全局图片默认都是响应式的，在myStyle.css中定义
    45. 引入动画css库 animate.css
    