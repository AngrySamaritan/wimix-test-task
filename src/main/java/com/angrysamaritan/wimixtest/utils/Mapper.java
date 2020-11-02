package com.angrysamaritan.wimixtest.utils;

import com.angrysamaritan.wimixtest.exceptions.ModelMappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    private final ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <I, O> O map(I i, Class<O> o) {
        try {
            return modelMapper.map(i, o);
        } catch (Exception e) {
            throw new ModelMappingException(e, i.getClass(), o);
        }
    }
}
