package annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * This annotation stores all the instances of {@link Author} annotation.
 * It works in conjunction with {@link Author} which is annotated with the
 * {@link Repeatable} annotation, which in turn makes the annotation
 * {@link Author} to be annotated in the same target more than one time.
 *
 * @author ConfusedRobo
 *
 * @see Annotation
 * @see Documented
 * @see Target
 * @see Retention
 */
@Documented
@Target({TYPE, FIELD, METHOD})
@Retention(CLASS)
@Author(
        author = "ConfusedRobo",
        creation = "29-06-2021",
        profile = "https://github.com/ConfusedRobo"
)
public @interface Authors {
    /**
     * The container which will store all the instances of {@link Author}
     */
    Author[] value();
}
