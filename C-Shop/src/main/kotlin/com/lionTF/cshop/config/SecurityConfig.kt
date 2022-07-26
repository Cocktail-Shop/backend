package com.lionTF.cshop.config

import com.lionTF.cshop.domain.member.handler.CustomAccessDeniedHandler
import com.lionTF.cshop.domain.member.handler.OAuthFailureHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
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
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()//post 요청 허용
        http.sessionManagement()
            .maximumSessions(1)
            .expiredUrl("/members/session-expire")
            .sessionRegistry(sessionRegistry())


        return http.authorizeRequests()
            .antMatchers("/admins/**").hasRole("ADMIN")
            .antMatchers("/members").hasAnyRole("MEMBER", "ADMIN")
            .antMatchers("/members/deny").hasRole("MEMBER")
            .antMatchers("/members/login").anonymous()
            .antMatchers("/members/password").authenticated()
            .antMatchers("/members/logout").authenticated()
            .antMatchers("/items").permitAll()
            .antMatchers("/items/**").hasAnyRole("ADMIN", "MEMBER")
            .antMatchers("/orders/**").hasAnyRole("ADMIN", "MEMBER")
            .antMatchers("/cart/**").hasAnyRole("ADMIN", "MEMBER")
            .antMatchers("/wish-list/**").hasAnyRole("ADMIN", "MEMBER")
            .antMatchers("/pre-members/**").hasRole("PREMEMBER")
            .and()
            .formLogin()
            .loginPage("/members/login")
            .loginProcessingUrl("/members/login")
            .defaultSuccessUrl("/members")
            .failureUrl("/members/login-fail")
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/members")
            .failureHandler(OAuthFailureHandler())
            .and()
            .logout()
            .logoutUrl("/members/logout")
            .logoutSuccessUrl("/members/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and()
            .exceptionHandling()
            .accessDeniedHandler(CustomAccessDeniedHandler())
            .and()
            .build()
    }
}
