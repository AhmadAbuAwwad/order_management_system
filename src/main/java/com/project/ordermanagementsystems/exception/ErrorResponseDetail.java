package com.project.ordermanagementsystems.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDetail {

    private String error;
    private Integer status;
    private String pointer;
    private String details;
    private String code;
}
