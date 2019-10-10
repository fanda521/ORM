package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)//作用于类上的主机
@Retention(value = RetentionPolicy.RUNTIME) //一直到运行时有效
public @interface MyTable {
	public String tableName() default "";
}
