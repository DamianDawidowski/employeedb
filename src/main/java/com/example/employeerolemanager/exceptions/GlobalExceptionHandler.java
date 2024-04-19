package com.example.employeerolemanager.exceptions;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception, Authentication authentication) {
      

        ProblemDetail errorDetail = null;

        
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");
            
          
            if(authentication==null) {
                LOGGER.info("User couldn't log in due to error: {}", exception.getMessage());
            } else {
                LOGGER.info("User {} couldn't log in due to error: {}", authentication.getName(), exception.getMessage());
            }
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");

            LOGGER.info("User couldn't log in due to locked account");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");

            if(authentication==null) {
                LOGGER.info("User couldn't access the resource due to lack of authorisation");
            } else {
                LOGGER.info("User {} couldn't access the resource due to lack of authorisation", authentication.getName());
            }
           
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");

            LOGGER.info("User couldn't access the resource due to wrong JWT signature");

        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");

            LOGGER.info("User couldn't access the resource due to expired JWT signature");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
            
            if(authentication==null) {
                LOGGER.info("User operation caused an unknown internal server error");
            } else {
                LOGGER.info("User {} operation caused an unknown internal server error", authentication.getName());
            } 
        }
 
        return errorDetail;
    }
}