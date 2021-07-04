package annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * This annotation stores all the instances of {@code Author} annotation.
 * It works in conjunction with {@code Author} which is annotated with the
 * {@code Repeatable} annotation, which in turn makes the annotation
 * {@code Author} to be annotated in the same target more than one time.
 *
 * @author ConfusedRobo
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Documented
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 */
@Documented
@Target({TYPE, FIELD, METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Authors {
    /**
     * The container which will store all the instances of {@code Author}
     */
    Author[] value();
}
