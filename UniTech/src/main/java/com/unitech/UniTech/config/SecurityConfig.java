package com.unitech.UniTech.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.unitech.UniTech.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

	
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	
	 
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	
	    	PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    	System.out.print(pe.toString());
	    	
	        auth.userDetailsService(customUserDetailsService)
	            .passwordEncoder(passwordEncoder());
	    }
	 
	 @Bean
	    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		     return http.getSharedObject(AuthenticationManagerBuilder.class)
		             .build();
		 }


		
		
	@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    // Отключаем CSRF защиту
                    .authorizeRequests(requests -> requests
                            .requestMatchers("/user-details").authenticated()
                            .anyRequest().permitAll())
                    .formLogin(login -> login
                            .loginPage("/login")
                            .defaultSuccessUrl("/user-details", true)
                            .permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/logout") // URL, по которому будет выполняться выход
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")).
                    		csrf(csrf -> csrf.disable()) ; // Удалить куки (если используются)
	     return http.build();
	    }


	 
    @Configuration 
    class securityConfig {
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
        }
    }



	

}
