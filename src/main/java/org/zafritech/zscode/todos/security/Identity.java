package org.zafritech.zscode.todos.security;

import java.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zafritech.zscode.todos.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class Identity {

	private static final Logger logger = LoggerFactory.getLogger(Identity.class);

	@Autowired
	private JwtConfig jwtConfig;
	
	public Identity()  { 

	}
	
	public boolean isValidToken(String token) throws SignatureException {
		
		return validateToken(token);
	}

	public String getApiKey(String token) throws SignatureException {
		
		return getClaims(token).get("uid", String.class);
	}

	public String getEmail(String token) throws SignatureException {
		
		return getClaims(token).get("eml", String.class);
	}

	public String getName(String token) throws SignatureException {
		
		return  getClaims(token).get("fnm", String.class) + " " + getClaims(token).get("lnm", String.class);
	}
	
	public String[] getRoles(String token) throws SignatureException {
		
		return getClaims(token).get("authorities", String[].class);
	}

	private boolean validateToken(String authToken) throws SignatureException {

        try {
        	
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(authToken);
            return true;
            
        } catch (MalformedJwtException ex) {
        	
            logger.error("Invalid JWT token");
            
        } catch (ExpiredJwtException ex) {
        	
            logger.error("Expired JWT token");
            
        } catch (UnsupportedJwtException ex) {
        	
            logger.error("Unsupported JWT token");
            
        } catch (IllegalArgumentException ex) {
        	
            logger.error("JWT claims string is empty.");
        }
        
        return false;
	}
	
	private Claims getClaims(String bearerToken) throws SignatureException {
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			
			String authToken = bearerToken.substring(7, bearerToken.length());
			
			if (StringUtils.hasText(authToken) && validateToken(authToken)) {

                Claims claims = Jwts.parser()
                        .setSigningKey(jwtConfig.getSecret())
                        .parseClaimsJws(authToken)
                        .getBody();
             
                return claims;
				
			}
		}
		
		return null;	
		
	}
	
	@Bean
  	public JwtConfig jwtConfig() {
		
    	   return new JwtConfig();
  	}
}
