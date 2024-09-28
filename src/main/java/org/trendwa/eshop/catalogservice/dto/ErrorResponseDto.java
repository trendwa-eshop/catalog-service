package org.trendwa.eshop.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

    private int statusCode;
    private String errorCode;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp;
    private String details;
    private String path;
    private String exceptionType;
}
