package annotationTest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationClass {
    String type();
}
