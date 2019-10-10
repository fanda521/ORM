package orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**@author Fiee
 @version 创建时间：2019年7月10日 下午8:48:25
*/
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClass {

	public String myTable();
}
