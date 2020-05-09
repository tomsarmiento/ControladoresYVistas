package com.loginandregistration.authentication.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.loginandregistration.authentication.models.User;
import com.loginandregistration.authentication.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository r;

	public UserService(UserRepository r) {
		this.r = r;
	}
	
	public User registerUser(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
		user.setPassword(hashed);
		return r.save(user);
	}
	
	public User findByEmail(String email) {
		return r.findByEmail(email);
	}
	
	public User findUserById(Long id) {
		Optional<User> optU = r.findById(id);
		if(optU.isPresent()) {
			return optU.get();
		}
		else {
			return null;
		}
	}
	
	public boolean authenticateUser(String email, String pw) {
		User user = r.findByEmail(email);
		if(user==null) {
			return false;
		}
		else {
			if(BCrypt.checkpw(pw, user.getPassword())) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
