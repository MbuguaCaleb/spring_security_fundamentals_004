package com.example.spring_security_fundamementals_004.managers;

import com.example.spring_security_fundamementals_004.providers.ApiKeyProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final String key;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        ApiKeyProvider provider = new ApiKeyProvider(key);
        if(provider.supports(authentication.getClass())){
            return provider.authenticate(authentication);
        }
        return authentication;
    }
}
