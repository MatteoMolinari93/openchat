package com.molim.cleancoders.openchat.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.molim.cleancoders.openchat.data.entities.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
