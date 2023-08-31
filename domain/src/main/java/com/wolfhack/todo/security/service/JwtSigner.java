package com.wolfhack.todo.security.service;

import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.exception.ForbiddenException;
import com.wolfhack.todo.security.model.UserSecurity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtSigner {

	private final static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
	private final UserDatabaseAdapter userDatabaseAdapter;

	public String create(String username) {
		return Jwts.builder()
				.signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
				.setSubject(String.valueOf(username))
				.setIssuer("iNikitaHrytsenko")
				.setExpiration(Date.from(Instant.now().plus(Duration.ofHours(24))))
				.setIssuedAt(Date.from(Instant.now()))
				.compact();
	}

	public Jws<Claims> validate(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(keyPair.getPrivate())
				.build()
				.parseClaimsJws(token);
	}

	public UserSecurity validateAndReturnInfo(String token) {
		try {
			Jws<Claims> validate = validate(token);
			return Optional.of(userDatabaseAdapter.getByUsername(validate.getBody().getSubject())).map(UserSecurity::new).get();
		} catch (MalformedJwtException | SecurityException exception) {
			throw new ForbiddenException("Token invalid");
		} catch (ExpiredJwtException exception) {
			throw new ForbiddenException("Token expired");
		}
	}

}
