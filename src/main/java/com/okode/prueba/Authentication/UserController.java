package com.okode.prueba.Authentication;

import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.AuthorityUtils;

import com.okode.prueba.Authentication.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class UserController {

	private String Key = "0as84625cAD21G50AD5dAw0";

	@PostMapping("user")
	public User Login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
	
		if(!UserExist(username,pwd)){
			return null;
		}
		String token = getJWTToken(username);
		User user = new User(username,pwd);
		user.setToken(token);
		return user;
	}

	private String getJWTToken(String username) {

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						Key.getBytes()).compact();

		return token;
	}

	private boolean UserExist(String username, String password){
		return true;
	}
}