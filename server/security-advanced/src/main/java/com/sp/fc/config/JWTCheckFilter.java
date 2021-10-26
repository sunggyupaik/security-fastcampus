package com.sp.fc.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.sp.fc.user.domain.SpUser;
import com.sp.fc.user.service.SpUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {
	private final SpUserService spUserService;

	public JWTCheckFilter(AuthenticationManager authenticationManager,
						  SpUserService spUserService) {
		super(authenticationManager);
		this.spUserService = spUserService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException {
		String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(bearer == null || !bearer.startsWith("Bearer ")){
			chain.doFilter(request, response);
			return;
		}
		String token = bearer.substring("Bearer ".length());
		VerifyResult result = JWTUtil.verify(token);
		if(result.isSuccess()){
			SpUser user = (SpUser) spUserService.loadUserByUsername(result.getUsername());
			UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
					user.getUsername(), null, user.getAuthorities()
			);
			SecurityContextHolder.getContext().setAuthentication(userToken);
			chain.doFilter(request, response);
		}else{
			throw new TokenExpiredException("Token is not valid");
		}
	}
}
