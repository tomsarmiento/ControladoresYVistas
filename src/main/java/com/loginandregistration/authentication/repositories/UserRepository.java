package com.loginandregistration.authentication.repositories;

import org.springframework.data.repository.CrudRepository;

import com.loginandregistration.authentication.models.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
}
