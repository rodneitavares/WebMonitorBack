package com.webmonitor.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.webmonitor.ApplicationContextLoad;
import com.webmonitor.model.ApplicationUser;
import com.webmonitor.repository.ApplicationUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAuthenticationService {
	
	/* Tempo de vida do token */
	private static final long EXPIRATION_TIME = 172800000;

	/* Senha unica para compor a autenticação e auxiliar na segurança */
	private static final String SECRET = "\\}TW_Y,cuQ#r6D:<6J{(67cqtSABmm@z";

	/* Prefixo padrão do token */
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/* Gerando token de autenticação e gerando a resposta para o navegador */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

		// montagem do token
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = TOKEN_PREFIX + " " + JWT;

		// Adicona ao cabeçalho Http
		response.addHeader(HEADER_STRING, token);

		corsAuthorization(response);

		// Escreve token como resposta no corpo do http
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	// retorna o usuario validado com token ou null caso invalido
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

		// pega o token enviado no cabeçalho http
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			// efetua validacao do token do usuario na requisacao.
			String user = Jwts.parser()
							.setSigningKey(SECRET)
							.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
							.getBody()
							.getSubject();

			if (user != null) {

				ApplicationUser appUser = ApplicationContextLoad.getApplicationContext()
						.getBean(ApplicationUserRepository.class).findUserByUsername(user);

				// retornar o usuario logado
				if (appUser != null) {
					return new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());
				}
			}
		}

		corsAuthorization(response);

		return null; // não autorizado

	}

	// Libera resposta para outras origens
	private void corsAuthorization(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}

	}
}
