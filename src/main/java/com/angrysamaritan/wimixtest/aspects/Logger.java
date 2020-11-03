package com.angrysamaritan.wimixtest.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logger {

    @Pointcut(value = "execution(com.angrysamaritan.wimixtest.service.)")

}
