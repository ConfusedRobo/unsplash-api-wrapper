package annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * A custom annotation that merely specifies information
 * about the author of the source code.
 * <ul>
 *     <li>It has been made repeatable as there could be multiple authors for a class.
 *     <li>Lastly, this annotation can be used for {@code TYPE, FIELD, METHOD}.
 *     <li>Its Retention policy is set to {@code CLASS}.
 *
 * @author ConfusedRobo
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Documented
 * @see java.lang.annotation.Repeatable
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 */
@Documented
@Repeatable(Authors.class)
@Target({TYPE, FIELD, METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Author {
    /**
     * The author of the {@code TYPE, FIELD, METHOD}.
     */
    String author();

    /**
     * Date of which the {@code TYPE/FIELD/METHOD} was created.
     */
    String creation();

    /**
     * Link your github profile (preferably)
     */
    String profile();
}
