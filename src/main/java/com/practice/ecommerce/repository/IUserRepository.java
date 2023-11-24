package com.practice.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.ecommerce.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
