package com.snackman.datnud11.mapStruct;

import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMap {
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    Users shouldMapUserDtoToUser(UsersDTO usersDTO);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    UsersDTO shouldMapUserToUserDto(Users users);
}
