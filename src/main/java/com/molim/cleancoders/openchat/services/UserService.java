package com.molim.cleancoders.openchat.services;

import java.util.List;

import com.molim.cleancoders.openchat.web.models.UserDto;

public interface UserService {

	List<UserDto> getUsers();

}
