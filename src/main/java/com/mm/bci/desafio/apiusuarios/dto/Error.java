package com.mm.bci.desafio.apiusuarios.dto;

import java.sql.Timestamp;

public class Error {
    private Timestamp timestamp;
    private Integer code;
    private String detail;

    public Error() {

    }

    public Error(Timestamp timestamp, Integer code, String detail) {
        this.timestamp = timestamp;
        this.code = code;
        this.detail = detail;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
