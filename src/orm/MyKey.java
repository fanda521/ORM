package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)//作用于属性
@Retention(RetentionPolicy.RUNTIME)
public @interface MyKey {
	public String columnName(); //表示主键列
	//主键是区分自动增长和不自动增长
	public boolean isGenerator() default false;//默认不是自动增长的
}
