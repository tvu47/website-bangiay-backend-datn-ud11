package com.snackman.datnud11.mapStruct;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMap {
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    UsersDTO shouldMapEmployeeToUserDto(EmployeeDTO employeeDTO);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")
    EmployeeDTO shouldMapEmployeeToDto(Employee employee);

}
