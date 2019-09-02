package com.haina.flight.price.response;

import com.haina.flight.price.model.Flight;

import java.util.List;

public class FlightResult {
    //0 success   1 empty   2 fail/error
    private int code;
    //描述信息
    private String msg;

    private List<Flight> data;

    public static FlightResult success(List<Flight> data){
        FlightResult result = new FlightResult();
        result.setCode(0);
        result.setMsg("success");
        result.setData(data);
        return result;
    }
    //失败不需要数据
    public static FlightResult fail(){
        FlightResult result = new FlightResult();
        result.setCode(2);
        result.setMsg("fail");
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Flight> getData() {
        return data;
    }

    public void setData(List<Flight> data) {
        this.data = data;
    }
}
