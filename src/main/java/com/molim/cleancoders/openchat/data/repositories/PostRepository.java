package com.molim.cleancoders.openchat.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.molim.cleancoders.openchat.data.entities.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

	List<Post> findByUserId(Long userId);

}
