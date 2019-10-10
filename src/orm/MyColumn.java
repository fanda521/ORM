package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)//作用于属性
@Retention(RetentionPolicy.RUNTIME)
public @interface MyColumn {
	public String columnName();
	public int length() default 50;
}
