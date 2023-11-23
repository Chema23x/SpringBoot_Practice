package com.practice.ecommerce.service;

import java.util.Optional;

import com.practice.ecommerce.model.User;

public interface IUserService {
	Optional<User> findById(Integer id);
}
