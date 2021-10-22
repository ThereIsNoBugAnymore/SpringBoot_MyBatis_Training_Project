package com.example.admin.utils;

import com.example.admin.exceptions.ParamsException;

public class AssertUtil {

    public static void isTrue(boolean flag, String msg) {
        if (flag)
            throw new ParamsException(msg);
    }
}
