// com/synth/newsaggregator/exception/GlobalExceptionHandler.java
package com.synth.news_aggregator.exception;

import com.synth.news_aggregator.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(e.getMessage()));
    }
}
