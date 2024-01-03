package com.example.spring_security_fundamementals_004.config;

import com.example.spring_security_fundamementals_004.filters.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig  {

    @Value("${the.secret}")
    private String key;

    //What is happening here??
    //1.I am configuring the http basic filter as my app runs (Everything including its basic authentication filter)
    //2. just before requests are forwarded to that filter,i am telling spring security to run my custom filter
    //N.B This is creating the security configuration for my Application
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http){
//        return http.httpBasic()
//                .and()
//                .addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class)
//                .authorizeRequests().anyRequest().authenticated() //means all endpoints will be authenitcated
//                //.and().authenticationManager() // Very powerful, i can override authentication manager from this method or i can add a bean of type AuthenticationManager in my Context.     //Good design (Have one authentication manager that will then delegate to my Provider)
//                //.and().authenticationProvider()//It doesnt override the AP, it adds one more to the collection
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new ApiKeyFilter(key),BasicAuthenticationFilter.class)
                .httpBasic(withDefaults());

        return http.build();
    }


}
