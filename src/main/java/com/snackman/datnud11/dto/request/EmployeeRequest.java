package com.snackman.datnud11.dto.request;

import com.snackman.datnud11.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequest extends Employee {
    private String password;
    private String birthday2;

    public EmployeeRequest(EmployeeRequest employeeRequest) {
        super(employeeRequest);
    }

}
