package com.molim.cleancoders.openchat.data.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.molim.cleancoders.openchat.data.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
