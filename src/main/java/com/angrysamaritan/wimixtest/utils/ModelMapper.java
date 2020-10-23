package com.angrysamaritan.wimixtest.utils;

import com.sun.istack.NotNull;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Log4j2
public class ModelMapper {

    @NotNull
    public static <I, O> O map(I inputObject, Class<O> outputClass) {
        O outputObject = null;
        try {
            outputObject = outputClass.getConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            log.error(e);
        }
        Class<?> inputClass = inputObject.getClass();
        for (Field outputField : outputClass.getFields()) {
            try {
                Field inputField = inputClass.getField(outputField.getName());
                if (inputField.getType().equals(outputField.getType())) {
                    outputField.set(outputObject, inputField.get(inputObject));
                }
            } catch (NoSuchFieldException ignored) {
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                log.error(e);
            }
        }
        return outputObject;
    }
}
