package com.example.spring_security_fundamementals_004.filters;

import com.example.spring_security_fundamementals_004.authentication.ApiKeyAuthentication;
import com.example.spring_security_fundamementals_004.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//My custom header Key authentication filter
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestKey = request.getHeader("x-api-key");

        //Delegate to the Http filter if the header key is null
        //remember i have got two authentications
        if("null".equals(requestKey) || requestKey == null){
            filterChain.doFilter(request,response);
        }

        //The filter has the responsibility of calling the manager // the manager calls the provider.
        //It checks whether the provider is supporting the given authentication
        CustomAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager(requestKey);
        //creating an instance of un-authenticated authentication
        ApiKeyAuthentication apiKeyAuthentication = new ApiKeyAuthentication(key);
        Authentication authenticate = customAuthenticationManager.authenticate(apiKeyAuthentication);
        if(authenticate.isAuthenticated()){

            //Security context is very important
            //We can get user details across the application once the user is authenticated
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request,response);
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
