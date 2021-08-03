package com.webmonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.webmonitor.service.ImplApplicationUserDetailService;

@EnableWebSecurity
@Configuration
/* Mapeia URL, endereços, autoriza ou bloqueia acessos a URLs. */
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplApplicationUserDetailService implApplicationUserDetailService;

	/* Configura as solicitações de acesso por Http */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/* Ativando a proteção contra usuário que não estão validados por TOKEN */
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

				/*Ativando a permissão para acesso a página incial do sistema EX: sistema.com.br/index */
				.disable().authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/index").permitAll()
				
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

				/* URL de Logout - Redireciona após o user deslogar do sistema */
				//.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index");

				/* Maperia URL de Logout e invalida o usuário */
				//.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				/* Filtra requisições de login para autenticação */
				/*.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)*/	
				
				/* Filtra demais requisições para verificar a presença do TOKEN JWT no HEADER HTTP */
				//.addFilterBefore(new JWTApiAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
				
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/* Service que irá consultar o usuário no banco de dados */
		auth.userDetailsService(implApplicationUserDetailService)

				/* Padrão de codifição de senha */
				.passwordEncoder(new BCryptPasswordEncoder());

	}

}
