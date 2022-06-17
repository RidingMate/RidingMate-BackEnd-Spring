package com.ridingmate.api.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private String secretKey = "SECRET_KEY";
	
	private final long VALIDITY_IN_MILLISECONDS = 1000L * 60 * 60 * 24 * 90; //90일

	@Autowired
	private CustomUserDetailsService customUserDetails;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
		Date now = new Date();
		Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);
		
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public String generateToken(String username) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);
		return Jwts.builder()
				   .setSubject(username)
				   .setIssuedAt(now)
				   .setExpiration(validity)
				   .signWith(SignatureAlgorithm.HS512, secretKey)
				   .compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		
		if(bearerToken != null && !bearerToken.startsWith("Bearer ")) {
			return "";
		}
		
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = customUserDetails.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}
