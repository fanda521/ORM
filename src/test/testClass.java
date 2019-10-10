package test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import entity.CustomerInfo;
import entity.CustomerUser;
import orm.MyColumn;
import orm.MyKey;

public class testClass {
	public static void main(String[] args) {
		test1();

	}

	private static void test1() {
		// TODO Auto-generated method stub
		Class clazz=CustomerInfo.class;
		Field[] Fields = clazz.getDeclaredFields();
		for (Field field : Fields) {
			String name = field.getName();
			System.out.println(name);
			Annotation[] Annotations = field.getDeclaredAnnotations();
			for (Annotation anno : Annotations) {
				if ( anno instanceof MyKey) {
					MyKey key=(MyKey) anno;
					String keyName = key.columnName();
					System.out.println(keyName);
				}
				else if ( anno instanceof MyColumn) {
					MyColumn column=(MyColumn) anno;
					String columnName = column.columnName();
					System.out.println(columnName);
				}
			}
			
		}
	}
	
}
