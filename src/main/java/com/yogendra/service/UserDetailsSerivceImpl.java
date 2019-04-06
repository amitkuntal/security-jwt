package com.yogendra.service;

import java.sql.ResultSet;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yogendra.model.UserInfo;

@Service
public class UserDetailsSerivceImpl implements UserDetailsService {

	@Autowired
	JdbcTemplate JdbcTemplate;

	String sql = "select users.user_name, users.user_password, user_role.user_role_name from user_role,users where users.user_role_id = user_role.user_role_id and user_name = ?";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = getUserInfo(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		return new User(userInfo.getUserName(), userInfo.getPassword(), Arrays.asList(authority));
	}

	private UserInfo getUserInfo(String username) {
		return JdbcTemplate.queryForObject(sql, new Object[] { username }, (ResultSet rs, int rownum) -> {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(rs.getString("user_name"));
			userInfo.setPassword(rs.getString("user_password"));
			userInfo.setRole(rs.getString("user_role_name"));
			return userInfo;
		});
	}
}
