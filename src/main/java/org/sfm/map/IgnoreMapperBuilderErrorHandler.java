package org.sfm.map;


import java.lang.reflect.Type;

public class IgnoreMapperBuilderErrorHandler implements MapperBuilderErrorHandler {
    @Override
    public void getterNotFound(final String msg) {
        throw new MapperBuildingException(msg);
    }

    @Override
    public void propertyNotFound(final Type target, final String property) {
    }

    @Override
    public void customFieldError(FieldKey<?> key, String message) {
        throw new MapperBuildingException(message);
    }

}
