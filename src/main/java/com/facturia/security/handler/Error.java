package com.facturia.security.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

    @JsonProperty("tipo")
    private String type;
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("mensaje")
    private String message;

}
