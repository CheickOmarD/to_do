package com.technologia.to_do.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.technologia.to_do.security.HttpResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static com.technologia.to_do.security.SecurityConstants.FORBIDDEN_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthentificationEntryPoint implements AuthenticationEntryPoint {


    /**
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException
                        ) throws IOException, ServletException {
        HttpResponse httpResponse = new HttpResponse(new Date(),FORBIDDEN.value(),
                FORBIDDEN,
                FORBIDDEN.getReasonPhrase().toUpperCase(),
                FORBIDDEN_MESSAGE);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream,httpResponse);
        outputStream.flush();
    }
}
