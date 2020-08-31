package com.angrysamaritan.wimixtest.validdators;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatches {
    String message() default "Passwords doesn't match";
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};
}
