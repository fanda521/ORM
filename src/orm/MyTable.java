package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)//���������ϵ�����
@Retention(value = RetentionPolicy.RUNTIME) //һֱ������ʱ��Ч
public @interface MyTable {
	public String tableName() default "";
}
