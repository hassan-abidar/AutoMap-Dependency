package hassan.abidar.automap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD}) // This annotation can be applied to classes or fields
@Retention(RetentionPolicy.RUNTIME) // This annotation will be available at runtime
public @interface AutoMap {
    Class<?> target() default void.class; // Target class for mapping (optional)
    String field() default ""; // Target field name (optional)
}
