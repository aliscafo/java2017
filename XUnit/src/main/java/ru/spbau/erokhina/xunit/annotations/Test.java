package ru.spbau.erokhina.xunit.annotations;

import com.sun.istack.internal.NotNull;
import ru.spbau.erokhina.xunit.exceptions.NotException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for methods that were annotated as Test.
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    @NotNull
    String ignore() default "";

    @NotNull
    Class<? extends Throwable> expected() default NotException.class;
}
