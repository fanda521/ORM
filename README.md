# ORM
自己写的一个小的orm框架，主要是通过制定规则，通过获取数据库的表名，表的字段，自动生成相应的sql语句，规则这一部分是利用注解，先对应数据库的表写实体类，实体类的字段上打上自定义的注解，在利用反射生成对应的sql语句，直接调用。
