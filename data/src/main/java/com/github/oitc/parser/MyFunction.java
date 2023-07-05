package com.github.oitc.parser;

import java.util.function.Function;

public class MyFunction implements Function<Integer, Integer> {

    private int value;

    MyFunction(int value) {
        this.value = value;
    }
    @Override
    public Integer apply(Integer arg) {
        return value + arg;
    }

}
