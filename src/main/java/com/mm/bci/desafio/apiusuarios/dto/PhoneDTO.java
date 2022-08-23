package com.mm.bci.desafio.apiusuarios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
public class PhoneDTO {

    @NotNull
    private Long number;

    @NotNull
    private Integer citycode;

    @NotNull
    @JsonProperty("countrycode")
    private String countryCode;
}
