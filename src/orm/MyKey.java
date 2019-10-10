package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)//����������
@Retention(RetentionPolicy.RUNTIME)
public @interface MyKey {
	public String columnName(); //��ʾ������
	//�����������Զ������Ͳ��Զ�����
	public boolean isGenerator() default false;//Ĭ�ϲ����Զ�������
}
