package com.angrysamaritan.wimixtest.validdators;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Wrong email format";
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};
}
