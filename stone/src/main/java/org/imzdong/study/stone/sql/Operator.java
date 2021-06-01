package org.imzdong.study.stone.sql;

/**
 * @author admin
 * @date 2021/5/29 下午7:39
 */
public enum Operator {

    EQ("=","等于"),
    GT(">","大于"),
    LT("<","小于"),
    GE(">=","大于等于"),
    LE("<=","小于等于"),

    IN("IN","在集合中"),

    AND("AND","与"),
    OR("OR","或"),

    ;

    String operator;
    String desc;

    Operator(String operator, String desc){
        this.operator = operator;
        this.desc = desc;
    }
}
