package com.mg.utils;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UuidUtil {

    @NotNull
    public static String getUserId(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

}
