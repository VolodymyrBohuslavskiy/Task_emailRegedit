package com.example.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter {


//    @Override//     securityPart1InMemoryUser
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("pass")).roles("ADMIN");
//    }


    @Override//    secPart2ConfigureHTTP
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/authorization/**").permitAll()
                .antMatchers(HttpMethod.POST, "/add").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/hello")//handle with post mapping in controller
                .failureUrl("/login?error").permitAll()
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                logoutSuccessUrl("/login")
                .permitAll();
    }

    @Bean//     secPath5PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired// sec DaoAuthenticationProvider
    @Qualifier("userService")// Вибір BEAN для ініціалізації, інакше буде помилка !
    private UserDetailsService userDetailsService;

    @Bean//     securityPath6AuthenticationProvider
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
