package khh.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
//@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Target({ ElementType.FIELD})
public @interface Convert {
//	Class[] value() default Convertor.class;
	Class <? extends Convertor> before() default Convertor.class;
	Class <? extends Convertor> after() default Convertor.class;

}
