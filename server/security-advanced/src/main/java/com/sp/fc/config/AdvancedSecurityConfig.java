package com.sp.fc.config;

import com.sp.fc.user.service.SpUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdvancedSecurityConfig extends WebSecurityConfigurerAdapter {
	private final SpUserService spUserService;

	public AdvancedSecurityConfig(SpUserService spUserService) {
		this.spUserService = spUserService;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager());
		JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), spUserService);
		http
				.csrf().disable()
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(checkFilter, BasicAuthenticationFilter.class);
	}
}
