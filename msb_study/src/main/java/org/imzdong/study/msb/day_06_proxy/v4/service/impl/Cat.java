package org.imzdong.study.msb.day_06_proxy.v4.service.impl;

import org.imzdong.study.msb.day_06_proxy.v4.service.Animal;

public class Cat implements Animal {

    @Override
    public String eat(String food) {
        return "server eat food:"+food;
    }
}
