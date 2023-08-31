package com.wolfhack.todo.repository;

import com.wolfhack.todo.model.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EntityUser, Long> {

	Optional<EntityUser> findByEmail(String email);

	Optional<EntityUser> findByUsername(String username);
}