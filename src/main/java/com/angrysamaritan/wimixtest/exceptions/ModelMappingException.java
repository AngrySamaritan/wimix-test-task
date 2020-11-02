package com.angrysamaritan.wimixtest.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

@Value
@EqualsAndHashCode(callSuper = true)
@Log4j2
public class ModelMappingException extends RuntimeException {

    public ModelMappingException(Exception e, Class<?> i, Class<?> o) {
        super("Model mapping problem occurred", e);
        this.exception = e;
        this.i = i;
        this.o = o;
        log.error(String.format("Model mapping problem occurred on types: <%s, %s>",
                i.getName(), o.getName()));
        printStackTrace();
    }

    private static final long serialVersionUID = 5001898104229166275L;

    Exception exception;

    Class<?> i;

    Class<?> o;

}
