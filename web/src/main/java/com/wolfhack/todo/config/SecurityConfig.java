package com.wolfhack.todo.config;

import com.wolfhack.todo.filter.JwtFilter;
import com.wolfhack.todo.security.JWTAuthenticationManager;
import com.wolfhack.todo.security.PBKDF2Encoder;
import com.wolfhack.todo.security.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true)
@RequiredArgsConstructor
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig {

	private final JWTAuthenticationManager jwtAuthenticationManager;
	private final JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityWebFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
		http.exceptionHandling(exceptionHandler -> exceptionHandler
						.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage()))
						.accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage())))
				.authorizeHttpRequests((authorize) ->
						authorize.requestMatchers("/sign-up", "/sign-in", "/activate/**").permitAll()
								.anyRequest().authenticated())
				.sessionManagement((manager) -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationManager(jwtAuthenticationManager)
				.cors(cors -> cors.configure(http))
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserSecurityService userSecurityService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userSecurityService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PBKDF2Encoder();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder builder, UserSecurityService userSecurityService) throws Exception {
		builder.userDetailsService(userSecurityService);
	}

}
