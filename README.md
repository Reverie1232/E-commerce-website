## 个人信息

### 学号：202130442266 姓名：张尔彦

### 用户测试
- 登录地址：[http://8.134.166.18:8080/login](http://8.134.166.18:8080/login)
- 邮箱：1097094336@qq.com
- 密码：123

### 商户测试
- 登陆地址：[http://8.134.166.18:8080/merchantLogin](http://8.134.166.18:8080/merchantLogin)
- 用户名：web
- 密码：123

## 主要代码文件说明

### 1. `src/main/java/com/` 文件夹

- `com.example.controller/`：包含[控制器类](src/main/java/com/example/controller/)，处理 HTTP 请求和响应。
- `com.example.service/`：包含[服务层代码](src/main/java/com/example/service/)，处理业务逻辑。
- `com.example.pojo/`：存放[数据模型（POJO）类](src/main/java/com/example/pojo/)。
- `com.example.mapper/`：包含 MyBatis [Mapper 接口](src/main/java/com/example/mapper/)。

### 2. `src/main/resources/` 文件夹

- `application.yml`：[应用程序的配置文件](src/main/resources/application.yml)，包含数据库连接信息、邮箱配置等。
- `static/`：存放[静态资源文件](src/main/resources/static/)，如 CSS 和 JavaScript。
- `templates/`：存放[模板文件](src/main/resources/temolates/)，用于视图渲染（ Thymeleaf 模板）。

 ### 3. `pom.xml`
- Maven [项目的配置文件](pom.xml)，定义了项目的依赖、插件和其他构建设置。

