package com.ultraviolet.delieve.model;

import java.io.Serializable;

public class User implements Serializable {
    public static final int USER_BEFORE_DELIEVER = 1;
    public static final int USER_WAITNG_FOR_JUDGE = 2;
    public static final int USER_DELIEVER = 3;

    private String token;

    private int type;

    public int getType() {
        return type;
    }
}
