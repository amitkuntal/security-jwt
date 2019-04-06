package com.yogendra.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.ResponseEntity.ok;

import com.yogendra.model.AuthenticationRequest;
import com.yogendra.security.JwtTokenProvider;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping("signin")
	public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {
		try {
			String username = data.getUsername();
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			List<String> roles = authorities.stream().map(auths -> auths.getAuthority()).collect(Collectors.toList());
			String token = jwtTokenProvider.createToken(username, roles);

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("username", username);
			map.put("token", token);
			return ok(map);

		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
