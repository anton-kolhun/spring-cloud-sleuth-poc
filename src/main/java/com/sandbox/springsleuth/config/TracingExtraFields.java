package com.sandbox.springsleuth.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TracingExtraFields {

    KEY1("key1"), KEY2("key2");

    private String key;

    TracingExtraFields(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public static  List<String> getAllKeys() {
        return Arrays.stream(values()).
                map(TracingExtraFields::getKey)
                .collect(Collectors.toList());
    }
}
