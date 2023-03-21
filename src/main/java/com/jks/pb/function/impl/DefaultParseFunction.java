package com.jks.pb.function.impl;



import org.springframework.stereotype.Component;

import com.jks.pb.function.IParseFunction;


@Component("default")
public class DefaultParseFunction implements IParseFunction<String> {
    @Override
    public String functionName() {
        return this.getClass().getAnnotation(Component.class).value();
    }

    @Override
    public String apply(String value) {

        throw new UnsupportedOperationException();
    }
}
