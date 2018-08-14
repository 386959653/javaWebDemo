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
    13. 利用Druid实现性能监控，用浏览器访问 http://localhost:8080/druid (默认用户名和密码都是123)
    14. 实现了子角色默认继承父角色权限，用户admin具有admin角色，用户test具有user角色，admin是user的子角色，
    密码都是123，访问http://localhost:8080/hello2需要user角色，访问http://localhost:8080/userList需要admin角色
    ，如果具有了admin角色，就可以访问只有user角色可以访问的链接
    15. 实现退出系统功能
    16. 实现登录错误提示功能
    17. 实现jsonResult
    18. 实现自定义spring security 的 UserDetails
    19. 通过包装类AjaxHelper，实现Ajax错误提示
    20. 引入弹框组件artDialog，并通过DlgUtils包装，使它更好用
    21. 需要重新登录时候，实现了就算是Ajax也能直接跳转到登录页面
    22. 在DefaultView类中设置默认首页
    23. 实现自定义Freemarker共享变量，配置类MyFreemarkerView.java
    24. 实现Freemarker统一引入类文件，可以在application.properties的auto_import字段配置，引入多个类用;隔开，
    配置类MyFreemarkerConfig.java
    25. 实现把主键id用fastjson序列化时候，自动从Long转为String类型，具体类：LongToStringSerializer.java,
    再在SuperDomain.java的id上配置@JSONField(serializeUsing = LongToStringSerializer.class)
    26. 实现loading遮罩效果，js控制：$(".loading").toggle();
    27. 实现响应式模态框，定义在lib.ftl的宏MY_MODAL