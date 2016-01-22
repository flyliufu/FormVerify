package com.lefu.verify.util;

/**
 * 表单验证类型枚举值
 *
 * @author liufu on 16/1/15.
 */
public enum Type {

    TEXT(0),
    PHONE(1),
    EMAIL(2),
    ID_CARD(3),
    PASSWORD(4);

    private int id;

    public int getId() {
        return id;
    }

    Type(int id) {
        this.id = id;
    }

}
