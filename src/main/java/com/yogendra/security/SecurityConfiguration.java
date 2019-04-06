package com.yogendra.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.inMemoryAuthentication().withUser("Dhananjay Yadav").password(
	 * "testadmin123!").roles("Admin").and()
	 * .withUser("Yogendra Raikwar").password("testadmin123!").roles("Dealer");
	 * 
	 * 
	 * auth.userDetailsService(UserDetailsSerivceImpl);
	 * 
	 * 
	 * }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.
		    cors().and().
		    authorizeRequests().
		    antMatchers("/jwt/**").authenticated().
		    antMatchers("/auth/**").permitAll().and().
		    httpBasic().realmName("DY_ASSOCIATES").and().
		    sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
		    csrf().disable().
		    addFilterBefore(jwtTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
       // @formatter:on
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.setAllowCredentials(true);
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "Origin",
				"Accept", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public JwtTokenFilter jwtTokenFilterBean() {
		return new JwtTokenFilter();
	}

}
