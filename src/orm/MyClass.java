package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**@author Fiee
 @version ����ʱ�䣺2019��7��10�� ����8:48:25
*/
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClass {

	public String myTable();
}
