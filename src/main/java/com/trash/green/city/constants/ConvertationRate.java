package com.trash.green.city.constants;

import java.util.stream.Stream;

public enum ConvertationRate {
    PLASTIC(300),
    ORGANIC(500),
    MIXED(600),
    PAPER(550);

    private Integer rate;

    ConvertationRate(Integer rate) {
        this.rate = rate;
    }

    public static ConvertationRate getByType(String type) {
        return Stream.of(ConvertationRate.values()).filter(value -> value.name().equalsIgnoreCase(type)).findFirst().get();
    }

    public Integer getRate() {
        return rate;
    }
}
