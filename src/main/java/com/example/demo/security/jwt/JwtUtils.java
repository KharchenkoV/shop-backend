package com.example.demo.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${jwt.secret}")
	private String jwtSecrer;
	
	@Value("${jwt.expirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecrer)
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecrer).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecrer).parseClaimsJws(authToken);
			return true;
		}catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		}catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}catch (ExpiredJwtException e) {
			logger.error("JWM token is expired: {}", e.getMessage());
		}catch (UnsupportedJwtException e) {
			logger.error("JWM token is unsupported: {}", e.getMessage());
		}catch (IllegalArgumentException e) {
			logger.error("JWM claims string is empty: {}", e.getMessage());
		}
		
		return false;
		
	}
}
