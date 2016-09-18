package od.chat.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by danila on 28.07.16.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface PerFragment {
}
