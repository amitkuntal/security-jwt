package com.yogendra.security;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class InvalidJwtAuthenticationException extends AuthenticationException {

	public InvalidJwtAuthenticationException(String msg) {
		super(msg);
	}
}
