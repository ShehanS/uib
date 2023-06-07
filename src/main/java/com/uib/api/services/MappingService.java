package com.uib.api.services;

import com.uib.api.interfaces.IXMLMapping;
import org.springframework.stereotype.Service;

@Service
public class MappingService<T> implements IXMLMapping<T> {

    @Override
    public T inputComponentToXMLAttribute(T inputComponent) {

        return inputComponent;
    }
}
