package com.haina.flight.price.constants;

public enum CarrierEnum {
    CA("CA", "中国国航"),
    MU("MU", "东方航空"),
    CZ("CZ", "南方航空"),
    JD("JD", "首都航空"),
    MF("MF", "厦门航空");

    //通过航司二字码，获取航司中文名
    public static String getNameByCarrier(String carrier){
        return CarrierEnum.valueOf(carrier).name;
    }

    CarrierEnum(String carrier, String name) {
        this.carrier = carrier;
        this.name = name;
    }
    private String carrier;
    private String name;
}
