# ORM(对象-关系映射（Object/Relation Mapping)
@[toc]
## 一，项目简介
- 该项目是自己手写的ORM，那么ORM又是什么呢，在百度百科上是这样解释的：

> 是通过使用描述对象和数据库之间映射的元数据，将面向对象语言程序中的对象自动持久化到关系数据库中。本质上就是将数据从一种形式转换到另外一种形式。 这也同时暗示着额外的执行开销；然而，如果ORM作为一种中间件实现，则会有很多机会做优化，而这些在手写的持久层并不存在 更重要的是用于控制转换的元数据需要提供和管理；但是同样，这些花费要比维护手写的方案要少；而且就算是遵守ODMG规范的对象数据库依然需要类级别的元数据。

 简单点说就是，将数据库的表对象映射为java中的类对象，数据库表的记录就对应Java类中的一个实体对象。
 - 当然该项目不是简单的将数据库的表在java中定义相应的实体类，我们将利用反射机制，注解自动拼接通用的sql语句，这样就不用每次操作一个表时都重写一次sql语句，这样写好了dao层的sql拼接和执行方法，每新建一个表格对它进行相关的数据库操作时，只要建立一个对应的实体类，在类和属性属性上打上自定义的注解就行，而且对于不熟悉sql语句的程序员来说是一个很大的帮助。
- 拼接sql语句的简单机制主要是通过制定规则，通过获取数据库的表名，表的字段，自动生成相应的sql语句，规则这一部分是利用注解，先对应数据库的表写实体类，实体类的字段上打上自定义的注解，在利用反射生成对应的sql语句，直接调用。
- 在定义规则上其实有两种方法：1.利用注解 2.通过对实体类命名规则。本项目时使用了第一种方法，读者可以自己实现第二种。只是指定一个规则，别人使用时遵循我们定义的规则就可以直接使用，mybatis和mybatis-plus代码生成器也是利用反射和指定相应的规则实现的。
- - --
## 二，项目运行部署
- 项目的导入
读者下载该项目后可以在一下平台运行：Eclipse，STS，IDEA。这几个开发工具都是可以的。
- 项目中需要修改的地方
1.dao包下的/web3/src/dao/DbUtil.java文件

```java
//获取数据库连接
	/**
	 * 
	 * @return 获取数据库连接
	 */
	static String url = "jdbc:mysql://127.0.0.1:3306/custom_info";
	static String user = "root";
	static String pwd = "331224";
	static String driver = "com.mysql.jdbc.Driver";
```
把数据库的连接地址，用户名，密码修改成读者自己的即可。

- 数据库和相应的sql文件
我们使用的是mysql数据库，而且版本使用的是5.7的，读者可自行百度下载下关软件。我们使用到的sql文件存放在github仓库对应项目下的sql文件夹下，将里面的文件都导入数据里执行一遍。
- --
## 三，项目详解
##### 项目结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013104530840.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013104546689.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- --

##### 项目包功能讲解
- dao
 数据库的连接工具，sql语句的接口和接口的实现，实现类中完成了反射和sql语句的拼接和执行。
- --
- entity
与数据库中表对应的实体类，实体类已经打上了自定义的注解
- --
- orm
自定义的注解，用于反射拼接sql语句使用
- --
- service
服务类
- --
- servlet
用于处理前台页面的数据和跳转
- --
- test
用预测是反射类和sql语句的拼接和执行
- --


##### 项目功能
- 1.用户的登入，密码记住，注销
- 2.用户信息的查询，包括用户基本信息，记录信息
- 3.用户的添加
- 4.用户记录的添加
- 5.用户的修改
- 6.分页
- --
## 四，效果展示
- 登入界面效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133113378.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133124410.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- 主界面效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133147174.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
-  分页界面效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133215786.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- 用户信息界面效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133311197.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- 用户记录添加效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133335533.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133346445.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- 用户信息修改效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133406803.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133416307.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133425411.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- 用户信息添加效果展示
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133455503.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013133504289.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
- 后台操作日志打印
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191013140301934.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTcwNTI2,size_16,color_FFFFFF,t_70)
## 五，技术囊括
- java
- javaweb
- jsp
- html
- css
- js
- mysql
## 六，问题详解

## 七，问题反馈
	如果对该项目由疑问的或者不能够理解的，再或者导入项目运行不成功的可以通过以下方式联系笔者！
	1.电话号码：13870873449
	2.qq：1056015243
	3.邮箱地址：1056015243@qq.com
	4.github地址：https://github.com/fanda521/StudentManagerSystem
## 八，工作进程
|时间|功能完成  |
|--|--|
| 2019年8月6日 |编写数据库连接工具  |
| 2019年8月7日 | 熟悉java反射机制 |
| 2019年8月9日 |编写接口和实现类  |
| 2019年8月10日 | 完善实现类 |
| 2019年8月12日 | 设计数据库表和创建数据 |
| 2019年8月13日 | 编写前台登录和密码记住 |
| 2019年8月15日 | 实现分页 |
| 2019年8月16日 | 实现用户的增加和修改 |
| 2019年8月17日 |实现用户记录的增加和修改  |
| 2019年8月20日 | 实现注销 |

## 九， 项目源码下载地址
<https://github.com/fanda521/ORM>
## 如果觉得不错就点个赞吧
