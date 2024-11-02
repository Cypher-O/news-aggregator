// com/synth/news_aggregator/dto/ApiResponse.java
package com.synth.news_aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(0, "success", message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(1, "error", message, null);
    }
}