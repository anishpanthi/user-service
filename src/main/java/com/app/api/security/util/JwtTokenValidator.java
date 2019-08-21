package com.app.api.security.util;

import com.app.api.security.dto.JwtUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Anish Panthi
 */
@Component
@Log4j2
public class JwtTokenValidator {

    @Value("${jwt.secret}")
    private String secret;

    public JwtUserDto parseToken(String token) {

        JwtUserDto jwtUserDto = null;

        try {
            Claims body = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token).getBody();
            String userId = body.get("userId").toString();
            String subject = body.getSubject();
            String role = (String) body.get("role");
            jwtUserDto = new JwtUserDto(Long.parseLong(userId), subject, role);
            log.info("Logged in User:: {}", jwtUserDto.toString());
        } catch (JwtException e) {
            log.error("", e);
        }

        return jwtUserDto;
    }
}
