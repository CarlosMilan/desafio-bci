package com.mm.bci.desafio.apiusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private Timestamp timestamp;
    private Integer code;
    private String detail;

}
