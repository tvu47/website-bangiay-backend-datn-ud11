package com.snackman.datnud11.mapStruct;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMap {
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    EmployeeDTO shouldMapEmployeeToDto(Employee employee);
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    Employee shouldMapDtoToEmployee(EmployeeDTO employeeDTO);

}
