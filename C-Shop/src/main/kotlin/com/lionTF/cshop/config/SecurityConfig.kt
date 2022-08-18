package com.lionTF.cshop.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun defaultSecurityFilterChain(http:HttpSecurity): SecurityFilterChain{
        http.csrf().disable()//post 요청 허용
        http.sessionManagement().maximumSessions(1)//중복로그인 방지

        http.oauth2Login()
            .defaultSuccessUrl("/members")

        return http.authorizeRequests()
            //.antMatchers("/user/**").authenticated()
            //.antMatchers("/admins/**").hasRole("ADMIN")
            .anyRequest()    // 모든 요청에 대해서 허용하라.
            .permitAll()
            .and()
            .formLogin()
            .loginPage("/members/login")
            .loginProcessingUrl("/members/login")
            .defaultSuccessUrl("/members")
            .failureUrl("/members/login-fail")
            .and()
            .logout()
            .logoutUrl("/members/logout") // 로그아웃 처리 URL
            .logoutSuccessUrl("/members/login") // 로그아웃 성공 후 이동페이지
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and()
            .build()
    }
}