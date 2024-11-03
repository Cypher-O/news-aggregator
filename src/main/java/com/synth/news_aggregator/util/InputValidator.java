// com/example/news_aggregator/util/InputValidator.java
package com.synth.news_aggregator.util;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class InputValidator {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern USERNAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9]{3,20}$");
    
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }
    
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Remove any HTML or script tags
        return input.replaceAll("<[^>]*>", "")
                   .replaceAll("javascript:", "")
                   .trim();
    }
}