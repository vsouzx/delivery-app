package br.com.anhembi.iHealth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String password = "123";
		String encodedPassword = encoder.encode(password);
		
		System.out.println(encodedPassword);
		
	}

}
