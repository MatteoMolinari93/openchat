package com.molim.cleancoders.openchat.web.mappers;

import org.mapstruct.Mapper;

import com.molim.cleancoders.openchat.data.entities.User;
import com.molim.cleancoders.openchat.web.models.UserDto;

@Mapper
public interface UserMapper {
	
	UserDto UserToUserDto(User user);
	
	User UserDtoToUser(UserDto userDto);

}
