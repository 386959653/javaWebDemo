# javaWebDemo
- java web demo，以后开发web应用可以基于这个demo开发
- 使用方法：
    1. idea中checkout from version control
    2. 把模块名称erp改为你要做的新项目名称，同时把pom文件中erp替换为你要做的新项目名称
    3. 把Project compiler output 路径改为“项目地址\target”如：D:\IdeaProjects\movie\target
    4. 在idea中配置tomcat，默认端口8080，before launch 里设置run maven goal clean 和 run maven goal package，deployment下的Application Context 设置为：“/”
    5. 数据库初始化：在mysql数据库中执行sql脚本文件doc/sqlScript/databaseInit.sql
    6. 点击idea左边jrebel面板，勾选后会在resources里出现rebel.xml,然后配置rebel.xml，让jrebel不处理mapper，因为mybatis热刷新已经处理了
    
            <classpath>
            		<dir name="项目路径/target/classes">
                        <!-- Exclude the unimportant subpackage of package1-->
                        <exclude name="项目路径/target/movie/WEB-INF/classes/mapper/**"/>
            		</dir>
            </classpath>
    7. 用浏览器访问 http://localhost:8080/helloTest
- 主要功能：
    1. mvc结构完整，便于后期基于这个骨架实现业务逻辑
    2. 实现了mybatis热加载
    3. 实现了在开发环境中控制台打印sql语句
    4. 实现了利用maven让应用在不同的环境使用不同的配置
    5. 实现基于springSecurity可以在数据库里配置的登录验证（默认用户名admin，密码是：123）
    6. 用户登录用户名验证实现类 MyUserDetailsService.java ，密码验证码验证实现类 MyAuthenticationProvider
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
     也支持通配符匹配，比如/dashboardController/**，就能匹配所有/dashboardController/下的链接。匹配的链接指的是
     @RequestMapping("匹配的链接")中定义的链接，不匹配get请求中?号后面的字符串。把权限匹配给哪个角色通过
     role_permission表配置，给用户增加一个角色，他就拥有该角色的权限，重新登录一下就可以生效。
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
    30. 实现freemarker分页，可以实现“到第一页”、“上一页”、“下一页”、“到最后一页”、
    “每次对多显示当前页到后面9页共10页”功能，模板在lib.ftl的page宏里，用法示例：
    
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
    46. 引入“回到顶部”插件
    47. 实现slickGrid单元格编辑框响应式，实现方法在myStyle.css中
                
                /*slickGrid单元格编辑框样式*/
                .editor-text {
                    height: 22px;
                    /*noinspection CssInvalidPropertyValue*/
                    width: -webkit-fill-available;
                }
                
    48. 引入lazyLoad，实现前端图片jquery懒加载，api如下：
    
            <script type="text/javascript" src="${ctx}/js/lazyLoad/jquery.lazyload.min.js"></script>
            <#--如果用了scrollstop事件，需要引入下面这个js-->
            <script type="text/javascript" src="${ctx}/js/lazyLoad/jquery.scrollstop.min.js"></script>
            <img src="${ctx}/img/lazyLoad.gif" data-original="实际要加载的图片地址"  class="lazy" >
            <#--方式一：没有事件-->
            <script type="text/javascript">
                $('img.lazy').lazyload();
                </script>
                
                <script type="text/javascript">
                <#--方式二：用了事件，如scrollstop事件-->
                $('img.lazy').lazyload({
                  event: 'scrollstop'
                });
                 </script>
    49. 实现手动刷新系统配置参数缓存功能，在controller里
                
                @Autowired
                private SysConfigService sysConfigService;
                
                //  刷新“系统参数缓存”
                    @ResponseBody
                    @RequestMapping("refreshSysConfig")
                    public JsonResult<?> refreshSysConfig() {
                        JsonResult jsonResult = new JsonResult();
                        try {
                            sysConfigService.refreshSysConfig();
                        } catch (Exception e) {
                            jsonResult.setStatus(JsonResult.ERROR);
                            e.printStackTrace();
                        }
                
                        return jsonResult;
                    }
    50. 引入caffeine缓存，用springBootCache管理 api可以参考springBootCache，
    注意，有些情形下注解式缓存是不起作用的：同一个bean内部方法调用，子类调用父类中有缓存注解的方法等。
    不起作用是因为缓存切面必须走代理才有效，这时可以手动使用CacheManager来获得缓存效果，例子如下：
            
            @Autowired
                CacheManager cacheManager;
                
            hotGoodsList = (List<HotGoods>) cacheManager.getCache("myCache").get("getHotGoods", ArrayList.class);
                    if (CollectionUtils.isEmpty(hotGoodsList)){
                        cacheManager.getCache("myCache").put("getHotGoods",this.getHotGoods());
                        hotGoodsList = (List<HotGoods>) cacheManager.getCache("myCache").get("getHotGoods", ArrayList.class);
                    }
    51. 引入slickGrid表格编辑前端框架
    52. 实现前端用户登录和注册密码RSA加密，后端解密
    53. 引入计数特效js插件“countUp.js”
    54. 改造“slick.grid.js”，添加语句
            
             // 弹出错误提示
              layer.open({
                  title: [
                      '错误消息',
                      'background-color: #FF4351; color:#fff;'
                  ]
                  ,
                  content: validationResults.msg
                  ,btn: '我知道了'
              });
        这样就是完善了单元格输入数据验证功能，使用示例：
                            
            function searchNumValidator(value) {
                       if (value.length != 4 || isNaN(value)) {
                           return {valid: false, msg: "内检号格式不正确，应为4位数字！"};
                       } else {
                           return {valid: true, msg: null};
                       }
                   }
                //   以下是对应的列（columns）定义
            , {
                           id: "searchNum",
                           name: "内检号",
                           field: "searchNum",
                           width: 60
                           ,editor: Slick.Editors.Text
                           ,validator: searchNumValidator
                       }
           
    55. 引入swagger自动生成API文档
        
        链接地址：http://localhost:8087/swagger-ui/
        生成MD文档类：src/main/java/com/weichi/erp/tool/ApiDocGen.java
    56.