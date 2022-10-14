package com.mm.bci.desafio.apiusuarios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PhoneDTO {

    @NotNull
    private Long number;

    @NotNull
    @JsonProperty("city_code")
    private Integer citycode;

    @NotNull
    @JsonProperty("country_code")
    private String countryCode;
}
