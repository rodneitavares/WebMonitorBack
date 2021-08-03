package com.webmonitor.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webmonitor.model.ApplicationUser;

/*Estabelece o gerenciado de token*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	/*Configurando o gerenciado de autenticacao*/
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		/*Obriga a autenticar a URL*/
		super( new AntPathRequestMatcher(url) );
	
		/*Gerenciador de autenticação*/
		setAuthenticationManager(authenticationManager);
	}

	
	/*Retorna o usuario ao processar a autenticacao*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		/*Esta pegando o token para validar*/
		ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
		
		/*Retornar o usuario login, senha e acessos*/
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		new JWTTokenAuthenticationService().addAuthentication(response, authResult.getName());
	}

}
