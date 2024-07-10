package com.example.EcommerceLaptopShop.utils;

import com.example.EcommerceLaptopShop.entity.User;
import com.example.EcommerceLaptopShop.service.CustomUserDetailService;
import com.example.EcommerceLaptopShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return ((request, response, accessDeniedException)
                -> response.sendRedirect(request.getContextPath() + "/error/403"));
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User oauth2User = delegate.loadUser(request);

            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String username = name;
            String password = "oauth2password";

            User user = userService.createUserIfNotExists(email, name, username, password);

            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            mappedAuthorities.add(new SimpleGrantedAuthority("USER"));

            return new DefaultOAuth2User(mappedAuthorities, oauth2User.getAttributes(), "email");
        };
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/css/**", "/js/**", "/", "/error", "/images/**", "shop/**")
                                .permitAll()
                                .requestMatchers("/forgotPassword", "/resetPassword/**", "/register", "login")
                                .permitAll()
                                .requestMatchers("admin/**")
                                .hasAnyAuthority("ADMIN")
//                                .requestMatchers("shop/products/**")
////                        .authenticated()
//                                .hasAnyAuthority("ADMIN", "USER")
                                .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .userInfoEndpoint(userInfoMapping -> userInfoMapping
                                .userService(oauth2UserService())
                        )
                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler()))
                .build();
    }
}
