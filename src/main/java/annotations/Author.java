package annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Repeatable(Authors.class)
@Target({TYPE, FIELD, METHOD})
public @interface Author {
    String author();
    String creation();
    String profile();
}
