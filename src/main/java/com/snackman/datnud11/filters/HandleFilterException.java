package com.snackman.datnud11.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class HandleFilterException extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       log.info("--------------handle Exception Filter chain--------------");
        try {
            filterChain.doFilter(request,response);
        } catch (BadCredentialsException e){
            log.info("-----badcredentialsException  catch filter-----");
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error_message", e.getMessage());

            response.getWriter().write(convertObjectToJson(errorMap));
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            resolver.resolveException(request, response, errorMap, e);
        } catch (Exception e){
            log.info("-----exception catch filter-----");
            e.printStackTrace();
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error_message", e.getMessage());

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(errorMap));
            resolver.resolveException(request, response, errorMap, e);
        }
    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
